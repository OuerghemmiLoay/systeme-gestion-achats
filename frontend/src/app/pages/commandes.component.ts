import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';
import { CommandeAchat } from '../core/models';

@Component({
  standalone: true,
  selector: 'app-commandes',
  imports: [CommonModule, FormsModule],
  template: `
    <section class="grid grid-2">
      <div class="card section">
        <div class="badge">Commandes</div>
        <h2>Créer une commande</h2>
        <div class="form-grid" style="margin-top:1rem;">
          <div class="field">
            <label>Fournisseur ID</label>
            <input [(ngModel)]="form.fournisseurId" name="fournisseurId" type="number" min="1" />
          </div>
          <div class="field">
            <label>Montant</label>
            <input [(ngModel)]="form.montant" name="montant" type="number" min="0" step="0.01" />
          </div>
          <div class="field">
            <label>Statut</label>
            <select [(ngModel)]="form.statut" name="statut">
              <option value="PENDING">PENDING</option>
              <option value="CONFIRMED">CONFIRMED</option>
              <option value="SHIPPED">SHIPPED</option>
              <option value="DELIVERED">DELIVERED</option>
              <option value="CANCELLED">CANCELLED</option>
            </select>
          </div>
          <div class="field">
            <label>Notes</label>
            <input [(ngModel)]="form.notes" name="notes" placeholder="Optionnel" />
          </div>
        </div>
        <div class="actions" style="margin-top:1rem;">
          <button class="btn btn-primary" (click)="create()">Créer</button>
          <button class="btn btn-secondary" (click)="load()">Rafraîchir</button>
        </div>
        <p class="helper" style="margin-top:0.75rem;">{{ message }}</p>
      </div>

      <div class="card section">
        <div class="badge">Suivi</div>
        <h2>Commandes</h2>
        <table class="table" style="margin-top:1rem;">
          <thead>
            <tr>
              <th>ID</th>
              <th>Fournisseur</th>
              <th>Statut</th>
              <th>Montant</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of commandes">
              <td>{{ item.id }}</td>
              <td>{{ item.fournisseurNom ?? item.fournisseurId }}</td>
              <td>{{ item.statut }}</td>
              <td>{{ item.montant }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  `
})
export class CommandesComponent implements OnInit {
  commandes: CommandeAchat[] = [];
  message = 'Chargement...';
  form: CommandeAchat = {
    fournisseurId: 1,
    statut: 'PENDING',
    montant: 0,
    notes: ''
  };

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getCommandes().subscribe({
      next: (response: any) => {
        this.commandes = response?.data ?? response ?? [];
        this.message = 'Commandes chargées.';
      },
      error: () => {
        this.message = 'Impossible de charger les commandes.';
      }
    });
  }

  create(): void {
    this.api.createCommande(this.form).subscribe({
      next: () => {
        this.message = 'Commande créée avec succès.';
        this.form = { fournisseurId: 1, statut: 'PENDING', montant: 0, notes: '' };
        this.load();
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Erreur lors de la création.';
      }
    });
  }
}
