
package main;

import entity.Entity;
import entity.Player;
import enviromment.EnvironmentManager;
import tile.Map;
import tile.Tile;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;


public class GamePanel extends JPanel implements Runnable{
    // screen settings
    final int originalTileSize = 16; // constant 16x16 : size of chars / map / npcs
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale; //48x48
    // Screen Size
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int maxscreenWidth = tileSize * maxScreenCol; //960 pixels
    public final int maxscreenHeight = tileSize * maxScreenRow;//576 p
    
    //FOR FULL SCREEN
    int screenWidth2 = maxscreenWidth;
    int screenHeight2 = maxscreenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    //WorldSetiting
    
    public  int maxWorldCol = 50;
    public  int maxWorldRow = 50;
    public  int maxMap = 10;
    public int currentMap = 0;
    
    public KeyHandler keyH = new KeyHandler(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    
    
    //AREA -- to fix the enviromment effect
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 50;
    
    //FPS
    int FPS = 60;
    
    //Entity and Objects and UI
    public TileManager TileM = new TileManager(this);
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc [][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    ArrayList<Entity> entityList = new ArrayList<Entity>();
    public Entity projectile[][] = new Entity [maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<Entity>();
    
    public AssetSetter aSetter = new AssetSetter(this);
    public EvenHandler eHandler = new EvenHandler(this);
    Config config = new Config(this);
    Thread gameThread; // call to run method
    public UI ui = new UI(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager  = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    
    //Sound
    Sound music = new Sound();
    Sound SE = new Sound();
    
    
    //GameState
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;
    public final int charaterState =  4;
    public final int optionsState =  5;
    public final int gameoverState =  6;
    public final int transitionState =  7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutsceneState = 11;
    
    
    //OTHER
    public boolean bossBattleOn = false;
    
    
    public GamePanel(){
        // set size of this class
        this.setPreferredSize(new Dimension(maxscreenWidth,maxscreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //improve game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void resetGame(boolean restart) {
    	stopMusic();
    	currentArea = outside;
    	removeTempEntity();
    	bossBattleOn = false;
    	
    	player.setDefaultPositions();
    	
    	player.restoreStatus();
    	player.resetCounter();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	if(restart == true) {
    		player.setDefaultValues();
        	aSetter.setObject();
        	aSetter.setInteractiveTile();
        	eManager.lighting.resetDay();
    	}
    	
    }
    public void  setupGame() {



    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	aSetter.setInteractiveTile();
    	eManager.setup();
    	//Theme Music
    	gameState = titleState;
    	currentArea = outside;
    	tempScreen = new BufferedImage(screenWidth2, screenHeight2,BufferedImage.TYPE_INT_ARGB);
    	g2  = (Graphics2D)tempScreen.getGraphics();
    	
    	if(fullScreenOn == true)
    		setFullScreen();
    	
    	
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        //Game loop : core of game 
        // using "Delta menthod"
        double drawInterval  = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        //display FPS
        long timer = 0;
        long drawCount = 0;
        
        while(gameThread != null){
            //System.out.println("the game loop is running");
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime); 
            lastTime = currentTime;
            
            if(delta >=1){
                //1 UPDATE : update infor
                update();
                //2 DRAW : draw the screen with the updated infor
//                repaint();//from paintComponent
                drawToTempScreen(); //draw everything to bufferedImage
                
                drawToScreen(); // draw BufferedImage to screen
                
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000){
                //if timer hit 1 second
                //System.out.println("FPS"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){



       if(gameState == playState) {
    	   //PLAYER
    	   player.update();
    	   
    	 
    	   //NPC
    	   for(int i=0;i<npc[1].length;i++) {
    		   if(npc[currentMap][i]!=null) {
    			   npc[currentMap][i].update();
    		   }
    	   }
    	   
    	   //MONSTER
    	   for(int i=0;i<monster[1].length;i++) {
    		   if(monster[currentMap][i]!=null) {
//    			   System.out.println("MONSTER");
    			   if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
    				   monster[currentMap][i].update();
    			   }
    			   if(monster[currentMap][i].alive == false) {
    				   monster[currentMap][i].checkDrop();
    				   monster[currentMap][i] = null;
    			   }
    		   }
    	   }
    	   //PROJECTILE
    	   for(int i=0;i<projectile[1].length;i++) {
    		   if(projectile[currentMap][i]!=null) {
    			   if(projectile[currentMap][i].alive == true ) {
    				   projectile[currentMap][i].update();
    			   }
    			   if(projectile[currentMap][i].alive == false) {
    				   projectile[currentMap][i] = null;
    			   }
    		   }
    	   }
    	   
    	   for(int i=0;i<particleList.size();i++) {
    		   if(particleList.get(i)!=null) {
    			   if(particleList.get(i).alive == true ) {
    				   particleList.get(i).update();
    			   }
    			   if(particleList.get(i).alive == false) {
    				   particleList.remove(i);
    			   }
    		   }
    	   }
    	   
    	   //Interactive Tile
    	   for(int i=0;i<iTile[1].length;i++) {
    		   if(iTile[currentMap][i] != null) {
    			   iTile[currentMap][i].update();
    		   }
    	   }
    	   eManager.update();
       }
       else if(gameState == pauseState) {
    	   //nothing
    	   
       }
    }
    public void drawToTempScreen() {


        long drawStart = 0;
        if(keyH.showDebugText == true) {
        	drawStart = System.nanoTime();
        }
        //TITLE SCREEN
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        
        //MAP SCREEN
        else if(gameState == mapState) {
        	map.drawFullMapScreen(g2);
        }
        //OTHER
        else {
        	
        	//Draw Tiles
           TileM.draw(g2);
           	
           //DRAW INTERACTIVE TILE
           for(int i=0;i<iTile[1].length;i++) {
        	   if(iTile[currentMap][i] != null) {
        		   iTile[currentMap][i].draw(g2);
        	   }
           }
           //ADD ENTITY TO THE LIST
            entityList.add(player);
            
            for(int i=0;i<npc[1].length;i++) {
            	if(npc[currentMap][i] != null) {
            		entityList.add(npc[currentMap][i]);
            	}
            }
            
            for(int i=0;i<obj[1].length;i++) {
            	if(obj[currentMap][i]!=null) {
            		entityList.add(obj[currentMap][i]);
            	}
            }
            
            for(int i=0;i<monster[1].length;i++) {
     		   if(monster[currentMap][i]!=null) {
     			   entityList.add(monster[currentMap][i]);
     		   }
     	   	}
            for(int i=0;i<projectile[1].length;i++) {
      		   if(projectile[currentMap][i]!=null) {
      			   entityList.add(projectile[currentMap][i]);
      			   
      		   }
      	   	}
            for(int i=0;i<particleList.size();i++) {
       		   if(particleList.get(i)!=null) {
       			   entityList.add(particleList.get(i));
       			   
       		   }
       	   	}
            //SORT
            Collections.sort(entityList,new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.WorldY, e2.WorldY);
					return result;
		
				}
            	
			});
            
            //Draw ENTITIES
            for(int i=0;i<entityList.size();i++) {
            	entityList.get(i).draw(g2);
            }
            
            //EMPTY ENTITY LIST	
           entityList.clear();
            
           //ENVIRONMENT
           eManager.draw(g2);
            
           //MINI MAP
           map.drawMiniMap(g2);
           
           
           //CUT SCENE
           csManager.draw(g2);
           
           
            //UI
            ui.draw(g2);
            
            //DEBUG SHOW PLAYER POSITION
            if(keyH.showDebugText == true) {
            	long drawEnd = System.nanoTime();
            	long passed = drawEnd - drawStart;
            	
            	g2.setFont(new Font("Arial",Font.PLAIN,20));
            	g2.setColor(Color.white);
            	int x = 10;
            	int y = 400;
            	int lineHeight = 20;
            	g2.drawString("WorldX : "+player.WorldX, x, y);
            	y+=lineHeight;
            	g2.drawString("WorldY : "+player.WorldY, x, y);
            	y+=lineHeight;
            	g2.drawString("Col : "+(player.WorldX+player.solidArea.x)/tileSize, x, y);
            	y+=lineHeight;
            	g2.drawString("Row : "+(player.WorldY+player.solidArea.y)/tileSize, x, y);
            	y+=lineHeight;
            	g2.drawString("Draw time : "+passed, x, y);
            	y+=lineHeight;
            	g2.drawString("GodMode : "+keyH.godModeOn, x, y);
            }
        }
    }

    public void drawToScreen() {

    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2,screenHeight2,null);
    	g.dispose();
    }
    public void setFullScreen() {
    	//GET LOCAL SCREEN DEVICE
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	
    	gd.setFullScreenWindow(Main.window);
    	
    	//GET FULL SCREEN WIDTH AND HEIGHT
    	screenWidth2 = Main.window.getWidth();
    	screenHeight2 = Main.window.getHeight();
    }
    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    	
    }
    public void stopMusic() {
    	music.stop();
    }
    
    public void playSE(int i) {
    	
    	SE.setFile(i);
    	SE.play();
    	
    }
    public void changeArea() {
    	
    	
    	if(nextArea != currentArea) {
    		stopMusic();
    		if(nextArea == outside) {
    			playMusic(0);
    		}
    		if(nextArea == indoor) {
    			playMusic(18);
    		}
    		if(nextArea == dungeon) {
    			playMusic(19);
    		}
    	}
    	currentArea = nextArea;
    	aSetter.setMonster();
    }
    public void removeTempEntity() {
    	
    	for(int mapNum = 0;mapNum < maxMap;mapNum ++) {
    		
    		for(int i=0;i<obj[1].length;i++) {
    			
    			if(obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
    				obj[mapNum][i] = null;
    			}
    		}
    	}
    }
}
