package model;

import java.util.List;
import java.util.Stack;
import javax.swing.Action;

/**
 * Cette classe mod\u00e8lise les entrainement.
 * Elle herite de la classe Partie
 * 
 * @author William Sardon
 */
public class Entrainement extends Partie {
    /** liste d'action pouvant etre realisées pour finir l'entrainement */
    private Stack<Action> listeAction;

    /**
     * Constructeur de la classe Entrainement
     * 
     * @param puzzle Puzzle de l'entrainement
     */
    public Entrainement(Puzzle puzzle) {
        super(puzzle);

        listeAction = new Stack<Action>();
    }

    /**
     * Methode d'ajout a la pile d'action solution de l'entrainement
     * 
     * @param nouvelleAction L'action a a ajouter au dessus de la pile dans la liste
     *                       d'action
     */
    public void ajouterActionSolution(Action nouvelleAction) {
        listeAction.push(nouvelleAction);
    }

    /**
     * Methode de suppression a la pile d'action solution de l'entrainement
     * 
     * @return L'action retiree au dessus de la pile dans la liste d'action
     */
    public Action effacerActionSolution() {
        return listeAction.pop();
    }

    /**
     * 
     * @param x
     * @param y
     */
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
