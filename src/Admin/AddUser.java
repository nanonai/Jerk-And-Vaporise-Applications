package Admin;

import Common.*;

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
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


public class AddUser {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Buffer current_user;
    private static User past, future;
    private static JComboBox<String> types;
    private static CustomComponents.EmptyTextField username, fullname, password, email, phone;
    private static final String EMAIL_REGEX =
            "^(?!\\.)(?!.*\\.\\.)([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)"
                    + "@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$";
    private static final String PHONE_REGEX = "^01[0-9]{8}$";

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Buffer current_user) {
        AddUser.parent = parent;
        AddUser.merriweather = merriweather;
        AddUser.boldonse = boldonse;
        AddUser.content = content;
        AddUser.current_user = current_user;
    }

    public static int[] ShowPage(int filter) {
        final int[][] result = {{0, 0}};
        JDialog dialog = new JDialog(parent, "Add New User", true);
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
        JLabel title = new JLabel("Add New User");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel type_label = new JLabel("Account Type:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 2;
        JLabel username_label = new JLabel("Username:");
        username_label.setOpaque(false);
        username_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username_label, gbc);

        gbc.gridy = 3;
        JLabel fullname_label = new JLabel("Full Name:");
        fullname_label.setOpaque(false);
        fullname_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname_label, gbc);

        gbc.gridy = 4;
        JLabel password_label = new JLabel("Password:");
        password_label.setOpaque(false);
        password_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(password_label, gbc);

        gbc.gridy = 5;
        JLabel email_label = new JLabel("Email:");
        email_label.setOpaque(false);
        email_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email_label, gbc);

        gbc.gridy = 6;
        JLabel phone_label = new JLabel("Phone:");
        phone_label.setOpaque(false);
        phone_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(phone_label, gbc);

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
        types = new JComboBox<>(new String[]{"Finance Manager", "Purchase Manager",
                "Inventory Manager", "Sales Manager"});
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
        switch (filter) {
            case 1:
                types.setSelectedItem("Finance Manager");
                break;
            case 2:
                types.setSelectedItem("Purchase Manager");
                break;
            case 3:
                types.setSelectedItem("Inventory Manager");
                break;
            case 4:
                types.setSelectedItem("Sales Manager");
                break;
        };
        types.addActionListener(_ -> {SwingUtilities.invokeLater(username::requestFocusInWindow);});
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
        username = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        username.addActionListener(_ -> {SwingUtilities.invokeLater(fullname::requestFocusInWindow);});
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                username.setForeground(Color.BLACK);
                username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                username.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                username.setToolTipText("");
                String input = username.getText();
                if (!User.usernameChecker(input, Main.userdata_file) && !input.isEmpty()) {
                    username.setForeground(new Color(159, 4, 4));
                    Font font = username.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    username.setFont(font.deriveFont(attributes));
                    username.setToolTipText("Username has been taken.");
                } else if ((input.length() < 8 || input.length() > 36) && !input.isEmpty()) {
                    username.setForeground(new Color(159, 4, 4));
                    Font font = username.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    username.setFont(font.deriveFont(attributes));
                    username.setToolTipText("Username length must be 8 to 36.");
                }
            }
        });
        ((AbstractDocument) username.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner1.add(username, igbc);

        fullname = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        fullname.addActionListener(_ -> {SwingUtilities.invokeLater(password::requestFocusInWindow);});
        fullname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fullname.setForeground(Color.BLACK);
                fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                fullname.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                fullname.setToolTipText("");
                String input = fullname.getText().trim();
                if ((input.length() < 8 || input.length() > 48)  && !input.isEmpty()) {
                    fullname.setForeground(new Color(159, 4, 4));
                    Font font = fullname.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    fullname.setFont(font.deriveFont(attributes));
                    fullname.setToolTipText("Full name length must be 8 to 48.");
                }
            }
        });
        inner2.add(fullname, igbc);

        password = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        password.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        password.addActionListener(_ -> {SwingUtilities.invokeLater(email::requestFocusInWindow);});
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                password.setForeground(Color.BLACK);
                password.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                password.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                password.setToolTipText("");
                String input = password.getText();
                if (!User.passwordChecker(input) && !input.isEmpty()) {
                    password.setForeground(new Color(159, 4, 4));
                    Font font = password.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    password.setFont(font.deriveFont(attributes));
                    password.setToolTipText("Password must contains at least one of each\n" +
                            "lowercase, uppercase, digit, and special character.");
                } else if (input.length() < 8 && !input.isEmpty()) {
                    password.setForeground(new Color(159, 4, 4));
                    Font font = password.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    password.setFont(font.deriveFont(attributes));
                    password.setToolTipText("Password length must be at least 8.");
                }
            }
        });
        ((AbstractDocument) password.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner3.add(password, igbc);

        email = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        email.addActionListener(_ -> {SwingUtilities.invokeLater(phone::requestFocusInWindow);});
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                email.setForeground(Color.BLACK);
                email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                email.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                email.setToolTipText("");
                String input = email.getText();
                if (!User.emailChecker(input, Main.userdata_file) && !input.isEmpty()) {
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
        ((AbstractDocument) email.getDocument()).setDocumentFilter(new NoSpaceFilter());
        inner4.add(email, igbc);

        igbc.weightx = 1;
        JLabel code = new JLabel("  +60");
        code.setOpaque(false);
        code.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        inner5.add(code, igbc);

        igbc.gridx = 1;
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, inner5.getHeight()));
        separator.setForeground(new Color(209, 209, 209));
        inner5.add(separator, igbc);

        igbc.gridx = 2;
        igbc.weightx = 4.8;
        phone = new CustomComponents.EmptyTextField(16, "",
                new Color(122, 122, 122));
        phone.addActionListener(_ -> {confirm.doClick();});
        phone.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                phone.setForeground(Color.BLACK);
                phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                phone.setToolTipText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                phone.setToolTipText("");
                String input = phone.getText();
                if (!User.phoneChecker("0" + input, Main.userdata_file) && !input.isEmpty()) {
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
        ((AbstractDocument) phone.getDocument()).setDocumentFilter(new DigitFilter());
        ((AbstractDocument) phone.getDocument()).setDocumentFilter(new DigitLimitFilter(9));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        inner5.add(phone, igbc);

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
        clear1.addActionListener(_ -> {username.Reset();});
        inner1.add(clear1, igbc);

        JButton clear2 = new JButton(icon_clear1);
        clear2.setRolloverIcon(icon_clear2);
        clear2.setOpaque(false);
        clear2.setFocusable(false);
        clear2.setBorder(BorderFactory.createEmptyBorder());
        clear2.addActionListener(_ -> {fullname.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.addActionListener(_ -> {password.Reset();});
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
        clear5.addActionListener(_ -> {phone.Reset();});
        inner5.add(clear5, igbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            if (username.getText().isEmpty() || fullname.getText().isEmpty() || password.getText().isEmpty() ||
                    email.getText().isEmpty() || phone.getText().isEmpty() || fullname.getText().isBlank()) {
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
                String validity = User.validityChecker(username.getText(), password.getText(), fullname.getText().trim(),
                        email.getText(), "0" + phone.getText(), Main.userdata_file);
                if (validity.charAt(0) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Username length must be 8 to 36!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(1) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Username has been taken!",
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
                            "Password length must be at least 8!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(3) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>Password must contains at least one of each<br>" +
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
                            "Invalid email address.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(6) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>This email address is already<br>registered under another account.</html>",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(7) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Invalid phone number format.",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(8) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "<html>This phone number is already<br>registered under another account.</html>",
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
                    future = new User(new_id, username.getText(), password.getText(), fullname.getText().trim(),
                            email.getText(), "0" + phone.getText(), acc_type, currentDate, 0);
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
                        username.Reset();
                        fullname.Reset();
                        password.Reset();
                        email.Reset();
                        phone.Reset();
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

        return result[0];
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