package Admin;

import Common.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class UserMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Buffer current_user;
    private static CustomComponents.CustomList<String> list;
    private static CustomComponents.CustomScrollPane scrollPane;
    private static CustomComponents.CustomSearchIcon search_icon, search_icon_h;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Buffer current_user) {
        UserMng.parent = parent;
        UserMng.merriweather = merriweather;
        UserMng.boldonse = boldonse;
        UserMng.content = content;
        UserMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.weighty = 10;
        gbc.insets = new Insets(20, 20, 20, 20);
        String[] columnNames = {"ID", "Name", "Age"};
        Object[][] data = {
                {3, "Alice", 22}, {1, "Bob", 25}, {2, "Charlie", 20}, {5, "Diana", 30},
                {4, "Ethan", 28}, {6, "Fiona", 24}, {7, "George", 27}, {8, "Hannah", 21},
                {9, "Ian", 26}, {10, "Jasmine", 23}, {11, "Kyle", 29}, {12, "Lara", 22},
                {13, "Mike", 24}, {14, "Nina", 20}, {15, "Oscar", 31}, {16, "Paula", 19},
                {17, "Quincy", 33}, {18, "Rita", 32}, {19, "Steve", 23}, {20, "Tina", 25},
                {21, "Uma", 26}, {22, "Victor", 27}, {23, "Wendy", 24}, {24, "Xavier", 30},
                {25, "Yara", 28}, {26, "Zane", 22}, {27, "Amber", 21}, {28, "Blake", 23},
                {29, "Cindy", 25}, {30, "Derek", 24}, {31, "Elle", 26}, {32, "Frank", 27},
                {33, "Grace", 29}, {34, "Hugo", 20}, {35, "Isla", 23}, {36, "Jake", 22},
                {37, "Kara", 24}, {38, "Leo", 30}, {39, "Mila", 25}, {40, "Noah", 21}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setFillsViewportHeight(true);

        CustomComponents.CustomTable tb = new CustomComponents.CustomTable(columnNames, data,
                boldonse, merriweather, Color.BLACK, Color.YELLOW, Color.WHITE, Color.BLACK, 4, 30);
        CustomComponents.CustomScrollPane sp = new CustomComponents.CustomScrollPane(false, 4, tb,
                30, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE, Color.CYAN,
                Main.transparent, Main.transparent, Main.transparent, Main.transparent, 0);
        content.add(sp, gbc);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}