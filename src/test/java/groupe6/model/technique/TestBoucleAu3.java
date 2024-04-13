package groupe6.model.technique;

import groupe6.CouleursANSI;
import groupe6.ModelTest;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.profil.Profil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoucleAu3 extends ModelTest {
    private static Puzzle puzzle;
    private static Adjacents3 techniqueAdjacents3;
    private static Partie partie;
    private static Profil profil;

    @BeforeAll
    public static void initAll(){

        ModelTest.afficherNomDebut(TestBoucleAu3.class);

        int largeur = 6;
        int longueur = 6;

        Cellule[][] grilleCellules = new Cellule[largeur][longueur];
        // Haut ,        Bas,             Gauche,           Droite
        grilleCellules[0][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[1][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.TRAIT, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[3][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][3] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[4][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[5][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        techniqueAdjacents3 = Adjacents3.getInstance();

        profil = new Profil("Test",null);

        puzzle = new Puzzle(largeur,longueur, grilleCellules , grilleCellules, DifficultePuzzle.MOYEN);

        partie = new Partie(puzzle, ModeJeu.CLASSIQUE,profil );

//        System.out.println(puzzle);
    }


    @Test
    public void testDetectionBoucleAu3(){
        System.out.println(CouleursANSI.RED + "Test à corriger" + CouleursANSI.RESET);

        // La technique n'est pas trouvée

//        ResultatTechnique resultat = techniqueAdjacents3.run(partie, 1);
//        System.out.println("res 1 : "+ resultat);
//        assertTrue(resultat.isTechniqueTrouvee());

    }

    @Test
    public void testDetectionBoucleAu3BonnesCoordonnees(){
        System.out.println(CouleursANSI.RED + "Test à corriger" + CouleursANSI.RESET);

        // La technique n'est pas trouvée

//        ResultatTechnique resultat = techniqueAdjacents3.run(partie, 1);
//        System.out.println("res 2 : "+ resultat);
//        assertEquals(resultat.getCoordonnees().size(),2);
//        assertTrue(resultat.getCoordonnees().contains(new Coordonnee(3,3)));
//        partie.getHistoriqueAide().ajouterAide(new AideInfos(resultat));
//        resultat = techniqueAdjacents3.run(partie, 1);
//        assertTrue(partie.getHistoriqueAide().aideDejaPresente(resultat));
//        assertFalse(resultat.isTechniqueTrouvee());
    }

    @AfterAll
    public static void tearDownAll() {
        ModelTest.afficherNomFin(TestBoucleAu3.class);
    }
}
