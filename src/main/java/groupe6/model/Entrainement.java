package groupe6.model;

import java.util.Objects;
import java.util.Stack;

/**
 * Cette classe modélise les entrainement.
 * Elle herite de la classe Partie
 * 
 * @author William Sardon
 */
public class Entrainement extends Partie {
    /** liste d'action pouvant etre realisées pour finir l'entrainement */
    private final Stack<Action> listeAction;

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
     * @param y La coordonnée y de la cellule
     * @param x La coordonnée x de la cellule
     * @param cote Le cote de la cellule
     */
    @Override
    // Méthode pour faire une action de type bascule à trois etats
    public void actionBasculeTroisEtat(int y, int x, int cote) {
        Cellule cellule1 = this.getPuzzle().getCellule(y, x);
        Cellule cellule2 = this.getPuzzle().getCelluleAdjacente(y, x, cote);
        ValeurCote nouvelleValeurCote = cellule1.basculeTroisEtats(cote);

        Action action = new Action(cellule1, cellule2, cote, nouvelleValeurCote, new Coordonnee(y, x));
        if (Objects.equals(listeAction.peek(), action)) {
            this.getGestionnaireAction().ajouterAction(action);
            action.appliquerAction();

            this.listeAction.pop();
        }

    }

    public static Partie chargerPartie() {
        throw new UnsupportedOperationException("Utilisé chargerEntrainement() à la place");
    }

    public static Entrainement chargerEntrainement() {
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

}
