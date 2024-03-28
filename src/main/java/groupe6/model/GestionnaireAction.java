package groupe6.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente le gestionnaire des actions
 *
 * @author Yamis
 */

// Classe GestionnaireAction qui implémente Serializable
public class GestionnaireAction implements Serializable,Cloneable {

  @Serial
  private static final long serialVersionUID = 1L;

  private List<Action> listeAction; // Liste des actions
  private Puzzle puzzle; // Puzzle associé au gestionnaire d'actions

  private int index; // Index qui correspond a l'action actuelle

  // Constructeur de la classe GestionnaireAction
  public GestionnaireAction(Puzzle puzzle) {
    this.listeAction = new ArrayList<Action>();
    this.index = -1;
    this.puzzle = puzzle;
  }

  // Méthode qui permet d'obtenir la liste des actions
  public List<Action> getListeAction() {
    return listeAction;
  }

  public void setListeAction(List<Action> listeAction) {
    this.listeAction = listeAction;
  }

  // Méthode qui permet d'obtenir l'index de l'action actuelle
  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void ajouterAction(Action action) {
    listeAction.add(action);
    index = listeAction.size() - 1;
  }

  // Méthode qui permet d'éffacer toutes les ations suivantes
  public void effacerActionsSuivantes() {
    for (int i = listeAction.size() - 1; i > index; i--) {
      listeAction.remove(i);
    }
  }

  // Méthode qui permet d'annuler une action
  public void annulerAction() {
    if (index >= 0) {
      listeAction.get(index).revoquerAction();
      index--;
    }
  }

  // Méthode qui permet de rétablir une action
  public void retablirAction() {
    if (index < listeAction.size() - 1) {
      index++;
      listeAction.get(index).appliquerAction();
    }
  }

  public GestionnaireAction clone(Puzzle puzzle) {
    GestionnaireAction gestionnaireClone = new GestionnaireAction(puzzle);
    gestionnaireClone.setListeAction(clonerListeAction(puzzle));
    gestionnaireClone.setIndex(this.index);

    return gestionnaireClone;
  }

  private List<Action> clonerListeAction(Puzzle puzzle) {
    List<Action> newListe = new ArrayList<Action>();
    for (Action action : this.listeAction) {
      newListe.add((Action) action.clone(puzzle));
    }
    return newListe;
  }

  @Override
  public GestionnaireAction clone() {
    throw new UnsupportedOperationException("Une action peut seulement être clonée avec un puzzle en paramètre");
  }
}