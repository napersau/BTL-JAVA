package entity;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.File;
import java.io.IOException;
import java.lang.classfile.CodeBuilder.CatchBuilder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.lang.model.type.UnknownTypeException;
import javax.swing.plaf.synth.SynthProgressBarUI;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Axe;
import object.OBJ_FireBall;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;


public class Player extends Entity{
  
    KeyHandler keyH;

    public final int screenX,screenY; //man hinh game -> tao camera
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    public Player(GamePanel gp, KeyHandler keyH) {

    	
    	super(gp);
    	
        this.keyH = keyH;
        
        this.screenX = gp.maxscreenWidth/2;
        this.screenY = gp.maxscreenHeight/2;
        
        
        setDefaultValues();
         
    }
    public int getCurrentWeaponSlot() {
    	int currentWeaponSlot = 0;
    	for(int i=0;i<inventory.size();i++) {
    		if(inventory.get(i)== currentWeapon) {
    			currentWeaponSlot = i;
    		}
    	}
    	return currentWeaponSlot;
    }
    public int getCurrenShieldSlot() {
    	int currentWeaponSlot = 0;
    	for(int i=0;i<inventory.size();i++) {
    		if(inventory.get(i)== currentShield) {
    			currentWeaponSlot = i;
    		}
    	}
    	return currentWeaponSlot;
    }
    
    public void getImage(){

        
        up1 = setup("player\\boy_up_1.png",gp.tileSize,gp.tileSize);
        up2 = setup("player\\boy_up_2.png",gp.tileSize,gp.tileSize);
        down1 = setup("player\\boy_down_1.png",gp.tileSize,gp.tileSize);
        down2 = setup("player\\boy_down_2.png",gp.tileSize,gp.tileSize);
        left1 = setup("player\\boy_left_1.png",gp.tileSize,gp.tileSize);//player\\leftside 1
        left2 = setup("player\\boy_left_2.png",gp.tileSize,gp.tileSize); //player\\right side walking 3
        right1 = setup("player\\boy_right_1.png",gp.tileSize,gp.tileSize);    
        right2 = setup("player\\boy_right_2.png",gp.tileSize,gp.tileSize);
    }
    
