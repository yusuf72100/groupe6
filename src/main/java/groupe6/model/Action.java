package groupe6.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe Action qui représente une action effectuée dans une partie
 *
 * @author Yamis
 */
public class Action implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Cellule cellule1; // cellule 1☼þ
  private final Cellule cellule2; // cellule 2
  private final int coteCellule1; // cote de la cellule 1
  private final ValeurCote ancienneValeurCote; // ancienne valeur u cote
  private final ValeurCote nouvelleValeurCote; // nouvelle valeur du cote

  // Coordonée necessaire pour deep copy une action
  private final Coordonnee coordsCellule1;

  // Constructeur de la classe Action
  public Action(Cellule cellule1, Cellule cellule2, int coteCellule1, ValeurCote nouvelleValeurCote,
                Coordonnee coordsCellule1) {
    if (cellule1.getCote(coteCellule1) != cellule2.getCote(Cellule.getCoteAdjacent(coteCellule1))) {
      throw new IllegalArgumentException("Les cotés des cellules ne sont pas equivalentes");
    }
    this.coordsCellule1 = coordsCellule1;
    this.cellule1 = cellule1;
    this.cellule2 = cellule2;
    this.coteCellule1 = coteCellule1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = cellule1.getCote(coteCellule1);
  }

  public Action(Cellule cellule1, Cellule cellule2, int coteCellule1, ValeurCote nouvelleValeurCote,
                ValeurCote ancienneValeurCote, Coordonnee coordsCellule1) {
    if (cellule1.getCote(coteCellule1) != cellule2.getCote(Cellule.getCoteAdjacent(coteCellule1))) {
      throw new IllegalArgumentException("Les cotés des cellules ne sont pas equivalentes");
    }

    this.coordsCellule1 = coordsCellule1;
    this.cellule1 = cellule1;
    this.cellule2 = cellule2;
    this.coteCellule1 = coteCellule1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = ancienneValeurCote;
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

  public int getCoteCellule1() {
    return  this.coteCellule1;
  }

  public ValeurCote getNouvelleValeurCote() {
    return this.nouvelleValeurCote;
  }

  public ValeurCote getAncienneValeurCote() {
    return this.ancienneValeurCote;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Action action = (Action) obj;
    return this.coordsCellule1.equals(action.getCoordsCellule1()) &&
        this.coteCellule1 == action.getCoteCellule1() &&
        this.nouvelleValeurCote == action.getNouvelleValeurCote() &&
        this.ancienneValeurCote == action.getAncienneValeurCote();
  }

  public Coordonnee getCoordsCellule1() {
    return this.coordsCellule1;
  }

  public Action clone(Puzzle puzzle) {
    int coordsCelluleY = this.coordsCellule1.getY();
    int coordsCelluleX = this.coordsCellule1.getX();
    Coordonnee coordsCelluleAdjacente = puzzle.getCoordoneeAdjacente(coordsCelluleY,coordsCelluleX,this.coteCellule1);

    return new Action(
        puzzle.getCellule(coordsCelluleY,coordsCelluleX),
        puzzle.getCellule(coordsCelluleAdjacente.getY(),coordsCelluleAdjacente.getX()),
        this.coteCellule1,
        this.nouvelleValeurCote,
        this.ancienneValeurCote,
        this.coordsCellule1
    );
  }
}
