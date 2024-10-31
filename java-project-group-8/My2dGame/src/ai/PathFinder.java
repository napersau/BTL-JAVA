package ai;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
	GamePanel gp;
	Node [][] node;
	ArrayList<Node> openList = new ArrayList<Node>();
	public ArrayList<Node> pathList =  new ArrayList<Node>();
	Node startNode,goalNode , currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
		
	}
	public void instantiateNodes() {
		
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			node[col][row] = new Node(col, row);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	public void resetNode() {
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//Reset open , checked and solid state
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
			
		}
		//Reset other settings
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol,int startRow,int goalCol,int goalRow,Entity e) {
		
		resetNode();
		
		//Set Start and Goal node
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//Set solid node
			//CHECK TILES
			int tileNum = gp.TileM.mapTileNum[gp.currentMap][col][row];
			if(gp.TileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			
			//CHECK interactive Tiles
			for(int i=0;i<gp.iTile[1].length;i++) {
				if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
					int itCol = gp.iTile[gp.currentMap][i].WorldX /gp.tileSize;
					int itRow = gp.iTile[gp.currentMap][i].WorldY /gp.tileSize;
					
					node[itCol][itRow].solid = true;
 				}
			}
			
			//SET COST 
			getCost(node[col][row]);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	public void getCost(Node node) {
		//G cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		//H cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		
		node.hCost = xDistance + yDistance;
		
		//F cost
		node.fCost = node.gCost + node.hCost;
	}
	public boolean search() {
		
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			//check the current node
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//Open up node
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			//Open left node
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			//Open down node
			if(row + 1 < gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			//Open right node
			if(col +1 < gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			//Find bes node
			int bestNodeIndex = 0;
			int bestNodeCost = 999;
			
			for(int i=0;i<openList.size();i++) {
				
				//check if node'F cost is better
				if(openList.get(i).fCost < bestNodeCost) {
					bestNodeCost = openList.get(i).fCost;
					bestNodeIndex = i;
				}
				
				//if F cost is equal ,check G cost
				else if(openList.get(i).fCost ==  bestNodeCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
				
			}
			//If no node in openlist , endloop
			if(openList.size() == 0) {
				break;
			}
			
			//after loop , openList[bestNodeIndex] is next step (= currentNode)
			currentNode = openList.get(bestNodeIndex);
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			
			step++;
		}
		return goalReached;
	}
	public void trackThePath() {
		Node current = goalNode;
		while(current != startNode) {
			pathList.add(0,current);
			current = current.parent;
		}
	}
	public void openNode(Node node) {
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
}
