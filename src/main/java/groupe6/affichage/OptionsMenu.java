package groupe6.affichage;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OptionsMenu implements Menu {
    private static ScrollPane scrollPane;

    private static VBox vBox;

    private static Label profilSettingsLabel;

    private static Label keySettingsLabel;

    private static StackPane stackPane;

    private static void initMenu(Double w, Double h) {
        scrollPane = new ScrollPane();
        vBox = new VBox();
        profilSettingsLabel = new Label("Profil");
        keySettingsLabel = new Label("Assignation des touches");
        stackPane = new StackPane();
    }

    public static StackPane getMenu(Double w, Double h) {
        initMenu(w, h);

        vBox.setAlignment(Pos.CENTER);
        return stackPane;
    }
}