package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.dto.HistoriqueAchatsDTO;
import tn.itbs.note.entity.Fournisseur;
import tn.itbs.note.entity.HistoriqueAchats;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class HistoriqueAchatsServiceTest {

    @Mock
    private HistoriqueAchatsRepository historiqueRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @InjectMocks
    private HistoriqueAchatsService historiqueService;

    @Test
    void createHistorique_persistsAndReturnsDto() {
        Fournisseur fournisseur = Fournisseur.builder()
                .id(10L)
                .nom("ACME")
                .contact("contact@acme.tn")
                .qualite_service(4)
                .note(80.0)
                .build();

        HistoriqueAchatsDTO dto = HistoriqueAchatsDTO.builder()
                .fournisseurId(10L)
                .produit("Papier")
                .quantite(5)
                .delai_livraison(3)
                .dateAchat(LocalDateTime.now())
                .build();

        when(fournisseurRepository.findById(10L)).thenReturn(Optional.of(fournisseur));
        when(historiqueRepository.save(any(HistoriqueAchats.class))).thenAnswer(invocation -> {
            HistoriqueAchats historique = invocation.getArgument(0);
            historique.setId(99L);
            return historique;
        });

        HistoriqueAchatsDTO result = historiqueService.createHistorique(dto);

        assertEquals(99L, result.getId());
        assertEquals(10L, result.getFournisseurId());
        assertEquals("Papier", result.getProduit());
    }
}
