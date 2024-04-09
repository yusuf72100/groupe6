package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
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

/**
 * Classe qui correspond au menu du mode classique qui permet de choisir sur quel puzzle l'utilisateur veut jouer
 *
 * @author Yamis
 */
public class ClassicModeMenu implements Menu {

  /**
   * La largeur de la fenêtre
   */
  protected static Double windowWidth;

  /**
   * La hauteur de la fenêtre
   */
  protected static Double windowHeight;

  /**
   * Le titre de la fenêtre
   */
  protected static Label title;

  /**
   * Le panneau d'affichage principal
   */
  protected static StackPane mainPane;

  /**
   * L'image de fond
   */
  protected static ImageView backgroundImage;

  /**
   * Le panneau d'affichage qui contient le selecteur de puzzle
   */
  protected  static StackPane stackPaneSelecteurPuzzle;

  /**
   * La boîte verticale principale qui place le titre et le selecteur de puzzle
   */
  protected static VBox mainVbox;

  /**
   * La boîte verticale qui contient les headers et les conteneurs de preview des puzzles pour chaque difficulté
   */
  protected static VBox VBoxDifficulteContainer;

  /**
   * Les boite horizontale par difficulté qui contiennent les previews des puzzles
   */
  protected static HBox[] PuzzlePreviewContainer;

  /**
   * Le panneau lateral d'information sur le puzzle sélectionné
   */
  protected static VBox infoPane;

  /**
   * Les informations textuelles sur le puzzle sélectionné
   */
  protected static VBox infoPuzzle;


  /**
   * Le puzzle sélectionné
   */
  protected static ImageView previewSelectionne;

  /**
   * La difficulté du puzzle sélectionné
   */
  protected static int difficulteSelectionne;

  /**
   * Le numéro du puzzle sélectionné dans sa difficulté
   */
  protected  static int numeroPuzzleSelectionne;


  /**
   * L'image du puzzle sélectionné dans le panneau latéral d'info
   */
  protected static ImageView imgPreviewInfo;

  /**
   * La difficulté du puzzle sélectionné
   */
  protected static Label infoDifficulte;

  /**
   * La taille du puzzle sélectionné
   */
  protected static Label infoTaille;

  /**
   * Les points de départ du puzzle sélectionné
   */
  protected static Label infoPointsDepart;

  /**
   * Le bouton de retour
   */
  protected static Button backButton;


  /**
   * Constructeur privé de la ClassicModeMenu qui est entièrement statique
   */
  protected ClassicModeMenu() {}

  public static void initMenuSelectionPuzzle() {
    // Création du panneau principal
    mainPane = new StackPane();
    // Création des éléments graphiques pour le selecteur de puzzle
    mainVbox = new VBox();
    VBoxDifficulteContainer = new VBox();
    PuzzlePreviewContainer = new HBox[]{new HBox(), new HBox(), new HBox()};
    stackPaneSelecteurPuzzle = new StackPane();
    // Création des éléments graphiques pour le panneau d'information sur le puzzle sélectionné
    infoPane = new VBox();
    infoPane.setVisible(false);
    infoPane.setManaged(false);
    infoPuzzle = new VBox();
    imgPreviewInfo = new ImageView(Launcher.chargerImage(Launcher.normaliserChemin(Launcher.dossierAssets + "/img/noPuzzle.png")));
    imgPreviewInfo.setFitWidth(Math.round(0.16 * windowWidth));
    imgPreviewInfo.setFitHeight(Math.round(0.16 * windowWidth));


  }

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
    titre.setStyle(
        " -fx-text-fill: white;" +
        " -fx-padding: 10px;" +
        " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
        " -fx-background-radius: 10px;"
    );

    // Les informations textuelles sur le puzzle
    infoDifficulte = new Label("Difficulté : ");
    infoTaille = new Label("Taille : ");
    infoPointsDepart = new Label("Points de départ : ");

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

    // Detecte les clics sur le bouton pour lancer le puzzle en mode classique
    btnJouer.setOnMouseClicked(e -> {
      Main.lancerPartie(difficulteSelectionne, numeroPuzzleSelectionne, ModeJeu.CLASSIQUE);
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
        espace2,
        btnJouer
    );
    infoPuzzle.setSpacing(0.01 * windowHeight);
    infoPuzzle.setAlignment(Pos.CENTER);

    // Création de la croix pour fermer le paneau lateral d'information
    Label croix = new Label("X");
    croix.setStyle(
        "-fx-text-fill: white;" +
        "-fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
        "-fx-cursor: hand;"
    );

    // Detecte les clics sur la croix pour fermer le paneau lateral d'information
    VBox finalInfoPane = infoPane;
    croix.setOnMouseClicked(e -> {
      finalInfoPane.setVisible(false);
      finalInfoPane.setManaged(false);
    });


    // Ajout des informations dans le paneau lateral d'information
    infoPane.getChildren().addAll(
        croix,
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
          imgPreviewPuzzle.setFitWidth(Math.round(0.10 * windowWidth));
          imgPreviewPuzzle.setFitHeight(Math.round(0.10 * windowWidth));
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
              updateInfoPuzzleSelectionneClassique(puzzle);
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
          " -fx-text-fill: white;" +
          " -fx-padding: 10px;" +
          " -fx-font-size: "+Math.round(1080 * 0.03)+"px ;" +
          " -fx-background-radius: 10px;" +
          " -fx-cursor: hand;"
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
    StackPane.setMargin(scrollPane, new javafx.geometry.Insets(0.08 * windowWidth,  0, 0.17 * windowHeight, 0.08 * windowWidth));

    // Gestion du titre
    title = new Label("Mode Classique");
    title.getStyleClass().add("title");
    title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeight));
    // Adaptation de la taille du texte en fonction de la taille de la fenêtre
    Menu.adaptTextSize(title,60, windowWidth, windowHeight);

    // Boite verticale principale qui contient le titre et le selecteur de puzzle
    mainVbox.setSpacing(0.10 * windowHeight);
    mainVbox.getChildren().add(stackPaneSelecteurPuzzle);
    mainVbox.setAlignment(Pos.CENTER);

    // Gestion du bouton de retour
    backButton = new Button("Retour");
    StackPane.setMargin(backButton, new javafx.geometry.Insets(0, 0, 0.05 * windowHeight, 0));
    backButton.getStyleClass().add("button-text");
    backButton.getStyleClass().add("button-rounded");
    backButton.setStyle( backButton.getStyle() + "-fx-cursor: hand;");
    backButton.setPrefSize(Menu.toPourcentWidth(200.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
//    Menu.adaptTextSize(backButton, 35, windowWidth, windowHeight);
    backButton.setOnMouseClicked(e -> {
      Main.showGameModeMenu();
    });

    // Change le curseur quand on passe sur le bouton de retour avec le css


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

  public static void updateInfoPuzzleSelectionneClassique(PuzzleSauvegarde puzzleSelectionne) {
    // Change les info textuel du puzzle sélectionné
    infoDifficulte.setText("Difficulté : " + puzzleSelectionne.getDifficulte().toString());
    infoTaille.setText("Taille : " + puzzleSelectionne.getLargeur() + "x" + puzzleSelectionne.getLongueur());
    infoPointsDepart.setText("Points de départ : " + Score.getScoreDebut(puzzleSelectionne.getDifficulte()));
  }
}