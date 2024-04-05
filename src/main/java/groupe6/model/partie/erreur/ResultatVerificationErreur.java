package groupe6.model.partie.erreur;

import groupe6.model.partie.puzzle.Coordonnee;

import java.util.Set;

/**
 * Classe qui représente le résultat de la vérification des erreurs
 *
 * @author Yamis
 */
public class ResultatVerificationErreur {

  /**
   * Booléen qui indique si des erreurs on été trouvées
   */
  private final boolean erreurTrouvee;

  /**
   * L'ensemble des coordonnées qui correspondent à la première erreur
   */
  private final Set<Coordonnee> premiereErreur;

  /**
   * L'ensemble des coordonnées qui correspondent au erreurs qui découlent de la première erreur
   */
  private final Set<Coordonnee> erreursSuivantes;

  /**
   * Constructeur de la classe ResultatVerificationErreur
   *
   * @param erreurTrouvee booléen qui indique si des erreurs on été trouvées
   * @param premiereErreur l'ensemble des coordonnées qui correspondent à la première erreur
   * @param erreursSuivantes l'ensemble des coordonnées qui correspondent au erreurs qui en découlent
   */
  public ResultatVerificationErreur(boolean erreurTrouvee, Set<Coordonnee> premiereErreur, Set<Coordonnee> erreursSuivantes) {
    this.erreurTrouvee = erreurTrouvee;
    this.premiereErreur = premiereErreur;
    this.erreursSuivantes = erreursSuivantes;
  }

  /**
   * Méthode pour obtenir le booléen qui indique si des erreurs on été trouvées
   *
   * @return le booléen qui indique si des erreurs on été trouvées
   */
  public boolean isErreurTrouvee() {
    return erreurTrouvee;
  }

  /**
   * Méthode pour obtenir l'ensemble des coordonnées qui correspondent à la première erreur
   *
   * @return l'ensemble des coordonnées qui correspondent à la première erreur
   */
  public Set<Coordonnee> getPremiereErreur() {
    return premiereErreur;
  }

  /**
   * Méthode pour obtenir l'ensemble des coordonnées qui correspondent aux erreurs qui découlent de la première erreur
   *
   * @return l'ensemble des coordonnées qui correspondent aux erreurs qui découlent de la première erreur
   */
  public Set<Coordonnee> getErreursSuivantes() {
    return this.erreursSuivantes;
  }

  /**
   * Méthode pour obtenir une représentation textuelle de l'objet
   *
   * @return une chaîne de caractères qui représente l'objet
   */
  @Override
  public String toString() {
    return "ResultatVerificationErreur{" +
        "erreurTrouvee=" + erreurTrouvee +
        ", premiereErreur=" + premiereErreur +
        ", erreursSuivantes=" + erreursSuivantes +
        '}';
  }
}
