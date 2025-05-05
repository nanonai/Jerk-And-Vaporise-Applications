package FinanceMgr;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Common.BufferForPR;

public class PurchaseRequisition {
    public String purchaseReqID,itemID,purchaseManID;
    public int quantity;
    public LocalDate reqDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PurchaseRequisition(String purchaseReqID, String itemID,
                               int quantity,LocalDate reqDate, String purchaseManID){
        this.purchaseReqID = purchaseReqID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.reqDate = reqDate;
        this.purchaseManID = purchaseManID;
    }

    public static List<PurchaseRequisition> listAllPurchaseRequisitions(String filename) {
        List<PurchaseRequisition> allPurchaseRequisitions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String purchaseReqID = "", itemID = "", purchaseManID = "";
            int quantity = 0;
            LocalDate reqDate = null;
            int counter = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        purchaseReqID = line.substring(21);
                        break;
                    case 2:
                        itemID = line.substring(21);
                        break;
                    case 3:
                        quantity = Integer.parseInt(line.substring(21));
                        break;
                    case 4:
                        reqDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 5:
                        purchaseManID = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPurchaseRequisitions.add(new PurchaseRequisition(
                                purchaseReqID, itemID, quantity,reqDate, purchaseManID
                        ));
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPurchaseRequisitions;
    }

}
