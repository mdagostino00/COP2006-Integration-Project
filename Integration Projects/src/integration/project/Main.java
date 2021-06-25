/**
 * Michael D'Agostino This program is going to be an RPG-based battle-sim, where you can make a
 * character, send them on quests, and do battle with auto-controlled Pals that are randomly
 * generated and controlled.
 *
 * <p>This is the Main file and will have methods related to making the program loops functioning
 * correctly.
 */

package integration.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class where program methods are stored. Allows for RPG game to be played.
 *
 * @author Michael D'Agostino
 */
public class Main {

  /**
 * This is supposed to be a fix for the bad rng bug, but it doesn't work. 
 * I guess bad rng is a feature now.
 */
  public static final Random RAND = new Random();

  /**
   * Initializes the player character and 2 pal objects, then starts a loop where the program can be
   * used indefinitely until exit. Loop is terminated on user exit or on player death.
   *
   * @param args needed to make main run
   */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    welcomeUser();
    promptCharCreation();
    // a variable is a place in
    // memory where data is stored
    Character adventurer = new Character('c', (byte) 1);
    adventurer.initializeCharStats(scan);
    Palico pal1 = new Palico('p', (byte) 1);
    pal1.initializeCharStats(scan);
    Palico pal2 = new Palico('p', (byte) 1);
    pal2.initializeCharStats(scan);

    ArrayList<Character> entityList = new ArrayList<Character>();
    entityList.add(adventurer); // entityList0
    entityList.add(pal1);
    entityList.add(pal2);

