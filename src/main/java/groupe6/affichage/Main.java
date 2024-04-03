package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private static Scene Main;
    private static GridMenu grid;

    /**
     * Programme principal jfx
     * @param primary
     * @throws IOException
     */
    @Override
    public void start(Stage primary) throws IOException {
        try {
            // gestion de la scène
            Main = new Scene(MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            String cheminStyleCss = Launcher.normaliserChemin(Launcher.dossierAssets + "/style.css");
            Main.getStylesheets().add(Launcher.chargerFichierEnUrl(cheminStyleCss));

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
            String cheminImgIcon = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/icon.png");
            primary.getIcons().add(Launcher.chargerImage(cheminImgIcon));
            primary.setResizable(true);
            primary.setMaximized(true);
            primary.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showGameModeMenu() {
        Main.setRoot(GameModeSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    public static void showMainMenu() {
        Main.setRoot(MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    public static void showGridMenu(Partie partie) {
        // Crée un GridMenu avec la partie passé en paramètre
        grid = new GridMenu(partie);
        Main.setRoot(grid.getMenu(false));
    }

    public static void showSaveSelectionMenu(List<String> listeSaves) {
        Main.setRoot(SaveSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), listeSaves));
    }

    // programme principal
    public static void main(String[] args) {
        launch();
    }
}
