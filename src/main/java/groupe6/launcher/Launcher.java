package groupe6.launcher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import groupe6.affichage.Main;
import groupe6.model.profil.CatalogueProfil;
import groupe6.model.partie.puzzle.CataloguePuzzle;
import groupe6.model.technique.GestionnaireTechnique;
import javafx.application.Application;
import javafx.scene.image.Image;

/**
 * La classe qui correspond au lanceur de l'application
 *
 * @author Yamis
 */
public class Launcher {

  /**
   * L'instance unique de la classe Launcher
   */
  private static Launcher instance;

  /**
   * Le booléen pour indiquer si le mode verbose est actif
   */
  public static boolean verbose = false;

  /**
   * Le chemin courant vers le dossier des ressources de l'application
   */
  public static String dossierSlitherlink = "Slitherlink";

  /**
   * Le chemin du dossier des assets à partir du dossier de l'application
   */
  public static String dossierAssets = Launcher.dossierSlitherlink + "/assets";

  /**
   * Le chemin du dossier des puzzles à partir du dossier de l'application
   */
  public static String dossierPuzzles = Launcher.dossierSlitherlink + "/puzzles";

  /**
   * Le chemin du dossier des profils à partir du dossier de l'application
   */
  public static String dossierProfils = Launcher.dossierSlitherlink + "/profils";

  /**
   * Le chemin du dossier des techniques à partir du dossier de l'application
   */
  public static String dossierTechniques = Launcher.dossierSlitherlink + "/techniques";

  /**
   * Le chemin du dossier des règles à partir du dossier de l'application
   */
  public static String dossierRegles = Launcher.dossierSlitherlink + "/regles";

  /**
   * Le chemin du dossier des ressources de l'outil PuzzleGenerator à partir du dossier de l'application
   */
  public static String dossierPuzzleGenerator = Launcher.dossierSlitherlink + "/tools/PuzzleGenerator";

  /**
   * Le catalogue des puzzles chargé au lancement de l'application
   */
  public static CataloguePuzzle cataloguePuzzles;

  /**
   * Le catalogue des profils chargé au lancement de l'application
   */
  public static CatalogueProfil catalogueProfils;

  /**
   * Constructeur privé de la classe Launcher
   */
  private Launcher() {}

  /**
   * Méthode pour obtenir l'instance unique de la classe Launcher
   *
   * @return l'instance unique de la classe Launcher
   */
  public static Launcher getInstance() {
    if (instance == null) {
      instance = new Launcher();
    }
    return instance;
  }

  /**
   * Méthode pour obtenir le boolean qui indique si le mode verbose est actif
   *
   * @return le boolean qui indique si le mode verbose est actif
   */
  public static boolean getVerbose() {
    return verbose;
  }

  /**
   * Méthode pour copier un fichier d'un emplacement à un autre
   *
   * @param sourcePath le chemin du fichier source
   * @param destinationPath le chemin du fichier de destination
   * @throws IOException si une erreur d'entrée/sortie se produit
   */
  public static void copyFile(String sourcePath, String destinationPath) throws IOException {
    File sourceFile = new File(sourcePath);
    File destinationFile = new File(destinationPath);

    if (!sourceFile.exists()) {
      throw new IOException("Le fichier source n'existe pas : " + sourcePath);
    }

    if (!destinationFile.getParentFile().exists()) {
      destinationFile.getParentFile().mkdirs();
    }

    FileInputStream fileInputStream = new FileInputStream(sourceFile);
    FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);

    byte[] buffer = new byte[1024];
    int length;

    // copie du fichier byte par byte
    while ((length = fileInputStream.read(buffer)) > 0) {
      fileOutputStream.write(buffer, 0, length);
    }

    fileInputStream.close();
    fileOutputStream.close();

