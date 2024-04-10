package groupe6.model.partie;

import java.time.Duration;

/**
 * Classe qui modélise le chronomètre d'une partie
 *
 * @author Yamis
 */
public class Chronometre {

  private Duration tempsEcoule;
  private boolean demarre;

  public Chronometre(Duration tempsEcoule) {
    this.tempsEcoule = tempsEcoule;
    this.demarre = false;
  }

  public Chronometre() {
    this.tempsEcoule = Duration.ZERO;
    this.demarre = false;
  }

  public void start() {
    if (!demarre) {
      startTime = System.nanoTime();
      demarre = true;
    }
  }

  public void stop() {
    if (demarre) {
      tempsEcoule = tempsEcoule.plus(Duration.ofNanos(System.nanoTime() - startTime));
      demarre = false;
    }
  }

   public Duration getTempsEcoule() {
    if (demarre) {
      return tempsEcoule.plus(Duration.ofNanos(System.nanoTime() - startTime));
    } else {
      return tempsEcoule;
    }
  }

  public String getElapsedTimeFormatted() {
    long seconds = getTempsEcoule().getSeconds();
    long minutes = seconds / 60;
    long hours = minutes / 60;

    return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
  }

  public Chronometre getChrono() {
    return this;
  }

  public void setTempsEcoule(Duration tempsEcoule) {
    this.tempsEcoule = tempsEcoule;
  }

  private long startTime;
}