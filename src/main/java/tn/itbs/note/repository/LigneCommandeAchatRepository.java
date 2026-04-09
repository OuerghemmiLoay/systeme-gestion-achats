package tn.itbs.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.itbs.note.entity.LigneCommandeAchat;

import java.util.List;

/**
 * Repository for LigneCommandeAchat entity.
 */
@Repository
public interface LigneCommandeAchatRepository extends JpaRepository<LigneCommandeAchat, Long> {

    /**
     * Find all line items for a specific order.
     */
    @Query("SELECT l FROM LigneCommandeAchat l WHERE l.commande.id = :commandeId ORDER BY l.id")
    List<LigneCommandeAchat> findByCommandeId(@Param("commandeId") Long commandeId);

    /**
     * Find all line items for a specific product.
     */
    @Query("SELECT l FROM LigneCommandeAchat l WHERE l.produit.id = :produitId ORDER BY l.createdAt DESC")
    List<LigneCommandeAchat> findByProduitId(@Param("produitId") Long produitId);

    /**
     * Find line items by product name pattern.
     */
    @Query("SELECT l FROM LigneCommandeAchat l WHERE LOWER(l.produit.nom) LIKE LOWER(CONCAT('%', :nom, '%')) ORDER BY l.createdAt DESC")
    List<LigneCommandeAchat> findByProduitNomContaining(@Param("nom") String nom);
}
