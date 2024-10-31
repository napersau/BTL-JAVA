package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_Rock;

public class MON_Orc extends Entity{
	GamePanel gp;
	public MON_Orc(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life  = maxLife;
		maxMana = 4;
		mana = maxMana;
		attack = 8;
		defense = 2;
		exp = 10;
		knockBackPower = 5;
		motion1_duration = 40;
		motion2_duration = 85;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.width = 48;
		attackArea.height = 48;
		getImage();
		getAttackImage();
	}
	
	public void getImage() {
		up1 = setup("monster\\orc_up_1.png",gp.tileSize,gp.tileSize);
		up2 = setup("monster\\orc_up_2.png",gp.tileSize,gp.tileSize);
		down1 = setup("monster\\orc_down_1.png",gp.tileSize,gp.tileSize);
		down2 = setup("monster\\orc_down_2.png",gp.tileSize,gp.tileSize);
		left1 = setup("monster\\orc_left_1.png",gp.tileSize,gp.tileSize);
		left2 = setup("monster\\orc_left_2.png",gp.tileSize,gp.tileSize);
		right1 = setup("monster\\orc_right_1.png",gp.tileSize,gp.tileSize);
		right2 = setup("monster\\orc_right_2.png",gp.tileSize,gp.tileSize);
	}
	public void getAttackImage() {
		attackDown1 = setup("monster\\orc_attack_down_1.png",gp.tileSize,gp.tileSize*2);
    	attackDown2 = setup("monster\\orc_attack_down_2.png",gp.tileSize,gp.tileSize*2);
    	attackUp1 = setup("monster\\orc_attack_up_1.png",gp.tileSize,gp.tileSize*2);
    	attackUp2 = setup("monster\\orc_attack_up_2.png",gp.tileSize,gp.tileSize*2);
    	attackRight1 = setup("monster\\orc_attack_right_1.png",gp.tileSize*2,gp.tileSize);
    	attackRight2 = setup("monster\\orc_attack_right_2.png",gp.tileSize*2,gp.tileSize);
    	attackLeft1 = setup("monster\\orc_attack_left_1.png",gp.tileSize*2,gp.tileSize);
    	attackLeft2 = setup("monster\\orc_attack_left_2.png",gp.tileSize*2,gp.tileSize);
	}
	public void setAction() {

		
		if(onPath == true) {
			
			//CHECK if IT STOPS CHASING
			checkStopChasingOrNot(gp.player, 15,100);
			
			//SEARCH THE DIRECTION TO G0
    		searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
    		
    		
		}
		else {
			
			//CHECK IF IT STARTS CHASING
			checkStartChasingOrNot(gp.player, 5, 100);
			
			//GET RANDOM DIRECTION 
			getRandomDirection(120);
		}
		
		//CHECK ATTACK OR NOT
		if(attacking == false) {
			
			checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
		}
	}

	public void damageReaction() {
		
		actionLookCounter = 0;
//		direction = gp.player.direction;
		onPath = true; 
	}
	public void checkDrop() {
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
