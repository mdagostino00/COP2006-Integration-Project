// Michael D'Agostino

// This is going to be where enemy behavior and stats are generated.
// Enemy stats and actions in battle will be randomly generated.

package integration.project;

import java.util.ArrayList;

/**
 * Allows for enemy generation, which is just a Character object, but generated based on the level
 * of the player.

 * @author Michael D'Agostino
 *
 */
public class Enemy extends Character {

  /**
 * Enemy constructor, given the level.

 * @param typ type
 * @param lvl level
 */
  public Enemy(char typ, byte lvl) {
    super(typ, lvl);
    setExp((double) (getLevel() * 1.21 * getLuck()));
    setHp(50 + 51 * lvl);
  }

  /**
 * Creates random stats for object.
 */
  public void initializeCharStats() {
    setRandomStats(rollRandomStats());
  }

  /**
 * sets stats from array into the object fields.

 * @param stats stat array of randomly generated stats
 */
  public void setRandomStats(byte[] stats) {
    for (byte i = 0; i < 7; i++) {
      setStat(i, stats[i]);
    }
  }

  /**
 * rolls a random stat array for the enemy's SPECIAL stats.

 * @return SPECIAL stat array
 */
  public byte[] rollRandomStats() {
    byte[] stats = new byte[7];
    Main.RAND.nextBytes(stats);
    for (byte i = 0; i < 7; i++) {
      stats[i] = (byte) ((Math.abs(stats[i]) % 3) + getLevel());
    }
    return stats;
  }

  /**
 * generates a random name from a list of names given the location data of the player.

 * @param location location byte
 * @return name as String
 */
  public String generateEnemyName(byte location) {
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
    int randomIndex = Main.RAND.nextInt(nameList.size());
    return nameList.get(randomIndex);
  }
}
