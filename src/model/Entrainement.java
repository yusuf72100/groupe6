import java.util.List;
import java.util.Stack;
import javax.swing.Action;

/**
 * Cette classe mod\u00e8lise les entrainement.
 * 
 * @author William Sardon
 */
public class Entrainement extends Partie {
    /** liste d'action pouvant etre realisées pour finir l'entrainement */
    private Stack<Action> listeAction;

    /** Constructeur de la classe Entrainement */
    public Entrainement(Puzzle puzzle) {
        super(puzzle);

        listeAction = new Stack<Action>();
    }

    public void ajouterActionSolution(Action nouvelleAction) {
        listeAction.push(nouvelleAction);
    }

    public Action effacerActionSolution() {
        return listeAction.pop();
    }

    @Override
    public void actionBasculeTroisEtats(int x, int y) {
        Action action = new Action(/* a remplir */);
        if (listeAction.peek() == action) {
            gestionnaireAction.ajouterAction(action);
            action.appliquerAction();

            this.listeAction.pop();
        }
    }

}
