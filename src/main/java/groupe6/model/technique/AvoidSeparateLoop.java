package groupe6.model.technique;


/**
 * Classe qui représente la technique AvoidSeparateLoop
 *
 * @author Yamis
 */
public class AvoidSeparateLoop extends NoNivDeux {

  /**
   * Instance unique de la classe AvoidSeparateLoop
   */
  private static AvoidSeparateLoop instance = null;

  /**
   * Méthode pour obtenir l'instance unique de la classe AvoidSeparateLoop
   *
   * @return l'instance unique de la classe AvoidSeparateLoop
   */
  public static AvoidSeparateLoop getInstance() {
    if (instance == null) {
      instance = new AvoidSeparateLoop();
    }
    return instance;
  }

  /**
   * Constructeur privé de la classe AvoidSeparateLoop
   */
  private AvoidSeparateLoop() {
    super(
        DifficulteTechnique.AVANCEE,
        "éviter les boucles séparées"
    );
  }
}
