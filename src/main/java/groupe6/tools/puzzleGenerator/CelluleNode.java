package groupe6.tools.puzzleGenerator;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.function.Function;

public class CelluleNode extends Node {
    private Button[] cellule;
    private Rectangle[] coins;
    private StackPane centerPane;
    private TextField centerTextField;
    private int label;

    public CelluleNode() {
        this.label = -1;
        double cellSize = 50;
        cellule = new Button[4];
        coins = new Rectangle[4];
        centerPane = new StackPane();

        centerPane.getChildren().add(createCenterContent());
        centerPane.setAlignment(Pos.CENTER);

        cellule[0] = new Button("Top");
        cellule[1] = new Button("Bottom");
        cellule[2] = new Button("Left");
        cellule[3] = new Button("Right");

        cellule[0].getStyleClass().addAll("button-top");
        cellule[1].getStyleClass().addAll("button-bottom");
        cellule[2].getStyleClass().addAll("button-left");
        cellule[3].getStyleClass().addAll("button-right");

        for (int i = 0; i < 4; i++) {
            coins[i] = createBlackSquare(cellSize / 5);
        }
    }

    /**
     * Création du label de la cellule
     * @return StackPane
     */
    private StackPane createCenterContent() {
        centerTextField = new TextField();
        double cellSize = 50;

        centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
        centerTextField.setMaxSize(cellSize, cellSize);
        centerTextField.setFont(new Font(25));
        centerTextField.setAlignment(Pos.CENTER);

        centerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                centerTextField.setText(oldValue);
                this.label = Integer.parseInt(oldValue);
            }
            else if (!newValue.matches("[0-3]")) {
                centerTextField.setText("");
                this.label = -1;
            }
            else{
                this.label = Integer.parseInt(newValue);
            }
        });

        return new StackPane(centerTextField);
    }

    /**
     * Méthode de création d'un carré noir
     * @param v TODO
     * @return Rectangle
     */
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(5, 5);
        square.getStyleClass().add("black-square");
        return square;
    }

    /**
     * Changer le css de la cellule
     * @param TODO
     */
    public void changeCellulesCss(String css) {
        cellule[0].getStyleClass().addAll(css + "-top");
        cellule[1].getStyleClass().addAll(css + "-bottom");
        cellule[2].getStyleClass().addAll(css + "-left");
        cellule[3].getStyleClass().addAll(css + "-right");
    }

    public void changeButtonCss(int buttonIndex, Function<Integer, String> cssFunction) {
        Button button = cellule[buttonIndex];
        String cssClass = cssFunction.apply(label);

        button.getStyleClass().clear();
        button.getStyleClass().addAll(cssClass);
    }

    /**
     * Getter coin
     * @param c TODO
     * @return Rectangle
     */
    public Rectangle getCoin(int c) { return coins[c]; }

    /**
     * Getter bouton
     * @param c TODO
     * @return Button
     */
    public Button getButton(int c) {
        return cellule[c];
    }

    /**
     * Getter pane de la cellule
     * @return StackPane
     */
    public StackPane getCenterPane() { return centerPane; }

    /**
     * Getter label cellule
     * @return int
     */
    public int getLabel() { return label; }

    public void setLabel(int label) { this.label = label; }

    public void setLabeText(int i) {
        centerTextField.setPromptText(Integer.toString(i));
    }
}
