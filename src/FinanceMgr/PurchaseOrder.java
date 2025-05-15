package FinanceMgr;

import Common.BufferForPO;
import Common.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseOrder {
    public static PurchaseOrder getPurchaseOrderID;
    public String purchaseOrderID,purchaseReqID,itemID,status,purchaseManID,supplierID;
    public int quantity;
    public LocalDate orderDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        List<PurchaseOrder> allPurchaseOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String purchaseOrderID = "", purchaseReqID = "", itemID = "", supplierID = "", purchaseManID = "", status = "";
            int quantity = 0;
            LocalDate orderDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        purchaseOrderID = line.substring(21);
                        break;
                    case 2:
                        purchaseReqID = line.substring(21);
                        break;
                    case 3:
                        itemID = line.substring(21);
                        break;
                    case 4:
                        quantity = Integer.parseInt(line.substring(21));
                        break;
                    case 5:
                        supplierID = line.substring(21);
                        break;
                    case 6:
                        orderDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 7:
                        purchaseManID = line.substring(21);
                        break;
                    case 8:
                        status = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPurchaseOrders.add(new PurchaseOrder(purchaseOrderID, purchaseReqID, itemID, quantity,
                                supplierID, orderDate, purchaseManID, status));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPurchaseOrders;
    }

    public static void ChangePurOrderStatus(String purchaseOrderID, BufferForPO buffer, String filename, String status) {
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
                po.status = status;  // change status
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
    public static PurchaseOrder getPurchaseOrderID(String purchaseOrderID, String filename){
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
            PurchaseOrder purchaseOrder_temp = null;
        for (PurchaseOrder purchaseOrder: purchaseOrderList) {
            if (Objects.equals(purchaseOrder.purchaseOrderID, purchaseOrderID)) {
                purchaseOrder_temp = purchaseOrder;
                break;
            }
        }
        return purchaseOrder_temp;
    }


}



