package InventoryMgr;

import Admin.Main;
import Admin.User;
import Admin.CustomComponents;
import InventoryMgr.misc.*;
import SalesMgr.Item_Sales;
import SalesMgr.Sales;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static PurchaseMgr.Item_Supplier.getSupplierIDFromItemID;
import static PurchaseMgr.Item_Supplier.getSupplierName;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static BufferedImage bg, img;
    private static int indicator, base_size;
    private static CustomComponents.RoundedPanel item_panel, sup_panel, ds_panel, po_panel,pr_panel, low_stock_summary;
    private static BufferedImage icon_item, icon_check,icon_dailysales, icon_po, icon_pr;
    private static CustomComponents.ImageCell item_img, supplier_img, dailysales_img, po_img, pr_img;
    private static List<Item> allItems;
    private static List<Sales> allSales;
    private static List<Item_Sales> allItemSales;
    private static CustomComponents.CustomRoundChart best_sold_quantity;
    private static JPanel data_panel;
    private static CustomComponents.CustomVaryingTextIcon bs_icon;
    private static JLabel bsTitle;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            img = ImageIO.read(new File("images/info.png"));
            icon_item = ImageIO.read(new File("images/image_icon.png"));
            icon_check = ImageIO.read(new File("images/supplier_icon.png"));
            icon_po = ImageIO.read(new File("images/PO_icon.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Dashboard.parent = parent;
        Dashboard.merriweather = merriweather;
        Dashboard.boldonse = boldonse;
        Dashboard.content = content;
        Dashboard.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        item_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        item_panel.setLayout(new GridBagLayout());
        content.add(item_panel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        ds_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        ds_panel.setLayout(new GridBagLayout());
        content.add(ds_panel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        po_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        po_panel.setLayout(new GridBagLayout());
        content.add(po_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 3.7;
        gbc.weighty = 0.8;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel bottom_panel = new JPanel(new GridBagLayout());
        bottom_panel.setOpaque(false);
        content.add(bottom_panel, gbc);

        allSales = Sales.listAllSales(Main.sales_file);
        allItemSales = Item_Sales.listAllItemSales(Main.item_sales_file);
        allItems = Item.listAllItem(Main.item_file);

        String[] titles = new String[]{"ItemID", "ItemName", "Unit Price", "Unit Cost", "StockCount"};
        Object[][] data = new Object[allItems.size()][titles.length];
        int counter = 0;
        for (Item item : allItems) {
            data[counter] = new Object[]{
                    item.ItemID,
                    item.ItemName,
                    item.UnitPrice,
                    item.UnitCost,
                    item.StockCount,
            };
            counter += 1;
        }

        CustomComponents.CustomTable table_inv = new CustomComponents.CustomTable(
                titles, data,
                merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16),
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212),
                1, 30
        );

        table_inv.setShowHorizontalLines(true);
        table_inv.setShowVerticalLines(true);
        table_inv.setGridColor(new Color(230, 230, 230));
        table_inv.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1) {
                    InventoryHome.indicator = 2;
                    InventoryHome.PageChanger();
                }
            }
        });

        JScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(
                false, 1, table_inv,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 3.7;
        gbc.weighty = 0.7;
        gbc.insets = new Insets(0, 10, 5, 5);
        bottom_panel.add(scrollPane1, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.3;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 5, 10);
        low_stock_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        low_stock_summary.setLayout(new GridBagLayout());
        bottom_panel.add(low_stock_summary, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.gridwidth = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(-18, 0, 0, 0);

        float[] sizes = new float[]{base_size * 1.2f, base_size};
        String[] texts = new String[]{"   Top Five", "    Best-Selling Categories"};
        bs_icon = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        bsTitle = new JLabel(bs_icon);
        bsTitle.setHorizontalAlignment(SwingConstants.LEFT);
        bsTitle.setFont(merriweather.deriveFont(Font.BOLD, base_size));
        low_stock_summary.add(bsTitle, igbc);

        class Pair {
            final double value;
            final String label;

            Pair(double value, String label) {
                this.value = value;
                this.label = label;
            }
        }

        allItems = Item.listAllItem(Main.item_file);
        allItemSales = Item_Sales.listAllItemSales(Main.item_sales_file);

        Map<String, Double> categorySalesMap = new HashMap<>();
        for (Item item : allItems) {
            List<Item_Sales> sales = Item_Sales.listAllItemSalesFromItemID(item.ItemID, Main.item_sales_file);
            double totalQty = sales.stream().mapToDouble(s -> s.Quantity).sum();
            categorySalesMap.put(item.Category, categorySalesMap.getOrDefault(item.Category, 0.0) + totalQty);
        }

        List<Pair> pairs = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categorySalesMap.entrySet()) {
            pairs.add(new Pair(entry.getValue(), entry.getKey()));
        }
        pairs.sort((a, b) -> Double.compare(b.value, a.value));

        List<Double> sortedValues = new ArrayList<>();
        List<String> sortedLabels = new ArrayList<>();
        for (Pair p : pairs) {
            sortedValues.add(p.value);
            sortedLabels.add(p.label);
        }

        List<Double> chartData = new ArrayList<>();
        List<String> chartLabels = new ArrayList<>();
        double others = 0;
        for (int i = 0; i < sortedValues.size(); i++) {
            if (i < 5) {
                chartData.add(sortedValues.get(i));
                chartLabels.add("Category: " + sortedLabels.get(i) + "\nSold Quantity: ");
            } else {
                others += sortedValues.get(i);
            }
        }
        chartData.add(others);
        chartLabels.add("Category: All of the others\nSold Quantity: ");

        Color[] colors = {
                new Color(168, 0, 56),
                new Color(191, 30, 84),
                new Color(207, 63, 112),
                new Color(223, 99, 141),
                new Color(234, 139, 171),
                new Color(166, 166, 166)
        };

        igbc.gridy = 1;
        igbc.weighty = 6;
        igbc.insets = new Insets(0, 0, 20, 0);

        List<Item> sa = StockAlert.Checker();
        if (sa.isEmpty()) {
            List<String> sa_list = List.of(
                    "E|All good, keep it up!",
                    "N|Everything is stocked and ready to go."
            );
            JLabel listLabel = ListGenerator.createListLabel(sa_list, 400);
            low_stock_summary.add(listLabel, gbc);
        } else {
            List<String> sa_list = new ArrayList<>();
            sa_list.add("B|Low Stock Alert:");
            for (Item item : sa) {
                sa_list.add("N|ID: " + item.ItemID + " | Name: " + item.ItemName);
            }
            JLabel listLabel = ListGenerator.createListLabel(sa_list, 400);
            low_stock_summary.add(listLabel, gbc);
        }

        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 5, 10);
        data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        low_stock_summary.add(data_panel, igbc);

        GridBagConstraints itgbc = new GridBagConstraints();
        itgbc.gridx = 0;
        itgbc.gridy = 0;
        itgbc.weightx = 1;
        itgbc.weighty = 1;
        itgbc.fill = GridBagConstraints.BOTH;
        item_img = new CustomComponents.ImageCell(icon_item, 1.4, 5);
        item_panel.add(item_img, itgbc);

        itgbc.gridy = 1;
        itgbc.weighty = 0;
        itgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnItem = new CustomComponents.CustomButton(
                "Items", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(225, 153, 179), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnItem.addActionListener(_ -> {
            InventoryHome.indicator = 2;
            InventoryHome.PageChanger();
        });
        item_panel.add(btnItem, itgbc);

        GridBagConstraints dgbc = new GridBagConstraints();
        dgbc.gridx = 0;
        dgbc.gridy = 0;
        dgbc.weightx = 1;
        dgbc.weighty = 1;
        dgbc.fill = GridBagConstraints.BOTH;
        dailysales_img = new CustomComponents.ImageCell(icon_check, 1.5, 5);
        ds_panel.add(dailysales_img, dgbc);

        dgbc.gridy = 1;
        dgbc.weighty = 0;
        dgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnSales = new CustomComponents.CustomButton(
                "Check Stock Alert", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 114, 92), new Color(249, 155, 140), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnSales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSales.addActionListener(_ -> {
            StockAlert.Popup(parent);
        });
        ds_panel.add(btnSales, dgbc);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridx = 0;
        pgbc.gridy = 0;
        pgbc.weightx = 1;
        pgbc.weighty = 1;
        pgbc.fill = GridBagConstraints.BOTH;
        po_img = new CustomComponents.ImageCell(icon_po, 1.3, 5);
        po_panel.add(po_img, pgbc);

        pgbc.gridy = 1;
        pgbc.weighty = 0;
        pgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnPO = new CustomComponents.CustomButton(
                "Purchase Order", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 180, 55), new Color(254, 210, 137), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnPO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPO.addActionListener(_ -> {
            InventoryHome.indicator = 3;
            InventoryHome.PageChanger();
        });
        po_panel.add(btnPO, pgbc);
    }

    public static void UpdateComponentSize(int base_size) {
    }
}
