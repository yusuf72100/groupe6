package groupe6.affichage;

import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OptionsMenu implements Menu {
    private static ScrollPane scrollPane;

    private static VBox vBox;

    private static Label profilSettingsLabel;

    private static Label keySettingsLabel;

    private static StackPane stackPane;

    private static CheckBox[] checkBox;

    private static Profil profile;

    public static void initMenu(Double w, Double h) {
        checkBox = new CheckBox[]{new CheckBox("Aide aux techniques de démarrage Facile"), new CheckBox("Aide aux techniques de démarrage Moyen"), new CheckBox("Aide aux techniques de démarrage Difficile"), new CheckBox("Remplissage automatique des croix")};
        scrollPane = new ScrollPane();
        vBox = new VBox();
        profilSettingsLabel = new Label("Profil :");
        keySettingsLabel = new Label("Assignation des touches :");
        profilSettingsLabel.getStyleClass().add("title_help");
        keySettingsLabel.getStyleClass().add("title_help");
        stackPane = new StackPane();

        Menu.adaptTextSize(profilSettingsLabel, 50, w, h);
        Menu.adaptTextSize(keySettingsLabel, 50, w, h);
        vBox.getChildren().addAll(profilSettingsLabel, checkBox[0], checkBox[1], checkBox[2], checkBox[3], keySettingsLabel);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(50);
        stackPane.setStyle("-fx-background-color: white;");
        scrollPane.setContent(vBox);
        scrollPane.setPadding(new Insets(20, 20, 20, 20));
        stackPane.getChildren().addAll(scrollPane);
        StackPane.setAlignment(stackPane, Pos.TOP_CENTER);
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        for(CheckBox cb : checkBox) {
            cb.setFont(Font.font("Inter", Math.min(w, h) * 0.02));
        }
    }

    public static StackPane getMenu() {
        return stackPane;
    }

    public static void showMenu() {
        stackPane.setVisible(true);
        stackPane.setManaged(true);
    }

    public static void hideMenu() {
        stackPane.setVisible(false);
        stackPane.setManaged(false);
    }

    public static void setProfil(Profil profil) {
        profile = profil;

        // update
        checkBox[0].setSelected(profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.FACILE));
        checkBox[1].setSelected(profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.MOYEN));
        checkBox[2].setSelected(profile.getParametre().getAideTechniqueDemarrage(DifficultePuzzle.DIFFICILE));
        checkBox[2].setSelected(profile.getParametre().getAideRemplissageCroix());

        // handlers
        checkBox[0].setOnAction(event -> {
            profil.getParametre().setAideTechniqueDemarrage( checkBox[0].isSelected(), DifficultePuzzle.FACILE);
        });

        checkBox[1].setOnAction(event -> {
            profil.getParametre().setAideTechniqueDemarrage(checkBox[1].isSelected(), DifficultePuzzle.MOYEN);
        });

        checkBox[2].setOnAction(event -> {
            profil.getParametre().setAideTechniqueDemarrage(checkBox[2].isSelected(), DifficultePuzzle.DIFFICILE);
        });

        checkBox[3].setOnAction(event -> {
            profil.getParametre().setAideRemplissageCroix(checkBox[3].isSelected());
        });
    }
}