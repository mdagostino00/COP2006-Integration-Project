// Michael D'Agostino

// This is where the player character is created and handled.
// Player will have full control during battle, and maybe an inventory
// system if I have enough time for this project.

package integration.project;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Character object, where the player character, enemy, and palico functions are handled. Contains
 * methods for handling actions, and building Character objects

 * @author Michael D'Agostino
 *
 */
public class Character {
  private static final byte LEVEL_CAP = 30; 
  // final sets a hard limit to the level
  // cap, as it can't be changed at all.
  private String name; // a string is an object that represents
  // a sequence of characters, and is called
  // via a class.
  private String weapon;
  private char type;
  // cap, as it can't be changed at all.
  private byte level;
  private int hp;
  private byte statPoints = 30;
  private double exp; // enemy level will cause multipliers for exp, allowing
  // for decimal values.
  private byte strength = 1;
  private byte perception = 1;
  private byte endurance = 1;
  private byte charisma = 1;
  private byte intelligence = 1;
  private byte agility = 1;
  private byte luck = 1;
  private int money;
  private boolean alive = true;

  /**
 * This shouldn't ever be called lol. Generates a level 1 char if it is tho.
 */
  public Character() {
    type = 'c';
    level = 1;
    exp = 0.00;
    hp = 100;
  }

  /**
 * Creates a Character object of given type and level.

 * @param typ type
 * @param lvl level
 */
  public Character(char typ, byte lvl) {
    type = typ;
    level = lvl;
    exp = 0.00;
    hp = 500;
  }

  /**
 * Prints action menu for player's turn in battle.

 * @return the whole string
 */
  public String printActionMenu() {
    String menuPrompt =
        "\nSelect an action:\n"
            + "[1] Melee\n"
            + "[2] Gun\n"
            + "[3] Magic\n"
            + "[4] Talk\n"
            + "[5] Run\n";
    return menuPrompt;
  }

  /**
 * Gets user input for player turn's action choice.

 * @param scan scanner object
 * @return user choice
 */
  public int getActionChoice(Scanner scan) {
    System.out.println(printActionMenu());
    int choice = 0;
    boolean checkChoice = false;
    do {
      try {
        System.out.println("\nSelect an action.\n");
        choice = scan.nextInt();
        scan.nextLine();
        if (choice >= 1 && choice <= 5) {
          checkChoice = true;
        } else {
          System.out.println("This is not a selection. Try again.\n");
          continue;
        }
      } catch (InputMismatchException e) {
        System.out.println("This is not a valid input. Try again.\n");
        scan.nextLine();
      }
    } while (checkChoice == false);
    return choice;
  }

  /**
 * Use that object's Melee Action. Determine hit chance, then damage if hit chance is true.

 * @param charArrays stat array
 * @param attPos where attacker is in array
 * @param defPos where defender is in array
 * @return damage done to defender
 */
  public int useMeleeAction(int[][] charArrays, int attPos, int defPos) {
    if (getHitChance(charArrays, attPos, defPos) == true) {
      int damage = getBaseDamage(charArrays, attPos);
      damage = (damage + charArrays[attPos][0] * level) - (damage / charArrays[attPos][2]);
      return damage;
    } else {
      return 0;
    }
  }

  /**
  * Use that object's Gun Action. Determine hit chance, then damage if hit chance is true.

  * @param charArrays stat array
  * @param attPos where attacker is in array
  * @param defPos where defender is in array
  * @return damage done to defender
  */
  public int useGunAction(int[][] charArrays, int attPos, int defPos) {
    if (getHitChance(charArrays, attPos, defPos) == true) {
      int damage = getBaseDamage(charArrays, attPos);
      damage = (damage + charArrays[attPos][1] * level) - (damage / charArrays[attPos][2]);
      return damage;
    } else {
      return 0;
    }
  }
  
  /**
 * Use that object's Magic Action. Determine hit chance, then damage if hit chance is true.

 * @param charArrays stat array
 * @param attPos where attacker is in array
 * @param defPos where defender is in array
 * @return damage done to defender
 */
  public int useMagicAction(int[][] charArrays, int attPos, int defPos) {
    if (getHitChance(charArrays, attPos, defPos) == true) {
      int damage = getBaseDamage(charArrays, attPos);
      damage = (damage + charArrays[attPos][4] * level) - (damage / charArrays[attPos][2]);
      return damage;
    } else {
      return 0;
    }
  }

