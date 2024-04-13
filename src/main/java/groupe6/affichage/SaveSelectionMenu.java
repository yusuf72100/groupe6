package groupe6.affichage;

import java.util.List;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.Profil;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Classe qui correspond au menu de sélection de sauvegarde
 *
 * @author Yusuf
 */
public class SaveSelectionMenu extends MainMenu {
    /**
     * Le bouton de back
     */
    private static Button backButton;

    /**
     * Le conteneur des boutons
     */
    private static StackPane[] buttonsContainer;


    /**
     * Les boutons du menu de sélection de sauvegarde
     */
    private static Button[] buttons;


    /**
     * Les labels des boutons du menu de sélection de sauvegarde
     */
    private static Label[] buttonsText;


    /**
     * Les transitions de fade des rectangles
     */
    private static FadeTransition[] fadeTransition;

    /**
     * Les transitions de fade des rectangles en sens inverse
     */
    private static FadeTransition[] fadeTransitionReverse;

    /**
     * Le titre de la fenêtre
     */
    private static Text title;

    /**
     * La liste des noms des sauvegardes du profil actuel
     */
    private static List<String> lstSave;

    /**
     * Constructeur privé de la classe SaveSelectionMenu qui est entièrement statique
     */
    private SaveSelectionMenu() {}

    /**
     * Méthode qui initialise le menu de sélection de sauvegarde
     */
    public static void initMenu() {
        lstSave = CatalogueSauvegarde.listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
        title = new Text("Sauvegardes");
        backButton = new Button("RETOUR");
        mainHbox = new HBox();
        mainPane = new StackPane();
        buttonsContainer = new StackPane[lstSave.size()];
        buttons = new Button[lstSave.size()];
        descriptionText = new Label[lstSave.size()];
        buttonsText = new Label[lstSave.size()];

        // animations
        fadeTransition = new FadeTransition[lstSave.size()];
        fadeTransitionReverse = new FadeTransition[lstSave.size()];
        profils = Launcher.catalogueProfils.getListeProfils();

        backText = new Label("RETOUR");
        buttons = new Button[lstSave.size()];
    }

    /**
     * Méthode qui extrait la difficulté depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la difficulté de la sauvegarde (String)
     */
    private static String extraireDifficulte(String data) {
        String difficulte = data.split("_")[0];
        String premiereLettre = difficulte.substring(0, 1).toUpperCase();
        String resteDuMot = difficulte.substring(1).toLowerCase();
        return premiereLettre + resteDuMot;
    }

    /**
     * Méthode qui extrait le mode de jeu depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return le mode de jeu de la sauvegarde (String)
     */
    private static String extraireModeDeJeu(String data) {
        String modeDeJeu = data.split("_")[1];

        // Change le format du mode de jeu
        if ( modeDeJeu.equals(ModeJeu.CONTRELAMONTRE.toString()) ) {
            return "Contre la montre";
        }
        else {
            String premiereLettre = modeDeJeu.substring(0, 1).toUpperCase();
            String resteDuMot = modeDeJeu.substring(1).toLowerCase();
            return premiereLettre + resteDuMot;
        }
    }

    /**
     * Méthode qui extrait la taille depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la taille de la sauvegarde (String)
     */
    private static String extraireTaille(String data) {
        return data.split("_")[2];
    }

    /**
     * Méthode qui extrait la date depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la date de la sauvegarde (String)
     */
    private static String extraireDate(String data) {
        return (data.split("_")[3] + "/" + data.split("_")[4] + "/" + data.split("_")[5]);
    }

    /**
     * Méthode qui extrait l'heure depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return l'heure de la sauvegarde (String)
     */
    private static String extraireHeure(String data) {
        return (data.split("_")[6] + ":" + data.split("_")[7] + ":" + data.split("_")[8]);
    }

    /**
     * Méthode qui retourne le menu de sélection de sauvegarde
     *
     * @param windowWidth  la largeur de la fenêtre
     * @param windowHeigth la hauteur de la fenêtre
     * @return le menu de sélection de sauvegarde
     */
    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        initMenu();
        Label noSavesLabel = new Label("Aucune sauvegarde");
        noSavesLabel.setAlignment(Pos.CENTER);
        noSavesLabel.setVisible(false);
        noSavesLabel.setStyle("-fx-font-weight: bold;");
        Menu.adaptTextSize(noSavesLabel, 100, windowWidth, windowHeigth);

