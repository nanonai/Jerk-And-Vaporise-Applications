package Common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BufferForItem {
    public String itemID,itemName,category,supplierID;
    public int quantity,threshold;
    public double unitPrice;
    public LocalDate lastUpdateDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BufferForItem(String itemID,String itemName,double unitPrice, int quantity,
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
}
