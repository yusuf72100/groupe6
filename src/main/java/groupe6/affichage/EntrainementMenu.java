package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import groupe6.model.technique.DifficulteTechnique;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Classe qui représente le menu d'entrainement
 *
 * @author Yamis

 */
public class EntrainementMenu {

  /**
   * Méthode pour obtenir le panneau du menu entrainement
   *
   * @param w la largeur de la fenêtre
   * @param h la hauteur de la fenêtre
   * @return le panneau du menu entrainement
   */
  public static StackPane getMenu(Double w, Double h) {

    // Initialisation des éléments graphiques
    // Création du panneau principal
    StackPane mainPane = new StackPane();
    // Création des éléments graphiques pour le selecteur de puzzle
    VBox mainVbox = new VBox();
    VBox VBoxDifficulteContainer = new VBox();
    HBox[] PuzzlePreviewContainer = new HBox[]{new HBox(), new HBox(), new HBox()};
    StackPane stackPaneSelecteurPuzzle = new StackPane();
    // Création des éléments graphiques pour le panneau d'information sur le puzzle sélectionné
    VBox infoPane = new VBox();
    infoPane.setVisible(false);
    infoPane.setManaged(false);
    VBox infoTechnique = new VBox();
    ImageView imgTechnique = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
    imgTechnique.setFitWidth(Math.round(0.16 * w));
    imgTechnique.setFitHeight(Math.round(0.16 * w));
    ImageView previewSelectionne = null;

    // ==========================================================================
    // Création des éléments du panneau d'information sur le puzzle sélectionné
    // ==========================================================================

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setSpacing(0.08 * h);

    // Ajout du padding dans le paneau lateral d'information
    infoPane.setPadding(new Insets(0.04 * h,0.02 * h,0.04 * h,0.02 * h));

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPane.setStyle(
        "-fx-background-color: "+Main.mainColorCSS+";" +
            " -fx-padding: "+Math.round(0.02 * h)+"px;" +
            " -fx-background-radius: 10px 0px 0px 10px;" +
            "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
    );

    // Definition de la taille du paneau lateral d'information
    infoPane.setMinWidth(0.25 * w);
    infoPane.setMaxWidth(0.25 * w);

    Label titre = new Label("Information sur la technique");
    Menu.adaptTextSize(titre, 35, w, h);
    titre.setStyle(
        titre.getStyle() +
            " -fx-text-fill: black;" +
            " -fx-padding: 10px;" +
            " -fx-background-radius: 10px;" +
            "-fx-font-weight: bold;"
    );

    // Le nom de la technique
    Label nomTechnique = new Label("Nom de la technique");
    Menu.adaptTextSize(nomTechnique, 30, w, h);
    nomTechnique.setStyle(
        nomTechnique.getStyle() +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;"
    );

    // Les informations textuelles sur la technique
    Label descriptionTechnique = new Label("Description de la technique");
    Menu.adaptTextSize(descriptionTechnique, 28, w, h);
    descriptionTechnique.setStyle(
        descriptionTechnique.getStyle() +
            "-fx-text-fill: black;"
    );

    // Element qui sert à faire un espace pour l'affichage
    Pane espace = new Pane();
    espace.setMinHeight(0.02 * h);

    // Element qui sert à faire un espace pour l'affichage
    Pane espace1 = new Pane();
    espace1.setMinHeight(0.02 * h);

    // Element qui sert à faire un espace pour l'affichage
    Pane espace2 = new Pane();
    espace2.setMinHeight(0.02 * h);

    // Bouton pour lancer l'entrainement
    Button btnEntrainement = new Button("ENTRAINEMENT");
    btnEntrainement.setPrefSize(Menu.toPourcentWidth(200.0, w), Menu.toPourcentHeight(100.0, h));
    // Style du bouton pour lancer l'entrainement
    btnEntrainement.getStyleClass().add("button-rounded-play");
    btnEntrainement.getStyleClass().add("button-text");
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    double newSize = 28 * Math.min(w / 1920, h / 1080);
    btnEntrainement.setStyle(btnEntrainement.getStyle() + "-fx-font-size: " + newSize + "px;");
    // Detecte les clics sur le bouton pour lancer l'entrainement sur la technique
    btnEntrainement.setOnMouseClicked(e -> {
      // TODO : Lancer entrainement
    });
    // Ajoute une marge de 5% en bas au bouton pour lancer entrainement
    StackPane.setMargin(btnEntrainement, new Insets(0, 0, 0.05 * h, 0));

    // Ajout de l'image et des informations textuelles sur l'entrainement
    infoTechnique.getChildren().addAll(
        nomTechnique,
        espace,
        imgTechnique,
        espace1,
        descriptionTechnique,
        espace2,
        btnEntrainement
    );
    infoTechnique.setSpacing(0.01 * h);
    infoTechnique.setAlignment(Pos.CENTER);

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
        infoTechnique
    );
    infoPane.setSpacing(0.05 * h);
    // Met les elements du paneau lateral d'information au centre
    infoPane.setAlignment(Pos.TOP_CENTER);

