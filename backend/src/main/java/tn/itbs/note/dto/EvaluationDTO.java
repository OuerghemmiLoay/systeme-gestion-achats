package tn.itbs.note.dto;

import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationDTO {

    private Long fournisseurId;

    private String fournisseurNom;

    private Integer qualiteService;

    private Double averageDeliveryTime;

    private Double onTimeDeliveryRate;

    private BigDecimal averageCost;

    private Double overallRating;

    private String recommendation;
}
