package InventoryMgr;

import Admin.User;
import Admin.CustomComponents;
import InventoryMgr.misc.ListGenerator;
import InventoryMgr.misc.PieGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static BufferedImage bg, img;

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

        JLabel label1 = new JLabel("We're no strangers to love...", SwingConstants.CENTER);
        label1.setFont(merriweather.deriveFont(20f).deriveFont(Font.BOLD));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);
        label1.setOpaque(true);
        label1.setBackground(Color.PINK);
        content.setLayout(new GridBagLayout());
        content.add(label1, gbc);

        gbc.gridx++;
        JLabel label2 = new JLabel("You know the rules, and so do i.", SwingConstants.CENTER);
        label2.setFont(merriweather.deriveFont(20f).deriveFont(Font.BOLD));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setVerticalAlignment(SwingConstants.CENTER);
        label2.setOpaque(true);
        label2.setBackground(Color.PINK);
        content.add(label2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        Map<String, Double> data = new LinkedHashMap<>();
        data.put("Wong Jia Le", 50.0);
        data.put("Vanessa", 15.0);
        data.put("Eason", 15.0);
        data.put("Whei Hung", 15.0);
        data.put("Booboon", 5.0);
        JLabel chartLabel = PieGenerator.createChartLabel(data, 400, 400);
        content.add(chartLabel, gbc);

        gbc.gridx++;
        List<Item> sa = StockAlert.Checker();
        if (sa.isEmpty()) {
            List<String> sa_list = List.of(
                    "E|All good, keep it up!",
                    "N|Everything is stocked and ready to go."
            );
            JLabel listLabel = ListGenerator.createListLabel(sa_list, 400);
            content.add(listLabel, gbc);
        } else {
            List<String> sa_list = new ArrayList<>();
            sa_list.add("B|Low Stock Alert:");
            for (Item item : sa) {
                sa_list.add("N|ID: " + item.ItemID + " | Name: " + item.ItemName);
            }
            JLabel listLabel = ListGenerator.createListLabel(sa_list, 400);
            content.add(listLabel, gbc);
        }
    }

    public static void UpdateComponentSize(int base_size) {
    }
}
