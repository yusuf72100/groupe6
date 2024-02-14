package org.groupe6.slitherlink.PuzzleGenerator;

import org.groupe6.slitherlink.PuzzleGenerator.PartieInfos;
import org.groupe6.slitherlink.PuzzleGenerator.Puzzle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yamis
 */

// Classe CataloguePuzzle qui permet de gérer un catalogue de puzzles
public class CataloguePuzzle {

  // Catalogue des puzzles répartis selon la difficulté
  private Map<PartieInfos.DifficultePuzzle, List<Puzzle>> cataloguePuzzle;

  // Constructeur de la classe CataloguePuzzle
  public CataloguePuzzle() {
    cataloguePuzzle = new HashMap<PartieInfos.DifficultePuzzle, List<Puzzle>>();

    for (PartieInfos.DifficultePuzzle d : PartieInfos.DifficultePuzzle.values()) {
      cataloguePuzzle.put(d, new ArrayList<Puzzle>());
    }

  }

  // Méthode pour ajouter un puzzle au catalogue
  public void ajouterPuzzle(Puzzle puzzle) {
    cataloguePuzzle.get(puzzle.getInfoPartie().getDifficulte()).add(puzzle);
  }

  // Méthode pour obtenir la liste des puzzles selon la difficulté
  public List<Puzzle> getListePuzzle(PartieInfos.DifficultePuzzle difficulte) {
    return cataloguePuzzle.get(difficulte);
  }

  // Méthode pour obtenier la liste de tous les puzzles
  public List<Puzzle> getListePuzzle() {
    List<Puzzle> liste = new ArrayList<Puzzle>();

    for (PartieInfos.DifficultePuzzle d : PartieInfos.DifficultePuzzle.values()) {
      liste.addAll(cataloguePuzzle.get(d));
    }

    return liste;
  }

  // Méthode pour obtenir un puzzle du catalogue selon la difficulté
  public Puzzle getPuzzle(PartieInfos.DifficultePuzzle difficulte, int numero) {
    return cataloguePuzzle.get(difficulte).get(numero);
  }

  // Méthode pour charger un catalogue de puzzle
  static public CataloguePuzzle chargerCataloguePuzzle(String cheminDossier) {
    CataloguePuzzle catalogue = new CataloguePuzzle();

    File pathDossier = new File(cheminDossier);
    String contenu[] = pathDossier.list();

    for (int i = 0; i < contenu.length; i++) {
      if (contenu[i].endsWith(".puzzle")) {

        Puzzle puzzle = Puzzle.chargerPuzzle(cheminDossier + File.separator + contenu[i]);
        catalogue.ajouterPuzzle(puzzle);
      }
    }

    return catalogue;
  }

}
