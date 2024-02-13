package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.Serializable;

// Classe Cellule qui implémente Serializable
class Cellule_Data implements Serializable {

  // Constantes pour les cotés
  public static final int HAUT = 0;
  public static final int BAS = 1;
  public static final int GAUCHE = 2;
  public static final int DROITE = 3;

  // Enum pour les valeurs des cotés
  public enum ValeurCote {
    VIDE,
    TRAIT,
    CROIX
  }

  private int valeur; // Valeur Numerique de la cellule
  private ValeurCote[] cotes; // Tableau des ValeurCote des cotés

  // Méthode pour obtenir la valeur numérique de la cellule
  public int getValeur() {
    return valeur;
  }

  // Méthode pour obtenir la valeur d'un coté
  public ValeurCote getCote(int cote) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    return this.cotes[cote];
  }

  public void setCote(int cote, ValeurCote valeur) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    this.cotes[cote] = valeur;
  }

  // Constructeur de la classe Cellule
  public void Cellule(int valeur, ValeurCote[] cotes) {
    if (valeur < 0 || valeur > 3) {
      throw new IllegalArgumentException("La valeur de la cellule doit être entre 0 et 3");
    }

    this.valeur = valeur;
    this.cotes = cotes;
  }

  // Méthode obntenir le nombre de trait d'une cellule
  private int nbTrait() {
    int nbTrait = 0;

    for (ValeurCote v : cotes) {
      if (v == ValeurCote.TRAIT) {
        nbTrait++;
      }
    }

    return nbTrait;
  }

  // Méthode pour verifier si le nomre de trait maximal est atteint
  public boolean maxTrait() {
    if (nbTrait() >= this.valeur) {
      return true;
    } else {
      return false;
    }
  }

  // Méthode pour completer les croix d'une cellule quand le nombre de trait
  // maximal est atteint
  public void completerCroix() {
    if (maxTrait()) {
      for (int i = 0; i < cotes.length; i++) {
        if (cotes[i] == ValeurCote.VIDE) {
          cotes[i] = ValeurCote.CROIX;
        }
      }
    }
  }

  // Méthode pour obtenir le coté de la cellule adjacente
  public static int getCoteAdjacent(int cote) {
    switch (cote) {
      case HAUT:
        return BAS;
      case BAS:
        return HAUT;
      case GAUCHE:
        return DROITE;
      case DROITE:
        return GAUCHE;
      default:
        throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
  }

}