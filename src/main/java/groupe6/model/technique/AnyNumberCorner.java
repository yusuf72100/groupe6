package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Classe de la technique nombre dans les coins
 *
 * @author Nathan
 */
public class AnyNumberCorner extends Technique{

  /**
   * Instance unique de la classe AnyNumberCorner
   */
  private static AnyNumberCorner instance;

  /**
   * Constructeur privé AnyNumberCorner
   */
  private AnyNumberCorner(){
    super(
        DifficulteTechnique.DEMARRAGE,
        "nombre dans les coins"
    );
  }

  /**
   * Méthode pour avoir l'instance de AnyNumberCorner (singleton)
   * @return singleton AnyNumberCorner
   */
  public static AnyNumberCorner getInstance(){
    if(instance == null){
      instance = new AnyNumberCorner();
    }
    return instance;

  }

  public ResultatTechnique run(Partie partie,int idx){
    Puzzle grille = partie.getPuzzle();
    HistoriqueAides historiqueAides = partie.getHistoriqueAide();
    ResultatTechnique resultat;
    int largeur = grille.getLargeur();
    int hauteur = grille.getLongueur();
    Cellule cellule = grille.getCellule(0,0);
    if(cellule.getValeur()!=-1){
      List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(0, 0)));
      resultat = creerResultat(true, listeCoordonnee, idx);
      if(!historiqueAides.aideDejaPresente(resultat)){
        return resultat;
      }
    }
    cellule = grille.getCellule(hauteur-1,0);
    if(cellule.getValeur()!=-1){
      List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteur-1, 0)));
      resultat = creerResultat(true, listeCoordonnee, idx);
      if(!historiqueAides.aideDejaPresente(resultat)){
        return resultat;
      }
    }
    cellule = grille.getCellule(0,largeur-1);
    if(cellule.getValeur()!=-1){
      List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(0, largeur-1)));
      resultat = creerResultat(true, listeCoordonnee, idx);
      if(!historiqueAides.aideDejaPresente(resultat)){
        return resultat;
      }
    }
    cellule = grille.getCellule(hauteur-1,largeur-1);
    if(cellule.getValeur()!=-1){
      List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteur-1, largeur-1)));
      resultat = creerResultat(true, listeCoordonnee, idx);
      if(!historiqueAides.aideDejaPresente(resultat)){
        return resultat;
      }
    }
    return new ResultatTechnique(false);
  }

  /**
   * Méthode pour créer un résultat technique
   *
   * @param trouve le booléen qui indique si la technique a été trouvée
   * @param listeCoordonnee la liste des coordonnées de la technique
   * @param idxTechnique l'index de la technique dans la liste des techniques
   * @return le résultat technique
   */
  public ResultatTechnique creerResultat(boolean trouve, List<Coordonnee> listeCoordonnee, int idxTechnique){
    return new ResultatTechnique(
        trouve,
        new HashSet<>(listeCoordonnee),
        idxTechnique,
        nomTechnique,
        nomTechniqueStylise,
        difficulte
    );
  }
}
