import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';
import { HistoriqueAchat } from '../core/models';

@Component({
  standalone: true,
  selector: 'app-historiques',
  imports: [CommonModule, FormsModule],
  template: `
    <section class="grid grid-2">
      <div class="card section">
        <div class="badge">Historique</div>
        <h2>Ajouter un achat enregistré</h2>
        <div class="form-grid" style="margin-top:1rem;">
          <div class="field">
            <label>Fournisseur ID</label>
            <input [(ngModel)]="form.fournisseurId" name="fournisseurId" type="number" min="1" />
          </div>
          <div class="field">
            <label>Produit</label>
            <input [(ngModel)]="form.produit" name="produit" />
          </div>
          <div class="field">
            <label>Quantité</label>
            <input [(ngModel)]="form.quantite" name="quantite" type="number" min="1" />
          </div>
          <div class="field">
            <label>Délai livraison</label>
            <input [(ngModel)]="form.delai_livraison" name="delai_livraison" type="number" min="0" />
          </div>
        </div>
        <div class="actions" style="margin-top:1rem;">
          <button class="btn btn-primary" (click)="create()">Créer</button>
          <button class="btn btn-secondary" (click)="load()">Rafraîchir</button>
        </div>
        <p class="helper" style="margin-top:0.75rem;">{{ message }}</p>
      </div>

      <div class="card section">
        <div class="badge">Données</div>
        <h2>Historique des achats</h2>
        <table class="table" style="margin-top:1rem;">
          <thead>
            <tr>
              <th>Fournisseur</th>
              <th>Produit</th>
              <th>Quantité</th>
              <th>Délai</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of historiques">
              <td>{{ item.fournisseurNom ?? item.fournisseurId }}</td>
              <td>{{ item.produit }}</td>
              <td>{{ item.quantite }}</td>
              <td>{{ item.delai_livraison }} j</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  `
})
export class HistoriquesComponent implements OnInit {
  historiques: HistoriqueAchat[] = [];
  message = 'Chargement...';
  form: HistoriqueAchat = {
    fournisseurId: 1,
    produit: '',
    quantite: 1,
    delai_livraison: 0
  };

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getHistoriques().subscribe({
      next: (response: any) => {
        this.historiques = response?.data ?? response ?? [];
        this.message = 'Historique chargé.';
      },
      error: () => {
        this.message = 'Impossible de charger l’historique.';
      }
    });
  }

  create(): void {
    this.api.createHistorique(this.form).subscribe({
      next: () => {
        this.message = 'Historique ajouté avec succès.';
        this.form = { fournisseurId: 1, produit: '', quantite: 1, delai_livraison: 0 };
        this.load();
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Erreur lors de la création.';
      }
    });
  }
}
