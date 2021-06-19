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
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		welcomeUser();
		promptCharCreation();
		// a variable is a place in
		// memory where data is stored.
		ArrayList<Character> entityList = new ArrayList<Character>();

		Character adventurer = new Character('c', (byte) 1);
		adventurer.initializeCharStats(scan, rand);

		Palico pal1 = new Palico('p', (byte) 1);
		pal1.initializeCharStats(scan, rand);
		Palico pal2 = new Palico('p', (byte) 1);
		pal2.initializeCharStats(scan, rand);

		entityList.add(adventurer); // entityList0
		entityList.add(pal1);
		entityList.add(pal2);

		// System.out.println(entityList);

		printMainMenu();
		String select;
		boolean menuLoop = true;
		while (menuLoop) {
			select = scan.nextLine();
			switch (select) {

			case "1":
				if (loadQuest(entityList, rand, scan) == true) {
					System.out.println("\n\nYou Win!\n");
					for (int i = 0; i <= 2; i++) {
						entityList.get(i).setExp(entityList.get(i).getExp() + (1.21 * entityList.get(i).getLevel()));
						entityList.get(i).levelUp(rand);
					}
					printMainMenu();
				} else {
					System.out.println("Try again next time!");
					if (entityList.get(0).getAlive() == false) {
						menuLoop = false;
					}else {
						printMainMenu();
					}
				}
				break;
				
			case "2":
				loadPartyManagement(entityList);
				printMainMenu();
				break;

			case "0":
				System.out.println("Are you sure you want to quit the game? (y/n)");
				boolean menuCheck = checkUserSelection(scan);
				if (menuCheck == true) {
					System.out.println("Thanks for playing!");
					menuLoop = false;
					scan.close();
					break;
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
		System.out.println("\n[1] Begin Quest\n" + "[2] Party Management\n" + "[0] Quit Game\n");
	}

	public static void promptCharCreation() {
		System.out.println("You can now customize your player character by\n" + "changing their SPECIAL stats");

	}

	public static ArrayList<Character> getBattleOrder(ArrayList<Character> myList) {
		byte thisAgility = 0, fastestAgility = 0, slowestAgility = 0;
		int fastestLoc = 0, slowestLoc = 0;
		for (Character var : myList) {
			thisAgility = var.getAgility();
			if (fastestAgility == 0 && slowestAgility == 0) {
				fastestAgility = thisAgility;
				slowestAgility = thisAgility;
				fastestLoc = myList.indexOf(var);
				slowestLoc = myList.indexOf(var);
			} else if (thisAgility > fastestAgility) {
				fastestAgility = thisAgility;
				fastestLoc = myList.indexOf(var);
			} else if (thisAgility <= slowestAgility) {
				slowestAgility = thisAgility;
				slowestLoc = myList.indexOf(var);
			} else {
				continue;
			}
		}
		ArrayList<Character> battleList = new ArrayList<Character>();
		if (fastestLoc == 0 && slowestLoc > 2) {
			for (Character var : myList) {
				battleList.add(var);
			}
		} else if (fastestLoc == 0 && slowestLoc <= 2) {
			battleList.add(myList.get(0));
			battleList.add(myList.get(3));
			battleList.add(myList.get(1));
			battleList.add(myList.get(2));
		} else if (fastestLoc > 2 && slowestLoc == 0) {
			battleList.add(myList.get(3));
			battleList.add(myList.get(1));
			battleList.add(myList.get(2));
			battleList.add(myList.get(0));
		} else {
			battleList.add(myList.get(3));
			battleList.add(myList.get(0));
			battleList.add(myList.get(1));
			battleList.add(myList.get(2));
		}

		return battleList;
	}

	public static ArrayList<Character> generateBattleArray(ArrayList<Character> myList, byte loc, Random rand) {
		Enemy enemy = generateEnemy(loc, rand);
		ArrayList<Character> battleList = new ArrayList<Character>();
		for (Character var : myList) {
			battleList.add(var);
		}
		battleList.add(enemy);
		battleList = getBattleOrder(battleList);
		return battleList;
	}

	public static boolean battleLoop(ArrayList<Character> battleList, int enemyPos, Random rand, Scanner scan) {
		int charPos = 0;
		int pal1Pos = 69;
		int pal2Pos = 0;
		int turnNum = 0;
		for (int i = 0; i < battleList.size(); i++) {
			if (battleList.get(i).getType() == 'c') {
				charPos = i;
			} else if (battleList.get(i).getType() == 'e') {
				enemyPos = i;
			} else if (battleList.get(i).getType() == 'p') {
				if (pal1Pos == 69) {
					pal1Pos = i;
				} else {
					pal2Pos = i;
				}
				;
			} else {
				continue;
			}
		}
		int[][] statArray = new int[4][8];
		statArray = createBattleStatArray(battleList);

		int currentEntity = 0;
		int decideAction;
		int decideDefender;
		Character defender;
		int defenderPos;
		int damage = 0;
		boolean runAway = false;
		while (statArray[charPos][7] > 0 && statArray[enemyPos][7] > 0) {
			turnNum++;
			currentEntity = getPartyTurn(turnNum);
			damage = 0;

			if (battleList.get(currentEntity).getAlive() == false) {
				continue;
			}

			switch (battleList.get(currentEntity).getType()) {
			case 'c':
				defender = battleList.get(enemyPos);
				defenderPos = enemyPos;

				switch (battleList.get(currentEntity).getActionChoice(scan)) {
				case 1:
					damage = battleList.get(currentEntity).useMeleeAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s attacks the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
					break;
				case 2:
					damage = battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s shoots their Glock at the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
					break;
				case 3:
					damage = battleList.get(currentEntity).useMagicAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s uses magic on the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
					break;
				case 4:
					damage = battleList.get(currentEntity).useTalkAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s distracts the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
					break;
				case 5:
					runAway = (battleList.get(currentEntity).useRunAction(statArray, currentEntity, defenderPos,
							rand) == true ? true : false);
					break;
				default:
					System.out.println("You should not have come here.");
				}

				break;
			case 'e':
				decideDefender = rand.nextInt(100) + 1;
				if (decideDefender < 30) {
					defender = battleList.get(pal1Pos);
					defenderPos = pal1Pos;
				} else if (decideDefender > 70) {
					defender = battleList.get(pal2Pos);
					defenderPos = pal2Pos;
				} else {
					defender = battleList.get(charPos);
					defenderPos = charPos;
				}

				decideAction = rand.nextInt(100) + 1;
				if (decideAction <= 20) {
					damage = battleList.get(currentEntity).useTalkAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s shrieks at %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else if (decideAction >= 80) {
					damage = battleList.get(currentEntity).useMagicAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s uses magic on %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else if (decideAction > 20 && decideAction <= 40) {
					damage = battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s shoots a projectile at %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else {
					damage = battleList.get(currentEntity).useMeleeAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s attacks %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				}

				break;
			case 'p':
				defender = battleList.get(enemyPos);
				defenderPos = enemyPos;

				decideAction = rand.nextInt(100) + 1;
				if (decideAction <= 20) {
					damage = battleList.get(currentEntity).useTalkAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s distracts the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else if (decideAction >= 80) {
					damage = battleList.get(currentEntity).useMagicAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s uses magic on the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else if (decideAction > 20 && decideAction <= 40) {
					damage = battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s shoots an arrow at the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				} else {
					damage = battleList.get(currentEntity).useMeleeAction(statArray, currentEntity, defenderPos, rand);
					System.out.printf("\n%s attacks the %s!\n", battleList.get(currentEntity).getName(),
							defender.getName());
				}
				break;
			default:
				defender = battleList.get(enemyPos);
				defenderPos = enemyPos;
				damage = 9999;
				System.out.printf("%s takes %d damage!\n" + "How TF did you get here!", defender.getName(), damage);
				break;
			}
			if (runAway == true) {
				System.out.printf("\n%s runs away!\n", battleList.get(currentEntity).getName());
				return false;
			} else {
				statArray[defenderPos][7] -= damage;
				if (statArray[defenderPos][7] <= 0) {
					battleList.get(defenderPos).setAlive(false);
					;
				}
				System.out.printf("%s takes %d damage!\n", defender.getName(), damage);
			}

		}

		if (statArray[charPos][7] < 0) {
			System.out.println("You Died.");
			return false;
		} else {
			return true;
		}

		// System.out.println("This is where the battleloop would go");
	}

	public static int[][] createBattleStatArray(ArrayList<Character> myList) {
		int[][] statArray = new int[4][8];
		for (int i = 0; i < myList.size(); i++) {
			for (int stat = 0; stat < 8; stat++) {
				statArray[i][stat] = myList.get(i).getStat(stat);
			}
		}
		return statArray;
	}

	public static boolean loadQuest(ArrayList<Character> myList, Random rand, Scanner scan) {
		System.out.println("Quest Start!\n");
		byte location = (byte) (myList.get(0).getLevel() % 10);
		ArrayList<Character> battleList = new ArrayList<Character>();
		battleList = generateBattleArray(myList, location, rand);
		int enemyPos = 0;
		for (int i = 0; i < battleList.size(); i++) {
			if (battleList.get(i).getType() == 'e') {
				enemyPos = i;
			} else {
				continue;
			}
			;
		}
		System.out.println("Your party heads to the " + getLocationName(location) + " and encounters a "
				+ battleList.get(enemyPos).getName() + "!\n");
		boolean checkQuest = battleLoop(battleList, enemyPos, rand, scan);
		battleList.remove(enemyPos);
		return checkQuest;
	}

	public static String getLocationName(byte location) {
		String locName;
		switch (location) {
		case 1:
			locName = "Blooming Plains";
			break;
		case 2:
			locName = "Misty Rainforest";
			break;
		case 3:
			locName = "Graven Marsh";
			break;
		case 4:
			locName = "Bellowing Mountain";
			break;
		case 5:
			locName = "Cryptic Caverns";
			break;
		case 6:
			locName = "Ancient Spire";
			break;
		case 7:
			locName = "Foggy Seabank";
			break;
		case 8:
			locName = "Canada";
			break;
		case 9:
			locName = "Volcanic Isles";
			break;
		case 0:
			locName = "Desert Wasteland";
			break;
		default:
			locName = "Neverland";
			break;
		}

		return locName;
	}

	public static void loadPartyManagement(ArrayList<Character> myList) {
		System.out.println("Viewing your Party Stats...");
		printBattleArray(myList);
	}

	public static void printBattleArray(ArrayList<Character> myList) {
		for (Character var : myList) {
			printStats(var);
		}
	}

	public static boolean checkUserSelection(Scanner scan) {
		String selection = scan.nextLine();
		boolean check = (selection.equals("y") ? true : false); // using == to compare strings
		return check; // will only check to see if they have the same name. using equals()
						// compares the two objects to see if their value is the same.
	}

	public static void printStats(Character member) {
		System.out.printf(
				"%s has the following stats:\n" + "Level: %d\n" + "Exp: %.2f\n" + "Strength: %d\n" + "Perception: %d\n"
						+ "Endurance: %d\n" + "Charisma: %d\n" + "Intelligence: %d\n" + "Agility: %d\n" + "Luck: %d\n"
						+ "Gold: %d\n\n",
				member.getName(), member.getLevel(), member.getExp(), member.getStrength(), member.getPerception(),
				member.getEndurance(), member.getCharisma(), member.getIntelligence(), member.getAgility(),
				member.getLuck(), member.getMoney());
	}

	public static int getPartyTurn(int turn) {
		int activeMember;
		if (turn % 4 == 0) {
			activeMember = 3;
		} else if (turn % 4 == 1) {
			activeMember = 0;
		} else if (turn % 4 == 2) {
			activeMember = 1;
		} else {
			activeMember = 2;
		}
		return activeMember;
	}

	public static Enemy generateEnemy(byte location, Random rand) { // location is capped at 10
		Enemy genEnemy = new Enemy('e', (byte) (location + rand.nextInt(2 * location)));
		genEnemy.setName(genEnemy.generateEnemyName(location, rand));
		// this level should never go above 30: 10 + 20 = 30
		genEnemy.initializeCharStats(rand);
		genEnemy.setMoney(genEnemy.getLevel() * genEnemy.getLuck() * rand.nextInt(genEnemy.getLevel() * 10));
		return genEnemy;
	}

	public static void welcomeUser() {
		System.out.println("Welcome to my Integration Project!\n" + "This is an RPG-inspired project to\n"
				+ "show what I've learned in COP 2006!\n");
	}

	public static String getSpecialSelection(byte selection) {
		String specialName;

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
/*
 * Inheritance is an important concept in Object-Oriented Programming. It's a
 * mechanism where an object can inherit the properties and behaviors of another
 * object, called the parent object. This allows for more code to be reused, as
 * one class's code can be reused for another, as well as allowing for the
 * addition of more code if needed.
 * 
 */
/*
 * Polymorphism occurs when object inheritance is used, and it happens when we
 * let one method perform multiple tasks. This can be done by overriding a
 * method in the superclass with a method in the subclass. This method will now
 * have a different function in that subclass, which can allow for the code to
 * seem more logical.
 */