  /**
 * Use that object's Talk Action. Determine hit chance, then damage if hit chance is true.

 * @param charArrays stat array
 * @param attPos where attacker is in array
 * @param defPos where defender is in array
 * @return damage done to defender
 */
  public int useTalkAction(int[][] charArrays, int attPos, int defPos) {
    if (getHitChance(charArrays, attPos, defPos) == true) {
      int damage = getBaseDamage(charArrays, attPos);
      damage = (damage + charArrays[attPos][3] * level) - (damage / charArrays[attPos][2]);
      return damage;
    } else {
      return 0;
    }
    // this was supposed to be a menu that let's you talk to and recruit the enemies
    // as pals
  }

  
  /**
 * Use the run action. Only used by player. If true, run successful, and battle ends without
 * player death.

 * @param charArrays stat array
 * @param attPos where attacker is in array
 * @param defPos where defender is in array
 * @return true if successful, false if not
 */
  public boolean useRunAction(int[][] charArrays, int attPos, int defPos) {
    boolean runSuccess;
    int damage = getBaseDamage(charArrays, attPos);
    damage = (damage + charArrays[attPos][5] * level) - (damage / charArrays[attPos][5]);
    if (Main.RAND.nextInt(damage) < damage / charArrays[defPos][5]) {
      runSuccess = true;
    } else {
      runSuccess = false;
    }
    return runSuccess;
  }

  /**
 * determines if attack hits depending on attacker and defender stats, + rng.

 * @param charArrays stat array
 * @param attPos attacker position in array
 * @param defPos defender position in array
 * @return true if hit successful, false if not
 */
  public boolean getHitChance(int[][] charArrays, int attPos, int defPos) {
    int hitProbability =
        (int)
            (((21 - (charArrays[defPos][5] + charArrays[defPos][2] - charArrays[attPos][5])) / 20.0)
                * 100.0);
    int diceRoll = Main.RAND.nextInt(100 - charArrays[attPos][6] * 5) + 1;
    return (diceRoll < hitProbability ? true : false);
  }

  /**
 * Gets base damage by accumlating the total sum of SPECIAL stats in that char's array.

 * @param charArrays stat array
 * @param pos where object is in array
 * @return sum of SPECIAL stat points
 */
  public int getBaseDamage(int[][] charArrays, int pos) {
    int baseDamage = 0;
    for (int i = 0; i < 7; i++) {
      baseDamage += charArrays[pos][i];
    }
    return baseDamage;
  }

  /**
 * Prints Talk menu. Cut for time.

 * @param enemyName enemy's name
 */
  public void printTalkMenu(String enemyName) {
    System.out.println(
        "How would you like to approach the "
            + enemyName
            + "?\n"
            + "[1] Friendly"
            + "[2] Neutral"
            + "[3] Aggressively"
            + "[0] Back");
  }

  /**
 *  Creates Array of Char stats.

 * @return stat array
 */
  public byte[] createCharStatArray() {
    byte[] statArray = new byte[8];
    for (byte i = 0; i <= 7; i++) {
      statArray[i] = getStat(i);
    }
    // statArray[7] = statArray[(byte) 8];
    return statArray;
  }

  /**
 * Runs a loop to assign 30 stat points to SPECIAL stats.

 * @param scan Scanner object
 */
  public void initializeCharStats(Scanner scan) { // this top line is the header
    // the scan object would be the parameter in this method, other
    // methods below might have more parameters.
    name = askCharName(scan, type);
    money =
        ((luck > 6) ? ((luck * luck * Main.RAND.nextInt(10))) : ((luck * 2) - luck))
            + charisma * 3
            + Main.RAND.nextInt(50)
            + 100;
    byte statAmount;
    for (byte i = 0; i <= 6; i++) {
      statAmount = askStatAllocation(scan, statPoints, i);
      // the askStatAllocation(arguments) is the function call.
      // the scan object, statPoints byte, and i byte are the arguments.
      // scan passes the Scanner, statPoints passes the character's
      // available stat points, i is the current loop which represents
      // which stat will be changed.
      if (statAmount == 0 && i > 0) { // if 0 is entered to go back
        i--;
        removeStatPoints((byte) (getStat(i) - 1), i);
        i--;
      } else if (statAmount == 0 && i == 0) { // if 0 is entered to go back and on strength
        System.out.println("You can't go back any farther!");
        i--;
      } else { // if entered stat > 0
        addStatPoints(statAmount, i);
      }
    }
  }

