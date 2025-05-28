package SalesMgr;

import Admin.Main;
import Admin.CustomComponents;
import PurchaseMgr.Supplier;
import InventoryMgr.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewSupplier {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Supplier current_supplier;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Supplier supplier) {
        ViewSupplier.parent = parent;
        ViewSupplier.merriweather = merriweather;
        ViewSupplier.boldonse = boldonse;
        ViewSupplier.content = content;
        ViewSupplier.current_supplier = supplier;
    }

    public static boolean ShowPage() {
        AtomicBoolean viewed = new AtomicBoolean(false);

        JDialog dialog = new JDialog(parent, "View Supplier Details", true);
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        JLabel title = new JLabel("View Supplier Details");
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(supplierIdLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel supplierId = new JLabel("    " + current_supplier.SupplierID);
        supplierId.setOpaque(true);
        supplierId.setBackground(Color.WHITE);
        supplierId.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        supplierId.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(supplierId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel nameLabel = new JLabel("Supplier Name:");
        nameLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel name = new JLabel("    " + current_supplier.SupplierName);
        name.setOpaque(true);
        name.setBackground(Color.WHITE);
        name.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        name.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel contactLabel = new JLabel("Contact Person:");
        contactLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(contactLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel contact = new JLabel("    " + current_supplier.ContactPerson);
        contact.setOpaque(true);
        contact.setBackground(Color.WHITE);
        contact.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        contact.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(contact, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel phone = new JLabel("    " + current_supplier.Phone);
        phone.setOpaque(true);
        phone.setBackground(Color.WHITE);
        phone.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel email = new JLabel("    " + current_supplier.Email);
        email.setOpaque(true);
        email.setBackground(Color.WHITE);
        email.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 10, 5);
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel address = new JLabel("    " + current_supplier.Address);
        address.setOpaque(true);
        address.setBackground(Color.WHITE);
        address.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        address.setFont(merriweather.deriveFont(Font.PLAIN, (float) base_size));
        panel.add(address, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.insets = new Insets(0, 10, 0, 10);

        bgbc.gridx = 0;
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false,
                5, false, null, 0, 0, 0);
        close.setPreferredSize(new Dimension(250, 38));
        close.addActionListener(e -> dialog.dispose());
        buttonPanel.add(close, bgbc);

        bgbc.gridx = 1;
        CustomComponents.CustomButton btnItems = new CustomComponents.CustomButton("View Supplied Items",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(108, 130, 209), new Color(136, 172, 237),
                Main.transparent, 0, base_size, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnItems.setPreferredSize(new Dimension(250, 38));
        btnItems.addActionListener(e -> {
            List<Item> items = PurchaseMgr.Item_Supplier.getItemsBySupplierID(
                    current_supplier.SupplierID,
                    "datafile/item_supplier.txt",
                    "datafile/item.txt"
            );

            if (items.isEmpty()) {
                CustomComponents.CustomOptionPane.showInfoDialog(
                        parent, "This supplier does not supply any items.",
                        "No Items Found", new Color(100, 100, 100),
                        Color.WHITE, new Color(170, 170, 170), Color.WHITE
                );
            } else {
                StringBuilder sb = new StringBuilder("<html><b>Supplied Items:</b><br><br>");
                for (Item item : items) {
                    sb.append("• ").append(item.ItemName).append(" (ID: ").append(item.ItemID).append(")<br>");
                }
                sb.append("</html>");

                CustomComponents.CustomOptionPane.showInfoDialog(
                        parent, sb.toString(), "Items Supplied",
                        new Color(100, 100, 100), Color.WHITE, new Color(170, 170, 170), Color.WHITE
                );
            }
        });
        buttonPanel.add(btnItems, bgbc);

        panel.add(buttonPanel, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);

        return viewed.get();

    }
}
