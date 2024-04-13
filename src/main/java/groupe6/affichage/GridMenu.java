package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.erreur.ResultatVerificationErreur;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Classe qui correspond a l'inteface graphique d'une partie joué de Slitherlink
 *
 * @author Yusuf
 */
public class GridMenu implements Menu {

    private final AnchorPane anchorPane;

    private final StackPane stackPane;

    /**
     * La partie en cours
     */
    private final Partie partie;

    /**
     * Le bouton pour sauvegarder la partie
     */
    private final Button sauvegarder;

    /**
     * Le bouton pour revenir au menu principal
     */
    private final Button home;

    /**
     * Le bouton pour mettre en pause la partie
     */
    private final Button pause;

    /**
     * Le bouton pour annuler une action
     */
    private final Button undo;

    /**
     * Le bouton pour rétablir une action
     */
    private final Button redo;

    /**
     * Le bouton pour vérifier les erreurs
     */
    private final Button check;

    /**
     * Le bouton pour activer le mode hypothèse
     */
    private final Button hypothese;

    /**
     * ScrollPane de la zone d'affichage d'aides
     */
    /**
     * Le bouton pour demander de l'aide
     */
    private final Button help;

    // TODO : a supprimer si pas utilisé
    /**
     * Le layout vertical
     */
    private HBox layout_v;

    /**
     * Le gridPane qui contient les cellules
     */
    private GridPane gridPane;

    /**
     * Le container qui contient le gridPane
     */
    private StackPane container;

    /**
     * L'historique des aides
     */
    private HistoriqueAidesArea historiqueAides;

    /**
     * Zone d'affichage de l'historique d'aides
     */
    private StackPane historiqueAidesStackPane;

    /**
     * La grille des nodes des cellules
     */
    private static CelluleNode[][] celluleNodes;
    /**
     * La grille des données des cellules
     */
    private Cellule[][] cellulesData;

    // TODO : supprimer après test
    /**
     * Le compteur de barres
     */
    private int compteur;

    /**
     * Le puzzle sur lequel on joue la partie
     */
    private Puzzle puzzle;

    // TODO : supprimer si pas utilisé
    /**
     * La scène de l'interface graphique
     */
    private Scene scene;

    // TODO
    /**
     * Le label du bouton hover
     */
    private static Label buttonHoverLabel;

    // TODO : mettre en final si pas modifié par la méthode initNewPuzzle(String)
    /**
     * La largeur du puzzle
     */
    private int largeur;

    // TODO : mettre en final si pas modifié par la méthode initNewPuzzle(String)
    /**
     * La longueur du puzzle
     */
    private int longueur;

    private final ChronoThread chronoThread;

    private final Thread thread;

    private Label chronoLabel;

    private final HBox hypotheseHbox;

    private final Label hypotheseLabel;

    /**
     * Constructeur de la classe GridMenu
     *
     * @param partie la partie à laquelle est liée l'interface graphique
     */
    public GridMenu(Partie partie, Double w, Double h){
        this.hypotheseHbox = new HBox();
        this.hypotheseLabel = new Label("Mode hypothèse actif");
        this.hypotheseHbox.setVisible(false);
        this.hypotheseLabel.getStyleClass().add("title_help");
        this.stackPane = new StackPane();
        this.anchorPane = new AnchorPane();
        this.compteur = 0;
        buttonHoverLabel = new Label();
        this.chronoLabel = new Label();
        partie.setObservateurGridMenu(this);

        // buttons
        this.home = initHeaderButton("button-home", "Retourner au menu", w, h);
        this.undo = initHeaderButton("button-undo", "Annuler l'action", w, h);
        this.redo = initHeaderButton("button-redo", "Rétablir l'action", w, h);
        this.sauvegarder = initHeaderButton("button-sauvegarder", "Sauvegarder", w, h);
        this.pause = initHeaderButton("button-pause", "Mettre en pause", w, h);
        this.check = initHeaderButton("button-check", "Vérifier", w, h);
        this.hypothese = initHeaderButton("button-hypothese", "Mode hypothèse", w, h);
        this.help = initHeaderButton("button-help", "Aide", w, h);

        this.partie = partie;
        this.gridPane = new GridPane();
        this.container = new StackPane(gridPane);
        this.longueur = partie.getPuzzle().getLongueur();
        this.largeur = partie.getPuzzle().getLargeur();
        initCellules(this.longueur, this.largeur, w, h);
        this.puzzle = partie.getPuzzle();
        updateAffichage();

        OptionsMenu.initMenu(w,h);
        OptionsMenu.setProfil(partie.getProfil());

        this.chronoThread = new ChronoThread(this.partie, this.chronoLabel, w, h);
        this.thread = new Thread(chronoThread);

        if ( Launcher.getVerbose() ) {
            System.out.println("Puzzle au lancement :\n"+this.puzzle);
        }
    }

