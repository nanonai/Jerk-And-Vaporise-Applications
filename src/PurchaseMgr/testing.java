package PurchaseMgr;

import java.util.List;

public class testing {
    public static void main(String[] args) {
        List<Item_Supplier> sorry = Item_Supplier.listAllItemSupplier("datafile/Item_Supplier.txt");
//        for (Item_Supplier i: sorry) {
//            System.out.println(i.ItemID);
//            System.out.println(i.SupplierID);
//            System.out.println(" ");
//        }

        sorry = Item_Supplier.listAllItemSupplierFromItemID("I7390", "datafile/Item_Supplier.txt");
        String sorry_text = "";
        for (Item_Supplier i: sorry) {
            System.out.println(i.ItemID);
            System.out.println(i.SupplierID);
            System.out.println(" ");
            sorry_text += i.SupplierID + "; ";
        }
        System.out.println(sorry_text);
    }
}
