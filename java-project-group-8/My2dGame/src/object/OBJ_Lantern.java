package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity{
	public static final String objName = "Lantern";

	public OBJ_Lantern(GamePanel gp) {
		super(gp);
		
		type = type_light;
		name = objName;
		down1 = setup("object\\lantern.png", gp.tileSize, gp.tileSize);
		description = "Can Make Light";
		price = 200;
		lightRadius = 350;
	}
	
}
