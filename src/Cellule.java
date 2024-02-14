

public class Cellule{

  // Constantes pour les cotés
  public static final int HAUT = 0;
  public static final int BAS = 1;
  public static final int GAUCHE = 2;
  public static final int DROITE = 3;

  // Enum pour les valeurs des cotés
  public enum ValeurCote {
    VIDE,
    TRAIT,
    CROIX
  }

  private int valeur;
  private ValeurCote[] cotes;

  // Constructeur
  public Cellule(int valeur, ValeurCote[] cotes) {
    this.valeur = valeur;
    this.cotes = cotes;
  }

  public int getValeur() {
    return valeur;
  }

  public ValeurCote getCote(int cote) {
    return this.cotes[cote];
  }

  public int nbTrait() {
    int nbTrait = 0;

    for (ValeurCote v : cotes) {
      if (v == ValeurCote.TRAIT) {
        nbTrait++;
      }
    }

    return nbTrait;
  }

}