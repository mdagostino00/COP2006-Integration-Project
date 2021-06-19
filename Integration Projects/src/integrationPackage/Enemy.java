package integrationPackage;
// Michael D'Agostino

// This is going to be where enemy behavior and stats are generated.
// Enemy stats and actions in battle will be randomly generated.

import java.util.*;

public class Enemy extends Character {

	public Enemy(char typ, byte lvl) {
		super(typ, lvl);
		setExp((double) (getLevel() * 1.21 * getLuck()));
		setHP(50 + 51 * lvl);
	}

	public void initializeCharStats(Random rand) {
		setRandomStats(rollRandomStats(rand));
	}

	public void setRandomStats(byte[] stats) {
		for (byte i = 0; i < 7; i++) {
			setStat(i, stats[i]);
		}
	}

	public byte[] rollRandomStats(Random rand) {
		byte[] stats = new byte[7];
		rand.nextBytes(stats);
		for (byte i = 0; i < 7; i++) {
			stats[i] = (byte) ((Math.abs(stats[i]) % 3) + getLevel());
		}
		return stats;
	}

	public String generateEnemyName(byte location, Random rnd) {
		ArrayList<String> nameList = new ArrayList<String>();
		switch (location) {
		case 1:
			nameList.add("Slime");
			nameList.add("Cursed Cornstalk");
			nameList.add("Buzzy Bee");
			nameList.add("Feral Mutt");
			break;
		case 2:
			nameList.add("Slime");
			nameList.add("Cain Toad");
			nameList.add("Vociferous Viper");
			nameList.add("Crocodire");
			break;
		case 3:
			nameList.add("Slime");
			nameList.add("Wild Roots");
			nameList.add("Pecking Duck");
			nameList.add("Breaking Bat");
			break;
		case 4:
			nameList.add("Slime");
			nameList.add("Billy Goat");
			nameList.add("Mountain Ape");
			nameList.add("Laughing Lion");
			break;
		case 5:
			nameList.add("Cryptic Slime");
			nameList.add("Walking Dead");
			nameList.add("Spider Monkey");
			nameList.add("????");
			break;
		case 6:
			nameList.add("Slime Knight");
			nameList.add("Dancing Devil");
			nameList.add("Living Armor");
			nameList.add("Rock Solid");
			break;
		case 7:
			nameList.add("Liquid Slime");
			nameList.add("Gilded Goose");
			nameList.add("Dragon Hatchling");
			nameList.add("Lonely Giant");
			break;
		case 8:
			nameList.add("Canadian Slime");
			nameList.add("Dire Wolf");
			nameList.add("Pal-less Citizen");
			nameList.add("Buddy");
			break;
		case 9:
			nameList.add("Flaming Slime");
			nameList.add("Earth Dragon");
			nameList.add("Lava Golem");
			nameList.add("Spicy Salamander");
			break;
		case 10:
			nameList.add("Metal Slime");
			nameList.add("Twice-Undead");
			nameList.add("Dragon Remains");
			nameList.add("Roaming Titan");
			break;
		default:
			nameList.add("Slime");
			break;
		}
		int randomIndex = rnd.nextInt(nameList.size());
		return nameList.get(randomIndex);
	}

}
