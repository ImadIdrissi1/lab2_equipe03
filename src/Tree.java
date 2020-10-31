import java.util.HashMap;

public class Tree {
    Node root, actualNode;

    public Tree(){
        root = new Node();
        actualNode = root;
    }

    public void insert(String mot){
        Node head = root;
        char [] characters = mot.toCharArray();
        for(char character : characters){
            if(head.enfants.get(character) == null){
                head.enfants.put(character, new Node(character));
            }

            head = head.enfants.get(character);
        }
        head.setFinMot(true, mot);
    }
    public Node getNext(char key){
        if(actualNode.enfants.get(key) == null){
            actualNode = root;
            return null;
        }
        actualNode = actualNode.enfants.get(key);
        return actualNode;
    }
    public void resetActualNode(){
        actualNode = root;
    }

}
