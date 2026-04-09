package tn.itbs.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.itbs.note.dto.ProduitDTO;
import tn.itbs.note.entity.Produit;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.ProduitRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing products.
 */
@Service
@Transactional
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Get all products.
     */
    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get product by ID.
     */
    public ProduitDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));
        return convertToDTO(produit);
    }

    /**
     * Find products by name pattern.
     */
    public List<ProduitDTO> searchProduitsByName(String nom) {
        return produitRepository.findByNomContaining(nom).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new product.
     */
    public ProduitDTO createProduit(ProduitDTO produitDTO) {
        // Check if product with same name already exists
        if (produitRepository.findByNomIgnoreCase(produitDTO.getNom()).isPresent()) {
            throw new BusinessException("Un produit avec le nom '" + produitDTO.getNom() + "' existe déjà");
        }

        Produit produit = convertToEntity(produitDTO);
        Produit savedProduit = produitRepository.save(produit);
        return convertToDTO(savedProduit);
    }

    /**
     * Update product.
     */
    public ProduitDTO updateProduit(Long id, ProduitDTO produitDTO) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        // Check if another product with same name exists
        if (!produit.getNom().equalsIgnoreCase(produitDTO.getNom())) {
            if (produitRepository.findByNomIgnoreCase(produitDTO.getNom()).isPresent()) {
                throw new BusinessException("Un produit avec le nom '" + produitDTO.getNom() + "' existe déjà");
            }
        }

        produit.setNom(produitDTO.getNom());
        produit.setDescription(produitDTO.getDescription());
        produit.setPrix_reference(produitDTO.getPrix_reference());

        Produit updatedProduit = produitRepository.save(produit);
        return convertToDTO(updatedProduit);
    }

    /**
     * Delete product.
     */
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));
        produitRepository.delete(produit);
    }

    /**
     * Convert entity to DTO.
     */
    private ProduitDTO convertToDTO(Produit produit) {
        return ProduitDTO.builder()
                .id(produit.getId())
                .nom(produit.getNom())
                .description(produit.getDescription())
                .prix_reference(produit.getPrix_reference())
                .createdAt(produit.getCreatedAt())
                .updatedAt(produit.getUpdatedAt())
                .build();
    }

    /**
     * Convert DTO to entity.
     */
    private Produit convertToEntity(ProduitDTO produitDTO) {
        return Produit.builder()
                .nom(produitDTO.getNom())
                .description(produitDTO.getDescription())
                .prix_reference(produitDTO.getPrix_reference())
                .build();
    }
}
