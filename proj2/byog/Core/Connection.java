package byog.Core;


/**
 * a connection between two areas.
 */
public class Connection implements Comparable<Connection> {
    private Area a;
    private Area b;
    private int distance;

    public Connection(Area a, Area b) {
        this.a = a;
        this.b = b;
        this.distance = calcDistance(a, b);
    }

    public static int calcDistance(Area a, Area b) {
        Area aX = new Area(a.width(), 1, a.downLeft());
        Area bX = new Area(b.width(), 1, new Position(b.downLeft().getX(), a.downLeft().getY()));
        if (aX.intersects(bX)) {
            if (a.downLeft().getY() < b.downLeft().getY()) {
                return b.downLeft().getY() - a.downLeft().getY()- a.height();
            } else {
                return a.downLeft().getY() - b.downLeft().getY()- b.height();
            }
        }
        Area aY = new Area(1, a.height(), a.downLeft());
        Area bY = new Area(1, b.height(), new Position(a.downLeft().getX(), b.downLeft().getY()));
        if (aY.intersects(bY)) {
            if (a.downLeft().getX() < b.downLeft().getX()) {
                return b.downLeft().getX() - a.downLeft().getX() - a.width();
            } else {
                return a.downLeft().getX() - b.downLeft().getX() - b.width();
            }
        }
        return 2;
    }

    public boolean equals(Connection c) {
        return (a.equals(c.a) && b.equals(c.b)) ||
                (a.equals(c.b) && b.equals(c.a));
    }

    @Override
    public int compareTo(Connection c) {
        return distance - c.distance;
    }
}