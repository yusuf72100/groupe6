package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface Menu {
    static <T> Node getMenu(T... args) {
        return new VBox();
    }
    static void showMenu(Stage stage) {};
}
