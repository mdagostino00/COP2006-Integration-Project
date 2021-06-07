// Michael D'Agostino

// This is where Pals will be created/handled.
// Pals will act under an auto-battle system where actions are chosen at random.

package integrationPackage;

import java.util.*;

public class Palico {
	private String name;
	private String weapon;
	private String type = "Pal";
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
	final byte LEVEL_CAP = 127; // final sets a hard limit to the level
								// cap, as it can't be changed at all.

}
