package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_FireBall extends Projectile {
	GamePanel gp;
	public static final String objName = "Fireball";

	public OBJ_FireBall(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name =objName;
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 1;
		useCost = 1;
		alive = false;
		knockBackPower = 5;
		getImage();
	}
	
	public void getImage() {
		down1 = setup("projectile\\fireball_down_1.png", gp.tileSize, gp.tileSize);
		down2 = setup("projectile\\fireball_down_2.png", gp.tileSize, gp.tileSize);
		up1 = setup("projectile\\fireball_up_1.png", gp.tileSize, gp.tileSize);
		up2 = setup("projectile\\fireball_up_2.png", gp.tileSize, gp.tileSize);
		right1 = setup("projectile\\fireball_right_1.png", gp.tileSize, gp.tileSize);
		right2 = setup("projectile\\fireball_right_2.png", gp.tileSize, gp.tileSize);
		left1 = setup("projectile\\fireball_left_1.png", gp.tileSize, gp.tileSize);
		left2 = setup("projectile\\fireball_left_2.png", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		if(user.mana >= useCost) {
			return true;
		}
		return false;
	}
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
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
