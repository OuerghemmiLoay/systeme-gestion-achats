import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';
import { Produit } from '../core/models';

@Component({
  standalone: true,
  selector: 'app-produits',
  imports: [CommonModule, FormsModule],
  template: `
    <section class="grid grid-2">
      <div class="card section">
        <div class="badge">Produits</div>
        <h2>Ajouter un produit</h2>
        <div class="form-grid" style="margin-top:1rem;">
          <div class="field">
            <label>Nom</label>
            <input [(ngModel)]="form.nom" name="nom" placeholder="Ex: Papier A4" />
          </div>
          <div class="field">
            <label>Prix de référence</label>
            <input [(ngModel)]="form.prix_reference" name="prix_reference" type="number" min="0" step="0.01" />
          </div>
          <div class="field" style="grid-column: 1 / -1;">
            <label>Description</label>
            <textarea [(ngModel)]="form.description" name="description" placeholder="Description courte"></textarea>
          </div>
        </div>
        <div class="actions" style="margin-top:1rem;">
          <button *ngIf="!editing" class="btn btn-primary" (click)="create()">Créer</button>
          <button *ngIf="editing" class="btn btn-primary" (click)="update()">Mettre à jour</button>
          <button *ngIf="editing" class="btn btn-secondary" (click)="cancelEdit()">Annuler</button>
          <button *ngIf="!editing" class="btn btn-secondary" (click)="load()">Rafraîchir</button>
        </div>
        <p class="helper" style="margin-top:0.75rem;">{{ message }}</p>
      </div>

      <div class="card section">
        <div class="badge">Catalogue</div>
        <h2>Produits</h2>
        <table class="table" style="margin-top:1rem;">
          <thead>
            <tr>
              <th>Nom</th>
              <th>Description</th>
              <th>Prix</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of produits">
              <td>{{ item.nom }}</td>
              <td>{{ item.description }}</td>
              <td>{{ item.prix_reference }}</td>
              <td>
                <button class="btn btn-link" (click)="edit(item)">Editer</button>
                <button class="btn btn-link danger" (click)="delete(item.id)">Supprimer</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  `
})
export class ProduitsComponent implements OnInit {
  produits: Produit[] = [];
  message = 'Chargement...';
  form: Produit = {
    nom: '',
    description: '',
    prix_reference: 0
  };
  editing = false;
  editingId?: number;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getProduits().subscribe({
      next: (response: any) => {
        this.produits = response?.data ?? response ?? [];
        this.message = 'Produits chargés.';
      },
      error: () => {
        this.message = 'Impossible de charger les produits.';
      }
    });
  }

  create(): void {
    this.api.createProduit(this.form).subscribe({
      next: () => {
        this.message = 'Produit créé avec succès.';
        this.form = { nom: '', description: '', prix_reference: 0 };
        this.load();
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Erreur lors de la création.';
      }
    });
  }

  edit(item: Produit): void {
    this.editing = true;
    this.editingId = item.id;
    this.form = { ...item } as Produit;
    this.message = 'Mode édition';
  }

  update(): void {
    if (!this.editingId) {
      this.message = 'Aucun produit sélectionné.';
      return;
    }
    this.api.updateProduit(this.editingId, this.form).subscribe({
      next: () => {
        this.message = 'Produit mis à jour.';
        this.editing = false;
        this.editingId = undefined;
        this.form = { nom: '', description: '', prix_reference: 0 };
        this.load();
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Erreur lors de la mise à jour.';
      }
    });
  }

  cancelEdit(): void {
    this.editing = false;
    this.editingId = undefined;
    this.form = { nom: '', description: '', prix_reference: 0 };
    this.message = 'Annulé.';
  }

  delete(id?: number): void {
    if (!id) return;
    if (!confirm('Supprimer ce produit ?')) return;
    this.api.deleteProduit(id).subscribe({
      next: () => {
        this.message = 'Produit supprimé.';
        this.load();
      },
      error: () => {
        this.message = 'Erreur lors de la suppression.';
      }
    });
  }
}
