package groupe6.tools.puzzleGenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

import groupe6.launcher.Launcher;

import groupe6.model.DifficultePuzzle;

public class Main extends Application {
    private static Scene scene;
    private static MainMenu main;
    private static GridMenu grid;
    private static Stage MainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStage = primaryStage;
        main = new MainMenu();
        scene = new Scene(main.getMenu(), 1000, 800);
        scene.getStylesheets().add(Launcher.chargerFichierEnUrl("Slitherlink/tools/puzzleGenerator/style.css"));

        primaryStage.setTitle("Puzzle Generator");
        primaryStage.getIcons().add(Launcher.chargerImage("Slitherlink/tools/puzzleGenerator/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainMenu() {
        scene.setRoot(main.getMenu());
    }

    /**
     * On affiche une toute nouvelle grille
     * @param longueur
     * @param largeur
     * @param diff
     */
    public static void showNewPuzzle(int longueur, int largeur, DifficultePuzzle diff) {
        grid = new GridMenu(longueur, largeur, diff);
        scene.setRoot(grid.getMenu(true));
    }

    /**
     * On affiche le puzzle choisit avec le gestionnaire des fichiers
     * @param selectedFile
     */
    public static void showLoadedPuzzle(java.io.File selectedFile) {
        grid = new GridMenu(1, 1, DifficultePuzzle.FACILE);             // on charge un puzzle de base
        grid.initNewPuzzle(selectedFile.getAbsolutePath());
        scene.setRoot(grid.getMenu(false));
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
    