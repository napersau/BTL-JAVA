package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{
	public static final String objName = "Blue Shield";
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		name = objName;
		type = type_shield;
		down1 = setup("object\\shield_blue.png", gp.tileSize, gp.tileSize);
		defenseValue = 2;
		description = "["+name+"]\nAn Shiny Blue shield.";
		price = 75;
	}
}
