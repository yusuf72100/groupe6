package groupe6.model.profil;

import groupe6.model.partie.info.PartieFinieInfos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Historique qui stocke l'historique des parties
 *
 * @author Tom MARSURA
 */
public class Historique implements Serializable{

    /**
     * Numéro de version de la sérialisation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * La liste des parties finies
     */
    private static List<PartieFinieInfos> historique;

    /**
     * Constructeur de la classe Historique
     */
    Historique(){
        historique = new ArrayList<>();
    }

    /**
     * Méthode de sauvegarde de l'historique des parties
     *
     * @param hist l'historique des parties
     * @param chemin le chemin du fichier de sauvegarde
     */
    public static void sauvegardeHistorique(Historique hist, String chemin){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))){
            oos.writeObject(historique);
            System.out.println("Historique des parties sauvegardé avec succès !");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode de chargement de l'historique des parties
     *
     * @param chemin le chemin du fichier de sauvegarde a charger
     * @throws ClassNotFoundException si la classe n'est pas trouvée
     */
    public static Historique chargerHistorique (String chemin) throws ClassNotFoundException{
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))){
            Historique historique = (Historique) ois.readObject();
            System.out.println("Historique des parties chargé avec succès !");
            return historique;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Méthode pour obtenir l'historique des parties
     *
     * @return l'historique des parties
     */
    public List<PartieFinieInfos> getReultatPartie(){
        return historique;
    }

    /**
     * Méthode qui ajoute le résultat d'une partie dans l'historique
     *
     * @param partie le résultat de la partie à ajouter
     */
    public void addResultParties(PartieFinieInfos partie){
        historique.add(partie);
    }

    /**
     * Méthode qui calcule les statistiques de l'historique
     *
     * @return les statistiques de l'historique
     */
    public List<Integer> calculerStat(){
        List<Integer> stat = new ArrayList<Integer>();
        int nbParties = historique.size();

        int nbPartiesGagnees = 0;
        for (PartieFinieInfos partieFinieInfos : historique) {
            if (partieFinieInfos.getGagner())
                nbPartiesGagnees++;
        }


        int meilleurScore = 0;
        for (PartieFinieInfos partieFinieInfos : historique) {
            if (partieFinieInfos.getScore() > meilleurScore)
                meilleurScore = partieFinieInfos.getScore();
        }

        stat.add(nbParties);
        stat.add(nbPartiesGagnees);
        stat.add(meilleurScore);

        return stat;
    }
}
