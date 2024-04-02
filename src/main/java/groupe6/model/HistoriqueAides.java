package groupe6.model;

import java.util.ArrayList;


/**
 * Classe qui représente l'historique des aides
 *
 * @autor Nathan
 */
public class HistoriqueAides {
    ArrayList<AideInfos> listeAides;
    HistoriqueAides(){
        this.listeAides=new ArrayList<AideInfos>();
    }
    public ArrayList<AideInfos> getListeAides(){
        return this.listeAides;
    }
    public void ajouterAide(AideInfos aide){
        this.listeAides.add(aide);
    }
}
