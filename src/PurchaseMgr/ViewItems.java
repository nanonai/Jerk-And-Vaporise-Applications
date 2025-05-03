package PurchaseMgr;

import Admin.*;
import Common.Buffer;
import Common.CustomComponents;
import Common.Home;
import Common.Main;
import SalesMgr.*;
import PurchaseMgr.*;
import InventoryMgr.*;
import FinanceMgr.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewItems {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    private static JLabel greet;
    private static CustomComponents.ImageCell weasel;
    private static BufferedImage logo;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        ViewItems.parent = parent;
        ViewItems.merriweather = merriweather;
        ViewItems.boldonse = boldonse;
        ViewItems.content = content;
        ViewItems.current_user = current_user;
        try {
            logo = ImageIO.read(new File("images/logo_sidebar.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        greet = new JLabel("");
        greet.setOpaque(true);
        greet.setBackground(Color.blue);
        content.add(greet, gbc);

        gbc.gridx = 1;
        gbc.weightx = 10;
        JLabel ferret = new JLabel("Jill");
        ferret.setOpaque(true);
        ferret.setBackground(Color.cyan);
        content.add(ferret, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        weasel = new CustomComponents.ImageCell(logo, 0.25, 5);
        content.add(weasel, gbc);
    }

    public static void UpdateComponentSize(int base_size) {
        greet.setFont(merriweather.deriveFont(Font.BOLD, base_size));
    }
}
