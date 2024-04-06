package groupe6.model.profil;

import groupe6.launcher.Launcher;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Cette classe modélise un profil d'utilisateur.
 *
 * @author William Sardon
 */
public class Profil implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Le nom de l'utilisateur
     */
    private final String nom;

    /**
     * Le chemin vers l'image utilisee dans le profil
     */
    private String cheminIMG;


    /**
     * Le niveaux de progression de l'utilisateur dans le mode aventure
     */
    private int niveauAventure;

    /**
     * L'historique des parties terminées par l'utilisateur ( gagnées ou perdues )
     */
    private final Historique historique;

    /**
     * Les parametres de l'utilisateur
     */
    private final Parametre parametre;

    /**
     * Constructeur de la classe Profil
     *
     * @param nom le nom du profil
     * @param cheminIMG le chemin vers l'image utilisee dans le profil
     */
    public Profil(String nom, String cheminIMG) {
        this.nom = nom;
        this.cheminIMG = cheminIMG;
        this.niveauAventure = 0;
        this.historique = new Historique();
        this.parametre = new Parametre();
    }

    /**
     * Méthode qui définit le niveau de progression de l'utilisateur dans le mode aventure
     *
     * @param niveauAventure le niveau de progression de l'utilisateur dans le mode aventure
     */
    public void setNiveauAventure(int niveauAventure) {
        // Modification du niveau de progression de l'utilisateur dans le mode aventure
        this.niveauAventure = niveauAventure;
        // Sauvegarde du profil dans un thread séparé
        new Thread(() -> sauvegarderProfil(this)).start();

    }

    /**
     * Méthode pour augmenter le niveau de progression de l'utilisateur dans le mode aventure
     */
    public void augmenterNiveauAventure() {
        // Augmentation du niveau de progression de l'utilisateur dans le mode aventure
        this.niveauAventure++;
        // Sauvegarde du profil dans un thread séparé
        new Thread(() -> sauvegarderProfil(this)).start();
    }

    /**
     * Méthode pour obtenir le nom du profil
     *
     * @return le nom du profil
     */
    public String getNom() {
        return nom;
    }

    /**
     * Methode pour obtenir le chemin vers l'image utilisee dans le profil
     *
     * @return le chemin vers l'image utilisee dans le profil
     */
    public String getIMG() {
        return cheminIMG;
    }

    /**
     * Methode pour obtenir le niveauAventure
     *
     * @return le niveauAventure
     */
    public int getNiveauAventure() {
        return niveauAventure;
    }

    /**
     * Methode pour obtenir l'historique des parties terminées par l'utilisateur
     *
     * @return l'historique des parties terminées par l'utilisateur
     */
    public Historique getHistorique() {
        return historique;
    }

    /**
     * Methode pour obtenir les parametres de l'utilisateur
     *
     * @return les parametres de l'utilisateur
     */
    public Parametre getParametre() {
        return parametre;
    }

    /**
     * Méthode pour changer l'image du profil
     *
     * @param fenetre la fenetre de l'application
     * @throws IOException si une erreur survient lors de la copie du fichier
     */
    public void choisirImage(Stage fenetre) throws IOException {
        // Afficher uniquement les fichiers images
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers IMAGE (*.jpeg, *.png, *.jpg)", "*.png", "*.jpeg", "*.jpg"));

        java.io.File file = fileChooser.showSaveDialog(fenetre);

        if (file != null) {
            String cheminDestination = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + this.nom + "/" + file.getName());

            Launcher.copyFile(file.getAbsolutePath(), cheminDestination);
            if ( Launcher.getVerbose() ) {
                System.out.println("Copie de l'image sélectionnée dans le dossier des ressources du profil :");
                System.out.println("  - Chemin source : " + file.getAbsolutePath());
                System.out.println("  - Chemin destination : " + cheminDestination);
            }

            cheminIMG = cheminDestination;
        }

        // Sauvegarde du profil dans un thread séparé
        new Thread(() -> sauvegarderProfil(this)).start();
    }

    /**
     * Méthode pour obtenir une représentation textuelle du profil
     *
     * @return la représentation textuelle du profil
     */
    @Override
    public String toString() {
        String str = "";
        str += "Nom : " + this.nom + "\n";
        str += "IMG : " + this.cheminIMG + "\n";
        str += "niveauAventure : " + this.niveauAventure + "\n";
        return str;
    }

    /**
     * Methode pour sauvegarder un profil
     *
     * @param profil le profil à sauvegarder
     */
    public static synchronized void sauvegarderProfil(Profil profil) {
        // Dossier ressources contenant les profils
        String cheminDossierRessourceProfils = Launcher.normaliserChemin(Launcher.dossierProfils + "/");
        File dossierRessourceProfils = new File(cheminDossierRessourceProfils);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode pour charger un profil
     *
     * @param chemin le chemin du fichier de sauvegarde du profil
     * @return le profil chargé
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
