package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import main.Disk;

public class TileManager {
	GamePanel gp;
	public Tile [] tile;
	public int mapTileNum [][] [];
	boolean drawPath = true;
	ArrayList<String> fileNames = new ArrayList<String>();
	ArrayList<String>	collisionStatus = new ArrayList<String>();

	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		//READ TILE DATA FILE
		
		try {
			
			InputStream is = new FileInputStream(new File(Disk.name+"java-project-group-8\\My2dGame\\res\\maps\\tiledata.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//GETTING TILES NAME AND COLLISION INFOR FROM TILEDATA
			String line;
			while((line  = br.readLine()) != null) {
				fileNames.add(line);
				collisionStatus.add(br.readLine());
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		tile = new Tile[fileNames.size()]; //10 kinds of tiles
		getTileImage();
		
		//GET THE WORLDCOL _ ROW
		try{
			InputStream is = new FileInputStream(new File(Disk.name+"java-project-group-8\\My2dGame\\res\\maps\\worldmap (1).txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line = br.readLine();
			String mapTile[] = line.split(" ");
			gp.maxWorldCol = mapTile.length;
			gp.maxWorldRow = mapTile.length;
			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
			
			br.close();
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	
		
		
		loadMap( Disk.name+ "java-project-group-8\\My2dGame\\res\\maps\\worldmap (1).txt",0);
		loadMap(Disk.name+ "java-project-group-8\\My2dGame\\res\\maps\\indoor01.txt",1);
		loadMap(Disk.name+ "java-project-group-8\\My2dGame\\res\\maps\\dungeon01.txt",2);
		loadMap(Disk.name+ "java-project-group-8\\My2dGame\\res\\maps\\dungeon02.txt",3);

	}
	
	public void setup(int index,String imagePath,boolean collision) {

		UtilityTool uTool = new UtilityTool();
		try{
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(new File(Disk.name+ "java-project-group-8\\My2dGame\\res\\tiles\\"+imagePath));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getTileImage() {


		for(int i=0;i < fileNames.size();i++){
			
			String fileName;
			boolean collision;
			
			
			//getFileName
			fileName = fileNames.get(i);
			
			//GET COLLISION
			if(collisionStatus.get(i).equals("true")) {
				collision = true;
			}
			else {
				collision = false;
			}
			
			setup(i, fileName, collision);
		}
		

	}
	
	public void loadMap(String filePath,int map) {

		try {
			
			Scanner sc =  new Scanner(new File(filePath));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = sc.nextLine();
				
				while(col < gp.maxWorldCol) {
					
					String [] nums = line.split(" ");
					
					int num = Integer.parseInt(nums[col]);
					
					mapTileNum[map][col][row] = num;
					
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					
					col = 0;
					
					row+=1;
					
				}
			}
			
			sc.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol &&  worldRow < gp.maxWorldRow) {
			
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			//System.out.println(worldCol +" "+ worldRow+" ");
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			// transf worldPosition -> screenPosition : Player position in camera
			int screenX = worldX - gp.player.WorldX + gp.player.screenX;
			int screenY = worldY - gp.player.WorldY + gp.player.screenY;
		
				
			if(worldX + gp.tileSize > gp.player.WorldX-gp.player.screenX &&
				worldX - gp.tileSize < gp.player.WorldX +gp.player.screenX &&
				worldY + gp.tileSize > gp.player.WorldY-gp.player.screenY&&
				worldY - gp.tileSize < gp.player.WorldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image,screenX,screenY,null);
			}
			
			worldCol++;
			
			
			if(worldCol == gp.maxWorldCol) {
				
				worldCol  = 0;
				
				worldRow++;
			
			}
		}
//		if(drawPath == true) {
//			g2.setColor(new Color(255,0,0,70));
//			
//			for(int i=0;i<gp.pFinder.pathList.size();i++) {
//				
//				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
//				int worldY =  gp.pFinder.pathList.get(i).row * gp.tileSize;
//				
//				// transf worldPosition -> screenPosition : Player position in camera
//				int screenX = worldX - gp.player.WorldX + gp.player.screenX;
//				int screenY = worldY - gp.player.WorldY + gp.player.screenY;
//				
//				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//			}
//		}
	}
}
