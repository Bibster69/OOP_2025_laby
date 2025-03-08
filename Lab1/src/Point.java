public class Point {
    public int x;
    public int y;

    public String toString(){
        return "Współżedna x punktu = " + x + "\nWspółżedna y punktu = " + y;
    }

    public String toSvg(){
        return "<circle cx=\"" + this.x + "\" cy=\"" + this.y + "\" r=\"5\" fill=\"black\" />";
    }

    public void translate(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

    public Point translated(int dx, int dy){
        Point result = new Point();
        result.x = this.x + dx;
        result.y = this.y + dy;
        return result;
    }
}
