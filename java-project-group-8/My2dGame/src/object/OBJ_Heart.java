package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	GamePanel gp;
	public static final String objName = "Heart";
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = objName;
		this.gp = gp;
		type = type_pickupOnly;
		value = 2;
		down1 = setup("object\\heart_full.png",gp.tileSize,gp.tileSize);
		image = setup("object\\heart_full.png",gp.tileSize,gp.tileSize);
		image2 = setup("object\\heart_half.png",gp.tileSize,gp.tileSize);
		image3 = setup("object\\heart_blank.png",gp.tileSize,gp.tileSize);
		
	}
	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Life +"+value);
		entity.life += value;
		
		return true;
	}
}
