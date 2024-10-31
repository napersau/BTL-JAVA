package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{
	GamePanel gp;
	public static final String objName = "Bronze Coin";

	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		value = 1;
		type = type_pickupOnly;
		down1  = setup("object\\coin_bronze.png", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {
		gp.playSE(1);
		gp.ui.addMessage("Coin +"+value);
		gp.player.coin += value;
		
		return true; 
	}
}
