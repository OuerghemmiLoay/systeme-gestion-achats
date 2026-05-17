import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [RouterLink],
  template: `
    <section class="card section" style="padding: 1.4rem;">
      <div class="grid grid-3">
        <div class="card section" style="background: rgba(56, 189, 248, 0.08);">
          <div class="badge">Suppliers</div>
          <h2>Manage vendors</h2>
          <p class="small">Create and review supplier quality, contact details, and ratings.</p>
          <a class="btn btn-primary" routerLink="/fournisseurs">Open</a>
        </div>
        <div class="card section" style="background: rgba(52, 211, 153, 0.08);">
          <div class="badge">Orders</div>
          <h2>Track purchases</h2>
          <p class="small">Inspect purchase orders, statuses, and delivery flow.</p>
          <a class="btn btn-primary" routerLink="/commandes">Open</a>
        </div>
        <div class="card section" style="background: rgba(251, 113, 133, 0.08);">
          <div class="badge">Evaluation</div>
          <h2>Compare performance</h2>
          <p class="small">See supplier scoring, delivery times, and recommendations.</p>
          <a class="btn btn-primary" routerLink="/evaluations">Open</a>
        </div>
      </div>
    </section>
  `
})
export class DashboardComponent {}
