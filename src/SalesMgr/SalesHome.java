package SalesMgr;

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

import Admin.*;
import FinanceMgr.FinanceHome;

public class SalesHome {
    public static int indicator = 0;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel side_bar, top_bar, content;
    private static User current_user;
    private static BufferedImage logo, caret_up, caret_down;
    private static CustomComponents.ImageCell logo_cell;
    private static JButton profile;
    private static CustomComponents.CustomProfileIcon profileIcon1, profileIcon2;
    private static CustomComponents.CustomButton home_btn, item_btn, sply_btn, dly_sls_btn, pr_btn, po_btn, profile_drop;
    private static JLabel title;
    private static CustomComponents.CustomPopupMenu profile_drop_menu;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel side_bar, JPanel top_bar, JPanel content, User current_user) {
        try {
            logo = ImageIO.read(new File("images/logo_sidebar.png"));
            caret_up = ImageIO.read(new File("images/caret_up.png"));
            caret_down = ImageIO.read(new File("images/caret_down.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        SalesHome.parent = parent;
        SalesHome.merriweather = merriweather;
        SalesHome.boldonse = boldonse;
        SalesHome.side_bar = side_bar;
        SalesHome.top_bar = top_bar;
        SalesHome.content = content;
        SalesHome.current_user = current_user;
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
        home_btn = new CustomComponents.CustomButton("Home", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        home_btn.addActionListener(_ -> {
            SalesHome.indicator = 0;
            PageChanger();

        });
        side_bar.add(home_btn, gbc_side);

        gbc_side.gridy = 2;
        item_btn = new CustomComponents.CustomButton("Items", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        item_btn.addActionListener(_ -> {
            SalesHome.indicator = 2;
            PageChanger();
        });
        side_bar.add(item_btn, gbc_side);

        gbc_side.gridy = 3;
        sply_btn = new CustomComponents.CustomButton("Suppliers", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        sply_btn.addActionListener(_ -> {
            SalesHome.indicator = 3;
            PageChanger();
        });
        side_bar.add(sply_btn, gbc_side);

        gbc_side.gridy = 4;
        dly_sls_btn = new CustomComponents.CustomButton("Daily Sales", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        dly_sls_btn.addActionListener(_ -> {
            SalesHome.indicator = 4;
            PageChanger();
        });
        side_bar.add(dly_sls_btn, gbc_side);

        gbc_side.gridy = 5;
        pr_btn = new CustomComponents.CustomButton("Purchase\nRequisitions", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 8,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        pr_btn.addActionListener(_ -> {
            SalesHome.indicator = 5;
            PageChanger();
        });
        side_bar.add(pr_btn, gbc_side);

        gbc_side.gridy = 6;
        po_btn = new CustomComponents.CustomButton("Purchase Orders", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        po_btn.addActionListener(_ -> {
            SalesHome.indicator = 6;
            PageChanger();
        });
        side_bar.add(po_btn, gbc_side);

        gbc_side.gridy =7;
        gbc_side.weighty = 4.2;
        JLabel placeholder = new JLabel("");
        side_bar.add(placeholder, gbc_side);

        GridBagConstraints gbc_top = new GridBagConstraints();
        gbc_top.gridx = 0;
        gbc_top.gridy = 0;
        gbc_top.weightx = 9;
        gbc_top.weighty = 1;
        gbc_top.fill =GridBagConstraints.BOTH;
        gbc_top.insets = new Insets(0, 20, 0, 0);
        title = new JLabel(String.format("<html>Welcome, Sales Manager <i>- %s</i></html>",
                Home.current_user.FullName));
        top_bar.add(title, gbc_top);

        gbc_top.gridx = 1;
        gbc_top.weightx = 0.7;
        profileIcon1 = new CustomComponents.CustomProfileIcon(10, false, "Sales Manager", boldonse);
        profile = new JButton(profileIcon1);
        profileIcon2 = new CustomComponents.CustomProfileIcon(10, true, "Sales Manager", boldonse);
        profile.setRolloverIcon(profileIcon2);
        profile.setMargin(new Insets(0, 0, 0, 0));
        profile.setBorderPainted(false);
        profile.setContentAreaFilled(false);
        profile.setFocusPainted(false);
        profile.setPreferredSize(new Dimension(profileIcon1.getIconWidth(), profileIcon1.getIconHeight()));
        profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
        profile.addActionListener(_ -> {
            SalesHome.indicator = 1;
            PageChanger();
        });
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

        List<String> options = new java.util.ArrayList<>(List.of("Check Profile", "Sign Out"));
        List<ActionListener> actions = new java.util.ArrayList<>(List.of(
                e -> {
                    SalesHome.indicator = 1;
                    PageChanger();
                },
                e -> {
                    User.UnrememberAllUser(Main.userdata_file);
                    Main.indicator = 0;
                    SalesHome.indicator = 0;
                    Main.PageChanger(parent, merriweather, boldonse);
                }
        ));

        if (current_user.UserID.substring(2).equals("0000000000")) {
            options.add(1, "Log back to Admin Mode");
            actions.add(1, e -> {
                User admin = User.GetUserById("AD0000000000", Main.userdata_file);
                admin.RememberMe = 1;
                AdmHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, admin);
                Home.indicator = 1;
                Home.PageChanger();
                AdmHome.PageChanger();
                User.UnrememberAllUser(Main.userdata_file);
                User.modifyUser(admin.UserID, admin, Main.userdata_file);
            });
        }

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
        ItemMng.Loader(parent, merriweather, boldonse, content, current_user);
        SalesDashboard.Loader(parent, merriweather, boldonse, content, current_user);
        SupplierMng.Loader(parent, merriweather, boldonse, content, current_user);
        DailySalesMng.Loader(parent, merriweather, boldonse, content, current_user);
        PurchaseRequisitionMng.Loader(parent, merriweather, boldonse, content, current_user);
        PurchaseOrderMng.Loader(parent, merriweather, boldonse, content, current_user);
        PageChanger();
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        switch (indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> Sales Manager Home Page
//    1 -> Profile page
//    2 -> Item page
//    3 -> Suppliers page
//    4 -> Daily sales page
//    5 -> Purchase requisitions page
//    6 -> Purchase orders page
            case 0:
                SalesDashboard.ShowPage();
                title.setText(String.format("<html>Welcome, Sales Manager <i>- %s</i></html>",
                        Home.current_user.FullName));
                break;
            case 1:
                Profile.ShowPage();
                title.setText("Profile");
                break;
            case 2:
                ItemMng.ShowPage();
                title.setText("Item Management Page");
                break;
            case 3:
                SupplierMng.ShowPage();
                title.setText("Supplier Management Page");
                break;
            case 4:
                DailySalesMng.ShowPage();
                title.setText("Daily Item-Wise Sales Management Page");
                break;
            case 5:
                PurchaseRequisitionMng.ShowPage();
                title.setText("Purchase Requisition Management Page");
                break;
            case 6:
                PurchaseOrderMng.ShowPage();
                title.setText("Purchase Order Management Page");
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
            home_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            item_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            sply_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            dly_sls_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            pr_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            po_btn.UpdateCustomButton(0, finalBase_size, null, 0);
            title.setFont(boldonse.deriveFont((float)finalBase_size));
            profile.repaint();
            profileIcon1.UpdateSize((int) (finalBase_size * 2.5));
            profileIcon2.UpdateSize((int) (finalBase_size * 2.5));
            profile_drop.UpdateSize(top_bar.getHeight() / 2, top_bar.getHeight());
            profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
            switch (indicator) {
                case 0:
                    SalesDashboard.UpdateComponentSize(finalBase_size);
                    break;
                case 1:
                    Profile.UpdateComponentSize(finalBase_size);
                    break;
                case 2:
                    ItemMng.UpdateComponentSize(finalBase_size);
                    break;
                case 3:
                    SupplierMng.UpdateComponentSize(finalBase_size);
                    break;
                case 4:
                    DailySalesMng.UpdateComponentSize(finalBase_size);
                    break;

            }
        });
    }
}