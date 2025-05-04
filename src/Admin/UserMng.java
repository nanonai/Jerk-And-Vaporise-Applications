package Admin;

import Common.*;
import javax.swing.*;
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
        gbc.weightx = 1;
        gbc.weighty = 1;
        search_icon = new CustomComponents.CustomSearchIcon(20, 4, Color.BLACK, Color.cyan);
        JLabel tl = new JLabel(search_icon);
        content.add(tl, gbc);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}