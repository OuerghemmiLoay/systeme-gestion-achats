package tn.itbs.note.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Fournisseur entity representing a supplier in the system.
 */
@Entity
@Table(name = "fournisseurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du fournisseur est requis")
    @Size(min = 3, max = 255, message = "Le nom doit être entre 3 et 255 caractères")
    @Column(nullable = false, length = 255)
    private String nom;

    @NotBlank(message = "Le contact est requis")
    @Email(message = "Le contact doit être une adresse email valide")
    @Column(nullable = false, length = 255)
    private String contact;

    @NotNull(message = "La qualité de service est requise")
    @Min(value = 1, message = "La qualité doit être entre 1 et 5")
    @Max(value = 5, message = "La qualité doit être entre 1 et 5")
    @Column(nullable = false)
    private Integer qualite_service;

    @Column(nullable = false)
    @Builder.Default
    private Double note = 0.0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommandeAchat> commandes = new ArrayList<>();

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoriqueAchats> historiques = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
