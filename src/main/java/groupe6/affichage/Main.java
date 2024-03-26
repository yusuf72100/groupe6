package groupe6.affichage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

import groupe6.model.DifficultePuzzle;
import groupe6.launcher.Launcher;

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
            Main.getStylesheets().add(Launcher.chargerFichierEnUrl("Slitherlink/assets/style/style.css"));

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
            primary.getIcons().add(Launcher.chargerImage("Slitherlink/assets/icon/icon.png"));
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

    public static void showGridMenu() {
        grid = new GridMenu(11, 11, DifficultePuzzle.DIFFICILE);
        Main.setRoot(grid.getMenu(true));
    }

    // programme principal
    public static void main(String[] args) {
        launch();
    }
}
