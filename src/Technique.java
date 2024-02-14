import java.util.concurrent.Callable;

public class Technique{
    private InfoTechnique.DifficulteTechnique difficulte;
    private int ordre;
    private Callable<ResultatTechnique> codeTechnique;
    Technique(InfoTechnique.DifficulteTechnique uneDifficulte, int unOrdre, Callable<ResultatTechnique> unCodeTechnique){
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
        ResultatTechnique resultat=codeTechnique.call();
        return resultat;
    }
}
