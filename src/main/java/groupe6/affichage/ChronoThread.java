package groupe6.affichage;

import groupe6.model.partie.Chronometre;
import groupe6.model.partie.Partie;
import groupe6.model.partie.info.PartieInfos;
import groupe6.model.partie.info.Score;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.Duration;

/**
 * Classe qui permet de gérer le thread du chronomètre
 *
 * @author Yusuf
 */
public class ChronoThread implements Runnable {

    /**
     * La partie qui est reliée au chronomètre
     */
    private final Partie partie;

    /**
     * Le boolean qui indique si une demande d'arrêt du ChronoThread a été demandé
     */
    private volatile boolean stopRequested;

    /**
     * Le label qui correspond au temps du chronomètre
     */
    private final Label label;

    /**
     * Boolean qui indique si la partie a déjà été detectée comme finie
     */
    private boolean partieFinie = false;

    /**
     * La largeur de la fenêtre
     */
    private double w;

    /**
     * La hauteur de la fenêtre
     */
    private double h;

    /**
     * Sauvegarde de la dernière minute ou on a enlevé des points
     */
    private int derniereMinute;

    /**
     * Constructeur de la classe ChronoThread
     *
     * @param partie la partie qui est reliée au chronomètre
     * @param label le label qui correspond au temps du chronomètre
     */
    public ChronoThread(Partie partie, Label label, double w, double h) {
        this.partie = partie;
        this.label = label;
        this.w = w;
        this.h = h;
        this.partieFinie = false;
        this.derniereMinute = PartieInfos.numberOfMinutes(this.partie.getInfos().getChrono());
    }

    /**
     * Méthode qui permet de stopper le thread du chronomètre
     */
    public void stopThread() {
        this.stopRequested = true;
    }

    /**
     * Méthode qui permet de lancer le thread du chronomètre
     */
    @Override
    public void run() {
        while (true) {
            if (stopRequested) {
                partie.sauvegarder();
                return;
            }

            // On met à jour le temps écoulé
            this.partie.getInfos().setChrono(this.partie.getChrono().getTempsEcoule());
            Platform.runLater(() -> this.label.setText(this.partie.getChrono().getElapsedTimeFormatted()));

            // On vérifie si la partie est finie
            if ( !this.partieFinie && this.partie.estComplet() ) {
                Platform.runLater(() -> Main.afficherPopUpFinPartie(this.partie,w,h));
                this.partieFinie = true;
                this.partie.getChrono().stop();
                this.stopThread();
                Main.resetGrid();
            }

            // On vérifie si le temps est écoulé
            if ( !this.partieFinie && this.partie.verifierTemps() ) {

                Platform.runLater(() -> this.partie.estTermine() );
            }

            // On enleve des points toutes les minutes écoulées
            if ( !this.partieFinie && this.partie.getInfos().getChrono().compareTo(Duration.ofMinutes(this.derniereMinute+1)) >= 0) {
                this.partie.getInfos().enleverPoints(Score.MALUS_PAR_MINUTE);
                this.derniereMinute++;
                System.out.println(this.partie.getInfos().getScore());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
