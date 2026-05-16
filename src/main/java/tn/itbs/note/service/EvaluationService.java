package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.EvaluationDTO;
import tn.itbs.note.dto.OffreProduitDTO;
import tn.itbs.note.dto.OffreProduitDTO.OffreDetailDTO;
import tn.itbs.note.entity.Fournisseur;
import tn.itbs.note.entity.HistoriqueAchats;
import tn.itbs.note.entity.LigneCommandeAchat;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class EvaluationService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private HistoriqueAchatsRepository historiqueRepository;

    @Autowired
    private LigneCommandeAchatRepository ligneRepository;

    @Autowired
    private FournisseurService fournisseurService;

    /**
     * Get detailed evaluation for a supplier.
     */
    public EvaluationDTO evaluateSupplier(Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", fournisseurId));

        List<HistoriqueAchats> historique = historiqueRepository.findByFournisseurId(fournisseurId);

        Double avgDeliveryTime = historiqueRepository.findAverageDeliveryTimeByFournisseurId(fournisseurId);
        if (avgDeliveryTime == null) {
            avgDeliveryTime = 0.0;
        }

        Double onTimeRate = calculateOnTimeDeliveryRate(historique);
        BigDecimal avgCost = calculateAverageCost(fournisseurId);
        Double overallRating = fournisseur.getNote();
        String recommendation = generateRecommendation(fournisseur, avgDeliveryTime, onTimeRate, overallRating);

        return EvaluationDTO.builder()
                .fournisseurId(fournisseurId)
                .fournisseurNom(fournisseur.getNom())
                .qualiteService(fournisseur.getQualite_service())
                .averageDeliveryTime(avgDeliveryTime)
                .onTimeDeliveryRate(onTimeRate)
                .averageCost(avgCost)
                .overallRating(overallRating)
                .recommendation(recommendation)
                .build();
    }

    /**
     * Get evaluations for all suppliers ranked by rating.
     */
    public List<EvaluationDTO> evaluateAllSuppliers() {
        return fournisseurRepository.findAllByRating().stream()
                .map(f -> evaluateSupplier(f.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Compare offers from multiple suppliers for the same product.
     */
    public OffreProduitDTO compareOffers(String nomProduit) {
        // Find all line items containing this product
        List<LigneCommandeAchat> lignes = ligneRepository.findByProduitNomContaining(nomProduit);

        if (lignes.isEmpty()) {
            throw new ResourceNotFoundException("Aucune offre trouvée pour le produit", "nom", nomProduit);
        }

        // Group by supplier and calculate averages
        Map<Long, List<LigneCommandeAchat>> suppliersOffres = lignes.stream()
                .collect(Collectors.groupingBy(l -> l.getCommande().getFournisseur().getId()));

        List<OffreDetailDTO> offres = new ArrayList<>();
        
        for (Map.Entry<Long, List<LigneCommandeAchat>> entry : suppliersOffres.entrySet()) {
            Long fournisseurId = entry.getKey();
            List<LigneCommandeAchat> lignesSupplier = entry.getValue();

            Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElse(null);
            if (fournisseur == null) continue;

            BigDecimal avgPrice = lignesSupplier.stream()
                    .map(LigneCommandeAchat::getPrix_unitaire)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(lignesSupplier.size()), BigDecimal.ROUND_HALF_UP);

            Double avgDeliveryTime = historiqueRepository.findByFournisseurIdAndProduit(
                    fournisseurId, nomProduit
            ).stream()
                    .mapToInt(HistoriqueAchats::getDelai_livraison)
                    .average()
                    .orElse(0.0);

            int score = calculateOfferScore(avgPrice, fournisseur.getQualite_service(), avgDeliveryTime);

            offres.add(OffreDetailDTO.builder()
                    .fournisseurId(fournisseurId)
                    .fournisseurNom(fournisseur.getNom())
                    .prix(avgPrice)
                    .qualiteService((double) fournisseur.getQualite_service())
                    .note(fournisseur.getNote())
                    .delaiLivraison(avgDeliveryTime.intValue())
                    .score(score)
                    .build());
        }

        // Sort by score descending
        offres.sort(Comparator.comparingInt(OffreDetailDTO::getScore).reversed());

        return OffreProduitDTO.builder()
                .produitNom(nomProduit)
                .offres(offres)
                .bestOffer(offres.isEmpty() ? null : offres.get(0))
                .build();
    }

    /**
     * Calculate on-time delivery rate (simplified: delivery time <= 7 days).
     */
    private Double calculateOnTimeDeliveryRate(List<HistoriqueAchats> historique) {
        if (historique.isEmpty()) {
            return 0.0;
        }

        long onTimeCount = historique.stream()
                .filter(h -> h.getDelai_livraison() <= 7)
                .count();

        return (onTimeCount * 100.0) / historique.size();
    }

    /**
     * Calculate average cost for supplier from historical data.
     */
    private BigDecimal calculateAverageCost(Long fournisseurId) {
        List<LigneCommandeAchat> lignes = ligneRepository.findByCommandeId(1L); // This is a simplified approach

        if (lignes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return lignes.stream()
                .map(LigneCommandeAchat::getPrix_unitaire)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(lignes.size()), BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Generate recommendation based on evaluation metrics.
     */
    private String generateRecommendation(Fournisseur fournisseur, Double avgDeliveryTime, 
                                         Double onTimeRate, Double overallRating) {
        StringBuilder recommendation = new StringBuilder();

        if (overallRating >= 80) {
            recommendation.append("★★★★★ Excellent supplier - Highly recommended. ");
        } else if (overallRating >= 60) {
            recommendation.append("★★★★ Good supplier - Recommended. ");
        } else if (overallRating >= 40) {
            recommendation.append("★★★ Average supplier - Consider alternatives. ");
        } else {
            recommendation.append("★★ Poor supplier - Not recommended. ");
        }

        if (onTimeRate < 50) {
            recommendation.append("Watch delivery times closely. ");
        }

        if (fournisseur.getQualite_service() >= 4) {
            recommendation.append("Good quality service. ");
        }

        return recommendation.toString();
    }

    /**
     * Calculate offer score based on price, quality, and delivery.
     */
    private int calculateOfferScore(BigDecimal price, Integer qualityService, Double deliveryDays) {
        // Weighted scoring: 40% quality, 30% price, 30% delivery
        int qualityScore = qualityService * 20; // 0-100
        int priceScore = Math.max(0, 100 - (price.intValue() * 5)); // Lower price = higher score
        int deliveryScore = Math.max(0, 100 - (deliveryDays.intValue() * 5)); // Fewer days = higher score

        return (int) ((qualityScore * 0.4) + (priceScore * 0.3) + (deliveryScore * 0.3));
    }
}
