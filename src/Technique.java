import java.util.function.Function;

public class Technique{
    private InfoTechnique.DifficulteTechnique difficulte;
    private int ordre;
    private Function<Puzzle, ResultatTechnique> codeTechnique;
    Technique(InfoTechnique.DifficulteTechnique uneDifficulte, int unOrdre, Function<Puzzle, ResultatTechnique> unCodeTechnique){
        this.difficulte = uneDifficulte;
        this.ordre = unOrdre;
        this.codeTechnique=unCodeTechnique;
    }
    public int getOrdre() {
        return ordre;
    }
    public InfoTechnique.DifficulteTechnique getDifficulte() {
        return difficulte;
    }
    public ResultatTechnique appliquerTechnique(Puzzle puzzle) throws Exception{
        ResultatTechnique resultat=codeTechnique.apply(puzzle);
        return resultat;
    }
}
