package groupe6.model;

import java.time.Duration;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe PartieInfos
 * Cette classe permet de stocker les informations d'une partie
 * @author Tom MARSURA
 */
public class PartieInfos implements Serializable{
    private static final long serialVersionUID = 1L;

    private Date date;
    private int score;
    private Duration chrono;
    private ModeJeu modeJeu;
    private Duration limiteTemps;

    /**
     * Constructeur de la classe PartieInfos
     * 
     * @param chrono Le chrono de la partie
     * @param score Le score de la partie
     * @param mode Le mode de jeu de la partie
     * @param limiteTemps La limite de temps de la partie
     */
    public PartieInfos(Duration chrono, int score, ModeJeu mode, Duration limiteTemps){
        this.initDate();
        this.score = score;

        //Chrono au format DateFormat -> HH:mm:ss
        this.chrono = chrono;
        this.modeJeu = mode;
    }

    /**
     * Getter de l'attribut date
     * @return La date de la partie
     */
    public Date getDate(){
        return date;
    }

    /**
     * Setter de l'attribut date
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Méthode qui permet d'initialiser la date de la partie
     */
    public void initDate(){
        this.date = new Date();
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
     * @return
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
     * @return
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
