package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Mana;


public class UI {
	GamePanel gp;
	Font arial_40,arial_80B;
//	BufferedImage imageKey ;
	public boolean messageOn = false;
	
	ArrayList<String> messageList = new ArrayList<>();
	ArrayList<Integer> messageCounterList = new ArrayList<>();
	
	public boolean gameEnd = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;// 0: the first screen , 1 :second
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int counter = 0;
	public Entity npc;
	public int charIndex = 0; //CHARACTER INDEX
	public String combinedText = "";
	
	
	BufferedImage heart_full, heart_half , heart_blank,crystal_ful,crystal_blank,coin;
	
	Graphics2D g2;
	
	public UI(GamePanel gp) {

		this.gp = gp;
		
		arial_40 = new Font("Arial",Font.PLAIN,40);
		arial_80B = new Font("Arial",Font.PLAIN,80);
		
//		OBJ_Key key = new OBJ_Key(gp);
//		imageKey = key.image;
		
		//CREATE HUB OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal = new OBJ_Mana(gp);
		crystal_ful = crystal.image;
		crystal_blank = crystal.image2;
		
		Entity bronze_coin = new OBJ_Coin_Bronze(gp);
		coin = bronze_coin.down1;
	}
	
	public void addMessage(String text) {
//		message = text;
//		messageOn = true;
		messageList.add(text);
		messageCounterList.add(0);
		
	}
	
	public void draw(Graphics2D g2) {



		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMonsterLife();
			drawMessage();
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		//CHARACTER STATE
		if(gp.gameState == gp.charaterState) {
			drawCharacterScreen();
			drawInventory(gp.player,true);
		}
		//OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
			
		}
		//GAMEOVER STATE
		if(gp.gameState == gp.gameoverState) {
			drawGameOverScreen();
		}
		
