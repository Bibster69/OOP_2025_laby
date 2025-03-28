public class SolidFillShapeDecorator extends ShapeDecorator {
    private String color;

    public SolidFillShapeDecorator(Shape shape, String color) {
        super(shape);
        this.color = color;
    }

    @Override
    public String toSvg(String param){
        String formattedAttributes = String.format("fill=\"%s\" %s ", color, param);
        return this.decoratedShape.toSvg(formattedAttributes);
    }
}
