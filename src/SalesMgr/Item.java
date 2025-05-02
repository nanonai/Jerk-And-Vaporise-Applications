package SalesMgr;

import Common.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Item {
    public int  Quantity;
    public String ItemID, Itemname, Category;
    public double UnitPrice;

    public Item(String ItemID, String Itemname,  double UnitPrice, int Quantity, String Category) {
        this.ItemID = ItemID;
        this.Itemname =  Itemname;
        this.UnitPrice = UnitPrice;
        this.Quantity = Quantity;
        this.Category = Category;
    }

    public static List<Item> listAllItem(String filename) {
        List<Item> allItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID= "", ItemName = "", Category = "";
            int Quantity = 0;
            double UnitPrice = 0;
            String line;
            int counter = 1;

            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        ItemName = line.substring(16);
                        break;
                    case 3:
                        UnitPrice = Double.parseDouble(line.substring(16));
                        break;
                    case 4:
                        Quantity = Integer.parseInt(line.substring(16));
                        break;
                    case 5:
                        Category = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allItem.add(new Item(ItemID,ItemName,UnitPrice,Quantity,Category));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }
    public static List<String> ItemConvt (List<Item> items){
        List<String> ItemList = new ArrayList<>();
        for(Item item: items){
            String emp = String.format(
            """
            <html>
            ItemID:         %s<br>
            Itemname:       %s<br>
            UnitPrice:      %s<br>
            Quantity:       %s<br>
            Category:       %s
            </html>
            """, item.ItemID, item.Itemname, item.UnitPrice, item.Quantity, item.Category);
            ItemList.add(emp);
        }
        return ItemList;
    }
}
