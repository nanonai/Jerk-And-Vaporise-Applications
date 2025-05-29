package SalesMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.ModifyUser;
import Admin.User;
import InventoryMgr.Item;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

import static InventoryMgr.Item.getItemName;
import static SalesMgr.Sales.ValidityChecker;

public class EditDailySales {
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
        EditDailySales.parent = parent;
        EditDailySales.merriweather = merriweather;
        EditDailySales.boldonse = boldonse;
        EditDailySales.content = content;
        EditDailySales.current_user = current_user;
        EditDailySales.selected_sales = selected_sales;
        EditDailySales.selected_itemSales = selected_itemSales;
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Edit Daily Sales", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(650, 400);
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        JLabel title = new JLabel("Edit Daily Sales Entry");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel name_label = new JLabel("Item Name:");
        name_label.setOpaque(false);
        name_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(name_label, gbc);

        gbc.gridy = 2;
        JLabel quantity_label = new JLabel("Sold Quantity:");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 3;
        JLabel amount_label = new JLabel("Revenue:");
        amount_label.setOpaque(false);
        amount_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(amount_label, gbc);

        gbc.gridy = 4;
        JLabel date_label = new JLabel("Date(yyyy-MM-dd):");
        date_label.setOpaque(false);
        date_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date_label, gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        button_panel.add(placeholder, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(confirm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 5;
        gbc.weighty =1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets =new Insets(10, 10, 10, 10);
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner1, gbc);

        gbc.gridy = 2;
        JPanel inner2 = new JPanel(new GridBagLayout());
        inner2.setOpaque(true);
        inner2.setBackground(Color.WHITE);
        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner2, gbc);

