package groupe6.model;
import java.util.function.Function;

/**
 * Classe Technique qui gère les techniques applicables sur un puzzle
 *
 * @author Nathan
 */
public abstract class Technique{
    private final DifficulteTechnique difficulte;
    private final int ordre;

    Technique(DifficulteTechnique uneDifficulte, int unOrdre, Function<Puzzle, ResultatTechnique> unCodeTechnique){
        this.difficulte = uneDifficulte;
        this.ordre = unOrdre;
    }
    public int getOrdre() {
        return ordre;
    }
    public DifficulteTechnique getDifficulte() {
        return difficulte;
    }
    public abstract ResultatTechnique run(Partie partie);
}
