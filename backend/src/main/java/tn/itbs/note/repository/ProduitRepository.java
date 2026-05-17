package tn.itbs.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.itbs.note.entity.Produit;

import java.util.Optional;


@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    /**
     * Find product by name (case-insensitive).
     */
    Optional<Produit> findByNomIgnoreCase(String nom);

    /**
     * Find products by name pattern.
     */
    @Query("SELECT p FROM Produit p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    java.util.List<Produit> findByNomContaining(@Param("nom") String nom);
}
