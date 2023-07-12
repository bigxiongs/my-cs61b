package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {
    private final int width;
    private final int height;
    private final long seed;
    private final WorldGenerator generator;

    public World(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.generator = new WorldGenerator(seed);
    }

    public static World generateWorld(int width, int height, long seed) {
        World world = new World(width, height, seed);
        world.generator.generateWorld();
        return world;
    }

    public static World generateWorld(long seed) {
        return generateWorld(Game.WIDTH, Game.HEIGHT, seed);
    }

    public TETile[][] worldState() {
        return generator.worldState();
    }

    private class WorldGenerator {
        private final TETile[][] state = new TETile[World.this.width][World.this.height];
        private Position player;
        private static final int MAX_ROOM_NUM = 20;
        private Random rand;
        private static final Area[] ROOM_PATTERN = {
                new Area(2, 3), new Area(2, 4),
                new Area(2, 5), new Area(2, 6),
                new Area(3, 3), new Area(3, 4),
                new Area(3, 5), new Area(3, 6),
                new Area(3, 7), new Area(3, 8),
                new Area(3, 9), new Area(4, 4),
                new Area(4, 5), new Area(4, 6),
                new Area(4, 7), new Area(4, 8),
                new Area(4, 9), new Area(5, 5),
                new Area(5, 6), new Area(5, 7),
                new Area(6, 6)};

        public WorldGenerator(long seed) {
            this.rand = new Random(seed);
        }

        /**
         * Generate a world with rooms, hallways connecting them, and a player.
         */
        public void generateWorld() {
            Area[] rooms = generateRoom();
            Connection[] hallways = generateHallway(rooms);
            fill(state, Tileset.NOTHING);
            fill(state, Tileset.FLOOR, rooms);
            player = generatePlayer(rooms);
        }

        /**
         * Generate MAX_ROOM_NUM random rooms according to the predefined room
         * pattern above. Discard it if any intersection with previously defined
         * rooms found.
         * @return an array of rooms
         */
        private Area[] generateRoom() {
            Set<Area> roomSet = new HashSet<>();
            for (int i = 0; i < MAX_ROOM_NUM; i++) {
                Area room = randomRoom();
                boolean valid = true;
                for (Area rm : roomSet) {
                    if (room.intersects(rm)) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    roomSet.add(room);
                }
            }
            Area[] rooms = roomSet.toArray(new Area[0]);
            return rooms;
        }

        private Area randomRoom() {
            Area room = ROOM_PATTERN[rand.nextInt(ROOM_PATTERN.length)];
            Position p = new Position(
                    rand.nextInt(1, World.this.width - room.width() - 1),
                    rand.nextInt(1, World.this.height - room.height() - 1));
            return new Area(room.width(), room.height(), p);
        }

        /**
         * Generate room.length-1 hallways with Prim algorithm.
         * Using UFSet to check connection among rooms.
         * @return an array of hallways
         */
        private Connection[] generateHallway(Area[] rooms) {
            //TODO: generate hallways using Prim algorithm, viewing rooms as vertices
            //and hallways as edges.
            Set<Connection> hallways = new HashSet<>();
            return hallways.toArray(new Connection[0]);
        }

        /**
         * Generate the initial position of the player, which should be a random
         * position within a random room.
         * @return position of the player
         */
        private Position generatePlayer(Area[] rooms) {
            Area room = rooms[rand.nextInt(rooms.length)];
            int pos = rand.nextInt(room.size());
            Position p = new Position(room.downLeft().getX() + pos % room.width(),
                    room.downLeft().getY() + pos / room.height());
            return p;
        }

        private static void fill(TETile[][] state, TETile t) {
            Position dl = new Position(0, 0);
            Position ur = new Position(state.length, state[0].length);
            Area a = new Area(dl, ur);
            fill(state, t, a);
        }

        private static void fill(TETile[][] state, TETile t, Position... positions) {
            for (Position p: positions) {
                state[p.getX()][p.getY()] = t;
            }
        }

        private static void fill(TETile[][] state, TETile t, Area... areas) {
            for (Area a : areas) {
                fill(state, t, a);
            }
        }

        public TETile[][] worldState() {
            fill(state, Tileset.PLAYER, player);
            return state;
        }
    }
}
