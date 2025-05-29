package PurchaseMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.View;
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
import InventoryMgr.Item;

public class PurchaseHome {
    public static int indicator = 2;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel side_bar, top_bar, content;
    private static User current_user;
    private static BufferedImage logo, caret_up, caret_down;
    private static CustomComponents.ImageCell logo_cell;
    private static JButton profile;
    private static CustomComponents.CustomProfileIcon profileIcon1, profileIcon2;
    private static CustomComponents.CustomButton home_page, profile_drop, view_item, view_supplier, view_requisition, generate_po, view_po;
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
        PurchaseHome.parent = parent;
        PurchaseHome.merriweather = merriweather;
        PurchaseHome.boldonse = boldonse;
        PurchaseHome.side_bar = side_bar;
        PurchaseHome.top_bar = top_bar;
        PurchaseHome.content = content;
        PurchaseHome.current_user = current_user;
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

//        gbc_side.gridy = 1;
//        gbc_side.weighty = 0.8;
//        home_page = new CustomComponents.CustomButton("Home", merriweather, Color.WHITE, Color.WHITE,
//                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
//                Main.transparent, false, 5, false, null, 0,
//                0, 0);
//        home_page.addActionListener(_ -> {
//            PurchaseHome.indicator = 0;
//            PageChanger();
//        });
//        side_bar.add(home_page, gbc_side);

        gbc_side.gridy = 2;
        gbc_side.weighty = 0.8;
        view_item = new CustomComponents.CustomButton("View Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        view_item.addActionListener(_ -> {
            PurchaseHome.indicator = 2;
            PageChanger();
        });
        side_bar.add(view_item, gbc_side);

        gbc_side.gridy = 3;
        gbc_side.weighty = 0.8;
        view_supplier = new CustomComponents.CustomButton("View Supplier", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        view_supplier.addActionListener(_ -> {
            PurchaseHome.indicator = 3;
            PageChanger();
        });
        side_bar.add(view_supplier, gbc_side);

        gbc_side.gridy = 4;
        gbc_side.weighty = 0.8;
        view_requisition = new CustomComponents.CustomButton("View Requisition", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        view_requisition.addActionListener(_ ->{
            PurchaseHome.indicator = 4;
            PageChanger();
        });
        side_bar.add(view_requisition, gbc_side);

        gbc_side.gridy = 5;
        gbc_side.weighty = 0.8;
        generate_po = new CustomComponents.CustomButton("Purchase Order", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        generate_po.addActionListener(_ ->{
            PurchaseHome.indicator = 5;
            PageChanger();
        });
        side_bar.add(generate_po, gbc_side);

        gbc_side.gridy = 7;
        gbc_side.weighty = 5.8;
        JLabel placeholder = new JLabel("");
        side_bar.add(placeholder, gbc_side);

        GridBagConstraints gbc_top = new GridBagConstraints();
        gbc_top.gridx = 0;
        gbc_top.gridy = 0;
        gbc_top.weightx = 9;
        gbc_top.weighty = 1;
        gbc_top.fill =GridBagConstraints.BOTH;
        gbc_top.insets = new Insets(0, 20, 0, 0);
        title = new JLabel(String.format("<html>Welcome, Purchase Manager <i>- %s</i></html>",
                Home.current_user.FullName));
        top_bar.add(title, gbc_top);

        gbc_top.gridx = 1;
        gbc_top.weightx = 0.7;
        profileIcon1 = new CustomComponents.CustomProfileIcon(10, false, "Purchase Manager", boldonse);
        profile = new JButton(profileIcon1);
        profileIcon2 = new CustomComponents.CustomProfileIcon(10, true, "Purchase Manager", boldonse);
        profile.setRolloverIcon(profileIcon2);
        profile.setMargin(new Insets(0, 0, 0, 0));
        profile.setBorderPainted(false);
        profile.setContentAreaFilled(false);
        profile.setFocusPainted(false);
        profile.setPreferredSize(new Dimension(profileIcon1.getIconWidth(), profileIcon1.getIconHeight()));
        profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
        profile.addActionListener(_ -> {
            PurchaseHome.indicator = 1;
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
                    PurchaseHome.indicator = 1;
                    PageChanger();
                },
                e -> {
                    User.UnrememberAllUser(Main.userdata_file);
                    Main.indicator = 0;
                    PurchaseHome.indicator = 2;
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
        Welcome.Loader(parent, merriweather, boldonse, content, current_user);
        ViewItems.Loader(parent, merriweather, boldonse, content, current_user);
        ViewSuppliers.Loader(parent, merriweather, boldonse, content, current_user);
        ViewPurchaseRequisition.Loader(parent, merriweather, boldonse, content, current_user);
        GenPurchaseOrder.Loader(parent, merriweather, boldonse, content, current_user);
        PurchaseOrderDetails.Loader(parent, merriweather, boldonse, content, null);
        PurchaseRequisitionDetails.Loader(parent, merriweather, boldonse, content, null);
        ItemDetails.Loader(parent, merriweather, boldonse, content, null);
        SupplierDetails.Loader(parent, merriweather, boldonse, content, null);
        EditPurchaseOrder.Loader(parent, merriweather, boldonse, content, current_user, null);
        AddPRtoPO.Loader(parent, merriweather, boldonse, content, current_user, null);
        PageChanger();
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        ViewPurchaseRequisition.list_length = 10;
        ViewPurchaseRequisition.page_counter = 0;
        ViewPurchaseRequisition.mode = 1;
        GenPurchaseOrder.list_length = 10;
        GenPurchaseOrder.page_counter = 0;
        GenPurchaseOrder.mode = 1;
        ViewItems.list_length = 10;
        ViewItems.page_counter = 0;
        ViewItems.mode = 1;
        switch (indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> Purchase Manager Welcome Page
//    1 -> Profile page
            case 1:
                Profile.ShowPage();
                break;
            case 2:
                ViewItems.ShowPage();
                break;
            case 3:
                ViewSuppliers.ShowPage();
                break;
            case 4:
                ViewPurchaseRequisition.ShowPage();
                break;
            case 5:
                GenPurchaseOrder.ShowPage();
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
            view_item.UpdateCustomButton(0, finalBase_size, null, 0);
            view_supplier.UpdateCustomButton(0, finalBase_size, null, 0);
            view_requisition.UpdateCustomButton(0, finalBase_size, null, 0);
            generate_po.UpdateCustomButton(0, finalBase_size, null, 0);
            title.setFont(boldonse.deriveFont((float)finalBase_size));
            profile.repaint();
            profileIcon1.UpdateSize((int) (finalBase_size * 2.5));
            profileIcon2.UpdateSize((int) (finalBase_size * 2.5));
            profile_drop.UpdateSize(top_bar.getHeight() / 2, top_bar.getHeight());
            profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
            switch (indicator) {
                case 0:
                    Welcome.UpdateComponentSize(finalBase_size);
                    break;
                case 1:
                    Profile.UpdateComponentSize(finalBase_size);
                    break;
            }
        });
    }
}
