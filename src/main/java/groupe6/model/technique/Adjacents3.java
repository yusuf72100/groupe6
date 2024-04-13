package groupe6.model.technique;

import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.Coordonnee;

import java.util.*;

/**
 * Classe concrete qui représente la technique des 3 adjacents
 *
 * @author Mateo
 */
public class Adjacents3 extends TechniquePattern3{

    /**
     * L'instance unique de la classe Adjacents3
     */
    private static Adjacents3 instance;

    /**
     * Méthode pour obtenir l'instance de la classe Adjacents3 (singleton)
     *
     * @return singleton Adjacents3
     */
    public static Adjacents3 getInstance(){
        if(instance==null){
            instance = new Adjacents3();
        }
        return instance;
    }

    /**
     * Constructeur de la classe Adjacents3
     */
    private Adjacents3() {
        super(
            DifficulteTechnique.DEMARRAGE,
            "3 adjacents"
        );
    }

    /**
     * Methode run exectuté lors de l'appelle de la technique
     *
     * @param partie La partie comportant la grille à analyser.
     * @param idx Index d'ordre de la technique
     * @return un ResultatTechnique à vrai si la méthode a détecté un schema incomplet sur deux 3 positionné de manière adjacentes.
     */
    @Override
    public ResultatTechnique run(Partie partie, int idx) {
        super.run(partie, idx);
        for(Coordonnee coordCellule : this.toutLes3) {
            int y = coordCellule.getY();
            int x = coordCellule.getX();
            List<Coordonnee> adjacents = Technique.rechercherNumeroAutour(partie.getPuzzle(), 3, coordCellule, Direction.ADJACENT);
            for(Coordonnee adj : adjacents) {
                if(Technique.verificationMultidir(partie.getPuzzle(), Arrays.asList(coordCellule,adj), TROIS )){
                    Set<Coordonnee> casesConcernees = new HashSet<Coordonnee>(Arrays.asList(coordCellule,adj));
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
