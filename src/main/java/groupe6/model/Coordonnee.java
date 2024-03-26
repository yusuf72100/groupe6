package groupe6.model;

/**
 * Classe qui modélise une coordonnée
 *
 * @author Nathan
 */
public class Coordonnee {
  private final int y;
  private final int x;

  Coordonnee(int unY, int unX){
    this.y=unY;
    this.x=unX;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

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
    if (x != other.x) {
      return false;
    }
    if (y != other.y) {
      return false;
    }
    return true;
  }
}