package SalesMgr;

import Common.Buffer;
import javax.swing.*;
import java.awt.*;

public class SalesHomePage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Buffer current_user;
    private static JLabel label1;
    private static int indicator, base_size;


    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        SalesMgr.SalesHomePage.parent = parent;
        SalesMgr.SalesHomePage.merriweather = merriweather;
        SalesMgr.SalesHomePage.boldonse = boldonse;
        SalesMgr.SalesHomePage.content = content;
        SalesMgr.SalesHomePage.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        label1 = new JLabel("HomePage");
        label1.setOpaque(true);
        content.add(label1,gbc);
    }


    public static void UpdateComponentSize(int base_size) {

    }
}
