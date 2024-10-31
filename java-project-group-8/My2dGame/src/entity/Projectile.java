package entity;

import main.GamePanel;

public class Projectile extends Entity{
	Entity user;
	public Projectile(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
	}
	public void set(int worldX,int worldY,String direction,boolean alive,Entity user) {
		this.WorldX = worldX;
		this.WorldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
	
	}
	public void update() {
		
		if(user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2),knockBackPower);
				generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
				alive = false;
			
			}
		}
		if(user != gp.player) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				generateParticle(user.projectile, user.projectile);
				alive = false;
			}
		}
		switch(direction) {
		case "up": WorldY -= speed;break;
		case "down": WorldY += speed;break;
		case "right": WorldX += speed;break;
		case "left": WorldX -= speed;break;
		}
		
		life--;
		if(life <=0) {
			alive = false;
		}
		
		spriteCount++;
		if(spriteCount > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum==2) {
				spriteNum = 1;
			}
			spriteCount = 0;
		}
		
	}
	public boolean haveResource(Entity user) {
		return false;
	}
	public void subtractResource(Entity user) {
	}
}
