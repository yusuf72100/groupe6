package groupe6.model;

import groupe6.launcher.Launcher;
import groupe6.model.Puzzle;

import java.io.File;
import java.util.*;

/**
 * Cette classe modélise un catalogue de puzzles
 *
 * @author Yamis
 */

// Classe CataloguePuzzle qui permet de gérer un catalogue de puzzles
public class CataloguePuzzle {

  // Catalogue des puzzles répartis selon la difficulté
  private final Map<DifficultePuzzle, List<Puzzle>> cataloguePuzzle;

  // Constructeur de la classe CataloguePuzzle
  public CataloguePuzzle() {
    cataloguePuzzle = new HashMap<DifficultePuzzle, List<Puzzle>>();

    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      cataloguePuzzle.put(d, new ArrayList<Puzzle>());
    }

  }

  // Méthode pour ajouter un puzzle au catalogue
  public void ajouterPuzzle(Puzzle puzzle) {
    cataloguePuzzle.get(puzzle.getDifficulte()).add(puzzle);
  }

  // Méthode pour obtenir la liste des puzzles selon la difficulté
  public List<Puzzle> getListePuzzle(DifficultePuzzle difficulte) {
    return cataloguePuzzle.get(difficulte);
  }

  // Méthode pour obtenier la liste de tous les puzzles
  public List<Puzzle> getListePuzzle() {
    List<Puzzle> liste = new ArrayList<Puzzle>();

    for (DifficultePuzzle d : DifficultePuzzle.values()) {
      liste.addAll(cataloguePuzzle.get(d));
    }

    return liste;
  }

  // Méthode pour obtenir un puzzle du catalogue selon la difficulté
  public Puzzle getPuzzle(DifficultePuzzle difficulte, int numero) {
    return cataloguePuzzle.get(difficulte).get(numero);
  }

  public Puzzle getCopyPuzzle(DifficultePuzzle difficulte, int numero) {
    try {
      return (Puzzle) this.getPuzzle(difficulte, numero).clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  // Méthode pour afficher le catalogue de puzzles
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

  // Trile le catalogue des puzzles dans chaque difficulté selon la taille de la grille
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

  // Méthode pour charger un catalogue de puzzle
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
