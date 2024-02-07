module org.groupe6.slitherlink {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

        exports org.groupe6.slitherlink.MainMenu;
    exports org.groupe6.slitherlink.PuzzleGenerator;

    opens org.groupe6.slitherlink.PuzzleGenerator to javafx.fxml;
    opens org.groupe6.slitherlink.MainMenu to javafx.fxml;
}