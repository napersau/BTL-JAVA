package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe  extends Entity{
	public static final String objName = "Axe";
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		name = objName;
		type = type_axe;
		down1 = setup("object\\axe.png", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		motion1_duration = 20;
		motion2_duration = 40;
		description = "["+name+"]\n"
				+"Rusty but can \ncut the woods";
		price = 75;
		knockBackPower = 10;
	}

}
