package groupe6.affichage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.CatalogueProfil;
import groupe6.model.profil.Profil;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Classe qui correspond au menu principal de l'application
 *
 * @author Yusuf
 */
public class MainMenu implements Menu {
    // TODO
    /**
     * Le bouton de back
     */
    protected static Button backButton;

    // TODO
    /**
     * Le texte du bouton de back
     */
    protected static Label backText;

    /**
     * Le sélecteur de profil
     */
    protected static ComboBox<String> profilSelector;

    // TODO
    /**
     * La boîte horizontale principale
     */
    protected static HBox mainHbox;

    // TODO
    /**
     * Le panneau d'affichage principal
     */
    protected static StackPane mainPane;

    // TODO
    /**
     * La description des backgrounds
     */
    protected static HBox[] descriptionsBackground;

    // TODO
    /**
     * Le conteneur des boutons
     */
    protected static StackPane[] buttonsContainer;

    /**
     * Les textes des boutons du menu principal
     */
    protected static String[] buttonTextsLabels;

    /**
     *  Les boutons du menu principal
     */
    protected static Button[] buttons;

    /**
     * Les textes des descriptions dans les boutons du menu principal
     */
    protected static Label[] descriptionText;

    /**
     * Les labels des boutons du menu principal
     */
    protected static Label[] buttonsText;

    // animations
    // TODO
    /**
     * Les transitions de translation des rectangles
     */
    protected static TranslateTransition[] rectangleTransition;

    // TODO
    /**
     * Les transitions de translation des rectangles en sens inverse
     */
    protected static TranslateTransition[] rectangleTransitionReverse;

    /**
     * Les transitions de fade
     */
    protected static FadeTransition[] fadeTransition;

    /**
     * Les transitions de fade en sens inverse
     */
    protected static FadeTransition[] fadeTransitionReverse;

    // TODO
    /**
     * Les rectangles pour l'animation de l'affichage des descriptions
     */
    protected static Rectangle[] clipRectangle;

    /**
     * La liste des profils chargés dans le catalogue de profils
     */
    protected static List<Profil> profils;

    /**
     * Le titre de la fenêtre
     */
    protected static Text title = new Text("Slitherlink");

    /**
     * La largeur de la fenêtre
     */
    protected static Double windowWidth;

    /**
     * La hauteur de la fenêtre
     */
    protected static Double windowHeight;

    /**
     * Constructeur de la classe MainMenu qui est entièrement statique
     */
    protected MainMenu() {}

    private static Button settingsButton;

    private static Button profilMenusButton;

    /**
     * Méthode d'initialisation du menu qui fait office de constructeur
     */
    public static void initMenu() {
        profilMenusButton = new Button();
        settingsButton = new Button();
        backText = new Label("QUITTER");
        buttonTextsLabels = new String[] { "CHARGER\nUNE\nPARTIE", "NOUVELLE\nPARTIE", "ENTRAÎNEMENT" };
        title = new Text("Slitherlink");
        backButton = new Button();
        profilSelector = new ComboBox<>();
        mainHbox = new HBox();
        mainPane = new StackPane();
        descriptionsBackground = new HBox[3];
        buttonsContainer = new StackPane[3];
        buttons = new Button[3];
        descriptionText = new Label[3];
        buttonsText = new Label[3];

        // animations
        rectangleTransition = new TranslateTransition[3];
        rectangleTransitionReverse = new TranslateTransition[3];
        fadeTransition = new FadeTransition[3];
        fadeTransitionReverse = new FadeTransition[3];
        clipRectangle = new Rectangle[3];
        profils = Launcher.catalogueProfils.getListeProfils();
    }

