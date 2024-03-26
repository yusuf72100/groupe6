package groupe6.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author Nathan
 */

public class GestionnaireTechnique{
    private ArrayList<Technique> listeTechnique;
    GestionnaireTechnique(){
        this.listeTechnique=new ArrayList<Technique>();
    }

    public void chargerTechnique(){
        File dir = new File("/techniques");
        File[] liste = dir.listFiles();
        for(File item : liste){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(item.getPath()))) {
                Technique technique=(Technique) ois.readObject();
                this.listeTechnique.add(technique);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
    }

    public ResultatTechnique rechercheAideTechnique(Partie partie){
        for(Technique t: listeTechnique){
            ResultatTechnique r=t.run(partie);
            if(r.isTechniqueTrouvee()){
                return r;
            }
        }
        return null;
    }

    public static Technique chargerPuzzle(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Technique technique = (Technique) ois.readObject();
            return technique;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}