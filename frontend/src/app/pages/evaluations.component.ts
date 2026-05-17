import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';
import { Evaluation } from '../core/models';

@Component({
  standalone: true,
  selector: 'app-evaluations',
  imports: [CommonModule, FormsModule],
  template: `
    <section class="grid grid-2">
      <div class="card section">
        <div class="badge">Evaluation</div>
        <h2>Consulter un fournisseur</h2>
        <div class="field" style="margin-top:1rem;">
          <label>Fournisseur ID</label>
          <input [(ngModel)]="fournisseurId" name="fournisseurId" type="number" min="1" />
        </div>
        <div class="actions" style="margin-top:1rem;">
          <button class="btn btn-primary" (click)="load()">Charger</button>
          <button class="btn btn-secondary" (click)="loadAll()">Charger tout</button>
        </div>
        <p class="helper" style="margin-top:0.75rem;">{{ message }}</p>
      </div>

      <div class="card section">
        <div class="badge">Résultat</div>
        <h2>Evaluation</h2>
        <div *ngIf="evaluation; else empty" style="margin-top:1rem;">
          <p><strong>{{ evaluation.fournisseurNom }}</strong></p>
          <p>Qualité: {{ evaluation.qualiteService }}/5</p>
          <p>Délai moyen: {{ evaluation.averageDeliveryTime }} jours</p>
          <p>Taux de livraison à temps: {{ evaluation.onTimeDeliveryRate }}%</p>
          <p>Coût moyen: {{ evaluation.averageCost }}</p>
          <p>Note globale: {{ evaluation.overallRating }}</p>
          <p class="small">{{ evaluation.recommendation }}</p>
        </div>
        <ng-template #empty>
          <p class="small" style="margin-top:1rem;">Aucune évaluation chargée pour le moment.</p>
        </ng-template>
      </div>
    </section>

    <section class="card section" style="margin-top:1rem;">
      <div class="badge">Liste</div>
      <h2>Toutes les évaluations</h2>
      <table class="table" style="margin-top:1rem;">
        <thead>
          <tr>
            <th>Fournisseur</th>
            <th>Note</th>
            <th>Coût moyen</th>
            <th>Reco</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let item of evaluations">
            <td>{{ item.fournisseurNom }}</td>
            <td>{{ item.overallRating }}</td>
            <td>{{ item.averageCost }}</td>
            <td>{{ item.recommendation }}</td>
          </tr>
        </tbody>
      </table>
    </section>
  `
})
export class EvaluationsComponent {
  fournisseurId = 1;
  message = 'Prêt.';
  evaluation: Evaluation | null = null;
  evaluations: Evaluation[] = [];

  constructor(private api: ApiService) {}

  load(): void {
    this.api.getEvaluation(this.fournisseurId).subscribe({
      next: (response: any) => {
        this.evaluation = response?.data ?? response ?? null;
        this.message = 'Evaluation chargée.';
      },
      error: (error) => {
        this.message = error?.error?.message ?? 'Impossible de charger l’évaluation.';
      }
    });
  }

  loadAll(): void {
    this.api.getAllEvaluations().subscribe({
      next: (response: any) => {
        this.evaluations = response?.data ?? response ?? [];
        this.message = 'Toutes les évaluations chargées.';
      },
      error: () => {
        this.message = 'Impossible de charger les évaluations.';
      }
    });
  }
}
