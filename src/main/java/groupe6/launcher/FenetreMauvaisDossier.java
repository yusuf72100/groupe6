package groupe6.launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Fenêtre affichée si l'application est lancée depuis un mauvais dossier
 *
 * @author Yamis
 */
public class FenetreMauvaisDossier extends Application {

    /**
     * Méthode start de l'application JavaFX
     *
     * @param primaryStage La scène principale de l'application JavaFX
     */
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

    /**
     * Méthode main de l'application qui lance la fenêtre de mauvais dossier
     *
     * @param args Les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}