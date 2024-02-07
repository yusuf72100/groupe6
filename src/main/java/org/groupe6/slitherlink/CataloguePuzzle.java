package org.groupe6.slitherlink;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

// Classe CataloguePuzzle qui permet de gérer un catalogue de puzzles
public class CataloguePuzzle {

  // Catalogue des puzzles répartis selon la difficulté
  private HashMap<Puzzle.DifficultePuzzle, ArrayList<Puzzle>> listePuzzle;

  // Constructeur de la classe CataloguePuzzle
  public CataloguePuzzle() {
    listePuzzle = new HashMap<Puzzle.DifficultePuzzle, ArrayList<Puzzle>>();

    for (Puzzle.DifficultePuzzle d : Puzzle.DifficultePuzzle.values()) {
      listePuzzle.put(d, new ArrayList<Puzzle>());
    }

  }

  // Méthode pour ajouter un puzzle au catalogue
  public void ajouterPuzzle(Puzzle puzzle) {
    listePuzzle.get(puzzle.getDifficulte()).add(puzzle);
  }

  // Méthode pour obtenir la liste des puzzles selon la difficulté
  public ArrayList<Puzzle> getListePuzzle(Puzzle.DifficultePuzzle difficulte) {
    return listePuzzle.get(difficulte);
  }

  // Méthode pour obtenier la liste de tous les puzzles
  public ArrayList<Puzzle> getListePuzzle() {
    ArrayList<Puzzle> liste = new ArrayList<Puzzle>();

    for (Puzzle.DifficultePuzzle d : Puzzle.DifficultePuzzle.values()) {
      liste.addAll(listePuzzle.get(d));
    }

    return liste;
  }

  // Méthode pour obtenir un puzzle du catalogue selon la difficulté
  public Puzzle getPuzzle(Puzzle.DifficultePuzzle difficulte, int numero) {
    return listePuzzle.get(difficulte).get(numero);
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
