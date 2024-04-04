package groupe6.model;

import java.io.*;
import java.util.Arrays;

/**
 * La classe qui représente un puzzle
 *
 * @author Yamis
 */
public class Puzzle implements Serializable, Cloneable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * La largeur du puzzle ( nombre de lignes )
   */
  private final int largeur;

  /**
   * La longueur du puzzle ( nombre de colonnes )
   */
  private final int longueur;

  /**
   * La grille avec la solution du puzzle
   */
  private Cellule[][] grilleSolution;

  /**
   * La grille sur laquelle le joueur joue
   */
  private Cellule[][] grilleJeu;

  /**
   * La difficulté du puzzle
   */
  private final DifficultePuzzle difficulte;

  /**
   * Constructeur de la classe Puzzle ( nouveau puzzle )
   *
   * @param largeur la largeur du puzzle (nombre de lignes)
   * @param longueur la longueur du puzzle (nombre de colonnes)
   * @param grilleSolution la grille avec la solution du puzzle
   * @param difficulte la difficulté du puzzle
   */
  public Puzzle(int largeur, int longueur, Cellule[][] grilleSolution, DifficultePuzzle difficulte) {

    // Vérification de la correspondance entre la taille de la grille de solution et la largeur et la longueur
    if (grilleSolution.length != largeur || grilleSolution[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.grilleSolution = grilleSolution;
    genererGrillePropre();
    this.difficulte = difficulte;
  }

  /**
   * Constructeur de la classe Puzzle ( puzzle déjà existant )
   *
   * @param largeur la largeur du puzzle (nombre de lignes)
   * @param longueur la longueur du puzzle (nombre de colonnes)
   * @param grilleSolution la grille avec la solution du puzzle
   * @param grilleJeu la grille sur laquelle le joueur joue
   * @param difficulte la difficulté du puzzle
   */
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

  /**
   * Méthode pour obtenir la largeur
   *
   * @return la largeur du puzzle
   */
  public int getLargeur() {
    return largeur;
  }

  /**
   * Méthode pour obtenir la longueur
   *
   * @return la longueur du puzzle
   */
  public int getLongueur() {
    return longueur;
  }


  /**
   * Méthode pour obtenir la grille de solution
   *
   * @return la grille de solution
   */
  public Cellule[][] getGrilleSolution() {
    return  this.grilleSolution;
  }

  /**
   * Méthode pour obtenir la grille de jeu
   *
   * @return la grille de jeu
   */
  public Cellule[][] getGrilleJeu() {
    return grilleJeu;
  }

  /**
   * Méthode pour obtenir la difficulté
   *
   * @return la difficulté du puzzle
   */
  public DifficultePuzzle getDifficulte() {
    return difficulte;
  }

  /**
   * Méthode pour générer une grille propre à partir de la grille de solution
   */
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

  /**
   * Méthode pour obtenir la représentation textuelle de la grille
   *
   * @param grille la grille dont on veut obtenir la représentation textuelle
   * @return la représentation textuelle de la grille
   */
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

  /**
   * Méthode pour obtenir la représentation textuelle du puzzle
   *
   * @return la représentation textuelle du puzzle
   */
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

  /**
   * Méthode pour obtenir une cellule dans la grille de jeu
   *
   * @param y la position en y
   * @param x la position en x
   * @return la cellule à la position (y,x) dans la grille de jeu
   */
  public Cellule getCellule(int y, int x) {
    return grilleJeu[y][x];
  }

  /**
   * Méthode pour obtenir une cellule dans la grille de solution
   *
   * @param y la position en y
   * @param x la position en x
   * @return la cellule à la position (y,x) dans la grille de solution
   */
  public Cellule getCelluleSolution(int y, int x) {
    return grilleSolution[y][x];
  }

  /**
   * Méthode pour verifier si le puzzle est complet
   *
   * @return vrai si le puzzle est complet, faux sinon
   */
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

  /**
   * Méthode pour obtenir la cellule adjacente à une position et un côté donné dans la grille de jeu
   *
   * @param y la position en y
   * @param x la position en x
   * @param cote le côté de la cellule
   * @return la cellule adjacente à la position (y,x) dans la direction du côté donné dans la grille de jeu
   */
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

  /**
   * Méthode pour obtenir la cellule adjacente à une position et un côté donné dans la grille de solution
   *
   * @param y la position en y
   * @param x la position en x
   * @param cote le côté de la cellule
   * @return la cellule adjacente à la position (y,x) dans la direction du côté donné dans la grille de solution
   */
  public Cellule getCelluleAdjacenteSolution(int y, int x, int cote) {
    Coordonnee coordsAdjacente = getCoordoneeAdjacente(y,x,cote);
    if ( coordsAdjacente != null ) {
      int coordsAdjacenteY = coordsAdjacente.getY();
      int coordsAdjacenteX = coordsAdjacente.getX();
      return getCelluleSolution(coordsAdjacenteY,coordsAdjacenteX);
    }else {
      return null;
    }
  }

  /**
   * Méthode pour obtenir les coordonnées adjacente à une position et un côté donné
   *
   * @param y la position en y
   * @param x la position en x
   * @param cote le côté de la cellule
   * @return les coordonnées adjacente à la position (y,x) dans la direction du côté donné
   */
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

  /**
   * Méthode pour vérifier si une position est dans la grille
   *
   * @param y la position en y
   * @param x la position en x
   * @return vrai si la position est dans la grille, faux sinon
   */
  public boolean estDansGrille(int y, int x) {
    return y >= 0 && y < largeur && x >= 0 && x < longueur;
  }

  /**
   * Méthode pour obtenir une copie du puzzle (deep copy)
   *
   * @return une copie profonde du puzzle
   * @throws CloneNotSupportedException si le clonage n'est pas supporté
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    Puzzle nouveauPuzzle = (Puzzle) super.clone();
    nouveauPuzzle.grilleSolution = clonerMatriceCellule(grilleSolution);
    nouveauPuzzle.grilleJeu = clonerMatriceCellule(grilleJeu);
    return nouveauPuzzle;
  }

  /**
   * Méthode pour cloner une grille de cellules (deep copy)
   *
   * @param grille la grille de cellules à cloner
   * @return une copie profonde de la grille de cellules
   */
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

  /**
   * Méthode statique pour sauvegarder un puzzle
   *
   * @param puzzle le puzzle à sauvegarder
   * @param chemin le chemin où sauvegarder le puzzle
   */
  public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
      oos.writeObject(puzzle);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Méthode statique pour charger un puzzle
   *
   * @param chemin le chemin du fichier de sauvegarde du puzzle
   * @return le puzzle chargé
   */
  public static Puzzle chargerPuzzle(String chemin) {
    Puzzle puzzle = null;
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
      puzzle = (Puzzle) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println(chemin);
      e.printStackTrace();
      return null;
    }
    puzzle.genererGrillePropre();
    return puzzle;
  }
}
