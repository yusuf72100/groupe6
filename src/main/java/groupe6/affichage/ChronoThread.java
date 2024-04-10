package groupe6.affichage;

import groupe6.model.partie.Chronometre;
import groupe6.model.partie.Partie;
import groupe6.model.partie.info.PartieInfos;
import javafx.scene.control.Label;

public class ChronoThread implements Runnable {
    private Partie partie;
    private volatile boolean stopRequested = false;
    private Label label;

    public ChronoThread(Partie partie, Label label) {
        this.partie = partie;
        this.label = label;
    }

    public void stopThread() {
        this.stopRequested = true;
    }

    @Override
    public void run() {
        while (true) {
            if (stopRequested) {
                partie.sauvegarder();
                break;
            }

            System.out.println(this.partie.getChrono().getElapsedTimeFormatted());
            this.partie.getInfos().setChrono(this.partie.getChrono().getTempsEcoule());
//            this.label.setText(this.partie.getChrono().getElapsedTimeFormatted());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
