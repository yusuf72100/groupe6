package groupe6.test;

import groupe6.model.Puzzle;

public class testPuzzle {
    public static void main(String[] args) {
        Puzzle p = Puzzle.chargerPuzzle("Slitherlink/puzzle/test.puzzle");

        System.out.println(p);
    }
}
