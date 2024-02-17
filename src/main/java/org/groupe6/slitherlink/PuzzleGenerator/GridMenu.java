package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GridMenu extends Application {
    private static Button sauvegarder;
    private static Button charger;
    private static VBox layout_v;
    private static GridPane gridPane;
    private static VBox container;
    static Cellule[][] cellules;
    static Cellule_Data[][] cellulesData;
    static int compteur;
    static Puzzle puzzle;
    static Scene scene;
    static int longueur;
    static int largeur;
    PartieInfos.DifficultePuzzle difficulte;

    GridMenu(int l, int L, PartieInfos.DifficultePuzzle diff){
        compteur = 0;
        sauvegarder = new Button("Sauvegarder");
        charger = new Button("Charger");
        layout_v = new VBox(2);
        gridPane = new GridPane();
        container = new VBox(layout_v, gridPane);
        longueur = l;
        largeur = L;
        difficulte = diff;
        initCellules(longueur, largeur);
        puzzle = new Puzzle(new PartieInfos(null, null, 0, false, null, diff), longueur, largeur, cellulesData);
        scene = new Scene(MainMenu.getMainMenu(), 1000, 800);
    }

    @Override
    public void start(Stage stage) throws Exception { }

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
                clickedButton.setStyle("-fx-background-color: transparent;");

            } else {
                clickedButton.getStyleClass().add("clicked");
                clickedButton.setStyle("-fx-background-color: lightgray;");
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
     * Renvoi le menu de la grille
     * @return VBox
     */
    public static VBox getGridMenu() {
        charger.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                puzzle = null;
                puzzle = Puzzle.chargerPuzzle("puzzle.bin");
                longueur = puzzle.getLongueur();
                largeur = puzzle.getLargeur();
                cellules = null;
                cellules = new Cellule[longueur][largeur];

                initCellules(longueur, largeur);
                cellulesData = null;
                cellulesData = puzzle.getCelluleData();

                for (int i  = 0 ; i < cellulesData.length; i++) {
                    for (int j = 0; j < cellulesData[i].length; j++) {
                        cellules[i][j].setLabel(cellulesData[i][j].getValeur());

                        if(cellulesData[i][j].getValeur() != -1) { cellules[i][j].setLabeText(cellulesData[i][j].getValeur()); }

                        for (int k = 0; k < 4; k++) {
                            switch (cellulesData[i][j].getCote(k)) {
                                case VIDE : cellules[i][j].getButton(k).getStyleClass().remove("clicked");
                                    System.out.println("VIDE");
                                    cellules[i][j].getButton(k).setStyle("-fx-background-color: transparent;");
                                    break ;
                                case TRAIT: cellules[i][j].getButton(k).getStyleClass().add("clicked");
                                    System.out.println("TRAIT");
                                    cellules[i][j].getButton(k).setStyle("-fx-background-color: lightgray;");
                                    System.out.println(cellules[i][j].getButton(k).getStyle());
                                    break ;
                                default: // rien
                                    break;
                            }
                        }
                    }
                }
                Platform.runLater(container::requestLayout);
                afficher(false);
            }
        });

        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                for (int i = 0; i < cellules.length; i++) {
                    for (int j = 0; j < cellules[i].length; j++) {
                        cellulesData[i][j].setValeur(cellules[i][j].getLabel());
                    }
                }
                Puzzle.sauvegarderPuzzle(puzzle, "puzzle.bin");
            }
        });

        layout_v.getChildren().addAll(charger,sauvegarder);

        afficher(true);

        System.out.println(compteur + " bars counted");
        gridPane.setAlignment(Pos.CENTER);
        layout_v.setAlignment(Pos.TOP_RIGHT);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().add("button-square");

        return container;
    }

    private static void initCellules(int l, int L) {
        cellules = null;
        cellulesData = null;
        cellules = new Cellule[l][L];
        cellulesData = new Cellule_Data[l][L];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                cellules[i][j] = new Cellule();
                cellulesData[i][j] = new Cellule_Data(-1, new Cellule_Data.ValeurCote[]{Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE, Cellule_Data.ValeurCote.VIDE});
            }
        }
    }

    private static void afficher(boolean nouveau) {
        // Colonnes
        for (int i = 0; i < cellules.length; i++) {
            // Lignes
            for (int j = 0; j < cellules[i].length; j++) {
                if(nouveau )
                    cellules[i][j] = new Cellule();

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
