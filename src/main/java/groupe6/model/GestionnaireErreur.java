package groupe6.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GestionnaireErreur implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private List<ErreurInfos> lstErreurs;

  public GestionnaireErreur() {
    this.lstErreurs = new LinkedList<>();
  }

  public GestionnaireErreur(List<ErreurInfos> lstErreurs) {
    this.lstErreurs = lstErreurs;
  }

  public ErreurInfos getErreur(int idx) {
    if ( this.lstErreurs.size() < idx ) {
      return null;
    }
    else {
      return this.lstErreurs.get(idx);
    }
  }

  public void ajouterErreur(ErreurInfos erreur) {
    this.lstErreurs.add(erreur);
  }

  public ErreurInfos getPremiereErreur() {
    if (!lstErreurs.isEmpty()) {
      return this.lstErreurs.get(0);
    }
    else {
      return  null;
    }
  }

  public void supprimer(int idx) {
    this.lstErreurs.remove(idx);
  }

  public int existe(Action action) {

    int coteAdjAction = Cellule.getCoteAdjacent(action.getCoteCellule1());

    // Cherche une erreur dans la liste qui a les mêmes coordonnées et le même côté
    for (int i = 0; i < this.lstErreurs.size(); i++ ) {
      ErreurInfos e = this.lstErreurs.get(i);
      if ( e.getCoordonneeCell1().equals(action.getCoordsCellule1()) && e.getCoteCell1() == action.getCoteCellule1() ) {
        return i;
      }
      int coteAdjErreur = Cellule.getCoteAdjacent(e.getCoteCell1());
      if ( e.getGetCoordonneeCell2() != null && e.getGetCoordonneeCell2().equals(action.getCoordsCellule1())
          && coteAdjErreur == action.getCoteCellule1()
      ) {
        return i;
      }
    }
    return -1;
  }

}
