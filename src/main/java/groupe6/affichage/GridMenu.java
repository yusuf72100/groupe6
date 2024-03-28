package groupe6.affichage;

import groupe6.model.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GridMenu implements Menu {
    private Partie partie;
    private Button sauvegarder;
    private Button home;
    private Button pause;
    private HBox layout_v;
    private GridPane gridPane;
    private StackPane container;
    private CelluleNode[][] celluleNodes;
    private Cellule[][] cellulesData;
    private int compteur;        // utilisé à des fins de test
    private Puzzle puzzle;
    private Scene scene;
    private int longueur;
    private int largeur;

    public GridMenu(Partie partie){
        this.compteur = 0;
        this.home = new Button();
        this.home.getStyleClass().add("button-home");
        this.home.setPrefSize(30, 30);
        this.pause = new Button();
        this.pause.getStyleClass().add("button-pause");
        this.pause.setPrefSize(30, 30);
        this.sauvegarder = new Button();
        this.sauvegarder.getStyleClass().add("button-sauvegarder");
        this.sauvegarder.setPrefSize(30, 30);
        this.partie = partie;

        FadeTransition fadeSauvegarder = new FadeTransition(Duration.millis(150), this.sauvegarder);
        fadeSauvegarder.setFromValue(1.0);
        fadeSauvegarder.setToValue(0.2);

        FadeTransition fadeHome = new FadeTransition(Duration.millis(150), this.home);
        fadeHome.setFromValue(1.0);
        fadeHome.setToValue(0.2);

        FadeTransition fadePause = new FadeTransition(Duration.millis(150), this.pause);
        fadePause.setFromValue(1.0);
        fadePause.setToValue(0.2);

        this.sauvegarder.setOnMouseEntered(event -> { mouseEntered(fadeSauvegarder, this.sauvegarder); });
        this.sauvegarder.setOnMouseExited(event -> { mouseExited(fadeSauvegarder, this.sauvegarder); });
        this.home.setOnMouseEntered(event -> { mouseEntered(fadeHome, this.home); });
        this.home.setOnMouseExited(event -> { mouseExited(fadeHome, this.home); });
        this.pause.setOnMouseEntered(event -> { mouseEntered(fadePause, this.pause); });
        this.pause.setOnMouseExited(event -> { mouseExited(fadePause, this.pause); });
        this.gridPane = new GridPane();
        this.container = new StackPane(gridPane);
        this.longueur = partie.getPuzzle().getLongueur();
        this.largeur = partie.getPuzzle().getLargeur();
        initCellules(this.longueur, this.largeur);
        this.puzzle = partie.getPuzzle();
    }

    public Partie getPartie() {
        return partie;
    }

    /**
     * Gestion de chaque bouton (barre)
     */
    public class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        private final int i, j;

        public CelluleButtonEventHandler(int i, int j, Cellule[][] data) {
            this.i = i;
            this.j = j;
            cellulesData = data;
        }

        /**
         * Execute l'action demandée sur le bouton
         * @param event
         */
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Button clicked at (" + i + ", " + j + ")");
            Button clickedButton = (Button) event.getSource();

            Partie partie = GridMenu.this.getPartie();

            int cote;
            switch ( clickedButton.getText() ) {
                case "Top" :
                    cote = Cellule.HAUT;
                    break;
                case "Bottom" :
                    cote = Cellule.BAS;
                    break;
                case "Left" :
                    cote = Cellule.GAUCHE;
                    break;
                case "Right" :
                    cote = Cellule.DROITE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + clickedButton.getText());
            }

            ValeurCote valeurCote = partie.getPuzzle().getCellule(j, i).getCote(cote);
            partie.actionBasculeTroisEtat(j,i,cote);

            switch ( valeurCote ) {
                case VIDE:
                    clickedButton.getStyleClass().add("clicked");
                    break;
                case TRAIT:
                    clickedButton.getStyleClass().remove("clicked");
                    clickedButton.getStyleClass().add("croix");
                    break;
                case CROIX:
                    clickedButton.getStyleClass().remove("croix");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + valeurCote);
            }
            System.out.println(clickedButton.getText());
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * @param args
     * @return
     * @param <T>
     */
    public <T> AnchorPane getMenu(T... args) {
        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Partie partie = GridMenu.this.getPartie();
                partie.sauvegarder();
            }
        });

        // handler bouton retour au menu principal
        home.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Main.showMainMenu();
            }
        });

        afficher((boolean) args[0]);

        System.out.println(compteur + " bars counted");
        gridPane.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().addAll("button-square");

        HBox buttonContainer = new HBox(home, sauvegarder, pause);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        buttonContainer.setSpacing(10);
        buttonContainer.setStyle("-fx-background-color: #d0d0d0;");
        //HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        AnchorPane anchorPane = new AnchorPane(container, buttonContainer);
        AnchorPane.setTopAnchor(container, buttonContainer.getPrefHeight());
        AnchorPane.setLeftAnchor(container, (anchorPane.getPrefWidth() - container.getPrefWidth()) / 2.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
        buttonContainer.prefWidthProperty().bind(anchorPane.widthProperty());

        return anchorPane;
    }

    /**
     * Initialise les données de l'affichage et le stockage du puzzle
     * @param l
     * @param L
     */
    private void initCellules(int l, int L) {
        this.celluleNodes = new CelluleNode[l][L];
        this.cellulesData = this.partie.getPuzzle().getGrilleJeu();

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                this.celluleNodes[i][j] = new CelluleNode(this.cellulesData[i][j].getValeur(), this.cellulesData[i][j].getCotes());
            }
        }
    }

    /**
     * Initialise un nouveau puzzle
     * @param path
     */
    public void initNewPuzzle(String path) {
        this.puzzle = Puzzle.chargerPuzzle(path);
        this.longueur = this.puzzle.getLargeur();
        this.largeur = this.puzzle.getLongueur();

        initCellules(this.longueur, this.largeur);
        this.cellulesData = this.puzzle.getGrilleSolution();

        for (int i  = 0 ; i < this.cellulesData.length; i++) {
            for (int j = 0; j < this.cellulesData[i].length; j++) {
                this.celluleNodes[i][j].setLabel(this.cellulesData[i][j].getValeur());

                if(this.cellulesData[i][j].getValeur() != -1) {
                    this.celluleNodes[i][j].setLabeText(this.cellulesData[i][j].getValeur());
                }

                for (int k = 0; k < 4; k++) {
                    switch (this.cellulesData[i][j].getCote(k)) {
                        case VIDE :
                            break ;
                        case TRAIT:
                            this.celluleNodes[i][j].getButton(k).getStyleClass().add("clicked");
                            break ;
                        default: // rien
                            break;
                    }
                }
            }
        }
    }

    /**
     * Affiche le puzzle en fonction de si on veut créer un nouveau puzzle ou non
     * @param nouveau
     */
    private void afficher(boolean nouveau) {
        // Colonnes
        for (int i = 0; i < this.celluleNodes.length; i++) {
            // Lignes
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                // if(nouveau) this.celluleNodes[i][j] = new CelluleNode(-1);

                // Coins
                this.gridPane.add(this.celluleNodes[i][j].getCoin(0), i * 2, j * 2);            // top left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(1), i * 2 + 2, j * 2);        // top right
                this.gridPane.add(this.celluleNodes[i][j].getCoin(2), i * 2, j * 2 + 2);        // bottom left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(3), i * 2 + 2, j * 2 + 2);    // bottom right

                // animation fade
                for (int boutonIndex = 0; boutonIndex < 4; boutonIndex++) {
                    Button button = this.celluleNodes[i][j].getButton(boutonIndex);
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), button);

                    button.setOnMouseEntered(event -> {
                        if(button.getStyleClass().contains("clicked")) {
                            fadeTransition.setFromValue(1.0);
                            fadeTransition.setToValue(0.2);
                        } else {
                            fadeTransition.setFromValue(0.2);
                            fadeTransition.setToValue(1.0);
                        }
                        mouseEntered(fadeTransition, button);
                    });

                    button.setOnMouseExited(event -> {
                        mouseExited(fadeTransition, button);
                    });
                }

                // Barres
                // Avoid horizontal bar duplication
                if (j == 0) {
                    this.gridPane.add(this.celluleNodes[i][j].getButton(0), i * 2 + 1, 0);   // top
                    this.celluleNodes[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.gridPane.add(this.celluleNodes[i][j].getCenterPane(), i * 2 + 1, j * 2 + 1);   // center
                this.gridPane.add(this.celluleNodes[i][j].getButton(1), i * 2 + 1, j * 2 + 2);   // bottom
                this.celluleNodes[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;

                // Avoid vertical bar duplication
                if(i == 0){
                    this.gridPane.add(this.celluleNodes[i][j].getButton(2), 0, j * 2 + 1);   // left
                    this.celluleNodes[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.gridPane.add(this.celluleNodes[i][j].getButton(3), i * 2 + 2, j * 2 + 1);   // right
                this.celluleNodes[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;
                System.out.println(this.celluleNodes[1][1].getLabel());
            }
        }
    }

    /**
     * Règle l'animation d'entrée sur le bouton souhaité
     * @param fade
     * @param button
     */
    private static void mouseEntered(FadeTransition fade, Button button) {
        fade.setRate(1);
        fade.play();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Règle l'animation de sortie sur le bouton souhaité
     * @param fade
     * @param button
     */
    private static void mouseExited(FadeTransition fade, Button button) {
        fade.setRate(-1);
        fade.play();
        fade.jumpTo(Duration.ZERO);
        button.setCursor(Cursor.DEFAULT);
    }
}
