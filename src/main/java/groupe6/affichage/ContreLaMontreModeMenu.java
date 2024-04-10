package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.info.LimiteTemps;
import groupe6.model.partie.info.Score;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ContreLaMontreModeMenu extends ClassicModeMenu {

  /**
   * Label qui contient le temps limite
   */
  protected static Label tempsLimite;

  public static StackPane getMenu(Double w, Double h) {
    windowWidth = w;
    windowHeight = h;

    // Initialisation des éléments graphiques
    initMenuSelectionPuzzle();

    // ==========================================================================
    // Création des éléments du panneau d'information sur le puzzle sélectionné
    // ==========================================================================

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setSpacing(0.08 * windowHeight);

    // Ajout du padding dans le paneau lateral d'information
    infoPane.setPadding(new Insets(0.04 * windowHeight,0.02 * windowHeight,0.04 * windowHeight,0.02 * windowHeight));

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setStyle(
        "-fx-background-color: "+Main.mainColorCSS+";" +
        " -fx-padding: "+Math.round(0.02 * windowHeight)+"px;" +
        " -fx-background-radius: 10px 0px 0px 10px;" +
        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
    );

    // Definition de la taille du paneau lateral d'information
    infoPane.setMinWidth(0.25 * windowWidth);
    infoPane.setMaxWidth(0.25 * windowWidth);

    Label titre = new Label("Information sur le puzzle");
    Menu.adaptTextSize(titre, 35, windowWidth, windowHeight);
    titre.setStyle(
        titre.getStyle() +
        " -fx-text-fill: black;" +
        " -fx-padding: 10px;" +
        " -fx-background-radius: 10px;" +
        "-fx-font-weight: bold;"
    );

    // Les informations textuelles sur le puzzle
    infoDifficulte = new Label("Difficulté : ");
    infoTaille = new Label("Taille : ");
    infoPointsDepart = new Label("Points de départ : ");
    tempsLimite = new Label("Temps limite : ");

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    Menu.adaptTextSize(infoDifficulte, 28, windowWidth, windowHeight);
    infoDifficulte.setStyle(
        infoDifficulte.getStyle() +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;"
    );
    Menu.adaptTextSize(infoTaille, 28, windowWidth, windowHeight);
    infoTaille.setStyle(
        infoTaille.getStyle() +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;"
    );
    Menu.adaptTextSize(infoPointsDepart, 28, windowWidth, windowHeight);
    infoPointsDepart.setStyle(
        infoPointsDepart.getStyle() +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;"
    );
    Menu.adaptTextSize(tempsLimite, 28, windowWidth, windowHeight);
    tempsLimite.setStyle(
        tempsLimite.getStyle() +
        "-fx-text-fill: black;" +
            "-fx-font-weight: bold;"
    );


    // Element qui sert a faire un espace entre l'image et les informations textuelles
    Pane espace = new Pane();
    espace.setMinHeight(0.02 * windowHeight);

    // Deuxieme espace pour separer les informations textuelles du bouton pour lancer le puzzle
    Pane espace2 = new Pane();
    espace2.setMinHeight(0.02 * windowHeight);

    // Bouton pour lancer le puzzle
    Button btnJouer = new Button("JOUER");
    btnJouer.setPrefSize(Menu.toPourcentWidth(200.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
    // Style du bouton pour lancer le puzzle
    btnJouer.getStyleClass().add("button-rounded-play");
    btnJouer.getStyleClass().add("button-text");
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    double newSize = 28 * Math.min(windowWidth / 1920, windowHeight / 1080);
    btnJouer.setStyle(btnJouer.getStyle() + "-fx-font-size: " + newSize + "px;");
    // Detecte les clics sur le bouton pour lancer le puzzle en mode classique
    btnJouer.setOnMouseClicked(e -> {
      Main.lancerPartie(difficulteSelectionne, numeroPuzzleSelectionne, ModeJeu.CONTRELAMONTRE);
    });
    // Ajoute une marge de 5% en bas au bouton pour lancer le puzzle
    StackPane.setMargin(btnJouer, new javafx.geometry.Insets(0, 0, 0.05 * windowHeight, 0));

    // Ajout de l'image et des informations textuelles sur le puzzle
    infoPuzzle.getChildren().addAll(
        imgPreviewInfo,
        espace,
        infoDifficulte,
        infoTaille,
        infoPointsDepart,
        tempsLimite,
        espace2,
        btnJouer
    );
    infoPuzzle.setSpacing(0.01 * windowHeight);
    infoPuzzle.setAlignment(Pos.CENTER);

    // Création de la croix pour fermer le paneau lateral d'information
    Label croix = new Label("✖");
    croix.setStyle(
        "-fx-text-fill: black;" +
        "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
        "-fx-cursor: hand;"
    );

    // Boite horizontale pour mettre la croix en haut à droite
    HBox HBoxCroix = new HBox(croix);
    HBoxCroix.setAlignment(Pos.TOP_RIGHT);

    // Detecte les clics sur la croix pour fermer le paneau lateral d'information
    VBox finalInfoPane = infoPane;
    croix.setOnMouseClicked(e -> {
      finalInfoPane.setVisible(false);
      finalInfoPane.setManaged(false);
    });

    // Ajout des informations dans le paneau lateral d'information
    infoPane.getChildren().addAll(
        HBoxCroix,
        titre,
        infoPuzzle
    );
    infoPane.setSpacing(0.05 * windowHeight);
    // Met les elements du paneau lateral d'information au centre
    infoPane.setAlignment(Pos.TOP_CENTER);

    // Met la croix en haut à droite
    StackPane.setAlignment(croix, Pos.TOP_RIGHT);

//    // Cente le titre
//    StackPane.setAlignment(titre, Pos.TOP_CENTER);
//
//    // Centre les informations sur le puzzle
//    StackPane.setAlignment(infoPuzzle, Pos.CENTER);

    // ==========================================================================
    // Création du selecteur de puzzle
    // ==========================================================================

    // Pour les 3 difficultés, on crée un conteneur de preview de puzzle
    for (int i = 0; i < 3; i++) {
      // HBox intermédiaire pour obtenir le style souhaité
      HBox HBoxPreviewContainer = new HBox();
      HBoxPreviewContainer.setSpacing(0.03 * windowWidth);
      // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
      HBoxPreviewContainer.setStyle(
          "-fx-background-color: "+Main.mainColorCSS+";" +
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
          final PuzzleSauvegarde puzzle = Launcher.cataloguePuzzles.getPuzzleSauvegarde(DifficultePuzzle.values()[i], j);
          final String cheminImgPreviewPuzzle = Launcher.normaliserChemin(Launcher.dossierPuzzles + "/" + CataloguePuzzle.getPuzzleName(puzzle)+".png");
          Image imgPreview = Launcher.chargerImage(cheminImgPreviewPuzzle);
          ImageView imgPreviewPuzzle = new ImageView(imgPreview);
          imgPreviewPuzzle.setFitWidth(Math.round(0.25 * windowWidth));
          imgPreviewPuzzle.setFitHeight(Math.round(0.25 * windowWidth));
          imgPreviewPuzzle.setStyle(
              "-fx-cursor: hand;"
          );
          HBoxPreviewContainer.getChildren().add(imgPreviewPuzzle);
          // Detecte les clics sur les previews des puzzles pour afficher les informations sur le puzzle
          final int finalI = i;
          final int finalJ = j;
          imgPreviewPuzzle.setOnMouseClicked(e -> {
            // Change la visibilité du paneau lateral si on vient de reselectionner le même puzzle
            if ( previewSelectionne == imgPreviewPuzzle ) {
              boolean isVisible = infoPane.isVisible();
              infoPane.setVisible(!isVisible);
              infoPane.setManaged(!isVisible);
            }
            // Sinon on change le puzzle sélectionné et on affiche les informations sur le puzzle
            else {
              previewSelectionne = imgPreviewPuzzle;
              difficulteSelectionne = finalI;
              numeroPuzzleSelectionne = finalJ;
              imgPreviewInfo.setImage(Launcher.chargerImage(cheminImgPreviewPuzzle));
              updateInfoPuzzleSelectionneContreLaMontre(puzzle);
              infoPane.setVisible(true);
              infoPane.setManaged(true);
            }
          });
        }
      }
      PuzzlePreviewContainer[i].getChildren().add(HBoxPreviewContainer);
    }

    // Ajoute les headers de chaque difficulté et ses conteneurs de preview des puzzles
    for (int i = 0; i < 3; i++) {
      Label header = new Label(DifficultePuzzle.values()[i].toString());

      // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
      header.setStyle(
          "-fx-background-color: "+Main.secondaryColorCSS+";" +
          "-fx-text-fill: black;" +
          "-fx-font-weight: bold;" +
          "-fx-padding: 10px;" +
          "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
          "-fx-background-radius: 10px;" +
          "-fx-cursor: hand;"
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

        TranslateTransition transitionElement;
        if(!isVisible) {
          transitionElement = new TranslateTransition(Duration.seconds(0.5), PuzzlePreviewContainer[finalI]);
          transitionElement.setFromX(0);
          transitionElement.setToX(-(windowWidth));
          transitionElement.play();

          transitionElement.setOnFinished(event -> {
            PuzzlePreviewContainer[finalI].setVisible(isVisible);
            PuzzlePreviewContainer[finalI].setManaged(isVisible);
          });
        } else {
          transitionElement = new TranslateTransition(Duration.seconds(0.5), PuzzlePreviewContainer[finalI]);
          transitionElement.setFromX(-(windowWidth));
          transitionElement.setToX(0);
          transitionElement.play();

          PuzzlePreviewContainer[finalI].setVisible(isVisible);
          PuzzlePreviewContainer[finalI].setManaged(isVisible);
        }
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
    StackPane.setMargin(scrollPane, new javafx.geometry.Insets(0.08 * windowWidth,  0, 0.17 * windowHeight, 0.08 * windowWidth));

    // Gestion du titre
    title = new Label("Mode Contre La Montre");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeight));
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    Menu.adaptTextSize(title,60, windowWidth, windowHeight);

    // Boite verticale principale qui contient le titre et le selecteur de puzzle
    mainVbox.setSpacing(0.10 * windowHeight);
    mainVbox.getChildren().add(stackPaneSelecteurPuzzle);
    mainVbox.setAlignment(Pos.CENTER);

    // Gestion du bouton de retour
    backButton = new Button("RETOUR");
    StackPane.setMargin(backButton, new javafx.geometry.Insets(0, 0, 0.05 * windowHeight, 0));
    backButton.getStyleClass().add("button-rounded");
    backButton.getStyleClass().add("button-text");
//    backButton.setStyle( backButton.getStyle() + "-fx-cursor: hand;");
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    double nouvelleTaille = 35 * Math.min(windowWidth / 1920, windowHeight / 1080);
    backButton.setStyle(backButton.getStyle() + "-fx-font-size: " + nouvelleTaille + "px;");
    // Adaptation de la taille du bouton en fonction de la taille de la fenêtre
    backButton.setPrefSize(Menu.toPourcentWidth(200.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
    // Action du bouton de retour
    backButton.setOnMouseClicked(e -> {
      Main.showGameModeMenu();
    });

    // Chargement de l'image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

    // Ajout des éléments graphiques au panneau principal
    mainPane.getChildren().addAll(
        backgroundImage,
        title,
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
  public static void updateInfoPuzzleSelectionneContreLaMontre(PuzzleSauvegarde puzzleSelectionne) {
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
}

