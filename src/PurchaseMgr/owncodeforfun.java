package PurchaseMgr;

import Admin.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


import static PurchaseMgr.PurchaseOrder.deletePurchaseOrder;

public class owncodeforfun {
    private static JFrame parent;
    private static Font merriweather;
    private static Font boldonse;
    private static JPanel content, inner, btnPanel;
    private static JLabel lbl_show, lbl_entries, lbl_space, lbl_indicate;
    private static JComboBox entries;
    public static User current_user;
    private static CustomComponents.RoundedPanel search_panel;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.EmptyTextField search;
    static CustomComponents.CustomTable table_po;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomButton AddBtn;
    static CustomComponents.CustomButton DelBtn;
    private static JButton s_btn;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static List<PurchaseOrder> purchaseOrder_List;
    private static JComboBox<String>  pages;
    private static CustomComponents.CustomTable table_purOrder;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user){
        owncodeforfun.parent = parent;
        owncodeforfun.merriweather = merriweather;
        owncodeforfun.boldonse = boldonse;
        owncodeforfun.content = content;
        owncodeforfun.current_user = current_user;
    }

    public static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 14; // left n right
        gbc.weighty = 1; // up n down
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel();
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1; // left n right
        gbc.weighty = 18; // up n down
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 20, 20, 20);
        inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.white);
        content.add(inner, gbc);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weightx = 1;
        gbc1.weighty = 1;
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = new Insets(10, 10, 10, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, gbc1);

        gbc1.gridx = 1;
        gbc1.insets = new Insets(10, 5, 10, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePOPages(list_length);
            page_counter = 0;
            UpdatePOTable(list_length, page_counter);
        });
        inner.add(entries, gbc1);

        gbc1.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, gbc1);

        gbc1.gridx = 3;
        gbc1.weightx = 25;
        lbl_space = new JLabel("");
        lbl_space.setBackground(Color.WHITE);
        lbl_space.setOpaque(true);
        inner.add(lbl_space, gbc1);

        gbc1.gridx = 4;
        gbc1.weightx = 1;
        search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE, new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(81, 81, 81), Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        search_panel.add(s_btn, gbc2);

        gbc2.gridx = 1;
        gbc2.insets = new Insets(6, 0, 10, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search_panel.add(search, gbc2);

        gbc1.gridwidth = 5;
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.weightx = 1;
        gbc1.weighty = 10;
        gbc1.insets = new Insets(0, 0, 10, 0);
        purchaseOrder_List = PurchaseOrder.listAllPurchaseOrders(Main.purchaseOrder_file);
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "PurchaseQuantity", "SupplierID","OrderDate","PurchaseMgrID","Status"};
        Object[][] data = new Object[purchaseOrder_List.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : purchaseOrder_List) {
            data[counter] = new Object[]{purchaseOrder.PurchaseOrderID, purchaseOrder.ItemID,purchaseOrder.PurchaseQuantity,
                    purchaseOrder.SupplierID,purchaseOrder.OrderDate, purchaseOrder.PurchaseMgrID,purchaseOrder.Status};
            counter += 1;
        }

        // i created a panel for button, the panel is inside inner
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 5;
        gbc1.insets = new Insets(0, 2, 0, 2);
        gbc1.fill = GridBagConstraints.BOTH;
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout());
        btnPanel.setBackground(Color.lightGray);
        btnPanel.setOpaque(true);
        inner.add(btnPanel, gbc1);

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 1;
        gbc3.weighty = 1;
        gbc3.fill = GridBagConstraints.BOTH;
        gbc3.insets = new Insets(5, 5, 5, 5);
        AddBtn = new CustomComponents.CustomButton("Add", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        AddBtn.addActionListener(_ -> {
            AddPurchaseOrder.Loader(parent, merriweather, boldonse, content, current_user);
            AddPurchaseOrder.ShowPage();
        });
        AddBtn.setPreferredSize(new Dimension(100, 1));
        btnPanel.add(AddBtn, gbc3);

        gbc3.gridx = 1;
        gbc3.weightx = 1;
        gbc3.weighty = 1;
        gbc3.fill = GridBagConstraints.BOTH;
        gbc3.insets = new Insets(5, 5, 5, 5);
        DelBtn = new CustomComponents.CustomButton("Delete", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        DelBtn.addActionListener(_ -> {
            if (table_po.getSelectedRowCount() > 0){
                int selectedRow = table_po.getSelectedRow();
                String selectedPOID = table_po.getValueAt(selectedRow, 0).toString(); // assume column 0 is PurchaseOrderID
                int confirm = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete PurchaseOrderID: " + selectedPOID + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    deletePurchaseOrder(selectedPOID, Main.purchaseOrder_file, parent);
                    // Optionally: remove row from table model (if I turn it on, the resize will become terrible)
                    // DefaultTableModel model = (DefaultTableModel) table_po.getModel();
                    // model.removeRow(selectedRow);
                }
            }
            else {
                CustomComponents.CustomOptionPane.showErrorDialog(parent, "Please select an ID to delete", "Error!",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255));
            }
        });
        DelBtn.setPreferredSize(new Dimension(100, 1));
        btnPanel.add(DelBtn, gbc3);

        gbc3.gridx = 3;
        gbc3.weightx = 14;
        JLabel placeholder2 = new JLabel();
        btnPanel.add(placeholder2, gbc3);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.insets = new Insets(0, 0, 0, 0);
        table_po = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18), merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_po.setShowHorizontalLines(true);
        table_po.setShowVerticalLines(true);
        table_po.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_po,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, gbc1);
    }

    public static void UpdatePOPages(int length) {
        int pageCount = (int) Math.ceil(purchaseOrder_List.size() / (double) length);
        if (purchaseOrder_List.size() <= length) {
            pageCount = 1;
        }
        pages.removeAllItems();
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }

    public static void UpdatePOTable(int length, int page) {
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "SupplierID", "PurchaseQuantity", "TotalAmt", "OrderDate", "PurchaseMgrID", "Status"};
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
                data[counter] = new Object[]{purchaseOrder.PurchaseOrderID, purchaseOrder.ItemID,
                        purchaseOrder.SupplierID, purchaseOrder.PurchaseQuantity, purchaseOrder.TotalAmt,
                        purchaseOrder.OrderDate, purchaseOrder.PurchaseMgrID, purchaseOrder.Status};
                counter += 1;
                if (counter == length || counter == purchaseOrder_List.size()) {
                    break;
                }
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
