package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    private static Scene scene;
    private static GridMenu grid;
    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(MainMenu.getMainMenu(), 1000, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Puzzle Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showGridMenu(int longueur, int largeur, PartieInfos.DifficultePuzzle diff) {
        grid = new GridMenu(longueur, largeur, diff);
        scene.setRoot(GridMenu.getGridMenu());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    