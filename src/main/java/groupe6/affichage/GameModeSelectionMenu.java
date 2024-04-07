package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.profil.Profil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 * Classe qui correspond au menu de sélection du mode de jeu
 *
 * @author Yusuf
 */
public class GameModeSelectionMenu extends MainMenu {
    /**
     * Récupère le menu de sélection du mode de jeu sous forme de Node pour l'affichage
     *
     * @param windowWidth la largeur de la fenêtre
     * @param windowHeigth la hauteur de la fenêtre
     * @return le menu de sélection du mode de jeu
     */
    public static StackPane getMenu(Double windowWidth, Double windowHeigth) {
        MainMenu.initMenu();
        MainMenu.getMenu(windowWidth, windowHeigth);
        MainMenu.buttonTextsLabels = new String[]{"CLASSIQUE", "AVENTURE", "CONTRE\nLA\nMONTRE"};
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

        return MainMenu.mainPane;
    }
}
