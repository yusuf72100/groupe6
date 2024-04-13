package groupe6.model.partie.sauvegarde;

import groupe6.launcher.Launcher;
import groupe6.model.partie.info.PartieFinieInfos;
import groupe6.model.profil.Profil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

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

    // Récupérer les noms des fichiers de sauvegardes
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

    // Trier la liste des sauvegardes par date la plus récente
    listeSaves.sort((s1, s2) -> {
      Date date1 = extraireDateSauvegarde(s1);
      Date date2 = extraireDateSauvegarde(s2);
      return date2.compareTo(date1);
    });


    return listeSaves;
  }

  public static Date extraireDateSauvegarde(String nomSauvegarde) {
    // Extraire les informations de la sauvegarde
    String[] infos = nomSauvegarde.split("_");
    String jour = infos[3];
    String numeroMois = infos[4];
    String annee = infos[5];
    String heure = infos[6];
    String minute = infos[7];
    String seconde = infos[8];

    // Concaténer les informations de la date
    String dateString = jour + "-" + numeroMois + "-" + annee + " " + heure + ":" + minute + ":" + seconde;

    // Créer un objet SimpleDateFormat pour le format de date attendu
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    try {
      // Parse la chaîne de date en objet Date
      Date date = formatter.parse(dateString);
      return date;
    } catch (ParseException e) {
      // Gérer l'exception de parsing
      e.printStackTrace();
      return null;
    }

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
