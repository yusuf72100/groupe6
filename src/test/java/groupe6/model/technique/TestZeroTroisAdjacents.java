package groupe6.model.technique;

import groupe6.ModelTest;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.TestPuzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.partie.Partie;
import groupe6.model.profil.Profil;
import javafx.scene.control.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import groupe6.model.partie.puzzle.Puzzle;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestZeroTroisAdjacents extends ModelTest {
    private static Puzzle puzzle;
    private static zeroTroisAdjacents techniqueZeroTroisAdjacents;
    private static Partie partie;
    private static Profil profil;

    @BeforeAll
    public static void initAll(){
        int largeur3 = 3;
        int longueur3 = 3;

        Cellule[][] grille = new Cellule[largeur3][longueur3];
        // Haut,        Bas,        Gauche,         Droite
        grille[0][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.TRAIT});
        grille[0][1] = new Cellule(3, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT});
        grille[0][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.VIDE});

        grille[1][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grille[1][1] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grille[1][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grille[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grille[2][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grille[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        techniqueZeroTroisAdjacents = new zeroTroisAdjacents(DifficulteTechnique.BASIQUE);

        puzzle = new Puzzle(largeur3, longueur3, grille, grille, DifficultePuzzle.FACILE);

        profil = new Profil("Test", null);

        partie = new Partie(puzzle, ModeJeu.NORMAL, profil);
    }

    @Test
    public void testDetectionZeroTroisAdjacents(){
        ResultatTechnique resultat = techniqueZeroTroisAdjacents.run(partie, 3);
        assertFalse(resultat.isTechniqueTrouvee());
    }
}
