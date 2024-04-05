package groupe6.model.partie.erreur;

import groupe6.model.partie.puzzle.Coordonnee;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe qui modélise les informations d'une erreur de l'utilisateur dans le puzzle
 *
 * @author Yamis
 */
public class ErreurInfos implements Serializable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Coordonnée de la première cellule sur laquelle l'erreur a été commise
   */
  private final Coordonnee coordonneeCell1;

  /**
   * Coordonnée de la deuxième cellule sur laquelle l'erreur a été commise
   */
  private final Coordonnee coordonneeCell2;

  /**
   * Côté de la première cellule sur laquelle l'erreur a été commise
   */
  private final int coteCell1;

  /**
   * Index de l'action qui a causé l'erreur
   */
  private final int indexAction;

  /**
   * Constructeur de la classe ErreurInfos
   *
   * @param coordonneeCell1 la coordonnée de la première cellule sur laquelle l'erreur a été commise
   * @param coordonneeCell2 la coordonnée de la deuxième cellule sur laquelle l'erreur a été commise
   * @param coteCell1 le côté de la première cellule sur laquelle l'erreur a été commise
   * @param indexAction l'index de l'action qui a causé l'erreur
   */
  public ErreurInfos(Coordonnee coordonneeCell1, Coordonnee coordonneeCell2,
                     int coteCell1, int indexAction
  ) {
    this.coordonneeCell1 = coordonneeCell1;
    this.coordonneeCell2 = coordonneeCell2;
    this.coteCell1 = coteCell1;
    this.indexAction = indexAction;
  }

  /**
   * Méthode pour obtenir la coordonnée de la première cellule où l'erreur a été commise
   *
   * @return la coordonnée de la première cellule
   */
  public Coordonnee getCoordonneeCell1() {
    return coordonneeCell1;
  }

  /**
   * Méthode pour obtenir la coordonnée de la deuxième cellule où l'erreur a été commise
   *
   * @return la coordonnée de la deuxième cellule
   */
  public Coordonnee getCoordonneeCell2() {
    return coordonneeCell2;
  }

  /**
   * Méthode pour obtenir le côté de la première cellule où l'erreur a été commise
   *
   * @return le côté de la première cellule
   */
  public int getCoteCell1() {
    return coteCell1;
  }

  /**
   * Méthode pour obtenir l'index de l'action qui a causé l'erreur
   *
   * @return l'index de l'action
   */
  public int getIndexAction() {
    return indexAction;
  }

  /**
   * Méthode pour obtenir une représentation textuelle de l'erreur
   *
   * @return la représentation textuelle de l'erreur
   */
  @Override
  public String toString() {
    return "ErreurInfos{" +
        "coordonneeCell1=" + coordonneeCell1 +
        ", coordonneeCell2=" + coordonneeCell2 +
        ", coteCell1=" + coteCell1 +
        ", indexAction=" + indexAction +
        '}';
  }
}
