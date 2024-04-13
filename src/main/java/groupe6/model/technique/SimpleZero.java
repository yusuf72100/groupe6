package groupe6.model.technique;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.partie.aide.HistoriqueAides;

public class SimpleZero extends Technique{

    private static SimpleZero instance;

    /**
     * Constructeur privé SimpleZero
     */
    private SimpleZero(){
        super(
            DifficulteTechnique.DEMARRAGE,
            "simple zero"
        );
    }

    /**
     * Méthode pour obtenir l'instance de SimpleZero (singleton)
     * @return singleton SimpleZero
     */
    public static SimpleZero getInstance(){
        if(instance == null){
            instance= new SimpleZero();
        }
        return instance;
    }

    //Detection technique des simples zeros (technique 1 sur conceptis)
    public ResultatTechnique run(Partie partie, int idx){
        Puzzle grille=partie.getPuzzle();
        List<Coordonnee> tousLes0=rechercherNumero(partie.getPuzzle(),0);
;       List<Coordonnee> coordonneesTechniques = new ArrayList<Coordonnee>();
        ResultatTechnique resultat;
        HistoriqueAides historiqueAides = partie.getHistoriqueAide();
            for(Coordonnee c : tousLes0){
                Cellule cour = grille.getCellule(c.getY(),c.getX());
                //Vérifie si toutes les croix ont été placé
                if (cour.getValeur() == 0 && ((cour.getCote(Cellule.HAUT) != ValeurCote.CROIX)
                        || (cour.getCote(Cellule.BAS) != ValeurCote.CROIX)
                        || (cour.getCote(Cellule.GAUCHE) != ValeurCote.CROIX)
                        || (cour.getCote(Cellule.DROITE) != ValeurCote.CROIX))) {
                    coordonneesTechniques.add(c);
                    resultat = creerResultat(true, coordonneesTechniques, idx);
                    //Si l'aide n'est pas déjà présente dans l'historique, on la renvoie
                    if(!historiqueAides.aideDejaPresente(resultat)){
                        return resultat;
                    }
                    coordonneesTechniques.clear();
                }
            }
        //Pas de technique trouvée
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
        
