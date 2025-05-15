package FinanceMgr;

import Common.BufferForPO;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    public String itemID,itemName,category,supplierID;
    public int quantity,threshold;
    public double unitPrice;
    public LocalDate lastUpdateDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Item(String itemID,String itemName,double unitPrice, int quantity,
                int threshold,String category,LocalDate lastUpdateDate,String supplierID){
        this.itemID = itemID;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.threshold = threshold;
        this.category = category;
        this.lastUpdateDate = lastUpdateDate;
        this.supplierID = supplierID;
    }

    public static List<Item> listAllItem(String filename) {
        List<Item> allItem= new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String itemID = "", itemName = "",supplierID = "",category = "";
            int quantity = 0, threshold = 0;
            double unitPrice = 0.0;
            LocalDate lastUpdateDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        itemID = line.substring(16);
                        break;
                    case 2:
                        itemName = line.substring(16);
                        break;
                    case 3:
                        unitPrice = Double.parseDouble(line.substring(16).trim());
                        break;
                    case 4:
                        quantity = Integer.parseInt(line.substring(16).trim());
                        break;
                    case 5:
                        threshold = Integer.parseInt(line.substring(16));
                        break;
                    case 6:
                        category = line.substring(16);
                        break;
                    case 7:
                        lastUpdateDate = LocalDate.parse(line.substring(16), df);
                        break;
                    case 8:
                        supplierID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allItem.add(new Item(itemID, itemName, unitPrice,quantity,threshold,category, lastUpdateDate,
                                supplierID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }
    public static Item getItemID(String itemID, String filename){
            List<Item> itemList = listAllItem(filename);
        Item intem_temp = null;
        for (Item item: itemList) {
            if (Objects.equals(item.itemID, itemID)) {
                intem_temp = item;
                break;
            }
        }
        return intem_temp;
    }
}


