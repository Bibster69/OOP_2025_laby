public class TransformationDecorator extends ShapeDecorator {
    private String transform;
    public TransformationDecorator(Shape shape, String transform) {
        super(shape);
        this.transform = transform;
    }


    @Override
    public String toSvg(String param) {
        return this.decoratedShape.toSvg(String.format("transform=\"%s\" %s", this.transform, param));
    }

    /*
    Builder to wzorzec który umożliwia nam zbudowanie danego obiektu, w naszym przypadku Shape
    dostosowując przy tym różne jego cech. Każda metoda buildera, poza build dodaje nową cechę do naszego docelowego obiektu,
    podobnie jak dekorator, a nawet sensowniej by było umieścić nasze poprzednie dekoratory w builderze!
    Morzemy to porównać do składania zamówienia na jakiś samochód, wybiaramy sobie po kolei silnik, skrzynie biegów
    wyposarzenie wnętrza, kolor i różne szmery bajery, po czym na koniec budujemy gotowy produkt!
     */


    public static class Builder {
        private String transform = "";

        public Builder translate(Vec2 translation) {
            this.transform += String.format("translate(%f %f) ", translation.x(), translation.y());
            return this;
        }

        public Builder rotate(float angle, Vec2 center) {
            this.transform += String.format("rotate(%f %f %f) ", angle, center.x(), center.y());
            return this;
        }

        public Builder scale(Vec2 scaleFactor) {
            this.transform += String.format("scale(%f %f) ", scaleFactor.x(), scaleFactor.y());
            return this;
        }

        public TransformationDecorator build(Shape shape) {
            return new TransformationDecorator(shape, this.transform);
        }

    }

}
