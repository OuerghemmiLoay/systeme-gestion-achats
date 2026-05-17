package tn.itbs.note.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.CommandeAchatDTO;
import tn.itbs.note.service.CommandeAchatService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommandeAchatController.class)
public class CommandeAchatControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private CommandeAchatService commandeAchatService;

    @Test
    void getAllCommandes_returnsList() throws Exception {
        CommandeAchatDTO dto = CommandeAchatDTO.builder()
                .id(1L)
                .fournisseurId(10L)
                .fournisseurNom("ACME")
                .date(LocalDateTime.now())
                .statut("PENDING")
                .montant(BigDecimal.valueOf(100.0))
                .build();

        when(commandeAchatService.getAllCommandes()).thenReturn(List.of(dto));

        mockMvc.perform(get("/commandes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }
}
