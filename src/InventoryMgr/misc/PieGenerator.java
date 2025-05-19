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
                new Color(209, 88, 128),
                new Color(237, 136, 172),
                new Color(209, 88, 128),
                new Color(237, 136, 172),
                new Color(209, 88, 128),
                new Color(237, 136, 172),
                new Color(209, 88, 128),
                new Color(237, 136, 172)
        };

        int colorIndex = 0;
        double startAngle = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            double angle = value * 360 / total;

            double sliceAngle = (int) Math.ceil(angle);
            double midAngle = startAngle + sliceAngle / 2.0;
            double radius = diameter / 2.0;
            double r = radius / 2.0;
            double midAngleRad = Math.toRadians(midAngle);

            g.setColor(colors[colorIndex % colors.length]);
            g.fillArc(x, y, diameter, diameter, (int) startAngle, (int) sliceAngle);

            double centerX = x + r * Math.cos(midAngleRad);
            double centerY = y + r * Math.sin(midAngleRad);
            System.out.println(centerX + "," + centerY);


            g.setColor(Color.BLACK);
            String label = String.format("%s (%.1f%%)", entry.getKey(), (value / total) * 100);
            g.setFont(merriweather.deriveFont(Font.BOLD, 12));
            g.drawString(label, (int) centerX, (int) centerY);

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
