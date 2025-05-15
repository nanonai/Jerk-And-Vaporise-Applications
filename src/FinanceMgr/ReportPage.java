package FinanceMgr;

import Admin.BufferForUser;

import javax.swing.*;
import java.awt.*;

public class ReportPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static BufferForUser current_user;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, BufferForUser current_user){
        ReportPage.parent = parent;
        ReportPage.merriweather = merriweather;
        ReportPage.boldonse = boldonse;
        ReportPage.content = content;
        ReportPage.current_user = current_user;
    }
    public  static void ShowPage(){

    }
}
