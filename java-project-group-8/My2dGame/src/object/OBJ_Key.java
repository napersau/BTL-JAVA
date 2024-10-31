package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
	GamePanel gp;
	
	public static final String objName = "Key";
	public OBJ_Key(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type= type_consumable;
		name = objName;
		down1 = setup("object\\key.png",gp.tileSize,gp.tileSize);
		description = "["+name+"]\nIt opens a door.";
		price = 100;
		stackable = true;
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "you use "+name+" and open the door";
		dialogues[1][0] = "What heck u doing ?";
	}
	public boolean use(Entity entity) {
	
		int objIndex = getDetected(entity,gp.obj , "Door");
		
		if(objIndex != 999) {
			startDialogue(this, 0);
			gp.playSE(3);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			startDialogue(this, 1);
			return false;
		}
		
	}
}
