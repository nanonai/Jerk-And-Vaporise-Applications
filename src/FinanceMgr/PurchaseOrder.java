package FinanceMgr;

import Common.BufferForPO;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseOrder {
    public String purchaseOrderID,purchaseReqID,itemID,status,purchaseManID,supplierID;
    public int quantity;
    public LocalDate orderDate;

    public PurchaseOrder(String purchaseOrderID,String purchaseReqID,String itemID,
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

    public static List<PurchaseOrder> listAllPurchaseOrders(String filename) {
        List<PurchaseOrder> listAllPurchaseOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String purchaseOrderID = "", purchaseReqID = "", itemID = "", supplierID = "", purchaseManID = "", status = "";
            int quantity = 0;
            LocalDate orderDate = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PurchaseOrderID")) {
                    purchaseOrderID = line.substring(19).trim();
                } else if (line.startsWith("PurchaseReqID")) {
                    purchaseReqID = line.substring(19).trim();
                } else if (line.startsWith("ItemID")) {
                    itemID = line.substring(19).trim();
                } else if (line.startsWith("Quantity")) {
                    quantity = Integer.parseInt(line.substring(19).trim());
                } else if (line.startsWith("SupplierID")) {
                    supplierID = line.substring(19).trim();
                } else if (line.startsWith("OrderDate")) {
                    orderDate = LocalDate.parse(line.substring(19).trim()); // format is yyyy-MM-dd
                } else if (line.startsWith("UserID")) {
                    purchaseManID = line.substring(19).trim();
                } else if (line.startsWith("Status")) {
                    status = line.substring(19).trim();
                } else if (line.startsWith("~~~~~")) {
                    listAllPurchaseOrders.add(new PurchaseOrder(
                            purchaseOrderID, purchaseReqID, itemID, quantity,
                            supplierID, orderDate, purchaseManID, status
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listAllPurchaseOrders;
    }

    public static void ApprovePurOrder(String purchaseOrderID, BufferForPO buffer, String filename, String status) {
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
        for (PurchaseOrder po : purchaseOrderList) {
            if (Objects.equals(po.purchaseOrderID, purchaseOrderID)) {
                po.purchaseOrderID = buffer.purchaseOrderID;
                po.purchaseReqID = buffer.purchaseReqID;
                po.itemID = buffer.itemID;
                po.quantity = buffer.quantity;
                po.supplierID = buffer.supplierID;
                po.orderDate = buffer.orderDate;
                po.purchaseManID = buffer.purchaseManID;
                po.status = status;  // Use method parameter here
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PurchaseOrder po : purchaseOrderList) {
                writer.write("purchaseOrderID:     " + po.purchaseOrderID + "\n");
                writer.write("purchaseReqID:       " + po.purchaseReqID + "\n");
                writer.write("itemID:              " + po.itemID + "\n");
                writer.write("quantity:            " + po.quantity + "\n");
                writer.write("supplierID:          " + po.supplierID + "\n");
                writer.write("orderDate:           " + po.orderDate + "\n");
                writer.write("purchaseManID:       " + po.purchaseManID + "\n");
                writer.write("status:              " + po.status + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ViewPO(){

    }


}



