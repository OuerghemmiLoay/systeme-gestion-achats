package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.LigneCommandeAchatDTO;
import tn.itbs.note.entity.CommandeAchat;
import tn.itbs.note.entity.LigneCommandeAchat;
import tn.itbs.note.entity.Produit;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.CommandeAchatRepository;
import tn.itbs.note.repository.LigneCommandeAchatRepository;
import tn.itbs.note.repository.ProduitRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing purchase order line items.
 */
@Service
@Transactional
public class LigneCommandeAchatService {

    @Autowired
    private LigneCommandeAchatRepository ligneRepository;

    @Autowired
    private CommandeAchatRepository commandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Get all line items for an order.
     */
    public List<LigneCommandeAchatDTO> getLignesForCommande(Long commandeId) {
        commandeRepository.findById(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", commandeId));
        
        return ligneRepository.findByCommandeId(commandeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get line item by ID.
     */
    public LigneCommandeAchatDTO getLigneById(Long id) {
        LigneCommandeAchat ligne = ligneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne commande", "id", id));
        return convertToDTO(ligne);
    }

    /**
     * Create new line item.
     */
    public LigneCommandeAchatDTO createLigne(LigneCommandeAchatDTO ligneDTO) {
        CommandeAchat commande = commandeRepository.findById(ligneDTO.getCommandeId())
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", ligneDTO.getCommandeId()));

        Produit produit = produitRepository.findById(ligneDTO.getProduitId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ligneDTO.getProduitId()));

        // Validate that order is still editable
        if (commande.getStatut() != CommandeAchat.StatutCommande.PENDING) {
            throw new BusinessException("Impossible d'ajouter une ligne à une commande non-modifiable");
        }

        LigneCommandeAchat ligne = LigneCommandeAchat.builder()
                .commande(commande)
                .produit(produit)
                .quantite(ligneDTO.getQuantite())
                .prix_unitaire(ligneDTO.getPrix_unitaire())
                .build();

        LigneCommandeAchat savedLigne = ligneRepository.save(ligne);
        return convertToDTO(savedLigne);
    }

    /**
     * Update line item.
     */
    public LigneCommandeAchatDTO updateLigne(Long id, LigneCommandeAchatDTO ligneDTO) {
        LigneCommandeAchat ligne = ligneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne commande", "id", id));

        // Validate that order is still editable
        if (ligne.getCommande().getStatut() != CommandeAchat.StatutCommande.PENDING) {
            throw new BusinessException("Impossible de modifier une ligne d'une commande non-modifiable");
        }

        ligne.setQuantite(ligneDTO.getQuantite());
        ligne.setPrix_unitaire(ligneDTO.getPrix_unitaire());

        LigneCommandeAchat updatedLigne = ligneRepository.save(ligne);
        return convertToDTO(updatedLigne);
    }

    /**
     * Delete line item.
     */
    public void deleteLigne(Long id) {
        LigneCommandeAchat ligne = ligneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne commande", "id", id));

        // Validate that order is still editable
        if (ligne.getCommande().getStatut() != CommandeAchat.StatutCommande.PENDING) {
            throw new BusinessException("Impossible de supprimer une ligne d'une commande non-modifiable");
        }

        ligneRepository.delete(ligne);
    }

    /**
     * Convert entity to DTO.
     */
    private LigneCommandeAchatDTO convertToDTO(LigneCommandeAchat ligne) {
        return LigneCommandeAchatDTO.builder()
                .id(ligne.getId())
                .commandeId(ligne.getCommande().getId())
                .produitId(ligne.getProduit().getId())
                .produitNom(ligne.getProduit().getNom())
                .quantite(ligne.getQuantite())
                .prix_unitaire(ligne.getPrix_unitaire())
                .total(ligne.getTotal())
                .createdAt(ligne.getCreatedAt())
                .updatedAt(ligne.getUpdatedAt())
                .build();
    }
}
