package AI;

public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost; //distance from node start to node current
    int hCost; //distance from node current to node end
    int fCost; //total gCost and fCost
    boolean solid;
    boolean open;
    boolean checker;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
