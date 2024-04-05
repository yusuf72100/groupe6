package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;
import groupe6.model.partie.erreur.ResultatVerificationErreur;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GridMenu implements Menu {
    private Partie partie;
    private Button sauvegarder;
    private Button home;
    private Button pause;
    private Button undo;
    private Button redo;
    private Button check;
    private Button hypothese;
    private Button help;
    private HBox layout_v;
    private GridPane gridPane;
    private GridPane gridPaneAide;
    private StackPane container;
    private CelluleNode[][] celluleNodes;
    private Cellule[][] cellulesData;
    private int compteur;        // utilisé à des fins de test
    private Puzzle puzzle;
    private Scene scene;
    private Label buttonHoverLabel;
    private int longueur;
    private int largeur;
    private Rectangle[][] rectangle;

    public GridMenu(Partie partie){
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
        this.gridPaneAide = new GridPane();
        this.gridPaneAide.setFocusTraversable(false);
        this.gridPaneAide.setMouseTransparent(true);
        this.gridPaneAide.setHgap(0);
        this.gridPaneAide.setVgap(0);
        this.gridPaneAide.setPadding(new Insets(0));
        this.container = new StackPane(gridPane, gridPaneAide);
        this.longueur = partie.getPuzzle().getLongueur();
        this.largeur = partie.getPuzzle().getLargeur();
        this.rectangle = new Rectangle[largeur][longueur];
        initCellules(this.longueur, this.largeur);
        this.puzzle = partie.getPuzzle();

        if ( Launcher.getVerbose() ) {
            System.out.println("Puzzle au lancement :\n"+this.puzzle);
        }
    }

    /**
     * Méthode qui change la couleur du fond d'une cellule
     *
     * @param x la position x de la cellule
     * @param y la position y de la cellule
     * @param color la couleur à appliquer ( format css )
     */
    public void highlightCellule(int y, int x, String color) {
        this.rectangle[x][y].setVisible(true);

        //this.celluleNodes[y][x].getCenterPane().setStyle("-fx-background-color: "+color+";");
        /*double celluleWidth = this.celluleNodes[x][y].getCenterPane().getWidth();
        double celluleHeight = this.celluleNodes[x][y].getCenterPane().getHeight();
        double celluleX = this.celluleNodes[x][y].getCenterPane().getLayoutX();
        double celluleY = this.celluleNodes[x][y].getCenterPane().getLayoutY();

        System.out.println("\n\n x : " + celluleX + "\n y : " + celluleY + "\n color : " + color);

        this.rectangle[y][x].setTranslateX(celluleX);
        this.rectangle[y][x].setTranslateY(celluleY);*/
    }


    private Button initHeaderButton(String style, String hoverText) {
        Button button = new Button();
        button.getStyleClass().add(style);
        button.setPrefSize(30, 30);

        FadeTransition fadeButton = new FadeTransition(Duration.millis(150), button);
        fadeButton.setFromValue(1.0);
        fadeButton.setToValue(0.2);

        button.setOnMouseEntered(event -> {
            mouseEntered(fadeButton, button);
            this.buttonHoverLabel.setText(hoverText);
        });
        
        button.setOnMouseExited(event -> {
            mouseExited(fadeButton, button);
            this.buttonHoverLabel.setText("");
        });

        button.setOnMouseMoved(event -> {
            this.buttonHoverLabel.setTranslateX(event.getSceneX() + 10);
            this.buttonHoverLabel.setTranslateY(event.getSceneY() + 15);
        });

        return button;
    }

    public Partie getPartie() {
        return partie;
    }

    /**
     * Gestion de chaque bouton (barre)
     */
    public class CelluleButtonEventHandler implements EventHandler<ActionEvent> {
        private final int i, j;

        public CelluleButtonEventHandler(int i, int j, Cellule[][] data) {
            this.i = i;
            this.j = j;
            cellulesData = data;
        }

        /**
         * Execute l'action demandée sur le bouton
         * @param event TODO
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
            if ( Launcher.getVerbose() ) {
                System.out.println("Puzzle après action bascule trois etat :\n"+puzzle);
            }
        }
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * @param args TODO
     * @return TODO
     * @param <T> TODO
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

                // Debug
                System.out.println("Gestionnaire d'action lors de la sauvegarde :\n"+partie.getGestionnaireAction());
            }
        });

        // Handler Undo
        undo.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if ( Launcher.getVerbose() ) {
                    System.out.println("Undo");
                }
                partie.undo();
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
                partie.redo();
                updateAffichage();
            }
        });

        // Handler Help
        help.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                partie.chercherAide();
                // TODO : Update l'affichage de l'historique d'aide
            }
        });

        // Handler Check
        check.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                // TODO : Activer la vérification
                ResultatVerificationErreur resultat = partie.verifierErreur();
                System.out.println("Résultat de la vérification : "+resultat);
                System.out.println(partie.getGestionnaireErreur());
                if ( resultat.isErreurTrouvee() ) {
                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        // TODO : Mettre en rouge les cellules au coordonnées coords
                        System.out.println("Première erreur : "+coords);
                        highlightCellule(coords.getY(), coords.getX(), "red");
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        // TODO : Mettre en orange les cellules au coordonnées coords
                        System.out.println("Erreur suivante : "+coords);
                        highlightCellule(coords.getY(), coords.getX(), "orange");
                    }

                    // TODO : Afficher pop up 2 btn ("oui","non") et
                    //      message "Les cellules en rouge et orange seront modifier si vous acceptez la correction.\n
                    //      Acceptez de revenir sur la première erreur trouvée ?"

                    boolean accepteCorrection = false;
                    if ( accepteCorrection ) {
                        partie.corrigerErreur();
                    }

                    for (Coordonnee coords : resultat.getPremiereErreur() ) {
                        // TODO : Enleve la couleurs rouge sur les cellules au coordonnées coords
//                        highlightCellule(coords.getY(), coords.getX(), "black");
                    }
                    for ( Coordonnee coords : resultat.getErreursSuivantes() ) {
                        // TODO : Enleve la couleurs orange sur les cellules au coordonnées coords
//                        highlightCellule(coords.getY(), coords.getX(), "black");
                    }

                    // TODO : Enlever des points meme si il n'accepte pas la correction
                }
                else {
                    // TODO : Afficher pop up un btn "ok" et message "Aucune erreur trouvée"
                    // TODO : Ne pas enlever de points
                }
            }
        });

        // Handler Hypothèse
        hypothese.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                // TODO : Activer le mode hypothèse
                if ( partie.getHypothese() == null ) {
                    partie.activerHypothese();
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Activation du mode hypothèse");
                    }
                }
                else {
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Désactivation du mode hypothèse");
                    }
                    // TODO : Affiche pop up 2 btn ("oui","non") et message "Voulez-vous valider l'hypothèse ?"

                    boolean validerHypothese = false;
                    if ( validerHypothese ) {
                        partie.validerHypothese();
                        System.out.println("Validation de l'hypothèse");
                    }
                    else {
                        partie.annulerHypothese();
                        System.out.println("Annulation de l'hypothèse");
                    }
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
        gridPaneAide.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().addAll("button-square");
        gridPaneAide.getStyleClass().addAll("button-square");

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
     * @param l TODO
     * @param L TODO
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
     * @param path TODO
     */
    public void initNewPuzzle(String path) {
        this.puzzle = Puzzle.chargerPuzzle(path);
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
     * @param nouveau TODO
     */
    private void afficher(boolean nouveau) {
        // Colonnes
        for (int i = 0; i < this.celluleNodes.length; i++) {
            // Lignes
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                // if(nouveau) this.celluleNodes[i][j] = new CelluleNode(-1);

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

        for (int i = 0; i < this.celluleNodes.length; i++) {
            for (int j = 0; j < this.celluleNodes[i].length; j++) {
                // rectangle d'aide
                this.rectangle[i][j] = new Rectangle();
                this.rectangle[i][j].setWidth((600.0 / this.largeur));
                this.rectangle[i][j].setHeight((600.0 / this.longueur));
                this.rectangle[i][j].setFocusTraversable(false);
                this.rectangle[i][j].setMouseTransparent(true);
                this.rectangle[i][j].setVisible(false);
                this.gridPaneAide.add(this.rectangle[i][j], i, j);
            }
        }
    }

    private void updateAffichage() {
        for ( int y = 0; y < this.largeur; y++ ) {
            for (int x = 0; x < this.longueur; x++) {
                this.celluleNodes[y][x].updateCotes(cellulesData[y][x].getCotes());
            }
        }
    }

    /**
     * Règle l'animation d'entrée sur le bouton souhaité
     * @param fade TODO
     * @param button TODO
     */
    private static void mouseEntered(FadeTransition fade, Button button) {
        fade.setRate(1);
        fade.play();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Règle l'animation de sortie sur le bouton souhaité
     * @param fade TODO
     * @param button TODO
     */
    private static void mouseExited(FadeTransition fade, Button button) {
        fade.setRate(-1);
        fade.play();
        fade.jumpTo(Duration.ZERO);
        button.setCursor(Cursor.DEFAULT);
    }
}
