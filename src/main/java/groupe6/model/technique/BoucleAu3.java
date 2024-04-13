package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.cellule.Cellule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe concrete qui représente la technique a appliquer lorsque la boucle atteint un 3
 *
 * @author Mateo
 */
public class BoucleAu3 extends TechniquePattern3{

    /**
     * Instance unique de la classe BoucleAu3
     */
    private static BoucleAu3 instance;

    /**
     * Méthode pour obtenir l'instance unique de la classe BoucleAu3
     *
     * @return l'instance unique de la classe BoucleAu3
     */
    public static BoucleAu3 getInstance(){
        if(instance == null){
            instance = new BoucleAu3();
        }
        return instance;
    }

    /**
     * Constructeur de la classe BoucleAu3
     */
    private BoucleAu3() {
        super(
            DifficulteTechnique.BASIQUE,
            "boucle atteint un 3"
        );
    }

    /**
     * Methode run exectuté lors de l'appel de la technique
     *
     * @param partie La partie comportant la grille à analyser.
     * @param idx Index d'ordre de la technique
     * @return un ResultatTechnique à vrai si la méthode a détecté un schema incomplet.
     */
    @Override
    public ResultatTechnique run(Partie partie, int idx) {
        super.run(partie, idx);
        for(Coordonnee unePosition : this.toutLes3) {
            Cellule cellule = partie.getPuzzle().getCellule(unePosition.getY(), unePosition.getX());
            if(!cellule.maxTrait() && rechercherBoucleAutour(partie.getPuzzle(), unePosition)){
                Set<Coordonnee> casesConcernees = new HashSet<>(Arrays.asList(unePosition));
                ResultatTechnique resultat = new ResultatTechnique(
                    true,
                    casesConcernees,
                    0,
                    nomTechnique,
                    nomTechniqueStylise,
                    difficulte
                );
                if(!partie.getHistoriqueAide().aideDejaPresente(resultat)) return resultat;
            }
        }
        return new ResultatTechnique(false);
    }
}
