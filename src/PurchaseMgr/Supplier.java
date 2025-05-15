package PurchaseMgr;

import InventoryMgr.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Supplier {
    public String SupplierID;
    public String SupplierName;
    public String ContactPerson;
    public String Phone;
    public String Email;
    public String Address;

    public Supplier(String SupplierID, String SupplierName, String ContactPerson, String Phone, String Email, String Address) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.ContactPerson = ContactPerson;
        this.Phone = Phone;
        this.Email = Email;
        this.Address = Address;
    }

    public static List<Supplier> listAllSupplier(String file){
        List<Supplier> listSupplier = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String SupplierID = "", SupplierName = "", ContactPerson = "", Phone = "", Email = "", Address = "";

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        SupplierID = line.substring(16);
                        break;
                    case 2:
                        SupplierName = line.substring(16);
                        break;
                    case 3:
                        ContactPerson = line.substring(16);
                        break;
                    case 4:
                        Phone = line.substring(16);
                        break;
                    case 5:
                        Email = line.substring(16);
                        break;
                    case 6:
                        Address = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        Supplier newsupplier = new Supplier(SupplierID, SupplierName, ContactPerson, Phone, Email, Address);
                        listSupplier.add(newsupplier);
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return listSupplier;
    }

    public static String idMaker(String filename) {
        List<Supplier> allSupplier = listAllSupplier(filename);
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "S", number);
            for (Supplier supplier : allSupplier) {
                if (Objects.equals(supplier.SupplierID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }
}
