package groupe6.model;
import java.util.function.Function;

public abstract class Technique{
    private DifficulteTechnique difficulte;
    private int ordre;

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
