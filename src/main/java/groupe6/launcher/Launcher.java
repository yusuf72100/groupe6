package groupe6.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import groupe6.affichage.Main;
import groupe6.model.CatalogueProfil;
import groupe6.model.CataloguePuzzle;
import javafx.application.Application;
import javafx.scene.image.Image;

public class Launcher {

  static boolean verbose = false; // Boolean pour activer le mode verbose
  private static Launcher instance; // Instance unique de la classe

  public static String dossierSlitherlink = "Slitherlink"; // Le dossier de l'application
  public static String dossierAssets = Launcher.dossierSlitherlink + "/assets"; // Dossier des assets
  public static String dossierPuzzles = Launcher.dossierSlitherlink + "/puzzles"; // Dossier des puzzles
  public static String dossierProfils = Launcher.dossierSlitherlink + "/profils"; // Dossier des profils
  public static String dossierTechniques = Launcher.dossierSlitherlink + "/techniques"; // Dossier des techniques
  public static String dossierPuzzleGenerator = Launcher.dossierSlitherlink + "/tools/PuzzleGenerator"; // Dossier du puzzle generator

  public static CataloguePuzzle cataloguePuzzles;
  public static CatalogueProfil catalogueProfils;

  private String cheminDossierParentJar;
  private Launcher() {}

  public static Launcher getInstance() {
    if (instance == null) {
      instance = new Launcher();
    }
    return instance;
  }

  public void setCheminDossierParentJar(String cheminDossierParentJar) {
    this.cheminDossierParentJar = cheminDossierParentJar;
  }

  public String getCheminDossierParentJar() {
    return cheminDossierParentJar;
  }

  public static boolean copier(File source, File dest) {
    try (InputStream sourceFile = new java.io.FileInputStream(source);
         OutputStream destinationFile = new FileOutputStream(dest)) {
      // Lecture par segment de 0.5Mo
      byte[] buffer = new byte[512 * 1024];
      int nbLecture;
      while ((nbLecture = sourceFile.read(buffer)) != -1){
        destinationFile.write(buffer, 0, nbLecture);
      }
    } catch (IOException e){
      e.printStackTrace();
      return false; // Erreur
    }
    return true; // Résultat OK
  }

  private static boolean isRessourcesCompletes(JarFile fichierJar, String cheminDossierRessourcesJar,
      String cheminDossierRessourcesLocal) throws IOException {
    Set<String> ressourcesJar = normaliserChemin(getRessourcesJar(fichierJar, cheminDossierRessourcesJar), "/");
    Set<String> ressourcesLocal = normaliserChemin(getRessourcesLocales(cheminDossierRessourcesLocal), "/");

    // Vérifier si des fichiers ou dossiers sont manquants
    Set<String> ressourcesManquantes = new HashSet<>(ressourcesJar);
    ressourcesManquantes.removeAll(ressourcesLocal);

    if (ressourcesManquantes.isEmpty()) {
      if (verbose) {
        System.out.println("Toutes les ressources sont presentes.");
      }
      return true;
    } else {
      if (verbose) {
        System.out.println("Ressources manquantes :");
        for (String ressourceManquante : ressourcesManquantes) {
          System.out.println("- " + ressourceManquante);
        }
      }
      return false;
    }

  }

  private static Set<String> getRessourcesJar(JarFile fichierJar, String cheminDossierRessourcesJar) {

    if (fichierJar.getEntry(cheminDossierRessourcesJar) == null) {
      throw new IllegalArgumentException("Le dossier de ressources " + cheminDossierRessourcesJar + " n'existe pas.");
    }

    Set<String> ressources = new HashSet<>();
    Enumeration<JarEntry> entrees = fichierJar.entries();

    while (entrees.hasMoreElements()) {
      JarEntry entree = entrees.nextElement();
      String nomEntree = entree.getName();

      if (nomEntree.startsWith(cheminDossierRessourcesJar + "/")) {
        ressources.add(nomEntree.substring(cheminDossierRessourcesJar.length() + 1));
      }
    }

    return ressources;
  }

  private static Set<String> getRessourcesLocales(String cheminDossierRessourcesLocal) throws IOException {

    if (!Files.exists(Paths.get(cheminDossierRessourcesLocal))) {
      return new HashSet<>();
    }

    Set<String> resources = new HashSet<>();
    Path cheminLocal = Paths.get(cheminDossierRessourcesLocal);

    Files.walk(cheminLocal).forEach(entree -> {
      String nomEntree = entree.toString();

      if (nomEntree.compareTo(cheminDossierRessourcesLocal) != 0) {
        resources.add(nomEntree.substring(cheminDossierRessourcesLocal.length() + 1));
      }
    });

    return resources;
  }

  // Méthode pour normaliser les chemins en utilisant un séparateur spécifié
  private static Set<String> normaliserChemin(Set<String> set, String separator) {
    Set<String> setNormalisee = new HashSet<>();
    for (String chemin : set) {
      String cheminNormalise = chemin.replace("\\", separator);

      if (cheminNormalise.endsWith(separator)) {
        cheminNormalise = cheminNormalise.substring(0, cheminNormalise.length() - 1);
      }
      if (!cheminNormalise.isBlank()) {
        setNormalisee.add(cheminNormalise);
      }

    }
    return setNormalisee;
  }

  public static Image chargerImage(String chemin) {
    File fichierImage = new File(chemin);
    String urlImage = fichierImage.toURI().toString();
    return new Image(urlImage);
  }

  public static String chargerFichierEnUrl(String chemin) {
    File fichier = new File(chemin);
    return fichier.toURI().toString();
  }

  public static String normaliserChemin(String chemin) {
    if (File.separator.compareTo("/") == 0 ) {
      return chemin.replace('\\', '/');
    }
    else if ( File.separator.compareTo("\\") == 0 ) {
      return chemin.replace('/','\\');
    }
    else {
      throw new IllegalStateException("Séparateur de fichier inconnu.");
    }
  }

