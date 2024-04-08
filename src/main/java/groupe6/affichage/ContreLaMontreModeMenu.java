package groupe6.affichage;

import groupe6.model.partie.info.LimiteTemps;
import groupe6.model.partie.info.Score;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ContreLaMontreModeMenu extends ClassicModeMenu {

  /**
   * Label qui contient le temps limite
   */
  protected static Label tempsLimite;

  public static StackPane getMenu(Double w, Double h) {
    windowWidth = w;
    windowHeight = h;

    initMenuSelectionPuzzle();

    // TODO : Création detection click preview

    //

    return mainPane;
  }

  /**
   *
   * @param puzzleSelectionne
   */
  public static void updateInfoPuzzleSelectionne(PuzzleSauvegarde puzzleSelectionne) {
    System.out.println("Update Contre la montre");
    infoDifficulte.setText("Difficulté : " + puzzleSelectionne.getDifficulte().toString());
    infoTaille.setText("Taille : " + puzzleSelectionne.getLargeur() + "x" + puzzleSelectionne.getLongueur());
    infoPointsDepart.setText("Points de départ : " + Score.getScoreDebut(puzzleSelectionne.getDifficulte()));
    tempsLimite.setText("Temps limite : " +
        LimiteTemps.convertDurationToMinSec(
          LimiteTemps.getLimiteTemps(puzzleSelectionne.getDifficulte())
        )
    );
  }

  /**
   * Méthode qui crée le paneau latéral d'information
   *
   * @return le paneau latéral d'information
   */
  public static VBox creerPaneauLateralInformation() {
    // Création de la boite verticale qui contient les informations sur le puzzle
    VBox infoPane = new VBox();
    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setSpacing(0.08 * windowHeight);

    // Ajout de la marge
    infoPane.setPadding(new Insets(0.04 * windowHeight,0.02 * windowHeight,0.04 * windowHeight,0.02 * windowHeight));

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setStyle(
        "-fx-background-color: gray;" +
            " -fx-padding: "+Math.round(0.02 * windowHeight)+"px;" +
            " -fx-background-radius: 10px 0px 0px 10px;"
    );

    // Definition de la taille du paneau lateral d'information
    infoPane.setMinWidth(0.25 * windowWidth);
    infoPane.setMaxWidth(0.25 * windowWidth);

    Label titre = new Label("Information sur le puzzle");
    titre.setStyle(
        "-fx-background-color: gray;" +
            " -fx-text-fill: white;" +
            " -fx-padding: 10px;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
            " -fx-background-radius: 10px;"
    );

    if ( infoDifficulte == null ) {
      infoDifficulte = new Label("Difficulté : ");
    }
    if ( infoTaille == null ) {
      infoTaille = new Label("Taille : ");
    }
    if ( infoPointsDepart == null ) {
      infoPointsDepart = new Label("Points de départ : ");
    }

    if ( tempsLimite == null ) {
      tempsLimite = new Label("Temps limite : ");
    }

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoDifficulte.setStyle(
        "-fx-text-fill: white;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;"
    );
    infoTaille.setStyle(
        "-fx-text-fill: white;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;"
    );
    infoPointsDepart.setStyle(
        "-fx-text-fill: white;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;"
    );
    tempsLimite.setStyle(
        "-fx-text-fill: white;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;"
    );


    // Création de la boite verticale qui contient les informations textuelles sur le puzzle
    VBox infoPuzzleText = new VBox();
    infoPuzzleText.setSpacing(0.02 * windowHeight);
    infoPuzzleText.getChildren().addAll(
        infoDifficulte,
        infoTaille,
        infoPointsDepart,
        tempsLimite
    );

    // Met le texte à gauche
    infoPuzzleText.setAlignment(Pos.CENTER_LEFT);


    // La boite verticale qui contient les informations sur le puzzle
    VBox infoPuzzle = new VBox();


    infoPuzzle.getChildren().addAll(
        previewSelectionne,
        infoPuzzleText
    );

    // Création de la croix pour fermer le paneau lateral d'information
    Label croix = new Label("X");
    croix.setStyle(
        "-fx-text-fill: white;" +
            " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;"
    );

    // Detecte les clics sur la croix pour fermer le paneau lateral d'information
    croix.setOnMouseClicked(e -> {
      infoPane.setVisible(false);
      infoPane.setManaged(false);
    });

    // Change le curseur quand on passe sur la croix avec le css
    croix.setStyle("-fx-cursor: hand;");


    // Met la croix en haut à droite

    // Ajout des informations dans le paneau lateral d'information
    infoPane.getChildren().addAll(
        croix,
        titre,
        infoPuzzle
    );

    // Met la croix en haut à droite
    StackPane.setAlignment(croix, Pos.TOP_RIGHT);

    // Cente le titre
    StackPane.setAlignment(titre, Pos.TOP_CENTER);

    // Centre les informations sur le puzzle
    StackPane.setAlignment(infoPuzzle, Pos.CENTER);

    return infoPane;
  }


}

