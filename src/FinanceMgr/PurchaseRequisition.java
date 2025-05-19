package FinanceMgr;

import PurchaseMgr.PurchaseOrder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseRequisition {
    public String PurchaseReqID, ItemID, SupplierID, SalesMgrID;
    public int Quantity;
    public LocalDate ReqDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PurchaseRequisition(String PurchaseReqID, String ItemID,String SupplierID,int Quantity,
                               LocalDate ReqDate, String SalesMgrID){
        this.PurchaseReqID = PurchaseReqID;
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
        this.Quantity = Quantity;
        this.ReqDate = ReqDate;
        this.SalesMgrID = SalesMgrID;
    }

    public static List<PurchaseRequisition> listAllPurchaseRequisitions(String filename) {
        List<PurchaseRequisition> allPurchaseRequisitions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String PurchaseReqID = "", ItemID = "", SupplierID = "",SalesMgrID = "";
            int Quantity = 0;
            LocalDate ReqDate = null;
            int counter = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PurchaseReqID = line.substring(21);
                        break;
                    case 2:
                        ItemID = line.substring(21);
                        break;
                    case 3:
                        SupplierID = line.substring(21);
                        break;
                    case 4:
                        Quantity = Integer.parseInt(line.substring(21));
                        break;
                    case 5:
                        ReqDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 6:
                        SalesMgrID = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPurchaseRequisitions.add(new PurchaseRequisition(
                                PurchaseReqID, ItemID, SupplierID,Quantity, ReqDate,SalesMgrID
                        ));
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPurchaseRequisitions;
    }

    public static String idMaker(String filename) {
        List<PurchaseRequisition> allPurchaseRequisition = listAllPurchaseRequisitions(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PR", number);
            for (PurchaseRequisition purchaseRequisition : allPurchaseRequisition) {
                if (Objects.equals(purchaseRequisition.PurchaseReqID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }
    public static PurchaseRequisition getPurchaseReqID(String purchaseReqID, String filename){
        List<PurchaseRequisition> purchaseReqList = listAllPurchaseRequisitions(filename);
        PurchaseRequisition purchaseReq_temp = null;
        for (PurchaseRequisition purchaseRequisition: purchaseReqList) {
            if (Objects.equals(purchaseRequisition.PurchaseReqID, purchaseReqID)) {
                purchaseReq_temp = purchaseRequisition;
                break;
            }
        }
        return purchaseReq_temp;
    }
}