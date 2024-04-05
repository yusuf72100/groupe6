package groupe6.model.partie;

import groupe6.launcher.Launcher;
import groupe6.model.partie.erreur.ResultatVerificationErreur;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.partie.puzzle.Coordonnee;
import groupe6.model.partie.puzzle.DifficultePuzzle;
import groupe6.model.partie.puzzle.Puzzle;
import groupe6.model.partie.puzzle.cellule.Cellule;
import groupe6.model.partie.puzzle.cellule.ValeurCote;
import groupe6.model.partie.action.Action;
import groupe6.model.partie.action.GestionnaireAction;
import groupe6.model.partie.aide.AideInfos;
import groupe6.model.partie.erreur.ErreurInfos;
import groupe6.model.partie.erreur.GestionnaireErreur;
import groupe6.model.partie.info.PartieFinieInfos;
import groupe6.model.partie.info.PartieInfos;
import groupe6.model.partie.sauvegarde.PartieSauvegarde;
import groupe6.model.profil.Profil;
import groupe6.model.technique.GestionnaireTechnique;

import java.util.*;


/**
 * Classe qui représente une partie de puzzle Slitherlink
 *
 * @author Yamis
 */
public class Partie {

  /**
   * Le puzzle sur lequel la partie est jouée
   */
  private Puzzle puzzle;

  /**
   * Les informations de la partie
   */
  private final PartieInfos infos;

  /**
   * Le gestionnaire des actions effectuées dans la partie
   */
  private GestionnaireAction gestionnaireAction;

  /**
   * L'historique des aides demandées par l'utilisateur
   */
  private final List<AideInfos> historiqueAide;

  /**
   * L'hypothèse en cours dans une partie
   */
  private Hypothese hypothese;

  /**
   * Le gestionnaire des erreurs commises par l'utilisateur dans la partie
   */
  private GestionnaireErreur gestionnaireErreur;

  /**
   * Le profil de l'utilisateur qui joue la partie
   */
  private final Profil profil;

  /**
   * Le chronomètre de la partie
   */
  private final Chronometre chrono;

  /**
   * Constructeur de la classe Partie
   *
   * @param puzzle le puzzle sur lequel la partie est jouée
   * @param modeJeu le mode de jeu de la partie
   * @param profil le profil de l'utilisateur qui joue la partie
   */
  public Partie(Puzzle puzzle, ModeJeu modeJeu, Profil profil) {
    this.puzzle = puzzle;
    this.infos = new PartieInfos(null, 0, modeJeu, null);
    this.gestionnaireAction = new GestionnaireAction(this.puzzle);
    this.historiqueAide = new ArrayList<AideInfos>();
    this.profil = profil;
    this.hypothese = null;
    this.gestionnaireErreur = new GestionnaireErreur();
    this.chrono = new Chronometre();
  }

  /**
   * Constructeur de la classe Partie
   *
   * @param save la sauvegarde de la partie à charger
   * @param profil le profil de l'utilisateur qui joue la partie
   */
  public Partie(PartieSauvegarde save, Profil profil) {
    this.puzzle = save.getPuzzle();
    this.infos = save.getInfos();
    this.gestionnaireAction = save.getGestionnaireAction().clone(this.puzzle);
    this.historiqueAide = save.getHistoriqueAide();
    this.profil = profil;
    this.hypothese = null;
    this.gestionnaireErreur = save.getGestionnaireErreur();
    this.chrono = new Chronometre(this.infos.getChrono());
  }

  /**
   * Méthode pour obtenir le puzzle de la partie
   *
   * @return le puzzle de la partie
   */
  public Puzzle getPuzzle() {
    return this.puzzle;
  }

  /**
   * Méthode pour obtenir les informations de la partie
   *
   * @return les informations de la partie
   */
  public PartieInfos getInfos() {
    return this.infos;
  }

  /**
   * Méthode pour obtenir l'historique des aides demandées par l'utilisateur
   *
   * @return l'historique des aides demandées par l'utilisateur
   */
  public List<AideInfos> getHistoriqueAide() {
    return this.historiqueAide;
  }

  /**
   * Méthode pour obtenir le profil de l'utilisateur qui joue la partie
   *
   * @return le profil de l'utilisateur qui joue la partie
   */
  public Profil getProfil() {
    return this.profil;
  }

  /**
   * Méthode pour obtenir le gestionnaire des actions effectuées dans la partie
   *
   * @return le gestionnaire des actions
   */
  public GestionnaireAction getGestionnaireAction() {
    return this.gestionnaireAction;
  }

  /**
   * Méthode pour obtenir le gestionnaire des erreurs commises par l'utilisateur dans la partie
   *
   * @return le gestionnaire des erreurs
   */
  public GestionnaireErreur getGestionnaireErreur() {
    return  this.gestionnaireErreur;
  }