  /**
 * Asks for a String, won't accept "".

 * @param scan Scanner
 * @param typ Type of object
 * @return String name
 */
  public String askCharName(Scanner scan, char typ) {
    boolean checkName = false;
    String charName;
    do {
      System.out.println("\nEnter your " + getStat(typ) + "\'s name.");
      charName = scan.nextLine();
      if (charName.compareTo("") > 0) {
        System.out.println("Your " + getStat(typ) + "\'s name is " + charName);
        checkName = true;
      } else {
        System.out.println("\nThat name is invalid! Enter another name.");
      }
    } while (checkName == false);
    return charName; // the word scope applies to a variable, and
    // refers to which parts of the program can
    // access the variable. Here, I return the
    // variable to be accessed somewhere else.
  }

  /**
 * Gets user input to get byte to put in SPECIAL stat.

 * @param scan Scanner object
 * @param points total current stat points
 * @param i which stat is being asked
 * @return amt of stat points for SPECIAL stat
 */
  private byte askStatAllocation(Scanner scan, byte points, byte i) {
    System.out.printf(
        "\nYou have %d points left to allocate to your SPECIAL stats.\n"
            + "How many points would you like to add to your %s stat?\n"
            + "Your %s is currently at %d "
            + (getStat(i) == 1 ? "point.\n" : "points.\n")
            + "Enter 0 to go back.\n",
        points,
        getStatName(i),
        getStatName(i),
        getStat(i));
    boolean checkPoints = false;
    int pointRead;
    byte pointAllot = 0;
    do {
      try {
        pointRead = Math.abs(scan.nextInt()); // this will ignore neg values
        scan.nextLine();

        pointAllot = (byte) pointRead;
        // casting is taking a value of a data type
        // and converting into another type.

        if (pointAllot >= 0 && pointAllot <= this.statPoints) {
          checkPoints = true;
        } else { // points < 0 || points > statPoints {
          System.out.println("You can't allocate this many points!\n" + "Enter another number.");
        }
      } catch (InputMismatchException e) {
        System.out.println("This is an invalid number. Try again.");
        scan.nextLine();
      }
    } while (checkPoints != true && statPoints > 0);
    return pointAllot;
    // pointAllot is a return value for this method.
  }

