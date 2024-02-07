package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class Cellule {
    private static Button[] cellule;
    private static Rectangle[] coins;

    Cellule() {
        cellule = new Button[4];
        coins = new Rectangle[4];

        cellule[0] = new Button("Top");
        cellule[1] = new Button("Bottom");
        cellule[2] = new Button("Left");
        cellule[3] = new Button("Right");

        cellule[0].getStyleClass().addAll("button-top");
        cellule[1].getStyleClass().addAll("button-bottom");
        cellule[2].getStyleClass().addAll("button-left");
        cellule[3].getStyleClass().addAll("button-right");

        for (int i = 0; i < 4; i++) {
            this.coins[i] = createBlackSquare();
        }
    }

    // Coins de chaque case
    private Rectangle createBlackSquare() {
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

    public Rectangle getCoin(int c) {
        return coins[c];
    }

    public Button getCellule(int c) {
        return cellule[c];
    }
}
