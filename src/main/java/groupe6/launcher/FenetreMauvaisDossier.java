package groupe6.launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FenetreMauvaisDossier extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création d'un texte à afficher
        Text text = new Text(
                "Veillez à lancer l'application depuis" +
                    "\n"+
                    "    le dossier parent du .jar !"
        );

        // Création d'un conteneur StackPane pour centrer le texte
        StackPane root = new StackPane();
        root.getChildren().add(text); // Ajout du texte au conteneur

        // Création de la scène
        Scene scene = new Scene(root, 300, 250); // Taille de la fenêtre

        // Configuration de la scène sur la scène principale
        primaryStage.setScene(scene);

        // Définition du titre de la fenêtre
        primaryStage.setTitle("Slitherlink");

        // Affichage de la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}