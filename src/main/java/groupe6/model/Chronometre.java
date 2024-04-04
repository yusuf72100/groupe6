package groupe6.model;

import java.security.Timestamp;
import java.time.Duration;
import java.util.Timer;

/**
 * @author Yamis
 */

// Classe qui correspond au chronomètre
public class Chronometre {

  private long debut; // Temps de début du chronomètre
  private Duration pause; // Temps de pause du chronomètre

  public Chronometre() {
    this.debut = System.currentTimeMillis();
    this.pause = null;
  }

  public Chronometre(Duration tempsEcoule) {
    this.debut = System.currentTimeMillis() - tempsEcoule.toMillis();
    this.pause = null;
  }

  // Méthode qui calcul le temps écoulé
  Duration getTempsEcoule() {
    if ( this.pause != null )
      return this.pause;
    else {
      Duration tempsEcoule = Duration.ofMillis(System.currentTimeMillis() - debut);
      return tempsEcoule;
    }
  }

  public void pause() {
    this.pause = getTempsEcoule();
  }

  public void reprendre() {
    this.debut = System.currentTimeMillis() - this.pause.toMillis();
    this.pause = null;
  }

  // Méthode qui indique si on a dépassé un temps limite
  boolean depassementTempsLimite(Duration limite) {
    return getTempsEcoule().compareTo(limite) > 0;
  }

}