    if (verbose) {
      System.out.println("Copie du fichier :");
      System.out.println("  - Source : " + sourcePath);
      System.out.println("  - Destination : " + destinationPath);
    }
  }

  /**
   * Méthode pour vérifier si les ressources sont complètes
   *
   * @param fichierJar le fichier JAR
   * @param cheminDossierRessourcesJar le chemin du dossier de ressources dans le JAR
   * @param cheminDossierRessourcesLocal le chemin du dossier de ressources local
   * @return true si les ressources sont complètes, false sinon
   * @throws IOException si une erreur d'entrée/sortie se produit
   */
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
        System.out.println("---------------------------");
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

  /**
   * Méthode pour obtenir la liste des ressources contenues dans un fichier JAR
   *
   * @param fichierJar le fichier JAR
   * @param cheminDossierRessourcesJar le chemin du dossier de ressources dans le JAR
   * @return un ensemble de chemins vers les ressources du JAR
   */
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

  /**
   * Méthode pour obtenir la liste des ressources locales
   *
   * @param cheminDossierRessourcesLocal le chemin du dossier qui contient les ressources locales
   * @return un ensemble de chemins vers les ressources locales
   * @throws IOException si une erreur d'entrée/sortie se produit
   */
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

  /**
   * Méthode pour normaliser un ensemble de chemins
   *
   * @param set l'ensemble qui contient les chemins
   * @param separator le séparateur de chemin à utiliser
   * @return l'ensemble des chemins normalisé
   */
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

  /**
   * Méthode pour charger une image
   *
   * @param chemin le chemin de l'image
   * @return l'image chargée
   */
  public static Image chargerImage(String chemin) {
    File fichierImage = new File(chemin);
    String urlImage = fichierImage.toURI().toString();
    return new Image(urlImage);
  }

  /**
   * Méthode pour obtenir le chemin d'un fichier en URL
   *
   * @param chemin le chemin du fichier
   * @return le chemin du fichier en URL
   */
  public static String chargerFichierEnUrl(String chemin) {
    File fichier = new File(chemin);
    return fichier.toURI().toString();
  }

  /**
   * Méthode pour normaliser un chemin selon le système d'exploitation
   *
   * @param chemin le chemin à normaliser
   * @return le chemin normalisé selon le système d'exploitation
   */
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

  /**
   * Méthode pour extraire les ressources depuis un fichier JAR
   *
   * @param fichierJar le fichier JAR
   * @param cheminDossierRessources le chemin du dossier de ressources
   * @param cheminDossierDestination le chemin du dossier de destination
   * @throws IOException si une erreur d'entrée/sortie se produit
   */
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
      System.out.println("---------------------------");
    }
  }

  /**
   * Méthode qui charge le modèle au lancement de l'application
   */
  public static void loadModel() {
    cataloguePuzzles = CataloguePuzzle.chargerCataloguePuzzle();
    catalogueProfils = CatalogueProfil.chargerCatalogueProfil();
    GestionnaireTechnique.getInstance();
  }

  /**
   * Méthode statique pour obtenir le nom des fichiers qui contiennent les règles du jeu
   *
   * @return la liste des noms des fichiers qui contiennent les règles du jeu
   */
  public static List<String> getRulesInfoNames() {
    String cheminDossier = Launcher.dossierRegles + "/img";
    File dossier = new File(cheminDossier);

    String[] listeFichiers = dossier.list();
    List<String> listeNomImageRegles = new ArrayList<String>();

    if ( listeFichiers == null ) {
      return listeNomImageRegles;
    }

    for ( String fichier : listeFichiers ) {
      if ( !fichier.endsWith(".png") ) {
        String nomRegle = fichier.substring(0, fichier.length() - 4);
        listeNomImageRegles.add(nomRegle);
      }
    }

    return listeNomImageRegles;
  }

  /**
   * Méthode principale qui lance le Launcher
   * @param args Arguments de la ligne de commande
   */
  public static void main(String[] args) {
    for (String arg : args) {
      if (arg.equals("--verbose")) {
        verbose = true;
        break;
      }
    }

    if (verbose) {
      System.out.println("---------------------------");
      System.out.println("Mode verbose actif");
      System.out.println("---------------------------");
    }

    // Obtient le chemin vers le fichier JAR en cours d'execution
    final String cheminFichierJar = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    // Extrait le chemin du dossier parent du fichier JAR
    String cheminDossierParentJar = cheminFichierJar.substring(0, cheminFichierJar.lastIndexOf("/") + 1);

    // Nettoie le chemin du dossier parent du fichier JAR
    cheminDossierParentJar = cheminDossierParentJar.replace("file:","");
    cheminDossierParentJar = cheminDossierParentJar.replace("Gr6.jar!/BOOT-INF/classes!/","");

    // Enleve le slash initial pour les chemins Windows
    if (cheminDossierParentJar.matches("^/[A-Za-z]:/.*")) {
      cheminDossierParentJar = cheminDossierParentJar.substring(1);
    }

    // Chemins des dossiers de ressources
    final String cheminDossierRessourcesJAR = "BOOT-INF/classes/ressources";
    final String cheminDossierDestinationRessourceSlitherLink = Launcher.dossierSlitherlink;

    // Vérifie si le programme est lancé dans le même dossier que le .jar (compare le dossier parent du .jar et du dossier de ressources local)
    int lastIdx = Paths.get(cheminDossierDestinationRessourceSlitherLink).toAbsolutePath().toString().lastIndexOf(File.separator);
    String cheminDossierParentRessourcesLocal = Paths.get(cheminDossierDestinationRessourceSlitherLink).toAbsolutePath().toString().substring(0, lastIdx + 1);

    if ( verbose ) {
      System.out.println("Verification de si le programme est lancé depuis le même dossier que le .jar");
      System.out.println("  - dossier parent jar             : " + normaliserChemin(cheminDossierParentJar));
      System.out.println("  - dossier parent ressource local : " + normaliserChemin(cheminDossierParentRessourcesLocal));
      System.out.println("---------------------------");
    }

    // Vérifie si le programme est lancé dans le même dossier que le .jar
    if ( normaliserChemin(cheminDossierParentJar).compareTo(normaliserChemin(cheminDossierParentRessourcesLocal)) != 0 ) {
      Application.launch(FenetreMauvaisDossier.class);
      return;
    }

    try {
      if (verbose) {
        System.out.println("Verification des Ressources");
        System.out.println("---------------------------");
      }

      // Ouvre le fichier JAR
      JarFile fichierJar = new JarFile(cheminDossierParentJar+"Gr6.jar");

      // Vérifie si les ressources sont complètes
      if (!isRessourcesCompletes(fichierJar, cheminDossierRessourcesJAR, cheminDossierDestinationRessourceSlitherLink)) {
        extraireRessourcesDepuisJar(fichierJar, cheminDossierRessourcesJAR, cheminDossierDestinationRessourceSlitherLink);
      }

      // Lancement du Launcher
      Launcher.getInstance();

      // Fermeture de l'accès au contenu du fichier JAR
      fichierJar.close();

      // Detection du paramètre --tools-puzzle-generator (seulement ce paramètre)
      for (String arg : args) {
        if (arg.equals("--tools-puzzle-generator")) {
          Application.launch(groupe6.tools.puzzleGenerator.Main.class, args);
          return;
        }
      }

      if (verbose) {
        System.out.println("Lancement de l'application");
        System.out.println("---------------------------");
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
