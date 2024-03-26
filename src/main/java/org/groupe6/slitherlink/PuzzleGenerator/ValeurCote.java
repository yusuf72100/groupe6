package org.groupe6.slitherlink.PuzzleGenerator;

/**
 * @author Yamis
 */

// Enum pour les valeurs des cotés
public enum ValeurCote {
  VIDE,
  TRAIT,
  CROIX;

  public static org.groupe6.slitherlink.PuzzleGenerator.ValeurCote getValeurSuivante(org.groupe6.slitherlink.PuzzleGenerator.ValeurCote valeurActuelle) {
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