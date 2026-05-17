import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard.component';
import { FournisseursComponent } from './pages/fournisseurs.component';
import { ProduitsComponent } from './pages/produits.component';
import { CommandesComponent } from './pages/commandes.component';
import { HistoriquesComponent } from './pages/historiques.component';
import { EvaluationsComponent } from './pages/evaluations.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'fournisseurs', component: FournisseursComponent },
  { path: 'produits', component: ProduitsComponent },
  { path: 'commandes', component: CommandesComponent },
  { path: 'historiques', component: HistoriquesComponent },
  { path: 'evaluations', component: EvaluationsComponent },
  { path: '**', redirectTo: '' }
];
