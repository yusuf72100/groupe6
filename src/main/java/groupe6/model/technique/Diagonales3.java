package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;

import java.util.*;

/**
 * Classe concrete qui représente la technique des 3 diagonales
 *
 * @author Mateo
 */
public class Diagonales3 extends TechniquePattern3{

    private static Diagonales3 instance;

    /**
     * Constructeur privé Diagonales3
     */
    private Diagonales3() {
        super(
                DifficulteTechnique.DEMARRAGE,
                "3 en diagonales"
        );
    }

    /**
     * Méthode pour obtenir l'instance de la classe (singleton)
     * @return singleton Diagonales3
     */
    public static Diagonales3 getInstance(){
        if(instance==null){
            instance = new Diagonales3();
        }
        return instance;
    }

    /**
     * Methode run exectuté lors de l'appelle de la technique
     *
     * @param partie La partie comportant la grille à analyser.
     * @param idx Index d'ordre de la technique
     * @return un ResultatTechnique à vrai si la méthode a détecté un schema incomplet sur deux 3 positionné de manière diagonales.
     */
    @Override
    public ResultatTechnique run(Partie partie, int idx) {
        super.run(partie, idx);
        for(Coordonnee coordCellule : this.toutLes3) {
            int y = coordCellule.getY();
            int x = coordCellule.getX();
            List<Coordonnee> diagonales = Technique.rechercherNumeroAutour(partie.getPuzzle(), 3, coordCellule, Direction.DIAGONAL);
            for(Coordonnee diag : diagonales) {
                if(Technique.verificationMultidir(partie.getPuzzle(), Arrays.asList(coordCellule,diag), TROIS)){
                    Set<Coordonnee> casesConcernees = new HashSet<Coordonnee>(Arrays.asList(coordCellule,diag));
                    ResultatTechnique resultat =  new ResultatTechnique(
                            true,
                            casesConcernees,
                            idx,
                            nomTechnique,
                            nomTechniqueStylise,
                            difficulte
                    );
                    if(!(partie.getHistoriqueAide().aideDejaPresente(resultat)))return resultat;
                }
            }
        }
        return new ResultatTechnique(false);
    }

}