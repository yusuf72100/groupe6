package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import javax.xml.transform.Result;
import java.util.Arrays;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe de la technique zéro et trois adjacent
 * Numéro de technique : 1
 * @author Tom MARSURA
 */
public class zeroTroisAdjacents extends Technique {

    //TODO
    /**
     *
     */
    private boolean VERTI = true;

    // TODO
    /**
     *
     */
    private boolean HORIZ = false;

    /**
     * Constructeur de la classe de la zeroTroisAdjacents
     *
     * @param uneDifficulte difficulté de la technique
     */
    public zeroTroisAdjacents(DifficulteTechnique uneDifficulte) {
        super(uneDifficulte);
    }

    /**
     * Méthode run qui parcourt la grille à la recherche de la technique zéro et trois adjacents
     * @param partie Partie en cours
     * @return ResultatTechnique renvoit les coordonées de la technique si elle est trouvée et sinon, renvoi un ResultatTechnique vide
     */
    @Override
    public ResultatTechnique run(Partie partie, int idxTechnique) {
        Puzzle grille = partie.getPuzzle();

        for(int i = 0; i < grille.getLargeur(); i++){
            for(int j = 0; j < grille.getLongueur(); j++){
                int valeur = grille.getCellule(i, j).getValeur();
                if(valeur == 0){
                    int gauche = grille.getCellule(i, j-1).getValeur();
                    int bas = grille.getCellule(i+1, j).getValeur();
                    int droite = grille.getCellule(i, j+1).getValeur();
                    int haut = grille.getCellule(i-1, j).getValeur();
                    if(gauche == 3){
                        System.out.println("gauche = 3");
                        Coordonnee trois = new Coordonnee(i, j-1);
                        Coordonnee zero = new Coordonnee(i, j);
                        if(verifZeroTroisAdjacent(grille, Arrays.asList(trois, zero), HORIZ)) {
                            System.out.println("verifZeroTroisAdjacents1");
                            ResultatTechnique result = creerResultat(Arrays.asList(trois, zero), idxTechnique);
                            if ( !partie.getHistoriqueAide().aideDejaPresente(result)) {
                                return result;
                            }
                        }
                    } else if(haut == 3){
                        System.out.println("haut = 3");
                        Coordonnee zero = new Coordonnee(i, j);
                        Coordonnee trois = new Coordonnee(i-1, j);
                        if(verifZeroTroisAdjacent(grille, Arrays.asList(zero, trois), VERTI)){
                            System.out.println("verifZeroTroisAdjacents2");
                            ResultatTechnique result = creerResultat(Arrays.asList(zero, trois), idxTechnique);
                            if ( !partie.getHistoriqueAide().aideDejaPresente(result)) {
                                return result;
                            }
                        }
                    } else if (droite == 3){
                        System.out.println("droite = 3");
                        Coordonnee zero = new Coordonnee(i, j);
                        Coordonnee trois = new Coordonnee(i, j+1);
                        if(verifZeroTroisAdjacent(grille, Arrays.asList(zero, trois), HORIZ)){
                            System.out.println("verifZeroTroisAdjacents3");
                            ResultatTechnique result = creerResultat(Arrays.asList(zero, trois), idxTechnique);
                            if ( !partie.getHistoriqueAide().aideDejaPresente(result)) {
                                return result;
                            }
                        }
                    } else if (bas == 3){
                        System.out.println("bas = 3");
                        Coordonnee zero = new Coordonnee(i, j);
                        Coordonnee trois = new Coordonnee(i+1, j);
                        if(verifZeroTroisAdjacent(grille, Arrays.asList(trois, zero), VERTI)){
                            System.out.println("verifZeroTroisAdjacents4");
                            ResultatTechnique result = creerResultat(Arrays.asList(trois, zero), idxTechnique);
                            if ( !partie.getHistoriqueAide().aideDejaPresente(result)) {
                                return result;
                            }
                        }
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
        return new ResultatTechnique(true, ListePositions, idxTechnique);
    }

    /**
     * Méthode qui permet de vérifier si les traits d'un zéro et trois adjacents sont corretement placés. La méthode lit de bas en haut si le sens et VERTI (true) et de gauche à droite si le sens est HORIZ (false).
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
            if(grille.getCellule(coord1.getY(), coord2.getX()).getValeur() == 0){ // Le trois est au dessus
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
            } else { // Le zéro est au dessus
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
