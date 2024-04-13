package groupe6.model.partie.aide;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import groupe6.model.partie.*;
import groupe6.model.technique.PartieTest;
import groupe6.model.technique.SimpleZero;
import groupe6.model.technique.ResultatTechnique;

public class TestAideInfos{
    private static AideInfos aideInfos;
    private static ResultatTechnique resultatTechnique;
    @BeforeAll
    public static void initAll(){
        Partie partie = PartieTest.getPartieTest();
        SimpleZero simpleZero = SimpleZero.getInstance();
        resultatTechnique = simpleZero.run(partie, 0);
        aideInfos = new AideInfos(resultatTechnique);
    }

    @Test
    public void testAidesInfos(){
        assertEquals(aideInfos.getNiveau(),1);
        assertEquals(aideInfos.getResultatTechnique(),resultatTechnique);
        aideInfos.upgradeNiveau();
        assertEquals(aideInfos.getNiveau(),2);

    }

    public static AideInfos getAideInfos(){
        return aideInfos;
    }

    public static ResultatTechnique getResultatTechnique(){
        return resultatTechnique;
    }
}