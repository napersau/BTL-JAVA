 package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Mana;

public class MON_Skeleton extends Entity{
	GamePanel gp;
	public static final String monName = "Skeleton Lord";
	public MON_Skeleton(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		sleep = true; 
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life  = maxLife;
		maxMana = 4;
		mana = maxMana;
		attack = 10;
		defense = 2;
		exp = 50;
		knockBackPower = 5;
		motion1_duration = 25;
		motion2_duration = 50;
		boss = true;
		int size = gp.tileSize * 5;
		solidArea.x = 40;
		solidArea.y = 40;
		solidArea.width = size - 48*2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.width = 170;
		attackArea.height = 170;
		getImage();
		getAttackImage();
		setDialogue();
	}
	
	public void getImage() {
		
		if(inRange == false) {
			int i = 5;
			up1 = setup("monster\\skeletonlord_up_1.png",gp.tileSize*i,gp.tileSize*i);
			up2 = setup("monster\\skeletonlord_up_2.png",gp.tileSize*i,gp.tileSize*i);
			down1 = setup("monster\\skeletonlord_down_1.png",gp.tileSize*i,gp.tileSize*i);
			down2 = setup("monster\\skeletonlord_down_2.png",gp.tileSize*i,gp.tileSize*i);
			left1 = setup("monster\\skeletonlord_left_1.png",gp.tileSize*i,gp.tileSize*i);
			left2 = setup("monster\\skeletonlord_left_2.png",gp.tileSize*i,gp.tileSize*i);
			right1 = setup("monster\\skeletonlord_right_1.png",gp.tileSize*i,gp.tileSize*i);
			right2 = setup("monster\\skeletonlord_right_2.png",gp.tileSize*i,gp.tileSize*i);
		}
		else {
			int i = 5;
			up1 = setup("monster\\skeletonlord_phase2_up_1.png",gp.tileSize*i,gp.tileSize*i);
			up2 = setup("monster\\skeletonlord_phase2_up_2.png",gp.tileSize*i,gp.tileSize*i);
			down1 = setup("monster\\skeletonlord_phase2_down_1.png",gp.tileSize*i,gp.tileSize*i);
			down2 = setup("monster\\skeletonlord_phase2_down_2.png",gp.tileSize*i,gp.tileSize*i);
			left1 = setup("monster\\skeletonlord_phase2_left_1.png",gp.tileSize*i,gp.tileSize*i);
			left2 = setup("monster\\skeletonlord_phase2_left_2.png",gp.tileSize*i,gp.tileSize*i);
			right1 = setup("monster\\skeletonlord_phase2_right_1.png",gp.tileSize*i,gp.tileSize*i);
			right2 = setup("monster\\skeletonlord_phase2_right_2.png",gp.tileSize*i,gp.tileSize*i);
		}
		
	}
	public void setDialogue() {
		
		dialogues[0][0]= "No one can steal my treasure";
		dialogues[0][1]= "You will die here";
		dialogues[0][2]= "WELCOM TO YOUR DOOM";
	}
	public void getAttackImage() {
		
		if(inRange == false) {
			int i = 5;
			
			attackDown1 = setup("monster\\skeletonlord_attack_down_1.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackDown2 = setup("monster\\skeletonlord_attack_down_2.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackUp1 = setup("monster\\skeletonlord_attack_up_1.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackUp2 = setup("monster\\skeletonlord_attack_up_2.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackRight1 = setup("monster\\skeletonlord_attack_right_1.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackRight2 = setup("monster\\skeletonlord_attack_right_2.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackLeft1 = setup("monster\\skeletonlord_attack_left_1.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackLeft2 = setup("monster\\skeletonlord_attack_left_2.png",gp.tileSize*i*2,gp.tileSize*i);
		}
		else {
int i = 5;
			
			attackDown1 = setup("monster\\skeletonlord_phase2_attack_down_1.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackDown2 = setup("monster\\skeletonlord_phase2_attack_down_2.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackUp1 = setup("monster\\skeletonlord_phase2_attack_up_1.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackUp2 = setup("monster\\skeletonlord_phase2_attack_up_2.png",gp.tileSize*i,gp.tileSize*2*i);
	    	attackRight1 = setup("monster\\skeletonlord_phase2_attack_right_1.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackRight2 = setup("monster\\skeletonlord_phase2_attack_right_2.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackLeft1 = setup("monster\\skeletonlord_phase2_attack_left_1.png",gp.tileSize*i*2,gp.tileSize*i);
	    	attackLeft2 = setup("monster\\skeletonlord_phase2_attack_left_2.png",gp.tileSize*i*2,gp.tileSize*i);
		}
		
	}
	public void setAction() {

		if(inRange == false && life < maxLife/2) {
			inRange = true;
			getImage();
			getAttackImage();
			defaultSpeed ++;
			speed = defaultSpeed;
			attack +=5;
			
		}
		if(getTileDistance(gp.player) < 10) {
			
			moveTowardPlayer(60); // per 60 frame boss check player position and change direction
    		
		}
		else {
			
		
			getRandomDirection(120);
		}
		
		//CHECK ATTACK OR NOT
		if(attacking == false) {
			
			checkAttackOrNot(60, gp.tileSize*10, gp.tileSize*5);
		}
	}

	public void damageReaction() {
		
		actionLookCounter = 0;
//	
	}
	public void checkDrop() {
		
		gp.bossBattleOn = false;
		
		Progress.skeletonLordDefeated = true;
		
		//RESOTE PREVIOUS MUSIC
		gp.stopMusic();
		gp.playMusic(19);
		
		//REMOVE THE IRON DOOR
		for(int i=0;i<gp.obj[1].length;i++) {
			
			if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
				gp.playSE(21);
				gp.obj[gp.currentMap][i] = null;
			}
		}
		
		//CAST A DIE
		int i = new Random().nextInt(100)+1;
		//SET THE MOSNTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_Mana(gp));
		}
	}
}
