package groupe6.model.technique;

/**
 * Classe qui représente la technique avancée n°2
 *
 * @author Yamis
 */
public class Advanced2 extends NoNivDeux {

  /**
   * Instance unique de la classe Advanced2
   */
  private static Advanced2 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced2
   *
   * @return l'instance unique de la classe Advanced2
   */
  public static Advanced2 getInstance() {
    if (instance == null) {
      instance = new Advanced2();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced2
   */
  private Advanced2() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°2"
    );
  }
}
