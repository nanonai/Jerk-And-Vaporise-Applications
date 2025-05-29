package Admin;

import SalesMgr.*;
import PurchaseMgr.*;
import InventoryMgr.*;
import FinanceMgr.*;
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

public class AdmHome {
    public static int indicator = 0;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel side_bar, top_bar, content;
    private static User current_user;
    private static BufferedImage logo, caret_up, caret_down;
    private static CustomComponents.ImageCell logo_cell;
    private static JButton profile;
    private static CustomComponents.CustomProfileIcon profileIcon1, profileIcon2;
    private static CustomComponents.CustomButton home_page, user_management, sales_management,
            purchase_management, inventory_management, finance_management, profile_drop;
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
        home_page = new CustomComponents.CustomButton("Home Page", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        home_page.addActionListener(_ -> {
            AdmHome.indicator = 0;
            PageChanger();
        });
        side_bar.add(home_page, gbc_side);

        gbc_side.gridy = 2;
        gbc_side.weighty = 0.8;
        user_management = new CustomComponents.CustomButton("Manage User", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        user_management.addActionListener(_ -> {
            AdmHome.indicator = 2;
            UserMng.list_length = 10;
            UserMng.page_counter = 0;
            UserMng.filter = 0;
            UserMng.mode = 1;
            PageChanger();
        });
        side_bar.add(user_management, gbc_side);

        gbc_side.gridy = 3;
        gbc_side.weighty = 0.8;
        sales_management = new CustomComponents.CustomButton("Sales Mode", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        sales_management.addActionListener(_ -> {
            boolean go = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter sales manager mode?",
                    "Confirmation",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (go) {
                AdmHome.indicator = 0;
                User sales = User.GetUserById("SM0000000000", Main.userdata_file);
                sales.RememberMe = 1;
                SalesHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, sales);
                Home.indicator = 2;
                Home.PageChanger();
                SalesHome.indicator = 0;
                SalesHome.PageChanger();
                User.UnrememberAllUser(Main.userdata_file);
                User.ModifyUser(sales.UserID, sales, Main.userdata_file);
            }
        });
        side_bar.add(sales_management, gbc_side);

        gbc_side.gridy = 4;
        gbc_side.weighty = 0.8;
        purchase_management = new CustomComponents.CustomButton("Purchase Mode", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        purchase_management.addActionListener(_ -> {
            boolean go = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter purchase manager mode?",
                    "Confirmation",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (go) {
                AdmHome.indicator = 0;
                User purchase = User.GetUserById("PM0000000000", Main.userdata_file);
                purchase.RememberMe = 1;
                PurchaseHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, purchase);
                Home.indicator = 3;
                Home.PageChanger();
                PurchaseHome.indicator = 2;
                PurchaseHome.PageChanger();
                User.UnrememberAllUser(Main.userdata_file);
                User.ModifyUser(purchase.UserID, purchase, Main.userdata_file);
            }
        });
        side_bar.add(purchase_management, gbc_side);

        gbc_side.gridy = 5;
        gbc_side.weighty = 0.8;
        inventory_management = new CustomComponents.CustomButton("Inventory Mode", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        inventory_management.addActionListener(_ -> {
            boolean go = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter inventory manager mode?",
                    "Confirmation",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (go) {
                AdmHome.indicator = 0;
                User inventory = User.GetUserById("IM0000000000", Main.userdata_file);
                inventory.RememberMe = 1;
                InventoryHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, inventory);
                Home.indicator = 4;
                Home.PageChanger();
                InventoryHome.indicator = 0;
                InventoryHome.PageChanger();
                User.UnrememberAllUser(Main.userdata_file);
                User.ModifyUser(inventory.UserID, inventory, Main.userdata_file);
            }
        });
        side_bar.add(inventory_management, gbc_side);

        gbc_side.gridy = 6;
        gbc_side.weighty = 0.8;
        finance_management = new CustomComponents.CustomButton("Finance Mode", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 20,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        finance_management.addActionListener(_ -> {
            boolean go = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter finance manager mode?",
                    "Confirmation",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (go) {
                AdmHome.indicator = 0;
                User Finance = User.GetUserById("FM0000000000", Main.userdata_file);
                Finance.RememberMe = 1;
                FinanceHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, Finance);
                Home.indicator = 5;
                FinanceHome.indicator = 1;
                Home.PageChanger();
                FinanceHome.PageChanger();
                User.UnrememberAllUser(Main.userdata_file);
                User.ModifyUser(Finance.UserID, Finance, Main.userdata_file);
            }
        });
        side_bar.add(finance_management, gbc_side);

        gbc_side.gridy = 7;
        gbc_side.weighty = 4.2;
        JLabel placeholder = new JLabel("");
        side_bar.add(placeholder, gbc_side);

        GridBagConstraints gbc_top = new GridBagConstraints();
        gbc_top.gridx = 0;
        gbc_top.gridy = 0;
        gbc_top.weightx = 9;
        gbc_top.weighty = 1;
        gbc_top.fill = GridBagConstraints.BOTH;
        gbc_top.insets = new Insets(0, 20, 0, 0);
        title = new JLabel(String.format("<html>Welcome, Administrator <i>- %s</i></html>",
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
        profile.addActionListener(_ -> {
            AdmHome.indicator = 1;
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

        List<String> options = List.of("Check Profile", "Sign Out");
        List<ActionListener> actions = List.of(
                _ -> {
                    AdmHome.indicator = 1;
                    PageChanger();
                },
                _ -> {
                    User.UnrememberAllUser(Main.userdata_file);
                    Main.indicator = 0;
                    AdmHome.indicator = 0;
                    Main.PageChanger(parent);
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

        Dashboard.Loader(merriweather, content, current_user);
        Profile.Loader(merriweather, boldonse, content, current_user);
        UserMng.Loader(parent, merriweather, content, current_user);
        PageChanger();
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        home_page.UpdateText("Home Page");
        user_management.UpdateText("Manage User");
        sales_management.UpdateText("Sales Mode");
        purchase_management.UpdateText("Purchase Mode");
        inventory_management.UpdateText("Inventory Mode");
        finance_management.UpdateText("Finance Mode");
        switch (indicator) {
            case 0:
                Dashboard.ShowPage();
                home_page.UpdateText("             Home Page             ");
                user_management.UpdateText( "             Manage User             ");
                sales_management.UpdateText("             Sales Mode             ");
                purchase_management.UpdateText("             Purchase Mode             ");
                inventory_management.UpdateText("             Inventory Mode             ");
                finance_management.UpdateText("             Finance Mode             ");
                title.setText(String.format("<html>Welcome, Administrator <i>- %s</i></html>",
                        Home.current_user.FullName));
                break;
            case 1:
                Profile.ShowPage();
                title.setText("Profile");
                home_page.UpdateText("      Home Page      ");
                user_management.UpdateText("      Manage User      ");
                sales_management.UpdateText("      Sales Mode      ");
                purchase_management.UpdateText("      Purchase Mode       ");
                inventory_management.UpdateText("      Inventory Mode      ");
                finance_management.UpdateText("      Finance Mode      ");
                break;
            case 2:
                UserMng.ShowPage();
                title.setText("User Management Page");
                home_page.UpdateText("   Home Page   ");
                user_management.UpdateText("    Manage User   ");
                sales_management.UpdateText("    Sales Mode    ");
                purchase_management.UpdateText("    Purchase Mode     ");
                inventory_management.UpdateText("    Inventory Mode    ");
                finance_management.UpdateText("    Finance Mode    ");
                break;
        }
    }

    public static void UpdateComponentSize(int parent_width, int parent_height) {
        int base_size;
        if (parent_width >= parent_height) {
            base_size = parent_height / 40;
        } else {
            base_size = parent_width / 30;
        }
        int finalBase_size = base_size;
        SwingUtilities.invokeLater(() -> {
            logo_cell.repaint();
            home_page.UpdateCustomButton(0, finalBase_size, null, 0);
            user_management.UpdateCustomButton(0, finalBase_size, null, 0);
            sales_management.UpdateCustomButton(0, finalBase_size, null, 0);
            purchase_management.UpdateCustomButton(0, finalBase_size, null, 0);
            inventory_management.UpdateCustomButton(0, finalBase_size, null, 0);
            finance_management.UpdateCustomButton(0, finalBase_size, null, 0);
            title.setFont(boldonse.deriveFont((float) finalBase_size));
            profileIcon1.UpdateSize((int) (finalBase_size * 2.5));
            profileIcon2.UpdateSize((int) (finalBase_size * 2.5));
            profile_drop.UpdateSize(top_bar.getHeight() / 2, top_bar.getHeight());
            profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
            profile.repaint();
            switch (indicator) {
                case 0:
                    Dashboard.UpdateComponentSize(finalBase_size);
                    break;
                case 1:
                    Profile.UpdateComponentSize(finalBase_size);
                    break;
                case 2:
                    UserMng.UpdateComponentSize(finalBase_size);
                    break;
            }
        });
    }
}