        gbc.gridy = 3;
        JPanel inner3 = new JPanel(new GridBagLayout());
        inner3.setOpaque(true);
        inner3.setBackground(Color.WHITE);
        inner3.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner3, gbc);

        gbc.gridy = 4;
        JPanel inner4 = new JPanel(new GridBagLayout());
        inner4.setOpaque(true);
        inner4.setBackground(Color.WHITE);
        inner4.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner4, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.fill = GridBagConstraints.BOTH;
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 5;
        igbc.weighty = 0;
        igbc.insets = new Insets(5, 10, 10, 10);
        itemname = new CustomComponents.EmptyTextField(20, "",
                new Color(0, 0, 0));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemname.setText(getItemName(selected_itemSales.ItemID));
        itemname.addActionListener(_ -> {SwingUtilities.invokeLater(quantity::requestFocusInWindow);});
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
                    Font baseFont = merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8));
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(baseFont.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    itemname.setFont(baseFont.deriveFont(attributes));
                    itemname.setToolTipText("Item name length must be between 8 and 48 characters.");
                } else {
                    String itemID = Item.getItemIDByName(input, "datafile/item.txt");
                    System.out.println("ItemID: " + itemID);

                    if (itemID == "Unknown" || itemID.isEmpty()) {
                        itemname.setForeground(new Color(159, 4, 4));
                        Font baseFont = merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8));
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(baseFont.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        itemname.setFont(baseFont.deriveFont(attributes));
                        itemname.setToolTipText("Item not found in inventory.");
                    } else {
                        itemname.setForeground(Color.BLACK);
                        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                        itemname.setToolTipText("");
                    }
                }
            }
        });
        inner1.add(itemname, igbc);

        quantity = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        quantity.setText(String.valueOf(selected_itemSales.Quantity));
        quantity.addActionListener(_ -> {SwingUtilities.invokeLater(amount::requestFocusInWindow);});
        quantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                quantity.setForeground(Color.BLACK);
                quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                quantity.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                quantity.setToolTipText("");
                String input = quantity.getText().trim();
                try {
                    int value = Integer.parseInt(input);
                    if (value < 0) {
                        quantity.setForeground(new Color(159, 4, 4));
                        Font font = quantity.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        quantity.setFont(font.deriveFont(attributes));
                        quantity.setToolTipText("Sold Quantity must be a non-negative integer.");
                        return;
                    }

                    String itemName = itemname.getText().trim();
                    String itemID = Item.getItemIDByName(itemName, Main.item_file);
                    Item item = Item.getItemByID(itemID, Main.item_file);
                    if (item != null && value > item.StockCount) {
                        quantity.setForeground(new Color(159, 4, 4));
                        Font font = quantity.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        quantity.setFont(font.deriveFont(attributes));
                        quantity.setToolTipText("Quantity exceeds available stock (" + item.StockCount + ").");
                    }

                } catch (NumberFormatException ex) {
                    quantity.setForeground(new Color(159, 4, 4));
                    Font font = quantity.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    quantity.setFont(font.deriveFont(attributes));
                    quantity.setToolTipText("Sold quantity must be an integer.");
                }
            }
        });
        inner2.add(quantity, igbc);

        amount = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        amount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        amount.setText(String.valueOf(selected_itemSales.Amount));
        amount.addActionListener(_ -> {SwingUtilities.invokeLater(date::requestFocusInWindow);});
        amount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                amount.setForeground(Color.BLACK);
                amount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                amount.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                amount.setToolTipText("");
                String input = amount.getText().trim();
                try {
                    double value = Double.parseDouble(input);
                    if (value <= 0) {
                        amount.setForeground(new Color(159, 4, 4));
                        Font font = amount.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        amount.setFont(font.deriveFont(attributes));
                        amount.setToolTipText("Revenue must be a positive value.");
                    }
                } catch (NumberFormatException ex) {
                    amount.setForeground(new Color(159, 4, 4));
                    Font font = amount.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    amount.setFont(font.deriveFont(attributes));
                    amount.setToolTipText("Revenue must be a valid number.");
                }
            }
        });
        inner3.add(amount, igbc);

        date = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        date.setText(String.valueOf(selected_sales.SalesDate));
        date.addActionListener(_ -> SwingUtilities.invokeLater(() -> confirm.doClick()));

        date.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                date.setForeground(Color.BLACK);
                date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                date.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String input = date.getText().trim();

                if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    date.setForeground(new Color(159, 4, 4));
                    Font font = date.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    date.setFont(font.deriveFont(attributes));
                    date.setToolTipText("Invalid date format. Use YYYY-MM-DD.");
                    return;
                }

                try {
                    LocalDate parsedDate = LocalDate.parse(input);
                } catch (DateTimeParseException ex) {
                    date.setForeground(new Color(159, 4, 4));
                    Font font = date.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    date.setFont(font.deriveFont(attributes));
                    date.setToolTipText("Invalid date. Please enter a real date.");
                }
            }
        });
        ((AbstractDocument) date.getDocument()).setDocumentFilter(new DateAutoFormatFilter());
        inner4.add(date, igbc);

        CustomComponents.CustomXIcon icon_clear1 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                new Color(209, 209, 209), true);
        CustomComponents.CustomXIcon icon_clear2 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                Color.BLACK, true);

        igbc.gridx = 3;
        igbc.weightx = 1;
        JButton clear1 = new JButton(icon_clear1);
        clear1.setRolloverIcon(icon_clear2);
        clear1.setOpaque(false);
        clear1.setFocusable(false);
        clear1.setBorder(BorderFactory.createEmptyBorder());
        clear1.addActionListener(_ -> {itemname.Reset();});
        inner1.add(clear1, igbc);

        JButton clear2 = new JButton(icon_clear1);
        clear2.setRolloverIcon(icon_clear2);
        clear2.setOpaque(false);
        clear2.setFocusable(false);
        clear2.setBorder(BorderFactory.createEmptyBorder());
        clear2.addActionListener(_ -> {quantity.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.addActionListener(_ -> {amount.Reset();});
        inner3.add(clear3, igbc);

        JButton clear4 = new JButton(icon_clear1);
        clear4.setRolloverIcon(icon_clear2);
        clear4.setOpaque(false);
        clear4.setFocusable(false);
        clear4.setBorder(BorderFactory.createEmptyBorder());
        clear4.addActionListener(_ -> {date.Reset();});
        inner4.add(clear4, igbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            if (itemname.getText().isEmpty() || quantity.getText().isEmpty() || amount.getText().isEmpty()) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Fields cannot be left empty or just contain spaces!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {

                String validity = ValidityChecker(itemname.getText().trim(), quantity.getText().trim(), amount.getText().trim());

                if (validity.length() != 4) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid validity string length (Expected 4 characters).",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(0) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name doesn't exist or is empty!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(1) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Sold Quantity must be a valid positive integer!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(2) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Revenue must be a valid positive number!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(3) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Insufficient stock: sold quantity exceeds current stock!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                try {
                    String itemNameValue = itemname.getText().trim();
                    int soldQuantityValue = Integer.parseInt(quantity.getText().trim());
                    double revenueValue = Double.parseDouble(amount.getText().trim());
                    String salesDateValue = date.getText().trim();

                    String salesID = selected_sales.SalesID;
                    selected_sales.ItemName = itemNameValue;
                    selected_sales.Quantity = soldQuantityValue;
                    selected_sales.Amount = revenueValue;
                    selected_sales.SalesDate = LocalDate.parse(salesDateValue);

                    Sales.ModifySales(selected_sales.SalesID, selected_sales, "datafile/sales.txt");

                    String itemID = Item.getItemIDByName(itemNameValue, "datafile/item.txt");

                    if (itemID == null || itemID.isEmpty()) {
                        CustomComponents.CustomOptionPane.showErrorDialog(
                                parent,
                                "Item not found! Please ensure the item name is correct.",
                                "Error",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255)
                        );
                        return;
                    }
                    Item_Sales updatedItemSales = new Item_Sales(itemID, salesID, soldQuantityValue, revenueValue);
                    Item_Sales.modifyItemSales(updatedItemSales, "datafile/item_sales.txt");

                boolean keepEditing = CustomComponents.CustomOptionPane.showConfirmDialog(
                        parent,
                        "Edit another sale?",
                        "Confirmation",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255),
                        new Color(56, 53, 70),
                        new Color(255, 255, 255),
                        new Color(73, 69, 87),
                        new Color(255, 255, 255),
                        true
                );

                if (!keepEditing) {
                    dialog.dispose();
                } else {
                    itemname.Reset();
                    quantity.Reset();
                    amount.Reset();
                    date.Reset();
                }
                } catch (NumberFormatException ex) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Please enter valid numeric values for quantity, revenue and sales date!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                }
            }
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

        return;

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
    static class DateAutoFormatFilter extends DocumentFilter {
        private static final int MAX_LENGTH = 8;

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null || !string.matches("\\d+")) return;

            StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            currentText.insert(offset, string);
            String digits = currentText.toString().replaceAll("[^\\d]", "");
            if (digits.length() > MAX_LENGTH) return;

            String formatted = formatDateString(digits);
            fb.replace(0, fb.getDocument().getLength(), formatted, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && !text.matches("\\d*")) return;

            StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            currentText.replace(offset, offset + length, text);
            String digits = currentText.toString().replaceAll("[^\\d]", "");
            if (digits.length() > MAX_LENGTH) return;

            String formatted = formatDateString(digits);
            fb.replace(0, fb.getDocument().getLength(), formatted, attrs);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            currentText.delete(offset, offset + length);
            String digits = currentText.toString().replaceAll("[^\\d]", "");
            String formatted = formatDateString(digits);
            fb.replace(0, fb.getDocument().getLength(), formatted, null);
        }

        private String formatDateString(String digits) {
            StringBuilder sb = new StringBuilder();
            int len = digits.length();

            if (len > 0) sb.append(digits.substring(0, Math.min(4, len)));
            if (len > 4) sb.append("-").append(digits.substring(4, Math.min(6, len)));
            if (len > 6) sb.append("-").append(digits.substring(6, Math.min(8, len)));

            return sb.toString();
        }
    }

}
