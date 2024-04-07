package groupe6.tools.puzzleGenerator;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale du puzzle generator
 *
 * @author Yusuf
 */
public class Main extends Application {

    /**
     * La scène principale
     */
    private static Scene scene;

    /**
     * Le menu principal
     */
    private static MainMenu main;

    /**
     * Le gridMenu utilisé dans le puzzle generator
     */
    private static GridMenu grid;

    /**
     * La fenêtre principale
     */
    private static Stage MainStage;

    /**
     * Méthode start qui lance le puzzle generator
     *
     * @param primaryStage la fenêtre principale
     * @throws Exception si une erreur survient
     */
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

    /**
     * Méthode statique qui affiche le menu principal
     */
    public static void showMainMenu() {
        scene.setRoot(main.getMenu());
    }

    /**
     * Méthode statique qui affiche une toute nouvelle grille
     *
     * @param longueur la longueur du puzzle
     * @param largeur la largeur du puzzle
     * @param diff la difficulté du puzzle
     */
    public static void showNewPuzzle(int largeur, int longueur, DifficultePuzzle diff) {
        grid = new GridMenu(largeur, longueur, diff);
        scene.setRoot(grid.getMenu(true));
    }

    /**
     * Méthode statique qui affiche le puzzle choisit avec le gestionnaire des fichiers
     *
     * @param selectedFile le fichier choisi
     */
    public static void showLoadedPuzzle(java.io.File selectedFile) {
        grid = new GridMenu(1, 1, DifficultePuzzle.FACILE);             // on charge un puzzle de base
        grid.initNewPuzzle(selectedFile.getAbsolutePath());
        scene.setRoot(grid.getMenu(false));
    }

    /**
     * Méthode statique qui renvoi la référence de la fenêtre
     *
     * @return MainStage
     */
    public static Stage getStage() {
        return MainStage;
    }

    /**
     * Méthode statique main qui lance le puzzle generator
     *
     * @param args les arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
