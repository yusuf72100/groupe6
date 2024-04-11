package groupe6.affichage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PauseMenu implements Menu {
    /**
     * Node d'affichage
     */
    private static StackPane stackPane;

    private static VBox vBox;

    private static Button reprendre;

    private static Button options;

    private static Button exitMenu;

    public static void initMenu(Double w, Double h) {
        vBox = new VBox();
        stackPane = new StackPane();
        reprendre = new Button("Reprendre");
        options = new Button("Option");
        exitMenu = new Button("Exit");

        reprendre.getStyleClass().addAll("button-rounded", "button-text");
        options.getStyleClass().addAll("button-rounded", "button-text");
        exitMenu.getStyleClass().addAll("button-rounded", "button-text");

        reprendre.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
        options.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
        exitMenu.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));

        vBox.setSpacing(50);
        vBox.getChildren().addAll(reprendre, options, exitMenu);
        vBox.setAlignment(Pos.CENTER);

        // TODO : Mettre un background noir transparent 0.7 avec une animation et mettre en pause le chrono

        stackPane.getChildren().add(vBox);
    }

    public static StackPane getMenu(Double w, Double h) {
        initMenu(w, h);

        return stackPane;
    }
}
