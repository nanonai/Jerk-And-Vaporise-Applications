package PurchaseMgr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Import the Objects class

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

    public static Supplier getSupplierByID(String SupplierID, String filename){
        List<Supplier> supplierList = listAllSupplier(filename);
        for (Supplier supplier : supplierList){
            if (Objects.equals(supplier.SupplierID, SupplierID)){
                return supplier;
            }
        }
        return null;
    }

    public static Supplier getSupplierByName(String SupplierName, String filename) {
        // itemList is data inside txt file
        List<Supplier> supplierList = listAllSupplier(filename);
        // item (each item inside txt file)
        for (Supplier supplier : supplierList) {  //    below store ItemName inside combobox
            if (Objects.equals(supplier.SupplierName, SupplierName)) {
                return supplier;
            }
        }
        return null;
    }

    public static String getSupplierID(String supplierName) {
        String filePath = "datafile/supplier.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String foundSupplierID = "", foundSupplierName = "";

            while ((line = reader.readLine()) != null) {
                // Check if line starts with SupplierID and SupplierName, then extract their values
                if (line.startsWith("SupplierID:")) {
                    foundSupplierID = line.substring(16).trim();  // Extract SupplierID
                } else if (line.startsWith("SupplierName:")) {
                    foundSupplierName = line.substring(16).trim();  // Extract SupplierName
                }

                // If the SupplierName matches, return the corresponding SupplierID
                if (line.startsWith("~~~~~") && Objects.equals(foundSupplierName, supplierName)) {
                    return foundSupplierID;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";  // Return a default value if no matching SupplierID was found
    }

}
