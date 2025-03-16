public class Polygon extends Shape{
    private Point[] points;

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Polygon(Point[] points, Style style) {
        super(style);
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
    }

    public Polygon(Point[] points) {
        this(points, new Style("none", "black", 1.0));
    }

    public Polygon(Polygon other) {
        // Tworzymy kopię głęboką więc i w super tworzymy nowy obiekt
        super(new Style(other.style.fillColor, other.style.strokeColor, other.style.strokeWidth));
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

    @Override
    public String toSvg(double offsetX, double offsetY) {
        String result = "<polygon points=\"";

        for (int i = 0; i < points.length; i++) {
            result += (points[i].getX() + offsetX) + "," + (points[i].getY() + offsetY);
            if (i < points.length - 1) {
                result += " ";
            }
        }
        result += "\" " + this.style.toSvg(0, 0) + " />";
        return result;
    }

    public static Polygon square(Segment diagonal, Style style) {
        // Tworzymy prostopadły segment do przekątnej
        Segment perpendicular = diagonal.perpendicular(diagonal.length());

        // Tablica punktów z których zrobimi kwadrat
        Point[] points = new Point[4];

        // Środek przekątnej
        double midX = (diagonal.getStart().getX() + diagonal.getEnd().getX()) / 2;
        double midY = (diagonal.getStart().getY() + diagonal.getEnd().getY()) / 2;

        // Pierwsze dwa punkty to początek i koniec przekątnej
        points[0] = diagonal.getStart();
        points[1] = diagonal.getEnd();

        // Tworzymy pozostałe dwa punkty za pomocą segmentu prostopadłego do przekątnej
        points[2] = new Point(midX + perpendicular.getStart().getX(), midY + perpendicular.getStart().getY());
        points[3] = new Point(midX + perpendicular.getEnd().getX(), midY + perpendicular.getEnd().getY());

        // Zwracamy nowy obiekt Polygon
        return new Polygon(points, style);
    }


}
