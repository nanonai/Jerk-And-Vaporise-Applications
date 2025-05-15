package FinanceMgr;

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

public class FinanceHome {
    public static int indicator = 0;
    private static JFrame parent;
    private static Font merriweather, boldonse;

    private static JPanel side_bar, top_bar, content;
    public static BufferForUser current_user;
    private static BufferedImage logo, caret_up, caret_down;
    private static CustomComponents.ImageCell logo_cell;
    private static JButton profile;
    private static CustomComponents.CustomProfileIcon profileIcon1, profileIcon2;
    private static CustomComponents.CustomButton purchaseOrder, profile_drop , inventory,payment, purchaseRequisition, report;
    private static JLabel title;
    private static CustomComponents.CustomPopupMenu profile_drop_menu;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel side_bar, JPanel top_bar, JPanel content, BufferForUser current_user) {
        try {
            logo = ImageIO.read(new File("images/logo_sidebar.png"));
            caret_up = ImageIO.read(new File("images/caret_up.png"));
            caret_down = ImageIO.read(new File("images/caret_down.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        FinanceHome.parent = parent;
        FinanceHome.merriweather = merriweather;
        FinanceHome.boldonse = boldonse;
        FinanceHome.side_bar = side_bar;
        FinanceHome.top_bar = top_bar;
        FinanceHome.content = content;
        FinanceHome.current_user = current_user;
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

        // purchase order button
        gbc_side.gridy = 1;
        gbc_side.weighty = 0.8;
        purchaseOrder = new CustomComponents.CustomButton("Purchase Order", merriweather, Color.WHITE, Color.BLACK,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(purchaseOrder, gbc_side);
        purchaseOrder.addActionListener(_ -> {
                    FinanceHome.indicator = 2;
                    PageChanger();
                });

        //purchaseRequisition
        gbc_side.gridy = 2;
        gbc_side.weighty = 0.8;
        purchaseRequisition = new CustomComponents.CustomButton("Purchase Req", merriweather,Color.WHITE, Color.BLACK,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(purchaseRequisition, gbc_side);
        purchaseRequisition.addActionListener(_ -> {
            FinanceHome.indicator = 3;
            PageChanger();
        });

        // View Inventory button
        gbc_side.gridy = 3;
        gbc_side.weighty = 0.8;
        inventory = new CustomComponents.CustomButton("Inventory", merriweather, Color.WHITE, Color.BLACK,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(inventory, gbc_side);
        inventory.addActionListener(_ -> {
            FinanceHome.indicator = 4;
            PageChanger();
        });

        // payment button
        gbc_side.gridy = 4;
        gbc_side.weighty = 0.8;
        payment = new CustomComponents.CustomButton("Payment", merriweather, Color.WHITE, Color.BLACK,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(payment, gbc_side);
        payment.addActionListener(_ -> {
            FinanceHome.indicator = 5;
            PageChanger();
        });

        //report
        gbc_side.gridy = 5;
        gbc_side.weighty = 0.8;
        report = new CustomComponents.CustomButton("Report", merriweather, Color.WHITE, Color.BLACK,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 14,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        side_bar.add(report, gbc_side);
        report.addActionListener(_ -> {
            FinanceHome.indicator = 6;
            PageChanger();
        });

        // label of welcome
        GridBagConstraints gbc_top = new GridBagConstraints();
        gbc_top.gridx = 0;
        gbc_top.gridy = 0;
        gbc_top.weightx = 9;
        gbc_top.weighty = 1;
        gbc_top.fill =GridBagConstraints.BOTH;
        gbc_top.insets = new Insets(0, 20, 0, 0);
        title = new JLabel(String.format("<html>Welcome, Finance Manager <i>- %s</i></html>",
                Home.current_user.FullName));
        top_bar.add(title, gbc_top);

        // profile button
        gbc_top.gridx = 1;
        gbc_top.weightx = 0.7;
        profileIcon1 = new CustomComponents.CustomProfileIcon(10, false, "Finance Manager", boldonse);
        profile = new JButton(profileIcon1);
        profileIcon2 = new CustomComponents.CustomProfileIcon(10, true, "Finance Manager", boldonse);
        profile.setRolloverIcon(profileIcon2);
        profile.setMargin(new Insets(0, 0, 0, 0));
        profile.setBorderPainted(false);
        profile.setContentAreaFilled(false);
        profile.setFocusPainted(false);
        profile.setPreferredSize(new Dimension(profileIcon1.getIconWidth(), profileIcon1.getIconHeight()));
        profile.setSize(profileIcon1.getIconWidth(), profileIcon1.getIconHeight());
        profile.addActionListener(_ -> {
            FinanceHome.indicator = 1;
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
                e -> {
                    FinanceHome.indicator = 1;
                    PageChanger();
                },
                e -> {
                    User.UnrememberAllUser(Main.userdata_file);
                    Main.indicator = 0;
                    FinanceHome.indicator = 0;
                    Main.PageChanger(parent, merriweather, boldonse);
                }
        );

        SwingUtilities.invokeLater(() -> { //ensure things are not re-size before its loaded causing an error
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

        PaymentPage.Loader(parent, merriweather, boldonse, content, current_user);
        InventoryPage.Loader(parent, merriweather, boldonse, content, current_user);
        PurchaseReqPage.Loader(parent, merriweather, boldonse, content, current_user);
        PurchaseOrderPage.Loader(parent, merriweather, boldonse, content, current_user);
        Profile.Loader(parent, merriweather, boldonse, content, current_user);
        PageChanger();
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        switch (FinanceHome.indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> Finance Home Welcome Page
//    1 -> Profile page
//    2 -> PO
//    3 -> PR
//    4 -> Inventory
//    5 -> Process payment
//    6 -> Generate financial report
            case 0:
                break;
            case 1:
                Profile.ShowPage();
                break;
            case 2:
                PurchaseOrderPage.ShowPage();
                break;
            case 3:
                PurchaseReqPage.ShowPage();
                break;
            case 4:
                InventoryPage.ShowPage();
                break;
            case 5:
                PaymentPage.ShowPage();
                break;
            case 6:
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
            purchaseOrder.UpdateCustomButton(0, finalBase_size, null, 0);
            inventory.UpdateCustomButton(0, finalBase_size, null, 0);
            payment.UpdateCustomButton(0, finalBase_size, null, 0);
            purchaseRequisition.UpdateCustomButton(0, finalBase_size, null, 0);
            report.UpdateCustomButton(0, finalBase_size, null, 0);
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
