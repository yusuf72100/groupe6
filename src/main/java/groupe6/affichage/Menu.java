package groupe6.affichage;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Interface qui correspond à un menu de l'application
 *
 * @author Yusuf
 */
public interface Menu {
    /**
     * Méthode qui permet d'afficher le menu qui lui-même est représenté en Node
     *
     * @param args les arguments du menu
     * @return le menu
     * @param <T> le type de l'argument
     */
    static <T extends Parent> Parent getMenu(T... args) {
        return new Parent() { };
    }

    /**
     * Méthode qui adapte la hauteur d'un node en fonction de la taille de l'écran
     *
     * @param target la hauteur cible
     * @param global la hauteur globale
     * @return la hauteur adaptée
     */
    static Double toPourcentHeight(Double target, Double global) {
        return (double) (target * global)/1080;
    }

    /**
     * Méthode qui adapte la longueur d'un node en fonction de la taille de l'écran
     *
     * @param target la longueur cible
     * @param global la longueur globale
     * @return la longueur adaptée
     */
    static Double toPourcentWidth(Double target, Double global) {
        return (double) (target * global)/1920;
    }

    /**
     * Méthode qui adapte la taille d'un texte en fonction de la taille de l'écran
     *
     * @param label le label à adapter
     * @param initialSize la taille initiale
     * @param windowWidth la largeur de la fenêtre
     * @param windowHeight la hauteur de la fenêtre
     */
    static void adaptTextSize(Label label, double initialSize, double windowWidth, double windowHeight) {
        double newSize = initialSize * Math.min(windowWidth / 1920, windowHeight / 1080);
        label.setStyle("-fx-font-size: " + newSize + "px;");
    }
}