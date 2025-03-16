class Ellipse extends Shape {
    private Point center;
    private double rX;
    private double rY;

    public Ellipse(Point center, double rX, double rY, Style style) {
        super(style);
        this.center = center;
        this.rX = rX;
        this.rY = rY;
    }

    public double getrX() {
        return rX;
    }

    public double getrY() {
        return rY;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public String toSvg(double offsetX, double offsetY) {
        return "<ellipse cx=\"" + this.center.getX() + "\" cy=\"" + this.center.getY() + "\" rx=\"" + this.rX + "\" ry=\"" + this.rY + "\" " + this.style.toSvg(0, 0) + " />";
    }
}