        if(!lstSave.isEmpty()) {
            for (int i = 0; i < lstSave.size(); i++) {

                buttons[i] = new Button();
                buttons[i].setMinSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
                buttons[i].setMaxSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
                buttons[i].getStyleClass().add("button-rounded");

                int finalI = i;
                String modeDeJeu = extraireModeDeJeu(lstSave.get(finalI));
                String difficulte = extraireDifficulte(lstSave.get(finalI));
                String taille = extraireTaille(lstSave.get(finalI));
                String date = extraireDate(lstSave.get(finalI));
                String heure = extraireHeure(lstSave.get(finalI));
                String description =
                    "Mode de jeu : " + modeDeJeu + "\n" +
                        "Difficulté : " + difficulte + "\n" +
                        "Taille : " + taille + "\n" +
                        "Date : " + date + "\n" +
                        "Heure : " + heure + "\n";

                buttonsText[i] = new Label(description);
                buttonsText[i].getStyleClass().add("button-text");
                buttonsText[i].setFocusTraversable(false);
                buttonsText[i].setMouseTransparent(true);
                buttonsText[i].setTextAlignment(TextAlignment.CENTER);
                Menu.adaptTextSize(buttonsText[i], 20, windowWidth, windowHeigth);
                buttonsText[i].setWrapText(true);

                buttonsContainer[i] = new StackPane();
                buttonsContainer[i].setAlignment(Pos.CENTER);
                buttonsContainer[i].getChildren().addAll(buttons[i], buttonsText[i]);
                buttonsContainer[i].setMinWidth(buttons[i].getMaxWidth());
                buttonsContainer[i].setMaxWidth(buttons[i].getMaxWidth());
                buttonsContainer[i].setMinHeight(buttons[i].getMaxHeight());
                buttonsContainer[i].setMaxHeight(buttons[i].getMaxHeight());
                buttonsContainer[i].setPadding(new Insets(10, 10, 10, 10));

                // translation text animation
                fadeTransition[i] = new FadeTransition(Duration.seconds(0.3), descriptionText[i]);
                fadeTransition[i].setFromValue(0.0);
                fadeTransition[i].setToValue(1.0);

                fadeTransitionReverse[i] = new FadeTransition(Duration.seconds(0.2), descriptionText[i]);
                fadeTransitionReverse[i].setFromValue(1.0);
                fadeTransitionReverse[i].setToValue(0.0);

                buttonsContainer[i].setOnMouseEntered(e -> {
                    buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            List<String> lstSave = CatalogueSauvegarde
                                    .listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
                            Main.showSaveSelectionMenu();
                            if (!lstSave.isEmpty()) {
                                String saveName;
                                if (finalI >= lstSave.size())
                                    saveName = "Rien";
                                else
                                    saveName = lstSave.get(finalI);
                                System.out.println("Chargement de la sauvegarde : " + saveName);
                                PartieSauvegarde save = PartieSauvegarde.chargerSauvegarde(saveName,
                                        Launcher.catalogueProfils.getProfilActuel());
                                Partie partie = Partie.chargerPartie(save, Launcher.catalogueProfils.getProfilActuel());
                                Main.showGridMenu(partie);
                            } else {
                                System.out.println("Aucune sauvegarde trouvée");
                            }
                        }
                    });
                });
            }
        } else {
            noSavesLabel.setVisible(true);
        }

        HBox box = new HBox();
        box.setSpacing(50);

        for (int i = 0; i < buttonsContainer.length; i++) {
            box.getChildren().add(buttonsContainer[i]);
            box.setAlignment(Pos.CENTER);
        }

        ScrollPane scrollPane = new ScrollPane(box);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));

        // Met le fond du ScrollPane en transparent pour laisser voir l'image de fond
        scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        // Chargement de l'image de fond
        String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
        ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(
            backgroundImage,
            scrollPane
        );
        if ( !lstSave.isEmpty() ) {
            stackPane.getChildren().add(title);
        }
        stackPane.getChildren().addAll(
            backButton,
            noSavesLabel
        );
        title.getStyleClass().add("title");
        title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeight));
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        stackPane.setAlignment(Pos.CENTER);
        StackPane.setAlignment(scrollPane, Pos.CENTER);

        StackPane.setMargin(backButton, new javafx.geometry.Insets(0, 0, 0.05 * windowHeight, 0));
        backButton.getStyleClass().add("button-rounded");
        backButton.getStyleClass().add("button-text");

        // Adaptation de la taille du texte en fonction de la taille de la fenêtre
        double nouvelleTaille = 35 * Math.min(windowWidth / 1920, windowHeight / 1080);
        backButton.setStyle(backButton.getStyle() + "-fx-font-size: " + nouvelleTaille + "px;");
        backButton.setPrefSize(Menu.toPourcentWidth(300.0, windowWidth), Menu.toPourcentHeight(100.0, windowHeight));
        StackPane.setAlignment(backButton,Pos.BOTTOM_CENTER);

        backButton.setOnMouseClicked(e -> {
            Main.showMainMenu();
        });

        // config des touches
        EventHandler<KeyEvent> keyEventHandler = event -> {
            KeyCode keyCode = event.getCode();

            if (keyCode == KeyCode.ESCAPE) {
                Main.showMainMenu();
            }
        };

        stackPane.setOnKeyPressed(keyEventHandler);

        return stackPane;
    }
}