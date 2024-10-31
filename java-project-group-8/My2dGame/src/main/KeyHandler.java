
package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Disk;
public class KeyHandler implements KeyListener{
	GamePanel gp;
    public boolean upPreased,downPreased,rightPreased,leftPreased,enterPreased,shotKeyPressed,spacePreased;
    
    //DEBUG
    public boolean showDebugText = false;
    public boolean godModeOn = false;
	public KeyHandler(GamePanel gp) {

		this.gp = gp;
	}
	
    @Override
    public void keyTyped(KeyEvent e) {


    }
    @Override
    public void keyPressed(KeyEvent e) {


    	int code  = e.getKeyCode();
        if(gp.gameState == gp.titleState) {
        	//TITLE STATE
        	titleState(code);
        }
    	
    	
        //PLAY STATE
         if(gp.gameState == gp.playState) {
        	playState(code);
        	
        }
        //PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
        	pauseState(code);
        	
        }
        
        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
        	dialogueState(code);
        
        }
         //Charater state
        else if(gp.gameState == gp.charaterState) {
        	charaterState(code);
        }
         
        //OPTIONS
        else if(gp.gameState == gp.optionsState) {
        	optionsState(code);
        }
         
        //GAMEOVER STATE
        else if(gp.gameState == gp.gameoverState) {
        	gameoverState(code);
        }
         
         //TRADE STATE
        else if(gp.gameState == gp.tradeState) {
        	tradeState(code);
        }
         
         //MAP STATE
        else if(gp.gameState == gp.mapState) {
        	mapState(code);
        }
    }
    public void titleState(int code) {

    	if(gp.gameState == gp.titleState) {
    		if(code == KeyEvent.VK_W){
                upPreased = true;
                gp.ui.commandNum --;
                if(gp.ui.commandNum<0) {
                	gp.ui.commandNum =2;
                	
                }
    		}
            if(code == KeyEvent.VK_S){
                downPreased = true;
                gp.ui.commandNum ++;
                if(gp.ui.commandNum>2) {
                	gp.ui.commandNum = 0;
                }
            }
            
            if(code == KeyEvent.VK_ENTER) {
            	if(gp.ui.commandNum == 0) {
            		gp.gameState = gp.playState;
            		gp.playMusic(0);
            	}
            	if(gp.ui.commandNum == 1) {
            		//LOAD GAME
            		gp.saveLoad.load();
            		gp.gameState = gp.playState;
            		gp.playMusic(0);
            	}
            	if(gp.ui.commandNum == 2) {
            		System.exit(0);
            	}
            }
    	}
    }
  
    public void playState(int code) {



    	if(code == KeyEvent.VK_W){
            upPreased = true;
        }
        if(code == KeyEvent.VK_S){
            downPreased = true;
        }
        if(code == KeyEvent.VK_A){
            leftPreased = true;
        }
        if(code == KeyEvent.VK_D){
            rightPreased = true;
        }
        if(code == KeyEvent.VK_P){
            	gp.gameState = gp.pauseState;
        }
        if(code  == KeyEvent.VK_C) {
        	gp.gameState = gp.charaterState;
        }
        if(code == KeyEvent.VK_ENTER){
        	enterPreased = true;;
        }
        if(code == KeyEvent.VK_F) {
        	shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
        	gp.gameState= gp.optionsState;
        }
        if(code == KeyEvent.VK_M) {
        	gp.gameState= gp.mapState;
        }
        if(code == KeyEvent.VK_X) {
        	if(gp.map.miniMapOn == false) {
        		gp.map.miniMapOn = true;
        	}else {
        		gp.map.miniMapOn = false;
        	}
        }
        
        if(code == KeyEvent.VK_SPACE) {
        	spacePreased = true;
        }
        
        
        //DEBUG
        if(code == KeyEvent.VK_T) {
        	if(showDebugText == false) {
        		showDebugText = true;
        	}else if(showDebugText == true) {
        		showDebugText = false;
        	}
        }
        if(code == KeyEvent.VK_R) {
        	switch(gp.currentMap) {
        	case 0:
        		gp.TileM.loadMap(Disk.name+ "java-project-group-8\\My2dGame\\res\\maps\\worldV3.txt",0);
        		break;
        	case 1:
        		gp.TileM.loadMap(Disk.name +"java-project-group-8D\\My2dGame\\res\\maps\\interior01.txt",0);
            	break;
        	}
        	
        }
        if(code == KeyEvent.VK_G) {
        	if(godModeOn == false) {
        		godModeOn = true;
        	}else if(godModeOn == true) {
        		godModeOn = false;
        	}
        }
        
    }
    public void pauseState(int code) {

    	 if(code == KeyEvent.VK_P){
          	gp.gameState = gp.playState;
     	 }	
     	 
    }
    public void dialogueState(int code) {

    	if(code == KeyEvent.VK_ENTER) {
    		enterPreased = true;
    	}
    }
    public void charaterState(int code) {

    	if(code == KeyEvent.VK_C) {
    		gp.gameState = gp.playState;
    	}
    	
    	if(code == KeyEvent.VK_ENTER) {
    		gp.player.selectItem();
    	}
    	playerInventory(code);
    }
    public void playerInventory(int code) {
    	if(code == KeyEvent.VK_W) {
    		if(gp.ui.playerSlotRow!=0) {
    			gp.ui.playerSlotRow--;
        		gp.playSE(9);
    		}
    		
    	}
    	if(code == KeyEvent.VK_A) {
    		if(gp.ui.playerSlotCol!=0) {
    			gp.ui.playerSlotCol--;
        		gp.playSE(9);
    		}
    		
    	}
    	if(code == KeyEvent.VK_S) {
    		if(gp.ui.playerSlotRow!=3) {
    			gp.ui.playerSlotRow++;
        		gp.playSE(9);
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(gp.ui.playerSlotCol!=4) {
    			gp.ui.playerSlotCol++;
        		gp.playSE(9);
    		}
    	}
    }
    public void npcInventory(int code) {
    	if(code == KeyEvent.VK_W) {
    		if(gp.ui.npcSlotRow!=0) {
    			gp.ui.npcSlotRow--;
        		gp.playSE(9);
    		}
    		
    	}
    	if(code == KeyEvent.VK_A) {
    		if(gp.ui.npcSlotCol!=0) {
    			gp.ui.npcSlotCol--;
        		gp.playSE(9);
    		}
    		
    	}
    	if(code == KeyEvent.VK_S) {
    		if(gp.ui.npcSlotRow!=3) {
    			gp.ui.npcSlotRow++;
        		gp.playSE(9);
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(gp.ui.npcSlotCol!=4) {
    			gp.ui.npcSlotCol++;
        		gp.playSE(9);
    		}
    	}
    
    }
    public void optionsState(int code) {

    
    	if(code == KeyEvent.VK_ESCAPE) {
    		gp.gameState = gp.playState;
    	}
    	if(code == KeyEvent.VK_ENTER) {
    		enterPreased = true;
    	}
    	int maxCommandNum = 0;
    	switch(gp.ui.subState) {
    	case 0: maxCommandNum = 5;break;
    	case 3: maxCommandNum = 1;break;
    	}
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		gp.playSE(9);
    		if(gp.ui.commandNum < 0) {
    			gp.ui.commandNum = maxCommandNum;
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum++;
    		gp.playSE(9);
    		if(gp.ui.commandNum > maxCommandNum) {
    			gp.ui.commandNum = 0;
    		}
    	}
    	if(code == KeyEvent.VK_A) {
    		if(gp.ui.subState == 0) {
    			if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
    				gp.music.volumeScale--;
    				gp.music.checkVolume();
    				gp.playSE(9);
    				
    			}
    			if(gp.ui.commandNum == 2 && gp.SE.volumeScale > 0) {
    				gp.SE.volumeScale--;
    			
    				gp.playSE(9);
    				
    			}
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(gp.ui.subState == 0) {
    			if(gp.ui.commandNum == 1 && gp.music.volumeScale <5) {
    				gp.music.volumeScale++;
    				gp.music.checkVolume();
    				gp.playSE(9);
    				
    			}
    			if(gp.ui.commandNum == 2 && gp.SE.volumeScale <5) {
    				gp.SE.volumeScale++;
    				gp.playSE(9);
    				
    			}
    		}
    	}
    }
    public void gameoverState(int code) {
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		if(gp.ui.commandNum < 0) {
    			gp.ui.commandNum=1;
    		}
    		gp.playSE(9);
    	}
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum++;
    		if(gp.ui.commandNum > 1) {
    			gp.ui.commandNum = 0;
    		}
    		gp.playSE(9);
    	}
    	
    	if(code == KeyEvent.VK_ENTER) {
    		if(gp.ui.commandNum == 0) {
    			gp.gameState = gp.playState;
    			gp.resetGame(false);
    			gp.playMusic(0);
    			
    		}
    		else if(gp.ui.commandNum == 1) {
    			gp.gameState = gp.titleState;
    			gp.ui.titleScreenState=0;
    			gp.resetGame(true);
    		}
    	}
    }
    public void tradeState(int code) {

    	if(code == KeyEvent.VK_ENTER) {
    		enterPreased = true;
    	}
    	if(gp.ui.subState == 0) {
    		if(code == KeyEvent.VK_W) {
    			gp.ui.commandNum --;
    			if(gp.ui.commandNum < 0) {
    				gp.ui.commandNum = 2;
    			}
    			gp.playSE(9);
    			
    		}
    		if(code == KeyEvent.VK_S) {
    			gp.ui.commandNum++;
    			if(gp.ui.commandNum > 2) {
    				gp.ui.commandNum = 0;
    			}
    			gp.playSE(9);
    			
    		}
    	}
    	if(gp.ui.subState == 1) {
    		npcInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			gp.ui.subState=0;
    		}
    	}
    	if(gp.ui.subState == 2) {
    		playerInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			gp.ui.subState=0;
    		}
    	}
    }
    public void mapState(int code) {
    	if(code == KeyEvent.VK_M) {
    		gp.gameState = gp.playState;
    	}
    }
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPreased = false;
        }
        if(code == KeyEvent.VK_S){
            downPreased = false;
        }
        if(code == KeyEvent.VK_A){
            leftPreased = false;
        }
        if(code == KeyEvent.VK_D){
            rightPreased = false;
        }
        if(code == KeyEvent.VK_F) {
        	shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_ENTER) {
        	enterPreased = false;
        }
        if(code == KeyEvent.VK_SPACE) {
        	spacePreased = false;
        }
    }
    
}
