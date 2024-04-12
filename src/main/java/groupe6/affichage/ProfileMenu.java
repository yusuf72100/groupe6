package groupe6.affichage;

import groupe6.launcher.Launcher;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class ProfileMenu implements Menu {
    private static AnchorPane anchorPane;

    private static VBox profilVbox;

    private static HBox hBox;

    private static ImageView imageView;

    private static ScrollPane scrollPane;

    private static Button profile;

    private static Button classement;

    private static StackPane content;

    private static Label description;

    public static void initMenu(Double w, Double h) {
        description = new Label();
        content = new StackPane();
        profile = new Button("Profile");
        classement = new Button("Classement");
        hBox = new HBox();
        profilVbox = new VBox();
        scrollPane = new ScrollPane();
        imageView = new ImageView();
        anchorPane = new AnchorPane();

        String cheminImageAvatar = Launcher
                .normaliserChemin(Launcher.catalogueProfils.getProfilActuel().getIMG());
        imageView.setImage(Launcher.chargerImage(cheminImageAvatar));
        imageView.setFitWidth(Menu.toPourcentWidth(70.0, w));
        imageView.setFitHeight(Menu.toPourcentHeight(70.0, h));
    }

    public static AnchorPane getMenu(Double w, Double h) {
        initMenu(w,h);

        List<Integer> stats = Launcher.catalogueProfils.getProfilActuel().getHistorique().calculerStat();
        description.setText(
                Launcher.catalogueProfils.getProfilActuel().getNom() +
                        "\nNiveau en mode aventure : " + Launcher.catalogueProfils.getProfilActuel().getNiveauAventure()+1 +
                        "\nNombre de parties jouées : " + stats.get(0) +
                        "\nNombre de parties gagnées : " + stats.get(1) +
                        "\nMeilleur score : " + stats.get(2)
                );

        profile.getStyleClass().add("button-withoutbg");
        profile.setStyle("-fx-font-weight: bold; -fx-font-size: 50; -fx-font-family: Inter; -fx-background-color: transparent;");
        classement.getStyleClass().add("button-withoutbg");
        classement.setStyle("-fx-font-weight: bold; -fx-font-size: 50; -fx-font-family: Inter; -fx-background-color: transparent;");

        Rectangle rectangle = new Rectangle(5, 50);
        HBox.setMargin(rectangle, new Insets(15, 0, 0, 0));
        hBox.getChildren().addAll(profile, rectangle, classement);
        hBox.setSpacing(50);
        hBox.setPadding(new Insets(0, 50, 0, 50));
        hBox.setAlignment(Pos.TOP_CENTER);

        hBox.widthProperty().addListener((obs, oldVal, newVal) -> {
            hBox.setTranslateX((w/2) - (newVal.doubleValue()/2) + 50);
        });

        profilVbox.setFillWidth(true);
        profilVbox.setSpacing(20);
        profilVbox.setStyle("-fx-background-color: lightgray; -fx-background-radius: 5;");
        profilVbox.getChildren().addAll(imageView, description);
        profilVbox.setAlignment(Pos.CENTER);
        profilVbox.setPadding(new Insets(15, 0, 0, 0));

        scrollPane.setContent(profilVbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        content.getChildren().add(scrollPane);
        content.setMinSize(Menu.toPourcentWidth(1200.0, w), Menu.toPourcentHeight(700.0, h));
        content.setMaxSize(Menu.toPourcentWidth(1200.0, w), Menu.toPourcentHeight(700.0, h));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: transparent; -fx-background-radius: 5;");
        StackPane.setAlignment(profilVbox, Pos.CENTER);

        anchorPane.getChildren().addAll(hBox, content);
        AnchorPane.setTopAnchor(content, (h - content.getMinHeight()) / 2);
        AnchorPane.setLeftAnchor(content, (w - content.getMinWidth()) / 2);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1200), imageView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), imageView);
        translateTransition.setByY(-(content.getMinHeight() / 2) + imageView.getFitHeight());
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setDelay(Duration.millis(1000));
        translateTransition.play();

        TranslateTransition descriptionTransition = new TranslateTransition(Duration.millis(1000), description);
        translateTransition.setToY(h + 100);
        translateTransition.setByY(content.getTranslateY() + Menu.toPourcentHeight(20.0, content.getMinHeight()));
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setDelay(Duration.millis(200));
        translateTransition.play();

        return anchorPane;
    }
}
