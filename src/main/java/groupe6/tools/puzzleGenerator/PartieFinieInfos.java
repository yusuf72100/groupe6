package groupe6.tools.puzzleGenerator;

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

    public PartieFinieInfos(Duration chrono, int score, boolean complete, ModeJeu mode, DifficultePuzzle difficulte) {
        super(chrono, score, complete, mode);
        this.difficulte = difficulte;
    }

    public DifficultePuzzle getDifficulte() {
        return difficulte;
    }
}
