package tn.itbs.note.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.ProduitDTO;
import tn.itbs.note.service.ProduitService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProduitController.class)
public class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProduitService produitService;

    @Test
    void getAllProduits_returnsList() throws Exception {
        ProduitDTO dto = ProduitDTO.builder()
                .id(1L)
                .nom("Papier")
                .description("Papier A4")
                .prix_reference(BigDecimal.valueOf(15.5))
                .build();

        when(produitService.getAllProduits()).thenReturn(List.of(dto));

        mockMvc.perform(get("/produits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }
}
