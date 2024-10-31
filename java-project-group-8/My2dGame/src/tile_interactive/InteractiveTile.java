package tile_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
	GamePanel gp;
	public boolean destructible = false;
	public InteractiveTile(GamePanel gp,int col,int row) {
		super(gp);
		this.gp = gp;
	}
	public void update() {
		if(invincible ==true) {
    		invincibleCounter++;
    		if(invincibleCounter > 20) {
    			invincible = false;
    			invincibleCounter = 0;
    		}
    	}
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		return isCorrectItem;
	}
	public void playSE() {
		
	}
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	public void draw(Graphics2D g2) {

    	int screenX = WorldX - gp.player.WorldX + gp.player.screenX;
		int screenY = WorldY - gp.player.WorldY + gp.player.screenY;
		
		BufferedImage image = null;
		
		if(WorldX + gp.tileSize > gp.player.WorldX - gp.player.screenX &&
			WorldX - gp.tileSize < gp.player.WorldX + gp.player.screenX &&
			WorldY + gp.tileSize > gp.player.WorldY - gp.player.screenY &&
			WorldY - gp.tileSize < gp.player.WorldY + gp.player.screenY ) {
			
			g2.drawImage(down1,screenX,screenY,null);
		
		}
		
	}
}
