package groupe6.model.partie.puzzle;

import groupe6.launcher.Launcher;
import groupe6.model.profil.Profil;

import java.io.File;
import java.util.*;

/**
 * Cette classe modélise un catalogue de puzzles
 *
 * @author Yamis
 */
public class CataloguePuzzle {

  /**
   * Catalogue des sauvegardes de puzzles indexées par difficulté
   */
  private final Map<DifficultePuzzle, List<PuzzleSauvegarde>> cataloguePuzzle;

  /**
   * Constructeur de la classe CataloguePuzzle
   */
  public CataloguePuzzle() {
    cataloguePuzzle = new HashMap<DifficultePuzzle, List<PuzzleSauvegarde>>();

    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      cataloguePuzzle.put(d, new ArrayList<PuzzleSauvegarde>());
    }

  }

  /**
   * Méthode pour ajouter une sauvegarde de puzzle au catalogue
   *
   * @param puzzle la sauvegarde de puzzle à ajouter
   */
  public void ajouterPuzzle(PuzzleSauvegarde puzzle) {
    cataloguePuzzle.get(puzzle.getDifficulte()).add(puzzle);
  }

  /**
   * Méthode pour obtenir la liste des puzzles correspondant à une difficulté donnée
   *
   * @param difficulte la difficulté des puzzles à obtenir
   * @return la liste des puzzles de la difficulté donnée
   */
  public List<PuzzleSauvegarde> getListePuzzle(DifficultePuzzle difficulte) {
    return cataloguePuzzle.get(difficulte);
  }

  /**
   * Méthode pour obtenir une sauvegarde de puzzle depuis le catalogue
   *
   * @param difficulte la difficulté de la sauvegarde de puzzle à obtenir
   * @param numero le numéro de la sauvegarde de puzzle à obtenir dans la liste des puzzles de la difficulté donnée
   * @return la sauvegarde de puzzle correspondant à la difficulté et au numéro donnés
   */
  public PuzzleSauvegarde getPuzzleSauvegarde(DifficultePuzzle difficulte, int numero) {
    return this.cataloguePuzzle.get(difficulte).get(numero);
  }

  /**
   * Méthode pour obtenir le nombre de puzzles disponibles pour une difficulté donnée
   *
   * @param difficulte la difficulté des puzzles à compter
   * @return le nombre de puzzles disponibles pour la difficulté donnée
   */
  public int getNombrePuzzleParDifficulte(DifficultePuzzle difficulte) {
    return this.cataloguePuzzle.get(difficulte).size();
  }

  /**
   * Méthode pour obtenir un nouveau puzzle depuis le catalogue
   *
   * @param difficulte la difficulté du puzzle à obtenir
   * @param numero le numéro du puzzle à obtenir dans la liste des puzzles de la difficulté donnée
   * @param optionTechDemarrage le boolén qui indique si l'application des techniques de démarrage est activée
   * @return le nouveau puzzle obtenu
   */
  public Puzzle getNouveauPuzzle(DifficultePuzzle difficulte, int numero, boolean optionTechDemarrage) {
    PuzzleSauvegarde puzzleSauvegarde = getPuzzleSauvegarde(difficulte, numero);
    return new Puzzle(puzzleSauvegarde, optionTechDemarrage, numero);
  }

  /**
   * Méthode pour obtenir une représentation textuelle du catalogue de puzzles
   *
   * @return la représentation textuelle du catalogue de puzzles
   */
  public String toString() {
    StringBuilder strBuilder = new StringBuilder();

    // Affichage pôur chaque puzzle de chaque difficulté sa difficulté et sa taille
    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      strBuilder.append("=> Difficulté ");
      strBuilder.append(d);
      strBuilder.append(" : \n");
      for (PuzzleSauvegarde p : cataloguePuzzle.get(d)) {
        strBuilder.append("  - ");
        strBuilder.append(p.getDifficulte().toString().toUpperCase());
        strBuilder.append("_");
        strBuilder.append(p.getLargeur());
        strBuilder.append("x");
        strBuilder.append(p.getLongueur());
        strBuilder.append("\n");
      }
    }

    return strBuilder.toString();
  }

  /**
   * Méthode pour savoir si un puzzle est verrouillé
   *
   * @param i l'indice qui représente la difficulté du puzzle
   * @param j le numéro du puzzle
   * @param profil le profil du joueur
   * @return vrai si le puzzle est déverrouillé, faux sinon
   */
  public boolean puzzleEstDeverrouille(int i, int j,Profil profil) {
    if ( i < 0 || i >= DifficultePuzzle.values().length || j < 0 || j >= cataloguePuzzle.get(DifficultePuzzle.values()[i]).size()) {
      throw new IllegalArgumentException("Index de puzzle invalide");
    }
    int sum = 0;
    for (int k = 0; k < i ; k++) {
      sum += cataloguePuzzle.get(DifficultePuzzle.values()[k]).size();
    }
    return (sum + j) <= profil.getNiveauAventure();
  }

  /**
   * Méthode pour savoir si un puzzle est le dernier puzzle déverrouillé
   *
   * @param i l'indice qui représente la difficulté du puzzle
   * @param j le numéro du puzzle
   * @param profil le profil du joueur
   * @return vrai si le puzzle est le dernier puzzle déverrouillé, faux sinon
   */
  public boolean estDernierPuzzleDeverrouille(int i, int j, Profil profil) {
    if ( i < 0 || i >= DifficultePuzzle.values().length || j < 0 || j >= cataloguePuzzle.get(DifficultePuzzle.values()[i]).size()) {
      throw new IllegalArgumentException("Index de puzzle invalide");
    }
    int sum = 0;
    for (int k = 0; k < i ; k++) {
      sum += cataloguePuzzle.get(DifficultePuzzle.values()[k]).size();
    }
    return (sum + j) == profil.getNiveauAventure();
  }

  /**
   * Méthode pour trier le catalogue de sauvegardes de puzzles par taille croissante dans chaque difficulté
   *
   * @param catalogue le catalogue de sauvegardes de puzzles à trier
   */
  static public void trierCataloguePuzzle(CataloguePuzzle catalogue) {
    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      catalogue.getListePuzzle(d).sort(new Comparator<PuzzleSauvegarde>() {
        @Override
        public int compare(PuzzleSauvegarde p1, PuzzleSauvegarde p2) {
          int tailleP1 = p1.getLongueur() * p1.getLargeur();
          int tailleP2 = p2.getLongueur() * p2.getLargeur();
          if (tailleP1 < tailleP2) {
            return -1;
          } else if (tailleP1 > tailleP2) {
            return 1;
          } else {
            return 0;
          }
        }
      });
    }
  }

  /**
   * Méthode pour obtenir le nom d'une sauvegarde de puzzle
   *
   * @param puzzle la sauvegarde de puzzle dont on veut obtenir le nom
   * @return le nom de la sauvegarde de puzzle
   */
  public static String getPuzzleName(PuzzleSauvegarde puzzle) {
    return puzzle.getDifficulte().toString().toUpperCase() + "_" + puzzle.getLargeur() + "x" + puzzle.getLongueur();
  }

  /**
   * Méthode pour obtenir le nom d'un puzzle
   *
   * @param puzzle le puzzle dont on veut obtenir le nom
   * @return le nom du puzzle
   */
  public static String getPuzzleName(Puzzle puzzle) {
    return puzzle.getDifficulte().toString().toUpperCase() + "_" + puzzle.getLargeur() + "x" + puzzle.getLongueur();
  }

  /**
   * Méthode pour charger un catalogue de puzzles
   *
   * @return le catalogue de puzzles chargé
   */
  static public CataloguePuzzle chargerCataloguePuzzle() {
    CataloguePuzzle catalogue = new CataloguePuzzle();

    // Chemin du dossier de ressources des puzzles
    String cheminRessourcesPuzzles = Launcher.normaliserChemin(Launcher.dossierPuzzles);
    File pathDossier = new File(cheminRessourcesPuzzles);
    String[] contenu = pathDossier.list();

    // Chargement des sauvegardes de puzzles depuis le dossier de ressources
    if (contenu != null) {
      for (String s : contenu) {
        if (s.endsWith(".puzzle")) {
          PuzzleSauvegarde puzzleSauvegarde = PuzzleSauvegarde.chargerPuzzleSauvegarde(cheminRessourcesPuzzles + File.separator + s);
          catalogue.ajouterPuzzle(Objects.requireNonNull(puzzleSauvegarde));
        }
      }
    }

    // Trié le catalogue de puzzles (par taille croissante dans chaque difficulté)
    trierCataloguePuzzle(catalogue);

    return catalogue;
  }

}
