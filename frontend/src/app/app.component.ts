import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html'
})
export class AppComponent {
  readonly navItems = [
    { label: 'Dashboard', path: '/' },
    { label: 'Fournisseurs', path: '/fournisseurs' },
    { label: 'Produits', path: '/produits' },
    { label: 'Commandes', path: '/commandes' },
    { label: 'Historique', path: '/historiques' },
    { label: 'Evaluations', path: '/evaluations' }
  ];
}