    // Met la croix en haut à droite
    StackPane.setAlignment(croix, Pos.TOP_RIGHT);

    // ==========================================================================
    // Création du selecteur de puzzle
    // ==========================================================================


    // Pour les 3 difficultés, on crée un conteneur de preview de puzzle
    for (int i = 0; i < 3; i++) {
      // HBox intermédiaire pour obtenir le style souhaité
      VBox VBoxPreviewContainer = new VBox();
      VBoxPreviewContainer.setSpacing(0.015 * w);
      // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
      VBoxPreviewContainer.setStyle(
          "-fx-background-color: "+Main.mainColorCSS+";" +
              " -fx-padding: "+Math.round(0.04 * h)+"px;" +
              " -fx-background-radius: 10px;"
      );
      int nbElem = 5;
      int idxElem = 0;
      for (int j = 0; j < 2; j++) {
        HBox HBoxPreviewContainer = new HBox();
        HBoxPreviewContainer.setSpacing(0.03 * w);
        // Ajoute au maxim 3 element par ligne dans le conteneur de preview de puzzle
        for ( int k = 0; k < 3  && idxElem < nbElem; k++, idxElem++) {
          ImageView img = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
          img.setFitWidth(Math.round(  0.15 * w));
          img.setFitHeight(Math.round( 0.15 * w));
          img.setOnMouseClicked(e -> {
            // Change la visibilité du paneau lateral si on vient de reselectionner le même puzzle
            if (  true ) {
              boolean isVisible = infoPane.isVisible();
              infoPane.setVisible(!isVisible);
              infoPane.setManaged(!isVisible);
            }
            // Sinon on change le puzzle sélectionné et on affiche les informations sur le puzzle
            else {
              infoPane.setVisible(true);
              infoPane.setManaged(true);
            }
          });
          HBoxPreviewContainer.getChildren().add(img);
        }
        VBoxPreviewContainer.getChildren().add(HBoxPreviewContainer);
      }

      PuzzlePreviewContainer[i].getChildren().add(VBoxPreviewContainer);
    }

    // Ajoute les headers de chaque difficulté et ses conteneurs de preview des puzzles
    for (int i = 0; i < 3; i++) {
      Label header = new Label(DifficulteTechnique.values()[i].toString());

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
          transitionElement.setToX(-(w));
          transitionElement.play();

          transitionElement.setOnFinished(event -> {
            PuzzlePreviewContainer[finalI].setVisible(isVisible);
            PuzzlePreviewContainer[finalI].setManaged(isVisible);
          });
        } else {
          transitionElement = new TranslateTransition(Duration.seconds(0.5), PuzzlePreviewContainer[finalI]);
          transitionElement.setFromX(-(w));
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
    VBoxDifficulteContainer.setSpacing(0.02 * h);

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
    StackPane.setMargin(scrollPane, new Insets(0.08 * w,  0, 0.17 * h, 0.08 * w));

    // Gestion du titre
    Label title = new Label("Entrainement");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, h));
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    Menu.adaptTextSize(title,60, w, h);

    // Boite verticale principale qui contient le titre et le selecteur de puzzle
    mainVbox.setSpacing(0.10 * h);
    mainVbox.getChildren().add(stackPaneSelecteurPuzzle);
    mainVbox.setAlignment(Pos.CENTER);

    // Gestion du bouton de retour
    Button backButton = new Button("RETOUR");
    StackPane.setMargin(backButton, new Insets(0, 0, 0.05 * h, 0));
    backButton.getStyleClass().add("button-rounded");
    backButton.getStyleClass().add("button-text");

    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    double nouvelleTaille = 35 * Math.min(w / 1920, h / 1080);
    backButton.setStyle(backButton.getStyle() + "-fx-font-size: " + nouvelleTaille + "px;");
    // Adaptation de la taille du bouton en fonction de la taille de la fenêtre
    backButton.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
    // Action du bouton de retour
    backButton.setOnMouseClicked(e -> {
      Main.showMainMenu();
    });

    // Chargement de l'image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

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

    // config des touches
    EventHandler<KeyEvent> keyEventHandler = event -> {
      KeyCode keyCode = event.getCode();

      if (keyCode == KeyCode.ESCAPE) {
        Main.showGameModeMenu();
      }
    };

    mainPane.setOnKeyPressed(keyEventHandler);

    return mainPane;
  }

}
