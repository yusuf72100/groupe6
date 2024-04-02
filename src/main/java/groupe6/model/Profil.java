package groupe6.model;

import groupe6.launcher.Launcher;
import groupe6.tools.puzzleGenerator.Main;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static groupe6.test.TestProfils.chargerProfilsExistant;

/**
 * Cette classe modélise un profil d'utilisateur.
 *
 * @author William Sardon
 */
public class Profil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Nom de l'utilisateur */
    private String nom;

    /** Chemin menant a l'image utilisee par defaut */
    private String cheminIMG;

    /** Niveau de progression dans le mode aventure */
    private int niveauAventure;

    /** Historique de jeu de l'utilisateur */
    private final Historique historique;

    /** Parametre de l'utilisateur */
    private final Parametre parametre;

    /**
     * Constructeur
     *
     * @param nom            Nom de l'utilisateur
     * @param cheminIMG      Chemin vers l'image utilisee dans le profil
     */
    public Profil(String nom, String cheminIMG) {
        this.nom = nom;
        this.cheminIMG = cheminIMG;
        this.niveauAventure = 0;
        this.historique = new Historique();
        this.parametre = new Parametre();
    }

    /**
     * Modifie le nom du profil
     *
     * @param newNom Nouveau nom
     */
    public void modifierNom(String newNom) {
        this.nom = newNom;
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

    public Historique getHistorique() {
        return historique;
    }

    /**
     * Methode pour obtenir les parametres
     */

    public Parametre getParametre() {
        return parametre;
    }

    /**
     * Modifie la photo de profil
     *
     */
    public void choisirImage(Stage fenetre) throws IOException {
        // Afficher uniquement les fichiers images
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers IMAGE (*.jpeg, *.png, *.jpg)", "*.png", "*.jpeg", "*.jpg"));

        java.io.File file = fileChooser.showSaveDialog(fenetre);

        if (file != null) {
            String cheminDestination = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + this.nom + "/" + file.getName());
            System.out.println("cheminDestination : "+cheminDestination);
            Launcher.copyFile(file.getAbsolutePath(), cheminDestination);
            System.out.println("Le fichier a été copié avec succès dans " + cheminDestination);
            cheminIMG = cheminDestination;
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += "Nom : " + this.nom + "\n";
        str += "IMG : " + this.cheminIMG + "\n";
        str += "niveauAventure : " + this.niveauAventure + "\n";
        return str;
    }

    /**
     * Methode de saauvegarde de profil
     *
     * @param profil Le profil à sauvegarder
     */
    public static String sauvegarderProfil(Profil profil) {
        // Dossier ressources contenant les profils
        String cheminDossierRessourceProfils = Launcher.normaliserChemin(Launcher.dossierProfils + "/");
        File dossierRessourceProfils = new File(cheminDossierRessourceProfils);
        // Parcours les elements du dossier "Slitherlink/profils/" et verifie si un dossier du nom de l'utilisateur existe
        for (File dossierProfil : Objects.requireNonNull(dossierRessourceProfils.listFiles())) {
            if (dossierProfil.isDirectory() && dossierProfil.getName().equals(profil.getNom())) {
                return  "Ce profil existe déjà dans les données de Slitherlink";
            }
        }
        // Si le dossier n'existe pas, on le crée
        File dossierProfil = new File(cheminDossierRessourceProfils + profil.getNom());
        dossierProfil.mkdir();
        // On crée le dossier saves
        File dossierSaves = new File(cheminDossierRessourceProfils + profil.getNom() + "/saves");
        dossierSaves.mkdir();
        // On crée le fichier profilName.profil
        String cheminFichierProfil = Launcher.normaliserChemin(cheminDossierRessourceProfils+ profil.getNom() + "/" + profil.getNom() + ".profil");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichierProfil))) {
            oos.writeObject(profil);
            return "Profil sauvegardé avec succès";
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors de la sauvegarde du profil";
        }
    }

    /**
     * Methode de chargement de profil
     *
     * @param chemin Le chemin du profil à charger
     * @return le profil à charger
     */
    public static Profil chargerProfil(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
          return (Profil) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
