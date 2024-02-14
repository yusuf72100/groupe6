package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class GridMenu extends Application {
    /**
     * Gestion de chaque bouton (barre)
     */
    public static class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        private final int i;
        private final int j;

        public CelluleButtonEventHandler(int i, int j) {
            this.i = i;
            this.j = j;
        }

        /**
         * Execute l'action demandée sur le bouton
         * @param event
         */
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Button clicked at (" + i + ", " + j + ")");
            Button clickedButton = (Button) event.getSource();

            // toggle
            if (clickedButton.getStyleClass().contains("clicked")) {
                clickedButton.getStyleClass().remove("clicked");
                clickedButton.setStyle("-fx-background-color: transparent;");
            } else {
                clickedButton.getStyleClass().add("clicked");
                clickedButton.setStyle("-fx-background-color: lightgray;");
            }
        }
    }

    /**
     * Programme principal jfx
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        Button sauvegarder = new Button("Sauvegarder");
        Button charger = new Button("Charger");

        VBox layout_v = new VBox(2);
        GridPane gridPane = new GridPane();
        VBox container = new VBox(layout_v, gridPane);

        Cellule[][] cellules = new Cellule[8][8];
        Scene scene = new Scene(MainMenu.getMainMenu(), 1000, 800);
        int compteur = 0;

        charger.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                //chargerPuzzle("ser");
            }
        });

        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                //Puzzle puzzle = new Puzzle(new Cellule_Data[][]);
                //sauvegarderPuzzle(puzzle, "ser" );
            }
        });

        layout_v.getChildren().addAll(charger,sauvegarder);

        // Colonnes
        for (int i = 0; i < cellules.length; i++) {
            // Lignes
            for (int j = 0; j < cellules[i].length; j++) {
                cellules[i][j] = new Cellule();

                // Coins
                gridPane.add(cellules[i][j].getCoin(0), i * 2, j * 2);            // top left
                gridPane.add(cellules[i][j].getCoin(1), i * 2 + 2, j * 2);        // top right
                gridPane.add(cellules[i][j].getCoin(2), i * 2, j * 2 + 2);        // bottom left
                gridPane.add(cellules[i][j].getCoin(3), i * 2 + 2, j * 2 + 2);    // bottom right

                // Barres
                // Avoid horizontal bar duplication
                if (j == 0) {
                    gridPane.add(cellules[i][j].getButton(0), i * 2 + 1, j * 2);   // top
                    cellules[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j));
                    compteur++;
                }
                gridPane.add(cellules[i][j].getCenterPane(), i * 2 + 1, j * 2 + 1);   // center
                gridPane.add(cellules[i][j].getButton(1), i * 2 + 1, j * 2 + 2);   // bottom
                cellules[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j));
                compteur++;

                // Avoid vertical bar duplication
                if(i == 0){
                    gridPane.add(cellules[i][j].getButton(2), i * 2, j * 2 + 1);   // left
                    cellules[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j));
                    compteur++;
                }
                gridPane.add(cellules[i][j].getButton(3), i * 2 + 2, j * 2 + 1);   // right
                cellules[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j));
                compteur++;

            }
        }

        System.out.println(compteur + " bars counted");
        gridPane.setAlignment(Pos.CENTER);
        layout_v.setAlignment(Pos.TOP_RIGHT);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().add("button-square");
    }
}
