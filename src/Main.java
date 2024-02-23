


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Nathan
 */

public class Main extends Application {



    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Création puzzle
        Cellule.ValeurCote[] cotes = {Cellule.ValeurCote.VIDE,Cellule.ValeurCote.VIDE,Cellule.ValeurCote.VIDE,Cellule.ValeurCote.VIDE};
        int lignes=5;
        int colonnes=5;
        Cellule[][] grille = new Cellule[lignes][colonnes];
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                grille[i][j]= new Cellule(i*j,cotes);
            }
        }
        Puzzle puzzle = new Puzzle("facile", 2, 2,grille);
        primaryStage.setTitle("Mon Application JavaFX");
        //Création bouton charger
        Button charger = new Button("Charger");
        charger.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Puzzle.chargerPuzzle("puzzle.ser");
            }
        });
        // Création d'un bouton avec le texte "Cliquez-moi !"
        Button sauvegarder = new Button("Sauvegarder");
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Puzzle.sauvegarderPuzzle(puzzle, "puzzle.ser" );
            }
        });
        // Création d'une disposition de type StackPane et ajout du bouton à celle-ci
        VBox layout_v=new VBox(2);
        layout_v.getChildren().addAll(charger,sauvegarder);
        

        // Création de la scène
        Scene scene = new Scene(layout_v, 300, 300);

        // Ajout de la scène à la scène principale (primaryStage)
        primaryStage.setScene(scene);

        // Affichage de la fenêtre
        primaryStage.show();
    }
}
