package groupe6.affichage;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public interface Menu {
    /**
     * Méthode qui permet d'afficher le menu qui lui-même est représenté en Node
     * @param args TODO
     * @return TODO
     * @param <T> TODO
     */
    static <T extends Parent> Parent getMenu(T... args) {
        return new Parent() { };
    }

    /**
     * Méthode complémentaire à but de test uniquement
     * @param stage TODO
     */
    static void showMenu(Stage stage) {};

    /**
     * Méthode qui adapte la hauteur d'un node en fonction de la taille de l'écran
     * @param target TODO
     * @param global TODO
     * @return TODO
     */
    static Double toPourcentHeight(Double target, Double global) {
        return (double) (target * global)/1080;
    }

    /**
     * Méthode qui adapte la longueur d'un node en fonction de la taille de l'écran
     * @param target TODO
     * @param global TODO
     * @return TODO
     */
    static Double toPourcentWidth(Double target, Double global) {
        return (double) (target * global)/1920;
    }

    /**
     * Méthode qui adapte la taille d'un texte en fonction de la taille de l'écran
     * @param label TODO
     * @param initialSize TODO
     * @param windowWidth TODO
     * @param windowHeight TODO
     */
    static void adaptTextSize(Label label, double initialSize, double windowWidth, double windowHeight) {
        double newSize = initialSize * Math.min(windowWidth / 1920, windowHeight / 1080);
        label.setStyle("-fx-font-size: " + newSize + "px;");
    }
}