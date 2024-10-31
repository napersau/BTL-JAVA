package main;
import entity.Entity;
public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.WorldX + entity.solidArea.x;
		int entityRightWorldX = entity.WorldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.WorldY + entity.solidArea.y;
		int entityBottomWorldY = entity.WorldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1 , tileNum2;
	
		
		//USE A TEMPORAL DIRECTION WHEN ITS BEING KNOCKBACKED
		String direction = entity.direction;
		if(entity.knockBack == true) {
			
			direction = entity.knockBackDirection;
			
		}
		switch (direction) {
		case "up": {
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];

			if(gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			
			break;
		}
		case "down": {
			
			entityTopRow = (entityTopWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			if(gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			
			break;
		}case "left": {
			
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

			if(gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			
			break;
		}case "right": {
			
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			if(gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			
			break;
		}}
		
	}
	
	public int checkObject(Entity entity,boolean player) {
		
		int index = 999;
		
		//USE A TEMPORAL DIRECTION WHEN ITS BEING KNOCKBACKED
		String direction = entity.direction;
		if(entity.knockBack == true) {
			
			direction = entity.knockBackDirection;
			
		}
		for(int i=0;i<gp.obj[1].length;i++) {
			
			if(gp.obj[gp.currentMap][i] != null) {
				// get entity's solid area position
				entity.solidArea.x = entity.WorldX + entity.solidArea.x;
				entity.solidArea.y = entity.WorldY + entity.solidArea.y;
				
				// get object's solid area position
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].WorldX +  gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].WorldY +  gp.obj[gp.currentMap][i].solidArea.y;				
				
				switch(direction) {
				case "up":{
					entity.solidArea.y -= entity.speed;
					//check 2 entity and Object : GIAO NHAU (collision or not)
				
					break;
				}
				case "down":{
					entity.solidArea.y +=entity.speed;
					break;
				}
				case "right":{
					entity.solidArea.x +=entity.speed;
					break;
				}
				case "left":{
					entity.solidArea.x -=entity.speed;
					break;
					}
				}
				
				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)==true) {
					if(gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
					
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
			
		}
		
		return index; // return index of Object player hit
	}
	
	//NPC or MONSTER
	public int checkEntity(Entity entity,Entity[][] target) {
	int index = 999;
	
	//USE A TEMPORAL DIRECTION WHEN ITS BEING KNOCKBACKED
	String direction = entity.direction;
	if(entity.knockBack == true) {
		
		direction = entity.knockBackDirection;
		
	}
	for(int i=0;i<target[1].length;i++) {
			
			if(target[gp.currentMap][i] != null) {
				// get entity's solid area position
				entity.solidArea.x = entity.WorldX + entity.solidArea.x;
				entity.solidArea.y = entity.WorldY + entity.solidArea.y;
				
				// get object's solid area position
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].WorldX +  target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].WorldY +  target[gp.currentMap][i].solidArea.y;				
				switch(direction) {
				case "up":{
					entity.solidArea.y -= entity.speed;
					break;
				}
				case "down":{
					entity.solidArea.y +=entity.speed;
					break;
				}
				case "right":{
					entity.solidArea.x +=entity.speed;
					break;
				}
				case "left":{
					entity.solidArea.x -=entity.speed;
					break;
					}
				}
				//check 2 entity and Object : GIAO NHAU (collision or not)
				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)==true) {
					if(target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
					
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
			
		}
		
		return index; // 
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		
		entity.solidArea.x = entity.WorldX + entity.solidArea.x;
		entity.solidArea.y = entity.WorldY + entity.solidArea.y;
//		System.out.println(entity.solidArea.x +" "+entity.solidArea.y+" "+gp.player.solidArea.x+" "+gp.player.solidArea.y );
		
		// get object's solid area position
		gp.player.solidArea.x = gp.player.WorldX +  gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.WorldY +  gp.player.solidArea.y;				
		
	
		
		switch(entity.direction) {
		case "up":{
			entity.solidArea.y -= entity.speed;
			break;
		}
		case "down":{
			entity.solidArea.y +=entity.speed;
			break;
		}
		case "right":{
			entity.solidArea.x +=entity.speed;
			break;
		}
		case "left":{
			entity.solidArea.x -=entity.speed;
			break;
			}
		}
		//check 2 entity and Object : GIAO NHAU (collision or not)
		if(entity.solidArea.intersects(gp.player.solidArea)==true) {
			entity.collisionOn = true;	
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
}
