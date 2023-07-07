package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static void addRow(TETile[][] world,
                               int x, int y, int s, TETile t) {
        for (int i = y; i < s; i++) {
            world[x][i] = t;
        }
    }
    public static void addHexagon(TETile[][] world,
                                  int x, int y, int s, TETile t) {
        assert s > 2 : "require size > 2 but " + s + " given.";
        assert x + 2 * s - 1 < world.length;
        assert y + 2 * s - 1 < world[0].length;
        for (int i = 0; i < s; i++) {
            addRow(world, x + i, y + s, s + i, t);
            addRow(world, x + 2 * s - 1 - i, y + s, s + i, t);
        }
    }
}
