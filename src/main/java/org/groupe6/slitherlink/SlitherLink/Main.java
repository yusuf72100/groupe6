package org.groupe6.slitherlink.SlitherLink;

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
            // gestion de la scène
            Scene Main = new Scene(MainMenu.getMenu(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
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
