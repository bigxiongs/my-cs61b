package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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

        World world = new World(WIDTH, HEIGHT);
        List<Consumer<World>> commands = Parser.parse(input.toUpperCase());
        for (Consumer<World> c : commands) {
            c.accept(world);
        }
        return world.getState();
    }
}

class Parser {
    private static final Consumer<World> MOVE_LEFT = world -> world.movePlayer(DIRECTION.WEST);
    private static final Consumer<World> MOVE_RIGHT = world -> world.movePlayer(DIRECTION.EAST);
    private static final Consumer<World> MOVE_UP = world -> world.movePlayer(DIRECTION.NORTH);
    private static final Consumer<World> MOVE_DOWN = world -> world.movePlayer(DIRECTION.SOUTH);
    private static final Consumer<World> SAVE = world -> {
        File f = new File(Game.SAVE_FILE);
        try {
            f.createNewFile();
            PrintWriter out = new PrintWriter(f);
            out.print(world.save());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    private static final Consumer<World> LOAD = world -> {
        File f = new File(Game.SAVE_FILE);
        try {
            if (!f.exists()) {
                throw new RuntimeException();
            }
            Scanner scn = new Scanner(f);
            String s = scn.nextLine();
            scn.close();
            List<Consumer<World>> cmds = parse(s);
            for (var cmd: cmds) {
                cmd.accept(world);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    };

    /**
     * Parse the input and return a list of consumers which take in a world
     * and apply that consumer/command to the world.
     * There are 4 kinds of commands:
     * N###S   : generate a random world with `###` as the seed
     * L       : load the last saved world
     * A/S/D/W : move the player in the world
     * :Q      : save the state of the world and quit
     * @return a list of consumers
     */
    public static List<Consumer<World>> parse(String input) {
        List<Consumer<World>> commands = new LinkedList<>();
        switch (input.charAt(0)) {
            case 'N': {
                int sPos = input.indexOf('S');
                if (sPos < 0) throw new RuntimeException();
                long seed = Long.parseLong(input.substring(1, sPos));
                Consumer<World> command = (world) -> world.generate(seed);
                commands.add(command);
                parse(input.substring(sPos + 1), commands);
                break;
            }
            case 'L': {
                commands.add(LOAD);
                parse(input.substring(1), commands);
                break;
            }
            case 'Q': {
                break;
            }
            default: throw new RuntimeException();
        }
        return commands;
    }

    private static void parse(String input, List<Consumer<World>> commands) {
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case 'A': {
                    commands.add(MOVE_LEFT);
                    break;
                }
                case 'S': {
                    commands.add(MOVE_DOWN);
                    break;
                }
                case 'D': {
                    commands.add(MOVE_RIGHT);
                    break;
                }
                case 'W': {
                    commands.add(MOVE_UP);
                    break;
                }
                case ':': {
                    if (i + 1 < input.length() && input.charAt(i + 1) == 'Q') {
                        commands.add(SAVE);
                        break;
                    } else {
                        throw new RuntimeException();
                    }
                }
                default: throw new RuntimeException();
            }
        }
    }
}