package tn.itbs.note.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.LigneCommandeAchatDTO;
import tn.itbs.note.service.LigneCommandeAchatService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LigneCommandeAchatController.class)
public class LigneCommandeAchatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LigneCommandeAchatService ligneCommandeAchatService;

    @Test
    void getLignesForCommande_returnsList() throws Exception {
        LigneCommandeAchatDTO dto = LigneCommandeAchatDTO.builder()
                .id(1L)
                .commandeId(5L)
                .produitId(7L)
                .produitNom("Papier")
                .quantite(3)
                .prix_unitaire(BigDecimal.valueOf(10.0))
                .build();

        when(ligneCommandeAchatService.getLignesForCommande(5L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/lignes-commande/commande/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }
}
