package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    private static Scene scene;
    private static GridMenu grid;
    private static Stage MainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStage = primaryStage;
        scene = new Scene(MainMenu.getMainMenu(), 1000, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        primaryStage.setTitle("Puzzle Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * On affiche une toute nouvelle grille
     * @param longueur
     * @param largeur
     * @param diff
     */
    public static void showNewPuzzle(int longueur, int largeur, PartieInfos.DifficultePuzzle diff) {
        grid = new GridMenu(longueur, largeur, diff);
        scene.setRoot(GridMenu.getGridMenu(true));
    }

    /**
     * On affiche le puzzle choisit avec le gestionnaire des fichiers
     * @param selectedFile
     */
    public static void showLoadedPuzzle(java.io.File selectedFile) {
        grid = new GridMenu(1, 1, PartieInfos.DifficultePuzzle.FACILE);             // on charge un puzzle random
        GridMenu.initNewPuzzle(selectedFile.getAbsolutePath());
        scene.setRoot(GridMenu.getGridMenu(false));
    }

    /**
     * Renvoi la référence de la fenêtre
     * @return MainStage
     */
    public static Stage getStage() {
        return MainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    