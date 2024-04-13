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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    // Initialisation des éléments graphiques du menu
    // ==========================================================================

    // StackPane qui contient tout le menu
    StackPane mainPane = new StackPane();
    // Chargement de l'image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));
    // Le titre du menu
    Label title = new Label("Glossaire");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, w));
    // La boite verticale qui centre verticalement le sélecteur de preview
    VBox mainVbox = new VBox();
    mainVbox.setSpacing(0.10 * h);
    mainVbox.setAlignment(Pos.CENTER);
    // Le bouton de retour
    Button backButton = new Button("RETOUR");
    StackPane.setMargin(backButton, new Insets(0, 0, 0.05 * h, 0));
    backButton.getStyleClass().add("button-rounded");
    backButton.getStyleClass().add("button-text");
    backButton.setStyle(backButton.getStyle() + "-fx-font-size: " + (35 * Math.min(w / 1920, h / 1080)) + "px;");
    backButton.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
    backButton.setOnMouseClicked(e -> {
      Main.showMainMenu();
    });

    // ========================================================================
    // Initialisation des éléments graphiques du panneau d'information
    // ========================================================================

    // Le panneau lateral d'information
    VBox infoPane =  new VBox();
    infoPane.setVisible(false);
    infoPane.setManaged(false);
    infoPane.setStyle(
        "-fx-background-color: "+Main.mainColorCSS+";" +
        " -fx-padding: "+Math.round(0.02 * h)+"px;" +
        " -fx-background-radius: 10px 0px 0px 10px;" +
        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
    );
    infoPane.setSpacing(0.08 * h);
    infoPane.setMinWidth(0.30 * w);
    infoPane.setMaxWidth(0.30 * w);
    infoPane.setSpacing(0.05 * h);
    infoPane.setAlignment(Pos.CENTER_RIGHT);
    // Création de la croix pour fermer le paneau lateral d'information
    Label croix = new Label("✖");
    croix.setStyle(
        "-fx-text-fill: black;" +
        "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
        "-fx-cursor: hand;"
    );
    final VBox finalInfoPane = infoPane;
    croix.setOnMouseClicked(e -> {
      finalInfoPane.setVisible(false);
      finalInfoPane.setManaged(false);
    });
    StackPane.setAlignment(croix, Pos.TOP_RIGHT);
    // Boite horizontale pour mettre la croix en haut à droite
    HBox HBoxCroix = new HBox();
    HBoxCroix.setAlignment(Pos.TOP_RIGHT);
    // Boite verticale qui contient les informations sur le puzzle sélectionné
    VBox infoTechnique = new VBox();
    infoTechnique.setSpacing(0.01 * h);
    infoTechnique.setAlignment(Pos.CENTER);
    // Element qui sert à faire un espace pour l'affichage
    Pane[] espace = new Pane[3];
    for (int i = 0; i < 3; i++) {
      espace[i] = new Pane();
      espace[i].setMinHeight(0.02 * h);
    }
    // Image du preview sélectionné
    ImageView imgPreview = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
    imgPreview.setFitWidth(Math.round(0.16 * w));
    imgPreview.setFitHeight(Math.round(0.16 * w));
    // Label information sur le preview
    Label titre = new Label("Information sur la technique");
    Menu.adaptTextSize(titre, 35, w, h);
    titre.setStyle(
        titre.getStyle() +
        " -fx-text-fill: black;" +
        " -fx-padding: 10px;" +
        " -fx-background-radius: 10px;" +
        "-fx-font-weight: bold;"
    );
    Label nomPreview = new Label("Nom de la technique");
    Menu.adaptTextSize(nomPreview, 30, w, h);
    nomPreview.setStyle(
        nomPreview.getStyle() +
        "-fx-text-fill: black;" +
        "-fx-font-weight: bold;"
    );
    Label descriptionPreview = new Label("Description de la technique");
    Menu.adaptTextSize(descriptionPreview, 28, w, h);
    descriptionPreview.setMinWidth(0.25 * w);
    descriptionPreview.setMaxWidth(0.25 * w);
    descriptionPreview.setWrapText(true);
    descriptionPreview.setStyle(
        descriptionPreview.getStyle() +
        "-fx-text-fill: black;"
    );
    // Bouton pour lancer l'entrainement
    Button btnEntrainement = new Button("ENTRAINEMENT");
    btnEntrainement.setPrefSize(Menu.toPourcentWidth(250.0, w), Menu.toPourcentHeight(100.0, h));
    btnEntrainement.getStyleClass().add("button-rounded-play");
    btnEntrainement.getStyleClass().add("button-text");
    btnEntrainement.setStyle(btnEntrainement.getStyle() + "-fx-font-size: " + (28 * Math.min(w / 1920, h / 1080)) + "px;");
    btnEntrainement.setOnMouseClicked(e -> {
      // TODO : Lancer entrainement
    });
    StackPane.setMargin(btnEntrainement, new Insets(0, 0, 0.05 * h, 0));
    // Boite verticale qui contient le contenu du panneau d'information
    VBox VBoxContenueInfoPane = new VBox();
    VBoxContenueInfoPane.getChildren().addAll(
        titre,
        infoTechnique
    );
    VBoxContenueInfoPane.setSpacing(0.02 * h);
    VBoxContenueInfoPane.setAlignment(Pos.CENTER);
    // ScrollPane pour le contenu du panneau d'information
    ScrollPane infoPaneScroll = new ScrollPane(VBoxContenueInfoPane);
    infoPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // ========================================================================
    // Initialisation des éléments graphiques du sélecteur de preview
    // ========================================================================

    // StackPane qui contient le sélecteur de preview
    StackPane stackPaneSelecteurPuzzle = new StackPane();
    // Création d'un ScrollPane pour afficher les previews
    ScrollPane scrollPaneSelecteur = new ScrollPane();
    scrollPaneSelecteur.setFitToWidth(true);
    scrollPaneSelecteur.setFitToHeight(true);
    scrollPaneSelecteur.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPaneSelecteur.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    StackPane.setMargin(scrollPaneSelecteur, new Insets(0.12 * w,  0, 0.17 * h, 0.08 * w));
    // Boite verticale qui contient les headers et les conteneurs de preview
    VBox VBoxHeaderAndContent = new VBox();
    // Les headers
    StackPane[] header = new StackPane[4];
    // Création du tableau qui contient les listes de previews
    List<VBox>[] lstPreview = new List[]{
        // Regles
        new ArrayList<VBox>(),
        // DEMARRAGE
        new ArrayList<VBox>(),
        // BASIQUE
        new ArrayList<VBox>(),
        // AVANCEE
        new ArrayList<VBox>(),
    };
    // Création du tableau qui contient les conteneurs de previews
    // Ajout des previews dans les conteneurs
    HBox[] previewContainer = new HBox[]{
        new HBox(),
        new HBox(),
        new HBox(),
        new HBox()
    };


    // ==========================================================================
    // Chargement des éléments graphiques du infoPane
    // ==========================================================================

    // Ajout des éléments graphiques dans la boite verticale d'information
    infoTechnique.getChildren().addAll(
        nomPreview,
        espace[0],
        imgPreview,
        espace[1],
        descriptionPreview,
        espace[2],
        btnEntrainement
    );

    // Ajout de la croix dans la boite horizontale
    HBoxCroix.getChildren().add(croix);

    // Ajout des informations dans le paneau lateral d'information
    infoPane.getChildren().addAll(
        HBoxCroix,
        infoPaneScroll
    );
    StackPane.setAlignment(HBoxCroix, Pos.TOP_RIGHT);

    // ==========================================================================
    // Chargement des éléments graphiques du sélecteur de preview
    // ==========================================================================

    // Chargement des headers
    String[] headersName = new String[4];
    headersName[0] = "REGLES DU JEU";
    for (int i = 0; i < DifficulteTechnique.values().length; i++) {
      headersName[i + 1] = DifficulteTechnique.values()[i].toString();
    }
    for (int i = 0; i < 4; i++) {
      int finalI = i;
      Label labelHeader = new Label(headersName[i]);
      labelHeader.setStyle(
          "-fx-background-color: "+Main.secondaryColorCSS+";" +
          "-fx-text-fill: black;" +
          "-fx-font-weight: bold;" +
          "-fx-padding: 10px;" +
          "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
          "-fx-background-radius: 10px;" +
          "-fx-cursor: hand;"
      );
      labelHeader.setOnMouseClicked(e -> {
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
      header[i] = new StackPane(labelHeader);
      StackPane.setAlignment(labelHeader, Pos.CENTER_LEFT);
    }

    // Chargement des informations des previews
    List<String> nomRegles = Launcher.getRulesInfoNames();
    List<String[]>[] listeNomTechnique = GestionnaireTechnique.getInstance().nomTechniques();
    List<String[]>[] infoPreview = new List[] {
        new ArrayList<String[]>(),
        new ArrayList<String[]>(),
        new ArrayList<String[]>(),
        new ArrayList<String[]>()
    };
    for (int i = 0; i < 4; i++) {
      String nomFichier;
      String nomStyle;
      String cheminImg;
      String cheminDesc;
      if ( i == 0 ) {
        for (String nomRegle : nomRegles) {
          nomFichier = nomRegle;
          nomStyle = cleanNomRegles(nomFichier);
          cheminImg = Launcher.normaliserChemin(Launcher.dossierRegles + "/img/" + nomFichier + ".png");
          cheminDesc = Launcher.normaliserChemin(Launcher.dossierRegles + "/description/" + nomFichier + ".desc");
          infoPreview[i].add(new String[]{nomStyle, cheminImg, cheminDesc});
        }
      }
      else {
        for (int j = 0; j < listeNomTechnique[i-1].size(); j++) {
          String[] techName = listeNomTechnique[i-1].get(j);
          nomFichier = techName[0];
          nomStyle = techName[1];
          nomStyle = nomStyle.substring(0, 1).toUpperCase() + nomStyle.substring(1);
          cheminImg = Launcher.normaliserChemin(Launcher.dossierTechniques + "/img/" + nomFichier + "_1.png");
          cheminDesc = Launcher.normaliserChemin(Launcher.dossierTechniques + "/description/" + nomFichier + ".desc");
          infoPreview[i].add(new String[]{nomStyle, cheminImg, cheminDesc});
        }
      }
    }

    // Chargement des previews
    for ( int i = 0; i < 4; i++) {
      for (int j = 0; j < infoPreview[i].size(); j++) {
        // VBox qui contient les informations du preview
        VBox VBoxPreview = new VBox();
        VBoxPreview.setSpacing(0.01 * h);
        // Récupération des informations du preview
        String[] info = infoPreview[i].get(j);
        // Label qui contient le nom du preview
        String nomStyilise = info[0];
        Label labelPreviewName = new Label(nomStyilise);
        Menu.adaptTextSize(labelPreviewName, 25, w, h);
        labelPreviewName.setAlignment(Pos.CENTER);
        // HBox qui centre le label
        HBox HBoxCenterLabelPreviewName = new HBox(labelPreviewName);
        HBoxCenterLabelPreviewName.setAlignment(Pos.CENTER);
        // Verifie si c'est une technique
        /// boolean estTechnique = estTechnique(info[1]);
        boolean estTechnique = false; // False car entrainement non disponible
        // Description de dy preview
        final String description = getDescriptionPreview(info[2]);
        // Image de la technique
        final Image img = Launcher.chargerImage(info[1]);
        final ImageView imgView = new ImageView(img);
        imgView.setFitWidth(0.15 * w);
        imgView.setFitHeight(0.15 * w);
        imgView.setStyle(
            "-fx-cursor: hand;"
        );
        imgView.setOnMouseClicked(e -> {
          if ( !estTechnique ) {
            btnEntrainement.setVisible(false);
            btnEntrainement.setManaged(false);
          } else {
            btnEntrainement.setVisible(true);
            btnEntrainement.setManaged(true);
          }
          if ( previewSelectionne == imgView ) {
            boolean isVisible = infoPane.isVisible();
            infoPane.setVisible(!isVisible);
            infoPane.setManaged(!isVisible);
          } else{
            previewSelectionne = imgView;
            nomPreview.setText(nomStyilise);
            imgPreview.setImage(img);
            descriptionPreview.setText(description);
            infoPane.setVisible(true);
            infoPane.setManaged(true);
          }
        });
        // Ajout de l'image et du label dans la VBoxPreview
        VBoxPreview.getChildren().addAll(
            imgView,
            HBoxCenterLabelPreviewName
        );
        // Ajout de la VBoxPreview dans la liste des previews
        lstPreview[i].add(VBoxPreview);
      }
    }

    for (int i = 0; i < 4; i++) {
      VBox VBoxLignePreviewContainer = new VBox();
      VBoxLignePreviewContainer.setSpacing(0.02 * h);
      VBoxLignePreviewContainer.setStyle(
          "-fx-background-color: "+Main.mainColorCSS+";" +
          " -fx-padding: "+Math.round(0.04 * h)+"px;" +
          " -fx-background-radius: 10px;"
      );
      int index = 0;
      for (int j = 0; j < 2; j++) {
        HBox HBoxLignePreview = new HBox();
        HBoxLignePreview.setSpacing(0.03 * w);
        for (int k = 0; k < 3 && index < lstPreview[i].size(); k++, index++) {
          HBoxLignePreview.getChildren().add(lstPreview[i].get(index));
        }
        VBoxLignePreviewContainer.getChildren().add(HBoxLignePreview);
      }
      previewContainer[i].getChildren().add(VBoxLignePreviewContainer);
      previewContainer[i].setVisible(false);
      previewContainer[i].setManaged(false);
    }

    for (int i = 0; i < 4; i++) {
      VBoxHeaderAndContent.getChildren().addAll(
          header[i],
          previewContainer[i]
      );
    }
    VBoxHeaderAndContent.setSpacing(0.02 * h);

    // Ajoute la VBOX qui contients les headers et les conteneurs de preview
    scrollPaneSelecteur.setContent(VBoxHeaderAndContent);

    // Ajoute le ScrollPane au StackPane qui contient le sélecteur
    stackPaneSelecteurPuzzle.getChildren().add(scrollPaneSelecteur);

    // Ajout du StackPane du sélecteur à la boite verticale principale
    mainVbox.getChildren().add(stackPaneSelecteurPuzzle);

    // Ajout des éléments graphiques au panneau principal
    mainPane.getChildren().addAll(
        backgroundImage,
        title,
        mainVbox,
        backButton,
        infoPane
    );

    // Positionnement des éléments graphiques
    StackPane.setAlignment(title, Pos.TOP_CENTER);
    StackPane.setAlignment(mainVbox, Pos.CENTER);
    StackPane.setAlignment(backButton,Pos.BOTTOM_CENTER);
    StackPane.setAlignment(infoPane, Pos.CENTER_RIGHT);

    // Detection touche action retour
    EventHandler<KeyEvent> keyEventHandler = event -> {
      KeyCode keyCode = event.getCode();

      if (keyCode == KeyCode.ESCAPE) {
        Main.showGameModeMenu();
      }
    };
    mainPane.setOnKeyPressed(keyEventHandler);

    // Renvoi le menu
    return mainPane;
  }

  /**
   * Méthode pour nettoyer le nom d'une règle
   *
   * @param nomRegle le nom de la règle
   * @return le nom de la règle nettoyé
   */
  public static String cleanNomRegles(String nomRegle) {

    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < nomRegle.length(); i++) {
      if ( i != 0 && Character.isUpperCase(nomRegle.charAt(i)) ) {
        strBuilder.append(" ");
        strBuilder.append(Character.toLowerCase(nomRegle.charAt(i)));
      }
      else {
        strBuilder.append(nomRegle.charAt(i));
      }
    }

    String cleanNom = strBuilder.toString();
    cleanNom = cleanNom.substring(0, 1).toUpperCase() + cleanNom.substring(1);

    return cleanNom;
  }

  /**
   * Méthode pour obtenir la description depuis un fichier .desc
   *
   * @param cheminDesc le chemin du fichier .desc
   * @return la description
   */
  public static String getDescriptionPreview(String cheminDesc) {
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cheminDesc), StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        contentBuilder.append(line).append("\n");
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return contentBuilder.toString();
  }

  /**
   * Méthode pour savoir si c'est le chemin d'une technique
   *
   * @param chemin le chemin
   * @return vrai si c'est une technique, faux sinon
   */
  public static boolean estTechnique(String chemin) {
    // Si le chemmin comment pas Launcher.dossierTechniques
    if ( chemin.startsWith(Launcher.normaliserChemin(Launcher.dossierTechniques)) ) {
      return true;
    }
    return false;
  }

}
