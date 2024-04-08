package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.erreur.ResultatVerificationErreur;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.PuzzleSauvegarde;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.technique.ResultatTechnique;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Classe qui correspond a l'inteface graphique d'une partie joué de Slitherlink
 *
 * @author Yusuf
 */
public class GridMenu implements Menu {

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
     * La grille des nodes des cellules
     */
    private CelluleNode[][] celluleNodes;

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
    private Label buttonHoverLabel;

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

    // TODO : supprimer si pas utilisé
    /**
     * Le stage principal
     */
    private Stage primaryStage;

    /**
     * Constructeur de la classe GridMenu
     *
     * @param partie la partie auquel est lié l'inteface graphique
     * @param primaryStage le stage principal
     */
    public GridMenu(Partie partie, Stage primaryStage){
        this.primaryStage = primaryStage;
        this.compteur = 0;
        this.buttonHoverLabel = new Label();

        // buttons
        this.home = initHeaderButton("button-home", "Retourner au menu");
        this.undo = initHeaderButton("button-undo", "Annuler l'action");
        this.redo = initHeaderButton("button-redo", "Rétablir l'action");
        this.sauvegarder = initHeaderButton("button-sauvegarder", "Sauvegarder");
        this.pause = initHeaderButton("button-pause", "Mettre en pause");
        this.check = initHeaderButton("button-check", "Vérifier");
        this.hypothese = initHeaderButton("button-hypothese", "Mode hypothèse");
        this.help = initHeaderButton("button-help", "Aide");

        this.partie = partie;
        this.gridPane = new GridPane();
        this.container = new StackPane(gridPane);
        this.longueur = partie.getPuzzle().getLongueur();
        this.largeur = partie.getPuzzle().getLargeur();
        initCellules(this.longueur, this.largeur);
        this.puzzle = partie.getPuzzle();
        updateAffichage();

        if ( Launcher.getVerbose() ) {
            System.out.println("Puzzle au lancement :\n"+this.puzzle);
        }
    }

