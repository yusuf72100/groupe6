package groupe6.model;

import java.time.Duration;

/**
 * Classe qui modélise le chronomètre d'une partie
 *
 * @author Yamis
 */
public class Chronometre {

  /**
   * Le temps qui sert de référence pour le lancement du chronomètre
   */
  private long debut;

  /**
   * Le temps écoulé au moment de la pause
   */
  private Duration pause;

  /**
   * Constructeur de la classe Chronometre
   */
  public Chronometre() {
    this.debut = System.currentTimeMillis();
    this.pause = null;
  }

  /**
   * Constructeur de la classe Chronometre
   *
   * @param tempsEcoule le temps écoulé pour initialiser le chronomètre
   */
  public Chronometre(Duration tempsEcoule) {
    this.debut = System.currentTimeMillis() - tempsEcoule.toMillis();
    this.pause = null;
  }

  /**
   * Méthode pour obtenir le temps écoulé depuis le lancement du chronomètre
   *
   * @return le temps écoulé
   */
  Duration getTempsEcoule() {
    if ( this.pause != null )
      return this.pause;
    else {
      return Duration.ofMillis(System.currentTimeMillis() - debut);
    }
  }

  /**
   * Méthode pour mettre en pause le chronomètre
   */
  public void pause() {
    this.pause = getTempsEcoule();
  }

  /**
   * Méthode pour reprendre le chronomètre
   */
  public void reprendre() {
    this.debut = System.currentTimeMillis() - this.pause.toMillis();
    this.pause = null;
  }

  /**
   * Méthode verifier si le temps écoulé dépasse un temps limite
   *
   * @param limite le temps limite
   */
  boolean depassementTempsLimite(Duration limite) {
    return getTempsEcoule().compareTo(limite) > 0;
  }

}
