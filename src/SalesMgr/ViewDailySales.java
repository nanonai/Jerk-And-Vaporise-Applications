package SalesMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import PurchaseMgr.Supplier;

import javax.swing.*;
import java.awt.*;

import static InventoryMgr.Item.getItemName;

public class ViewDailySales {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static Item past, future;
    private static JComboBox<String> types;
    private static CustomComponents.EmptyTextField itemname, quantity, amount, date;
    private static Sales selected_sales;
    private static Item_Sales selected_itemSales;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user,
                              Sales selected_sales, Item_Sales selected_itemSales) {
        ViewDailySales.parent = parent;
        ViewDailySales.merriweather = merriweather;
        ViewDailySales.boldonse = boldonse;
        ViewDailySales.content = content;
        ViewDailySales.current_user = current_user;
        ViewDailySales.selected_sales = selected_sales;
        ViewDailySales.selected_itemSales = selected_itemSales;
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Edit Daily Sales", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(600, 450);
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
        JLabel title = new JLabel("View Sales Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel salesid_label = new JLabel("Sales ID:");
        salesid_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(salesid_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel salesid_value = new JLabel("    " + selected_sales.SalesID);
        salesid_value.setOpaque(true);
        salesid_value.setBackground(Color.WHITE);
        salesid_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        salesid_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(salesid_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel date_label = new JLabel("Sales Date:");
        date_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(date_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel date_value = new JLabel("    " + selected_sales.SalesDate);
        date_value.setOpaque(true);
        date_value.setBackground(Color.WHITE);
        date_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        date_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(date_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel mgr_label = new JLabel("SalesMgr ID:");
        mgr_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(mgr_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel mgr_value = new JLabel("    " + selected_sales.SalesMgrID);
        mgr_value.setOpaque(true);
        mgr_value.setBackground(Color.WHITE);
        mgr_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        mgr_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(mgr_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel item_label = new JLabel("Item Name:");
        item_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(item_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel item_value = new JLabel("    " + getItemName(selected_itemSales.ItemID));
        item_value.setOpaque(true);
        item_value.setBackground(Color.WHITE);
        item_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        item_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(item_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel qty_label = new JLabel("Sold Quantity:");
        qty_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(qty_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel qty_value = new JLabel("    " + selected_itemSales.Quantity);
        qty_value.setOpaque(true);
        qty_value.setBackground(Color.WHITE);
        qty_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        qty_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(qty_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel rev_label = new JLabel("Revenue:");
        rev_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(rev_label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel rev_value = new JLabel("    RM " + String.format("%.2f", selected_itemSales.Amount));
        rev_value.setOpaque(true);
        rev_value.setBackground(Color.WHITE);
        rev_value.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        rev_value.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(rev_value, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bgbc = new GridBagConstraints();

        bgbc.gridx = 0;
        bgbc.insets = new Insets(0, 10, 0, 10);
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false,
                5, false, null, 0, 0, 0);
        close.setPreferredSize(new Dimension(250, 38));
        close.addActionListener(e -> dialog.dispose());
        buttonPanel.add(close, bgbc);

        bgbc.gridx = 1;
        CustomComponents.CustomButton btnSM = new CustomComponents.CustomButton(" SalesManager Info ",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        btnSM.setPreferredSize(new Dimension(250, 38));
        btnSM.addActionListener(e -> {
            User matchedUser = User.GetUserById(selected_sales.SalesMgrID, Main.userdata_file);

            if (matchedUser == null) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Sales Manager details not found for User ID: " + selected_sales.SalesMgrID,
                        "Sales Manager Error",
                        new Color(209, 88, 128),
                        Color.WHITE,
                        new Color(237, 136, 172),
                        Color.WHITE
                );
            } else {
                String details = String.format(
                        "<html>" +
                                "<b>Sales Manager ID:</b> %s<br>" +
                                "<b>Username:</b> %s<br>" +
                                "<b>Full Name:</b> %s<br>" +
                                "<b>Email:</b> %s<br>" +
                                "<b>Phone:</b> %s<br>" +
                                "<b>Account Type:</b> %s<br>" +
                                "<b>Registration Date:</b> %s" +
                                "</html>",
                        matchedUser.getUserID(),
                        matchedUser.Username,
                        matchedUser.FullName,
                        matchedUser.Email,
                        matchedUser.Phone,
                        matchedUser.AccType,
                        matchedUser.DateOfRegis.toString()
                );

                CustomComponents.CustomOptionPane.showInfoDialog(
                        parent,
                        details,
                        "Sales Manager Information",
                        new Color(209, 88, 128),
                        Color.WHITE,
                        new Color(237, 136, 172),
                        Color.WHITE
                );
            }
        });
        buttonPanel.add(btnSM, bgbc);
        panel.add(buttonPanel, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);


    }
}
