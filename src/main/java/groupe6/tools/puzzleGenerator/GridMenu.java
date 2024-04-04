package groupe6.tools.puzzleGenerator;

import groupe6.model.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GridMenu implements Menu {
    private Button sauvegarder;
    private Button home;
    private Label infos;
    private VBox layout_v;
    private GridPane gridPane;
    private VBox container;
    private CelluleNode[][] celluleNodes;
    private Cellule[][] cellulesData;
    private Puzzle puzzle;
    private Scene scene;
    private int longueur;
    private int largeur;
    private int compteur;        // utilisé à des fins de test

    public GridMenu(int largeur, int longueur, DifficultePuzzle diff){
        this.compteur = 0;
        this.infos = new Label();
        this.infos.setAlignment(Pos.CENTER);

        this.home = new Button();
        this.home.getStyleClass().add("button-home");
        this.home.setPrefSize(30, 30);
        this.sauvegarder = new Button();
        this.sauvegarder.getStyleClass().add("button-sauvegarder");
        this.sauvegarder.setPrefSize(30, 30);

        FadeTransition fadeSauvegarder = new FadeTransition(Duration.millis(150), this.sauvegarder);
        fadeSauvegarder.setFromValue(1.0);
        fadeSauvegarder.setToValue(0.2);

        FadeTransition fadeHome = new FadeTransition(Duration.millis(150), this.home);
        fadeHome.setFromValue(1.0);
        fadeHome.setToValue(0.2);

        this.sauvegarder.setOnMouseEntered(event -> { mouseEntered(fadeSauvegarder, this.sauvegarder); });
        this.sauvegarder.setOnMouseExited(event -> { mouseExited(fadeSauvegarder, this.sauvegarder); });
        this.home.setOnMouseEntered(event -> { mouseEntered(fadeHome, this.home); });
        this.home.setOnMouseExited(event -> { mouseExited(fadeHome, this.home); });

        this.gridPane = new GridPane();
        this.container = new VBox(infos, gridPane);
        this.longueur = longueur;
        this.largeur = largeur;
        initCellules(this.largeur, this.longueur);
        this.puzzle = new Puzzle(this.largeur, this.longueur, this.cellulesData, diff);
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
            ValeurCote valeur = ValeurCote.VIDE;

            // toggle
            if (clickedButton.getStyleClass().contains("clicked")) {
                clickedButton.getStyleClass().remove("clicked");
            } else {
                clickedButton.getStyleClass().add("clicked");
                valeur = ValeurCote.TRAIT;
            }

            Puzzle puzzle = GridMenu.this.puzzle;
            Cellule cell1 = puzzle.getCelluleSolution(i,j);
            Cellule cell2;
            switch(clickedButton.getText()){
                case "Top" :
                    cell2 = puzzle.getCelluleAdjacenteSolution(i,j,Cellule.HAUT);
                    cell1.setCote(Cellule.HAUT,valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.HAUT));
                    if ( cell2 != null ) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.HAUT);
                        cell2.setCote(coteAdjacent,valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Bottom" :
                    cell2 = puzzle.getCelluleAdjacenteSolution(i,j,Cellule.BAS);
                    cell1.setCote(Cellule.BAS,valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.BAS));
                    if ( cell2 != null ) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.BAS);
                        cell2.setCote(coteAdjacent,valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Left" :
                    cell2 = puzzle.getCelluleAdjacenteSolution(i,j,Cellule.GAUCHE);
                    cell1.setCote(Cellule.GAUCHE,valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.GAUCHE));
                    if ( cell2 != null ) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.GAUCHE);
                        cell2.setCote(coteAdjacent,valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Right" :
                    cell2 = puzzle.getCelluleAdjacenteSolution(i,j,Cellule.DROITE);
                    cell1.setCote(Cellule.DROITE,valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.DROITE));
                    if ( cell2 != null ) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.DROITE);
                        cell2.setCote(coteAdjacent,valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;

                default: // rien
                    break;
            }
            System.out.println(celluleNodes[i][j].getLabel());
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * @param args
     * @return
     * @param <T>
     */
    public <T> HBox getMenu(T... args) {
        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                for (int i = 0; i < celluleNodes.length; i++) {
                    for (int j = 0; j < celluleNodes[i].length; j++) {
                        cellulesData[i][j].setValeur(celluleNodes[i][j].getLabel());
                    }
                }
                // File chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sauvegarder le puzzle");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PUZZLE (*.puzzle)", "*.puzzle"));

                Stage stage = (Stage) sauvegarder.getScene().getWindow();
                java.io.File file = fileChooser.showSaveDialog(Main.getStage());

                if (file != null) {
                    Puzzle.sauvegarderPuzzle(puzzle, file.getAbsolutePath());
                }
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

        VBox buttonContainer = new VBox(home, sauvegarder);
        buttonContainer.setAlignment(Pos.TOP_LEFT);
        buttonContainer.setSpacing(10);
        buttonContainer.setStyle("-fx-background-color: #d0d0d0;");

        HBox globalContainer = new HBox(buttonContainer, container);
        HBox.setHgrow(container, Priority.ALWAYS);

        return globalContainer;
    }

    /**
     * Initialise les données de l'affichage et le stockage du puzzle
     * @param largeur
     * @param longueur
     */
    private void initCellules(int largeur, int longueur) {
        this.celluleNodes = new CelluleNode[largeur][longueur];
        this.cellulesData = new Cellule[largeur][longueur];

        for (int y = 0; y < largeur; y++) {
            for (int x = 0; x < longueur; x++) {
                this.celluleNodes[y][x] = new CelluleNode();
                this.cellulesData[y][x] = new Cellule(-1, new ValeurCote[] { ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE } );
            }
        }
    }

    /**
     * Initialise un nouveau puzzle
     * @param path
     */
    public void initNewPuzzle(String path) {
        this.puzzle = Puzzle.chargerPuzzle(path);
        this.largeur = this.puzzle.getLargeur();
        this.longueur = this.puzzle.getLongueur();

        initCellules(this.largeur, this.longueur);
        this.cellulesData = this.puzzle.getSolutionCelluleData();

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
        switch (this.puzzle.getDifficulte()) {
            case FACILE -> this.infos.setText("Difficulté : Facile\nDimensions : " + this.largeur + " X " + this.longueur);
            case MOYEN -> this.infos.setText("Difficulté : Moyen\nDimensions : " + this.largeur + " X " + this.longueur);
            case DIFFICILE -> this.infos.setText("Difficulté : Difficile\nDimensions : " + this.largeur + " X " + this.longueur);
        }

        // Colonnes
        for (int i = 0; i < this.celluleNodes.length; i++) {
            // Lignes
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                if(nouveau) this.celluleNodes[i][j] = new CelluleNode();
                System.out.print(this.cellulesData[i][j].getValeur() + " ");

                // Coins
                this.gridPane.add(this.celluleNodes[i][j].getCoin(0), j * 2, i * 2);            // top left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(1), j * 2 + 2, i * 2);        // top right
                this.gridPane.add(this.celluleNodes[i][j].getCoin(2), j * 2, i * 2 + 2);        // bottom left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(3), j * 2 + 2, i * 2 + 2);    // bottom right

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
                // Éviter la duplication de la barre horizontale
                if (i == 0) {
                    this.gridPane.add(this.celluleNodes[i][j].getButton(0), j * 2 + 1, i * 2);   // top
                    this.celluleNodes[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.gridPane.add(this.celluleNodes[i][j].getCenterPane(), j * 2 + 1, i * 2 + 1);   // center
                this.gridPane.add(this.celluleNodes[i][j].getButton(1), j * 2 + 1, i * 2 + 2);   // bottom
                this.celluleNodes[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;

                // Éviter la duplication de la barre verticale
                if(j == 0){
                    this.gridPane.add(this.celluleNodes[i][j].getButton(2), j * 2, i * 2 + 1);   // left
                    this.celluleNodes[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.gridPane.add(this.celluleNodes[i][j].getButton(3), j * 2 + 2, i * 2 + 1);   // right
                this.celluleNodes[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;
            }
            System.out.println(" ");
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
