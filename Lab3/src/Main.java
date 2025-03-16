public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(11,12);
        Point p2 = new Point(21,22);
        Point p3 = new Point(31,32);

        Point[] points = {p1,p2,p3};

        Polygon polygon1 = new Polygon(points);
        Ellipse ellipse1 = new Ellipse(p1, 10, 11, new Style("none", "black", 1.0));
        SvgScene svgScene1 = new SvgScene();

        svgScene1.addShape(ellipse1);
        svgScene1.addShape(polygon1);

        System.out.println(svgScene1.toSvg());
    }
}