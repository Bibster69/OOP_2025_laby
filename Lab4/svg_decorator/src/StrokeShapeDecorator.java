public class StrokeShapeDecorator extends ShapeDecorator {
    private String color;
    private double width;

    public StrokeShapeDecorator(Shape shape, String color, double width) {
        super(shape);
        this.color = color;
        this.width = width;
    }

    @Override
    public String toSvg(String param){
        String formattedAttributes = String.format("stroke=\"%s\" stroke-width=\"%f\" %s", this.color, this.width, param);
        return this.decoratedShape.toSvg(formattedAttributes);
    }

}
