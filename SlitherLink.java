import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

/**
 * Cette classe sera la classe principale de notre programme
 */
public class SlitherLink extends JFrame {
    public SlitherLink(){
        super("Slitherlink");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static String lireContenuFichier(String cheminFichier) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        
        // Utiliser Files.readString pour lire le contenu du fichier en tant que chaîne
        return Files.readString(chemin);
    }

    public static void main(String[] var){
        SlitherLink slitherLink = new SlitherLink();
        slitherLink.setVisible(true);
        System.out.print("Ceci est un test jeune truite\n");

        String cheminFichier = "chemin/vers/votre/fichier.json";

        try {
            // Lire le contenu du fichier dans une chaîne
            String contenuFichier = lireContenuFichier("./template_grille.json");

            // Manipuler le contenu JSON comme une chaîne
            System.out.println(contenuFichier);

            // Vous pouvez ensuite traiter manuellement la chaîne JSON selon vos besoins
            // Par exemple, vous pouvez utiliser des opérations de la classe String pour extraire des données.

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
