package FinanceMgr;

import Common.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    public static BufferedImage bg;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,Buffer current_user){
        try {
            bg = ImageIO.read(new File("images/login_bg.jbg"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        PurchaseOrderPage.parent = parent;
        PurchaseOrderPage.merriweather = merriweather;
        PurchaseOrderPage.boldonse = boldonse;
        PurchaseOrderPage.content = content;
        PurchaseOrderPage.current_user = current_user;

    }
    public  static void ShowPage(){
        List<PurchaseOrder> poList = PurchaseOrder.listAllPurchaseOrders("datafile/purchaseOrder.txt");
        JPanel tablePanel = TableUIHelper.createPurchaseOrderTablePanel(poList);
        content.add(tablePanel);

    }

}