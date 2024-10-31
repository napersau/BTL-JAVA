package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.PlayeDummy;
import monster.MON_Skeleton;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {
	GamePanel gp;
	Graphics2D g2;
	
	String endCredit;
	
	public int sceneNum;
	public int scenePhase;
	public int counter =0;
	public float alpha = 0f;
	public int y;
	
	//Scene Number
	public final int NA = 0;
	public final int skeletonLord = 1;
	public final int ending = 2;
	
	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
		endCredit = "End game\n"
				+ "Group 8"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "Dat\n"
				+ "Khoi\n"
				+ "Hieu\n"
				+ "Thuat\n"
				+ "Vinh\n\n\n\n\n"
				+"Thank you for playing !";
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch(sceneNum) {
		case skeletonLord: scene_skeletonLord();break;
		case ending : scene_ending();break;
		}
	}
	public void scene_skeletonLord() {

		
		if(scenePhase == 0) {
			gp.bossBattleOn = true;
			
			//SHUT THE IRON DOOR
			for(int i=0;i<gp.obj[1].length;i++) {
				
				if(gp.obj[gp.currentMap][i] == null) {
					
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].WorldX = gp.tileSize*25;
					gp.obj[gp.currentMap][i].WorldY = gp.tileSize*28;
					gp.obj[gp.currentMap][i].temp = true;
					gp.playSE(21);
					break;
				}
			}
			
			//Search a vacant slot for dummy
			for(int i=0;i<gp.npc[1].length;i++) {
				
				if(gp.npc[gp.currentMap][i] == null) {
					
					gp.npc[gp.currentMap][i] = new PlayeDummy(gp);
					gp.npc[gp.currentMap][i].WorldX = gp.player.WorldX;
					gp.npc[gp.currentMap][i].WorldY = gp.player.WorldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
			
			gp.player.drawing = false;
			scenePhase++;
		}
		if(scenePhase == 1) {
			
			
			gp.player.WorldY -=2;
			
			if(gp.player.WorldY < gp.tileSize*16) {
				
				scenePhase++;
			}
		}
		if(scenePhase == 2) {
			
			//Srearch boss
			for(int i=0;i<gp.monster[1].length;i++) {
				
				if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_Skeleton.monName)) {
					
					gp.monster[gp.currentMap][i] .sleep = false;
					
					gp.ui.npc = gp.monster[gp.currentMap][i];
					scenePhase++;
					break;
				}
			}
		}
		if(scenePhase == 3) {
			
			//BOSS SPEAK
			
			gp.ui.drawDialogueScreen();
			
		}
		if(scenePhase == 4) {
			
			//RETURN CAMERA TO PLAYER
			
			//SREACH DUMMY
			for(int i=0;i<gp.npc[1].length;i++) {
				
				if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayeDummy.npcName)) {
					
					gp.player.WorldX = gp.npc[gp.currentMap][i].WorldX;
					gp.player.WorldY = gp.npc[gp.currentMap][i].WorldY-gp.tileSize;
					
					//DELETE DUMMY
					gp.npc[gp.currentMap][i] = null;
					break;
				}
				
			}
			
			//START DRAWING PLAYER AGAIN
			gp.player.drawing = true;
			
			//RESET 
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;
			
			
			//CHANGE MUSIC
			gp.stopMusic();
			gp.playMusic(22);
		}
	}
	
	public void scene_ending() {
		
		
		if(scenePhase ==0) {
			
			gp.stopMusic();
			gp.ui.npc = new OBJ_BlueHeart(gp);
			scenePhase++;
			
		}
		if(scenePhase == 1) {
			
//			scenePhase++;
//			DISPLAY DIALOGUES
			gp.ui.drawDialogueScreen();
			
		}
		if(scenePhase == 2) {
			
			//PLAY THE FANFARE MUSIC
			gp.playSE(4);
			scenePhase++;
		}
		if(scenePhase == 3) {
			
			//WAIT UNTIL THE SOUND EFECT END
			if(counterReached(300)==true) {
				//300 frame = 5 sceond
				scenePhase++;
				
			}
			
		}
		if(scenePhase == 4) {
			
			alpha+= 0.005f;
			if(alpha > 1f) {
				alpha = 1f;
			}
			
			drawBlackBackGround(alpha);
			if(alpha == 1) {
				alpha = 0;
				scenePhase ++;
			}
		}
		if(scenePhase == 5) {
			
			drawBlackBackGround(1f);
			alpha+= 0.005f;
			if(alpha > 1f) {
				alpha = 1f;
			}
			
			
			String text ="Sau khi THơ săn đánh bại chúa tể địa ngục,\n"
					+ "Ngươi đã tìm thầy kho báu huyền thoại !\n"
					+ "Nhưng cuộc hành trình chưa kết thúc.\n"
					+ "Một Sử thi mới sẽ đc viết lên.";
			
			drawString(alpha, 38f, 200, text, 70);
			if(counterReached(600) == true) {
				scenePhase++;
			}
			
		}
		
		if(scenePhase ==6) {
			
			drawBlackBackGround(1f);
			y=gp.maxscreenHeight/2;
			drawString(1f, 120f, y, "Treasure Hunter", 70);
			
			if(counterReached(480) == true) {
				scenePhase++;
			}

		}
		if(scenePhase == 7) {
			
			drawBlackBackGround(1f);
			y=gp.maxscreenHeight/2;
			drawString(1f, 38f, y, endCredit, 40);
			
			if(counterReached(480) == true) {
				scenePhase++;
			}			
		}
		
		if(scenePhase == 8) {
			
			drawBlackBackGround( 1f);
			
			//Scrolling the credit
			y--;
			drawString(1f, 38f, y, endCredit, 40);
			
		}
	}
	public boolean counterReached(int target) {
		
		boolean counterReached = false;
		counter++;
		if(counter > target) {
			counterReached = true;
			counter = 0;
			
		}
		return counterReached;
	}
	
	public void drawBlackBackGround(float alpha) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0,0, gp.maxscreenWidth, gp.maxscreenHeight);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, 1f));

	}
	public void drawString(float alpha, float fontSize,int y,String text,int lineHeight) {
		
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		
		for(String line : text.split("\n")) {
			
			int x = gp.ui.getXforCenterText(line);
			g2.drawString(line, x, y);
			y+= lineHeight;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, 1f));
	}
}
