package org.groupe6.slitherlink.PuzzleGenerator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * Classe PartieInfos
 * Cette classe permet de stocker les informations d'une partie
 * 
 * @author Tom MARSURA
 */
public class PartieInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date date;
    private int score;
    private Duration chrono;
    private boolean complete;
    private ModeJeu modeJeu;

    /**
     * Constructeur de la classe PartieInfos
     * 
     * @param chrono   Le chrono de la partie
     * @param score    Le score de la partie
     * @param complete Si la partie est complète ou non
     * @param mode     Le mode de jeu de la partie
     */
    public PartieInfos(Duration chrono, int score, boolean complete, ModeJeu mode) {
        this.setDate();
        this.score = score;

        // Chrono au format DateFormat -> HH:mm:ss
        this.chrono = chrono;
        this.complete = complete;
        this.modeJeu = mode;
    }

    /**
     * Getter de l'attribute date
     * 
     * @return La date de la partie
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter de l'attribute date
     */
    public void setDate() {
        this.date = new Date();
    }

    /**
     * Initialisation de la date à la date actuelle
     * 
     * @return La date actuelle
     */
    public Date initDate() {
        Date dateActuelle = new Date();
        return dateActuelle;
    }

    /**
     * Convertir la date en String
     * 
     * @return
     */
    public String dateToString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    /**
     * Getter de l'attribute score
     * 
     * @return Le score de la partie
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter de l'attribute score
     * 
     * @param scorePartie Le score de la partie
     */
    public void setScore(int scorePartie) {
        this.score = scorePartie;
    }

    /**
     * Getter de l'attribute chrono
     * 
     * @return Le chrono de la partie
     */
    public Duration getChrono() {
        return chrono;
    }

    /**
     * Setter de l'attribute chrono
     * 
     * @param chrono Le chrono de la partie
     */
    public void setChrono(Duration chrono) {
        this.chrono = chrono;
    }

    /**
     * Getter de l'attribute complete
     * 
     * @return Si la partie est complète ou non
     */
    public boolean getComplete() {
        return complete;
    }

    /**
     * Setter de l'attribute complete
     * 
     * @param complete Si la partie est complète ou non
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Getter de l'attribute modeJeu
     * 
     * @return Le mode de jeu de la partie
     */
    public ModeJeu getModeJeu() {
        return modeJeu;
    }
}
