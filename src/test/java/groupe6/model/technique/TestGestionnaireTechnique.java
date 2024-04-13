package groupe6.model.technique;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGestionnaireTechnique {
    private static SimpleZero  simpleZero;
    private static Adjacents03  adjacents03;

    @BeforeAll
    public static void initAll() {
        simpleZero = SimpleZero.getInstance();
        adjacents03 = Adjacents03.getInstance();
    }

    @Test
    public void TestGestionnaireTechnique() {
        GestionnaireTechnique gestionnaireTechnique = GestionnaireTechnique.getInstance();
        assertEquals(gestionnaireTechnique.getListeTechnique().get(0), simpleZero);
        assertEquals(gestionnaireTechnique.getListeTechnique().get(1), adjacents03);
    }
}
