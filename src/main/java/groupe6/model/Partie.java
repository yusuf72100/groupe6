package groupe6.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yamis
 */

public class Partie {

  private Puzzle puzzle; // Le puzzle qui correspond à la partie
  private final PartieInfos infos; // Les informations de la partie
  private GestionnaireAction gestionnaireAction; // Gestionnaire d'actions
  private final List<AideInfos> historiqueAide; // Historique des aides
  private Hypothese hypothese; // Hypothese en cours
  private GestionnaireErreur gestionnaireErreur; // Gestionnaire des erreurs pour le check
  private Coordonnee coordsErreur; // Coordonées de la première erreur

  private final Profil profil; // Le profil qui joue la partie

  public Partie(Puzzle puzzle, ModeJeu modeJeu, Profil profil) {
    this.puzzle = puzzle;
    this.infos = new PartieInfos(null, 0, modeJeu, null);
    this.gestionnaireAction = new GestionnaireAction(this.puzzle);
    this.historiqueAide = new ArrayList<AideInfos>();
    this.profil = profil;
    this.hypothese = null;
    this.gestionnaireErreur = new GestionnaireErreur();
  }

  public Partie(PartieSauvegarde save, Profil profil) {
    this.puzzle = save.getPuzzle();
    this.infos = save.getInfos();
    this.gestionnaireAction = save.getGestionnaireAction().clone(this.puzzle);
    this.historiqueAide = save.getHistoriqueAide();
    this.profil = profil;
    this.hypothese = null;
    this.gestionnaireErreur = save.getGestionnaireErreur();
  }

  public Puzzle getPuzzle() {
    return this.puzzle;
  }

  public PartieInfos getInfos() {
    return this.infos;
  }

  public List<AideInfos> getHistoriqueAide() {
    return this.historiqueAide;
  }

  public Profil getProfil() {
    return this.profil;
  }

  public GestionnaireAction getGestionnaireAction() {
    return this.gestionnaireAction;
  }

  public GestionnaireErreur getGestionnaireErreur() { return  this.gestionnaireErreur; }

  public boolean verifierErreur(Action action) {
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
      System.out.println("correction err : \n"+this.gestionnaireErreur.getErreur(idxErreurExistante));
      this.gestionnaireErreur.supprimer(idxErreurExistante);
    }

    // Si aucune erreur existe déja et que l'action n'est pas valide
    if ( idxErreurExistante == -1 && !valide ) {
      ErreurInfos erreur = new ErreurInfos(coordsCell1,coordsCell2, action.getCoteCellule1(), this.gestionnaireAction.getIndex());
      this.getGestionnaireErreur().ajouterErreur(erreur);
      System.out.println("ajout err : \n"+erreur);
    }

    System.out.println(getGestionnaireErreur().getPremiereErreur());

    return valide;
  }

  // Méthode pour faire une action de type bascule à trois etats
  public void actionBasculeTroisEtat(int y, int x, int cote) {
    Cellule cellule1 = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);
    ValeurCote nouvelleValeurCote = cellule1.basculeTroisEtats(cote);
    Action action = new Action(cellule1, cellule2, cote, nouvelleValeurCote,new Coordonnee(y, x));
    System.out.println(action);
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    Boolean err = verifierErreur(action);
    System.out.println(err);
  }

  // Méthode pour faire une action de type Vide
  public void actionVide(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.VIDE,new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    verifierErreur(action);
  }

  // Méthode pour faire une action de type Trait
  public void actionTrait(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.TRAIT,new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    verifierErreur(action);
  }

  // Méthode pour faire une action de type Croix
  public void actionCroix(int y, int x, int cote) {
    Cellule cellule = puzzle.getCellule(y, x);
    Cellule cellule2 = puzzle.getCelluleAdjacente(y, x, cote);

    Action action = new Action(cellule, cellule2, cote, ValeurCote.CROIX, new Coordonnee(y, x));
    gestionnaireAction.ajouterAction(action);

    action.appliquerAction();
    verifierErreur(action);
  }

  // Méthode qui verifie si la partie est terminée et agit en conséquence
  public boolean estTermine() {
    if (this.puzzle.estComplet()) {
      this.infos.setChrono(null); // TODO recupéré le chrono et update les infos

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

  public void undo() {
    this.gestionnaireAction.annulerAction();
  }

  public void redo() {
    this.gestionnaireAction.retablirAction();
  }

  // Méthode pour afficher dans la console les informations de la partie
  @Override
  public String toString() {
    return infos.toString() + puzzle.toString();
  }

  // Méthode pour commencer une nouvelle partie
  public static Partie nouvellePartie(CataloguePuzzle catalogue, DifficultePuzzle difficulte, int numero,
      ModeJeu modeJeu, Profil profil) {
    Puzzle puzzleVide = catalogue.getCopyPuzzle(difficulte, numero);
    return new Partie(puzzleVide, modeJeu, profil);
  }

  public void activerHypothese() {
    Puzzle puzzleClone = null;
    try {
      puzzleClone = (Puzzle) puzzle.clone();
      GestionnaireAction gestionnaireActionClone = gestionnaireAction.clone(puzzleClone);
      this.hypothese = new Hypothese(puzzleClone, gestionnaireActionClone);
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }

  }

  public void validerHypothese(){
    this.puzzle = hypothese.getPuzzle();
    this.gestionnaireAction = hypothese.getGestionnaireAction();
  }

  public void annulerHypothese(){
    this.hypothese=null;
  }

  public void sauvegarder() {
    System.out.println("Debut de la sauvegarde !");
    PartieSauvegarde.creerSauvegardePartie(this);
  }

  // Charger une partie
  public static Partie chargerPartie(PartieSauvegarde save, Profil profil) {
    return new Partie(save, profil);
  }
}
