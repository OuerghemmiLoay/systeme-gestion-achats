package tn.itbs.note.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Produit entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDTO {

    private Long id;

    @NotBlank(message = "Le nom du produit est requis")
    @Size(min = 3, max = 255, message = "Le nom doit être entre 3 et 255 caractères")
    private String nom;

    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;

    @NotNull(message = "Le prix de référence est requis")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private BigDecimal prix_reference;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
