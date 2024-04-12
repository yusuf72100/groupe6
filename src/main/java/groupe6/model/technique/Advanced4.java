package groupe6.model.technique;


/**
 * Classe qui représente la technique avancée n°4
 *
 * @author Yamis
 */
public class Advanced4 extends Advanced {

  /**
   * Instance unique de la classe Advanced4
   */
  private static Advanced4 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced4
   *
   * @return l'instance unique de la classe Advanced4
   */
  public static Advanced4 getInstance() {
    if (instance == null) {
      instance = new Advanced4();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced4
   */
  private Advanced4() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°4"
    );
  }
}
