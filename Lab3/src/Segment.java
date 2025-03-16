public class Segment {
    private Point start;
    private Point end;

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

//    Poprzez stworzenie NOWYCH OBIEKTÓW a nie przekazywanie REFERENCJI obiekt
//    Segment jest niewrażliwy na zmiany pynktów z których został stworzony

    public Segment(Point start, Point end) {
        this.start = new Point(start);
        this.end = new Point(end);
    }

//    Konstruktor kopiujący

    public Segment(Segment old) {
        this.start = new Point(old.getStart().getX(), old.getStart().getY());
        this.end = new Point(old.getEnd().getX(), old.getEnd().getY());

    }

    public double length(){
        return Math.sqrt(Math.pow(this.end.getX() - this.start.getX(), 2) + Math.pow(this.end.getY() - this.start.getY(), 2));
    }

    @Override
    public String toString(){
        return "Punkt start odcinka: " + this.start + "\nPunkt end odcinka: " + this.end;
    }

    public Segment maxSegment(Segment[] segments){
        int max_len = 0;
        Segment max_segment = segments[0];
        for(Segment s : segments){
            if (s.length() > max_len){
                max_segment = s;
            }
        }
        return max_segment;
    }

    public Segment perpendicular() {
        // Obliczamy kierunek oryginalnego segmentu
        double dx = this.end.getX() - this.start.getX();
        double dy = this.end.getY() - this.start.getY();

        // Obliczamy segment prostopadły
        double perpendicularDx = -dy;
        double perpendicularDy = dx;

        // Normalizujemy prostopoadły segment (ma długość 1)
        double perpendicularLength = Math.sqrt(perpendicularDx * perpendicularDx + perpendicularDy * perpendicularDy);
        perpendicularDx /= perpendicularLength;
        perpendicularDy /= perpendicularLength;


        // Obliczamy środkowy punkt obecnego segmentu
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;

        // Tworzymy nowy segment
        Point newStart = new Point(midX + perpendicularDx, midY + perpendicularDy);
        Point newEnd = new Point(midX - perpendicularDx, midY - perpendicularDy);

        // Zwracamy prostopadły segment
        return new Segment(newStart, newEnd);
    }

    public Segment perpendicular(double length) {
        // Obliczamy kierunek oryginalnego segmentu
        double dx = this.end.getX() - this.start.getX();
        double dy = this.end.getY() - this.start.getY();

        // Obliczamy segment prostopadły
        double perpendicularDx = -dy;
        double perpendicularDy = dx;

        // Normalizujemy prostopoadły segment (ma długość 1)
        double perpendicularLength = Math.sqrt(perpendicularDx * perpendicularDx + perpendicularDy * perpendicularDy);
        perpendicularDx /= perpendicularLength;
        perpendicularDy /= perpendicularLength;

        perpendicularDx *= length;
        perpendicularDy *= length;

        // Obliczamy środkowy punkt obecnego segmentu
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;

        // Tworzymy nowy segment
        Point newStart = new Point(midX + perpendicularDx, midY + perpendicularDy);
        Point newEnd = new Point(midX - perpendicularDx, midY - perpendicularDy);

        // Zwracamy prostopadły segment
        return new Segment(newStart, newEnd);
    }

}
