package groupe6.model.partie.info;

import groupe6.model.partie.puzzle.DifficultePuzzle;

import java.time.Duration;

/**
 * Classe qui correspond au limite de temps
 *
 * @author Yamis
 */
public class LimiteTemps {

  /**
   * Constante : limite de temps pour la difficulté facile
   */
  public static final Duration LIMITE_TEMPS_FACILE = Duration.ofMinutes(25);

  /**
   * Constante : limite de temps pour la difficulté moyenne
   */
  public static final Duration LIMITE_TEMPS_MOYEN = Duration.ofMinutes(35);

  /**
   * Constante : limite de temps pour la difficulté difficile
   */
  public static final Duration LIMITE_TEMPS_DIFFICILE = Duration.ofMinutes(65);

  /**
   * Méthode qui permet de récupérer la limite de temps en fonction de la difficulté
   *
   * @param difficulte la difficulté du puzzle
   * @return la limite de temps
   */
  public static Duration getLimiteTemps(DifficultePuzzle difficulte) {
    switch (difficulte) {
      case FACILE:
        return LIMITE_TEMPS_FACILE;
      case MOYEN:
        return LIMITE_TEMPS_MOYEN;
      case DIFFICILE:
        return LIMITE_TEMPS_DIFFICILE;
      default:
        throw new IllegalArgumentException("Difficulté inconnue");
    }
  }

}
