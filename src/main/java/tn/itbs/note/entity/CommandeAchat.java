package tn.itbs.note.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CommandeAchat entity representing a purchase order.
 */
@Entity
@Table(name = "commandes_achat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @NotNull(message = "La date de commande est requise")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Le statut de la commande est requis")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut;

    @NotNull(message = "Le montant de la commande est requis")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montant;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LigneCommandeAchat> lignes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum StatutCommande {
        PENDING("En attente"),
        CONFIRMED("Confirmée"),
        SHIPPED("Expédiée"),
        DELIVERED("Livrée"),
        CANCELLED("Annulée");

        private final String label;

        StatutCommande(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
