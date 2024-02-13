package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.Serializable;

// Classe Action qui implémente Serializable
public class Action implements Serializable {

  private Cellule_Data Cellule_Data1; // Cellule_Data 1
  private Cellule_Data Cellule_Data2; // Cellule_Data 2
  private int coteCellule_Data1; // cote de la Cellule_Data 1
  private Cellule_Data.ValeurCote ancienneValeurCote; // ancienne valeur du cote
  private Cellule_Data.ValeurCote nouvelleValeurCote; // nouvelle valeur du cote

  // Constructeur de la classe Action
  public Action(Cellule_Data Cellule_Data1, Cellule_Data Cellule_Data2, int coteCellule_Data1, Cellule_Data.ValeurCote nouvelleValeurCote) {
    if (Cellule_Data1.getCote(coteCellule_Data1) != Cellule_Data2.getCote(Cellule_Data.getCoteAdjacent(coteCellule_Data1))) {
      throw new IllegalArgumentException("Les cotés des Cellule_Datas ne sont pas equivalentes");
    }

    this.Cellule_Data1 = Cellule_Data1;
    this.Cellule_Data2 = Cellule_Data2;
    this.coteCellule_Data1 = coteCellule_Data1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = Cellule_Data1.getCote(coteCellule_Data1);
  }

  // Méthode pour appliquer une action
  public void appliquerAction() {
    Cellule_Data1.setCote(this.coteCellule_Data1, nouvelleValeurCote);
    Cellule_Data2.setCote(Cellule_Data.getCoteAdjacent(this.coteCellule_Data1), nouvelleValeurCote);
  }

  // Méthode pour révoquer une action
  public void revoquerAction() {
    Cellule_Data1.setCote(this.coteCellule_Data1, ancienneValeurCote);
    Cellule_Data2.setCote(Cellule_Data.getCoteAdjacent(this.coteCellule_Data1), ancienneValeurCote);
  }

}
