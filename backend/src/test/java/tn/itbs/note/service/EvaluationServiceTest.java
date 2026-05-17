package tn.itbs.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.itbs.note.entity.CommandeAchat;
import tn.itbs.note.entity.Fournisseur;
import tn.itbs.note.entity.LigneCommandeAchat;
import tn.itbs.note.entity.Produit;
import tn.itbs.note.dto.EvaluationDTO;
import tn.itbs.note.exception.ResourceNotFoundException;
import tn.itbs.note.repository.FournisseurRepository;
import tn.itbs.note.repository.HistoriqueAchatsRepository;
import tn.itbs.note.repository.LigneCommandeAchatRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private HistoriqueAchatsRepository historiqueRepository;

    @Mock
    private LigneCommandeAchatRepository ligneRepository;

    @Mock
    private FournisseurService fournisseurService;

    @InjectMocks
    private EvaluationService evaluationService;

    @Test
    void compareOffers_throwsWhenNoOffers() {
        when(ligneRepository.findByProduitNomContaining("Papier"))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> evaluationService.compareOffers("Papier"));
    }

        @Test
        void evaluateSupplier_usesSupplierLineItemsForAverageCost() {
        Fournisseur fournisseur = Fournisseur.builder()
            .id(10L)
            .nom("ACME")
            .contact("contact@acme.tn")
            .qualite_service(4)
            .note(82.0)
            .build();

        CommandeAchat commande = CommandeAchat.builder()
            .id(1L)
            .fournisseur(fournisseur)
            .statut(CommandeAchat.StatutCommande.DELIVERED)
            .build();

        Produit produit = Produit.builder()
            .id(7L)
            .nom("Papier")
            .build();

        LigneCommandeAchat ligne1 = LigneCommandeAchat.builder()
            .commande(commande)
            .produit(produit)
            .prix_unitaire(BigDecimal.valueOf(10.0))
            .quantite(2)
            .build();

        LigneCommandeAchat ligne2 = LigneCommandeAchat.builder()
            .commande(commande)
            .produit(produit)
            .prix_unitaire(BigDecimal.valueOf(20.0))
            .quantite(1)
            .build();

        when(fournisseurRepository.findById(10L)).thenReturn(Optional.of(fournisseur));
        when(historiqueRepository.findByFournisseurId(10L)).thenReturn(Collections.emptyList());
        when(historiqueRepository.findAverageDeliveryTimeByFournisseurId(10L)).thenReturn(null);
        when(ligneRepository.findByFournisseurId(10L)).thenReturn(List.of(ligne1, ligne2));

        EvaluationDTO evaluation = evaluationService.evaluateSupplier(10L);

        assertEquals(new BigDecimal("15.00"), evaluation.getAverageCost());
        assertEquals(82.0, evaluation.getOverallRating());
        }
}
