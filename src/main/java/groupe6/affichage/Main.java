package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

/**
 * Classe qui correspond à la méthode principale de l'application
 *
 * @author Yusuf
 */
public class Main extends Application {

    /**
     * La scène principale
     */
    private static Scene Main;

    /**
     * La gridMenu actuelle
     */
    private static GridMenu grid;

    /**
     * La scène principale
     */
    private static Stage primaryStage;

    /**
     * Métode qui démarre l'application
     *
     * @param primary la scène principale
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @Override
    public void start(Stage primary) throws IOException {
        try {
            primaryStage = primary;

            // Gestion de la scène
            Main = new Scene(
                MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()),
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()
            );

            // Gestion du style
            String cheminStyleCss = Launcher.normaliserChemin(Launcher.dossierAssets + "/style.css");
            Main.getStylesheets().add(Launcher.chargerFichierEnUrl(cheminStyleCss));

            // Gestion de la fenêtre
            primary.initStyle(StageStyle.DECORATED);
            primary.setScene(Main);
            primary.setTitle("SlitherLink");
            primary.setResizable(true);
            primary.setMaximized(true);
            primary.show();

            // Gestion de l'icône
            String cheminImgIcon = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/icon.png");
            primary.getIcons().add(Launcher.chargerImage(cheminImgIcon));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode statique pour afficher le menu de sélection du mode de jeu
     */
    public static void showGameModeMenu() {
        Main.setRoot(GameModeSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu principal
     */
    public static void showMainMenu() {
        Main.setRoot(MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de profil
     *
     * @param partie la partie à afficher
     */
    public static void showGridMenu(Partie partie) {
        // Crée un GridMenu avec la partie passé en paramètre
        grid = new GridMenu(partie, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        Main.setRoot(grid.getMenu(false, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de sauvegarde
     */
    public static void showSaveSelectionMenu() {
        Main.setRoot(SaveSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode principale qui lance l'application javaFX
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch();
    }
}
