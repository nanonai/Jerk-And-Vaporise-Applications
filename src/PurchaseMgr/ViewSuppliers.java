package PurchaseMgr;

import Admin.Welcome;
import Common.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViewSuppliers {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    public static List<String> list = new ArrayList<>();

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        ViewSuppliers.parent = parent;
        ViewSuppliers.merriweather = merriweather;
        ViewSuppliers.boldonse = boldonse;
        ViewSuppliers.content = content;
        ViewSuppliers.current_user = current_user;
        try (BufferedReader reader = new BufferedReader(new FileReader("datafile/supplier.txt"))) {
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
                        list.add(SupplierID);
                        list.add(SupplierName);
                        list.add(ContactPerson);
                        list.add(Phone);
                        list.add(Email);
                        list.add(Address);
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        String data = "";
        for (String string : list) {
            System.out.println(string);
            data += string;
        }
        JLabel ferret = new JLabel(data);
        content.add(ferret, gbc);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}
