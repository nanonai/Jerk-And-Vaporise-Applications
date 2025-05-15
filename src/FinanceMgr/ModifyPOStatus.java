package FinanceMgr;

import Common.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import Common.BufferForPO;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModifyPOStatus {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static PurchaseOrder current_PO;
    private static Item current_Item;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content,PurchaseOrder current_PO) {
        ModifyPOStatus.parent = parent;
        ModifyPOStatus.merriweather = merriweather;
        ModifyPOStatus.boldonse = boldonse;
        ModifyPOStatus.content = content;
        ModifyPOStatus.current_PO = current_PO;
    }

    public static void UpdatePO(PurchaseOrder purchaseOrder) {
        ModifyPOStatus.current_PO = purchaseOrder;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Modify Status", true);
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
        JLabel title = new JLabel("Modify Status");
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
        JLabel purchaseReqID_label = new JLabel("Purchase ReqID :");
        purchaseReqID_label.setOpaque(false);
        purchaseReqID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseReqID_label, gbc);

        gbc.gridy = 3;
        JLabel itemID_label = new JLabel("ItemID :");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Quantity :");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel supplierID_label = new JLabel("SupplierID :");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("OrderDate :");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

        gbc.gridy = 7;
        JLabel purchaseManID_label = new JLabel("Purchase ManID :");
        purchaseManID_label.setOpaque(false);
        purchaseManID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseManID_label, gbc);

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
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("Modify Status",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel purchaseOrderID = new JLabel("    " + current_PO.purchaseOrderID);
        purchaseOrderID.setOpaque(true);
        purchaseOrderID.setBackground(Color.WHITE);
        purchaseOrderID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseOrderID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID, gbc);

        gbc.gridy = 2;
        JLabel purchaseReqID = new JLabel("    " + current_PO.purchaseReqID);
        purchaseReqID.setOpaque(true);
        purchaseReqID.setBackground(Color.WHITE);
        purchaseReqID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseReqID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseReqID, gbc);

        gbc.gridy = 3;
        JLabel itemID = new JLabel("    " + current_PO.itemID);
        itemID.setOpaque(true);
        itemID.setBackground(Color.WHITE);
        itemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID, gbc);

        gbc.gridy = 4;
        JLabel quantity = new JLabel("    " + current_PO.quantity);
        quantity.setOpaque(true);
        quantity.setBackground(Color.WHITE);
        quantity.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity, gbc);

//        gbc.gridy = 5;
//        JLabel supplierID = new JLabel("    " + current_PO.supplierID);
//        supplierID.setOpaque(true);
//        supplierID.setBackground(Color.WHITE);
//        supplierID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
//        supplierID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
//        panel.add(supplierID, gbc);

        gbc.gridy = 5;
        String itemIDValue = itemID.getText().trim();
        List<String> supplierIDs = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(Main.item_file))) {
            String currentSupplierID = null;
            String currentItemID = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("SupplierID:")) {
                    currentSupplierID = line.split(":", 2)[1].trim();
                } else if (line.startsWith("ItemID:")) {
                    currentItemID = line.split(":", 2)[1].trim();
                } else if (line.equals("~~~~~")) {
                    // Check if the ItemID matches, then add the supplierID
                    if (itemIDValue.equals(currentItemID) && currentSupplierID != null) {
                        supplierIDs.add(currentSupplierID);
                    }
                    // Reset for next supplier block
                    currentSupplierID = null;
                    currentItemID = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JComboBox<String> supplierIdCombo = new JComboBox<>(supplierIDs.toArray(new String[0]));
        supplierIdCombo.setSelectedItem(current_PO.status);
        supplierIdCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        supplierIdCombo.setBackground(Color.WHITE);
        supplierIdCombo.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        supplierIdCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierIdCombo, gbc);

        gbc.gridy = 6;
        JLabel orderDate = new JLabel("    " + current_PO.orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        orderDate.setOpaque(true);
        orderDate.setBackground(Color.WHITE);
        orderDate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        orderDate.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate, gbc);

        gbc.gridy = 7;
        JLabel purchaseManID = new JLabel("    " + current_PO.purchaseManID);
        purchaseManID.setOpaque(true);
        purchaseManID.setBackground(Color.WHITE);
        purchaseManID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseManID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseManID, gbc);

        gbc.gridy = 8;
        String[] statusOptions = {"Pending", "Approved", "Rejected"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem(current_PO.status);
        statusCombo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(statusCombo, gbc);

        gbc.gridy = 9;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        view.addActionListener(e -> {
            String updatedStatus = statusCombo.getSelectedItem().toString();

            current_PO.status = updatedStatus;

            BufferForPO buffer = new BufferForPO(
                    current_PO.purchaseOrderID,
                    current_PO.purchaseReqID,
                    current_PO.itemID,
                    current_PO.quantity,
                    current_PO.supplierID,
                    current_PO.orderDate,
                    current_PO.purchaseManID,
                    updatedStatus // <-- new status passed here
            );

            // Update the status in the file
            PurchaseOrder.ChangePurOrderStatus(current_PO.purchaseOrderID, buffer, Main.purchaseOrder_file, updatedStatus);

            JOptionPane.showMessageDialog(null, "Status updated successfully!");

            view_or_not.set(true); // Flag to indicate something was modified
            dialog.dispose();      // Close the dialog
        });


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
