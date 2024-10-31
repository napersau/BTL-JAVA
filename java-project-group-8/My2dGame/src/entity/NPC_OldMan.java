package entity;


import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


public class NPC_OldMan extends Entity{
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		type = type_npc;
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 30;
		dialogueSet = -1;
		getImage();
		setDialogue();
	}
	    
	  public void getImage(){

	       
	    	up1 = setup("npc//oldman_up_1.png",gp.tileSize,gp.tileSize);
	        up2 = setup("npc//oldman_up_2.png",gp.tileSize,gp.tileSize);
	        down1 = setup("npc//oldman_down_1.png",gp.tileSize,gp.tileSize);
	        down2 = setup("npc//oldman_down_2.png",gp.tileSize,gp.tileSize);
	        left1 = setup("npc//oldman_left_1.png",gp.tileSize,gp.tileSize);
	        left2 = setup("npc//oldman_left_2.png",gp.tileSize,gp.tileSize);
	        right1 = setup("npc//oldman_right_1.png",gp.tileSize,gp.tileSize);
	        right2 = setup("npc//oldman_right_2.png",gp.tileSize,gp.tileSize);
	    }
	    
	    public void setDialogue() {
	    	
	    	dialogues[0][0] = "Hello pik";
	    	dialogues[0][1] = "You wanna find treasure ?";
	    	dialogues[0][2] = "I used to be a great sutpid wizard \nbut now ...I'm just a dirty old man !";
	    	dialogues[0][3] = "Good luck my boiiii";
	    	

	    	dialogues[1][0] = "If you become tired, rest at lake";
	    	dialogues[1][1] = "You look stronger than your mom";
	    	dialogues[1][2] = "In any case, dont be pussy!";
	    	
	    	dialogues[2][0] = "i wonder how to open the door...";
	    }
	    
	    public void setAction() {
//	    	if(onPath == true) {
////	    		int goalCol=12;
////	    		int goalRow=9;
//	    		int goalCol = (gp.player.WorldX + gp.player.solidArea.x)/gp.tileSize;
//	    		int goalRow = (gp.player.WorldY + gp.player.solidArea.y)/gp.tileSize;
//	    		searchPath(goalCol,goalRow);
//	    	}
//	    	else {
//	    		
//	    		actionLookCounter++;
//		    	if(actionLookCounter == 120) {
//		    		Random random = new Random();
//			    	int i = random.nextInt(100)+1;//random : 1->100
//			    	
//			    	if(i<=25) {
//			    		direction = "up";
//			    	}
//			    	if(i>25 && i <= 50) {
//			    		direction = "down";
//			    	}
//			    	if(i>50 && i<=75) {
//			    		direction = "left";
//			    	}
//			    	if(i > 75 && i<=100) {
//			    		direction = "right";
//			    	}
//			    	
//			    	actionLookCounter = 0;
//		    	}	
//	    	}
    		actionLookCounter++;
	    	if(actionLookCounter == 120) {
	    		Random random = new Random();
		    	int i = random.nextInt(100)+1;//random : 1->100
		    	
		    	if(i<=25) {
		    		direction = "up";
		    	}
		    	if(i>25 && i <= 50) {
		    		direction = "down";
		    	}
		    	if(i>50 && i<=75) {
		    		direction = "left";
		    	}
		    	if(i > 75 && i<=100) {
		    		direction = "right";
		    	}
		    	
		    	actionLookCounter = 0;
	    	}
	    	
	    }
	    public void speak() {
	    	
	    	facePlayer();
	    	startDialogue(this, dialogueSet);
	    	
	    	dialogueSet++;
	    	if(dialogues[dialogueSet][0] == null) {
	    		dialogueSet--;
	    	}
	    	onPath = true;
	    }
	   
}
