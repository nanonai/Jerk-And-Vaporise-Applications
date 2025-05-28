package SalesMgr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item_Sales {
    public String ItemID, SalesID;
    public int Quantity;
    public double Amount;

    public Item_Sales(String ItemID, String SalesID, int Quantity, double Amount) {
        this.ItemID = ItemID;
        this.SalesID = SalesID;
        this.Quantity = Quantity;
        this.Amount = Amount;
    }

    public static List<Item_Sales> listAllItemSales(String filename) {
        List<Item_Sales> allItemSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", SalesID = "";
            int Quantity = 0;
            double Amount = 0;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        SalesID = line.substring(16);
                        break;
                    case 3:
                        Quantity = Integer.parseInt(line.substring(16));
                        break;
                    case 4:
                        Amount = Double.parseDouble(line.substring(16));
                        break;
                    default:
                        counter = 0;
                        allItemSales.add(new Item_Sales(ItemID, SalesID, Quantity, Amount));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItemSales;
    }

    public static List<Item_Sales> listAllItemSalesFromItemID(String id, String filename) {
        List<Item_Sales> allItemSales = listAllItemSales(filename);
        List<Item_Sales> filteredList = new ArrayList<>();
        for (Item_Sales itemSales: allItemSales) {
            if (Objects.equals(itemSales.ItemID, id)) {
                filteredList.add(itemSales);
            }
        }
        return filteredList;
    }

    public static List<Item_Sales> listAllItemSalesFromSalesID(String id, String filename) {
        List<Item_Sales> allItemSales = listAllItemSales(filename);
        List<Item_Sales> filteredList = new ArrayList<>();
        for (Item_Sales itemSales: allItemSales) {
            if (Objects.equals(itemSales.SalesID, id)) {
                filteredList.add(itemSales);
            }
        }
        return filteredList;
    }

    public static List<Item_Sales> listAllItemSalesFromBothID(String ItemID, String SalesID, String filename) {
        List<Item_Sales> allItemSales = listAllItemSales(filename);
        List<Item_Sales> filteredList = new ArrayList<>();
        for (Item_Sales itemSales: allItemSales) {
            if (Objects.equals(itemSales.ItemID, ItemID) && Objects.equals(itemSales.SalesID, SalesID)) {
                filteredList.add(itemSales);
            }
        }
        return filteredList;
    }

    public static Boolean RedundancyChecker(String ItemID, String SalesID, String filename) {
        List<Item_Sales> sameItem = listAllItemSalesFromBothID(ItemID, SalesID, filename);
        return sameItem.isEmpty();
    }

    public static void saveNewItemSales(Item_Sales itemSales, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("ItemID:         " + itemSales.ItemID + "\n");
            writer.write("SalesID:        " + itemSales.SalesID + "\n");
            writer.write("Quantity:       " + itemSales.Quantity + "\n");
            writer.write("Amount:         " + itemSales.Amount + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void modifyItemSales(Item_Sales updatedSales, String filename) {
        List<Item_Sales> allItemSales = listAllItemSales(filename);
        boolean found = false;

        for (Item_Sales itemSales : allItemSales) {
            if (Objects.equals(itemSales.SalesID, updatedSales.SalesID)) {
                // Update the matching record
                itemSales.ItemID = updatedSales.ItemID;
                itemSales.Quantity = updatedSales.Quantity;
                itemSales.Amount = updatedSales.Amount;
                found = true;
                break;
            }
        }

        // If a matching sales record was found, save the changes
        if (found) {
            try (FileWriter writer = new FileWriter(filename)) {
                for (Item_Sales itemSales : allItemSales) {
                    writer.write("ItemID:         " + itemSales.ItemID + "\n");
                    writer.write("SalesID:        " + itemSales.SalesID + "\n");
                    writer.write("Quantity:       " + itemSales.Quantity + "\n");
                    writer.write("Amount:         " + itemSales.Amount + "\n");
                    writer.write("~~~~~\n");
                }
            } catch (IOException e) {
                e.printStackTrace(); // Log the error
            }
        }
    }

    public static void removeItemSalesBySalesID(String SalesID, String filename) {
        List<Item_Sales> allItemSales = Item_Sales.listAllItemSales(filename);
        allItemSales.removeIf(itemSales -> Objects.equals(itemSales.SalesID, SalesID));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item_Sales itemSales : allItemSales) {
                writer.write("ItemID:         " + itemSales.ItemID + "\n");
                writer.write("SalesID:        " + itemSales.SalesID + "\n");
                writer.write("Quantity:       " + itemSales.Quantity + "\n");
                writer.write("Amount:         " + itemSales.Amount + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
