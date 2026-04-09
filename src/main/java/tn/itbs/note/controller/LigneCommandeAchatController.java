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
import tn.itbs.note.dto.LigneCommandeAchatDTO;
import tn.itbs.note.service.LigneCommandeAchatService;

import java.util.List;

/**
 * REST Controller for managing purchase order line items (Lignes Commande Achat).
 */
@RestController
@RequestMapping("/lignes-commande")
@Tag(name = "Lignes Commande", description = "Endpoints for managing purchase order line items")
public class LigneCommandeAchatController {

    @Autowired
    private LigneCommandeAchatService ligneService;

    @GetMapping("/commande/{commandeId}")
    @Operation(summary = "Get line items for order", description = "Retrieve all line items for a specific purchase order")
    public ResponseEntity<ApiResponse<List<LigneCommandeAchatDTO>>> getLignesForCommande(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long commandeId) {
        List<LigneCommandeAchatDTO> lignes = ligneService.getLignesForCommande(commandeId);
        return ResponseEntity.ok(ApiResponse.success("Line items retrieved successfully", lignes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get line item by ID", description = "Retrieve a specific line item by their ID")
    public ResponseEntity<ApiResponse<LigneCommandeAchatDTO>> getLigneById(
            @Parameter(description = "Line item ID", required = true)
            @PathVariable Long id) {
        LigneCommandeAchatDTO ligne = ligneService.getLigneById(id);
        return ResponseEntity.ok(ApiResponse.success("Line item retrieved successfully", ligne));
    }

    @PostMapping
    @Operation(summary = "Create new line item", description = "Create a new line item in a purchase order")
    public ResponseEntity<ApiResponse<LigneCommandeAchatDTO>> createLigne(
            @Valid @RequestBody LigneCommandeAchatDTO ligneDTO) {
        LigneCommandeAchatDTO createdLigne = ligneService.createLigne(ligneDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Line item created successfully", createdLigne));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update line item", description = "Update an existing line item (only if order is PENDING)")
    public ResponseEntity<ApiResponse<LigneCommandeAchatDTO>> updateLigne(
            @Parameter(description = "Line item ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody LigneCommandeAchatDTO ligneDTO) {
        LigneCommandeAchatDTO updatedLigne = ligneService.updateLigne(id, ligneDTO);
        return ResponseEntity.ok(ApiResponse.success("Line item updated successfully", updatedLigne));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete line item", description = "Delete a specific line item (only if order is PENDING)")
    public ResponseEntity<ApiResponse<String>> deleteLigne(
            @Parameter(description = "Line item ID", required = true)
            @PathVariable Long id) {
        ligneService.deleteLigne(id);
        return ResponseEntity.ok(ApiResponse.success("Line item deleted successfully", "Line item with ID " + id + " deleted"));
    }
}
