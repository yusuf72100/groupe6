package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.technique.DifficulteTechnique;
import groupe6.model.technique.ResultatTechnique;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.Set;

public class HistoriqueAidesArea {

    /**
     * Le GridMenu qui contient l'historique des aides
     */
    private GridMenu gridMenu;

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
     * Largeut de la fenêtre
     */
    private final Double width;

    /**
     * Hauteur de la fenêtre
     */
    private final Double height;

    private final VBox elementsBox;

    public HistoriqueAidesArea(GridMenu gridMenu, Double w, Double h) {
        this.elementsBox = new VBox();
        this.gridMenu = gridMenu;
        this.width = w;
        this.height = h;
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
     *
     * @param aide l'aide à ajouter
     */
    public void ajouterNouvelleAide(AideInfos aide) {
        int level = aide.getNiveau();
        String description = aide.getResultatTechnique().getNomTechniqueStylise();
        boolean[] eye_opened = {true};
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

        eye_opened[0] = updateUpgradeButtonDisplay(lvl[0], upgradeHelp, eye_opened[0]);

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

        final Set<Coordonnee> coordonnees = aide.getResultatTechnique().getCoordonnees();
        final String nomSyliseTechnique = description;
        final String nomTechnique = aide.getResultatTechnique().getNomTechnique();
        final DifficulteTechnique difficulteTechnique = aide.getResultatTechnique().getDifficulte();
        // handler bouton upgrade niveau
        upgradeHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(aide.getNiveau());
                if ( aide.getNiveau() < 2 ) {
                    // Augmente le niveau de l'aide
                    aide.upgradeNiveau();
                    lvl[0]++;
                    niveauProperty.set(lvl[0]);
                    // Change l'icone de l'upgrade en icon afficher coordonnées
                    upgradeHelp.getStyleClass().clear();
                    upgradeHelp.getStyleClass().add("eye_opened");
                    montrerCoordonneesTechnique(coordonnees,nomSyliseTechnique);
                }
                else {
                    montrerCoordonneesTechnique(coordonnees,nomSyliseTechnique);
                }

            }
        });
        // handler bouton d'affichage des informations supplémentaires
        more.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO : Popup avec les schémas explicatifs ...
                Main.afficherPopUpInfoTechnique(
                    nomSyliseTechnique,
                    nomTechnique,
                    difficulteTechnique,
                    width,
                    height
                );
            }
        });
    }

    private void montrerCoordonneesTechnique(Set<Coordonnee> coordonnees, String nomSyliseTechnique) {
        // Highlight les coordonnées de la technique
        for ( Coordonnee coord : coordonnees ) {
            this.gridMenu.highlightCellule(coord.getY(), coord.getX());
            this.gridMenu.setCellulesAdjacentesCss(coord.getY(), coord.getX());
        }

        Main.afficherPopUpInformation(
            "Aide de niveau 2",
            "Les cellules où à été détectée la technique " + nomSyliseTechnique + " sont mises en évidence en bleu.",
            "Appuyez sur OK pour continuer"

        );

        // Unhighlight les coordonnées de la technique
        for ( Coordonnee coord : coordonnees ) {
            this.gridMenu.resetCellule(coord.getY(), coord.getX());
            this.gridMenu.resetCellulesAdjacentesCss(coord.getY(), coord.getX());
        }
    }

    private boolean updateUpgradeButtonDisplay(int lvl, Button upgradeHelp, boolean eye_opened) {
        //if(aidesInfosList.get(index).getNiveau() >= 2) {
        if(lvl >= 2) {
            upgradeHelp.getStyleClass().clear();
            if(!eye_opened) {
                upgradeHelp.getStyleClass().add("eye_closed");
                eye_opened = true;
            } else {
                upgradeHelp.getStyleClass().add("eye_opened");
                eye_opened = false;
            }
        }
        return eye_opened;
    }
}
