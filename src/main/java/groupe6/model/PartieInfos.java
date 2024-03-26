package groupe6.model;

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
    @Serial
    private static final long serialVersionUID = 1L;

    private final Date date; // Date de création de la partie
    private int score; // Score de la partie
    private Duration chrono; // Chrono de la partie ( format HH:mm:ss )
    private final ModeJeu modeJeu; // Mode de jeu de la partie
    private final Duration limiteTemps; // Limite de temps de la partie selon le mode de jeu

    /**
     * Constructeur de la classe PartieInfos ( pas de limite de temps )
     *
     * @param chrono Le chrono de la partie
     * @param score Le score de la partie
     * @param mode Le mode de jeu de la partie
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
     * @param chrono Le chrono de la partie
     * @param score Le score de la partie
     * @param mode Le mode de jeu de la partie
     * @param limiteTemps La limite de temps de la partie
     */
    public PartieInfos(Duration chrono, int score, ModeJeu mode, Duration limiteTemps){
        this.date = new Date();
        this.score = score;
        this.chrono = chrono;
        this.modeJeu = mode;
        this.limiteTemps = limiteTemps;
    }

    /**
     * Getter de l'attribut date
     * @return La date de la partie
     */
    public Date getDate(){
        return date;
    }

    /**
     * Méthode qui permet de convertir la date de la partie en String
     * @return La date de la partie au format String
     */
    public String dateToString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.date);
    }

    /**
     * Méthode qui permet de convertir la date de la partie en String avec des tirets
     * @return La date de la partie au format String avec des tirets
     */
    public String dateToStringTiret(){
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        return dateFormat.format(this.date);
    }

    /**
     * Getter de l'attribut score
     * @return Le score de la partie
     */
    public int getScore(){
        return score;
    }

    /**
     * Setter de l'attribut score
     * @param scorePartie Le score de la partie
     */
    public void setScore(int scorePartie){
        this.score = scorePartie;
    }

    /**
     * Getter de l'attribut chrono
     * @return Le chrono de la partie
     */
    public Duration getChrono(){
        return chrono;
    }

    /**
     * Setter de l'attribut chrono
     * @param chrono Le chrono de la partie
     */
    public void setChrono(Duration chrono){
        this.chrono = chrono;
    }

    /**
     * Getter de l'attribut modeJeu
     * @return Le mode de jeu de la partie
     */
    public ModeJeu getModeJeu(){
        return modeJeu;
    }

    /**
     * Getter de l'attribut limiteTemps
     * @return La limite de temps de la partie
     */
    public Duration getLimiteTemps(){
        return limiteTemps;
    }

    /**
     * Méthode qui permet d'afficher les informations de la partie
     */
    public String toString() {
        return
        "Date : " + this.getDate() + "\n" +
        "Chrono : " + this.getChrono() + "\n" +
        "Score : " + this.getScore() + "\n" +
        "Mode de jeu : " + this.getModeJeu() + "\n" +
        "Limite de temps : " + this.getLimiteTemps() + "\n";
    }
}
