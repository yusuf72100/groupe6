package groupe6.model.partie.info;

import groupe6.model.partie.puzzle.DifficultePuzzle;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe qui permet de stocker les informations d'une partie finie
 * Cette classe hérite de la classe PartieInfos
 * 
 * @see PartieInfos
 * @author Tom MARSURA
 */
public class PartieFinieInfos extends PartieInfos implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * La difficulté du puzzle terminé
     */
    private final DifficultePuzzle difficulte;

    /**
     * Boolean qui indique si la partie est complète
     */
    private final boolean  complete;

    /**
     * Boolean qui indique si la partie est gagnée
     */
    private final boolean gagner;

    /**
     * Constructeur de la classe PartieFinieInfos
     *
     * @param infos les informations de la partie
     * @param difficulte la difficulté du puzzle
     * @param complete boolean qui indique si la partie est complète
     * @param gagner boolean qui indique si la partie est gagnée
     */
    public PartieFinieInfos(PartieInfos infos, DifficultePuzzle difficulte, boolean complete, boolean gagner){
        super(infos.getChrono(), infos.getScore(),  infos.getModeJeu(), infos.getLimiteTemps());
        this.difficulte = difficulte;
        this.complete = complete;
        this.gagner = gagner;
    }

    /**
     * Méthode pour obtenir la date de la partie finie
     *
     * @return la date de la partie finie
     */
    public DifficultePuzzle getDifficulte(){
        return difficulte;
    }

    /**
     * Méthode pour le boolean qui indique si la partie est complète
     *
     * @return le boolean qui indique si la partie est complète
     */
    public boolean getComplete(){
        return complete;
    }

    /**
     * Méthode pour obtenir le boolean qui indique si la partie est gagnée
     *
     * @return le boolean qui indique si la partie est gagnée
     */
    public boolean getGagner(){
        return gagner;
    }

    /**
     * Méthode pour obtenir une représentation textuelle de la partie finie
     *
     * @return une représentation textuelle de la partie finie
     */
    public String toString(){
        return "Partie finie : " + this.complete + "\n" +
        "Difficulté : " + this.difficulte + "\n" +
        "Gagner : " + this.gagner + "\n" +
        "Date : " + this.getDate() + "\n" +
        "Chrono : " + this.getChrono() + "\n" +
        "Score : " + this.getScore() + "\n" +
        "Mode de jeu : " + this.getModeJeu() + "\n" +
        "Limite de temps : " + this.getLimiteTemps() + "\n";
    }

}
