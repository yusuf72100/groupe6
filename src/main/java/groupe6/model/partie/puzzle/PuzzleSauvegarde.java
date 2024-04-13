package groupe6.model.partie.puzzle;

import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.io.*;
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
   * La largeur du puzzle (nombre de lignes)
   */
  private final int largeur;

  /**
   * La longueur du puzzle (nombre de colonnes)
   */
  private final int longueur;

  /**
   * La difficulté du puzzle
   */
  private final DifficultePuzzle difficulte;

  /**
   * La grille avec la solution du puzzle
   */
  private final Cellule[][] grilleSolution;

  /**
   * La grille vide du puzzle
   */
  private Cellule[][] grilleVide;

  /**
   * La grille avec les techniques de départ déja remplies
   */
  private Cellule[][] grilleTechDemarrage;


  /**
   * Constructeur de la classe PuzzleSauvegarde
   *
   * @param largeur la largeur du puzzle
   * @param longueur la longueur du puzzle
   * @param grilleSolution la grille avec la solution du puzzle
   * @param grilleVide la grille vide du puzzle
   * @param grilleTechDemarrage la grille avec les techniques de démarrage déja remplies
   * @param difficulte la difficulté du puzzle
   */
  public PuzzleSauvegarde(int largeur, int longueur, DifficultePuzzle difficulte, Cellule[][] grilleSolution, Cellule[][] grilleVide, Cellule[][] grilleTechDemarrage) {

    if (grilleSolution.length != largeur || grilleSolution[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille Solution ne correspond pas à la largeur et la longueur");
    }

    if (grilleVide.length != largeur || grilleVide[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille Vide ne correspond pas à la largeur et la longueur");
    }

    if (grilleTechDemarrage.length != largeur || grilleTechDemarrage[0].length != longueur) {
      throw new IllegalArgumentException("La taille de la grille grilleTechDemmarage  ne correspond pas à la largeur et la longueur");
    }

    this.largeur = largeur;
    this.longueur = longueur;
    this.difficulte = difficulte;
    this.grilleSolution = grilleSolution;
    this.grilleVide = grilleVide;
    this.grilleTechDemarrage = grilleTechDemarrage;
  }

  /**
   * Constructeur de la classe PuzzleSauvegarde ( grilleSollution vide, grilleVide null, grilleTechDemmarage null)
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
    this.grilleTechDemarrage = null;
  }

  /**
   * Méthode pour initialiser une grille avec des cellules vides (-1, ValeurCote.VIDE)
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
   * Méthode pour générer la grille avec les techniques de départ déja remplies depuis la grille vide
   */
  public void genererGrilleTechDemarrage() {
    this.grilleTechDemarrage = Cellule.clonerMatriceCellule(this.grilleVide);
  }

  /**
   * Méthode pour vérifier si une coordonnée est dans la grille
   *
   * @param y la position en y
   * @param x la position en x
   * @return vrai si la coordonnée est dans la grille, faux sinon
   */
  public boolean estDansGrille(int y, int x) {
    return y >= 0 && y < largeur && x >= 0 && x < longueur;
  }

  /**
   * Méthode pour obtenir la valeur d'un côté d'une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à lire
   * @param cote le côté à lire
   * @return la valeur du côté
   */
  public ValeurCote getCoteCelluleGrille(int y, int x, Cellule[][] grille, int cote) {
    if (!estDansGrille(y, x)) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    return grille[y][x].getCote(cote);
  }

  /**
   * Méthode pour obtenir les cotes d'une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à lire
   * @return les cotes de la cellule
   */
  public ValeurCote[] getCotesCelluleGrille(int y, int x, Cellule[][] grille) {
    if (!estDansGrille(y, x)) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    return grille[y][x].getCotes();
  }

  /**
   * Méthode pour modifier un côté d'une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à modifier
   * @param cote le côté à modifier
   * @param valeur la nouvelle valeur du côté
   */
  public void modifierCoteCelluleGrille(int y, int x, int cote, Cellule[][] grille, ValeurCote valeur) {
    if ( !estDansGrille(y,x) ) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    if ( cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }

    grille[y][x].setCote(cote, valeur);
  }

  /**
   * Méthode pour modifier la valeur d'un côté d'une cellule et de sa cellule adjacente dans une grille
   *
   * @param y la position en y
   * @param x la position en x
   * @param cote le côté à modifier
   * @param grille la grille à modifier
   * @param valeur la nouvelle valeur du côté
   */
  public void modifierValeurCoteCelluleEtAdjGrille(int y, int x, int cote, Cellule[][] grille, ValeurCote valeur) {
    if ( !estDansGrille(y,x) ) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    if ( cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }

    grille[y][x].setCote(cote, valeur);
    Cellule celluleAdj = getCelluleAdjacentGrille(y,x,cote,grille);
    if (celluleAdj != null) {
      int coteAdj = Cellule.getCoteAdjacent(cote);
      celluleAdj.setCote(coteAdj, valeur);
    }
  }

  /**
   * Méthode pour obtenir une cellule d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param grille la grille à lire
   * @return la cellule
   */
  public Cellule getCelluleGrille(int y, int x, Cellule[][] grille) {
    if ( !estDansGrille(y,x) ) {
      throw new IllegalArgumentException("Les coordonnées de la cellule ne sont pas valides");
    }
    return grille[y][x];
  }

  /**
   * Méthode pour obtenir une cellule adjacente d'une grille
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param cote le cote de la cellule
   * @param grille la grille à lire
   * @return la cellule adjacente
   */
  public Cellule getCelluleAdjacentGrille(int y, int x, int cote, Cellule[][] grille) {
    if (cote < 0 || cote > 3) {
      throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }
    int yAdj;
    int xAdj;
    switch (cote) {
      // Haut = y-1
      case Cellule.HAUT:
        yAdj = y - 1;
        xAdj = x;
        break;
      // Bas = y+1
      case Cellule.BAS:
        yAdj = y + 1;
        xAdj = x;
        break;
      // Gauche = x-1
      case Cellule.GAUCHE:
        yAdj = y;
        xAdj = x - 1;
        break;
      // Droite = x+1
      case Cellule.DROITE:
        yAdj = y;
        xAdj = x + 1;
        break;
      default:
        throw new IllegalArgumentException("Le cote doit être entre 0 et 3");
    }

    if ( estDansGrille(yAdj, xAdj) ) {
      return grille[yAdj][xAdj];
    }
    return null;
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

  /**
   * Méthode pour obtenir une cellule de la grille solution
   *
   * @param y la position en y
   * @param x la position en x
   * @return la cellule de la grille solution
   */
  public Cellule getCelluleGrilleSolution(int y, int x) { return grilleSolution[y][x]; }

  /**
   * Méthode pour obtenir la cellule adjacente à une position et un côté donné
   *
   * @param y la position en y
   * @param x la position en x
   * @param cote le côté de la cellule
   * @return la cellule adjacente à la position (y,x) dans la direction du côté donné
   */
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
   * @return les coordonnées adjacentes à la position (y,x) dans la direction du côté donné
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
      return new Coordonnee(yAdj, xAdj);
    } else {
      return null;
    }
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
  public Cellule[][] getGrilleTechDemarrage() {
    return this.grilleTechDemarrage;
  }

  /**
   * Méthode pour obtenir la difficulté du puzzle
   *
   * @return la difficulté du puzzle
   */
  public DifficultePuzzle getDifficulte() {
    return difficulte;
  }

  /**
   * Méthode pour obtenir la représentation textuelle d'une sauvegarde de puzzle
   *
   * @return la représentation textuelle de la sauvegarde de puzzle
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
    strBuilder.append(Cellule.grilleTostring(this.largeur,this.longueur,grilleSolution));
    strBuilder.append("\nGrille Vide : \n");
    strBuilder.append(Cellule.grilleTostring(this.largeur,this.longueur,grilleVide));
    strBuilder.append("\nGrille Départ : \n");
    strBuilder.append(Cellule.grilleTostring(this.largeur,this.longueur, grilleTechDemarrage));
    strBuilder.append("\n");

    return strBuilder.toString();
  }

  /**
   * Méthode statique pour sauvegarder une sauvegarde de puzzle
   *
   * @param puzzleSauvegarde la sauvegarde de puzzle à sauvegarder
   * @param chemin le chemin du fichier de sauvegarde créé
   */
  public static void sauvegarderPuzzleSauvegarde(PuzzleSauvegarde puzzleSauvegarde, String chemin) {
    try {
      FileOutputStream fileOut = new FileOutputStream(chemin);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(puzzleSauvegarde);
      out.close();
      fileOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Méthode statique pour charger un puzzle
   *
   * @param chemin le chemin du fichier de sauvegarde
   * @return la sauvegarde de puzzle chargée
   */
  public static PuzzleSauvegarde chargerPuzzleSauvegarde(String chemin) {
    PuzzleSauvegarde puzzleSauvegarde = null;
    try {
      FileInputStream fileIn = new FileInputStream(chemin);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      puzzleSauvegarde = (PuzzleSauvegarde) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return puzzleSauvegarde;
  }
}
