// Michael D'Agostino
// This program is going to be an RPG-based battle-sim, where you can
// make a character, send them on quests, and do battle with auto-controlled
// Pals that are randomly generated and controlled.

// This is the Main file and will have methods related to making the
// program loops functioning correctly.

package integrationPackage;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		welcomeUser();
		Scanner scan = new Scanner(System.in);
		// a variable is a place in
		// memory where data is stored.

		Character adventurer = new Character();
		adventurer.initializeCharStats(scan);
		printStats(adventurer);

		printMainMenu();
		String select;
		boolean menuLoop = true;
		while (menuLoop) {
			select = scan.nextLine();
			switch (select) {
			case "1":
				loadQuest();
				printMainMenu();
				break;
			case "2":
				loadItemShop();
				printMainMenu();
				break;
			case "3":
				loadEquipShop();
				printMainMenu();
				break;
			case "4":
				loadInventory(adventurer);
				printMainMenu();
				break;
			case "5":
				loadPalManagement();
				printMainMenu();
				break;
			case "6":
				loadHunterManual();
				printMainMenu();
				break;
			case "0":
				System.out.println("Are you sure you want to quit the game? (y/n)");
				boolean menuCheck = checkUserSelection(scan);
				if (menuCheck == true) {
					System.out.println("Thanks for playing!");
					menuLoop = false;
					scan.close();
					break; //

				} else {
					printMainMenu();
					continue; // continue skips this iteration of a loop, and
								// moves to the end of the loop.
				}
			default:
				System.out.println("This input is invalid, try another selection!");
			}
		}
	}

	public static void printMainMenu() {
		System.out.println("[1] Begin Quest\n" + "[2] Item Shop\n" + "[3] Equipment Shop\n" + "[4] Hunter Inventory\n"
				+ "[5] Pal Management\n" + "[6] The Hunter's Manual\n" + "[0] Quit Game\n");
	}

	public static void loadQuest() {
		System.out.println("Quest Start!\n Coming Soon!\n");
	}

	public static void loadItemShop() {
		System.out.println("Welcome to the Item Shop!\n Coming Soon!\n");
	}

	public static void loadEquipShop() {
		System.out.println("Welcome to the Equipment Shop!\n Coming Soon!\n");
	}

	public static void loadInventory(Character member) {
		System.out.println("Opening your inventory...\n");
		printStats(member);
	}

	public static void loadPalCreation() {
		System.out.println("Hiring a Pal.\n Coming Soon!\n");
	}

	public static void loadPalManagement() {
		System.out.println("Viewing your Pal list.\n Coming Soon!\n");
	}

	public static void loadHunterManual() {
		System.out.println("Viewing Hunter\'s Manual\n Coming Soon!\n");
	}

	public static boolean checkUserSelection(Scanner scan) {
		String selection = scan.nextLine();
		boolean check = (selection.equals("y") ? true : false); // using == to compare strings
		return check; // will only check to see if they have the same name. using equals()
						// compares the two objects to see if their value is the same.
	}

	public static void printStats(Character member) {
		System.out.printf(
				"%s has the following stats:\n" + "Level: %d\n" + "Exp: %d\n" + "Strength: %d\n" + "Perception: %d\n"
						+ "Endurance: %d\n" + "Charisma: %d\n" + "Intelligence: %d\n" + "Agility: %d\n" + "Luck: %d\n"
						+ "Gold: %d\n",
				member.getName(), member.getLevel(), member.getExp(), member.getStrength(), member.getPerception(),
				member.getEndurance(), member.getCharisma(), member.getIntelligence(), member.getAgility(),
				member.getLuck(), member.getMoney());
	}

	public static boolean getPartyTurn(int turn) {
		boolean activeParty;
		if (turn % 2 == 1) {
			activeParty = true;
		} else {
			activeParty = false;
		}
		return activeParty;
	}

	public static void welcomeUser() {
		System.out.println("Welcome to my Integration Project!\n" + "This is an RPG-inspired project to\n"
				+ "show what I've learned in COP 2006!\n");
	}

	public static String getSpecialSelection(byte selection) {
		String specialName = "nope";

		if (selection == 0) {
			specialName = "Strength";
		} else if (selection == 1) {
			specialName = "Perception";
		} else if (selection == 2) {
			specialName = "Endurance";
		} else if (selection == 3) {
			specialName = "Charisma";
		} else if (selection == 4) {
			specialName = "Intelligence";
		} else if (selection == 5) {
			specialName = "Agility";
		} else {
			specialName = "Luck";
		}
		return specialName;

	}

}

//a string is an object that represents
// a sequence of characters, and is called
// via a class.

//final sets a hard limit to the level
// cap, as it can't be changed at all.

/*
 * byte bytes are 8-bits, and are basically integers with a range between -128
 * and 127
 */
/*
 * short shorts are 16-bits with a range of -32768 to 32767, and can be used for
 * documentation the same way as a byte.
 */
/*
 * int ints are 32-bits, with a range between -2^31 and 2^31 - 1. They represent
 * integers.
 */
/*
 * long longs are 64-bits, min -2^63, max 2^63 - 1, and can be used the same
 * ways as shorts and ints
 */
/*
 * float floats are 32-bits and allow for the representation of decimal values
 * in a variable. These should usually not be used for precise values
 */
/*
 * double doubles are 64-bits, and allow for the use of decimal values in
 * variables, similar to floats.
 */
/*
 * boolean booleans are either true or false, and are useful when u need to
 * track something that's defined as either true or false.
 */
/*
 * char chars are 16-bit Unicode characters, and basically represent one
 * character
 */

/*
 * Operator precedence in Java determines the order that operators are
 * evaluated. In Java, operators of equal precedence are evaluated from left to
 * right, starting in the innermost parentheses, in the following order: postfix
 * increment/decrement, prefix incre/decrement, multiplication/division/modulus,
 * add/subtract, logicals
 */
