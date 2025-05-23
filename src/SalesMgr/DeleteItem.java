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

public class DeleteItem {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static List<Item_Supplier> itemsupplier;
    private static List<Item> items;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, List<Item> items) {
        // Ensure parent is properly passed to DeleteItem
        DeleteItem.parent = parent;
        DeleteItem.merriweather = merriweather;
        DeleteItem.boldonse = boldonse;
        DeleteItem.content = content;
        DeleteItem.items = items; // Initialize items list here
    }


    public static void UpdateItemSupplier(List<Item_Supplier> itemsupplier) {
        DeleteItem.itemsupplier = itemsupplier;
    }

    public static boolean ShowPage() {
        if (parent == null) {
            // Optionally show an error or log it
            System.out.println("Error: Parent JFrame is not initialized.");
            return false; // Exit the method if parent is not set
        }
        AtomicBoolean delete_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Delete Items", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.8));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        // Define the base size of the dialog
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
        JLabel title = new JLabel("Please confirm to delete all items listed below:");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        List<String> data = new ArrayList<>();
        int counter = 1;
        for (Item item : items) {
            // Displaying item details
            data.add(String.format("%s. ItemID: %s", counter, item.ItemID));
            data.add(String.format("    Item Name: %s", item.ItemName));
            data.add(String.format("    Unit Price: %.2f", item.UnitPrice)); // Corrected here
            data.add(String.format("    Unit Cost: %.2f", item.UnitCost));  // Corrected here
            data.add(String.format("    Stock Count: %d", item.StockCount));
            data.add(String.format("    Threshold: %d", item.Threshold));
            data.add(String.format("    Category: %s", item.Category));
            data.add(String.format("    Last Update: %s", item.LastUpdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
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
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        panel.add(placeholder, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        CustomComponents.CustomButton delete = new CustomComponents.CustomButton("Delete Items (" + items.size() + ")",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(159, 4, 4), new Color(161, 40, 40),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(delete, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        delete.addActionListener(_ -> {
            boolean proceed = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent, "Confirm to delete?", "Confirmation",
                    new Color(159, 4, 4),
                    new Color(255, 255, 255),
                    new Color(161, 40, 40),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );
            if (proceed) {
                // Proceed with item deletion
                for (Item item : items) {
                    // Remove related item_supplier associations
                    List<Item_Supplier> relatedSuppliers = Item_Supplier.listAllItemSupplierFromItemID(item.ItemID, "datafile/item_supplier.txt");
                    for (Item_Supplier itemSupplier : relatedSuppliers) {
                        Item_Supplier.removeItemSupplier(itemSupplier.ItemID, itemSupplier.SupplierID, "datafile/item_supplier.txt");
                    }
                    // Remove the item itself
                    Item.removeItem(item.ItemID, "datafile/item.txt");
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