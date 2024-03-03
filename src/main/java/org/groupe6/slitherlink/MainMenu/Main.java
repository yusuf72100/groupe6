package org.groupe6.slitherlink.MainMenu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
            hBox.setPrefSize(primary.getWidth(), 512);
            hBox.setSpacing(200); // espacement entre les enfants

            // gestion des boutons
            StackPane[] buttonContainers = new StackPane[3];
            Button[] buttons = new Button[3];
            Label[] labels = new Label[3];

            // instanciation et initialisation des boutons et des labels
            for (int i = 0; i < buttons.length; i++) {
                int finalI = i;

                buttons[i] = new Button();
                buttons[i].setPrefSize(350, 512);
                buttons[i].getStyleClass().add("button-rounded");

                // positionnement de la description
                labels[i] = new Label();
                labels[i].setAlignment(Pos.CENTER);
                labels[i].setPrefWidth(350);
                labels[i].setTranslateY((buttons[i].getPrefHeight() / 2) - 20);
                //labels[i].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas

                // hover on
                buttons[i].setOnMouseEntered(e -> {
                    buttons[finalI].setStyle("-fx-background-radius: 0; -fx-background-color: #e0ac1e;");
                    labels[finalI].setText("PlaceHolder #" + (finalI + 1));
                });

                // hover off
                buttons[i].setOnMouseExited(e -> {
                    buttons[finalI].setStyle("-fx-background-radius: 0; -fx-background-color: #D9D9D9;");
                    labels[finalI].setText("");
                });

                // button click
                buttons[i].setOnAction(event -> {
                    // animation de click
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(0), new KeyValue(buttons[finalI].scaleXProperty(), 1)),
                            new KeyFrame(Duration.seconds(0), new KeyValue(buttons[finalI].scaleYProperty(), 1)),
                            new KeyFrame(Duration.seconds(0.25), new KeyValue(buttons[finalI].scaleXProperty(), 1.2)),
                            new KeyFrame(Duration.seconds(0.25), new KeyValue(buttons[finalI].scaleYProperty(), 1.2)),
                            new KeyFrame(Duration.seconds(0.5), new KeyValue(buttons[finalI].scaleXProperty(), 1)),
                            new KeyFrame(Duration.seconds(0.5), new KeyValue(buttons[finalI].scaleYProperty(), 1))
                    );
                    timeline.play();
                });

                buttonContainers[i] = new StackPane();
                buttonContainers[i].getChildren().addAll(buttons[i], labels[i]);
                buttonContainers[i].setAlignment(Pos.CENTER);
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
