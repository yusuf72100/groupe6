package groupe6.model.partie.action;

import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe Action qui représente une action effectuée dans une partie
 *
 * @author Yamis
 */
public class Action implements Serializable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Première cellule sur laquelle l'action est effectuée
   */
  private final Cellule cellule1;

  /**
   * Deuxième cellule sur laquelle l'action est effectuée
   */
  private final Cellule cellule2;

  /**
   * Côté de la première cellule sur laquelle l'action est effectuée
   */
  private final int coteCellule1;

  /**
   * Ancienne valeur du coté avant l'action
   */
  private final ValeurCote ancienneValeurCote;

  /**
   * Nouvelle valeur du coté après l'action
   */
  private final ValeurCote nouvelleValeurCote;

  /**
   * Coordonnée de la première cellule dans le puzzle
   */
  private final Coordonnee coordsCellule1;

  /**
   * Constructeur de la classe Action
   *
   * @param cellule1 La première cellule sur laquelle l'action est effectuée
   * @param cellule2 La deuxième cellule sur laquelle l'action est effectuée
   * @param coteCellule1 Le côté de la première cellule sur laquelle l'action est effectuée
   * @param nouvelleValeurCote La nouvelle valeur du côté après l'action
   * @param coordsCellule1 Coordonnée de la première cellule dans le puzzle
   */
  public Action(Cellule cellule1, Cellule cellule2, int coteCellule1, ValeurCote nouvelleValeurCote,
                Coordonnee coordsCellule1) {
    // On verifie qu'on a bien une deuxieme cellule ( cas bord de grille )
    if ( cellule2 != null ) {
      // On verifie que les cotés des deux cellules correspondent bien
      if (cellule1.getCote(coteCellule1) != cellule2.getCote(Cellule.getCoteAdjacent(coteCellule1))) {
        throw new IllegalArgumentException("Les cotés des cellules ne sont pas equivalentes");
      }
    }

    this.coordsCellule1 = coordsCellule1;
    this.cellule1 = cellule1;
    this.cellule2 = cellule2;
    this.coteCellule1 = coteCellule1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = cellule1.getCote(coteCellule1);
  }

  /**
   * Constructeur de la classe Action
   *
   * @param cellule1 La première cellule sur laquelle l'action est effectuée
   * @param cellule2 La deuxième cellule sur laquelle l'action est effectuée
   * @param coteCellule1 Le côté de la première cellule sur laquelle l'action est effectuée
   * @param nouvelleValeurCote La nouvelle valeur du côté après l'action
   * @param ancienneValeurCote L'ancienne valeur du côté avant l'action
   * @param coordsCellule1 Coordonnée de la première cellule dans le puzzle
   */
  public Action(Cellule cellule1, Cellule cellule2, int coteCellule1, ValeurCote nouvelleValeurCote,
                ValeurCote ancienneValeurCote, Coordonnee coordsCellule1) {
    // On verifie qu'on a bien une deuxieme cellule ( cas bord de grille )
    if ( cellule2 != null ) {
      // On verifie que les cotés des deux cellules correspondent bien
      if (cellule1.getCote(coteCellule1) != cellule2.getCote(Cellule.getCoteAdjacent(coteCellule1))) {
        throw new IllegalArgumentException("Les cotés des cellules ne sont pas equivalentes");
      }
    }

    this.coordsCellule1 = coordsCellule1;
    this.cellule1 = cellule1;
    this.cellule2 = cellule2;
    this.coteCellule1 = coteCellule1;
    this.nouvelleValeurCote = nouvelleValeurCote;
    this.ancienneValeurCote = ancienneValeurCote;
  }

  /**
   * Méthode pour appliquer une action sur les cellules du puzzle
   */
  public void appliquerAction() {
    cellule1.setCote(this.coteCellule1, nouvelleValeurCote);
    if ( cellule2 != null ) {
      cellule2.setCote(Cellule.getCoteAdjacent(this.coteCellule1), nouvelleValeurCote);
    }
  }

  /**
   * Méthode pour révoquer une action sur les cellules du puzzle
   */
  public void revoquerAction() {
    cellule1.setCote(this.coteCellule1, ancienneValeurCote);
    if ( cellule2 != null ) {
      cellule2.setCote(Cellule.getCoteAdjacent(this.coteCellule1), ancienneValeurCote);
    }
  }

  /**
   * Méthode pour obtenir la première cellule sur laquelle l'action est effectuée
   *
   * @return La première cellule sur laquelle l'action est effectuée
   */
  public int getCoteCellule1() {
    return  this.coteCellule1;
  }

  /**
   * Méthode pour obtenir la nouvelle valeur du côté après l'action
   *
   * @return La nouvelle valeur du côté après l'action
   */
  public ValeurCote getNouvelleValeurCote() {
    return this.nouvelleValeurCote;
  }

  /**
   * Méthode pour obtenir l'ancienne valeur du côté avant l'action
   *
   * @return L'ancienne valeur du côté avant l'action
   */
  public ValeurCote getAncienneValeurCote() {
    return this.ancienneValeurCote;
  }

  /**
   * Méthode pour vérifier si deux actions sont égales
   *
   * @param obj L'objet à comparer
   * @return Vrai si les deux actions sont égales, faux sinon
   */
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

  /**
   * Méthode pour obtenir la coordonnée dans le puzzle de la première cellule de l'action
   *
   * @return La coordonnée de la première cellule dans le puzzle
   */
  public Coordonnee getCoordsCellule1() {
    return this.coordsCellule1;
  }

  /**
   * Méthode pour cloner une action
   *
   * @param puzzle Le puzzle sur lequel la copie de l'action sera effectuée
   * @return Une copie de l'action
   */
  public Action clone(Puzzle puzzle) {
    int coordsCelluleY = this.coordsCellule1.getY();
    int coordsCelluleX = this.coordsCellule1.getX();
    Coordonnee coordsCelluleAdjacente = puzzle.getCoordoneeAdjacente(coordsCelluleY,coordsCelluleX,this.coteCellule1);
    Cellule cellule1 = puzzle.getCellule(coordsCelluleY,coordsCelluleX);
    Cellule cellule2 = null;
    if ( coordsCelluleAdjacente != null ) {
      cellule2 = puzzle.getCellule(coordsCelluleAdjacente.getY(),coordsCelluleAdjacente.getX());
    }

    return new Action(
        cellule1,
        cellule2,
        this.coteCellule1,
        this.nouvelleValeurCote,
        this.ancienneValeurCote,
        this.coordsCellule1
    );
  }

  /**
   * Méthode pour obtenir une représentation textuelle de l'action
   *
   * @return Une représentation textuelle de l'action
   */
  @Override
  public String toString() {
    return "Action{" +
        ", coteCellule1=" + coteCellule1 +
        ", ancienneValeurCote=" + ancienneValeurCote +
        ", nouvelleValeurCote=" + nouvelleValeurCote +
        ", coordsCellule1=" + coordsCellule1.toString() +
        '}';
  }

}
