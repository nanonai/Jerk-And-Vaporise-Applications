package Admin;

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

public class Welcome {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static Buffer current_user;
    private static BufferedImage bg, img;
    private static JLabel haha, haha1, haha2;
    private static CustomComponents.ImagePanel pnl;
    private static CustomComponents.RoundedPanel pnl2;
    private static CustomComponents.ImageCell cll;
    private static CustomComponents.EmptyPasswordField txt1;
    private static JCheckBox check;
    private static CustomComponents.CustomButton btn;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, Buffer current_user) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            img = ImageIO.read(new File("images/info.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Welcome.parent = parent;
        Welcome.merriweather = merriweather;
        Welcome.boldonse = boldonse;
        Welcome.content = content;
        Welcome.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        haha = new JLabel("HaHa really funny stuff LOLLLLLLLLLL");
        haha.setOpaque(true);
        haha.setBackground(Color.RED);
        content.add(haha, gbc);

        gbc.gridy = 1;
        haha1 = new JLabel("HaHa really funny stuff LOLLLLLLLLLL");
        haha1.setOpaque(true);
        haha1.setBackground(Color.RED);
        content.add(haha1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.3;
        haha2 = new JLabel(new CustomComponents.CustomProfileIcon(40, false, "Administrator", merriweather));
        haha2.setOpaque(true);
        haha2.setBackground(Color.GREEN);
        content.add(haha2, gbc);

        gbc.gridx = 0;
        cll = new CustomComponents.ImageCell(img, 0.4, 5);
        content.add(cll, gbc);

        gbc.gridx = 1;
        pnl2 = new CustomComponents.RoundedPanel(30, 1, 3, Color.MAGENTA, Color.BLUE);
        content.add(pnl2, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        pnl = new CustomComponents.ImagePanel(bg);
        content.add(pnl, gbc);

        gbc.gridy = 3;
        btn = new CustomComponents.CustomButton("", boldonse, Main.transparent, Main.transparent,
                Main.transparent, Main.transparent, Main.transparent, 60, 0, Color.MAGENTA, false,
                5, true, img, 1, 60, 60);
        btn.addActionListener(_ -> {
            btn.UpdateCustomButton(0, 0, img, 1);
            List<String> options = java.util.List.of("Check Profile", "Sign Out");
            List<ActionListener> actions = List.of(
                    e -> {
                        System.out.println("hahaha");
                    },
                    e -> {
                        System.out.println("hehehe");
                    }
            );
            CustomComponents.CustomPopupMenu profile_drop_menu = new CustomComponents.CustomPopupMenu(
                    btn,
                    options,
                    actions,
                    merriweather,
                    1
            );
        });
        content.add(btn, gbc);
    }

    public static void UpdateComponentSize(int base_size) {
        haha.setFont(merriweather.deriveFont(Font.ITALIC, base_size));
        haha1.setFont(merriweather.deriveFont(Font.ITALIC, base_size));
        haha2.setFont(boldonse.deriveFont(Font.BOLD, base_size));
    }
}
