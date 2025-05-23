package FinanceMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReportPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content,inner;
    public static User current_user;
    private static JLabel lbl_begin_date,lbl_to,lbl_po_status,lbl_sales_made,lbl_Total_Status_Amount,
            lbl_Tota_sales_Amount,lbl_Status,lbl_Tota_profit_Amount,lbl_Total_po_count,lbl_Tota_sales_Count;
    private static JTextArea poDetail,salesDetail;
    private static CustomComponents.EmptyTextField startDateTxtField,endDateTxtField,
            totalpoAmtTxtField,totalSalesAmtTxtField,totalProfitTxtField,totalSalesCountTxtField,totalpoCountTxtField;
    private static CustomComponents.CustomButton generateBtn;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user){
        ReportPage.parent = parent;
        ReportPage.merriweather = merriweather;
        ReportPage.boldonse = boldonse;
        ReportPage.content = content;
        ReportPage.current_user = current_user;
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
        lbl_begin_date = new JLabel("Start Date : ");
        lbl_begin_date.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_begin_date.setOpaque(false);
        lbl_begin_date.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_begin_date, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        startDateTxtField = new CustomComponents.EmptyTextField(19, "", new Color(122, 122, 122));
        startDateTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        startDateTxtField.setOpaque(true);
        startDateTxtField.setBackground(Color.LIGHT_GRAY);
        startDateTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(startDateTxtField,igbc);

        igbc.gridx = 2;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_to = new JLabel("End Date : ");
        lbl_to.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_to.setOpaque(false);
        lbl_to.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_to, igbc);

        igbc.gridx = 3;
        igbc.insets = new Insets(10, 5, 10, 5);
        endDateTxtField = new CustomComponents.EmptyTextField(19, "", new Color(122, 122, 122));
        endDateTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        endDateTxtField.setOpaque(true);
        endDateTxtField.setBackground(Color.LIGHT_GRAY);
        endDateTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(endDateTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.insets = new Insets(10, 10, 10, 10);
        igbc.anchor = GridBagConstraints.WEST;
        lbl_Status = new JLabel("Status : ");
        lbl_Status.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Status.setOpaque(false);
        lbl_Status.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Status, igbc);

        igbc.gridx = 1;
        igbc.gridy = 2;
        igbc.insets = new Insets(10, 5, 10, 5);
        igbc.anchor = GridBagConstraints.WEST;
        String[] statusOptions = { "Pending", "Approved", "Arrived", "Denied", "Paid" };
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 252), 1));
        statusCombo.setFont(new Font("Merriweather", Font.PLAIN, 18));
        statusCombo.setPreferredSize(new Dimension(120, 25));
        inner.add(statusCombo, igbc);

        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);
        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        generateBtn = new CustomComponents.CustomButton("Generate Report", merriweather, new Color(30, 31, 34, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);

        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_po_status = new JLabel("Purchase Order Detail : ");
        lbl_po_status.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_po_status.setOpaque(false);
        lbl_po_status.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_po_status, igbc);

        igbc.gridx = 1;
        igbc.gridy = 3;
        igbc.gridwidth = 3;
        igbc.weightx = 3;
        igbc.weighty = 3;
        igbc.insets = new Insets(5, 10, 5, 10);
        poDetail = new JTextArea(20, 80); // Larger
        poDetail.setLineWrap(true);
        poDetail.setWrapStyleWord(true);
        poDetail.setEditable(false);
        poDetail.setFont(merriweather.deriveFont(Font.PLAIN, 16));
        poDetail.setForeground(new Color(0, 0, 0, 252));
        poDetail.setBackground(Color.lightGray);
        JScrollPane poScrollPane = new JScrollPane(poDetail);
        poScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        poScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        poScrollPane.setPreferredSize(new Dimension(1000, 200)); // Larger size
        inner.add(poScrollPane, igbc);

        igbc.gridx = 0;
        igbc.gridy = 4;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_Total_Status_Amount = new JLabel("Total Amount : ");
        lbl_Total_Status_Amount.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Total_Status_Amount.setOpaque(false);
        lbl_Total_Status_Amount.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Total_Status_Amount, igbc);

        igbc.gridx = 1;
        igbc.gridy = 4;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalpoAmtTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalpoAmtTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalpoAmtTxtField.setOpaque(true);
        totalpoAmtTxtField.setBackground(Color.LIGHT_GRAY);
        totalpoAmtTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalpoAmtTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 5;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_Total_po_count = new JLabel("Number of Purchase Order :  ");
        lbl_Total_po_count.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Total_po_count.setOpaque(false);
        lbl_Total_po_count.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Total_po_count, igbc);

        igbc.gridx = 1;
        igbc.gridy = 5;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalpoCountTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalpoCountTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalpoCountTxtField.setOpaque(true);
        totalpoCountTxtField.setBackground(Color.LIGHT_GRAY);
        totalpoCountTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalpoCountTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 6;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_sales_made = new JLabel("Sales Detail : ");
        lbl_sales_made.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_sales_made.setOpaque(false);
        lbl_sales_made.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_sales_made, igbc);

        igbc.gridx = 1;
        igbc.gridy = 6;
        igbc.gridwidth = 3;
        igbc.weightx = 3;
        igbc.weighty = 3;
        igbc.insets = new Insets(10, 10, 10, 10);
        salesDetail = new JTextArea(20, 80); // Larger
        salesDetail.setLineWrap(true);
        salesDetail.setWrapStyleWord(true);
        salesDetail.setEditable(false);
        salesDetail.setFont(merriweather.deriveFont(Font.PLAIN, 16));
        salesDetail.setForeground(new Color(0, 0, 0, 252));
        salesDetail.setBackground(Color.lightGray);
        JScrollPane payScrollPane = new JScrollPane(salesDetail);
        payScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        payScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        payScrollPane.setPreferredSize(new Dimension(1000, 200)); // Larger size
        inner.add(payScrollPane, igbc);

        igbc.gridx = 0;
        igbc.gridy = 7;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_Tota_sales_Amount = new JLabel("Total Sales Amount :  ");
        lbl_Tota_sales_Amount.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Tota_sales_Amount.setOpaque(false);
        lbl_Tota_sales_Amount.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Tota_sales_Amount, igbc);

        igbc.gridx = 1;
        igbc.gridy = 7;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalSalesAmtTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalSalesAmtTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalSalesAmtTxtField.setOpaque(true);
        totalSalesAmtTxtField.setBackground(Color.LIGHT_GRAY);
        totalSalesAmtTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalSalesAmtTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 8;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_Tota_sales_Count = new JLabel("Number of Sales :  ");
        lbl_Tota_sales_Count.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Tota_sales_Count.setOpaque(false);
        lbl_Tota_sales_Count.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Tota_sales_Count, igbc);

        igbc.gridx = 1;
        igbc.gridy = 8;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalSalesCountTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalSalesCountTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalSalesCountTxtField.setOpaque(true);
        totalSalesCountTxtField.setBackground(Color.LIGHT_GRAY);
        totalSalesCountTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalSalesCountTxtField, igbc);

        generateBtn.addActionListener(e ->
        {
            try {
                String startDate, endDate;
                String selectedStatus = statusCombo.getSelectedItem().toString();
                double totalStatusPoAmt = 0.0, totalSale = 0.0;
                int countPO = 0,countSales =0;
                StringBuilder poDetailsBuilder = new StringBuilder();
                StringBuilder salesDetailsBuilder = new StringBuilder();

                if (!isValidDate(startDateTxtField.getText().trim()) || !isValidDate(endDateTxtField.getText().trim())) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Please enter a valid Date Format! (yyyy-MM-dd)",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    startDateTxtField.setText("");
                    endDateTxtField.setText("");
                    return;
                } else {
                    startDate = startDateTxtField.getText().trim();
                    endDate = endDateTxtField.getText().trim();
                }
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                inputFormat.setLenient(false);
                Date start = inputFormat.parse(startDate);
                Date end = inputFormat.parse(endDate);

                // PURCHASE ORDERS
                File file = new File(Main.purchaseOrder_file);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();

                String[] records = content.toString().split("~");
                totalStatusPoAmt = 0.0;
                totalSale = 0.0;
                poDetailsBuilder.setLength(0);
                salesDetailsBuilder.setLength(0);

                for (String record : records) {
                    String[] lines = record.trim().split("\n");
                    String orderDateStr = "", current_status = "", totalAmtStr = "";

                    for (String entry : lines) {
                        if (entry.startsWith("OrderDate:")) {
                            orderDateStr = entry.split(":", 2)[1].trim();
                        } else if (entry.startsWith("Status:")) {
                            current_status = entry.split(":", 2)[1].trim();
                        } else if (entry.startsWith("TotalAmt:")) {
                            totalAmtStr = entry.split(":", 2)[1].trim();
                        }
                    }
                    if (orderDateStr.isEmpty() || current_status.isEmpty() || totalAmtStr.isEmpty()) continue;
                    Date orderDate = inputFormat.parse(orderDateStr);

                    if (!orderDate.before(start) && !orderDate.after(end)) {
                        double amt = Double.parseDouble(totalAmtStr);

                        if (current_status.equalsIgnoreCase(selectedStatus)) {
                            totalStatusPoAmt += amt;
                            poDetailsBuilder.append(record.trim()).append("\n\n");
                            countPO = countPO+1;
                        }

                        if (current_status.equalsIgnoreCase(selectedStatus)) {
                            countPO++;
                        }
                    }
                }
                //SALES DATA
                //Step 1: Read Sales Summary and collect valid SalesIDs by date
                File summaryFile = new File(Main.sales_file);
                BufferedReader summaryReader = new BufferedReader(new FileReader(summaryFile));
                StringBuilder summaryContent = new StringBuilder();

                while ((line = summaryReader.readLine()) != null) {
                    summaryContent.append(line).append("\n");
                }
                summaryReader.close();

                String[] salesSummaries = summaryContent.toString().split("~");
                Set<String> validSalesIDs = new HashSet<>();

                SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                for (String summary : salesSummaries) {
                    String[] entries = summary.trim().split("\n");
                    String salesID = "", salesDateStr = "";

                    for (String entry : entries) {
                        if (entry.startsWith("SalesID:")) {
                            salesID = entry.split(":", 2)[1].trim();
                        } else if (entry.startsWith("SalesDate:")) {
                            salesDateStr = entry.split(":", 2)[1].trim();
                        }
                    }

                    if (!salesID.isEmpty() && !salesDateStr.isEmpty()) {
                        Date salesDate = fileDateFormat.parse(salesDateStr);
                        if (!salesDate.before(start) && !salesDate.after(end)) {
                            validSalesIDs.add(salesID);
                        }
                    }
                }

                // Step 2: Match SalesID in item file and accumulate Amount
                File itemFile = new File(Main.item_sales_file);
                BufferedReader itemReader = new BufferedReader(new FileReader(itemFile));
                StringBuilder itemContent = new StringBuilder();

                while ((line = itemReader.readLine()) != null) {
                    itemContent.append(line).append("\n");
                }
                itemReader.close();

                String[] itemRecords = itemContent.toString().split("~");

                for (String record : itemRecords) {
                    String[] entries = record.trim().split("\n");
                    String salesID = "", amountStr = "";

                    for (String entry : entries) {
                        if (entry.startsWith("SalesID:")) {
                            salesID = entry.split(":", 2)[1].trim();
                        } else if (entry.startsWith("Amount:")) {
                            amountStr = entry.split(":", 2)[1].trim();
                        }
                    }

                    if (!salesID.isEmpty() && validSalesIDs.contains(salesID)) {
                        try {
                            double amt = Double.parseDouble(amountStr);
                            totalSale += amt;
                            countSales = countSales+1;
                            salesDetailsBuilder
                                    .append("SalesID: ").append(salesID).append("\n")
                                    .append(record.trim()).append("\n\n");
                        } catch (NumberFormatException i) {
                            System.err.println("Invalid amount format: " + amountStr);
                        }
                    }
                }

                // Part 3: GUI OUTPUTS
                totalpoAmtTxtField.setText(String.format("%.2f", totalStatusPoAmt));
                totalSalesAmtTxtField.setText(String.format("%.2f", totalSale));
                poDetail.setText(poDetailsBuilder.toString());
                salesDetail.setText(salesDetailsBuilder.toString());
                totalpoCountTxtField.setText(Integer.toString(countPO));
                totalSalesCountTxtField.setText(Integer.toString(countSales));

            } catch (Exception i) {
                i.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Error reading or parsing data.");
            }
        });
        button_panel1.add(generateBtn, igbc);
        inner.add(button_panel1);

    }
    public static boolean isValidDate(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(inputDate);
            return true;          // Valid format and real date
        } catch (ParseException e) {
            return false;         // Invalid format or invalid date
        }
    }
}
