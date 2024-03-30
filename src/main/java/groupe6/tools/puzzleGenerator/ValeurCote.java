package groupe6.tools.puzzleGenerator;

/**
 * @author Yamis
 */

// Enum pour les valeurs des cotés
public enum ValeurCote {
  VIDE,
  TRAIT,
  CROIX;

  public static ValeurCote getValeurSuivante(ValeurCote valeurActuelle) {
    switch (valeurActuelle) {
      case VIDE:
        return TRAIT;
      case TRAIT:
        return CROIX;
      case CROIX:
        return VIDE;
      default:
        throw new IllegalArgumentException("Valeur invalide : " + valeurActuelle);
    }
  }
}