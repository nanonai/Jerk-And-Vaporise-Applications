import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRecordReader {
    public static List<Order> readOrdersFromFile(String filename) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int orderID = 0, quantity = 0;
            String customer = "", product = "", date = "";
            double price = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[OrderID:")) {
                    orderID = Integer.parseInt(line.substring(9, line.length() - 1));
                } else if (line.startsWith("Customer: ")) {
                    customer = line.substring(10);
                } else if (line.startsWith("Product: ")) {
                    product = line.substring(9);
                } else if (line.startsWith("Quantity: ")) {
                    quantity = Integer.parseInt(line.substring(10));
                } else if (line.startsWith("Price: ")) {
                    price = Double.parseDouble(line.substring(7));
                } else if (line.startsWith("Date: ")) {
                    date = line.substring(6);
                } else if (line.equals("-----")) {
                    orders.add(new Order(orderID, customer, product, quantity, price, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}