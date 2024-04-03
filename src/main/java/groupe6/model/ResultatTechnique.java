package groupe6.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe qui représente un résultat technique
 *
 * @author Nathan
 */

public class ResultatTechnique implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean techniqueTrouvee;
    private ArrayList<int[]> coordonnees;
    private int idx;
    /** Constructeur de ResultatTechnique */
    ResultatTechnique(boolean uneTechniqueTrouvee, ArrayList<int[]> desCoordonnees, int idx){
        this.techniqueTrouvee=uneTechniqueTrouvee;
        this.coordonnees=desCoordonnees;
        this.idx=idx;
    }

    public ResultatTechnique(boolean uneTechniqueTrouvee) {
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
