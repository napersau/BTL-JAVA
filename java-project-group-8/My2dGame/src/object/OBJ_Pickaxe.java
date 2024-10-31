package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity{
	public static final String objName = "Pickaxe";
	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);
		name = objName;
		type = type_pickaxe;
		down1 = setup("object\\pickaxe.png", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		motion1_duration = 20;
		motion2_duration = 40;
		description = "["+name+"]\n"
				+"You will dig it";
		price = 75;
		knockBackPower = 10;
	}
}
