package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{
	GamePanel gp;
	public static final String objName = "Rock";

	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name =objName;
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	
	public void getImage() {
		down1 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		down2 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		up1 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		up2 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		right1 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		right2 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		left1 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
		left2 = setup("projectile\\rock_down_1.png", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		if(user.amor >= useCost) {
			return true;
		}
		return false;
	}
	public void subtractResource(Entity user) {
		user.amor -= useCost;
	}
	public Color getParticleColor() {
		Color color = new Color(40,50,0);
		return color;
	}
	public int getParticleSize() {
		int size = 10; // 6 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
