package groupe6.model;

import groupe6.launcher.Launcher;

import java.io.File;
import java.util.*;

/**
 * Cette classe modélise un catalogue de puzzles
 *
 * @author Yamis
 */
public class CataloguePuzzle {

  /**
   * Catalogue des puzzles indexé par difficulté
   */
  private final Map<DifficultePuzzle, List<Puzzle>> cataloguePuzzle;

  /**
   * Constructeur de la classe CataloguePuzzle
   */
  public CataloguePuzzle() {
    cataloguePuzzle = new HashMap<DifficultePuzzle, List<Puzzle>>();

    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      cataloguePuzzle.put(d, new ArrayList<Puzzle>());
    }

  }

  /**
   * Méthode pour ajouter un puzzle au catalogue
   *
   * @param puzzle le puzzle à ajouter
   */
  public void ajouterPuzzle(Puzzle puzzle) {
    cataloguePuzzle.get(puzzle.getDifficulte()).add(puzzle);
  }

  /**
   * Méthode pour obtenir la liste des puzzles correspondant à une difficulté donnée
   *
   * @param difficulte la difficulté des puzzles à obtenir
   * @return la liste des puzzles de la difficulté donnée
   */
  public List<Puzzle> getListePuzzle(DifficultePuzzle difficulte) {
    return cataloguePuzzle.get(difficulte);
  }

  /**
   * Méthode pour obtenir la liste de tous les puzzles du catalogue
   *
   * @return la liste de tous les puzzles du catalogue
   */
  public List<Puzzle> getListePuzzle() {
    List<Puzzle> liste = new ArrayList<Puzzle>();

    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      liste.addAll(cataloguePuzzle.get(d));
    }

    return liste;
  }

  /**
   * Méthode pour obtenir un puzzle à partir de sa difficulté et de son numéro
   *
   * @param difficulte la difficulté du puzzle que l'on veut obtenir
   * @param numero le numéro du puzzle parmi ceux de la difficulté donnée
   * @return le puzzle correspondant à la difficulté et au numéro donnés
   */
  public Puzzle getPuzzle(DifficultePuzzle difficulte, int numero) {
    return cataloguePuzzle.get(difficulte).get(numero);
  }

  /**
   * Méthode pour obtenir une copie d'un puzzle à partir de sa difficulté et de son numéro
   *
   * @param difficulte la difficulté du puzzle que l'on veut obtenir
   * @param numero le numéro du puzzle parmi ceux de la difficulté donnée
   * @return une copie du puzzle correspondant à la difficulté et au numéro donnés
   */
  public Puzzle getCopyPuzzle(DifficultePuzzle difficulte, int numero) {
    try {
      return (Puzzle) this.getPuzzle(difficulte, numero).clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
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
      strBuilder.append("=> Difficulté " + d + " : \n");
      for (Puzzle p : cataloguePuzzle.get(d)) {
        strBuilder.append("  - " + d.toString().toLowerCase() + "_" + p.getLargeur() + "x" + p.getLongueur() + "\n");
      }
    }

    return strBuilder.toString();
  }

  /**
   * Méthode pour trier le catalogue de puzzles par taille croissante dans chaque difficulté
   *
   * @param catalogue le catalogue de puzzles à trier
   */
  static public void trierCataloguePuzzle(CataloguePuzzle catalogue) {
    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      catalogue.getListePuzzle(d).sort(new Comparator<Puzzle>() {
        @Override
        public int compare(Puzzle p1, Puzzle p2) {
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
   * Méthode pour charger un catalogue de puzzles
   *
   * @return le catalogue de puzzles chargé
   */
  static public CataloguePuzzle chargerCataloguePuzzle() {
    CataloguePuzzle catalogue = new CataloguePuzzle();

    String cheminRessourcesPuzzles = Launcher.normaliserChemin(Launcher.dossierPuzzles);
    File pathDossier = new File(cheminRessourcesPuzzles);
    String[] contenu = pathDossier.list();

    if (contenu != null) {
      for (String s : contenu) {
        if (s.endsWith(".puzzle")) {
          Puzzle puzzle = Puzzle.chargerPuzzle(cheminRessourcesPuzzles + File.separator + s);
          catalogue.ajouterPuzzle(Objects.requireNonNull(puzzle));
        }
      }
    }

    return catalogue;
  }

}
