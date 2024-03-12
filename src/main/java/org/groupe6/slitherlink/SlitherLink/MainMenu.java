package org.groupe6.slitherlink.SlitherLink;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainMenu implements Menu {

    public static HBox getMenu() {
        String[] buttonTextsLabels = {"JOUER", "OPTIONS", "ENTRAÎNEMENT"};
        HBox MainMenu = new HBox();
        HBox[] descriptionsBackground = new HBox[3];
        StackPane[] buttonsContainer = new StackPane[3];
        Button[] buttons = new Button[3];
        Text[] descriptionText = new Text[3];
        Text[] buttonsText = new Text[3];

        // animations
        TranslateTransition[] rectangleTransition = new TranslateTransition[3];
        TranslateTransition[] rectangleTransitionReverse = new TranslateTransition[3];
        FadeTransition[] fadeTransition = new FadeTransition[3];
        FadeTransition[] fadeTransitionReverse = new FadeTransition[3];
        Rectangle[] clipRectangle = new Rectangle[3];

        MainMenu.setSpacing(200);       // espacement entre les éléments

        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;

            buttons[finalI] = new Button();
            buttons[finalI].setPrefSize(350, 512);
            buttons[finalI].getStyleClass().add("button-rounded");

            buttonsText[finalI] = new Text(buttonTextsLabels[finalI]);
            buttonsText[finalI].getStyleClass().add("button-text");
            buttonsText[finalI].setFocusTraversable(false);
            buttonsText[finalI].setMouseTransparent(true);

            descriptionsBackground[finalI] = new HBox();
            descriptionsBackground[finalI].setMaxSize(buttons[finalI].getPrefWidth(), 100);
            descriptionsBackground[finalI].setStyle("-fx-background-color: transparent;");
            descriptionsBackground[finalI].setTranslateY(1);
            StackPane.setAlignment(descriptionsBackground[finalI], Pos.BOTTOM_CENTER);

            // positionnement de la description
            descriptionText[finalI] = new Text();
            descriptionText[finalI].setFocusTraversable(false);
            descriptionText[finalI].setMouseTransparent(true);
            descriptionText[finalI].setTranslateY(200);
            descriptionText[finalI].getStyleClass().add("description-text");
            //labels[finalI].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas
            buttonsContainer[finalI] = new StackPane();
            buttonsContainer[finalI].getChildren().addAll(buttons[finalI], buttonsText[finalI], descriptionsBackground[finalI], descriptionText[finalI]);
            buttonsContainer[finalI].setAlignment(Pos.CENTER);
            //translation text animation

            clipRectangle[finalI] = new Rectangle(buttons[finalI].getPrefWidth(), 100);
            descriptionsBackground[finalI].getStyleClass().add("description-background");
            descriptionsBackground[finalI].setClip(clipRectangle[finalI]);
            descriptionsBackground[finalI].setFocusTraversable(false);
            descriptionsBackground[finalI].setMouseTransparent(true);

            rectangleTransition[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransition[finalI].setFromY(200);
            rectangleTransition[finalI].setToY(0);

            rectangleTransitionReverse[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransitionReverse[finalI].setFromY(0);
            rectangleTransitionReverse[finalI].setToY(200);

            fadeTransition[finalI] = new FadeTransition(Duration.seconds(0.3), descriptionText[finalI]);
            fadeTransition[finalI].setFromValue(0.0);
            fadeTransition[finalI].setToValue(1.0);

            fadeTransitionReverse[finalI] = new FadeTransition(Duration.seconds(0.2), descriptionText[finalI]);
            fadeTransitionReverse[finalI].setFromValue(1.0);
            fadeTransitionReverse[finalI].setToValue(0.0);

            // hover on
            buttonsContainer[finalI].setOnMouseEntered(e -> {
                buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #e0ac1e;");
                descriptionText[finalI].setText("PlaceHolder #" + (finalI + 1));
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
                buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #D9D9D9;");
            });

            buttonsContainer[finalI].setMaxSize(buttonsContainer[finalI].getPrefWidth(), buttons[finalI].getPrefHeight());
        }

        // alignement de ma boîte
        MainMenu.setAlignment(Pos.CENTER);
        MainMenu.getChildren().addAll(buttonsContainer);

        return MainMenu;
    }
}
