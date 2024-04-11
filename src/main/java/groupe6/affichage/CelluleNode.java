package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Classe qui correspond à l'affichage d'une cellule
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
    private Label centerTextField;

    /**
     * Les cotes de la cellule
     */
    private ValeurCote[] cotes;

    /**
     * Sauvegarde des anciens css des boutons ( côtés de la cellule )
     */
    private String[] buttonsOldCss;

    /**
     * Sauvegarde des anciens css des images ( croix sur les côtés de la cellule )
     */
    private String centerOldCss;

    /**
     * Sauvegarde des anciens css du paneau central de la cellule
     */
    private String centerPaneOldCss;

    /**
     * Sauvegarde des anciens css du label de la valeur numérique de la cellule
     */
    private String centerTextFieldOldCss;

    /**
     * La valeur numérique de la cellule
     */
    private int label;

    /**
     * Constructeur de la classe CelluleNode
     *
     * @param label la valeur numérique de la cellule
     * @param cotes les cotes de la cellule
     */
    public CelluleNode(int label, ValeurCote[] cotes) {
        // récupération du label qui correspond à la valeur numérique de la cellule
        this.buttonsOldCss = new String[4];
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

        this.cellule[0].getStyleClass().add("button-top");
        this.cellule[1].getStyleClass().add("button-bottom");
        this.cellule[2].getStyleClass().add("button-left");
        this.cellule[3].getStyleClass().add("button-right");

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
                    this.cellule[i].getStyleClass().removeAll("clicked");
                    this.cellule[i].getStyleClass().removeAll("croix");
                    this.image[i].setVisible(false);
                    break;
                case TRAIT:
                    this.cellule[i].getStyleClass().removeAll("croix");
                    if(!this.cellule[i].getStyleClass().contains("clicked")) {
                        this.cellule[i].getStyleClass().add("clicked");
                    }
                    this.image[i].setVisible(false);
                    break;
                case CROIX:
                    this.cellule[i].getStyleClass().removeAll("clicked");
                    if(!this.cellule[i].getStyleClass().contains("croix")) {
                        this.cellule[i].getStyleClass().add("croix");
                    }
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
     * Méthode pour obtenir la valeur numérique de la cellule
     *
     * @param i le coté qu'on veut obtenir
     * @return la valeur du coté de la cellule
     */
    public ValeurCote getValeurCote(int i) {
        return this.cotes[i];
    }

    /**
     * Méthode pour changer l'affichage css de la cellule
     *
     */
    public void changeCellulesCss(String buttonClass, String centerClass) {
        for ( int i = 0; i < 4; i++ ) {
            changeButtonCss(i, buttonClass);
        }
        this.centerOldCss = centerClass;
        this.centerPane.getStyleClass().add(centerClass);
        this.centerTextField.getStyleClass().add(centerClass);
    }

    /**
     * Méthode qui remet l'affichage de la cellule à l'état précédent
     */
    public void resetCellulesCss() {
        for ( int i = 0; i < 4; i++ ) {
            resetButtonCss(i);
        }
        this.centerPane.getStyleClass().removeAll(this.centerOldCss);
        this.centerTextField.getStyleClass().removeAll(this.centerOldCss);
        this.centerOldCss = null;
    }

    /**
     * Méthode qui change l'affichage css d'un bouton
     *
     * @param buttonIndex le bouton qu'on veut changer ( coté )
     */
    public void changeButtonCss(int buttonIndex, String cssClass) {
        if(this.cellule[buttonIndex].getStyleClass().size() == 3) {
            this.buttonsOldCss[buttonIndex] = this.cellule[buttonIndex].getStyleClass().get(this.cellule[buttonIndex].getStyleClass().size()-1);
            System.out.println("Old css : " + this.buttonsOldCss[buttonIndex]);
            this.cellule[buttonIndex].getStyleClass().removeAll(this.cellule[buttonIndex].getStyleClass().get(this.cellule[buttonIndex].getStyleClass().size()-1));
        }
        this.cellule[buttonIndex].getStyleClass().add(cssClass);
        System.out.println("Nouveau css : " + this.cellule[buttonIndex].getStyleClass());
    }

    /**
     * Méthode qui remet l'affichage d'un bouton à l'état précédent
     *
     * @param buttonIndex le bouton qu'on veut remettre à l'état précédent ( coté )
     */
    public void resetButtonCss(int buttonIndex) {
        if(this.cellule[buttonIndex].getStyleClass().size() == 3) {
            System.out.println("Reset Old css : " + this.cellule[buttonIndex].getStyleClass().get(this.cellule[buttonIndex].getStyleClass().size()-1));
            this.cellule[buttonIndex].getStyleClass().removeAll(this.cellule[buttonIndex].getStyleClass().get(this.cellule[buttonIndex].getStyleClass().size()-1));
        }
        if(this.buttonsOldCss[buttonIndex] != null) {
            this.cellule[buttonIndex].getStyleClass().add(this.buttonsOldCss[buttonIndex]);
            System.out.println("Reset Nouveau css : " + this.cellule[buttonIndex].getStyleClass());
            this.buttonsOldCss[buttonIndex] = null;
        }
    }

    /**
     * Méthode qui permet d'obtenir le rectangle qui correspond à un coin
     *
     * @param c le coin qu'on veut obtenir
     * @return le rectangle qui correspond au coin
     */
    public Rectangle getCoin(int c) { return this.coins[c]; }

    /**
     * Méthode qui permet d'obtenir l'image qui correspond à un coté
     *
     * @param i le coté qu'on veut obtenir
     * @return l'image qui correspond au coté
     */
    public ImageView getImage(int i) { return this.image[i]; }

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
     * Méthode pour obtenir un bouton de la cellule
     *
     * @param c le bouton qu'on veut obtenir ( coté )
     * @return le bouton de la cellule
     */
    public Button getButton(int c) {
        return this.cellule[c];
    }

    /**
     * Méthode pour obtenir le paneau central de la cellule
     *
     * @return le paneau central de la cellule
     */
    public StackPane getCenterPane() { return this.centerPane; }

    /**
     * Méthode pour obtenir la valeur numérique de la cellule
     *
     * @return la valeur numérique de la cellule
     */
    public int getLabel() { return this.label; }

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
        this.centerTextField.setText(Integer.toString(i));
    }
}
