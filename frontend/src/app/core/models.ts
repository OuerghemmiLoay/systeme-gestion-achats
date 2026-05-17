export interface Fournisseur {
  id?: number;
  nom: string;
  contact: string;
  qualite_service: number;
  note?: number;
}

export interface Produit {
  id?: number;
  nom: string;
  description?: string;
  prix_reference: number;
}

export interface CommandeAchat {
  id?: number;
  fournisseurId: number;
  fournisseurNom?: string;
  date?: string;
  statut: string;
  montant: number;
  notes?: string;
}

export interface HistoriqueAchat {
  id?: number;
  fournisseurId: number;
  fournisseurNom?: string;
  produit: string;
  quantite: number;
  delai_livraison: number;
  dateAchat?: string;
}

export interface Evaluation {
  fournisseurId: number;
  fournisseurNom: string;
  qualiteService: number;
  averageDeliveryTime: number;
  onTimeDeliveryRate: number;
  averageCost: number;
  overallRating: number;
  recommendation: string;
}
