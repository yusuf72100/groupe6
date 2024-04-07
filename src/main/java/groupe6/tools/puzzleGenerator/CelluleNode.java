package groupe6.tools.puzzleGenerator;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.function.Function;

/**
 * Classe CelluleNode qui représente une cellule dans le puzzle generator
 *
 * @author Yusuf
 */
public class CelluleNode extends Node {
    /**
     * Les boutons qui correspondent aux côtés de la cellule
     */
    private Button[] cellule;

    /**
     * Les images qui correspondent aux croix sur les côtés de la cellule
     */
    private ImageView[] image;

    /**
     * Les rectangles qui correspondent aux coins de la cellule
     */
    private Rectangle[] coins;

    /**
     * Le paneau central de la cellule qui contient le label de la valeur numérique de la cellule
     */
    private StackPane centerPane;

    /**
     * Le label de la valeur numérique de la cellule
     */
    private TextField centerTextField;

    /**
     * Les cotes de la cellule
     */
    private ValeurCote[] cotes;

    /**
     * La valeur numérique de la cellule
     */
    private int label;

    /**
     * Constructeur de la classe CelluleNode du puzzle generator
     *
     * @param label La valeur numérique de la cellule
     * @param cotes Les cotes de la cellule
     */
    public CelluleNode(int label, ValeurCote[] cotes) {
        this.label = -1;
        double cellSize = 50;
        this.image = new ImageView[4];
        this.cotes = cotes;
        this.cellule = new Button[4];
        this.coins = new Rectangle[4];
        this.centerPane = new StackPane();
        this.centerPane.getChildren().add(createCenterContent());
        this.centerPane.setAlignment(Pos.CENTER);

        this.cellule[0] = new Button("Top");
        this.cellule[1] = new Button("Bottom");
        this.cellule[2] = new Button("Left");
        this.cellule[3] = new Button("Right");

        this.cellule[0].getStyleClass().addAll("button-top");
        this.cellule[1].getStyleClass().addAll("button-bottom");
        this.cellule[2].getStyleClass().addAll("button-left");
        this.cellule[3].getStyleClass().addAll("button-right");

        String cheminCroix = Launcher.normaliserChemin(Launcher.dossierAssets + "/icon/croix.png");

        for ( int i = 0; i < 4; i++ ) {
            this.image[i] = new ImageView(Launcher.chargerImage(cheminCroix));
            this.image[i].setFitWidth(15);
            this.image[i].setFitHeight(15);
            this.image[i].setFocusTraversable(false);
            this.image[i].setMouseTransparent(true);
            this.image[i].setVisible(false);

            this.coins[i] = createBlackSquare(cellSize / 5);
            switch (this.cotes[i]) {
                case VIDE:
                    break;
                case TRAIT:
                    this.cellule[i].getStyleClass().add("clicked");
                    break;
                case CROIX:
                    this.cellule[i].getStyleClass().add("croix");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.cotes[i]);
            }
        }
    }

    /**
     * Méthode qui met a jour le styles des boutons en fonction des cotes
     *
     * @param cotes les cotes de la cellule
     */
    public void updateCotes(ValeurCote[] cotes) {
        for ( int i = 0; i < 4; i++ ) {
            switch (cotes[i]) {
                case VIDE:
                    this.cellule[i].getStyleClass().remove("clicked");
                    this.cellule[i].getStyleClass().remove("croix");
                    this.image[i].setVisible(false);
                    break;
                case TRAIT:
                    this.cellule[i].getStyleClass().remove("croix");
                    this.cellule[i].getStyleClass().add("clicked");
                    this.image[i].setVisible(false);
                    break;
                case CROIX:
                    this.cellule[i].getStyleClass().remove("clicked");
                    this.cellule[i].getStyleClass().add("croix");
                    this.image[i].setVisible(true);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.cotes[i]);
            }
        }
    }

    /**
     * Méthode qui crée le label de la valeur numérique de la cellule
     *
     * @return StackPane le paneau central de la cellule
     */
    private StackPane createCenterContent() {
        this.centerTextField = new TextField();
        double cellSize = 50;

        centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
        centerTextField.setMaxSize(cellSize, cellSize);
        centerTextField.setFont(new Font(25));
        centerTextField.setAlignment(Pos.CENTER);

        centerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                centerTextField.setText(oldValue);
                this.label = Integer.parseInt(oldValue);
            }
            else if (!newValue.matches("[0-3]")) {
                centerTextField.setText("");
                this.label = -1;
            }
            else{
                this.label = Integer.parseInt(newValue);
            }
        });

        return new StackPane(this.centerTextField);
    }

    // TODO: param "v" non utilisé
    /**
     * Méthode qui crée un carré noir
     *
     * @param v la taille du carré
     * @return le carré noir créé
     */
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(7, 7);
        square.getStyleClass().add("black-square");
        return square;
    }

    /**
     * Méthode qui change le style des cellules
     *
     * @param css le style des cellules
     */
    public void changeCellulesCss(String css) {
        cellule[0].getStyleClass().addAll(css + "-top");
        cellule[1].getStyleClass().addAll(css + "-bottom");
        cellule[2].getStyleClass().addAll(css + "-left");
        cellule[3].getStyleClass().addAll(css + "-right");
    }

    /**
     * Méthode qui change le style des boutons
     *
     * @param buttonIndex l'index du bouton
     * @param cssFunction la fonction qui retourne le style des boutons
     */
    public void changeButtonCss(int buttonIndex, Function<Integer, String> cssFunction) {
        Button button = cellule[buttonIndex];
        String cssClass = cssFunction.apply(label);

        button.getStyleClass().clear();
        button.getStyleClass().addAll(cssClass);
    }

    /**
     * Méthode pour changer la taille de la cellule
     *
     * @param width la largeur
     * @param height la hauteur
     */
    public void setPrefSize(double width, double height) {
        for (Button button : this.cellule) {
            if(button.getStyleClass().contains("button-top") || button.getStyleClass().contains("button-bottom")) {
                button.setStyle(
                        "-fx-min-width: " + width + "px; " +
                                "-fx-max-width: " + width + "px; " +
                                "-fx-min-height: 7px; " +
                                "-fx-max-height: 7px;"
                );
            } else {
                button.setStyle(
                        "-fx-min-height: " + height + "px; " +
                                "-fx-max-height: " + height + "px; " +
                                "-fx-min-width: 7px; " +
                                "-fx-max-width: 7px;"
                );
            }

            double adaptativeSize = ((width*80)/500);
            double adaptativeFontSize = ((width*50)/83.3);

            this.centerTextField.setPrefSize(adaptativeSize, adaptativeSize);
            this.centerTextField.setFont(new Font(adaptativeFontSize));
        }
    }

    /**
     * Méthode qui permet d'obtenir le rectangle qui correspond à un coin
     *
     * @param c le coin qu'on veut obtenir
     * @return le rectangle qui correspond au coin
     */
    public Rectangle getCoin(int c) { return coins[c]; }

    /**
     * Méthode qui permet d'obtenir l'image qui correspond à un coté
     *
     * @param i le coté qu'on veut obtenir
     * @return l'image qui correspond au coté
     */
    public ImageView getImage(int i) { return this.image[i]; }

    /**
     * Méthode pour obtenir un bouton de la cellule
     *
     * @param c le bouton qu'on veut obtenir ( coté )
     * @return le bouton de la cellule
     */
    public Button getButton(int c) {
        return cellule[c];
    }

    /**
     * Getter pane de la cellule
     * @return StackPane
     */
    public StackPane getCenterPane() { return centerPane; }

    /**
     * Méthode pour obtenir la valeur numérique de la cellule
     *
     * @return la valeur numérique de la cellule
     */
    public int getLabel() { return label; }

    /**
     * Métode pour changer la valeur numérique de la cellule
     *
     * @param label la valeur numérique de la cellule
     */
    public void setLabel(int label) { this.label = label; }

    /**
     * Méthode pour obtenir le label de la valeur numérique de la cellule
     *
     * @param i la valeur numérique de la cellule
     */
    public void setLabeText(int i) {
        centerTextField.setPromptText(Integer.toString(i));
    }
}
