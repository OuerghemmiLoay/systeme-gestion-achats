package tn.itbs.note.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.itbs.note.dto.ApiResponse;
import tn.itbs.note.dto.EvaluationDTO;
import tn.itbs.note.dto.OffreProduitDTO;
import tn.itbs.note.service.EvaluationService;

import java.util.List;

/**
 * REST Controller for supplier evaluation and offer comparison.
 */
@RestController
@RequestMapping("/evaluations")
@Tag(name = "Evaluations", description = "Endpoints for supplier evaluation and offer comparison")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/supplier/{fournisseurId}")
    @Operation(summary = "Evaluate supplier", description = "Get detailed evaluation metrics for a specific supplier")
    public ResponseEntity<ApiResponse<EvaluationDTO>> evaluateSupplier(
            @Parameter(description = "Supplier ID", required = true)
            @PathVariable Long fournisseurId) {
        EvaluationDTO evaluation = evaluationService.evaluateSupplier(fournisseurId);
        return ResponseEntity.ok(ApiResponse.success("Supplier evaluation retrieved successfully", evaluation));
    }

    @GetMapping("/all")
    @Operation(summary = "Evaluate all suppliers", description = "Get evaluation metrics for all suppliers ranked by overall rating")
    public ResponseEntity<ApiResponse<List<EvaluationDTO>>> evaluateAllSuppliers() {
        List<EvaluationDTO> evaluations = evaluationService.evaluateAllSuppliers();
        return ResponseEntity.ok(ApiResponse.success("All suppliers evaluated successfully", evaluations));
    }

    @GetMapping("/compare-offers/{produitNom}")
    @Operation(summary = "Compare offers", description = "Compare offers from multiple suppliers for the same product")
    public ResponseEntity<ApiResponse<OffreProduitDTO>> compareOffers(
            @Parameter(description = "Product name", required = true)
            @PathVariable String produitNom) {
        OffreProduitDTO comparison = evaluationService.compareOffers(produitNom);
        return ResponseEntity.ok(ApiResponse.success("Offers compared successfully", comparison));
    }
}
