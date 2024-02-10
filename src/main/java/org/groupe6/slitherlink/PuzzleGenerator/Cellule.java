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

    private StackPane createCenterContent() {
        TextField centerTextField = new TextField();

        centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");

        double cellSize = 50;
        centerTextField.setMaxSize(cellSize, cellSize);

        centerTextField.setFont(new Font(25)); // Ajustez la taille de la police en conséquence
        centerTextField.setAlignment(Pos.CENTER);

        centerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                centerTextField.setText(newValue.substring(0, 1));
            }
        });

        return new StackPane(centerTextField);
    }

    // Coins de chaque case
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(5, 5);
        square.getStyleClass().add("black-square");
        return square;
    }

    public void changeCellulesCss(String css) {
        cellule[0].getStyleClass().addAll(css + "-top");
        cellule[1].getStyleClass().addAll(css + "-bottom");
        cellule[2].getStyleClass().addAll(css + "-left");
        cellule[3].getStyleClass().addAll(css + "-right");
    }

    public Rectangle getCoin(int c) { return coins[c]; }

    public Button getButton(int c) {
        return cellule[c];
    }

    public StackPane getCenterPane() { return centerPane; }

    public int getLabel() { return label; }
}
