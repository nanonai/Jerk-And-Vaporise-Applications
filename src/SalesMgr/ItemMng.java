package SalesMgr;

import Admin.Welcome;
import Common.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ItemMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    public static int indicator, base_size;
    public static List<Item> AllItems;
    public static JList ItemList;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        SalesMgr.ItemMng.parent = parent;
        SalesMgr.ItemMng.merriweather = merriweather;
        SalesMgr.ItemMng.boldonse = boldonse;
        SalesMgr.ItemMng.content = content;
        SalesMgr.ItemMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        AllItems = Item.listAllItem("datafile/item.txt");
        List<String> AllItemConvt = Item.ItemConvt(AllItems);
        ItemList = new JList<String>(AllItemConvt.toArray(new String[0]));
        ItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ItemList);
        content.add(scrollPane , gbc);
    }

    public static void PageChanger() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        switch (indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> lol
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        UpdateComponentSize(base_size);
    }

    public static void UpdateComponentSize(int base_size) {

    }
}
