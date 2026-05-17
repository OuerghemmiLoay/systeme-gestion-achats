package tn.itbs.note.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.EvaluationDTO;
import tn.itbs.note.service.EvaluationService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EvaluationController.class)
public class EvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EvaluationService evaluationService;

    @Test
    void evaluateSupplier_returnsEvaluation() throws Exception {
        EvaluationDTO dto = EvaluationDTO.builder()
                .fournisseurId(10L)
                .fournisseurNom("ACME")
                .qualiteService(4)
                .averageDeliveryTime(3.0)
                .onTimeDeliveryRate(90.0)
                .averageCost(BigDecimal.valueOf(12.5))
                .overallRating(80.0)
                .recommendation("Good")
                .build();

        when(evaluationService.evaluateSupplier(10L)).thenReturn(dto);

        mockMvc.perform(get("/evaluations/supplier/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.fournisseurId").value(10));
    }
}
