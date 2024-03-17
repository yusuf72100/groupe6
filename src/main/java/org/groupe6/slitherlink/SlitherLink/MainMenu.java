package org.groupe6.slitherlink.SlitherLink;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.Objects;

public class MainMenu implements Menu {
    protected static ComboBox<String> profilSelector;
    protected static String[] buttonTextsLabels;
    protected static HBox mainHbox;
    protected static StackPane mainPane;
    protected static HBox[] descriptionsBackground;
    protected static StackPane[] buttonsContainer;
    protected static Button[] buttons;
    protected static Text[] descriptionText;
    protected static Text[] buttonsText;

    // animations
    protected static TranslateTransition[] rectangleTransition;
    protected static TranslateTransition[] rectangleTransitionReverse;
    protected static FadeTransition[] fadeTransition;
    protected static FadeTransition[] fadeTransitionReverse;
    protected static Rectangle[] clipRectangle;

    protected static Text title = new Text("Slitherlink");

    public static void initMenu() {
        profilSelector = new ComboBox<>();
        buttonTextsLabels = new String[]{"JOUER", "OPTIONS", "ENTRAÎNEMENT"};
        mainHbox = new HBox();
        mainPane = new StackPane();
        descriptionsBackground = new HBox[3];
        buttonsContainer = new StackPane[3];
        buttons = new Button[3];
        descriptionText = new Text[3];
        buttonsText = new Text[3];

        // animations
        rectangleTransition = new TranslateTransition[3];
        rectangleTransitionReverse = new TranslateTransition[3];
        fadeTransition = new FadeTransition[3];
        fadeTransitionReverse = new FadeTransition[3];
        clipRectangle = new Rectangle[3];

        title = new Text("Slitherlink");
    }

    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        title.getStyleClass().add("title");
        title.setTranslateY(Menu.toPourcentHeight(50.0, windowHeigth));

        mainHbox.setSpacing(200);       // espacement entre les éléments

        profilSelector.getItems().addAll("PlaceHolder #1", "PlaceHolder #2", "PlaceHolder #3");
        profilSelector.getSelectionModel().selectFirst();
        profilSelector.setTranslateX(Menu.toPourcentWidth(700.0, windowWidth));
        profilSelector.setTranslateY(Menu.toPourcentHeight(-450.0, windowHeigth));
        profilSelector.getStyleClass().add("combo-box");

