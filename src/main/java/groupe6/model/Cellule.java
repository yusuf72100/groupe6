package groupe6.model;

import java.io.Serializable;

/**
 * @author Yamis
 */

// Classe Cellule qui implémente Serializable et Cloneable
public class Cellule implements Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  // Constantes pour les cotés
  public static final int HAUT = 0;
  public static final int BAS = 1;
  public static final int GAUCHE = 2;
  public static final int DROITE = 3;

  private int valeur; // Valeur Numerique de la cellule
  private ValeurCote[] cotes; // Tableau des ValeurCote des cotés

  // Constructeur de la classe Cellule
  public Cellule(int valeur, ValeurCote[] cotes) {
    if (valeur < -1 || valeur > 3) {
      throw new IllegalArgumentException("La valeur de la cellule doit être entre -1 et 3");
    }

    this.valeur = valeur;
    this.cotes = cotes;
  }

  // Méthode pour obtenir la valeur numérique de la cellule
  public int getValeur() {
    return valeur;
  }

  // Méthode pour changer la valeur numérique de la cellule
  public void setValeur(int valeur) {
    if (valeur < -1 || valeur > 3) {
      throw new IllegalArgumentException("La valeur de la cellule doit être entre -1 et 3");
    }
    this.valeur = valeur;
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
  private boolean maxTrait() {
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

  // Méthode pour comparer si deux cellules sont equavalentes
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != this.getClass()) {
      return false;
    }

    if (!(obj instanceof Cellule)) {
      return false;
    }

    Cellule cellule = (Cellule) obj;

    if (this.getValeur() != cellule.getValeur()) {
      return false;
    }

    for (int i = 0; i < cotes.length; i++) {
      if (this.getCote(i) != cellule.getCote(i)) {
        // Si l'un des côtés est TRAIT, la comparaison est fausse
        if (this.getCote(i) == ValeurCote.TRAIT || cellule.getCote(i) == ValeurCote.TRAIT) {
          return false;
        }
      }
    }

    return true;
  }

  public ValeurCote basculeTroisEtats(int cote) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }

    ValeurCote valeur = this.getCote(cote);

    switch (valeur) {
      case VIDE:
        return ValeurCote.TRAIT;
      case TRAIT:
        return ValeurCote.CROIX;
      case CROIX:
        return ValeurCote.VIDE;
      default:
        throw new IllegalArgumentException("État invalide : " + valeur);
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

  @Override
  public Object clone() {

    ValeurCote[] nouveauxCotes = new ValeurCote[4];
    for (int i = 0; i < 4; i++) {
      nouveauxCotes[i] = this.cotes[i];
    }

    return new Cellule(this.valeur, nouveauxCotes);
  }

}