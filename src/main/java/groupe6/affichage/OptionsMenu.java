package groupe6.affichage;

import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class OptionsMenu implements Menu {

  /**
   * Label pour les aides
   */
  private static Label labelAide;

  /**
   * Label pour les contrôles
   */
  private static Label keySettingsLabel;

  /**
   * Le panneau principal
   */
  private static StackPane stackPane;

  /**
   * Bouton pour activer/désactiver l'aide au démarrage pour les puzzles de difficulté MOYEN
   */
  private static Button btnCheckMoyen;

  /**
   * Bouton pour activer/désactiver l'aide au démarrage pour les puzzles de difficulté DIFFICILE
   */
  private static Button btnCheckDifficile;

  /**
   * Bouton pour activer/désactiver l'aide au remplissage des croix
   */
  private static Button btnAutoCroix;

  /**
   * Le profil qui contient les paramètres
   */
  private static Profil profile;

  /**
   * Méthode statique pour initialiser le menu des options
   *
   * @param w la largeur de la fenêtre
   * @param h la hauteur de la fenêtre
   */
  public static void initMenu(Double w, Double h) {

    profile = Launcher.catalogueProfils.getProfilActuel();

    // VBox principale
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(0.03 * h);

    // Labels Aide
    labelAide = new Label("Aides :");
    labelAide.getStyleClass().add("title_help");
    Menu.adaptTextSize(labelAide, 50, w, h);

    // Labels Contrôles
    keySettingsLabel = new Label("Contrôles :");
    keySettingsLabel.getStyleClass().add("title_help");
    Menu.adaptTextSize(keySettingsLabel, 50, w, h);

    // Le panneau principal
    stackPane = new StackPane();
    stackPane.setVisible(false);
    stackPane.setManaged(false);

    // Image représentant les contrôles
    String cheminControlesImage = Launcher.dossierAssets+ "/img/controles.png";
    ImageView controlesImage = new ImageView(Launcher.chargerImage(cheminControlesImage));
    controlesImage.setFitHeight(0.25 * h);
    controlesImage.setPreserveRatio(true);

    // Label aide démarrage
    Label labelAideDemarrage = new Label("Application automatique des techniques de démarrage");
    labelAideDemarrage.getStyleClass().add("title2_help");
    Menu.adaptTextSize(labelAideDemarrage, 30, w, h);

    // Label avertissement
    Label avertissementAideDemarrage = new Label(
        "Cette aide s'applique uniquement au début de" +
            "\n" +
            "puzzle MOYEN et DIFFICILE"
    );
    avertissementAideDemarrage.getStyleClass().add("avertissement_help");
    Menu.adaptTextSize(avertissementAideDemarrage, 23, w, h);
    avertissementAideDemarrage.setAlignment(Pos.CENTER);

    // BtnCheckFacile
    btnCheckMoyen = new Button("MOYEN");
    btnCheckMoyen.setPrefSize(0.10 * w, 0.05 * h);

    // BtnCheckMoyen
    btnCheckDifficile = new Button("DIFFICILE");
    btnCheckDifficile.setPrefSize(0.10 * w, 0.05 * h);

    Button[] btnCheck = new Button[]{btnCheckMoyen, btnCheckDifficile};
    for ( int i = 0; i < 2; i++ ) {
      boolean aideDemmarageActive = profile.getParametre().getAideTechniqueDemarrage(
          DifficultePuzzle.values()[i+1]
      );
      if ( aideDemmarageActive ) {
        btnCheck[i].getStyleClass().clear();
        btnCheck[i].getStyleClass().add("button");
        btnCheck[i].getStyleClass().add("custom-checkbox-on");
      } else {
        btnCheck[i].getStyleClass().clear();
        btnCheck[i].getStyleClass().add("button");
        btnCheck[i].getStyleClass().add("custom-checkbox-off");
      }
    }

    HBox HBoxToggleBtnAideDemarrage = new HBox();
    HBoxToggleBtnAideDemarrage.getChildren().addAll(btnCheckMoyen, btnCheckDifficile);
    HBoxToggleBtnAideDemarrage.setAlignment(Pos.CENTER);
    HBoxToggleBtnAideDemarrage.setSpacing(0.02 * w);

    // Gérer le clic sur les boutons pour les activer/désactiver
    btnCheckMoyen.setOnAction(e -> {
      boolean on = btnCheckMoyen.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnCheckMoyen.getStyleClass().clear();
        btnCheckMoyen.getStyleClass().add("button");
        btnCheckMoyen.getStyleClass().add("custom-checkbox-off");
      } else {
        btnCheckMoyen.getStyleClass().clear();
        btnCheckMoyen.getStyleClass().add("button");
        btnCheckMoyen.getStyleClass().add("custom-checkbox-on");
      }
      boolean aide = profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.MOYEN);
      profile.getParametre().setAideTechniqueDemarrage(!aide, DifficultePuzzle.MOYEN);
    });

    btnCheckDifficile.setOnAction(e -> {
      boolean on = btnCheckDifficile.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnCheckDifficile.getStyleClass().clear();
        btnCheckDifficile.getStyleClass().add("button");
        btnCheckDifficile.getStyleClass().add("custom-checkbox-off");
      } else {
        btnCheckDifficile.getStyleClass().clear();
        btnCheckDifficile.getStyleClass().add("button");
        btnCheckDifficile.getStyleClass().add("custom-checkbox-on");
      }
      boolean aide = profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.DIFFICILE);
      profile.getParametre().setAideTechniqueDemarrage(!aide, DifficultePuzzle.DIFFICILE);
    });

    // Espace entre les éléments
    Pane espace1 = new Pane();
    espace1.setMinHeight(0.01 * h);
    espace1.setMaxHeight(0.01 * h);

    // VBox pour les éléments de l'aide au démarrage
    VBox VBoxCentreAideDemarrage = new VBox();
    VBoxCentreAideDemarrage.getChildren().addAll(
        labelAideDemarrage,
        avertissementAideDemarrage,
        espace1,
        HBoxToggleBtnAideDemarrage
    );
    VBoxCentreAideDemarrage.setSpacing(0.01 * h);
    VBoxCentreAideDemarrage.setAlignment(Pos.CENTER);

    // Label pour l'aide au remplissage des croix
    Label labelAideAutoCroix = new Label("Remplissage automatique des croix");
    labelAideAutoCroix.getStyleClass().add("title2_help");
    Menu.adaptTextSize(labelAideAutoCroix, 30, w, h);

    btnAutoCroix = new Button("Cliquez pour activer");
    btnAutoCroix.setPrefSize(0.15 * w, 0.05 * h);

    boolean aideRemplisageActive = profile.getParametre().getAideRemplissageCroix();
    if ( aideRemplisageActive ) {
      btnAutoCroix.getStyleClass().clear();
      btnAutoCroix.getStyleClass().add("button");
      btnAutoCroix.getStyleClass().add("custom-checkbox-on");
      btnAutoCroix.setText("Cliquez pour désactiver");
    } else {
      btnAutoCroix.getStyleClass().clear();
      btnAutoCroix.getStyleClass().add("button");
      btnAutoCroix.getStyleClass().add("custom-checkbox-off");
      btnAutoCroix.setText("Cliquez pour activer");
    }

    btnAutoCroix.setOnAction( e -> {
      boolean on = btnAutoCroix.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnAutoCroix.getStyleClass().remove("custom-checkbox-on");
        btnAutoCroix.getStyleClass().add("custom-checkbox-off");
        btnAutoCroix.setText("Cliquez pour activer");

      } else {
        btnAutoCroix.getStyleClass().remove("custom-checkbox-off");
        btnAutoCroix.getStyleClass().add("custom-checkbox-on");
        btnAutoCroix.setText("Cliquez pour désactiver");
      }
      boolean aide = profile.getParametre().getAideRemplissageCroix();
      profile.getParametre().setAideRemplissageCroix(!aide);
    });

    // VBox pour les éléments de l'aide au remplissage des croix
    VBox VBoxAideAutoCroix = new VBox();
    VBoxAideAutoCroix.getChildren().addAll(
        labelAideAutoCroix,
        btnAutoCroix
    );
    VBoxAideAutoCroix.setSpacing(0.05 * h);
    VBoxAideAutoCroix.setAlignment(Pos.CENTER);

    // VBox pour les paramètres des aides
    VBox VboxAide = new VBox();
    VboxAide.getChildren().addAll(
        VBoxCentreAideDemarrage,
        VBoxAideAutoCroix
    );
    VboxAide.setSpacing(0.02 * h);
    VboxAide.setAlignment(Pos.CENTER);

    // Ajout des éléments à la VBox
    vBox.getChildren().addAll(
        OptionsMenu.labelAide,
        VboxAide,
        OptionsMenu.keySettingsLabel,
        controlesImage
    );
    vBox.setAlignment(Pos.CENTER);

    // Image de fond
    String cheminBgImage = Launcher.normaliserChemin(Launcher.dossierAssets + "/img/bg.png");
    ImageView backgroundImage = new ImageView(Launcher.chargerImage(cheminBgImage));

    // Ajout des éléments graphiques au menu
    stackPane.getChildren().addAll(
        backgroundImage,
        vBox
    );

  }

  /**
   * Méthode statique pour obtenir le panneau du menu des options
   *
   * @return le panneau du menu des options
   */
  public static StackPane getMenu() {
    return stackPane;
  }

  /**
   * Méthode statique pour afficher le menu des options
   */
  public static void showMenu() {
    stackPane.setVisible(true);
    stackPane.setManaged(true);
  }

  /**
   * Méthode statique pour cacher le menu des options
   */
  public static void hideMenu() {
    stackPane.setVisible(false);
    stackPane.setManaged(false);
  }

  /**
   * Méthode statique pour mettre à jour le profil
   *
   * @param profil le profil à mettre à jour
   */
  public static void setProfil(Profil profil) {
    profile = profil;

    // Update des boutons

    Button[] btnCheck = new Button[]{btnCheckMoyen, btnCheckDifficile};
    for ( int i = 0; i < 2; i++ ) {
      boolean aideDemmarageActive = profile.getParametre().getAideTechniqueDemarrage(
          DifficultePuzzle.values()[i+1]
      );
      if ( aideDemmarageActive ) {
        btnCheck[i].getStyleClass().remove("custom-checkbox-off");
        btnCheck[i].getStyleClass().add("custom-checkbox-on");
      } else {
        btnCheck[i].getStyleClass().remove("custom-checkbox-on");
        btnCheck[i].getStyleClass().add("custom-checkbox-off");
      }
    }

    // Gérer le clic sur les boutons pour les activer/désactiver
    btnCheckMoyen.setOnAction(e -> {
      boolean on = btnCheckMoyen.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnCheckMoyen.getStyleClass().clear();
        btnCheckMoyen.getStyleClass().add("button");
        btnCheckMoyen.getStyleClass().add("custom-checkbox-off");
      } else {
        btnCheckMoyen.getStyleClass().clear();
        btnCheckMoyen.getStyleClass().add("button");
        btnCheckMoyen.getStyleClass().add("custom-checkbox-on");
      }
      boolean aide = profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.MOYEN);
      profile.getParametre().setAideTechniqueDemarrage(!aide, DifficultePuzzle.MOYEN);
    });

    btnCheckDifficile.setOnAction(e -> {
      boolean on = btnCheckDifficile.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnCheckDifficile.getStyleClass().clear();
        btnCheckDifficile.getStyleClass().add("button");
        btnCheckDifficile.getStyleClass().add("custom-checkbox-off");
      } else {
        btnCheckDifficile.getStyleClass().clear();
        btnCheckDifficile.getStyleClass().add("button");
        btnCheckDifficile.getStyleClass().add("custom-checkbox-on");
      }
      boolean aide = profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.DIFFICILE);
      profile.getParametre().setAideTechniqueDemarrage(!aide, DifficultePuzzle.DIFFICILE);
    });

    boolean aideRemplisageActive = profile.getParametre().getAideRemplissageCroix();
    if ( aideRemplisageActive ) {
      btnAutoCroix.getStyleClass().clear();
      btnAutoCroix.getStyleClass().add("button");
      btnAutoCroix.getStyleClass().add("custom-checkbox-on");
      btnAutoCroix.setText("Cliquez pour désactiver");
    } else {
      btnAutoCroix.getStyleClass().clear();
      btnAutoCroix.getStyleClass().add("button");
      btnAutoCroix.getStyleClass().add("custom-checkbox-off");
      btnAutoCroix.setText("Cliquez pour activer");
    }

    btnAutoCroix.setOnAction( e -> {
      boolean on = btnAutoCroix.getStyleClass().contains(
          "custom-checkbox-on"
      );
      if ( on ) {
        btnAutoCroix.getStyleClass().remove("custom-checkbox-on");
        btnAutoCroix.getStyleClass().add("custom-checkbox-off");
        btnAutoCroix.setText("Cliquez pour activer");

      } else {
        btnAutoCroix.getStyleClass().remove("custom-checkbox-off");
        btnAutoCroix.getStyleClass().add("custom-checkbox-on");
        btnAutoCroix.setText("Cliquez pour désactiver");
      }
      boolean aide = profile.getParametre().getAideRemplissageCroix();
      profile.getParametre().setAideRemplissageCroix(!aide);
    });
  }
}