package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.ValeurCote;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.function.Function;

public class CelluleNode extends Node {
    private Button[] cellule;
    private ImageView[] image;
    private Rectangle[] coins;
    private StackPane centerPane;
    private Label centerTextField;
    private int label;
    private ValeurCote[] cotes;

    public CelluleNode(int label, ValeurCote[] cotes) {
        // récupération du label qui correspond à la valeur numérique de la cellule
        this.image = new ImageView[4];
        this.label = label;
        this.cotes = cotes;
        double cellSize = 50;
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

    public void updateCotes(ValeurCote[] cotes) {
        for ( int i = 0; i < 4; i++ ) {
            switch (cotes[i]) {
                case VIDE:
                    this.image[i].setVisible(false);
                    this.cellule[i].getStyleClass().remove("clicked");
                    this.cellule[i].getStyleClass().remove("croix");
                    break;
                case TRAIT:
                    this.image[i].setVisible(false);
                    this.cellule[i].getStyleClass().remove("croix");
                    this.cellule[i].getStyleClass().remove("clicked");
                    this.cellule[i].getStyleClass().add("clicked");
                    break;
                case CROIX:
                    this.image[i].setVisible(true);
                    this.cellule[i].getStyleClass().remove("clicked");
                    this.cellule[i].getStyleClass().add("croix");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.cotes[i]);
            }
        }
    }

    /**
     * Création du label de la cellule
     * @return StackPane
     */
    private StackPane createCenterContent() {
        this.centerTextField = new Label();
        double cellSize = 80;

        this.centerTextField.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-insets: 0;");
        this.centerTextField.setPrefSize(cellSize, cellSize);
        this.centerTextField.setFont(new Font(25));
        this.centerTextField.setAlignment(Pos.CENTER);
        // Affichage du label si la cellule a une valeur numérique
        if ( this.label != -1 ) {
            this.centerTextField.setText(Integer.toString(this.label));
        }

        return new StackPane(this.centerTextField);
    }

    /**
     * Méthode de création d'un carré noir
     * @param v
     * @return Rectangle
     */
    private Rectangle createBlackSquare(double v) {
        Rectangle square = new Rectangle(7, 7);
        square.getStyleClass().add("black-square");
        return square;
    }

    public ValeurCote getValeurCote(int i) {
        return this.cotes[i];
    }

    /**
     * Changer le css de la cellule
     * @param
     */
    public void changeCellulesCss(String css) {
        this.cellule[0].getStyleClass().addAll(css + "-top");
        this.cellule[1].getStyleClass().addAll(css + "-bottom");
        this.cellule[2].getStyleClass().addAll(css + "-left");
        this.cellule[3].getStyleClass().addAll(css + "-right");
    }

    public void changeButtonCss(int buttonIndex, Function<Integer, String> cssFunction) {
        Button button = cellule[buttonIndex];
        String cssClass = cssFunction.apply(this.label);

        button.getStyleClass().clear();
        button.getStyleClass().addAll(cssClass);
    }

    /**
     * Getter coin
     * @param c
     * @return Rectangle
     */
    public Rectangle getCoin(int c) { return this.coins[c]; }

    public ImageView getImage(int i) { return this.image[i]; }

    /**
     * Changer la taille d'une cellule
     * @param width
     * @param height
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
     * Getter bouton
     * @param c
     * @return Button
     */
    public Button getButton(int c) {
        return this.cellule[c];
    }

    /**
     * Getter pane de la cellule
     * @return StackPane
     */
    public StackPane getCenterPane() { return this.centerPane; }

    /**
     * Getter label cellule
     * @return int
     */
    public int getLabel() { return this.label; }

    public void setLabel(int label) { this.label = label; }

    public void setLabeText(int i) {
        this.centerTextField.setText(Integer.toString(i));
    }
}