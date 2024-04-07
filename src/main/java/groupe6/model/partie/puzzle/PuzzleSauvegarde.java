package groupe6.model.partie.puzzle;

import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe qui permet de stocker les informations d'un puzzle
 *
 * @author Yamis
 */
public class PuzzleSauvegarde implements Serializable {

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
   * La difficulté du puzzle
   */
  private final DifficultePuzzle difficulte;

  /**
   * La grille avec la solution du puzzle
   */
  private Cellule[][] grilleSolution;

  /**
   * La grille vide du puzzle
   */
  private Cellule[][] grilleVide;

  /**
   * La grille avec les techniques de départ déja remplies
   */
  private Cellule[][] grilleDepart;


  /**
   * Constructeur de la classe PuzzleSauvegarde
   *
   * @param largeur la largeur du puzzle
   * @param longueur la longueur du puzzle
   * @param grilleSolution la grille avec la solution du puzzle
   * @param grilleVide la grille vide du puzzle
   * @param grilleDepart la grille avec les techniques de départ déja remplies
   * @param difficulte la difficulté du puzzle
   */
  public PuzzleSauvegarde(int largeur, int longueur, Cellule[][] grilleSolution, Cellule[][] grilleVide, Cellule[][] grilleDepart, DifficultePuzzle difficulte) {

    if (grilleSolution.length != largeur || grilleSolution[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille Solution ne correspond pas à la largeur et la longueur");
    }

    if (grilleVide.length != largeur || grilleVide[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille Vide ne correspond pas à la largeur et la longueur");
    }

    if (grilleDepart.length != largeur || grilleDepart[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille Depart ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.difficulte = difficulte;
    this.grilleSolution = grilleSolution;
    this.grilleVide = grilleVide;
    this.grilleDepart = grilleDepart;
  }

  /**
   * Constructeur de la classe PuzzleSauvegarde ( grilleSollution vide, grilleVide null, grilleDepart null)
   *
   * @param largeur la largeur du puzzle
   * @param longueur la longueur du puzzle
   * @param difficulte la difficulté du puzzle
   */
  public PuzzleSauvegarde(int largeur, int longueur, DifficultePuzzle difficulte) {
    this.largeur = largeur;
    this.longueur = longueur;
    this.difficulte = difficulte;
    this.grilleSolution = initGrilleSolution();
    this.grilleVide = null;
    this.grilleDepart = null;
  }

  /**
   * Méthode pour initialiser une grille avec des cellules vides ( -1, ValeurCote.VIDE )
   *
   * @return la grille initialisée avec des cellules vides
   */
  private Cellule[][] initGrilleSolution() {
    Cellule[][] grille = new Cellule[this.largeur][this.longueur];
    for (int i = 0; i < this.largeur; i++) {
      for (int j = 0; j < this.longueur; j++) {
        grille[i][j] = new Cellule(-1,new ValeurCote[]{ValeurCote.VIDE,ValeurCote.VIDE,ValeurCote.VIDE,ValeurCote.VIDE});
      }
    }
    return grille;
  }

  /**
   * Méthode pour générer une grille propre à partir de la grille solution
   */
  public void genererGrillePropre() {
    this.grilleVide = new Cellule[largeur][longueur];
    for (int y = 0; y < grilleSolution.length; y++) {
      for (int x = 0; x < grilleSolution[y].length; x++) {
        ValeurCote[] cotesVide = new ValeurCote[4];
        Arrays.fill(cotesVide, ValeurCote.VIDE);
        this.grilleVide[y][x] = new Cellule(grilleSolution[y][x].getValeur(), cotesVide);
      }
    }
  }

  /**
   * Méthode pour modifier la valeur d'une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à modifier
   * @param valeur la nouvelle valeur de la cellule
   */
  public void modifierValeurCelluleGrille(int y, int x, Cellule[][] grille, int valeur) {
    if ( y < 0 || y >= grille.length || x < 0 || x >= grille[0].length) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    grille[y][x].setValeur(valeur);
  }

  /**
   * Méthode pour modifier un cote d'une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à modifier
   * @param cote le cote à modifier
   * @param valeur la nouvelle valeur du cote
   */
  public void modifierCoteCelluleGrille(int y, int x, Cellule[][] grille, int cote, ValeurCote valeur) {
    if ( y < 0 || y >= grille.length || x < 0 || x >= grille[0].length) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    if ( cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }

    grille[y][x].setCote(cote, valeur);
  }

  /**
   * Méthode pour obtenir la largeur du puzzle
   *
   * @return la largeur du puzzle
   */
  public int getLargeur() {
    return largeur;
  }

  /**
   * Méthode pour obtenir la longueur du puzzle
   *
   * @return la longueur du puzzle
   */
  public int getLongueur() {
    return longueur;
  }

  /**
   * Méthode pour obtenir la grille avec la solution du puzzle
   *
   * @return la grille avec la solution du puzzle
   */
  public Cellule[][] getGrilleSolution() {
    return grilleSolution;
  }

  public Cellule getCelluleGrilleSolution(int y, int x) { return grilleSolution[y][x]; }

  public Cellule getCelluleAdjacenteSolution(int y, int x, int cote) {
    Coordonnee coordsAdjacente = getCoordoneeAdjacente(y,x,cote);
    if ( coordsAdjacente != null ) {
      int coordsAdjacenteY = coordsAdjacente.getY();
      int coordsAdjacenteX = coordsAdjacente.getX();
      return getCelluleGrilleSolution(coordsAdjacenteY,coordsAdjacenteX);
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

  /**
   * Méthode pour obtenir la grille vide du puzzle
   *
   * @return la grille vide du puzzle
   */
  public Cellule[][] getGrilleVide() {
    return grilleVide;
  }

  /**
   * Méthode pour obtenir la grille avec les techniques de départ déja remplies
   *
   * @return la grille avec les techniques de départ déja remplies
   */
  public Cellule[][] getGrilleDepart() {
    return grilleDepart;
  }

  /**
   * Méthode pour obtenir la difficulté du puzzle
   *
   * @return la difficulté du puzzle
   */
  public DifficultePuzzle getDifficulte() {
    return difficulte;
  }
}
