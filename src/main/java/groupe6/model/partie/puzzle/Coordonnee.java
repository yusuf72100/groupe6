package groupe6.model.partie.puzzle;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe qui modélise une coordonnée
 *
 * @author Nathan
 */
public class Coordonnee implements Serializable,Cloneable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * La position en y de la coordonnée
   */
  private final int y;

  /**
   * La position en x de la coordonnée
   */
  private final int x;

  /**
   * Constructeur de la classe Coordonnee
   *
   * @param unY la position en y de la coordonnée
   * @param unX la position en x de la coordonnée
   */
  public Coordonnee(int unY, int unX){
    this.y=unY;
    this.x=unX;
  }

  /**
   * Méthode pour obtenir la position en y de la coordonnée
   *
   * @return la position en y
   */
  public int getY() {
    return y;
  }

  /**
   * Méthode pour obtenir la position en x de la coordonnée
   *
   * @return la position en x
   */
  public int getX() {
    return x;
  }

  /**
   * Méthode pour verifier si deux coordonnées sont equivalentes
   *
   * @param obj L'ojet à comparer
   * @return vrai si les coordonnées sont équivalentes, faux sinon
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Coordonnee other = (Coordonnee) obj;
    return y == other.y && x == other.x;
  }

  /**
   * Méthode pour obtenir le hashcode d'une coordonnée
   *
   * @return le hashcode de la coordonnée
   */
  @Override
  public int hashCode() {
    return Objects.hash(y, x);
  }

  /**
   * Méthode pour obtenir une représentation textuelle de la coordonnée
   *
   * @return la représentation textuelle de la coordonnée
   */
  @Override
  public String toString() {
    return "Coordonnee{" +
        "y=" + y +
        ", x=" + x +
        '}';
  }

  /**
   * Méthode pour cloner une coordonnée
   *
   * @return la coordonnée clonée
   * @throws CloneNotSupportedException si le clonage n'est pas supporté
   */
  @Override
  public Coordonnee clone() throws CloneNotSupportedException {
    return new Coordonnee(this.y,this.x);
  }
}