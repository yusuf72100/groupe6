package groupe6.affichage;

import groupe6.launcher.Launcher;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class historiqueAidesArea {
    private final VBox historiqueAidesVBox;
    private final ScrollPane historiqueAidesScrollPane;
    private final StackPane historiqueAidesStackPane;
    private final Label titre;

    public historiqueAidesArea(Double w, Double h) {
        this.titre = new Label("Aides");
        this.titre.getStyleClass().add("title_help");
        Menu.adaptTextSize(this.titre, 50, w, h);
        this.historiqueAidesStackPane = new StackPane();
        this.historiqueAidesVBox = new VBox(titre);
        this.historiqueAidesVBox.setPrefSize(Menu.toPourcentWidth(800.0, 800.0) , h);
        this.historiqueAidesVBox.setAlignment(Pos.TOP_CENTER);
        this.historiqueAidesScrollPane = new ScrollPane(this.historiqueAidesVBox);
        this.historiqueAidesScrollPane.setFitToWidth(true);
        this.historiqueAidesScrollPane.setFitToHeight(true);
        this.historiqueAidesStackPane.getChildren().add(this.historiqueAidesScrollPane);
        this.historiqueAidesStackPane.setAlignment(Pos.CENTER_RIGHT);
        this.historiqueAidesVBox.getStyleClass().addAll("historiqueAidesVBox");
        this.titre.setTranslateY(Menu.toPourcentHeight(200.0, 200.0));
        StackPane.setAlignment(this.historiqueAidesScrollPane, Pos.CENTER);

        // placement à droite
        this.historiqueAidesStackPane.setTranslateX(w-this.historiqueAidesVBox.getPrefWidth());
    }

    /**
     * Getter de la variable historiqueAidesStackPane
     * @return renvoi la variable historiqueAidesStackPane
     */
    public StackPane getHistoriqueAidesStackPane() {
        return this.historiqueAidesStackPane;
    }
}
