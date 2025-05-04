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
        gbc.weightx = 2;
        gbc.weighty = 1;

        String[] dataset = {"apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi", "apple", "banana", "skibidi"};
        List<String> data2 = new ArrayList<String>();
        data2.add("apple");
        data2.add("banana");
        data2.add("cherry");
        gbc.gridx = 6;
        list = new CustomComponents.CustomList<>(dataset, 0, 14, boldonse,
                Color.black, Color.WHITE, Color.WHITE, Color.BLACK);
        scrollPane = new CustomComponents.CustomScrollPane(false, 10, list, 24, Color.WHITE,
                new Color(231, 231, 231), new Color(77, 77, 77),
                new Color(231, 231, 231), new Color(77, 77, 77),
                new Color(255, 222, 237), new Color(147, 169, 255),
                new Color(255, 184, 211), new Color(225, 108, 150),
                new Color(87, 120, 255), new Color(0, 48, 255), 12);
        content.add(scrollPane, gbc);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}