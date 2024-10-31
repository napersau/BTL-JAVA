package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//This  stores variables of char , monster ,npcs

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import main.Disk;

public class Entity {
	GamePanel gp;
	public BufferedImage image,image2,image3;
	public String name;
	
	public boolean collision = false;
//	public String Disk = "F:\\\\";
	
    public int WorldX,WorldY; // position player/npcs... in  worldmap
    public int speed;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    
    public BufferedImage up1,up2 , down1,down2,right1,right2,left1,left2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2
    ,guardUp,guardDown,guardLeft,guardRight;
    public  String direction="down";
    //COUNTER
    public int spriteNum = 1, spriteCount = 0; // make animation per frame by loop spriteNum
    public int standCount = 0;
    public int dyingCount = 0;
    public int hpBarCount = 0;
    public int shotAvailableCount =0;
    public int actionLookCounter = 0;
    public int invincibleCounter = 0;;
    public int knockBackCounter = 0;
    public int guardCounter  = 0;
    public int offBalanceCounter = 0;
    
    
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    
    public boolean transparent  = false;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean guarding = false;
    public boolean offBalance = false;
    public boolean inRange = false;
    public boolean boss =false;
    public  boolean sleep = false;
    public boolean temp = false; // CHECK TEMP OBJ FOR CUT SCENE THAT CAN DELETE WHEN U DIE 
    public boolean drawing = true; //CHECK DRAWING PLAYER
    public int solidAreaDefaultX, solidAreaDefaultY;
    
    public String dialogues[][] = new String[20][20];
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public Entity attacker;
	public Entity loot;
	public boolean opened = false;
   
	
    //CHARACTER STATES
    public int maxLife;
    public int life;
 	 public int defaultSpeed;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int	maxMana;
    public int mana;
    public int amor;
    public int knockBackPower ;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public Entity currentLight;
    public Entity linkedEntity;
    
    //ITEM ATTRIBUTES 
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost; // cost mana to shoot projectile
    public ArrayList<Entity> inventory = new ArrayList<Entity>();
    public final int maxInventorySize = 15;
    public int price;
    public boolean stackable = false;
    public int amount = 1; //amount of stackable item
    public int lightRadius;
    
    //Type
    public int type;// 0 = player, 1 = npcs , 2 = monster
    public final int type_player = 0;
    public final int type_monster = 2;
    public final int type_npc = 1;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10; 
    
    
    public Entity(GamePanel gp) {

    	this.gp = gp;
    }
    public int getLeftX() {
    	return WorldX + solidArea.x;
    }
    public int getRightX() {
    	return WorldX + solidArea.x + solidArea.width;
    }
    public int getTopY() {
    	return WorldY + solidArea.y;
    }
    public int getBottomY() {
    	return WorldY + solidArea.y+solidArea.height;
    }
    public int getCol() {
    	return (WorldX + solidArea.x)/gp.tileSize;
    }
    public int getRow() {
    	return (WorldY+solidArea.y)/gp.tileSize;
    }
    public int getXdistance(Entity target) {
    	int xDistance = Math.abs(getCenterX() - target.getCenterX());
    	return xDistance;
    	
    }
    public int getYdistance(Entity target) {
    	int yDistance = Math.abs(getCenterY() - target.getCenterY());
    	return yDistance;
    }
    public int getTileDistance(Entity target) {
    	int TileDistance = (getXdistance(target)+getYdistance(target))/gp.tileSize;
    	return TileDistance;
    }
    public int getGoalCol(Entity target) {
    	return (target.WorldX + target.solidArea.x)/gp.tileSize;
    }
    public int getGoalRow(Entity target) {
    	return (target.WorldY + target.solidArea.y)/gp.tileSize;
    }
    public void resetCounter() {
    	   spriteCount = 0; // make animation per frame by loop spriteNum
    	   standCount = 0;
    	   dyingCount = 0;
    	   hpBarCount = 0;
    	   shotAvailableCount =0;
    	   invincibleCounter = 0;;
    	   knockBackCounter = 0;
    	   guardCounter  = 0;
    	   offBalanceCounter = 0;
    }