		//TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransitionScreen();
		}
		//TRANSITION STATE
		if(gp.gameState == gp.tradeState) {
				drawTradeScreen();
		}
		
		//SLEEP STATE
		if(gp.gameState == gp.sleepState) {
			
			drawSleepScreen();
			
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.maxscreenWidth, gp.maxscreenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));
		
		text = "Game Over";
		g2.setColor(Color.black);
		x = getXforCenterText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		
		//MaIn
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenterText(text);
		y+=gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//Back to the title screen
		text = "Quit";
		x = getXforCenterText(text);
		y+= 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
		
	}
	public void drawCharacterScreen() {
		//CREATE A FRAME
		final int frameX = gp.tileSize *2;
		final int frameY = gp.tileSize ;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX+20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 34;
		
		g2.drawString("Level", textX, textY);
		textY+=lineHeight;
		g2.drawString("Life", textX, textY);
		textY+=lineHeight;
		g2.drawString("Mana", textX, textY);
		textY+=lineHeight;
		g2.drawString("Strength", textX, textY);
		textY+=lineHeight;
		g2.drawString("Dexerity", textX, textY);
		textY+=lineHeight;
		g2.drawString("Attack", textX, textY);
		textY+=lineHeight;
		g2.drawString("Defense", textX, textY);
		textY+=lineHeight;
		g2.drawString("Exp", textX, textY);
		textY+=lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY+=lineHeight;
		g2.drawString("Coin", textX, textY);
		textY+=lineHeight+10;
		g2.drawString("Weapon", textX, textY);
		textY+=lineHeight+30;
		g2.drawString("Shield", textX, textY);
		
		//Values
		int tailX = (frameX + frameWidth) - 30;
		textY = frameY + gp.tileSize;
		String value;
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.life +"/"+gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.mana +"/"+gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+=lineHeight;
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-20, null);
		textY+=gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-20, null);
	
	}
	public void drawInventory(Entity entity,boolean cursor) {



		int frameX ;
		int frameY ;
		int frameWidth ;
		int frameHeight ;
		int slotCol = 0;
		int slotRow = 0;
		if(entity == gp.player) {
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		//FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		
		//SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
		//DRAW PLAYER ITEMS
		for(int i=0;i<entity.inventory.size();i++) {
			
			//Equip cursor
			if(entity.inventory.get(i)==entity.currentWeapon||entity.inventory.get(i)==entity.currentShield||
					entity.inventory.get(i)==entity.currentLight) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize,gp.tileSize,10,10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY,null);
			
			//DISPLAY AMOUNT
			if(entity == gp.player &&entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;
				
				String s  = "" + entity.inventory.get(i).amount;
				amountX = getXforAlignToRightText(s, slotX+44);
				amountY = slotY + gp.tileSize;
				
				//Shadow 
				g2.setColor(new Color(60,60,60));
				g2.drawString(s, amountX, amountY);
				
				//Number
				g2.setColor(Color.white);
				g2.drawString(s, amountX-3, amountY-3);
			}
			
			slotX+=slotSize;
			if( i== 4 || i==9 || i==14) {
				slotX = slotXstart;
				slotY+=slotSize;
			}
		}
		
		//CURSOR
		if(cursor == true) {
			int  cursorX = slotXstart + (slotSize*slotCol);
			int cursorY = slotYstart + (slotSize*slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			
			//DRAW CURSON
			g2.setColor(Color.white);
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight,10,10);
		
			//DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
			//Draw DescriptionText
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));
			int itemInedx = getItemIndexOnSlot(slotCol,slotRow);
			if(itemInedx < entity.inventory.size()) {
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				for(String line : entity.inventory.get(itemInedx).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY+=32;
				}
				
			}
		}
		else {
			
		}
		
	}
	public int getItemIndexOnSlot(int slotCol,int slotRow) {


		int intemI = slotCol + (slotRow * 5);
		return intemI;
	}
	public void drawPlayerLife() {
		//gp.player.life = 1;
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//Draw MAx life
		while(i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x,y,null);
			i++;
			x+=gp.tileSize;
		}
		//Reset current life
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		while(i < gp.player.life) {
			g2.drawImage(heart_half,x,y,null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full,x,y,null);
			}
			i++;
			x += gp.tileSize;
			
		}
		x= gp.tileSize/2-5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x+=35;
		}
		//Draw mana
		x= gp.tileSize/2-5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.mana) {
			
			g2.drawImage(crystal_ful, x, y, null);
			i++;
			x+=35;
		}
	}
	public void drawMonsterLife() {
		//MONSTER HP bar
		for(int i=0;i<gp.monster[1].length;i++) {
			if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].inCamera() == true) {
				
				Entity monster = gp.monster[gp.currentMap][i];
				if(monster.hpBarOn == true && monster.boss == false) {
					 double oneScale = (double)gp.tileSize/monster.maxLife;
					 double hpBarValue = oneScale*monster.life;
					 g2.setColor(new Color(35,35,35));
					 g2.fillRect(monster.getScreenX()-1, monster.getCenterY()-16, gp.tileSize+2, 10);
					 
					 g2.setColor(new Color(255,0,30));
					 g2.fillRect(monster.getScreenX(), monster.getScreenY()-15, (int)hpBarValue, 10);
					 
					 monster.hpBarCount++;
					 if(monster.hpBarCount > 600) {
						 monster.hpBarCount = 0;
						 monster.hpBarOn = false;
					 }
				 }
				else if(monster.boss == true) {
					
					
					 double oneScale = (double)gp.tileSize*8/monster.maxLife;
					 double hpBarValue = oneScale*monster.life;
					 
					 int x= gp.maxscreenWidth/2 - gp.tileSize*4;
					 int y = gp.tileSize*10;
					 
					 g2.setColor(new Color(35,35,35));
					 g2.fillRect(x-1,y-1, gp.tileSize*8+2, 22);
					 
					 g2.setColor(new Color(255,0,30));
					 g2.fillRect(x,y, (int)hpBarValue, 20);
					 
					 g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
					 g2.setColor(Color.white);
					 g2.drawString(monster.name, x+4, y-10);
				}
			
			}
		}
		
		 
	}

	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		for(int i=0;i<messageList.size();i++) {
			if(messageList.get(i)!=null) {
				
				g2.setColor(Color.black);
				g2.drawString(messageList.get(i), messageX+3, messageY+3);
				g2.setColor(Color.white);
				g2.drawString(messageList.get(i), messageX, messageY);
				
				int cnt = messageCounterList.get(i)+1;
				messageCounterList.set(i, cnt);
				messageY+=50;
				
				if(messageCounterList.get(i)> 100) {
					messageList.remove(i);
					messageCounterList.remove(i);
				}
			}
		}
	}
	public void drawTitleScreen() {

		if(titleScreenState == 0) {
			g2.setColor(new Color(70,120,80));
			g2.fillRect(0,0,gp.maxscreenWidth, gp.maxscreenHeight);
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
			String text = "Blue Boii Adventure";
			int x = getXforCenterText(text);
			int y = gp.tileSize*3;
			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text, x+5, y+5);;
			//MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//BLUE BOI IMAGE
			x = gp.maxscreenWidth / 2 - (gp.tileSize);
			y += gp.tileSize*2;
			g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);
		
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
			
			text = "New Game";
			x = getXforCenterText(text);
			y += gp.tileSize*3;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Load Game";
			x = getXforCenterText(text);
			y += gp.tileSize;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Quit";
			x = getXforCenterText(text);
			y += gp.tileSize;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize/2, y);
			}
		}
		else if(titleScreenState == 1){
			g2.setColor(new Color(70,120,80));
			g2.fillRect(0,0,gp.maxscreenWidth, gp.maxscreenHeight);
			//CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(32F));
			
			String text = "Select your class!";
			int x = getXforCenterText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenterText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text = "Thief";
			x = getXforCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Mage";
			x = getXforCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenterText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		
	}
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
		String text = "PAUSED";
		int x = getXforCenterText(text);
		int y= gp.maxscreenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {

		
		//WINDOW
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int width = gp.maxscreenWidth - (gp.tileSize*6);
		int height = gp.tileSize*4; 
		drawSubWindow(x, y, width, height);
		
		//SET NPCS DIALOGUE
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x+= gp.tileSize;
		y+=gp.tileSize;
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			
			
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			
			
			if(charIndex < characters.length) {
				
				gp.playSE(17);
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText+s;
				currentDialogue = combinedText;
				
				
				charIndex++;
			}
			
			
			if(gp.keyH.enterPreased == true) {
			
				charIndex = 0;
				combinedText = "";
				
				if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
					
					
					npc.dialogueIndex++;
					gp.keyH.enterPreased = false;
					
				}
			}
		}
		else {
			// NO TEXT IN ARRAY
			npc.dialogueIndex = 0;
			
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
			if(gp.gameState == gp.cutsceneState) {
				gp.csManager.scenePhase ++;
			}
			
		}
		
		for(String line : currentDialogue.split("\n")) {
			
			g2.drawString(line, x, y);
			y += 40;
		}
		
		
	}
	public void drawOptionsScreen() {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//SUB WINDOW
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		switch (subState) {
		case 0: options_top(frameX,frameY);break;
		case 1: options_fullScreenNotification(frameX,frameY);break;
		case 2: options_control(frameX,frameY);break;
		case 3: options_endGameConfirmation(frameX,frameY);break;
		}
		gp.keyH.enterPreased = false;
	}
	public void drawTradeScreen() {

		
		
		switch(subState){
		case 0:trade_select();break;
		case 1:trade_buy();break;
		case 2:trade_sell();break;
		}
		gp.keyH.enterPreased = false;
	}
	public void trade_select() {

		npc.dialogueSet = 0;
		drawDialogueScreen();
		//DRAW WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int)(gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		
		//DRAW TEXT
		x += gp.tileSize;
		y +=gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPreased == true) {
				subState = 1;
			}
		}
		
	
		y +=gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPreased == true) {
				subState = 2;
			}
		}
		
	
		y +=gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPreased == true) {
				commandNum = 0;
				npc.startDialogue(npc,1);
		
			}
		}
	}
	public void trade_buy() {


		//Draw player inventory
		drawInventory(gp.player, false);
		//Draw npc inventory
		drawInventory(npc, true);
		
		//Draw hint window
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		//Draw player coin window
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your coin: "+gp.player.coin, x+24, y+60);
		
		//Draw price Window
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x =(int)(gp.tileSize*5.5);
			y =(int)(gp.tileSize*5.5);
			width =(int)(gp.tileSize*2.5);
			height =(int)(gp.tileSize);
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32,32,null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text =""+price;
			x = getXforAlignToRightText(text, gp.tileSize*8-20);
			g2.drawString(text, x, y+34);
			
			//BUY AN Item
			if(gp.keyH.enterPreased == true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc,2);
					
				}
				else {
					if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						npc.startDialogue(npc,3);
						
					}
				}
