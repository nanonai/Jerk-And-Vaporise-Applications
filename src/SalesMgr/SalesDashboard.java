package SalesMgr;

import Admin.*;
import InventoryMgr.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


import static PurchaseMgr.Item_Supplier.getSupplierIDFromItemID;
import static PurchaseMgr.Item_Supplier.getSupplierName;

public class SalesDashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static JLabel bsTitle;
    private static int indicator, base_size;
    private static CustomComponents.RoundedPanel item_panel, sup_panel, ds_panel, po_panel,pr_panel, best_seller_summary;
    private static BufferedImage icon_item, icon_supplier,icon_dailysales, icon_po, icon_pr;
    private static CustomComponents.ImageCell item_img, supplier_img, dailysales_img, po_img, pr_img;
    private static List<Item> allItems;
    private static List<Sales> allSales;
    private static List<Item_Sales> allItemSales;
    private static CustomComponents.CustomRoundChart best_sold_quantity;
    private static JPanel data_panel;
    private static CustomComponents.CustomVaryingTextIcon bs_icon;


    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        try {
            icon_item = ImageIO.read(new File("images/image_icon.png"));
            icon_supplier = ImageIO.read(new File("images/supplier_icon.png"));
            icon_dailysales = ImageIO.read(new File("images/dailysales_icon.png"));
            icon_po = ImageIO.read(new File("images/PO_icon.png"));
            icon_pr = ImageIO.read(new File("images/PR_icon.png"));


        } catch (IOException e) {
            e.getStackTrace();
        }
        SalesDashboard.parent = parent;
        SalesDashboard.merriweather = merriweather;
        SalesDashboard.boldonse = boldonse;
        SalesDashboard.content = content;
        SalesDashboard.current_user = current_user;
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
        sup_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        sup_panel.setLayout(new GridBagLayout());
        content.add(sup_panel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        ds_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        ds_panel.setLayout(new GridBagLayout());
        content.add(ds_panel, gbc);

        gbc.gridx = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        po_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        po_panel.setLayout(new GridBagLayout());
        content.add(po_panel, gbc);

        gbc.gridx = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        pr_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        pr_panel.setLayout(new GridBagLayout());
        content.add(pr_panel, gbc);


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

        String[] titles = new String[]{"SalesID", "SalesDate", "SalesMgrID", "ItemName", "SoldQuantity", "Revenue"};
        Object[][] data = new Object[allItemSales.size()][titles.length];
        int counter = 0;

        for (Sales sales : allSales) {
            java.util.List<Item_Sales> itemSalesList = Item_Sales.listAllItemSalesFromSalesID(
                    sales.SalesID, Main.item_sales_file);

            for (Item_Sales itemSale : itemSalesList) {
                String itemName = "";
                for (Item item : allItems) {
                    if (item.ItemID.equals(itemSale.ItemID)) {
                        itemName = item.ItemName;
                        break;
                    }
                }

                data[counter] = new Object[]{
                        sales.SalesID,
                        sales.SalesDate.toString(),
                        sales.SalesMgrID,
                        itemName,
                        itemSale.Quantity,
                        itemSale.Amount
                };
                counter += 1;
            }
        }

        CustomComponents.CustomTable table_sales = new CustomComponents.CustomTable(
                titles, data,
                merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16),
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212),
                1, 30
        );

        table_sales.setShowHorizontalLines(true);
        table_sales.setShowVerticalLines(true);
        table_sales.setGridColor(new Color(230, 230, 230));
        table_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1) {
                    SalesHome.indicator = 4;
                    SalesHome.PageChanger();
                }
            }
        });

        JScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(
                false, 1, table_sales,
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
        best_seller_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        best_seller_summary.setLayout(new GridBagLayout());
        bottom_panel.add(best_seller_summary, gbc);

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
        best_seller_summary.add(bsTitle, igbc);

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
        best_sold_quantity = new CustomComponents.CustomRoundChart(
                chartData, colors, Color.WHITE, Color.BLACK, merriweather,
                0, 0, 0.82, 1, chartLabels
        );
        best_seller_summary.add(best_sold_quantity, igbc);

        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 5, 10);
        data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        best_seller_summary.add(data_panel, igbc);

        CustomComponents.CustomDataSeriesIcon cat1 = new CustomComponents.CustomDataSeriesIcon(20, colors[0], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), sortedLabels.get(0));
        CustomComponents.CustomDataSeriesIcon cat2 = new CustomComponents.CustomDataSeriesIcon(20, colors[1], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), sortedLabels.get(1));
        CustomComponents.CustomDataSeriesIcon cat3 = new CustomComponents.CustomDataSeriesIcon(20, colors[2], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), sortedLabels.get(2));
        CustomComponents.CustomDataSeriesIcon cat4 = new CustomComponents.CustomDataSeriesIcon(20, colors[3], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), sortedLabels.get(3));
        CustomComponents.CustomDataSeriesIcon cat5 = new CustomComponents.CustomDataSeriesIcon(20, colors[4], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), sortedLabels.get(4));
        CustomComponents.CustomDataSeriesIcon other = new CustomComponents.CustomDataSeriesIcon(20, colors[5], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), "All of the others");

        igbc.gridx = 0;
        igbc.gridy = 0;
        data_panel.add(new JLabel(cat1), igbc);
        igbc.gridy = 1;
        data_panel.add(new JLabel(cat3), igbc);
        igbc.gridy = 2;
        data_panel.add(new JLabel(cat5), igbc);

        igbc.gridx = 1;
        igbc.gridy = 0;
        data_panel.add(new JLabel(cat2), igbc);
        igbc.gridy = 1;
        data_panel.add(new JLabel(cat4), igbc);
        igbc.gridy = 2;
        data_panel.add(new JLabel(other), igbc);


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
            SalesHome.indicator = 2;
            SalesHome.PageChanger();
        });
        item_panel.add(btnItem, itgbc);

        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.gridx = 0;
        sgbc.gridy = 0;
        sgbc.weightx = 1;
        sgbc.weighty = 1;
        sgbc.fill = GridBagConstraints.BOTH;
        supplier_img = new CustomComponents.ImageCell(icon_supplier, 1.5, 5);
        sup_panel.add(supplier_img, sgbc);

        sgbc.gridy = 1;
        sgbc.weighty = 0;
        sgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnSupplier = new CustomComponents.CustomButton(
                "Suppliers", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 49, 134), new Color(248, 105, 163), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnSupplier.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSupplier.addActionListener(_ -> {
            SalesHome.indicator = 3;
            SalesHome.PageChanger();
        });
        sup_panel.add(btnSupplier, sgbc);

        GridBagConstraints dgbc = new GridBagConstraints();
        dgbc.gridx = 0;
        dgbc.gridy = 0;
        dgbc.weightx = 1;
        dgbc.weighty = 1;
        dgbc.fill = GridBagConstraints.BOTH;
        dailysales_img = new CustomComponents.ImageCell(icon_dailysales, 1.5, 5);
        ds_panel.add(dailysales_img, dgbc);

        dgbc.gridy = 1;
        dgbc.weighty = 0;
        dgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnSales = new CustomComponents.CustomButton(
                "Daily Sales", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 114, 92), new Color(249, 155, 140), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnSales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSales.addActionListener(_ -> {
            SalesHome.indicator = 4;
            SalesHome.PageChanger();
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
            SalesHome.indicator = 6;
            SalesHome.PageChanger();
        });
        po_panel.add(btnPO, pgbc);

        GridBagConstraints prgbc = new GridBagConstraints();
        prgbc.gridx = 0;
        prgbc.gridy = 0;
        prgbc.weightx = 1;
        prgbc.weighty = 1;
        prgbc.fill = GridBagConstraints.BOTH;
        pr_img = new CustomComponents.ImageCell(icon_pr, 1.3, 5);
        pr_panel.add(pr_img, prgbc);

        prgbc.gridy = 1;
        prgbc.weighty = 0;
        prgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnPR = new CustomComponents.CustomButton(
                "Purchase Request", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 143, 143), new Color(248, 171, 171), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnPR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPR.addActionListener(_ -> {
            SalesHome.indicator = 5;
            SalesHome.PageChanger();
        });
        pr_panel.add(btnPR, prgbc);

    }

    public static void UpdateBestSellerChart() {
        best_seller_summary.removeAll();


        allItems = Item.listAllItem(Main.item_file);
        allItemSales = Item_Sales.listAllItemSales(Main.item_sales_file);

        class Pair {
            final double value;
            final String label;
            Pair(double value, String label) {
                this.value = value;
                this.label = label;
            }
        }

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

        List<Double> chartData = new ArrayList<>();
        List<String> chartLabels = new ArrayList<>();
        double others = 0;
        for (int i = 0; i < pairs.size(); i++) {
            if (i < 5) {
                chartData.add(pairs.get(i).value);
                chartLabels.add("Category: " + pairs.get(i).label + "\nSold Quantity: ");
            } else {
                others += pairs.get(i).value;
            }
        }
        chartData.add(others);
        chartLabels.add("Category: All of the others\nSold Quantity: ");

        Color[] colors = {
                new Color(168, 0, 56), new Color(191, 30, 84), new Color(207, 63, 112),
                new Color(223, 99, 141), new Color(234, 139, 171), new Color(166, 166, 166)
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 10, 5, 10);

        JLabel pieTitle = new JLabel("Best-Selling Item Categories");
        pieTitle.setFont(merriweather.deriveFont(Font.BOLD, 14f));
        pieTitle.setForeground(new Color(80, 80, 80));
        pieTitle.setHorizontalAlignment(SwingConstants.CENTER);
        best_seller_summary.add(pieTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.7;
        gbc.insets = new Insets(5, 10, 5, 10);
        best_sold_quantity = new CustomComponents.CustomRoundChart(
                chartData, colors, Color.WHITE, Color.WHITE, merriweather,
                0, 0, 0.55, 1, chartLabels
        );
        best_sold_quantity.setPreferredSize(new Dimension(100, 100));
        best_seller_summary.add(best_sold_quantity, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(10, 10, 10, 10);
        data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        best_seller_summary.add(data_panel, gbc);

        for (int i = 0; i < Math.min(5, pairs.size()); i++) {
            CustomComponents.CustomDataSeriesIcon icon = new CustomComponents.CustomDataSeriesIcon(
                    16, colors[i], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), pairs.get(i).label
            );
            JLabel label = new JLabel(icon);
            GridBagConstraints lg = new GridBagConstraints();
            lg.gridx = i % 2;
            lg.gridy = i / 2;
            lg.insets = new Insets(2, 8, 2, 8);
            data_panel.add(label, lg);
        }

        CustomComponents.CustomDataSeriesIcon iconOther = new CustomComponents.CustomDataSeriesIcon(
                16, colors[5], Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12), "All of the others"
        );
        JLabel otherLabel = new JLabel(iconOther);
        GridBagConstraints lgOther = new GridBagConstraints();
        lgOther.gridx = pairs.size() % 2;
        lgOther.gridy = pairs.size() / 2;
        lgOther.insets = new Insets(2, 8, 2, 8);
        data_panel.add(otherLabel, lgOther);

        best_seller_summary.revalidate();
        best_seller_summary.repaint();
    }


    public static void UpdateComponentSize(int base_size) {
        item_panel.setBorder(BorderFactory.createEmptyBorder(
                item_panel.GetShadowSize(), item_panel.GetShadowSize(), item_panel.GetShadowSize(), item_panel.GetShadowSize()
        ));
        sup_panel.setBorder(BorderFactory.createEmptyBorder(
                sup_panel.GetShadowSize(), sup_panel.GetShadowSize(), sup_panel.GetShadowSize(), sup_panel.GetShadowSize()
        ));
        ds_panel.setBorder(BorderFactory.createEmptyBorder(
                ds_panel.GetShadowSize(), ds_panel.GetShadowSize(), ds_panel.GetShadowSize(), ds_panel.GetShadowSize()
        ));
        po_panel.setBorder(BorderFactory.createEmptyBorder(
                po_panel.GetShadowSize(), po_panel.GetShadowSize(), po_panel.GetShadowSize(), po_panel.GetShadowSize()
        ));
        pr_panel.setBorder(BorderFactory.createEmptyBorder(
                pr_panel.GetShadowSize(), pr_panel.GetShadowSize(), pr_panel.GetShadowSize(), pr_panel.GetShadowSize()
        ));
        best_seller_summary.setBorder(BorderFactory.createEmptyBorder(
                best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize()
        ));

        item_panel.repaint();
        sup_panel.repaint();
        ds_panel.repaint();
        po_panel.repaint();
        pr_panel.repaint();
        best_seller_summary.repaint();

        item_img.setPreferredSize(new Dimension(base_size * 5, base_size * 5));
        supplier_img.setPreferredSize(new Dimension(base_size * 5, base_size * 5));
        dailysales_img.setPreferredSize(new Dimension(base_size * 5, base_size * 5));
        po_img.setPreferredSize(new Dimension(base_size * 5, base_size * 5));
        pr_img.setPreferredSize(new Dimension(base_size * 5, base_size * 5));

        best_sold_quantity.setPreferredSize(new Dimension(base_size * 5, base_size * 5));

        bs_icon.UpdateSize(new float[]{base_size * 1.5f, base_size * 1.2f});
        bsTitle.setIcon(bs_icon);

        for (Component comp : data_panel.getComponents()) {
            if (comp instanceof JLabel label && label.getIcon() instanceof CustomComponents.CustomDataSeriesIcon icon) {
                icon.UpdateSize(base_size);
                label.setIcon(icon);
            }
        }

        for (JPanel panel : new JPanel[]{item_panel, sup_panel, ds_panel, po_panel, pr_panel}) {
            for (Component comp : panel.getComponents()) {
                if (comp instanceof CustomComponents.CustomButton btn) {
                    btn.setFont(merriweather.deriveFont(Font.PLAIN, base_size));
                    btn.UpdateCustomButton(0, base_size, null, 0);
                }
            }
        }
    }


}
