package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.dto.FournisseurDTO;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FournisseurServiceTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private HistoriqueAchatsRepository historiqueAchatsRepository;

    @InjectMocks
    private FournisseurService fournisseurService;

    @Test
    void createFournisseur_throwsWhenNameExists() {
        FournisseurDTO dto = FournisseurDTO.builder()
                .nom("ACME")
                .contact("contact@acme.tn")
                .qualite_service(4)
                .build();

        when(fournisseurRepository.findByNomIgnoreCase("ACME")).thenReturn(Optional.of(new tn.itbs.note.entity.Fournisseur()));

        assertThrows(BusinessException.class, () -> fournisseurService.createFournisseur(dto));
    }
}
