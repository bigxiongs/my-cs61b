package byog.Core;

import byog.TileEngine.TETile;

public class World {
    private final StringBuilder serial;
    private final WorldGenerator generator;

    public World(int width, int height) {
        this.serial = new StringBuilder("N");
        generator = new WorldGenerator(width, height);
    }

    public void generate(long seed) {
        serial.append(seed);
        serial.append('S');
        generator.generate(seed);
    }

    public TETile[][] getState() {
        return generator.getState();
    }

    public void movePlayer(DIRECTION dir) {
        if (generator.movePlayer(dir)) {
            serial.append(dir.getAbbr());
        }
    }

    public String save() {
        return serial.toString();
    }
}
