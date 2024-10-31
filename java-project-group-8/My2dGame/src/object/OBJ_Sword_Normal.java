package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
	public static final String objName = "Normal Sword";
	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		
		name = objName;
		type = type_sword;
		attackArea.width = 36;
		attackArea.height = 36;
		down1 = setup("object\\sword_normal.png", gp.tileSize, gp.tileSize);
		attackValue = 1;
		description = "["+name+"]\nAn old sword.";
		price = 75;
		knockBackPower = 2;
		motion1_duration = 5;
		motion2_duration = 25;
		
	}
	
}
