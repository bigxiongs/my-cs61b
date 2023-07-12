package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.In;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final String SAVE_FILE = "./save.txt";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        input = input.toLowerCase();
        Character mainOption = input.charAt(0);
        switch (mainOption) {
            case 'n': {
                return newGame(input.substring(1));
            }
            case 'l': {
                return loadGame(input.substring(1));
            }
            case 'q': {
                return null;
            }
            default: {
                throw new IllegalArgumentException("Main Menu option false, "
                        + mainOption + " given");
            }
        }
    }

    /**
     * Method used for parse user input seed, generate a world with it. Call
     * play() on the remaining input.
     * @param input user input, beginning with a seed
     * @return the worldState after operate on it
     */
    private TETile[][] newGame(String input) {

        // Parse Seed
        int del = input.indexOf('s');
        String seedString = input.substring(0, del);
        String operation = input.substring((del + 1));
        long seed = Long.parseLong(seedString);

        // Generate a random world with the given seed
        World world = World.generateWorld(seed);

        // Play game
        play(world, operation);
        return world.worldState();
    }

    /**
     * Method used to retrieve the saved file, load that to generate a world,
     * and call play() on the give input.
     * @param input user input of operation
     * @return the worldState after play with it
     */
    private TETile[][] loadGame(String input) {
        try {
            Scanner in = new Scanner(Path.of(SAVE_FILE));
            String save = in.nextLine();
            return newGame(save + input);
        } catch (IOException e) {
            throw new RuntimeException("Can not load game, no save file found");
        }
    }

    public void play(World world, String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
                i++;
                if (s.charAt(i) == 'q') {
                    saveState(world);
                    return;
                } else {
                    throw new IllegalArgumentException("quit on save fail.");
                }
            } else {
//                world.movePlayer(s.charAt(i));
            }
        }
    }

    private void saveState(World world) {
        try {
            PrintWriter out = new PrintWriter(SAVE_FILE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Save file not found");
        }
    }
}
