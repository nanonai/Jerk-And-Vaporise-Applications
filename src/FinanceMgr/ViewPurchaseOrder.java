package FinanceMgr;

import Admin.CustomComponents;
import Admin.Main;
import PurchaseMgr.PurchaseOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewPurchaseOrder {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static PurchaseOrder current_po;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, PurchaseOrder current_po) {
        ViewPurchaseOrder.parent = parent;
        ViewPurchaseOrder.merriweather = merriweather;
        ViewPurchaseOrder.boldonse = boldonse;
        ViewPurchaseOrder.content = content;
        ViewPurchaseOrder.current_po = current_po;
    }

    public static void UpdatePurchaseOrder(PurchaseOrder purchaseOrder) {
        ViewPurchaseOrder.current_po = purchaseOrder;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Purchase Order Details", true);
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
        JLabel title = new JLabel("View PO Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel itemID_label = new JLabel("Purchase Order ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 2;
        JLabel itemName_label = new JLabel("Item ID :");
        itemName_label.setOpaque(false);
        itemName_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemName_label, gbc);

        gbc.gridy = 3;
        JLabel unitPrice_label = new JLabel("Supplier ID :");
        unitPrice_label.setOpaque(false);
        unitPrice_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitPrice_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Purchase Quantity :");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel threshold_label = new JLabel("Total Amount :");
        threshold_label.setOpaque(false);
        threshold_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(threshold_label, gbc);

        gbc.gridy = 6;
        JLabel category_label = new JLabel("OrderDate :");
        category_label.setOpaque(false);
        category_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(category_label, gbc);

        gbc.gridy = 7;
        JLabel purchaseMgrID_label = new JLabel("PurchaseMgrID :");
        purchaseMgrID_label.setOpaque(false);
        purchaseMgrID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseMgrID_label, gbc);

        gbc.gridy = 8;
        JLabel status_label = new JLabel("Status :");
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
        CustomComponents.CustomButton view = new CustomComponents.CustomButton(" ",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(209, 209, 209),
                new Color(255, 255, 255, 255), new Color(209, 209, 209),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10); // retained from above
        JLabel purchaseOrderID = new JLabel("    " + current_po.PurchaseOrderID);
        purchaseOrderID.setOpaque(true);
        purchaseOrderID.setBackground(Color.WHITE);
        purchaseOrderID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseOrderID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID, gbc);

        gbc.gridy = 2;
        JLabel itemID = new JLabel("    " + current_po.ItemID);
        itemID.setOpaque(true);
        itemID.setBackground(Color.WHITE);
        itemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID, gbc);

        gbc.gridy = 3;
        JLabel supplierID = new JLabel("    " + current_po.SupplierID);
        supplierID.setOpaque(true);
        supplierID.setBackground(Color.WHITE);
        supplierID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        supplierID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID, gbc);

        gbc.gridy = 4;
        JLabel purchaseQuantity = new JLabel("    " + current_po.PurchaseQuantity);
        purchaseQuantity.setOpaque(true);
        purchaseQuantity.setBackground(Color.WHITE);
        purchaseQuantity.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseQuantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseQuantity, gbc);

        gbc.gridy = 5;
        JLabel totalAmt = new JLabel("    " + current_po.TotalAmt);
        totalAmt.setOpaque(true);
        totalAmt.setBackground(Color.WHITE);
        totalAmt.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        totalAmt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt, gbc);

        gbc.gridy = 6;
        JLabel orderDate = new JLabel("    " + current_po.OrderDate);
        orderDate.setOpaque(true);
        orderDate.setBackground(Color.WHITE);
        orderDate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        orderDate.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate, gbc);

        gbc.gridy = 7;
        JLabel purchaseMgrID = new JLabel("    " + current_po.PurchaseMgrID);
        purchaseMgrID.setOpaque(true);
        purchaseMgrID.setBackground(Color.WHITE);
        purchaseMgrID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseMgrID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseMgrID, gbc);

        gbc.gridy = 8;
        JLabel status = new JLabel("    " + current_po.Status);
        status.setOpaque(true);
        status.setBackground(Color.WHITE);
        status.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        status.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status, gbc);

        gbc.gridy = 8;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;


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





