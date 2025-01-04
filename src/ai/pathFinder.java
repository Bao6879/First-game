package ai;

import main.gamePanel;

import java.util.ArrayList;

public class pathFinder {
    gamePanel gp;
    Node[][] node;
    ArrayList <Node> openList=new ArrayList <>();
    public ArrayList <Node> pathList=new ArrayList <>();
    Node startNode, goalNode, currentNode;
    boolean goalReached=false;
    int step=0;
    public pathFinder(gamePanel gp) {
        this.gp=gp;
        instantiateNode();
    }
    public void instantiateNode()
    {
        node=new Node[gp.maxWorldCol][gp.maxWorldRow];
        int col=0;
        int row=0;
        for (col=0;col<gp.maxWorldCol;col++)
        {
            for (row=0;row<gp.maxWorldRow;row++)
            {
                node[col][row]=new Node(col,row);
            }
        }
    }
    public void resetNode()
    {
        for (int col=0; col<gp.maxWorldCol; col++)
        {
            for (int row=0; row<gp.maxWorldRow; row++)
            {
                node[col][row].open=false;
                node[col][row].checked=false;
                node[col][row].solid=false;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached=false;
        step=0;
    }
    public void setNode(int startCol, int startRow, int goalCol, int goalRow)
    {
        resetNode();
        startNode=node[startCol][startRow];
        goalNode=node[goalCol][goalRow];
        currentNode=startNode;
        openList.add(startNode);
        for (int row = 0; row < gp.maxWorldRow; row++) {
             for (int col=0; col<gp.maxWorldCol; col++) {
                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                if (gp.tileM.tiles[tileNum].collision == true) {
                    node[col][row].solid = true;
                }
                getCost(node[col][row]);
            }
        }
    }
    public void getCost(Node node)
    {
        int xDist=Math.abs(node.col-startNode.col);
        int yDist=Math.abs(node.row-startNode.row);
        node.gCost=xDist+yDist;
        xDist=Math.abs(node.col-goalNode.col);
        yDist=Math.abs(node.row-goalNode.row);
        node.hCost=xDist+yDist;
        node.fCost=node.gCost+node.hCost;
    }
    public boolean search(){
        while (goalReached==false && step<500)
        {
            int col=currentNode.col;
            int row=currentNode.row;
            currentNode.checked=true;
            openList.remove(currentNode);
            if (row-1>=0)
                openNode(node[col][row-1]);
            if (col-1>=0)
                openNode(node[col-1][row]);
            if (row+1<gp.maxWorldRow)
                openNode(node[col][row+1]);
            if (col+1<gp.maxWorldCol)
                openNode(node[col+1][row]);
            int bestNodeIndex=0;
            int bestNodeCost=999;
            for (int i=0; i<openList.size(); i++)
            {
                if (openList.get(i).fCost<bestNodeCost)
                {
                    bestNodeCost=openList.get(i).fCost;
                    bestNodeIndex=i;
                }
                else if (openList.get(i).fCost==bestNodeCost)
                {
                    if (openList.get(i).gCost<openList.get(bestNodeIndex).gCost)
                    {
                        bestNodeIndex=i;
                    }
                }
            }
            if (openList.size()==0)
                break;
            currentNode=openList.get(bestNodeIndex);
            if (currentNode==goalNode)
            {
                goalReached=true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node)
    {
        if (node.open==false && node.checked==false && node.solid==false)
        {
            node.open=true;
            node.parent=currentNode;
            openList.add(node);
        }
    }
    public void trackThePath()
    {
        Node current=goalNode;
        while (current!=startNode)
        {
            pathList.add(0, current);
            current=current.parent;
        }
    }
}
