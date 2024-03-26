package groupe6.test;

import groupe6.model.Puzzle;

/**
 * Classe de test pour les puzzles
 *
 * @author Yamis
 */
public class TestPuzzle {
    public static void main(String[] args) {
        Puzzle p = Puzzle.chargerPuzzle("Slitherlink/puzzle/test.puzzle");

        System.out.println(p);
    }
}
