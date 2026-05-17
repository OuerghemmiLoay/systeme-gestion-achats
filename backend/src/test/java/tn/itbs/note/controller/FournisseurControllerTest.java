package tn.itbs.note.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.itbs.note.dto.FournisseurDTO;
import tn.itbs.note.service.FournisseurService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FournisseurController.class)
public class FournisseurControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private FournisseurService fournisseurService;

    @Test
    void getAllFournisseurs_returnsList() throws Exception {
        FournisseurDTO dto = FournisseurDTO.builder()
                .id(1L)
                .nom("ACME")
                .contact("contact@acme.tn")
                .qualite_service(4)
                .note(80.0)
                .build();

        when(fournisseurService.getAllFournisseurs()).thenReturn(List.of(dto));

        mockMvc.perform(get("/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }
}
