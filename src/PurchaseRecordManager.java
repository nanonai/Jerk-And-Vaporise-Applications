import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PurchaseRecordManager {
    public static void saveOrdersToFile(List<Order> orders, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Order order : orders) {
                writer.write("[OrderID:" + order.orderID + "]\n");
                writer.write("Customer: " + order.customerName + "\n");
                writer.write("Product: " + order.product + "\n");
                writer.write("Quantity: " + order.quantity + "\n");
                writer.write("Price: " + order.price + "\n");
                writer.write("Date: " + order.date + "\n");
                writer.write("-----\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}