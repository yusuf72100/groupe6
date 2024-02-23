import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;

/**
 * Cette classe mod\u00e8lise les entrainement.
 * 
 * @author William Sardon
 */
public class Entrainement extends Partie {
    /** liste d'action pouvant etre realisées pour finir l'entrainement */
    private List<Action> listeActionSolution;

    /** Constructeur de la classe Entrainement */
    public Entrainement(DifficultePuzzle difficulte, int largeur, int longueur, Cellule[][] grilleCellules) {
        super(difficulte, largeur, longueur, grilleCellules);

        listeActionSolution = new ArrayList<Action>();
    }

    public void ajouterActionSolution(Action nouvelleAction) {
        listeActionSolution.add(nouvelleAction);
    }

    @Override
    public void ajouterAction(Action nouvelleAction) {
        if (listeAction.contains(nouvelleAction)) {
            // ajouter l'action
        }
    }

}
