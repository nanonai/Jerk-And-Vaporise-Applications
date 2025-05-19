package InventoryMgr;

import Admin.AddUser;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.misc.InvStatic;
import PurchaseMgr.PurchaseOrder;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class UpdateStock {

    private static JFrame parent = InvStatic.parent;
    private static Font merriweather = InvStatic.merriweather, boldonse = InvStatic.boldonse;
    private static JPanel content = InvStatic.content;
    private static User current_user = InvStatic.current_user;

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Stock Update", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = 0;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }

        int base_size = size_factor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        JLabel title = new JLabel("Stock Update");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel type_label = new JLabel("Account Type:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);
    }

    static List<PurchaseOrder> FindPo(Item item) {

        // ReadPO File, find itemID and check if the itemId is approved or whatsoever

        List<PurchaseOrder> AllPO = PurchaseOrder.listAllPurchaseOrders("datafile/purchaseOrder.txt");
        List<PurchaseOrder> Filtered = new ArrayList<PurchaseOrder>();

        for (PurchaseOrder po : AllPO) {
            if (Objects.equals(po.ItemID, item.ItemID)) {
                if (Objects.equals(po.Status, "Approved")) {
                    Filtered.add(po);
                }
            }
        }

        return Filtered;
    }
}
