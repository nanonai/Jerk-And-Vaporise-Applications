package FinanceMgr;

import InventoryMgr.Item;
import PurchaseMgr.PurchaseOrder;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseRequisition {
    public String PurchaseReqID, ItemID, SupplierID, SalesMgrID;
    public int Quantity, Status;
    public LocalDate ReqDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PurchaseRequisition(String PurchaseReqID, String ItemID, String SupplierID, int Quantity,
                               LocalDate ReqDate, String SalesMgrID, int Status){
        this.PurchaseReqID = PurchaseReqID;
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
        this.Quantity = Quantity;
        this.ReqDate = ReqDate;
        this.SalesMgrID = SalesMgrID;
        this.Status = Status;
    }

    public static List<PurchaseRequisition> listAllPurchaseRequisitions(String filename) {
        List<PurchaseRequisition> allPurchaseRequisitions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String PurchaseReqID = "", ItemID = "", SupplierID = "",SalesMgrID = "";
            int Quantity = 0, Status = 0;
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
                    case 7:
                        Status = Integer.parseInt(line.substring(21));
                        break;
                    default:
                        counter = 0;
                        allPurchaseRequisitions.add(new PurchaseRequisition(
                                PurchaseReqID, ItemID, SupplierID, Quantity, ReqDate, SalesMgrID, Status
                        ));
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPurchaseRequisitions;
    }

    public static List<PurchaseRequisition> listAllPRFromFilter(String filename, String filter) {
        List<PurchaseRequisition> pr_list = listAllPurchaseRequisitions(filename);
        List<PurchaseRequisition> filtered_pr_list = new ArrayList<>();
        if (filter.isEmpty()) {
            return pr_list;
        }
        for (PurchaseRequisition pr: pr_list) {
            String temp = (pr.Status == 0) ? (pr.ReqDate.isBefore(LocalDate.now())) ? "Overdue" : "Pending" : "Processed";
            if ((pr.PurchaseReqID.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    pr.ItemID.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    pr.SupplierID.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    Integer.toString(pr.Quantity).contains(filter.toLowerCase().replace(" ", "")) ||
                    pr.ReqDate.toString().contains(filter.toLowerCase().replace(" ", "")) ||
                    pr.SalesMgrID.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    temp.toLowerCase().contains(filter.toLowerCase().replace(" ", "")))) {
                filtered_pr_list.add(pr);
            }
        }
        return filtered_pr_list;
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

    public static PurchaseRequisition getPurchaseReqByID(String purchaseReqID, String filename){
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

    public static void ModifyPurchaseRequisition(String PurchaseReqID, PurchaseRequisition purchaseRequisition, String filename) {
        List<PurchaseRequisition> purchaseRequisitionList = listAllPurchaseRequisitions(filename);
        for (PurchaseRequisition pr : purchaseRequisitionList) {
            if (Objects.equals(pr.PurchaseReqID, PurchaseReqID)) {
                pr.PurchaseReqID = purchaseRequisition.PurchaseReqID;
                pr.ItemID = purchaseRequisition.ItemID;
                pr.SupplierID = purchaseRequisition.SupplierID;
                pr.Quantity = purchaseRequisition.Quantity;
                pr.ReqDate = purchaseRequisition.ReqDate;
                pr.SalesMgrID = purchaseRequisition.SalesMgrID;
                pr.Status = purchaseRequisition.Status;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PurchaseRequisition pr : purchaseRequisitionList) {
                writer.write("PurchaseReqID :      " + pr.PurchaseReqID + "\n");
                writer.write("ItemID:              " + pr.ItemID + "\n");
                writer.write("SupplierID:          " + pr.SupplierID + "\n");
                writer.write("Quantity:            " + pr.Quantity + "\n");
                writer.write("ReqDate:             " + pr.ReqDate + "\n");
                writer.write("SalesMgrID:          " + pr.SalesMgrID + "\n");
                writer.write("Status:              " + pr.Status + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void savePurchaseRequisition(
            PurchaseRequisition PR,
            String filename,
            JFrame parentFrame // for JOptionPane
    ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("PurchaseReqID :      " + PR.PurchaseReqID + "\n");
            writer.write("ItemID:              " + PR.ItemID + "\n");
            writer.write("SupplierID:          " + PR.SupplierID + "\n");
            writer.write("Quantity:            " + PR.Quantity + "\n");
            writer.write("ReqDate:             " + PR.ReqDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n");
            writer.write("SalesMgrID:          " + PR.SalesMgrID + "\n");
            writer.write("Status:              " + PR.Status + "\n");
            writer.write("~~~~~\n");

            JOptionPane.showMessageDialog(parentFrame, "Purchase requisition saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "Error saving purchase requisition:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static PurchaseRequisition getPurchaseReqID(String purchaseReqID, String filename){
        List<PurchaseRequisition> purchaseRequisitionList = listAllPurchaseRequisitions(filename);
        PurchaseRequisition purchaseReq_temp = null;
        for (PurchaseRequisition purchaseRequisition : purchaseRequisitionList) {
            if (Objects.equals(purchaseRequisition.PurchaseReqID, purchaseReqID)) {
                purchaseReq_temp = purchaseRequisition;
                break;
            }
        }
        return purchaseReq_temp;
    }
}