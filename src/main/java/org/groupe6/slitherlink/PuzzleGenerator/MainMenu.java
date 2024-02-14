package org.groupe6.slitherlink.PuzzleGenerator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
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
        valider.setPrefSize(200, 50);

        TextField longueur = createUnrestrictedTextField("Longueur");
        TextField largeur = createUnrestrictedTextField("Largeur");
        longueur.setPromptText("Longueur");
        largeur.setPromptText("Largeur");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefSize(200, 50);
        comboBox.getItems().addAll("Facile", "Moyen", "Difficile");

        layout_v.getChildren().addAll(longueur, largeur, comboBox, valider);
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

                    Main.showGridMenu(Integer.parseInt(longueurValue), Integer.parseInt(largeurValue));
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion en entier.");
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
