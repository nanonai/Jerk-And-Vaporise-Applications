package FinanceMgr;

import Admin.AddUser;
import Admin.ViewUser;
import Common.*;
import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PurchaseOrderPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content,inner;
    public static Buffer current_user;
    private static JButton s_btn;
    private static CustomComponents.CustomButton all, fin, pur, inv, sls, view, add, updateStatus,
            delete1, delete2, data_transfer;
//    private static CustomComponents.CustomButton all, purOrder, purReq, itemID, quan,supplier,orderDate,purMan,status;
    private static CustomComponents.RoundedPanel search_panel;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox<String> entries;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomTable table_purOrder;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static JComboBox<String>  pages;
    private static List<PurchaseOrder> purchaseOrder_List;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,Buffer current_user){
        PurchaseOrderPage.parent = parent;
        PurchaseOrderPage.merriweather = merriweather;
        PurchaseOrderPage.boldonse = boldonse;
        PurchaseOrderPage.content = content;
        PurchaseOrderPage.current_user = current_user;

    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
//        gbc.insets = new Insets(20, 20, 0, 0);
//        all = new CustomComponents.CustomButton("All", merriweather,
//                Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE,
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(all, gbc);
//
//        gbc.gridx = 1;
//        gbc.insets = new Insets(20, 0, 0, 0);
//        purOrder = new CustomComponents.CustomButton("PurOrderID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(purOrder, gbc);
//
//        gbc.gridx = 2;
//        purReq = new CustomComponents.CustomButton("PurReqID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(purReq, gbc);
//
//        gbc.gridx = 3;
//        itemID = new CustomComponents.CustomButton("itemID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(itemID, gbc);
//
//        gbc.gridx = 4;
//        quan = new CustomComponents.CustomButton("Quantity", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(quan, gbc);
//
//        gbc.gridx = 5;
//        supplier = new CustomComponents.CustomButton("SupplierID", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(supplier, gbc);
//
//        gbc.gridx = 6;
//        orderDate = new CustomComponents.CustomButton("OrderDate", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(orderDate, gbc);
//
//        gbc.gridx = 7;
//        purMan = new CustomComponents.CustomButton("OrderDate", merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(purMan, gbc);
//
//        gbc.gridx = 8;
//        status = new CustomComponents.CustomButton("Status",  merriweather, new Color(255, 255, 255),
//                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
//                Main.transparent, 0, 20, Main.transparent, false, 5, false,
//                null, 0, 0, 0);
//        content.add(status, gbc);

        gbc.gridx = 9;
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
        String[] titles = new String[]{"PurchaseOrderID", "PurchaseReqID", "ItemID", "Quantity", "SupplierID","OrderDate","UserID","Status"};
        List<PurchaseOrder> purchaseOrder_list = PurchaseOrder.listAllPurchaseOrders(Main.purchaseOrder_file);
        Object[][] data = new Object[purchaseOrder_list.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : purchaseOrder_list) {
            data[counter] = new Object[]{purchaseOrder.purchaseOrderID,purchaseOrder.purchaseReqID, purchaseOrder.itemID,purchaseOrder.quantity,
                    purchaseOrder.supplierID,purchaseOrder.orderDate, purchaseOrder.purchaseManID,purchaseOrder.status};
            counter += 1;
        }

        table_purOrder = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_purOrder.setShowHorizontalLines(true);
        table_purOrder.setShowVerticalLines(true);
        table_purOrder.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_purOrder,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 5, 10, 0);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);
        ii_gbc.gridx = 0;

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 10, 5);
        JPanel button_panel2 = new JPanel(new GridBagLayout());
        button_panel2.setOpaque(false);
        inner.add(button_panel2, igbc);

        ii_gbc.insets = new Insets(0, 0, 0, 4);
        updateStatus = new CustomComponents.CustomButton("Update Status", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        updateStatus.addActionListener(_ -> {
            if (table_purOrder.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PO to update status!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_purOrder.getValueAt(table_purOrder.getSelectedRow(),
                        table_purOrder.getColumnModel().getColumnIndex("PurchaseOrderID")).toString();
                ModifyPOStatus.UpdatePO(PurchaseOrder.getPurchaseOrderID(selected_id, Main.purchaseOrder_file));
                boolean see = ModifyPOStatus.ShowPage();
                if (see) {
                    System.out.println(" ");
                }
            }
        });
        button_panel1.add(updateStatus, ii_gbc);
        ModifyPOStatus.Loader(parent, merriweather, boldonse, content, null);
}
    public static void UpdateTable(int length, int page) {
        String[] titles = new String[]{"PurchaseOrderID", "PurchaseReqID", "ItemID", "Quantity", "SupplierID", "OrderDate", "PurchaseManID","Status"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= purchaseOrder_List.size() - page * length) {
            data = new Object[purchaseOrder_List.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (PurchaseOrder purchaseOrder : purchaseOrder_List) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
                data[counter] = new Object[]{purchaseOrder.purchaseOrderID, purchaseOrder.purchaseReqID, purchaseOrder.itemID,
                        purchaseOrder.quantity, purchaseOrder.supplierID, purchaseOrder.orderDate, purchaseOrder.purchaseManID,purchaseOrder.status};
                counter += 1;
                if (counter == length || counter == purchaseOrder_List.size()) { break; }
            }
        }
        table_purOrder.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= purchaseOrder_List.size()) {
            lbl_indicate.setText(String.format(temp2, (!purchaseOrder_List.isEmpty()) ? 1 : 0, purchaseOrder_List.size(),
                    purchaseOrder_List.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, purchaseOrder_List.size()),
                    purchaseOrder_List.size()));
        }
    }

}