package SalesMgr;

import Common.Buffer;

import javax.swing.*;
import java.awt.*;

public class PurchaseOrderMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    private static JLabel label1;
    public static int indicator, base_size;


    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        SalesMgr.PurchaseOrderMng.parent = parent;
        SalesMgr.PurchaseOrderMng.merriweather = merriweather;
        SalesMgr.PurchaseOrderMng.boldonse = boldonse;
        SalesMgr.PurchaseOrderMng.content = content;
        SalesMgr.PurchaseOrderMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        label1 = new JLabel("Purchase Order Page");
        label1.setOpaque(true);
        content.add(label1,gbc);
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        switch (indicator) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        UpdateComponentSize(base_size);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}
