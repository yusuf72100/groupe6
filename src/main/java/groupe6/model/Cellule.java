package groupe6.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe Cellule qui permet de stocker les informations d'une cellule
 *
 * @author Yamis
 */
public class Cellule implements Serializable, Cloneable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Constantes HAUT = 0 qui représente le coté haut de la cellule
   */
  public static final int HAUT = 0;

  /**
   * Constantes BAS = 1 qui représente le coté bas de la cellule
   */
  public static final int BAS = 1;

  /**
   * Constantes GAUCHE = 2 qui représente le coté gauche de la cellule
   */
  public static final int GAUCHE = 2;

  /**
   * Constantes DROITE = 3 qui représente le coté droit de la cellule
   */
  public static final int DROITE = 3;

  /**
   * La valeur numérique de la cellule
   */
  private int valeur;

  /**
   * Les cotés de la cellule
   */
  private final ValeurCote[] cotes; // Tableau des ValeurCote des cotés

  /**
   * Constructeur de la classe Cellule
   *
   * @param valeur la valeur numérique de la cellule
   * @param cotes les cotés de la cellule
   */
  public Cellule(int valeur, ValeurCote[] cotes) {
    // On vérifie que la valeur numérique soit correcte ( Vide, 0, 1, 2, 3 )
    if (valeur < -1 || valeur > 3) {
      throw new IllegalArgumentException("La valeur de la cellule doit être entre -1 et 3");
    }

    this.valeur = valeur;
    this.cotes = cotes;
  }

  /**
   * Méthode pour obtenir la valeur numérique de la cellule
   *
   * @return la valeur numérique de la cellule
   */
  public int getValeur() {
    return valeur;
  }

  /**
   * Méthode pour définir la valeur numérique de la cellule
   *
   * @param valeur la valeur numérique de la cellule
   */
  public void setValeur(int valeur) {
    if (valeur < -1 || valeur > 3) {
      throw new IllegalArgumentException("La valeur de la cellule doit être entre -1 et 3");
    }
    this.valeur = valeur;
  }

  /**
   * Méthode pour obtenir la valeur d'un coté de la cellule
   *
   * @param cote le coté de la cellule que l'on veut obtenir
   * @return la valeur du coté de la cellule
   */
  public ValeurCote getCote(int cote) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    return this.cotes[cote];
  }

  /**
   * Méthode pour obtenir les cotés de la cellule
   *
   * @return les cotés de la cellule
   */
  public ValeurCote[] getCotes() {
    return cotes;
  }

  /**
   * Méthode pour définir la valeur d'un coté de la cellule
   *
   * @param cote   le coté de la cellule que l'on veut définir
   * @param valeur la valeur du coté de la cellule
   */
  public void setCote(int cote, ValeurCote valeur) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    this.cotes[cote] = valeur;
  }

  /**
   * Méthode pour obtenir le nombre coté TRAIT d'une cellule
   *
   * @return le nombre de coté TRAIT
   */
  public int nbTrait() {
    int nbTrait = 0;

    for (ValeurCote v : cotes) {
      if (v == ValeurCote.TRAIT) {
        nbTrait++;
      }
    }

    return nbTrait;
  }

  /**
   * Méthode pour verifier si le nombre de trait maximal est atteint
   *
   * @return vrai si le nombre de trait maximal est atteint, faux sinon
   */
  public boolean maxTrait() {
    return nbTrait() >= this.valeur;
  }


  /**
   * Méthode pour verifier si deux cellules sont equivalentes
   *
   * @param obj l'objet à comparer
   * @return vrai si les cellules sont équivalentes, faux sinon
   */
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

  /**
   * Méthode pour obtenir la valeur de coté suivante selon la bascule à trois états
   *
   * @param cote la valeur actuelle du coté
   * @return la valeur suivante du coté
   */
  public ValeurCote basculeTroisEtats(int cote) {
    // On vérifie que le coté soit valide
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

  /**
   * Méthode pour obtenir la valeur du cote adjacent à un cote donné
   *
   * @param cote le cote dont on veut obtenir le cote adjacent
   * @return le cote adjacent
   */
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

  /**
   * Méthode pour cloner une cellule
   *
   * @return la cellule clonée
   * @throws CloneNotSupportedException si la cellule ne peut pas être clonée
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    Cellule cellule = (Cellule) super.clone();

    ValeurCote[] nouveauxCotes = new ValeurCote[4];
    for (int i = 0; i < 4; i++) {
      nouveauxCotes[i] = this.cotes[i];
    }

    return new Cellule(this.valeur, nouveauxCotes);
  }

}