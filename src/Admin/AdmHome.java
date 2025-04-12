package Admin;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import Common.*;

public class AdmHome {
    public static int indicator = 0;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel side_bar, top_bar, content;
    public static Buffer current_user;
    private static BufferedImage logo, caret_up, caret_down;
    private static CustomComponents.ImageCell logo_cell;
    private static JButton profile;
    private static CustomComponents.CustomProfileIcon profileIcon1, profileIcon2;
    private static CustomComponents.CustomButton user_management, profile_drop;
    private static JLabel title;
    private static CustomComponents.CustomPopupMenu profile_drop_menu;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel side_bar, JPanel top_bar, JPanel content, Buffer current_user) {
        try {
            logo = ImageIO.read(new File("images/logo_sidebar.png"));
            caret_up = ImageIO.read(new File("images/caret_up.png"));
            caret_down = ImageIO.read(new File("images/caret_down.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        AdmHome.parent = parent;
        AdmHome.merriweather = merriweather;
        AdmHome.boldonse = boldonse;
        AdmHome.side_bar = side_bar;
        AdmHome.top_bar = top_bar;
        AdmHome.content = content;
        AdmHome.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc_side = new GridBagConstraints();
        gbc_side.gridx = 0;
        gbc_side.gridy = 0;
        gbc_side.weightx = 1;
        gbc_side.weighty = 1.05;
        gbc_side.fill =GridBagConstraints.BOTH;
        logo_cell = new CustomComponents.ImageCell(logo, 0.8, 5);
        side_bar.add(logo_cell, gbc_side);

        gbc_side.gridy = 1;
        gbc_side.weighty = 0.8;
        user_management = new CustomComponents.CustomButton("Manage User", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(user_management, gbc_side);

        gbc_side.gridy = 2;
        gbc_side.weighty = 8.2;
        JLabel placeholder = new JLabel("");
        side_bar.add(placeholder, gbc_side);

        GridBagConstraints gbc_top = new GridBagConstraints();
        gbc_top.gridx = 0;
        gbc_top.gridy = 0;
        gbc_top.weightx = 9;
        gbc_top.weighty = 1;
        gbc_top.fill =GridBagConstraints.BOTH;
        gbc_top.insets = new Insets(0, 20, 0, 0);
        title = new JLabel(String.format("<html>Welcome, Administrator <i>-%s</i></html>",
                Home.current_user.FullName));
        top_bar.add(title, gbc_top);

        gbc_top.gridx = 1;
        gbc_top.weightx = 0.7;
        profileIcon1 = new CustomComponents.CustomProfileIcon(10, false, "Administrator", boldonse);
        profile = new JButton(profileIcon1);
        profileIcon2 = new CustomComponents.CustomProfileIcon(10, true, "Administrator", boldonse);
        profile.setRolloverIcon(profileIcon2);
        profile.setMargin(new Insets(0, 0, 0, 0));
        profile.setBorderPainted(false);
        profile.setContentAreaFilled(false);
        profile.setFocusPainted(false);
        profile.setPreferredSize(new Dimension(profileIcon1.getIconWidth(), profileIcon1.getIconHeight()));
        profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
        top_bar.add(profile, gbc_top);

        gbc_top.gridx = 2;
        gbc_top.weightx = 0.01;
        gbc_top.insets = new Insets(0, 0, 0, 0);
        profile_drop = new CustomComponents.CustomButton("", merriweather, null, null,
                null, null, null, 0, 0, Main.transparent, false, 6,
                true, caret_down, 0.6,
                top_bar.getHeight() / 2, top_bar.getHeight());
        profile_drop.addActionListener(_ -> {
            if (profile_drop.ReturnImageState()) {
                profile_drop.UpdateCustomButton(0, 0, caret_up, 0.6);
            } else {
                profile_drop.UpdateCustomButton(0, 0, caret_down, 0.6);
            }
            profile_drop.UpdateSize(top_bar.getHeight() / 2, top_bar.getHeight());
        });
        top_bar.add(profile_drop, gbc_top);

        List<String> options = List.of("Check Profile", "Sign Out");
        List<ActionListener> actions = List.of(
                e -> {
                    AdmHome.indicator = 1;
                    PageChanger();
                },
                e -> {
                    User.UnrememberAllUser(Main.userdata_file);
                    Main.indicator = 0;
                    AdmHome.indicator = 0;
                    Main.PageChanger(parent, merriweather, boldonse);
                }
        );

        SwingUtilities.invokeLater(() -> {
            profile_drop_menu = new CustomComponents.CustomPopupMenu(
                    profile_drop,
                    options,
                    actions,
                    merriweather.deriveFont(title.getHeight() / 3F),
                    1
            );

            profile_drop_menu.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    profile_drop.UpdateCustomButton(0, 0, caret_down, 0.6);
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    profile_drop.UpdateCustomButton(0, 0, caret_down, 0.6);
                }
            });
        });

        content.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                UpdateComponentSize(parent.getWidth(), parent.getHeight());
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                UpdateComponentSize(parent.getWidth(), parent.getHeight());
            }
        });

        Profile.Loader(parent, merriweather, boldonse, content, current_user);
        PageChanger();
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        switch (indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> Administrator Welcome Page
//    1 -> Profile page
            case 0:
                break;
            case 1:
                Profile.ShowPage();
                break;
        }
        UpdateComponentSize(parent.getWidth(), parent.getHeight());
    }

    public static void UpdateComponentSize(int parent_width, int parent_height) {
        int base_size = 0;
        if (parent_width >= parent_height) {
            base_size = parent_height / 40;
        } else {
            base_size = parent_width / 30;
        }
        int finalBase_size = base_size;
        SwingUtilities.invokeLater(() -> {
            logo_cell.repaint();
            user_management.UpdateCustomButton(0, finalBase_size, null, 0);
            title.setFont(boldonse.deriveFont((float)finalBase_size));
            profile.repaint();
            profileIcon1.UpdateSize((int) (finalBase_size * 2.5));
            profileIcon2.UpdateSize((int) (finalBase_size * 2.5));
            profile_drop.UpdateSize(top_bar.getHeight() / 2, top_bar.getHeight());
            profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
            switch (indicator) {
                case 0:
                    break;
                case 1:
                    Profile.UpdateComponentSize(finalBase_size);
                    break;
            }
        });
    }
}
