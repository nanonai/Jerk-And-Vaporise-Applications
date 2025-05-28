package SalesMgr;

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
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class EditSupplier {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static Supplier selectedSupplier;
    private static CustomComponents.EmptyTextField supplierName, contactPerson, phone, email, address;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^01[0-9]{8}$";
    private static CustomComponents.CustomButton btnConfirm, btnCancel;
    private static Supplier selected_supplier;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user,
                              Supplier selected_supplier) {
        EditSupplier.parent = parent;
        EditSupplier.merriweather = merriweather;
        EditSupplier.boldonse = boldonse;
        EditSupplier.content = content;
        EditSupplier.current_user = current_user;
        EditSupplier.selected_supplier = selected_supplier;
    }
    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Edit Supplier", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize((int)(parent.getWidth() * 0.7), (int)(parent.getHeight() * 0.7));
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JLabel title = new JLabel("Edit Supplier");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.15)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        JLabel supplierName_label = new JLabel("Supplier Name:");
        supplierName_label.setOpaque(false);
        supplierName_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierName_label, gbc);

        gbc.gridy = 2;
        JLabel contactPerson_label = new JLabel("Contact Person:");
        contactPerson_label.setOpaque(false);
        contactPerson_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(contactPerson_label, gbc);

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
        gbc.weightx = 0.4;          // allow horizontal expansion
        gbc.weighty = 0.3;          // usually 0 for buttons (unless you want vertical growth)
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.NONE;
        btnCancel = new CustomComponents.CustomButton("Cancel", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnCancel.setPreferredSize(new Dimension(280, 50));  // Adjusted width and height
        panel.add(btnCancel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(15, 10, 15, 10);
        btnConfirm = new CustomComponents.CustomButton("Confirm", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnConfirm.setPreferredSize(new Dimension(220, 50));  // Adjusted width and height
        panel.add(btnConfirm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel originalLabel = new JLabel("Original Supplier:");
        originalLabel.setOpaque(false);
        originalLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(originalLabel, gbc);

        gbc.gridy = 1;
        JLabel supplierNameValue = new JLabel(selected_supplier.SupplierName);
        supplierNameValue.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        panel.add(supplierNameValue, gbc);

        gbc.gridy = 2;
        JLabel contactPersonValue = new JLabel(selected_supplier.ContactPerson);
        contactPersonValue.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        panel.add(contactPersonValue, gbc);

        gbc.gridy = 3;
        JLabel phoneValue = new JLabel(selected_supplier.Phone);
        phoneValue.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        panel.add(phoneValue, gbc);

        gbc.gridy = 4;
        JLabel emailValue = new JLabel(selected_supplier.Email);
        emailValue.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        panel.add(emailValue, gbc);

        gbc.gridy = 5;
        JLabel addressValue = new JLabel(selected_supplier.Address);
        addressValue.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        panel.add(addressValue, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 2;
        JLabel currentLabel = new JLabel("Current Supplier:");
        currentLabel.setOpaque(false);
        currentLabel.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(currentLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
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
        igbc.weightx = 3;
        igbc.weighty = 0.6;

        supplierName = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        supplierName.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        supplierName.setPreferredSize(new Dimension(150, 25));
        supplierName.addActionListener(_ -> { SwingUtilities.invokeLater(contactPerson::requestFocusInWindow); });
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

// Contact Person
        contactPerson = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        contactPerson.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        contactPerson.setPreferredSize(new Dimension(150, 25));
        contactPerson.addActionListener(_ -> { SwingUtilities.invokeLater(phone::requestFocusInWindow); });
        contactPerson.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                contactPerson.setForeground(Color.BLACK);
                contactPerson.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                contactPerson.setToolTipText("Enter contact person name (8 to 30 characters).");
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
                    contactPerson.setToolTipText("Contact person name length must be between 8 and 30 characters.");
                }
            }
        });
        inner2.add(contactPerson, igbc);

        igbc.gridwidth = 1;
        igbc.weightx = 0.5;
        JLabel code = new JLabel("  +60");
        code.setOpaque(false);
        code.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        inner3.add(code, igbc);

        igbc.gridx = 1;
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, inner5.getHeight()));
        separator.setForeground(new Color(209, 209, 209));
        inner3.add(separator, igbc);

        phone = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        phone.setPreferredSize(new Dimension(150, 25));
        phone.addActionListener(_ -> { SwingUtilities.invokeLater(email::requestFocusInWindow); });
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
                if (!Supplier.phoneChecker("0" + input, Main.supplier_file) && !input.isEmpty()) {
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

// Email
        email = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        email.setPreferredSize(new Dimension(150, 25));
        email.addActionListener(_ -> { SwingUtilities.invokeLater(address::requestFocusInWindow); });
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
                if (!Supplier.emailChecker(input, Main.supplier_file) && !input.isEmpty()) {
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

// Address
        address = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        address.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        address.setPreferredSize(new Dimension(150, 25));
        address.addActionListener(_ -> { SwingUtilities.invokeLater(btnConfirm::requestFocusInWindow); });
        address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                address.setForeground(Color.BLACK);
                address.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                address.setToolTipText("Enter address (8 to 48 characters).");
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
        igbc.weightx = 0.5;
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

        igbc.gridx = 3;
        igbc.weightx = 0.5;
        JButton clear4 = new JButton(icon_clear1);
        clear4.setRolloverIcon(icon_clear2);
        clear4.setOpaque(false);
        clear4.setFocusable(false);
        clear4.setBorder(BorderFactory.createEmptyBorder());
        clear4.addActionListener(_ -> {email.Reset();});
        inner4.add(clear4, igbc);

        igbc.gridx = 3;
        igbc.weightx = 0.5;
        JButton clear5 = new JButton(icon_clear1);
        clear5.setRolloverIcon(icon_clear2);
        clear5.setOpaque(false);
        clear5.setFocusable(false);
        clear5.setBorder(BorderFactory.createEmptyBorder());
        clear5.addActionListener(_ -> {address.Reset();});
        inner5.add(clear5, igbc);


        btnCancel.addActionListener(_ -> {
            dialog.dispose();
        });

        btnConfirm.addActionListener(_ -> {
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

                String validity = Supplier.validitychecker(supplierName.getText(), contactPerson.getText(),
                        "0"+phone.getText(), email.getText(), address.getText(),
                        Main.supplier_file);

                if (validity.length() != 7) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid validity string length (Expected 8 characters).",
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
                            "Supplier name must be between 8 and 48 characters!",
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
                            "Contact person name must be between 8 and 30 characters!",
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
                            "Phone number is invalid!",
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
                            "Phone number is already registered!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(4) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Email format is invalid!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(5) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Email already exists in the system!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                if (validity.charAt(6) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Address must be between 8 and 48 characters!!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                // All validations passed, proceed with item creation
                try {
                    String supplierID = selected_supplier.SupplierID;

                    // Update the selected supplier fields
                    selected_supplier.SupplierName = supplierName.getText();
                    selected_supplier.ContactPerson = contactPerson.getText();
                    selected_supplier.Phone = "0" + phone.getText();// assuming phone prefixed with '0'
                    selected_supplier.Email = email.getText();
                    selected_supplier.Address = address.getText();

                    Supplier.modifySupplier(selected_supplier, Main.supplier_file);

                    boolean keep_adding = CustomComponents.CustomOptionPane.showConfirmDialog(
                            parent,
                            "Supplier added successfully. Add another one?",
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

                    if (!keep_adding) {
                        dialog.dispose();
                    } else {
                        // Reset fields for the next item entry
                        supplierName.Reset();
                        contactPerson.Reset();
                        phone.Reset();
                        email.Reset();
                        address.Reset();
                    }

                } catch (NumberFormatException ex) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Please enter valid numeric values for price, cost, stock count, and threshold!",
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






