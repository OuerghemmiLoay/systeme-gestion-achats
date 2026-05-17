package tn.itbs.note.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.HistoriqueAchatsDTO;
import tn.itbs.note.service.HistoriqueAchatsService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HistoriqueAchatsController.class)
public class HistoriqueAchatsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private HistoriqueAchatsService historiqueService;

    @Test
    void getAllHistoriques_returnsList() throws Exception {
        HistoriqueAchatsDTO dto = HistoriqueAchatsDTO.builder()
                .id(1L)
                .fournisseurId(10L)
                .fournisseurNom("ACME")
                .produit("Papier")
                .quantite(5)
                .delai_livraison(3)
                .dateAchat(LocalDateTime.now())
                .build();

        when(historiqueService.getAllHistoriques()).thenReturn(List.of(dto));

        mockMvc.perform(get("/historiques-achats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }
}
