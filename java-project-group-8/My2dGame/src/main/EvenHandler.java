package main;

import java.awt.Rectangle;

import org.w3c.dom.css.Rect;

import data.Progress;
import entity.Entity;

public class EvenHandler {
	GamePanel gp;
	EventRect eventRect [][][];
	Entity eventMaster;
	
	int previousEventX,previousEventY;
	boolean canTouchEvent = true;
	int tempMap,tempCol,tempRow;
	public EvenHandler(GamePanel gp) {



		this.gp  = gp;
		eventMaster = new Entity(gp);
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		int map = 0;
		int col = 0;
		int row = 0;
		while(map <gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY= eventRect[map][col][row].y;
		
			col++;
			if(col == gp.maxWorldCol) {
				row++;
				col = 0;
				
				if(row == gp.maxWorldRow) {
					row = 0;map++;
				}
			}
		}
		setDialogue();
	}
	public void setDialogue() {
		eventMaster.dialogues[0][0] = "U fall in to a pit";
		eventMaster.dialogues[1][0] = "You drink water.\nLife and Mana has been recoverd.\n"
				+ "(The prog)";
		
		eventMaster.dialogues[1][1] = "DAMMM !..This water is the shit";
	}
	public void checkEvent() {



		
		//check if player charater is more than 1 tile away from event
		int xDistance = Math.abs(gp.player.WorldX-previousEventX);
		int yDistance =  Math.abs(gp.player.WorldY-previousEventY);
		int distance = Math.abs(xDistance-yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if(canTouchEvent == true) {
			if(hit(0,27,26,"right")==true) {
				//event happens	
				damagePit(gp.dialogueState);
			}
			else if(hit(0,23,12,"up")==true) {
				healingPool(gp.dialogueState); 
			}
			else if(hit(0,10,39,"any")==true) {
				teleport(1,12,13,gp.indoor);// to merchant house
			}
			else if(hit(1,12,13,"any")==true) {
				teleport(0,10,39,gp.outside); // to out side
			}
			else if(hit(1,12,9,"up")==true) {
				speak(gp.npc[1][0]);
			}
			else if(hit(0,12,9,"any")==true) {
				teleport(2, 9, 41,gp.dungeon); // to the dungeon
			}
			else if(hit(2,9,41,"any")==true) {
				teleport(0,12,9,gp.outside); // to out side
			}
			else if(hit(2,8,7,"any")==true) {
				teleport(3,26,41,gp.dungeon); // to B2
			}
			else if(hit(3,26,41,"any")==true) {
				teleport(2,8,7,gp.dungeon); // to B1
			}
			else if(hit(3,25,27,"any")==true) {
				skeletonLord(); // CUTSCENE 1
			}
		}
	}
	public boolean hit(int map,int col , int row,String regDirection) {


		boolean hit  = false;
		
		if(map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;
			
			//eventRect's SolidArea position 
			eventRect[map][col][row].x = col*gp.tileSize+eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize+eventRect[map][col][row].y;
			
			if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if(gp.player.direction.contentEquals(regDirection)||regDirection.contentEquals("any")) {
					hit = true;
					
					//check distant player and last event
					previousEventX = gp.player.WorldX;
					previousEventY = gp.player.WorldY;
				}
			}
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}
	public void damagePit(int gameState) {

		
		gp.gameState = gameState;
		gp.playSE(6);
		gp.player.life-=1;
		eventMaster.startDialogue(eventMaster, 0);
		canTouchEvent = false;
	}
	public void teleport(int map,int col,int row,int area) {
		
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		gp.nextArea = area;
		canTouchEvent = false;
		gp.playSE(13);
	}
	public void healingPool(int gameState) {


		if(gp.keyH.enterPreased == true) {
			
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			eventMaster.startDialogue(eventMaster, 1);
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.aSetter.setMonster();
			gp.saveLoad.save();
		}
	
	}
	public void speak(Entity entity) {
		if(gp.keyH.enterPreased == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}
	public void skeletonLord() {
		
		
		if(gp.bossBattleOn == false && Progress.skeletonLordDefeated== false) {
			
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.skeletonLord;
		}
	}
}
