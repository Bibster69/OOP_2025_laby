public class Polygon {
    private Point[] points;

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Polygon(Point[] points) {
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
    }

    public Polygon(Polygon other) {
        Point[] otherPoints = other.getPoints();
        this.points = new Point[otherPoints.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(otherPoints[i].getX(), otherPoints[i].getY());
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.points.length; i++)  {
            result += "Punkt nr. " + (i + 1) + "\n" + this.points[i] + "\n";
        }

        return result;
    }

    public String toSvg(double offsetX, double offsetY) {
        String result = "<polygon points=\"";

        for (int i = 0; i < points.length; i++) {
            result += (points[i].getX() + offsetX) + "," + (points[i].getY() + offsetY);
            if (i < points.length - 1) {
                result += " ";
            }
        }
        result += "\" style=\"fill:none;stroke:black;stroke-width:1\" />";
        return result;
    }


}
