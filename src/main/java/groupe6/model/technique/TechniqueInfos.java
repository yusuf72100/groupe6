package groupe6.model.technique;

import java.io.Serial;
import java.io.Serializable;

/**
 * Cette classe modélise les informations données sur chaque technique.
 * 
 * @author William Sardon
 */
public class TechniqueInfos implements Serializable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Texte expliquant la technique
   */
  private final String explicationTxt;

  /**
   * Chemin vers une image demontrant l'utilisation d'une technique
   */
  private final String schemaIMG;

  /**
   * Niveau de difficulte de la technique
   */
  private final DifficulteTechnique difficulte;

  /**
   * Constructeur de la classe TechniqueInfos
   *
   * @param explicationTxt l'explication textuelle de la technique
   * @param schemaIMG le chemin vers l'image demontrant l'utilisation d'une technique
   * @param difficulte le niveau de difficulte de la technique
   */
  public TechniqueInfos(String explicationTxt, String schemaIMG, DifficulteTechnique difficulte) {
    this.explicationTxt = explicationTxt;
    this.schemaIMG = schemaIMG;
    this.difficulte = difficulte;
  }

  /**
   * Méthode pour obtenir la difficulte de la technique
   *
   * @return la difficulte de la technique
   */
  public DifficulteTechnique getDifficulte() {
      return this.difficulte;
  }

  /**
   * Méthode pour obtenir l'explication textuelle de la technique
   *
   * @return l'explication textuelle de la technique
   */
  public String getExplicationTxt() {
      return "PlaceHolder";
  }

  /**
   * Méthode pour obtenir le chemin vers l'image demontrant l'utilisation de la technique
   *
   * @return le chemin vers l'image demontrant l'utilisation de la technique
   */
  public DifficulteTechnique getSchemaIMG() {
      return this.difficulte;
  }

}
