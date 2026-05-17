package tn.itbs.note.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.itbs.note.dto.ApiResponse;
import tn.itbs.note.dto.HistoriqueAchatsDTO;
import tn.itbs.note.service.HistoriqueAchatsService;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/historiques-achats")
@Tag(name = "Historique Achats", description = "Endpoints for managing purchase history")
public class HistoriqueAchatsController {

    @Autowired
    private HistoriqueAchatsService historiqueService;

    @GetMapping
    @Operation(summary = "Get all purchase history", description = "Retrieve all purchase history records")
    public ResponseEntity<ApiResponse<List<HistoriqueAchatsDTO>>> getAllHistoriques() {
        List<HistoriqueAchatsDTO> historiques = historiqueService.getAllHistoriques();
        return ResponseEntity.ok(ApiResponse.success("Historique achats retrieved successfully", historiques));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get purchase history by ID", description = "Retrieve a specific purchase history record by ID")
    public ResponseEntity<ApiResponse<HistoriqueAchatsDTO>> getHistoriqueById(
            @Parameter(description = "History ID", required = true)
            @PathVariable Long id) {
        HistoriqueAchatsDTO historique = historiqueService.getHistoriqueById(id);
        return ResponseEntity.ok(ApiResponse.success("Historique achats retrieved successfully", historique));
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    @Operation(summary = "Get history by supplier", description = "Retrieve purchase history for a specific supplier")
    public ResponseEntity<ApiResponse<List<HistoriqueAchatsDTO>>> getHistoriquesByFournisseur(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long fournisseurId) {
        List<HistoriqueAchatsDTO> historiques = historiqueService.getHistoriquesByFournisseur(fournisseurId);
        return ResponseEntity.ok(ApiResponse.success("Historique achats retrieved successfully", historiques));
    }

    @GetMapping("/produit/{produit}")
    @Operation(summary = "Get history by product", description = "Retrieve purchase history by product name")
    public ResponseEntity<ApiResponse<List<HistoriqueAchatsDTO>>> getHistoriquesByProduit(
            @Parameter(description = "Product name", required = true)
            @PathVariable String produit) {
        List<HistoriqueAchatsDTO> historiques = historiqueService.getHistoriquesByProduit(produit);
        return ResponseEntity.ok(ApiResponse.success("Historique achats retrieved successfully", historiques));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get history by date range", description = "Retrieve purchase history within a date range")
    public ResponseEntity<ApiResponse<List<HistoriqueAchatsDTO>>> getHistoriquesByDateRange(
            @Parameter(description = "Start date (ISO 8601)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (ISO 8601)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<HistoriqueAchatsDTO> historiques = historiqueService.getHistoriquesByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Historique achats retrieved successfully", historiques));
    }

    @PostMapping
    @Operation(summary = "Create purchase history", description = "Create a new purchase history record")
    public ResponseEntity<ApiResponse<HistoriqueAchatsDTO>> createHistorique(
            @Valid @RequestBody HistoriqueAchatsDTO dto) {
        HistoriqueAchatsDTO created = historiqueService.createHistorique(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Historique achats created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update purchase history", description = "Update an existing purchase history record")
    public ResponseEntity<ApiResponse<HistoriqueAchatsDTO>> updateHistorique(
            @Parameter(description = "History ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody HistoriqueAchatsDTO dto) {
        HistoriqueAchatsDTO updated = historiqueService.updateHistorique(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Historique achats updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete purchase history", description = "Delete a specific purchase history record")
    public ResponseEntity<ApiResponse<String>> deleteHistorique(
            @Parameter(description = "History ID", required = true)
            @PathVariable Long id) {
        historiqueService.deleteHistorique(id);
        return ResponseEntity.ok(ApiResponse.success("Historique achats deleted successfully", "Historique with ID " + id + " deleted"));
    }
}