    public void setAction() {
    	
    }
    public void damageReaction() {
    	
    }
    public boolean use(Entity entity) {   	
    	return false;
    }
    public void move(String direction) {
    	// move BIG ROCK
    }
    public void checkDrop() {
    	
    }
    public void interact() {
    	
    }
    public void setLoot(Entity loot) {
    	
    }
    public String getOppositeDirection(String direction) {
    	String tmp = "";
    	switch(direction) {
    	case "up": tmp = "down";break;
    	case "down": tmp = "up"; break;
    	case "left": tmp = "right";break;
    	case "right": tmp = "left";break;
    	}
    	return tmp;
    }
    public int getDetected(Entity user,Entity target[][],String targetName) {

    	int index = 999;
    	
    	//CHECK THE SURROUNDING OBJ
    	int nextWorldX = user.getLeftX();
    	int nextWorldY = user.getTopY();
    	
    	switch(user.direction) {
    	case "up" : nextWorldY= user.getTopY() - gp.player.speed; break;
    	case "down" : nextWorldY = user.getBottomY() +  gp.player.speed;break;
    	case "left" : nextWorldX = user.getLeftX() - gp.player.speed;break;
    	case "right" :nextWorldX = user.getRightX() +  gp.player.speed ;break;
    	}
    	
    	int col = nextWorldX/gp.tileSize;
    	int row = nextWorldY/gp.tileSize;
    	
    	for(int i=0;i<target[1].length;i++) {
    		if(target[gp.currentMap][i] != null) {
    			if(target[gp.currentMap][i].getCol() == col && 
    					target[gp.currentMap][i].getRow() == row &&
    					target[gp.currentMap][i].name.equals(targetName)) {
    					index = i;
    					break;
    			}
    		}
    	}
    	return index;
    }
    public void dropItem(Entity droppedItem) {
    	for(int i = 0;i<gp.obj[1].length;i++) {
    		if(gp.obj[gp.currentMap][i] == null) {
    			gp.obj[gp.currentMap][i] = droppedItem;
    			gp.obj[gp.currentMap][i].WorldX = WorldX;
    			gp.obj[gp.currentMap][i].WorldY = WorldY;
    			break;
    		}
    	}
    	
    }
    public void speak() {

    }
    public void facePlayer() {
    	switch(gp.player.direction) {
    	case "up":
    		direction = "down";
    		break;
    	case "down":
    		direction = "up";
    		break;
    	case "left":
    		direction = "right";
    		break;
    	case "right":
    		direction = "left";
    		break;
    	}
    }
    public void startDialogue(Entity entity,int setNum) {
    	
    	gp.gameState = gp.dialogueState;
    	gp.ui.npc = entity;
    	dialogueSet = setNum;
    }
    public void update() {
    	if(sleep == false) {
    		if(knockBack == true) {
        		
        		checkColision();
        		
        		if(collisionOn == true) {
        			knockBackCounter = 0;
        			knockBack = false;
        			speed = defaultSpeed;
        		}
        		else if(collisionOn == false) {
        			switch(knockBackDirection) {
        			case "up": {
        				WorldY -= speed;
        				break;
        			}case "down": {
        				WorldY += speed;
        				break;
        			}case "left": {
        				WorldX -= speed;
        				break;
        			}case "right": {
        				WorldX += speed;
        				break;}
        			}
        			
        			knockBackCounter ++;
        			if(knockBackCounter == 10) {
        				knockBackCounter = 0;
        				knockBack = false;
        				speed = defaultSpeed;
        			}
        		}
        	}
        	else if(attacking == true) {
        		attacking();
        	}
        	else {
        		setAction();
            	checkColision();
            	
             	// collision = true : player cant move
            	if(collisionOn == false) {
            		switch (direction) {
        			case "up": {
        				WorldY -= speed;
        				break;
        			}case "down": {
        				WorldY += speed;
        				break;
        			}case "left": {
        				WorldX -= speed;
        				break;
        			}case "right": {
        				WorldX += speed;
        				break;
        			}}
        			
            	}
            	
            	spriteCount+=1; //+1 frame
        	    //redraw per 10 frames 
        		
        		if(spriteCount > 24) {
        	    	if(spriteNum==1) {
        	    		spriteNum = 2;
        	    	}
        	    	else {
        				spriteNum = 1;
        			}
        	    	spriteCount = 0;
        	    }
        	
        	}

    		
    		if(invincible ==true) {
        		invincibleCounter++;
        		if(invincibleCounter > 40) {
        			invincible = false;
        			invincibleCounter = 0;
        		}
        	}
        	if(shotAvailableCount < 30) {
        		shotAvailableCount++;
        		
        	}
        	
        	if(offBalance == true) {
        		offBalanceCounter ++;
        		if(offBalanceCounter > 60) {
        			offBalanceCounter = 0;
        			offBalance = false;
        		}
        	}
    	}

    	else if(sleep == true){
    		
    		
    	}
    	
    }
	public void checkColision() {


    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkObject(this, false);
    	gp.cChecker.checkEntity(this, gp.npc);
    	gp.cChecker.checkEntity(this, gp.monster);
    	gp.cChecker.checkEntity(this, gp.iTile);
    	boolean contactPLayer = gp.cChecker.checkPlayer(this);
    	
    	if(this.type == type_monster && contactPLayer == true) {
    		damagePlayer(attack);
    	}
    	
	}
    public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0; // 6 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator,Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
    public void checkStopChasingOrNot(Entity target,int distance,int rate) {
    	
    	if(getTileDistance(target) > distance) {
    		
    		int i = new Random().nextInt(rate);
    		if(i == 0) {
    			onPath = false;
    		}
    	}
    }
    public void checkStartChasingOrNot(Entity target,int distance,int rate) {
    	
    	if(getTileDistance(target) < distance) {
    		
    		int i = new Random().nextInt(rate);
    		if(i == 0) {
    			onPath = true;
    		}
    	}
    }
    public void checkShootOrNot(int rate,int shotInterval) {
    	
    	int i = new Random().nextInt(rate);
    	if(i == 0 && projectile.alive == false && shotAvailableCount == shotInterval) {
    		projectile.set(WorldX, WorldY, direction, true, this);
    		
    		//CHECK VACANCY
    		for(int j=0;j<gp.projectile[1].length;j++) {
    			if(gp.projectile[gp.currentMap][j] == null) {
    				gp.projectile[gp.currentMap][j] = projectile;
    				break;
    			}
    		}
    		
    		shotAvailableCount = 0;
    	}
    }
	public void getRandomDirection(int interval) {
		
		actionLookCounter++;
    	if(actionLookCounter == interval) {
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
    public void setknockBack(Entity target,Entity attacker,int knockBackPower) {

    	this.attacker = attacker;
    	target.knockBackDirection = attacker.direction;
    	target.speed += knockBackPower;
    	target.knockBack = true;
    }
    public void damagePlayer(int attack) {


    	if(gp.player.invincible == false) {
			//give damage

			int damage = attack - gp.player.defense;
    		gp.playSE(6);
			
    		//GET AN OPPOSTIE DIRECTION of this attacker
    		String canGuadDirection = getOppositeDirection(direction);
    		
    		if(gp.player.guarding == true && gp.player.direction.equals(canGuadDirection)) {
    			
    			//PARRY
    			if(gp.player.guardCounter < 15) {
    				damage = 0;
    				gp.playSE(16);
    				
    				
    				setknockBack(this, gp.player, knockBackPower);
    				
    				
    				offBalance = true;
    				spriteCount = -60;
    			}
    			else {
    				//NORMAL GUARD
        			damage /= 3;
        			
        			gp.playSE(15);
    			}
    		}
    		else {
    			//NOT GUARDING
    			gp.playSE(6);
    			if(damage < 1) {
			   		damage = 1;
			   	}
    		}
			
    		if(damage != 0) {
    			gp.player.transparent = true;
    			setknockBack(gp.player, this, knockBackPower);
    		}
    		
			gp.player.life-=damage;
			gp.player.invincible = true;
			
		}
    }
    public void attacking() {



    	spriteCount++;
    	
    	//PLAYER ATTACK ANIMATIONS
    	if(spriteCount  <= motion1_duration) {
    		spriteNum = 1;
    	}
    	
    	if(spriteCount > motion1_duration && spriteCount <= motion2_duration ) {
    		spriteNum = 2;
    		
    		//SAVE current WorldX , worldY, solidArea
    		int currentWorldX = WorldX;
    		int currentWorldY = WorldY;
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		//Adjust player's World X,Y for the attackArea
    		switch(direction) {
    		case "up": WorldY -= attackArea.height;break;
    		case "down":WorldY += attackArea.height;break;
    		case "left":WorldX -= attackArea.width;break;
    		case "right":WorldX += attackArea.width;break;
    		}
    		
    		solidArea.width = attackArea.width;
    		solidArea.height = attackArea.height;
    		
    		if(type == type_monster) {
    			
    			if(gp.cChecker.checkPlayer(this) == true) {
    				damagePlayer(attack);
    			}
    		}
    		else {
    	  		//CHECK MONSTER COLLISION
        		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        	
        		gp.player.damageMonster(monsterIndex,this,attack,currentWeapon.knockBackPower);
        		
        		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        		gp.player.damageInteractiveTile(iTileIndex);
        		
        		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
        		gp.player.damageProjectile(projectileIndex);
    			
    		}
  
    		
    		WorldX = currentWorldX;
    		WorldY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    	}
    	
    	if(spriteCount > motion2_duration) {
    		spriteNum = 1;
    		spriteCount  = 0;
    		attacking = false;
    	}
    }
    public int getCenterX() {
    	int centerX = WorldX + left1.getWidth()/2;
    	return centerX;
    }
    public int getCenterY() {
    	int centerY = WorldY + up1.getHeight()/2;
    	return centerY;
    }
    public void moveTowardPlayer(int interval) { // FOR BOSS MOVE
    	
    	actionLookCounter++;
    	if(actionLookCounter > interval) {
    		if(getXdistance(gp.player) > getYdistance(gp.player)) {
    			
    			if(gp.player.getCenterX() < getCenterX()) {
    				direction = "left";
    			}
    			else {
    				direction = "right";
    			}
    		}
    		else if(getXdistance(gp.player) < getYdistance(gp.player)) {
    			
    			if(gp.player.getCenterY() < getCenterY()) {
    				direction = "up";
    			}
    			else {
    				direction = "down";
    			}
    		}
    		actionLookCounter = 0;
    	}
    }
    public void checkAttackOrNot(int rate,int straight , int horizontal) {

    
    	boolean targetInRange = false;
    	int xDis = getXdistance(gp.player);
    	int yDis = getYdistance(gp.player);
    	switch(direction) {
    	case "up" : 
    		if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
    			targetInRange = true;
    		}
    		break;
    	case "down": 
    		if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
    			targetInRange = true;
    		}
    		break;
    	case "left": 
    		if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
    			targetInRange = true;
    		}
    		break;
    	case "right": 
    		if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
    			targetInRange = true;
    		}
    		break;
    	}
    	
    	if(targetInRange == true) {
    	
    		//CHECK IF IT INITIATES AN ATTACK
    		int i = new Random().nextInt(rate);
    		if( i == 0 ) {
    			attacking = true;
    			spriteNum = 1;
    			spriteCount = 0;
    			shotAvailableCount = 0;
    		}
    	}
    }
    public int getScreenX() {
    	return WorldX - gp.player.WorldX + gp.player.screenX;
    }
    public int getScreenY() {
    	return  WorldY - gp.player.WorldY + gp.player.screenY;
    }
    public boolean inCamera() {
	   if(WorldX + gp.tileSize*5 > gp.player.WorldX - gp.player.screenX &&
				WorldX - gp.tileSize < gp.player.WorldX + gp.player.screenX &&
				WorldY + gp.tileSize*5 > gp.player.WorldY - gp.player.screenY &&
				WorldY - gp.tileSize < gp.player.WorldY + gp.player.screenY ) {
		   return true;
	   }
	   return false;
   }
    
    public void draw(Graphics2D g2) {

		
		BufferedImage image = null;
		
		if(inCamera() == true) {
			  int tempScreenX = getScreenX();
		      int tempScreenY = getScreenY();
		        
		        
		        switch(direction){
		            case "up":
		            	if(attacking == false) {
		            
		            		if(spriteNum==1) {image = up1;}
		                	if(spriteNum==2) {image = up2;}
		            	}
		            	if(attacking == true) {
		            		tempScreenY = getScreenY() - up1.getHeight();
		            		if(spriteNum==1) {image = attackUp1;}
		                	if(spriteNum==2) {image = attackUp2;}
		            	}
		                break;
		            case "down":
		            	if(attacking == false) {
		            		if(spriteNum==1) {image = down1;}
		                	if(spriteNum==2) {image = down2;}
		            	}
		            	if(attacking == true) {
		            		if(spriteNum==1) {image = attackDown1;}
		                	if(spriteNum==2) {image = attackDown2;}
		            	}
		                break;
		            case "right":
		            	if(attacking == false) {
		            		if(spriteNum==1) {image = right1;}
		                	if(spriteNum==2) {image = right2;}
		            	}
		            	if(attacking == true) {
		            		if(spriteNum==1) {image = attackRight1;}
		                	if(spriteNum==2) {image = attackRight2;}
		            	}
		            	break;
		            case "left":
		            	
		            	if(attacking == false) {
		            	
		            		
		            		if(spriteNum==1) {image = left1;}
		                	if(spriteNum==2) {image = left2;}
		            	}
		            	if(attacking == true) {
		            		tempScreenX = getScreenX() - left1.getWidth();
		            		if(spriteNum==1) {image = attackLeft1;}
		                	if(spriteNum==2) {image = attackLeft2;}
		            	}
		                break;
		        }
		        
		        
			
			 
			 
			if(invincible == true) {
				hpBarOn = true;
				hpBarCount = 0;
				changeAlpha(g2, 0.4F);
		        
		    }
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image,tempScreenX,tempScreenY,null);
			changeAlpha(g2, 1F);
		}
		
    }
    public void dyingAnimation(Graphics2D g2) {
    	
    	dyingCount++;
    	int i = 5;
    	if(dyingCount <= i) {
			changeAlpha(g2, 0f);
    	}
    	if(dyingCount > i && dyingCount <= i*2) {
			changeAlpha(g2, 1f);
    	}
    	if(dyingCount > i*2 && dyingCount <= i*3) {
    		changeAlpha(g2, 0f);
    	}
    	if(dyingCount > i*3 && dyingCount <= i*4) {
    		changeAlpha(g2, 1f);		
    	}
    	if(dyingCount > i*4 && dyingCount <= i*5) {
    		changeAlpha(g2, 0f);    	
    	}
    	if(dyingCount > i*5 && dyingCount <= i*6) {
    		changeAlpha(g2, 01f);    	
    	}
    	if(dyingCount > i*6 && dyingCount <= i*7) {
    		changeAlpha(g2, 0f);    	
    	}
    	if(dyingCount > i*7 && dyingCount <= i*8) {
    		changeAlpha(g2, 1f);    	
    	}
    	
    	if(dyingCount > i*8) {
    		
    		alive = false;
    	}
    	
    }
    public void changeAlpha(Graphics2D g2,float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));

    }
    public BufferedImage setup(String imagePath,int width,int height) {
    	UtilityTool utoTool = new UtilityTool();
    	BufferedImage Image = null;
    	try {
    		Image = ImageIO.read(new File(Disk.name +"java-project-group-8\\My2dGame\\res\\"+imagePath));
    		
    		Image = utoTool.scaleImage(Image, width, height);
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return Image;
    }
    public void searchPath(int goalCol,int goalRow) {
    	int startCol = (WorldX + solidArea.x)/gp.tileSize;
    	int startRow = (WorldY + solidArea.y)/gp.tileSize;
    	
    	gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow,this);
    	
    	if(gp.pFinder.search() == true) {
    		// NEXT WORLDX,Y
    		
    		int nextX = gp.pFinder.pathList.get(0).col*gp.tileSize;
    		int nextY = gp.pFinder.pathList.get(0).row*gp.tileSize;

    		
    		//Entity's solidArea Position
    		int enLeftX = WorldX + solidArea.x;
    		int enRightX = WorldX +solidArea.x+ solidArea.width;
    		int enTopY = WorldY + solidArea.y;
    		int enBottomtY = WorldY + solidArea.y+solidArea.height;
    		
    		if(enTopY > nextY && enLeftX >= nextX && enRightX < gp.tileSize+nextX) {
    			direction="up";
    		}
    		else if(enTopY < nextY && enLeftX >= nextX && enRightX < gp.tileSize+nextX) {
    			direction="down";
    		}
    		else if(enTopY >= nextY && enBottomtY < nextY + gp.tileSize) {
    			//Left or Right
    			
    			if(enLeftX > nextX) {
    				direction = "left";
    			}
    			if(enLeftX < nextX) {
    				direction = "right";
    			}    			
    		}
    		else if(enTopY > nextY && enLeftX > nextX) {
    			// up or left
    			direction  = "up";
    			checkColision();
    			if(collisionOn == true) {
    				direction = "left";
    			}
    		}
    		else if(enTopY > nextY && enLeftX < nextX) {
    			// up or right
    			direction  = "up";
    			checkColision();
    			if(collisionOn == true) {
    				direction = "right";
    			}
    		}
    		else if(enTopY < nextY && enLeftX > nextX) {
    			//down or left
    			direction  = "down";
    			checkColision();
    			if(collisionOn == true) {
    				direction = "left";
    			}
    		}
    		else if(enTopY < nextY && enLeftX < nextX) {
    			//down or right
    			direction  = "down";
    			checkColision();
    			if(collisionOn == true) {
    				direction = "right";
    			}
    		}
    		
//    		int nextCol = gp.pFinder.pathList.get(0).col;
//    		int nextRow = gp.pFinder.pathList.get(0).row;
//    		if(nextCol == goalCol && nextRow == goalRow) {
//    			onPath = false;
//    		}
    		
    	}
    	
    }

}
