package Common;

import java.time.LocalDate;

public class BufferForPO {
    public String purchaseOrderID,purchaseReqID,itemID,status,purchaseManID,supplierID;
    public int quantity;
    public LocalDate orderDate;

    public BufferForPO(String purchaseOrderID,String purchaseReqID,String itemID,
                         int quantity,String supplierID,LocalDate orderDate,String purchaseManID,String status){
        this.purchaseOrderID = purchaseOrderID;
        this.purchaseReqID = purchaseReqID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.supplierID = supplierID;
        this.orderDate = orderDate;
        this.purchaseManID = purchaseManID;
        this.status = status;
    }
}