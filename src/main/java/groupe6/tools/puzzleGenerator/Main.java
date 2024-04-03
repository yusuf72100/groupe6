package groupe6.tools.puzzleGenerator;

import groupe6.launcher.Launcher;
import groupe6.model.DifficultePuzzle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

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
        String cheminStyleCss = Launcher.normaliserChemin(Launcher.dossierPuzzleGenerator + "/style.css");
        scene.getStylesheets().add(Launcher.chargerFichierEnUrl(cheminStyleCss));

        String cheminIcon = Launcher.normaliserChemin(Launcher.dossierAssets + "icon/icon.png");
        primaryStage.setTitle("Puzzle Generator");
        primaryStage.getIcons().add(Launcher.chargerImage(cheminIcon));
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
    public static void showNewPuzzle(int largeur, int longueur, DifficultePuzzle diff) {
        grid = new GridMenu(largeur, longueur, diff);
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