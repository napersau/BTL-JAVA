package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity{
	GamePanel gp;
	
	public static final String objName = "Blue Heart";
	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		
		this.gp = gp;
		type = type_pickupOnly;
		name = objName;
		down1 = setup("object\\blueheart.png", gp.tileSize, gp.tileSize);
		
		
	}
	public void setDialogues() {
		dialogues [0][0] = "You pick up a beautiful blue gem";
		dialogues[0][1] = "You find the blue heart the Legendary Treasure!";
		use(this);
	}
	public boolean use(Entity enity) {
		
		gp.gameState = gp.cutsceneState;
		gp.csManager.sceneNum = gp.csManager.ending;
		return true;
	}
}
