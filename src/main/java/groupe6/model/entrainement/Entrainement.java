package groupe6.model.entrainement;

import groupe6.model.partie.action.Action;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.profil.Profil;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;

import java.util.Objects;
import java.util.Stack;

/**
 * Cette classe modélise les entrainement.
 * Elle herite de la classe Partie
 * 
 * @author William Sardon
 */
public class Entrainement extends Partie {

    /**
     * liste d'action pouvant etre realisées pour finir l'entrainement
     */
    private final Stack<Action> listeAction;

    /**
     * Constructeur de la classe Entrainement
     * 
     * @param puzzle le puzzle de l'entrainement
     * @param profil le profil qui joue l'entrainement
     */
    public Entrainement(Puzzle puzzle, Profil profil) {
        super(puzzle, ModeJeu.ENTRAINEMENT, profil);

        listeAction = new Stack<Action>();
    }

    /**
     * Methode d'ajout a la pile d'action solution de l'entrainement
     * 
     * @param nouvelleAction l'action a ajouter a la pile d'action
     */
    public void ajouterActionSolution(Action nouvelleAction) {
        listeAction.push(nouvelleAction);
    }

    /**
     * Methode de suppression a la pile d'action solution de l'entrainement
     * 
     * @return l'action retiree du sommet de la pile d'action
     */
    public Action effacerActionSolution() {
        return listeAction.pop();
    }

    /**
     * Méthode pour effectuer une action de type bascule à trois etats
     * 
     * @param y la position en y de la cellule
     * @param x la position en x de la cellule
     * @param cote le cote de la cellule sur lequel on effectue l'action
     */
    @Override
    public void actionBasculeTroisEtat(int y, int x, int cote) {
        Cellule cellule1 = this.getPuzzle().getCellule(y, x);
        Cellule cellule2 = this.getPuzzle().getCelluleAdjacente(y, x, cote);
        ValeurCote nouvelleValeurCote = cellule1.basculeTroisEtats(cote);
        Action action = new Action(cellule1, cellule2, cote, nouvelleValeurCote, new Coordonnee(y, x));

        // On vérifie que l'action soit bien la prochaine action attendue
        if (Objects.equals(listeAction.peek(), action)) {
            super.actionBasculeTroisEtat(y, x, cote);
            this.listeAction.pop();
        }

    }

    /**
     * Méthode pour charger un entrainement
     *
     * @return l'entrainement chargé
     */
    public static Entrainement chargerEntrainement() {
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

}
