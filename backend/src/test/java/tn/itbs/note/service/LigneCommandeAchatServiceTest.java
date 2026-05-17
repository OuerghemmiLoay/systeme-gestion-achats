package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.dto.LigneCommandeAchatDTO;
import tn.itbs.note.entity.CommandeAchat;
import tn.itbs.note.entity.Produit;
import tn.itbs.note.exception.BusinessException;
import tn.itbs.note.repository.CommandeAchatRepository;
import tn.itbs.note.repository.LigneCommandeAchatRepository;
import tn.itbs.note.repository.ProduitRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LigneCommandeAchatServiceTest {

    @Mock
    private LigneCommandeAchatRepository ligneRepository;

    @Mock
    private CommandeAchatRepository commandeRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private LigneCommandeAchatService ligneCommandeAchatService;

    @Test
    void createLigne_throwsWhenCommandeNotPending() {
        CommandeAchat commande = CommandeAchat.builder()
                .id(5L)
                .statut(CommandeAchat.StatutCommande.SHIPPED)
                .build();

        Produit produit = Produit.builder()
                .id(7L)
                .nom("Papier")
                .build();

        when(commandeRepository.findById(5L)).thenReturn(Optional.of(commande));
        when(produitRepository.findById(7L)).thenReturn(Optional.of(produit));

        LigneCommandeAchatDTO dto = LigneCommandeAchatDTO.builder()
                .commandeId(5L)
                .produitId(7L)
                .quantite(2)
                .prix_unitaire(BigDecimal.valueOf(10.0))
                .build();

        assertThrows(BusinessException.class, () -> ligneCommandeAchatService.createLigne(dto));
    }
}
