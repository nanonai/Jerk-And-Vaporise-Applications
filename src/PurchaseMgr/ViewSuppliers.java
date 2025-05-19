package PurchaseMgr;

import Admin.User;
import Admin.CustomComponents;
import Admin.Main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewSuppliers {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content, inner;
    public static User current_user;
    private static JButton s_btn;
    //    private static CustomComponents.CustomButton all, purOrder, purReq, itemID, quan,supplier,orderDate,purMan,status;
    private static CustomComponents.RoundedPanel search_panel;
    private static JLabel lbl_show, lbl_entries;
    private static JComboBox<String> entries;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomTable table_supplier;
    private static CustomComponents.CustomScrollPane scrollPane1;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user){
        ViewSuppliers.parent = parent;
        ViewSuppliers.merriweather = merriweather;
        ViewSuppliers.boldonse = boldonse;
        ViewSuppliers.content = content;
        ViewSuppliers.current_user = current_user;

    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel("");
        // placeholder1.setBorder(BorderFactory.createLineBorder(Color.RED));
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 20, 20, 20);
        inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        // main white panel I change it to blue first
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(10, 10, 10, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        inner.add(entries, igbc);

        igbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, igbc);

        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        placeholder2.setBackground(Color.WHITE); // Background color
        placeholder2.setOpaque(true);
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 1;
        search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(81, 81, 81), Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        search_panel.add(s_btn, ii_gbc);

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(6, 0, 10, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search_panel.add(search, ii_gbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 16;
        igbc.insets = new Insets(0, 0, 10, 0);
        List<Supplier> list = Supplier.listAllSupplier("datafile/supplier.txt");
        String[] titles = new String[]{"SupplierID", "SupplierName", "ContactPerson", "Phone", "Email", "Address"};
        Object[][] data = new Object[list.size()][titles.length];
        int Counter = 0;
        for (Supplier listItem: list){
            data[Counter] = new Object[]{listItem.SupplierID, listItem.SupplierName, listItem.ContactPerson, listItem.Phone, listItem.Email, listItem.Address };
            Counter += 1;
        }

        table_supplier = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_supplier.setShowHorizontalLines(true);
        table_supplier.setShowVerticalLines(true);
        table_supplier.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_supplier,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);

    }

}