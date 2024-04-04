package groupe6.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe qui modélise le gestionnaire des erreurs
 *
 * @author Yamis
 */
public class GestionnaireErreur implements Serializable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   *  La liste des erreurs commises par l'utilisateur sur le puzzle
   */
  private List<ErreurInfos> lstErreurs;

  /**
   * Constructeur de la classe GestionnaireErreur
   */
  public GestionnaireErreur() {
    this.lstErreurs = new LinkedList<>();
  }

  /**
   * Constructeur de la classe GestionnaireErreur
   *
   * @param lstErreurs la liste des erreurs commises par l'utilisateur sur le puzzle
   */
  public GestionnaireErreur(List<ErreurInfos> lstErreurs) {
    this.lstErreurs = lstErreurs;
  }

  /**
   * Méthode pour obtenir une erreur de la liste
   *
   * @param idx l'index de l'erreur dans la liste
   * @return l'erreur à l'index donné
   */
  public ErreurInfos getErreur(int idx) {
    if ( this.lstErreurs.size() < idx ) {
      return null;
    }
    else {
      return this.lstErreurs.get(idx);
    }
  }

  /**
   * Méthode pour verifier si la liste des erreurs est vide
   *
   * @return vrai si la liste est vide, faux sinon
   */
  public Boolean  estVide() {
    return this.lstErreurs.isEmpty();
  }

  /**
   * Métode pour ajouter une erreur à la liste des erreurs
   *
   * @param erreur l'erreur à ajouter
   */
  public void ajouterErreur(ErreurInfos erreur) {
    this.lstErreurs.add(erreur);
  }

  /**
   * Méthode pour obtenir la première erreur qui a été commise
   *
   * @return la première erreur commise par l'utilisateur
   */
  public ErreurInfos getPremiereErreur() {
    if (!lstErreurs.isEmpty()) {
      return this.lstErreurs.get(0);
    }
    else {
      return  null;
    }
  }

  /**
   * Méthode pour supprimer une erreur de la liste
   *
   * @param idx l'index de l'erreur à supprimer
   */
  public void supprimer(int idx) {
    this.lstErreurs.remove(idx);
  }

  /**
   * Méthode pour verifier si une erreur existe déjà dans la liste
   *
   * @param action l'action qui cause l'erreur
   * @return l'index de l'erreur dans la liste, -1 si l'erreur n'existe pas
   */
  public int existe(Action action) {

    int coteAdjAction = Cellule.getCoteAdjacent(action.getCoteCellule1());

    // Cherche une erreur dans la liste qui a les mêmes coordonnées et le même côté
    for (int i = 0; i < this.lstErreurs.size(); i++ ) {
      ErreurInfos e = this.lstErreurs.get(i);
      if ( e.getCoordonneeCell1().equals(action.getCoordsCellule1()) && e.getCoteCell1() == action.getCoteCellule1() ) {
        return i;
      }
      int coteAdjErreur = Cellule.getCoteAdjacent(e.getCoteCell1());
      if ( e.getCoordonneeCell2() != null && e.getCoordonneeCell2().equals(action.getCoordsCellule1())
          && coteAdjErreur == action.getCoteCellule1()
      ) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Méthode pour supprimer toutes les erreurs de la liste
   */
  public void supprimerErreurs() {
    this.lstErreurs.clear();
  }

}
