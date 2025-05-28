package PurchaseMgr;

import Admin.User;
import InventoryMgr.Item;

import java.io.*;
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
                        listSupplier.add(new Supplier(SupplierID, SupplierName, ContactPerson, Phone, Email, Address));
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
            newId = String.format("%s%s", "S", number);
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

        if (supplierName.length() >= 8 && supplierName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("SupplierName validity: " + indicator);

        if (contactPerson.length() >= 8 && contactPerson.length() <= 30) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("contactPerson validity: " + indicator);

        if (Pattern.compile(PHONE_REGEX).matcher(phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("Phone format validity: " + indicator);

        if (phoneChecker(phone, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        System.out.println("Phone exist validity: " + indicator);

        if (Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("Email format validity: " + indicator);

        if (emailChecker(email, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        System.out.println("Email exist validity: " + indicator);

        if (address.length() >= 8 && address.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        System.out.println("address validity: " + indicator);
        return indicator;
    }

    public static void saveNewSupplier(Supplier supplier, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("SupplierID:     " + supplier.SupplierID + "\n");
            writer.write("SupplierName:   " + supplier.SupplierName + "\n");
            writer.write("ContactPerson:  " + supplier.ContactPerson + "\n");
            writer.write("Phone:          " + supplier.Phone + "\n");
            writer.write("Email:          " + supplier.Email + "\n");
            writer.write("Address:        " + supplier.Address + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.printStackTrace(); // Make sure to print the stack trace for debugging
        }
    }
    public static void modifySupplier(Supplier incoming, String filename) {
        List<Supplier> allSuppliers = listAllSupplier(filename);
        for (Supplier supplier : allSuppliers) {
            if (Objects.equals(supplier.SupplierID, incoming.SupplierID)) {
                supplier.SupplierID = incoming.SupplierID;
                supplier.SupplierName = incoming.SupplierName;
                supplier.ContactPerson = incoming.ContactPerson;
                supplier.Phone = incoming.Phone;
                supplier.Email = incoming.Email;
                supplier.Address = incoming.Address;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Supplier supplier : allSuppliers) {
                writer.write("SupplierID:     " + supplier.SupplierID + "\n");
                writer.write("SupplierName:   " + supplier.SupplierName + "\n");
                writer.write("ContactPerson:  " + supplier.ContactPerson + "\n");
                writer.write("Phone:          " + supplier.Phone + "\n");
                writer.write("Email:          " + supplier.Email + "\n");
                writer.write("Address:        " + supplier.Address + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeSupplier(String supplierID, String filename) {
        List<Supplier> allSuppliers = Supplier.listAllSupplier(filename); // Load all suppliers
        allSuppliers.removeIf(supplier -> Objects.equals(supplier.SupplierID, supplierID)); // Remove the matching one

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Supplier supplier : allSuppliers) {
                writer.write("SupplierID:     " + supplier.SupplierID + "\n");
                writer.write("SupplierName:   " + supplier.SupplierName + "\n");
                writer.write("ContactPerson:  " + supplier.ContactPerson + "\n");
                writer.write("Phone:          " + supplier.Phone + "\n");
                writer.write("Email:          " + supplier.Email + "\n");
                writer.write("Address:        " + supplier.Address + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
