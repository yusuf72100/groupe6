package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



public class Coin03 extends Technique{

    private static Coin03 instance;

    /**
     * Constructeur privé Coin03
     */
    private Coin03(){
        super(
            DifficulteTechnique.BASIQUE,
            "coin 03"
        );
    }


    /**
     * Méthode pour obtenir l'instance de la classe (singleton)
     * @return singleton Coin0Trois
     */
    public static Coin03 getInstance(){
        if(instance==null){
            instance = new Coin03();
        }
        return instance;
    }

    /**
     * Méthode qui parcourt la grille et détecte la technique
     * @param partie la partie sur laquelle on cherche la technique
     * @param idx l'index de la technique dans la liste des techniques
     * @return un ResultatTechnique
     */
    public ResultatTechnique run(Partie partie, int idx){
        Puzzle grille=partie.getPuzzle();
        ResultatTechnique resultat;
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
        int largeurPuzzle= grille.getLargeur();
        int hauteurPuzzle= grille.getLongueur();
        //Vérifie dans le coin haut gauche
        if(grille.getCellule(0, 0).getValeur()==0){
            //Vérifie horizontalement
            if(grille.getCellule(0,2).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(0, 2)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
            //Vérifie verticalement
            if(grille.getCellule(2,0).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(2, 0)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
        }
        //Vérifie dans le coin haut droit
        if(grille.getCellule(0, largeurPuzzle-1).getValeur()==0){
            //Vérifie verticalement
            if(grille.getCellule(0,largeurPuzzle-3).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(0, hauteurPuzzle-3)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
            //Vérifie horizontalement
            if(grille.getCellule(2,largeurPuzzle-1).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(2, largeurPuzzle-1)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
        }
        //Vérifie dans le coin bas gauche
        if(grille.getCellule(hauteurPuzzle-1, 0).getValeur()==0){
            //Vérifie horizontalement
            if(grille.getCellule(hauteurPuzzle-1,2).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteurPuzzle-1, 2)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
            //Vérifie verticalement
            if(grille.getCellule(hauteurPuzzle-3,0).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteurPuzzle-3, 0)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }

        }
        //Vérifie dans le coin bas droit
        if(grille.getCellule(hauteurPuzzle-1, largeurPuzzle-1).getValeur()==0){
            //Vérifie horizontalement
            if(grille.getCellule(hauteurPuzzle-3,largeurPuzzle-1).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteurPuzzle-3, largeurPuzzle-1)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
            //Vérifie verticalement
            if(grille.getCellule(hauteurPuzzle-1,hauteurPuzzle-3).getValeur()==3){
                List<Coordonnee> listeCoordonnee = new ArrayList<>(List.of(new Coordonnee(hauteurPuzzle-1, hauteurPuzzle-3)));
                resultat = creerResultat(true, listeCoordonnee, idx);
                if(!historiqueAides.aideDejaPresente(resultat)){
                    return resultat;
                }
            }
        }
        //Si aucune technique n'est trouvée ou que toutes les techniques ont déjà été détecté, renvoie ResultatTechnique avec false
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

