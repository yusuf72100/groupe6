package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.*;
import groupe6.model.Menu;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.List;

public class SaveSelectionMenu implements Menu {
    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        MainMenu.initMenu();
        MainMenu.getMenu(windowWidth, windowHeigth);
        MainMenu.buttonTextsLabels = new String[]{"AUCUNE\nSAUVEGARDE", "AUCUNE\nSAUVEGARDE", "AUCUNE\nSAUVEGARDE"};
        MainMenu.backText.setText("RETOUR");

        for (int i = 0; i < MainMenu.buttons.length; i++) {
            int finalI = i;

            MainMenu.buttonsText[finalI].setText(MainMenu.buttonTextsLabels[finalI]);
            MainMenu.buttonsContainer[finalI].setOnMouseEntered(e -> {
                MainMenu.descriptionText[finalI].setTextAlignment(TextAlignment.CENTER);

                switch (finalI) {
                    case 0 : MainMenu.descriptionText[finalI].setText("Jeu classique"); break;
                    case 1 : MainMenu.descriptionText[finalI].setText("Mode Aventure avec\nplusieurs niveaux"); break;
                    case 2 : MainMenu.descriptionText[finalI].setText("Faire une grille\ndans un temps imparti"); break;
                    default : MainMenu.descriptionText[finalI].setText("Placeholder #" + finalI); break;
                }

                MainMenu.descriptionsBackground[finalI].setStyle("-fx-background-color: gray;");
                MainMenu.rectangleTransition[finalI].play();
                MainMenu.fadeTransition[finalI].play();
            });
        }

        MainMenu.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.showMainMenu();
            }
        });

        // Bouton menu classique
        MainMenu.buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DifficultePuzzle difficultePuzzle = DifficultePuzzle.FACILE;
                int numPuzzle = 0;
                Profil profilJoueur = Launcher.catalogueProfils.getProfilActuel();
                Partie partieClassique = Partie.nouvellePartie(Launcher.cataloguePuzzles, difficultePuzzle, numPuzzle, ModeJeu.NORMAL, profilJoueur);
                Main.showGridMenu(partieClassique);
            }
        });

        ScrollPane scollPane = new ScrollPane(MainMenu.mainHbox);
        MainMenu.mainPane.getChildren().add(scollPane);

        return MainMenu.mainPane;
    }
}
