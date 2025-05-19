package SalesMgr;

import Admin.User;

import javax.swing.*;
import java.awt.*;

public class PurchaseRequisitionMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static User current_user;
    private static JLabel label1;
    private static int indicator, base_size;


    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        SalesMgr.PurchaseRequisitionMng.parent = parent;
        SalesMgr.PurchaseRequisitionMng.merriweather = merriweather;
        SalesMgr.PurchaseRequisitionMng.boldonse = boldonse;
        SalesMgr.PurchaseRequisitionMng.content = content;
        SalesMgr.PurchaseRequisitionMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        label1 = new JLabel("Purchase Requisition Page");
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




