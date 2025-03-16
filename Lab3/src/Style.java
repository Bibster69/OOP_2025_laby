public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final double strokeWidth;

    public Style(String fillColor, String strokeColor, double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }


    public String toSvg(double offsetX, double offsetY) {
//        return String.format("style=\"fill:%s;stroke:%s;stroke-width:%.2f\"", fillColor, strokeColor, strokeWidth);
        return "style=\"fill:" + this.fillColor + ";stroke:" + this.strokeColor + ";stroke-width:" + strokeWidth + "\"";
    }

}
