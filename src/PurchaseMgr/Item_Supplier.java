package PurchaseMgr;

import SalesMgr.Item_Sales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item_Supplier {
    public String ItemID, SupplierID;

    public Item_Supplier(String ItemID, String SupplierID) {
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
    }

    public static List<Item_Supplier> listAllItemSupplier(String filename) {
        List<Item_Supplier> allItemSupplier = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", SupplierID = "";

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        SupplierID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allItemSupplier.add(new Item_Supplier(ItemID, SupplierID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItemSupplier;
    }

    public static List<Item_Supplier> listAllItemSupplierFromItemID(String id, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, id)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static List<Item_Supplier> listAllItemSupplierFromSupplierID(String id, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.SupplierID, id)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static List<Item_Supplier> listAllItemSupplierFromBothID(String ItemID, String SupplierID, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, ItemID) && Objects.equals(itemSupplier.SupplierID, SupplierID)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static Boolean RedundancyChecker(String ItemID, String SupplierID, String filename) {
        List<Item_Supplier> sameItem = listAllItemSupplierFromBothID(ItemID, SupplierID, filename);
        return sameItem.isEmpty();
    }

    public static void saveNewItemSupplier(Item_Supplier itemSupplier, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("ItemID:         " + itemSupplier.ItemID + "\n");
            writer.write("SupplierID:     " + itemSupplier.SupplierID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
