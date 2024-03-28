module groupe6.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens groupe6.launcher to javafx.fxml;

    exports groupe6.launcher;
    exports groupe6.affichage;
    exports groupe6.model;
    exports groupe6.tools.puzzleGenerator;
}