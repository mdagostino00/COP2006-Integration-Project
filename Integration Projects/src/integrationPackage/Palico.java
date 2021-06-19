// Michael D'Agostino

// This is where Pals will be created/handled.
// Pals will act under an auto-battle system where actions are chosen at random.

package integrationPackage;

import java.util.*;

public class Palico extends Character {

	public Palico(char typ, byte lvl) {
		super(typ, lvl);
	}

	public void initializeCharStats(Scanner scan, Random rand) {
		setRandomStats(rollRandomStats(rand));
		setName(askCharName(scan, getType()));
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
			stats[i] = (byte) ((Math.abs(stats[i]) % 9) + 2);
		}
		return stats;
	}

}
