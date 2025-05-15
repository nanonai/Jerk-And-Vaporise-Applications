package FinanceMgr;

import PurchaseMgr.PurchaseOrder;

import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableUIHelper {

    public static JPanel createPurchaseOrderTablePanel(List<PurchaseOrder> purchaseOrderList) {
        String[] columnNames = {
                "Purchase Order ID", "Item ID", "Purchase Quantity", "Total Amount",
                "Order Date", "Manager ID", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (PurchaseOrder po : purchaseOrderList) {
            Object[] row = {
                    po.PurchaseOrderID,
                    po.ItemID,
                    po.PurchaseQuantity,
                    po.TotalAmt,
                    po.OrderDate,
                    po.PurchaseMgrID,
                    po.Status
            };
            model.addRow(row);
        }

        return createTable(model);
    }
    public static JPanel createPurchaseReqTablePanel(List<PurchaseRequisition> PurchaseRequisitionList) {
        String[] columnNames = {
                "Purchase Req ID", "Item ID", "Supplier ID",
                 "Requisition Date", "Manager ID"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (PurchaseRequisition pr : PurchaseRequisitionList) {
            Object[] row = {
                    pr.PurchaseReqID,
                    pr.ItemID,
                    pr.SupplierID,
                    pr.ReqDate,
                    pr.SalesMgrID
            };
            model.addRow(row);
        }
        return createTable(model);
    }

    public static JPanel createTable( DefaultTableModel model){
        JTable table = new JTable(model);

        // Set the preferred size of the table (2/3 of the screen width and height)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int tableWidth = (int) (screenWidth * 0.66);  // 2/3 of the screen width
        int tableHeight = (int) (screenHeight * 0.66);

        // Set table properties
        table.setPreferredScrollableViewportSize(new Dimension(tableWidth, tableHeight));
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Merriweather", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Merriweather", Font.BOLD, 14));

        // Change the row color when clicked (selection)
        table.setSelectionBackground(Color.WHITE); // Set the background color of the selected row
        table.setSelectionForeground(Color.BLACK); // Set the text color of the selected row

        // Add a listener for row selection and change the color of selected rows dynamically
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    table.setSelectionBackground(Color.GRAY); // Highlight selected row in green
                }
            }
        });

        // Customize the scroll bar color
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(tableWidth, tableHeight));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(56, 53, 70);
                this.trackColor = new Color(155, 152, 152, 255);
            }
        });

        JPanel panel = new JPanel(new BorderLayout());

        // Add the scroll pane (table) to the left side of the panel
        panel.add(scrollPane, BorderLayout.WEST);

        return panel;
    }
}