  /**
   * Méthode pour veifier si une erreur a été commise
   *
   * @return le résultat de la vérification des erreurs ( ResultatVerificationErreur )
   */
  public ResultatVerificationErreur verifierErreur() {
    if ( this.gestionnaireErreur.estVide() ) {
      return new ResultatVerificationErreur(false, null, null);
    }else {
      ErreurInfos premiereErreur = this.gestionnaireErreur.getPremiereErreur();
      Set<Coordonnee> setPremiereErreur = new HashSet<Coordonnee>();
      setPremiereErreur.add(premiereErreur.getCoordonneeCell1());
      if ( premiereErreur.getCoordonneeCell2() != null ) {
        setPremiereErreur.add(premiereErreur.getCoordonneeCell2());
      }

      Set<Coordonnee> coordsCasesErrone  = this.getGestionnaireAction().getCoordsActionApresErreur(premiereErreur.getIndexAction());

      Iterator<Coordonnee> iterator = coordsCasesErrone.iterator();
      while (iterator.hasNext()) {
        Coordonnee coordCE = iterator.next();
        for (Coordonnee coordPE : setPremiereErreur) {
          if (coordCE.equals(coordPE)) {
            iterator.remove(); // Utilisation de l'itérateur pour supprimer l'élément
          }
        }
      }

      System.out.println("Set premiere erreur : "+setPremiereErreur);
      System.out.println("Set erreurs suivantes : "+coordsCasesErrone);

      return new ResultatVerificationErreur(true, setPremiereErreur, coordsCasesErrone);
    }
  }

  /**
   * Méthode pour revenir en arrière avant la première erreur commise
   */
  public void corrigerErreur() {
    ErreurInfos premiereErreur = this.gestionnaireErreur.getPremiereErreur();
    this.getGestionnaireAction().annulerActionApresErreur(premiereErreur.getIndexAction());
    this.gestionnaireErreur.supprimerErreurs();
  }

  /**
   * Méthode pour mettre en pause la partie
   */
  public void pause() {
    this.chrono.pause();
  }

  /**
   * Méthode pour reprendre la partie
   */
  public void reprendre() {
    this.chrono.reprendre();
  }

  /**
   * Méthode pour demander une aide ( detection de technique )
   */
  public void chercherAide() {
    GestionnaireTechnique.getInstance().rechercheAideTechnique(this);
  }

  /**
   * Méthode pour completer automatiquement les croix d'une case au nombre de trait maximum
   */
  public void autoCompletionCroix() {
    // TODO
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Méthode pour detecter une erreur causée par une action
   *
   * @param action l'action effectuée par l'utilisateur
   * @return vrai si l'action est valide, faux sinon
   */
  private boolean detectionErreur(Action action) {
    Coordonnee coordsCell1 = action.getCoordsCellule1();
    Coordonnee coordsCell2 = puzzle.getCoordoneeAdjacente(coordsCell1.getY(),coordsCell1.getX(),action.getCoteCellule1());

    Cellule cellSol1 = this.puzzle.getCelluleSolution(coordsCell1.getY(),coordsCell1.getY());
    Cellule cellJeu1 = this.puzzle.getCellule(coordsCell1.getY(),coordsCell1.getX());

    boolean valide;
    if ( coordsCell2 != null ) {
      Cellule cellSol2 = this.puzzle.getCelluleSolution(coordsCell2.getY(),coordsCell2.getY());
      Cellule cellJeu2 = this.puzzle.getCellule(coordsCell2.getY(),coordsCell2.getX());
      valide = cellSol1.equals(cellJeu1) && cellSol2.equals(cellJeu2);
    }
    else {
      valide = cellSol1.equals(cellJeu1);
    }

    // Si une erreur existe deja sur ce cote de cellule, et que l'erreur a été corigé
    int idxErreurExistante = this.gestionnaireErreur.existe(action);
    if ( idxErreurExistante != -1 && valide ) {
      if ( Launcher.getVerbose() ) {
        System.out.println("Correction de l'erreur :\n  - "+this.gestionnaireErreur.getErreur(idxErreurExistante)+" .");
      }
      this.gestionnaireErreur.supprimer(idxErreurExistante);
    }

    // Si aucune erreur existe déja et que l'action n'est pas valide
    if ( idxErreurExistante == -1 && !valide ) {
      ErreurInfos erreur = new ErreurInfos(coordsCell1,coordsCell2, action.getCoteCellule1(), this.gestionnaireAction.getIndex());
      this.getGestionnaireErreur().ajouterErreur(erreur);
      if ( Launcher.getVerbose() ) {
        System.out.println("Nouvelle erreur detectée :\n  - "+erreur);
      }
    }

    return valide;
  }

  /**
   * Méthode pour effectuer une action de type bascule à trois états
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param cote le côté de la cellule sur lequel l'action est effectuée
   */
  public void actionBasculeTroisEtat(int y, int x, int cote) {
    Cellule cellule1 = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);
    ValeurCote nouvelleValeurCote = cellule1.basculeTroisEtats(cote);

    Action action = new Action(cellule1, cellule2, cote, nouvelleValeurCote,new Coordonnee(y, x));
    if ( Launcher.getVerbose() ) {
      System.out.println("Nouvelle action bascule a trois etats :\n  - "+action);
    }

    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    Boolean err = detectionErreur(action);
  }

