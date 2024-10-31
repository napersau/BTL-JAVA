package data;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Entity;

public class DataStorage implements Serializable{
	
	
	//PLAYER STATE
	int level;
	int life;
	int maxLife;
	int maxMana;
	int strength;
	int dexterity;
	int exp;
	int nextLevelExp;
	int coin;
	int mana;
	
	//PLAYER IVENTORY
	ArrayList<String> itemNames = new ArrayList<String>();
	ArrayList<Integer> itemAmount = new ArrayList<Integer>();
	int currentWeaponSlot;
	int currentShieldSlot;
	
	//OBJECT ON MAP
	String mapObjectNames [][];
	int  mapObjectWorldX [][];
	int  mapObjectWorldY [][];
	String mapObjectLootNames [][];
	boolean mapObjectOpened [][];
	
}
