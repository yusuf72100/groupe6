package groupe6.model;

import java.util.Stack;

/**
 * Cette classe modélise les entrainement.
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
    public Entrainement(Puzzle puzzle, Profil profil) {
        super(puzzle, ModeJeu.ENTRAINEMENT, profil);

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
    // Méthode pour faire une action de type bascule à trois etats
    public void actionBasculeTroisEtat(int y, int x, int cote) {
        Cellule cellule1 = this.getPuzzle().getCellule(y, x);
        Cellule cellule2 = this.getPuzzle().getCelluleAdjacente(y, x, cote);
        ValeurCote nouvelleValeurCote = cellule1.basculeTroisEtats(cote);

        Action action = new Action(cellule1, cellule2, cote, nouvelleValeurCote);
        if (listeAction.peek() == action) {
            this.getGestionnaireAction().ajouterAction(action);
            action.appliquerAction();

            this.listeAction.pop();
        }

    }

}
