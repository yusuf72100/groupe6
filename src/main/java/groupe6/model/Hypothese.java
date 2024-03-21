package groupe6.model;


public class Hypothese {
    private Puzzle puzzle;
    

    private GestionnaireAction gestionnaireAction;
    
   

    //Constructeur Hypothese
    public Hypothese(Puzzle puzzle,GestionnaireAction gestionnaire){
        this.puzzle=puzzle;
        this.gestionnaireAction=gestionnaire;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setGestionnaireAction(GestionnaireAction gestionnaireAction) {
        this.gestionnaireAction = gestionnaireAction;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public GestionnaireAction getGestionnaireAction() {
        return gestionnaireAction;
    }

}
