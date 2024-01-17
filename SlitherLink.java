import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Cette classe sera la classe principale de notre programme
 */
public class SlitherLink extends JFrame {
    private static Grille grille;
    public SlitherLink(){
        super("Slitherlink");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        grille = new Grille();

        grille.width = 5;
        grille.height = 5;

        // Définir la grille
        grille.grid = Arrays.asList(
            Arrays.asList(1, 0, 0, 0, 0),
            Arrays.asList(0, 1, 0, 0, 0),
            Arrays.asList(0, 0, 1, 0, 0),
            Arrays.asList(0, 0, 0, 1, 0),
            Arrays.asList(0, 0, 0, 0, 1)
        );

        // Définir le niveau
        Map<String, Object> level = new HashMap<>();
        level.put("width", grille.width);
        level.put("height", grille.height);
        level.put("grid", grille.grid);

        grille.level = Arrays.asList(level);
    }

    public static void loadDatas() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("save.yml");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Grille myObject = (Grille) ois.readObject();
            System.out.println("Objet chargé avec succès: " + myObject);
        }
    }

    public static void saveDatas() throws IOException {
        try (
            FileOutputStream fos = new FileOutputStream("save.yml");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(grille);
            System.out.println("Objet sauvegardé avec succès.");

        }
    }

    public static void main(String[] var) throws IOException, ClassNotFoundException {
        SlitherLink slitherLink = new SlitherLink();
        slitherLink.setVisible(true);

        System.out.print("-----------------------Ceci est un test jeune truite-----------------------------\n");

        //saveDatas();
        loadDatas();
        System.out.print(grille.height);
    }
}
