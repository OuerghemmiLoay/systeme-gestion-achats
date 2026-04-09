package tn.itbs.note.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for offer comparison between suppliers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffreProduitDTO {

    private String produitNom;

    private List<OffreDetailDTO> offres;

    private OffreDetailDTO bestOffer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OffreDetailDTO {

        private Long fournisseurId;

        private String fournisseurNom;

        private BigDecimal prix;

        private Double qualiteService;

        private Double note;

        private Integer delaiLivraison;

        private Integer score;
    }
}
