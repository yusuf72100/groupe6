package groupe6.model.technique;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTechnique {
    private static SimpleZero simpleZero;

    /**
     * Inialisation d'une technique pour tester
     */
    @BeforeAll
    public static void initAll() {
         simpleZero = SimpleZero.getInstance();
    }

    /**
     * Test des méthodes de la classe Technique
     */
    @Test
    public void testTechnique(){
        assertEquals(simpleZero.getDifficulte(),DifficulteTechnique.DEMARRAGE);
        assertEquals(simpleZero.getNomTechnique(),"SimpleZero");
        assertEquals(simpleZero.getNomTechniqueStylise(),"simple zero");
    }
}
