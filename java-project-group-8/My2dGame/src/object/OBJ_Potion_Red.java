package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	public static final String objName = "Red Potion";

	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp =gp;
		type = type_consumable;
		value = 5;
		name = objName;
		down1 = setup("object\\potion_red.png", gp.tileSize, gp.tileSize);
		description = "["+name+"]\n"
				+"Heal your life by "+value+".";
		price = 115;
		stackable = true;
		setDialogue();
	}
	public void setDialogue() {
		dialogues[0][0] = "Your drink the "+name+"!\n"+"Healing "+value+".";
	}
	public boolean use(Entity entity) {
		startDialogue(this, 0);
		entity.life+=value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
		return true;
	}

}
