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
    private static JPanel content, inner;
    private static Buffer current_user;
    private static CustomComponents.CustomButton all, fin, pur, inv, sls;
    private static JLabel lbl_show, lbl_entries;
    private static JComboBox<String> entries;

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
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 0, 0);
        all = new CustomComponents.CustomButton("All user", merriweather,
                Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE,
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        content.add(all, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        fin = new CustomComponents.CustomButton("Finance", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        content.add(fin, gbc);

        gbc.gridx = 2;
        pur = new CustomComponents.CustomButton("Purchase", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        content.add(pur, gbc);

        gbc.gridx = 3;
        inv = new CustomComponents.CustomButton("Inventory", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        content.add(inv, gbc);

        gbc.gridx = 4;
        sls = new CustomComponents.CustomButton("Sales", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        content.add(sls, gbc);

        gbc.gridx = 5;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(0, 20, 20, 20);
        inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}