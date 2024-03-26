package org.groupe6.slitherlink.SlitherLink;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.function.Function;

public class CelluleNode extends Node {
    private Button[] cellule;
    private Rectangle[] coins;
    private StackPane centerPane;
    private Label centerTextField;
    private int label;

    public CelluleNode() {
        this.label = -1;
        double cellSize = 50;
        this.cellule = new Button[4];
        this.coins = new Rectangle[4];
        this.centerPane = new StackPane();

        this.centerPane.getChildren().add(createCenterContent());
        this.centerPane.setAlignment(Pos.CENTER);

        this.cellule[0] = new Button("Top");
        this.cellule[1] = new Button("Bottom");
        this.cellule[2] = new Button("Left");
        this.cellule[3] = new Button("Right");

        this.cellule[0].getStyleClass().addAll("button-top");
        this.cellule[1].getStyleClass().addAll("button-bottom");
        this.cellule[2].getStyleClass().addAll("button-left");
        this.cellule[3].getStyleClass().addAll("button-right");

        for (int i = 0; i < 4; i++) {
            coins[i] = createBlackSquare(cellSize / 5);
        }
    }

    /**
     * Création du label de la cellule
     * @return StackPane
     */
    private StackPane createCenterContent() {
        this.centerTextField = new Label();
        double cellSize = 50;

        this.centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
        this.centerTextField.setMaxSize(cellSize, cellSize);
        this.centerTextField.setFont(new Font(25));
        this.centerTextField.setAlignment(Pos.CENTER);

        return new StackPane(centerTextField);
    }

    /**
     * Méthode de création d'un carré noir
     * @param v
     * @return Rectangle
     */
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(5, 5);
        square.getStyleClass().add("black-square");
        return square;
    }

    /**
     * Changer le css de la cellule
     * @param
     */
    public void changeCellulesCss(String css) {
        this.cellule[0].getStyleClass().addAll(css + "-top");
        this.cellule[1].getStyleClass().addAll(css + "-bottom");
        this.cellule[2].getStyleClass().addAll(css + "-left");
        this.cellule[3].getStyleClass().addAll(css + "-right");
    }

    public void changeButtonCss(int buttonIndex, Function<Integer, String> cssFunction) {
        Button button = cellule[buttonIndex];
        String cssClass = cssFunction.apply(label);

        button.getStyleClass().clear();
        button.getStyleClass().addAll(cssClass);
    }

    /**
     * Getter coin
     * @param c
     * @return Rectangle
     */
    public Rectangle getCoin(int c) { return coins[c]; }

    /**
     * Getter bouton
     * @param c
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
        centerTextField.setText(Integer.toString(i));
    }
}
