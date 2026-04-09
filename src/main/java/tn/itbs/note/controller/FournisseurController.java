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
import tn.itbs.note.dto.FournisseurDTO;
import tn.itbs.note.service.FournisseurService;

import java.util.List;

/**
 * REST Controller for managing suppliers (Fournisseurs).
 */
@RestController
@RequestMapping("/fournisseurs")
@Tag(name = "Fournisseurs", description = "Endpoints for managing suppliers")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Retrieve a list of all suppliers")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> getAllFournisseurs() {
        List<FournisseurDTO> fournisseurs = fournisseurService.getAllFournisseurs();
        return ResponseEntity.ok(ApiResponse.success("Fournisseurs retrieved successfully", fournisseurs));
    }

    @GetMapping("/ranked")
    @Operation(summary = "Get suppliers by rating", description = "Retrieve suppliers ranked by their overall rating")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> getFournisseursRankedByRating() {
        List<FournisseurDTO> fournisseurs = fournisseurService.getFournisseursRankedByRating();
        return ResponseEntity.ok(ApiResponse.success("Suppliers ranked successfully", fournisseurs));
    }

    @GetMapping("/high-quality")
    @Operation(summary = "Get high-quality suppliers", description = "Retrieve suppliers with quality service >= 4")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> getHighQualityFournisseurs() {
        List<FournisseurDTO> fournisseurs = fournisseurService.getHighQualityFournisseurs();
        return ResponseEntity.ok(ApiResponse.success("High-quality suppliers retrieved", fournisseurs));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", description = "Retrieve a specific supplier by their ID")
    public ResponseEntity<ApiResponse<FournisseurDTO>> getFournisseurById(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long id) {
        FournisseurDTO fournisseur = fournisseurService.getFournisseurById(id);
        return ResponseEntity.ok(ApiResponse.success("Supplier retrieved successfully", fournisseur));
    }

    @GetMapping("/search/{nom}")
    @Operation(summary = "Get supplier by name", description = "Retrieve a specific supplier by their name")
    public ResponseEntity<ApiResponse<FournisseurDTO>> getFournisseurByNom(
            @Parameter(description = "Supplier name", required = true)
            @PathVariable String nom) {
        FournisseurDTO fournisseur = fournisseurService.getFournisseurByNom(nom);
        return ResponseEntity.ok(ApiResponse.success("Supplier retrieved successfully", fournisseur));
    }

    @PostMapping
    @Operation(summary = "Create new supplier", description = "Create a new supplier in the system")
    public ResponseEntity<ApiResponse<FournisseurDTO>> createFournisseur(
            @Valid @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO createdFournisseur = fournisseurService.createFournisseur(fournisseurDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Supplier created successfully", createdFournisseur));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update supplier", description = "Update an existing supplier")
    public ResponseEntity<ApiResponse<FournisseurDTO>> updateFournisseur(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO updatedFournisseur = fournisseurService.updateFournisseur(id, fournisseurDTO);
        return ResponseEntity.ok(ApiResponse.success("Supplier updated successfully", updatedFournisseur));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete supplier", description = "Delete a specific supplier")
    public ResponseEntity<ApiResponse<String>> deleteFournisseur(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok(ApiResponse.success("Supplier deleted successfully", "Supplier with ID " + id + " deleted"));
    }
}
