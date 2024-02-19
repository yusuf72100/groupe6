package org.groupe6.slitherlink.MainMenu;

import javafx.scene.layout.VBox;

public interface Menu {
    static <T> VBox getMenu(T... args) {
        return new VBox();
    }
}
