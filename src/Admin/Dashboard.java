package Admin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static BufferForUser current_user;
    private static BufferedImage bg, img;
    private static JLabel haha, haha1, haha2;
    private static CustomComponents.ImagePanel pnl;
    private static CustomComponents.RoundedPanel pnl2;
    private static CustomComponents.ImageCell cll;
    private static CustomComponents.EmptyPasswordField txt1;
    private static JCheckBox check;
    private static CustomComponents.CustomButton btn;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, BufferForUser current_user) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            img = ImageIO.read(new File("images/info.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Dashboard.parent = parent;
        Dashboard.merriweather = merriweather;
        Dashboard.boldonse = boldonse;
        Dashboard.content = content;
        Dashboard.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;


    }

    public static void UpdateComponentSize(int base_size) {
    }
}
