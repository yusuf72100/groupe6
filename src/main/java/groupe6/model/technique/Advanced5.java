package groupe6.model.technique;


/**
 * Classe qui représente la technique avancée n°5
 *
 * @author Yamis
 */
public class Advanced5 extends NoNivDeux {

  /**
   * Instance unique de la classe Advanced5
   */
  private static Advanced5 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced5
   *
   * @return l'instance unique de la classe Advanced5
   */
  public static Advanced5 getInstance() {
    if (instance == null) {
      instance = new Advanced5();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced5
   */
  private Advanced5() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°5"
    );
  }
}
