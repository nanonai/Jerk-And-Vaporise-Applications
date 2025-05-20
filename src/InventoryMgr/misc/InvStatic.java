package InventoryMgr.misc;

import Admin.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InvStatic {

    public static Font merriweather, boldonse;
    public static JFrame parent;
    public static JPanel content;
    public static User current_user;

    public static void Initialize(JFrame parent, JPanel content, User current_user) {
        InvStatic.parent = parent;
        InvStatic.content = content;
        InvStatic.current_user = current_user;
    }

    static {
        try {
            merriweather = Font.createFont(Font.TRUETYPE_FONT, new File("fonts_and_resources/static/Merriweather_24pt-Regular.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (IOException | FontFormatException e) {
            System.out.print("Merriweather Font Fallback");
            merriweather = new Font("Serif", Font.PLAIN, 14);
        }

        try {
            boldonse = Font.createFont(Font.TRUETYPE_FONT,
                            new File("fonts_and_resources/Boldonse-Regular.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (IOException | FontFormatException e) {
            System.out.print("Boldonse Font Fallback");
            merriweather = new Font("Serif", Font.PLAIN, 14);
        }

//        System.out.print("Finished loading Fonts for InventoryManager under InvStatic");
    }
}
