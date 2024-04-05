package groupe6.model.profil;

import groupe6.launcher.Launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Cette classe modélise un catalogue de profils
 *
 * @author William Sardon
 */
public class CatalogueProfil {

  /**
   * Liste des profils dans le catalogue
   */
  private static List<Profil> listeProfil;

  /**
   * Le profil actuelement utilisé
   */
  private Profil profilActuel;

  /**
   * Constructeur de la classe CatalogueProfil
   */
  public CatalogueProfil() {
    listeProfil = new ArrayList<Profil>();
    this.profilActuel = null;
  }

  /**
   * Méthode pour obtenir la liste des profils
   *
   * @return la liste des profils
   */
  public List<Profil> getListeProfils() {
      return listeProfil;
  }

  /**
   * Méthode pour ajouter un profil au catalogue
   *
   * @param profil le profil à ajouter
   */
  public void ajouterProfil(Profil profil) {
    if(profil!=null) {
      listeProfil.add(profil);
      if ( Launcher.getVerbose() ) {
        System.out.println("Le profil " + profil.getNom() + " a été ajouté au catalogue des profils.");
      }
    } else {
      System.out.println("Erreur : le profil n'a pas pu être ajouté au catalogue des profils");
    }
  }

  /**
   * Méthode pour obtenir un profil à partir de son nom
   *
   * @param nom le nom du profil à obtenir
   * @return le profil correspondant au nom
   */
  public Profil getProfilByName(String nom) {
    for (Profil profil : listeProfil) {
      if (profil.getNom().equals(nom)) {
        return profil;
      }
    }
    return null;
  }

  /**
   * Méthode pour obtenir le profil actuelement utilisé
   *
   * @return le profil actuelement utilisé
   */
  public Profil getProfilActuel() {
    return profilActuel;
  }

  /**
   * Méthode pour définir le profil actuelement utilisé
   *
   * @param profil le profil actuelement utilisé
   */
  public void setProfilActuel(Profil profil) {
    this.profilActuel = profil;
  }

  /**
   * Méthode pour obtenir une représentation textuelle du catalogue de profils
   *
   * @return une représentation textuelle du catalogue de profils
   */
  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("Liste des profils :\n");
    for (Profil profil : listeProfil) {
      strBuilder.append(profil.toString());
      strBuilder.append("\n");
    }
    strBuilder.append("Profil actuel : ");
    strBuilder.append(profilActuel.getNom());
    return strBuilder.toString();
  }

  /**
   * Méthode statique pour charger le catalogue de profils
   *
   * @return le catalogue avec les profils chargés
   */
  public static CatalogueProfil chargerCatalogueProfil() {
    CatalogueProfil catalogueProfil = new CatalogueProfil();

    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils+"/");

    // Dossier ressources contenant les profils
    File dossierRessourceProfils = new File(cheminDossier);

    File[] listeDossierProfils = dossierRessourceProfils.listFiles();

    // Parcours les elements du dossier "Slitherlink/profils/"
    for (File dossierProfil : Objects.requireNonNull(listeDossierProfils)) {
      if (dossierProfil.isDirectory()) {
        // Parcours les elements du dossier "Slitherlink/profils/nomProfil/saves/"
        for (File file : Objects.requireNonNull(dossierProfil.listFiles())) {
          // File termine par .profil
          if (file.getName().endsWith(".profil")) {
            // On charge le profil
            Profil p = Profil.chargerProfil(file.getAbsolutePath());
            catalogueProfil.ajouterProfil(p);
          }
        }
      }
    }
    // Verifie si au moins un profil a été chargé
    if (catalogueProfil.getListeProfils().isEmpty()) {
      throw new RuntimeException("Erreur : aucun profil n'a été trouvé");
    }

    // Ouvre le fichier profilActuel.config
    String cheminFichierProfilActuel = Launcher.normaliserChemin(Launcher.dossierProfils + "/profilActuel.config");
    File profilActuelConfig = new File(cheminFichierProfilActuel);
    if (profilActuelConfig.exists()) {
      try {
        // On lit le contenu du fichier profilActuel.config et on met le texte dans nomProfilActuel
        Scanner scanner = new Scanner(profilActuelConfig);
        String nomProfilActuel = scanner.nextLine();
        scanner.close();

        Profil defaut = null;
        for (Profil profil : catalogueProfil.getListeProfils()) {
          if (profil.getNom().equals(nomProfilActuel)) {
            catalogueProfil.setProfilActuel(profil);
          }
          if (profil.getNom().equals("utilisateur")) {
            defaut = profil;
          }
        }
        if (catalogueProfil.getProfilActuel() == null) {
          if (defaut != null) {
            catalogueProfil.setProfilActuel(defaut);
          } else {
            catalogueProfil.setProfilActuel(catalogueProfil.getListeProfils().get(0));
          }
        }
      } catch (FileNotFoundException e) {
        System.err.println("Erreur : Impossible de lire le fichier profilActuel.config");
        e.printStackTrace();
      }
    } else {
      System.err.println("Erreur : fichier profilActuel.config non trouvé");
    }

    return catalogueProfil;
  }

  /**
   * Méthode statique pour sauvegarder le profil actuel
   *
   * @param catalogueProfil le catalogue de profils
   */
  public static void sauvegarderProfilActuel(CatalogueProfil catalogueProfil) {
    try {
      String cheminFichierProfilActuel = Launcher.normaliserChemin(Launcher.dossierProfils + "/profilActuel.config");
      // Fichier ou sera sauvegardé le profil actuel
      File profilActuelConfig = new File(cheminFichierProfilActuel);
      // Créer le fichier profilActuel.config s'il n'existe pas
      if (!profilActuelConfig.exists()) {
        profilActuelConfig.createNewFile();
      }
      // Ecrire dans le fichier profilActuel.config le nom du profil actuel
      FileWriter writer = new FileWriter(profilActuelConfig);
      writer.write(catalogueProfil.getProfilActuel().getNom());
      writer.close();
    } catch (FileNotFoundException e) {
      System.err.println("Erreur : Impossible de lire le fichier profilActuel.config");
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("Erreur : Impossible de créer le fichier profilActuel.config");
      e.printStackTrace();
    }
  }

}
