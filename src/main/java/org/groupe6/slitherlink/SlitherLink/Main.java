package org.groupe6.slitherlink.SlitherLink;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private static Scene Main;

    /**
     * Programme principal jfx
     * @param primary
     * @throws IOException
     */
    @Override
    public void start(Stage primary) throws IOException {
        try {
            // gestion de la scène
            MainMenu.initMenu();
            Main = new Scene(MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            Main.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            /*double windowWidth = primary.getWidth();
            double windowHeight = primary.getHeight();

            Main.widthProperty().addListener((obs, oldVal, newVal) -> {
                for (Button button : buttons) {
                    button.setPrefWidth(newVal.doubleValue() * 0.1823);
                }
            });

            Main.heightProperty().addListener((obs, oldVal, newVal) -> {
                for (Button button : buttons) {
                    button.setPrefHeight(newVal.doubleValue() * 0.47);
                }
            });*/

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

    public static void showGameModeMenu() {
        MainMenu.initMenu();
        Main.setRoot(GameModeSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    // programme principal
    public static void main(String[] args) {
        launch();
    }
}
