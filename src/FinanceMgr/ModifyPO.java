package FinanceMgr;

import PurchaseMgr.PurchaseOrder;
import Admin.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import InventoryMgr.Item;
import java.util.List;
import java.util.ArrayList;

public class ModifyPO {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static PurchaseOrder current_PO;
    private static Item current_Item;
    private static CustomComponents.EmptyTextField quantityTxtField;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content,PurchaseOrder current_PO) {
        ModifyPO.parent = parent;
        ModifyPO.merriweather = merriweather;
        ModifyPO.boldonse = boldonse;
        ModifyPO.content = content;
        ModifyPO.current_PO = current_PO;
    }

    public static void UpdatePO(PurchaseOrder purchaseOrder) {
        ModifyPO.current_PO = purchaseOrder;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Modify Purchase Order", true);
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
        JLabel title = new JLabel("Modify Purchase Order");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel purchaseOrderID_label = new JLabel("Purchase OrderID :");
        purchaseOrderID_label.setOpaque(false);
        purchaseOrderID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID_label, gbc);

        gbc.gridy = 2;
        JLabel purchaseReqID_label = new JLabel("ItemID :");
        purchaseReqID_label.setOpaque(false);
        purchaseReqID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseReqID_label, gbc);

        gbc.gridy = 3;
        JLabel supplierID_label = new JLabel("SupplierID :");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 4;
        JLabel purchaseQuantity_label = new JLabel("Purchase Quantity :");
        purchaseQuantity_label.setOpaque(false);
        purchaseQuantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseQuantity_label, gbc);

        gbc.gridy = 5;
        JLabel totalAmt_label = new JLabel("TotalAmt :");
        totalAmt_label.setOpaque(false);
        totalAmt_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("OrderDate :");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

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
        CustomComponents.CustomButton modifyPo = new CustomComponents.CustomButton("Modify PO",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(modifyPo, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel purchaseOrderID = new JLabel("    " + current_PO.PurchaseOrderID);
        purchaseOrderID.setOpaque(true);
        purchaseOrderID.setBackground(Color.WHITE);
        purchaseOrderID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseOrderID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID, gbc);

        gbc.gridy = 2;
        JLabel ItemID = new JLabel("    " + current_PO.ItemID);
        ItemID.setOpaque(true);
        ItemID.setBackground(Color.WHITE);
        ItemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        ItemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(ItemID, gbc);

        gbc.gridy = 3;
        String itemIDValue = ItemID.getText().trim();
        List<String> supplierIDs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.item_Supplier_file))) {
            String currentSupplierID = null;
            String currentItemID = null;
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("ItemID:") && line.contains(":")) { // FIXED order
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) currentItemID = parts[1].trim();
                }
                else if (line.startsWith("SupplierID:") && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) currentSupplierID = parts[1].trim();
                }
                else if (line.equals("~~~~~")) {
                    if (itemIDValue.equalsIgnoreCase(currentItemID) && currentSupplierID != null) {
                        supplierIDs.add(currentSupplierID);
                    }
                    // reset for the next block
                    currentItemID = null;
                    currentSupplierID = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JComboBox<String> supplierIdCombo = new JComboBox<>(supplierIDs.toArray(new String[0]));
        supplierIdCombo.setSelectedItem(current_PO.SupplierID);
        supplierIdCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        supplierIdCombo.setBackground(Color.WHITE);
        supplierIdCombo.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        supplierIdCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierIdCombo, gbc);

        gbc.gridy = 4;
        String quantity =  String.valueOf(current_PO.PurchaseQuantity);
        quantityTxtField = new CustomComponents.EmptyTextField(19, quantity, new Color(122, 122, 122));
        quantityTxtField.setFont(merriweather.deriveFont(Font.BOLD, 14));
        quantityTxtField.setOpaque(true);
        quantityTxtField.setBackground(Color.WHITE);
        quantityTxtField.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        quantityTxtField.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantityTxtField, gbc);

        gbc.gridy = 5;
        JLabel totalAmt = new JLabel("    " + current_PO.TotalAmt);
        totalAmt.setOpaque(true);
        totalAmt.setBackground(Color.WHITE);
        totalAmt.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        totalAmt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt, gbc);

        gbc.gridy = 6;
        JLabel orderDate = new JLabel("    " + current_PO.OrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        orderDate.setOpaque(true);
        orderDate.setBackground(Color.WHITE);
        orderDate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        orderDate.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate, gbc);

        gbc.gridy = 7;
        JLabel purchaseMgrID = new JLabel("    " + current_PO.PurchaseMgrID);
        purchaseMgrID.setOpaque(true);
        purchaseMgrID.setBackground(Color.WHITE);
        purchaseMgrID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseMgrID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseMgrID, gbc);

        gbc.gridy = 8;
        String[] statusOptions = { "Approved", "Denied"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem(current_PO.Status);
        statusCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(statusCombo, gbc);

        gbc.gridy = 7;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        modifyPo.addActionListener(e -> {
            String updatedStatus = statusCombo.getSelectedItem().toString();
            String updateSupplierID = supplierIdCombo.getSelectedItem().toString();
            int updatedQuantity = Integer.parseInt(quantityTxtField.getText());
            current_PO.Status = updatedStatus;
            PurchaseOrder po = new PurchaseOrder(
                    current_PO.PurchaseOrderID,
                    current_PO.ItemID,
                    updateSupplierID,
                    updatedQuantity,
                    current_PO.TotalAmt,
                    current_PO.OrderDate,
                    current_PO.PurchaseMgrID,
                    updatedStatus // <-- new status passed here
            );

            // Update the status in the file
            PurchaseOrder.ModifyPurchaseOrder(current_PO.PurchaseOrderID, po, Main.purchaseOrder_file);
            JOptionPane.showMessageDialog(null, "Updated successfully!");
            view_or_not.set(true); // Flag to indicate something was modified
            dialog.dispose();      // Close the dialog
        });

        close.addActionListener(_ -> {
            dialog.dispose();
        });

        modifyPo.addActionListener(_ -> {
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
