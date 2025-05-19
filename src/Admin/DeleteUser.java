package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;

public class DeleteUser {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static List<User> users;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        DeleteUser.parent = parent;
        DeleteUser.merriweather = merriweather;
        DeleteUser.boldonse = boldonse;
        DeleteUser.content = content;
        DeleteUser.current_user = current_user;
    }

    public static void UpdateUsers(List<User> users) {
        DeleteUser.users = users;
    }

    public static boolean ShowPage() {
        AtomicBoolean delete_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Delete Users", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.8));
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
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 0, 10);
        JLabel title = new JLabel("Please confirm to delete all users listed below:");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        List<String> data = new ArrayList<>();
        int counter = 1;
        for (User user: users) {
            data.add(counter + ".)");
            data.add(String.format("UserID:              %s", user.UserID));
            data.add(String.format("Username:        %s", user.Username));
            data.add(String.format("Password:         %s", user.Password));
            data.add(String.format("Full Name:        %s", user.FullName));
            data.add(String.format("Email:                 %s", user.Email));
            data.add(String.format("Phone:               %s", user.Phone));
            data.add(String.format("Role:                   %s", user.AccType));
            data.add(String.format("Joined Date:     %s", user.DateOfRegis.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            data.add(" ");
            counter += 1;
        }
        CustomComponents.CustomList<String> list = new CustomComponents.CustomList<>(
                data,  1, base_size,
                merriweather.deriveFont(Font.PLAIN),
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212)
        );
        CustomComponents.CustomScrollPane scrollPane = new CustomComponents.CustomScrollPane(false, 1, list,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        panel.add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        panel.add(placeholder, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        CustomComponents.CustomButton delete = new CustomComponents.CustomButton("Delete Users (" + users.size() + ")",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(159, 4, 4), new Color(161, 40, 40),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(delete, gbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        delete.addActionListener(_ -> {
            boolean proceed = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent, "Confirm to delete?", "Confirmation",
                    new Color(159, 4, 4),
                    new Color(255, 255, 255),
                    new Color(161, 40, 40),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (proceed) {
                delete_or_not.set(true);
            }
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

        return delete_or_not.get();
    }
}