//				else if(gp.player.inventory.size() == gp.player.maxInventorySize) {
//					subState = 0;
//					gp.gameState = gp.dialogueState;
//					currentDialogue ="You need more slot inventory to buy that";
//					drawDialogueScreen();
//				}
//				else {
//					gp.player.coin -= npc.inventory.get(itemIndex).price;
//					gp.player.inventory.add(npc.inventory.get(itemIndex));
//				}
				
			}
		}
	}
	public void trade_sell() {

		//Draw player inventory
				drawInventory(gp.player, true);
				
				int x;
				int y;
				int width;
				int height;
				//Draw hint window
				 x = gp.tileSize*2;
				 y = gp.tileSize*9;
				 width = gp.tileSize*6;
				 height = gp.tileSize*2;
				drawSubWindow(x, y, width, height);
				g2.drawString("[ESC] Back", x+24, y+60);
				
				//Draw player coin window
				x = gp.tileSize*12;
				y = gp.tileSize*9;
				width = gp.tileSize*6;
				height = gp.tileSize*2;
				drawSubWindow(x, y, width, height);
				g2.drawString("Your coin: "+gp.player.coin, x+24, y+60);
				
				//Draw price Window
				int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
				if(itemIndex < gp.player.inventory.size()) {
					x =(int)(gp.tileSize*15.5);
					y =(int)(gp.tileSize*5.5);
					width =(int)(gp.tileSize*2.5);
					height =(int)(gp.tileSize);
					drawSubWindow(x, y, width, height);
					g2.drawImage(coin, x+10, y+8, 32,32,null);
					
					int price = gp.player.inventory.get(itemIndex).price/2;
					String text =""+price;
					x = getXforAlignToRightText(text, gp.tileSize*18-20);
					g2.drawString(text, x, y+34);
					
					//Sell Item
					if(gp.keyH.enterPreased == true) {
						if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
							gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
							commandNum = 0;
							subState = 0;
							npc.startDialogue(npc,4);
				
							
						}
						else {
							if(gp.player.inventory.get(itemIndex).amount > 1) {
								gp.player.inventory.get(itemIndex).amount--;
							}
							else {
								gp.player.inventory.remove(itemIndex);
							}
							gp.player.coin += price;
						}
					}
				}
	}
	public void drawTransitionScreen() {
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.maxscreenWidth, gp.maxscreenHeight);
		
		if(counter == 50) { // The TRANSITION IS DONe
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.WorldX = gp.tileSize*gp.eHandler.tempCol;
			gp.player.WorldY = gp.tileSize*gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.WorldX;
			gp.eHandler.previousEventY = gp.player.WorldY;
			gp.changeArea();
		}
	}
	public void options_top(int frameX,int frameY) {


		int textX;
		int textY;
		//TITLES
		String text = "Options";
		textX = getXforCenterText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		
		//FuLL SCreen ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
	
		}
		//MUSIC
		textY+=gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SE
		textY+=gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		//CONTROL
		textY+=gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				subState = 2;
				commandNum = 3;
			}
		}
		//END GAME
		textY+=gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		//BACK
		textY+=gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
		}
		
		
		//Full SCREEN CHECK BOX
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2+24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY,24,24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY,24,24);
		}
		//MUSIC VOLUME
		textY+= gp.tileSize;
		g2.drawRect(textX, textY, 120,24);
		int volumeWidth = 24*gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//SE VOLUME
		textY+= gp.tileSize;
		g2.drawRect(textX, textY, 120,24);
		volumeWidth = 24*gp.SE.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	public void drawSleepScreen() {
		
		counter++;
		
		if(counter < 120) {
			
			gp.eManager.lighting.filterAlpha += 0.01f;
			
			if(gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		if(counter >= 120) {
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if(gp.eManager.lighting.filterAlpha <= 0) {
				gp.eManager.lighting.filterAlpha  = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.eManager.lighting.dayCounter = 0;
				gp.gameState = gp.playState;
				
				gp.player.getImage();
			}
		}
		
	}
	public void options_fullScreenNotification(int  frameX,int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will take \neffect after restarting \nthe game.";
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		textY = frameY + gp.tileSize+ 9;
		//Back 
		textY += gp.tileSize*6;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				subState = 0;
			}
		}
	}
	public void drawSubWindow(int x,int y,int width,int height) {
		
		Color c = new Color(0,0,0,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,225,225);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	public void options_control(int frameX,int frameY) {
		
		int textX;
		int textY;
		
		String text = "Control";
		textX = getXforCenterText(text);
		textY = frameY+gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX+gp.tileSize;
		textY+= gp.tileSize;
		g2.drawString("Move", textX, textY);textY+=gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY);textY+=gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY);textY+=gp.tileSize;
		g2.drawString("Charater Screen", textX, textY);textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY);textY+=gp.tileSize;
		g2.drawString("Options", textX, textY);textY+=gp.tileSize;
		
		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY);textY+=gp.tileSize;
		g2.drawString("Enter", textX, textY);textY+=gp.tileSize;
		g2.drawString("F", textX, textY);textY+=gp.tileSize;
		g2.drawString("C", textX, textY);textY+=gp.tileSize;
		g2.drawString("P", textX, textY);textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY);textY+=gp.tileSize;
		
		//BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0 ) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased==true) {
				subState = 0;
			}
		}
	}
	public void options_endGameConfirmation(int frameX,int frameY) {
		int textX = frameX+gp.tileSize;
		int textY = frameY+gp.tileSize*3;
		
		currentDialogue = "Quit the game and \nreturn to the \ntitle screen ?";
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=48;
		}
		
		//YES
		String text = "Yes";
		textX = getXforCenterText(text);
		textY+=gp.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState=0;
				
				gp.resetGame(true);
			}
		}
		
		//NO
		text = "No";
		textX = getXforCenterText(text);
		textY+=gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPreased == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	public int getXforCenterText(String text) {
		int x;
		int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.maxscreenWidth/2 - len/2;
		return x;
	}
	public int getXforAlignToRightText(String text,int tailX) {
		int x;
		int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = tailX - len;
		return x;
	}
}
