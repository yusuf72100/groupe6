package groupe6.model.partie;

import groupe6.affichage.GridMenu;
import groupe6.launcher.Launcher;
import groupe6.model.partie.aide.HistoriqueAides;
import groupe6.model.partie.erreur.ResultatVerificationErreur;
import groupe6.model.partie.info.LimiteTemps;
import groupe6.model.partie.info.Score;
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
import groupe6.model.technique.ResultatTechnique;

import java.time.Duration;
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
  private final HistoriqueAides historiqueAide;

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
   * Le GridMenu qui observe la partie
   */
  private GridMenu gridMenu;

  /**
   * Constructeur de la classe Partie
   *
   * @param puzzle le puzzle sur lequel la partie est jouée
   * @param modeJeu le mode de jeu de la partie
   * @param profil le profil de l'utilisateur qui joue la partie
   */
  public Partie(Puzzle puzzle, ModeJeu modeJeu, Profil profil) {
    this.puzzle = puzzle;
    this.infos = new PartieInfos(
        Duration.ofMinutes(0),
        Score.getScoreDebut(this.puzzle.getDifficulte()),
        modeJeu,
        false,
        LimiteTemps.getLimiteTemps(this.puzzle.getDifficulte()),
        false
    );
    this.gestionnaireAction = new GestionnaireAction(this.puzzle);
    this.historiqueAide = new HistoriqueAides();
    this.profil = profil;
    this.hypothese = null;
    this.gestionnaireErreur = new GestionnaireErreur();
    this.chrono = new Chronometre();
    this.gridMenu = null;
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
    this.gridMenu = null;
  }

  /**
   * Méthode pour définir le GridMenu qui observe la partie
   *
   * @param gridMenu le GridMenu qui observe la partie
   */
  public void setObservateurGridMenu(GridMenu gridMenu) {
    this.gridMenu = gridMenu;
  }

  /**
   * Méthode pour notifier le GridMenu qu'il faut mettre à jour l'affichage
   */
  public void notifierObservateurUpdateAffichage() {
    if ( this.gridMenu != null ) {
      this.gridMenu.updateAffichage();
    }
  }

  /**
   * Méthode pour notifier le GridMenu qu'il faut mettre à jour le puzzle
   */
  public void notifierObservateurUpdatePuzzle() {
    if ( this.gridMenu != null ) {
      this.gridMenu.updatePuzzle();
    }
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
  public HistoriqueAides getHistoriqueAide() {
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
   * Méthode pour vérifier si une erreur a été commise
   *
   * @return le résultat de la vérification des erreurs (ResultatVerificationErreur)
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

      // Malus pour avoir demandé une vérification d'erreur
      this.infos.enleverPoints(Score.MALUS_VERIFICATION_ERREUR);

      return new ResultatVerificationErreur(true, setPremiereErreur, coordsCasesErrone);
    }
  }

  /**
   * Méthode pour obtenir le chronomètre de la partie
   *
   * @return le chronomètre de la partie
   */
  public Chronometre getChrono() {
    return chrono;
  }

  /**
   * Méthode pour obtenir le score de la partie
   *
   * @return le score de la partie
   */
  public int getScore() {
    return this.infos.getScore();
  }

  /**
   * Méthode pour verifier si le temps est écoulé
   *
   * @return vrai si le temps est écoulé et que le mode de jeu est contre-la-montre, faux sinon
   */
  public boolean verifierTemps() {
    return this.infos.getModeJeu() == ModeJeu.CONTRELAMONTRE &&
        this.infos.getChrono().compareTo(this.infos.getLimiteTemps()) >= 0;
  }

  /**
   * Méthode pour revenir en arrière avant la première erreur commise
   */
  public void corrigerErreur() {
    ErreurInfos premiereErreur = this.gestionnaireErreur.getPremiereErreur();
    this.getGestionnaireAction().annulerActionApresIndice(premiereErreur.getIndexAction());
    this.gestionnaireErreur.supprimerErreurs();
    // Sauvegarde de la partie
    this.sauvegarder();

    // Notifier d'un besoin d'update de l'affichage
    this.notifierObservateurUpdateAffichage();
  }

  /**
   * Méthode pour demander une aide (détection de technique)
   *
   * @return le résultat de la recherche de l'aide (ResultatTechnique)
   */
  public AideInfos chercherAide() {
    ResultatTechnique result = GestionnaireTechnique.getInstance().rechercheAideTechnique(this);

    if ( result.isTechniqueTrouvee() ) {
      // Malus pour avoir demandé une aide de niveau 1
      this.infos.enleverPoints(Score.MALUSE_AIDE_NIVEAU_1);
      // Ajout de l'aide détectée dans l'historique des aides
      AideInfos aide = new AideInfos(result);
      this.historiqueAide.ajouterAide(aide);
      return aide;
    }

    return null;
  }

  /**
   * Méthode pour completer automatiquement les croix d'une case au nombre de traits maximum
   *
   * @param action l'action effectuée par l'utilisateur
   */
  public void autoCompletionCroix(Action action) {

    if ( action.getNouvelleValeurCote() != ValeurCote.TRAIT ) {
      return;
    }

    Coordonnee coordsCell1 = action.getCoordsCellule1();
    Coordonnee coordsCell2 = puzzle.getCoordoneeAdjacente(coordsCell1.getY(),coordsCell1.getX(),action.getCoteCellule1());

    Cellule cellule1 = puzzle.getCellule(coordsCell1.getY(),coordsCell1.getX());
    Cellule cellule2 = null;
    if ( coordsCell2 != null ) {
      cellule2 = puzzle.getCellule(coordsCell2.getY(),coordsCell2.getX());
    }

    boolean besoinDeUpdate = false;
    if ( cellule1.maxTrait() ) {
      completerCroix(cellule1,coordsCell1);
      besoinDeUpdate = true;
    }
    if ( cellule2 != null && cellule2.maxTrait() ) {
      completerCroix(cellule2,coordsCell2);
      besoinDeUpdate = true;
    }

    // Notifie si besoin d'un update de l'affichage
    if ( besoinDeUpdate ) {
      this.notifierObservateurUpdateAffichage();
    }

  }

  /**
   * Méthode privée pour completer les croix d'une cellule si elle a atteint son nombre maximal de traits
   *
   * @param cellule la cellule à completer
   * @param coordsCellule les coordonnées de la cellule
   */
  private void completerCroix(Cellule cellule, Coordonnee coordsCellule ) {
    ValeurCote[] cotes = cellule.getCotes();
    for (int i = 0; i < cotes.length; i++) {
      if ( cotes[i] == ValeurCote.VIDE ) {
        int y = coordsCellule.getY();
        int x = coordsCellule.getX();
        Cellule cellule1 = puzzle.getCellule(y,x);
        Cellule cellule2 = puzzle.getCelluleAdjacente(y,x, i);

        Action action = new Action(cellule1, cellule2, i, ValeurCote.CROIX, new Coordonnee(y,x));
        gestionnaireAction.ajouterAction(action);
        action.appliquerAction();
      }
    }
  }

  /**
   * Méthode pour detecter une erreur causée par une action
   *
   * @param action l'action effectuée par l'utilisateur
   */
  private void detectionErreur(Action action) {
    Coordonnee coordsCell1 = action.getCoordsCellule1();
    Coordonnee coordsCell2 = puzzle.getCoordoneeAdjacente(coordsCell1.getY(),coordsCell1.getX(),action.getCoteCellule1());

    int coteCell1 = action.getCoteCellule1();
    Cellule cellSol1 = this.puzzle.getCelluleSolution(coordsCell1.getY(),coordsCell1.getX());
    Cellule cellJeu1 = this.puzzle.getCellule(coordsCell1.getY(),coordsCell1.getX());

    boolean valide;
    if ( coordsCell2 != null ) {
      Cellule cellSol2 = this.puzzle.getCelluleSolution(coordsCell2.getY(),coordsCell2.getX());
      Cellule cellJeu2 = this.puzzle.getCellule(coordsCell2.getY(),coordsCell2.getX());
      int coteCell2 = Cellule.getCoteAdjacent(coteCell1);

      valide = Cellule.compareValeurCote(cellSol1.getCote(coteCell1),cellJeu1.getCote(coteCell1)) &&
               Cellule.compareValeurCote(cellSol2.getCote(coteCell2),cellJeu2.getCote(coteCell2));
    }
    else {
      valide = Cellule.compareValeurCote(cellSol1.getCote(coteCell1),cellJeu1.getCote(coteCell1));
    }

    // Si une erreur existe deja sur ce côté de cellule, et que l'erreur a été corrigé
    int idxErreurExistante = this.gestionnaireErreur.existe(action);
    if ( idxErreurExistante != -1 && valide ) {
      this.gestionnaireErreur.supprimer(idxErreurExistante);
    }

    // Si aucune erreur n'existe déja et que l'action n'est pas valide
    if ( idxErreurExistante == -1 && !valide ) {
      ErreurInfos erreur = new ErreurInfos(coordsCell1,coordsCell2, action.getCoteCellule1(), this.gestionnaireAction.getIndex());
      this.getGestionnaireErreur().ajouterErreur(erreur);
    }

  }

  /**
   * Méthode qui execute les différents comportements qui doivent être effectués pour chaque action
   *
   * @param action l'action effectuée par l'utilisateur
   */
  private void pourChaqueAction(Action action) {
    // Suppression des actions suivantes a l'index courant (plus de redo)
    this.gestionnaireAction.effacerActionsSuivantes();
    // Ajout de l'action dans le gestionnaire d'actions
    this.gestionnaireAction.ajouterAction(action);
    // Application de l'action
    action.appliquerAction();
    // Detection d'erreur commise par l'utilisateur
    detectionErreur(action);
    // Auto-complétion des croix si l'option est activée
    if ( this.profil.getParametre().getAideRemplissageCroix() ) {
      autoCompletionCroix(action);
    }
    // Verification si la partie est terminée
    if ( !estTermine() ) {
      // Sauvegarde de la partie après chaque action
      this.sauvegarder();
    }
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

    pourChaqueAction(action);
  }

  /**
   * Méthode qui verifie si la partie est terminée (le puzzle est complet)
   *
   * @return vrai si la partie est terminée, faux sinon
   */
  public boolean estTermine() {
    if (
        this.infos.getModeJeu() == ModeJeu.CONTRELAMONTRE &&
        this.infos.getChrono().compareTo(this.infos.getLimiteTemps()) >= 0
    ) {
      // Obtenir la difficulté du puzzle pour le PartieFinieInfos
      DifficultePuzzle difficulte = this.puzzle.getDifficulte();

      // Update du chronometre
      this.infos.setChrono(this.chrono.getTempsEcoule());

      // Update indice completiton
      this.infos.setComplete(true);

      // Update indice gagnee
      this.infos.setGagnee(false);

      // Partie non complète et perdue, car temps limite atteint
      PartieFinieInfos partieFinieInfos = new PartieFinieInfos(this.infos, difficulte, this.puzzle.getLargeur(), this.puzzle.getLongueur());
      this.profil.getHistorique().addResultParties(partieFinieInfos);

      if ( Launcher.getVerbose() ) {
        System.out.println("Partie terminée : Le temps limite est atteint !");
      }

      return true;
    } else if (this.puzzle.estComplet()) {
      // Obtenir la difficulté du puzzle pour le PartieFinieInfos
      DifficultePuzzle difficulte = this.puzzle.getDifficulte();

      // Update du chronometre
      this.infos.setChrono(this.chrono.getTempsEcoule());

      // Update indice completiton
      this.infos.setComplete(true);

      // Update indice gagnee
      this.infos.setGagnee(true);

      PartieFinieInfos partieFinieInfos = new PartieFinieInfos(this.infos, difficulte, this.puzzle.getLargeur(), this.puzzle.getLongueur());
      this.profil.getHistorique().addResultParties(partieFinieInfos);

      if ( this.getInfos().getModeJeu() == ModeJeu.AVENTURE ) {
        if ( Launcher.cataloguePuzzles.estDernierPuzzleDeverrouille(difficulte.ordinal(),this.getPuzzle().getNumeroPuzzle(), this.profil) ) {
          this.profil.augmenterNiveauAventure();
        }
      }

      if ( Launcher.getVerbose() ) {
        System.out.println("Partie terminée : Le puzzle est complet !");
      }

      return true;
    } else {
      return false;
    }
  }

  /**
   * Méthode pour obtenir le booléen qui indique si la partie est terminée
   *
   * @return vrai si la partie est terminée, faux sinon
   */
  public boolean estComplet() {
    return this.infos.getComplete();
  }

  /**
   * Méthode pour annuler la dernière action effectuée
   */
  public void undo() {

    Action action = this.gestionnaireAction.annulerAction();
    if ( action != null ) {
      Action actionInverse = Action.inverserAction(action);
      detectionErreur(actionInverse);
    }

    // Suppression des erreurs si l'on est revenu à l'état de départ du puzzle
    if ( this.gestionnaireAction.getIndex() == -1 ) {
      this.gestionnaireErreur.supprimerErreurs();
    }

    // Sauvegarde de la partie après chaque action
    this.sauvegarder();

    // Notifier d'un besoin d'update de l'affichage
    this.notifierObservateurUpdateAffichage();
  }

  /**
   * Méthode pour rétablir la dernière action annulée
   */
  public void redo() {
    Action action = this.gestionnaireAction.retablirAction();
    if ( action != null ) {
      detectionErreur(action);
    }

    // Sauvegarde de la partie
    this.sauvegarder();

    // Notifier d'un besoin d'update de l'affichage
    this.notifierObservateurUpdateAffichage();
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
    this.hypothese = null;
  }

  /**
   * Méthode pour annuler son hypothèse
   */
  public void annulerHypothese() {
    this.puzzle = hypothese.getPuzzle();
    System.out.println("annuler hypothese");
    this.gestionnaireAction = hypothese.getGestionnaireAction();
    this.gestionnaireErreur = hypothese.getGestionnaireErreur();
    this.hypothese = null;

    // Notifier d'un besoin d'update du puzzle dans l'affichage
    this.notifierObservateurUpdatePuzzle();

    // Notifier d'un besoin d'update de l'affichage
    this.notifierObservateurUpdateAffichage();
  }

  /**
   * Méthode pour obtenir le booléen qui indique si le mode hypothèse est actif
   *
   * @return vrai si le mode hypothèse est actif, faux sinon
   */
  public boolean modeHypotheseActif() {
    return this.hypothese != null;
  }

  /**
   * Méthode pour sauvegarder la partie
   */
  public synchronized void sauvegarder() {
    // Lance un thread séparé pour sauvegarder la partie
    new Thread(() -> PartieSauvegarde.creerSauvegardePartie(this)).start();
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
    // Récupération du booléen qui indique si l'application des techniques de démarrage est activée
    boolean optionTechDemarage = profil.getParametre().getAideTechniqueDemarrage(difficulte);
    // Création d'un nouveau puzzle en fonction de la difficulté, du numéro et de l'option technique de démarrage
    Puzzle nvPuzzle = catalogue.getNouveauPuzzle(difficulte, numero, optionTechDemarage);
    // Création d'une nouvelle partie à partir du nouveau puzzle, du mode de jeu et du profil
    Partie partie = new Partie(nvPuzzle, modeJeu, profil);
    partie.sauvegarder();
    return partie;
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
