package FinanceMgr;


import Admin.CustomComponents;
import Admin.Main;
import Admin.User;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;

public class ReportPage {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content,inner;
    public static User current_user;
    private static JLabel lbl_begin_date,lbl_to,lbl_po_approve,lbl_payment_made;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user){
        ReportPage.parent = parent;
        ReportPage.merriweather = merriweather;
        ReportPage.boldonse = boldonse;
        ReportPage.content = content;
        ReportPage.current_user = current_user;
    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridx = 9;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(0, 20, 20, 20);
        inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        lbl_begin_date = new JLabel("Report generated from :");
        lbl_begin_date.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_begin_date.setOpaque(false);
        lbl_begin_date.setForeground(new Color(122, 122, 122));
        inner.add(lbl_begin_date, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        lbl_po_approve = new JLabel("Purchase Order been Approved :");
        lbl_po_approve.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_po_approve.setOpaque(false);
        lbl_po_approve.setForeground(new Color(122, 122, 122));
        inner.add(lbl_po_approve, gbc);

        gbc.gridy = 2;
        JTextPane poDetail = new JTextPane();
        poDetail.setEditable(false);
        inner.add(poDetail,gbc);

    }
}
