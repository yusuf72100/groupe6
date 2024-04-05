package groupe6.model.partie;

import groupe6.model.partie.action.GestionnaireAction;
import groupe6.model.partie.puzzle.Puzzle;

/**
 * Cette classe modélise le mode hypothèse
 *
 * @author Nathan
 */
public class Hypothese {

    /**
     * Le puzzle sur lequel on effectue notre hypothèse
     */
    private final Puzzle puzzle;

    /**
     * Le gestionnaire d'action lié au puzzle d'hypothèse
     */
    private final GestionnaireAction gestionnaireAction;

    /**
     * Constructeur de la classe Hypothese
     *
     * @param puzzle le puzzle sur lequel on effectue notre hypothèse
     * @param gestionnaire le gestionnaire d'action lié au puzzle d'hypothèse
     */
    public Hypothese(Puzzle puzzle,GestionnaireAction gestionnaire){
        this.puzzle=puzzle;
        this.gestionnaireAction=gestionnaire;
    }

    /**
     * Méthode pour obtenir le puzzle de l'hypothèse
     *
     * @return le puzzle de l'hypothèse
     */
    public Puzzle getPuzzle() {
        return puzzle;
    }

    /**
     * Méthode pour obtenir le gestionnaire d'action de l'hypothèse
     *
     * @return le gestionnaire d'action de l'hypothèse
     */
    public GestionnaireAction getGestionnaireAction() {
        return gestionnaireAction;
    }

}
