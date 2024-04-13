package groupe6.model.partie.info;

import groupe6.model.partie.puzzle.DifficultePuzzle;

/**
 * Classe qui correspond aux constantes de score
 *
 * @author Yamis
 */
public class Score {

  /**
   * Constante : score en début de partie pour la difficulté facile
   */
  public static final int SCORE_DEBUT_FACILE = 300;

  /**
   * Constante : score en début de partie pour la difficulté moyenne
   */
  public static final int SCORE_DEBUT_MOYEN = 600;

  /**
   * Constante : score en début de partie pour la difficulté difficile
   */
  public static final int SCORE_DEBUT_DIFFICILE = 1000;

  /**
   * Constante : malus pour avoir demandé une vérification d'erreur
   */
  public static final int MALUS_VERIFICATION_ERREUR = 20;

  /**
   * Constante : malus pour avoir demandé une aide de niveau 1
   */
  public static final int MALUSE_AIDE_NIVEAU_1 = 30;

  /**
   * Constante : malus supplémentaire pour avoir demandé une aide de niveau 2
   */
  public static final int MALUSE_SUPPLEMENTAIRE_AIDE_NIVEAU_2 = 10;

  /**
   * Constante : malus par minute de temps de jeu écoulé
   */
  public static final int MALUS_PAR_MINUTE = 15;

  /**
   * Constante : le score minimum qui peut être obtenu
   */
  public static final int MIN_SCORE = 50;

  /**
   * Méthode qui permet de récupérer le score en début de partie en fonction de la difficulté
   *
   * @param difficulte la difficulté du puzzle
   * @return le score en début de partie
   */
  public static int getScoreDebut(DifficultePuzzle difficulte) {
    switch (difficulte) {
      case FACILE:
        return SCORE_DEBUT_FACILE;
      case MOYEN:
        return SCORE_DEBUT_MOYEN;
      case DIFFICILE:
        return SCORE_DEBUT_DIFFICILE;
      default:
        throw new IllegalArgumentException("Difficulté inconnue");
    }
  }

}
