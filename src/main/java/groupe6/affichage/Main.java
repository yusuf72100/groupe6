package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.Partie;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import groupe6.model.technique.DifficulteTechnique;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.time.Duration;
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
    public static String secondaryColorCSS = "#e0ac1e";

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

            primary.setOnCloseRequest(event -> {
                if(grid!=null) {
                    grid.saveGame();
                    resetGrid();
                }
                Platform.exit();
                // Attend la fin des sauvegardes avant de fermer l'application
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            });

            // Gestion de l'icône
            String cheminImgIcon = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/icon.png");
            primary.getIcons().add(Launcher.chargerImage(cheminImgIcon));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode statique pour reset le grid à null qu'an on à fini une partie
     */
    public static void resetGrid() {
        grid = null;
    }

    /**
     * Arrête les threads du grid menu
     */
    public static void exitAll() {
        if(grid!=null) {
            grid.saveGame();
            resetGrid();
        }
        Platform.exit();
        return;
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
        if(grid != null) {
            grid.saveGame();
        }
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
     * Méthode statique pour afficher le menu de sélection de puzzle en mode aventure
     */
    public static void showAdventureModeMenu() {
        Main.setRoot(AdventureMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
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
     * Méthode statique pour afficher le menu d'entraînement
     */
    public static void showGlossaireMenu() {
        Main.setRoot(GlossaireMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
    }

    /**
     * Méthode statique pour afficher le menu d'entraînement
     */
    public static void showProfileMenu() {
        Main.setRoot(ProfileMenu.getMenu(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
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
        // Création de la pop up
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information sur la technique");
        alert.setContentText("Appuyez sur OK pour continuer");

        // Ajoute les images de la technique
        HBox imagesTechniqueHBox = new HBox();
        int nbImage = 2;
        // Si la technique est avancée et que ce n'est pas la technique Advanced4, on affiche 3 images
        if ( difficulteTechnique == DifficulteTechnique.AVANCEE && !techniqueName.equals("Advanced4") ) {
            nbImage = 3;
        }
        for (int i = 1; i <= nbImage; i++) {
            String cheminImgTechnique = Launcher.normaliserChemin(
                Launcher.dossierTechniques + "/img/" + techniqueName + "_" + i + ".png"
            );
            ImageView imgTechnique = new ImageView(Launcher.chargerImage(cheminImgTechnique));
            imgTechnique.setFitWidth( (0.4 * width) / nbImage );
            imgTechnique.setPreserveRatio(true);
            imagesTechniqueHBox.getChildren().add(imgTechnique);
        }
        imagesTechniqueHBox.setSpacing(50);

        // Chemin du fichier de description de la technique
        String cheminTxtTechnique = Launcher.normaliserChemin(
            Launcher.dossierTechniques + "/description/" + techniqueName + ".desc"
        );

        // Récupération de la description de la technique à partir du fichier .desc
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cheminTxtTechnique), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String descriptionTechnique = contentBuilder.toString();

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
     * Méthode statique pour afficher une pop-up de fin de partie
     *
     * @param partie la partie qui vient d'être finie
     */
    public static void afficherPopUpFinPartie(Partie partie, double width, double height) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la partie");
        alert.setContentText("Appuyez sur OK pour revenir au menu principal");

        boolean partieGagnee = partie.getInfos().getGagnee();

        // Gestion du message de fin de partie

        String endingMessageTexte = "Bravo, vous avez terminé la partie !";
        if ( !partieGagnee ) {
            endingMessageTexte = "Dommage, vous avez dépassé le temps limite !";
        }

        // Le label qui contient le message de fin de partie
        Label endingMessage = new Label(endingMessageTexte);
        endingMessage.setWrapText(true);
        Menu.adaptTextSize(endingMessage, 28, width, height);

        endingMessage.setAlignment(Pos.CENTER);

        // Le label qui contient le score de la partie
        String scoreTexte = "Score : " + partie.getScore();
        Label scoreLabel = new Label(scoreTexte);
        Menu.adaptTextSize(scoreLabel, 26, width, height);
        scoreLabel.setAlignment(Pos.CENTER);

        // Vbox qui contient les informations de la partie
        VBox infoPartieVBox = new VBox();
        infoPartieVBox.getChildren().addAll(
            endingMessage,
            new Label("\n"),
            scoreLabel
        );

        // Gestion de l'affichage du temps de la partie
        if ( !partieGagnee ) {
            // Gestion de l'affichage du temps limite
            Duration limiteTemps = partie.getInfos().getLimiteTemps();
            String limiteTempsTexte;
            if ( limiteTemps.toHours() > 0 ) {
                limiteTempsTexte = limiteTemps.toHours() + "h " + limiteTemps.toMinutesPart() + "m et " + limiteTemps.toSecondsPart() + "s";
            } else {
                limiteTempsTexte = limiteTemps.toMinutesPart() + "m et " + limiteTemps.toSecondsPart() + "s";
            }
            Label labelLimiteTemps = new Label("Temps limite : " + limiteTempsTexte);
            Menu.adaptTextSize(labelLimiteTemps, 26, width, height);
            labelLimiteTemps.setAlignment(Pos.CENTER);
            infoPartieVBox.getChildren().add(labelLimiteTemps);
        }

        // Ajout saut de ligne pour la présentation
        infoPartieVBox.getChildren().add(new Label("\n"));

        infoPartieVBox.setAlignment(Pos.CENTER);

        // HBox centrée horizontalement le contenu
        HBox centerHBox = new HBox();
        centerHBox.getChildren().addAll(
            infoPartieVBox
        );
        centerHBox.setAlignment(Pos.CENTER);

        // VBox pour centrer verticalement le contenu
        VBox centerVBox = new VBox();
        centerVBox.getChildren().addAll(
            centerHBox
        );
        centerVBox.setAlignment(Pos.CENTER);


        // VBox principale pour mettre en place le contenu et le séparateur
        VBox mainVBox = new VBox();
        mainVBox.getChildren().addAll(
            new Label("\n"),
            centerVBox,
            new Label("\n"),
            new Separator()
        );

        // Gestion du dialogPane
        alert.getDialogPane().setHeader(mainVBox);
        alert.getDialogPane().setMinWidth(0.4 * width);

        // Affichage de la pop-up et attente de la réponse
        alert.showAndWait();

        // Retour au menu principal
        showMainMenu();
    }

    /**
     * Méthode statique pour afficher une pop-up qui indique que le mode hypothèse est actif
     *
     */
    public static void afficherPopUpModeHypotheseActif() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mode Hypothèse Actif");
        alert.setHeaderText(
            "Le mode hypothèse est actif !" +
            "\n\n" +
            "Vous ne pouvez accéder à cette fonctionnalité dans le mode hypothèse."
        );
        alert.setContentText("Appuyez sur OK pour continuer");

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
