package groupe6.tools.puzzleGenerator;

import java.util.function.UnaryOperator;

import groupe6.affichage.Menu;
import groupe6.launcher.Launcher;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * Classe du menu principal du puzzle generator
 *
 * @author Yusuf
 */
public class MainMenu implements Menu {

    /**
     * Méthode statique qui vérifie les valeurs entrées dans les textfields
     * 
     * @param prompt le prompt
     * @return le textfield
     */
    private static TextField createUnrestrictedTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);

        return textField;
    }

    /**
     * Méthode d'interface pour récupérer le menu
     * 
     * @param args les arguments
     * @return le menu
     * @param <T> le type de l'argument
     */
    public <T> VBox getMenu(T... args) {
        Main main = new Main();
        VBox layout_v = new VBox(10);
        Button valider = new Button("Valider");
        Button charger = new Button("Charger");
        valider.setPrefSize(200, 50);
        charger.setPrefSize(200, 50);

        TextField longueur = createUnrestrictedTextField("Longueur");
        TextField largeur = createUnrestrictedTextField("Largeur");
        longueur.setPromptText("Longueur");
        largeur.setPromptText("Largeur");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefSize(200, 50);
        comboBox.getItems().addAll("Facile", "Moyen", "Difficile");
        comboBox.setValue("Facile");

        layout_v.getChildren().addAll(longueur, largeur, comboBox, valider, charger);
        layout_v.setAlignment(Pos.CENTER);

        valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String longueurValue = longueur.getText();
                String largeurValue = largeur.getText();

                try {
                    DifficultePuzzle diff = switch (comboBox.getValue()) {
                        case "Moyen" -> DifficultePuzzle.MOYEN;
                        case "Difficile" -> DifficultePuzzle.DIFFICILE;
                        default -> DifficultePuzzle.FACILE;
                    };

                    main.showNewPuzzle(Integer.parseInt(largeurValue), Integer.parseInt(longueurValue), diff);
                } catch (NumberFormatException e) {
                    System.err.println("Erreur de conversion en entier.");
                }
            }
        });

        charger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionnez un fichier");

                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Fichier PUZZLE", "*.puzzle"));

                java.io.File selectedFile = fileChooser.showOpenDialog(main.getStage());

                if (selectedFile != null) {
                    if (Launcher.getVerbose() ) {
                        System.out.println("Log : Fichier sélectionné : " + selectedFile.getAbsolutePath());
                    }
                    main.showLoadedPuzzle(selectedFile);
                } else {
                    if ( Launcher.getVerbose() ) {
                        System.out.println("Log : Aucun fichier sélectionné.");
                    }
                }
            }
        });

        return layout_v;
    }
}
