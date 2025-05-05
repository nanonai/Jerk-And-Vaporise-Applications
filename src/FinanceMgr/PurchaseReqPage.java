package FinanceMgr;

import Common.Buffer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PurchaseReqPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,Buffer current_user){
        PurchaseReqPage.parent = parent;
        PurchaseReqPage.merriweather = merriweather;
        PurchaseReqPage.boldonse = boldonse;
        PurchaseReqPage.content = content;
        PurchaseReqPage.current_user = current_user;

    }
    public  static void ShowPage(){
        List<PurchaseRequisition> prList = PurchaseRequisition.listAllPurchaseRequisitions("datafile/purchaseReq.txt");
        JPanel tablePanel = TableUIHelper.createPurchaseReqTablePanel(prList);
        content.add(tablePanel);
    }
}
