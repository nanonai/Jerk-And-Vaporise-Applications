package SalesMgr;

import FinanceMgr.PurchaseRequisition;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sales {
    public String SalesID, SalesMgrID;
    public LocalDate SalesDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Sales(String SalesID, LocalDate SalesDate, String SalesMgrID) {
        this.SalesID = SalesID;
        this.SalesDate = SalesDate;
        this.SalesMgrID = SalesMgrID;
    }

    public static List<Sales> listAllSales(String filename) {
        List<Sales> allSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String SalesID = "", SalesMgrID = "";
            LocalDate SalesDate = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        SalesID = line.substring(16);
                        break;
                    case 2:
                        SalesDate = LocalDate.parse(line.substring(16), df);
                        break;
                    case 3:
                        SalesMgrID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allSales.add(new Sales(SalesID, SalesDate, SalesMgrID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allSales;
    }

    public static String idMaker(String filename) {
        List<Sales> allSales = listAllSales(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "SL", number);
            for (Sales sales : allSales) {
                if (Objects.equals(sales.SalesID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static void saveNewSales(Sales sales, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("SalesID:        " + sales.SalesID + "\n");
            writer.write("SalesDate:      " + sales.SalesDate + "\n");
            writer.write("SalesMgrID:     " + sales.SalesMgrID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ModifySales(String SalesID, Sales sales, String filename) {
        List<Sales> salesList = listAllSales(filename);
        for (Sales sl : salesList) {
            if (Objects.equals(sl.SalesID, SalesID)) {
                sl.SalesID = sales.SalesID;
                sl.SalesDate = sales.SalesDate;
                sl.SalesMgrID = sales.SalesMgrID;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Sales sl : salesList) {
                writer.write("SalesID:        " + sl.SalesID + "\n");
                writer.write("SalesDate:      " + sl.SalesDate + "\n");
                writer.write("SalesMgrID:     " + sl.SalesMgrID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