    /**
     * Méthode qui highlight une cellule
     *
     * @param x la position x de la cellule
     * @param y la position y de la cellule
     */
    public static void highlightCellule(int y, int x, String buttonCss, String centerCss) {
        celluleNodes[y][x].changeCellulesCss(buttonCss, centerCss);
    }

    /**
     * Réinitialise l'affichage de la cellule
     * @param y la position x de la cellule
     * @param x la position y de la cellule
     */
    public static void resetCelluleCss(int y, int x) {
        celluleNodes[y][x].resetCellulesCss();
    }

    /**
     * Méthode qui met à jour l'affichage de la grille
     *
     * @param y la position y de la cellule
     * @param x la position x de la cellule
     */
    public void resetCellule(int y, int x) {
        celluleNodes[y][x].resetCellulesCss();
    }

    /**
     * Méthode qui verifie si des coordonnées sont dans la grille
     *
     * @param y la position y
     * @param x la position x
     * @return vrai si les coordonnées sont dans la grille, faux sinon
     */
    public boolean estDansGrille(int y, int x) {
        return y >= 0 && y < this.cellulesData.length && x >= 0 && x < this.cellulesData[0].length;
    }

    /**
     * Méthode qui change la couleurs des boutons ( traits ) des cellules voisines
     *
     * @param y la position y de la cellule
     * @param x la position x de la cellule
     */
    public void setCellulesAdjacentesCss(int y, int x, String css) {

        if ( estDansGrille(y, x-1) ) {
            if(this.celluleNodes[y][x-1] != null) this.celluleNodes[y][x-1].changeButtonCss(3, css);
        }

        if ( estDansGrille(y, x+1) ) {
            if(this.celluleNodes[y][x+1] != null) this.celluleNodes[y][x+1].changeButtonCss(2, css);
        }

        if ( estDansGrille(y-1, x) ) {
            if(this.celluleNodes[y-1][x] != null) this.celluleNodes[y-1][x].changeButtonCss(1, css);
        }

        if ( estDansGrille(y+1, x) ) {
            if(this.celluleNodes[y+1][x] != null) this.celluleNodes[y+1][x].changeButtonCss(0, css);
        }

    }

    /**
     * Méthode qui remet l'affichage à l'état précédent
     *
     * @param y la position y de la cellule
     * @param x la position x de la cellule
     */
    public void resetCellulesAdjacentesCss(int y, int x) {

        if ( estDansGrille(y, x-1) ) {
            if(this.celluleNodes[y][x-1] != null) this.celluleNodes[y][x-1].resetButtonCss(3);
        }

        if ( estDansGrille(y, x+1) ) {
            if(this.celluleNodes[y][x+1] != null) this.celluleNodes[y][x+1].resetButtonCss(2);
        }

        if ( estDansGrille(y-1, x) ) {
            if(this.celluleNodes[y-1][x] != null) this.celluleNodes[y-1][x].resetButtonCss(1);
        }

        if ( estDansGrille(y+1, x) ) {
            if(this.celluleNodes[y+1][x] != null) this.celluleNodes[y+1][x].resetButtonCss(0);
        }

    }

    /**
     * Change le texte de survol du bouton en question
     * @param button référence vers le bouton
     * @param hoverText texte survol
     */
    public static void changeHeaderButtonHoverText(Button button, String hoverText) {
        FadeTransition fadeButton = new FadeTransition(Duration.millis(150), button);
        fadeButton.setFromValue(1.0);
        fadeButton.setToValue(0.2);

        button.removeEventHandler(MouseEvent.MOUSE_ENTERED, button.getOnMouseEntered());
        button.setOnMouseEntered(event -> {
            if ( button.getStyleClass().contains("button-disabled") ) {
                button.setStyle("-fx-opacity: 1");
            } else {
                mouseEntered(fadeButton, button);
            }
            buttonHoverLabel.setText(hoverText);
        });
    }

