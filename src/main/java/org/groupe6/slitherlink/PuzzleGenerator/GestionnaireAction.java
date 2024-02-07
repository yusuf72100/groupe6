package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.Serializable;
import java.util.ArrayList;

// Classe GestionnaireAction qui implémente Serializable
public class GestionnaireAction implements Serializable {

  private ArrayList<Action> listeAction; // Liste des actions
  private int index; // Index qui correspond a l'action actuelle

  // Méthode qui permet d'obtenir la liste des actions
  public ArrayList<Action> getListeAction() {
    return listeAction;
  }

  // Méthode qui permet d'obtenir l'index de l'action actuelle
  public int getIndex() {
    return index;
  }

  // Constructeur de la classe GestionnaireAction
  public GestionnaireAction() {
    this.listeAction = new ArrayList<Action>();
    this.index = -1;
  }

  // Méthode qui permet de créé une nouvelle action
  public void nouvelleAction(Cellule_Data Cellule_Data1, Cellule_Data Cellule_Data2, int coteCellule_Data1, Cellule_Data.ValeurCote valeurCote) {
    Action action = new Action(Cellule_Data1, Cellule_Data2, coteCellule_Data1, valeurCote);
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

}