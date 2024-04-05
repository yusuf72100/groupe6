package groupe6.test;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.Puzzle;

/**
 * Classe de test pour les puzzles
 *
 * @author Yamis
 */
public class TestPuzzle {
    public static void main(String[] args) {
        String cheminPuzzle = Launcher.normaliserChemin(Launcher.dossierPuzzles + "puzzle/test.puzzle");
        Puzzle p = Puzzle.chargerPuzzle(cheminPuzzle);

        System.out.println(p);
    }
}
