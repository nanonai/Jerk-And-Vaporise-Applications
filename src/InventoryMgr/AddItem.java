package InventoryMgr;

import Admin.BufferForUser;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;

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
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


public class AddItem {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static BufferForUser current_user;
    private static User past, future;
    private static JComboBox<String> types;
    private static CustomComponents.EmptyTextField itemname, unitprice, startamt, minamt, supplierid;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, BufferForUser current_user) {
        AddItem.parent = parent;
        AddItem.merriweather = merriweather;
        AddItem.boldonse = boldonse;
        AddItem.content = content;
        AddItem.current_user = current_user;
    }

    public static void ShowPage() {
        final int[][] result = {{0, 0}};
        JDialog dialog = new JDialog(parent, "Add New Item", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
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
        JLabel title = new JLabel("Add New Item");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel type_label = new JLabel("Item Category:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 2;
        JLabel name_label = new JLabel("Item Name:");
        name_label.setOpaque(false);
        name_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(name_label, gbc);

        gbc.gridy = 3;
        JLabel price_label = new JLabel("Item Price:");
        price_label.setOpaque(false);
        price_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(price_label, gbc);

        gbc.gridy = 4;
        JLabel qty_label = new JLabel("Inv Quantity:");
        qty_label.setOpaque(false);
        qty_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(qty_label, gbc);

        gbc.gridy = 5;
        JLabel min_label = new JLabel("Minimum Threshold:");
        min_label.setOpaque(false);
        min_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(min_label, gbc);

        gbc.gridy = 6;
        JLabel supplier_label = new JLabel("Supplier(s):");
        supplier_label.setOpaque(false);
        supplier_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplier_label, gbc);

        gbc.gridy = 7;
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
        types = new JComboBox<>(new String[]{"Fruits", "Vegetables", "Foods", "Drinks"});
        types.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
        types.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        types.setFocusable(false);
        types.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        types.addActionListener(_ -> {SwingUtilities.invokeLater(itemname::requestFocusInWindow);});
        panel.add(types, gbc);

        gbc.gridy = 2;
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner1, gbc);

        gbc.gridy = 3;
        JPanel inner2 = new JPanel(new GridBagLayout());
        inner2.setOpaque(true);
        inner2.setBackground(Color.WHITE);
        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner2, gbc);

        gbc.gridy = 4;
        JPanel inner3 = new JPanel(new GridBagLayout());
        inner3.setOpaque(true);
        inner3.setBackground(Color.WHITE);
        inner3.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner3, gbc);

        gbc.gridy = 5;
        JPanel inner4 = new JPanel(new GridBagLayout());
        inner4.setOpaque(true);
        inner4.setBackground(Color.WHITE);
        inner4.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner4, gbc);

        gbc.gridy = 6;
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
        itemname = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemname.addActionListener(_ -> {SwingUtilities.invokeLater(unitprice::requestFocusInWindow);});
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
                String input = itemname.getText();
            }
        });
        inner1.add(itemname, igbc);

        unitprice = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        unitprice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        unitprice.addActionListener(_ -> {SwingUtilities.invokeLater(startamt::requestFocusInWindow);});
        unitprice.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                unitprice.setForeground(Color.BLACK);
                unitprice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                unitprice.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                unitprice.setToolTipText("");
                String input = unitprice.getText().trim();
            }
        });
        inner2.add(unitprice, igbc);

        startamt = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        startamt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        startamt.addActionListener(_ -> {SwingUtilities.invokeLater(minamt::requestFocusInWindow);});
        startamt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                startamt.setForeground(Color.BLACK);
                startamt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                startamt.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                startamt.setToolTipText("");
                String input = startamt.getText();
            }
        });
        ((AbstractDocument) startamt.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner3.add(startamt, igbc);

        minamt = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        minamt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        minamt.addActionListener(_ -> {SwingUtilities.invokeLater(supplierid::requestFocusInWindow);});
        minamt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                minamt.setForeground(Color.BLACK);
                minamt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                minamt.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                minamt.setToolTipText("");
                String input = minamt.getText();
            }
        });
        ((AbstractDocument) minamt.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner4.add(minamt, igbc);


        supplierid = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        supplierid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        supplierid.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                supplierid.setForeground(Color.BLACK);
                supplierid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                supplierid.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                minamt.setToolTipText("");
                String input = minamt.getText();
            }
        });
        ((AbstractDocument) minamt.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner5.add(supplierid, igbc);

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
        clear2.addActionListener(_ -> {unitprice.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.addActionListener(_ -> {startamt.Reset();});
        inner3.add(clear3, igbc);

        JButton clear4 = new JButton(icon_clear1);
        clear4.setRolloverIcon(icon_clear2);
        clear4.setOpaque(false);
        clear4.setFocusable(false);
        clear4.setBorder(BorderFactory.createEmptyBorder());
        clear4.addActionListener(_ -> {minamt.Reset();});
        inner4.add(clear4, igbc);

        JButton clear5 = new JButton(icon_clear1);
        clear5.setRolloverIcon(icon_clear2);
        clear5.setOpaque(false);
        clear5.setFocusable(false);
        clear5.setBorder(BorderFactory.createEmptyBorder());
        clear5.addActionListener(_ -> {supplierid.Reset();});
        inner5.add(clear5, igbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            if (itemname.getText().isEmpty() || unitprice.getText().isEmpty() || startamt.getText().isEmpty() ||
                    minamt.getText().isEmpty() || supplierid.getText().isEmpty() || unitprice.getText().isBlank()) {
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
                String validity = User.validityChecker(itemname.getText(), startamt.getText(), unitprice.getText().trim(),
                        minamt.getText(), "0" + supplierid.getText(), Main.userdata_file);
                if (validity.charAt(0) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "itemname length must be 8 to 36!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(1) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "itemname has been taken!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(4) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Full name length must be 8 to 48.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(2) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "startamt length must be at least 8!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(3) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>startamt must contains at least one of each<br>" +
                                    "lowercase, uppercase, digit, and special character.</html>",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(5) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid minamt address.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(6) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>This minamt address is already<br>registered under another account.</html>",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(7) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid supplierid number format.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(8) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>This supplierid number is already<br>registered under another account.</html>",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else {
                    if (future != null) {
                        past = future;
                    }
                    String acc_type = (String) Objects.requireNonNull(types.getSelectedItem());
                    String new_id = User.idMaker(acc_type, Main.userdata_file);
                    LocalDate currentDate = LocalDate.now();
                    future = new User(new_id, itemname.getText(), startamt.getText(), unitprice.getText().trim(),
                            minamt.getText(), "0" + supplierid.getText(), acc_type, currentDate, 0);
                    User.saveNewUser(future, Main.userdata_file);
                    result[0][0] = 1;
                    if (past != null && !Objects.equals(past.AccType, future.AccType)) {
                        result[0][1] = 0;
                    } else {
                        result[0][1] = switch (future.AccType) {
                            case "Finance Manager" -> 1;
                            case "Purchase Manager" -> 2;
                            case "Inventory Manager" -> 3;
                            case "Sales Manager" -> 4;
                            default -> 0;
                        };
                    }
                    boolean keep_adding = CustomComponents.CustomOptionPane.showConfirmDialog(
                            parent,
                            "Keep adding new users?",
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
                        itemname.Reset();
                        unitprice.Reset();
                        startamt.Reset();
                        minamt.Reset();
                        supplierid.Reset();
                    }
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