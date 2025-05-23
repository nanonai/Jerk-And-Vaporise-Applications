package PurchaseMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class testing {
    public static void main(String[] args) {
        List<Item_Supplier> sorry = Item_Supplier.listAllItemSupplier("datafile/Item_Supplier.txt");
//        for (Item_Supplier i: sorry) {
//            System.out.println(i.ItemID);
//            System.out.println(i.SupplierID);
//            System.out.println(" ");
//        }

        sorry = Item_Supplier.listAllItemSupplierFromItemID("I7390", "datafile/Item_Supplier.txt");
        String sorry_text = "";
        for (Item_Supplier i: sorry) {
            System.out.println(i.ItemID);
            System.out.println(i.SupplierID);
            System.out.println(" ");
            sorry_text += i.SupplierID + "; ";
        }
        System.out.println(sorry_text);
    }
}




//package PurchaseMgr;
//
//import Admin.User;
//import Admin.CustomComponents;
//import Admin.Main;
//import InventoryMgr.Item;
//
//import javax.swing.*;
//        import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.*;
//        import java.awt.*;
//        import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.regex.Pattern;
//
//public class AddPurchaseOrder {
//    private static JFrame parent;
//    private static Font merriweather, boldonse;
//    private static JPanel content;
//    private static User current_user;
//    private static String itemNames;
//    private static JComboBox<Object> itemComboBox, supplierComboBox;
//    private static CustomComponents.EmptyTextField quantity, price;
//    private static JLabel total;
//    private static JDialog dialog;
//
//    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
//        AddPurchaseOrder.parent = parent;
//        AddPurchaseOrder.merriweather = merriweather;
//        AddPurchaseOrder.boldonse = boldonse;
//        AddPurchaseOrder.content = content;
//        AddPurchaseOrder.current_user = current_user;
//    }
//
//    public static void ShowPage(){
//        JDialog dialog = new JDialog(parent, "Add Purchase Order", true);
//        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
//        dialog.setResizable(false);
//        dialog.setLocationRelativeTo(parent);
//
//        int size_factor = 0;
//        if (parent.getWidth() >= parent.getHeight()) {
//            size_factor = parent.getHeight() / 40;
//        } else {
//            size_factor = parent.getWidth() / 30;
//        }
//
//        int base_size = size_factor;
//
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        gbc.insets = new Insets(10, 10, 10, 0);
//        JLabel title = new JLabel("Create Purchase Order");
//        title.setOpaque(false);
//        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
//        panel.add(title, gbc);
//
//        gbc.gridy = 1;
//        gbc.gridwidth = 1;
//        gbc.insets = new Insets(0, 10, 10, 0);
//        JLabel label1 = new JLabel("ItemName:");
//        label1.setOpaque(false);
//        label1.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
//        panel.add(label1, gbc);
//
//        gbc.gridy = 2;
//        JLabel label2 = new JLabel("Supplier:");
//        label2.setOpaque(false);
//        label2.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
//        panel.add(label2, gbc);
//
//        gbc.gridy = 3;
//        JLabel label3 = new JLabel("Quantity:");
//        label3.setOpaque(false);
//        label3.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
//        panel.add(label3, gbc);
//
//        gbc.gridy = 4;
//        JLabel label4 = new JLabel("Price:");
//        label4.setOpaque(false);
//        label4.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
//        panel.add(label4, gbc);
//
//        gbc.gridy = 5;
//        JLabel label5 = new JLabel("Total:");
//        label5.setOpaque(false);
//        label5.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
//        panel.add(label5, gbc);
//
//        gbc.gridy = 6;
//        //gbc.weighty = 1;
//        JLabel type_label6 = new JLabel();
//        panel.add(type_label6, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 7;
//        gbc.insets = new Insets(0, 0, 10, 10);
//        JPanel button_panel1 = new JPanel(new GridBagLayout());
//        button_panel1.setOpaque(false);
//        panel.add(button_panel1, gbc);
//
//        gbc.gridx = 0;
//        gbc.insets = new Insets(0, 10, 0, 0);
//        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
//                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
//                new Color(56, 53, 70), new Color(73, 69, 87),
//                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        button_panel1.add(cancel, gbc);
//
//        cancel.addActionListener(_ -> {
//            dialog.dispose();
//        });
//
//        gbc.gridx = 1;
//        gbc.insets = new Insets(0, 0, 0, 0);
//        JLabel blank1 = new JLabel();
//        blank1.setOpaque(false);
//        button_panel1.add(blank1, gbc);
//
//        gbc.gridx = 1;
//        gbc.insets = new Insets(0, 0, 10, 10);
//        JPanel button_panel2 = new JPanel(new GridBagLayout());
//        button_panel2.setOpaque(false);
//        panel.add(button_panel2, gbc);
//
//        gbc.gridx = 0;
//        // gbc.weightx = 1;
//        gbc.insets = new Insets(0, 0, 0, 0);
//        JLabel blank2 = new JLabel();
//        blank2.setOpaque(false);
//        button_panel2.add(blank2, gbc);
//
//        gbc.gridx = 1;
//        gbc.insets = new Insets(0, 0, 0, 0);
//        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
//                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
//                new Color(56, 53, 70), new Color(73, 69, 87),
//                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        button_panel2.add(confirm, gbc);
//
//        confirm.addActionListener(e -> {
//            String itemName = Objects.requireNonNull(itemComboBox.getSelectedItem()).toString();
//            Item item = Item.getItemByName(itemName, Main.item_file);
//            assert item != null;
//            String itemID = item.ItemID;
//
//            String supplierName = Objects.requireNonNull(supplierComboBox.getSelectedItem()).toString();
//            Supplier supplier = Supplier.getSupplierByName(supplierName, Main.supplier_file);
//            assert supplier != null;
//            String supplierID = supplier.SupplierID;
//
//            String qtyText = quantity.getText().trim();
//            String priceText = price.getText().replace("RM", "").trim();
//
//            if (qtyText.isEmpty() || priceText.isEmpty()) {
//                JOptionPane.showMessageDialog(dialog, "Please enter both quantity and price.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            try {
//                PurchaseOrder PO = new PurchaseOrder(
//                        PurchaseOrder.idMaker(Main.purchaseOrder_file),
//                        itemID,
//                        supplierID,
//                        Integer.parseInt(qtyText),
//                        calculateTotal(quantity , price),
//                        LocalDate.now(),
//                        current_user.UserID,
//                        "Pending"
//                );
//                PurchaseOrder.savePurchaseOrder(PO, Main.purchaseOrder_file, parent);
//                dialog.dispose();
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for quantity and price.",
//                        "Format Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        // ArrayList
//        List<String> itemComboBoxData = new ArrayList<>();
//        // Save data to an Arraylist
//        List<Item> itemsForPO = Item.listAllItem(Main.item_file);
//        // loop item into array(itemComboBoxData) so we use itemsForPO other than itemComboBox.
//        for (Item item : itemsForPO){
//            itemComboBoxData.add(item.ItemName);
//        }
//        itemComboBox = new JComboBox<>(itemComboBoxData.toArray());
//        itemComboBox.setFocusable(false);
//        itemComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
//        itemComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SwingUtilities.invokeLater(() -> {
//                    List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
//                            Objects.requireNonNull(Item.getItemByName(Objects.requireNonNull(
//                                    itemComboBox.getSelectedItem()).toString(), Main.item_file)).ItemID,
//                            Main.item_supplier_file
//                    );
//                    supplierComboBox.removeAllItems();
//                    for (Supplier supplier : suppliersForPO) {
//                        supplierComboBox.addItem(supplier.SupplierName);
//                    }
//                    supplierComboBox.repaint();
//                    supplierComboBox.revalidate();
//                });
//            }
//        });
//        panel.add(itemComboBox, gbc);
//
//        gbc.gridy = 2;
//        List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
//                Objects.requireNonNull(Item.getItemByName(Objects.requireNonNull(
//                        itemComboBox.getSelectedItem()).toString(), Main.item_file)).ItemID,
//                Main.item_supplier_file
//        );
//        List<String> supplierComboBoxData = new ArrayList<>();
//        for (Supplier supplier : suppliersForPO){
//            supplierComboBoxData.add(supplier.SupplierName);
//        }
//        supplierComboBox = new JComboBox<>(supplierComboBoxData.toArray());
//        supplierComboBox.setFocusable(false);
//        supplierComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
//        panel.add(supplierComboBox, gbc);
//
//        // my quantity panel
//        gbc.gridy = 3;
//        gbc.insets = new Insets(3, 2, 3, 2);
//        JPanel inner1 = new JPanel(new GridBagLayout());
//        inner1.setOpaque(true);
//        inner1.setBackground(Color.WHITE);
//        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
//        panel.add(inner1, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        quantity = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
//        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
//        allowOnlyPositiveNonZeroIntegers(quantity);
//        inner1.add(quantity, gbc);
//
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
//        price = new CustomComponents.EmptyTextField(20, "", new Color(0, 0, 0));
//        price.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
//        allowOnlyPriceFormat(price);
//
//// Apply your custom price filter that respects "RM" prefix
//        allowOnlyPriceFormat(price);
//
//// Add caret listener to prevent caret moving before prefix
////        price.addCaretListener(e -> {
////            int prefixLength = 2; // length of "RM"
////            int pos = price.getCaretPosition();
////            if (pos < prefixLength) {
////                price.setCaretPosition(prefixLength);
////            }
////        });
//
//// Add price field to panel
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
//        JLabel total = new JLabel("");
//        total.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
//        inner3.add(total, gbc);
//
//        Runnable updateTotal = () -> {
//            double totalAmt = calculateTotal(quantity, price);
//            total.setText("RM " + String.format("%.2f", totalAmt));
//        };
//
//// === Document Listener ===
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
//// Attach listener to quantity and price fields
//        quantity.getDocument().addDocumentListener(listener);
//        price.getDocument().addDocumentListener(listener);
//        dialog.setContentPane(panel);
//        dialog.setVisible(true);
//    }
//
//    public static void allowOnlyPositiveNonZeroIntegers(JTextField textField) {
//        PlainDocument doc = (PlainDocument) textField.getDocument();
//        doc.setDocumentFilter(new DocumentFilter() {
//            @Override
//            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
//                if (string == null) return;
//
//                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
//                newText.insert(offset, string);
//
//                if (isValidQuantity(newText.toString())) {
//                    super.insertString(fb, offset, string, attr);
//                }
//            }
//
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                if (text == null) return;
//
//                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
//                newText.replace(offset, offset + length, text);
//
//                if (isValidQuantity(newText.toString())) {
//                    super.replace(fb, offset, length, text, attrs);
//                }
//            }
//
//            private boolean isValidQuantity(String text) {
//                // Empty is allowed to permit deleting input
//                if (text.isEmpty()) return true;
//
//                // Only digits allowed
//                if (!text.matches("\\d+")) return false;
//
//                // Disallow leading zero
//                if (text.startsWith("0")) return false;
//
//                return true;
//            }
//        });
//    }
//
//    public static void allowOnlyPriceFormat(JTextField textField) {
//        PlainDocument doc = (PlainDocument) textField.getDocument();
//
//        final String prefix = "RM ";
//        final int prefixLength = prefix.length();
//
//        Pattern pattern = Pattern.compile("^([1-9]\\d*)(\\.\\d{0,2})?$"); // no leading zero, max 2 decimals
//
//        doc.setDocumentFilter(new DocumentFilter() {
//
//            @Override
//            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
//                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
//                StringBuilder sb = new StringBuilder(currentText);
//                sb.insert(offset, string);
//
//                if (isValidWithPrefix(sb.toString())) {
//                    super.insertString(fb, offset, string, attr);
//                }
//            }
//
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
//                StringBuilder sb = new StringBuilder(currentText);
//                sb.replace(offset, offset + length, text);
//
//                if (isValidWithPrefix(sb.toString())) {
//                    super.replace(fb, offset, length, text, attrs);
//                }
//            }
//
//            @Override
//            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
//                // Disallow removing part of the prefix
//                if (offset < prefixLength) {
//                    // If trying to remove prefix, do nothing (reject)
//                    return;
//                }
//                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
//                StringBuilder sb = new StringBuilder(currentText);
//                sb.delete(offset, offset + length);
//
//                if (isValidWithPrefix(sb.toString())) {
//                    super.remove(fb, offset, length);
//                }
//            }
//
//            private boolean isValidWithPrefix(String text) {
//                if (!text.startsWith(prefix)) {
//                    return false; // prefix must be present
//                }
//
//                String numberPart = text.substring(prefixLength);
//
//                if (numberPart.isEmpty()) {
//                    return true; // allow empty after prefix (user deleting all digits)
//                }
//
//                if (numberPart.startsWith("0")) {
//                    return false; // disallow leading zero
//                }
//
//                return pattern.matcher(numberPart).matches();
//            }
//        });
//
//        // Initialize text with prefix if missing
//        if (!textField.getText().startsWith(prefix)) {
//            textField.setText(prefix);
//        }
//
//        // Caret listener to prevent caret before prefix
//        textField.addCaretListener(e -> {
//            int pos = textField.getCaretPosition();
//            if (pos < prefixLength) {
//                textField.setCaretPosition(prefixLength);
//            }
//        });
//    }
//
//    public static double calculateTotal(JTextField quantityField, JTextField priceField) {
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
//
//
//}

