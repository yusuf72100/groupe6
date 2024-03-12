package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.Serializable;

/**
 * @author Yamis
 */

// Classe Action qui implémente Serializable
public class Action implements Serializable {

  private static final long serialVersionUID = 1L;

  private CelluleData cellule1; // cellule 1
  private CelluleData cellule2; // cellule 2
  private int coteCellule1; // cote de la cellule 1
  private ValeurCote ancienneValeurCote; // ancienne valeur du cote
  private ValeurCote nouvelleValeurCote; // nouvelle valeur du cote

  // Constructeur de la classe Action
  public Action(CelluleData cellule1, CelluleData cellule2, int coteCellule1, ValeurCote nouvelleValeurCote) {
    if (cellule1.getCote(coteCellule1) != cellule2.getCote(CelluleData.getCoteAdjacent(coteCellule1))) {
      throw new IllegalArgumentException("Les cotés des cellules ne sont pas equivalentes");
    }

    this.cellule1 = cellule1;
    this.cellule2 = cellule2;
    this.coteCellule1 = coteCellule1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = cellule1.getCote(coteCellule1);
  }

  // Méthode pour appliquer une action
  public void appliquerAction() {
    cellule1.setCote(this.coteCellule1, nouvelleValeurCote);
    cellule2.setCote(CelluleData.getCoteAdjacent(this.coteCellule1), nouvelleValeurCote);
  }

  // Méthode pour révoquer une action
  public void revoquerAction() {
    cellule1.setCote(this.coteCellule1, ancienneValeurCote);
    cellule2.setCote(CelluleData.getCoteAdjacent(this.coteCellule1), ancienneValeurCote);
  }

}
