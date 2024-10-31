package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity{
	GamePanel gp;
	public static final String objName = "Tent";

	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = objName;
		down1 = setup("object\\tent.png", gp.tileSize, gp.tileSize);
		description = "[Tent]\nYOu can sleep until\nnext morning.";
		price = 100;
		stackable = true;
	}
	public boolean use(Entity entity) {
		
		gp.gameState = gp.sleepState;
		
		gp.playSE(14);
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		
		gp.player.getSleepingImage();
		
		return true;
		
	}
}
