public class Main {
    public static void main(String[] args) {


//        Tworzymy nowy obiekt klasy Point i ustawiamy jego publiczne pola x i y
        Point p1 = new Point();
        p1.x = 11;
        p1.y = 32;

        System.out.println("p1 x = " + p1.x + "\np1 y = " + p1.y);

        System.out.println(p1);
        System.out.println(p1.toSvg());
        Point p2 = new Point();
        p2.x = 15;
        p2.y = 12;

        Segment s1 = new Segment();
        s1.start = p1;
        s1.end = p2;
        System.out.println("Segment length = " + s1.length());

    }
}