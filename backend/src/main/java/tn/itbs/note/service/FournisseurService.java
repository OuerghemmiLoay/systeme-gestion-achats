package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.FournisseurDTO;
import tn.itbs.note.entity.Fournisseur;
import tn.itbs.note.entity.HistoriqueAchats;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private HistoriqueAchatsRepository historiqueAchatsRepository;

    /**
     * Get all suppliers.
     */
    public List<FournisseurDTO> getAllFournisseurs() {
        return fournisseurRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get suppliers ranked by rating.
     */
    public List<FournisseurDTO> getFournisseursRankedByRating() {
        return fournisseurRepository.findAllByRating().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get supplier by ID.
     */
    public FournisseurDTO getFournisseurById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", id));
        return convertToDTO(fournisseur);
    }

    /**
     * Get supplier by name.
     */
    public FournisseurDTO getFournisseurByNom(String nom) {
        Fournisseur fournisseur = fournisseurRepository.findByNomIgnoreCase(nom)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "nom", nom));
        return convertToDTO(fournisseur);
    }

    /**
     * Get high-quality suppliers (quality >= 4).
     */
    public List<FournisseurDTO> getHighQualityFournisseurs() {
        return fournisseurRepository.findByQualiteServiceGreaterThanEqual(4).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new supplier.
     */
    public FournisseurDTO createFournisseur(FournisseurDTO fournisseurDTO) {
        // Check if supplier with same name exists
        if (fournisseurRepository.findByNomIgnoreCase(fournisseurDTO.getNom()).isPresent()) {
            throw new BusinessException("Un fournisseur avec le nom '" + fournisseurDTO.getNom() + "' existe déjà");
        }

        // Check if supplier with same contact exists
        if (fournisseurRepository.findByContact(fournisseurDTO.getContact()).isPresent()) {
            throw new BusinessException("Un fournisseur avec le contact '" + fournisseurDTO.getContact() + "' existe déjà");
        }

        Fournisseur fournisseur = convertToEntity(fournisseurDTO);
        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
        return convertToDTO(savedFournisseur);
    }

    /**
     * Update supplier.
     */
    public FournisseurDTO updateFournisseur(Long id, FournisseurDTO fournisseurDTO) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", id));

        // Check if another supplier with same name exists
        if (!fournisseur.getNom().equalsIgnoreCase(fournisseurDTO.getNom())) {
            if (fournisseurRepository.findByNomIgnoreCase(fournisseurDTO.getNom()).isPresent()) {
                throw new BusinessException("Un fournisseur avec le nom '" + fournisseurDTO.getNom() + "' existe déjà");
            }
        }

        // Check if another supplier with same contact exists
        if (!fournisseur.getContact().equals(fournisseurDTO.getContact())) {
            if (fournisseurRepository.findByContact(fournisseurDTO.getContact()).isPresent()) {
                throw new BusinessException("Un fournisseur avec le contact '" + fournisseurDTO.getContact() + "' existe déjà");
            }
        }

        fournisseur.setNom(fournisseurDTO.getNom());
        fournisseur.setContact(fournisseurDTO.getContact());
        fournisseur.setQualite_service(fournisseurDTO.getQualite_service());

        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
        return convertToDTO(updatedFournisseur);
    }

    /**
     * Delete supplier.
     */
    public void deleteFournisseur(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", id));
        fournisseurRepository.delete(fournisseur);
    }

    /**
     * Calculate supplier rating based on quality and delivery history.
     */
    @Transactional(readOnly = true)
    public Double calculateSupplierRating(Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", fournisseurId));

        List<HistoriqueAchats> historique = historiqueAchatsRepository.findByFournisseurId(fournisseurId);

        if (historique.isEmpty()) {
            // If no history, return quality score
            return fournisseur.getQualite_service() * 20.0; // Scale to 0-100
        }

        // Calculate on-time delivery rate (simplified: based on delivery time)
        double avgDeliveryTime = historique.stream()
                .mapToInt(HistoriqueAchats::getDelai_livraison)
                .average()
                .orElse(0.0);

        // Assume ideal delivery time is 5 days
        double deliveryScore = Math.max(0, 100 - (avgDeliveryTime * 5));

        // Combine quality and delivery score
        double qualityScore = fournisseur.getQualite_service() * 20.0;
        double overallRating = (qualityScore * 0.4) + (deliveryScore * 0.6);

        return Math.min(100, Math.max(0, overallRating));
    }

    /**
     * Update supplier rating.
     */
    public void updateSupplierRating(Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", fournisseurId));

        Double newRating = calculateSupplierRating(fournisseurId);
        fournisseur.setNote(newRating);
        fournisseurRepository.save(fournisseur);
    }

    /**
     * Convert entity to DTO.
     */
    private FournisseurDTO convertToDTO(Fournisseur fournisseur) {
        return FournisseurDTO.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .contact(fournisseur.getContact())
                .qualite_service(fournisseur.getQualite_service())
                .note(fournisseur.getNote())
                .createdAt(fournisseur.getCreatedAt())
                .updatedAt(fournisseur.getUpdatedAt())
                .build();
    }

    /**
     * Convert DTO to entity.
     */
    private Fournisseur convertToEntity(FournisseurDTO fournisseurDTO) {
        return Fournisseur.builder()
                .nom(fournisseurDTO.getNom())
                .contact(fournisseurDTO.getContact())
                .qualite_service(fournisseurDTO.getQualite_service())
                .note(0.0)
                .build();
    }
}