    /**
     * Méthode qui permet d'ajouter une bouton parmis les boutons headers du menu de grille
     *
     * @param style le style du bouton
     * @param hoverText le texte à afficher lors du hover
     * @return renvoi un bouton initialisé
     */
    public static Button initHeaderButton(String style, String hoverText, Double w, Double h) {
        Button button = new Button();
        button.getStyleClass().add(style);
        button.setPrefSize(Menu.toPourcentWidth(50.0, w), Menu.toPourcentWidth(50.0, w));

        FadeTransition fadeButton = new FadeTransition(Duration.millis(150), button);
        fadeButton.setFromValue(1.0);
        fadeButton.setToValue(0.2);

        button.setOnMouseEntered(event -> {
            if ( button.getStyleClass().contains("button-disabled") ) {
                button.setStyle("-fx-opacity: 1");
            } else {
                mouseEntered(fadeButton, button);
            }
            buttonHoverLabel.setText(hoverText);
        });

        button.setOnMouseExited(event -> {
            if ( button.getStyleClass().contains("button-disabled") ) {
                button.setStyle("-fx-opacity: 0.5");
                button.setCursor(Cursor.DEFAULT);
            }
            else {
                mouseExited(fadeButton, button);
            }
            buttonHoverLabel.setText("");
        });

        button.setOnMouseMoved(event -> {
            buttonHoverLabel.setTranslateX(event.getSceneX() + 10);
            buttonHoverLabel.setTranslateY(event.getSceneY() + 15);
        });

        return button;
    }

    /**
     * Méthode qui permet d'obtenir la partie actuelle
     *
     * @return la partie actuelle
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * Classe interne pour gérer les événements des boutons des cellules
     */
    public class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        /**
         * La position en y de la cellule dans la grille
         */
        private final int i;

        /**
         * La position en x de la cellule dans la grille
         */
        private final int j;

        /**
         * Constructeur de la classe CelluleButtonEventHandler
         *
         * @param i la position en y de la cellule dans la grille
         * @param j la position en x de la cellule dans la grille
         * @param data la grille de cellules
         */
        public CelluleButtonEventHandler(int i, int j, Cellule[][] data) {
            this.i = i;
            this.j = j;
            cellulesData = data;
        }

        /**
         * Execute l'action demandée sur le bouton
         *
         * @param event l'événement qui a déclenché l'action
         */
        @Override
        public void handle(ActionEvent event) {

            Button clickedButton = (Button) event.getSource();
            Partie partie = GridMenu.this.getPartie();

            int cote;
            switch ( clickedButton.getText() ) {
                case "Top" :
                    cote = Cellule.HAUT;
                    break;
                case "Bottom" :
                    cote = Cellule.BAS;
                    break;
                case "Left" :
                    cote = Cellule.GAUCHE;
                    break;
                case "Right" :
                    cote = Cellule.DROITE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + clickedButton.getText());
            }

            ValeurCote valeurCote = partie.getPuzzle().getCellule(i, j).getCote(cote);
            partie.actionBasculeTroisEtat(i,j,cote);

