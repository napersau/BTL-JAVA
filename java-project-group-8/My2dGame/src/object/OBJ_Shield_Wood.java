package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{
	public static final String objName = "Wood Shield";

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		
		name = objName;
		type = type_shield;
		down1 = setup("object\\shield_wood.png", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "["+name+"]\nAn old shield.";
		price = 75;
	}

}
