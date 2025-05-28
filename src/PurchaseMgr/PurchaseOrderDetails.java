package PurchaseMgr;

import Admin.CustomComponents;
import Admin.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class PurchaseOrderDetails {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static PurchaseOrder current_po;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, PurchaseOrder current_po) {
        PurchaseOrderDetails.parent = parent;
        PurchaseOrderDetails.merriweather = merriweather;
        PurchaseOrderDetails.boldonse = boldonse;
        PurchaseOrderDetails.content = content;
        PurchaseOrderDetails.current_po = current_po;
    }

    public static void UpdatePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrderDetails.current_po = purchaseOrder;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Purchase Order", true);
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
        JLabel title = new JLabel("Details of Purchase Order");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel purchaseOrderID_label = new JLabel("Purchase Order ID:");
        purchaseOrderID_label.setOpaque(false);
        purchaseOrderID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID_label, gbc);

        gbc.gridy = 2;
        JLabel itemID_label = new JLabel("Item ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 3;
        JLabel supplierID_label = new JLabel("Supplier ID:");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Purchase Quantity:");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel totalAmt_label = new JLabel("Total Amount:");
        totalAmt_label.setOpaque(false);
        totalAmt_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("Order Date:");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

        gbc.gridy = 7;
        JLabel managerID_label = new JLabel("Manager ID:");
        managerID_label.setOpaque(false);
        managerID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(managerID_label, gbc);

        gbc.gridy = 8;
        JLabel status_label = new JLabel("Status:");
        status_label.setOpaque(false);
        status_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status_label, gbc);

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
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel userid = new JLabel("    " + current_po.PurchaseOrderID);
        userid.setOpaque(true);
        userid.setBackground(Color.WHITE);
        userid.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid, gbc);

        gbc.gridy = 2;
        JLabel type = new JLabel("    " + current_po.ItemID);
        type.setOpaque(true);
        type.setBackground(Color.WHITE);
        type.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        type.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type, gbc);

        gbc.gridy = 3;
        JLabel username = new JLabel("    " + current_po.SupplierID);
        username.setOpaque(true);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username, gbc);

        gbc.gridy = 4;
        JLabel fullname = new JLabel("    " + current_po.PurchaseQuantity);
        fullname.setOpaque(true);
        fullname.setBackground(Color.WHITE);
        fullname.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname, gbc);

        gbc.gridy = 5;
        JLabel password = new JLabel("    " + current_po.TotalAmt);
        password.setOpaque(true);
        password.setBackground(Color.WHITE);
        password.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        password.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(password, gbc);

        gbc.gridy = 6;
        JLabel email = new JLabel("    " + current_po.OrderDate);
        email.setOpaque(true);
        email.setBackground(Color.WHITE);
        email.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email, gbc);

        gbc.gridy = 7;
        JLabel email1 = new JLabel("    " + current_po.PurchaseMgrID);
        email1.setOpaque(true);
        email1.setBackground(Color.WHITE);
        email1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email1.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email1, gbc);

        gbc.gridy = 8;
        JLabel date = new JLabel("    " + current_po.Status);
        date.setOpaque(true);
        date.setBackground(Color.WHITE);
        date.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date, gbc);

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