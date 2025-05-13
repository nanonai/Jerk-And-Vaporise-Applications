package FinanceMgr;

import Common.BufferForPO;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventory {
    public String itemID,itemName;
    public int currentStock,threshold;
    public LocalDate lastUpdateDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Inventory(String itemID,String itemName,
                         int currentStock,LocalDate lastUpdateDate,int threshold){
        this.itemID = itemID;
        this.itemName = itemName;
        this.currentStock = currentStock;
        this.threshold = threshold;
        this.lastUpdateDate = lastUpdateDate;
    }

    public static List<Inventory> listAllInventory(String filename) {
        List<Inventory> allInventory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String itemID = "", itemName = "";
            int currentStock = 0, threshold = 0;
            LocalDate lastUpdateDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        itemID = line.substring(21);
                        break;
                    case 2:
                        itemName = line.substring(21);
                        break;
                    case 3:
                        currentStock = Integer.parseInt(line.substring(21));
                        break;
                    case 4:
                        lastUpdateDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 5:
                        threshold = Integer.parseInt(line.substring(21));
                        break;
                    default:
                        counter = 0;
                        allInventory.add(new Inventory(itemID, itemName, currentStock, lastUpdateDate,
                                threshold));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allInventory;
    }
}


