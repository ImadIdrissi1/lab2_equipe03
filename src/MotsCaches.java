import java.io.*;
import java.util.ArrayList;



public class MotsCaches {
    private BufferedReader reader;
    private String dict_in, grid_in;
    private char [][] grid;
    private Tree dictTree;
    private int [] dx = new int[] {1,-1,0,0,1,1,-1,-1};
    private int [] dy = new int[] {0,0,1,-1,-1,1,1,-1};
    private int  x ;
    private int  y ;
    private ArrayList<String> wordsFind;


    public MotsCaches(String grid_in, String dict_in){
        this.dict_in = dict_in;
        this.grid_in = grid_in;
        wordsFind = new ArrayList<String>();
    }
    public BufferedReader reader(String file){
        try {
            return new BufferedReader(new FileReader(file),16384);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Tree getDict(String dict_in) throws IOException{
        reader = reader(dict_in);
        String word;
        Tree root = new Tree();
        while((word = reader.readLine()) != null){
            root.insert(word);
        }
        reader.close();
        return root;
    }
    private char [][] getGrid(String grid_in) throws IOException {
        reader = reader(grid_in);
        int size = Integer.parseInt(reader.readLine());
        String line;
        char [][] grid = new char[size][size];
        int index = 0;
        while ((line = reader.readLine()) != null) {
            grid[index] = line.toCharArray();
            index = index + 1;
        }
        reader.close();
        return grid;
    }
    private void searchWord(char [][] grid, Tree treeDict){
        int rows = grid[0].length;
        int cols = grid.length;
        char ch;
        Node n;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                ch = grid[row][col];
                n = treeDict.getNext(ch);
                if(n != null){
                    treeDict.resetActualNode();
                    dfs_direc(grid,col,row,treeDict, rows, cols);
                }else{
                    treeDict.resetActualNode();
                }
            }
        }
    }
    public void dfs(char [][] grid,int col,int row,Tree node, int rows, int cols){
        if(col < 0 || row < 0 || row >= rows || col >= cols ){
            node.resetActualNode();
            return;
        }

        char ch_grid = grid[row][col];
        Node ch_node = node.getNext(ch_grid);
        if(ch_node == null){
            node.resetActualNode();
            return;
        }
        if(ch_node.finDuMot){
            wordsFind.add(ch_node.mot);
        }
        if(ch_node.isLeaf()){
            node.resetActualNode();
            return;
        }
        dfs(grid,col+x, row+y,node,rows, cols);
    }
    private void dfs_direc(char [][] grid,int col,int row,Tree node, int rows, int cols){
        for(int i = 0;i < dx.length;i++){
            x = dx[i];
            y = dy[i];
            dfs(grid,col,row,node,rows, cols);
        }
    }
    public void process(){
        try {
            long startTime = System.currentTimeMillis();
            grid = getGrid(grid_in);
            dictTree = getDict(dict_in);
            searchWord(grid,dictTree);
            wordsFind.sort(String::compareToIgnoreCase);
            long endTime = System.currentTimeMillis();
            float sec = (endTime - startTime) / 1000f;
            wordsFind.forEach(word -> System.out.println(word));
            System.out.println("La grille contient "+wordsFind.size()+" mots du dictionnaire");
            System.out.printf("Temps d'exécution : %.6f secondes", sec);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args) {
        MotsCaches mc = new MotsCaches(args[0],args[1]);
        mc.process();
    }
}