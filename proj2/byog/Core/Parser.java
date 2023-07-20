package byog.Core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Parser {
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
            for (var cmd : cmds) {
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
     *
     * @return a list of consumers
     */
    public static List<Consumer<World>> parse(String input) {
        List<Consumer<World>> commands = new LinkedList<>();
        switch (input.charAt(0)) {
            case 'N': {
                int sPos = input.indexOf('S');
                if (sPos < 0) {
                    throw new RuntimeException();
                }
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
            default:
                throw new RuntimeException();
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
                        return;
                    } else {
                        throw new RuntimeException();
                    }
                }
                default:
                    throw new RuntimeException();
            }
        }
    }
}
