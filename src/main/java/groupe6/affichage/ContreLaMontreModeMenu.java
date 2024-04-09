package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.info.LimiteTemps;
import groupe6.model.partie.info.Score;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ContreLaMontreModeMenu extends ClassicModeMenu {

  /**
   * Label qui contient le temps limite
   */
  protected static Label tempsLimite;

  public static StackPane getMenu(Double w, Double h) {
    windowWidth = w;
    windowHeight = h;

    initMenuSelectionPuzzle();

    previewSelectionne = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
    previewSelectionne.setFitWidth(Math.round(0.10 * windowWidth));
    previewSelectionne.setFitHeight(Math.round(0.10 * windowWidth));

    // Pour les 3 difficultés, on crée un conteneur de preview de puzzle
    for (int i = 0; i < 3; i++) {
      // HBox intermédiaire pour obtenir le style souhaité
      HBox HBoxPreviewContainer = new HBox();
      HBoxPreviewContainer.setSpacing(0.03 * windowWidth);
      // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
      HBoxPreviewContainer.setStyle(
          "-fx-background-color: gray;" +
              " -fx-padding: "+Math.round(0.04 * windowHeight)+"px;" +
              " -fx-background-radius: 10px;"
      );
      int nbPuzzleParDifficulte = Launcher.cataloguePuzzles.getNombrePuzzleParDifficulte(DifficultePuzzle.values()[i]);
      // Si il n'y a pas de puzzle pour la difficulté, on affiche une image spéciale
      if ( nbPuzzleParDifficulte <= 0 ) {
        ImageView imgNoPuzzle = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
        imgNoPuzzle.setFitWidth(Math.round(0.10 * windowWidth));
        imgNoPuzzle.setFitHeight(Math.round(0.10 * windowWidth));
        HBoxPreviewContainer.getChildren().add(imgNoPuzzle);
      }
      // Sinon on affiche les previews des puzzles disponibles pour la difficulté
      else {
        for (int j = 0; j < nbPuzzleParDifficulte ; j++) {
          for (int k = 0; k < 8; k++) {
            final PuzzleSauvegarde puzzle = Launcher.cataloguePuzzles.getPuzzleSauvegarde(DifficultePuzzle.values()[i], j);
            String cheminImgPreviewPuzzle = Launcher.normaliserChemin(Launcher.dossierPuzzles + "/" + CataloguePuzzle.getPuzzleName(puzzle)+".png");
            System.out.println(cheminImgPreviewPuzzle);
            ImageView imgPreviewPuzzle = new ImageView(Launcher.chargerImage(cheminImgPreviewPuzzle));
            imgPreviewPuzzle.setFitWidth(Math.round(0.10 * windowWidth));
            imgPreviewPuzzle.setFitHeight(Math.round(0.10 * windowWidth));
            HBoxPreviewContainer.getChildren().add(imgPreviewPuzzle);
            // Detecte les clics sur les previews des puzzles pour afficher les informations sur le puzzle
            int finalJ = j;
            imgPreviewPuzzle.setOnMouseClicked(e -> {
              // Change la visibilité du paneau lateral si on vient de reselectionner le même puzzle
              if ( previewSelectionne == imgPreviewPuzzle ) {
                infoPane.setVisible(!infoPane.isVisible());
                infoPane.setManaged(!infoPane.isManaged());
              }
              // Sinon on change le puzzle sélectionné et on affiche les informations sur le puzzle
              else {
                previewSelectionne = imgPreviewPuzzle;
                updateInfoPuzzleSelectionne(puzzle);
                infoPane.setVisible(true);
                infoPane.setManaged(true);
              }
            });
          }
        }
      }
      // Ajout de la HBox intermédiaire au conteneur de preview de puzzle
      PuzzlePreviewContainer[i].getChildren().add(HBoxPreviewContainer);
    }

    // Ajoute les headers de chaque difficulté et ses conteneurs de preview des puzzles
    for (int i = 0; i < 3; i++) {
      Label header = new Label(DifficultePuzzle.values()[i].toString());
      // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
      header.setStyle(
          "-fx-background-color: gray;" +
              " -fx-text-fill: white;" +
              " -fx-padding: 10px;" +
              " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
              " -fx-background-radius: 10px;"
      );
      // Création d'un StackPane intermédiaire pour obtenir le style souhaité
      StackPane headerPane = new StackPane(header);
      // Met le text du header à gauche
      StackPane.setAlignment(header, Pos.CENTER_LEFT);
      // Detecte les clics sur le header pour afficher ou cacher les previews des puzzles
      int finalI = i;
      header.setOnMouseClicked(e -> {
        // Change la visibilité des previews des puzzles de la difficulté
        boolean isVisible = !PuzzlePreviewContainer[finalI].isVisible();
        PuzzlePreviewContainer[finalI].setVisible(isVisible);
        PuzzlePreviewContainer[finalI].setManaged(isVisible);
      });
      // Ajoute le header de la difficulté dans le conteneur
      VBoxDifficulteContainer.getChildren().add(headerPane);
      // Ajoute le conteneur de preview des puzzles de la difficulté dans le conteneur
      VBoxDifficulteContainer.getChildren().add(PuzzlePreviewContainer[i]);
      // Met le conteneur de preview des puzzles de la difficulté en invisible
      PuzzlePreviewContainer[finalI].setVisible(false);
      PuzzlePreviewContainer[finalI].setManaged(false);
    }

    // Change l'espacement entre les éléments du conteneur de preview des puzzles
    VBoxDifficulteContainer.setSpacing(0.02 * windowHeight);

    // Création d'un ScrollPane pour afficher les previews des puzzles
    ScrollPane scrollPane = new ScrollPane(VBoxDifficulteContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // Met le fond du ScrollPane en transparent pour laisser voir l'image de fond
    scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    // Ajoute le ScrollPane au StackPane qui contient le selecteur de puzzle
    stackPaneSelecteurPuzzle.getChildren().add(scrollPane);

    // Ajouter une marge de 7% à gauche et 12% en bas au scrollPane
    StackPane.setMargin(scrollPane, new javafx.geometry.Insets(0,  0, 0.12 * windowHeight, 0.07 * windowWidth));

    // Gestion du titre
    title = new Label("Mode Contre La Montre");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeight));
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    Menu.adaptTextSize(title,60, windowWidth, windowHeight);

    // Boite verticale principale qui contient le titre et le selecteur de puzzle
    mainVbox.setSpacing(0.10 * windowHeight);
    mainVbox.getChildren().add(title);
    mainVbox.getChildren().add(stackPaneSelecteurPuzzle);
    mainVbox.setAlignment(Pos.TOP_CENTER);

    // Gestion du bouton de retour
    backButton = new Label("Retour");
    StackPane.setMargin(backButton, new javafx.geometry.Insets(0, 0, 0.05 * windowHeight, 0));
    backButton.getStyleClass().add("button-rounded");
    backButton.getStyleClass().add("text-button");
    backButton.setPrefSize(Menu.toPourcentWidth(200.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
    backButton.setOnMouseClicked(e -> {
      Main.showGameModeMenu();
    });


    // Change le curseur quand on passe sur le bouton de retour avec le css
    backButton.setStyle("-fx-cursor: hand;");

    // Chargement de l'image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

    // Création du paneau lateral d'information sur le puzzle sélectionné
    infoPane = creerPaneauLateralInformation();
    infoPane.setVisible(false);
    infoPane.setManaged(false);

    // Ajout des éléments graphiques au panneau principal
    mainPane.getChildren().addAll(
        backgroundImage,
        mainVbox,
        backButton,
        infoPane
    );

    // Met le titre en haut de la boite verticale principale
    StackPane.setAlignment(title, Pos.TOP_CENTER);

    // Met la boite verticale principale au centre du panneau
    StackPane.setAlignment(mainVbox, Pos.CENTER);

    // Met le bouton de retour en bas du panneau
    StackPane.setAlignment(backButton,Pos.BOTTOM_CENTER);

    // Met le paneau lateral d'information à droite
    StackPane.setAlignment(infoPane, Pos.CENTER_RIGHT);

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
        imgPreviewInfo,
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

