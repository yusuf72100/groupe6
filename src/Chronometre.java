

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;

import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;


public class Chronometre extends Application {

    int seconde=0;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane bp= new BorderPane();
        Label chronoAffi=new Label(String.valueOf(seconde));
        bp.setTop(chronoAffi);
        Timer chrono = new Timer();
        TimerTask taskChrono = new TimerTask(){
            public void run(){
                Platform.runLater(() -> {
                    seconde++;
                    chronoAffi.setText(String.valueOf(seconde));
                });
                
                
            }
        };
        chrono.schedule(taskChrono, 1000);
        Scene scene = new Scene(bp, 300, 300);

        // Ajout de la scène à la scène principale (primaryStage)
        primaryStage.setScene(scene);

            // Affichage de la fenêtre
        primaryStage.show();
    }
    

}

