import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.ProviderNotFoundException;

import javax.swing.text.html.ParagraphView;

/**
 * Cette classe mod\u00e8lise un profil d'utilisateur.
 */
public class Profil implements Serializable {

    /** Nom de l'utilisateur */
    private String nom;

    /** Chemin menant a l'image utilisee par defaut */
    private String cheminIMG;

    /** Niveau de progression dans le mode aventure */
    private int niveauAventure;

    /** Historique de jeu de l'utilisateur */
    // private Historique historique;

    /** Parametre de l'utilisateur */
    // private Parametre parametre;

    /**
     * Constructeur
     * 
     * @param nom            Nom de l'utilisateur
     * @param cheminIMG      Chemin vers l'image utilisee dans le profil
     * @param niveauAventure Niveau de progression dans le mode aventure
     */
    public Profil(String nom, String cheminIMG) {
        this.nom = nom;
        this.cheminIMG = cheminIMG;
        this.niveauAventure = 0;
        // this.historique = new Historique();
        // this.parametre = new Parametre();
    }

    /**
     * Modifie le nom du profil
     * 
     * @param newNom
     */
    public void modifierNom(String newNom) {
        this.nom = newNom;
    }

    /**
     * Modifie la photo du profil
     * 
     * @param newIMG
     */
    public void modifierIMG(String newIMG) {
        this.cheminIMG = newIMG;
    }

    /**
     * Methode pour obtenir le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Methode pour obtenir le chemin vers l'image (IMG)
     */
    public String getIMG() {
        return cheminIMG;
    }

    /**
     * Methode pour obtenir le niveauAventure
     */
    public int getNiveauAventure() {
        return niveauAventure;
    }

    /**
     * Methode pour obtenir l'historique
     */
    /*
     * public historique getHistorique() {
     * return historique;
     * }
     */

    /**
     * Methode pour obtenir les parametres
     */
    /*
     * public int getParametre() {
     * return parametre;
     * }
     */

    @Override
    public String toString() {
        String str = "";
        str += "Nom : " + nom + "\n";
        str += "IMG : " + cheminIMG + "\n";
        str += "niveauAventure : " + niveauAventure + "\n";
        return str;
    }

    /**
     * 
     * @param profil
     * @param chemin
     */
    public static void sauvegarderProfil(Profil profil, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(profil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param chemin
     * @return le profil à charger
     */
    public static Profil chargerProfil(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Profil profil = (Profil) ois.readObject();
            return profil;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
