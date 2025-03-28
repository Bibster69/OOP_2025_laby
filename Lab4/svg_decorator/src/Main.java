import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Polygon triangle = new Polygon(new Vec2[]{
                new Vec2(0, 0),
                new Vec2(300, 0),
                new Vec2(150, 250)
        });

        Polygon rectangle = new Polygon(new Vec2[]{
                new Vec2(350, 0),
                new Vec2(750, 0),
                new Vec2(750, 200),
                new Vec2(350, 200)
        });

        Polygon pentagon = new Polygon(new Vec2[]{
                new Vec2(0, 260),
                new Vec2(100, 460),
                new Vec2(300, 560),
                new Vec2(500, 460),
                new Vec2(600, 260)
        });

        Ellipse ellipse = new Ellipse(new Vec2(500, 700), 400, 100);
        SolidFillShapeDecorator filledPentagon = new SolidFillShapeDecorator(pentagon, "red");
        StrokeShapeDecorator strokeFilledPentagon = new StrokeShapeDecorator(filledPentagon, "blue", 5.0);

        SolidFillShapeDecorator filledEllipse = new SolidFillShapeDecorator(ellipse, "blue");
        StrokeShapeDecorator strokeFilledEllipse = new StrokeShapeDecorator(filledEllipse, "red", 5.0);

        TransformationDecorator transformedStrokeFilledPentagon = new TransformationDecorator.Builder()
                .translate(new Vec2(10, 20))
                .rotate(45, new Vec2(50, 50))
                .scale(new Vec2(1.5, 1.5))
                .build(strokeFilledPentagon);


        SvgScene scene = new SvgScene();
        scene.addShape(triangle);
        scene.addShape(rectangle);
        scene.addShape(transformedStrokeFilledPentagon);
        scene.addShape(strokeFilledEllipse);
        scene.save("result.svg");

    }
}
