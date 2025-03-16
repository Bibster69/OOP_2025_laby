public abstract class Shape {
    // Chronione pole Style
    protected Style style;

    // Konstruktor ustawiający pole style
    public Shape(Style style) {
        this.style = style;
    }

    // Abstrakcyjna metoda toSvg, czymi muszą ją implementować WSZYSTKIE klasy dziedziczace po Shape
    public abstract String toSvg(double offsetX, double offsetY);

}