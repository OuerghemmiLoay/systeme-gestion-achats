package tn.itbs.note.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FournisseurDTO {

    private Long id;

    @NotBlank(message = "Le nom du fournisseur est requis")
    @Size(min = 3, max = 255, message = "Le nom doit être entre 3 et 255 caractères")
    private String nom;

    @NotBlank(message = "Le contact est requis")
    @Email(message = "Le contact doit être une adresse email valide")
    private String contact;

    @NotNull(message = "La qualité de service est requise")
    @Min(value = 1, message = "La qualité doit être entre 1 et 5")
    @Max(value = 5, message = "La qualité doit être entre 1 et 5")
    private Integer qualite_service;

    private Double note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
