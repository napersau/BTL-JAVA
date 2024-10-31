package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity{
	public static final String npcName = "Big Rock";
	public NPC_BigRock(GamePanel gp) {
		super(gp);
		type = type_npc;
		direction = "down";
		speed = 1;
		name = npcName;
		solidArea = new Rectangle();
		solidArea.x = 2;
		solidArea.y = 6;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 40;
		solidArea.height = 40;
		dialogueSet = -1;
		getImage();
		setDialogue();
	}
	    
	  public void getImage(){

	       
	    	up1 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        up2 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        down1 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        down2 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        left1 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        left2 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        right1 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	        right2 = setup("npc//bigrock.png",gp.tileSize,gp.tileSize);
	    }
	    
	    public void setDialogue() {
	    	
	    	dialogues[0][0] = "It's a giant rock.";
	    	
	    	
	    }
	    public void update() {
	    	
	    }
	    public void setAction() {
	    	
	    }
	    public void speak() {
	    	
	    	facePlayer();
	    	startDialogue(this, dialogueSet);
	    	
	    	dialogueSet++;
	    	if(dialogues[dialogueSet][0] == null) {
	    		dialogueSet--;
	    	}
	    }
	    public void move(String direction) {
	    	this.direction = direction;
	    	
	    	checkColision();
	    	if(collisionOn == false) {
	    		
	    		switch(direction) {
	    		case"up" : WorldY -= speed; break;
	    		case"down" : WorldY += speed; break;
	    		case"left" : WorldX -= speed; break;
	    		case"right" : WorldX += speed; break;

	    		}
	    	}
	    	
	    	detectPlate();
	    }
	    
	    public void detectPlate() {
	    		
	    	
	    	ArrayList<InteractiveTile> plateList = new ArrayList<>();
	    	ArrayList<Entity> rockList = new ArrayList<>();
	    	
	    	//CREATE A PLATE LIST
	    	for(int i=0;i<gp.iTile[1].length;i++) {
	    		
	    		if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name!= null && gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
	    			plateList.add(gp.iTile[gp.currentMap][i]);
	    		}
	    		
	    	}
	    	
	    	//CREATE A ROCK LIST
	    	for(int i=0;i<gp.npc[1].length;i++) {
	    		
	    		if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {

	    			rockList.add(gp.npc[gp.currentMap][i]);
	    		}
	    	}
	    	
	    	//SCAN THE PLATE LIST
	    	
	    	int cnt = 0;
	    	for(int i=0;i<plateList.size();i++) {
	    		
	    		int xDis = Math.abs(WorldX - plateList.get(i).WorldX);
	    		int yDis = Math.abs(WorldY - plateList.get(i).WorldY);
	    		int dis = Math.max(xDis, yDis);
	    		
	    		//rock on plate
	    		if(dis < 8) {
	    			if(linkedEntity == null) {
	    				linkedEntity = plateList.get(i);
		    			gp.playSE(3);
	    			}
	    		}
	    		else {
	    			if(linkedEntity == plateList.get(i)) {
	    				linkedEntity = null;
	    			}
	    			
	    		}
	    	}
	    	
	    	
	    	//SCAN ROCK LIST
	    	for(int i=0;i<rockList.size();i++) {
	    		
	    		if(rockList.get(i).linkedEntity != null) {
	    			cnt++;
	    		}
	    	}
	    	
	    	//IF ALL THE ROCK ON PLATE , IRON DOOR OPEN :))
	    	
	    	if(cnt == rockList.size()) {
	    		
	    		for(int i=0;i<gp.obj[1].length;i++) {
	    			
	    			if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
	    				
	    				gp.obj[gp.currentMap][i] = null;
	    				gp.playSE(21);
	    			}
	    		}
	    	}
	    }
}
