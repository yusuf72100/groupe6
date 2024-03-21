package groupe6.model;

import java.time.Duration;

/**
 * Classe qui permet de stocker les informations d'une partie finie
 * Cette classe hérite de la classe PartieInfos
 * 
 * @see PartieInfos
 * @author Tom MARSURA
 */
public class PartieFinieInfos extends PartieInfos {

    private DifficultePuzzle difficulte;
    boolean complete;
    boolean gagner;

    /**
     * Constructeur de la classe PartieFinieInfos
     * @param infos
     * @param difficulte
     * @param complete
     * @param gagner
     */
    public PartieFinieInfos(PartieInfos infos, DifficultePuzzle difficulte, boolean complete, boolean gagner){
        super(infos.getChrono(), infos.getScore(),  infos.getModeJeu(), infos.getLimiteTemps());
        this.difficulte = difficulte;
        this.complete = complete;
        this.gagner = gagner;
    }

    /**
     * Getter de l'attribute difficulte
     * @return
     */
    public DifficultePuzzle getDifficulte(){
        return difficulte;
    }

    /**
     * Getter de l'attribute complete
     * @return
     */
    public boolean getComplete(){
        return complete;
    }

    /**
     * Getter de l'attribute gagner
     * @return
     */
    public boolean getGagner(){
        return gagner;
    }

    /**
     * Méthode qui permet d'afficher les informations de la partie finie
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
