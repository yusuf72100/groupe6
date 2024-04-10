package groupe6.model.partie.aide;

import groupe6.model.technique.ResultatTechnique;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Classe qui représente l'historique des aides
 *
 * @author Nathan
 */
public class HistoriqueAides implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * L'historique des aides demandées par le joueur dans la partie
     */
    private final ArrayList<AideInfos> listeAides;

    /**
     * Constructeur de la classe HistoriqueAides
     */
    public HistoriqueAides(){
        this.listeAides=new ArrayList<AideInfos>();
    }

    /**
     * Méthode pour obtenir la liste des aides
     *
     * @return la liste des aides
     */
    public ArrayList<AideInfos> getListeAides(){
        return this.listeAides;
    }

    /**
     * Méthode pour ajouter une aide à la liste des aides
     *
     * @param aide l'aide à ajouter
     */
    public void ajouterAide(AideInfos aide){
        this.listeAides.add(aide);
    }

    /**
     * Méthode pour savoir si une aide est déjà présente dans la liste des aides
     *
     * @param resTechnique le résultat technique de l'aide
     * @return vrai si l'aide est déjà présente, faux sinon
     */
    public boolean aideDejaPresente(ResultatTechnique resTechnique){
        for (AideInfos a : this.listeAides){
            if ( a.getResultatTechnique().equals(resTechnique)){
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour obtenir une représentation textuelle de l'historique des aides
     *
     * @return la représentation textuelle de l'historique des aides
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (AideInfos a : this.listeAides){
            str.append(a.toString()).append("\n");
        }
        return str.toString();
    }

}
