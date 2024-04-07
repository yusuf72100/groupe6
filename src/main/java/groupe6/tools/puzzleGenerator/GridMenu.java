package groupe6.tools.puzzleGenerator;

import groupe6.affichage.Menu;
import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Classe qui correspond a l'inteface graphique d'un puzzle dans le puzzle generator
 *
 * @author Yusuf
 */
public class GridMenu implements Menu {

    /**
     * Le bouton pour sauvegarder la partie
     */
    private final Button sauvegarder;

    /**
     * Le bouton pour revenir au menu principal
     */
    private final Button home;

    /**
     * Le label pour afficher les informations du puzzle
     */
    private Label infos;

    // TODO : a supprimer si pas utilisé
    /**
     * Le layout vertical
     */
    private VBox layout_v;

    /**
     * Le gridPane qui contient les cellules
     */
    private GridPane gridPane;

    /**
     * Le container qui contient le gridPane
     */
    private VBox container;

    /**
     * La grille des nodes des cellules
     */
    private CelluleNode[][] celluleNodes;

    /**
     * La grille des données des cellules
     */
    private Cellule[][] cellulesData;

    /**
     * La sauvegarde du puzzle qui modifié
     */
    private PuzzleSauvegarde puzzle;

    // TODO : supprimer si pas utilisé
    /**
     * La scène de l'interface graphique
     */
    private Scene scene;

    // TODO : mettre en final si pas modifié par la méthode initNewPuzzle(String)
    /**
     * La largeur du puzzle
     */
    private int largeur;

    // TODO : mettre en final si pas modifié par la méthode initNewPuzzle(String)
    /**
     * La longueur du puzzle
     */
    private int longueur;

    // TODO : supprimer après test
    /**
     * Le compteur de barres
     */
    private int compteur; // utilisé à des fins de test

    /**
     * Constructeur de la classe GridMenu du puzzle generator
     *
     * @param largeur la largeur du puzzle
     * @param longueur la longueur du puzzle
     * @param diff la difficulté du puzzle
     */
    public GridMenu(int largeur, int longueur, DifficultePuzzle diff) {
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

        this.sauvegarder.setOnMouseEntered(event -> {
            mouseEntered(fadeSauvegarder, this.sauvegarder);
        });
        this.sauvegarder.setOnMouseExited(event -> {
            mouseExited(fadeSauvegarder, this.sauvegarder);
        });
        this.home.setOnMouseEntered(event -> {
            mouseEntered(fadeHome, this.home);
        });
        this.home.setOnMouseExited(event -> {
            mouseExited(fadeHome, this.home);
        });

        this.gridPane = new GridPane();
        this.container = new VBox(infos, gridPane);
        this.longueur = longueur;
        this.largeur = largeur;
        initCellules(this.largeur, this.longueur);
        this.puzzle = new PuzzleSauvegarde(this.largeur, this.longueur, diff);
    }

    /**
     * Méthode qui met à jour l'affichage du puzzle en fonction du modèle
     */
    private void updateAffichage() {
        // Update des cellules
        for ( int y = 0; y < this.largeur; y++ ) {
            for (int x = 0; x < this.longueur; x++) {
                this.celluleNodes[y][x].updateCotes(cellulesData[y][x].getCotes());
            }
        }
    }

    /**
     * Classe qui s'occupe de la gestion de chaque bouton (barre)
     */
    public class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        /**
         * Les coordonnées en y et x de la cellule
         */
        private final int i, j;

        /**
         * Le constructeur de la classe CelluleButtonEventHandler
         *
         * @param i la position en y de la cellule
         * @param j la position en x de la cellule
         * @param data la grille des données des cellules
         */
        public CelluleButtonEventHandler(int i, int j, Cellule[][] data) {
            this.i = i;
            this.j = j;
            cellulesData = data;
        }

        /**
         * Méthode qui execute l'action demandée sur le bouton
         * 
         * @param event l'événement qui a déclenché l'action
         */
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Button clicked at (" + i + ", " + j + ")");
            Button clickedButton = (Button) event.getSource();
            ValeurCote valeur;

            // toggle
            if (clickedButton.getStyleClass().contains("clicked")) {
                clickedButton.getStyleClass().remove("clicked");
                clickedButton.getStyleClass().add("croix");
                System.out.println("trait");
                valeur = ValeurCote.CROIX;
            } else if(clickedButton.getStyleClass().contains("croix")) {
                clickedButton.getStyleClass().remove("clicked");
                clickedButton.getStyleClass().remove("croix");
                System.out.println("croix");
                valeur = ValeurCote.VIDE;
            } else {
                clickedButton.getStyleClass().add("clicked");
                clickedButton.getStyleClass().remove("croix");
                System.out.println("vide");
                valeur = ValeurCote.TRAIT;
            }

