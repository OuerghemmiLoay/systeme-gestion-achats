package tn.itbs.note.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeAchatDTO {

    private Long id;

    @NotNull(message = "L'ID du fournisseur est requis")
    private Long fournisseurId;

    private String fournisseurNom;

    @NotNull(message = "La date de commande est requise")
    private LocalDateTime date;

    @NotNull(message = "Le statut de la commande est requis")
    private String statut;

    @NotNull(message = "Le montant de la commande est requis")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @Size(max = 1000, message = "Les notes ne peuvent pas dépasser 1000 caractères")
    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
