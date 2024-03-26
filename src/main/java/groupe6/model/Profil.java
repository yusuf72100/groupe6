package groupe6.model;

import groupe6.launcher.Launcher;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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
    public void choisirImage() {
        // TODO : Probleme copie de l'image selectionnee
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
            String dossierDestination = "Slitherlink/profils/" + this.nom + File.separator;
            String cheminDestination = Launcher.normaliserChemin(dossierDestination + fichierSelectionne.getName());
            System.out.println("cheminDestination : "+cheminDestination);
            File destination = new File(cheminDestination);
            System.out.println(destination.getAbsolutePath());
            Launcher.copier(fichierSelectionne, destination);
            System.out.println("Le fichier a été copié avec succès dans " + cheminDestination);
            cheminIMG = cheminDestination;
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
     * @param profil Le profil à sauvegarder
     */
    public static String sauvegarderProfil(Profil profil) {
        // Dossier ressources contenant les profils
        String cheminDossierRessourceProfils = "Slitherlink/profils/";
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
        String cheminFichierProfil = cheminDossierRessourceProfils + profil.getNom() + "/" + profil.getNom() + ".profil";
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
