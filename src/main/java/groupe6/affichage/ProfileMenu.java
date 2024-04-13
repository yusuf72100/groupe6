package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.profil.Profil;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static Label[] description;

    private static VBox descriptionVbox;

    private static Button backButton;

    private static Button photoButton;

    private static StackPane classementStack;

    public static void initMenu(Double w, Double h) {
        classementStack = new StackPane();
        photoButton = new Button();
        backButton = new Button("QUITTER");
        description = new Label[5];
        descriptionVbox = new VBox();
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
        imageView.setFitWidth(Menu.toPourcentWidth(100.0, w));
        imageView.setFitHeight(Menu.toPourcentHeight(100.0, h));
    }

    /**
     * Méthode qui extrait la date depuis le nom de la sauvegarde
     *
     * @param date le nom de la sauvegarde
     * @return la date de la sauvegarde (String)
     */
    private static String extraireDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatee = sdf.format(date);

        return dateFormatee;
    }

    public static AnchorPane getMenu(Stage primaryStage, Double w, Double h) {
        initMenu(w,h);

        content.getChildren().addAll(scrollPane, classementStack);
        content.setMinSize(Menu.toPourcentWidth(1200.0, w), Menu.toPourcentHeight(700.0, h));
        content.setMaxSize(Menu.toPourcentWidth(1200.0, w), Menu.toPourcentHeight(700.0, h));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: transparent; -fx-background-radius: 5;");
        StackPane.setAlignment(profilVbox, Pos.CENTER);

        // Classement
        classementStack.setVisible(false);
        classementStack.setManaged(false);
        classementStack.setStyle("-fx-background-color: lightgray");
        classementStack.setPadding(new Insets(10, 10, 10, 10));

        Rectangle[] classementRectangles = new Rectangle[Launcher.catalogueProfils.getListeProfils().size()];
        HBox classementHBox = new HBox();
        VBox[] elementVbox = new VBox[Launcher.catalogueProfils.getListeProfils().size()];
        classementHBox.setSpacing(20);

        int maxScore = 0;

        // on récupère le score max
        for ( Profil profil : Launcher.catalogueProfils.getListeProfils())
            maxScore = maxScore < profil.getHistorique().calculerStat().get(2) ? profil.getHistorique().calculerStat().get(2) : maxScore;

        for(int i = 0 ; i < Launcher.catalogueProfils.getListeProfils().size(); i++) {
            elementVbox[i] = new VBox();
            if(maxScore >= content.getMinHeight()) {
                classementRectangles[i] = new Rectangle(30, Launcher.catalogueProfils.getListeProfils().get(i).getHistorique().calculerStat().get(2)*0.5);
            } else {
                classementRectangles[i] = new Rectangle(30, Launcher.catalogueProfils.getListeProfils().get(i).getHistorique().calculerStat().get(2));
            }

            classementRectangles[i].setFill(Color.valueOf("#e0ac1e"));
            elementVbox[i].setSpacing(10);

            elementVbox[i].getChildren().addAll(new Label(Launcher.catalogueProfils.getListeProfils().get(i).getNom()), classementRectangles[i]);
            classementHBox.getChildren().add(elementVbox[i]);
            elementVbox[i].setAlignment(Pos.BOTTOM_CENTER);
        }

        classementHBox.setAlignment(Pos.CENTER);
        classementStack.getChildren().add(classementHBox);
        classementStack.setAlignment(Pos.CENTER);

        // Statistiques
        photoButton.setGraphic(imageView);
        photoButton.setStyle("-fx-background-color: transparent;");
        photoButton.setOnMouseEntered(e -> photoButton.setStyle("-fx-opacity: 0.5; -fx-background-color: transparent;"));
        photoButton.setOnMouseExited(e -> photoButton.setStyle("-fx-opacity: 1.0; -fx-background-color: transparent;"));

        List<Integer> stats = Launcher.catalogueProfils.getProfilActuel().getHistorique().calculerStat();
        description[0] = new Label(Launcher.catalogueProfils.getProfilActuel().getNom());
        description[1] = new Label("Niveau en mode aventure : " + (Launcher.catalogueProfils.getProfilActuel().getNiveauAventure()+1));
        description[2] = new Label("Nombre de parties jouées : " + stats.get(0));
        description[3] = new Label("Nombre de parties gagnées : " + stats.get(1));
        description[4] = new Label("Meilleur score : " + stats.get(2));

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
        profilVbox.setSpacing(50);
        profilVbox.setStyle("-fx-background-color: lightgray; -fx-background-radius: 5;");
        profilVbox.getChildren().addAll(photoButton, descriptionVbox);
        profilVbox.setAlignment(Pos.CENTER);
        profilVbox.setPadding(new Insets(15, 0, 15, 0));

        scrollPane.setContent(profilVbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        anchorPane.getChildren().addAll(hBox, content);
        AnchorPane.setTopAnchor(content, (h - content.getMinHeight()) / 2);
        AnchorPane.setLeftAnchor(content, (w - content.getMinWidth()) / 2);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1200), photoButton);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        for(Label l : description) {
            l.setStyle("-fx-font-size: 30; -fx-text-align: center;");
            l.setWrapText(true);
            descriptionVbox.getChildren().add(l);
        }

        Rectangle separatorDescription = new Rectangle(content.getMinWidth() - Menu.toPourcentWidth(80.0, content.getMinWidth()), 5);
        HBox.setMargin(separatorDescription, new Insets(15, 0, 0, 0));
        descriptionVbox.getChildren().add(separatorDescription);

        descriptionVbox.setFillWidth(true);
        descriptionVbox.setAlignment(Pos.CENTER);
        descriptionVbox.setSpacing(5);

        // Historique
        VBox historique = new VBox();
        historique.setAlignment(Pos.CENTER);
        historique.setSpacing(20);
        historique.setPadding(new Insets(30, 0, 0, 0));

        for(int i = 0; i < stats.get(1); i++) {
            VBox descriptionBox = new VBox();
            Pane pane = new Pane();
            pane.getStyleClass().add("button-rounded");
            pane.setStyle("-fx-background-color: #e0ac1e;");
            pane.setMinSize(700, 100);
            pane.setMaxSize(700, 100);

            Label difficulte = new Label("Difficulté : " + Launcher.catalogueProfils.getProfilActuel().getHistorique().getReultatPartie().get(i).getDifficulte().name());
            difficulte.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 15px;");
            difficulte.setAlignment(Pos.CENTER);
            difficulte.setPrefSize(pane.getMinWidth(), pane.getMinHeight());
            difficulte.setWrapText(true);

            Label date = new Label("Date : " + extraireDate(Launcher.catalogueProfils.getProfilActuel().getHistorique().getReultatPartie().get(i).getDate()));
            date.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
            date.setAlignment(Pos.CENTER);
            date.setPrefSize(pane.getMinWidth(), pane.getMinHeight());
            date.setWrapText(true);

            Label taille = new Label("Taille : " + Launcher.catalogueProfils.getProfilActuel().getHistorique().getReultatPartie().get(i).getLargeur() + "x" + Launcher.catalogueProfils.getProfilActuel().getHistorique().getReultatPartie().get(i).getLongeur());
            taille.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
            taille.setAlignment(Pos.CENTER);
            taille.setPrefSize(pane.getMinWidth(), pane.getMinHeight());
            taille.setWrapText(true);

            Label modeDeJeu = new Label("Mode de jeu : " + Launcher.catalogueProfils.getProfilActuel().getHistorique().getReultatPartie().get(i).getModeJeu().name());
            modeDeJeu.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
            modeDeJeu.setAlignment(Pos.CENTER);
            modeDeJeu.setPrefSize(pane.getMinWidth(), pane.getMinHeight());
            modeDeJeu.setWrapText(true);

            descriptionBox.getChildren().addAll(date, modeDeJeu, difficulte, taille);
            descriptionBox.setAlignment(Pos.CENTER);
            descriptionBox.setMinSize(pane.getMinWidth(), pane.getMinHeight());
            descriptionBox.setMaxSize(pane.getMaxWidth(), pane.getMaxHeight());
            pane.getChildren().addAll(descriptionBox);
            historique.getChildren().add(pane);
        }

        descriptionVbox.getChildren().add(historique);

        backButton.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
        backButton.getStyleClass().add("button-rounded");

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.exitAll();
            }
        });

        anchorPane.getChildren().addAll(backButton);
        AnchorPane.setBottomAnchor(backButton, 10.0);
        AnchorPane.setLeftAnchor(backButton, ((w/2) - (backButton.getPrefWidth()) / 2));

        backButton.setStyle("-fx-font-weight: bold; -fx-text-align: center; -fx-content-display: center; -fx-font-size: 30;");

        photoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Launcher.catalogueProfils.getProfilActuel().choisirImage(primaryStage);
                    String cheminImageAvatar = Launcher
                            .normaliserChemin(Launcher.catalogueProfils.getProfilActuel().getIMG());
                    imageView.setImage(Launcher.chargerImage(cheminImageAvatar));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                classementStack.setVisible(false);
                classementStack.setManaged(false);
                scrollPane.setVisible(true);
                scrollPane.setManaged(true);
            }
        });

        classement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scrollPane.setVisible(false);
                scrollPane.setManaged(false);
                classementStack.setVisible(true);
                classementStack.setManaged(true);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.showMainMenu();
            }
        });

        EventHandler<KeyEvent> keyEventHandler = event -> {
            KeyCode keyCode = event.getCode();

            if (keyCode == KeyCode.ESCAPE) {
                Main.showMainMenu();
            }
        };

        anchorPane.setOnKeyPressed(keyEventHandler);

        return anchorPane;
    }
}