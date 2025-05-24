package PurchaseMgr;

import Admin.CustomComponents;
import Admin.Main;
import InventoryMgr.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class SupplierDetails {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Supplier current_sup;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Supplier current_sup) {
        SupplierDetails.parent = parent;
        SupplierDetails.merriweather = merriweather;
        SupplierDetails.boldonse = boldonse;
        SupplierDetails.content = content;
        SupplierDetails.current_sup = current_sup;
    }

    public static void UpdateSupplier(Supplier supplier) {
        SupplierDetails.current_sup = supplier;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Suppliers", true);
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
        JLabel title = new JLabel("Details of Suppliers");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel purchaseOrderID_label = new JLabel("Supplier ID:");
        purchaseOrderID_label.setOpaque(false);
        purchaseOrderID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID_label, gbc);

        gbc.gridy = 2;
        JLabel itemID_label = new JLabel("Supplier Name:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 3;
        JLabel supplierID_label = new JLabel("Contact Person:");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Phone:");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel totalAmt_label = new JLabel("Email:");
        totalAmt_label.setOpaque(false);
        totalAmt_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("Address:");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

        gbc.gridy = 7;
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
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder1 = new JLabel("");
        placeholder1.setOpaque(false);
        button_panel.add(placeholder1, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel userid = new JLabel("    " + current_sup.SupplierID);
        userid.setOpaque(true);
        userid.setBackground(Color.WHITE);
        userid.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid, gbc);

        gbc.gridy = 2;
        JLabel type = new JLabel("    " + current_sup.SupplierName);
        type.setOpaque(true);
        type.setBackground(Color.WHITE);
        type.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        type.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type, gbc);

        gbc.gridy = 3;
        JLabel username = new JLabel("    " + current_sup.ContactPerson);
        username.setOpaque(true);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username, gbc);

        gbc.gridy = 4;
        JLabel fullname = new JLabel("    " + current_sup.Phone);
        fullname.setOpaque(true);
        fullname.setBackground(Color.WHITE);
        fullname.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname, gbc);

        gbc.gridy = 5;
        JLabel email = new JLabel("    " + current_sup.Email);
        email.setOpaque(true);
        email.setBackground(Color.WHITE);
        email.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email, gbc);

        gbc.gridy = 6;
        JLabel email1 = new JLabel("    " + current_sup.Address);
        email1.setOpaque(true);
        email1.setBackground(Color.WHITE);
        email1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email1.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email1, gbc);

        close.addActionListener(_ -> {
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