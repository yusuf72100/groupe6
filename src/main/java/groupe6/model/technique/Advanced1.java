package groupe6.model.technique;

/**
 * Classe qui représente la technique avancée n°1
 *
 * @author Yamis
 */
public class Advanced1 extends Advanced {

  /**
   * Instance unique de la classe Advanced1
   */
  private static Advanced1 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced1
   *
   * @return l'instance unique de la classe Advanced1
   */
  public static Advanced1 getInstance() {
    if (instance == null) {
      instance = new Advanced1();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced1
   */
  private Advanced1() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°1"
    );
  }
}
