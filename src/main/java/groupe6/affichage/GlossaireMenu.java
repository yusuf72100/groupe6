package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.technique.DifficulteTechnique;
import groupe6.model.technique.GestionnaireTechnique;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Classe qui représente le menu du glossaire
 *
 * @author Yamis

 */
public class GlossaireMenu {

  /**
   * Image de preview sélectionnée
   */
  private static ImageView previewSelectionne = null;

  /**
   * Méthode pour obtenir le panneau du menu glossaire
   *
   * @param w la largeur de la fenêtre
   * @param h la hauteur de la fenêtre
   * @return le panneau du menu entrainement
   */
  public static StackPane getMenu(Double w, Double h) {
    // ==========================================================================
    // Création des éléments du panneau d'information sur le puzzle sélectionné
    // ==========================================================================

    // Création du paneau lateral d'information
    VBox infoPaneVBox =  new VBox();
    infoPaneVBox.setVisible(false);
    infoPaneVBox.setManaged(false);

    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPaneVBox.setStyle(
        "-fx-background-color: "+Main.mainColorCSS+";" +
            " -fx-padding: "+Math.round(0.02 * h)+"px;" +
            " -fx-background-radius: 10px 0px 0px 10px;" +
            "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
    );


    // Gestion du style avec des valeurs qui varient en fonction de la taille de la fenêtre
    infoPaneVBox.setSpacing(0.08 * h);

    infoPaneVBox.setMinWidth(0.30 * w);
    infoPaneVBox.setMaxWidth(0.30 * w);

    // Definition de la taille du paneau lateral d'information


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
    descriptionTechnique.setMinWidth(0.25 * w);
    descriptionTechnique.setMaxWidth(0.25 * w);
    descriptionTechnique.setWrapText(true);

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
    btnEntrainement.setPrefSize(Menu.toPourcentWidth(250.0, w), Menu.toPourcentHeight(100.0, h));
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

    // Image de la technique
    ImageView imgTechnique = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
    imgTechnique.setFitWidth(Math.round(0.16 * w));
    imgTechnique.setFitHeight(Math.round(0.16 * w));


    // Ajout de l'image et des informations textuelles sur l'entrainement
    VBox infoTechnique = new VBox();
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
    final VBox finalInfoPane = infoPaneVBox;
    croix.setOnMouseClicked(e -> {
      finalInfoPane.setVisible(false);
      finalInfoPane.setManaged(false);
    });

    VBox infoPaneInfoVbox = new VBox();
    infoPaneInfoVbox.getChildren().addAll(
        titre,
        infoTechnique
    );
    infoPaneInfoVbox.setSpacing(0.02 * h);
    infoPaneInfoVbox.setAlignment(Pos.CENTER);

    ScrollPane infoPaneScroll = new ScrollPane(infoPaneInfoVbox);
    infoPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // Ajout des informations dans le paneau lateral d'information
    infoPaneVBox.getChildren().addAll(
        HBoxCroix,
        infoPaneScroll
    );
    infoPaneVBox.setSpacing(0.05 * h);
    // Met les elements du paneau lateral d'information au centre
    infoPaneVBox.setAlignment(Pos.CENTER_RIGHT);

    // Met la croix en haut à droite
    StackPane.setAlignment(croix, Pos.TOP_RIGHT);

    // ==========================================================================
    // Création du selecteur de puzzle
    // ==========================================================================

    HBox[] previewContainer = new HBox[]{new HBox(), new HBox(), new HBox()};
    List<String[]>[] listeNomTechnique = GestionnaireTechnique.getInstance().nomTechniques();

    // Pour les 4 sections, on crée un conteneur de preview
    for (int i = 0; i < 3; i++) {
      final int finalI = i;
      // VBox qui contient les lignes de preview
      VBox VBoxPreviewContainer = new VBox();
      VBoxPreviewContainer.setSpacing(0.015 * w);
      VBoxPreviewContainer.setStyle(
          "-fx-background-color: "+Main.mainColorCSS+";" +
              " -fx-padding: "+Math.round(0.04 * h)+"px;" +
              " -fx-background-radius: 10px;"
      );
      // Pour chaque ligne de preview
      int idxElem = 0;
      for (int j = 0; j < 2; j++) {
        // HBox qui correspond à une ligne de preview
        HBox HBoxPreviewContainer = new HBox();
        HBoxPreviewContainer.setSpacing(0.03 * w);
        // Liste nom technique
        for ( int k = 0; k < 3  && idxElem < listeNomTechnique[i].size() ; k++, idxElem++) {
          final int currentIdxElem = idxElem;
          // Gestion nom stylisé de la technique
          String cheminTxtTechnique = Launcher.normaliserChemin(
              Launcher.dossierTechniques + "/description/" + listeNomTechnique[finalI].get(currentIdxElem)[0] + ".desc"
          );
          StringBuilder contentBuilder = new StringBuilder();
          try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cheminTxtTechnique), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
              contentBuilder.append(line).append("\n");
            }
          } catch (IOException exception) {
            exception.printStackTrace();
          }
          String descTechnique = contentBuilder.toString();
          String nomStylise = listeNomTechnique[finalI].get(currentIdxElem)[1];
          final String cleanNomStylise = nomStylise.substring(0, 1).toUpperCase() + nomStylise.substring(1);
          // Gestion image preview
          String cheminImg = Launcher.normaliserChemin(Launcher.dossierTechniques+ "/img/" + listeNomTechnique[i].get(currentIdxElem)[0] + "_1.png");
          final Image image = Launcher.chargerImage(cheminImg);
          final ImageView imgView = new ImageView(image);
          imgView.setFitWidth(Math.round(  0.15 * w));
          imgView.setFitHeight(Math.round( 0.15 * w));
          imgView.setStyle(
              "-fx-cursor: hand;"
          );
          imgView.setOnMouseClicked(e -> {
            // Change la visibilité du panneau lateral si on vient de sélectionner le même preview que précédemment
            if ( previewSelectionne == imgView ) {
              boolean isVisible = infoPaneVBox.isVisible();
              infoPaneVBox.setVisible(!isVisible);
              infoPaneVBox.setManaged(!isVisible);
            }
            // Sinon, on change le puzzle sélectionné et on affiche les informations sur le puzzle
            else {
              previewSelectionne = imgView;
              nomTechnique.setText(cleanNomStylise);
              imgTechnique.setImage(image);
              descriptionTechnique.setText(descTechnique);
              infoPaneVBox.setVisible(true);
              infoPaneVBox.setManaged(true);
            }
          });
          VBox vBoxPreview = new VBox();
          vBoxPreview.setSpacing(0.01 * h);
          Label label = new Label(cleanNomStylise);
          Menu.adaptTextSize(label, 25, w, h);
          label.setAlignment(Pos.CENTER);
          HBox centerLabel = new HBox(label);
          centerLabel.setAlignment(Pos.CENTER);
          vBoxPreview.getChildren().addAll(
              imgView,
              centerLabel
          );
          HBoxPreviewContainer.getChildren().add(vBoxPreview);
        }
        VBoxPreviewContainer.getChildren().add(HBoxPreviewContainer);
      }

      previewContainer[i].getChildren().add(VBoxPreviewContainer);
    }

    // Change l'espacement entre les éléments du conteneur de preview des puzzles
    VBox VBoxDifficulteContainer = new VBox();
    VBoxDifficulteContainer.setSpacing(0.02 * h);

    // Ajout du header des règles et des previews des règles
    Label headerRegles = new Label("Règles du jeu");
    headerRegles.setStyle(
        "-fx-background-color: "+Main.secondaryColorCSS+";" +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10px;" +
            "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;"
    );

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
        boolean isVisible = !previewContainer[finalI].isVisible();

        TranslateTransition transitionElement;
        if(!isVisible) {
          transitionElement = new TranslateTransition(Duration.seconds(0.5), previewContainer[finalI]);
          transitionElement.setFromX(0);
          transitionElement.setToX(-(w));
          transitionElement.play();

          transitionElement.setOnFinished(event -> {
            previewContainer[finalI].setVisible(isVisible);
            previewContainer[finalI].setManaged(isVisible);
          });
        } else {
          transitionElement = new TranslateTransition(Duration.seconds(0.5), previewContainer[finalI]);
          transitionElement.setFromX(-(w));
          transitionElement.setToX(0);
          transitionElement.play();

          previewContainer[finalI].setVisible(isVisible);
          previewContainer[finalI].setManaged(isVisible);
        }
      });

      // Ajoute le header de la difficulté dans le conteneur
      VBoxDifficulteContainer.getChildren().add(headerPane);

      // Ajoute le conteneur de preview des puzzles de la difficulté dans le conteneur
      VBoxDifficulteContainer.getChildren().add(previewContainer[i]);

      // Met le conteneur de preview des puzzles de la difficulté en invisible
      previewContainer[finalI].setVisible(false);
      previewContainer[finalI].setManaged(false);
    }

    // Création d'un ScrollPane pour afficher les previews des puzzles
    ScrollPane scrollPane = new ScrollPane(VBoxDifficulteContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // Met le fond du ScrollPane en transparent pour laisser voir l'image de fond
    scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    // Ajoute le ScrollPane au StackPane qui contient le selecteur
    StackPane stackPaneSelecteurPuzzle = new StackPane();
    stackPaneSelecteurPuzzle.getChildren().add(scrollPane);

    // Ajouter une marge de 7% à gauche et 12% en bas au scrollPane
    StackPane.setMargin(scrollPane, new Insets(0.12 * w,  0, 0.17 * h, 0.08 * w));

    // Boite verticale principale qui centre les éléments graphiques
    VBox mainVbox = new VBox();
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

    // Gestion du titre
    Label title = new Label("Glossaire");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, w));
    StackPane.setAlignment(title, Pos.TOP_CENTER);

    // Chargement de l'image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

    // Ajout des éléments graphiques au panneau principal
    StackPane mainPane = new StackPane();
    mainPane.getChildren().addAll(
        backgroundImage,
        title,
        mainVbox,
        backButton,
        infoPaneVBox
    );

    // Met le titre en haut de la boite verticale principale
    StackPane.setAlignment(title, Pos.TOP_CENTER);

    // Met la boite verticale principale au centre du panneau
    StackPane.setAlignment(mainVbox, Pos.CENTER);

    // Met le bouton de retour en bas du panneau
    StackPane.setAlignment(backButton,Pos.BOTTOM_CENTER);

    // Met le paneau lateral d'information à droite
    StackPane.setAlignment(infoPaneVBox, Pos.CENTER_RIGHT);

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
