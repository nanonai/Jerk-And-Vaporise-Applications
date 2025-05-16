package PurchaseMgr;

import Admin.BufferForUser;
import Admin.CustomComponents;
import InventoryMgr.Item;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewItems {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static BufferForUser current_user;
    private static java.util.List<String> list = new ArrayList<>();

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, BufferForUser current_user) {
        ViewItems.parent = parent;
        ViewItems.merriweather = merriweather;
        ViewItems.boldonse = boldonse;
        ViewItems.content = content;
        ViewItems.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        List<Item> list = Item.listAllItem("datafile/item.txt");
        String[] itemColumn = new String[]{"ItemID", "Itemname", "UnitPrice", "StockCount", "Category"};
        Object[][] itemData = new Object[list.size()][6];
        int Counter = 0;
        for (Item listItem: list){
            itemData[Counter] = new Object[]{listItem.ItemID, listItem.ItemName, listItem.UnitPrice, listItem.StockCount, listItem.Category };
            Counter += 1;
        }
        CustomComponents.CustomTable customtable = new CustomComponents.CustomTable(itemColumn, itemData, merriweather.deriveFont(Font.BOLD, 15),
                merriweather.deriveFont(Font.PLAIN, 13), Color.CYAN, new Color(255, 254, 233), Color.DARK_GRAY, Color.BLUE, 2, 80);
        CustomComponents.CustomScrollPane supplierPane = new CustomComponents.CustomScrollPane(true, 40, customtable, 10, Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN,
                Color.GRAY, Color.DARK_GRAY, Color.BLUE, Color.blue, Color.DARK_GRAY, Color.magenta, Color.PINK, 100);
        content.add(supplierPane, gbc);

    }

    public static void UpdateComponentSize(int base_size) {

    }
}
