package tn.itbs.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.itbs.note.entity.CommandeAchat;
import tn.itbs.note.entity.CommandeAchat.StatutCommande;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CommandeAchatRepository extends JpaRepository<CommandeAchat, Long> {

    /**
     * Find all orders for a specific supplier.
     */
    @Query("SELECT c FROM CommandeAchat c WHERE c.fournisseur.id = :fournisseurId ORDER BY c.date DESC")
    List<CommandeAchat> findByFournisseurId(@Param("fournisseurId") Long fournisseurId);

    /**
     * Find orders by status.
     */
    List<CommandeAchat> findByStatut(StatutCommande statut);

    /**
     * Find orders by status and supplier.
     */
    @Query("SELECT c FROM CommandeAchat c WHERE c.fournisseur.id = :fournisseurId AND c.statut = :statut ORDER BY c.date DESC")
    List<CommandeAchat> findByFournisseurIdAndStatut(@Param("fournisseurId") Long fournisseurId, @Param("statut") StatutCommande statut);

    /**
     * Find orders within a date range.
     */
    @Query("SELECT c FROM CommandeAchat c WHERE c.date BETWEEN :startDate AND :endDate ORDER BY c.date DESC")
    List<CommandeAchat> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders for a supplier within a date range.
     */
    @Query("SELECT c FROM CommandeAchat c WHERE c.fournisseur.id = :fournisseurId AND c.date BETWEEN :startDate AND :endDate ORDER BY c.date DESC")
    List<CommandeAchat> findByFournisseurIdAndDateRange(@Param("fournisseurId") Long fournisseurId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Count orders by status.
     */
    long countByStatut(StatutCommande statut);

    /**
     * Count orders for a specific supplier and status.
     */
    @Query("SELECT COUNT(c) FROM CommandeAchat c WHERE c.fournisseur.id = :fournisseurId AND c.statut = :statut")
    long countByFournisseurIdAndStatut(@Param("fournisseurId") Long fournisseurId, @Param("statut") StatutCommande statut);
}
