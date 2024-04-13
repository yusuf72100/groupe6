package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe de la technique boucle qui rejoint un 1
 *
 * @author Tom MARSURA
 */
public class BoucleAu1 extends Technique {

  /**
   * Instance unique de la classe BoucleAu1
   */
  private static BoucleAu1 instance = null;

  /**
   * Méthode qui renvoit l'instance unique de la classe BoucleAu1
   *
   * @return l'instance unique de la classe BoucleAu1
   */
  public static BoucleAu1 getInstance(){
    if(instance == null){
      instance = new BoucleAu1();
    }
    return instance;
  }

  /**
   * Constructeur de la classe BoucleAu1
   */
  private BoucleAu1(){
    super(
        DifficulteTechnique.BASIQUE,
        "boucle atteignant le 1"
    );
  }

  /**
   * Méthode run qui parcoure la grille à la recherche de la technique boucle qui rejoint un 1.
   *
   * @param partie la partie sur laquelle on cherche la technique
   * @param indexTechnique le numéro de la technique
   * @return ResultatTechnique renvoit les coordonnées de la technique si elle est trouvée et sinon, renvoi un ResultatTechnique vide
   */
  @Override
  public ResultatTechnique run(Partie partie, int indexTechnique){
    Puzzle grille = partie.getPuzzle();
    int largeur = grille.getLargeur() - 1;
    int longueur = grille.getLongueur() - 1;

    // Parcours de la grille
    for(int i = 1; i < largeur; i++){

      //Récupération des valeurs des côtés de la grille.
      int haut = grille.getCellule(0, i).getValeur();
      int bas = grille.getCellule(largeur-1, i).getValeur();
      int droite = grille.getCellule(i, longueur-1).getValeur();
      int gauche = grille.getCellule(i, 0).getValeur();

      // Vérification des côtés de la grille
      if(haut == 1){
        if(grille.getCellule(0, i + 1).getCote(Cellule.HAUT) == ValeurCote.TRAIT){
          if(grille.getCellule(0, i).getCote(Cellule.BAS) == ValeurCote.TRAIT || grille.getCellule(0, i).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(0, i), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        }
        else if (grille.getCellule(0, i - 1).getCote(Cellule.HAUT) == ValeurCote.TRAIT){
          if(grille.getCellule(0, i).getCote(Cellule.BAS) == ValeurCote.TRAIT || grille.getCellule(0, i).getCote(Cellule.DROITE) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(0, i), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        }
      }

      if(bas == 1){
        if(grille.getCellule(largeur, i - 1).getCote(Cellule.BAS) == ValeurCote.TRAIT){
          if(grille.getCellule(largeur, i).getCote(Cellule.HAUT) == ValeurCote.TRAIT || grille.getCellule(largeur, i).getCote(Cellule.DROITE) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(largeur, i), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        } else if (grille.getCellule(largeur, i + 1).getCote(Cellule.BAS) == ValeurCote.TRAIT) {
          if(grille.getCellule(largeur, i).getCote(Cellule.HAUT) == ValeurCote.TRAIT || grille.getCellule(largeur, i).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(largeur, i), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        }
      }

      if(droite == 1){
        if(grille.getCellule(i + 1, longueur).getCote(Cellule.DROITE) == ValeurCote.TRAIT){
          if(grille.getCellule(i, longueur).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || grille.getCellule(i, longueur).getCote(Cellule.HAUT) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(i, longueur), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        } else if (grille.getCellule(i - 1, longueur).getCote(Cellule.BAS) == ValeurCote.TRAIT){
          if(grille.getCellule(i, longueur).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT || grille.getCellule(i, longueur).getCote(Cellule.BAS) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(i, longueur), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        }
      }

      if(gauche == 1){
        if(grille.getCellule(i, 0).getCote(Cellule.GAUCHE) == ValeurCote.TRAIT){
          if(grille.getCellule(i + 1, 0).getCote(Cellule.DROITE) == ValeurCote.TRAIT || grille.getCellule(i, 0).getCote(Cellule.HAUT) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(i, 0), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        } else if (grille.getCellule(i - 1, 0).getCote(Cellule.BAS) == ValeurCote.TRAIT){
          if(grille.getCellule(i, 0).getCote(Cellule.DROITE) == ValeurCote.TRAIT || grille.getCellule(i, 0).getCote(Cellule.BAS) == ValeurCote.TRAIT){
            ResultatTechnique result = creerResultat(new Coordonnee(i, 0), indexTechnique);
            if(!partie.getHistoriqueAide().aideDejaPresente(result)){
              return result;
            }
          }
        }
      }
    }

    return new ResultatTechnique(false);
  }

  /**
   * Méthode qui crée un résultat de la technique boucle qui rejoint un 1
   * @param position Coordonnée de la technique
   * @param indexTechnique Numéro de la technique
   * @return ResultatTechnique renvoit les coordonnées de la technique si elle est trouvée
   */
  private ResultatTechnique creerResultat(Coordonnee position, int indexTechnique){
    ArrayList<Coordonnee> ListePositions = new ArrayList<>();
    ListePositions.add(position);

    return new ResultatTechnique(
        true,
        new HashSet<>(ListePositions),
        indexTechnique,
        nomTechnique,
        nomTechniqueStylise,
        difficulte
    );
  }
}
