package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity{
	GamePanel gp;
	public static final String objName = "Mana";

	public OBJ_Mana(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = objName;
		type = type_pickupOnly;
		value = 1;
		down1 = setup("object\\manacrystal_full.png",gp.tileSize,gp.tileSize);
		image = setup("object\\manacrystal_full.png",gp.tileSize,gp.tileSize);
		image2 =  setup("object\\manacrystal_blank.png",gp.tileSize,gp.tileSize);
		
		
	}
	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Mana +"+value);
		entity.mana += value;
		price = 75;
		return true;
	}
	
}
