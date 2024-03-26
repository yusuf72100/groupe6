package groupe6.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui modélise un catalogue de sauvegardes
 *
 * @author Yamis
 */

public class CatalogueSauvegarde {

  private final List<PartieSauvegarde> catalogueSaves;

  public CatalogueSauvegarde() {
    this.catalogueSaves = new ArrayList<PartieSauvegarde>();
  }

  public void ajouterSave(PartieSauvegarde save) {
    this.catalogueSaves.add(save);
  }

  public List<PartieSauvegarde> getCatalogueSaves() {
    return this.catalogueSaves;
  }

  public PartieSauvegarde getSave(int index) {
    return this.catalogueSaves.get(index);
  }

  public static List<String> listerSauvegarde(Profil profil) {
    List<String> listeSaves = new ArrayList<String>();

    String cheminDossier = "ressources/profils/" + profil.getNom() + "/saves/";
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
}