  /**
   * Méthode pour effectuer une action de type coté vide
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param cote le côté de la cellule sur lequel l'action est effectuée
   */
  public void actionVide(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.VIDE,new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    detectionErreur(action);
  }

  /**
   * Méthode pour effectuer une action de type coté trait
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param cote le côté de la cellule sur lequel l'action est effectuée
   */
  public void actionTrait(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.TRAIT,new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    detectionErreur(action);
  }

  /**
   * Méthode pour effectuer une action de type coté croix
   *
   * @param y la position en y de la cellule
   * @param x la position en x de la cellule
   * @param cote le côté de la cellule sur lequel l'action est effectuée
   */
  public void actionCroix(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.CROIX, new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    detectionErreur(action);
  }

  /**
   * Méthode qui verifie si la partie est terminée ( le puzzle est complet )
   *
   * @return vrai si la partie est terminée, faux sinon
   */
  private boolean estTermine() {
    if (this.puzzle.estComplet()) {
      this.infos.setChrono(this.chrono.getTempsEcoule());

      DifficultePuzzle difficulte = this.puzzle.getDifficulte();
      boolean gagnee = true;
      if (this.infos.getModeJeu() == ModeJeu.CONTRELAMONTRE &&
          this.infos.getChrono().compareTo(this.infos.getLimiteTemps()) >= 0) {
        gagnee = false;
      }

      PartieFinieInfos partieFinieInfos = new PartieFinieInfos(this.infos, difficulte, true, gagnee);
      this.profil.getHistorique().addResultParties(partieFinieInfos);

      return true;
    } else {
      return false;
    }
  }

  /**
   * Méthode pour annuler la dernière action effectuée
   */
  public void undo() {
    this.gestionnaireAction.annulerAction();
  }

  /**
   * Méthode pour rétablir la dernière action annulée
   */
  public void redo() {
    this.gestionnaireAction.retablirAction();
  }

  /**
   * Méthode pour obtenir une représentation textuelle de la partie
   *
   * @return une représentation textuelle de la partie
   */
  @Override
  public String toString() {
    return infos.toString() + puzzle.toString();
  }

  /**
   * Méthode pour obtenir l'hypothèse en cours
   *
   * @return l'hypothèse en cours
   */
  public Hypothese getHypothese() {
    return hypothese;
  }

  /**
   * Méthode pour activer le mode hypothèse
   */
  public void activerHypothese() {
    try {
      Puzzle puzzleClone = (Puzzle) puzzle.clone();
      GestionnaireAction gestionnaireActionClone = gestionnaireAction.clone(puzzleClone);
      GestionnaireErreur gestionnaireErreurClone = gestionnaireErreur.clone();
      this.hypothese = new Hypothese(puzzleClone, gestionnaireActionClone, gestionnaireErreurClone);
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * Méthode pour valider son hypothèse
   */
  public void validerHypothese(){
    this.puzzle = hypothese.getPuzzle();
    this.gestionnaireAction = hypothese.getGestionnaireAction();
    this.gestionnaireErreur = hypothese.getGestionnaireErreur();
  }

  /**
   * Méthode pour annuler son hypothèse
   */
  public void annulerHypothese(){
    this.hypothese = null;
  }

  /**
   * Méthode pour sauvegarder la partie
   */
  public void sauvegarder() {
    if ( Launcher.getVerbose() ) {
      System.out.println("Debut de la sauvegarde : "+this.getPuzzle().getDifficulte()+"_"+this.getPuzzle().getLargeur()+"x"+this.getPuzzle().getLongueur());
    }
    this.infos.setChrono(this.chrono.getTempsEcoule());
    PartieSauvegarde.creerSauvegardePartie(this);
  }

  /**
   * Méthode statique pour créer une nouvelle partie
   *
   * @param catalogue le catalogue des puzzles
   * @param difficulte la difficulté du puzzle lancé dans la partie
   * @param numero le numéro du puzzle lancé dans la partie
   * @param modeJeu le mode de jeu de la partie
   * @param profil le profil de l'utilisateur qui joue la partie
   * @return la nouvelle partie créée
   */
  public static Partie nouvellePartie(CataloguePuzzle catalogue, DifficultePuzzle difficulte, int numero,
                                      ModeJeu modeJeu, Profil profil
  ) {
    Puzzle puzzleVide = catalogue.getCopyPuzzle(difficulte, numero);
    return new Partie(puzzleVide, modeJeu, profil);
  }

  /**
   * Méthode statique pour charger une partie à partir d'une sauvegarde
   *
   * @param save la sauvegarde de la partie à charger
   * @param profil le profil de l'utilisateur qui joue la partie
   * @return la partie chargée
   */
  public static Partie chargerPartie(PartieSauvegarde save, Profil profil) {
    return new Partie(save, profil);
  }
}
