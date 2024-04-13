package groupe6.affichage;

import groupe6.launcher.Launcher;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Classe qui correspond au menu de pause de l'application
 *
 * @author Yusuf
 */
public class PauseMenu implements Menu {
  /**
   * Node d'affichage
   */
  private static StackPane stackPane;

  /**
   * Boîte verticale qui agence les boutons
   */
  private static VBox vBox;

  /**
   * Bouton pour reprendre la partie et donc relancer le chrono
   */
  private static Button reprendre;

  /**
   * Bouton d'accès au menu d'options
   */
  private static Button options;

  /**
   * Bouton d'accès au menu principal
   */
  private static Button exitMenu;

  /**
   * Initialise le menu de pause
   * @param w largeur de la fenêtre
   * @param h hauteur de la fenêtre
   */
  public static void initMenu(Double w, Double h) {
    vBox = new VBox();
    stackPane = new StackPane();
    reprendre = new Button("Reprendre");
    options = new Button("Options");
    exitMenu = new Button("Menu principal");

    reprendre.getStyleClass().addAll("button-rounded", "button-text");
    options.getStyleClass().addAll("button-rounded", "button-text");
    exitMenu.getStyleClass().addAll("button-rounded", "button-text");

    reprendre.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
    options.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));
    exitMenu.setPrefSize(Menu.toPourcentWidth(300.0, w), Menu.toPourcentHeight(100.0, h));

    vBox.setSpacing(50);
    vBox.getChildren().addAll(reprendre, options, exitMenu);
    vBox.setAlignment(Pos.CENTER);

    // Image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

//        stackPane.setStyle("-fx-background-color: white;");
    stackPane.getChildren().addAll(
        backgroundImage,
        vBox,
        OptionsMenu.getMenu()
    );

    stackPane.setVisible(false);
    stackPane.setManaged(false);

    // handler bouton reprendre la partie
    reprendre.setOnMouseClicked(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hideMenu();
      }
    });

    options.setOnMouseClicked(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        OptionsMenu.showMenu();
      }
    });

    // handler bouton menu principal
    exitMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        Main.showMainMenu();
      }
    });
  }

  /**
   * Cache le menu avec une animation
   */
  public static void hideMenu() {
    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), stackPane);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();

    fadeTransition.setOnFinished(event -> {
      stackPane.setVisible(false);
      stackPane.setManaged(false);
    });
  }

  /**
   * Affiche le menu avec une animation
   */
  public static void showMenu() {
    stackPane.setVisible(true);
    stackPane.setManaged(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), stackPane);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  /**
   * Initialise et renvoi le menu sous forme de Node
   * @return renvoi un stackpane
   */
  public static StackPane getMenu() {
    return stackPane;
  }
}
