package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.dto.ProduitDTO;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.ProduitRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitService produitService;

    @Test
    void createProduit_throwsWhenNameExists() {
        ProduitDTO dto = ProduitDTO.builder()
                .nom("Papier")
                .build();

        when(produitRepository.findByNomIgnoreCase("Papier"))
                .thenReturn(Optional.of(new tn.itbs.note.entity.Produit()));

        assertThrows(BusinessException.class, () -> produitService.createProduit(dto));
    }
}
