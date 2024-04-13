package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;

import java.util.List;

/**
 * Classe abstraite représentant la majorité des techniques impliquant lq présence d'un 3 dans la grille.
 *
 * @author Matéo
 * */
public abstract class TechniquePattern3 extends Technique{

    /**
     * Liste des coordonnées de tous les 3 présents sur la grille
     */
    protected List<Coordonnee> toutLes3;


    /**
     * Constructeur de la classe abstraite TechniquePattern3
     *
     * @param uneDifficulte difficulté de la technique
     * @param nomTechniqueStylise nom stylisé de la technique
     */
    public  TechniquePattern3(DifficulteTechnique uneDifficulte, String nomTechniqueStylise){
        super(
            uneDifficulte,
            nomTechniqueStylise
        );
    }

    /**
     * Détection de tous les 3 présents sur la grille et rangement de leurs coordonnées dans une liste
     * @param partie La partie comportant la grille à analyser.
     * @param idx Index d'ordre de la technique
     * @return un ResultatTechnique vide.
     */
    @Override
    public ResultatTechnique run(Partie partie, int idx) {
        this.toutLes3 = Technique.rechercherNumero(partie.getPuzzle(), 3);
        return new ResultatTechnique(false);
    }
}
