public class Segment {
    public Point start;
    public Point end;

    public double length(){
        return Math.sqrt(Math.pow(this.end.x - this.start.x, 2) + Math.pow(this.end.y - this.start.y, 2));
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
