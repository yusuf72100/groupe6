package groupe6.model.technique;

import groupe6.CouleursANSI;
import groupe6.ModelTest;
import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.Coordonnee;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimpleZero extends ModelTest {
    private static SimpleZero techniqueSimpleZero;
    private static Partie partie;


    @BeforeAll
    public static void initAll(){
        ModelTest.afficherNomDebut(TestSimpleZero.class);
        techniqueSimpleZero = SimpleZero.getInstance();
        partie = PartieTest.getPartieTest();
    }

    @Test
    public void testDetectionSimpleZero(){
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        ResultatTechnique resultatTechnique;

        resultatTechnique= techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.isTechniqueTrouvee());
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(0,0)));

        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(0,5)));

       historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(2,2)));

        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

       resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(3,4)));

        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(5,0)));

        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(5,5)));

        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));
       resultatTechnique = techniqueSimpleZero.run(partie, 0);
        assertFalse(resultatTechnique.isTechniqueTrouvee());

    }
    @AfterAll
    public static void tearDownAll() {
        ModelTest.afficherNomFin(TestSimpleZero.class);
    }
}
