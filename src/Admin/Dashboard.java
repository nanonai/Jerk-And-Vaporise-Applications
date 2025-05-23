package Admin;

import SalesMgr.*;
import PurchaseMgr.*;
import InventoryMgr.*;
import FinanceMgr.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content, series_panel, button_panel;
    private static User current_user;
    private static CustomComponents.RoundedPanel user_summary, item_spl_stock_summary, pr_po_py_summary,
            financial_summary, best_seller_summary;
    private static List<User> allUser;
    private static List<Sales> allSales;
    private static List<PurchaseRequisition> allPR;
    private static List<PurchaseOrder> allPO;
    private static List<Payment> allPY;
    private static List<Item> allItem;
    private static List<Supplier> allSpl;
    private static JLabel user1, item1, spl1, stock1, pr1, po1, py1, finTitle, sales_label, payment_label, bsTitle,
            c_lab1, c_lab2, c_lab3, c_lab4, c_lab5, other_lab;
    private static JButton user3, user4, user5, user6;
    private static CustomComponents.CustomVaryingTextIcon user2, item2, spl2, stock2, pr2, po2, py2, bs_icon;
    private static List<String> days, weeks, months, years, dataLabel;
    private static List<Double> daily_sales, weekly_sales, monthly_sales, yearly_sales,
            daily_payment, weekly_payment, monthly_payment, yearly_payment;
    private static CustomComponents.CustomLineChart finReport;
    private static CustomComponents.CustomDataSeriesIcon sales_icon, payment_icon, cat1, cat2, cat3, cat4, cat5, other;
    private static double maxValue;
    private static List<List<Double>> dataSeries;
    private static int mode = 1;
    private static Color[] colors1 = {
            new Color(216, 66, 120),
            new Color(234, 121, 160)
    };
    private static Color[] colors2 = {
            new Color(168, 0, 56),
            new Color(191, 30, 84),
            new Color(207, 63, 112),
            new Color(223, 99, 141),
            new Color(234, 139, 171),
            new Color(166, 166, 166),
    };
    private static CustomComponents.CustomButton b_day, b_week, b_month, b_year;
    private static List<String> categories;
    private static List<Double> sold_quantities;
    private static CustomComponents.CustomRoundChart best_sold_quantity;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        Dashboard.parent = parent;
        Dashboard.merriweather = merriweather;
        Dashboard.boldonse = boldonse;
        Dashboard.content = content;
        Dashboard.current_user = current_user;
    }

    public static void ShowPage() {
        UpdateAllData();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        user_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        user_summary.setLayout(new GridBagLayout());
        content.add(user_summary, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        item_spl_stock_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        item_spl_stock_summary.setLayout(new GridBagLayout());
        content.add(item_spl_stock_summary, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        pr_po_py_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        pr_po_py_summary.setLayout(new GridBagLayout());
        content.add(pr_po_py_summary, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 40;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel bottom_panel = new JPanel(new GridBagLayout());
        bottom_panel.setOpaque(false);
        content.add(bottom_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 5, 5, 5);
        financial_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        financial_summary.setLayout(new GridBagLayout());
        bottom_panel.add(financial_summary, gbc);

        gbc.gridx = 1;
        gbc.weightx = 10;
        gbc.insets = new Insets(0, 0, 5, 5);
        best_seller_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        best_seller_summary.setLayout(new GridBagLayout());
        bottom_panel.add(best_seller_summary, gbc);
        
        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.gridheight = 4;
        float[] sizes = new float[]{34f, 12f};
        String[] texts = new String[]{Integer.toString(allUser.size()), "Total Users"};
        user2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        user1 = new JLabel(user2);
        user_summary.add(user1, igbc);

        igbc.gridx = 1;
        igbc.gridy = 0;
        igbc.gridheight = 1;
        int sm_count = 0, pm_count = 0, im_count = 0, fm_count = 0;
        for (User user: allUser) {
            switch (user.AccType) {
                case "Sales Manager":
                    sm_count += 1;
                    break;
                case "Purchase Manager":
                    pm_count += 1;
                    break;
                case "Inventory Manager":
                    im_count += 1;
                    break;
                case "Finance Manager":
                    fm_count += 1;
                    break;
            }
        }
        String fm_str = String.format("     Finance Manager       (%s)", fm_count);
        String pm_str = String.format("     Purchase Manager     (%s)", pm_count);
        String im_str = String.format("     Inventory Manager   (%s)", im_count);
        String sm_str = String.format("     Sales Manager              (%s)", sm_count);

        user3 = new JButton(fm_str);
        user3.setForeground(Color.WHITE);
        user3.setFocusable(false);
        user3.setBorderPainted(false);
        user3.setOpaque(true);
        user3.setBorder(BorderFactory.createLineBorder(Main.transparent));
        user3.setBackground(new Color(225, 108, 150));
        user3.setFont(merriweather.deriveFont(Font.PLAIN, 12));
        user3.setHorizontalAlignment(SwingConstants.LEFT);
        user3.addActionListener(_ -> {
            AdmHome.indicator = 2;
            UserMng.list_length = 10;
            UserMng.page_counter = 0;
            UserMng.filter = 1;
            UserMng.mode = 1;
            AdmHome.PageChanger();
        });
        user_summary.add(user3, igbc);

        igbc.gridy = 1;
        user4 = new JButton(pm_str);
        user4.setForeground(Color.WHITE);
        user4.setFocusable(false);
        user4.setBorderPainted(false);
        user4.setOpaque(true);
        user4.setBorder(BorderFactory.createLineBorder(Main.transparent));
        user4.setBackground(new Color(234, 121, 160));
        user4.setFont(merriweather.deriveFont(Font.PLAIN, 12));
        user4.setHorizontalAlignment(SwingConstants.LEFT);
        user4.addActionListener(_ -> {
            AdmHome.indicator = 2;
            UserMng.list_length = 10;
            UserMng.page_counter = 0;
            UserMng.filter = 2;
            UserMng.mode = 1;
            AdmHome.PageChanger();
        });
        user_summary.add(user4, igbc);

        igbc.gridy = 2;
        user5 = new JButton(im_str);
        user5.setForeground(Color.WHITE);
        user5.setFocusable(false);
        user5.setBorderPainted(false);
        user5.setOpaque(true);
        user5.setBorder(BorderFactory.createLineBorder(Main.transparent));
        user5.setBackground(new Color(225, 108, 150));
        user5.setFont(merriweather.deriveFont(Font.PLAIN, 12));
        user5.setHorizontalAlignment(SwingConstants.LEFT);
        user5.addActionListener(_ -> {
            AdmHome.indicator = 2;
            UserMng.list_length = 10;
            UserMng.page_counter = 0;
            UserMng.filter = 3;
            UserMng.mode = 1;
            AdmHome.PageChanger();
        });
        user_summary.add(user5, igbc);

        igbc.gridy = 3;
        user6 = new JButton(sm_str);
        user6.setForeground(Color.WHITE);
        user6.setFocusable(false);
        user6.setBorderPainted(false);
        user6.setOpaque(true);
        user6.setBorder(BorderFactory.createLineBorder(Main.transparent));
        user6.setBackground(new Color(234, 121, 160));
        user6.setFont(merriweather.deriveFont(Font.PLAIN, 12));
        user6.setHorizontalAlignment(SwingConstants.LEFT);
        user6.addActionListener(_ -> {
            AdmHome.indicator = 2;
            UserMng.list_length = 10;
            UserMng.page_counter = 0;
            UserMng.filter = 4;
            UserMng.mode = 1;
            AdmHome.PageChanger();
        });
        user_summary.add(user6, igbc);

        igbc.gridx = 0;
        igbc.gridy = 0;
        sizes = new float[]{34f, 12f};
        texts = new String[]{Integer.toString(allItem.size()), "Total Items        "};
        item2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        item1 = new JLabel(item2);
        item_spl_stock_summary.add(item1, igbc);

        igbc.gridy = 1;
        igbc.weighty = 0.1;
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setForeground(new Color(209, 209, 209));
        item_spl_stock_summary.add(separator1, igbc);

        igbc.gridy = 2;
        igbc.weighty = 1;
        sizes = new float[]{34f, 12f};
        texts = new String[]{Integer.toString(allSpl.size()), "Total Suppliers"};
        spl2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        spl1 = new JLabel(spl2);
        item_spl_stock_summary.add(spl1, igbc);

        igbc.gridx = 1;
        igbc.gridy = 0;
        igbc.gridheight = 3;
        igbc.weightx = 0.1;
        JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
        separator2.setForeground(new Color(209, 209, 209));
        item_spl_stock_summary.add(separator2, igbc);

        igbc.gridx = 2;
        igbc.weightx = 1;
        int low_counter = 0;
        for (Item item: allItem) {
            if (item.StockCount < item.Threshold) { low_counter += 1; }
        }
        sizes = new float[]{34f, 12f};
        texts = new String[]{(low_counter > 0) ? Integer.toString(low_counter) : "---", "Low Stock Alerts"};
        stock2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        stock1 = new JLabel(stock2);
        item_spl_stock_summary.add(stock1, igbc);

        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.gridheight = 1;
        int pending_counter = 0;
        for (PurchaseRequisition pr: allPR) {
            if (pr.Status == 0) { pending_counter += 1; }
        }
        sizes = new float[]{34f, 12f};
        texts = new String[]{(pending_counter > 0) ? Integer.toString(pending_counter) : "---", "Pending PR"};
        pr2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        pr1 = new JLabel(pr2);
        pr_po_py_summary.add(pr1, igbc);

        igbc.gridy = 1;
        igbc.weighty = 0.1;
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        separator3.setForeground(new Color(209, 209, 209));
        pr_po_py_summary.add(separator3, igbc);

        pending_counter = 0;
        for (PurchaseOrder po: allPO) {
            if (Objects.equals(po.Status, "Pending")) { pending_counter += 1; }
        }
        igbc.gridy = 2;
        igbc.weighty = 1;
        sizes = new float[]{34f, 12f};
        texts = new String[]{(pending_counter > 0) ? Integer.toString(pending_counter) : "---", "Pending PO"};
        po2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        po1 = new JLabel(po2);
        pr_po_py_summary.add(po1, igbc);

        igbc.gridx = 1;
        igbc.gridy = 0;
        igbc.weightx = 0.1;
        igbc.gridheight = 3;
        JSeparator separator4 = new JSeparator(SwingConstants.VERTICAL);
        separator4.setForeground(new Color(209, 209, 209));
        pr_po_py_summary.add(separator4, igbc);

        pending_counter = 0;
        for (PurchaseOrder po: allPO) {
            if (Objects.equals(po.Status, "Arrived")) { pending_counter += 1; }
        }
        igbc.gridx = 2;
        igbc.weightx = 1;
        sizes = new float[]{34f, 12f};
        texts = new String[]{(pending_counter > 0) ? Integer.toString(pending_counter) : "---", "Pending Payment"};
        py2 = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        py1 = new JLabel(py2);
        pr_po_py_summary.add(py1, igbc);

        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 2;
        igbc.weighty = 1;
        igbc.gridheight = 1;
        igbc.insets = new Insets(0, 20, 0, 20);
        finTitle = new JLabel("Daily Financial Report");
        finTitle.setFont(merriweather.deriveFont(Font.BOLD, 12));
        financial_summary.add(finTitle, igbc);

        igbc.gridy = 1;
        igbc.fill = GridBagConstraints.HORIZONTAL;
        igbc.insets = new Insets(0, 20, 0, 20);
        series_panel = new JPanel(new GridBagLayout());
        series_panel.setOpaque(false);
        financial_summary.add(series_panel, igbc);

        igbc.gridx = 1;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(0, 20, 0, 20);
        button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(true);
        button_panel.setBackground(new Color(220, 220, 220));
        financial_summary.add(button_panel, igbc);

        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.gridwidth = 2;
        igbc.weighty = 20;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(0, 0, 0, 5);
        UpdateFinReportData();
        finReport = new CustomComponents.CustomLineChart(
                dataSeries, dataLabel, colors1, maxValue, Color.WHITE, Color.BLACK, merriweather.deriveFont(Font.BOLD, 12)
        );
        financial_summary.add(finReport, igbc);


        sales_icon = new CustomComponents.CustomDataSeriesIcon(
                20, colors1[0],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                "Sales Records"
        );

        payment_icon = new CustomComponents.CustomDataSeriesIcon(
                20, colors1[1],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                "Payment Records"
        );

        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 0, 0, 0);
        sales_label = new JLabel(sales_icon);
        series_panel.add(sales_label, igbc);

        igbc.gridx = 1;
        igbc.weightx = 2;
        payment_label = new JLabel(payment_icon);
        series_panel.add(payment_label, igbc);

        igbc.gridx = 0;
        igbc.weightx = 1;
        igbc.insets = new Insets(2, 2, 2, 0);
        b_day = new CustomComponents.CustomButton("Daily", merriweather.deriveFont(Font.BOLD),
                Color.BLACK, Color.BLACK, new Color(220, 220, 220), new Color(220, 220, 220),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        b_day.addActionListener(_ -> {
            if (mode != 1) {
                mode = 1;
                UpdateFinReportData();
                finReport.UpdateData(dataSeries, dataLabel, maxValue);
                finTitle.setText("Daily Financial Report");
                b_day.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(220, 220, 220), new Color(220, 220, 220), Main.transparent);
                b_week.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_month.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_year.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
            }
        });
        button_panel.add(b_day, igbc);

        igbc.gridx = 1;
        b_week = new CustomComponents.CustomButton("Weekly", merriweather.deriveFont(Font.BOLD),
                Color.BLACK, Color.BLACK, new Color(247, 247, 247), new Color(243, 243, 243),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        b_week.addActionListener(_ -> {
            if (mode != 2) {
                mode = 2;
                UpdateFinReportData();
                finReport.UpdateData(dataSeries, dataLabel, maxValue);
                finTitle.setText("Weekly Financial Report");
                b_day.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_week.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(220, 220, 220), new Color(220, 220, 220), Main.transparent);
                b_month.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_year.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
            }
        });
        button_panel.add(b_week, igbc);

        igbc.gridx = 2;
        b_month = new CustomComponents.CustomButton("Monthly", merriweather.deriveFont(Font.BOLD),
                Color.BLACK, Color.BLACK, new Color(247, 247, 247), new Color(243, 243, 243),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        b_month.addActionListener(_ -> {
            if (mode != 3) {
                mode = 3;
                UpdateFinReportData();
                finReport.UpdateData(dataSeries, dataLabel, maxValue);
                finTitle.setText("Monthly Financial Report");
                b_day.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_week.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_month.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(220, 220, 220), new Color(220, 220, 220), Main.transparent);
                b_year.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
            }
        });
        button_panel.add(b_month, igbc);

        igbc.gridx = 3;
        igbc.insets = new Insets(2, 2, 2, 2);
        b_year = new CustomComponents.CustomButton("Yearly", merriweather.deriveFont(Font.BOLD),
                Color.BLACK, Color.BLACK, new Color(247, 247, 247), new Color(243, 243, 243),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        b_year.addActionListener(_ -> {
            if (mode != 4) {
                mode = 4;
                UpdateFinReportData();
                finReport.UpdateData(dataSeries, dataLabel, maxValue);
                finTitle.setText("Monthly Financial Report");
                b_day.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_week.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_month.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(247, 247, 247), new Color(243, 243, 243), Main.transparent);
                b_year.UpdateColor(Color.BLACK, Color.BLACK,
                        new Color(220, 220, 220), new Color(220, 220, 220), Main.transparent);
            }
        });
        button_panel.add(b_year, igbc);

        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.gridwidth = 1;
        igbc.gridheight = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(-18, 0, 0, 0);
        sizes = new float[]{12, 12};
        texts = new String[]{"   Top Five", "    Best-Selling Categories"};
        bs_icon = new CustomComponents.CustomVaryingTextIcon(sizes, texts, Color.BLACK, merriweather);
        bsTitle = new JLabel(bs_icon);
        bsTitle.setHorizontalAlignment(SwingConstants.LEFT);
        bsTitle.setFont(merriweather.deriveFont(Font.BOLD, 12));
        best_seller_summary.add(bsTitle, igbc);

        class Pair {
            final double value;
            final String label;

            Pair(double value, String label) {
                this.value = value;
                this.label = label;
            }
        };
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < sold_quantities.size(); i++) {
            pairs.add(new Pair(sold_quantities.get(i), categories.get(i)));
        }
        pairs.sort((a, b) -> Double.compare(b.value, a.value));
        List<Double> sortedValues = new ArrayList<>();
        List<String> sortedLabels = new ArrayList<>();
        for (Pair p : pairs) {
            sortedValues.add(p.value);
            sortedLabels.add(p.label);
        }
        List<Double> result1 = new ArrayList<>();
        List<String> result2 = new ArrayList<>();
        double remainingSum = 0;
        for (int i = 0; i < sortedValues.size(); i++) {
            if (i < 5) {
                result1.add(sortedValues.get(i));
                result2.add("Category: " + sortedLabels.get(i) + "\nSold Quantity: ");
            } else {
                remainingSum += sortedValues.get(i);
            }
        }
        result1.add(remainingSum);
        result2.add("Category: All of the others\nSold Quantity: ");
        igbc.gridy = 1;
        igbc.weighty = 6;
        igbc.insets = new Insets(0, 0, 20, 0);
        best_sold_quantity = new CustomComponents.CustomRoundChart(
                result1, colors2, Color.WHITE, Color.WHITE, merriweather,
                0, 0, 0.66, 1, result2
        );
        best_seller_summary.add(best_sold_quantity, igbc);

        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 5, 10);
        JPanel data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        best_seller_summary.add(data_panel, igbc);

        cat1 = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[0],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                sortedLabels.getFirst()
        );

        cat2 = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[1],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                sortedLabels.get(1)
        );

        cat3 = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[2],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                sortedLabels.get(2)
        );

        cat4 = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[3],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                sortedLabels.get(3)
        );

        cat5 = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[4],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                sortedLabels.get(4)
        );

        other = new CustomComponents.CustomDataSeriesIcon(
                20, colors2[5],
                Color.WHITE, merriweather.deriveFont(Font.PLAIN, 12),
                "All of the others"
        );

        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weighty = 1;
        c_lab1 = new JLabel(cat1);
        data_panel.add(c_lab1, igbc);

        igbc.gridy = 1;
        c_lab3 = new JLabel(cat3);
        data_panel.add(c_lab3, igbc);

        igbc.gridy = 2;
        c_lab5 = new JLabel(cat5);
        data_panel.add(c_lab5, igbc);

        igbc.gridx = 1;
        igbc.gridy = 0;
        c_lab2 = new JLabel(cat2);
        data_panel.add(c_lab2, igbc);

        igbc.gridy = 1;
        c_lab4 = new JLabel(cat4);
        data_panel.add(c_lab4, igbc);

        igbc.gridy = 2;
        other_lab = new JLabel(other);
        data_panel.add(other_lab, igbc);
    }

    public static void UpdateComponentSize(int base_size) {
        user_summary.setBorder(BorderFactory.createEmptyBorder(
                user_summary.GetShadowSize(), user_summary.GetShadowSize(), user_summary.GetShadowSize(), user_summary.GetShadowSize()
        ));
        item_spl_stock_summary.setBorder(BorderFactory.createEmptyBorder(
                item_spl_stock_summary.GetShadowSize(), item_spl_stock_summary.GetShadowSize(), item_spl_stock_summary.GetShadowSize(), item_spl_stock_summary.GetShadowSize()
        ));
        pr_po_py_summary.setBorder(BorderFactory.createEmptyBorder(
                pr_po_py_summary.GetShadowSize(), pr_po_py_summary.GetShadowSize(), pr_po_py_summary.GetShadowSize(), pr_po_py_summary.GetShadowSize()
        ));
        financial_summary.setBorder(BorderFactory.createEmptyBorder(
                financial_summary.GetShadowSize(), financial_summary.GetShadowSize(), financial_summary.GetShadowSize(), financial_summary.GetShadowSize()
        ));
        best_seller_summary.setBorder(BorderFactory.createEmptyBorder(
                best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize(), best_seller_summary.GetShadowSize()
        ));
        user_summary.repaint();
        item_spl_stock_summary.repaint();
        pr_po_py_summary.repaint();
        financial_summary.repaint();
        best_seller_summary.repaint();
        user2.UpdateSize(new float[]{base_size * 3, base_size});
        user1.setIcon(user2);
        user1.setSize(user2.getIconWidth(), user2.getIconHeight());
        user3.setFont(merriweather.deriveFont(Font.PLAIN, base_size));
        user4.setFont(merriweather.deriveFont(Font.PLAIN, base_size));
        user5.setFont(merriweather.deriveFont(Font.PLAIN, base_size));
        user6.setFont(merriweather.deriveFont(Font.PLAIN, base_size));
        item2.UpdateSize(new float[]{(float) (base_size * 1.9), (float) (base_size * 0.9)});
        item1.setIcon(item2);
        spl2.UpdateSize(new float[]{(float) (base_size * 1.9), (float) (base_size * 0.9)});
        spl1.setIcon(spl2);
        stock2.UpdateSize(new float[]{base_size * 3, base_size});
        stock1.setIcon(stock2);
        pr2.UpdateSize(new float[]{(float) (base_size * 1.9), (float) (base_size * 0.9)});
        pr1.setIcon(pr2);
        po2.UpdateSize(new float[]{(float) (base_size * 1.9), (float) (base_size * 0.9)});
        po1.setIcon(po2);
        py2.UpdateSize(new float[]{base_size * 3, base_size});
        py1.setIcon(py2);
        bs_icon.UpdateSize(new float[]{base_size * 1.5f, base_size * 1.2f});
        bsTitle.setIcon(bs_icon);
        sales_icon.UpdateSize(base_size);
        sales_label.setIcon(sales_icon);
        payment_icon.UpdateSize(base_size);
        payment_label.setIcon(payment_icon);
        series_panel.repaint();
        button_panel.repaint();
        cat1.UpdateSize(base_size);
        c_lab1.setIcon(cat1);
        cat2.UpdateSize(base_size);
        c_lab2.setIcon(cat2);
        cat3.UpdateSize(base_size);
        c_lab3.setIcon(cat3);
        cat4.UpdateSize(base_size);
        c_lab4.setIcon(cat4);
        cat5.UpdateSize(base_size);
        c_lab5.setIcon(cat5);
        other.UpdateSize(base_size);
        other_lab.setIcon(other);
        finTitle.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.5)));
        Font font = finTitle.getFont();
        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        finTitle.setFont(font.deriveFont(attributes));
        b_day.UpdateCustomButton(0, (int) (base_size * 0.8), null, 0);
        b_week.UpdateCustomButton(0, (int) (base_size * 0.8), null, 0);
        b_month.UpdateCustomButton(0, (int) (base_size * 0.8), null, 0);
        b_year.UpdateCustomButton(0, (int) (base_size * 0.8), null, 0);
    }

    public static void UpdateAllData() {
        allUser = User.listAllUserFromFilter(Main.userdata_file, "", "", current_user);
        allSales = Sales.listAllSales(Main.sales_file);
        allPR = PurchaseRequisition.listAllPurchaseRequisitions(Main.purchaseReq_file);
        allPO = PurchaseOrder.listAllPurchaseOrders(Main.purchaseOrder_file);
        allPY = Payment.listAllPayment(Main.payment_file);
        allItem = Item.listAllItem(Main.item_file);
        allSpl = Supplier.listAllSupplier(Main.supplier_file);
        LocalDate today = LocalDate.now();
        days = new ArrayList<>();
        weeks = new ArrayList<>();
        months = new ArrayList<>();
        daily_sales = new ArrayList<>();
        daily_payment = new ArrayList<>();
        weekly_sales = new ArrayList<>();
        weekly_payment = new ArrayList<>();
        monthly_sales = new ArrayList<>();
        monthly_payment = new ArrayList<>();
        yearly_sales = new ArrayList<>();
        yearly_payment = new ArrayList<>();
        years = new ArrayList<>();
        for (int i = 13; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            days.add(date.format(DateTimeFormatter.ofPattern("dd/MM")));
            double total = 0;
            for (Sales sales: allSales) {
                if (sales.SalesDate.equals(date)) {
                    List<Item_Sales> temp = Item_Sales.listAllItemSalesFromSalesID(sales.SalesID, Main.item_sales_file);
                    for (Item_Sales item_sales: temp) {
                        total += item_sales.Amount;
                    }
                }
            }
            daily_sales.add(total);
            total = 0;
            for (Payment payment: allPY) {
                if (payment.PaymentDate.equals(date)) {
                    total += payment.Amount;
                }
            }
            daily_payment.add(total);
        }
        LocalDate lastSunday = today.with(DayOfWeek.SUNDAY);
        for (int i = 6; i >= 0; i--) {
            LocalDate sunday = lastSunday.minusWeeks(i);
            weeks.add("Week of " + sunday.format(DateTimeFormatter.ofPattern("dd/MM")));
            double total = 0;
            for (Sales sales: allSales) {
                if (sales.SalesDate.with(DayOfWeek.SUNDAY).equals(sunday)) {
                    List<Item_Sales> temp = Item_Sales.listAllItemSalesFromSalesID(sales.SalesID, Main.item_sales_file);
                    for (Item_Sales item_sales: temp) {
                        total += item_sales.Amount;
                    }
                }
            }
            weekly_sales.add(total);
            total = 0;
            for (Payment payment: allPY) {
                if (payment.PaymentDate.with(DayOfWeek.SUNDAY).equals(sunday)) {
                    total += payment.Amount;
                }
            }
            weekly_payment.add(total);
        }
        for (int i = 6; i >= 0; i--) {
            LocalDate monthDate = today.minusMonths(i);
            months.add(monthDate.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            double total = 0;
            for (Sales sales: allSales) {
                if (sales.SalesDate.getMonth() == monthDate.getMonth()) {
                    List<Item_Sales> temp = Item_Sales.listAllItemSalesFromSalesID(sales.SalesID, Main.item_sales_file);
                    for (Item_Sales item_sales: temp) {
                        total += item_sales.Amount;
                    }
                }
            }
            monthly_sales.add(total);
            total = 0;
            for (Payment payment: allPY) {
                if (payment.PaymentDate.getMonth() == monthDate.getMonth()) {
                    total += payment.Amount;
                }
            }
            monthly_payment.add(total);
        }
        int currentYear = Year.now().getValue();
        for (int i = 6; i >= 0; i--) {
            years.add(String.valueOf(currentYear - i));
            double total = 0;
            for (Sales sales: allSales) {
                if (sales.SalesDate.getYear() == currentYear - i) {
                    List<Item_Sales> temp = Item_Sales.listAllItemSalesFromSalesID(sales.SalesID, Main.item_sales_file);
                    for (Item_Sales item_sales: temp) {
                        total += item_sales.Amount;
                    }
                }
            }
            yearly_sales.add(total);
            total = 0;
            for (Payment payment: allPY) {
                if (payment.PaymentDate.getYear() == currentYear - i) {
                    total += payment.Amount;
                }
            }
            yearly_payment.add(total);
        }
        mode = 1;
        categories = new ArrayList<>();
        sold_quantities = new ArrayList<>();
        for (Item item: allItem) {
            if (!categories.contains(item.Category)) { categories.add(item.Category); }
        }
        for (String category: categories) {
            double counter = 0;
            for (Item item: allItem) {
                if (Objects.equals(item.Category, category)) {
                    List<Item_Sales> related_sales = Item_Sales.listAllItemSalesFromItemID(item.ItemID, Main.item_sales_file);
                    for (Item_Sales item_sales: related_sales) {
                        counter += item_sales.Quantity;
                    }
                }
            }
            sold_quantities.add(counter);
        }
    }

    public static void UpdateFinReportData() {
        maxValue = 0;
        switch (mode) {
            case 1:
                dataSeries = Arrays.asList(daily_sales, daily_payment);
                dataLabel = days;
                for (Double db: daily_sales) {
                    if (maxValue < db) { maxValue = db; }
                }
                for (Double db: daily_payment) {
                    if (maxValue < db) { maxValue = db; }
                }
                break;
            case 2:
                dataSeries = Arrays.asList(weekly_sales, weekly_payment);
                dataLabel = weeks;
                for (Double db: weekly_sales) {
                    if (maxValue < db) { maxValue = db; }
                }
                for (Double db: weekly_payment) {
                    if (maxValue < db) { maxValue = db; }
                }
                break;
            case 3:
                dataSeries = Arrays.asList(monthly_sales, monthly_payment);
                dataLabel = months;
                for (Double db: monthly_sales) {
                    if (maxValue < db) { maxValue = db; }
                }
                for (Double db: monthly_payment) {
                    if (maxValue < db) { maxValue = db; }
                }
                break;
            case 4:
                dataSeries = Arrays.asList(yearly_sales, yearly_payment);
                dataLabel = years;
                for (Double db: yearly_sales) {
                    if (maxValue < db) { maxValue = db; }
                }
                for (Double db: yearly_payment) {
                    if (maxValue < db) { maxValue = db; }
                }
                break;
        }
    }
}
