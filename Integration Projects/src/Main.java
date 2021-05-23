// Michael D'Agostino
// This program is going to be an RPG-based auto-battler, where you can
// make a character, send them on quests, and watch them do battle.

// Sources:
// https://www.javatpoint.com/how-to-generate-random-number-in-java

import java.util.Scanner;
// import java.lang.Math;

public class Main {

	public static void main(String[] args) {
		System.out.println(welcomeUser());
		
		String charName = askCharName();  // a variable is a place in
										  // memory where data is stored.
		Character adventurer = new Character();
		adventurer.name = charName;
		
		int statPoints = 30;
		byte specialSelection = 0;
		int statAddition;
		while ((statPoints > 0) && (specialSelection <=6)) {
			System.out.println("You have "+ statPoints + " points to allocate "
					+ "to your\n'SPECIAL' stats.");
			System.out.println("How many points would you like to allocate\n"
					+ "to your " + getSpecialSelection(specialSelection) +
					" stat?\n");
			
			statAddition = getPointAllocation();
			// System.out.println("Enter an integer: ");
			// statAddition = scan.nextInt();
			
			if (statPoints - statAddition < 0) {
				System.out.println("You can't allocate this many points!");
			}else {
				statPoints -= statAddition;
				
				if (specialSelection == 0) {
					adventurer.strength = statAddition;
				}else if (specialSelection == 1) {
					adventurer.perception = statAddition;
				}else if (specialSelection == 2) {
					adventurer.endurance = statAddition;
				}else if (specialSelection == 3) {
					adventurer.charisma = statAddition;
				}else if (specialSelection == 4) {
					adventurer.intelligence = statAddition;
				}else if (specialSelection == 5) {
					adventurer.agility = statAddition;
				}else {
					adventurer.luck = statAddition;
				}
				
				specialSelection += 1;
			}
		}
		System.out.println(adventurer.name + " has the following stats:\n"
				+ "Strength: " + adventurer.strength + "\n"
				+ "Perception: " + adventurer.perception + "\n"
				+ "Endurance: " + adventurer.endurance + "\n"
				+ "Charisma: " + adventurer.charisma + "\n"
				+ "Intelligence: " + adventurer.intelligence + "\n"
				+ "Agility: " + adventurer.agility + "\n"
				+ "Luck: " + adventurer.luck + "\n"
				+ "Gold: " + adventurer.money + "\n"
				);
	}


	public static String welcomeUser() {
		return "Welcome to my Integration Project!\n"
				+ "This is an RPG-inspired project to\n"
				+ "show what I've learned in COP 2006!\n";
	}
	
	
	public static String askCharName() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your character's name.");
		String charName = scan.nextLine();
		System.out.println("Your adventurer's name is " + charName);
		return charName;  // the word scope applies to a variable, and
						  // refers to which parts of the program can
						  // access the variable. Here, I return the 
						  // variable to be accessed somewhere else.
	}
	
	
	public static int getPointAllocation() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter an integer: ");
		int points = scan.nextInt();
		return points;
	}
	
	
	public static String getSpecialSelection(
			byte selection) {
		String specialName = "nope";
		
		if (selection == 0){
			specialName = "Strength";
		}else if (selection == 1){
			specialName = "Perception";
		}else if (selection == 2){
			specialName = "Endurance";
		}else if (selection == 3){
			specialName = "Charisma";
		}else if (selection == 4){
			specialName = "Intelligence";
		}else if (selection == 5){
			specialName = "Agility";
		}else {
			specialName = "Luck";
		}
		return specialName;
		
	}

}

class Character {
	String name;			 // a string is an object that represents
							 // a sequence of characters, and is called
							 // via a class.
	String weapon;
	final int levelCap = 30; // final sets a hard limit to the level
							 // cap, as it can't be changed at all.
	byte level = 1;
	double exp = 0.00;	// enemy level will cause multipliers for exp, allowing
						// for decimal values.
	int strength;
	int perception;
	int endurance;
	int charisma;
	int intelligence;
	int agility;
	int luck;
	int money = 100;
	boolean alive = true;
}

class Enemy {
	String name;
	byte level;
	double exp;
	int strength;
	int perception;
	int endurance;
	int charisma;
	int intelligence;
	int agility;
	int luck;
	int money;
}

/* byte     bytes are 8-bits, and are basically
			integers with a range between -128 and 127*/
/* short    shorts are 16-bits with a range of -32768 to
  			32767, and can be used for documentation the
  			same way as a byte.*/
/* int      ints are 32-bits, with a range between
            -2^31 and 2^31 - 1. They represent integers.*/
/* long     longs are 64-bits, min -2^63, max 2^63 - 1, and
   			can be used the same ways as shorts and ints*/
/* float    floats are 32-bits and allow for the representation
            of decimal values in a variable. These should usually
            not be used for precise values*/
/* double   doubles are 64-bits, and allow for the use of 
            decimal values in variables, similar to floats.*/
/* boolean  booleans are either true or false, and are
            useful when u need to track something that's
            defined as either true or false.*/
/* char     chars are 16-bit Unicode characters, and basically
 			represent one character*/