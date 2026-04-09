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
import tn.itbs.note.dto.CommandeAchatDTO;
import tn.itbs.note.service.CommandeAchatService;

import java.util.List;

/**
 * REST Controller for managing purchase orders (Commandes Achat).
 */
@RestController
@RequestMapping("/commandes")
@Tag(name = "Commandes Achat", description = "Endpoints for managing purchase orders")
public class CommandeAchatController {

    @Autowired
    private CommandeAchatService commandeAchatService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all purchase orders")
    public ResponseEntity<ApiResponse<List<CommandeAchatDTO>>> getAllCommandes() {
        List<CommandeAchatDTO> commandes = commandeAchatService.getAllCommandes();
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", commandes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by their ID")
    public ResponseEntity<ApiResponse<CommandeAchatDTO>> getCommandeById(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        CommandeAchatDTO commande = commandeAchatService.getCommandeById(id);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", commande));
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    @Operation(summary = "Get orders by supplier", description = "Retrieve all orders for a specific supplier")
    public ResponseEntity<ApiResponse<List<CommandeAchatDTO>>> getCommandesByFournisseur(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long fournisseurId) {
        List<CommandeAchatDTO> commandes = commandeAchatService.getCommandesByFournisseur(fournisseurId);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", commandes));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Get orders by status", description = "Retrieve all orders with a specific status")
    public ResponseEntity<ApiResponse<List<CommandeAchatDTO>>> getCommandesByStatut(
            @Parameter(description = "Order status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)", required = true)
            @PathVariable String statut) {
        List<CommandeAchatDTO> commandes = commandeAchatService.getCommandesByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", commandes));
    }

    @PostMapping
    @Operation(summary = "Create new order", description = "Create a new purchase order")
    public ResponseEntity<ApiResponse<CommandeAchatDTO>> createCommande(
            @Valid @RequestBody CommandeAchatDTO commandeDTO) {
        CommandeAchatDTO createdCommande = commandeAchatService.createCommande(commandeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order created successfully", createdCommande));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Update an existing purchase order")
    public ResponseEntity<ApiResponse<CommandeAchatDTO>> updateCommande(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CommandeAchatDTO commandeDTO) {
        CommandeAchatDTO updatedCommande = commandeAchatService.updateCommande(id, commandeDTO);
        return ResponseEntity.ok(ApiResponse.success("Order updated successfully", updatedCommande));
    }

    @PatchMapping("/{id}/status/{newStatus}")
    @Operation(summary = "Update order status", description = "Update the status of a purchase order with delivery tracking")
    public ResponseEntity<ApiResponse<CommandeAchatDTO>> updateCommandeStatus(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "New status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)", required = true)
            @PathVariable String newStatus) {
        CommandeAchatDTO updatedCommande = commandeAchatService.updateCommandeStatus(id, newStatus);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", updatedCommande));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order", description = "Delete a specific purchase order (only if PENDING)")
    public ResponseEntity<ApiResponse<String>> deleteCommande(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        commandeAchatService.deleteCommande(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully", "Order with ID " + id + " deleted"));
    }
}
