package groupe6.model.partie.sauvegarde;

import groupe6.launcher.Launcher;
import groupe6.model.partie.info.PartieFinieInfos;
import groupe6.model.profil.Profil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui modélise un catalogue de sauvegardes
 *
 * @author Yamis
 */
public class CatalogueSauvegarde {

  /**
   * La liste des parties sauvegardées dans le catalogue
   */
  private final List<PartieSauvegarde> catalogueSaves;

  /**
   * Constructeur de la classe CatalogueSauvegarde
   */
  public CatalogueSauvegarde() {
    this.catalogueSaves = new ArrayList<PartieSauvegarde>();
  }

  /**
   * Méthode pour ajouter une sauvegarde de partie au catalogue
   *
   * @param save la sauvegarde de partie à ajouter
   */
  public void ajouterSave(PartieSauvegarde save) {
    this.catalogueSaves.add(save);
  }

  /**
   * Méthode pour obtenir la liste des sauvegardes de parties
   *
   * @return la liste des sauvegardes de parties
   */
  public List<PartieSauvegarde> getCatalogueSaves() {
    return this.catalogueSaves;
  }

  /**
   * Méthode pour obtenir une sauvegarde de partie du catalogue
   *
   * @param index l'index de la sauvegarde à obtenir
   * @return la sauvegarde de partie à l'index donné
   */
  public PartieSauvegarde getSave(int index) {
    return this.catalogueSaves.get(index);
  }

  /**
   * Méthode pour obtenir la liste des parties sauvegardées d'un profil
   *
   * @param profil le profil dont on veut obtenir les sauvegardes
   * @return la liste des parties sauvegardées du profil
   */
  public static List<String> listerSauvegarde(Profil profil) {
    List<String> listeSaves = new ArrayList<String>();

    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + profil.getNom() + "/saves/");
    File dossierSauvegardes = new File(cheminDossier);
    File[] fichiersSauvegardes = dossierSauvegardes.listFiles();
    for (File fichier : Objects.requireNonNull(fichiersSauvegardes)) {
      if (fichier.getName().endsWith(".save")) {
        // Ajouter a la liste le nom du fichier sans .save
        String nom = fichier.getName().substring(0, fichier.getName().length() - 5); //
        listeSaves.add(nom);
      }
    }

    return listeSaves;
  }

  public static void suppimerAnciennesSauvegardes(Profil profil, PartieFinieInfos partieFinieInfos) {
    // Récupérer les fichiers de sauvegardes
    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + profil.getNom() + "/saves/");
    File dossierSauvegardes = new File(cheminDossier);
    File[] fichiersSauvegardes = dossierSauvegardes.listFiles();

    // Nom attendu de la sauvegarde
    String nomSauvegarde = PartieFinieInfos.getNomSauvegarde(partieFinieInfos);

    // Parcourir les fichiers de sauvegardes a la recherche de la sauvegarde à supprimer
    for (File fichier : Objects.requireNonNull(fichiersSauvegardes)) {
      if (fichier.getName().endsWith(".save")) {
        // Ajouter à la liste le nom du fichier sans .save
        String nom = fichier.getName().substring(0, fichier.getName().length() - 5); //
        if (nom.equals(nomSauvegarde)) {
          fichier.delete();
        }
      }
    }
  }
}
