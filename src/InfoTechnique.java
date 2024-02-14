public class InfoTechnique {
    /** Texte d'explication d'une technique */
    private String explicationTxt;

    /** Chemin vers une image démontrant l'utilisation d'une technique */
    private String schemaIMG;

    /** Niveau de difficulté de la technique : 'FACILE', 'MOYEN', 'DIFFICILE' */
    private DifficulteTechnique difficulte;

    /** Enum pour les difficultes des techniques */
    public enum DifficulteTechnique {
        DEMARRAGE,
        BASIQUE,
        AVANCEE;
    }

    public InfoTechnique(String explicationTxt, String schemaIMG, DifficulteTechnique difficulte) {
        this.explicationTxt = explicationTxt;
        this.schemaIMG = schemaIMG;
        this.difficulte = difficulte;
    }

    /** Methode pour obtenir la difficulte */
    public DifficulteTechnique getDifficulte() {
        return this.difficulte;
    }

    /** Methode pour obtenir l'explication texte */
    public DifficulteTechnique getExplicationTxt() {
        return this.difficulte;
    }

    /** Methode pour obtenir le chemin vers l'image */
    public DifficulteTechnique getSchemaIMG() {
        return this.difficulte;
    }
}