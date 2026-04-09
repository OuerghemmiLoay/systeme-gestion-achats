package tn.itbs.note.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for HistoriqueAchats entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueAchatsDTO {

    private Long id;

    @NotNull(message = "L'ID du fournisseur est requis")
    private Long fournisseurId;

    private String fournisseurNom;

    @NotBlank(message = "Le nom du produit est requis")
    @Size(max = 255, message = "Le nom du produit ne peut pas dépasser 255 caractères")
    private String produit;

    @NotNull(message = "La quantité est requise")
    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private Integer quantite;

    @NotNull(message = "Le délai de livraison est requis")
    @Min(value = 0, message = "Le délai doit être égal ou supérieur à 0")
    private Integer delai_livraison;

    private LocalDateTime dateAchat;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
