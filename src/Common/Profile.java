package Common;

import Admin.*;
import SalesMgr.*;
import PurchaseMgr.*;
import InventoryMgr.*;
import FinanceMgr.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Profile {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    private static BufferedImage icon_username, icon_fullname, icon_password, icon_email, icon_phone,
            icon_exit, icon_exit_hover, icon_hide, icon_show;
    private static JLabel profile_pic, id_label, job_label, date_label, username_label, fullname_label,
            password_label, email_label, phone_label;
    private static CustomComponents.ImageCell username_img, fullname_img, password_img, email_img, phone_img;
    private static CustomComponents.CustomButton exit, hidden;
    private static CustomComponents.CustomProfileIcon profileIcon;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        try {
            icon_username = ImageIO.read(new File("images/user_dark.png"));
            icon_fullname = ImageIO.read(new File("images/name_dark.png"));
            icon_password = ImageIO.read(new File("images/lock_dark.png"));
            icon_email = ImageIO.read(new File("images/email_dark.png"));
            icon_phone = ImageIO.read(new File("images/phone_dark.png"));
            icon_exit = ImageIO.read(new File("images/out.png"));
            icon_exit_hover = ImageIO.read(new File("images/out_hover.png"));
            icon_hide = ImageIO.read(new File("images/hidden_dark.png"));
            icon_show = ImageIO.read(new File("images/show_dark.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Profile.parent = parent;
        Profile.merriweather = merriweather;
        Profile.boldonse = boldonse;
        Profile.content = content;
        Profile.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc_content = new GridBagConstraints();
        gbc_content.gridx = 0;
        gbc_content.gridy = 0;
        gbc_content.insets = new Insets(20, 20, 20, 20);
        gbc_content.fill = GridBagConstraints.BOTH;
        gbc_content.weighty = 1;
        gbc_content.weightx = 0.01;
        JPanel left_panel = new JPanel(new GridBagLayout());
        left_panel.setOpaque(false);
        content.add(left_panel, gbc_content);

        gbc_content.gridx = 1;
        gbc_content.insets = new Insets(20, 0, 20, 20);
        gbc_content.weightx = 1;
        JPanel middle_panel = new JPanel(new GridBagLayout());
        middle_panel.setOpaque(false);
        content.add(middle_panel, gbc_content);

        gbc_content.gridx = 2;
        gbc_content.weightx = 5;
        JPanel right_panel = new JPanel(new GridBagLayout());
        right_panel.setOpaque(false);
        content.add(right_panel, gbc_content);

        GridBagConstraints gbc_inner = new GridBagConstraints();
        gbc_inner.gridx = 0;
        gbc_inner.gridy = 0;
        gbc_inner.fill = GridBagConstraints.BOTH;
        gbc_inner.weighty = 1;
        gbc_inner.weightx = 1;
        exit = new CustomComponents.CustomButton("", merriweather, null, null,
                null, null, null, 0, 0, Main.transparent, false, 6,
                true, icon_exit, 1,
                40, 40);
        exit.addActionListener(_ -> {
            switch (Home.indicator) {
                case 1:
                    AdmHome.indicator = 0;
                    AdmHome.PageChanger();
                    break;
                case 2:
                    SalesHome.indicator = 0;
                    SalesHome.PageChanger();
                    break;
                case 3:
                    PurchaseHome.indicator = 0;
                    PurchaseHome.PageChanger();
                    break;
                case 4:
                    InventoryHome.indicator = 0;
                    InventoryHome.PageChanger();
                    break;
                case 5:
                    FinanceHome.indicator = 0;
                    FinanceHome.PageChanger();
                    break;
            }
        });
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.UpdateCustomButton(0, 0, icon_exit_hover, 1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.UpdateCustomButton(0, 0, icon_exit, 1);
            }
        });
        left_panel.add(exit, gbc_inner);

        gbc_inner.gridy = 1;
        gbc_inner.weighty = 10;
        JLabel placeholder1 = new JLabel("");
        left_panel.add(placeholder1, gbc_inner);

        gbc_inner.gridy = 0;
        gbc_inner.weighty = 4;
        profileIcon = new CustomComponents.CustomProfileIcon(300, false, current_user.AccType, boldonse);
        profile_pic = new JLabel(profileIcon);
        profile_pic.setSize(300, 300);
        middle_panel.add(profile_pic, gbc_inner);

        gbc_inner.gridy = 1;
        gbc_inner.weighty = 2;
        id_label = new JLabel(String.format("<html><b>User ID: <i>%s</i></b></html>", current_user.UserID));
        middle_panel.add(id_label, gbc_inner);

        gbc_inner.gridy = 2;
        gbc_inner.weighty = 2;
        job_label = new JLabel(String.format("<html><b>Job Title: <i>%s</i></b></html>", current_user.AccType));
        middle_panel.add(job_label, gbc_inner);

        gbc_inner.gridy = 3;
        gbc_inner.weighty = 2;
        date_label = new JLabel(String.format("<html><b>Date of Registration: <i>%s</i></b></html>", current_user.DateOfRegis));
        middle_panel.add(date_label, gbc_inner);

        gbc_inner.gridy = 4;
        gbc_inner.weighty = 4;
        JLabel placeholder2 = new JLabel("");
        middle_panel.add(placeholder2, gbc_inner);

        gbc_inner.gridy = 0;
        gbc_inner.weightx = 1;
        gbc_inner.weighty = 2;
        username_img = new CustomComponents.ImageCell(icon_username, 0.4, 5);
        right_panel.add(username_img, gbc_inner);

        gbc_inner.gridy = 1;
        fullname_img = new CustomComponents.ImageCell(icon_fullname, 0.5, 5);
        right_panel.add(fullname_img, gbc_inner);

        gbc_inner.gridy = 2;
        password_img = new CustomComponents.ImageCell(icon_password, 0.5, 5);
        right_panel.add(password_img, gbc_inner);

        gbc_inner.gridy = 3;
        email_img = new CustomComponents.ImageCell(icon_email, 0.5, 5);
        right_panel.add(email_img, gbc_inner);

        gbc_inner.gridy = 4;
        phone_img = new CustomComponents.ImageCell(icon_phone, 0.45, 5);
        right_panel.add(phone_img, gbc_inner);

        gbc_inner.gridy = 5;
        gbc_inner.weighty = 8;
        JLabel placeholder3 = new JLabel("");
        right_panel.add(placeholder3, gbc_inner);

        gbc_inner.gridx = 1;
        gbc_inner.gridy = 0;
        gbc_inner.weighty = 2;
        username_label = new JLabel(String.format("<html><b>Username: <i>%s</i></b></html>", current_user.Username));
        right_panel.add(username_label, gbc_inner);

        gbc_inner.gridy = 1;
        fullname_label = new JLabel(String.format("<html><b>Fullname: <i>%s</i></b></html>", current_user.FullName));
        right_panel.add(fullname_label, gbc_inner);

        gbc_inner.gridy = 2;
        int password_len = current_user.Password.length();
        String hidden_value = "*".repeat(password_len);
        password_label = new JLabel(String.format("<html><b>Password: <i>%s</i></b></html>", hidden_value));
        right_panel.add(password_label, gbc_inner);

        gbc_inner.gridy = 3;
        email_label = new JLabel(String.format("<html><b>Email: <i>%s</i></b></html>", current_user.Email));
        right_panel.add(email_label, gbc_inner);

        gbc_inner.gridy = 4;
        phone_label = new JLabel(String.format("<html><b>Phone No.: <i>%s</i></b></html>", current_user.Phone));
        right_panel.add(phone_label, gbc_inner);

        gbc_inner.gridx = 2;
        gbc_inner.gridy = 2;
        hidden = new CustomComponents.CustomButton("", merriweather,
                Main.transparent, Main.transparent, Main.transparent, Main.transparent, Main.transparent,
                0, 0, Main.transparent, false, 4,
                true, icon_hide, 0.5,
                0, 0);
        hidden.addActionListener(_ -> {
            if (hidden.ReturnImageState()) {
                hidden.UpdateCustomButton(0, 0, icon_show, 0.5);
                password_label.setText(String.format("<html><b>Password: <i>%s</i></b></html>",
                        current_user.Password));
                password_label.repaint();
            } else {
                hidden.UpdateCustomButton(0, 0, icon_hide, 0.5);
                password_label.setText(String.format("<html><b>Password: <i>%s</i></b></html>",
                        hidden_value));
                password_label.repaint();
            }
        });
        right_panel.add(hidden, gbc_inner);
    }

    public static void UpdateComponentSize(int base_size) {
        exit.UpdateSize((int) (base_size * 2), (int) (base_size * 2));
        profileIcon.UpdateSize((int) (base_size * 17));
        profile_pic.setSize(profileIcon.getIconWidth(), profileIcon.getIconHeight());
        profile_pic.repaint();
        id_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        job_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        date_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        username_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        fullname_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        password_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        email_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        phone_label.setFont(merriweather.deriveFont((float) (base_size * 1.2)));
        username_img.repaint();
        fullname_img.repaint();
        password_img.repaint();
        email_img.repaint();
        phone_img.repaint();
        hidden.UpdateSize((int) (base_size * 4), (int) (base_size * 4));
    }
}
