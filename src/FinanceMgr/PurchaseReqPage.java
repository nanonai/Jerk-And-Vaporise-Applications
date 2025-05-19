package FinanceMgr;

import Admin.User;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class PurchaseReqPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content,inner;
    public static User current_user;
    private static JButton s_btn;
    private static CustomComponents.CustomButton all, purReq, itemID, quan, reqDate,purManID;
    private static CustomComponents.RoundedPanel search_panel;
    private static JLabel lbl_show, lbl_entries;
    private static JComboBox<String> entries;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomTable table_purReq;
    private static CustomComponents.CustomScrollPane scrollPane1;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,User current_user){
        PurchaseReqPage.parent = parent;
        PurchaseReqPage.merriweather = merriweather;
        PurchaseReqPage.boldonse = boldonse;
        PurchaseReqPage.content = content;
        PurchaseReqPage.current_user = current_user;

    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 0, 0);
//        all = new CustomComponents.CustomButton("All", merriweather,
//                Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE,
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(all, gbc);
//
//        gbc.gridx = 1;
//        gbc.insets = new Insets(20, 0, 0, 0);
//        purReq = new CustomComponents.CustomButton("PurchaseReqID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(purReq, gbc);
//
//        gbc.gridx = 2;
//        itemID = new CustomComponents.CustomButton("ItemID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(itemID, gbc);
//
//        gbc.gridx = 3;
//        quan = new CustomComponents.CustomButton("Quantity", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(quan, gbc);
//
//        gbc.gridx = 4;
//        reqDate = new CustomComponents.CustomButton("ReqDate", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(reqDate, gbc);
//
//        gbc.gridx = 5;
//        purManID = new CustomComponents.CustomButton("PurchaseManID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(purManID, gbc);

        gbc.gridx = 6;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(0, 20, 20, 20);
        inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        inner.add(entries, igbc);

        igbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, igbc);

        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 1;
        search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(81, 81, 81), Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        search_panel.add(s_btn, ii_gbc);

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(6, 0, 10, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search_panel.add(search, ii_gbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 10;
        igbc.insets = new Insets(0, 0, 10, 0);
        String[] titles = new String[]{"PurchaseReqID", "ItemID", "SupplierID", "Quantity", "ReqDate","SalesMgrID"};
        List<PurchaseRequisition> purchaseReq_list = PurchaseRequisition.listAllPurchaseRequisitions(Main.purchaseReq_file);
        Object[][] data = new Object[purchaseReq_list.size()][titles.length];
        int counter = 0;
        for (PurchaseRequisition purchaseRequisition : purchaseReq_list) {
            data[counter] = new Object[]{purchaseRequisition.PurchaseReqID, purchaseRequisition.ItemID,purchaseRequisition.SupplierID,
                    purchaseRequisition.Quantity, purchaseRequisition.ReqDate,purchaseRequisition.SalesMgrID};
            counter += 1;
        }

        table_purReq = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_purReq.setShowHorizontalLines(true);
        table_purReq.setShowVerticalLines(true);
        table_purReq.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_purReq,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);
    }
}
