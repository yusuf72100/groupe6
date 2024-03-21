package groupe6.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @author Yamis
 */

public class PartieSauvegarde implements Serializable {

  private static final long serialVersionUID = 1L;

  private Puzzle puzzle; // Le puzzle qui correspond à la partie
  private PartieInfos infos; // Les informations de la partie
  private GestionnaireAction gestionnaireAction; // Gestionnaire d'actions
  private List<AideInfos> historiqueAide; // Historique des aides

  public PartieSauvegarde(Puzzle puzzle, PartieInfos infos, GestionnaireAction gestionnaireAction,
      List<AideInfos> historiqueAide) {
    this.puzzle = puzzle;
    this.infos = infos;
    this.gestionnaireAction = gestionnaireAction;
    this.historiqueAide = historiqueAide;
  }

  public Puzzle getPuzzle() {
    return puzzle;
  }

  public PartieInfos getInfos() {
    return infos;
  }

  public GestionnaireAction getGestionnaireAction() {
    return gestionnaireAction;
  }

  public List<AideInfos> getHistoriqueAide() {
    return historiqueAide;
  }

  public static void creerSauvegardePartie(Partie partie) {
    PartieSauvegarde save = new PartieSauvegarde(
        partie.getPuzzle(),
        partie.getInfos(),
        partie.getGestionnaireAction(),
        partie.getHistoriqueAide());

    String cheminDossier = "ressources/profils/" + partie.getProfil().getNom() + "/saves/";

    StringBuilder nomFichier = new StringBuilder("");
    nomFichier.append(partie.getPuzzle().getDifficulte().toString());
    nomFichier.append("_");
    nomFichier.append(partie.getPuzzle().getLargeur() + "x" + partie.getPuzzle().getLongueur());
    nomFichier.append("_");
    nomFichier.append(partie.getInfos().dateToStringTiret());
    nomFichier.append(".save");

    String cheminFichier = cheminDossier + nomFichier.toString();
    try {
      FileOutputStream fileOut = new FileOutputStream(cheminFichier);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(save);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      i.printStackTrace();
    }

  }

  public static PartieSauvegarde chargerSauvegarde(String cheminFichier) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
      PartieSauvegarde save = (PartieSauvegarde) ois.readObject();
      return save;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public  static PartieSauvegarde chargerSauvegarde(String nomPartie, Profil profil) {
    String cheminDossier = "ressources/profils/" + profil.getNom() + "/saves/";
    return PartieSauvegarde.chargerSauvegarde(cheminDossier + nomPartie + ".save");
  }

}
