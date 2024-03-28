package groupe6.test;

import groupe6.model.Chronometre;
import groupe6.model.Puzzle;

/**
 * Classe de test qui est lancer avec le paramètre --test
 *
 * @author Yamis
 */
public class TestMain {
    public static void main(String[] args) {

        //TestPartie.main(args);

        Puzzle puzzle = Puzzle.chargerPuzzle("Slitherlink/puzzles/FACILE_6x6_F.puzzle");
        puzzle.genererGrillePropre();
        Puzzle.sauvegarderPuzzle(puzzle, "Slitherlink/puzzles/FACILE_6x6.puzzle");
    }
}
