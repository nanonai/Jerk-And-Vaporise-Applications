package FinanceMgr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment {
    public String paymentID,purchaseOrderID,status;
    public double amount;
    public LocalDate date;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Payment(String paymentID,String purchaseOrderID,
                     double amount,LocalDate date,String status){
        this.paymentID = paymentID;
        this.purchaseOrderID = purchaseOrderID;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public static List<Payment> listAllPayment(String filename) {
        List<Payment> allPayment = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String paymentID = "", purchaseOrderID = "";
            double amount = 0;
            LocalDate date = null;
            String status = "";
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        paymentID = line.substring(21);
                        break;
                    case 2:
                        purchaseOrderID = line.substring(21);
                        break;
                    case 3:
                        amount = Double.parseDouble(line.substring(21).trim());
                        break;
                    case 4:
                        date = LocalDate.parse(line.substring(21).trim(), df);
                        break;
                    case 5:
                        status = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPayment.add(new Payment(paymentID, purchaseOrderID, amount, date,
                                status));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPayment;
    }
    public static Payment getPaymentID(String paymentID, String filename){
        List<Payment> paymentList = listAllPayment(filename);
        Payment payment_temp = null;
        for (Payment payment: paymentList) {
            if (Objects.equals(payment.paymentID, paymentID)) {
                payment_temp = payment;
                break;
            }
        }
        return payment_temp;
    }


}


