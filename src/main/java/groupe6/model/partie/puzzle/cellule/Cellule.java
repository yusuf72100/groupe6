package groupe6.model.partie.puzzle.cellule;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

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

  /**
   * Méthode statique pour cloner une grille de cellules (deep copy)
   *
   * @param grille la grille de cellules à cloner
   * @return une copie profonde de la grille de cellules
   */
  public static Cellule[][] clonerMatriceCellule(Cellule[][] grille) {
    int largeur = grille.length;
    int longueur = grille[0].length;
    Cellule[][] nouvelleGrille = new Cellule[largeur][longueur];

    try {
      for (int i = 0; i < nouvelleGrille.length; i++) {
        for (int j = 0; j < nouvelleGrille[0].length; j++) {
          nouvelleGrille[i][j] = (Cellule) grille[i][j].clone();
        }
      }
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    return nouvelleGrille;
  }

  /**
   * Méthode pour obtenir la représentation textuelle de la grille
   *
   * @param largeur la largeur de la grille
   * @param longueur la longueur de la grille
   * @param grille la grille dont on veut obtenir la représentation textuelle
   * @return la représentation textuelle de la grille
   */
  public static String grilleTostring(int largeur, int longueur, Cellule[][] grille) {
    if ( largeur < 1 || longueur < 1 || largeur != grille.length || longueur != grille[0].length) {
      throw new IllegalArgumentException("La taille de la grille est invalide");
    }

    StringBuilder strBuilder = new StringBuilder();

    for (int y = 0; y < largeur; y++) {
      // Affichage des lignes horizontales
      for (int x = 0; x < longueur; x++) {
        strBuilder.append("+");
        if (grille[y][x].getCote(Cellule.HAUT) == ValeurCote.TRAIT) {
          strBuilder.append("-");
        } else if (grille[y][x].getCote(Cellule.HAUT) == ValeurCote.CROIX) {
          strBuilder.append("*");
        } else {
          strBuilder.append(" ");
        }
      }
      strBuilder.append("+\n");

      // Affichage des lignes verticales et valeurs
      for (int x = 0; x < longueur; x++) {
        if (grille[y][x].getCote(Cellule.GAUCHE) == ValeurCote.TRAIT) {
          strBuilder.append("|");
        } else if (grille[y][x].getCote(Cellule.GAUCHE) == ValeurCote.CROIX) {
          strBuilder.append("*");
        } else {
          strBuilder.append(" ");
        }
        if (grille[y][x].getValeur() != -1) {
          strBuilder.append(grille[y][x].getValeur());
        } else {
          strBuilder.append(" ");
        }
      }
      // Affichage du côté droit de la grille
      if (grille[y][longueur - 1].getCote(Cellule.DROITE) == ValeurCote.TRAIT) {
        strBuilder.append("|\n");
      } else if ( grille[y][longueur - 1].getCote(Cellule.DROITE) == ValeurCote.CROIX) {
        strBuilder.append("*\n");
      } else {
        strBuilder.append(" \n");
      }
    }

    // Affichage de la dernière ligne horizontale
    for (int x = 0; x < longueur; x++) {
      strBuilder.append("+");
      if (grille[largeur - 1][x].getCote(Cellule.BAS) == ValeurCote.TRAIT) {
        strBuilder.append("-");
      } else if (grille[largeur - 1][x].getCote(Cellule.BAS) == ValeurCote.CROIX) {
        strBuilder.append("*");
      } else {
        strBuilder.append(" ");
      }
    }

    strBuilder.append("+");

    return strBuilder.toString();
  }

  /**
   * Méthode pour obtenir une représentation textuelle de la cellule
   *
   * @return la représentation textuelle de la cellule
   */
  @Override
  public String toString() {
    return "Cellule{" +
        "valeur=" + valeur +
        ", cotes=" + Arrays.toString(cotes) +
        '}';
  }

  /**
   * Méthode statique pour comparer deux valeurs de coté
   *
   * @param cote1 le premier coté à comparer
   * @param cote2 le deuxième coté à comparer
   * @return vrai si les valeurs de coté sont équivalentes, faux sinon
   */
  public static boolean compareValeurCote(ValeurCote cote1, ValeurCote cote2){
    if(cote1 == cote2){
      return true;
    }
    return cote1 == ValeurCote.VIDE && cote2 == ValeurCote.CROIX ||
        cote1 == ValeurCote.CROIX && cote2 == ValeurCote.VIDE;
  }
}