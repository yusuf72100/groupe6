package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe de la technique zéro et trois adjacents
 *
 * @author Tom MARSURA
 */
public class Adjacents03 extends Technique {

  /**
   * Booléen qui permet de vérifier de façon verticale
   */
  private static final boolean VERTI = true;


  /**
   * Booléen qui permet de vérifier de façon horizontale
   */
  private static final boolean HORIZ = false;

  /**
   * Instance unique de la classe Adjacents03
   */
  private static Adjacents03 instance = null;

  /**
   * Méthode qui permet de récupérer l'instance unique de la classe Adjacents03
   *
   * @return instance unique de la classe Adjacents03
   */
  public static Adjacents03 getInstance() {
    if ( instance == null){
      instance = new Adjacents03();
      return instance;
    }
    else {
      return instance;
    }
  }

  /**
   * Constructeur de la classe de la zeroTroisAdjacents
   *
   */
  private Adjacents03() {
    super(
        DifficulteTechnique.DEMARRAGE,
        "zéro et trois adjacents"
    );
  }

  /**
   * Méthode run qui parcourt la grille à la recherche de la technique zéro et trois adjacents
   *
   * @param partie Partie en cours
   * @return ResultatTechnique renvoit les coordonées de la technique si elle est trouvée et sinon, renvoi un ResultatTechnique vide
   */
  @Override
  public ResultatTechnique run(Partie partie, int idxTechnique) {
    Puzzle grille = partie.getPuzzle();

    List<Coordonnee> numero = rechercherNumero(grille, 0);
    if(numero.isEmpty()){
      return new ResultatTechnique(false);
    }

    // Parcours des coordonnées des 0 trouvées
    for(int i = 0; i < numero.size(); i++) {
      int x = numero.get(i).getX();
      int y = numero.get(i).getY();

      int haut;
      int bas;
      int droite;
      int gauche;

      if (grille.estDansGrille(y - 1, x)) {
        haut = grille.getCellule(y - 1, x).getValeur();
      } else {
        haut = -1;
      }

      if (grille.estDansGrille(y + 1, x)) {
        bas = grille.getCellule(y + 1, x).getValeur();
      } else {
        bas = -1;
      }

      if (grille.estDansGrille(y, x + 1)) {
        droite = grille.getCellule(y, x + 1).getValeur();
      } else {
        droite = -1;
      }

      if (grille.estDansGrille(y, x - 1)) {
        gauche = grille.getCellule(y, x - 1).getValeur();
      } else {
        gauche = -1;
      }
      if (gauche == 3) {
        Coordonnee trois = new Coordonnee(y, x - 1);
        Coordonnee zero = new Coordonnee(y, x);
        if (verifZeroTroisAdjacent(grille, Arrays.asList(trois, zero), HORIZ)) {
          ResultatTechnique result = creerResultat(Arrays.asList(trois, zero), idxTechnique);
          if (!partie.getHistoriqueAide().aideDejaPresente(result)) {
            return result;
          }
        }
      } else if (haut == 3) {
        Coordonnee zero = new Coordonnee(y, x);
        Coordonnee trois = new Coordonnee(y - 1, x);
        if (verifZeroTroisAdjacent(grille, Arrays.asList(zero, trois), VERTI)) {
          ResultatTechnique result = creerResultat(Arrays.asList(zero, trois), idxTechnique);
          if (!partie.getHistoriqueAide().aideDejaPresente(result)) {
            return result;
          }
        }
      } else if (droite == 3) {
        Coordonnee zero = new Coordonnee(y, x);
        Coordonnee trois = new Coordonnee(y, x + 1);
        if (verifZeroTroisAdjacent(grille, Arrays.asList(zero, trois), HORIZ)) {
          ResultatTechnique result = creerResultat(Arrays.asList(zero, trois), idxTechnique);
          if (!partie.getHistoriqueAide().aideDejaPresente(result)) {
            return result;
          }
        }
      } else if (bas == 3) {
        Coordonnee zero = new Coordonnee(y, x);
        Coordonnee trois = new Coordonnee(y + 1, x);
        if (verifZeroTroisAdjacent(grille, Arrays.asList(trois, zero), VERTI)) {
          ResultatTechnique result = creerResultat(Arrays.asList(trois, zero), idxTechnique);
          if (!partie.getHistoriqueAide().aideDejaPresente(result)) {
            return result;
          }
        }
      }
    }
    return new ResultatTechnique(false);
  }

  /**
   * Méthode qui permet de créer le résultat de la technique avec les coordonnées où la technique est applicable
   *
   * @param positions Coordonnées où la technique est applicable
   * @param idxTechnique index de la technique dans la liste des techniques
   * @return ResultatTechnique qui contient les coordonnées et le numéro de la technique à appliquer
   */
  private ResultatTechnique creerResultat(List<Coordonnee> positions,int idxTechnique){
    ArrayList<Coordonnee> ListePositions = new ArrayList<>();
    ListePositions.add(positions.get(0));
    ListePositions.add(positions.get(1));

    return new ResultatTechnique(
        true,
        new HashSet<>(ListePositions),
        idxTechnique,
        nomTechnique,
        nomTechniqueStylise,
        difficulte
    );
  }

  /**
   * Méthode qui permet de vérifier si les traits d'un zéro et trois adjacents sont correctement placés. La méthode lit de bas en haut si le sens et VERTI (true) et de gauche à droite si le sens est HORIZ (false).
   *
   * @param grille la grille de jeu
   * @param position les coordonnées des deux cellules à vérifier
   * @param sens le sens de la lecture
   * @return true si la technique est appliquable, false sinon
   * @author Tom MARSURA
   */
  private boolean verifZeroTroisAdjacent(Puzzle grille, List<Coordonnee> position, boolean sens){
    Coordonnee coord1 = position.get(0);
    Coordonnee coord2 = position.get(1);

    if(sens){
      if(grille.getCellule(coord1.getY(), coord2.getX()).getValeur() == 0){ // Le trois est au-dessus
        if(grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.HAUT) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.DROITE) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.BAS) == ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX() - 1).getCote(Cellule.BAS) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX() + 1).getCote(Cellule.BAS) != ValeurCote.TRAIT){
          return true;
        } else {
          return false;
        }
      } else { // Le zéro est au-dessus
        if(grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.DROITE) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.BAS) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.HAUT) == ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX() - 1).getCote(Cellule.HAUT) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX() + 1).getCote(Cellule.HAUT) != ValeurCote.TRAIT){
          return true;
        } else {
          return false;
        }
      }
    } else {
      if(grille.getCellule(coord1.getY(), coord1.getX()).getValeur() == 0){ // Le trois est à droite
        if(grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.DROITE) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.BAS) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.HAUT) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY(), coord2.getX()).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT
            || grille.getCellule(coord2.getY() - 1, coord2.getX()).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT
            || grille.getCellule(coord2.getY() + 1, coord2.getX()).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT){
          return true;
        } else {
          return false;
        }
      } else {
        if(grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.BAS) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.HAUT) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY(), coord1.getX()).getCote(Cellule.DROITE) == ValeurCote.TRAIT
            || grille.getCellule(coord1.getY() - 1, coord1.getX()).getCote(Cellule.DROITE) != ValeurCote.TRAIT
            || grille.getCellule(coord1.getY() + 1, coord1.getX()).getCote(Cellule.DROITE) != ValeurCote.TRAIT) {
          return true;
        } else {
          return false;
        }
      }
    }
  }
}
