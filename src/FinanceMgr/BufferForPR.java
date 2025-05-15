package FinanceMgr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BufferForPR {
    public String PurchaseReqID,ItemID,SupplierID,SalesMrgID;
    public int Quantity;
    public LocalDate ReqDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BufferForPR(String PurchaseReqID, String ItemID,String SupplierID,int Quantity,
                       LocalDate ReqDate, String SalesMrgID){
        this.PurchaseReqID = PurchaseReqID;
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
        this.Quantity = Quantity;
        this.ReqDate = ReqDate;
        this.SalesMrgID = SalesMrgID;
    }
}