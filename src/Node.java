import java.util.HashMap;

public class Node {
    HashMap<Character, Node> enfants;
    char ch;
    String mot;
    boolean finDuMot = false;

    public Node(){
        enfants = new HashMap<>();
    }
    public Node(char ch){
        this.ch = ch;
        enfants = new HashMap<>();
    }
    public void setFinMot(boolean finDuMot, String mot){
        this.finDuMot = finDuMot;
        this.mot = mot;
    }
    public boolean isLeaf(){
        return enfants.isEmpty();
    }
}
