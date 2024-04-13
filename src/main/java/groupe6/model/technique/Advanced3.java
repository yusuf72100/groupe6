package groupe6.model.technique;


/**
 * Classe qui représente la technique avancée n°3
 *
 * @author Yamis
 */
public class Advanced3 extends NoNivDeux {

  /**
   * Instance unique de la classe Advanced3
   */
  private static Advanced3 instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe Advanced3
   *
   * @return l'instance unique de la classe Advanced3
   */
  public static Advanced3 getInstance() {
    if (instance == null) {
      instance = new Advanced3();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe Advanced3
   */
  private Advanced3() {
    super(
        DifficulteTechnique.AVANCEE,
        "technique avancée n°3"
    );
  }
}
