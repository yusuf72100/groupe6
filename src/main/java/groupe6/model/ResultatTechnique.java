package groupe6.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe qui représente un résultat technique
 *
 * @author Nathan
 */
public class ResultatTechnique implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Booléen qui indique si une technique a été trouvée
     */
    private boolean techniqueTrouvee;

    /**
     * La listes des coordonnées qui matchent avec la technique
     */
    private List<Coordonnee> coordonnees;

    /**
     * L'index de la technique dans la liste des techniques
     */
    private int idx;

    /**
     * Constructeur de la classe ResultatTechnique
     *
     * @param uneTechniqueTrouvee le booléen qui indique si une technique a été trouvée
     * @param desCoordonnees la liste des coordonnées qui matchent avec la technique
     * @param idx l'index de la technique dans la liste des techniques
     */
    public ResultatTechnique(boolean uneTechniqueTrouvee, List<Coordonnee> desCoordonnees, int idx){
        this.techniqueTrouvee=uneTechniqueTrouvee;
        this.coordonnees=desCoordonnees;
        this.idx=idx;
    }

    /**
     * Constructeur de la classe ResultatTechnique
     *
     * @param uneTechniqueTrouvee le booléen qui indique si une technique a été trouvée
     */
    public ResultatTechnique(boolean uneTechniqueTrouvee) {
        if (uneTechniqueTrouvee) {
            throw new IllegalArgumentException("Une technique a été trouvée, il faut donner des coordonnées et un index");
        }
        else {
            this.techniqueTrouvee = false;
            this.coordonnees = null;
            this.idx = -1;
        }
    }

    /**
     * Méthode pour obtenir le booléen qui indique si une technique a été trouvée
     *
     * @return le booléen qui indique si une technique a été trouvée
     */
    public boolean isTechniqueTrouvee() {
        return techniqueTrouvee;
    }

    /**
     * Méthode pour obtenir la liste des coordonnées qui matchent avec la technique
     *
     * @return la liste des coordonnées qui matchent avec la technique
     */
    public List<Coordonnee> getCoordonnees() {
        return coordonnees;
    }

    /**
     * Méthode pour obtenir l'index de la technique dans la liste des techniques
     *
     * @return l'index de la technique dans la liste des techniques
     */
    public int getIdx(){
        return this.idx;
    }

    /**
     * Méthode pour obtenir une représentation textuelle du résultat technique
     *
     * @return une représentation textuelle du résultat technique
     */
    @Override
    public String toString() {
        return "ResultatTechnique{" +
                "techniqueTrouvee=" + techniqueTrouvee +
                ", coordonnees=" + coordonnees +
                ", idx=" + idx +
                '}';
    }

    /**
     * Méthode qui verifie si deux résultats techniques sont équivalents
     *
     * @param o l'objet à comparer
     * @return vrai si les résultats techniques sont équivalents, faux sinon
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ResultatTechnique)) {
            return false;
        }

        ResultatTechnique rt = (ResultatTechnique) o;
        return rt.techniqueTrouvee == this.techniqueTrouvee && rt.coordonnees.equals(this.coordonnees) && rt.idx == this.idx;
    }
}
