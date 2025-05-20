
package InventoryMgr.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class PieGenerator {

    public static Font merriweather = InvStatic.merriweather;

    public static BufferedImage generatePieChart(Map<String, Double> data, int width, int height) {
        BufferedImage chartImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = chartImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        int diameter = Math.min(width, height) - 60;
        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;

        Color[] colors = {
                Color.RED,
                Color.PINK,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA
        };

        int colorIndex = 0;
        double startAngle = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            double angle = value * 360 / total;
            double sliceAngle = (int) Math.ceil(angle);
            double midAngle = (sliceAngle + 2 * startAngle) / 2.0;
            double radius = diameter / 2.0;
            g.setColor(colors[colorIndex % colors.length]);
            g.fillArc(x, y, diameter, diameter, (int) startAngle, (int) sliceAngle);

            double r = radius / 1.2;
            double centerX = (double) width / 2;
            double centerY = (double) height / 2;
            if (midAngle < 90) {
                double midAngleRad = Math.toRadians(midAngle);
                centerX += r * Math.cos(midAngleRad);
                centerY -= r * Math.sin(midAngleRad);
            } else if (midAngle < 180) {
                midAngle = 180 - midAngle;
                double midAngleRad = Math.toRadians(midAngle);
                centerX -= r * Math.cos(midAngleRad);
                centerY -= r * Math.sin(midAngleRad);
            } else if (midAngle < 270) {
                midAngle -= 180;
                double midAngleRad = Math.toRadians(midAngle);
                centerX -= r * Math.cos(midAngleRad);
                centerY += r * Math.sin(midAngleRad);
            } else {
                midAngle = 360 - midAngle;
                double midAngleRad = Math.toRadians(midAngle);
                centerX += r * Math.cos(midAngleRad);
                centerY += r * Math.sin(midAngleRad);
            }
//            System.out.println(centerX + "," + centerY);

            g.setColor(Color.BLACK);
            String label = String.format("%s (%.1f%%)", entry.getKey(), (value / total) * 100);
            g.setFont(merriweather.deriveFont(Font.BOLD, 12));
            FontMetrics fm = g.getFontMetrics();
            int lineHeight = fm.getHeight() - fm.getAscent();
            int lineWidth = fm.stringWidth(label);
            g.drawString(label, (int) centerX - lineWidth / 2, (int) centerY + lineHeight / 2);

            startAngle += angle;
            colorIndex++;
        }

        g.dispose();
        return chartImage;
    }

    public static JLabel createChartLabel(Map<String, Double> data, int width, int height) {
        BufferedImage image = generatePieChart(data, width, height);
        return new JLabel(new ImageIcon(image));
    }
}
