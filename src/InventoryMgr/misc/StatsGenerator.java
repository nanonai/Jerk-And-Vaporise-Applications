package InventoryMgr.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class StatsGenerator {

    public static BufferedImage generateStatCircle(int current, int max, int size, Color fillColor, Color baseColor) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float percent = (max == 0) ? 0 : Math.min((float) current / max, 1.0f);

        g2d.setColor(baseColor);
        g2d.fillOval(0, 0, size, size);

        g2d.setColor(fillColor);
        int angle = (int) (360 * percent);
        g2d.fillArc(0, 0, size, size, 90, -angle); // Start from top center

        g2d.dispose();
        return image;
    }

    public static JLabel createStatLabel(int current, int max, int size, Color fillColor, Color baseColor) {
        BufferedImage image = generateStatCircle(current, max, size, fillColor, baseColor);
        return new JLabel(new ImageIcon(image));
    }

    public static JPanel createStatPanel(int current, int max, int size, Color fillColor, Color baseColor, String topText, String bottomText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        JLabel circleLabel = createStatLabel(current, max, size, fillColor, baseColor);
        panel.add(circleLabel);

        panel.add(Box.createRigidArea(new Dimension(15, 0))); // Small gap between circle and text

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel topLabel = new JLabel(topText);
        topLabel.setFont(topLabel.getFont().deriveFont(Font.BOLD, 16f));
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bottomLabel = new JLabel(bottomText);
        bottomLabel.setFont(bottomLabel.getFont().deriveFont(Font.PLAIN, 14f));
        bottomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(topLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(bottomLabel);

        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(textPanel);

        return panel;
    }
}
