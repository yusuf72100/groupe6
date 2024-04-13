package groupe6.model.technique;

import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.Coordonnee;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import groupe6.ModelTest;
import groupe6.model.partie.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoin03 extends ModelTest {

    private static Partie partie;

    private static Coin03 techniqueCoin03;

    @BeforeAll
    public static void initAll(){

        ModelTest.afficherNomDebut(TestCoin03.class);

        partie = PartieTest.getPartieTest();
        techniqueCoin03 = Coin03.getInstance();

    }

    @Test
    public void testCoin0Trois(){
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        ResultatTechnique resultatTechnique;
        //Detection haut gauche horizontal
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(0,2)));
        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        //Detection haut droit horizontal
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(0,3)));
        historiqueAides.ajouterAide(new AideInfos( resultatTechnique));

        //Detection haut droit vertical
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(2,5)));
        historiqueAides.ajouterAide(new AideInfos( resultatTechnique));

        //Detection bas gauche horizontal
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(5,2)));
        historiqueAides.ajouterAide(new AideInfos( resultatTechnique));

        //Detection bas gauche vertical
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(3,0)));
        historiqueAides.ajouterAide(new AideInfos( resultatTechnique));

        //Detection bas droit horizontal
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertTrue(resultatTechnique.getCoordonnees().contains(new Coordonnee(5,3)));
        historiqueAides.ajouterAide(new AideInfos(resultatTechnique));

        //Detection plus de technique détectée
        resultatTechnique = techniqueCoin03.run(partie, 6);
        assertFalse(resultatTechnique.isTechniqueTrouvee());
    }

    @AfterAll
    public static void tearDownAll() {
        ModelTest.afficherNomFin(TestCoin03.class);
    }
}
