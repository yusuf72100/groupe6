package groupe6.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Cette classe modélise les informations données sur chaque technique.
 * 
 * @author William Sardon
 */
public class TechniqueInfos implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /** Chemin vers une image demontrant l'utilisation d'une technique */

    private final String explicationTxt; // Texte d'explication d'une technique
    private final String schemaIMG;

    /** Niveau de difficulté de la technique : 'FACILE', 'MOYEN', 'DIFFICILE' */
    private final DifficulteTechnique difficulte;

    /**
     * Constructeur
     * 
     * @param explicationTxt explication textuelle de la technique
     * @param schemaIMG      chemin vers l'image demontrant l'utilisation d'une technique
     * @param difficulte     niveau de difficulte des techniques
     */
    public TechniqueInfos(String explicationTxt, String schemaIMG, DifficulteTechnique difficulte) {
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
