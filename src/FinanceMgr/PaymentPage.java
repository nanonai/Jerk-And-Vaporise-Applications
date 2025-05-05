package FinanceMgr;

import Common.Buffer;

import javax.swing.*;
import java.awt.*;

public class PaymentPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;

    public static void Loader(JFrame parent,Font merriweather,Font boldonse,
                              JPanel content,Buffer current_user){
        PaymentPage.parent = parent;
        PaymentPage.merriweather = merriweather;
        PaymentPage.boldonse = boldonse;
        PaymentPage.content = content;
        PaymentPage.current_user = current_user;
    }
    public  static void ShowPage(){

    }
}
