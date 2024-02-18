package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

public class MainMenu extends Application {

    /**
     * Renvoi le menu principal
     * @return VBox
     */
    public static VBox getMainMenu(){
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
                    System.out.println("Bouton Valider cliqué !");
                    System.out.println("Longueur: " + Integer.parseInt(longueurValue));
                    System.out.println("Largeur: " + Integer.parseInt(largeurValue));

                    PartieInfos.DifficultePuzzle diff = switch (comboBox.getValue()) {
                        case "Moyen" -> PartieInfos.DifficultePuzzle.MOYEN;
                        case "Difficile" -> PartieInfos.DifficultePuzzle.DIFFICILE;
                        default -> PartieInfos.DifficultePuzzle.FACILE;
                    };

                    Main.showNewPuzzle(Integer.parseInt(longueurValue), Integer.parseInt(largeurValue), diff);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion en entier.");
                }
            }
        });

        charger.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionnez un fichier");

                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Fichiers Texte", "*.bin"),
                        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
                );

                java.io.File selectedFile = fileChooser.showOpenDialog(Main.getStage());

                if (selectedFile != null) {
                    System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
                    Main.showLoadedPuzzle(selectedFile);
                } else {
                    System.out.println("Aucun fichier sélectionné.");
                }
            }
        });

        return layout_v;
    }

    /**
     * Vérifie les valeurs entrées dans les textfields
     * @param prompt
     * @return TextField
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
     * Inutile ici
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception { }
}
