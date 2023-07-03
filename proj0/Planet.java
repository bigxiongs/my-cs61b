public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double G = 6.67E-11;

    public Planet(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double distance = calcDistance(p);
        double force = G * mass * p.mass / Math.pow(distance, 2);
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double distance = calcDistance(p);
        double dx = p.xxPos - xxPos;
        double force = calcForceExertedBy(p);
        double forceX = force * dx / distance;
        return forceX;
    }

    public double calcForceExertedByY(Planet p) {
        double distance = calcDistance(p);
        double dy = p.yyPos - yyPos;
        double force = calcForceExertedBy(p);
        double forceY = force * dy / distance;
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0;
        for (Planet p : planets) {
            if (equals(p)) continue;
            else netForceX += calcForceExertedByX(p);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0;
        for (Planet p : planets) {
            if (equals(p)) continue;
            else netForceY += calcForceExertedByY(p);
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        double vX = xxVel + dt * aX;
        double vY = yyVel + dt * aY;
        double pX = xxPos + dt * vX;
        double pY = yyPos + dt * vY;
        xxVel = vX;
        yyVel = vY;
        xxPos = pX;
        yyPos = pY;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