  // Méthode qui permet d'extraire les ressources qui sont dans le fichier JAR
  private static void extraireRessourcesDepuisJar(JarFile fichierJar, String cheminDossierRessources,
      String cheminDossierDestination) throws IOException {

    File dossierDestination = new File(cheminDossierDestination);
    if (!dossierDestination.exists()) {
      dossierDestination.mkdirs();
    }

    Enumeration<JarEntry> lst = fichierJar.entries();
    while (lst.hasMoreElements()) {
      JarEntry entree = lst.nextElement();

      if (entree.getName().startsWith(cheminDossierRessources + "/")) {
        File fichierSortie = new File(cheminDossierDestination + File.separator +
            entree.getName().substring(cheminDossierRessources.length() + 1));

        if (entree.isDirectory()) {
          fichierSortie.mkdirs();
        } else {
          File dossierSortie = new File(fichierSortie.getParent());
          if (!dossierSortie.exists()) {
            dossierSortie.mkdirs();
          }

          try (InputStream fluxEntree = fichierJar.getInputStream(entree);
              OutputStream fluxSortie = new FileOutputStream(fichierSortie)) {
            byte[] memoireTampon = new byte[1024];
            int octesLus;
            while ((octesLus = fluxEntree.read(memoireTampon)) != -1) {
              fluxSortie.write(memoireTampon, 0, octesLus);
            }
          }
        }
      }
    }

    if (verbose) {
      System.out.println(" \nRessources extraites avec succes.");
    }
  }

  public static void loadModel() {
    cataloguePuzzles = CataloguePuzzle.chargerCataloguePuzzle();
    catalogueProfils = CatalogueProfil.chargerCatalogueProfil();
  }

  public static void main(String[] args) {
    for (String arg : args) {
      if (arg.equals("--verbose")) {
        verbose = true;
        break;
      }
    }

    if (verbose) {
      System.out.println("---------------------------");
      System.out.println("Mode verbose active");
      System.out.println("---------------------------");
    }


    // Obtient le chemin vers le fichier JAR en cours d'execution
    final String cheminFichierJar = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    // Extrait le chemin du dossier parent du fichier JAR
    String cheminDossierParentJar = cheminFichierJar.substring(0, cheminFichierJar.lastIndexOf("/") + 1);
    // Replace file: by ""
    cheminDossierParentJar = cheminDossierParentJar.replace("file:","");
    cheminDossierParentJar = cheminDossierParentJar.replace("Gr6.jar!/BOOT-INF/classes!/","");

    // Enleve le slash initial pour les chemins Windows
    if (cheminDossierParentJar.matches("^/[A-Za-z]:/.*")) {
      cheminDossierParentJar = cheminDossierParentJar.substring(1);
    }

    // Chemins des dossiers de ressources
    final String cheminDossierRessourcesJAR = "BOOT-INF/classes/ressources";
    final String cheminDossierDestinationRessourceSlitherLink = Launcher.dossierSlitherlink;

    // Vérifie si le programme est lancé dans le même dossier que le .jar ( compare les dossier parent du .jar et du dossier de ressources local )
    int lastIdx = Paths.get(cheminDossierDestinationRessourceSlitherLink).toAbsolutePath().toString().lastIndexOf(File.separator);
    String cheminDossierParentRessourcesLocal = Paths.get(cheminDossierDestinationRessourceSlitherLink).toAbsolutePath().toString().substring(0, lastIdx + 1);

    System.out.println("dossier parent jar : " + cheminDossierParentJar);
    System.out.println("dossier parent ressource local : " + cheminDossierParentRessourcesLocal);
    System.out.println("Chemins normalisés:");
    System.out.println("dossier parent jar : " + normaliserChemin(cheminDossierParentJar));
    System.out.println("dossier parent ressource local : " + normaliserChemin(cheminDossierParentRessourcesLocal));
    if ( normaliserChemin(cheminDossierParentJar).compareTo(normaliserChemin(cheminDossierParentRessourcesLocal)) != 0 ) {
      Application.launch(FenetreMauvaisDossier.class);
      return;
    }

    try {
      if (verbose) {
        System.out.println("Verification des Ressources");
        System.out.println("---------------------------");
      }

      JarFile fichierJar = new JarFile(cheminDossierParentJar+"Gr6.jar");

      if (!isRessourcesCompletes(fichierJar, cheminDossierRessourcesJAR, cheminDossierDestinationRessourceSlitherLink)) {
        extraireRessourcesDepuisJar(fichierJar, cheminDossierRessourcesJAR, cheminDossierDestinationRessourceSlitherLink);
      }

      // Création de l'objet Launcher
      Launcher launcher = Launcher.getInstance();
      // Ajout du chemin du dossier parent du fichier JAR a l'objet Launcher
      launcher.setCheminDossierParentJar(normaliserChemin(cheminDossierParentJar));

      fichierJar.close();

      if (verbose) {
        System.out.println("---------------------------");
        System.out.println("Lancement de l'application");
        System.out.println("---------------------------");
      }

      // Detection du paramètre --tools-puzzle-generator ( seulement ce paramètre )
      for (String arg : args) {
        if (arg.equals("--tools-puzzle-generator")) {
          Application.launch(groupe6.tools.puzzleGenerator.Main.class, args);
          return;
        }
      }

      // Detection du paramètre --test ( seulement ce paramètre )
      for (String arg : args) {
        if (arg.equals("--test")) {

          groupe6.test.TestMain.main(args);
          return;
        }
      }

      // Chargement du modele
      loadModel();
      // Lancement de l'affichage
      Application.launch(Main.class, args);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
