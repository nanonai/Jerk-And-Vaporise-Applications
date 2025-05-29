package Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main {
    public static int indicator = 0;
    public static final Color transparent = new Color(0, 0, 0, 0);
    public static final String userdata_file = "datafile/user.txt";
    public static final String purchase_req_file = "datafile/purchase_req.txt";
    public static final String purchase_order_file = "datafile/purchase_order.txt";
    public static final String item_file = "datafile/item.txt";
    public static final String item_supplier_file = "datafile/item_supplier.txt";
    public static final String payment_file = "datafile/payment.txt";
    public static final String supplier_file = "datafile/supplier.txt";
    public static final String sales_file = "datafile/sales.txt";
    public static final String item_sales_file = "datafile/item_sales.txt";
    public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String email_regex =
            "^(?!\\.)(?!.*\\.\\.)([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)"
                    + "@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$";
    public static final String phone_regex = "^01[0-9]{8}$";
    public static final String upper_regex = ".*[A-Z].*";
    public static final String lower_regex = ".*[a-z].*";
    public static final String digit_regex = ".*\\d.*";
    public static final String special_regex = ".*[!@#$%^&*()\\-+].*";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BufferedImage icon;
            Font merriweather, boldonse;
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                icon = ImageIO.read(new File("images/icon.png"));
                merriweather = Font.createFont(Font.TRUETYPE_FONT,
                                new File("fonts_and_resources/static/Merriweather_24pt-Regular.ttf"))
                        .deriveFont(Font.PLAIN, 14);
                boldonse = Font.createFont(Font.TRUETYPE_FONT,
                                new File("fonts_and_resources/Boldonse-Regular.ttf"))
                        .deriveFont(Font.PLAIN, 14);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(merriweather);
                ge.registerFont(boldonse);
            } catch (IOException e) {
                e.getStackTrace();
                return;
            } catch (FontFormatException | UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            JFrame main_frame = new JFrame();
            main_frame.setMinimumSize(new Dimension(1330, 780));
            main_frame.setTitle("OWSB Automated Purchase Order Management System");
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main_frame.setLocationRelativeTo(null);
            main_frame.setResizable(true);
            main_frame.setIconImage(icon);

            SignIn.Loader(main_frame, merriweather, boldonse);
            Home.Loader(main_frame, merriweather, boldonse);
            PageChanger(main_frame);

            main_frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (indicator == 0) {
                        SignIn.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight());
                        SwingUtilities.invokeLater(() -> SignIn.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight()));
                    } else if (indicator == 1) {
                        Home.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight());
                        SwingUtilities.invokeLater(() -> Home.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight()));
                    }
                }
            });

            main_frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Component clickedComponent = e.getComponent();
                    SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
                }
            });
        });
    }

    public static void PageChanger(JFrame parent) {
        parent.getContentPane().removeAll();
        parent.revalidate();
        parent.repaint();
        switch (indicator) {
            case 0:
                SignIn.ShowPage();
                if (User.GetRememberedUser(userdata_file) != null) {
                    SignIn.LoginRemembered();
                    break;
                } else {
                    SignIn.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                    SwingUtilities.invokeLater(() -> {
                        SignIn.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                        parent.getContentPane().revalidate();
                        parent.getContentPane().repaint();
                    });
                }
                parent.setVisible(true);
                break;
            case 1:
                Home.ShowPage();
                Home.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                SwingUtilities.invokeLater(() -> {
                    Home.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                    parent.getContentPane().revalidate();
                    parent.getContentPane().repaint();
                });
                parent.setVisible(true);
                break;
        }
    }
}
