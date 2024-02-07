module org.groupe6.slitherlink {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.groupe6.slitherlink to javafx.fxml;
    exports org.groupe6.slitherlink;
}