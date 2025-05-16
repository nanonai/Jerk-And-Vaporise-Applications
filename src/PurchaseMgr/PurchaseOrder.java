package PurchaseMgr;

import Admin.BufferForUser;
import Admin.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PurchaseOrder {
    public static PurchaseOrder getPurchaseOrderID;
    public String PurchaseOrderID, ItemID, SupplierID, PurchaseMgrID, Status;
    public int PurchaseQuantity;
    public double TotalAmt;
    public LocalDate OrderDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PurchaseOrder(String PurchaseOrderID,String ItemID,String SupplierID,int PurchaseQuantity,
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

    public static List<PurchaseOrder> listAllPurchaseOrders(String filename) {
        List<PurchaseOrder> allPurchaseOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String PurchaseOrderID = "", ItemID= "", SupplierID = "",PurchaseMgrID = " ", Status = "";
            int PurchaseQuantity = 0;
            double TotalAmt = 0.0;
            LocalDate OrderDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PurchaseOrderID = line.substring(21);
                        break;
                    case 2:
                        ItemID = line.substring(21);
                        break;
                    case 3:
                        SupplierID = line.substring(21);
                        break;
                    case 4:
                        PurchaseQuantity = Integer.parseInt(line.substring(21));
                        break;
                    case 5:
                        TotalAmt = Double.parseDouble(line.substring(21));
                        break;
                    case 6:
                        OrderDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 7:
                        PurchaseMgrID = line.substring(21);
                        break;
                    case 8:
                        Status = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPurchaseOrders.add(new PurchaseOrder(PurchaseOrderID,ItemID,SupplierID,
                                PurchaseQuantity,TotalAmt,OrderDate,PurchaseMgrID,Status));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPurchaseOrders;
    }

    public static void ChangePurOrderStatus(String PurchaseOrderID, BufferForPO buffer, String filename,String status) {
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
        for (PurchaseOrder po : purchaseOrderList) {
            if (Objects.equals(po.PurchaseOrderID, PurchaseOrderID)) {
                po.PurchaseOrderID = buffer.PurchaseOrderID;
                po.ItemID = buffer.ItemID;
                po.SupplierID = buffer.SupplierID;
                po.PurchaseQuantity = buffer.PurchaseQuantity;
                po.TotalAmt = buffer.TotalAmt;
                po.OrderDate = buffer.OrderDate;
                po.PurchaseMgrID = buffer.PurchaseMgrID;
                po.Status = status;  // change status
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PurchaseOrder po : purchaseOrderList) {
                writer.write("PurchaseOrderID:     " + po.PurchaseOrderID + "\n");
                writer.write("ItemID:              " + po.ItemID + "\n");
                writer.write("SupplierID:          " + po.SupplierID + "\n");
                writer.write("PurchaseQuantity:    " + po.PurchaseQuantity + "\n");
                writer.write("TotalAmt:            " + po.TotalAmt + "\n");
                writer.write("OrderDate:           " + po.OrderDate + "\n");
                writer.write("PurchaseMgrID:       " + po.PurchaseMgrID + "\n");
                writer.write("Status:              " + po.Status + "\n");
                writer.write("~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public static void ChangePurOrder(String PurchaseOrderID, BufferForPO buffer, String filename,String supplierID,int quantity, String status) {
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
        for (PurchaseOrder po : purchaseOrderList) {
            if (Objects.equals(po.PurchaseOrderID, PurchaseOrderID)) {
                po.PurchaseOrderID = buffer.PurchaseOrderID;
                po.ItemID = buffer.ItemID;
                po.SupplierID = supplierID;
                po.PurchaseQuantity = quantity;
                po.TotalAmt = buffer.TotalAmt;
                po.OrderDate = buffer.OrderDate;
                po.PurchaseMgrID = buffer.PurchaseMgrID;
                po.Status = status;  // change status
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PurchaseOrder po : purchaseOrderList) {
                writer.write("PurchaseOrderID:     " + po.PurchaseOrderID + "\n");
                writer.write("ItemID:              " + po.ItemID + "\n");
                writer.write("SupplierID:          " + po.SupplierID + "\n");
                writer.write("PurchaseQuantity:    " + po.PurchaseQuantity + "\n");
                writer.write("TotalAmt:            " + po.TotalAmt + "\n");
                writer.write("OrderDate:           " + po.OrderDate + "\n");
                writer.write("PurchaseMgrID:       " + po.PurchaseMgrID + "\n");
                writer.write("Status:              " + po.Status + "\n");
                writer.write("~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public static String idMaker(String filename) {
        List<PurchaseOrder> allPurchaseOrder = listAllPurchaseOrders(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PO", number);
            for (PurchaseOrder purchaseOrder : allPurchaseOrder) {
                if (Objects.equals(purchaseOrder.PurchaseOrderID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }
    
    public static PurchaseOrder getPurchaseOrderID(String purchaseOrderID, String filename){
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
        PurchaseOrder purchaseOrder_temp = null;
        for (PurchaseOrder purchaseOrder: purchaseOrderList) {
            if (Objects.equals(purchaseOrder.PurchaseOrderID, purchaseOrderID)) {
                purchaseOrder_temp = purchaseOrder;
                break;
            }
        }
        return purchaseOrder_temp;
    }

//    public static List<PurchaseOrder> listAllPOFromFilter(String filename, String purchaseOrderID, String filter, BufferForPO current_po) {
//        List<PurchaseOrder> po_list = listAllPurchaseOrders(filename);
//        List<PurchaseOrder> poID_list = new ArrayList<>();
//        List<PurchaseOrder> filtered_po_list = new ArrayList<>();
//        if (type.isEmpty() && filter.isEmpty()) {
//            if (current_po != null) {
//                int length = po_list.size();
//                for (int i = 0; i < length; i++) {
//                    if (Objects.equals(poID_list.get(i).PurchaseOrderID, current_po.PurchaseOrderID)) {
//                        poID_list.remove(i);
//                        break;
//                    }
//                }
//            }
//            return poID_list;
//        }
//        for (PurchaseOrder purchaseOrder: po_list) {
//            if (!Objects.equals(purchaseOrder.PurchaseOrderID, current_po.PurchaseOrderID)) {
//                if (Objects.equals(purchaseOrder.PurchaseOrderID, purchaseOrderID)) {
//                    poID_list.add(purchaseOrder);
//                } else if (purchaseOrderID.isEmpty()) {
//                    poID_list.add(purchaseOrder);
//                }
//            }
//        }
//        if (filter.isEmpty()) {
//            return poID_list;
//        }
//        for (PurchaseOrder purchaseOrder: poID_list) {
//            if ((purchaseOrder.PurchaseOrderID.toLowerCase().contains(filter.toLowerCase()) ||
//                    purchaseOrder.ItemID.toLowerCase().contains(filter.toLowerCase()) ||
//                    purchaseOrder.SupplierID.toLowerCase().contains(filter) ||
//                    purchaseOrder.PurchaseQuantity.contains(filter) ||
//                    purchaseOrder.TotalAmt.contains(filter) ||
//                    purchaseOrder.OrderDate.toString().contains(filter.toLowerCase()) ||
//                    purchaseOrder.PurchaseMgrID.toLowerCase().contains(filter.toLowerCase()) ||
//                    purchaseOrder.Status.toLowerCase().contains(filter.toLowerCase()))) {
//                filtered_po_list.add(purchaseOrder);
//            }
//        }
//        return filtered_po_list;
//    }


}