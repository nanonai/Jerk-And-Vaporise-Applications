package PurchaseMgr;

import Admin.User;
import InventoryMgr.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Import the Objects class
import java.util.regex.Pattern;

public class Supplier {
    public String SupplierID;
    public String SupplierName;
    public String ContactPerson;
    public String Phone;
    public String Email;
    public String Address;
    private static final String EMAIL_REGEX =
            "^(?!\\.)(?!.*\\.\\.)([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)"
                    + "@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$";
    private static final String PHONE_REGEX = "^01[0-9]{8}$";

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
            newId = String.format("%s%s", "SUP", number);
            for (Supplier supplier : allSupplier) {
                if (Objects.equals(supplier.SupplierID,newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static boolean emailChecker(String Email, String filename) {
        List<Supplier> allSupplier = listAllSupplier(filename);
        boolean repeated = false;
        for ( Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.Email, Email)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static boolean phoneChecker(String Phone, String filename) {
        List<Supplier> allSupplier = listAllSupplier(filename);
        boolean repeated = false;
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.Phone, Phone)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static String validitychecker(String supplierName, String contactPerson,
                                         String phone, String email, String address, String filename) {

        String indicator = "";

        // 1. Supplier Name (min 3 characters)
        if (supplierName.length() >= 8 && supplierName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        // 2. Contact Person (non-empty)
        if (contactPerson.length() >= 8 && contactPerson.length() <= 30) {
            indicator += "1";
        } else {
            indicator += "0";
        }

        if (Pattern.compile(PHONE_REGEX).matcher(phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(PHONE_REGEX).matcher(phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }

        if (emailChecker(email, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        // 5. Address (at least 5 characters)
        if (address != null && address.trim().length() >= 5) {
            indicator += "1";
        } else {
            indicator += "0";
        }

        // 6. Name uniqueness (assuming no duplicate supplier name)
//        if (nameChecker(supplierName, filename)) {
//            indicator += "1";
//        } else {
//            indicator += "0";
//        }
//
        return indicator;
    }
}
