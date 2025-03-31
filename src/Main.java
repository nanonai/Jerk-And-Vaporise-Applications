import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Directly specify the filename
        String filename = "purchasing_records.txt";

        // Create sample records
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1001, "John Doe", "Laptop", 1, 1200.00, "2025-03-31"));
        orders.add(new Order(1002, "Jane Smith", "Phone", 2, 800.00, "2025-03-30"));

        // Save records to the file
        PurchaseRecordManager.saveOrdersToFile(orders, filename);
        System.out.println("Records saved successfully to " + filename);

        // Read and print records
        List<Order> readOrders = PurchaseRecordReader.readOrdersFromFile(filename);
        System.out.println("\nLoaded Orders:");
        for (Order order : readOrders) {
            System.out.println("Order ID: " + order.orderID + ", Customer: " + order.customerName +
                    ", Product: " + order.product + ", Quantity: " + order.quantity +
                    ", Price: " + order.price + ", Date: " + order.date);
        }
    }
}