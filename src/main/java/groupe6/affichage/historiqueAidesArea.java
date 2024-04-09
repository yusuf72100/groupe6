package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.aide.AideInfos;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class historiqueAidesArea {
    /**
     * Boîte vertical de scroll
     */
    private final VBox historiqueAidesVBox;

    /**
     * Scroll pane du composant d'historique des aides
     */
    private final ScrollPane historiqueAidesScrollPane;

    /**
     * Stack pane du composant d'historique des aides
     */
    private final StackPane historiqueAidesStackPane;

    /**
     * Titre du composant d'historique des aides
     */
    private final Label titre;

    /**
     * Liste des aides
     */
    private final List<AideInfos> aidesInfosList;

    /**
     * Largeut de la fenêtre
     */
    private final Double width;

    /**
     * Hauteur de la fenêtre
     */
    private final Double height;

    private final VBox elementsBox;

    public historiqueAidesArea(Double w, Double h) {
        this.elementsBox = new VBox();
        this.width = w;
        this.height = h;
        this.aidesInfosList = new ArrayList<>();
        this.titre = new Label("Aides");
        this.titre.getStyleClass().add("title_help");
        this.historiqueAidesStackPane = new StackPane();
        this.historiqueAidesVBox = new VBox();
        this.historiqueAidesVBox.setPrefSize(Menu.toPourcentWidth(800.0, 800.0) , h);
        this.historiqueAidesVBox.setAlignment(Pos.TOP_CENTER);
        this.historiqueAidesScrollPane = new ScrollPane(this.historiqueAidesVBox);
        this.historiqueAidesScrollPane.setFitToWidth(true);
        this.historiqueAidesScrollPane.setFitToHeight(true);
        this.historiqueAidesScrollPane.getStyleClass().add("scroll-pane");
        this.historiqueAidesStackPane.getChildren().add(this.historiqueAidesScrollPane);
        this.historiqueAidesStackPane.setAlignment(Pos.CENTER_RIGHT);
        this.historiqueAidesVBox.setSpacing(50);
        this.elementsBox.setSpacing(50);
        this.historiqueAidesVBox.setStyle("-fx-background-color: #e0ac1e;");
        this.historiqueAidesVBox.getChildren().addAll(this.titre, elementsBox);
        this.historiqueAidesVBox.setPadding(new Insets(0, 30, 0, 15));
        this.titre.setTranslateY(Menu.toPourcentHeight(200.0, 200.0));

        Menu.adaptTextSize(this.titre, 50, w, h);
        StackPane.setAlignment(this.historiqueAidesScrollPane, Pos.CENTER);
        this.historiqueAidesStackPane.setTranslateX(w-this.historiqueAidesVBox.getPrefWidth()); // placement à droite

        // test
        ajouterNouvelleAide(1, "Nouvelle Aide");
        ajouterNouvelleAide(2, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(3, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(2, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(2, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(2, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(2, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
        ajouterNouvelleAide(3, "Nouvelle Aide2abcdefghijklmnopqrstuvwxyzAide2abcdefghijklmnopqrstuvwxyz");
    }

    /**
     * Getter de la variable historiqueAidesStackPane
     * @return renvoi la variable historiqueAidesStackPane
     */
    public StackPane getHistoriqueAidesStackPane() {
        return this.historiqueAidesStackPane;
    }

    /**
     * Ajouter une aide à la liste des aides
     * @param aideInfos aide à ajouter
     */
    public void ajouterNouvelleAide(AideInfos aideInfos) {
        this.aidesInfosList.add(aideInfos);
        ajouterNouvelleAide(aideInfos.getNiveau(), aideInfos.getInfoTechnique().getExplicationTxt());
    }

    /**
     * Ajouter une aide visuellement à la liste
     * @param level niveau d'aide
     * @param description description de l'aide
     */
    private void ajouterNouvelleAide(int level, String description) {
        Button upgradeHelp = GridMenu.initHeaderButton("button-upgradeLevel", "Améliorer l'aide", this.width, this.height);
        Button more = GridMenu.initHeaderButton("button-more", "Plus d'informations", this.width, this.height);
        String cheminLevel = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/level" + level + ".png");
        final ImageView[] imageLevel = {new ImageView(Launcher.chargerImage(cheminLevel))};
        Label niveau = new Label("Niveau");
        Label descriptionText = new Label();
        HBox header = new HBox(niveau, imageLevel[0], upgradeHelp, more);
        Rectangle separator = new Rectangle(this.historiqueAidesVBox.getPrefWidth()-30, 5);
        VBox aideNode = new VBox(header, descriptionText, separator);
        final int[] lvl = {level};

        header.setSpacing(10);
        aideNode.setSpacing(20);
        descriptionText.setWrapText(true);
        separator.setFill(Color.BLACK);
        imageLevel[0].setFitWidth(Menu.toPourcentWidth(200.0, 300.0));
        imageLevel[0].setFitHeight(Menu.toPourcentWidth(200.0, 300.0));
        upgradeHelp.setPrefSize(imageLevel[0].getFitWidth(), imageLevel[0].getFitHeight());
        more.setPrefSize(upgradeHelp.getPrefWidth(), upgradeHelp.getPrefHeight());
        descriptionText.setStyle("-fx-font-family: 'Inter'");
        Menu.adaptTextSize(niveau, 25, this.width, this.height);
        Menu.adaptTextSize(descriptionText, 20, this.width, this.height);
        niveau.getStyleClass().add("aideNiveau");

        aideNode.setAlignment(Pos.CENTER_LEFT);
        aideNode.setMinSize(this.historiqueAidesVBox.getMaxWidth(), Region.USE_PREF_SIZE);  // fixer la taile d'un composant de la liste
        aideNode.setMaxSize(this.historiqueAidesVBox.getMaxWidth(), Region.USE_PREF_SIZE);  // fixer la taile d'un composant de la liste
        this.elementsBox.getChildren().add(0, aideNode);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {
            descriptionText.setText("");
        }));

        for (int i = 0; i < description.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis((i + 1) * 30), e -> {
                descriptionText.setText(descriptionText.getText() + description.charAt(index));
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        FadeTransition fade = new FadeTransition(Duration.millis(300), descriptionText);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        timeline.play();

        FadeTransition ft = new FadeTransition(Duration.millis(300), aideNode);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), aideNode);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);
        scale.play();

        int index = aidesInfosList.size();

        // Handler de la variable de niveau
        IntegerProperty niveauProperty = new SimpleIntegerProperty(lvl[0]);      //TEST
        //TODO : IntegerProperty niveauProperty = new SimpleIntegerProperty(aidesInfosList.get(index).getNiveau());
        niveauProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String cheminLevel = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/level" + newValue + ".png");
                imageLevel[0].setImage(Launcher.chargerImage(cheminLevel));
            }
        });

        // handler bouton upgrade niveau
        upgradeHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO : aidesInfosList.get(index).upgradeNiveau(); niveauProperty.set(aidesInfosList.get(index).getNiveau());
                lvl[0]++;   // test
                niveauProperty.set(lvl[0]);
            }
        });

        // handler bouton d'affichage des informations supplémentaires
        more.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO : Popup avec les schémas explicatifs ...
            }
        });
    }
}
