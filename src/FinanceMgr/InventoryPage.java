package FinanceMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.Item;
import PurchaseMgr.PurchaseOrder;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class InventoryPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content,inner;
    public static User current_user;
    private static CustomComponents.RoundedPanel search_panel;
    private static JComboBox<String> entries;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomTable table_item;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomButton viewInventory,delete2;
    private static final Set<String> deleting_id = new LinkedHashSet<>();
    private static final Set<Integer> previousSelection = new HashSet<>();
    private static boolean deleting = false;
    private static List<Item> item_list;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox<String>  pages;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static JButton s_btn, p_left, p_right, p_first, p_last, x_btn;
    private static CustomComponents.CustomArrowIcon left_icon1, left_icon2, right_icon1, right_icon2;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,User current_user){
        InventoryPage.parent = parent;
        InventoryPage.merriweather = merriweather;
        InventoryPage.boldonse = boldonse;
        InventoryPage.content = content;
        InventoryPage.current_user = current_user;

    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
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
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdateItemPages(list_length);
            page_counter = 0;
            UpdateItemTable(list_length, page_counter);
        });
        inner.add(entries, igbc);


        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 1;
        search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setVisible(false);
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.insets = new Insets(6, 6, 10, 5);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 10;
        igbc.insets = new Insets(0, 0, 10, 0);
        String[] titles = new String[]{"ItemID", "ItemName", "UnitPrice","UnitCost", "StockCount", "threshold","category","lastUpdateDate"};
        item_list = Item.listAllItem(Main.item_file);
        Object[][] data = new Object[item_list.size()][titles.length];
        int counter = 0;
        for (Item item : item_list) {
            data[counter] = new Object[]{item.ItemID,item.ItemName, item.UnitPrice,item.UnitCost,
                    item.StockCount,item.Threshold,item.Category,item.LastUpdate};
            counter += 1;
        }

        table_item = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_item.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && deleting) {
                    SwingUtilities.invokeLater(() -> {
                        int[] selectedRows = table_item.getSelectedRows();
                        Set<Integer> currentSelection = new HashSet<>();
                        for (int row : selectedRows) {
                            currentSelection.add(row);
                        }
                        Set<Integer> newlySelected = new HashSet<>(currentSelection);
                        newlySelected.removeAll(previousSelection);
                        Set<Integer> deselected = new HashSet<>(previousSelection);
                        deselected.removeAll(currentSelection);
                        for (int row : newlySelected) {
                            deleting_id.add(table_item.getValueAt(row,
                                    table_item.getColumnModel().getColumnIndex("Id")).toString());
                        }
                        for (int row : deselected) {
                            deleting_id.remove(table_item.getValueAt(row,
                                    table_item.getColumnModel().getColumnIndex("Id")).toString());
                        }
                        previousSelection.clear();
                        previousSelection.addAll(currentSelection);
                    });
                }
            }
        });
        lbl_indicate = new JLabel("");
        pages = new JComboBox<>();
        UpdateItemTable(list_length, page_counter);
        UpdateItemPages(list_length);
        table_item.setShowHorizontalLines(true);
        table_item.setShowVerticalLines(true);
        table_item.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_item,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 2, 0);
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));
        inner.add(lbl_indicate, igbc);

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 2, 5);
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        inner.add(page_panel, igbc);

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(0);
            UpdateItemTable(list_length, page_counter);
            previousSelection.clear();
        });
        p_first.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_first.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_first);
                if (p_first.contains(point)) {
                    p_first.setForeground(Color.BLACK);
                } else {
                    p_first.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_first, ii_gbc);

        ii_gbc.gridx = 1;
        left_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), true);
        left_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, true);
        p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter -= 1;
                pages.setSelectedIndex(page_counter);
                UpdateItemTable(list_length, page_counter);
                previousSelection.clear();
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.addActionListener(e -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdateItemTable(list_length, page_counter);
                previousSelection.clear();
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        right_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), false);
        right_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, false);
        p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter += 1;
                pages.setSelectedIndex(page_counter);
                UpdateItemTable(list_length, page_counter);
                previousSelection.clear();
            }
        });
        page_panel.add(p_right, ii_gbc);

        ii_gbc.gridx = 4;
        p_last = new JButton("Last");
        p_last.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_last.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_last.setForeground(new Color(122, 122, 122));
        p_last.addActionListener(_ -> {
            page_counter = pages.getItemCount() - 1;
            pages.setSelectedIndex(page_counter);
            UpdateItemTable(list_length, page_counter);
            previousSelection.clear();
        });
        p_last.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_last.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_last);
                if (p_last.contains(point)) {
                    p_last.setForeground(Color.BLACK);
                } else {
                    p_last.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_last, ii_gbc);

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

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 5, 10, 0);
        JPanel button_panel3 = new JPanel(new GridBagLayout());
        button_panel3.setOpaque(false);
        inner.add(button_panel3, igbc);

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 10, 5);
        JPanel button_panel4 = new JPanel(new GridBagLayout());
        button_panel4.setOpaque(false);
        inner.add(button_panel4, igbc);

        ii_gbc.gridx = 0;
        ii_gbc.insets = new Insets(0, 0, 0, 4);
        viewInventory= new CustomComponents.CustomButton("View Details", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        viewInventory.addActionListener(_ -> {
            if (table_item.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select an item to view!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_item.getValueAt(table_item.getSelectedRow(),
                        table_item.getColumnModel().getColumnIndex("ItemID")).toString();
                ViewInventory.UpdateInventory(Item.getItemByID(selected_id, Main.item_file));
                boolean see = ViewInventory.ShowPage();
                if (see) {
                    System.out.println(" ");
                }
            }
        });
        button_panel1.add(viewInventory, ii_gbc);
        ViewInventory.Loader(parent, merriweather, boldonse, content, null);
    }

    public static void UpdateItemTable(int length, int page) {
        String[] titles = new String[]{"ItemID", "ItemName", "UnitPrice", "UnitCost", "StockCount", "Threshold","Category","LastUpdate"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= item_list.size() - page * length) {
            data = new Object[item_list.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (Item item : item_list) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
                data[counter] = new Object[]{item.ItemID, item.ItemName,
                        item.UnitPrice, item.UnitCost, item.StockCount, item.Threshold,item.Category,item.LastUpdate};
                counter += 1;
                if (counter == length || counter == item_list.size()) { break; }
            }
        }
        table_item.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= item_list.size()) {
            lbl_indicate.setText(String.format(temp2, (!item_list.isEmpty()) ? 1 : 0, item_list.size(),
                    item_list.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, item_list.size()),
                    item_list.size()));
        }
    }

    public static void UpdateItemPages(int length) {
        int pageCount = (int) Math.ceil(item_list.size() / (double) length);
        if (item_list.size() <= length) {
            pageCount = 1;
        }
        pages.removeAllItems();
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }
}
