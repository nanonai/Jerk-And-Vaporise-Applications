package FinanceMgr;

import Admin.ViewUser;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewInventory {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Item current_item;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Item current_item) {
        ViewInventory.parent = parent;
        ViewInventory.merriweather = merriweather;
        ViewInventory.boldonse = boldonse;
        ViewInventory.content = content;
        ViewInventory.current_item = current_item;
    }

    public static void UpdateInventory(Item item) {
        ViewInventory.current_item = item;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Item Details", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
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
        JLabel title = new JLabel("View Item Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel itemID_label = new JLabel("Item ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 2;
        JLabel itemName_label = new JLabel("Item Name :");
        itemName_label.setOpaque(false);
        itemName_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemName_label, gbc);

        gbc.gridy = 3;
        JLabel unitPrice_label = new JLabel("Unit Price :");
        unitPrice_label.setOpaque(false);
        unitPrice_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitPrice_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Quantity :");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel threshold_label = new JLabel("Threshold :");
        threshold_label.setOpaque(false);
        threshold_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(threshold_label, gbc);

        gbc.gridy = 6;
        JLabel category_label = new JLabel("Category :");
        category_label.setOpaque(false);
        category_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(category_label, gbc);

        gbc.gridy = 7;
        JLabel lastUpDate_label = new JLabel("Last Update Date:");
        lastUpDate_label.setOpaque(false);
        lastUpDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(lastUpDate_label, gbc);

        gbc.gridy =8;
        JLabel supplierID_label = new JLabel("Supplier ID :");
        supplierID_label.setOpaque(true);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN,(float) (base_size)));
        panel.add(supplierID_label,gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(close, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        button_panel.add(placeholder, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("View Related Payment",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel itemID = new JLabel("    " + current_item.ItemID);
        itemID.setOpaque(true);
        itemID.setBackground(Color.WHITE);
        itemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID, gbc);

        gbc.gridy = 2;
        JLabel itemName = new JLabel("    " + current_item.ItemName);
        itemName.setOpaque(true);
        itemName.setBackground(Color.WHITE);
        itemName.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemName.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemName, gbc);

        gbc.gridy = 3;
        JLabel unitPrice = new JLabel("    " + current_item.UnitPrice);
        unitPrice.setOpaque(true);
        unitPrice.setBackground(Color.WHITE);
        unitPrice.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        unitPrice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitPrice, gbc);

        gbc.gridy = 4;
        JLabel quantity = new JLabel("    " + current_item.UnitCost);
        quantity.setOpaque(true);
        quantity.setBackground(Color.WHITE);
        quantity.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity, gbc);

        gbc.gridy = 5;
        JLabel threshold = new JLabel("    " + current_item.StockCount);
        threshold.setOpaque(true);
        threshold.setBackground(Color.WHITE);
        threshold.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        threshold.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(threshold, gbc);

        gbc.gridy = 6;
        JLabel category = new JLabel("    " + current_item.Threshold);
        category.setOpaque(true);
        category.setBackground(Color.WHITE);
        category.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        category.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(category, gbc);

        gbc.gridy = 7;
        JLabel lastUpdateDate = new JLabel("    " + current_item.Category);
        lastUpdateDate.setOpaque(true);
        lastUpdateDate.setBackground(Color.WHITE);
        lastUpdateDate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        lastUpdateDate.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(lastUpdateDate, gbc);

        gbc.gridy = 8;
        JLabel supplierID = new JLabel("    " + current_item.LastUpdate);
        supplierID.setOpaque(true);
        supplierID.setBackground(Color.WHITE);
        supplierID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        supplierID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID, gbc);

        gbc.gridy = 7;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        close.addActionListener(_ -> {
            dialog.dispose();
        });

        view.addActionListener(_ -> {
            view_or_not.set(true);
            dialog.dispose();
        });

        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = e.getComponent();
                SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
            }
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);

        return view_or_not.get();
    }

}
