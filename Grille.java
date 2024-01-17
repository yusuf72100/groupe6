import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Grille implements Serializable{
    private static final long serialVersionUID = 1L;
    private Map<String, List<Map<String, Object>>> easy;
    private List<Map<String, Object>> medium;
    private List<Map<String, Object>> hard;

    // Constructeurs, getters et setters

    public Map<String, List<Map<String, Object>>> getEasy() {
        return easy;
    }

    public void setEasy(Map<String, List<Map<String, Object>>> easy) {
        this.easy = easy;
    }

    public List<Map<String, Object>> getMedium() {
        return medium;
    }

    public void setMedium(List<Map<String, Object>> medium) {
        this.medium = medium;
    }

    public List<Map<String, Object>> getHard() {
        return hard;
    }

    public void setHard(List<Map<String, Object>> hard) {
        this.hard = hard;
    }

    public void displayGrid() {
        System.out.println("Easy:");
        // Affichez ici les détails de la grille...
    }
}
