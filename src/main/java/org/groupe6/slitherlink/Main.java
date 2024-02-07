package org.groupe6.slitherlink;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    // Event handler
    public static class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        private final int i;
        private final int j;

        public CelluleButtonEventHandler(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void handle(ActionEvent event) {
            System.out.println("Bouton cliqué à la position (" + i + ", " + j + ")");
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

    @Override
    public void start(Stage primaryStage) {
        Button sauvegarder = new Button("Sauvegarder");
        Button charger = new Button("Charger");
        VBox layout_v = new VBox(2);
        GridPane gridPane = new GridPane();
        Puzzle puzzle = new Puzzle(8, 8);
        int compteur = 0;

        charger.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Puzzle.chargerPuzzle("puzzle.ser");
            }
        });

        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Puzzle.sauvegarderPuzzle(puzzle, "puzzle.ser" );
            }
        });

        layout_v.getChildren().addAll(charger,sauvegarder);

        // Colonnes
        for (int i = 0; i < puzzle.cellules.length; i++) {
            // Lignes
            for (int j = 0; j < puzzle.cellules[i].length; j++) {
                puzzle.cellules[i][j] = new Cellule();

                // Coins
                gridPane.add(puzzle.cellules[i][j].coins[0], i * 2, j * 2);            // top left
                gridPane.add(puzzle.cellules[i][j].coins[1], i * 2 + 2, j * 2);        // top right
                gridPane.add(puzzle.cellules[i][j].coins[2], i * 2, j * 2 + 2);        // bottom left
                gridPane.add(puzzle.cellules[i][j].coins[3], i * 2 + 2, j * 2 + 2);    // bottom right

                // Barres
                // Avoid horizontal bar duplication
                if (j == 0) {
                    gridPane.add(puzzle.cellules[i][j].cellule[0], i * 2 + 1, j * 2);          // top
                    puzzle.cellules[i][j].cellule[0].setOnAction(new CelluleButtonEventHandler(i,j));
                    compteur++;
                }
                gridPane.add(puzzle.cellules[i][j].cellule[1], i * 2 + 1, j * 2 + 2);      // bottom
                puzzle.cellules[i][j].cellule[1].setOnAction(new CelluleButtonEventHandler(i,j));
                compteur++;

                // Avoid vertical bar duplication
                if(i == 0){
                    gridPane.add(puzzle.cellules[i][j].cellule[2], i * 2, j * 2 + 1);          // left
                    puzzle.cellules[i][j].cellule[2].setOnAction(new CelluleButtonEventHandler(i,j));
                    compteur++;
                }
                gridPane.add(puzzle.cellules[i][j].cellule[3], i * 2 + 2, j * 2 + 1);      // right
                puzzle.cellules[i][j].cellule[3].setOnAction(new CelluleButtonEventHandler(i,j));
                compteur++;
            }
        }

        System.out.println(compteur + " barres comptées");
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 1000, 800);
        gridPane.getStyleClass().add("button-square");
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Button Square");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
