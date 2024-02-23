import java.util.ArrayList;

/**
 * @author Nathan
 */

public class ResultatTechnique {
    private boolean techniqueTrouvee;
    private ArrayList<int[]> coordonnees;
    /** Constructeur de ResultatTechnique */
    ResultatTechnique(boolean uneTechniqueTrouvee, ArrayList<int[]> desCoordonnees){
        this.techniqueTrouvee=uneTechniqueTrouvee;
        this.coordonnees=desCoordonnees;
    }
    /** Methode pour savoir si la technique a été trouvée */
    public boolean isTechniqueTrouvee() {
        return techniqueTrouvee;
    }
    /** Methode pour obtenir les coordonnées */
    public ArrayList<int[]> getCoordonnees() {
        return coordonnees;
    }
}
