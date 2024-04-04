package groupe6.model;

import groupe6.launcher.Launcher;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Classe qui représente le gestionnaire des techniques
 *
 * @author Nathan
 */
public class GestionnaireTechnique{

    private static GestionnaireTechnique instance = null;

    private final List<Technique> listeTechnique;

    private GestionnaireTechnique(){
        this.listeTechnique=new ArrayList<Technique>();
        chargerTechniques();
    }

    private void chargerTechniques() {
        // Charger les techniques

        // this.ajouterTechnique(technique1);
    }

    public void ajouterTechnique(Technique technique) {
        listeTechnique.add(technique);
    }

    public List<Technique> getListeTechnique() {
        return listeTechnique;
    }

    public ResultatTechnique rechercheAideTechnique(Partie partie) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int index = 0;

        while (index < listeTechnique.size()) {
            CompletionService<ResultatTechnique> completionService = new ExecutorCompletionService<>(executor);

            // Lancer jusqu'à 4 techniques en même temps ou jusqu'à ce que toutes les techniques aient été lancées
            for (int i = 0; i < 4 && index < listeTechnique.size(); i++, index++) {
                final int currentIndex = index;
                completionService.submit(() -> listeTechnique.get(currentIndex).run(partie));
            }

            // Attendre que les résultats des techniques soient disponibles
            for (int i = 0; i < Math.min(4, listeTechnique.size() - index); i++) {
                try {
                    Future<ResultatTechnique> future = completionService.take();
                    ResultatTechnique resultat = future.get();
                    if (resultat.isTechniqueTrouvee()) {
                        executor.shutdown();
                        return resultat;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
        // Si aucune technique n'a trouvé à True, vous pouvez retourner un résultat indiquant qu'aucune technique n'a été trouvée
        return new ResultatTechnique(false);
    }

    public static GestionnaireTechnique getInstance() {
        if (instance == null) {
            instance = new GestionnaireTechnique();
        }
        return instance;
    }

}