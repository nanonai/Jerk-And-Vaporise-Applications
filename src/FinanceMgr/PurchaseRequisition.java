package FinanceMgr;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Common.BufferForPR;

import static Common.User.df;

public class PurchaseRequisition {
    public String purchaseReqID,itemID,purchaseManID;
    public int quantity;
    public LocalDate reqDate;

    public PurchaseRequisition(String purchaseReqID, String itemID,
                               int quantity,LocalDate reqDate, String purchaseManID){
        this.purchaseReqID = purchaseReqID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.reqDate = reqDate;
        this.purchaseManID = purchaseManID;
    }

    public static List<PurchaseRequisition> listAllPurchaseRequisitions(String filename) {
        List<PurchaseRequisition> listAllPurchaseRequisitions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String purchaseReqID = "", itemID = "", purchaseManID = "";
            int quantity = 0;
            LocalDate reqDate = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PurchaseReqID")) {
                    purchaseReqID = line.substring(21);
                } else if (line.startsWith("ItemID")) {
                    itemID = line.substring(21);
                } else if (line.startsWith("Quantity")) {
                    quantity = Integer.parseInt(line.substring(21));
                } else if (line.startsWith("ReqDate")) {
                    reqDate = LocalDate.parse(line.substring(21), df);
                } else if (line.startsWith("UserID")) {
                    purchaseManID = line.substring(21);
                } else if (line.startsWith("~~~~~")) {
                    listAllPurchaseRequisitions.add(new PurchaseRequisition(
                            purchaseReqID, itemID, quantity,reqDate, purchaseManID
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listAllPurchaseRequisitions;
    }

}
