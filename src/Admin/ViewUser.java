package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewUser {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        ViewUser.parent = parent;
        ViewUser.merriweather = merriweather;
        ViewUser.boldonse = boldonse;
        ViewUser.content = content;
        ViewUser.current_user = current_user;
    }

    public static void UpdateUser(User user) {
        ViewUser.current_user = user;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View User Details", true);
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
        JLabel title = new JLabel("View User Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel userid_label = new JLabel("User ID:");
        userid_label.setOpaque(false);
        userid_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid_label, gbc);

        gbc.gridy = 2;
        JLabel type_label = new JLabel("Account Type:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 3;
        JLabel username_label = new JLabel("Username:");
        username_label.setOpaque(false);
        username_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username_label, gbc);

        gbc.gridy = 4;
        JLabel fullname_label = new JLabel("Full Name:");
        fullname_label.setOpaque(false);
        fullname_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname_label, gbc);

        gbc.gridy = 5;
        JLabel password_label = new JLabel("Password:");
        password_label.setOpaque(false);
        password_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(password_label, gbc);

        gbc.gridy = 6;
        JLabel email_label = new JLabel("Email:");
        email_label.setOpaque(false);
        email_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email_label, gbc);

        gbc.gridy = 7;
        JLabel phone_label = new JLabel("Phone:");
        phone_label.setOpaque(false);
        phone_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(phone_label, gbc);

        gbc.gridy = 8;
        JLabel date_label = new JLabel("Date Joined:");
        date_label.setOpaque(false);
        date_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date_label, gbc);

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
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("View Related Records",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel userid = new JLabel("    " + current_user.UserID);
        userid.setOpaque(true);
        userid.setBackground(Color.WHITE);
        userid.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid, gbc);

        gbc.gridy = 2;
        JLabel type = new JLabel("    " + current_user.AccType);
        type.setOpaque(true);
        type.setBackground(Color.WHITE);
        type.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        type.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type, gbc);

        gbc.gridy = 3;
        JLabel username = new JLabel("    " + current_user.Username);
        username.setOpaque(true);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username, gbc);

        gbc.gridy = 4;
        JLabel fullname = new JLabel("    " + current_user.FullName);
        fullname.setOpaque(true);
        fullname.setBackground(Color.WHITE);
        fullname.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname, gbc);

        gbc.gridy = 5;
        JLabel password = new JLabel("    " + current_user.Password);
        password.setOpaque(true);
        password.setBackground(Color.WHITE);
        password.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        password.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(password, gbc);

        gbc.gridy = 6;
        JLabel email = new JLabel("    " + current_user.Email);
        email.setOpaque(true);
        email.setBackground(Color.WHITE);
        email.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email, gbc);

        gbc.gridy = 8;
        JLabel date = new JLabel("    " + current_user.DateOfRegis.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        date.setOpaque(true);
        date.setBackground(Color.WHITE);
        date.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date, gbc);

        gbc.gridy = 7;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel code = new JLabel("    +60");
        code.setOpaque(true);
        code.setBackground(Color.WHITE);
        code.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        code.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        inner.add(code, gbc);

        gbc.gridx = 1;
        gbc.weightx = 8;
        JLabel phone = new JLabel("    " + current_user.Phone.substring(1));
        phone.setOpaque(true);
        phone.setBackground(Color.WHITE);
        phone.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        inner.add(phone, gbc);

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