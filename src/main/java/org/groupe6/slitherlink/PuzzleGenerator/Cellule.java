package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Cellule {
    private Button[] cellule;
    private Rectangle[] coins;
    private StackPane centerPane;
    private int label;

    Cellule() {
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

        double cellSize = 50;
        for (int i = 0; i < 4; i++) {
            coins[i] = createBlackSquare(cellSize / 5);
        }
    }

    /**
     * Création du label de la cellule
     * @return
     */
    private StackPane createCenterContent() {
        TextField centerTextField = new TextField();
        double cellSize = 50;

        centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
        centerTextField.setMaxSize(cellSize, cellSize);
        centerTextField.setFont(new Font(25));
        centerTextField.setAlignment(Pos.CENTER);

        centerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                centerTextField.setText(oldValue);
            }
            else if (!newValue.matches("[0-3]")) {
                centerTextField.setText("");
            }
        });

        return new StackPane(centerTextField);
    }

    /**
     * Méthode de création d'un carré noir
     * @param v
     * @return
     */
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(5, 5);
        square.getStyleClass().add("black-square");
        return square;
    }

    /**
     * Changer le css de la cellule
     * @param css
     */
    public void changeCellulesCss(String css) {
        cellule[0].getStyleClass().addAll(css + "-top");
        cellule[1].getStyleClass().addAll(css + "-bottom");
        cellule[2].getStyleClass().addAll(css + "-left");
        cellule[3].getStyleClass().addAll(css + "-right");
    }

    /**
     * Getter coin
     * @param c
     * @return
     */
    public Rectangle getCoin(int c) { return coins[c]; }

    /**
     * Getter bouton
     * @param c
     * @return
     */
    public Button getButton(int c) {
        return cellule[c];
    }

    /**
     * Getter pane de la cellule
     * @return
     */
    public StackPane getCenterPane() { return centerPane; }

    /**
     * Getter label cellule
     * @return
     */
    public int getLabel() { return label; }
}
