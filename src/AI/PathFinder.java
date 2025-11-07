package AI;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>(); //contain node when open
    public ArrayList<Node> pathList = new ArrayList<>(); //contain node move
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNode();
    }

    public void instantiateNode() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row <gp.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col >= gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNode(){
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            //reset open, checker and solid state
            node[col][row].open = false;
            node[col][row].checker = false;
            node[col][row].solid = false;

            col++;
            if(col >= gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

        //reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow){
        resetNode();

        // set start node and goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            //set solid node
            //check tiles
            int tileNum = gp.tileM.mapIileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision){
                node[col][row].solid = true;
            }
            //set cost
            getCost(node[col][row]);

            col++;
            if(col >= gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        //check interactive tiles
        for(int i=0; i<gp.iTile.length; i++){
            if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible){
                int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tile_size;
                int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tile_size;
                node[itCol][itRow].solid = true;
            }
        }
    }

    public void getCost(Node node){
        //g cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //h cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //f cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            //check the current node
            currentNode.checker = true;
            openList.remove(currentNode);

            //Open the Up node
            if(row - 1 >= 0){   //node can't outside the map
                openNode(node[col][row-1]);
            }
            //Open the Down node
            if(row + 1 < gp.maxWorldRow){   //node can't outside the map
                openNode(node[col][row+1]);
            }
            //Open the Left node
            if(col - 1 >= 0){   //node can't outside the map
                openNode(node[col-1][row]);
            }
            //Open the Up node
            if(col + 1 < gp.maxWorldCol){   //node can't outside the map
                openNode(node[col+1][row]);
            }

            //find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            for(int i=0; i<openList.size(); i++){
                //check if this node's f cost is better
                if(openList.get(i).fCost < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                //if ths f cost is equal, check g cost
                else if (openList.get(i).fCost == bestNodeFCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            // if there is no node in openList, end the loop
            if(openList.isEmpty()){
                break;
            }
            //After the loop, openList[bestNodeIndex] is the current node (next step)
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checker && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
