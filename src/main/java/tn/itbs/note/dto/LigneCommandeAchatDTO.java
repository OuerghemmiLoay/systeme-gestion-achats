package tn.itbs.note.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for LigneCommandeAchat entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommandeAchatDTO {

    private Long id;

    @NotNull(message = "L'ID de la commande est requis")
    private Long commandeId;

    @NotNull(message = "L'ID du produit est requis")
    private Long produitId;

    private String produitNom;

    @NotNull(message = "La quantité est requise")
    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private Integer quantite;

    @NotNull(message = "Le prix unitaire est requis")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private BigDecimal prix_unitaire;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
