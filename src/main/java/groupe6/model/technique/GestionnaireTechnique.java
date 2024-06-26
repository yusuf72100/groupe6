package groupe6.model.technique;

import groupe6.launcher.Launcher;
import groupe6.model.partie.Partie;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Classe qui représente le gestionnaire des techniques
 *
 * @author Nathan, Yamis
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
        this.ajouterTechnique(SimpleZero.getInstance());
        this.ajouterTechnique(Adjacents03.getInstance());
        this.ajouterTechnique(Diagonales03.getInstance());
        this.ajouterTechnique(Adjacents3.getInstance());
        this.ajouterTechnique(Diagonales3.getInstance());
        this.ajouterTechnique(AnyNumberCorner.getInstance());
        /*
         * Techniques BASIQUES
         */
        this.ajouterTechnique(Coin03.getInstance());
        this.ajouterTechnique(BoucleAu3.getInstance());
        this.ajouterTechnique(BoucleAu1.getInstance());
        this.ajouterTechnique(ConstraintOn2.getInstance());
        this.ajouterTechnique(AvoidSeparateLoop.getInstance());
        /*
         * Techniques AVANCEES
         */
        this.ajouterTechnique(Advanced1.getInstance());
        this.ajouterTechnique(Advanced2.getInstance());
        this.ajouterTechnique(Advanced3.getInstance());
        this.ajouterTechnique(Advanced4.getInstance());
        this.ajouterTechnique(Advanced5.getInstance());
        this.ajouterTechnique(Advanced6.getInstance());
    }

    /**
     * Méthode pour obtenir les noms des techniques par difficulté
     *
     * @return les noms des techniques par difficulté
     */
    public List<String[]>[] nomTechniques() {
        List<String[]>[] res = new List[]{
            new ArrayList<String>(),
            new ArrayList<String>(),
            new ArrayList<String>()
        };

        for (Technique t : listeTechnique) {
            // Tableau avec nomTechnique et nomStylise
            String[] noms = new String[]{
                t.getNomTechnique(),
                t.getNomTechniqueStylise()
            };
            res[t.getDifficulte().ordinal()].add(noms);
        }

        return res;
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
                    lstResultats.add(resultat);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // Tri la liste des résultats par l'index dans ResultatTechnique
            lstResultats.sort(Comparator.comparingInt(ResultatTechnique::getIdx));

            // Retourne le premier résultat technique trouvé
            for (ResultatTechnique resultat : lstResultats) {
                if (resultat.isTechniqueTrouvee()) {
                    return resultat;
                }
            }
        }

        // Arrêter l'exécuteur de tâches
        executor.shutdown();

        // Si aucune technique n'a été trouvée, on retourne un résultat technique avec trouvé à faux
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