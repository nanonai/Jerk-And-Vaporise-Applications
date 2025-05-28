package PurchaseMgr;

import Admin.Main;
import Admin.CustomComponents;
import Admin.User;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

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

    public static void ModifyPurchaseOrder(String PurchaseOrderID, PurchaseOrder purchaseOrder, String filename) {
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(filename);
        for (PurchaseOrder po : purchaseOrderList) {
            if (Objects.equals(po.PurchaseOrderID, PurchaseOrderID)) {
                po.PurchaseOrderID = purchaseOrder.PurchaseOrderID;
                po.ItemID = purchaseOrder.ItemID;
                po.SupplierID = purchaseOrder.SupplierID;
                po.PurchaseQuantity = purchaseOrder.PurchaseQuantity;
                po.TotalAmt = purchaseOrder.TotalAmt;
                po.OrderDate = purchaseOrder.OrderDate;
                po.PurchaseMgrID = purchaseOrder.PurchaseMgrID;
                po.Status = purchaseOrder.Status;
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
                writer.write("~~~~~\n");
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

    public static void savePurchaseOrder(
            PurchaseOrder PO,
            String filename,
            JFrame parentFrame // for JOptionPane
    ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("PurchaseOrderID:     " + PO.PurchaseOrderID + "\n");
            writer.write("ItemID:              " + PO.ItemID + "\n");
            writer.write("SupplierID:          " + PO.SupplierID + "\n");
            writer.write("PurchaseQuantity:    " + PO.PurchaseQuantity + "\n");
            writer.write("TotalAmt:            " + PO.TotalAmt + "\n");
            writer.write("OrderDate:           " + PO.OrderDate.format(df) + "\n");
            writer.write("PurchaseMgrID:       " + PO.PurchaseMgrID + "\n");
            writer.write("Status:              " + PO.Status + "\n");
            writer.write("~~~~~\n");

            JOptionPane.showMessageDialog(parentFrame, "Purchase order saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "Error saving purchase order:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deletePurchaseOrder(
            String purchaseOrderId,
            String filename,
            JFrame parentFrame // for JOptionPane
    ) {
        try {
            File inputFile = new File(filename);
            List<String> lines = new ArrayList<>();
            Scanner scanner = new Scanner(inputFile);

            boolean skipBlock = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("PurchaseOrderID:")) {
                    if (line.contains(purchaseOrderId)) {
                        skipBlock = true;
                    } else {
                        skipBlock = false;
                    }
                }

                if (!skipBlock) {
                    lines.add(line);
                }

                if (line.equals("~~~~~")) {
                    skipBlock = false;
                }
            }
            scanner.close();

            // Write back to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (String l : lines) {
                    writer.write(l + "\n");
                }
            }

            JOptionPane.showMessageDialog(parentFrame, "Purchase order " + purchaseOrderId + " deleted successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "Error deleting purchase order:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateTotalAmountInFile(String PurchaseOrderID, PurchaseOrder purchaseOrder, double newTotalAmt) {
        List<PurchaseOrder> purchaseOrderList = listAllPurchaseOrders(Main.purchaseOrder_file);
        for (PurchaseOrder po : purchaseOrderList) {
            if (Objects.equals(po.PurchaseOrderID, PurchaseOrderID)) {
                po.PurchaseOrderID = purchaseOrder.PurchaseOrderID;
                po.ItemID = purchaseOrder.ItemID;
                po.SupplierID = purchaseOrder.SupplierID;
                po.PurchaseQuantity = purchaseOrder.PurchaseQuantity;
                po.TotalAmt = newTotalAmt;
                po.OrderDate = purchaseOrder.OrderDate;
                po.PurchaseMgrID = purchaseOrder.PurchaseMgrID;
                po.Status = purchaseOrder.Status;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.purchaseOrder_file))) {
            for (PurchaseOrder po : purchaseOrderList) {
                writer.write("PurchaseOrderID:     " + po.PurchaseOrderID + "\n");
                writer.write("ItemID:              " + po.ItemID + "\n");
                writer.write("SupplierID:          " + po.SupplierID + "\n");
                writer.write("PurchaseQuantity:    " + po.PurchaseQuantity + "\n");
                writer.write("TotalAmt:            " + po.TotalAmt + "\n");
                writer.write("OrderDate:           " + po.OrderDate + "\n");
                writer.write("PurchaseMgrID:       " + po.PurchaseMgrID + "\n");
                writer.write("Status:              " + po.Status + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}