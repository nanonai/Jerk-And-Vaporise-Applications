package Common;

import Admin.AdmHome;

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
    private static JPanel content, left_panel, middle_panel, right_panel;
    public static Buffer current_user;
    private static BufferedImage icon_username, icon_fullname, icon_password, icon_email, icon_phone,
            icon_exit, icon_exit_hover;
    private static JLabel profile_pic, id_label, job_label, date_label, username_label, fullname_label,
            password_label, email_label, phone_label;
    private static CustomComponents.CustomButton exit;
    private static CustomComponents.CustomProfileIcon profileIcon1;

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
        gbc_content.weightx = 1;
        left_panel = new JPanel(new GridBagLayout());
        content.add(left_panel, gbc_content);

        gbc_content.gridx = 1;
        gbc_content.insets = new Insets(20, 0, 20, 20);
        gbc_content.weightx = 9;
        right_panel = new JPanel(new GridBagLayout());
        content.add(right_panel, gbc_content);

        GridBagConstraints gbc_inner = new GridBagConstraints();
        gbc_inner.gridx = 0;
        gbc_inner.gridy = 0;
        gbc_inner.fill = GridBagConstraints.BOTH;
        gbc_content.weighty = 1;
        gbc_content.weightx = 1;
        exit = new CustomComponents.CustomButton("", merriweather, null, null,
                null, null, null, 0, 0, Main.transparent, false, 5,
                true, icon_exit, 0.6,
                left_panel.getWidth(), left_panel.getWidth());
        exit.addActionListener(_ -> {
            AdmHome.indicator = 0;
            AdmHome.PageChanger();
        });
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.UpdateCustomButton(0, 0, icon_exit_hover, 0.6);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.UpdateCustomButton(0, 0, icon_exit, 0.6);
            }
        });
        left_panel.add(exit, gbc_inner);

        gbc_inner.gridy = 1;
        gbc_content.weighty = 10;
        JLabel placeholder1 = new JLabel("");
        left_panel.add(placeholder1, gbc_inner);
    }
}
