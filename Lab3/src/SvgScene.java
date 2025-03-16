import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private final Shape[] shapes;
    private int currentReplacementIndex;

    public SvgScene() {
        this.shapes = new Shape[3];
        this.currentReplacementIndex = 0;
    }

    public Shape[] getShapes() {
        return this.shapes;
    }

    public void addShape(Shape s) {
        for (int i = 0; i < this.shapes.length; i++) {
            if (this.shapes[i] == null) {
                this.shapes[i] = s;
                return;
            }
        }
        shapes[this.currentReplacementIndex] = s;
        this.currentReplacementIndex++;
        if (this.currentReplacementIndex == this.shapes.length) {
            this.currentReplacementIndex = 0;
        }
    }

    public String toSvg() {
        StringBuilder result = new StringBuilder();
        for (Shape s : this.shapes) {
            if (s != null) {
                result.append(s.toSvg(0, 0)).append("\n");
            }
        }
        return result.toString();
    }

    public BoundingBox boundingBox() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (Shape s : this.shapes) {
            if (s instanceof Polygon poly) {
                for (Point p : poly.getPoints()) {
                    minX = Math.min(minX, p.getX());
                    minY = Math.min(minY, p.getY());
                    maxX = Math.max(maxX, p.getX());
                    maxY = Math.max(maxY, p.getY());
                }
            } else if (s instanceof Ellipse ellipse) {
                minX = Math.min(minX, ellipse.getCenter().getX() - ellipse.getrX());
                minY = Math.min(minY, ellipse.getCenter().getY() - ellipse.getrY());
                maxX = Math.max(maxX, ellipse.getCenter().getX() + ellipse.getrX());
                maxY = Math.max(maxY, ellipse.getCenter().getY() + ellipse.getrY());
            }
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    public void save(String filePath) {
        BoundingBox box = this.boundingBox();

        if (box == null) {
            System.out.println("No shapes to save.");
            return;
        }

        double width = box.width();
        double height = box.height();

        StringBuilder svgContent = new StringBuilder();
        svgContent.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%.2f\" height=\"%.2f\" viewBox=\"0 0 %.2f %.2f\">\n",
                width, height, width, height
        ));

        for (Shape s : this.shapes) {
            if (s != null) {
                svgContent.append(s.toSvg(0, 0)).append("\n");
            }
        }

        svgContent.append("</svg>");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(svgContent.toString());
            System.out.println("SVG file saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving SVG file: " + e.getMessage());
        }
    }
}
