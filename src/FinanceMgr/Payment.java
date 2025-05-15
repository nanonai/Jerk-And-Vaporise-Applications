package FinanceMgr;

import InventoryMgr.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static InventoryMgr.Item.listAllItem;

public class Payment {
    public String PaymentID, PurchaseOrderID, FinanceMrgID;
    public double Amount;
    public LocalDate PaymentDate;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Payment(String PaymentID,String PurchaseOrderID,
                   double Amount,LocalDate PaymentDate,String FinanceMrgID){
        this.PaymentID = PaymentID;
        this.PurchaseOrderID = PurchaseOrderID;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.FinanceMrgID = FinanceMrgID;
    }

    public static List<Payment> listAllPayment(String filename) {
        List<Payment> allPayment = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String PaymentID = "", PurchaseOrderID = "",FinanceMrgID="";
            double Amount = 0;
            LocalDate PaymentDate = null;
            String status = "";
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PaymentID = line.substring(21);
                        break;
                    case 2:
                        PurchaseOrderID = line.substring(21);
                        break;
                    case 3:
                        Amount = Double.parseDouble(line.substring(21).trim());
                        break;
                    case 4:
                        PaymentDate = LocalDate.parse(line.substring(21).trim(), df);
                        break;
                    case 5:
                        FinanceMrgID = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPayment.add(new Payment(PaymentID, PurchaseOrderID, Amount, PaymentDate,
                                FinanceMrgID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPayment;
    }

    public static String idMaker(String filename) {
        List<Payment> allPayment = listAllPayment(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PY", number);
            for (Payment payment : allPayment) {
                if (Objects.equals(payment.PaymentID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static Payment getPaymentID(String paymentID, String filename){
        List<Payment> paymentList = listAllPayment(filename);
        Payment payment_temp = null;
        for (Payment payment: paymentList) {
            if (Objects.equals(payment.PaymentID, paymentID)) {
                payment_temp = payment;
                break;
            }
        }
        return payment_temp;
    }
}