            updateAffichage();
            //updateNode(i, j);
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     *
     * @param isNew indique s'il faut créer une nouvelle grille
     * @param w largeur de la fenêtre
     * @param h hauteur de la fenêtre
     * @return T le menu à afficher
     */
    public <T> StackPane getMenu(boolean isNew, Double w, Double h) {
        PauseMenu.initMenu(w,h);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), hypotheseHbox);
        this.historiqueAides = new HistoriqueAidesArea(this,w, h);
        this.historiqueAidesStackPane = this.historiqueAides.getHistoriqueAidesStackPane();

        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (Launcher.getVerbose()) {
                    System.out.println("Sauvegarde de la partie en cours");
                }
                Partie partie = GridMenu.this.getPartie();
                partie.sauvegarder();
                Main.resetGrid();
            }
        });

        // Handler bouton Undo
        undo.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if ( Launcher.getVerbose() ) {
                    System.out.println("Undo");
                }

                if ( !partie.getGestionnaireAction().debutListe() ) {
                    partie.undo();
                    if ( partie.getGestionnaireAction().debutListe()) {
                        GridMenu.this.undo.getStyleClass().add("button-disabled");
                    }
                }

                updateAffichage();
            }
        });

        // Handler Redo
        redo.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if ( Launcher.getVerbose() ) {
                    System.out.println("Redo");
                }

                if ( !partie.getGestionnaireAction().finListe() ) {
                    partie.redo();
                    if ( partie.getGestionnaireAction().finListe()) {
                        GridMenu.this.redo.getStyleClass().add("button-disabled");
                    }
                }

                updateAffichage();
            }
        });

        // Handler Help
        help.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){

                // Désactive la fonctionnalité si le mode hypothèse est actif
                if ( partie.modeHypotheseActif() ) {
                    Main.afficherPopUpModeHypotheseActif();
                    return;
                }

                AideInfos aide = partie.chercherAide();
                // Détecte si une aide a été trouvée
                if ( aide != null ) {
                    historiqueAides.ajouterNouvelleAide(aide);
                }
                else {
                    Main.afficherPopUpInformation(
                        "Aide à la progression",
                        "Aucune aide trouvée. " +
                            "\n\n" +
                            "Vous ne serez pas pénalisé pour cette recherche d'aide.",
                        "Appuyez sur OK pour continuer"
                    );
                }
                updateAffichage();
            }
        });

        // Handler Check
        check.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){

                // Désactive la fonctionnalité si le mode hypothèse est actif
                if ( partie.modeHypotheseActif() ) {
                    Main.afficherPopUpModeHypotheseActif();
                    return;
                }

                ResultatVerificationErreur resultat = partie.verifierErreur();
                if ( Launcher.getVerbose() ) {
                    System.out.println("Résultat de la vérification : \n"+ resultat.toString());
                }

                if ( resultat.isErreurTrouvee() ) {
                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        highlightCellule(coords.getY(), coords.getX(), "highlight-red", "bg_custom-red");
                        setCellulesAdjacentesCss(coords.getY(), coords.getX(), "highlight-red");
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        highlightCellule(coords.getY(), coords.getX(),"highlight-orange", "bg_custom-orange" );
                        setCellulesAdjacentesCss(coords.getY(), coords.getX(), "highlight-orange");
                    }

                    // Affiche une pop up pour demander si l'utilisateur accepte la correction
                    String titlePopUp = "Correction des erreurs";
                    String headerPopUp = "Les cellules en rouge et orange seront modifiées si vous acceptez la correction!";
                    String contentPopUp = "Acceptez de revenir sur la première erreur trouvée?";
                    boolean accepteCorrection = Main.afficherPopUpChoixOuiNon(titlePopUp,headerPopUp,contentPopUp);
                    if ( accepteCorrection ) {
                        partie.corrigerErreur();
                    }

                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        // Enlève la couleur rouge sur les cellules aux coordonnées coords
                        celluleNodes[coords.getY()][coords.getX()].resetCellulesCss();
                        resetCellulesAdjacentesCss(coords.getY(), coords.getX());
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        // Enlève la couleur orange sur les cellules aux coordonnées coords
                        celluleNodes[coords.getY()][coords.getX()].resetCellulesCss();
                        resetCellulesAdjacentesCss(coords.getY(), coords.getX());
                    }
                }
                else {
                    Main.afficherPopUpInformation(
                        "Vérification des erreurs",
                        "Aucune erreur trouvée. " +
                            "\n\n" +
                            "Vous ne serez pas pénalisé pour cette vérification.",
                        "Appuyez sur OK pour continuer"
                    );
                }
                updateAffichage();
            }
        });

        // Handler Hypothèse
        hypothese.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if ( !partie.modeHypotheseActif() ) {
                    partie.activerHypothese();

                    hypotheseHbox.setVisible(true);
                    translateTransition.setByY(hypotheseHbox.getMaxHeight() + Menu.toPourcentHeight(10.0, h));
                    translateTransition.play();

                    if ( Launcher.getVerbose() ) {
                        System.out.println("Activation du mode hypothèse");
                    }
                    Main.afficherPopUpInformation(
                        "Mode hypothèse",
                        "Le mode hypothèse est activé. " +
                            "\n\n" +
                            "Vous prochaines actions seront enregistrées en tant qu'hypothèses.",
                        "Appuyez sur OK pour continuer"
                    );
                    // TODO : Modifier couleurs action hypothese ( trait, barre, etc ), ou utiliser un autre moyen pour indiquer le mode hypothèse
                }
                else {
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Désactivation du mode hypothèse");
                    }

                    translateTransition.setByY(-(hypotheseHbox.getMaxHeight() + Menu.toPourcentHeight(10.0, h)));
                    translateTransition.play();

                    boolean validerHypothese = Main.afficherPopUpChoixOuiNon(
                        "Validation de l'hypothèse",
                        "Le mode hypothèse va être désactivé.",
                        "Voulez-vous valider l'hypothèse ?" +
                            "\n " +
                            "( Les actions effectuées en mode hypothèse seront validées )"
                    );
                    if ( validerHypothese ) {
                        partie.validerHypothese();
                        System.out.println("Validation de l'hypothèse");
                    }
                    else {
                        partie.annulerHypothese();
                        System.out.println("Annulation de l'hypothèse");
                    }

                    // TODO : Remettre le style par defaut pour signaler la fin du mode hypothèse
                }
            }
        });

        // Handler Pause
        pause.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if(!PauseMenu.getMenu().isVisible()) {
                    PauseMenu.showMenu();
                } else {
                    PauseMenu.hideMenu();
                }
            }
        });

        // handler bouton retour au menu principal
        home.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Main.showMainMenu();
            }
        });

        // handler visibilité pause menu
        PauseMenu.getMenu().visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                partie.getChrono().start();
            } else {
                partie.getChrono().stop();
            }
        });

        afficher(isNew);

        gridPane.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().addAll("button-square");

        HBox buttonContainer = new HBox(this.home, this.sauvegarder, this.pause, this.undo, this.redo, this.hypothese, this.check, this.help);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        buttonContainer.setSpacing(10);
        buttonContainer.setStyle("-fx-background-color: #e0ac1e; -fx-background-radius: 10; -fx-padding: 5 20 5 20;");

        buttonContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            buttonContainer.setTranslateX((w/2) - (newVal.doubleValue()/2));
        });

        buttonContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            buttonContainer.setTranslateY(Menu.toPourcentHeight(50.0, 500.0));
        });

        Rectangle separator = new Rectangle(5, this.help.getPrefHeight());
        separator.setFill(Color.BLACK);

        this.chronoLabel.setStyle("-fx-font-family: 'Inter'");
        Menu.adaptTextSize(this.chronoLabel,35.0, w, h);
        buttonContainer.getChildren().addAll(separator, this.chronoLabel);

        this.partie.getChrono().start();
        this.thread.start();

        anchorPane.getChildren().addAll(this.container, buttonContainer, this.historiqueAidesStackPane, buttonHoverLabel);
        AnchorPane.setTopAnchor(container, buttonContainer.getPrefHeight());
        AnchorPane.setLeftAnchor(container, (anchorPane.getPrefWidth() - container.getPrefWidth()) / 2.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);

        stackPane.getChildren().addAll(this.hypotheseHbox, anchorPane, PauseMenu.getMenu());
        StackPane.setAlignment(anchorPane, Pos.TOP_CENTER);
        StackPane.setAlignment(this.hypotheseHbox, Pos.TOP_CENTER);

        // config des touches
        EventHandler<KeyEvent> keyEventHandler = event -> {
            KeyCode keyCode = event.getCode();

            if (keyCode == KeyCode.ESCAPE) {
                if(!PauseMenu.getMenu().isVisible()) {
                    PauseMenu.showMenu();
                } else if (!OptionsMenu.getMenu().isVisible() && PauseMenu.getMenu().isVisible()){
                    PauseMenu.hideMenu();
                } else {
                    OptionsMenu.hideMenu();
                }
            }
        };

        stackPane.setOnKeyPressed(keyEventHandler);

        Menu.adaptTextSize(this.hypotheseLabel, 15.0, w, h);
        this.hypotheseHbox.getChildren().add(this.hypotheseLabel);
        this.hypotheseHbox.setAlignment(Pos.CENTER);
        this.hypotheseHbox.setStyle("-fx-background-color: #e0ac1e; -fx-background-radius: 10; -fx-padding: 5 20 5 20;");
        this.hypotheseHbox.setMaxHeight(help.getPrefHeight());
        this.hypotheseHbox.setMaxWidth(Menu.toPourcentWidth(200.0, w));
        this.hypotheseHbox.setTranslateY(buttonContainer.getTranslateY() + Menu.toPourcentHeight(30.0, h));
        this.hypotheseLabel.setAlignment(Pos.CENTER);

        return stackPane;
    }

    /**
     * Arrête le chrono et enregistre la partie
     */
    public void saveGame() {
        this.partie.getChrono().stop();
        this.chronoThread.stopThread();
        this.partie.sauvegarder();
    }

    /**
     * Initialise les données de l'affichage et le stockage du puzzle
     *
     * @param l la largeur du puzzle
     * @param L la longueur du puzzle
     */
    private void initCellules(int l, int L, Double w, Double h) {
        this.celluleNodes = new CelluleNode[l][L];
        this.cellulesData = this.partie.getPuzzle().getGrilleJeu();

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                this.celluleNodes[i][j] = new CelluleNode(this.cellulesData[i][j].getValeur(), this.cellulesData[i][j].getCotes());
                this.celluleNodes[i][j].setPrefSize(Menu.toPourcentWidth(500.0 / this.longueur, w), Menu.toPourcentWidth(500.0 / this.longueur, w));
            }
        }
    }

    /**
     * Affiche le puzzle en fonction de si on veut créer un nouveau puzzle ou non
     *
     * @param nouveau si on veut créer un nouveau puzzle
     */
    private void afficher(boolean nouveau) {
        // Colonnes
        for (int i = 0; i < this.celluleNodes.length; i++) {
            // Lignes
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                // Coins
                this.gridPane.add(this.celluleNodes[i][j].getCoin(0), j * 2, i * 2);            // top left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(1), j * 2 + 2, i * 2);        // top right
                this.gridPane.add(this.celluleNodes[i][j].getCoin(2), j * 2, i * 2 + 2);        // bottom left
                this.gridPane.add(this.celluleNodes[i][j].getCoin(3), j * 2 + 2, i * 2 + 2);    // bottom right

                // animation fade
                for (int boutonIndex = 0; boutonIndex < 4; boutonIndex++) {
                    Button button = this.celluleNodes[i][j].getButton(boutonIndex);
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), button);

                    button.setOnMouseEntered(event -> {
                        if(button.getStyleClass().contains("clicked")) {
                            fadeTransition.setFromValue(1.0);
                            fadeTransition.setToValue(0.2);
                        } else {
                            fadeTransition.setFromValue(0.2);
                            fadeTransition.setToValue(1.0);
                        }
                        mouseEntered(fadeTransition, button);
                    });

                    button.setOnMouseExited(event -> {
                        mouseExited(fadeTransition, button);
                    });
                }

                // Barres
                // Avoid horizontal bar duplication
                if (i == 0) {
                    this.celluleNodes[i][j].getButton(0).setGraphic(this.celluleNodes[i][j].getImage(0));
                    this.celluleNodes[i][j].getButton(0).setContentDisplay(ContentDisplay.CENTER);
                    this.gridPane.add(this.celluleNodes[i][j].getImage(0), j * 2 + 1, i * 2);   // top
                    this.gridPane.add(this.celluleNodes[i][j].getButton(0), j * 2 + 1, i * 2);   // top
                    this.celluleNodes[i][j].getButton(0).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.celluleNodes[i][j].getButton(1).setGraphic(this.celluleNodes[i][j].getImage(1));
                this.celluleNodes[i][j].getButton(1).setContentDisplay(ContentDisplay.CENTER);
                this.gridPane.add(this.celluleNodes[i][j].getCenterPane(), j * 2 + 1, i * 2 + 1);   // center
                this.gridPane.add(this.celluleNodes[i][j].getImage(1), j * 2 + 1, i * 2 + 2);   // bottom
                this.gridPane.add(this.celluleNodes[i][j].getButton(1), j * 2 + 1, i * 2 + 2);   // bottom
                this.celluleNodes[i][j].getButton(1).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;

                // Avoid vertical bar duplication
                if(j == 0){
                    this.celluleNodes[i][j].getButton(2).setGraphic(this.celluleNodes[i][j].getImage(2));
                    this.celluleNodes[i][j].getButton(2).setContentDisplay(ContentDisplay.CENTER);
                    this.gridPane.add(this.celluleNodes[i][j].getImage(2), j * 2, i * 2 + 1);   // left
                    this.gridPane.add(this.celluleNodes[i][j].getButton(2), j * 2, i * 2 + 1);   // left
                    this.celluleNodes[i][j].getButton(2).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                    this.compteur++;
                }
                this.celluleNodes[i][j].getButton(3).setGraphic(this.celluleNodes[i][j].getImage(3));
                this.celluleNodes[i][j].getButton(3).setContentDisplay(ContentDisplay.CENTER);
                this.gridPane.add(this.celluleNodes[i][j].getImage(3), j * 2 + 2, i * 2 + 1);   // right
                this.gridPane.add(this.celluleNodes[i][j].getButton(3), j * 2 + 2, i * 2 + 1);   // right
                this.celluleNodes[i][j].getButton(3).setOnAction(new CelluleButtonEventHandler(i,j, this.cellulesData));
                this.compteur++;
            }
        }
    }

    /**
     * Met à jour l'affichage du puzzle en fonction du modèle
     */
    private void updateNode(int y, int x) {
        if ( Launcher.getVerbose() ) {
            System.out.printf(this.partie.getPuzzle().toString());
        }

        // Update des cellules
        this.celluleNodes[y][x].updateCotes(cellulesData[y][x].getCotes());

        // Update btn undo
        if ( this.partie.getGestionnaireAction().debutListe() ) {
            this.undo.getStyleClass().add("button-disabled");
        } else {
            this.undo.getStyleClass().remove("button-disabled");
            this.undo.setStyle("-fx-opacity: 1");
        }

        // Update btn redo
        if ( this.partie.getGestionnaireAction().finListe() ) {
            this.redo.getStyleClass().add("button-disabled");
        }
        else {
            this.redo.getStyleClass().remove("button-disabled");
            this.redo.setStyle("-fx-opacity: 1");
        }
    }

    /**
     * Met à jour les référencement vers le puzzle et les cellules
     */
    public void updatePuzzle() {
        this.puzzle = this.partie.getPuzzle();
        this.cellulesData = this.partie.getPuzzle().getGrilleJeu();
        // Update des cellules
        for ( int y = 0; y < this.largeur; y++ ) {
            for (int x = 0; x < this.longueur; x++) {
                this.celluleNodes[y][x].updateCotes(cellulesData[y][x].getCotes());
            }
        }
    }

    /**
     * Met à jour l'affichage du puzzle en fonction du modèle
     */
    public void updateAffichage() {
        if ( Launcher.getVerbose() ) {
            System.out.println("Update de l'affichage");
        }

        // Update des cellules
        for ( int y = 0; y < this.largeur; y++ ) {
            for (int x = 0; x < this.longueur; x++) {
                this.celluleNodes[y][x].updateCotes(cellulesData[y][x].getCotes());
            }
        }

        // Update btn undo
        if ( this.partie.getGestionnaireAction().debutListe() ) {
            this.undo.getStyleClass().add("button-disabled");
        } else {
            this.undo.getStyleClass().remove("button-disabled");
            this.undo.setStyle("-fx-opacity: 1");
        }

        // Update btn redo
        if ( this.partie.getGestionnaireAction().finListe() ) {
            this.redo.getStyleClass().add("button-disabled");
        }
        else {
            this.redo.getStyleClass().remove("button-disabled");
            this.redo.setStyle("-fx-opacity: 1");
        }
    }

    /**
     * Règle l'animation d'entrée sur le bouton souhaité
     *
     * @param fade l'animation de fade
     * @param button le bouton sur lequel l'animation doit être appliquée
     */
    private static void mouseEntered(FadeTransition fade, Button button) {
        fade.setRate(1);
        fade.play();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Règle l'animation de sortie sur le bouton souhaité
     * @param fade le fade
     * @param button le bouton
     */
    private static void mouseExited(FadeTransition fade, Button button) {
        fade.setRate(-1);
        fade.play();
        fade.jumpTo(Duration.ZERO);
        button.setCursor(Cursor.DEFAULT);
    }
}
