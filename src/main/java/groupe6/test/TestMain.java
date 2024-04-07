package groupe6.test;

import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

/**
 * Classe de test qui est lancer avec le paramètre --test
 */
public class TestMain {

    /**
     * Méthode main qui lance les tests
     *
     * @param args les arguments
     */
    public static void main(String[] args) {

//        TestProfils.main(args);

//        Puzzle puzzle = Puzzle.chargerPuzzle("Slitherlink/puzzles/FACILE_6x6_F.puzzle");
//        puzzle.genererGrillePropre();
//        Puzzle.sauvegarderPuzzle(puzzle, "Slitherlink/puzzles/FACILE_6x6.puzzle");

        try {
            System.out.println("==== Test Création & Sauvegarde de SauvegardePuzzle ====");

            Puzzle puzzle = Puzzle.chargerPuzzle("Slitherlink/test/FACILE_6x6.puzzle");
            System.out.println("-- Puzzle chargé --");
            System.out.println(puzzle.toString());


            System.out.println("-- PuzzleSauvegarde créé --");
            PuzzleSauvegarde puzzleSauvegarde = new PuzzleSauvegarde(
                puzzle.getLargeur(),
                puzzle.getLongueur(),
                puzzle.getDifficulte(),
                Cellule.clonerMatriceCellule(puzzle.getGrilleSolution()),
                Cellule.clonerMatriceCellule(puzzle.getGrilleJeu()),
                Cellule.clonerMatriceCellule(puzzle.getGrilleJeu())
            );

           for (int i = 0; i < 4; i++) {
               puzzleSauvegarde.modifierValeurCoteCelluleEtAdjGrille(
                   4,
                   1,
                   i,
                   puzzleSauvegarde.getGrilleTechDemarrage(),
                   ValeurCote.CROIX
               );
           }

            System.out.println(puzzleSauvegarde.toString());

            PuzzleSauvegarde.sauvegarderPuzzleSauvegarde(puzzleSauvegarde, "Slitherlink/puzzles/FACILE_6x6_T.puzzle");

            System.out.println("==== Test Chargement de SauvegardePuzzle ====");

            PuzzleSauvegarde puzzleSauvegardeCharge = PuzzleSauvegarde.chargerPuzzleSauvegarde("Slitherlink/puzzles/FACILE_6x6_T.puzzle");

            System.out.println(puzzleSauvegardeCharge.toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
