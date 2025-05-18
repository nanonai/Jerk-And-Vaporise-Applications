package SalesMgr;
import Admin.*;
import PurchaseMgr.Item_Supplier;
import InventoryMgr.Item;
import static PurchaseMgr.Item_Supplier.getSupplierIDFromItemID;
import static PurchaseMgr.Item_Supplier.getSupplierName;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static PurchaseMgr.Item_Supplier.itemIDChecker;

public class Restock {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel restockpanel;
    private static BufferForUser current_user;
    private static User past, future;
    private static CustomComponents.EmptyTextField itemID, itemname, restockquantity;
    private static String itemdata_file = "datafile/Item_Supplier.txt";
    private static int width = 500, height = 300;


    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, BufferForUser current_user) {
        Restock.parent = parent;
        Restock.merriweather = merriweather;
        Restock.boldonse = boldonse;
        Restock.restockpanel = content;
        Restock.current_user = current_user;
    }

    public static void ShowPage() {
        final int[][] result = {{0, 0}};
        JDialog dialog = new JDialog(parent, "Restock Item", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(width, height);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = 0;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }
        int base_size = size_factor;

        restockpanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Restock Item");
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        restockpanel.add(title, gbc);

        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        JLabel itemID_label = new JLabel("Item ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        restockpanel.add(itemID_label, gbc);

        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel itemname_label = new JLabel("Item name:");
        itemname_label.setOpaque(false);
        itemname_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        restockpanel.add(itemname_label, gbc);

        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel quantity_label = new JLabel("Restock Quantity:");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        restockpanel.add(quantity_label, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;  // important for expansion
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        button_panel.setPreferredSize(new Dimension(300, 40));
        restockpanel.add(button_panel, gbc);

        GridBagConstraints buttongbc = new GridBagConstraints();
        buttongbc.gridy = 0;
        buttongbc.weighty = 1;
        buttongbc.fill = GridBagConstraints.BOTH;
        buttongbc.ipadx = 30;
        buttongbc.insets = new Insets(7, 10, 0, 10);

        buttongbc.gridx = 0;
        buttongbc.weightx = 0.5;
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        confirm.setPreferredSize(new Dimension(140, 40));
        button_panel.add(confirm, buttongbc);
        confirm.addActionListener(_ -> {
            // Check if any fields are empty
            if (itemID.getText().isEmpty() || itemname.getText().isEmpty() || restockquantity.getText().isEmpty()) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Areas cannot be left empty or just spaces!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                // Validate itemID in item_supplier.txt
                String inputItemID = itemID.getText();
                if (!Item_Supplier.itemIDChecker(inputItemID, itemdata_file) && !inputItemID.isEmpty()) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item ID not found or does not exist in the supplier list!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;  // Stop further execution if itemID is invalid
                }

                // Validate itemName (length check between 8 and 48 characters)
                String inputItemName = itemname.getText().trim();
                if (inputItemName.length() < 8 || inputItemName.length() > 48) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name length must be between 8 and 48 characters.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;  // Stop further execution if itemName is invalid
                }

                // Validate restock quantity (check if it's a valid number between 1 and 10,000)
                String inputRestockQuantity = restockquantity.getText().trim();
                try {
                    int restockQuantity = Integer.parseInt(inputRestockQuantity);
                    if (restockQuantity < 1 || restockQuantity > 10000) {
                        CustomComponents.CustomOptionPane.showErrorDialog(
                                parent,
                                "Restock quantity must be between 1 and 10,000.",
                                "Error",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255)
                        );
                        return;  // Stop further execution if quantity is out of range
                    }

                    // After validation, proceed to update item.txt
                    // Read all items from item.txt
                    File itemFile = new File("datafile/item.txt");
                    List<Item> allItems = Item.listAllItem("datafile/item.txt"); // Read all items
                    boolean itemFound = false;

                    // Iterate through the items to find the one with the matching itemID
                    for (Item item : allItems) {
                        if (item.ItemID.equals(inputItemID)) {
                            // Item found, now update the StockCount
                            int currentStockCount = item.StockCount;
                            int newStockCount = currentStockCount + restockQuantity;

                            // Update the StockCount in the item
                            item.StockCount = newStockCount;
                            itemFound = true;

                            // Break the loop after updating the item
                            break;
                        }
                    }

                    // If the item was found, update the item.txt file with the new StockCount
                    if (itemFound) {
                        // Write the updated item data back to item.txt
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemFile))) {
                            for (Item item : allItems) {
                                writer.write("ItemID:         " + item.ItemID + "\n");
                                writer.write("ItemName:       " + item.ItemName + "\n");
                                writer.write("Unit Price:     " + item.UnitPrice + "\n");
                                writer.write("Unit Cost:      " + item.UnitCost + "\n");
                                writer.write("StockCount:     " + item.StockCount + "\n");
                                writer.write("Threshold:      " + item.Threshold + "\n");
                                writer.write("Category:       " + item.Category + "\n");
                                writer.write("Last Update:    " + item.LastUpdate + "\n");
                                String supplierID = getSupplierIDFromItemID(item.ItemID, "datafile/item_supplier.txt");
                                String supplierName = getSupplierName(supplierID, "datafile/supplier.txt");
                                writer.write("Supplier Name:  " + supplierName + "\n");
                                writer.write("~~~~~\n"); // Separate items with the delimiter
                            }
                        } catch (IOException e) {
                            e.printStackTrace(); // Handle IO exceptions while writing to the file
                        }

                        // Show success message
                        CustomComponents.CustomOptionPane.showInfoDialog(
                                parent,
                                "Item restocked successfully!",
                                "Success",
                                new Color(56, 53, 70),
                                new Color(255, 255, 255),
                                new Color(73, 69, 87),
                                new Color(255, 255, 255)
                        );

                        // Reset fields after successful operation
                        itemID.Reset();
                        itemname.Reset();
                        restockquantity.Reset();
                        dialog.dispose();

                    } else {
                        // Handle case when item is not found
                        CustomComponents.CustomOptionPane.showErrorDialog(
                                parent,
                                "Item ID not found in item.txt.",
                                "Error",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255)
                        );
                    }

                } catch (NumberFormatException ex) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Restock quantity must be a valid number.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                }
            }
        });

        buttongbc.gridx = 1;
        buttongbc.weightx = 0.5;
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(cancel, buttongbc);;

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        inner1.setPreferredSize(new Dimension(200, 40));  // Reduced panel size
        restockpanel.add(inner1, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.fill = GridBagConstraints.BOTH;  // Allow horizontal resizing but not vertical
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 0.3;
        itemID = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemID.setPreferredSize(new Dimension(180, 30));  // Reduced size of the text field
        itemID.addActionListener(_ -> {SwingUtilities.invokeLater(itemname::requestFocusInWindow);});
        itemID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                itemID.setForeground(Color.BLACK);
                itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                itemID.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                itemID.setToolTipText("");
                String input = itemID.getText();
                if (!Item_Supplier.itemIDChecker(input, itemdata_file) && !input.isEmpty()) {
                    itemID.setForeground(new Color(159, 4, 4));
                    Font font = itemID.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    itemID.setFont(font.deriveFont(attributes));
                    itemID.setToolTipText("Item ID not found or does not exist!");
                } else {
                    itemID.setForeground(Color.BLACK);
                    itemID.setFont(itemID.getFont().deriveFont(Font.PLAIN));
                }
            }
        });
        ((AbstractDocument) itemID.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner1.add(itemID, igbc);

        gbc.gridy = 2;
        JPanel inner2 = new JPanel(new GridBagLayout());
        inner2.setOpaque(true);
        inner2.setBackground(Color.WHITE);
        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        inner2.setPreferredSize(new Dimension(200, 40));  // Reduced panel size
        restockpanel.add(inner2, gbc);

        itemname = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemname.setPreferredSize(new Dimension(180, 30));
        itemname.addActionListener(_ -> {SwingUtilities.invokeLater(restockquantity::requestFocusInWindow);});
        itemname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                itemname.setForeground(Color.BLACK);
                itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                itemname.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                itemname.setToolTipText("");
                String input = itemname.getText().trim();
                if ((input.length() < 8 || input.length() > 48) && !input.isEmpty()) {
                    itemname.setForeground(new Color(159, 4, 4));
                    Font font = itemname.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    itemname.setFont(font.deriveFont(attributes));
                    itemname.setToolTipText("Item name length must be 8 to 48.");
                }
            }
        });
        inner2.add(itemname, igbc);

        gbc.gridy = 3;
        JPanel inner3 = new JPanel(new GridBagLayout());
        inner3.setOpaque(true);
        inner3.setBackground(Color.WHITE);
        inner3.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        inner3.setPreferredSize(new Dimension(200, 40));
        restockpanel.add(inner3, gbc);

        restockquantity = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        restockquantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        restockquantity.setPreferredSize(new Dimension(180, 30));
        restockquantity.addActionListener(_ -> {SwingUtilities.invokeLater(() -> confirm.doClick());});
        restockquantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                restockquantity.setForeground(Color.BLACK);
                restockquantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                restockquantity.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                restockquantity.setToolTipText("");
                String input = restockquantity.getText().trim();

                try {
                    int value = Integer.parseInt(input);
                    if (value > 10000 || value < 1) {
                        restockquantity.setForeground(new Color(159, 4, 4));
                        Font font = restockquantity.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        restockquantity.setFont(font.deriveFont(attributes));
                        restockquantity.setToolTipText("Quantity must be between 1 and 10,000.");
                    } else {
                        restockquantity.setForeground(Color.BLACK);
                        restockquantity.setFont(restockquantity.getFont().deriveFont(Font.PLAIN));
                    }
                } catch (NumberFormatException ex) {
                    restockquantity.setForeground(new Color(159, 4, 4));
                    Font font = restockquantity.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    restockquantity.setFont(font.deriveFont(attributes));
                    restockquantity.setToolTipText("Quantity must be a number.");
                }
            }
        });
        ((AbstractDocument) restockquantity.getDocument()).setDocumentFilter(new DigitFilter());
        inner3.add(restockquantity, igbc);

        CustomComponents.CustomXIcon icon_clear1 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                new Color(209, 209, 209), true);
        CustomComponents.CustomXIcon icon_clear2 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                Color.BLACK, true);

        igbc.gridx = 1;
        igbc.weightx = 0;
        JButton clear1 = new JButton(icon_clear1);
        clear1.setRolloverIcon(icon_clear2);
        clear1.setOpaque(false);
        clear1.setFocusable(false);
        clear1.setBorder(BorderFactory.createEmptyBorder());
        clear1.setPreferredSize(new Dimension(20, 20));
        clear1.addActionListener(_ -> {itemID.Reset();});
        inner1.add(clear1, igbc);

        JButton clear2 = new JButton(icon_clear1);
        clear2.setRolloverIcon(icon_clear2);
        clear2.setOpaque(false);
        clear2.setFocusable(false);
        clear2.setBorder(BorderFactory.createEmptyBorder());
        clear2.setPreferredSize(new Dimension(20, 20));
        clear2.addActionListener(_ -> {itemname.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.setPreferredSize(new Dimension(20, 20));
        clear3.addActionListener(_ -> {restockquantity.Reset();});
        inner3.add(clear3, igbc);

        dialog.setContentPane(restockpanel);
        dialog.setVisible(true);
    }

    static class DigitFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    static class NoSpaceFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && !string.contains(" ")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && !text.contains(" ")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    static class DigitLimitFilter extends DocumentFilter {
        private final int maxLength;

        public DigitLimitFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                int newLength = fb.getDocument().getLength() + string.length();
                if (newLength <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    int allowed = maxLength - fb.getDocument().getLength();
                    if (allowed > 0) {
                        super.insertString(fb, offset, string.substring(0, allowed), attr);
                    }
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength - length + text.length();
                if (newLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    int allowed = maxLength - (currentLength - length);
                    if (allowed > 0) {
                        super.replace(fb, offset, length, text.substring(0, allowed), attrs);
                    }
                }
            } else if (text.isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
}