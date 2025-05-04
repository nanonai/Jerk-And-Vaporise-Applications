package SalesMgr;

import Admin.Welcome;
import Common.*;
import PurchaseMgr.PurchaseHome;

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
    private static JPanel content, top_bar;
    private static Buffer current_user;
    private static int indicator, base_size;
    private static List<Item> AllItems;
    private static JList ItemList;
    private static CustomComponents.CustomButton btnAdd,btnDelete,btnEdit;


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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 6;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(0,0,18,0);
        gbc.fill = GridBagConstraints.BOTH;
        AllItems = Item.listAllItem("datafile/item.txt");
        List<String> AllItemConvt = Item.ItemConvt(AllItems);
        ItemList = new JList<String>(AllItemConvt.toArray(new String[0]));
        ItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ItemList);
        content.add(scrollPane , gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,0,15,10);
        gbc.fill = GridBagConstraints.BOTH;

        btnAdd = new CustomComponents.CustomButton("Add", boldonse, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.GRAY, Color.BLACK, 0, 30, Main.transparent, false,
                5, false, null, 0, 0,0);
        content.add(btnAdd, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,30,20,10);
        gbc.fill = GridBagConstraints.BOTH;
        btnEdit = new CustomComponents.CustomButton("Edit", boldonse, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.GRAY, Color.BLACK, 0, 30, Main.transparent, false,
                5, false, null, 0, 0,0);
        btnEdit.addActionListener(_ -> {
            ItemMng.indicator = 0;

        });
        content.add( btnEdit, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,30,20,10);
        gbc.fill = GridBagConstraints.BOTH;
        btnDelete = new CustomComponents.CustomButton("Delete", boldonse, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.GRAY, Color.BLACK, 0, 30, Main.transparent, false,
                5, false, null, 0, 0,0);
        content.add( btnDelete, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.gridwidth = 1;
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1,gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.gridwidth = 1;
        JLabel placeholder2 = new JLabel("");
        content.add(placeholder2,gbc);


    }

    public static void UpdateComponentSize(int base_size) {

    }
}
