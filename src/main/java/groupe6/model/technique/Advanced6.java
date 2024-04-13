package groupe6.model.technique;


/**
 * Classe qui représente la technique avancée n°6
 *
 * @author Yamis
 */
public class Advanced6 extends NoNivDeux {

  /**
   * Instance unique de la classe Advanced6
   */
  private static Advanced6 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced6
   *
   * @return l'instance unique de la classe Advanced6
   */
  public static Advanced6 getInstance() {
    if (instance == null) {
      instance = new Advanced6();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced6
   */
  private Advanced6() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°6"
    );
  }
}
