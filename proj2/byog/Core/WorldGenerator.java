package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;


public class WorldGenerator {
    private final TETile[][] state;
    private final int width;
    private final int height;
    private Coordinate player;
    private static final int MAX_ROOM_NUM = 20;
    private static final Rectangle[] ROOM_PATTERN = Rectangle.of(
            1, 2, 1, 3, 1, 4, 1, 5,
            2, 1, 2, 2, 2, 3, 2, 4, 2, 5, 2, 6, 2, 7, 2, 8,
            3, 1, 3, 2, 3, 3, 3, 4, 3, 5, 3, 6, 3, 7, 3, 8,
            4, 1, 4, 2, 4, 3, 4, 4, 4, 5, 4, 6,
            5, 1, 5, 2, 5, 3, 5, 4, 5, 5, 5, 6, 5, 7,
            6, 2, 6, 3, 6, 4, 6, 5, 6, 6
    );

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        state = new TETile[width][height];
        fill(Tileset.NOTHING, new Rectangle(width - 1, height - 1));
    }

    public void generate(long seed) {
        Random random = new Random(seed);
        List<Rectangle> rooms = generateRooms(random);
        for (Rectangle room: rooms) {
            fill(Tileset.FLOOR, room);
            fill(Tileset.WALL, room.outline());
        }
        player = rooms.get(random.nextInt(rooms.size())).get(0);
        Set<Line> hallways = generateHallways(random, rooms);
        for (Line hallway: hallways) {
            fill(Tileset.FLOOR, hallway);
            fillNothing(Tileset.WALL, hallway.outline());
        }
    }

    public List<Rectangle> generateRooms(Random random) {
        List<Rectangle> rooms = new LinkedList<>();
        for (int i = 0; i < MAX_ROOM_NUM; i++) {
            Rectangle room = ROOM_PATTERN[random.nextInt(ROOM_PATTERN.length)];
            Rectangle available = new Rectangle(1, 1, width - room.width() - 1, height - room.height() - 1);
            room = room.moveTo(available.get(random.nextInt(available.size())));
            boolean valid = true;
            for (Rectangle rectangle: rooms) {
                if (room.intersects(rectangle) || room.near(rectangle)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                rooms.add(room);
            }
        }
        return rooms;
    }

    public Set<Line> generateHallways(Random random, List<Rectangle> rooms) {
        Set<Line> hallways = new HashSet<>();
        List<Rectangle> unconnected = rooms;
        Set<Rectangle> connected = new HashSet<>();
        PriorityQueue<Connection[]> lineCandidates = new PriorityQueue<>(rooms.size(),
                Comparator.comparingInt(c -> c[0].getLine().size()));
        Rectangle room = unconnected.remove(0);
        connected.add(room);
        while (!unconnected.isEmpty()) {
            lineCandidates.addAll(getConnections(room, unconnected));
            Connection[] candidates = lineCandidates.poll();
            Connection candidate = candidates[random.nextInt(candidates.length)];
            hallways.add(candidate.getLine());
            room = candidate.getTo();
            connected.add(room);
            unconnected.remove(room);
            Rectangle finalRoom = room;
            lineCandidates.removeIf(cdd -> cdd[0].getTo() == finalRoom);
        }
        return hallways;
    }

    public List<Connection[]> getConnections(Rectangle room, List<Rectangle> others) {
        List<Connection[]> connections = new LinkedList<>();
        for (Rectangle rec: others) {
            connections.add(room.connect(rec));
        }
        return connections;
    }

    public boolean movePlayer(DIRECTION d) {
        Coordinate c = player.towards(d);
        if (accessible(c)) {
            player = c;
            return true;
        }
        return false;
    }

    private boolean accessible(Coordinate c) {
        TETile t = state[c.getX()][c.getY()];
        if (t == Tileset.FLOOR) {
            return true;
        } else if (t == Tileset.FLOWER) {
            return true;
        } else if (t == Tileset.UNLOCKED_DOOR) {
            return true;
        } else {
            return false;
        }
    }

    public TETile[][] getState() {
        state[player.getX()][player.getY()] = Tileset.PLAYER;
        return state;
    }

    private void fill(TETile t, Face... faces) {
        for (Face face: faces) {
            Set<Coordinate> set = face.toCoordinateSet();
            for (Coordinate c: set) {
                state[c.getX()][c.getY()] = t;
            }
        }
    }

    private void fillNothing(TETile t, Set<Coordinate> coordinates) {
        for (Coordinate c: coordinates) {
            if (state[c.getX()][c.getY()] == Tileset.NOTHING) {
                state[c.getX()][c.getY()] = t;
            }
        }
    }

    private void fill(TETile t, Rectangle... rectangles) {
        for (Rectangle rectangle: rectangles) {
            for (int i = rectangle.getLeft(); i <= rectangle.getRight(); i++) {
                for (int j = rectangle.getDown(); j <= rectangle.getUp(); j++) {
                    state[i][j] = t;
                }
            }
        }
    }
}

