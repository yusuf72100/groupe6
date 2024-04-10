package groupe6.model.technique;

import groupe6.ModelTest;
import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.profil.Profil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimpleZero extends ModelTest {
    private static Puzzle puzzle;
    private static SimpleZero techniqueSimpleZero;
    private static Partie partie;
    private static Profil profil;

    @BeforeAll
    public static void initAll(){
        techniqueSimpleZero = techniqueSimpleZero.getInstance();
        partie = PartieTest.getPartieTest();
    }


    @Test
    public void testDetectionSimpleZero(){
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        ResultatTechnique resultatTechnique;

        System.out.println("Test à corriger");

//        resultatTechnique= techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.isTechniqueTrouvee());
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==0);
//
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==5);
//
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==2 && resultatTechnique.getCoordonnees().get(0).getX()==2);
//
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==3 && resultatTechnique.getCoordonnees().get(0).getX()==4);
//
//        historiqueAides.ajouterAide(new AideInfos(null,resultatTechnique));
//
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==0);
//
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==5);
//
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//        resultatTechnique = techniqueSimpleZero.run(partie, 0);
//        assertFalse(resultatTechnique.isTechniqueTrouvee());

    }
}
