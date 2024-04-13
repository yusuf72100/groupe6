package groupe6.model.partie.aide;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestHistoriqueAides{
    private static HistoriqueAides historiqueAides;
    @BeforeAll
    public static void initAll(){
        historiqueAides = new HistoriqueAides();

    }

    @Test
    public void testHistoriqueAides(){
        historiqueAides.ajouterAide(TestAideInfos.getAideInfos());
        assertTrue(historiqueAides.aideDejaPresente(TestAideInfos.getResultatTechnique()));
    }


}