package InventoryMgr;

import PurchaseMgr.Supplier;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Item {
    public String ItemID, ItemName, Category;
    public double UnitPrice, UnitCost;
    public int StockCount, Threshold;
    public LocalDate LastUpdate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Item(String ItemID, String ItemName, double UnitPrice, double UnitCost,
                int StockCount, int Threshold, String Category, LocalDate LastUpdate) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.UnitPrice = UnitPrice;
        this.UnitCost = UnitCost;
        this.StockCount = StockCount;
        this.Threshold = Threshold;
        this.Category = Category;
        this.LastUpdate = LastUpdate;
    }

    public static List<Item> listAllItem(String filename) {
        List<Item> allItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", ItemName = "", Category = "";
            double UnitPrice = 0, UnitCost = 0;
            int StockCount = 0, Threshold = 0;
            LocalDate LastUpdate = null;

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
                        UnitCost = Double.parseDouble(line.substring(16));
                        break;
                    case 5:
                        StockCount = Integer.parseInt(line.substring(16));
                        break;
                    case 6:
                        Threshold = Integer.parseInt(line.substring(16));
                        break;
                    case 7:
                        Category = line.substring(16);
                        break;
                    case 8:
                        LastUpdate = LocalDate.parse(line.substring(16), df);
                        break;
                    default:
                        counter = 0;
                        allItem.add(new Item(ItemID, ItemName, UnitPrice, UnitCost,
                                StockCount, Threshold, Category, LastUpdate));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }

    public static String idMaker(String filename) {
        List<Item> allItem = listAllItem(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
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

    public static Boolean nameChecker(String name, String filename) {
        List<Item> allItem = listAllItem(filename);
        boolean repeated = false;
        for (Item item : allItem) {
            if (Objects.equals(item.ItemName, name)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static void saveNewItem(Item item, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("ItemID:         " + item.ItemID + "\n");
            writer.write("ItemName:       " + item.ItemName + "\n");
            writer.write("UnitPrice:      " + item.UnitPrice + "\n");
            writer.write("UnitCost:       " + item.UnitCost + "\n");
            writer.write("StockCount:     " + item.StockCount + "\n");
            writer.write("Threshold:      " + item.Threshold + "\n");
            writer.write("Category:       " + item.Category + "\n");
            writer.write("LastUpdate:     " + item.LastUpdate + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static String validitychecker(String ItemName, String UnitPrice, String UnitCost, String StockCount,
                                         String Threshold,
                                         String SupplierName, String filename) {
        // Sample output: 0X0X00X0X
        String indicator = "";

        // Check item name length (8 to 48 characters)
        if (ItemName.length() >= 8 && ItemName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("ItemName validity: " + indicator);

        if (nameChecker(ItemName, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }

        if (UnitPrice.matches("\\d+(\\.\\d{1,2})?") && Double.parseDouble(UnitPrice) > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("UnitPrice validity string: " + indicator);

        if (UnitCost.matches("\\d+(\\.\\d{1,2})?") && Double.parseDouble(UnitCost) > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("UnitCost validity string: " + indicator);

        if (StockCount.matches("\\d+") && Integer.parseInt(StockCount) >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("StockCount validity string: " + indicator);
        // Validate threshold (non-negative integer)
        if (Threshold.matches("\\d+") && Integer.parseInt(Threshold) >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("Threshold validity string: " + indicator);

        System.out.println("Checking Supplier: " + SupplierName);  // Debug log
        if (!SupplierName.trim().isEmpty()) {
            String supplierID = Supplier.getSupplierID(SupplierName);  // Validate supplier ID
            System.out.println("Supplier ID: " + supplierID);  // Debug log

            if ("Unknown".equals(supplierID)) {
                indicator += "0";  // Supplier not found
            } else {
                indicator += "1";  // Supplier exists
            }
        } else {
            indicator += "0";  // Supplier name is empty
        }

        return indicator;
    }


    public static Item getItemID(String ItemID, String filename){
        List<Item> itemList = listAllItem(filename);
        Item item_temp = null;
        for (Item item: itemList) {
            if (Objects.equals(item.ItemID, ItemID)) {
                item_temp = item;
                break;
            }
        }
        return item_temp;
    }
    public static void removeItem(String ItemID, String filename) {
        List<Item> allItems = Item.listAllItem(filename);  // Assuming `listAllItem` fetches all items
        allItems.removeIf(item -> Objects.equals(item.ItemID, ItemID));  // Remove item with matching ItemID

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item item : allItems) {
                writer.write("ItemID:         " + item.ItemID + "\n");
                writer.write("ItemName:       " + item.ItemName + "\n");
                writer.write("UnitPrice:      " + item.UnitPrice + "\n");
                writer.write("UnitCost:       " + item.UnitCost + "\n");
                writer.write("StockCount:     " + item.StockCount + "\n");
                writer.write("Threshold:      " + item.Threshold + "\n");
                writer.write("Category:       " + item.Category + "\n");
                writer.write("LastUpdate:     " + item.LastUpdate + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}