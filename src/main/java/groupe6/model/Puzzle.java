package groupe6.model;

import java.io.*;
import java.util.Arrays;

/**
 * La classe qui représente un puzzle
 *
 * @author Yamis
 */

// Classe Puzzle qui implémente Serializable et Cloneable
public class Puzzle implements Serializable, Cloneable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final int largeur; // Nombre de lignes
  private final int longueur; // Nombre de colonnes
  private Cellule[][] grilleSolution; // Grille avec la solution du puzzle
  private Cellule[][] grilleJeu; // Grille sur laquelle le joueur joue
  private final DifficultePuzzle difficulte; // Difficulté du puzzle

  // Constructeur de la classe Puzzle
  public Puzzle(int largeur, int longueur, Cellule[][] grilleSolution, DifficultePuzzle difficulte) {
    if (grilleSolution.length != largeur || grilleSolution[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.grilleSolution = grilleSolution;
    genererGrillePropre();
    this.difficulte = difficulte;
  }

  // Constructeur de la classe Puzzle avec un grilleJeu de spécifié
  public Puzzle(int largeur, int longueur, Cellule[][] grilleSolution, Cellule[][] grilleJeu,
                DifficultePuzzle difficulte) {
    if (grilleSolution.length != largeur || grilleSolution[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.grilleSolution = grilleSolution;
    this.grilleJeu = grilleJeu;
    this.difficulte = difficulte;
  }

  // Méthode pour obtenir la largeur
  public int getLargeur() {
    return largeur;
  }

  // Méthode pour obtenir la longueur
  public int getLongueur() {
    return longueur;
  }

  public Cellule[][] getGrilleSolution() {
    return  this.grilleSolution;
  }

  public Cellule[][] getGrilleJeu() {
    return grilleJeu;
  }

  public DifficultePuzzle getDifficulte() {
    return difficulte;
  }

  // Méthode pour générer un puzzle propre a partir de la solution
  public void genererGrillePropre() {
    this.grilleJeu = new Cellule[largeur][longueur];
    for (int y = 0; y < grilleSolution.length; y++) {
      for (int x = 0; x < grilleSolution[y].length; x++) {
        ValeurCote[] cotesVide = new ValeurCote[4];
        Arrays.fill(cotesVide, ValeurCote.VIDE);
        this.grilleJeu[y][x] = new Cellule(grilleSolution[y][x].getValeur(), cotesVide);
      }
    }
  }

  private String grilleTostring(Cellule[][] grille) {
    StringBuilder strBuilder = new StringBuilder();

    for (int y = 0; y < largeur; y++) {
      // Affichage des lignes horizontales
      for (int x = 0; x < longueur; x++) {
        strBuilder.append("+");
        if (grille[y][x].getCote(Cellule.HAUT) == ValeurCote.TRAIT) {
          strBuilder.append("-");
        } else {
          strBuilder.append(" ");
        }
      }
      strBuilder.append("+\n");

      // Affichage des lignes verticales et valeurs
      for (int x = 0; x < longueur; x++) {
        if (grille[y][x].getCote(Cellule.GAUCHE) == ValeurCote.TRAIT) {
          strBuilder.append("|");
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
      } else {
        strBuilder.append(" \n");
      }
    }

    // Affichage de la dernière ligne horizontale
    for (int x = 0; x < longueur; x++) {
      strBuilder.append("+");
      if (grille[largeur - 1][x].getCote(Cellule.BAS) == ValeurCote.TRAIT) {
        strBuilder.append("-");
      } else {
        strBuilder.append(" ");
      }
    }
    strBuilder.append("+\n");

    return strBuilder.toString();
  }

  // Méthode pour afficher le puzzle dans la console
  @Override
  public String toString() {
    StringBuilder strBuilder = new StringBuilder();

    String str = "Difficulté : " + difficulte + "\n";
    strBuilder.append(str);
    str = "Largeur : " + largeur + "\n";
    strBuilder.append(str);
    str = "Longueur : " + longueur + "\n";
    strBuilder.append(str);
    strBuilder.append("\nGrille Solution : \n");
    strBuilder.append(grilleTostring(grilleSolution));
    strBuilder.append("\nGrille Cellules : \n");
    strBuilder.append(grilleTostring(grilleJeu));

    return strBuilder.toString();
  }

  // Méthode pour obenir une cellule dans la grille
  public Cellule getCellule(int y, int x) {
    return grilleJeu[y][x];
  }

  public Cellule getCelluleSolution(int y, int x) {
    return grilleSolution[y][x];
  }

  // Méthode pour savoir si le puzzle est complet
  public boolean estComplet() {
    for (int y = 0; y < grilleSolution.length; y++) {
      for (int x = 0; x < grilleSolution[y].length; x++) {
        if (!grilleSolution[y][x].equals(grilleJeu[y][x])) {
          return false;
        }
      }
    }

    return true;
  }

  public Cellule getCelluleAdjacente(int y, int x, int cote) {
    Coordonnee coordsAdjacente = getCoordoneeAdjacente(y,x,cote);
    if ( coordsAdjacente != null ) {
      int coordsAdjacenteY = coordsAdjacente.getY();
      int coordsAdjacenteX = coordsAdjacente.getX();
      return getCellule(coordsAdjacenteY,coordsAdjacenteX);
    }else {
      return null;
    }
  }

  public Coordonnee getCoordoneeAdjacente(int y, int x, int cote) {
    int yAdj = y;
    int xAdj = x;

    switch (cote) {
      case Cellule.HAUT:
        yAdj--;
        break;
      case Cellule.BAS:
        yAdj++;
        break;
      case Cellule.GAUCHE:
        xAdj--;
        break;
      case Cellule.DROITE:
        xAdj++;
        break;
    }

    if (estDansGrille(yAdj, xAdj)) {
      return new Coordonnee(yAdj,xAdj);
    } else {
      return null;
    }
  }

  public boolean estDansGrille(int y, int x) {
    return y >= 0 && y < largeur && x >= 0 && x < longueur;
  }

  // Méthode pour faire cloner recursivement un puzzle
  @Override
  public Object clone() throws CloneNotSupportedException {
    Puzzle nouveauPuzzle = (Puzzle) super.clone();
    nouveauPuzzle.grilleSolution = clonerMatriceCellule(grilleSolution);
    nouveauPuzzle.grilleJeu = clonerMatriceCellule(grilleJeu);
    return nouveauPuzzle;
  }

  // Méthode pour cloner un tableau de Cellules de manière récursive
  private Cellule[][] clonerMatriceCellule(Cellule[][] grille) {
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

  // Méthode pour sauvegarder le puzzle
  public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
      oos.writeObject(puzzle);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Méthode pour charger le puzzle
  public static Puzzle chargerPuzzle(String chemin) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
      return (Puzzle) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println(chemin);
      e.printStackTrace();
      return null;
    }
  }
}