            Cellule cell1 = puzzle.getCelluleGrille(i, j,GridMenu.this.puzzle.getGrilleSolution());
            Cellule cell2;
            switch (clickedButton.getText()) {
                case "Top":
                    if(valeur == ValeurCote.CROIX) {
                        celluleNodes[i][j].addCroix(0);
                    } else {
                        celluleNodes[i][j].removeCroix(0);
                    }
                    cell2 =  GridMenu.this.puzzle.getCelluleAdjacentGrille(i, j, Cellule.HAUT, puzzle.getGrilleSolution());
                    cell1.setCote(Cellule.HAUT, valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.HAUT));
                    if (cell2 != null) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.HAUT);
                        cell2.setCote(coteAdjacent, valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Bottom":
                    if(valeur == ValeurCote.CROIX) {
                        celluleNodes[i][j].addCroix(1);
                    } else {
                        celluleNodes[i][j].removeCroix(1);
                    }
                    cell2 = GridMenu.this.puzzle.getCelluleAdjacentGrille(i, j, Cellule.BAS, puzzle.getGrilleSolution());
                    cell1.setCote(Cellule.BAS, valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.BAS));
                    if (cell2 != null) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.BAS);
                        cell2.setCote(coteAdjacent, valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Left":
                    if(valeur == ValeurCote.CROIX) {
                        celluleNodes[i][j].addCroix(2);
                    } else {
                        celluleNodes[i][j].removeCroix(2);
                    }
                    cell2 = GridMenu.this.puzzle.getCelluleAdjacentGrille(i, j, Cellule.GAUCHE, puzzle.getGrilleSolution());
                    cell1.setCote(Cellule.GAUCHE, valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.GAUCHE));
                    if (cell2 != null) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.GAUCHE);
                        cell2.setCote(coteAdjacent, valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;
                case "Right":
                    if(valeur == ValeurCote.CROIX) {
                        celluleNodes[i][j].addCroix(3);
                    } else {
                        celluleNodes[i][j].removeCroix(3);
                    }
                    cell2 = GridMenu.this.puzzle.getCelluleAdjacentGrille(i, j, Cellule.DROITE, puzzle.getGrilleSolution());
                    cell1.setCote(Cellule.DROITE, valeur);
                    System.out.println("cell1 :" + cell1.getCote(Cellule.DROITE));
                    if (cell2 != null) {
                        int coteAdjacent = Cellule.getCoteAdjacent(Cellule.DROITE);
                        cell2.setCote(coteAdjacent, valeur);
                        System.out.println("cell2 :" + cell2.getCote(coteAdjacent));
                    }
                    break;

                default: // rien
                    break;
            }
            //updateAffichage();
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * 
     * @param args les arguments du menu
     * @return le GridMenu du puzzle generator
     * @param <T> le type de l'argument
     */
    public <T> HBox getMenu(T... args) {
        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int i = 0; i < celluleNodes.length; i++) {
                    for (int j = 0; j < celluleNodes[i].length; j++) {
                        cellulesData[i][j].setValeur(celluleNodes[i][j].getLabel());
                    }
                }
                // File chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sauvegarder le puzzle");
                fileChooser.getExtensionFilters()
                        .add(new FileChooser.ExtensionFilter("Fichiers PUZZLE (*.puzzle)", "*.puzzle"));

                Stage stage = (Stage) sauvegarder.getScene().getWindow();
                java.io.File file = fileChooser.showSaveDialog(Main.getStage());

                if (file != null) {
                    PuzzleSauvegarde.sauvegarderPuzzleSauvegarde(puzzle, file.getAbsolutePath());
                }
            }
        });

        // handler bouton retour au menu principal
        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
     * Méthode qui initialise les données de l'affichage et le stockage du puzzle
     *
     * @param l la largeur du puzzle
     * @param L la longueur du puzzle
     */
    private void initCellules(int l, int L) {
        this.celluleNodes = new CelluleNode[l][L];
        this.cellulesData = new Cellule[l][L];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                this.cellulesData[i][j] = new Cellule(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
                this.celluleNodes[i][j] = new CelluleNode(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
            }
        }
    }

    /**
     * Méthode qui initialise un nouveau puzzle
     * 
     * @param path le chemin du fichier de sauvegarde du puzzle
     */
    public void initNewPuzzle(String path) {
        this.puzzle = PuzzleSauvegarde.chargerPuzzleSauvegarde(path);
        this.largeur = this.puzzle.getLargeur();
        this.longueur = this.puzzle.getLongueur();

        initCellules(this.largeur, this.longueur);
        this.cellulesData = this.puzzle.getGrilleSolution();

        for (int i = 0; i < this.cellulesData.length; i++) {
            for (int j = 0; j < this.cellulesData[i].length; j++) {
                this.celluleNodes[i][j].setLabel(this.cellulesData[i][j].getValeur());

                if (this.cellulesData[i][j].getValeur() != -1) {
                    this.celluleNodes[i][j].setLabeText(this.cellulesData[i][j].getValeur());
                }
            }
        }
    }

    /**
     * Méthode qui affiche le puzzle en fonction de si on veut créer un nouveau puzzle ou non
     * 
     * @param nouveau TODO
     */
    private void afficher(boolean nouveau) {
        switch (this.puzzle.getDifficulte()) {
            case FACILE ->
                this.infos.setText("Difficulté : Facile\nDimensions : " + this.largeur + " X " + this.longueur);
            case MOYEN ->
                this.infos.setText("Difficulté : Moyen\nDimensions : " + this.largeur + " X " + this.longueur);
            case DIFFICILE ->
                this.infos.setText("Difficulté : Difficile\nDimensions : " + this.largeur + " X " + this.longueur);
        }

        // Colonnes
        for (int i = 0; i < this.celluleNodes.length; i++) {
            // Lignes
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                if (nouveau)
                    this.celluleNodes[i][j] = new CelluleNode(-1, new ValeurCote[]{ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE, ValeurCote.VIDE});
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
                // Avoid horizontal bar duplication
                if (i == 0) {
                    this.celluleNodes[i][j].getButton(0).setGraphic(this.celluleNodes[i][j].getImage(0));
                    this.celluleNodes[i][j].getButton(0).setContentDisplay(ContentDisplay.CENTER);
                    this.gridPane.add(this.celluleNodes[i][j].getImage(0), j * 2 + 1, i * 2);   // top
                    this.gridPane.add(this.celluleNodes[i][j].getButton(0), j * 2 + 1, i * 2);   // top
                    this.celluleNodes[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.celluleNodes[i][j].getButton(1).setGraphic(this.celluleNodes[i][j].getImage(1));
                this.celluleNodes[i][j].getButton(1).setContentDisplay(ContentDisplay.CENTER);
                this.gridPane.add(this.celluleNodes[i][j].getCenterPane(), j * 2 + 1, i * 2 + 1);   // center
                this.gridPane.add(this.celluleNodes[i][j].getImage(1), j * 2 + 1, i * 2 + 2);   // bottom
                this.gridPane.add(this.celluleNodes[i][j].getButton(1), j * 2 + 1, i * 2 + 2);   // bottom
                this.celluleNodes[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;

                // Avoid vertical bar duplication
                if(j == 0){
                    this.celluleNodes[i][j].getButton(2).setGraphic(this.celluleNodes[i][j].getImage(2));
                    this.celluleNodes[i][j].getButton(2).setContentDisplay(ContentDisplay.CENTER);
                    this.gridPane.add(this.celluleNodes[i][j].getImage(2), j * 2, i * 2 + 1);   // left
                    this.gridPane.add(this.celluleNodes[i][j].getButton(2), j * 2, i * 2 + 1);   // left
                    this.celluleNodes[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.celluleNodes[i][j].getButton(3).setGraphic(this.celluleNodes[i][j].getImage(3));
                this.celluleNodes[i][j].getButton(3).setContentDisplay(ContentDisplay.CENTER);
                this.gridPane.add(this.celluleNodes[i][j].getImage(3), j * 2 + 2, i * 2 + 1);   // right
                this.gridPane.add(this.celluleNodes[i][j].getButton(3), j * 2 + 2, i * 2 + 1);   // right
                this.celluleNodes[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;
            }
            System.out.println(" ");
        }
        updateAffichage();
    }

    /**
     * Méthode statique qui règle l'animation d'entrée sur le bouton souhaité
     * 
     * @param fade le fadeTransition
     * @param button le bouton
     */
    private static void mouseEntered(FadeTransition fade, Button button) {
        fade.setRate(1);
        fade.play();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Méthode statique qui règle l'animation de sortie sur le bouton souhaité
     * 
     * @param fade le fadeTransition
     * @param button le bouton
     */
    private static void mouseExited(FadeTransition fade, Button button) {
        fade.setRate(-1);
        fade.play();
        fade.jumpTo(Duration.ZERO);
        button.setCursor(Cursor.DEFAULT);
    }
}
