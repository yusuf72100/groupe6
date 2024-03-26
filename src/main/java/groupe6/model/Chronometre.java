package groupe6.model;

import java.time.Duration;

/**
 * @author Yamis
 */

// Classe qui correspond au chronomètre
public class Chronometre {
  // TODO
  private long debut; // Temps de début du chronomètre
  private Duration tempsEcoule; // Temps écoulé

  // Constructeur de la classe Chronometre
  public Chronometre() {
    tempsEcoule = Duration.ZERO;
  }

  // Méthode qui démarre le chronomètre
  public void demarrer() {
    debut = System.currentTimeMillis();
  }

  // Méthode qui démarre le chronomètre depuis un temps écoulé
  public void demarrerDepuisTempsEcoule() {
    debut = System.currentTimeMillis() - tempsEcoule.toMillis();
  }

  // Méthode qui calcul le temps écoulé
  Duration tempsEcoule() {
    this.tempsEcoule = Duration.ofMillis(System.currentTimeMillis() - debut);
    return tempsEcoule;
  }

  // Méthode qui indique si on a dépassé un temps limite
  boolean depassementTempsLimite(Duration limite) {
    return tempsEcoule().compareTo(limite) > 0;
  }

}
