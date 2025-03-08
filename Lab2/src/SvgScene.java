import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private final Polygon[] polygons;
    private int currentReplacementIndex;

    public SvgScene() {
        this.polygons = new Polygon[3];
        this.currentReplacementIndex = 0;
    }

    public Polygon[] getPolygons() {
        return this.polygons;
    }

    public void addPolygon(Polygon p) {
        for (int i = 0; i < this.polygons.length; i++) {
            if (this.polygons[i] == null) {
                this.polygons[i] = p;
                return;
            }
        }
        polygons[this.currentReplacementIndex] = p;
        this.currentReplacementIndex++;
        if (this.currentReplacementIndex == this.polygons.length) {
            this.currentReplacementIndex = 0;
        }
    }

    public String toSvg() {
        String result = "";
        for (Polygon p : this.polygons) {
            result += p.toSvg(0,0);
            result += "\n";
        }
        return result;
    }

    public BoundingBox boundingBox() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (Polygon poly : this.polygons) {
            if (poly == null) continue;
            for (Point p : poly.getPoints()) {
                minX = Math.min(minX, p.getX());
                minY = Math.min(minY, p.getY());
                maxX = Math.max(maxX, p.getX());
                maxY = Math.max(maxY, p.getY());
            }
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }


    public void save(String filePath) {
        BoundingBox box = this.boundingBox();

        if (box == null) {
            System.out.println("No polygons to save.");
            return;
        }

        double offsetX = box.x();
        double offsetY = box.y();
        double width = box.width();
        double height = box.height();

        StringBuilder svgContent = new StringBuilder();
        svgContent.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%.2f\" height=\"%.2f\" viewBox=\"0 0 %.2f %.2f\">\n",
                width, height, width, height
        ));

        for (Polygon poly : this.polygons) {
            if (poly == null) continue;
            svgContent.append(poly.toSvg(offsetX, offsetY)).append("\n");
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
