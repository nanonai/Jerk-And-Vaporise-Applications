package SalesMgr;

import Admin.Main;
import InventoryMgr.Item;
import Admin.CustomComponents;
import PurchaseMgr.Item_Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class DeleteDailySales {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static List<Item_Sales> itemSalesList;
    private static List<Sales> salesList;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content,
                              List<Sales> salesList, List<Item_Sales> itemSalesList) {
        DeleteDailySales.parent = parent;
        DeleteDailySales.merriweather = merriweather;
        DeleteDailySales.boldonse = boldonse;
        DeleteDailySales.content = content;
        DeleteDailySales.salesList = salesList;           // Set the list of Sales records to delete
        DeleteDailySales.itemSalesList = itemSalesList;   // Set the list of related Item_Sales records
    }

    public static boolean ShowPage() {
        if (parent == null) {
            System.out.println("Error: Parent JFrame is not initialized.");
            return false;
        }

        AtomicBoolean delete_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Delete Sales Records", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.8));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = (parent.getWidth() >= parent.getHeight()) ? parent.getHeight() / 40 : parent.getWidth() / 30;
        int base_size = size_factor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 0, 10);
        JLabel title = new JLabel("Please confirm to delete the following daily sales records:");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 5;
        gbc.fill = GridBagConstraints.BOTH;
        List<String> data = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < salesList.size(); i++) {
            Sales sales = salesList.get(i);
            Item_Sales itemSales = itemSalesList.get(i);

            data.add(String.format("%d. Sales ID: %s", counter, sales.SalesID));
            data.add(String.format("    Sales Date: %s", sales.SalesDate));
            data.add(String.format("    Manager ID: %s", sales.SalesMgrID));
            data.add(String.format("    Item ID: %s", itemSales.ItemID));
            data.add(String.format("    Quantity: %d", itemSales.Quantity));
            data.add(String.format("    Revenue: %.2f", itemSales.Amount));
            counter++;
        }


        CustomComponents.CustomList<String> list = new CustomComponents.CustomList<>(data, 1, base_size,
                merriweather.deriveFont(Font.PLAIN),
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212));

        CustomComponents.CustomScrollPane scrollPane = new CustomComponents.CustomScrollPane(false, 1, list,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);

        panel.add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        panel.add(new JLabel(""), gbc); // Placeholder

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        CustomComponents.CustomButton delete = new CustomComponents.CustomButton("Delete Sales (" + salesList.size() + ")",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(159, 4, 4), new Color(161, 40, 40),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(delete, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        delete.addActionListener(_ -> {
            boolean proceed = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent, "Confirm to delete?", "Confirmation",
                    new Color(159, 4, 4), Color.WHITE,
                    new Color(161, 40, 40), Color.WHITE,
                    new Color(56, 53, 70), Color.WHITE,
                    new Color(73, 69, 87), Color.WHITE,
                    false
            );
            if (proceed) {
                for (Sales sales : salesList) {
                    Sales.removeSales(sales.SalesID, "datafile/sales.txt");
                    Item_Sales.removeItemSalesBySalesID(sales.SalesID, "datafile/item_sales.txt");
                }
                delete_or_not.set(true);
            }
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);

        return delete_or_not.get();
    }


}
