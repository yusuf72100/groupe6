package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;

import java.util.*;

/**
 * Méthode run qui parcourt la grille à la recherche de la technique zéro et trois en diagonales
 *
 * @author Mateo
 */
public class Diagonales03 extends TechniquePattern3 {

    private static Diagonales03 instance;

    /**
     * Constructeur privé Diagonales03
     */
    private Diagonales03() {
        super(
                DifficulteTechnique.DEMARRAGE,
                "zéro et trois en diagonale"
        );
    }

    /**
     * Méthode pour obtenir l'instance de la classe (singleton)
     * @return singleton Diagonales03
     */
    public static Diagonales03 getInstance(){
        if(instance==null){
            instance = new Diagonales03();
        }
        return instance;
    }

    /**
     * Methode run exectuté lors de l'appelle de la technique
     *
     * @param partie La partie comportant la grille à analyser.
     * @param idx    Index d'ordre de la technique
     * @return un ResultatTechnique à vrai si la méthode a détecté un schema incomplet sur deux 3 positionné de manière diagonales.
     */
    @Override
    public ResultatTechnique run(Partie partie, int idx) {
        super.run(partie, idx);
        for (Coordonnee coordCellule : this.toutLes3) {
            int y = coordCellule.getY();
            int x = coordCellule.getX();
            List<Coordonnee> diagonales = Technique.rechercherNumeroAutour(partie.getPuzzle(), 0, coordCellule, Direction.DIAGONAL);
            for (Coordonnee diag : diagonales) {
                if (Technique.verificationMultidir(partie.getPuzzle(), Arrays.asList(coordCellule, diag), ZERO)) {
                    Set<Coordonnee> casesConcernees = new HashSet<Coordonnee>(Arrays.asList(coordCellule, diag));
                    ResultatTechnique resultat = new ResultatTechnique(
                            true,
                            casesConcernees,
                            idx,
                            nomTechnique,
                            nomTechniqueStylise,
                            difficulte
                    );
                    if (!(partie.getHistoriqueAide().aideDejaPresente(resultat))) return resultat;
                }
            }
        }
        return new ResultatTechnique(false);
    }
}