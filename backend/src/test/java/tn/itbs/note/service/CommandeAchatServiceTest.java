package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.CommandeAchatRepository;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;
import tn.itbs.note.repository.LigneCommandeAchatRepository;
import tn.itbs.note.repository.ProduitRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class CommandeAchatServiceTest {

    @Mock
    private CommandeAchatRepository commandeAchatRepository;

    @Mock
    private LigneCommandeAchatRepository ligneCommandeAchatRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private HistoriqueAchatsRepository historiqueAchatsRepository;

    @Mock
    private FournisseurService fournisseurService;

    @InjectMocks
    private CommandeAchatService commandeAchatService;

    @Test
    void getCommandesByStatut_throwsOnInvalidStatus() {
        assertThrows(BusinessException.class, () -> commandeAchatService.getCommandesByStatut("invalid"));
    }
}