    /**
     * Méthode qui highlight une cellule
     *
     * @param x la position x de la cellule
     * @param y la position y de la cellule
     * @param color la couleur à appliquer ( format css )
     */
    public void highlightCellule(int y, int x, String color) {
        this.celluleNodes[y][x].changeCellulesCss(color);
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
     * @param color la couleur à appliquer ( format css )
     */
    private void setCellulesAdjacentesCss(int y, int x, String color) {

        if ( estDansGrille(y, x-1) ) {
            if(this.celluleNodes[y][x-1] != null) this.celluleNodes[y][x-1].changeButtonCss(3, color);
        }

        if ( estDansGrille(y, x+1) ) {
            if(this.celluleNodes[y][x+1] != null) this.celluleNodes[y][x+1].changeButtonCss(2, color);
        }

        if ( estDansGrille(y-1, x) ) {
            if(this.celluleNodes[y-1][x] != null) this.celluleNodes[y-1][x].changeButtonCss(1, color);
        }

        if ( estDansGrille(y+1, x) ) {
            if(this.celluleNodes[y+1][x] != null) this.celluleNodes[y+1][x].changeButtonCss(0, color);
        }

    }

    /**
     * Méthode qui remet l'affichage à l'état précédent
     *
     * @param y la position y de la cellule
     * @param x la position x de la cellule
     */
    private void resetCellulesAdjacentesCss(int y, int x) {

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
     * Méthode qui permet d'ajouter une bouton parmis les boutons headers du menu de grille
     *
     * @param style le style du bouton
     * @param hoverText le texte à afficher lors du hover
     * @return renvoi un bouton initialisé
     */
    private Button initHeaderButton(String style, String hoverText) {
        Button button = new Button();
        button.getStyleClass().add(style);
        button.setPrefSize(30, 30);

        FadeTransition fadeButton = new FadeTransition(Duration.millis(150), button);
        fadeButton.setFromValue(1.0);
        fadeButton.setToValue(0.2);

        button.setOnMouseEntered(event -> {
            if ( button.getStyleClass().contains("button-disabled") ) {
                button.setStyle("-fx-opacity: 1");
                this.buttonHoverLabel.setText(hoverText);
            }
            else {
                mouseEntered(fadeButton, button);
                this.buttonHoverLabel.setText(hoverText);
            }
        });
        
        button.setOnMouseExited(event -> {
            if ( button.getStyleClass().contains("button-disabled") ) {
                button.setStyle("-fx-opacity: 0.5");
                this.buttonHoverLabel.setText(hoverText);
            }
            else {
                mouseExited(fadeButton, button);
                this.buttonHoverLabel.setText("");
            }

        });

        button.setOnMouseMoved(event -> {
            this.buttonHoverLabel.setTranslateX(event.getSceneX() + 10);
            this.buttonHoverLabel.setTranslateY(event.getSceneY() + 15);
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

            if ( Launcher.getVerbose() ) {
                System.out.println("Bouton cliqué en (" + i + ", " + j + ")");
            }

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
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     *
     * @param args les arguments à passer à la méthode
     * @return T le menu à afficher
     * @param <T> le type de menu à afficher
     */
    public <T> AnchorPane getMenu(T... args) {
        // handler bouton de sauvegarde
        sauvegarder.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (Launcher.getVerbose()) {
                    System.out.println("Sauvegarde de la partie en cours");
                }
                Partie partie = GridMenu.this.getPartie();
                partie.sauvegarder();
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
                else {
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Début de la liste d'actions");
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
                else {
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Fin de la liste d'actions");
                    }
                }
                updateAffichage();
            }
        });

        // Handler Help
        help.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                ResultatTechnique result = partie.chercherAide();
                if (result.isTechniqueTrouvee() ) {
                    // TODO : Update l'affichage de l'historique d'aide
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

            }
        });

        // Handler Check
        check.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                ResultatVerificationErreur resultat = partie.verifierErreur();
                System.out.println("Résultat de la vérification : \n"+ resultat.toString());
                System.out.println(partie.getGestionnaireErreur());

                if ( resultat.isErreurTrouvee() ) {
                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        System.out.println("Première erreur : "+coords);
                        highlightCellule(coords.getY(), coords.getX(), "red");
                        setCellulesAdjacentesCss(coords.getY(), coords.getX(), "red");
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        System.out.println("Erreur suivante : "+coords);
                        highlightCellule(coords.getY(), coords.getX(), "orange");
                        setCellulesAdjacentesCss(coords.getY(), coords.getX(), "orange");
                    }

                    // Affiche une popup pour demander si l'utilisateur accepte la correction
                    String titlePopUp = "Correction des erreurs";
                    String headerPopUp = "Les cellules en rouge et orange seront modifiées si vous acceptez la correction!";
                    String contentPopUp = "Acceptez de revenir sur la première erreur trouvée?";
                    boolean accepteCorrection = Main.afficherPopUpChoixOuiNon(titlePopUp,headerPopUp,contentPopUp);
                    if ( accepteCorrection ) {
                        partie.corrigerErreur();
                    }

                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        // Enleve la couleurs rouge sur les cellules au coordonnées coords
                        celluleNodes[coords.getY()][coords.getX()].resetCellulesCss();
                        resetCellulesAdjacentesCss(coords.getY(), coords.getX());
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        // Enleve la couleurs orange sur les cellules au coordonnées coords
                        celluleNodes[coords.getY()][coords.getX()].resetCellulesCss();
                        resetCellulesAdjacentesCss(coords.getY(), coords.getX());
                    }

//                    updateAffichage();
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
            }
        });

        // Handler Hypothèse
        hypothese.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if ( partie.getHypothese() == null ) {
                    partie.activerHypothese();
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
                partie.pause();
                // TODO : Affiche le menu de pause ( unpause doit appeller partie.reprendre() )
            }
        });

        // handler bouton retour au menu principal
        home.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Main.showMainMenu();
            }
        });

        afficher((boolean) args[0]);

        if ( Launcher.getVerbose() ) {
            System.out.println("Compteurs de barres : "+compteur);
        }

        gridPane.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().addAll("button-square");

        HBox buttonContainer = new HBox(this.home, this.sauvegarder, this.pause, this.undo, this.redo, this.hypothese, this.check, this.help);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        buttonContainer.setSpacing(10);
        buttonContainer.setStyle("-fx-background-color: #d0d0d0;");

        AnchorPane anchorPane = new AnchorPane(container, buttonContainer, buttonHoverLabel);
        AnchorPane.setTopAnchor(container, buttonContainer.getPrefHeight());
        AnchorPane.setLeftAnchor(container, (anchorPane.getPrefWidth() - container.getPrefWidth()) / 2.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
        buttonContainer.prefWidthProperty().bind(anchorPane.widthProperty());

        return anchorPane;
    }

    /**
     * Initialise les données de l'affichage et le stockage du puzzle
     *
     * @param l la largeur du puzzle
     * @param L la longueur du puzzle
     */
    private void initCellules(int l, int L) {
        this.celluleNodes = new CelluleNode[l][L];
        this.cellulesData = this.partie.getPuzzle().getGrilleJeu();

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                this.celluleNodes[i][j] = new CelluleNode(this.cellulesData[i][j].getValeur(), this.cellulesData[i][j].getCotes());
                this.celluleNodes[i][j].setPrefSize((double) 500 / this.largeur, (double) 500 / this.longueur);
            }
        }
    }

    /**
     * Initialise un nouveau puzzle
     * @param path les arguments à passer à la méthode
     */
    public void initNewPuzzle(String path) {
        this.puzzle = new Puzzle(PuzzleSauvegarde.chargerPuzzleSauvegarde(path), false);
        this.longueur = this.puzzle.getLargeur();
        this.largeur = this.puzzle.getLongueur();

        initCellules(this.longueur, this.largeur);
        this.cellulesData = this.puzzle.getGrilleSolution();

        for (int i  = 0 ; i < this.cellulesData.length; i++) {
            for (int j = 0; j < this.cellulesData[i].length; j++) {
                this.celluleNodes[i][j].setLabel(this.cellulesData[i][j].getValeur());

                if(this.cellulesData[i][j].getValeur() != -1) {
                    this.celluleNodes[i][j].setLabeText(this.cellulesData[i][j].getValeur());
                }

                for (int k = 0; k < 4; k++) {
                    switch (this.cellulesData[i][j].getCote(k)) {
                        case VIDE :
                            break ;
                        case TRAIT:
                            this.celluleNodes[i][j].getButton(k).getStyleClass().add("clicked");
                            break ;
                        default: // rien
                            break;
                    }
                }
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
    private void updateAffichage() {
        if ( Launcher.getVerbose() ) {
            System.out.printf(this.partie.getPuzzle().toString());
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
