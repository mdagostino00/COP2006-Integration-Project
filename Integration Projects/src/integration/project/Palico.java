// Michael D'Agostino

// This is where Pals will be created/handled.
// Pals will act under an auto-battle system where actions are chosen at random.

package integration.project;

import java.util.Scanner;

/**
 * Palico object, which act as randomly controlled party members.

 * @author Michael D'Agostino
 *
 */
public class Palico extends Character {

  /**
 * palico constructor, given the level.

 * @param typ type
 * @param lvl level
 */
  public Palico(char typ, byte lvl) {
    super(typ, lvl);
  }

  /**
 * creates random stats for pal.
 */
  public void initializeCharStats(Scanner scan) {
    setRandomStats(rollRandomStats());
    setName(askCharName(scan, getType()));
  }

  /**
 * sets stats from array into fields.

 * @param stats stat array
 */
  public void setRandomStats(byte[] stats) {
    for (byte i = 0; i < 7; i++) {
      setStat(i, stats[i]);
    }
  }

  /**
 * generates random SPECIAL stat array.

 * @return SPECIAL stat array.
 */
  public byte[] rollRandomStats() {
    byte[] stats = new byte[7];
    Main.RAND.nextBytes(stats);
    for (byte i = 0; i < 7; i++) {
      stats[i] = (byte) ((Math.abs(stats[i]) % 9) + 2);
    }
    return stats;
  }
}
