public class codeTechnique {

    public static final boolean ADJ = true;
    public static final boolean DIAG = false;

    public static final boolean HORIZ = false;
    public static final boolean VERTI = true;
    public static final boolean MONT = false;
    public static final boolean DESC = true;

    public static resultatTechnique simpleZero (Puzzle grille) {
        for(int y = 0; i < grille.getLargeur(); i++) {
            for(int x = 0; j < grille.getLongueur(); j++) {
                Cellule cour = grille.getCellule(y,x);
                if (cour.getValeur() == 0 && ( (cour.getCote(HAUT) != CROIX)
                                            || (cour.getCote(BAS) != CROIX)
                                            || (cour.getCote(GAUCHE) != CROIX)
                                            || (cour.getCote(DROITE) != CROIX)))
                    return new resultatTechnique(true, Arrays.asList({y,x}))
            }
        }
        return new resultatTechnique(false, null);
    }

    private static verificationMultidir(Puzzle grille, List<int[]> listeCellule, boolean adjacent, boolean sens) {

    }
    public static resultatTechnique adjacentTrois (Puzzle grille) {
        for(int y = 0; i < grille.getLargeur(); i++) {
            for(int x = 0; j < grille.getLongueur(); j++) {
                Cellule cour = grille.getCellule(y,x);
                if(cour.getValeur() == 3) {
                    List<Cellule> adjacents = Arrays.asList(grille.getCellule(y,x-1), //Haut
                                                            grille.getCellule(y,x+1), //Bas
                                                            grille.getCellule(y-1,x), //Gauche
                                                            grille.getCellule(y+1,x));//Droite
                    for(Cellule adj : adjacents) {
                        if(adj.getValeur() == 3 ) {
                            switch (adjacents.indexOf(adj)) {
                                case HAUT:
                                    if (cour.getCote(BAS) != TRAIT || cour.getCote(HAUT) != TRAIT || adj.getCote(HAUT) != TRAIT)
                                        return new resultatTechnique(true, Arrays.asListe({y,x},{y,x-1}));
                                    break;
                                case BAS:
                                    if (cour.getCote(BAS) != TRAIT || cour.getCote(HAUT) != TRAIT || adj.getCote(HAUT) != TRAIT)
                                        return new resultatTechnique(true, Arrays.asListe({y,x},{y,x+1}));
                                    break;
                                case GAUCHE:
                                    if (cour.getCote(BAS) != TRAIT || cour.getCote(HAUT) != TRAIT || adj.getCote(HAUT) != TRAIT)
                                        return new resultatTechnique(true, Arrays.asListe({y,x},{y-1,x}));
                                    break;
                                case DROITE:
                                    if (cour.getCote(BAS) != TRAIT || cour.getCote(HAUT) != TRAIT || adj.getCote(HAUT) != TRAIT)
                                        return new resultatTechnique(true, Arrays.asListe({y,x},{y+1,x}));
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return new resultatTechnique(false, null);
    }
}