  /**
 * Adds given points to SPECIAL stats.

 * @param points given stat points
 * @param stat given stat byte
 */
  private void addStatPoints(byte points, byte stat) {
    switch (stat) {
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

  /**
 * Subtracts given points to SPECIAL stats.

 * @param points given stat points
 * @param stat given stat byte
 */
  private void removeStatPoints(byte points, byte stat) {
    switch (stat) {
      case 0:
        strength -= points;
        break;
      case 1:
        perception -= points;
        break;
      case 2:
        endurance -= points;
        break;
      case 3:
        charisma -= points;
        break;
      case 4:
        intelligence -= points;
        break;
      case 5:
        agility -= points;
        break;
      default:
        luck -= points;
        break;
    }
    statPoints += points;
  }

  /**
 * Gets stat name string depending on given byte.

 * @param i byte for SPECIAL stat name
 * @return name of stat as String
 */
  public String getStatName(byte i) {
    String statName;
    if (i == 0) {
      statName = "Stength";
    } else if (i == 1) {
      statName = "Perception";
    } else if (i == 2) {
      statName = "Endurance";
    } else if (i == 3) {
      statName = "Charisma";
    } else if (i == 4) {
      statName = "Intelligence";
    } else if (i == 5) {
      statName = "Agility";
    } else if (i == 6) {
      statName = "Luck";
    } else if (i == 7) {
      statName = "Stat Points";
    } else { // (0 !< i !< 7) {
      statName = "Money";
    }
    return statName;
  }

  /**
 * Level up check.
 */
  public void levelUp() {
    if (exp >= (double) (10) && level < LEVEL_CAP) {
      System.out.println(this.name + " has leveled up!");
      exp = 0.00;
      level++;
      levelUpStats();
      hp += 51 + 3 * Main.RAND.nextInt(getLuck());
      System.out.printf(this.name + "\'s HP is now at %d!\n", this.hp);
    }
  }

  /**
 * Adds 1 point to stat for each stat.
 */
  public void levelUpStats() {
    for (byte i = 0; i <= 6; i++) {
      byte stat = (byte) Main.RAND.nextInt(2);
      addStatPoints(stat, i);
      if (stat >= 1) {
        System.out.println(
            this.name + "\'s " + getSpecialSelection(i) + " stat has" + "increased by 1!");
      }
    }
  }

  /**
 * Returns message for char stats.

 * @return string
 */
  public String printCharStats() {
    String message =
        "%s has the following stats:%n"
            + "Level: %d%n"
            + "Exp: %d%n"
            + "Strength: %d%n"
            + "Perception: %d%n"
            + "Endurance: %d%n"
            + "Charisma: %d%n"
            + "Intelligence: %d%n"
            + "Agility: %d%n"
            + "Luck: %d%n";
    return message;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getHp() {
    return hp;
  }

  public void setName(String nam) {
    name = nam;
  }

  public String getName() {
    return name;
  }

  public void setWeapon(String weap) {
    weapon = weap;
  }

  public String getWeapon() {
    return weapon;
  }

  public void setType(char typ) {
    type = typ;
  }

  public char getType() {
    return type;
  }

  public void setExp(double e) {
    exp = e;
  }

  public double getExp() {
    return exp;
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
    return money; // Foie gras bust of Albert Einstein
  }

  public byte getStatPoints() {
    return statPoints;
  }

  public void setLevel(byte lvl) {
    level = lvl;
  }

  public byte getLevel() {
    return level;
  }

  public void setAlive(boolean a) {
    alive = a;
  }

  public boolean getAlive() {
    return alive;
  }

  /**
 * sets SPECIAL stat, used with for loops.

 * @param i given stat's position in SPECIAL
 * @param stat given stat to set
 */
  public void setStat(byte i, byte stat) {
    switch (i) {
      case 0:
        setStrength(stat);
        break;
      case 1:
        setPerception(stat);
        break;
      case 2:
        setEndurance(stat);
        break;
      case 3:
        setCharisma(stat);
        break;
      case 4:
        setIntelligence(stat);
        break;
      case 5:
        setAgility(stat);
        break;
      case 6:
        setLuck(stat);
        break;
      default:
        setLevel(stat);
        break;
    }
  }


  /**
 * gets stat depending on byte, used for reading char stats.

 * @param i byte
 * @return SPECIAL stat amount + extras
 */
  public byte getStat(byte i) {
    byte stat;
    switch (i) {
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
      case 7:
        stat = getStatPoints();
        break;
      default:
        stat = getLevel();
        break;
    }
    return stat;
  }

  /**
 * gets stat depending on int, used for array.

 * @param i int
 * @return SPECIAL stats
 */
  public int getStat(int i) {
    int stat;
    switch (i) {
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
      case 7:
        stat = getHp();
        break;
      default:
        stat = getLevel();
        break;
    }
    return stat;
  }
 
  
  /**
 * gets stat depending on given string. unused.

 * @param s wanted attribute
 * @return value in attribute
 */
  public String getStat(String s) {
    String info;
    switch (s) {
      case "name":
        info = getName();
        break;
      case "weapon":
        info = getWeapon();
        break;
      default:
        info = "Unknown String called in getStat!";
        break;
    }
    return info;
  }

  /**
 * gets object type.

 * @param c object type
 * @return name of object type
 */
  public String getStat(char c) { // gets char type
    String info;
    switch (c) {
      case 'c':
        info = "Character";
        break;
      case 'e':
        info = "Enemy";
        break;
      case 'p':
        info = "Pal";
        break;
      case 'b':
        info = "Boss";
        break;
      default:
        info = "Unknown char called in getStat!";
    }
    return info;
  }

  /**
 * gets name of SPECIAL stat, pretty much the same thing as main.

 * @param selection byte for SPECIAL stat's location
 * @return name of stat as String.
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
