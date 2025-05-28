package SalesMgr;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import PurchaseMgr.Item_Supplier;
import PurchaseMgr.Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;  // Import from java.util package
import java.util.Objects;


import static SalesMgr.Sales.ValidityChecker;

public class AddDailySales {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static Item past, future;;
    private static CustomComponents.EmptyTextField itemname,quantity, amount;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        AddDailySales.parent = parent;
        AddDailySales.merriweather = merriweather;
        AddDailySales.boldonse = boldonse;
        AddDailySales.content = content;
        AddDailySales.current_user = current_user;
    }

    public static void ShowPage() {
        final int[][] result = {{0, 0}};
        JDialog dialog = new JDialog(parent, "Add Daily Sales Entry", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(600, 400);
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
        JLabel title = new JLabel("Add Daily Sales Entry");
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

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.fill = GridBagConstraints.BOTH;
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 5;
        igbc.weighty = 0;
        igbc.insets = new Insets(5, 10, 10, 10);
        itemname = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        itemname.setPreferredSize(new Dimension(200, 25));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemname.addActionListener(_ -> {SwingUtilities.invokeLater(quantity::requestFocusInWindow);});
        itemname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                itemname.setForeground(Color.BLACK);
                itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                itemname.setToolTipText("Enter the item name.");
            }

            @Override
            public void focusLost(FocusEvent e) {
                itemname.setToolTipText("");
                String input = itemname.getText().trim();
                if (!input.isEmpty()) {
                    // Check if the item exists
                    List<Item> itemList = Item.listAllItem(Main.item_file);
                    boolean itemExists = false;

                    for (Item item : itemList) {
                        if (item.ItemName.equalsIgnoreCase(input)) {
                            itemExists = true;
                            break;
                        }
                    }

                    if (!itemExists) {
                        itemname.setForeground(new Color(159, 4, 4));  // Red text color for invalid input
                        Font font = itemname.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        itemname.setFont(font.deriveFont(attributes));
                        itemname.setToolTipText("Item name does not exist.");
                    }
                }
            }
        });
        inner1.add(itemname, igbc);


        quantity = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        quantity.setPreferredSize(new Dimension(200, 25));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        quantity.addActionListener(_ -> {SwingUtilities.invokeLater(amount::requestFocusInWindow);});
        quantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                quantity.setForeground(Color.BLACK);
                quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                quantity.setToolTipText("Enter sold quantity of the item.");
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
        amount.setPreferredSize(new Dimension(200, 25));
        amount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        amount.addActionListener(_ -> {SwingUtilities.invokeLater(() -> confirm.doClick());});
        amount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                amount.setForeground(Color.BLACK);
                amount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                amount.setToolTipText("Enter the revenue of the item.");
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

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            // Check if any of the fields are empty or just contain spaces
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
                    return;  // Exit if the length is not 3
                }

                // Handle Item Name Error
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

                // Handle Sold Quantity Error
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

                // Handle Revenue Error
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
                // All validations passed, proceed with sales entry creation
                try {
                    // Parse and process valid input
                    String itemName = itemname.getText().trim();
                    int quantitySold = Integer.parseInt(quantity.getText().trim());
                    double revenue = Double.parseDouble(amount.getText().trim());
                    LocalDate currentDate = LocalDate.now();
                    String new_id = Sales.idMaker(Main.sales_file);

                    Sales newSales = new Sales(new_id, currentDate, current_user.getUserID());
                    Sales.saveNewSales(newSales, Main.sales_file);

                    String itemID = Item.getItemIDByName(itemName, Main.item_file);  // Assuming Item.getItemID() returns the item ID based on item name
                    String salesID = newSales.SalesID;

                    Item_Sales newItemSales = new Item_Sales(itemID, salesID, quantitySold, revenue);
                    Item_Sales.saveNewItemSales(newItemSales, Main.item_sales_file);

                    CustomComponents.CustomOptionPane.showInfoDialog(
                            parent,
                            "Sales Entry Added Successfully!",
                            "Success",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );

                    // Close the dialog
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Please enter valid numeric values for quantity and revenue!",
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
}

