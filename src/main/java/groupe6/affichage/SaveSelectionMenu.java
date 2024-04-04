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
    private ComboBox<String> profilSelector;

    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        List<String> lstSave = CatalogueSauvegarde.listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
        title = new Text("Choisissez une partie");
        backButton = new Button();
        mainHbox = new HBox();
        mainPane = new StackPane();
        descriptionsBackground = new HBox[lstSave.size()];
        buttonsContainer = new StackPane[lstSave.size()];
        buttons = new Button[lstSave.size()];
        descriptionText = new Label[lstSave.size()];
        buttonsText = new Label[lstSave.size()];

        // animations
        rectangleTransition = new TranslateTransition[3];
        rectangleTransitionReverse = new TranslateTransition[3];
        fadeTransition = new FadeTransition[3];
        fadeTransitionReverse = new FadeTransition[3];
        clipRectangle = new Rectangle[3];
        profils = Launcher.catalogueProfils.getListeProfils();
        
        backText.setText("RETOUR");
        buttonTextsLabels = new String[lstSave.size()];
        buttons = new Button[lstSave.size()];

        for (int i = 0; i < buttons.length; i++) {
            System.out.println(buttons.length);
            buttonTextsLabels[i] = lstSave.get(i);
            buttons[i] = new Button();
            buttons[i].setPrefSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            buttons[i].getStyleClass().add("button-rounded");

            buttonsText[i] = new Label(buttonTextsLabels[i]);
            buttonsText[i].getStyleClass().add("button-text");
            buttonsText[i].setFocusTraversable(false);
            buttonsText[i].setMouseTransparent(true);
            buttonsText[i].setTextAlignment(TextAlignment.CENTER);
            Menu.adaptTextSize(buttonsText[i], 20, windowWidth, windowHeigth);

            descriptionsBackground[i] = new HBox();
            descriptionsBackground[i].setMaxSize(buttons[i].getPrefWidth(), Menu.toPourcentHeight(100.0, windowHeigth));
            descriptionsBackground[i].setStyle("-fx-background-color: transparent;");
            descriptionsBackground[i].setTranslateY(1);
            StackPane.setAlignment(descriptionsBackground[i], Pos.BOTTOM_CENTER);

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
            buttonsContainer[i].getChildren().addAll(buttons[i], buttonsText[i], descriptionsBackground[i], descriptionText[i]);

            //translation text animation
            clipRectangle[i] = new Rectangle(buttons[i].getPrefWidth(), Menu.toPourcentHeight(100.0, windowHeigth));
            descriptionsBackground[i].getStyleClass().add("description-background");
            descriptionsBackground[i].setClip(clipRectangle[i]);
            descriptionsBackground[i].setFocusTraversable(false);
            descriptionsBackground[i].setMouseTransparent(true);

            rectangleTransition[i] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[i]);
            rectangleTransition[i].setFromY(Menu.toPourcentHeight(200.0, windowHeigth));
            rectangleTransition[i].setToY(0);

            rectangleTransitionReverse[i] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[i]);
            rectangleTransitionReverse[i].setFromY(0);
            rectangleTransitionReverse[i].setToY(Menu.toPourcentHeight(200.0, windowHeigth));

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
                descriptionText[finalI].setText(lstSave.get(finalI));

                buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        List<String> lstSave = CatalogueSauvegarde.listerSauvegarde(Launcher.catalogueProfils.getProfilActuel());
                        Main.showSaveSelectionMenu();
                        if (!lstSave.isEmpty()) {
                            String saveName = lstSave.get(0);
                            System.out.println("Chargement de la sauvegarde : " + saveName);
                            PartieSauvegarde save = PartieSauvegarde.chargerSauvegarde(saveName,Launcher.catalogueProfils.getProfilActuel());
                            Partie partie = Partie.chargerPartie(save,Launcher.catalogueProfils.getProfilActuel());
                        } else {
                            System.out.println("Aucune sauvegarde trouvée");
                        }

                    }
                });

                descriptionsBackground[finalI].setStyle("-fx-background-color: gray;");
                rectangleTransition[finalI].play();
                fadeTransition[finalI].play();
            });

            // hover off
            int finalI1 = i;
            buttonsContainer[i].setOnMouseExited(e -> {
                rectangleTransition[finalI1].stop();
                fadeTransition[finalI1].stop();
                rectangleTransitionReverse[finalI1].play();
                fadeTransitionReverse[finalI1].play();
            });

            buttonsContainer[i].setMaxSize(buttonsContainer[i].getPrefWidth(), buttons[i].getPrefHeight());
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(50);
        gridPane.setVgap(10);

        for (int i = 0; i < buttonsContainer.length; i++) {
            gridPane.add(buttonsContainer[i], i % 5, i / 5);
            gridPane.setAlignment(Pos.CENTER);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        StackPane stack = new StackPane();
        stack.getChildren().add(scrollPane);
        stack.setAlignment(Pos.CENTER);

        StackPane.setAlignment(scrollPane, Pos.CENTER);

        return stack;
    }
}
