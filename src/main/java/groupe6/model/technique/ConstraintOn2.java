package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe de la technique contraintes sur 2
 *
 * @author Tom MARSURA
 */
public class ConstraintOn2 extends Technique {

    /**
     * L'instance unique de la classe ConstraintOn2
     */
    private static ConstraintOn2 instance;

    /**
     * Méthode pour obtenir l'instance de la classe ConstraintOn2 (singleton)
     *
     * @return singleton ConstraintOn2
     */
    public static ConstraintOn2 getInstance(){
        if(instance == null){
            instance = new ConstraintOn2();
        }
        return instance;
    }

    /**
     * Constructeur de la technique contraintes sur 2
     */
    private ConstraintOn2(){
        super(
            DifficulteTechnique.BASIQUE,
            "contraintes sur un 2"
        );
    }

    /**
     * Méthode de détection de la technique contraintes sur 2
     * @param partie la partie sur laquelle on cherche la technique
     * @param idxTechnique l'index de la technique dans la liste des techniques
     * @return ResultatTechnique avec les coordonnées de la technique détéctée
     */
    @Override
    public ResultatTechnique run(Partie partie, int idxTechnique){
        Puzzle grille = partie.getPuzzle();
        int largeur = grille.getLargeur() - 1;
        int longueur = grille.getLongueur() -1;

        for(int i = 1; i < largeur; i++){
            int haut = grille.getCellule(0, i).getValeur();
            int bas = grille.getCellule(largeur, i).getValeur();
            int droite = grille.getCellule(i, longueur).getValeur();
            int gauche = grille.getCellule(i, 0).getValeur();

            // Bordure du haut
            if(haut == 2){
                if(i == 1){
                    if((grille.getCellule(0, 0).getValeur() == 1
                    || grille.getCellule(0, 0).getCote(Cellule.HAUT) == ValeurCote.CROIX)
                    && grille.getCellule(0, i+1).getCote(Cellule.HAUT) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(0, i), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
                if (i == largeur - 1){
                    if((grille.getCellule(0, largeur).getValeur() == 1
                    || grille.getCellule(0, largeur).getCote(Cellule.HAUT) == ValeurCote.CROIX)
                    && grille.getCellule(0, i-1).getCote(Cellule.HAUT) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(0, i), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
            }

            // Bordure du bas
            if(bas == 2){
                if(i == 1) {
                    if ((grille.getCellule(largeur, 0).getValeur() == 1
                            || grille.getCellule(largeur, 0).getCote(Cellule.BAS) == ValeurCote.CROIX)
                            && grille.getCellule(0, i + 1).getCote(Cellule.BAS) != ValeurCote.TRAIT) {
                        ResultatTechnique result = creerResultat(new Coordonnee(largeur, i), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
                if(i == largeur -1){
                    if((grille.getCellule(largeur, largeur).getValeur() == 1
                    || grille.getCellule(largeur, largeur).getCote(Cellule.BAS) == ValeurCote.CROIX)
                    && grille.getCellule(largeur, i-1).getCote(Cellule.BAS) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(largeur, i), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
            }

            // Bordure de gauche
            if(gauche == 2){
                if(i == 1){
                    if((grille.getCellule(0, 0).getValeur() == 1
                    || grille.getCellule(0, 0).getCote(Cellule.GAUCHE) == ValeurCote.CROIX)
                    && grille.getCellule(i+1, 0).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(i, 0), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
                if(i == largeur - 1){
                    if((grille.getCellule(largeur, 0).getValeur() == 1
                    || grille.getCellule(largeur, 0).getCote(Cellule.GAUCHE) == ValeurCote.CROIX)
                    && grille.getCellule(i-1, 0).getCote(Cellule.GAUCHE) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(i, 0), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }
            }

            // Bordure de droite
            if (droite == 2){
                if(i == 1){
                    if((grille.getCellule(0, largeur).getValeur() == 1
                    || grille.getCellule(0, largeur).getCote(Cellule.DROITE) == ValeurCote.CROIX)
                    && grille.getCellule(i+1, largeur).getCote(Cellule.DROITE) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(i, largeur), idxTechnique);
                        if(!partie.getHistoriqueAide().aideDejaPresente(result)){
                            return result;
                        }
                    }
                }

                if(i == largeur - 1){
                    if((grille.getCellule(largeur, largeur).getValeur() == 1
                    || grille.getCellule(largeur, largeur).getCote(Cellule.DROITE) == ValeurCote.CROIX)
                    && grille.getCellule(i-1, largeur).getCote(Cellule.DROITE) != ValeurCote.TRAIT){
                        ResultatTechnique result = creerResultat(new Coordonnee(i, largeur), idxTechnique);
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
     * Méthode de création d'un résultat avec les coordonnées
     * @param position Position de la technique
     * @param indexTechnique numéro de la technique
     * @return Le ResultatTechnique contenant les coordonnées
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
