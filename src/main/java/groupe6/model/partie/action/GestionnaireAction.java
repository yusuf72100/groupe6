package groupe6.model.partie.action;

import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe qui représente le gestionnaire des actions
 *
 * @author Yamis
 */
public class GestionnaireAction implements Serializable,Cloneable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * La liste des actions effectuées dans la partie
   */
  private List<Action> listeAction;

  /**
   * Le puzzle sur lequel les actions du gestionnaire sont effectuées
   */
  private final Puzzle puzzle;

  /**
   * L'index actuel dans la liste des actions
   */
  private int index;

  /**
   * Constructeur de la classe GestionnaireAction
   *
   * @param puzzle le puzzle sur lequel les actions du gestionnaire sont effectuées
   */
  public GestionnaireAction(Puzzle puzzle) {
    this.listeAction = new ArrayList<Action>();
    this.index = -1;
    this.puzzle = puzzle;
  }

  /**
   * Méthode pour obtenir la liste des actions
   *
   * @return la liste des actions
   */
  public List<Action> getListeAction() {
    return listeAction;
  }

  /**
   * Méthode pour définir la liste des actions
   *
   * @param listeAction la liste des actions
   */
  public void setListeAction(List<Action> listeAction) {
    this.listeAction = listeAction;
  }

  /**
   * Méthode pour obtenir l'index actuel dans la liste des actions
   *
   * @return l'index actuel
   */
  public int getIndex() {
    return index;
  }

  /**
   * Méthode pour définir l'index actuel dans la liste des actions
   *
   * @param index l'index actuel
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * Méthode pour ajouter une action au gestionnaire d'actions
   *
   * @param action l'action à ajouter
   */
  public void ajouterAction(Action action) {
    listeAction.add(action);
    index = listeAction.size() - 1;
  }

  /**
   * Méthode pour effacer les actions suivantes à partir de l'index actuel
   */
  public void effacerActionsSuivantes() {
    for (int i = listeAction.size() - 1; i > index; i--) {
      listeAction.remove(i);
    }
  }

  /**
   * Méthode qui permet d'annuler une action
   */
  public void annulerAction() {
    if (index >= 0) {
      listeAction.get(index).revoquerAction();
      index--;
    }
  }

  /**
   * Méthode qui permet de rétablir une action
   */
  public void retablirAction() {
    if (index < listeAction.size() - 1) {
      index++;
      listeAction.get(index).appliquerAction();
    }
  }

  /**
   * Méthode pour cloner le gestionnaire d'actions
   *
   * @param puzzle le puzzle sur lequel les actions du gestionnaire sont effectuées
   * @return une copie du gestionnaire d'actions sur le puzzle donné
   */
  public GestionnaireAction clone(Puzzle puzzle) {
    GestionnaireAction gestionnaireClone = new GestionnaireAction(puzzle);
    gestionnaireClone.setListeAction(clonerListeAction(puzzle));
    gestionnaireClone.setIndex(this.index);

    return gestionnaireClone;
  }

  /**
   * Méthode pour cloner la liste des actions
   *
   * @param puzzle le puzzle sur lequel les actions du gestionnaire sont effectuées
   * @return une copie de la liste des actions sur le puzzle donné
   */
  private List<Action> clonerListeAction(Puzzle puzzle) {
    List<Action> newListe = new ArrayList<Action>();
    for (Action action : this.listeAction) {
      newListe.add((Action) action.clone(puzzle));
    }
    return newListe;
  }

  /**
   * Méthode pour cloner le gestionnaire d'actions sans le puzzle
   * Méthode non supportée pour un gestionnaire d'actions
   *
   * @return Erreur
   * @throws UnsupportedOperationException un gestionnaire d'actions ne peut pas être cloné definir le puzzle ou sont effectuées les actions
   */
  @Override
  public GestionnaireAction clone() {
    throw new UnsupportedOperationException("Une action peut seulement être clonée avec un puzzle en paramètre");
  }

  /**
   * Méthode pour obtenir une représentation textuelle du gestionnaire d'actions
   *
   * @return une représentation textuelle du gestionnaire d'actions
   */
  @Override
  public String toString() {
    StringBuilder strBuilder = new StringBuilder("");
    strBuilder.append("GestionnaireAction :\n"+
        "index=" + index + '\n');
    for ( Action a : listeAction ) {
      strBuilder.append(a.toString()+"\n");
    }

    return  strBuilder.toString();
  }

  /**
   * Méthode pour obtenir les coordonnées des cellules qui ont été modifiées après une erreur
   *
   * @param idxActionPremiereErreur l'index de l'action qui a causé l'erreur
   * @return un ensemble de coordonnées de cellules modifiées après l'erreur
   */
  public Set<Coordonnee> getCoordsActionApresErreur(int idxActionPremiereErreur ) {
    Set<Coordonnee> setCoords = new HashSet<Coordonnee>();
    for (int i = idxActionPremiereErreur; i < this.listeAction.size(); i++) {
      Action action = this.listeAction.get(i);
      Coordonnee coordsCell1 = action.getCoordsCellule1();
      Coordonnee coordsCell2 = puzzle.getCoordoneeAdjacente(coordsCell1.getY(),coordsCell1.getX(),action.getCoteCellule1());
      setCoords.add(coordsCell1);
      setCoords.add(coordsCell2);
    }
    return setCoords;
  }

  /**
   * Méthode pour annuler toutes les actions effectuées après une erreur
   *
   * @param idxActionPremiereErreur l'index de l'action qui a causé l'erreur
   */
  public void annulerActionApresErreur(int idxActionPremiereErreur) {
    while ( this.index >= idxActionPremiereErreur ) {
      this.annulerAction();
    }
    this.effacerActionsSuivantes();
  }

}