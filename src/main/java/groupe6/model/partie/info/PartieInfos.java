package groupe6.model.partie.info;

import groupe6.model.partie.ModeJeu;

import java.io.Serial;
import java.time.Duration;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe PartieInfos
 * Cette classe permet de stocker les informations d'une partie
 * @author Tom MARSURA
 */
public class PartieInfos implements Serializable{

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * La date de création de la partie
     */
    private final Date date;

    /**
     * Le score de la partie
     */
    private int score;

    /**
     * La durée de la partie
     */
    private Duration chrono;

    /**
     * Le mode de jeu associé à la partie
     */
    private final ModeJeu modeJeu;

    /**
     * La limite de temps de la partie selon le mode de jeu
     */
    private final Duration limiteTemps;

    /**
     * Constructeur de la classe PartieInfos ( pas de limite de temps )
     *
     * @param chrono le chrono de la partie
     * @param score le score de la partie
     * @param mode le mode de jeu de la partie
     */
    public PartieInfos(Duration chrono, int score, ModeJeu mode){
        this.date = new Date();
        this.score = score;
        this.chrono = chrono;
        this.modeJeu = mode;
        this.limiteTemps = null;
    }

    /**
     * Constructeur de la classe PartieInfos ( avec limite de temps )
     * 
     * @param chrono le chrono de la partie
     * @param score le score de la partie
     * @param mode le mode de jeu de la partie
     * @param limiteTemps la limite de temps de la partie
     */
    public PartieInfos(Duration chrono, int score, ModeJeu mode, Duration limiteTemps){
        this.date = new Date();
        this.score = score;
        this.chrono = chrono;
        this.modeJeu = mode;
        this.limiteTemps = limiteTemps;
    }

    /**
     * Méthode qui permet de récupérer la date de la partie
     *
     * @return La date de la partie
     */
    public Date getDate(){
        return date;
    }

    /**
     * Méthode statique qui permet de convertir une date en String
     *
     * @param date la date à convertir
     * @return la date au format String ( dd/MM/yyyy HH:mm:ss )
     */
    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * Méthode statique qui permet de convertir une date en String
     *
     * @param date la date à convertir
     * @return la date au format String ( dd_MM_yyyy_HH_mm_ss )
     */
    public static String dateToStringTiret(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        return dateFormat.format(date);
    }

    /**
     * Méthode pour obtenir le score de la partie
     *
     * @return le score de la partie
     */
    public int getScore(){
        return score;
    }

    /**
     * Méthode pour définir le score de la partie
     *
     * @param scorePartie le score de la partie
     */
    public void setScore(int scorePartie){
        this.score = scorePartie;
    }

    /**
     * Méthode pour obtenir le chrono de la partie
     *
     * @return le chrono de la partie
     */
    public Duration getChrono(){
        return chrono;
    }

    /**
     * Méthode pour définir le chrono de la partie
     *
     * @param chrono le chrono de la partie
     */
    public void setChrono(Duration chrono){
        this.chrono = chrono;
    }

    /**
     * Méthode pour obtenir le mode de jeu de la partie
     *
     * @return le mode de jeu de la partie
     */
    public ModeJeu getModeJeu(){
        return modeJeu;
    }

    /**
     * Méthode pour obtenir la limite de temps de la partie
     *
     * @return la limite de temps de la partie
     */
    public Duration getLimiteTemps(){
        return limiteTemps;
    }

    /**
     * Méthode statique pour obtenir une représentation textuelle d'une durée
     *
     * @param duration la durée a formater
     * @return la durée formatée ( HH:mm:ss )
     */
    public static String formatDuration(Duration duration) {
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Méthode pour obtenir une représentation textuelle de la partie
     *
     * @return la représentation textuelle de la partie
     */
    public String toString() {
        return
        "Date : " + this.getDate() + "\n" +
        "Chrono : " + formatDuration(this.getChrono()) + "\n" +
        "Score : " + this.getScore() + "\n" +
        "Mode de jeu : " + this.getModeJeu() + "\n" +
        "Limite de temps : " + formatDuration(this.getLimiteTemps()) + "\n";
    }

    /**
     * Méthode pour enlever des points au score
     * Score minimum possible : 50
     *
     * @param points les points à enlever
     */
    public void enleverPoints(int points){
        this.score -= points;
        if ( this.score < Score.MIN_SCORE ){
            this.score = Score.MIN_SCORE;
        }
    }
}
