package groupe6.affichage;

import java.util.List;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.Profil;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    // TODO : supprimer si pas utilisé
    /**
     * Le bouton de back
     */
    private static Button backButton;

    // TODO : supprimer si pas utilisé
    /**
     * Le texte du bouton de back
     */
    private static Label backText;

    // TODO : supprimer si pas utilisé
    /**
     * Le sélecteur de profil
     */
    private static ComboBox<String> profilSelector;

    // TODO : supprimer si pas utilisé
    /**
     * La boîte horizontale principale
     */
    private static HBox mainHbox;

    // TODO : supprimer si pas utilisé
    /**
     * Le panneau d'affichage principal
     */
    private static StackPane mainPane;

    // TODO : supprimer si pas utilisé
    /**
     * La description des backgrounds
     */
    private static HBox[] descriptionsBackground;

    // TODO
    /**
     * Le conteneur des boutons
     */
    private static StackPane[] buttonsContainer;

    /**
     * Les textes des boutons du menu de sélection de sauvegarde
     */
    private static String[] buttonTextsLabels;

    /**
     * Les boutons du menu de sélection de sauvegarde
     */
    private static Button[] buttons;

    /**
     * Les textes des descriptions dans les boutons du menu de sélection de sauvegarde
     */
    private static Label[] descriptionText;

    /**
     * L.es labels des boutons du menu de sélection de sauvegarde
     */
    private static Label[] buttonsText;

    // animations
    // TODO : supprimer si pas utilisé
    /**
     * Les transitions de translation des rectangles
     */
    private static TranslateTransition[] rectangleTransition;

    // TODO : supprimer si pas utilisé
    /**
     * Les transitions de translation des rectangles en sens inverse
     */
    private static TranslateTransition[] rectangleTransitionReverse;

    /**
     * Les transitions de fade des rectangles
     */
    private static FadeTransition[] fadeTransition;

    /**
     * Les transitions de fade des rectangles en sens inverse
     */
    private static FadeTransition[] fadeTransitionReverse;

    // TODO : supprimer si pas utilisé
    /**
     * Les rectangles pour l'animation de l'affichage des descriptions
     */
    private static Rectangle[] clipRectangle;

    // TODO : supprimer si pas utilisé
    /**
     * Le titre de la fenêtre
     */
    private static Text title = new Text("Slitherlink");

    // TODO : supprimer si pas utilisé
    /**
     * La liste des profils chargés dans le catalogue de profils
     */
    private static List<Profil> profils;

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
        title = new Text("Choisissez une partie");
        backButton = new Button();
        mainHbox = new HBox();
        mainPane = new StackPane();
        buttonsContainer = new StackPane[lstSave.size() + 10];
        buttons = new Button[lstSave.size() + 10];
        descriptionText = new Label[lstSave.size() + 10];
        buttonsText = new Label[lstSave.size() + 10];

        // animations
        fadeTransition = new FadeTransition[lstSave.size() + 10];
        fadeTransitionReverse = new FadeTransition[lstSave.size() + 10];
        profils = Launcher.catalogueProfils.getListeProfils();

        backText = new Label("RETOUR");
        buttonTextsLabels = new String[lstSave.size() + 10];
        buttons = new Button[lstSave.size() + 10];
    }

    /**
     * Méthode qui extrait la difficulté depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la difficulté de la sauvegarde ( String )
     */
    private static String extraireDifficulte(String data) {
        String difficulte = data.split("_")[0];
        String premiereLettre = difficulte.substring(0, 1).toUpperCase();
        String resteDuMot = difficulte.substring(1).toLowerCase();
        return premiereLettre + resteDuMot;
    }

    /**
     * Méthode qui extrait la taille depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la taille de la sauvegarde ( String )
     */
    private static String extraireTaille(String data) {
        return data.split("_")[1];
    }

    /**
     * Méthode qui extrait la date depuis le nom de la sauvegarde
     *
     * @param data le nom de la sauvegarde
     * @return la date de la sauvegarde ( String )
     */
    private static String extraireDate(String data) {
        return (data.split("_")[2] + "-" + data.split("_")[3] + "-" + data.split("_")[4]);
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
        for (int i = 0; i < lstSave.size() + 10; i++) {
            if (i >= lstSave.size())
                buttonTextsLabels[i] = "Rien";
            else
                buttonTextsLabels[i] = lstSave.get(i);

            buttons[i] = new Button();
            buttons[i].setMinSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            buttons[i].setMaxSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            // buttons[i].setPrefSize(Menu.toPourcentWidth(350.0, windowWidth),
            // Menu.toPourcentHeight(500.0, windowHeigth));
            buttons[i].getStyleClass().add("button-rounded");

            buttonsText[i] = new Label(buttonTextsLabels[i]);
            buttonsText[i].getStyleClass().add("button-text");
            buttonsText[i].setFocusTraversable(false);
            buttonsText[i].setMouseTransparent(true);
            buttonsText[i].setTextAlignment(TextAlignment.CENTER);
            Menu.adaptTextSize(buttonsText[i], 20, windowWidth, windowHeigth);

            // positionnement de la description
            descriptionText[i] = new Label();
            descriptionText[i].setFocusTraversable(false);
            descriptionText[i].setMouseTransparent(true);
            descriptionText[i].setTranslateY(Menu.toPourcentHeight(200.0, windowHeigth));
            descriptionText[i].getStyleClass().add("description-text");
            Menu.adaptTextSize(descriptionText[i], 18, windowWidth, windowHeigth);

            // labels[i].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour
            // déplacer le texte vers le bas
            buttonsContainer[i] = new StackPane();
            buttonsContainer[i].setAlignment(Pos.CENTER);
            buttonsContainer[i].getChildren().addAll(buttons[i], buttonsText[i], descriptionText[i]);
            buttonsContainer[i].setMaxHeight(buttons[i].getMaxHeight());

            // translation text animation
            fadeTransition[i] = new FadeTransition(Duration.seconds(0.3), descriptionText[i]);
            fadeTransition[i].setFromValue(0.0);
            fadeTransition[i].setToValue(1.0);

            fadeTransitionReverse[i] = new FadeTransition(Duration.seconds(0.2), descriptionText[i]);
            fadeTransitionReverse[i].setFromValue(1.0);
            fadeTransitionReverse[i].setToValue(0.0);

            // hover on
            int finalI = i;
            buttonsContainer[i].setOnMouseEntered(e -> {
                descriptionText[finalI].setTextAlignment(TextAlignment.CENTER);
                if (finalI >= lstSave.size())
                    descriptionText[finalI].setText("Rien");
                else {
                    String difficulte = extraireDifficulte(lstSave.get(finalI));
                    String taille = extraireTaille(lstSave.get(finalI));
                    String date = extraireDate(lstSave.get(finalI));
                    String description = "Difficulté : " + difficulte + "\nTaille : " + taille + "\nDate : " + date;
                    descriptionText[finalI].setText(description);
                }

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

                fadeTransition[finalI].play();
            });

            // hover off
            int finalI1 = i;
            buttonsContainer[i].setOnMouseExited(e -> {
                fadeTransition[finalI1].stop();
                fadeTransitionReverse[finalI1].play();
            });
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

        StackPane stack = new StackPane();
        stack.getChildren().add(scrollPane);
        stack.setAlignment(Pos.CENTER);
        StackPane.setAlignment(scrollPane, Pos.CENTER);

        return stack;
    }
}
