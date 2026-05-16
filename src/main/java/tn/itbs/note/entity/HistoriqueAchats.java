package tn.itbs.note.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "historique_achats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueAchats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @NotBlank(message = "Le nom du produit est requis")
    @Size(max = 255, message = "Le nom du produit ne peut pas dépasser 255 caractères")
    @Column(nullable = false, length = 255)
    private String produit;

    @NotNull(message = "La quantité est requise")
    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    @Column(nullable = false)
    private Integer quantite;

    @NotNull(message = "Le délai de livraison est requis")
    @Min(value = 0, message = "Le délai doit être égal ou supérieur à 0")
    @Column(nullable = false)
    private Integer delai_livraison; // in days

    @Column(name = "date_achat", nullable = false)
    private LocalDateTime dateAchat;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.dateAchat == null) {
            this.dateAchat = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
