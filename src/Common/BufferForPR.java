package Common;

import java.time.LocalDate;

public class BufferForPR {
        public String purchaseReqID,itemID,purchaseManID;
        public int quantity;
        public LocalDate reqDate;

        public BufferForPR(String purchaseReqID,String itemID,
                           int quantity,LocalDate reqDate,String purchaseManID,String status){
            this.purchaseReqID = purchaseReqID;
            this.itemID = itemID;
            this.quantity = quantity;
            this.reqDate = reqDate;
            this.purchaseManID = purchaseManID;
        }
}

