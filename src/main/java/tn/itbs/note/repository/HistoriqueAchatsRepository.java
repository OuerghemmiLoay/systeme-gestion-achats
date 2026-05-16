package tn.itbs.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.itbs.note.entity.HistoriqueAchats;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HistoriqueAchatsRepository extends JpaRepository<HistoriqueAchats, Long> {

    /**
     * Find all historical records for a specific supplier.
     */
    @Query("SELECT h FROM HistoriqueAchats h WHERE h.fournisseur.id = :fournisseurId ORDER BY h.dateAchat DESC")
    List<HistoriqueAchats> findByFournisseurId(@Param("fournisseurId") Long fournisseurId);

    /**
     * Find historical records by product name.
     */
    @Query("SELECT h FROM HistoriqueAchats h WHERE LOWER(h.produit) LIKE LOWER(CONCAT('%', :produit, '%')) ORDER BY h.dateAchat DESC")
    List<HistoriqueAchats> findByProduitContaining(@Param("produit") String produit);

    /**
     * Find historical records for a supplier and product combination.
     */
    @Query("SELECT h FROM HistoriqueAchats h WHERE h.fournisseur.id = :fournisseurId AND LOWER(h.produit) = LOWER(:produit) ORDER BY h.dateAchat DESC")
    List<HistoriqueAchats> findByFournisseurIdAndProduit(@Param("fournisseurId") Long fournisseurId, @Param("produit") String produit);

    /**
     * Find historical records within a date range.
     */
    @Query("SELECT h FROM HistoriqueAchats h WHERE h.dateAchat BETWEEN :startDate AND :endDate ORDER BY h.dateAchat DESC")
    List<HistoriqueAchats> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Calculate average delivery time for a supplier.
     */
    @Query("SELECT AVG(h.delai_livraison) FROM HistoriqueAchats h WHERE h.fournisseur.id = :fournisseurId")
    Double findAverageDeliveryTimeByFournisseurId(@Param("fournisseurId") Long fournisseurId);
}
