import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CommandeAchat, Evaluation, Fournisseur, HistoriqueAchat, Produit } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getFournisseurs() {
    return this.http.get<any>(`${this.baseUrl}/fournisseurs`);
  }

  createFournisseur(payload: Fournisseur) {
    return this.http.post<any>(`${this.baseUrl}/fournisseurs`, payload);
  }

  updateFournisseur(id: number, payload: Fournisseur) {
    return this.http.put<any>(`${this.baseUrl}/fournisseurs/${id}`, payload);
  }

  deleteFournisseur(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/fournisseurs/${id}`);
  }

  getProduits() {
    return this.http.get<any>(`${this.baseUrl}/produits`);
  }

  createProduit(payload: Produit) {
    return this.http.post<any>(`${this.baseUrl}/produits`, payload);
  }

  updateProduit(id: number, payload: Produit) {
    return this.http.put<any>(`${this.baseUrl}/produits/${id}`, payload);
  }

  deleteProduit(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/produits/${id}`);
  }

  getCommandes() {
    return this.http.get<any>(`${this.baseUrl}/commandes`);
  }

  createCommande(payload: CommandeAchat) {
    return this.http.post<any>(`${this.baseUrl}/commandes`, payload);
  }

  getHistoriques() {
    return this.http.get<any>(`${this.baseUrl}/historiques-achats`);
  }

  createHistorique(payload: HistoriqueAchat) {
    return this.http.post<any>(`${this.baseUrl}/historiques-achats`, payload);
  }

  getEvaluation(fournisseurId: number) {
    return this.http.get<any>(`${this.baseUrl}/evaluations/supplier/${fournisseurId}`);
  }

  getAllEvaluations() {
    return this.http.get<any>(`${this.baseUrl}/evaluations/all`);
  }
}
