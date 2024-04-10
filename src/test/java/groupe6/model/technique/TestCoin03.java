package groupe6.model.technique;

import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
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
        partie = PartieTest.getPartieTest();
        techniqueCoin03 = Coin03.getInstance();

    }

    @Test
    public void testCoin0Trois(){
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        ResultatTechnique resultatTechnique ;
        System.out.println("Test à corriger");

//        //Detection haut gauche horizontal
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==2);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection haut droit horizontal
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==0 && resultatTechnique.getCoordonnees().get(0).getX()==3);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection haut droit vertical
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==2 && resultatTechnique.getCoordonnees().get(0).getX()==5);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection bas gauche horizontal
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==2);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection bas gauche vertical
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==3 && resultatTechnique.getCoordonnees().get(0).getX()==0);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection bas droit horizontal
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertTrue(resultatTechnique.getCoordonnees().get(0).getY()==5 && resultatTechnique.getCoordonnees().get(0).getX()==3);
//        historiqueAides.ajouterAide(new AideInfos(null, resultatTechnique));
//
//        //Detection plus de technique détectée
//        resultatTechnique = techniqueCoin03.run(partie, 6);
//        assertFalse(resultatTechnique.isTechniqueTrouvee());
    }
}
