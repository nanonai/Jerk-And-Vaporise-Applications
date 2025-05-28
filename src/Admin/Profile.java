package Admin;

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
    public static User current_user;
    private static BufferedImage icon_username, icon_fullname, icon_password, icon_email, icon_phone,
            icon_hide, icon_show, icon_exit1, icon_exit2;
    private static JLabel profile_pic, id_label, job_label, date_label, username_label, fullname_label,
            password_label, email_label, phone_label;
    private static CustomComponents.ImageCell username_img, fullname_img, password_img, email_img, phone_img;
    private static CustomComponents.CustomButton exit, hidden;
    private static CustomComponents.CustomProfileIcon profileIcon;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        try {
            icon_username = ImageIO.read(new File("images/user_dark.png"));
            icon_fullname = ImageIO.read(new File("images/name_dark.png"));
            icon_password = ImageIO.read(new File("images/lock_dark.png"));
            icon_email = ImageIO.read(new File("images/email_dark.png"));
            icon_phone = ImageIO.read(new File("images/phone_dark.png"));
            icon_hide = ImageIO.read(new File("images/hidden_dark.png"));
            icon_show = ImageIO.read(new File("images/show_dark.png"));
            icon_exit1 = ImageIO.read(new File("images/out.png"));
            icon_exit2 = ImageIO.read(new File("images/out_hover.png"));
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 10, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        exit = new CustomComponents.CustomButton("", merriweather, null, null,
                null, null, null, 0, 0, Main.transparent, false, 6,
                true, icon_exit1, 1,
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
                exit.UpdateCustomButton(0, 0, icon_exit2, 1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.UpdateCustomButton(0, 0, icon_exit1, 1);
            }
        });
        content.add(exit, gbc);

        gbc.gridx = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridheight = 5;
        profileIcon = new CustomComponents.CustomProfileIcon(300, false, current_user.AccType, boldonse);
        profile_pic = new JLabel(profileIcon);
        content.add(profile_pic, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        username_img = new CustomComponents.ImageCell(icon_username, 0.35, 5);
        content.add(username_img, gbc);

        gbc.gridx = 3;
        username_label = new JLabel(String.format("<html><b>Username: <i>%s</i></b></html>", current_user.Username));
        content.add(username_label, gbc);

        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        fullname_img = new CustomComponents.ImageCell(icon_fullname, 0.4, 5);
        content.add(fullname_img, gbc);

        gbc.gridx = 3;
        fullname_label = new JLabel(String.format("<html><b>Fullname: <i>%s</i></b></html>", current_user.FullName));
        content.add(fullname_label, gbc);

        gbc.gridy = 2;
        gbc.gridx = 2;
        password_img = new CustomComponents.ImageCell(icon_password, 0.4, 5);
        content.add(password_img, gbc);

        gbc.gridx = 3;
        int password_len = current_user.Password.length();
        String hidden_value = "*".repeat(password_len);
        password_label = new JLabel(String.format("<html><b>Password: <i>%s</i></b></html>", hidden_value));
        content.add(password_label, gbc);

        gbc.gridx = 4;
//        gbc.insets = new Insets(4, 0, 0, 0);
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
        content.add(hidden, gbc);

        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        email_img = new CustomComponents.ImageCell(icon_email, 0.4, 5);
        content.add(email_img, gbc);

        gbc.gridx = 3;
        email_label = new JLabel(String.format("<html><b>Email: <i>%s</i></b></html>", current_user.Email));
        content.add(email_label, gbc);

        gbc.gridy = 4;
        gbc.gridx = 2;
        phone_img = new CustomComponents.ImageCell(icon_phone, 0.35, 5);
        content.add(phone_img, gbc);

        gbc.gridx = 3;
        String format_phone = String.format("+60 %s-%s %s", current_user.Phone.substring(1, 3),
                current_user.Phone.substring(3, 6), current_user.Phone.substring(6));
        phone_label = new JLabel(String.format("<html><b>Phone No.: <i>%s</i></b></html>", format_phone));
        content.add(phone_label, gbc);

        gbc.gridx = 1;
        gbc.weighty = 4;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 0, 0);
        id_label = new JLabel(String.format("<html><b>User ID: <i>%s</i></b></html>", current_user.UserID));
        content.add(id_label, gbc);

        gbc.gridy = 6;
        job_label = new JLabel(String.format("<html><b>Job Title: <i>%s</i></b></html>", current_user.AccType));
        content.add(job_label, gbc);

        gbc.gridy = 7;
        date_label = new JLabel(String.format("<html><b>Date of Registration: <i>%s</i></b></html>", current_user.DateOfRegis));
        content.add(date_label, gbc);
    }

    public static void UpdateComponentSize(int base_size) {
        exit.UpdateSize((int) (base_size * 2), (int) (base_size * 2));
        profileIcon.UpdateSize((int) (base_size * 20));
        profile_pic.repaint();
        id_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        job_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        date_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        username_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        fullname_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        password_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        email_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        phone_label.setFont(merriweather.deriveFont((float) (base_size * 1.1)));
        username_img.repaint();
        fullname_img.repaint();
        password_img.repaint();
        email_img.repaint();
        phone_img.repaint();
        hidden.UpdateSize((int) (base_size * 4.5), (int) (base_size * 4.5));
    }
}