    /**
     * Met à jour la combobox de sélection de profil
     */
    protected static void updateProfilsSelector() {
        profils = Launcher.catalogueProfils.getListeProfils();
        profilSelector = new ComboBox<>();

        if (profils != null) {
            for (Profil p : profils) {
                profilSelector.getItems().add(p.getNom());
            }
        } else {
            profilSelector.getItems().add("Invite");
        }
        profilSelector.getItems().add("Ajouter");

        profilSelector.getSelectionModel().selectFirst();
        profilSelector.setTranslateX(Menu.toPourcentWidth(700.0, windowWidth));
        profilSelector.setTranslateY(Menu.toPourcentHeight(-450.0, windowHeight));
        profilSelector.getStyleClass().add("combo-box");

        profilSelector.getItems().addListener((ListChangeListener<String>) change -> {
            if (change.next()) {
                if (change.wasAdded()) {
                    String nouvelleValeur = change.getAddedSubList().get(0);
                    profilSelector.getSelectionModel().select(nouvelleValeur);
                }
            }
        });

        // Header
        profilSelector.setButtonCell(new ListCell<String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    String cheminImageAvatar = null;

                    if (item.equals("Ajouter")) {
                        cheminImageAvatar = Launcher
                                .normaliserChemin(Launcher.dossierAssets + "/icon/ajouter.png");
                    } else {
                        Profil p = Launcher.catalogueProfils.getProfilByName(item);
                        if(p != null) {
                            cheminImageAvatar = p.getIMG();
                        }
                    }

                    if(cheminImageAvatar != null) {
                        imageView.setImage(Launcher.chargerImage(cheminImageAvatar));
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        setGraphic(imageView);
                    }
                }
            }
        });

        // Elements
        profilSelector.setCellFactory(param -> new ListCell<String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    String cheminImageAvatar = null;

                    if (item.equals("Ajouter")) {
                        cheminImageAvatar = Launcher
                                .normaliserChemin(Launcher.dossierAssets + "/icon/ajouter.png");
                    } else {
                        Profil p = Launcher.catalogueProfils.getProfilByName(item);
                        if(p != null) {
                            cheminImageAvatar = p.getIMG();
                        }
                    }

                    if(cheminImageAvatar != null) {
                        imageView.setImage(Launcher.chargerImage(cheminImageAvatar));
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        setGraphic(imageView);
                    }
                }
            }
        });

        // handler des éléments
        profilSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("Ajouter")) {
                    profilSelector.getSelectionModel().selectedItemProperty().removeListener(this);
                    creerNouveauProfil();
                    profilSelector.getSelectionModel().selectedItemProperty().addListener(this);
                } else {
                    Launcher.catalogueProfils.setProfilActuel(Launcher.catalogueProfils.getProfilByName(newValue));
                }
            }
        });

        profilSelector.getSelectionModel().select(Launcher.catalogueProfils.getProfilActuel().getNom());
    }

    /**
     * Affichage du popup de création de profil
     */
    protected static void creerNouveauProfil() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nouveau profil");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du nouveau profil valide:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nom -> {
            if(CatalogueProfil.nomProfilValide(nom) && nom != "Ajouter" && !CatalogueProfil.profilExiste(nom)) {
                try {
                    Launcher.catalogueProfils.creerNouveauProfil(nom);
                    profilSelector.getItems().add(profilSelector.getItems().size()-1, nom);
                    profilSelector.setValue(nom);
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                creerNouveauProfil();
            }
        });

        // on remet l'ancien
        for (String item : profilSelector.getItems()) {
            if (item.equals(Launcher.catalogueProfils.getProfilActuel().getNom())) {
                profilSelector.setValue(item);
                break;
            }
        }
    }

    /**
     * Permet d'accéder au Node qui représentera le menu en question
     * @param w largeur de la fenetre
     * @param h hauteur de la fenetre
     * @return renvoi un Node javafx pour l'affichage, en l'occurence un StackPane
     */
    public static StackPane getMenu(Double w, Double h) {
        initMenu();
        windowWidth = w;
        windowHeight = h;
        title.getStyleClass().add("title");
        title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeight));
        mainHbox.setSpacing(200);

        OptionsMenu.initMenu(w,h);
        OptionsMenu.setProfil(Launcher.catalogueProfils.getProfilActuel());

        settingsButton.getStyleClass().add("settingsButton");
        settingsButton.setPrefSize(Menu.toPourcentWidth(50.0, w), Menu.toPourcentWidth(50.0, w));

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), settingsButton);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setCycleCount(1);

        settingsButton.setOnMouseClicked(event -> {
            OptionsMenu.showMenu();
        });

        settingsButton.setOnMouseEntered(event -> {
            rotateTransition.stop();
            rotateTransition.setByAngle(360);
            rotateTransition.play();
        });

        settingsButton.setOnMouseExited(event -> {
            rotateTransition.stop();
            rotateTransition.setByAngle(-360);
            rotateTransition.play();
        });

        updateProfilsSelector();

        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;

            buttons[finalI] = new Button();
            buttons[finalI].setPrefSize(Menu.toPourcentWidth(350.0, windowWidth),
                    Menu.toPourcentHeight(500.0, windowHeight));
            buttons[finalI].getStyleClass().add("button-rounded");

            buttonsText[finalI] = new Label(buttonTextsLabels[finalI]);
            buttonsText[finalI].getStyleClass().add("button-text");
            buttonsText[finalI].setFocusTraversable(false);
            buttonsText[finalI].setMouseTransparent(true);
            buttonsText[finalI].setTextAlignment(TextAlignment.CENTER);
            Menu.adaptTextSize(buttonsText[finalI], 35, windowWidth, windowHeight);
            buttonsText[finalI].setWrapText(true);

            descriptionsBackground[finalI] = new HBox();
            descriptionsBackground[finalI].setMaxSize(buttons[finalI].getPrefWidth(),
                    Menu.toPourcentHeight(100.0, windowHeight));
            descriptionsBackground[finalI].setStyle("-fx-background-color: transparent;");
            descriptionsBackground[finalI].setTranslateY(1);
            StackPane.setAlignment(descriptionsBackground[finalI], Pos.BOTTOM_CENTER);

            // positionnement de la description
            descriptionText[finalI] = new Label();
            descriptionText[finalI].setFocusTraversable(false);
            descriptionText[finalI].setMouseTransparent(true);
            descriptionText[finalI].setTranslateY(Menu.toPourcentHeight(200.0, windowHeight));
            descriptionText[finalI].getStyleClass().add("description-text");
            Menu.adaptTextSize(descriptionText[finalI], 18, windowWidth, windowHeight);
            descriptionText[finalI].setWrapText(true);

            // déplacer le texte vers le bas
            buttonsContainer[finalI] = new StackPane();
            buttonsContainer[finalI].setAlignment(Pos.CENTER);
            buttonsContainer[finalI].getChildren().addAll(buttons[finalI], buttonsText[finalI],
                    descriptionsBackground[finalI], descriptionText[finalI]);

            // translation text animation
            clipRectangle[finalI] = new Rectangle(buttons[finalI].getPrefWidth(),
                    Menu.toPourcentHeight(100.0, windowHeight));
            descriptionsBackground[finalI].getStyleClass().add("description-background");
            descriptionsBackground[finalI].setClip(clipRectangle[finalI]);
            descriptionsBackground[finalI].setFocusTraversable(false);
            descriptionsBackground[finalI].setMouseTransparent(true);

            rectangleTransition[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransition[finalI].setFromY(Menu.toPourcentHeight(200.0, windowHeight));
            rectangleTransition[finalI].setToY(0);

            rectangleTransitionReverse[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransitionReverse[finalI].setFromY(0);
            rectangleTransitionReverse[finalI].setToY(Menu.toPourcentHeight(200.0, windowHeight));

            fadeTransition[finalI] = new FadeTransition(Duration.seconds(0.3), descriptionText[finalI]);
            fadeTransition[finalI].setFromValue(0.0);
            fadeTransition[finalI].setToValue(1.0);

            fadeTransitionReverse[finalI] = new FadeTransition(Duration.seconds(0.2), descriptionText[finalI]);
            fadeTransitionReverse[finalI].setFromValue(1.0);
            fadeTransitionReverse[finalI].setToValue(0.0);

            // hover on
            buttonsContainer[finalI].setOnMouseEntered(e -> {
                descriptionText[finalI].setTextAlignment(TextAlignment.CENTER);

                switch (finalI) {
                    // bouton charger une partie
                    case 0:
                        buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) { Main.showSaveSelectionMenu(); }
                        });
                        descriptionText[finalI].setText("Charger une partie en cours");
                        break;
                    // bouton nouvelle partie
                    case 1:

                        buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.showGameModeMenu();
                            }
                        });
                        descriptionText[finalI].setText("Choisissez un mode de jeu");
                        break;
                    // bouton entrainement
                    case 2:
                        descriptionText[finalI].setText(
                            "Decouvrez les règles et apprenez" +
                            "\n" +
                            "des techniques"
                        );

                        buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.showEntrainementMenu();
                            }
                        });
                        break;
                    default:
                        descriptionText[finalI].setText("Placeholder #" + finalI);
                        break;
                }

                descriptionsBackground[finalI].setStyle("-fx-background-color: gray;");
                rectangleTransition[finalI].play();
                fadeTransition[finalI].play();
            });

            // hover off
            buttonsContainer[finalI].setOnMouseExited(e -> {
                rectangleTransition[finalI].stop();
                fadeTransition[finalI].stop();
                rectangleTransitionReverse[finalI].play();
                fadeTransitionReverse[finalI].play();
            });

            buttonsContainer[finalI].setMaxSize(buttonsContainer[finalI].getPrefWidth(),
                    buttons[finalI].getPrefHeight());
        }

        backButton.setPrefSize(Menu.toPourcentWidth(300.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
        backButton.setTranslateY(Menu.toPourcentHeight(400.0, windowHeight));
        backButton.getStyleClass().add("button-rounded");

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.exitAll();
            }
        });

        backText.setTranslateY(Menu.toPourcentHeight(400.0, windowHeight));
        backText.getStyleClass().add("button-text");
        backText.setFocusTraversable(false);
        backText.setMouseTransparent(true);
        Menu.adaptTextSize(backText, 35, windowWidth, windowHeight);

        mainHbox.setAlignment(Pos.CENTER);
        mainHbox.getChildren().addAll(buttonsContainer);
        mainPane.setAlignment(Pos.CENTER);

        EventHandler<KeyEvent> keyEventHandler = event -> {
            KeyCode keyCode = event.getCode();

            if (keyCode == KeyCode.ESCAPE) {
                OptionsMenu.hideMenu();
            }
        };

        profilMenusButton.setPrefSize(profilSelector.getPrefWidth(), Menu.toPourcentHeight(60.0, h));
        profilMenusButton.setTranslateY(profilSelector.getTranslateY());
        profilMenusButton.setTranslateX(profilSelector.getTranslateX() + Menu.toPourcentWidth(150.0, w));
        profilMenusButton.getStyleClass().add("button-carre");

        profilMenusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.showProfileMenu();
            }
        });

        String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");

        mainPane.getChildren().addAll(
            new ImageView(Launcher.chargerImage(cheminBgImage)),
            title, 
            mainHbox,
            profilSelector,
            profilMenusButton,
            backButton,
            backText,
            settingsButton,
            OptionsMenu.getMenu()
        );
      
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(settingsButton, Pos.TOP_LEFT);

        mainPane.setOnKeyPressed(keyEventHandler);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        return mainPane;
    }
}