    printMainMenu();
    String select;
    boolean menuLoop = true;
    while (menuLoop) {
      select = scan.nextLine();
      switch (select) {
        case "1":
          if (loadQuest(entityList, scan) == true) {
            System.out.println("\n\nYou Win!\n");
            for (int i = 0; i <= 2; i++) { // level up check
              entityList
                  .get(i)
                  .setExp(entityList.get(i).getExp() + (3.21 * entityList.get(i).getLevel()));
              entityList.get(i).levelUp();
            }
            printMainMenu();
          } else {
            System.out.println("Try again next time!");
            if (entityList.get(0).getAlive() == false) {
              menuLoop = false;
            } else {
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

  /**
 * Prints main menu options to cmd line. Just a string.
 */
  public static void printMainMenu() {
    System.out.println("\n[1] Begin Quest\n" + "[2] Party Management\n" + "[0] Quit Game\n");
  }

  /**
 * Prints character creation prompt to cmd line.
 */
  public static void promptCharCreation() {
    System.out.println(
        "You can now customize your player character by\n" + "changing their SPECIAL stats");
  }

  /**
 * Takes given ArrayList of character objects, and sorts the lists based on the object's agility
 * stat. The resulting sort will group together objects of similar types. Only supports an
 * ArrayList with size 4.

 * @param myList given ArrayList of Character objects
 * @return sorted ArrayList
 */
  public static ArrayList<Character> getBattleOrder(ArrayList<Character> myList) {
    byte thisAgility = 0; 
    byte fastestAgility = 0; 
    byte slowestAgility = 0;
    int fastestLoc = 0;
    int slowestLoc = 0;
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

  /**
 * Adds a new Enemy object to the party's list of other Character Objects.

 * @param myList ArrayList of Character Objects
 * @param loc byte that represents current location/player level interval
 * @return ArrayList with Enemy Object added
 */
  public static ArrayList<Character> generateBattleArray(ArrayList<Character> myList, byte loc) {
    Enemy enemy = generateEnemy(loc);
    ArrayList<Character> battleList = new ArrayList<Character>();
    for (Character var : myList) {
      battleList.add(var);
    }
    battleList.add(enemy);
    battleList = getBattleOrder(battleList);
    return battleList;
  }

  /**
 * Runs the battle loop until enemy health hits 0, or player character health hits 0. Creates a 
 * stat array that stores the stats of each entity in the battle. The battle loop then loops through
 * each object in the order determined by getBattleOrder(), with each entity taking 1 action.
 * Enemy and Pal objects are determined randomly, while the player can choose which action to take.
 * When enemy or player health hits 0, loop ends. If enemy health hits 0, party gets exp and level
 * up is checked. if player health hits 0, program ends.

 * @param battleList sorted ArrayList containing Character Objects.
 * @param enemyPos given position of the enemy in the battle list
 * @param scan scanner object
 * @return true if win, false if lose.
 */
  public static boolean battleLoop(ArrayList<Character> battleList, int enemyPos, Scanner scan) {
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
        case 'c': //character actions
          defender = battleList.get(enemyPos);
          defenderPos = enemyPos;

          switch (battleList.get(currentEntity).getActionChoice(scan)) {
            case 1:
              damage =
                  battleList
                      .get(currentEntity)
                      .useMeleeAction(statArray, currentEntity, defenderPos);
              System.out.printf(
                  "%n%s attacks the %s.!%n",
                  battleList.get(currentEntity).getName(), defender.getName());
              break;
            case 2:
              damage =
                  battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos);
              System.out.printf(
                  "%n%s shoots their Glock at the %s.!%n",
                  battleList.get(currentEntity).getName(), defender.getName());
              break;
            case 3:
              damage =
                  battleList
                      .get(currentEntity)
                      .useMagicAction(statArray, currentEntity, defenderPos);
              System.out.printf(
                  "%n%s uses magic on the %s.%n",
                  battleList.get(currentEntity).getName(), defender.getName());
              break;
            case 4:
              damage =
                  battleList
                      .get(currentEntity)
                      .useTalkAction(statArray, currentEntity, defenderPos);
              System.out.printf(
                  "%n%s distracts the %s!%n",
                  battleList.get(currentEntity).getName(), defender.getName());
              break;
            case 5:
              runAway =
                  (battleList.get(currentEntity).useRunAction(statArray, currentEntity, defenderPos)
                          == true
                      ? true
                      : false);
              break;
            default:
              System.out.println(
                  "You should not have come here. The Action Choice" + "Menu messed up somehow.");
          }

          break;
        case 'e': //enemy actions
          decideDefender = Main.RAND.nextInt(100) + 1;
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

          decideAction = Main.RAND.nextInt(100) + 1;
          if (decideAction <= 20) {
            damage =
                battleList.get(currentEntity).useTalkAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s shrieks at %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else if (decideAction >= 80) {
            damage =
                battleList.get(currentEntity).useMagicAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s uses magic on %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else if (decideAction > 20 && decideAction <= 40) {
            damage =
                battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s shoots a projectile at %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else {
            damage =
                battleList.get(currentEntity).useMeleeAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s attacks %s!%n", battleList.get(currentEntity).getName(), defender.getName());
          }

          break;
        case 'p': //pal actions
          defender = battleList.get(enemyPos);
          defenderPos = enemyPos;

          decideAction = Main.RAND.nextInt(100) + 1;
          if (decideAction <= 20) {
            damage =
                battleList.get(currentEntity).useTalkAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s distracts the %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else if (decideAction >= 80) {
            damage =
                battleList.get(currentEntity).useMagicAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s uses magic on the %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else if (decideAction > 20 && decideAction <= 40) {
            damage =
                battleList.get(currentEntity).useGunAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s shoots an arrow at the %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          } else {
            damage =
                battleList.get(currentEntity).useMeleeAction(statArray, currentEntity, defenderPos);
            System.out.printf(
                "%n%s attacks the %s!%n",
                battleList.get(currentEntity).getName(), defender.getName());
          }
          break;
        default:
          defender = battleList.get(enemyPos);
          defenderPos = enemyPos;
          damage = 9999;
          System.out.printf(
              "%s takes %d damage!%n" + "How TF did you get here!", defender.getName(), damage);
          break;
      }
      if (runAway == true) {
        System.out.printf("%n%s runs away!%n", battleList.get(currentEntity).getName());
        return false;
      } else {
        statArray[defenderPos][7] -= damage;
        if (statArray[defenderPos][7] <= 0) {
          battleList.get(defenderPos).setAlive(false);
          ;
        }
        System.out.printf("%s takes %d damage!%n", defender.getName(), damage);
      }
    }

    if (statArray[charPos][7] <= 0) { //check if player died
      System.out.println("You Died.");
      return false;
    } else { //if player is not dead
      return true;
    }
  }

  /**
 * creates an array of Character's SPECIAL stats + health.

 * @param myList ArrayList of Characters
 * @return 4x8 byte array of stats
 */
  public static int[][] createBattleStatArray(ArrayList<Character> myList) {
    int[][] statArray = new int[4][8];
    for (int i = 0; i < myList.size(); i++) {
      for (int stat = 0; stat < 8; stat++) {
        statArray[i][stat] = myList.get(i).getStat(stat);
      }
    }
    return statArray;
  }

  /**
 * Grabs location byte and battle list from player Character object and objects made in Main, then
 * passes them to the battle loop. When battleLoop() is finished, party success is returned as
 * boolean to determine results of battle.

 * @param myList list of party Character objects
 * @param scan scanner object
 * @return if party won or lost
 */
  public static boolean loadQuest(ArrayList<Character> myList, Scanner scan) {
    System.out.println("Quest Start!\n");
    byte location = (byte) (myList.get(0).getLevel() % 10);
    ArrayList<Character> battleList;
    battleList = generateBattleArray(myList, location);
    int enemyPos = 0;
    for (int i = 0; i < battleList.size(); i++) {
      if (battleList.get(i).getType() == 'e') {
        enemyPos = i;
      } else {
        continue;
      }
      ;
    }
    System.out.println(
        "Your party heads to the "
            + getLocationName(location)
            + " and encounters a "
            + battleList.get(enemyPos).getName()
            + "!\n");
    boolean checkQuest = battleLoop(battleList, enemyPos, scan);
    battleList.remove(enemyPos);
    return checkQuest;
  }

  /**
 * Genertes location name depending on given byte.

 * @param location given byte of player level
 * @return string name
 */
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

  /**
 * Prints SPECIAL stats + money + level for each object.

 * @param myList ArrayList of Chars
 */
  public static void printBattleArray(ArrayList<Character> myList) {
    for (Character var : myList) {
      printStats(var);
    }
  }

  /**
 * check if input is "y".

 * @param scan scanner object
 * @return true if y, false if other
 */
  public static boolean checkUserSelection(Scanner scan) {
    String selection = scan.nextLine();
    boolean check = (selection.equals("y") ? true : false);
    // using == to compare strings
    // will only check to see if they have the same name. using equals()
    // compares the two objects to see if their value is the same.
    return check;
  }

  /**
 * prints Character's SPECIAL stats, + level + gold.

 * @param member Character object
 */
  public static void printStats(Character member) {
    System.out.printf(
        "%n%s has the following stats:%n"
            + "Level: %d%n"
            + "Exp: %.2f%n"
            + "Strength: %d%n"
            + "Perception: %d%n"
            + "Endurance: %d%n"
            + "Charisma: %d%n"
            + "Intelligence: %d%n"
            + "Agility: %d%n"
            + "Luck: %d%n"
            + "Gold: %d%n",
        member.getName(),
        member.getLevel(),
        member.getExp(),
        member.getStrength(),
        member.getPerception(),
        member.getEndurance(),
        member.getCharisma(),
        member.getIntelligence(),
        member.getAgility(),
        member.getLuck(),
        member.getMoney());
  }

  /**
 * Determines current active entity in battleloop, depending on turn count.

 * @param turn ArrayList position
 * @return current active entity
 */
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

  /**
   * Generates new enemy based on location, with money being randomly generated.

   * @param location byte of player level
   * @return enemy object
   */
  public static Enemy generateEnemy(byte location) { // location is capped at 10
    Enemy genEnemy = new Enemy('e', (byte) (location + Main.RAND.nextInt(2 * location)));
    genEnemy.setName(genEnemy.generateEnemyName(location));
    // this level should never go above 30: 10 + 20 = 30
    genEnemy.initializeCharStats();
    genEnemy.setMoney(
        genEnemy.getLevel() * genEnemy.getLuck() * Main.RAND.nextInt(genEnemy.getLevel() * 10));
    return genEnemy;
  }

  /**
 * Welcomes user.
 */
  public static void welcomeUser() {
    System.out.println(
        "Welcome to my Integration Project!\n"
            + "This is an RPG-inspired project to\n"
            + "show what I've learned in COP 2006!\n");
  }

  /**
 * Gets name of SPECIAL stat as string depending on given byte.

 * @param selection given byte representing desired SPECIAL stat
 * @return String of stat's name
 */
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

// a string is an object that represents
// a sequence of characters, and is called
// via a class.

// final sets a hard limit to the level
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
