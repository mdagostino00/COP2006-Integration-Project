package integrationPackage;
// Michael D'Agostino

import java.util.*;

public class Character {
	String name;			 // a string is an object that represents
							 // a sequence of characters, and is called
							 // via a class.
	String weapon;
	final byte LEVEL_CAP = 30; // final sets a hard limit to the level
							 // cap, as it can't be changed at all.
	private byte level = 1;
	private byte statPoints = 30;
	private double exp = 0.00;	// enemy level will cause multipliers for exp, allowing
						// for decimal values.
	private byte strength = 1;
	private byte perception = 1;
	private byte endurance = 1;
	private byte charisma = 1;
	private byte intelligence = 1;
	private byte agility = 1;
	private byte luck = 1;
	private int money = 100;
	private boolean alive = true;
	
	
	public void initializeCharStats(Scanner scan) {
		name = askCharName(scan);
		byte statAmount;
		for (byte i=0; i<6; i++) {
			statAmount = askStatAllocation(scan, statPoints, i);
			if (statAmount == 0 && i>0) {
				i--;
				i--;
			}else {
				addStatPoints(statAmount, i);
			}
		}
	}
	
	public static String askCharName(Scanner scan) {
		System.out.println("Enter your character's name.");
		String charName = scan.nextLine();
		System.out.println("Your adventurer's name is " + charName);
		return charName;  // the word scope applies to a variable, and
						  // refers to which parts of the program can
						  // access the variable. Here, I return the 
						  // variable to be accessed somewhere else.
	}
	
	private byte askStatAllocation(Scanner scan, byte points, byte i) {
		System.out.printf("\nYou have %d points left to allocate to your SPECIAL stats.\n"
				+ "How many points would you like to add to your %s stat?\n"
				+ "Your %s is currently at %d "
				+ (getStat(i)==1 ? "point.\n":"points.\n"),
				points, getStatName(i), getStatName(i), getStat(i));
		boolean checkPoints = false;
		int pointRead;
		byte pointAllot;
		do {
			pointRead = scan.nextInt();
			scan.nextLine();
			pointAllot = (byte)pointRead; // casting is taking a value of a data type
											   // and converting into another type.
			if (pointAllot >= 0 && pointAllot <= this.statPoints) {
				checkPoints = true;
			}else {
				System.out.println("You can't allocate this many points!\n"
						+ "Enter another number.");
			}
		}while (checkPoints != true && statPoints > 0);
		return pointAllot;
	}
	
	private void addStatPoints(byte points, byte stat) {
		switch(stat) {
		case 0:
			strength += points;
			break;
		case 1:
			perception += points;
			break;
		case 2:
			endurance += points;
			break;
		case 3:
			charisma += points;
			break;
		case 4:
			intelligence += points;
			break;
		case 5:
			agility += points;
			break;
		default:
			luck += points;
			break;
		}
		statPoints -= points;
	}
	
	public String getStatName(byte i) {
		String statName;
		if (i==0) {
			statName = "Stength";
		}else if (i==1) {
			statName = "Perception";
		}else if (i==2) {
			statName = "Endurance";
		}else if (i==3) {
			statName = "Charisma";
		}else if (i==4) {
			statName = "Intelligence";
		}else if (i==5) {
			statName = "Agility";
		}else if (i==6) {
			statName = "Luck";
		}else {
			statName = "Money";
		}
		return statName;
	}
	
	
	public void setName(String nam) {
		name = nam;
	}
	
	public String getName() {
		return name;
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
		return money;  //Foie gras bust of Albert Einstein
	}
	
	public byte getLevel() {
		return level;
	}
	
	public void levelUp() {
		if (exp >= 10*level && level < LEVEL_CAP) {
			exp = 0.00;
			++level;
		}
	}
	
	public byte getStat(byte i) {
		byte stat;
		switch(i) {
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
		default:
			stat = getLevel();
			break;
		}
		return stat;
	}
}

