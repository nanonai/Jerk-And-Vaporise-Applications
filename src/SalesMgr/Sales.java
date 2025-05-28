package SalesMgr;

import Admin.Main;
import FinanceMgr.PurchaseRequisition;
import InventoryMgr.Item;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sales {
    public String SalesID, SalesMgrID, ItemName;
    public int Quantity;
    public double Amount;
    public LocalDate SalesDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Sales(String SalesID, LocalDate SalesDate, String SalesMgrID) {
        this.SalesID = SalesID;
        this.SalesDate = SalesDate;
        this.SalesMgrID = SalesMgrID;
    }

    public static List<Sales> listAllSales(String filename) {
        List<Sales> allSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String SalesID = "", SalesMgrID = "";
            LocalDate SalesDate = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        SalesID = line.substring(16);
                        break;
                    case 2:
                        SalesDate = LocalDate.parse(line.substring(16), df);
                        break;
                    case 3:
                        SalesMgrID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allSales.add(new Sales(SalesID, SalesDate, SalesMgrID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allSales;
    }

    public static String idMaker(String filename) {
        List<Sales> allSales = listAllSales(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "SL", number);
            for (Sales sales : allSales) {
                if (Objects.equals(sales.SalesID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static void saveNewSales(Sales sales, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("SalesID:        " + sales.SalesID + "\n");
            writer.write("SalesDate:      " + sales.SalesDate + "\n");
            writer.write("SalesMgrID:     " + sales.SalesMgrID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ModifySales(String SalesID, Sales sales, String filename) {
        List<Sales> salesList = listAllSales(filename);
        for (Sales sl : salesList) {
            if (Objects.equals(sl.SalesID, SalesID)) {
                sl.SalesID = sales.SalesID;
                sl.SalesDate = sales.SalesDate;
                sl.SalesMgrID = sales.SalesMgrID;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Sales sl : salesList) {
                writer.write("SalesID:        " + sl.SalesID + "\n");
                writer.write("SalesDate:      " + sl.SalesDate + "\n");
                writer.write("SalesMgrID:     " + sl.SalesMgrID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    public static String ValidityChecker(String itemName, String soldQuantity, String revenue) {
        String indicator = "";

        System.out.println("Checking Item: " + itemName);
        boolean itemExists = false;
        Item matchedItem = null;

        if (itemName != null && !itemName.trim().isEmpty()) {
            List<Item> itemList = Item.listAllItem(Main.item_file);

            for (Item item : itemList) {
                System.out.println("Comparing: [" + item.ItemName + "] with [" + itemName + "]");
                if (item.ItemName.equalsIgnoreCase(itemName.trim())) {
                    itemExists = true;
                    matchedItem = item;
                    break;
                }
            }

            if (itemExists) {
                indicator += "1";
            } else {
                indicator += "0";
            }
        } else {
            indicator += "0";
        }

        boolean quantityValid = false;
        int quantityInt = 0;
        if (soldQuantity.matches("\\d+")) {
            quantityInt = Integer.parseInt(soldQuantity);
            if (quantityInt > 0) {
                quantityValid = true;
            }
        }

        if (quantityValid) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("Sold Quantity validity: " + indicator);

        if (revenue.matches("\\d+(\\.\\d{1,2})?") && Double.parseDouble(revenue) > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("Revenue validity: " + indicator);

        if (itemExists && quantityValid && matchedItem != null) {
            List<Item_Sales> itemSalesList = Item_Sales.listAllItemSales(Main.item_sales_file);
            int totalSold = 0;

            for (Item_Sales itemSales : itemSalesList) {
                if (itemSales.ItemID.equals(matchedItem.ItemID)) {
                    totalSold += itemSales.Quantity;
                }
            }

            if ((totalSold + quantityInt) > matchedItem.StockCount) {
                indicator += "0";
            } else {
                indicator += "1";
            }
        } else {
            indicator += "1";
        }

        System.out.println("Stock count validity: " + indicator);

        return indicator;
    }

    public static void removeSales(String SalesID, String filename) {
        List<Sales> allSales = Sales.listAllSales(filename);
        allSales.removeIf(sales -> Objects.equals(sales.SalesID, SalesID));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Sales sales : allSales) {
                writer.write("SalesID:        " + sales.SalesID + "\n");
                writer.write("SalesDate:      " + sales.SalesDate + "\n");
                writer.write("SalesMgrID:     " + sales.SalesMgrID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Sales getSalesByID(String salesID, String filename) {
        List<Sales> salesList = listAllSales(filename);
        Sales tempSales = null;
        for (Sales sales : salesList) {
            if (Objects.equals(sales.SalesID, salesID)) {
                tempSales = sales;
                break;
            }
        }
        return tempSales;
    }

}
