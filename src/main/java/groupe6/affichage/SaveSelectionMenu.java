package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.List;

public class SaveSelectionMenu extends MainMenu {
    private static Button backButton;
    private static Label backText;
    private static ComboBox<String> profilSelector;
    private static String[] buttonTextsLabels;
    private static HBox mainHbox;
    private static StackPane mainPane;
    private static HBox[] descriptionsBackground;
    private static StackPane[] buttonsContainer;
    private static Button[] buttons;
    private static Label[] descriptionText;
    private static Label[] buttonsText;

    // animations
    private static TranslateTransition[] rectangleTransition;
    private static TranslateTransition[] rectangleTransitionReverse;
    private static FadeTransition[] fadeTransition;
    private static FadeTransition[] fadeTransitionReverse;
    private static Rectangle[] clipRectangle;
    private static Text title = new Text("Slitherlink");
    private static List<Profil> profils;
    private static List<String> lstSave;

    public static void initMenu() {
        lstSave = CatalogueSauvegarde.listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
        title = new Text("Choisissez une partie");
        backButton = new Button();
        mainHbox = new HBox();
        mainPane = new StackPane();
        buttonsContainer = new StackPane[lstSave.size()+10];
        buttons = new Button[lstSave.size()+10];
        descriptionText = new Label[lstSave.size()+10];
        buttonsText = new Label[lstSave.size()+10];

        // animations
        fadeTransition = new FadeTransition[lstSave.size()+10];
        fadeTransitionReverse = new FadeTransition[lstSave.size()+10];
        profils = Launcher.catalogueProfils.getListeProfils();

        backText = new Label("RETOUR");
        buttonTextsLabels = new String[lstSave.size()+10];
        buttons = new Button[lstSave.size()+10];
    }

    private static String extraireDifficulte(String data) {
        String difficulte = data.split("_")[0];
        String premiereLettre = difficulte.substring(0, 1).toUpperCase();
        String resteDuMot = difficulte.substring(1).toLowerCase();
        return premiereLettre + resteDuMot;
    }

    private static String extraireTaille(String data) {
        return data.split("_")[1];
    }

    private static String extraireDate(String data) {
        return (data.split("_")[2] + "-" + data.split("_")[3] + "-" + data.split("_")[4]);
    }

    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        initMenu();
        for (int i = 0; i < lstSave.size() + 10; i++) {
            if(i >= lstSave.size()) buttonTextsLabels[i] = "Rien";
            else buttonTextsLabels[i] = lstSave.get(i);

            buttons[i] = new Button();
            buttons[i].setMinSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            buttons[i].setMaxSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            //buttons[i].setPrefSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
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

            //labels[i].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas
            buttonsContainer[i] = new StackPane();
            buttonsContainer[i].setAlignment(Pos.CENTER);
            buttonsContainer[i].getChildren().addAll(buttons[i], buttonsText[i], descriptionText[i]);
            buttonsContainer[i].setMaxHeight(buttons[i].getMaxHeight());

            //translation text animation
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
                if (finalI >= lstSave.size()) descriptionText[finalI].setText("Rien");
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
                        List<String> lstSave = CatalogueSauvegarde.listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
                        Main.showSaveSelectionMenu();
                        if (!lstSave.isEmpty()) {
                            String saveName;
                            if(finalI >= lstSave.size()) saveName = "Rien";
                            else saveName = lstSave.get(finalI);
                            System.out.println("Chargement de la sauvegarde : " + saveName);
                            PartieSauvegarde save = PartieSauvegarde.chargerSauvegarde(saveName,Launcher.catalogueProfils.getProfilActuel());
                            Partie partie = Partie.chargerPartie(save,Launcher.catalogueProfils.getProfilActuel());
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
