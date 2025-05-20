package SalesMgr;

import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import Admin.CustomComponents;
import PurchaseMgr.Supplier;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewItem {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Item current_item;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Item current_item) {
        ViewItem.parent = parent;
        ViewItem.merriweather = merriweather;
        ViewItem.boldonse = boldonse;
        ViewItem.content = content;
        ViewItem.current_item = current_item;
    }

    public static boolean ShowPage() {
        AtomicBoolean viewed = new AtomicBoolean(false);

        JDialog dialog = new JDialog(parent, "View Item Details", true);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        JLabel title = new JLabel("View Item Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel itemid_label = new JLabel("Item ID:");
        itemid_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(itemid_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel itemid = new JLabel("    " + current_item.ItemID);
        itemid.setOpaque(true);
        itemid.setBackground(Color.WHITE);
        itemid.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        itemid.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(itemid, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel itemname_label = new JLabel("Item Name:");
        itemname_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(itemname_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel itemname = new JLabel("    " + current_item.ItemName);
        itemname.setOpaque(true);
        itemname.setBackground(Color.WHITE);
        itemname.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(itemname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel unitprice_label = new JLabel("Unit Price:");
        unitprice_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(unitprice_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel unitprice = new JLabel("    RM " + String.format("%.2f", current_item.UnitPrice));
        unitprice.setOpaque(true);
        unitprice.setBackground(Color.WHITE);
        unitprice.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        unitprice.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(unitprice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel unitcost_label = new JLabel("Unit Cost:");
        unitcost_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(unitcost_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel unitcost = new JLabel("    RM " + String.format("%.2f", current_item.UnitCost));
        unitcost.setOpaque(true);
        unitcost.setBackground(Color.WHITE);
        unitcost.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        unitcost.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(unitcost, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel stockcount_label = new JLabel("Stock Count:");
        stockcount_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(stockcount_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel stockcount = new JLabel("    " + current_item.StockCount);
        stockcount.setOpaque(true);
        stockcount.setBackground(Color.WHITE);
        stockcount.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        stockcount.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(stockcount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel threshold_label = new JLabel("Threshold:");
        threshold_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(threshold_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel threshold = new JLabel("    " + current_item.Threshold);
        threshold.setOpaque(true);
        threshold.setBackground(Color.WHITE);
        threshold.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        threshold.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(threshold, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel category_label = new JLabel("Category:");
        category_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(category_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel category = new JLabel("    " + current_item.Category);
        category.setOpaque(true);
        category.setBackground(Color.WHITE);
        category.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        category.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(category, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel lastupdate_label = new JLabel("Last Update:");
        lastupdate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(lastupdate_label, gbc);

        gbc.gridx = 1;
        gbc.insets =new Insets(0, 0, 10, 20);
        JLabel lastupdate = new JLabel("    " + current_item.LastUpdate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lastupdate.setOpaque(true);
        lastupdate.setBackground(Color.WHITE);
        lastupdate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        lastupdate.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(lastupdate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.insets = new Insets(0, 10, 0, 10);
        bgbc.gridx = 0;
        CustomComponents.CustomButton btnSupplier = new CustomComponents.CustomButton(" Related Supplier",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        btnSupplier.setPreferredSize(new Dimension(250, 38));
        btnSupplier.addActionListener(e -> {

            String supplierID = PurchaseMgr.Item_Supplier.getSupplierIDFromItemID(current_item.ItemID, "datafile/item_supplier.txt");

            if (supplierID.equals("Unknown")) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Supplier not found for this item.",
                        "Supplier Error",
                        new Color(209, 88, 128),
                        Color.WHITE,
                        new Color(237, 136, 172),
                        Color.WHITE
                );
                return;
            }

            Supplier supplier = Supplier.getSupplierByID(supplierID, "datafile/supplier.txt");

            if (supplier == null) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Supplier details not found for Supplier ID: " + supplierID,
                        "Supplier Error",
                        new Color(209, 88, 128),
                        Color.WHITE,
                        new Color(237, 136, 172),
                        Color.WHITE
                );
            } else {
                String details = String.format(
                        "<html>" +
                                "<b>Supplier ID:</b> %s<br>" +
                                "<b>Supplier Name:</b> %s<br>" +
                                "<b>Contact Person:</b> %s<br>" +
                                "<b>Phone:</b> %s<br>" +
                                "<b>Email:</b> %s<br>" +
                                "<b>Address:</b> %s" +
                                "</html>",
                        supplier.SupplierID, supplier.SupplierName, supplier.ContactPerson,
                        supplier.Phone, supplier.Email, supplier.Address
                );

                CustomComponents.CustomOptionPane.showInfoDialog(
                        parent,
                        details,
                        "Related Supplier Details",
                        new Color(100, 100, 100),
                        Color.WHITE,
                        new Color(170, 170, 170),
                        Color.WHITE
                );
            }
        });
        buttonPanel.add(btnSupplier, bgbc);


        bgbc.gridx = 1;
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false,
                5, false, null, 0, 0, 0);
        close.setPreferredSize(new Dimension(250, 38));
        close.addActionListener(e -> dialog.dispose());
        buttonPanel.add(close, bgbc);

// Add the button panel to the main panel
        panel.add(buttonPanel, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);

        return viewed.get();
    }
}