        // Header
        profilSelector.setCellFactory(param -> new ListCell<String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("avatard-50x50.png")));
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        // Elements
        profilSelector.setButtonCell(new ListCell<String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    // Chargement de l'image
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("avatard-50x50.png")));
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;

            buttons[finalI] = new Button();
            buttons[finalI].setPrefSize(Menu.toPourcentWidth(350.0, windowWidth), Menu.toPourcentHeight(500.0, windowHeigth));
            buttons[finalI].getStyleClass().add("button-rounded");

            buttonsText[finalI] = new Text(buttonTextsLabels[finalI]);
            buttonsText[finalI].getStyleClass().add("button-text");
            buttonsText[finalI].setFocusTraversable(false);
            buttonsText[finalI].setMouseTransparent(true);
            Menu.adaptTextSize(buttonsText[finalI], 35, windowWidth, windowHeigth);

            descriptionsBackground[finalI] = new HBox();
            descriptionsBackground[finalI].setMaxSize(buttons[finalI].getPrefWidth(), Menu.toPourcentHeight(100.0, windowHeigth));
            descriptionsBackground[finalI].setStyle("-fx-background-color: transparent;");
            descriptionsBackground[finalI].setTranslateY(1);
            StackPane.setAlignment(descriptionsBackground[finalI], Pos.BOTTOM_CENTER);

            // positionnement de la description
            descriptionText[finalI] = new Text();
            descriptionText[finalI].setFocusTraversable(false);
            descriptionText[finalI].setMouseTransparent(true);
            descriptionText[finalI].setTranslateY(Menu.toPourcentHeight(200.0, windowHeigth));
            descriptionText[finalI].getStyleClass().add("description-text");
            Menu.adaptTextSize(descriptionText[finalI], 18, windowWidth, windowHeigth);

            //labels[finalI].setStyle("-fx-padding: 0 0 10 0;"); // Ajouter un padding pour déplacer le texte vers le bas
            buttonsContainer[finalI] = new StackPane();
            buttonsContainer[finalI].getChildren().addAll(buttons[finalI], buttonsText[finalI], descriptionsBackground[finalI], descriptionText[finalI]);
            buttonsContainer[finalI].setAlignment(Pos.CENTER);

            //translation text animation
            clipRectangle[finalI] = new Rectangle(buttons[finalI].getPrefWidth(), Menu.toPourcentHeight(100.0, windowHeigth));
            descriptionsBackground[finalI].getStyleClass().add("description-background");
            descriptionsBackground[finalI].setClip(clipRectangle[finalI]);
            descriptionsBackground[finalI].setFocusTraversable(false);
            descriptionsBackground[finalI].setMouseTransparent(true);

            rectangleTransition[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransition[finalI].setFromY(Menu.toPourcentHeight(200.0, windowHeigth));
            rectangleTransition[finalI].setToY(0);

            rectangleTransitionReverse[finalI] = new TranslateTransition(Duration.seconds(0.2), clipRectangle[finalI]);
            rectangleTransitionReverse[finalI].setFromY(0);
            rectangleTransitionReverse[finalI].setToY(Menu.toPourcentHeight(200.0, windowHeigth));

            fadeTransition[finalI] = new FadeTransition(Duration.seconds(0.3), descriptionText[finalI]);
            fadeTransition[finalI].setFromValue(0.0);
            fadeTransition[finalI].setToValue(1.0);

            fadeTransitionReverse[finalI] = new FadeTransition(Duration.seconds(0.2), descriptionText[finalI]);
            fadeTransitionReverse[finalI].setFromValue(1.0);
            fadeTransitionReverse[finalI].setToValue(0.0);

            // hover on
            buttonsContainer[finalI].setOnMouseEntered(e -> {
                buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #e0ac1e;");
                descriptionText[finalI].setTextAlignment(TextAlignment.CENTER);

                switch (finalI) {
                    // bouton jouer
                    case 0 :
                        buttons[finalI].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.showGameModeMenu();
                            }
                        });
                        descriptionText[finalI].setText("Choisissez un mode de jeu");
                        break;
                    case 1 : descriptionText[finalI].setText("Personnalisez les options du jeu"); break;
                    case 2 : descriptionText[finalI].setText("Entraînez-vous à devenir \nmeilleur au jeu"); break;
                    default : descriptionText[finalI].setText("Placeholder #" + finalI); break;
                }

                descriptionsBackground[finalI].setStyle("-fx-background-color: gray;");
                rectangleTransition[finalI].play();
                fadeTransition[finalI].play();
            });

            // hover off
            buttonsContainer[finalI].setOnMouseExited(e -> {
                rectangleTransition[finalI].stop();
                fadeTransition[finalI].stop();
                rectangleTransitionReverse[finalI].play();
                fadeTransitionReverse[finalI].play();
                buttons[finalI].setStyle("-fx-background-radius: 10px; -fx-background-color: #D9D9D9;");
            });

            buttonsContainer[finalI].setMaxSize(buttonsContainer[finalI].getPrefWidth(), buttons[finalI].getPrefHeight());
        }

        mainHbox.setAlignment(Pos.CENTER);
        mainHbox.getChildren().addAll(buttonsContainer);
        mainPane.getChildren().addAll(new ImageView(new Image(Objects.requireNonNull(MainMenu.class.getResource("bg.png")).toExternalForm())), title, mainHbox, profilSelector);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        return mainPane;
    }
}
