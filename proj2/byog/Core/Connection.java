package byog.Core;

public class Connection {
    public final Rectangle from;
    public final Rectangle to;
    public final Line line;

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
}
