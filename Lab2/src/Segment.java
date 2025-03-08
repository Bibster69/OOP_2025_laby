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

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Segment(Segment old) {
        this.start = new Point(old.getStart().getX(), old.getStart().getY());
        this.end = new Point(old.getEnd().getX(), old.getEnd().getY());

    }

    // kopia płytka
//    public Segment(Segment old) {
//        this.start = old.start;
//        this.end = old.end;
//
//    }

    public double length(){
        return Math.sqrt(Math.pow(this.end.getX() - this.start.getX(), 2) + Math.pow(this.end.getY() - this.start.getY(), 2));
    }

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
}
