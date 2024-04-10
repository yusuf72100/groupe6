package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import groupe6.model.technique.DifficulteTechnique;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
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

    public static void afficherPopUpInfoTechnique(
        String nomSyliseTechnique, String techniqueName, DifficulteTechnique difficulteTechnique,
        double width, double height
    ) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information sur la technique");
        alert.setContentText("Appuyez sur OK pour continuer");

        // Ajoute les images
        HBox imagesTechniqueHBox = new HBox();
        int nbImage = 2;
        if ( difficulteTechnique == DifficulteTechnique.AVANCEE ) {
            nbImage = 3;
        }
        for (int i = 1; i <= nbImage; i++) {
            String cheminImgTechnique = Launcher.normaliserChemin(Launcher.dossierTechniques + "/" + techniqueName + "_" + i + ".png");
            ImageView imgTechnique = new ImageView(Launcher.chargerImage(cheminImgTechnique));
            imgTechnique.setFitWidth( (0.4 * width) / nbImage );
            imgTechnique.setPreserveRatio(true);
            imagesTechniqueHBox.getChildren().add(imgTechnique);
        }
        imagesTechniqueHBox.setSpacing(50);

        // Récupération de la description de la technique
        String cheminTxtTechnique = Launcher.normaliserChemin(Launcher.dossierTechniques + "/" + techniqueName + ".desc");
        File fichierDescriptionTechnique = new File(cheminTxtTechnique);
        System.out.println(cheminTxtTechnique);
        String descriptionTechnique = "";
        if ( !fichierDescriptionTechnique.exists()) {
            throw new IllegalArgumentException("Le fichier " + cheminTxtTechnique + " n'existe pas");
        }
        try {
            Scanner scanner = new Scanner(fichierDescriptionTechnique);
            while (scanner.hasNextLine()) {
                descriptionTechnique += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Le label qui montre le nom de la technique
        Label labelNomTech = new Label(nomSyliseTechnique);
        labelNomTech.setWrapText(true);
        Menu.adaptTextSize(labelNomTech, 28, width, height);
        labelNomTech.setAlignment(Pos.CENTER);

        // Le label qui contient la description de la technique
        Label textDescriptionTechnique = new Label(descriptionTechnique);
        textDescriptionTechnique.setMinWidth(0.4 * width);
        textDescriptionTechnique.setMaxWidth(0.4 * width);
        textDescriptionTechnique.setWrapText(true);
        Menu.adaptTextSize(textDescriptionTechnique, 22, width, height);

        // HBox pour centrer le nom de la technique
        HBox centeredTechName = new HBox();
        centeredTechName.getChildren().add(labelNomTech);
        centeredTechName.setAlignment(Pos.CENTER);


        // Vbox qui contient les informations sur la technique
        VBox infoTechniqueVBox = new VBox();
        infoTechniqueVBox.getChildren().addAll(
            centeredTechName,
            new Label("\n"),
            imagesTechniqueHBox,
            new Label("\n"),
            textDescriptionTechnique,
            new Label("\n")

        );
        infoTechniqueVBox.setAlignment(Pos.CENTER);

        // HBox centrée horizontalement le contenu
        HBox explicationTechniqueHBox = new HBox();
        explicationTechniqueHBox.getChildren().addAll(
            infoTechniqueVBox
        );
        explicationTechniqueHBox.setAlignment(Pos.CENTER);

        // VBox pour centrer verticalement le contenu
        VBox ContenuVBox = new VBox();
        ContenuVBox.getChildren().addAll(
            explicationTechniqueHBox
        );
        ContenuVBox.setAlignment(Pos.CENTER);


        // VBox principale pour mettre en place le contenu et le séparateur
        VBox mainVBox = new VBox();
        mainVBox.getChildren().addAll(
            new Label("\n"),
            ContenuVBox,
            new Label("\n"),
            new Separator()
        );


        // Gestion du dialogPane
        alert.getDialogPane().setHeader(mainVBox);
        alert.getDialogPane().setMinWidth(0.5 * width);
        alert.getDialogPane().setMinHeight(0.5 * height);


        alert.showAndWait();
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
