package InventoryMgr;

import Admin.User;
import Admin.CustomComponents;
import InventoryMgr.misc.PieGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static BufferedImage bg, img;
    private static JLabel label1;
    private static CustomComponents.ImagePanel pnl;
    private static CustomComponents.RoundedPanel pnl2;
    private static CustomComponents.ImageCell cll;
    private static CustomComponents.EmptyPasswordField txt1;
    private static JCheckBox check;
    private static CustomComponents.CustomButton btn;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            img = ImageIO.read(new File("images/info.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Dashboard.parent = parent;
        Dashboard.merriweather = merriweather;
        Dashboard.boldonse = boldonse;
        Dashboard.content = content;
        Dashboard.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        label1 = new JLabel("I am going to shove this shovel up your ass.", SwingConstants.CENTER);
        label1.setFont(merriweather.deriveFont(20f).deriveFont(Font.BOLD));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);
        label1.setOpaque(true);
        label1.setBackground(Color.PINK);
        content.setLayout(new GridBagLayout());
        content.add(label1, gbc);

        gbc.gridy++;
        Map<String, Double> data = new LinkedHashMap<>();
        data.put("Wong Jia Le", 50.0);
        data.put("Vanessa", 15.0);
        data.put("Eason", 15.0);
        data.put("Whei Hung", 15.0);
        data.put("Booboon", 5.0);
        JLabel chartLabel = PieGenerator.createChartLabel(data, 400, 400);
        content.add(chartLabel, gbc);
    }

    public static void UpdateComponentSize(int base_size) {
    }
}
