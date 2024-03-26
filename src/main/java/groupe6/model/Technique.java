package groupe6.model;
import java.util.function.Function;

public class Technique{
    private DifficulteTechnique difficulte;
    private int ordre;
    private Function<Puzzle, ResultatTechnique> codeTechnique;
    Technique(DifficulteTechnique uneDifficulte, int unOrdre, Function<Puzzle, ResultatTechnique> unCodeTechnique){
        this.difficulte = uneDifficulte;
        this.ordre = unOrdre;
        this.codeTechnique=unCodeTechnique;
    }
    public int getOrdre() {
        return ordre;
    }
    public DifficulteTechnique getDifficulte() {
        return difficulte;
    }
    public ResultatTechnique appliquerTechnique(Puzzle puzzle) {
        ResultatTechnique resultat=codeTechnique.apply(puzzle);
        return resultat;
    }
}
