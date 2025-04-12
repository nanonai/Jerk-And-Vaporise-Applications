package Common;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SignIn {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static BufferedImage bg, logo, user_icon, lock_icon, show_icon, hidden_icon;
    private static CustomComponents.ImagePanel background;
    private static CustomComponents.ImageCell logo_cell, txt_icon1, txt_icon2;
    private static JLabel logo_text1, logo_text2, right_title;
    private static JPanel outer_grid;
    private static CustomComponents.RoundedPanel txt_grid1;
    private static CustomComponents.RoundedPanel txt_grid2;
    private static CustomComponents.EmptyTextField txt1;
    private static CustomComponents.EmptyPasswordField txt2;
    private static CustomComponents.CustomButton hidden, button;
    private static JCheckBox check;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            logo = ImageIO.read(new File("images/logo_original.png"));
            user_icon = ImageIO.read(new File("images/user.png"));
            lock_icon = ImageIO.read(new File("images/lock.png"));
            show_icon = ImageIO.read(new File("images/show.png"));
            hidden_icon = ImageIO.read(new File("images/hidden.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        SignIn.merriweather = merriweather;
        SignIn.boldonse = boldonse;
        SignIn.parent = parent;
    }

    public static void ShowPage() {
        background = new CustomComponents.ImagePanel(bg);

        outer_grid = background.getGridPanel();
        GridBagConstraints gbc_outer = new GridBagConstraints();
        outer_grid.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 20));

        gbc_outer.gridx = 0;
        gbc_outer.gridy = 0;
        gbc_outer.weightx = 1;
        gbc_outer.weighty = 1;
        gbc_outer.fill = GridBagConstraints.BOTH;
        JLabel place_holder1 = new JLabel("", SwingConstants.CENTER);
        outer_grid.add(place_holder1, gbc_outer);

        gbc_outer.gridy = 1;
        logo_cell = new CustomComponents.ImageCell(logo, 0.85 , 7);
        outer_grid.add(logo_cell, gbc_outer);

        gbc_outer.gridy = 2;
        gbc_outer.weighty = 0.001;
        logo_text1 = new JLabel("<html><div style='color:#383546; padding-top: 14px;'>"
                + "<b>Omega Wholesale Sdn Bhd (OWSB)</b></div></html>");
        logo_text1.setFont(merriweather);
        outer_grid.add(logo_text1, gbc_outer);
        gbc_outer.gridy = 3;
        gbc_outer.weighty = 0.7;
        logo_text2 = new JLabel("<html><div style='color:#383546;'>"
                + "Automated Purchase Order<br>Management System</div></html>");
        logo_text2.setFont(boldonse);
        outer_grid.add(logo_text2, gbc_outer);

        gbc_outer.gridy = 4;
        gbc_outer.weighty = 1;
        JLabel place_holder2 = new JLabel("", SwingConstants.CENTER);
        outer_grid.add(place_holder2, gbc_outer);

        gbc_outer.gridx = 1;
        gbc_outer.gridy = 0;
        gbc_outer.gridheight = 5;
        gbc_outer.weightx = 5;
        gbc_outer.fill = GridBagConstraints.BOTH;
        CustomComponents.RoundedPanel right_panel = new CustomComponents.RoundedPanel(30,
                1, 0, Color.WHITE, Color.BLACK);
        right_panel.setBackground(Color.WHITE);

        right_panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc_right = new GridBagConstraints();

        gbc_right.gridx = 0;
        gbc_right.weightx = 1;
        gbc_right.weighty = 1;
        gbc_right.fill = GridBagConstraints.BOTH;
        JPanel right_grid = new JPanel(new GridBagLayout());
        right_grid.setOpaque(false);
        right_grid.setBorder(BorderFactory.createEmptyBorder(60, 100, 120, 100));
        right_panel.add(right_grid, gbc_right);

        GridBagConstraints gbc_inner = new GridBagConstraints();
        gbc_inner.gridy = 0;
        gbc_inner.weightx = 10;
        gbc_inner.weighty = 0.3;
        gbc_inner.fill = GridBagConstraints.BOTH;
        right_title = new JLabel("<html><div style='color:#383546;'>"
                + "<b>Sign In</b></div></html>");
        right_grid.add(right_title, gbc_inner);

        gbc_inner.gridy = 1;
        gbc_inner.weighty = 0.125;
        txt_grid1 = new CustomComponents.RoundedPanel(
                60, 0, 3,
                Color.WHITE, new Color(179, 181, 180));
        txt_grid1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        txt_grid1.setLayout(new GridBagLayout());
        right_grid.add(txt_grid1, gbc_inner);

        gbc_inner.gridx = 0;
        gbc_inner.gridy = 0;
        gbc_inner.weightx = 4;
        txt_icon1 = new CustomComponents.ImageCell(
                user_icon, 0.5, 5);
        txt_grid1.add(txt_icon1, gbc_inner);

        gbc_inner.gridx = 1;
        gbc_inner.gridy = 0;
        gbc_inner.weightx = 6;
        txt1 = new CustomComponents.EmptyTextField(
                10, "Username or email \r\r", new Color(178, 181, 180));
        txt1.addActionListener(_ -> SwingUtilities.invokeLater(txt2::requestFocusInWindow));
        txt_grid1.add(txt1, gbc_inner);

        gbc_inner.gridx = 0;
        gbc_inner.gridy = 2;
        gbc_inner.weighty = 0.125;
        gbc_inner.insets = new Insets(10, 0, 0, 0);
        txt_grid2 = new CustomComponents.RoundedPanel(60, 0, 3,
                Color.WHITE, new Color(179, 181, 180));
        txt_grid2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        txt_grid2.setLayout(new GridBagLayout());
        right_grid.add(txt_grid2, gbc_inner);

        gbc_inner.gridx = 0;
        gbc_inner.gridy = 0;
        gbc_inner.weightx = 5.8;
        gbc_inner.insets = new Insets(0, 0, 0, 0);
        txt_icon2 = new CustomComponents.ImageCell(
                lock_icon, 0.5, 5);
        txt_grid2.add(txt_icon2, gbc_inner);

        gbc_inner.gridx = 1;
        gbc_inner.weightx = 9.1;
        txt2 = new CustomComponents.EmptyPasswordField(
                7, "Password \r\r", new Color(178, 181, 180));
        txt2.setEchoChar((char) 0);
        txt2.addActionListener(_ -> Checker());
        txt_grid2.add(txt2, gbc_inner);

        gbc_inner.gridx = 2;
        gbc_inner.weightx = 0.5;
        hidden = new CustomComponents.CustomButton("", merriweather,
                Main.transparent, Main.transparent, Main.transparent, Main.transparent, Main.transparent,
                0, 0, Main.transparent, false, 4,
                true, hidden_icon, 0.6,
                txt_grid2.getHeight() * 5 / 6, txt_grid2.getHeight());
        hidden.addActionListener(_ -> {
            if (hidden.ReturnImageState()) {
                hidden.UpdateCustomButton(0, 0, show_icon, 0.6);
            } else {
                hidden.UpdateCustomButton(0, 0, hidden_icon, 0.6);
            }
            hidden.UpdateSize(txt_grid2.getHeight() * 5 / 6, txt_grid2.getHeight());
            txt2.UpdateStatus(hidden.ReturnImageState());
            txt2.repaint();
        });
        txt_grid2.add(hidden, gbc_inner);

        gbc_inner.gridx = 0;
        gbc_inner.gridy = 3;
        gbc_inner.weightx = 4;
        gbc_inner.weighty = 0.05;
        gbc_inner.insets = new Insets(6, 12, 15, 0);
        CustomComponents.RoundedPanel pick_grid = new CustomComponents.RoundedPanel(
                0, 0, 0, Main.transparent, Main.transparent);
        pick_grid.setLayout(new GridBagLayout());
        right_grid.add(pick_grid, gbc_inner);

        gbc_inner.gridy = 0;
        gbc_inner.weightx = 2;
        check = new JCheckBox("<html><div style='color:#383546; padding-left: 6px;'>"
                + "<b>Remember me</b></div></html>");
        check.setBorder(null);
        check.setFocusPainted(false);
        check.setOpaque(false);
        check.setVerticalAlignment(SwingConstants.TOP);
        pick_grid.add(check, gbc_inner);

        gbc_inner.gridx = 1;
        gbc_inner.weightx = 8;
        JLabel placeholder1 = new JLabel("");
        pick_grid.add(placeholder1, gbc_inner);

        gbc_inner.gridx = 0;
        gbc_inner.gridy = 4;
        gbc_inner.weightx = 10;
        gbc_inner.weighty = 0.125;
        gbc_inner.insets = new Insets(0, 0, 0, 0);
        button = new CustomComponents.CustomButton(
                "Sign In", merriweather, Color.BLACK, Color.WHITE,
                new Color(244, 156, 187), new Color(56, 53, 70),
                new Color(161, 111, 136), 30, 14, Color.WHITE,
                true, 5, false, null, 0, 0, 0);
        button.addActionListener(_ -> Checker());
        right_grid.add(button, gbc_inner);
        outer_grid.add(right_panel, gbc_outer);
        background.add(outer_grid);
        parent.setContentPane(background);
        SwingUtilities.invokeLater(logo_cell::requestFocusInWindow);
    }

    private static void Checker() {
        if (txt1.getText().isEmpty() || txt1.getText().equals("Username or email \r\r") ||
                new String(txt2.getPassword()).isEmpty() ||
                new String(txt2.getPassword()).equals("Password \r\r")) {
            CustomComponents.CustomDialog error = new CustomComponents.CustomDialog(parent,
                    merriweather, 0);
            error.show_dialog("Error", "Details must not be empty!",
                    "Ok", null, null, null);
        } else if (txt1.getText().contains(" ") || new String(txt2.getPassword()).contains(" ")) {
            CustomComponents.CustomDialog error = new CustomComponents.CustomDialog(parent,
                    merriweather, 0);
            error.show_dialog("Error", "Details must not contain spaces!",
                    "Ok", null, null, null);
        } else if (!User.usernameChecker(txt1.getText().toLowerCase(), Main.userdata_file) ||
                !User.emailChecker(txt1.getText(), Main.userdata_file)) {
            List<User> allUser = User.listAllUser(Main.userdata_file);
            boolean correct = false;
            String AccType = "";
            for (User user : allUser) {
                if ((Objects.equals(user.Username, txt1.getText().toLowerCase()) ||
                        Objects.equals(user.Email, txt1.getText())) &&
                        Objects.equals(user.Password, new String(txt2.getPassword()))) {
                    correct = true;
                    AccType = user.AccType;
                    break;
                }
            }
            if (correct) {
                Main.indicator = 1;
                switch (AccType) {
                    case "Administrator" -> Home.indicator = 1;
                    case "Sales Manager" -> Home.indicator = 2;
                    case "Purchase Manager" -> Home.indicator = 3;
                    case "Inventory Manager" -> Home.indicator = 4;
                    case "Finance Manager" -> Home.indicator = 5;
                }
                Buffer logged_in = null;
                for (User user : allUser) {
                    if (Objects.equals(user.Username, txt1.getText().toLowerCase()) ||
                            Objects.equals(user.Email, txt1.getText())) {
                        logged_in = new Buffer(user.UserID, user.Username, user.Password, user.FullName,
                                user.Email, user.Phone, user.AccType, user.DateOfRegis, 1);
                        break;
                    }
                }
                if (check.isSelected() && logged_in != null) {
                        User.modifyUser(logged_in.UserID, logged_in, Main.userdata_file);
                }
                Home.current_user = logged_in;
                Main.PageChanger(parent, merriweather, boldonse);
            } else {
                CustomComponents.CustomDialog error = new CustomComponents.CustomDialog(parent,
                        merriweather, 0);
                error.show_dialog("Error", "<html>Username/email or password<br>is incorrect!</html>",
                        "Ok", null, null, null);
            }
        } else {
            CustomComponents.CustomDialog error = new CustomComponents.CustomDialog(parent,
                    merriweather, 0);
            error.show_dialog("Error", "<html>Username/email or password<br>is incorrect!</html>",
                    "Ok", null, null, null);
        }
    }

    public static void LoginRemembered() {
        User user = User.RememberedUser(Main.userdata_file);
        Buffer logged_in = new Buffer(user.UserID, user.Username, user.Password, user.FullName,
                user.Email, user.Phone, user.AccType, user.DateOfRegis, 1);
        String AccType = logged_in.AccType;
        Main.indicator = 1;
        switch (AccType) {
            case "Administrator" -> Home.indicator = 1;
            case "Sales Manager" -> Home.indicator = 2;
            case "Purchase Manager" -> Home.indicator = 3;
            case "Inventory Manager" -> Home.indicator = 4;
            case "Finance Manager" -> Home.indicator = 5;
        }
        Home.current_user = logged_in;
        Main.PageChanger(parent, merriweather, boldonse);
    }

    public static void UpdateComponentSize(int parent_width, int parent_height) {
        background.updateSize(parent_width, parent_height);
        outer_grid.revalidate();
        outer_grid.repaint();
        logo_cell.repaint();
        logo_text1.setFont(merriweather.deriveFont((float) parent_height / 30));
        logo_text2.setFont(boldonse.deriveFont((float) parent_height  / 25));
        right_title.setFont(merriweather.deriveFont((float) parent_height / 14));
        txt_grid1.ChangeRadius(parent_height / 10);
        txt_grid2.ChangeRadius(parent_height / 10);
        txt1.setFont(merriweather.deriveFont((float) parent_height / 30));
        txt2.setFont(merriweather.deriveFont((float) parent_height / 30));
        check.setFont(merriweather.deriveFont((float) parent_height / 40));
        check.setIcon(new CustomComponents.CustomCheckBoxIcon(30, 3,2,false,
                new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
        check.setSelectedIcon(new CustomComponents.CustomCheckBoxIcon(30, 3,1,true,
                new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
        button.UpdateCustomButton(parent_height / 10, parent_height / 30, null, 0);
        txt_icon1.repaint();
        txt_icon2.repaint();
        hidden.UpdateSize(txt_grid2.getHeight() * 5 / 6, txt_grid2.getHeight());
        hidden.repaint();
        txt2.UpdateStatus(hidden.ReturnImageState());
        txt2.repaint();
    }
}
