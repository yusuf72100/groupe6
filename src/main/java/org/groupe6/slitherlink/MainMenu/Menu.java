package org.groupe6.slitherlink.MainMenu;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface Menu {
    static <T> Node getMenu(T... args) {
        return new VBox();
    }
}
