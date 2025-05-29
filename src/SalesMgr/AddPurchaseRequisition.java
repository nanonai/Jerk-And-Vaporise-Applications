package SalesMgr;

import Admin.User;
import Admin.CustomComponents;
import Admin.Main;
import FinanceMgr.PurchaseRequisition;
import InventoryMgr.Item;
import PurchaseMgr.Item_Supplier;
import PurchaseMgr.Supplier;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddPurchaseRequisition {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static String itemNames;
    private static JComboBox<Object> itemComboBox, supplierComboBox;
    private static CustomComponents.EmptyTextField quantity;
    private static JLabel price, total;
    private static JDialog dialog;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        AddPurchaseRequisition.parent = parent;
        AddPurchaseRequisition.merriweather = merriweather;
        AddPurchaseRequisition.boldonse = boldonse;
        AddPurchaseRequisition.content = content;
        AddPurchaseRequisition.current_user = current_user;
    }

    public static void ShowPage(){
        JDialog dialog = new JDialog(parent, "Add Purchase Requisition", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(650, 350);
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
        JLabel title = new JLabel("Create Purchase Requisition");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel label1 = new JLabel("ItemName:");
        label1.setOpaque(false);
        label1.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label1, gbc);

        gbc.gridy = 2;
        JLabel label2 = new JLabel("Supplier:");
        label2.setOpaque(false);
        label2.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label2, gbc);

        gbc.gridy = 3;
        JLabel label3 = new JLabel("Quantity:");
        label3.setOpaque(false);
        label3.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label3, gbc);

        gbc.gridy = 4;
        JLabel label4 = new JLabel();
        panel.add(label4, gbc);

        gbc.gridy = 5;
        JLabel label5 = new JLabel();
        panel.add(label5, gbc);

        gbc.gridy = 6;
        JLabel type_label6 = new JLabel();
        panel.add(type_label6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        panel.add(button_panel1, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel1.add(cancel, gbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel blank1 = new JLabel();
        blank1.setOpaque(false);
        button_panel1.add(blank1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel2 = new JPanel(new GridBagLayout());
        button_panel2.setOpaque(false);
        panel.add(button_panel2, gbc);

        gbc.gridx = 0;
        // gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel blank2 = new JLabel();
        blank2.setOpaque(false);
        button_panel2.add(blank2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel2.add(confirm, gbc);

        confirm.addActionListener(e -> {
            String itemName = Objects.requireNonNull(itemComboBox.getSelectedItem()).toString();
            Item item = Item.getItemByName(itemName, Main.item_file);
            assert item != null;
            String itemID = item.ItemID;

            String supplierName = Objects.requireNonNull(supplierComboBox.getSelectedItem()).toString();
            Supplier supplier = Supplier.getSupplierByName(supplierName, Main.supplier_file);
            assert supplier != null;
            String supplierID = supplier.SupplierID;

            String qtyText = quantity.getText().trim();

            if (qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter both quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                PurchaseRequisition PR = new PurchaseRequisition(
                        PurchaseRequisition.idMaker(Main.purchase_req_file),
                        itemID,
                        supplierID,
                        Integer.parseInt(qtyText),
                        LocalDate.now(),
                        current_user.UserID,
                        0
                );
                PurchaseRequisition.savePurchaseRequisition(PR, Main.purchase_req_file, parent);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for quantity and price.",
                        "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        // ArrayList
        List<String> itemComboBoxData = new ArrayList<>();
        // Save data to an Arraylist
        List<Item> itemsForPO = Item.listAllItem(Main.item_file);
        // loop item into array(itemComboBoxData) so we use itemsForPO other than itemComboBox.
        for (Item item : itemsForPO){
            itemComboBoxData.add(item.ItemName);
        }
        itemComboBox = new JComboBox<>(itemComboBoxData.toArray());
        itemComboBox.setFocusable(false);
        itemComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
        itemComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String selectedItemName = Objects.requireNonNull(itemComboBox.getSelectedItem()).toString();

                    // Get the item object
                    Item selectedItem = Item.getItemByName(selectedItemName, Main.item_file);
                    if (selectedItem == null) return;

                    // Get item ID
                    String selectedItemID = selectedItem.ItemID;

                    // 1. Update Supplier ComboBox
                    List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
                            selectedItemID,
                            Main.item_supplier_file
                    );
                    supplierComboBox.removeAllItems();
                    for (Supplier supplier : suppliersForPO) {
                        supplierComboBox.addItem(supplier.SupplierName);
                    }
                    supplierComboBox.repaint();
                    supplierComboBox.revalidate();

//                    // 2. Update UnitCost Label (your 'price' label)
//                    double unitCost = getUnitCostByItemID(selectedItemID, new File(Main.item_file));
//                    if (unitCost >= 0) {
//                        price.setText(String.format(" RM %.2f", unitCost));
//                    } else {
//                        price.setText("Price not found");
//                    }
//                    quantity.setText("");
//
//                    double totalAmt = calculateTotal(quantity, price);
//                    total.setText(" RM " + String.format("%.2f", totalAmt));
                });
            }
        });
        itemComboBox.setSelectedIndex(0);
        itemComboBox.getActionListeners()[0].actionPerformed(new ActionEvent(itemComboBox, ActionEvent.ACTION_PERFORMED, ""));

        panel.add(itemComboBox, gbc);

        gbc.gridy = 2;
        List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
                Objects.requireNonNull(Item.getItemByName(Objects.requireNonNull(
                        itemComboBox.getSelectedItem()).toString(), Main.item_file)).ItemID,
                Main.item_supplier_file
        );
        List<String> supplierComboBoxData = new ArrayList<>();
        for (Supplier supplier : suppliersForPO){
            supplierComboBoxData.add(supplier.SupplierName);
        }
        supplierComboBox = new JComboBox<>(supplierComboBoxData.toArray());
        supplierComboBox.setFocusable(false);
        supplierComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
        panel.add(supplierComboBox, gbc);

        // my quantity panel
        gbc.gridy = 3;
        gbc.insets = new Insets(3, 2, 3, 2);
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        quantity = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        allowOnlyPositiveNonZeroIntegers(quantity);
        inner1.add(quantity, gbc);

//        // my price panel
//        gbc.gridx = 1;
//        gbc.gridy = 4;
//        gbc.insets = new Insets(3, 2, 3, 2);
//
//        JPanel inner2 = new JPanel(new GridBagLayout());
//        inner2.setOpaque(true);
//        inner2.setBackground(Color.WHITE);
//        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
//        panel.add(inner2, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        price = new JLabel(" RM 0.00");
//        price.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
//        inner2.add(price, gbc);
//
//        // my total panel
//        gbc.gridx = 1;
//        gbc.gridy = 5;
//        gbc.insets = new Insets(3, 2, 3, 2);
//        JPanel inner3 = new JPanel(new GridBagLayout());
//        inner3.setOpaque(true);
//        inner3.setBackground(Color.WHITE);
//        inner3.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
//        panel.add(inner3, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        total = new JLabel("");
//        total.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
//        inner3.add(total, gbc);
//
//        Runnable updateTotal = () -> {
//            double totalAmt = calculateTotal(quantity, price);
//            total.setText(" RM " + String.format("%.2f", totalAmt));
//        };
//
//        DocumentListener listener = new DocumentListener() {
//            public void insertUpdate(DocumentEvent e) {
//                updateTotal.run();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                updateTotal.run();
//            }
//
//            public void changedUpdate(DocumentEvent e) {
//                updateTotal.run();
//            }
//        };
//
//        quantity.getDocument().addDocumentListener(listener);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    public static void allowOnlyPositiveNonZeroIntegers(JTextField textField) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;

                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.insert(offset, string);

                if (isValidQuantity(newText.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;

                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.replace(offset, offset + length, text);

                if (isValidQuantity(newText.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidQuantity(String text) {
                // Empty is allowed to permit deleting input
                if (text.isEmpty()) return true;

                // Only digits allowed
                if (!text.matches("\\d+")) return false;

                // Disallow leading zero
                if (text.startsWith("0")) return false;

                return true;
            }
        });
    }

//    public static double calculateTotal(JTextField quantityField, JLabel priceField) {
//        String quantityText = quantityField.getText().trim();
//        String priceText = priceField.getText().trim();
//
//        try {
//            // Remove "RM " prefix
//            if (priceText.startsWith("RM ")) {
//                priceText = priceText.substring(3).trim();
//            }
//
//            // Parse both values
//            int quantity = Integer.parseInt(quantityText);
//            double price = Double.parseDouble(priceText);
//
//            // Calculate total
//            return quantity * price;
//
//        } catch (NumberFormatException e) {
//            // Invalid input — return 0 or handle accordingly
//            return 0.0;
//        }
//    }

//    public static double getUnitCostByItemID(String itemID, File itemFile) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(itemFile))) {
//            String line;
//            boolean found = false;
//
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//                if (line.startsWith("ItemID:") && line.contains(itemID)) {
//                    found = true;
//                }
//
//                if (found && line.startsWith("UnitCost:")) {
//                    String costStr = line.replace("UnitCost:", "").trim();
//                    return Double.parseDouble(costStr);
//                }
//
//                // Skip to next item if separator is found
//                if (line.equals("~~~~~")) {
//                    found = false;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NumberFormatException e) {
//            System.out.println("Failed to parse UnitCost for itemID: " + itemID);
//        }
//        // If not found or error occurred
//        return -1;
//    }

}