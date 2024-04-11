package groupe6.model.technique;

import groupe6.ModelTest;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAnyNumberCorner extends ModelTest {
    private static Partie partie;
    private static AnyNumberCorner techniqueAnyNumberCorner;
    private static SimpleZero techniqueSimpleZero;

    /**
     * Initialise la partie pour le test
     */
    //@BeforeAll
    public static void initAll(){
        techniqueAnyNumberCorner = AnyNumberCorner.getInstance();
        techniqueSimpleZero = SimpleZero.getInstance();
        Cellule[][] grilleCellules = new Cellule[6][6];

        grilleCellules[0][0] = new Cellule(1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][2] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][3] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[0][5] = new Cellule(2, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[1][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[1][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[2][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[2][5] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[3][0] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[3][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[4][0] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][2] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][3] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[4][5] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        grilleCellules[5][0] = new Cellule(0, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][1] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][2] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][3] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][4] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
        grilleCellules[5][5] = new Cellule(3, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});

        Puzzle puzzle = new Puzzle(6, 6, grilleCellules, grilleCellules, DifficultePuzzle.FACILE);

        partie = new Partie(puzzle, ModeJeu.CLASSIQUE,null);

    }

    //@Test
    public void TestAnyNumberCorner(){
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        ResultatTechnique resultatTechnique;

        System.out.println("Test à corriger");

//        resultatTechnique = techniqueSimpleZero.run(partie,0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==0);
//        historiqueAides.ajouterAide(new AideInfos(null,resultatTechnique));
//
//        resultatTechnique = techniqueAnyNumberCorner.run(partie,0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==0);
//        historiqueAides.ajouterAide(new AideInfos(null,resultatTechnique));
//
//        resultatTechnique = techniqueAnyNumberCorner.run(partie,0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==5);
//        historiqueAides.ajouterAide(new AideInfos(null,resultatTechnique));
//
//        resultatTechnique = techniqueAnyNumberCorner.run(partie,0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==5);
//        historiqueAides.ajouterAide(new AideInfos(null,resultatTechnique));
//
//        resultatTechnique = techniqueAnyNumberCorner.run(partie,0);
//        assertFalse(resultatTechnique.isTechniqueTrouvee());


    }
}
