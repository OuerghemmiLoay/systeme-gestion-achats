import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';
import { Fournisseur } from '../core/models';

@Component({
  standalone: true,
  selector: 'app-fournisseurs',
  imports: [CommonModule, FormsModule],
  template: `
    <section class="grid grid-2">
      <div class="card section">
        <div class="badge">Fournisseurs</div>
        <h2>Ajouter un fournisseur</h2>
        <div class="form-grid" style="margin-top:1rem;">
          <div class="field">
            <label>Nom</label>
            <input [(ngModel)]="form.nom" name="nom" placeholder="Ex: ACME" />
          </div>
          <div class="field">
            <label>Contact</label>
            <input [(ngModel)]="form.contact" name="contact" placeholder="contact@acme.tn" />
          </div>
          <div class="field">
            <label>Qualité service</label>
            <input [(ngModel)]="form.qualite_service" name="qualite_service" type="number" min="1" max="5" />
          </div>
          <div class="field">
            <label>Note</label>
            <input [(ngModel)]="form.note" name="note" type="number" min="0" max="100" />
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
        <div class="badge">Liste</div>
        <h2>Fournisseurs enregistrés</h2>
        <table class="table" style="margin-top:1rem;">
          <thead>
            <tr>
              <th>Nom</th>
              <th>Contact</th>
              <th>Qualité</th>
              <th>Note</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of fournisseurs">
              <td>{{ item.nom }}</td>
              <td>{{ item.contact }}</td>
              <td>{{ item.qualite_service }}</td>
              <td>{{ item.note ?? 0 }}</td>
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
export class FournisseursComponent implements OnInit {
  fournisseurs: Fournisseur[] = [];
  message = 'Chargement...';
  form: Fournisseur = {
    nom: '',
    contact: '',
    qualite_service: 3,
    note: 0
  };
  editing = false;
  editingId?: number;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getFournisseurs().subscribe({
      next: (response: any) => {
        this.fournisseurs = response?.data ?? response ?? [];
        this.message = 'Fournisseurs chargés.';
      },
      error: () => {
        this.message = 'Impossible de charger les fournisseurs.';
      }
    });
  }

  create(): void {
    this.api.createFournisseur(this.form).subscribe({
      next: () => {
        this.message = 'Fournisseur créé avec succès.';
        this.form = { nom: '', contact: '', qualite_service: 3, note: 0 };
        this.load();
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Erreur lors de la création.';
      }
    });
  }

  edit(item: Fournisseur): void {
    this.editing = true;
    this.editingId = item.id;
    this.form = { ...item } as Fournisseur;
    this.message = 'Mode édition';
  }

  update(): void {
    if (!this.editingId) {
      this.message = 'Aucun fournisseur sélectionné.';
      return;
    }
    this.api.updateFournisseur(this.editingId, this.form).subscribe({
      next: () => {
        this.message = 'Fournisseur mis à jour.';
        this.editing = false;
        this.editingId = undefined;
        this.form = { nom: '', contact: '', qualite_service: 3, note: 0 };
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
    this.form = { nom: '', contact: '', qualite_service: 3, note: 0 };
    this.message = 'Annulé.';
  }

  delete(id?: number): void {
    if (!id) return;
    if (!confirm('Supprimer ce fournisseur ?')) return;
    this.api.deleteFournisseur(id).subscribe({
      next: () => {
        this.message = 'Fournisseur supprimé.';
        this.load();
      },
      error: () => {
        this.message = 'Erreur lors de la suppression.';
      }
    });
  }
}
