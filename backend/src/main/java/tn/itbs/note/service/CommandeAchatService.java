package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.CommandeAchatDTO;
import tn.itbs.note.entity.*;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CommandeAchatService {

    @Autowired
    private CommandeAchatRepository commandeAchatRepository;

    @Autowired
    private LigneCommandeAchatRepository ligneCommandeAchatRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private HistoriqueAchatsRepository historiqueAchatsRepository;

    @Autowired
    private FournisseurService fournisseurService;

    /**
     * Get all orders.
     */
    public List<CommandeAchatDTO> getAllCommandes() {
        return commandeAchatRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order by ID.
     */
    public CommandeAchatDTO getCommandeById(Long id) {
        CommandeAchat commande = commandeAchatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));
        return convertToDTO(commande);
    }

    /**
     * Get all orders for a supplier.
     */
    public List<CommandeAchatDTO> getCommandesByFournisseur(Long fournisseurId) {
        fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", fournisseurId));
        return commandeAchatRepository.findByFournisseurId(fournisseurId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get orders by status.
     */
    public List<CommandeAchatDTO> getCommandesByStatut(String statut) {
        try {
            CommandeAchat.StatutCommande status = CommandeAchat.StatutCommande.valueOf(statut.toUpperCase());
            return commandeAchatRepository.findByStatut(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Statut invalide: " + statut);
        }
    }

    /**
     * Create new order.
     */
    public CommandeAchatDTO createCommande(CommandeAchatDTO commandeDTO) {
        // Validate supplier exists
        Fournisseur fournisseur = fournisseurRepository.findById(commandeDTO.getFournisseurId())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", commandeDTO.getFournisseurId()));

        CommandeAchat commande = CommandeAchat.builder()
                .fournisseur(fournisseur)
                .date(commandeDTO.getDate() != null ? commandeDTO.getDate() : LocalDateTime.now())
                .statut(CommandeAchat.StatutCommande.PENDING)
                .montant(commandeDTO.getMontant())
                .notes(commandeDTO.getNotes())
                .build();

        CommandeAchat savedCommande = commandeAchatRepository.save(commande);
        return convertToDTO(savedCommande);
    }

    /**
     * Update order.
     */
    public CommandeAchatDTO updateCommande(Long id, CommandeAchatDTO commandeDTO) {
        CommandeAchat commande = commandeAchatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));

        // Only allow status change to valid next states
        if (commandeDTO.getStatut() != null) {
            CommandeAchat.StatutCommande newStatus = CommandeAchat.StatutCommande.valueOf(commandeDTO.getStatut().toUpperCase());
            validateStatusTransition(commande.getStatut(), newStatus);
            commande.setStatut(newStatus);
        }

        if (commandeDTO.getNotes() != null) {
            commande.setNotes(commandeDTO.getNotes());
        }

        if (commandeDTO.getMontant() != null) {
            commande.setMontant(commandeDTO.getMontant());
        }

        CommandeAchat updatedCommande = commandeAchatRepository.save(commande);
        return convertToDTO(updatedCommande);
    }

    /**
     * Delete order.
     */
    public void deleteCommande(Long id) {
        CommandeAchat commande = commandeAchatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));
        
        if (commande.getStatut() != CommandeAchat.StatutCommande.PENDING) {
            throw new BusinessException("Impossible de supprimer une commande qui n'est pas en attente");
        }
        
        commandeAchatRepository.delete(commande);
    }

    /**
     * Update order status (with delivery tracking).
     */
    public CommandeAchatDTO updateCommandeStatus(Long id, String newStatus) {
        CommandeAchat commande = commandeAchatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));

        CommandeAchat.StatutCommande status = CommandeAchat.StatutCommande.valueOf(newStatus.toUpperCase());
        validateStatusTransition(commande.getStatut(), status);
        
        commande.setStatut(status);
        
        // If delivered, record in history
        if (status == CommandeAchat.StatutCommande.DELIVERED) {
            recordDeliveryInHistory(commande);
        }

        CommandeAchat updatedCommande = commandeAchatRepository.save(commande);
        
        // Update supplier rating after order is delivered
        if (status == CommandeAchat.StatutCommande.DELIVERED) {
            fournisseurService.updateSupplierRating(commande.getFournisseur().getId());
        }

        return convertToDTO(updatedCommande);
    }

    /**
     * Validate status transition (restricted workflow).
     */
    private void validateStatusTransition(CommandeAchat.StatutCommande currentStatus, CommandeAchat.StatutCommande newStatus) {
        if (currentStatus == CommandeAchat.StatutCommande.CANCELLED) {
            throw new BusinessException("Impossible de modifier une commande annulée");
        }

        if (currentStatus == CommandeAchat.StatutCommande.DELIVERED) {
            throw new BusinessException("Impossible de modifier une commande livrée");
        }

        // Allowed transitions
        if (currentStatus == CommandeAchat.StatutCommande.PENDING && 
            (newStatus != CommandeAchat.StatutCommande.CONFIRMED && newStatus != CommandeAchat.StatutCommande.CANCELLED)) {
            throw new BusinessException("La commande doit être confirmée avant d'être expédiée");
        }

        if (currentStatus == CommandeAchat.StatutCommande.CONFIRMED && 
            (newStatus != CommandeAchat.StatutCommande.SHIPPED && newStatus != CommandeAchat.StatutCommande.CANCELLED)) {
            throw new BusinessException("Transition d'état non autorisée");
        }

        if (currentStatus == CommandeAchat.StatutCommande.SHIPPED && 
            newStatus != CommandeAchat.StatutCommande.DELIVERED) {
            throw new BusinessException("Une commande expédiée ne peut que être livrée");
        }
    }

    /**
     * Record delivery in purchase history.
     */
    private void recordDeliveryInHistory(CommandeAchat commande) {
        List<LigneCommandeAchat> lignes = ligneCommandeAchatRepository.findByCommandeId(commande.getId());
        
        for (LigneCommandeAchat ligne : lignes) {
            long deliveryTimeInDays = java.time.temporal.ChronoUnit.DAYS.between(
                    commande.getDate().toLocalDate(),
                    LocalDateTime.now().toLocalDate()
            );

            HistoriqueAchats historique = HistoriqueAchats.builder()
                    .fournisseur(commande.getFournisseur())
                    .produit(ligne.getProduit().getNom())
                    .quantite(ligne.getQuantite())
                    .delai_livraison((int) deliveryTimeInDays)
                    .dateAchat(commande.getDate())
                    .build();

            historiqueAchatsRepository.save(historique);
        }
    }

    /**
     * Convert entity to DTO.
     */
    private CommandeAchatDTO convertToDTO(CommandeAchat commande) {
        return CommandeAchatDTO.builder()
                .id(commande.getId())
                .fournisseurId(commande.getFournisseur().getId())
                .fournisseurNom(commande.getFournisseur().getNom())
                .date(commande.getDate())
                .statut(commande.getStatut().name())
                .montant(commande.getMontant())
                .notes(commande.getNotes())
                .createdAt(commande.getCreatedAt())
                .updatedAt(commande.getUpdatedAt())
                .build();
    }

    /**
     * Convert DTO to entity.
     */
    private CommandeAchat convertToEntity(CommandeAchatDTO commandeDTO) {
        Fournisseur fournisseur = fournisseurRepository.findById(commandeDTO.getFournisseurId())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", "id", commandeDTO.getFournisseurId()));

        return CommandeAchat.builder()
                .fournisseur(fournisseur)
                .date(commandeDTO.getDate())
                .montant(commandeDTO.getMontant())
                .notes(commandeDTO.getNotes())
                .build();
    }
}
