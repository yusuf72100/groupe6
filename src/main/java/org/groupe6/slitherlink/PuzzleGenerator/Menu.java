package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.scene.layout.VBox;

public interface Menu {
    static <T> VBox getMenu(T... args) {
        return new VBox();
    }
}
