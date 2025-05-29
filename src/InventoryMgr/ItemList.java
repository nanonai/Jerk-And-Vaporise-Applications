package InventoryMgr;

import Admin.User;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.misc.InvStatic;
import SalesMgr.AddNewItem;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static PurchaseMgr.Item_Supplier.getSupplierIDFromItemID;
import static PurchaseMgr.Item_Supplier.getSupplierName;

public class ItemList {

    private static JFrame parent = InvStatic.parent;
    private static Font merriweather = InvStatic.merriweather, boldonse = InvStatic.boldonse;
    private static JPanel content = InvStatic.content;
    private static User current_user = InvStatic.current_user;
    private static int indicator, base_size;
    private static java.util.List<Item> AllItems;
    private static JButton p_first,p_left,p_right,p_last;
    private static CustomComponents.CustomButton btnAdd,btnDelete,btnEdit,btnModify,btnStockReport;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomTable table_item;
    private static CustomComponents.CustomArrowIcon right_icon1,right_icon2, left_icon1, left_icon2;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox entries,pages;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 0, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        content.add(lbl_show, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 0, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePages(AllItems.size());
            page_counter = 0;
            UpdateTable(AllItems, list_length, page_counter);

        });
        content.add(entries, gbc);

        gbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        content.add(lbl_entries, gbc);

        gbc.gridx = 3;
        gbc.weightx = 25;
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 4;
        gbc.weightx = 1;
        JLabel search_panel = new JLabel("");
        content.add(search_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 6;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;

        AllItems = Item.listAllItem(Main.item_file);
        String[] titles = new String[]{"ItemID", "ItemName", "Unit Price", "Unit Cost", "StockCount", "Threshold",
                "Category", "Last Update","Supplier Name"};
        Object[][] data = new Object[AllItems.size()][titles.length];
        int counter = 0;
        for (Item item : AllItems) {
            String supplierID = getSupplierIDFromItemID(item.ItemID, Main.item_supplier_file);
            String supplierName = getSupplierName(supplierID, Main.supplier_file);

            data[counter] = new Object[]{
                    item.ItemID,
                    item.ItemName,
                    item.UnitPrice,
                    item.UnitCost,
                    item.StockCount,
                    item.Threshold,
                    item.Category,
                    item.LastUpdate,
                    supplierName
            };
            counter += 1;
        }

        table_item = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);

        lbl_indicate = new JLabel("");
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));

        pages = new JComboBox<>();
        UpdateTable(AllItems, list_length, page_counter);
        UpdatePages(AllItems.size());
        table_item.setShowHorizontalLines(true);
        table_item.setShowVerticalLines(true);
        table_item.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_item,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);

        content.add(scrollPane1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 2, 2, 0);
        content.add(lbl_indicate, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        content.add(page_panel, gbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setPreferredSize(new Dimension(50, 27));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(page_counter);
            UpdateTable(AllItems,list_length, page_counter);
        });
        page_panel.add(p_first, ii_gbc);

        ii_gbc.gridx = 1;
        left_icon1 = new CustomComponents.CustomArrowIcon(24, 3, new Color(122, 122, 122), true);
        left_icon2 = new CustomComponents.CustomArrowIcon(24, 3, Color.BLACK, true);
        p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.setPreferredSize(new Dimension(30, 27));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter--;
                pages.setSelectedIndex(page_counter);
                UpdateTable(AllItems,list_length, page_counter);
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.setPreferredSize(new Dimension(150, 27));
        pages.addActionListener(e -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdateTable(AllItems,list_length, page_counter);
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        right_icon1 = new CustomComponents.CustomArrowIcon(24, 3, new Color(122, 122, 122), false);
        right_icon2 = new CustomComponents.CustomArrowIcon(24, 3, Color.BLACK, false);
        p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.setPreferredSize(new Dimension(30, 27));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter++;
                pages.setSelectedIndex(page_counter);
                UpdateTable(AllItems,list_length, page_counter);
            }
        });
        page_panel.add(p_right, ii_gbc);

        ii_gbc.gridx = 4;
        p_last = new JButton("Last");
        p_last.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_last.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_last.setPreferredSize(new Dimension(50, 27));
        p_last.setForeground(new Color(122, 122, 122));
        p_last.addActionListener(_ -> {
            page_counter = pages.getItemCount() - 1;
            pages.setSelectedIndex(page_counter);
            UpdateTable(AllItems,list_length, page_counter);
        });
        page_panel.add(p_last, ii_gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Using GridBagLayout for better alignment of buttons
        buttonPanel.setOpaque(false); // Set the panel background to transparent
        content.add(buttonPanel, gbc);

        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.insets = new Insets(0, 7, 5, 7);  // Reduced horizontal space (left and right padding)

        // Add "Add Item" button
        btnAdd = new CustomComponents.CustomButton("Add Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnAdd.setPreferredSize(new Dimension(165, 45));  // Increase width and height of the "Add Item" button
        btnAdd.addActionListener(_ -> {
            AddNewItem.ShowPage();
        });
        buttonPanel.add(btnAdd, buttonGbc);

        // Add "Edit Item" button
        buttonGbc.gridx = 1;
        btnEdit = new CustomComponents.CustomButton("Edit Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnEdit.setPreferredSize(new Dimension(165, 45));  // Increase width and height of the "Edit Item" button
        btnEdit.addActionListener(_ -> {
            int selectedRowIndex = table_item.getSelectedRow();
            Object value = table_item.getValueAt(selectedRowIndex, 0);
            Item itm = Item.getItemByID(value.toString(), Main.item_file);
            EditItem.ShowPage(itm);
        });
        buttonPanel.add(btnEdit, buttonGbc);

        // Add "Delete Item" button
        buttonGbc.gridx = 2;
        btnDelete = new CustomComponents.CustomButton("Delete Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnDelete.setPreferredSize(new Dimension(165, 45));
        btnDelete.addActionListener(_ -> {
            InventoryMgr.ItemList.indicator = 2;
            PageChanger();
        });
        buttonPanel.add(btnDelete, buttonGbc);
        
        // Add "Modify Stock" button
        buttonGbc.gridx = 3;
        btnModify = new CustomComponents.CustomButton("Modify Stock", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnModify.setPreferredSize(new Dimension(165, 45));
        btnModify.addActionListener(_ -> {
            try {
                int selectedRowIndex = table_item.getSelectedRow();
                Object value = table_item.getValueAt(selectedRowIndex, 0);
                Item itm = Item.getItemByID(value.toString(), Main.item_file);
                UpdateStock.ShowPage(itm);
            } catch (Exception e){
                CustomComponents.CustomOptionPane.showErrorDialog(parent, "You might want to select something before doing this", "Invalid selection",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255));
            }
        });
        buttonPanel.add(btnModify, buttonGbc);

        // Add "Stock Report" button
        buttonGbc.gridx = 4;
        btnStockReport = new CustomComponents.CustomButton("Stock Report", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnStockReport.setPreferredSize(new Dimension(165, 45));
        btnStockReport.addActionListener(_ -> {
            int selectedRowIndex = table_item.getSelectedRow();
            int columnIndex = table_item.getColumnModel().getColumnIndex("ItemID");
            Object value = table_item.getValueAt(selectedRowIndex, columnIndex);
            Item itm = Item.getItemByID(value.toString(), Main.item_file);
            StockReport.ShowPage(itm);
        });
        buttonPanel.add(btnStockReport, buttonGbc);
    }

    public static void PageChanger() {
        switch (indicator) {
            case 0: // Add Item
                break;
            case 1: // Edit Item
                break;
            case 2: // Delete Item
                break;
            case 3: // Modify Stock
                break;
            case 4: // Report
                break;
        }
        UpdateComponentSize(base_size);
    }

    public static void UpdateTable(java.util.List<Item> filteredItems, int length, int page) {
        String[] titles = new String[]{"ItemID", "ItemName", "Unit Price", "Unit Cost", "StockCount", "Threshold",
                "Category", "Last Update", "Supplier Name"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;

        if (length >= filteredItems.size() - page * length) {
            data = new Object[filteredItems.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }

        for (Item item : filteredItems) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
                String supplierID = getSupplierIDFromItemID(item.ItemID, Main.item_supplier_file);
                String supplierName = getSupplierName(supplierID, Main.supplier_file);

                data[counter] = new Object[]{
                        item.ItemID,
                        item.ItemName,
                        item.UnitPrice,
                        item.UnitCost,
                        item.StockCount,
                        item.Threshold,
                        item.Category,
                        item.LastUpdate,
                        supplierName
                };
                counter += 1;
                if (counter == length || counter == filteredItems.size()) { break; }
            }
        }

        table_item.UpdateTableContent(titles, data);

        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        int start = page * length + 1;
        int end = Math.min((page + 1) * length, filteredItems.size());
        lbl_indicate.setText(String.format(temp2, start, end, filteredItems.size()));
    }


    public static void UpdatePages(int totalItems) {
        int pageCount = (int) Math.ceil(totalItems / (double) list_length);
        if (totalItems <= list_length) {
            pageCount = 1; // If the filtered results are less than the page length, show 1 page
        }
        pages.removeAllItems(); // Remove existing pages
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }


    public static void UpdateComponentSize(int base_size) {
        // Update left and right arrow icons dynamically
        left_icon1.UpdateSize(base_size);
        left_icon2.UpdateSize(base_size);
        p_left.setSize(left_icon1.getIconWidth(), left_icon1.getIconHeight());
        p_left.repaint();

        right_icon1.UpdateSize(base_size);
        right_icon2.UpdateSize(base_size);
        p_right.setSize(right_icon1.getIconWidth(), right_icon1.getIconHeight());
        p_right.repaint();

        p_first.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        p_last.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));

        // Update pages and other relevant labels with new font size
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));

        // Update labels' font sizes based on base_size
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));

        // Adjust the font size for combo boxes and text fields
        entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));

        // Update table font sizes based on base_size
        table_item.SetChanges(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.95)),
                merriweather.deriveFont(Font.PLAIN, (int) (base_size * 0.9)), mode);

        // Update other buttons with base_size
        btnAdd.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnEdit.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnDelete.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnModify.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnStockReport.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);

        // Ensure components revalidate and repaint to reflect size changes
        p_left.revalidate();
        p_right.revalidate();
        p_first.revalidate();
        p_last.revalidate();
        pages.revalidate();
        lbl_show.revalidate();
        lbl_entries.revalidate();
        lbl_indicate.revalidate();
        entries.revalidate();
        table_item.revalidate();
        btnAdd.revalidate();
        btnEdit.revalidate();
        btnDelete.revalidate();
        btnModify.revalidate();
        btnStockReport.revalidate();

        p_left.repaint();
        p_right.repaint();
        p_first.repaint();
        p_last.repaint();
        pages.repaint();
        lbl_show.repaint();
        lbl_entries.repaint();
        lbl_indicate.repaint();
        entries.repaint();
        table_item.repaint();
        btnAdd.repaint();
        btnEdit.repaint();
        btnDelete.repaint();
        btnModify.repaint();
        btnStockReport.repaint();
    }
}
