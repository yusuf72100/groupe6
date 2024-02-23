package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

public class GridMenu implements Menu{
    private static Button sauvegarder;
    private static Button home;
    private static Label infos;
    private static VBox layout_v;
    private static GridPane gridPane;
    private static VBox container;
    private static CelluleNode[][] cellules;
    private static Cellule_Data[][] cellulesData;
    private static int compteur;        // utilisé à des fins de test
    private static Puzzle puzzle;
    private static Scene scene;
    private static int longueur;
    private static int largeur;

    GridMenu(int l, int L, PartieInfos.DifficultePuzzle diff){
        compteur = 0;
        infos = new Label();
        infos.setAlignment(Pos.CENTER);

        home = new Button();
        home.getStyleClass().add("button-home");
        home.setPrefSize(30, 30);
        sauvegarder = new Button();
        sauvegarder.getStyleClass().add("button-sauvegarder");
        sauvegarder.setPrefSize(30, 30);

        gridPane = new GridPane();
        container = new VBox(infos, gridPane);
        longueur = l;
        largeur = L;
        initCellules(longueur, largeur);
        puzzle = new Puzzle(new PartieInfos(null, null, 0, false, null, diff), longueur, largeur, cellulesData);
        scene = new Scene(MainMenu.getMenu(), 1000, 800);
    }

    /**
     * Gestion de chaque bouton (barre)
     */
    public static class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        private final int i;
        private final int j;

        public CelluleButtonEventHandler(int i, int j, Cellule_Data[][] data) {
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
            Cellule_Data.ValeurCote valeur = Cellule_Data.ValeurCote.VIDE;

            // toggle
            if (clickedButton.getStyleClass().contains("clicked")) {
                clickedButton.getStyleClass().remove("clicked");
            } else {
                clickedButton.getStyleClass().add("clicked");
                valeur = Cellule_Data.ValeurCote.TRAIT;
            }

            switch(clickedButton.getText()){
                case "Top" : cellulesData[i][j].setCote(0, valeur); break;
                case "Bottom" : cellulesData[i][j].setCote(1, valeur); break;
                case "Left" : cellulesData[i][j].setCote(2, valeur); break;
                case "Right" : cellulesData[i][j].setCote(3, valeur); break;

                default: // rien
                    break;
            }
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * @param args
     * @return
     * @param <T>
     */
    public static <T> HBox getMenu(T... args) {
        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                for (int i = 0; i < cellules.length; i++) {
                    for (int j = 0; j < cellules[i].length; j++) {
                        cellulesData[i][j].setValeur(cellules[i][j].getLabel());
                    }
                }
                // File chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sauvegarder le puzzle");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers BIN (*.bin)", "*.bin"));

                Stage stage = (Stage) sauvegarder.getScene().getWindow();
                java.io.File file = fileChooser.showSaveDialog(Main.getStage());

                if (file != null) {
                    Puzzle.sauvegarderPuzzle(puzzle, file.getAbsolutePath());
                }
            }
        });

        // handler bouton retour au menyu principal
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
        gridPane.getStyleClass().add("button-square");

        VBox buttonContainer = new VBox(home, sauvegarder);
        buttonContainer.setAlignment(Pos.TOP_LEFT);
        buttonContainer.setSpacing(10);
        buttonContainer.setStyle("-fx-background-color: #d0d0d0;");

        HBox globalContainer = new HBox(buttonContainer, container);
        HBox.setHgrow(container, Priority.ALWAYS);

        return globalContainer;

    }

    /**
     * Initialise les données de l'affichage et de stockage du puzzle
     * @param l
     * @param L
     */
    private static void initCellules(int l, int L) {
        cellules = null;
        cellulesData = null;
        cellules = new CelluleNode[l][L];
        cellulesData = new Cellule_Data[l][L];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                cellules[i][j] = new CelluleNode();
                cellulesData[i][j] = new Cellule_Data(-1, new Cellule_Data.ValeurCote[]{Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE});
            }
        }
    }

    /**
     * Initialise un nouveau puzzle
     * @param path
     */
    public static void initNewPuzzle(String path) {
        puzzle = Puzzle.chargerPuzzle(path);
        longueur = puzzle.getLongueur();
        largeur = puzzle.getLargeur();
        cellules = new CelluleNode[longueur][largeur];

        initCellules(longueur, largeur);
        cellulesData = null;
        cellulesData = puzzle.getCelluleData();

        for (int i  = 0 ; i < cellulesData.length; i++) {
            for (int j = 0; j < cellulesData[i].length; j++) {
                cellules[i][j].setLabel(cellulesData[i][j].getValeur());

                if(cellulesData[i][j].getValeur() != -1) { cellules[i][j].setLabeText(cellulesData[i][j].getValeur()); }

                for (int k = 0; k < 4; k++) {
                    switch (cellulesData[i][j].getCote(k)) {
                        case VIDE :
                            System.out.println("VIDE");
                            break ;
                        case TRAIT:
                            System.out.println("TRAIT");
                            cellules[i][j].getButton(k).getStyleClass().add("clicked");
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
    private static void afficher(boolean nouveau) {
        switch (puzzle.getInfoPartie().getDifficulte()) {
            case FACILE -> infos.setText("Difficulté : Facile\nDimensions : " + longueur + " X " + largeur);
            case MOYEN -> infos.setText("Difficulté : Moyen\nDimensions : " + longueur + " X " + largeur);
            case DIFFICILE -> infos.setText("Difficulté : Difficile\nDimensions : " + longueur + " X " + largeur);
        }

        // Colonnes
        for (int i = 0; i < cellules.length; i++) {
            // Lignes
            for (int j = 0; j < cellules[i].length; j++) {
                if(nouveau) cellules[i][j] = new CelluleNode();

                // Coins
                gridPane.add(cellules[i][j].getCoin(0), i * 2, j * 2);            // top left
                gridPane.add(cellules[i][j].getCoin(1), i * 2 + 2, j * 2);        // top right
                gridPane.add(cellules[i][j].getCoin(2), i * 2, j * 2 + 2);        // bottom left
                gridPane.add(cellules[i][j].getCoin(3), i * 2 + 2, j * 2 + 2);    // bottom right

                // Barres
                // Avoid horizontal bar duplication
                if (j == 0) {
                    gridPane.add(cellules[i][j].getButton(0), i * 2 + 1, 0);   // top
                    cellules[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j, cellulesData));
                    compteur++;
                }
                gridPane.add(cellules[i][j].getCenterPane(), i * 2 + 1, j * 2 + 1);   // center
                gridPane.add(cellules[i][j].getButton(1), i * 2 + 1, j * 2 + 2);   // bottom
                cellules[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j, cellulesData));
                compteur++;

                // Avoid vertical bar duplication
                if(i == 0){
                    gridPane.add(cellules[i][j].getButton(2), 0, j * 2 + 1);   // left
                    cellules[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j, cellulesData));
                    compteur++;
                }
                gridPane.add(cellules[i][j].getButton(3), i * 2 + 2, j * 2 + 1);   // right
                cellules[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j, cellulesData));
                compteur++;
            }
        }
    }
}
