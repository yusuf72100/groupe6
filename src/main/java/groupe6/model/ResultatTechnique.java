package groupe6.model;

import java.util.ArrayList;

/**
 * @author Nathan
 */

public class ResultatTechnique {
    private boolean techniqueTrouvee;
    private ArrayList<int[]> coordonnees;
    private int idx;
    /** Constructeur de ResultatTechnique */
    ResultatTechnique(boolean uneTechniqueTrouvee, ArrayList<int[]> desCoordonnees, int idx){
        this.techniqueTrouvee=uneTechniqueTrouvee;
        this.coordonnees=desCoordonnees;
        this.idx=idx;
    }
    /** Methode pour savoir si la technique a été trouvée */
    public boolean isTechniqueTrouvee() {
        return techniqueTrouvee;
    }
    /** Methode pour obtenir les coordonnées */
    public ArrayList<int[]> getCoordonnees() {
        return coordonnees;
    }
    public int getIdx(){
        return this.idx;
    }
}
