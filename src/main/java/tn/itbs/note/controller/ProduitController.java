package tn.itbs.note.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.itbs.note.dto.ApiResponse;
import tn.itbs.note.dto.ProduitDTO;
import tn.itbs.note.service.ProduitService;

import java.util.List;


@RestController
@RequestMapping("/produits")
@Tag(name = "Produits", description = "Endpoints for managing products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> getAllProduits() {
        List<ProduitDTO> produits = produitService.getAllProduits();
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", produits));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by their ID")
    public ResponseEntity<ApiResponse<ProduitDTO>> getProduitById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id) {
        ProduitDTO produit = produitService.getProduitById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", produit));
    }

    @GetMapping("/search/{nom}")
    @Operation(summary = "Search products by name", description = "Search for products by name pattern")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> searchProduitsByName(
            @Parameter(description = "Product name pattern", required = true)
            @PathVariable String nom) {
        List<ProduitDTO> produits = produitService.searchProduitsByName(nom);
        return ResponseEntity.ok(ApiResponse.success("Products found", produits));
    }

    @PostMapping
    @Operation(summary = "Create new product", description = "Create a new product in the system")
    public ResponseEntity<ApiResponse<ProduitDTO>> createProduit(
            @Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO createdProduit = produitService.createProduit(produitDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", createdProduit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    public ResponseEntity<ApiResponse<ProduitDTO>> updateProduit(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO updatedProduit = produitService.updateProduit(id, produitDTO);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduit));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a specific product")
    public ResponseEntity<ApiResponse<String>> deleteProduit(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", "Product with ID " + id + " deleted"));
    }
}
