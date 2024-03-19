package model;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Cette classe mod\u00e8lise un profil d'utilisateur.
 * 
 * @author William Sardon
 */
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nom de l'utilisateur */
    private String nom;

    /** Chemin menant a l'image utilisee par defaut */
    private String cheminIMG;

    /** Niveau de progression dans le mode aventure */
    private int niveauAventure;

    /** Historique de jeu de l'utilisateur */
    private Historique historique;

    /** Parametre de l'utilisateur */
    private Parametre parametre;

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
        this.historique = new Historique();
        this.parametre = new Parametre();
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
    public void choisirImage() {
        JFileChooser selecteurFichiers = new JFileChooser();
        selecteurFichiers.setDialogTitle("Choisir une image de profil");

        // Afficher uniquement les fichiers images
        selecteurFichiers.setFileFilter(new javax.swing.filechooser.FileFilter() {
            // Condition d'acceptation par le filtre pour le fichier
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String extension = getExtension(f);
                // Accepte si l'extension est en jpg, jpeg ou png
                return extension != null && (extension.equals("jpg") || extension.equals("jpeg") ||
                        extension.equals("png"));
            }

            // Description du filtre
            public String getDescription() {
                return "Images (*.jpg, *.jpeg, *.png)";
            }

            // Recupère l'extension du fichier
            private String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }
        });

        int selectionUtilisateur = selecteurFichiers.showOpenDialog(null);
        if (selectionUtilisateur == JFileChooser.APPROVE_OPTION) {
            File fichierSelectionne = selecteurFichiers.getSelectedFile();
            String dossierDestination = "ressources/profil/" + this.nom;

            try {
                Path cheminSource = fichierSelectionne.toPath();
                Path cheminDestination = Paths.get(dossierDestination, fichierSelectionne.getName());
                Files.copy(cheminSource, cheminDestination);
                System.out.println("Le fichier a été copié avec succès dans " + cheminDestination.toString());
                cheminIMG = cheminDestination.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += "Nom : " + nom + "\n";
        str += "IMG : " + cheminIMG + "\n";
        str += "niveauAventure : " + niveauAventure + "\n";
        return str;
    }

    /**
     * Methode de saauvegarde de profil
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
     * Methode de chargement de profil
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
