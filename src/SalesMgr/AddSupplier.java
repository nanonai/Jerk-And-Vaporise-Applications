package SalesMgr;

import Admin.AddUser;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import PurchaseMgr.Item_Supplier;
import PurchaseMgr.Supplier;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddSupplier {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static CustomComponents.EmptyTextField supplierName, contactPerson, phone, email, address;
    private static final String EMAIL_REGEX =
            "^(?!\\.)(?!.*\\.\\.)([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)"
                    + "@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$";
    private static final String PHONE_REGEX = "^01[0-9]{8}$";

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        AddSupplier.parent = parent;
        AddSupplier.merriweather = merriweather;
        AddSupplier.boldonse = boldonse;
        AddSupplier.content = content;
        AddSupplier.current_user = current_user;
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Add New Supplier", true);
        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        int size_factor = 0;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }

        int base_size = size_factor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JLabel title = new JLabel("Add New Supplier");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel supplier_label = new JLabel("Supplier Name:");
        supplier_label.setOpaque(false);
        supplier_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplier_label, gbc);

        gbc.gridy = 2;
        JLabel contact_label = new JLabel("Contact Person:");
        contact_label.setOpaque(false);
        contact_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(contact_label, gbc);

        gbc.gridy = 3;
        JLabel phone_label = new JLabel("Phone Number:");
        phone_label.setOpaque(false);
        phone_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(phone_label, gbc);

        gbc.gridy = 4;
        JLabel email_label = new JLabel("Email:");
        email_label.setOpaque(false);
        email_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email_label, gbc);

        gbc.gridy = 5;
        JLabel address_label = new JLabel("Address:");
        address_label.setOpaque(false);
        address_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(address_label, gbc);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);
        
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
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(confirm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
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

        gbc.gridy = 5;
        JPanel inner5 = new JPanel(new GridBagLayout());
        inner5.setOpaque(true);
        inner5.setBackground(Color.WHITE);
        inner5.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner5, gbc);
        
        GridBagConstraints igbc = new GridBagConstraints();
        igbc.fill = GridBagConstraints.BOTH;
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 5;
        igbc.weighty = 1;
        supplierName = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        supplierName.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        supplierName.addActionListener(_ -> {SwingUtilities.invokeLater(contactPerson::requestFocusInWindow);});
        supplierName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                supplierName.setForeground(Color.BLACK);
                supplierName.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                supplierName.setToolTipText("Enter the supplier name (8 to 48 characters).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                supplierName.setToolTipText("");
                String input = supplierName.getText();
                if ((input.length() < 8 || input.length() > 48) && !input.isEmpty()) {
                    supplierName.setForeground(new Color(159, 4, 4));
                    Font font = supplierName.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    supplierName.setFont(font.deriveFont(attributes));
                    supplierName.setToolTipText("Supplier name length must be between 8 and 48 characters.");
                }
            }
        });
        inner1.add(supplierName, igbc);

        contactPerson = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        contactPerson.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        contactPerson.addActionListener(_ -> {SwingUtilities.invokeLater(phone::requestFocusInWindow);});
        contactPerson.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                contactPerson.setForeground(Color.BLACK);
                contactPerson.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                contactPerson.setToolTipText("Enter the contact person (8 to 30 characters).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                contactPerson.setToolTipText("");
                String input = contactPerson.getText();
                if ((input.length() < 8 || input.length() > 30) && !input.isEmpty()) {
                    contactPerson.setForeground(new Color(159, 4, 4));
                    Font font = contactPerson.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    contactPerson.setFont(font.deriveFont(attributes));
                    contactPerson.setToolTipText("Name of contact person length must be between 8 and 30 characters.");
                }
            }
        });
        inner2.add(contactPerson, igbc);

        igbc.weightx = 1;
        JLabel code = new JLabel("  +60");
        code.setOpaque(false);
        code.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        inner3.add(code, igbc);

        igbc.gridx = 1;
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, inner5.getHeight()));
        separator.setForeground(new Color(209, 209, 209));
        inner3.add(separator, igbc);

        igbc.gridx = 2;
        igbc.weightx = 4.8;
        phone = new CustomComponents.EmptyTextField(16, "",
                new Color(122, 122, 122));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        phone.addActionListener(_ -> {SwingUtilities.invokeLater(email::requestFocusInWindow);});
        phone.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                phone.setForeground(Color.BLACK);
                phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                phone.setToolTipText("Enter the phone number");
            }

            @Override
            public void focusLost(FocusEvent e) {
                phone.setForeground(Color.BLACK);
                phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                phone.setToolTipText("");
                String input = phone.getText();
                if (!User.phoneChecker("0" + input, Main.supplier_file) && !input.isEmpty()) {
                    phone.setForeground(new Color(159, 4, 4));
                    Font font = phone.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    phone.setFont(font.deriveFont(attributes));
                    phone.setToolTipText("This phone number is already\nregistered under another account.");
                } else if (!Pattern.compile(PHONE_REGEX).matcher("0" + input).matches() && !input.isEmpty()) {
                    phone.setForeground(new Color(159, 4, 4));
                    Font font = phone.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    phone.setFont(font.deriveFont(attributes));
                    phone.setToolTipText("Invalid phone number format.");
                }
            }
        });
        ((AbstractDocument) phone.getDocument()).setDocumentFilter(new AddSupplier.DigitLimitFilter(9));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        inner3.add(phone, igbc);

        email = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        email.addActionListener(_ -> {SwingUtilities.invokeLater(address::requestFocusInWindow);});
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                email.setForeground(Color.BLACK);
                email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                email.setToolTipText("Enter supplier's email address");
            }

            @Override
            public void focusLost(FocusEvent e) {
                email.setToolTipText("");
                String input = email.getText();
                if (!User.emailChecker(input, Main.supplier_file) && !input.isEmpty()) {
                    email.setForeground(new Color(159, 4, 4));
                    Font font = email.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    email.setFont(font.deriveFont(attributes));
                    email.setToolTipText("This email address is already\nregistered under another account.");
                } else if (!Pattern.compile(EMAIL_REGEX).matcher(input).matches() && !input.isEmpty()) {
                    email.setForeground(new Color(159, 4, 4));
                    Font font = email.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    email.setFont(font.deriveFont(attributes));
                    email.setToolTipText("Invalid email address.");
                }
            }
        });
        ((AbstractDocument) email.getDocument()).setDocumentFilter(new AddSupplier.NoSpaceFilter());
        inner4.add(email, igbc);

        address = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        address.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        address.addActionListener(_ -> {SwingUtilities.invokeLater(confirm::requestFocusInWindow);});
        address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                address.setForeground(Color.BLACK);
                address.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                address.setToolTipText("Enter the supplier's address (8 to 48 characters).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                address.setToolTipText("");
                String input = address.getText();
                if ((input.length() < 8 || input.length() > 48) && !input.isEmpty()) {
                    address.setForeground(new Color(159, 4, 4));
                    Font font = address.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    address.setFont(font.deriveFont(attributes));
                    address.setToolTipText("Address length must be between 8 and 48 characters.");
                }
            }
        });
        inner5.add(address, igbc);

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
        clear1.addActionListener(_ -> {supplierName.Reset();});
        inner1.add(clear1, igbc);

        JButton clear2 = new JButton(icon_clear1);
        clear2.setRolloverIcon(icon_clear2);
        clear2.setOpaque(false);
        clear2.setFocusable(false);
        clear2.setBorder(BorderFactory.createEmptyBorder());
        clear2.addActionListener(_ -> {contactPerson.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.addActionListener(_ -> {phone.Reset();});
        inner3.add(clear3, igbc);

        JButton clear4 = new JButton(icon_clear1);
        clear4.setRolloverIcon(icon_clear2);
        clear4.setOpaque(false);
        clear4.setFocusable(false);
        clear4.setBorder(BorderFactory.createEmptyBorder());
        clear4.addActionListener(_ -> {email.Reset();});
        inner4.add(clear4, igbc);

        JButton clear5 = new JButton(icon_clear1);
        clear5.setRolloverIcon(icon_clear2);
        clear5.setOpaque(false);
        clear5.setFocusable(false);
        clear5.setBorder(BorderFactory.createEmptyBorder());
        clear5.addActionListener(_ -> {address.Reset();});
        inner5.add(clear5, igbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            if (supplierName.getText().trim().isEmpty() || contactPerson.getText().trim().isEmpty() ||
                    phone.getText().trim().isEmpty() || email.getText().trim().isEmpty() ||
                    address.getText().trim().isEmpty()) {

                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Fields cannot be left empty or just contain spaces!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            }else {
                // Perform item-specific validity check
                String validity = Supplier.validitychecker(supplierName.getText(), contactPerson.getText(),
                        phone.getText(), email.getText(), address.getText(),
                        Main.supplier_file);

                // Check if the validity string length is exactly 6
                if (validity.length() != 7) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid validity string length (Expected 6 characters).",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;  // Exit if the length is not 6
                }

                // Handle Item Name Error
                if (validity.charAt(0) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name must be between 8 and 48 characters!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // Handle Item Name Already Exists Error
                if (validity.charAt(1) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name already exists!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // Handle Unit Price Error
                if (validity.charAt(2) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Unit price must be a valid positive number!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // Handle Unit Cost Error
                if (validity.charAt(3) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Unit cost must be a valid positive number!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }
//                double unitPricegt = Double.parseDouble(unitprice.getText().trim());
//                double unitCostgt = Double.parseDouble(unitcost.getText().trim());
//                if (unitCostgt > unitPricegt) {
//                    CustomComponents.CustomOptionPane.showErrorDialog(
//                            parent,
//                            "Unit cost cannot be greater than unit price!",
//                            "Error",
//                            new Color(209, 88, 128),
//                            new Color(255, 255, 255),
//                            new Color(237, 136, 172),
//                            new Color(255, 255, 255)
//                    );
//                    return;
//                }

                // Handle Stock Count Error
                if (validity.charAt(4) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Stock count must be a valid non-negative integer!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // Handle Threshold Error
                if (validity.charAt(5) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Threshold must be a valid non-negative integer!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // Handle Supplier Error
                if (validity.charAt(6) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Supplier not found! PLease add supplier!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // All validations passed, proceed with item creation
//                try {
//                    double unitPriceValue = Double.parseDouble(unitprice.getText().trim());
//                    double unitCostValue = Double.parseDouble(unitcost.getText().trim());
//                    int stockCountValue = Integer.parseInt(stockcount.getText().trim());
//                    int thresholdValue = Integer.parseInt(threshold.getText().trim());
//
//                    // If no exception was thrown, proceed with item creation
//                    String item_type = (String) Objects.requireNonNull(types.getSelectedItem());
//                    String new_id = Item.idMaker("datafile/item.txt");  // You can define your own ID maker logic
//                    LocalDate currentDate = LocalDate.now();
//                    future = new Item(new_id, itemname.getText(), unitPriceValue, unitCostValue,
//                            stockCountValue, thresholdValue, item_type, currentDate);
//                    Item.saveNewItem(future, "datafile/item.txt");
//
//                    String supplierName = suppliername.getText().trim();
//                    String supplierID = Supplier.getSupplierID(supplierName);  // Use the method you already created to get SupplierID
//
//                    if ("Unknown".equals(supplierID)) {
//                        // If supplier doesn't exist, show error message
//                        CustomComponents.CustomOptionPane.showErrorDialog(
//                                parent,
//                                "Supplier name does not exist in the system!",
//                                "Error",
//                                new Color(209, 88, 128),
//                                new Color(255, 255, 255),
//                                new Color(237, 136, 172),
//                                new Color(255, 255, 255)
//                        );
//                    } else {
//                        // If supplier exists, create an Item_Supplier instance
//                        Item_Supplier itemSupplier = new Item_Supplier(new_id, supplierID);
//                        Item_Supplier.saveNewItemSupplier(itemSupplier, "datafile/item_supplier.txt");
//
//                        // Ask user if they want to continue adding more items
//                        boolean keep_adding = CustomComponents.CustomOptionPane.showConfirmDialog(
//                                parent,
//                                "Keep adding new items?",
//                                "Confirmation",
//                                new Color(209, 88, 128),
//                                new Color(255, 255, 255),
//                                new Color(237, 136, 172),
//                                new Color(255, 255, 255),
//                                new Color(56, 53, 70),
//                                new Color(255, 255, 255),
//                                new Color(73, 69, 87),
//                                new Color(255, 255, 255),
//                                true
//                        );
//
//                        if (!keep_adding) {
//                            dialog.dispose();
//                        } else {
//                            // Reset fields for the next item entry
//                            itemname.Reset();
//                            unitprice.Reset();
//                            unitcost.Reset();
//                            stockcount.Reset();
//                            threshold.Reset();
//                            suppliername.Reset();
//                        }
//                    }
//
//                } catch (NumberFormatException ex) {
//                    CustomComponents.CustomOptionPane.showErrorDialog(
//                            parent,
//                            "Please enter valid numeric values for price, cost, stock count, and threshold!",
//                            "Error",
//                            new Color(209, 88, 128),
//                            new Color(255, 255, 255),
//                            new Color(237, 136, 172),
//                            new Color(255, 255, 255)
//                    );
//                }
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

