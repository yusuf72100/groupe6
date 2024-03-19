package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe modélise un catalogue de profils
 * 
 * @author William Sardon
 */
public class CatalogueProfil {
    private static List<Profil> listeProfil;

    private Profil profilActuel;

    public static void parcourirProfils() {
        // Parcours les elements du dossier "ressources/profil/"
        File dossierProfils = new File("ressources/profil/");
        File[] profils = dossierProfils.listFiles();
        for (File profil : profils) {
            if (profil.isDirectory()) {
                // Parcours les elements du dossier "ressources/profil/nomProfil/saves/"
                for (File file : profil.listFiles()) {
                    // File termine par .profil
                    if (file.getName().endsWith(".profil")) {
                        // On charge le profil
                        Profil p = Profil.chargerProfil(file.getAbsolutePath());
                        ajouterProfil(p);
                    }
                }
            }
        }
    }

    public CatalogueProfil() {
        listeProfil = new ArrayList<Profil>();
        parcourirProfils();
    }

    public List<Profil> getListeProfils() {
        return listeProfil;
    }

    public static void ajouterProfil(Profil profil) {
        listeProfil.add(profil);
    }
}
