package groupe6.model;

import groupe6.launcher.Launcher;

import java.io.*;
import java.util.List;

/**
 * Classe qui représente une sauvegarde de partie
 *
 * @author Yamis
 */

public class PartieSauvegarde implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Puzzle puzzle; // Le puzzle qui correspond à la partie
  private final PartieInfos infos; // Les informations de la partie
  private final GestionnaireAction gestionnaireAction; // Gestionnaire d'actions
  private final List<AideInfos> historiqueAide; // Historique des aides
  private final GestionnaireErreur gestionnaireErreur; // Gestionnaire des erreurs

  public PartieSauvegarde(Puzzle puzzle, PartieInfos infos, GestionnaireAction gestionnaireAction,
                          List<AideInfos> historiqueAide, GestionnaireErreur gestionnaireErreur
  ) {
    this.puzzle = puzzle;
    this.infos = infos;
    this.gestionnaireAction = gestionnaireAction;
    this.historiqueAide = historiqueAide;
    this.gestionnaireErreur = gestionnaireErreur;
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

  public GestionnaireErreur getGestionnaireErreur() {
    return gestionnaireErreur;
  }

  public static void creerSauvegardePartie(Partie partie) {
    PartieSauvegarde save = new PartieSauvegarde(
        partie.getPuzzle(),
        partie.getInfos(),
        partie.getGestionnaireAction(),
        partie.getHistoriqueAide(),
        partie.getGestionnaireErreur()
    );

    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + partie.getProfil().getNom() + "/saves/");

    StringBuilder nomFichier = new StringBuilder();
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

    System.out.println("Fin de la sauvegarde : " + nomFichier);

  }

  public  static PartieSauvegarde chargerSauvegarde(String nomPartie, Profil profil) {
    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + profil.getNom() + "/saves/");
    String cheminSauvegarde = Launcher.normaliserChemin(cheminDossier + nomPartie + ".save");

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminSauvegarde))) {
      return (PartieSauvegarde) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

}
