public class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape shape) {
        this.decoratedShape = shape;
    }

    @Override
    public BoundingBox boundingBox() {
        return decoratedShape.boundingBox();
    }

    @Override
    public String toSvg(String param) {
        return decoratedShape.toSvg(param);
    }
}
