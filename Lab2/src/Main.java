public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(11,12);
        Point p2 = new Point(21,22);
        Point p3 = new Point(31,32);

        Point[] points = {p1,p2,p3};

        Polygon polygon1 = new Polygon(points);
        System.out.println(polygon1);
    }
}