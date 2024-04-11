package groupe6.model.technique;

import groupe6.model.partie.Partie;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Classe qui représente le gestionnaire des techniques
 *
 * @author Nathan
 */
public class GestionnaireTechnique{

    /**
     * Instance unique de la classe GestionnaireTechnique
     */
    private static GestionnaireTechnique instance = null;

    /**
     * La liste des techniques
     */
    private final List<Technique> listeTechnique;

    /**
     * Constructeur de la classe GestionnaireTechnique
     */
    private GestionnaireTechnique(){
        this.listeTechnique=new ArrayList<Technique>();
        chargerTechniques();
    }

    /**
     * Méthode pour charger les techniques
     */
    private void chargerTechniques() {
        /*
         * Techniques DEMARRAGE
         */
        /// this.ajouterTechnique(SimpleZero.getInstance());
        this.ajouterTechnique(new Adjacents03());
        /// TODO : this.ajouterTechnique(new Diagonales03());
        this.ajouterTechnique(new Adjacents03());
        /// TODO : this.ajouterTechnique(new Diagonales3());
        /// this.ajouterTechnique(AnyNumberCorner.getInstance());
        /*
         * Techniques BASIQUES
         */
        /// this.ajouterTechnique(Coin03.getInstance());
        /// this.ajouterTechnique(new BoucleAu3());
        this.ajouterTechnique(new BoucleAu1());
        // TODO : this.ajouterTechnique(new ConstraintOn2());
        // TODO : this.ajouterTechnique(new AvoidSeparateLoop());
        /*
         * Techniques AVANCEES
         */
        // TODO : this.ajouterTechnique(new Advanced1());
        // TODO : this.ajouterTechnique(new Advanced2());
        // TODO : this.ajouterTechnique(new Advanced3());
        // TODO : this.ajouterTechnique(new Advanced4());
        // TODO : this.ajouterTechnique(new Advanced5());
        // TODO : this.ajouterTechnique(new Advanced6());
    }

    /**
     * Méthode pour ajouter une technique à la liste des techniques
     *
     * @param technique la technique à ajouter
     */
    public void ajouterTechnique(Technique technique) {
        listeTechnique.add(technique);
    }

    /**
     * Méthode pour obtenir la liste des techniques
     *
     * @return la liste des techniques
     */
    public List<Technique> getListeTechnique() {
        return listeTechnique;
    }

    /**
     * Méthode pour rechercher une technique applicable à la partie
     *
     * @param partie la partie sur laquelle on recherche la technique
     * @return le résultat de la recherche
     */
    public ResultatTechnique rechercheAideTechnique(Partie partie) {
        // Démarrage de l'exécuteur de tâches avec 4 threads maximum
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Index actuel dans la liste des techniques
        int index = 0;

        // Tant qu'il reste des techniques à lancer
        while (index < listeTechnique.size()) {
            CompletionService<ResultatTechnique> completionService = new ExecutorCompletionService<>(executor);

            // Lancer jusqu'à 4 techniques en même temps ou jusqu'à ce que toutes les techniques aient été lancées
            for (int i = 0; i < 4 && index < listeTechnique.size(); i++, index++) {
                final int currentIndex = index;
                completionService.submit(() -> listeTechnique.get(currentIndex).run(partie,currentIndex));
            }

            // Attends le résultat de chaque technique et renvoie la technique la moins complexe
            List <ResultatTechnique> lstResultats = new ArrayList<>();
            for (int i = 0; i < Math.min(4, listeTechnique.size() - (index-1)); i++) {
                try {
                    Future<ResultatTechnique> future = completionService.take();
                    ResultatTechnique resultat = future.get();
                    System.out.println(resultat.toString());
                    lstResultats.add(resultat);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // Tri la liste des résultats par l'index dans ResultatTechnique
            lstResultats.sort((r1, r2) -> r1.getIdx() - r2.getIdx());

            // Retourne le premier résultat technique trouvé
            for (ResultatTechnique resultat : lstResultats) {
                if (resultat.isTechniqueTrouvee()) {
                    return resultat;
                }
            }
        }

        // Arrêter l'exécuteur de tâches
        executor.shutdown();

        // Si aucune technique n'a été trouvée on retourne un résultat technique avec trouvé à faux
        return new ResultatTechnique(false);
    }

    /**
     * Méthode pour obtenir l'instance unique de la classe GestionnaireTechnique
     *
     * @return l'instance de la classe GestionnaireTechnique
     */
    public static GestionnaireTechnique getInstance() {
        if (instance == null) {
            instance = new GestionnaireTechnique();
        }
        return instance;
    }

}