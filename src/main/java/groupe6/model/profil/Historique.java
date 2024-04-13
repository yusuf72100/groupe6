package groupe6.model.profil;

import groupe6.launcher.Launcher;
import groupe6.model.partie.info.PartieFinieInfos;
import groupe6.model.partie.sauvegarde.CatalogueSauvegarde;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Historique qui stocke l'historique des parties
 *
 * @author Tom MARSURA
 */
public class Historique implements Serializable {

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * La liste des parties finies
     */
    private final List<PartieFinieInfos> historique;

    /**
     * Constructeur de la classe Historique
     */
    public Historique() {
        this.historique = new ArrayList<PartieFinieInfos>();
    }

    /**
     * Méthode pour obtenir l'historique des parties
     *
     * @return l'historique des parties
     */
    public List<PartieFinieInfos> getReultatPartie(){
        return this.historique;
    }

    /**
     * Méthode qui ajoute le résultat d'une partie dans l'historique
     *
     * @param partie le résultat de la partie à ajouter
     */
    public void addResultParties(PartieFinieInfos partie){
        // Ajout du résultat de la partie dans l'historique
        this.historique.add(partie);
        // Lance un thread séparé pour sauvegarder le profil actuel qui vient de modifier son historique
        new Thread(() -> Profil.sauvegarderProfil(Launcher.catalogueProfils.getProfilActuel())).start();
        // Lance un thread séparé pour supprimer la sauvegarde de la partie si elle existe
        Profil profil = Launcher.catalogueProfils.getProfilActuel();
        new Thread(() -> CatalogueSauvegarde.suppimerAnciennesSauvegardes(profil,partie)).start();
    }

    /**
     * Méthode qui calcule les statistiques de l'historique
     *
     * @return les statistiques de l'historique
     */
    public List<Integer> calculerStat(){
        List<Integer> stat = new ArrayList<Integer>();
        int nbParties = this.historique.size();

        int nbPartiesGagnees = 0;
        for (PartieFinieInfos partieFinieInfos : this.historique) {
            if (partieFinieInfos.getGagnee())
                nbPartiesGagnees++;
        }


        int meilleurScore = 0;
        for (PartieFinieInfos partieFinieInfos : this.historique) {
            if (
                partieFinieInfos.getGagnee() &&
                partieFinieInfos.getScore() > meilleurScore
            ) {
                meilleurScore = partieFinieInfos.getScore();
            }

        }

        stat.add(nbParties);
        stat.add(nbPartiesGagnees);
        stat.add(meilleurScore);

        return stat;
    }
}
