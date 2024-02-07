public class InfoTechnique {
    /** Texte d'explication d'une technique */
    private String explicationTxt;

    /** Chemin vers une image démontrant l'utilisation d'une technique */
    private String schemaIMG;

    /** Niveau de difficulté de la technique : 'FACILE', 'MOYEN', 'DIFFICILE' */
    private String difficulte;

    public InfoTechnique(String explicationTxt, String schemaIMG, String difficulte) {
        this.explicationTxt = explicationTxt;
        this.schemaIMG = schemaIMG;
        this.difficulte = difficulte;
    }

}
