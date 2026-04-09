package tn.itbs.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.itbs.note.entity.Fournisseur;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Fournisseur entity.
 */
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    /**
     * Find supplier by name (case-insensitive).
     */
    Optional<Fournisseur> findByNomIgnoreCase(String nom);

    /**
     * Find all suppliers sorted by rating in descending order.
     */
    @Query("SELECT f FROM Fournisseur f ORDER BY f.note DESC")
    List<Fournisseur> findAllByRating();

    /**
     * Find suppliers with quality service >= specified value.
     */
    @Query("SELECT f FROM Fournisseur f WHERE f.qualite_service >= :qualite ORDER BY f.note DESC")
    List<Fournisseur> findByQualiteServiceGreaterThanEqual(@Param("qualite") Integer qualite);

    /**
     * Find suppliers with note >= specified value.
     */
    @Query("SELECT f FROM Fournisseur f WHERE f.note >= :note ORDER BY f.note DESC")
    List<Fournisseur> findByNoteGreaterThanEqual(@Param("note") Double note);

    /**
     * Find supplier by contact email.
     */
    Optional<Fournisseur> findByContact(String contact);
}
