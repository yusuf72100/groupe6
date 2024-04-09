package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe qui correspond à la méthode principale de l'application
 *
 * @author Yusuf
 */
public class Main extends Application {

    /**
     * La scène principale
     */
    private static Scene Main;

    /**
     * La gridMenu actuelle
     */
    private static GridMenu grid;

    /**
     * La scène principale
     */
    private static Stage primaryStage;

    /**
     * Couleur principale pour le CSS
     */
    public static String mainColorCSS = "#e0ac1e";

    /**
     * Couleur secondaire pour le CSS
     */
    public static String secondaryColorCSS = "#4688AE";

    /**
     * Métode qui démarre l'application
     *
     * @param primary la scène principale
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @Override
    public void start(Stage primary) throws IOException {
        try {
            primaryStage = primary;

            // Gestion de la scène
            Main = new Scene(
                MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()),
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()
            );

            // Gestion du style
            String cheminStyleCss = Launcher.normaliserChemin(Launcher.dossierAssets + "/style.css");
            Main.getStylesheets().add(Launcher.chargerFichierEnUrl(cheminStyleCss));

            // Gestion de la fenêtre
            primary.initStyle(StageStyle.DECORATED);
            primary.setScene(Main);
            primary.setTitle("SlitherLink");
            primary.setResizable(true);
            primary.setMaximized(true);
            primary.show();

            // Gestion de l'icône
            String cheminImgIcon = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/icon.png");
            primary.getIcons().add(Launcher.chargerImage(cheminImgIcon));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode statique pour afficher le menu de sélection du mode de jeu
     */
    public static void showGameModeMenu() {
        Main.setRoot(GameModeSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu principal
     */
    public static void showMainMenu() {
        Main.setRoot(MainMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de puzzle en mode classique
     */
    public static void showClassicModeMenu() {
        Main.setRoot(ClassicModeMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de puzzle en mode classique
     */
    public static void showContreLaMontreModeMenu() {
        Main.setRoot(ContreLaMontreModeMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de profil
     *
     * @param partie la partie à afficher
     */
    public static void showGridMenu(Partie partie) {
        // Crée un GridMenu avec la partie passé en paramètre
        grid = new GridMenu(partie, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        Main.setRoot(grid.getMenu(false, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu de sélection de sauvegarde
     */
    public static void showSaveSelectionMenu() {
        Main.setRoot(SaveSelectionMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher une pop-up d'information
     *
     * @param title le titre de la pop-up
     * @param headerTexte le texte de l'en-tête
     * @param contentTexte le texte du contenu
     */
    public static void afficherPopUpInformation(String title, String headerTexte, String contentTexte) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerTexte);
        alert.setContentText(contentTexte);

        alert.showAndWait();
    }

    /**
     * Méthode statique pour afficher une pop-up de choix oui/non
     *
     * @param title le titre de la pop-up
     * @param headerTexte le texte de l'en-tête
     * @param contentTexte le texte du contenu
     * @return vrai si l'utilisateur a choisi oui, faux sinon
     */
    public static boolean afficherPopUpChoixOuiNon(String title, String headerTexte, String contentTexte) {
        // Initialisation du résultat
        AtomicBoolean resultat = new AtomicBoolean(false);

        // Création de la fenêtre de dialogue
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerTexte);
        alert.setContentText(contentTexte);

        // Changement du bouton ok en bouton oui
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("Oui");

        // Changement du bouton cancel en bouton non
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("Non");

        // Récupération du résultat
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                resultat.set(true);
            } else {
                resultat.set(false);
            }
        });

        // Retourne le résultat
        return resultat.get();
    }

    /**
     * Méthode statique pour lancer une partie
     *
     * @param difficulte la difficulté du puzzle
     * @param numero le numéro du puzzle
     * @param modeJeu le mode de jeu de la partie
     */
    public static void lancerPartie(int difficulte, int numero, ModeJeu modeJeu) {
        DifficultePuzzle difficultePuzzle = DifficultePuzzle.values()[difficulte];
        Profil profilJoueur = Launcher.catalogueProfils.getProfilActuel();
        Partie partieClassique = Partie.nouvellePartie(Launcher.cataloguePuzzles, difficultePuzzle, numero, modeJeu, profilJoueur);
        showGridMenu(partieClassique);
    }

    /**
     * Méthode principale qui lance l'application javaFX
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch();
    }
}
