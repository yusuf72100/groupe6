
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Puzzle implements Serializable {

    private String difficulte;
    private int longueur;
    private int largeur;
    private Cellule[][] grilleCellules;

    // Constructeur
    public Puzzle(String difficulte, int longueur, int largeur, Cellule[][] grilleCellules) {
        this.difficulte = difficulte;
        this.longueur = longueur;
        this.largeur = largeur;
        this.grilleCellules = grilleCellules;
    }

    // Méthode pour sauvegarder le puzzle
    public static void sauvegarderPuzzle(Puzzle puzzle, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(puzzle);
            System.out.println("Puzzle sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger le puzzle
    public static Puzzle chargerPuzzle(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Puzzle puzzle = (Puzzle) ois.readObject();
            System.out.println("Puzzle chargé avec succès.");
            return puzzle;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        // parcourir la martice et affiche le nombre dansla chauque cellule
        String str = "";
        str += "Difficulté : " + difficulte + "\n";
        str += "Longueur : " + longueur + "\n";
        str += "Largeur : " + largeur + "\n";
        str += "Grille : \n";
        for (int i = 0; i < grilleCellules.length; i++) {
            for (int j = 0; j < grilleCellules[i].length; j++) {
                str += grilleCellules[i][j].getValeur() + " ";
            }
            str += "\n";
        }
        return str;
    }

    public Cellule getCellule(int i, int j) {
        return grilleCellules[i][j];
    }
}
