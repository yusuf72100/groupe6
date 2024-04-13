package groupe6.model.partie;

import java.time.Duration;

/**
 * Classe qui modélise le chronomètre d'une partie
 *
 * @author Yamis, Yusuf
 */
public class Chronometre {

  /**
   * Le temps écoulé
   */
  private Duration tempsEcoule;

  /**
   * Le boolean qui indique si le chronomètre est démarré
   */
  private boolean demarre;

  /**
   * Le temps de départ
   */
  private long startTime;

  /**
   * Constructeur de la classe Chronometre
   *
   * @param tempsEcoule le temps écoulé
   */
  public Chronometre(Duration tempsEcoule) {
    this.tempsEcoule = tempsEcoule;
    this.demarre = false;
  }

  /**
   * Constructeur de la classe Chronometre
   */
  public Chronometre() {
    this.tempsEcoule = Duration.ZERO;
    this.demarre = false;
  }

  /**
   * Méthode qui démarre le chronomètre
   */
  public void start() {
    if (!demarre) {
      startTime = System.nanoTime();
      demarre = true;
    }
  }

  /**
   * Méthode qui arrête le chronomètre
   */
  public void stop() {
    if (demarre) {
      tempsEcoule = tempsEcoule.plus(Duration.ofNanos(System.nanoTime() - startTime));
      demarre = false;
    }
  }

  /**
   * Méthode permet d'obtenir le temps écoulé
   *
   * @return le temps écoulé
   */
   public Duration getTempsEcoule() {
    if (demarre) {
      return tempsEcoule.plus(Duration.ofNanos(System.nanoTime() - startTime));
    } else {
      return tempsEcoule;
    }
  }

  /**
   * Méthode qui permet de formater le temps écoulé
   *
   * @return le temps écoulé formaté
   */
  public String getElapsedTimeFormatted() {
    long seconds = getTempsEcoule().getSeconds();
    long minutes = seconds / 60;
    long hours = minutes / 60;

    return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
  }

  /**
   * Méthode qui permet d'obtenir le chronomètre
   *
   * @return le chronomètre
   */
  public Chronometre getChrono() {
    return this;
  }
}