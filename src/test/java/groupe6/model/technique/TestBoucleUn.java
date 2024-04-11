package groupe6.model.technique;

import groupe6.ModelTest;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.TestPuzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.profil.Profil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import groupe6.model.partie.aide.AideInfos;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoucleUn extends ModelTest{
    private static Puzzle puzzle;
    private static Puzzle puzzleTrue;
    private static BoucleAu1 techniqueBoucleUn;
    private static Partie partie;
    private static Partie partieTrue;
    private static Profil profil;

    //@BeforeAll
    public static void initAll(){
        ModelTest.afficherNomDebut(TestPuzzle.class);
        int largeur3 = 3;
        int longueur3 = 3;

        Cellule[][] grille = new Cellule[largeur3][longueur3];
        Cellule[][] grilleTrue = new Cellule[largeur3][longueur3];
        // Haut,        Bas,        Gauche,         Droite

        // Grille qui n'a pas la technique applicable

        grille[0][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});
        grille[0][1] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});
        grille[0][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});

        grille[1][0] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});
        grille[1][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grille[1][2] = new Cellule(1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grille[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});
        grille[2][1] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});
        grille[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.VIDE});

        // Grille qui a une technique applicable

        grilleTrue[0][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});
        grilleTrue[0][1] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.VIDE});
        grilleTrue[0][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});

        grilleTrue[1][0] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});
        grilleTrue[1][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleTrue[1][2] = new Cellule(1, new ValeurCote[]{ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleTrue[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE});
        grilleTrue[2][1] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.TRAIT});
        grilleTrue[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.TRAIT, ValeurCote.VIDE});

        techniqueBoucleUn = new BoucleAu1();

        profil = new Profil("Test", null);

        puzzle = new Puzzle(largeur3, longueur3, grille, grille, DifficultePuzzle.FACILE);
        partie = new Partie(puzzle, ModeJeu.CLASSIQUE, profil);

        puzzleTrue = new Puzzle(largeur3, longueur3, grilleTrue, grilleTrue, DifficultePuzzle.FACILE);
        partieTrue = new Partie(puzzleTrue, ModeJeu.CLASSIQUE, profil);
    }

    //@Test
    public void testDetectionBoucleUnFalse(){
        ResultatTechnique resultat = techniqueBoucleUn.run(partie, 4);
        assertFalse(resultat.isTechniqueTrouvee());
    }

    //@Test
    public void testDetectionBoucleUnTrue(){
        ResultatTechnique resultatTrue = techniqueBoucleUn.run(partieTrue, 4);
        assertTrue(resultatTrue.isTechniqueTrouvee());
        partieTrue.getHistoriqueAide().ajouterAide(new AideInfos(resultatTrue));
        assertTrue(partieTrue.getHistoriqueAide().aideDejaPresente(resultatTrue));
    }

    //@Test
    public void testDetectionBoucleUnDejaPresente(){
        ResultatTechnique resultat = techniqueBoucleUn.run(partieTrue, 4);
        assertFalse(resultat.isTechniqueTrouvee());
    }
}
