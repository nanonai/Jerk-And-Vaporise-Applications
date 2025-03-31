public class Order {
    int orderID;
    String customerName, product, date;
    int quantity;
    double price;

    public Order(int orderID, String customerName, String product, int quantity, double price, String date) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
}