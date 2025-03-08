public class Point {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

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
