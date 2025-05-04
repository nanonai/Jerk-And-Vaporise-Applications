package FinanceMgr;

import Common.Buffer;

import javax.swing.*;
import java.awt.*;

public class InventoryPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,Buffer current_user){
        InventoryPage.parent = parent;
        InventoryPage.merriweather = merriweather;
        InventoryPage.boldonse = boldonse;
        InventoryPage.content = content;
        InventoryPage.current_user = current_user;

    }
    public  static void ShowPage(){

    }
}
