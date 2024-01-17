import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

/**
 * Cette classe sera la classe principale de notre programme
 */
public class SlitherLink extends JFrame {
    private static final long serialVersionUID = 1L;
    
    public SlitherLink(){
        super("Slitherlink");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] var){
        SlitherLink slitherLink = new SlitherLink();
        slitherLink.setVisible(true);

        System.out.print("Ceci est un test jeune truite\n");

        ObjectOutputStream oos = null;

        Grille grille = new Grille();
        // Modification de la grille selon votre exemple YAML
        // Vous devez implémenter les setters appropriés dans la classe Grille
        // Par exemple : grille.setEasy(...);

        // Sérialisation de l'objet Grille en format YAML
        try (FileOutputStream fileOutputStream = new FileOutputStream("template_grille.yaml");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            // Écrire l'objet Grille dans le fichier
            objectOutputStream.writeObject(grille);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Désérialisation de l'objet Grille à partir du fichier
        try (FileInputStream fileInputStream = new FileInputStream("template_grille.yaml");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            // Lire l'objet Grille du fichier
            Grille loadedGrille = (Grille) objectInputStream.readObject();

            // Afficher la grille chargée
            loadedGrille.displayGrid();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
