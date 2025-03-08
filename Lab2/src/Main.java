public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Point p1 = new Point(11,32);

        System.out.println("p1 x = " + p1.getX() + "\np1 y = " + p1.getY());

        System.out.println(p1.toString());
        System.out.println(p1.toSvg());
        Point p2 = new Point(15, 12);


        Segment s1 = new Segment(p1, p2);
        System.out.println("Segment length = " + s1.length());

        Point p3 = new Point(14, 22);

        Point[] points = {p1, p2, p3};
        Polygon poly1 = new Polygon(points);

        System.out.println("\n\n\n" + poly1.toString());
        Polygon poly2 = new Polygon(poly1);
        System.out.println("\n\n\n" + poly2.toString());

    }
}