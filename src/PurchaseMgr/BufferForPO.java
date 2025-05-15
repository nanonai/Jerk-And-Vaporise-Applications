package PurchaseMgr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BufferForPO {
    public static PurchaseOrder getPurchaseOrderID;
    public String PurchaseOrderID,ItemID,SupplierID,PurchaseMgrID,Status;
    public int PurchaseQuantity;
    public double TotalAmt;
    public LocalDate OrderDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BufferForPO(String PurchaseOrderID,String ItemID,String SupplierID,int PurchaseQuantity,
                       double TotalAmt,LocalDate OrderDate,String PurchaseMgrID,String Status){
        this.PurchaseOrderID = PurchaseOrderID;
        this.ItemID =   ItemID;
        this.SupplierID = SupplierID;
        this.PurchaseQuantity = PurchaseQuantity;
        this.TotalAmt = TotalAmt;
        this.OrderDate = OrderDate;
        this.PurchaseMgrID = PurchaseMgrID;
        this.Status = Status;
    }
}