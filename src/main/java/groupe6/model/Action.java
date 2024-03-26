package groupe6.model;

import java.io.Serializable;

/**
 * @author Yamis
 */

// Classe Action qui implémente Serializable
public class Action implements Serializable {

  private static final long serialVersionUID = 1L;

  private Cellule cellule1; // cellule 1
  private Cellule cellule2; // cellule 2
  private int coteCellule1; // cote de la cellule 1
  private ValeurCote ancienneValeurCote; // ancienne valeur u cote
  private ValeurCote nouvelleValeurCote; // nouvelle valeur du cote

  // Constructeur de la classe Action
  public Action(Cellule cellule1, Cellule cellule2, int coteCellule1, ValeurCote nouvelleValeurCote) {
    if (cellule1.getCote(coteCellule1) != cellule2.getCote(Cellule.getCoteAdjacent(coteCellule1))) {
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
    cellule2.setCote(Cellule.getCoteAdjacent(this.coteCellule1), nouvelleValeurCote);
  }

  // Méthode pour révoquer une action
  public void revoquerAction() {
    cellule1.setCote(this.coteCellule1, ancienneValeurCote);
    cellule2.setCote(Cellule.getCoteAdjacent(this.coteCellule1), ancienneValeurCote);
  }

  @Override
  public Object clone() {
    throw new UnsupportedOperationException("Pas encore implementé !");
  }
}
