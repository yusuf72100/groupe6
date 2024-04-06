package groupe6.affichage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.CatalogueProfil;
import groupe6.model.profil.Profil;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MainMenu implements Menu {
    protected static Button backButton;
    protected static Label backText;
    protected static ComboBox<String> profilSelector;
    protected static String[] buttonTextsLabels;
    protected static HBox mainHbox;
    protected static StackPane mainPane;
    protected static HBox[] descriptionsBackground;
    protected static StackPane[] buttonsContainer;
    protected static Button[] buttons;
    protected static Label[] descriptionText;
    protected static Label[] buttonsText;

    // animations
    protected static TranslateTransition[] rectangleTransition;
    protected static TranslateTransition[] rectangleTransitionReverse;
    protected static FadeTransition[] fadeTransition;
    protected static FadeTransition[] fadeTransitionReverse;
    protected static Rectangle[] clipRectangle;
    protected static List<Profil> profils;
    protected static Text title = new Text("Slitherlink");
    protected static Double windowWidth;
    protected static Double windowHeight;

    /**
     * Méthode d'initialisation du menu qui fait office de constructeur
     */
    public static void initMenu() {
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
        System.out.println("\n\n\n" + profils.size() + " profils");
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
                        System.out.println(cheminImageAvatar);
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
                        System.out.println(cheminImageAvatar);
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
     * Affichage du popup de création de profile
     */
    protected static void creerNouveauProfil() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nouveau profil");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du nouveau profil valide:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nom -> {
            if(CatalogueProfil.nomProfilValide(nom)) {
                try {
                    Launcher.catalogueProfils.creerNouveauProfil(nom);
                    System.out.println("Nouveau profil : " + nom);
                    profilSelector.getItems().add(profilSelector.getItems().size()-1, nom);
                    profilSelector.setValue(nom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                creerNouveauProfil();
            }
        });
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
        mainHbox.setSpacing(200); // espacement entre les éléments

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
                            public void handle(ActionEvent actionEvent) {
                                List<String> lstSave = CatalogueSauvegarde
                                        .listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
                                Main.showSaveSelectionMenu();
                                if (!lstSave.isEmpty()) {
                                    String saveName = lstSave.get(0);
                                    System.out.println("Chargement de la sauvegarde : " + saveName);
                                    PartieSauvegarde save = PartieSauvegarde.chargerSauvegarde(saveName,
                                            Launcher.catalogueProfils.getProfilActuel());
                                    Partie partie = Partie.chargerPartie(save,
                                            Launcher.catalogueProfils.getProfilActuel());
                                } else {
                                    System.out.println("Aucune sauvegarde trouvée");
                                }

                            }
                        });
                        descriptionText[finalI].setText("Charger une partie existante");
                        break;
                    case 1:
                        // bouton nouvelle partie
                        buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.showGameModeMenu();
                            }
                        });
                        descriptionText[finalI].setText("Choisissez un mode de jeu");
                        break;
                    case 2:
                        descriptionText[finalI].setText("Entraînez-vous à devenir \nmeilleur au jeu");
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
                Platform.exit();
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
        String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
        mainPane.getChildren().addAll(new ImageView(Launcher.chargerImage(cheminBgImage)), title, mainHbox,
                profilSelector, backButton, backText);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        return mainPane;
    }
}