    public void getAtackImage() {
    	if(currentWeapon.type == type_sword) {
    		attackDown1 = setup("player\\boy_attack_down_1.png",gp.tileSize,gp.tileSize*2);
        	attackDown2 = setup("player\\boy_attack_down_2.png",gp.tileSize,gp.tileSize*2);
        	attackUp1 = setup("player\\boy_attack_up_1.png",gp.tileSize,gp.tileSize*2);
        	attackUp2 = setup("player\\boy_attack_up_2.png",gp.tileSize,gp.tileSize*2);
        	attackRight1 = setup("player\\boy_attack_right_1.png",gp.tileSize*2,gp.tileSize);
        	attackRight2 = setup("player\\boy_attack_right_2.png",gp.tileSize*2,gp.tileSize);
        	attackLeft1 = setup("player\\boy_attack_left_1.png",gp.tileSize*2,gp.tileSize);
        	attackLeft2 = setup("player\\boy_attack_left_2.png",gp.tileSize*2,gp.tileSize);
    	}
    	if(currentWeapon.type == type_axe) {
    		attackDown1 = setup("player\\boy_axe_down_1.png",gp.tileSize,gp.tileSize*2);
        	attackDown2 = setup("player\\boy_axe_down_2.png",gp.tileSize,gp.tileSize*2);
        	attackUp1 = setup("player\\boy_axe_up_1.png",gp.tileSize,gp.tileSize*2);
        	attackUp2 = setup("player\\boy_axe_up_2.png",gp.tileSize,gp.tileSize*2);
        	attackRight1 = setup("player\\boy_axe_right_1.png",gp.tileSize*2,gp.tileSize);
        	attackRight2 = setup("player\\boy_axe_right_2.png",gp.tileSize*2,gp.tileSize);
        	attackLeft1 = setup("player\\boy_axe_left_1.png",gp.tileSize*2,gp.tileSize);
        	attackLeft2 = setup("player\\boy_axe_left_2.png",gp.tileSize*2,gp.tileSize);
    	}
    	if(currentWeapon.type == type_pickaxe) {
    		attackDown1 = setup("player\\boy_pick_down_1.png",gp.tileSize,gp.tileSize*2);
        	attackDown2 = setup("player\\boy_pick_down_2.png",gp.tileSize,gp.tileSize*2);
        	attackUp1 = setup("player\\boy_pick_up_1.png",gp.tileSize,gp.tileSize*2);
        	attackUp2 = setup("player\\boy_pick_up_2.png",gp.tileSize,gp.tileSize*2);
        	attackRight1 = setup("player\\boy_pick_right_1.png",gp.tileSize*2,gp.tileSize);
        	attackRight2 = setup("player\\boy_pick_right_2.png",gp.tileSize*2,gp.tileSize);
        	attackLeft1 = setup("player\\boy_pick_left_1.png",gp.tileSize*2,gp.tileSize);
        	attackLeft2 = setup("player\\boy_pick_left_2.png",gp.tileSize*2,gp.tileSize);
    	}
    	
    }
    public void getGuardImage() {
    	guardUp = setup("player\\boy_guard_up.png",gp.tileSize,gp.tileSize);
    	guardDown = setup("player\\boy_guard_down.png",gp.tileSize,gp.tileSize);
    	guardLeft = setup("player\\boy_guard_left.png",gp.tileSize,gp.tileSize);
    	guardRight = setup("player\\boy_guard_right.png",gp.tileSize,gp.tileSize);
    }
    public void setDefaultValues(){


    	//PLAYER POSITION // Spoil lúc đầu game
   	 	WorldX = gp.tileSize*23;
        WorldY = gp.tileSize*19;
        
        // Hình chữ nhật của thg player nhỏ nhỏ
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; 
        
        solidArea.width = 32;
        solidArea.height = 32;
        //Attack Area
//        attackArea.width = 36;
//        attackArea.height = 36;
        
        
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";
        
        //PLAYER STATE
        level = 1;
        maxLife = 10;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        amor = 10;
        strength = 10; // more strength -> more damage 
        dexterity = 1; // more dex -> more defense
        nextLevelExp = 5;
        coin  = 500;
        
        currentShield = new OBJ_Shield_Wood(gp);
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentLight = null;
        projectile = new OBJ_FireBall(gp);
        attack = getAttack();
        defense = getDefense();
        
        getImage();
        getAtackImage();
        getGuardImage();
        setItem();
        setDialogue();
        
    }
    public void setDefaultPositions() {
    	gp.currentMap = 0;
    	 WorldX = gp.tileSize*23;
         WorldY = gp.tileSize*21;
         direction = "down";
         
        
    }
    public void restoreStatus() {


    	life = maxLife;
    	mana = maxMana;
    	speed = defaultSpeed;
    	invincible = false;
    	transparent = false;
    	attacking = false;
    	guarding = false;
    	knockBack = false;
    	lightUpdated = true;
    	
    }
    public void setItem() {

    	inventory.clear();
    	inventory.add(currentWeapon);
    	inventory.add(currentShield);
    	inventory.add(new OBJ_Key(gp));
    	inventory.add(new OBJ_Key(gp));
    }
    public int getAttack() {
    	motion1_duration = currentWeapon.motion1_duration;
    	motion2_duration = currentWeapon.motion2_duration;
    	attackArea = currentWeapon.attackArea;
    	return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
    	return defense = dexterity * currentShield.defenseValue;
    }
    
