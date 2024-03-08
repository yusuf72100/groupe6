package org.groupe6.slitherlink.MainMenu;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    /**
     * Programme principal jfx
     * @param primary
     * @throws IOException
     */
    @Override
    public void start(Stage primary) throws IOException {
        try {
            HBox MainMenu = new HBox();
            HBox[] descriptionBackground = new HBox[3];
            StackPane[] buttonContainers = new StackPane[3];
            Button[] buttons = new Button[3];
            Text[] descriptionTexts = new Text[3];

            // animations
            TranslateTransition[] rectangleTransition = new TranslateTransition[3];
            TranslateTransition[] rectangleTransitionReverse = new TranslateTransition[3];
            FadeTransition[] fadeTransition = new FadeTransition[3];
            FadeTransition[] fadeTransitionReverse = new FadeTransition[3];
            Rectangle[] clipRectangle = new Rectangle[3];

            MainMenu.setPrefSize(primary.getWidth(), 512);
            MainMenu.setSpacing(200);       // espacement entre les enfants

            // instanciation et initialisation des boutons et des labels
            for (int i = 0; i < buttons.length; i++) {
                int finalI = i;

                buttons[i] = new Button();
                buttons[i].setPrefSize(350, 512);
                buttons[i].getStyleClass().add("button-rounded");

                descriptionBackground[i] = new HBox();
                descriptionBackground[i].setMaxSize(buttons[i].getPrefWidth(), 100);
                descriptionBackground[i].setStyle("-fx-background-color: transparent;");
                StackPane.setAlignment(descriptionBackground[i], Pos.BOTTOM_CENTER);
                descriptionBackground[i].setTranslateY(1);

                // positionnement de la description
                descriptionTexts[i] = new Text();
                descriptionTexts[i].setFocusTraversable(false);
                descriptionTexts[i].setMouseTransparent(true);
                descriptionTexts[i].setTranslateY(200);
                //labels[i].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas
                buttonContainers[i] = new StackPane();
                buttonContainers[i].getChildren().addAll(buttons[i], descriptionBackground[i], descriptionTexts[i]);
                buttonContainers[i].setAlignment(Pos.CENTER);
                //translation text animation

                clipRectangle[finalI] = new Rectangle(buttons[finalI].getPrefWidth(), 100);
                descriptionBackground[finalI].getStyleClass().add("description-background");
                descriptionBackground[finalI].setClip(clipRectangle[finalI]);
                descriptionBackground[i].setFocusTraversable(false);
                descriptionBackground[i].setMouseTransparent(true);

                rectangleTransition[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
                rectangleTransition[finalI].setFromY(200);
                rectangleTransition[finalI].setToY(0);

                rectangleTransitionReverse[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
                rectangleTransitionReverse[finalI].setFromY(0);
                rectangleTransitionReverse[finalI].setToY(200);

                fadeTransition[finalI] = new FadeTransition(Duration.seconds(0.3), descriptionTexts[finalI]);
                fadeTransition[finalI].setFromValue(0.0);
                fadeTransition[finalI].setToValue(1.0);

                fadeTransitionReverse[finalI] = new FadeTransition(Duration.seconds(0.2), descriptionTexts[finalI]);
                fadeTransitionReverse[finalI].setFromValue(1.0);
                fadeTransitionReverse[finalI].setToValue(0.0);

                // hover on
                buttonContainers[i].setOnMouseEntered(e -> {
                    buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #e0ac1e;");
                    descriptionTexts[finalI].setText("PlaceHolder #" + (finalI + 1));
                    descriptionBackground[finalI].setStyle("-fx-background-color: gray;");
                    rectangleTransition[finalI].play();
                    fadeTransition[finalI].play();
                });

                // hover off
                buttonContainers[i].setOnMouseExited(e -> {
                    rectangleTransition[finalI].stop();
                    fadeTransition[finalI].stop();
                    rectangleTransitionReverse[finalI].play();
                    fadeTransitionReverse[finalI].play();
                    buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #D9D9D9;");
                });

                buttonContainers[finalI].setMaxSize(buttonContainers[finalI].getPrefWidth(), buttons[finalI].getPrefHeight());
            }

            // texte de chaque bouton
            buttons[0].setText("Jouer");
            buttons[1].setText("Options");
            buttons[2].setText("Entraînement");

            // alignement de ma boîte
            MainMenu.setAlignment(Pos.CENTER);
            MainMenu.getChildren().addAll(buttonContainers);

            // gestion de la scène
            Scene Main = new Scene(MainMenu, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            Main.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            // gestion de la fenêtre
            primary.initStyle(StageStyle.DECORATED);
            primary.setScene(Main);
            primary.setTitle("SlitherLink");
            primary.setResizable(true);
            primary.setMaximized(true);
            primary.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // programme principal
    public static void main(String[] args) {
        launch();
    }
}
