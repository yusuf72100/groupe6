package org.groupe6.slitherlink.MainMenu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
            HBox hBox = new HBox();
            StackPane[] buttonContainers = new StackPane[3];
            Button[] buttons = new Button[3];
            Text[] descriptionTexts = new Text[3];
            TranslateTransition[] translateTransitions = new TranslateTransition[3];
            TranslateTransition[] translateTransitionsReverse = new TranslateTransition[3];

            hBox.setPrefSize(primary.getWidth(), 512);
            hBox.setSpacing(200);       // espacement entre les enfants

            // instanciation et initialisation des boutons et des labels
            for (int i = 0; i < buttons.length; i++) {
                int finalI = i;

                buttons[i] = new Button();
                buttons[i].setPrefSize(350, 512);
                buttons[i].getStyleClass().add("button-rounded");

                // positionnement de la description
                descriptionTexts[i] = new Text();
                descriptionTexts[i].setFocusTraversable(false);
                descriptionTexts[i].setMouseTransparent(true);
                //labels[i].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas
                buttonContainers[i] = new StackPane();
                buttonContainers[i].getChildren().addAll(buttons[i], descriptionTexts[i]);
                buttonContainers[i].setAlignment(Pos.CENTER);
                //translation text animation
                translateTransitions[finalI] = new TranslateTransition(Duration.seconds(0.3), descriptionTexts[finalI]);
                translateTransitions[finalI].setFromY(200);
                translateTransitions[finalI].setToY(150);

                translateTransitionsReverse[finalI] = new TranslateTransition(Duration.seconds(0.3), descriptionTexts[finalI]);
                translateTransitionsReverse[finalI].setFromY(150);
                translateTransitionsReverse[finalI].setToY(200);

                // hover on
                buttonContainers[i].setOnMouseEntered(e -> {
                    buttons[finalI].setStyle("-fx-background-radius: 0; -fx-background-color: #e0ac1e;");
                    descriptionTexts[finalI].setText("PlaceHolder #" + (finalI + 1));
                    translateTransitionsReverse[finalI].stop();
                    translateTransitions[finalI].play();
                });

                // hover off
                buttonContainers[i].setOnMouseExited(e -> {
                    translateTransitions[finalI].stop();
                    translateTransitionsReverse[finalI].play();

                    translateTransitionsReverse[finalI].setOnFinished(event -> {
                        descriptionTexts[finalI].setText("");
                    });

                    buttons[finalI].setStyle("-fx-background-radius: 0; -fx-background-color: #D9D9D9;");
                });

                buttonContainers[finalI].setMaxSize(buttonContainers[finalI].getPrefWidth(), buttons[finalI].getPrefHeight());
            }

            // texte de chaque bouton
            buttons[0].setText("Jouer");
            buttons[1].setText("Options");
            buttons[2].setText("Entraînement");

            // alignement de ma boîte
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(buttonContainers);

            // gestion de la scène
            Scene Main = new Scene(hBox, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
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
