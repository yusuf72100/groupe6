/**
 * @author Yusuf Ulas
 */

package src;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe s'occupera de la sauvegarde et du chargement des objets
 */
public class Datas {
    private static Grid grid;
    public Datas(){
        grid = new Grid();

        grid.width = 5;
        grid.height = 5;

        grid.grid = Arrays.asList(
            Arrays.asList(1, 0, 0, 0, 0),
            Arrays.asList(0, 1, 0, 0, 0),
            Arrays.asList(0, 0, 1, 0, 0),
            Arrays.asList(0, 0, 0, 1, 0),
            Arrays.asList(0, 0, 0, 0, 1)
        );

        Map<String, Object> level = new HashMap<>();
        level.put("width", grid.width);
        level.put("height", grid.height);
        level.put("grid", grid.grid);

        grid.level = Arrays.asList(level);
    }

    public static void loadDatas() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("./src/grids.yml");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Grid grid = (Grid) ois.readObject();
            System.out.println("Objet chargé avec succès: " + grid);
        }
    }

    public static void saveDatas() throws IOException {
        try (
            FileOutputStream fos = new FileOutputStream("./src/grids.yml");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(grid);
            System.out.println("Objet sauvegardé avec succès.");
        }
    }

    public static void main(String[] var) throws IOException, ClassNotFoundException {
        Datas datas = new Datas();
        System.out.print("-----------------------| Ceci est un test jeune truite |-----------------------------\n");
    
        saveDatas();
        loadDatas();
        System.out.print("Donnée de test reçu: " + grid.height);
    }
}
