package SalesMgr;

import Common.User;
import jdk.jfr.Threshold;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Item {
    public int Quantity, Threshold;
    public String ItemID, ItemName, Category;
    public Set<String> SupplierID;
    public double UnitPrice;
    public LocalDate lastUpdate;

    public Item(String ItemID, String ItemName, double UnitPrice, int Quantity, int Threshold, String Category,
            Set<String> SupplierID) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.UnitPrice = UnitPrice;
        this.Quantity = Quantity;
        this.Threshold = Threshold;
        this.Category = Category;
//        this.lastUpdate = lastUpdate;
        this.SupplierID = SupplierID;
    }

    public static List<Item> listAllItem(String filename) {
        List<Item> allItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", ItemName = "", Category = "";
            int Quantity = 0, Threshold = 0;
            double UnitPrice = 0;
            Set<String> SupplierID = new HashSet<>();

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
                        Threshold = Integer.parseInt(line.substring(16));
                        break;
                    case 6:
                        Category = line.substring(16);
                        break;
                    case 7:
                        SupplierID =  new HashSet<String>(List.of(line.substring(16).split(", ")));
                        break;
                    default:
                        counter = 0;
                        allItem.add(new Item(ItemID, ItemName, UnitPrice, Quantity, Threshold, Category, SupplierID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }

    public static List<String> ItemConvt(List<Item> items) {
        List<String> ItemList = new ArrayList<>();
        for (Item item : items) {
            StringBuilder supID = new StringBuilder();
            int counter = 1;
            for (String id : item.SupplierID){
                supID.append(id);
                if (counter != item.SupplierID.size()){
                    supID.append(", ");
                }
                counter += 1;
            }
            String emp = String.format(
                    """
                    <html>
                    ItemID:         %s<br>
                    ItemName:       %s<br>
                    UnitPrice:      %s<br>
                    Quantity:       %s<br>
                    Threshold:      %s<br>
                    Category:       %s<br>
                    SupplierID:     %s
                    </html>
                    """, item.ItemID, item.ItemName, item.UnitPrice, item.Quantity, item.Threshold, item.Category, supID);  // Add SupplierName here
            ItemList.add(emp);
        }
        return ItemList;
    }

    public static String idMaker(String AccType, String filename) {
        List<Item> allItem = listAllItem(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E5);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 5) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "I", number);
            for (Item item : allItem) {
                if (Objects.equals(item.ItemID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static Boolean NameChecker(String name, String filename) {
        List<Item> allItem = listAllItem(filename);
        boolean repeated = false;
        if (name.length() > 150) {
            return false;
        }
        for (Item item : allItem) {
            if (Objects.equals(item.ItemName, name)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static Boolean PriceChecker(Double price) {
        return price <= 100000;
    }

    public static void saveNewItem(Item item, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            StringBuilder supID = new StringBuilder();
            int counter = 1;
            for (String id : item.SupplierID){
                supID.append(id);
                if (counter != item.SupplierID.size()){
                    supID.append(", ");
                }
                counter += 1;
            }
//            writer.write("ItemID:         " + item.ItemID + "\n");
//            writer.write("Username:       " + item.Username + "\n");
//            writer.write("Password:       " + item.Password + "\n");
//            writer.write("FullName:       " + item.FullName + "\n");
//            writer.write("Email:          " + item.Email + "\n");
//            writer.write("Phone:          " + item.Phone + "\n");
//            writer.write("AccType:        " + item.AccType + "\n");
//            writer.write("SupplierID:     " + supID + "\n");
//            writer.write("RememberMe:     0\n");
//            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}