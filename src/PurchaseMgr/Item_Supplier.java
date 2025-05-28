package PurchaseMgr;

import Admin.Main;
import InventoryMgr.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item_Supplier {
    public String ItemID, SupplierID;

    public Item_Supplier(String ItemID, String SupplierID) {
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
    }

    public static List<Item_Supplier> listAllItemSupplier(String filename) {
        List<Item_Supplier> allItemSupplier = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ItemID = "", SupplierID = "";

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        SupplierID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allItemSupplier.add(new Item_Supplier(ItemID, SupplierID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItemSupplier;
    }

    public static List<Item_Supplier> listAllItemSupplierFromItemID(String id, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, id)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static List<Item_Supplier> listAllItemSupplierFromSupplierID(String id, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.SupplierID, id)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static List<Item_Supplier> listAllItemSupplierFromBothID(String ItemID, String SupplierID, String filename) {
        List<Item_Supplier> allItemSupplier = listAllItemSupplier(filename);
        List<Item_Supplier> filteredList = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, ItemID) && Objects.equals(itemSupplier.SupplierID, SupplierID)) {
                filteredList.add(itemSupplier);
            }
        }
        return filteredList;
    }

    public static Boolean RedundancyChecker(String ItemID, String SupplierID, String filename) {
        List<Item_Supplier> sameItem = listAllItemSupplierFromBothID(ItemID, SupplierID, filename);
        return sameItem.isEmpty();
    }

    public static void saveNewItemSupplier(Item_Supplier itemSupplier, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("ItemID:         " + itemSupplier.ItemID + "\n");
            writer.write("SupplierID:     " + itemSupplier.SupplierID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static String getSupplierIDFromItemID(String itemID, String itemSupplierFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(itemSupplierFilename))) {
            String line;
            String foundItemID = "", foundSupplierID = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ItemID:")) {
                    foundItemID = line.substring(16).trim();
                } else if (line.startsWith("SupplierID:")) {
                    foundSupplierID = line.substring(16).trim();
                }

                if (line.startsWith("~~~~~") && Objects.equals(foundItemID, itemID)) {
                    return foundSupplierID;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static String getSupplierName(String supplierID, String supplierFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(supplierFilename))) {
            String line;
            String foundSupplierID = "", foundSupplierName = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("SupplierID:")) {
                    foundSupplierID = line.substring(16).trim();
                } else if (line.startsWith("SupplierName:")) {
                    foundSupplierName = line.substring(16).trim();
                }

                if (line.startsWith("~~~~~") && Objects.equals(foundSupplierID, supplierID)) {
                    return foundSupplierName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Supplier";
    }

    public static String getSupplierNameByItemID(String ItemID, String itemSupplierFilename, String supplierFilename) {
        String supplierID = getSupplierIDFromItemID(ItemID, itemSupplierFilename);

        if (!"Unknown".equals(supplierID)) {
            return getSupplierName(supplierID, supplierFilename);
        }

        return "Supplier not found";
    }

    public static List<Supplier> getSuppliersByItemID(String ItemID, String filename) {
        List<Supplier> filterList = new ArrayList<>();
        List<Item_Supplier> itemSupplierList = listAllItemSupplier(filename);
        for (Item_Supplier itemSupplier : itemSupplierList) {
            if (Objects.equals(itemSupplier.ItemID, ItemID)) {
                filterList.add(Supplier.getSupplierByID(itemSupplier.SupplierID, Main.supplier_file));
            }
        }
        return filterList;
    }

    public static List<Item_Supplier> GetItemSupplierByItemIds(List<String> ids, String filename) {
        List<Item_Supplier> itemSupplierList = new ArrayList<>();
        for (String id : ids) {
            // Retrieve the Item_Supplier list based on ItemID
            List<Item_Supplier> itemSuppliers = Item_Supplier.listAllItemSupplierFromItemID(id, filename);
            itemSupplierList.addAll(itemSuppliers); // Add all matching Item_Supplier objects to the list
        }
        return itemSupplierList;
    }
    public static void removeItemSupplier(String ItemID, String SupplierID, String filename) {
        List<Item_Supplier> allItemSuppliers = Item_Supplier.listAllItemSupplier(filename); // Fetch all item-supplier pairs
        // Remove the item-supplier pair with the matching ItemID and SupplierID
        allItemSuppliers.removeIf(itemSupplier ->
                Objects.equals(itemSupplier.ItemID, ItemID) && Objects.equals(itemSupplier.SupplierID, SupplierID)
        );

        // Write the updated list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item_Supplier itemSupplier : allItemSuppliers) {
                writer.write("ItemID:         " + itemSupplier.ItemID + "\n");
                writer.write("SupplierID:     " + itemSupplier.SupplierID + "\n");
                writer.write("~~~~~\n");  // Write the delimiter after each entry
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       public static boolean updateSupplierForItem(String ItemID, String newSupplierID, String filename) {
        List<Item_Supplier> allItemSuppliers = listAllItemSupplier(filename);
        boolean found = false;

        for (Item_Supplier itemSupplier : allItemSuppliers) {
            if (Objects.equals(itemSupplier.ItemID, ItemID)) {
                itemSupplier.SupplierID = newSupplierID;
                found = true;
                break;
            }
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Item_Supplier itemSupplier : allItemSuppliers) {
                    writer.write("ItemID:         " + itemSupplier.ItemID + "\n");
                    writer.write("SupplierID:     " + itemSupplier.SupplierID + "\n");
                    writer.write("~~~~~\n");
                }
                return true;  // Return true if updated and saved successfully
            } catch (IOException e) {
                e.printStackTrace();
                return false;  // Return false on error writing file
            }
        }
        return false;  // ItemID not found
    }
    public static List<Item> getItemsBySupplierID(String supplierID, String itemSupplierPath, String itemPath) {
        List<Item> result = new ArrayList<>();
        List<Item_Supplier> mappings = listAllItemSupplierFromSupplierID(supplierID, itemSupplierPath);

        for (Item_Supplier mapping : mappings) {
            Item item = Item.getItemByID(mapping.ItemID, itemPath);
            if (item != null) {
                result.add(item);
            }
        }

        return result;
    }
    public static void replaceSupplierID(String itemID, String oldSupplierID, String newSupplierID, String filename) {
        List<Item_Supplier> allLinks = listAllItemSupplier(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item_Supplier link : allLinks) {
                String supplierID = link.SupplierID;
                if (link.ItemID.equals(itemID) && link.SupplierID.equals(oldSupplierID)) {
                    supplierID = newSupplierID;
                }

                writer.write("ItemID:         " + link.ItemID + "\n");
                writer.write("SupplierID:     " + supplierID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
