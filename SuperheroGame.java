import java.util.Scanner;

/**
 * SuperheroGame is a text-based adventure game where the player controls a superhero,
 * Captain Nova, who must save the city by finding and disarming a bomb planted by Dr. Chaos.
 * The game involves using various superpowers to accomplish tasks and ultimately save the city.
 */

public class SuperheroGame implements Contract {
    // Game state variables
    private boolean bombFound;
    private boolean bombDisarmed;
    private boolean citySaved;
    private String lastAction;

    /**
     * Constructor for SuperheroGame. Initializes the game state.
     */

    public SuperheroGame() {
        this.bombFound = false;
        this.bombDisarmed = false;
        this.citySaved = false;
        this.lastAction = "";
    }

     /**
     * Grabs an item.
     * @param item The item to grab.
     */
    @Override
    public void grab(String item) {
        if (item.equalsIgnoreCase("bomb")) {
            if (bombFound) {
                System.out.println("You grab the bomb carefully. Time to disarm it!");
                lastAction = "grabbed the bomb";
            } else {
                System.out.println("You can't grab what you haven't found!");
            }
        } else {
            System.out.println("You don’t need to grab the " + item + " right now.");
        }
    }

    /**
     * Drops an item.
     * @param item The item to drop.
     * @return The item that was dropped.
     */
    @Override
    public String drop(String item) {
        if (item.equalsIgnoreCase("bomb") && bombDisarmed) {
            System.out.println("You safely drop the disarmed bomb in a containment unit.");
            lastAction = "dropped the bomb";
            return item;
        } else {
            System.out.println("You can't drop that right now!");
            return null;
        }
    }

     /**
     * Examines an item to gather information about it.
     * @param item The item to examine.
     */
    @Override
    public void examine(String item) {
        if (item.equalsIgnoreCase("bomb")) {
            if (bombFound) {
                System.out.println("The bomb is complex, with wires in various colors: red, blue, and yellow.");
            } else {
                System.out.println("You haven’t found the bomb yet!");
            }
        } else if (item.equalsIgnoreCase("clue")) {
            System.out.println("The clue reads: 'Where light meets the sky, the threat hides nearby.'");
        } else {
            System.out.println("There’s nothing unusual about the " + item + ".");
        }
        lastAction = "examined " + item;
    }

     /**
     * Uses a superpower or ability.
     * @param item The item or power to use.
     */
    @Override
    public void use(String item) {
        if (item.equalsIgnoreCase("x-ray vision")) {
            System.out.println("You activate your X-ray vision and scan the city...");
            if (!bombFound) {
                System.out.println("You find the bomb hidden in the rooftop garden of City Tower!");
                bombFound = true;
            } else {
                System.out.println("You’ve already found the bomb.");
            }
        } else if (item.equalsIgnoreCase("super speed")) {
            if (bombFound && !bombDisarmed) {
                System.out.println("You use super speed to disarm the bomb, cutting the blue wire just in time!");
                bombDisarmed = true;
            } else {
                System.out.println("There’s nothing urgent to disarm right now.");
            }
        } else if (item.equalsIgnoreCase("healing powers")) {
            if (bombDisarmed) {
                System.out.println("You use your healing powers to restore damage caused by the chaos!");
                citySaved = true;
            } else {
                System.out.println("You can’t heal the city while the bomb is still active!");
            }
        } else {
            System.out.println("You don’t have a superpower like " + item + ".");
        }
        lastAction = "used " + item;
    }

    /**
     * Walks in a specified direction.
     * @param direction The direction to walk.
     * @return True if the direction was valid, false otherwise.
     */

    @Override
    public boolean walk(String direction) {
        if (direction.equalsIgnoreCase("north")) {
            System.out.println("You fly north towards City Tower.");
            lastAction = "walked north";
            return true;
        } else {
            System.out.println("You fly " + direction + " but find nothing of interest.");
            return false;
        }
    }

     /**
     * Flies to specified coordinates in search of danger.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True, as flying to coordinates always works.
     */
    @Override
    public boolean fly(int x, int y) {
        System.out.println("You soar to the coordinates (" + x + ", " + y + ") in search of danger.");
        lastAction = "flew to coordinates (" + x + ", " + y + ")";
        return true;
    }

    /**
     * Shrinks the superhero to a tiny size for navigating small spaces.
     * @return The size after shrinking.
     */
    @Override
    public Number shrink() {
        System.out.println("You shrink down to a tiny size to navigate small spaces.");
        lastAction = "shrunk";
        return 0.1;
    }

    /**
     * Grows the superhero to a towering size for better visibility.
     * @return The size after growing.
     */
    @Override
    public Number grow() {
        System.out.println("You grow to a towering size, scanning the city from above.");
        lastAction = "grew";
        return 10.0;
    }

     /**
     * Takes a rest to regain strength.
     */
    @Override
    public void rest() {
        System.out.println("You take a moment to rest and regain your strength.");
        lastAction = "rested";
    }

     /**
     * Undoes the last action performed by the superhero.
     */
    @Override
    public void undo() {
        System.out.println("You undo your last action: " + lastAction);
        lastAction = "undid " + lastAction;
    }

     /**
     * Starts the game and takes input commands from the player.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome, Captain Nova! The city is in danger. Stop Dr. Chaos’s bomb to save the day.");

        while (!citySaved) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] command = input.split(" ", 2);

            switch (command[0].toLowerCase()) {
                case "grab":
                    if (command.length > 1) grab(command[1]);
                    else System.out.println("Specify what to grab.");
                    break;
                case "drop":
                    if (command.length > 1) drop(command[1]);
                    else System.out.println("Specify what to drop.");
                    break;
                case "examine":
                    if (command.length > 1) examine(command[1]);
                    else System.out.println("Specify what to examine.");
                    break;
                case "use":
                    if (command.length > 1) use(command[1]);
                    else System.out.println("Specify what to use.");
                    break;
                case "walk":
                    if (command.length > 1) walk(command[1]);
                    else System.out.println("Specify a direction to walk.");
                    break;
                case "fly":
                    if (command.length > 1) {
                        String[] coords = command[1].split(",");
                        if (coords.length == 2) {
                            int x = Integer.parseInt(coords[0].trim());
                            int y = Integer.parseInt(coords[1].trim());
                            fly(x, y);
                        } else {
                            System.out.println("Specify coordinates in the format 'x, y'.");
                        }
                    } else {
                        System.out.println("Specify coordinates to fly to.");
                    }
                    break;
                case "shrink":
                    shrink();
                    break;
                case "grow":
                    grow();
                    break;
                case "rest":
                    rest();
                    break;
                case "undo":
                    undo();
                    break;
                default:
                    System.out.println("Unknown command. Try 'grab', 'drop', 'examine', 'use', 'walk', 'fly', 'shrink', 'grow', 'rest', or 'undo'.");
                    break;
            }
        }

        System.out.println("Congratulations, Captain Nova! You’ve saved the city!");
        scanner.close();
    }

    /**
     * Main method to start the game.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SuperheroGame game = new SuperheroGame();
        game.play();
    }
}
