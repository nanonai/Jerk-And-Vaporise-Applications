package SalesMgr;

import Common.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    public int Quantity;
    public String ItemID, Itemname, Category, SupplierName;  // Add SupplierName field
    public double UnitPrice;

    // Constructor including SupplierName
    public Item(String ItemID, String Itemname, double UnitPrice, int Quantity, String Category, String SupplierName) {
        this.ItemID = ItemID;
        this.Itemname = Itemname;
        this.UnitPrice = UnitPrice;
        this.Quantity = Quantity;
        this.Category = Category;
        this.SupplierName = SupplierName;  // Initialize SupplierName
    }

    // Method to list all items from a file
    public static List<Item> listAllItem(String filename) {
        List<Item> allItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", ItemName = "", Category = "", SupplierName = "";  // Add SupplierName here
            int Quantity = 0;
            double UnitPrice = 0;
            String line;
            int counter = 1;

            // Reading each line and extracting the values
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);  // Extract ItemID
                        break;
                    case 2:
                        ItemName = line.substring(16);  // Extract ItemName
                        break;
                    case 3:
                        UnitPrice = Double.parseDouble(line.substring(16));  // Extract UnitPrice
                        break;
                    case 4:
                        Quantity = Integer.parseInt(line.substring(16));  // Extract Quantity
                        break;
                    case 5:
                        Category = line.substring(16);  // Extract Category
                        break;
                    case 6:
                        SupplierName = line.substring(16);  // Extract SupplierName
                        break;
                    default:
                        counter = 0;  // Reset counter for the next item
                        allItem.add(new Item(ItemID, ItemName, UnitPrice, Quantity, Category, SupplierName));  // Create new item
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }

    // Method to convert Item objects to HTML strings
    public static List<String> ItemConvt(List<Item> items) {
        List<String> ItemList = new ArrayList<>();
        for (Item item : items) {
            String emp = String.format(
                    """
                    <html>
                    ItemID:         %s<br>
                    Itemname:       %s<br>
                    UnitPrice:      %s<br>
                    Quantity:       %s<br>
                    Category:       %s<br>
                    SupplierName:   %s
                    </html>
                    """, item.ItemID, item.Itemname, item.UnitPrice, item.Quantity, item.Category, item.SupplierName);  // Add SupplierName here
            ItemList.add(emp);
        }
        return ItemList;
    }
}
