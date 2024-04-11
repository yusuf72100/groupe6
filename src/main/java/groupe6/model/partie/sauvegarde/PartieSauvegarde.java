package groupe6.model.partie.sauvegarde;

import groupe6.launcher.Launcher;
import groupe6.model.partie.ModeJeu;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.erreur.GestionnaireErreur;
import groupe6.model.partie.Partie;
import groupe6.model.partie.info.PartieInfos;
import groupe6.model.partie.action.GestionnaireAction;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.profil.Profil;
import groupe6.model.partie.puzzle.Puzzle;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Classe qui représente une sauvegarde de partie
 *
 * @author Yamis
 */
public class PartieSauvegarde implements Serializable {

  /**
   * Numéro de version de la sérialisation
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Le puzzle sur lequel la partie est jouée
   */
  private final Puzzle puzzle;

  /**
   * Les informations de la partie
   */
  private final PartieInfos infos;

  /**
   * Le gestionnaire des actions effectuées dans la partie
   */
  private final GestionnaireAction gestionnaireAction;

  /**
   * L'historique des aides demandées par l'utilisateur
   */
  private final HistoriqueAides historiqueAide;

  /**
   * Le gestionnaire des erreurs commises par l'utilisateur dans la partie
   */
  private final GestionnaireErreur gestionnaireErreur;

  /**
   * Constructeur de la classe PartieSauvegarde
   *
   * @param puzzle le puzzle sur lequel la partie est jouée
   * @param infos les informations de la partie
   * @param gestionnaireAction le gestionnaire des actions effectuées dans la partie
   * @param historiqueAide l'historique des aides demandées par l'utilisateur
   * @param gestionnaireErreur le gestionnaire des erreurs commises par l'utilisateur dans la partie
   */
  public PartieSauvegarde(Puzzle puzzle, PartieInfos infos, GestionnaireAction gestionnaireAction,
                          HistoriqueAides historiqueAide, GestionnaireErreur gestionnaireErreur
  ) {
    this.puzzle = puzzle;
    this.infos = infos;
    this.gestionnaireAction = gestionnaireAction;
    this.historiqueAide = historiqueAide;
    this.gestionnaireErreur = gestionnaireErreur;
  }

  /**
   * Méthode pour obtenir le puzzle de la partie
   *
   * @return le puzzle de la partie
   */
  public Puzzle getPuzzle() {
    return puzzle;
  }

  /**
   * Méthode pour obtenir les informations de la partie
   *
   * @return les informations de la partie
   */
  public PartieInfos getInfos() {
    return infos;
  }

  /**
   * Méthode pour obtenir le gestionnaire des actions effectuées dans la partie
   *
   * @return le gestionnaire des actions
   */
  public GestionnaireAction getGestionnaireAction() {
    return gestionnaireAction;
  }

  /**
   * Méthode pour obtenir l'historique des aides demandées par l'utilisateur
   *
   * @return l'historique des aides
   */
  public HistoriqueAides getHistoriqueAide() {
    return historiqueAide;
  }

  /**
   * Méthode pour obtenir le gestionnaire des erreurs commises par l'utilisateur
   *
   * @return le gestionnaire des erreurs
   */
  public GestionnaireErreur getGestionnaireErreur() {
    return gestionnaireErreur;
  }

  /**
   * Méthode pour obtenir le nom de la sauvegarde
   *
   * @param partie la partie dont on veut obtenir le nom de la sauvegarde
   * @return le nom de la sauvegarde
   */
  public static String getNomSauvegarde(Partie partie) {
    return getNomSauvegarde(
      partie.getPuzzle().getDifficulte(),
      partie.getInfos().getModeJeu(),
      partie.getPuzzle().getLargeur(),
      partie.getPuzzle().getLongueur(),
      partie.getInfos().getDate()
    );
  }

  /**
   * Méthode pour obtenir le nom de la sauvegarde d'une partie
   *
   * @param difficulte la difficulté du puzzle
   * @param modeJeu le mode de jeu de la partie
   * @param largeur la largeur du puzzle
   * @param longueur la longueur du puzzle
   * @param date la date de la partie
   * @return
   */
  public static String getNomSauvegarde(
      DifficultePuzzle difficulte, ModeJeu modeJeu, int largeur, int longueur, Date date
  ) {
    return
      difficulte.toString() + "_" +
      modeJeu.toString() + "_" +
      largeur + "x" + longueur + "_" +
      PartieInfos.dateToStringTiret(date);
  }

  /**
   * Méthode statique pour créer une sauvegarde d'une partie
   *
   * @param partie la partie à sauvegarder
   */
  public static synchronized void creerSauvegardePartie(Partie partie) {
    PartieSauvegarde save = new PartieSauvegarde(
        partie.getPuzzle(),
        partie.getInfos(),
        partie.getGestionnaireAction(),
        partie.getHistoriqueAide(),
        partie.getGestionnaireErreur()
    );

    String cheminDossier = Launcher.normaliserChemin(Launcher.dossierProfils + "/" + partie.getProfil().getNom() + "/saves/");

    String nomFichier = PartieSauvegarde.getNomSauvegarde(partie) + ".save";

    String cheminFichier = cheminDossier + nomFichier;
    try {
      FileOutputStream fileOut = new FileOutputStream(cheminFichier);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(save);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      i.printStackTrace();
    }

    if ( Launcher.getVerbose() ) {
      System.out.println("Fin de la sauvegarde : " + nomFichier);
    }

  }

  /**
   * Méthode statique pour charger une sauvegarde de partie
   *
   * @param nomPartie le nom de la sauvegarde à charger
   * @param profil le profil du joueur
   * @return la sauvegarde de la partie
   */
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
