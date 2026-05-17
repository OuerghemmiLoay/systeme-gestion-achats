package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.HistoriqueAchatsDTO;
import tn.itbs.note.entity.Fournisseur;
import tn.itbs.note.entity.HistoriqueAchats;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HistoriqueAchatsService {

    @Autowired
    private HistoriqueAchatsRepository historiqueRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    /**
     * Get all purchase history records.
     */
    @Transactional(readOnly = true)
    public List<HistoriqueAchatsDTO> getAllHistoriques() {
        return historiqueRepository.findAll().stream()
                .sorted(Comparator.comparing(HistoriqueAchats::getDateAchat).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get purchase history by ID.
     */
    @Transactional(readOnly = true)
    public HistoriqueAchatsDTO getHistoriqueById(Long id) {
        HistoriqueAchats historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistoriqueAchats", "id", id));
        return convertToDTO(historique);
    }

    /**
     * Get purchase history by supplier.
     */
    @Transactional(readOnly = true)
    public List<HistoriqueAchatsDTO> getHistoriquesByFournisseur(Long fournisseurId) {
        fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", fournisseurId));
        return historiqueRepository.findByFournisseurId(fournisseurId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get purchase history by product name.
     */
    @Transactional(readOnly = true)
    public List<HistoriqueAchatsDTO> getHistoriquesByProduit(String produit) {
        return historiqueRepository.findByProduitContaining(produit).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get purchase history by date range.
     */
    @Transactional(readOnly = true)
    public List<HistoriqueAchatsDTO> getHistoriquesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("La date de debut doit etre avant la date de fin");
        }

        return historiqueRepository.findByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new purchase history record.
     */
    public HistoriqueAchatsDTO createHistorique(HistoriqueAchatsDTO dto) {
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", dto.getFournisseurId()));

        HistoriqueAchats historique = HistoriqueAchats.builder()
                .fournisseur(fournisseur)
                .produit(dto.getProduit())
                .quantite(dto.getQuantite())
                .delai_livraison(dto.getDelai_livraison())
                .dateAchat(dto.getDateAchat())
                .build();

        HistoriqueAchats saved = historiqueRepository.save(historique);
        return convertToDTO(saved);
    }

    /**
     * Update an existing purchase history record.
     */
    public HistoriqueAchatsDTO updateHistorique(Long id, HistoriqueAchatsDTO dto) {
        HistoriqueAchats historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistoriqueAchats", "id", id));

        if (dto.getFournisseurId() != null &&
                !dto.getFournisseurId().equals(historique.getFournisseur().getId())) {
            Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", dto.getFournisseurId()));
            historique.setFournisseur(fournisseur);
        }

        historique.setProduit(dto.getProduit());
        historique.setQuantite(dto.getQuantite());
        historique.setDelai_livraison(dto.getDelai_livraison());

        if (dto.getDateAchat() != null) {
            historique.setDateAchat(dto.getDateAchat());
        }

        HistoriqueAchats updated = historiqueRepository.save(historique);
        return convertToDTO(updated);
    }

    /**
     * Delete purchase history record.
     */
    public void deleteHistorique(Long id) {
        HistoriqueAchats historique = historiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistoriqueAchats", "id", id));
        historiqueRepository.delete(historique);
    }

    private HistoriqueAchatsDTO convertToDTO(HistoriqueAchats historique) {
        return HistoriqueAchatsDTO.builder()
                .id(historique.getId())
                .fournisseurId(historique.getFournisseur().getId())
                .fournisseurNom(historique.getFournisseur().getNom())
                .produit(historique.getProduit())
                .quantite(historique.getQuantite())
                .delai_livraison(historique.getDelai_livraison())
                .dateAchat(historique.getDateAchat())
                .createdAt(historique.getCreatedAt())
                .updatedAt(historique.getUpdatedAt())
                .build();
    }
}
