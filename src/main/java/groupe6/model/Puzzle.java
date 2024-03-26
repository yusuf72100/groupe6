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
  private Cellule[][] solutionPuzzle; // Grille avec la solution du puzzle
  private Cellule[][] grilleCellules; // Grille de cellules du puzzle
  private final DifficultePuzzle difficulte; // Difficulté du puzzle

  // Constructeur de la classe Puzzle
  public Puzzle(int largeur, int longueur, Cellule[][] solutionPuzzle, DifficultePuzzle difficulte) {
    if (solutionPuzzle.length != largeur || solutionPuzzle[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.solutionPuzzle = solutionPuzzle;
    this.grilleCellules = genererGrillePropre();
    this.difficulte = difficulte;
  }

  // Constructeur de la classe Puzzle avec un grilleCellules
  public Puzzle(int largeur, int longueur, Cellule[][] solutionPuzzle, Cellule[][] grilleCellules,
      DifficultePuzzle difficulte) {
    if (solutionPuzzle.length != largeur || solutionPuzzle[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.solutionPuzzle = solutionPuzzle;
    this.grilleCellules = grilleCellules;
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

  public Cellule[][] getSolutionPuzzle() {
    return  this.solutionPuzzle;
  }

  public DifficultePuzzle getDifficulte() {
    return difficulte;
  }

  // Méthode pour générer un puzzle propre a partir de la solution
  private Cellule[][] genererGrillePropre() {
    Cellule[][] grillePropre = new Cellule[largeur][longueur];

    for (int y = 0; y < solutionPuzzle.length; y++) {
      for (int x = 0; x < solutionPuzzle[y].length; x++) {
        ValeurCote[] cotesVide = new ValeurCote[4];
        Arrays.fill(cotesVide, ValeurCote.VIDE);
        grillePropre[y][x] = new Cellule(solutionPuzzle[y][x].getValeur(), cotesVide);
      }
    }

    return grillePropre;
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
    strBuilder.append(grilleTostring(solutionPuzzle));
    strBuilder.append("\nGrille Cellules : \n");
    strBuilder.append(grilleTostring(grilleCellules));

    return strBuilder.toString();
  }

  // Méthode pour obenir une cellule dans la grille
  public Cellule getCellule(int y, int x) {
    return grilleCellules[y][x];
  }

  public Cellule getCelluleSolution(int y, int x) {
    return solutionPuzzle[y][x];
  }

  // Méthode pour savoir si le puzzle est complet
  public boolean estComplet() {
    for (int y = 0; y < solutionPuzzle.length; y++) {
      for (int x = 0; x < solutionPuzzle[y].length; x++) {
        System.out.print("-----------------");
        System.out.println();
        System.out.print(solutionPuzzle[y][x].getValeur() + "-");
        for (int i = 0; i < 4; i++) {
          System.out.print(solutionPuzzle[y][x].getCote(i) + ",");
        }
        System.out.println();
        System.out.print(grilleCellules[y][x].getValeur() + "-");
        for (int i = 0; i < 4; i++) {
          System.out.print(grilleCellules[y][x].getCote(i) + ",");
        }
        System.out.println();

        if (!solutionPuzzle[y][x].equals(grilleCellules[y][x])) {
          return false;
        }
      }
    }

    return true;
  }

  public Cellule getCelluleAdjacente(int y, int x, int cote) {
    Coordonnee coordsAdjacente = getCoordoneeAdjacente(y,x,cote);
    int coordsAdjacenteY = coordsAdjacente.getY();
    int coordsAdjacenteX = coordsAdjacente.getX();
    if ( estDansGrille(coordsAdjacenteY,coordsAdjacenteX) ) {
      return getCellule(coordsAdjacenteY,coordsAdjacenteX);
    }
    else {
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
    nouveauPuzzle.solutionPuzzle = clonerMatriceCellule(solutionPuzzle);
    nouveauPuzzle.grilleCellules = clonerMatriceCellule(grilleCellules);
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
      e.printStackTrace();
      return null;
    }
  }
}
