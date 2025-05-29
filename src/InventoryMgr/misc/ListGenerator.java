package InventoryMgr.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ListGenerator {

    public static Font merriweather = InvStatic.merriweather;

    public static BufferedImage generateListImg(List<String> lines, Integer width) {
        int padding = 10;
        int lineSpacing = 4;

        BufferedImage dummyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dummyImage.createGraphics();
        g2d.setFont(merriweather);
        FontMetrics metrics = g2d.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int height = (lineHeight * lines.size() + lineSpacing * (lines.size() - 1) + padding * 2) + 80;
        g2d.dispose();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, width, height);

        g2d.setFont(merriweather);
        metrics = g2d.getFontMetrics();
        int y = padding + metrics.getAscent();

        boolean bad = false;

        for (String line : lines) {
            String[] liners = line.split("\\|");
            if (liners[0].equals("B")) {
                bad = true;
                g2d.setColor(Color.RED);
                g2d.setFont(merriweather.deriveFont(Font.BOLD));
            } else if (liners[0].equals("E")){
                g2d.setColor(new Color(11, 132, 19));
                g2d.setFont(merriweather.deriveFont(Font.BOLD));
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setFont(merriweather);
            }
            g2d.drawString(line.substring(2) , padding, y);
            y += lineHeight + lineSpacing;
        }

        y += 20;

        if (bad) {
            g2d.setColor(Color.RED);
            g2d.drawString("Stock is low, immediate restock is advised.", padding, y);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Keep it up!", padding, y);
        }

        g2d.dispose();
        return image;
    }

    public static JLabel createListLabel(List<String> data, int width) {
        BufferedImage image = generateListImg(data, width);
        return new JLabel(new ImageIcon(image));
    }
}