    public void update(){

    	if(knockBack == true) {
    		
            collisionOn = false;
        	gp.cChecker.checkTile(this);
        	gp.cChecker.checkObject(this, true);
        	gp.cChecker.checkEntity(this, gp.npc);    	
        	gp.cChecker.checkEntity(this, gp.monster);
        	gp.cChecker.checkEntity(this, gp.iTile);
    		
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
    	else if(keyH.spacePreased == true) {
    		
    		guarding = true;
    		guardCounter++;
    		
    		
    	}
    	else if(keyH.upPreased == true ||keyH.downPreased== true||keyH.rightPreased== true||keyH.leftPreased== true ||keyH.enterPreased==true) {
    		if(keyH.upPreased == true){
                direction = "up";
              
            }else if(keyH.downPreased== true){
                direction = "down";
               
            }else if(keyH.rightPreased== true){
                direction = "right";
            
            }
            else if(keyH.leftPreased== true){
                direction = "left";
               
            }
            
            //CHECK TILE COLLISION
            collisionOn = false;
        	gp.cChecker.checkTile(this);
        	
        	//CHECK OBJECT COLLISION
        	int objIndex=gp.cChecker.checkObject(this, true);
        	
        	pickUpObject(objIndex);
        	
        	//CHECK NPC COLLISION
        	int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
//        	System.out.println(npcIndex);
        	interactNPC(npcIndex);
        	
        	//CHECK MONSTER COLLISION
        	int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        	contactMonster(monsterIndex);
        	
        	//CHECK INTERACTIVE TILE COLLISION
        	int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        	
        	
        	//CHECk EVENT
        	gp.eHandler.checkEvent();
        	
        	
        	// collision = true : player cant move
        	if(collisionOn == false && keyH.enterPreased == false) {
        		
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
        	if(keyH.enterPreased == true && attackCanceled == false) {
        		gp.playSE(7);
        		attacking = true;
        		spriteCount = 0;
        	}
        	
        	attackCanceled = false;
        	gp.keyH.enterPreased = false;
        	guarding = false;
        	guardCounter = 0;
        	
        	spriteCount+=1; //+1 frame
            //redraw per 10 frames 
        	
        	if(spriteCount > 12) {
            	if(spriteNum==1) {
            		spriteNum = 2;
            	}
            	else {
    				spriteNum = 1;
    			}
            	spriteCount = 0;
            }
    	}
    	else {
    		
    		standCount++;
    		if(standCount == 20) {
    			spriteNum = 1;
    			standCount = 0;
    		}
    		guarding = false;
    		guardCounter = 0;
    	}
    	if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCount == 30 && projectile.haveResource(this) == true) {
    		
    		//Set DEFAULT COORDINATES , DIRECTION , USER to PROJECTILE
    		projectile.set(WorldX,WorldY,direction,true,this);
    		
    		//SUbtract THe cost mana
    		projectile.subtractResource(this);
    		
    		//CHECK VACANCY
    		for(int i =0;i<gp.projectile[1].length;i++) {
    			if(gp.projectile[gp.currentMap][i] == null) {
    				gp.projectile[gp.currentMap][i] = projectile;
    				break;
    			}
    		}
    		
    		shotAvailableCount = 0;
    		gp.playSE(10);
    	}
    	
    	//INVINCIBLE COUNTER
    	if(invincible ==true) {
    		invincibleCounter++;
    		if(invincibleCounter > 60) {
    			invincible = false;
    			transparent = false;
    			invincibleCounter = 0;
    		}
    	}
       
    	if(shotAvailableCount < 30) {
    		shotAvailableCount++;
    		
    	}
    	
    	if(life > maxLife) {
			life = maxLife;
		}
    	if(mana > maxMana) {
			mana = maxMana;
		}
    	if(keyH.godModeOn == true) {
    		
    	}
    	else {
    		if(life <= 0) {
        		gp.gameState = gp.gameoverState;
        		gp.ui.commandNum = -1;
        		gp.stopMusic();
        		gp.playSE(12);
        	}
    	}
    	
    }

    public void setDialogue() {
    	 dialogues [0][0]= "You are level "+level+" now!\n";
    }
    public void pickUpObject(int i) {



    	
    	if(i != 999) {
    		//Pick Up ONLY
    		if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
    			gp.obj[gp.currentMap][i].use(this);
    			gp.obj[gp.currentMap][i] = null;
    		}
    		
    		//OBSTACLE
    		else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
    			if(keyH.enterPreased == true) {
    				attackCanceled = true;
    				gp.obj[gp.currentMap][i].interact();
    			}
    		}
    		//INVENTORY ITEM
    		else {

        		String text;
        		
        		if(canObtainItem(gp.obj[gp.currentMap][i])==true) {
        		
        			gp.playSE(1);
        			text = "Got a "+gp.obj[gp.currentMap][i].name+"!";
        			
        		}else {
    				text = "Cannot carry more !";
    			}
        		gp.ui.addMessage(text);
        		gp.obj[gp.currentMap][i] = null;
    		}
    	}
    }
    public void interactNPC(int i) {

    	if(i != 999) {
    	if(gp.keyH.enterPreased == true) {
    		
    			attackCanceled = true;
            	gp.npc[gp.currentMap][i].speak();
        	}
    		gp.npc[gp.currentMap][i].move(direction);
    	}
    	
    }
    public void getSleepingImage() {
    	
    	up1 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        up2 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        down1 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        down2 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        left1 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        left2 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
        right1 = setup("object\\tent.png", gp.tileSize, gp.tileSize);    
        right2 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
    	
    }
    public void contactMonster(int i) {

    	if(i != 999) {
    		if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
    			gp.playSE(6);
    			int damage = gp.monster[gp.currentMap][i].attack - defense;
  			   	if(damage < 1) {
  			   		damage = 1;
  			   }
    			life-= damage;
    			invincible = true;
    			transparent = true;
    		}
    	
    	}
    }
    public void damageMonster(int i,Entity attacker,int attack,int knockBackPower) {




	   if(i != 999) {
//		   System.out.println(1);
		   if(gp.monster[gp.currentMap][i].invincible == false) {
			   gp.playSE(5);
			   
			   if(knockBackPower  > 0) {
					setknockBack(gp.monster[gp.currentMap][i],attacker,knockBackPower);
			   }
			   // Nếu mất cân bằng thì damage nhân 4
			   if(gp.monster[gp.currentMap][i].offBalance == true) {
				   attack *= 4;
			   }
			   
			   int damage = attack - gp.monster[gp.currentMap][i].defense;
			   if(damage < 0) {
				   damage = 0;
			   }
			   
			   gp.monster[gp.currentMap][i].life-=damage;
			   gp.ui.addMessage(damage+"Damage!");
			   
			   gp.monster[gp.currentMap][i].invincible = true;
			   gp.monster[gp.currentMap][i].damageReaction();
			   
			   if(gp.monster[gp.currentMap][i].life <= 0) {
				   gp.monster[gp.currentMap][i].dying = true;
				   
				   gp.ui.addMessage("Killed the "+gp.monster[gp.currentMap][i].name+"!");
				   gp.ui.addMessage("Exp"+gp.monster[gp.currentMap][i].exp+"!");
				   exp+= gp.monster[gp.currentMap][i].exp;
				   checkLevelUp();
			   }
		   }
	   }
    }
   	public void damageInteractiveTile(int i) {



	   if(i!= 999 && gp.iTile[gp.currentMap][i].destructible ==true && gp.iTile[gp.currentMap][i].isCorrectItem(this)==true&&gp.iTile[gp.currentMap][i].invincible == false) {
		   gp.iTile[gp.currentMap][i].playSE();
		   gp.iTile[gp.currentMap][i].invincible = true;
		   
		   //GENERATOR PARTICLE
		   generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
		   
		   gp.iTile[gp.currentMap][i].life--;
		   if(gp.iTile[gp.currentMap][i].life == 0) {
			   
//			   gp.iTile[gp.currentMap][i].checkDrop(); //MINING STATs
			   
			   gp.iTile[gp.currentMap][i]  = gp.iTile[gp.currentMap][i].getDestroyedForm();   
		   }
		  
		   
	   }
   }
   public void checkLevelUp() {

	   if(exp >= nextLevelExp) {
		   level++;
		   nextLevelExp = nextLevelExp*2;
		   maxLife+=2;
		   strength++;
		   dexterity++;
		   attack = getAttack();
		   defense = getDefense();
		   
		   gp.playSE(8);
		   gp.gameState = gp.dialogueState;
		   setDialogue();
		   startDialogue(this, 0);
	   }
   }
   	public void selectItem() {



   		int indexItem = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
   		if(indexItem < inventory.size()) {
   			Entity selectedItem = inventory.get(indexItem);
   			
   			if(selectedItem.type == type_axe||selectedItem.type == type_sword||selectedItem.type == type_pickaxe) {
   				currentWeapon = selectedItem;
   				attack = getAttack();
   				getAtackImage(); // update attackImage
   			}
   			if(selectedItem.type == type_shield) {
   				currentShield = selectedItem;
   				defense = getDefense();
   			}
   			if(selectedItem.type == type_light) {
   				if(currentLight == selectedItem) {
   					currentLight = null;
   				}
   				else {
   					currentLight = selectedItem;
   				}
   				lightUpdated = true;
   			}
   			if(selectedItem.type == type_consumable) {
   				//Later
   				if(selectedItem.use(this)==true) {
   					if(selectedItem.amount > 1) {
   						selectedItem.amount--;
   					}
   					else {
   						inventory.remove(indexItem);
   					}
   					
   				}
   				
   			}
   		}
   	}
    public void draw(Graphics2D g2){

        
        BufferedImage image = null;
        
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        
        switch(direction){
            case "up":
            	if(attacking == false) {
            
            		if(spriteNum==1) {image = up1;}
                	if(spriteNum==2) {image = up2;}
            	}
            	if(attacking == true) {
            		tempScreenY = screenY - gp.tileSize;
            		if(spriteNum==1) {image = attackUp1;}
                	if(spriteNum==2) {image = attackUp2;}
            	}
            	if(guarding == true) {
            		image = guardUp;
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
            	if(guarding == true) {
            		image = guardDown;
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
            	if(guarding == true) {
            		image = guardRight;
            	}
            	break;
            case "left":
            	
            	if(attacking == false) {
            		
            		if(spriteNum==1) {image = left1;}
                	if(spriteNum==2) {image = left2;}
            	}
            	if(attacking == true) {
            		tempScreenX = screenX - gp.tileSize;
            		if(spriteNum==1) {image = attackLeft1;}
                	if(spriteNum==2) {image = attackLeft2;}
            	}
            	if(guarding == true) {
            		image = guardLeft;
            	}
                break;
        }
        
        if(transparent == true) {
        	
        	//set opacity when player got damage
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        
        }
        //DRAW PLAYER IMAGE
       if(drawing == true) {
    	   g2.drawImage(image, tempScreenX, tempScreenY, null);
       }
        
        //RESET Alpha
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        //DEGUB
//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.white);
//        g2.drawString("invincible"+invincibleCounter, 40, 400);
    }
    public void damageProjectile(int i) {

    	if(i != 999) {
    		Entity projectile = gp.projectile[gp.currentMap][i];
    		projectile.alive = false;
    		generateParticle(projectile, projectile);
    	}
    }

    
    public int searchItemInInventory(String itemName) {
    	
    	int itemIndex = 999;
    	for(int i=0;i<inventory.size();i++) {
    		if(inventory.get(i).name.equals(itemName)) {
    			itemIndex = i;
    			break;
    		}
    	}
    	return itemIndex;
    	
    }
    public boolean canObtainItem(Entity item) {
    	
    	boolean canObtain = false;
    	Entity newItem = gp.eGenerator.getObject(item.name);
    	
    	//CHECK IF STACKABLE
    	
    	if(newItem.stackable == true) {
    		
    		int index = searchItemInInventory(newItem.name);
    		if(index != 999) {
    			inventory.get(index).amount++;
    			canObtain = true;
    		}
    		else {
    			//New Item need to check vacancy(Chỗ trống)
    			if(inventory.size() != maxInventorySize) {
    				
    				inventory.add(newItem);
    				canObtain = true;
    			}
    		}
    	}
    	else {
    		//NOt stackable so check vacancy
    		if(inventory.size() != maxInventorySize) {
				inventory.add(newItem);
				canObtain = true;
			}
    	}
    	return canObtain;
    }
    public void speak() {
    	
    }
}
