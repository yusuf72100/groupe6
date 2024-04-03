package groupe6.model;

import java.io.Serial;
import java.io.Serializable;

public class ErreurInfos implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Coordonnee coordonneeCell1;
  private Coordonnee coordonneeCell2;
  private int coteCell1;
  private int indexAction;

  public ErreurInfos(Coordonnee coordonneeCell1, Coordonnee coordonneeCell2,
                     int coteCell1, int indexAction
  ) {
    this.coordonneeCell1 = coordonneeCell1;
    this.coordonneeCell2 = coordonneeCell2;
    this.coteCell1 = coteCell1;
    this.indexAction = indexAction;
  }

  public Coordonnee getCoordonneeCell1() {
    return coordonneeCell1;
  }

  public int getCoteCell1() {
    return coteCell1;
  }

  public Coordonnee getGetCoordonneeCell2() {
    return coordonneeCell2;
  }

  public int getIndexAction() {
    return indexAction;
  }

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
