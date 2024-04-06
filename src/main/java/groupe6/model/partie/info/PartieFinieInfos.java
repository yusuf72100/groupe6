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
     * La largeur du puzzle terminé
     */
    private final int largeur;

    /**
     * La longueur du puzzle terminé
     */
    private final int longeur;

    /**
     * Constructeur de la classe PartieFinieInfos
     *
     * @param infos les informations de la partie
     * @param difficulte la difficulté du puzzle
     */
    public PartieFinieInfos(PartieInfos infos, DifficultePuzzle difficulte, int largeur, int longeur){
        super(
            infos.getChrono(),
            infos.getScore(),
            infos.getModeJeu(),
            infos.getComplete(),
            infos.getLimiteTemps()
        );
        this.difficulte = difficulte;
        this.largeur = largeur;
        this.longeur = longeur;
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
     * Méthode pour obtenir une représentation textuelle de la partie finie
     *
     * @return une représentation textuelle de la partie finie
     */
    public String toString(){
        return  "Partie finie : " + this.getComplete() + "\n" +
                "Difficulté : " + this.difficulte + "\n" +
                "Taille : "+this.largeur+"x"+this.longeur+"\n" +
                "Date : " + this.getDate() + "\n" +
                "Chrono : " + PartieInfos.formatDuration(this.getChrono()) + "\n" +
                "Score : " + this.getScore() + "\n" +
                "Mode de jeu : " + this.getModeJeu() + "\n" +
                "Limite de temps : " + PartieInfos.formatDuration(this.getLimiteTemps()) + "\n";
    }

}
