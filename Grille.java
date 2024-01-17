import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Grille implements Serializable{
    private static final long serialVersionUID = 1L;
    public List<Map<String, Object>> level;
    public List<List<Integer>> grid;
    public int width;
    public int height;


}
