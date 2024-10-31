package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sql.DataSource;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
		
	}
	
	
	public void save() {
		
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			DataStorage ds = new DataStorage();
			
			//PLAYER STATS
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.maxMana = gp.player.maxMana;
			ds.mana = gp.player.mana;
			ds.strength = gp.player.strength;
			ds.dexterity = gp.player.dexterity;
			ds.coin = gp.player.coin;
			ds.nextLevelExp = gp.player.nextLevelExp;
			ds.exp = gp.player.exp;
			
			//PLAYER INVENTORY
			for(int i=0;i<gp.player.inventory.size();i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmount.add(gp.player.inventory.get(i).amount);
				
			}
			
			//PLAYER EQUIPMENT
			ds.currentShieldSlot = gp.player.getCurrenShieldSlot();
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			
			
			//OBJECT ON MAP
			ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			for(int mapNum = 0;mapNum <gp.maxMap;mapNum++) {
				
				for(int i=0;i<gp.obj[1].length;i++) {
					
					if(gp.obj[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NA";
						
					}else {
						ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].WorldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].WorldY;
						
						if(gp.obj[mapNum][i].loot != null) {
							ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
							
						}
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
					}
				}
			}
			
			//WRItE DATASTORAGE OBJECT
			oos.writeObject(ds);;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void load() {
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			//READ THE DATASTORAGE OBJECT
			DataStorage ds = (DataStorage)ois.readObject();
			
			
			//PLAYER STATS
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.maxMana = ds.maxMana;
			gp.player.mana = ds.mana;
			gp.player.strength = ds.strength;
			gp.player.dexterity = ds.dexterity;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.coin = ds.coin;
			
			//LOAD PLAYER INVENTORY
			gp.player.inventory.clear();
			for(int i=0;i<ds.itemNames.size();i++) {
				gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
				
				gp.player.inventory.get(i).amount  = ds.itemAmount.get(i);
				
			}
			
			//PLAYER EQUIPMENT
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
			gp.player.getAttack();
			gp.player.getDefense();
			gp.player.getAtackImage();
			
			
			//OBJECT ON MAP
			for(int mapNum = 0;mapNum < gp.maxMap;mapNum++) {
				
				for(int i = 0;i<gp.obj[1].length;i++) {
					
					if(ds.mapObjectNames[mapNum][i].equals("NA")) {
						gp.obj[mapNum][i] = null;
								
					}
					else {
						
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
						gp.obj[mapNum][i].WorldX = ds.mapObjectWorldX[mapNum][i];
						gp.obj[mapNum][i].WorldY = ds.mapObjectWorldY[mapNum][i];
						if(ds.mapObjectLootNames[mapNum][i] != null) {
							gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
						}
						gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
						if(gp.obj[mapNum][i].opened == true) {
							gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
						}
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Load Exception!");
		}
		
	}
}
