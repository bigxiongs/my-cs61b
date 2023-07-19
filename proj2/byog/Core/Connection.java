package byog.Core;

import java.util.Objects;

public class Connection {
    private final Rectangle from;
    private final Rectangle to;
    private final Line line;

    public Rectangle getFrom() {
        return from;
    }

    public Rectangle getTo() {
        return to;
    }

    public Line getLine() {
        return line;
    }

    public Connection(Rectangle from, Rectangle to, Line line) {
        this.from = from;
        this.to = to;
        this.line = line;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Connection cn = (Connection) obj;
            return from.equals(cn.from) && to.equals(cn.to) && line.equals(cn.line);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, line);
    }
}
