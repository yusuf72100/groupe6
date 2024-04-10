package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.info.LimiteTemps;
import groupe6.model.partie.info.Score;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ContreLaMontreModeMenu extends ClassicModeMenu {

  /**
   * Label qui contient le temps limite
   */
  protected static Label tempsLimite;

  public static StackPane getMenu(Double w, Double h) {
    // Initialisation des éléments graphiques
    ClassicModeMenu.getMenu(w, h);

    ClassicModeMenu.title.setText("Mode contre la montre");

    tempsLimite = new Label("Temps limite : ");

    Menu.adaptTextSize(tempsLimite, 28, windowWidth, windowHeight);
    tempsLimite.setStyle(
        tempsLimite.getStyle() +
        "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
    );

    // Detecte les clics sur le bouton pour lancer le puzzle en mode classique
    ClassicModeMenu.btnJouer.setOnMouseClicked(e -> {
      Main.lancerPartie(ClassicModeMenu.difficulteSelectionne, ClassicModeMenu.numeroPuzzleSelectionne, ModeJeu.CONTRELAMONTRE);
    });

    // Ajout de l'image et des informations textuelles sur le puzzle
    ClassicModeMenu.infoPuzzle.getChildren().addAll(
        tempsLimite
    );

    // Pour les 3 difficultés, on crée un conteneur de preview de puzzle
    for (int i = 0; i < 3; i++) {
      int nbPuzzleParDifficulte = Launcher.cataloguePuzzles.getNombrePuzzleParDifficulte(DifficultePuzzle.values()[i]);

      if ( nbPuzzleParDifficulte <= 0 ) {

      }
      // Sinon on affiche les previews des puzzles disponibles pour la difficulté
      else {
        for (int j = 0; j < nbPuzzleParDifficulte ; j++) {

          // Detecte les clics sur les previews des puzzles pour afficher les informations sur le puzzle
          final int finalI = i;
          final int finalJ = j;

          ClassicModeMenu.imgPreviewPuzzle.setOnMouseClicked(e -> {
            // Change la visibilité du paneau lateral si on vient de reselectionner le même puzzle
            if ( previewSelectionne == imgPreviewPuzzle ) {
              boolean isVisible = infoPane.isVisible();
              ClassicModeMenu.infoPane.setVisible(!isVisible);
              ClassicModeMenu.infoPane.setManaged(!isVisible);
            }

            // Sinon on change le puzzle sélectionné et on affiche les informations sur le puzzle
            else {
              ClassicModeMenu.previewSelectionne = imgPreviewPuzzle;
              ClassicModeMenu.difficulteSelectionne = finalI;
              ClassicModeMenu.numeroPuzzleSelectionne = finalJ;
              ClassicModeMenu.imgPreviewInfo.setImage(Launcher.chargerImage(ClassicModeMenu.cheminImgPreviewPuzzle));
              updateInfoPuzzleSelectionneContreLaMontre(ClassicModeMenu.puzzle);
              ClassicModeMenu.infoPane.setVisible(true);
              ClassicModeMenu.infoPane.setManaged(true);
            }
          });
        }
      }
    }

    return mainPane;
  }

  /**
   *
   * @param puzzleSelectionne
   */
  public static void updateInfoPuzzleSelectionneContreLaMontre(PuzzleSauvegarde puzzleSelectionne) {
    System.out.println("Update Contre la montre");
    ClassicModeMenu.infoDifficulte.setText("Difficulté : " + puzzleSelectionne.getDifficulte().toString());
    ClassicModeMenu.infoTaille.setText("Taille : " + puzzleSelectionne.getLargeur() + "x" + puzzleSelectionne.getLongueur());
    ClassicModeMenu.infoPointsDepart.setText("Points de départ : " + Score.getScoreDebut(puzzleSelectionne.getDifficulte()));
    tempsLimite.setText("Temps limite : " +
        LimiteTemps.convertDurationToMinSec(
          LimiteTemps.getLimiteTemps(puzzleSelectionne.getDifficulte())
        )
    );
  }


}

