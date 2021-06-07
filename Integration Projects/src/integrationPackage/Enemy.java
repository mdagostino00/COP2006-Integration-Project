package integrationPackage;
// Michael D'Agostino

// This is going to be where enemy behavior and stats are generated.
// Enemy stats and actions in battle will be randomly generated.

import java.util.*;

public class Enemy {
	private String name;
	private String weapon;
	private String type = "Enemy";
	private byte statPoints;
	private byte level;
	private double exp;
	private byte strength;
	private byte perception;
	private byte endurance;
	private byte charisma;
	private byte intelligence;
	private byte agility;
	private byte luck;
	private int money;
	final byte LEVEL_CAP = 127;

	public Enemy generateEnemy(byte location) { // location is capped at 10
		Random rnd = new Random();
		Enemy generatedEnemy = new Enemy();
		generatedEnemy.setName(generateEnemyName(location, rnd));
		generatedEnemy.setLevel((byte) (location + rnd.nextInt(2 * location)));
		// this level should never go above 30: 10 + 20 = 30

		return generatedEnemy;
	}

	public String generateEnemyName(byte location, Random rnd) {
		String[] nameList = new String[4];
		String name1;
		String name2;
		String name3;
		String name4;
		switch (location) {
		case 1:
			name1 = "Slime";
			name2 = "Cursed Cornstalk";
			name3 = "Buzzy Bee";
			name4 = "Feral Mutt";
			break;
		case 2:
			name1 = "Slime";
			name2 = "Cain Toad";
			name3 = "Vociferous Viper";
			name4 = "Crocodire";
			break;
		case 3:
			name1 = "Slime";
			name2 = "Wild Roots";
			name3 = "Pecking Duck";
			name4 = "Breaking Bat";
			break;
		case 4:
			name1 = "Slime";
			name2 = "Billy Goat";
			name3 = "Mountain Ape";
			name4 = "Laughing Lion";
			break;
		case 5:
			name1 = "Cryptic Slime";
			name2 = "Walking Dead";
			name3 = "Spider Monkey";
			name4 = "????";
			break;
		case 6:
			name1 = "Slime Knight";
			name2 = "Dancing Devil";
			name3 = "Living Armor";
			name4 = "Rock Solid";
			break;
		case 7:
			name1 = "Liquid Slime";
			name2 = "Gilded Goose";
			name3 = "Dragon Hatchling";
			name4 = "Lonely Giant";
			break;
		case 8:
			name1 = "Canadian Slime";
			name2 = "Dire Wolf";
			name3 = "Pal-less Citizen";
			name4 = "Buddy";
			break;
		case 9:
			name1 = "Flaming Slime";
			name2 = "Earth Dragon";
			name3 = "Lava Golem";
			name4 = "Spicy Salamander";
			break;
		case 10:
			name1 = "Metal Slime";
			name2 = "Twice-Undead";
			name3 = "Dragon Remains";
			name4 = "Roaming Titan";
			break;
		default:
			name1 = "Slime";
			name2 = "Slime";
			name3 = "Slime";
			name4 = "Slime";
			break;
		}
		nameList[0] = name1;
		nameList[1] = name2;
		nameList[2] = name3;
		nameList[3] = name4;
		int randomIndex = rnd.nextInt(nameList.length);
		return nameList[randomIndex];
	}

	public void setName(String nam) {
		name = nam;
	}

	public String getName() {
		return name;
	}

	public void setWeapon(String weap) {
		weapon = weap;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setType(String typ) {
		type = typ;
	}

	public String getType() {
		return type;
	}

	public void setExp(double e) {
		exp = e;
	}

	public double getExp() {
		return exp;
	}

	public void setStrength(byte str) {
		strength = str;
	}

	public byte getStrength() {
		return strength;
	}

	public void setPerception(byte per) {
		perception = per;
	}

	public byte getPerception() {
		return perception;
	}

	public void setEndurance(byte end) {
		endurance = end;
	}

	public byte getEndurance() {
		return endurance;
	}

	public void setCharisma(byte cha) {
		charisma = cha;
	}

	public byte getCharisma() {
		return charisma;
	}

	public void setIntelligence(byte intel) {
		intelligence = intel;
	}

	public byte getIntelligence() {
		return intelligence;
	}

	public void setAgility(byte agi) {
		agility = agi;
	}

	public byte getAgility() {
		return agility;
	}

	public void setLuck(byte lck) {
		luck = lck;
	}

	public byte getLuck() {
		return luck;
	}

	public void setMoney(int cash) {
		money = cash;
	}

	public int getMoney() {
		return money; // Foie gras bust of Albert Einstein
	}

	public byte getStatPoints() {
		return statPoints;
	}

	public void setLevel(byte lvl) {
		if (lvl < LEVEL_CAP && lvl > 0) {
			level = lvl;
		} else if (lvl < 0) {
			level = 1;
		} else {
			level = 127;
		}
	}

	public byte getLevel() {
		return level;
	}

	public byte getStat(byte i) {
		byte stat;
		switch (i) {
		case 0:
			stat = getStrength();
			break;
		case 1:
			stat = getPerception();
			break;
		case 2:
			stat = getEndurance();
			break;
		case 3:
			stat = getCharisma();
			break;
		case 4:
			stat = getIntelligence();
			break;
		case 5:
			stat = getAgility();
			break;
		case 6:
			stat = getLuck();
			break;
		case 7:
			stat = getStatPoints();
			break;
		default:
			stat = getLevel();
			break;
		}
		return stat;
	}

	public String getStat(String s) {
		String info;
		switch (s) {
		case "name":
			info = getName();
			break;
		case "weapon":
			info = getWeapon();
			break;
		case "type":
			info = getType();
			break;
		default:
			info = "Unknown stat called in Enemy getStat!";
			break;
		}
		return info;
	}
}
