// Test ur stuff here :3
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//import com.formdev.flatlaf.FlatIntelliJLaf;
//
//public class Main {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            BufferedImage bg_login, icon, logo_login, user_icon, lock_icon, name_icon,
//                    email_icon, phone_icon, show_icon, hidden_icon;
//            Font merriweather, boldonse;
//            int interface_indicator = 0;
//            try {
//                UIManager.setLookAndFeel(new FlatIntelliJLaf());
//                bg_login = ImageIO.read(new File("images/login_bg.jpg"));
//                icon = ImageIO.read(new File("images/icon.png"));
//                logo_login = ImageIO.read(new File("images/logo_original.png"));
//                user_icon = ImageIO.read(new File("images/user.png"));
//                lock_icon = ImageIO.read(new File("images/lock.png"));
//                name_icon = ImageIO.read(new File("images/name.png"));
//                email_icon = ImageIO.read(new File("images/email.png"));
//                phone_icon = ImageIO.read(new File("images/phone.png"));
//                show_icon = ImageIO.read(new File("images/show.png"));
//                hidden_icon = ImageIO.read(new File("images/hidden.png"));
//                merriweather = Font.createFont(Font.TRUETYPE_FONT,
//                                new File("fonts_and_resources/static/Merriweather_24pt-Regular.ttf"))
//                        .deriveFont(Font.PLAIN, 14);
//                boldonse = Font.createFont(Font.TRUETYPE_FONT,
//                                new File("fonts_and_resources/Boldonse-Regular.ttf"))
//                        .deriveFont(Font.PLAIN, 14);
//                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                ge.registerFont(merriweather);
//                ge.registerFont(boldonse);
//            } catch (IOException e) {
//                e.getStackTrace();
//                return;
//            } catch (FontFormatException | UnsupportedLookAndFeelException e) {
//                throw new RuntimeException(e);
//            }
//
//            JFrame main_frame = new JFrame();
//            main_frame.setMinimumSize(new Dimension(1330, 780));
//            main_frame.setTitle("OWSB Automated Purchase Order Management System");
//            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            main_frame.setLocationRelativeTo(null);
//            main_frame.setResizable(true);
//            main_frame.setIconImage(icon);
//
//            CustomComponents.ImagePanel background = new CustomComponents.ImagePanel(bg_login);
//            main_frame.setContentPane(background);
//
//            JPanel outer_grid = background.getGridPanel();
//            GridBagConstraints gbc_outer = new GridBagConstraints();
//            outer_grid.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 20));
//
//            gbc_outer.gridx = 0;
//            gbc_outer.gridy = 0;
//            gbc_outer.weightx = 1;
//            gbc_outer.weighty = 1;
//            gbc_outer.fill = GridBagConstraints.BOTH;
//            JLabel place_holder1 = new JLabel("", SwingConstants.CENTER);
//            outer_grid.add(place_holder1, gbc_outer);
//
//            gbc_outer.gridy = 1;
//            CustomComponents.ImageCell logo = new CustomComponents.ImageCell(logo_login, 0.85 , 7);
//            outer_grid.add(logo, gbc_outer);
//
//            gbc_outer.gridy = 2;
//            gbc_outer.weighty = 0.001;
//            JLabel logo_text1 = new JLabel("<html><div style='color:#383546; padding-top: 14px;'>"
//                    + "<b>Omega Wholesale Sdn Bhd (OWSB)</b></div></html>");
//            logo_text1.setFont(merriweather);
//            outer_grid.add(logo_text1, gbc_outer);
//
//            gbc_outer.gridy = 3;
//            gbc_outer.weighty = 0.7;
//            JLabel logo_text2 = new JLabel("<html><div style='color:#383546;'>"
//                    + "Automated Purchase Order<br>Management System</div></html>");
//            logo_text2.setFont(boldonse);
//            outer_grid.add(logo_text2, gbc_outer);
//
//            gbc_outer.gridy = 4;
//            gbc_outer.weighty = 1;
//            JLabel place_holder2 = new JLabel("", SwingConstants.CENTER);
//            outer_grid.add(place_holder2, gbc_outer);
//
//            gbc_outer.gridx = 1;
//            gbc_outer.gridy = 0;
//            gbc_outer.gridheight = 5;
//            gbc_outer.weightx = 5;
//            gbc_outer.fill = GridBagConstraints.BOTH;
//            CustomComponents.RoundedPanel right_panel = new CustomComponents.RoundedPanel(30,
//                    1, 0, Color.WHITE, Color.BLACK);
//            right_panel.setBackground(Color.WHITE);
//
//            right_panel.setLayout(new GridBagLayout());
//            GridBagConstraints gbc_right = new GridBagConstraints();
//
//            gbc_right.gridx = 0;
//            gbc_right.gridy = 0;
//            gbc_right.fill = GridBagConstraints.BOTH;
//            gbc_right.weightx = 1;
//            gbc_right.weighty = 1;
//            JPanel right_grid = new JPanel(new GridBagLayout());
//            right_grid.setOpaque(false);
//            right_grid.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));
//            right_panel.add(right_grid, gbc_right);
//
//            LoginInterfaceChanger changer = new LoginInterfaceChanger(interface_indicator, right_grid);
//            changer.ChangeLoginInterface(user_icon, lock_icon, name_icon, email_icon, phone_icon,
//                    show_icon, hidden_icon, merriweather, main_frame);
//
//            outer_grid.add(right_panel, gbc_outer);
//            background.add(outer_grid);
//
//            main_frame.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    background.updateSize(main_frame.getContentPane().getWidth(),
//                            main_frame.getContentPane().getHeight());
//                    logo.repaint();
//                    logo_text1.setFont(merriweather.deriveFont(
//                            (float) main_frame.getContentPane().getHeight() / 30));
//                    logo_text2.setFont(boldonse.deriveFont(
//                            (float) main_frame.getContentPane().getHeight() / 25));
//                    changer.UpdateInterface(main_frame.getContentPane().getHeight(), merriweather);
//                }
//            });
//
//            main_frame.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    Component clickedComponent = e.getComponent();
//                    SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
//                }
//            });
//            main_frame.setVisible(true);
//        });
//    }
//}
//
//class LoginInterfaceChanger {
//    int indicator, parent_height;
//    JPanel grid;
//    CustomComponents.RoundedPanel txt_grid, pick_grid;
//    JLabel title, label1, label2;
//    CustomComponents.CustomButton button1, button2, hidden;
//    CustomComponents.ImageCell txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5;
//    CustomComponents.EmptyTextField txt1, txt3, txt4, txt5;
//    CustomComponents.EmptyPasswordField txt2;
//    JCheckBox check1;
//    JComboBox<String> combo1;
//    final Color transparent = new Color(0, 0, 0, 0);
//    JFrame frame;
//
//    public LoginInterfaceChanger(int indicator, JPanel grid) {
//        this.indicator = indicator;
//        this.grid = grid;
//    }
//
//    public void ChangeIndicator(int indicator) {
//        this.indicator = indicator;
//    }
//
//    public void ChangeLoginInterface(BufferedImage user_icon, BufferedImage lock_icon,
//                                     BufferedImage name_icon, BufferedImage email_icon,
//                                     BufferedImage phone_icon, BufferedImage show_icon,
//                                     BufferedImage hidden_icon, Font font, JFrame frame) {
//        grid.removeAll();
//        if (indicator == 0) {
//            GridBagConstraints gbc_inner = new GridBagConstraints();
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 10;
//            gbc_inner.weighty = 0.3;
//            gbc_inner.fill = GridBagConstraints.BOTH;
//            title = new JLabel("<html><div style='color:#383546;'>"
//                    + "<b>Sign In</b></div></html>");
//            grid.add(title, gbc_inner);
//
//            gbc_inner.gridy = 1;
//            gbc_inner.weighty = 0.125;
//            txt_grid = new CustomComponents.RoundedPanel(60, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 4;
//            txt_icon1 = new CustomComponents.ImageCell(user_icon, 0.5, 5);
//            txt_grid.add(txt_icon1, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            txt1 = new CustomComponents.EmptyTextField(10, "Username or email \r\r", new Color(178, 181, 180));
//            txt_grid.add(txt1, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 2;
//            gbc_inner.weighty = 0.125;
//            gbc_inner.insets = new Insets(10, 0, 0, 0);
//            txt_grid = new CustomComponents.RoundedPanel(60, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 5.8;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            txt_icon2 = new CustomComponents.ImageCell(lock_icon, 0.5, 5);
//            txt_grid.add(txt_icon2, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.weightx = 5.6;
//            txt2 = new CustomComponents.EmptyPasswordField(7, "Password \r\r", new Color(178, 181, 180));
//            txt2.setEchoChar((char) 0);
//            txt_grid.add(txt2, gbc_inner);
//
//            gbc_inner.gridx = 2;
//            gbc_inner.weightx = 4;
//            hidden = new CustomComponents.CustomButton("", font, transparent,
//                    transparent, transparent, transparent, transparent, 30, 0,
//                    transparent, false, 5, true, hidden_icon, 0.4);
//            hidden.addActionListener(_ -> {
//                if (hidden.ReturnImageState()) {
//                    hidden.UpdateCustomButton(30, 0, show_icon, 0.35);
//                } else {
//                    hidden.UpdateCustomButton(30, 0, hidden_icon, 0.4);
//                }
//                txt2.UpdateStatus(hidden.ReturnImageState());
//                txt2.repaint();
//            });
//            txt_grid.add(hidden, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 3;
//            gbc_inner.weightx = 4;
//            gbc_inner.weighty = 0.05;
//            gbc_inner.insets = new Insets(6, 12, 15, 0);
//            pick_grid = new CustomComponents.RoundedPanel(0, 0, 0,
//                    transparent, transparent);
//            pick_grid.setLayout(new GridBagLayout());
//            grid.add(pick_grid, gbc_inner);
//
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            check1 = new JCheckBox("<html><div style='color:#383546;'>"
//                    + "<pre><b> Remember me</b></pre></div></html>");
//            check1.setBorder(null);
//            check1.setFocusPainted(false);
//            check1.setOpaque(false);
//            check1.setVerticalAlignment(SwingConstants.TOP);
//            pick_grid.add(check1,gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.weightx = 8;
//            JLabel placeholder1 = new JLabel("");
//            pick_grid.add(placeholder1, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 4;
//            gbc_inner.weightx = 10;
//            gbc_inner.weighty = 0.125;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            button1 = new CustomComponents.CustomButton("Sign In", font, Color.BLACK, Color.WHITE,
//                    new Color(244, 156, 187), new Color(56, 53, 70), new Color(161, 111, 136),
//                    30, 14, Color.WHITE, true, 5, false, null,
//                    0);
//            button1.addActionListener(e -> {
//                if (txt1.getText().isEmpty() || txt1.getText().equals("Username or email \r\r") ||
//                        new String(txt2.getPassword()).isEmpty() ||
//                        new String(txt2.getPassword()).equals("Password \r\r")) {
//                    CustomComponents.CustomDialog error_empty = new CustomComponents.CustomDialog(frame,
//                            font, 0);
//                    error_empty.show_dialog("Error", "Details must not be empty!",
//                            "Ok", null, null, null);
//                } else if (txt1.getText().contains(" ") || new String(txt2.getPassword()).contains(" ")) {
//
//                }
//            });
//            grid.add(button1, gbc_inner);
//
//            gbc_inner.gridy = 5;
//            gbc_inner.weighty = 0.05;
//            gbc_inner.insets = new Insets(10, 0, 30, 0);
//            txt_grid = new CustomComponents.RoundedPanel(0, 0, 0,
//                    transparent, transparent);
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 4;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            label1 = new JLabel("<html><div style='color: #383546;'>" +
//                    "<b>New here?</b></div></html>");
//            label1.setHorizontalAlignment(SwingConstants.RIGHT);
//            txt_grid.add(label1, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.weightx = 6;
//            button2 = new CustomComponents.CustomButton("  Create an Account", font.deriveFont(Font.BOLD),
//                    new Color(212, 87, 132), new Color(244, 156, 187), transparent,
//                    transparent, transparent, 0, 14, Color.WHITE,
//                    false, 4, false, null, 0);
//            button2.setHorizontalAlignment(SwingConstants.LEFT);
//            button2.addActionListener(_ -> {
//                ChangeIndicator(1);
//                ChangeLoginInterface(user_icon, lock_icon, name_icon, email_icon, phone_icon,
//                        show_icon, hidden_icon, font, frame);
//                UpdateInterface(parent_height, font);
//            });
//            txt_grid.add(button2, gbc_inner);
//            SwingUtilities.invokeLater(() -> title.requestFocusInWindow());
//
//        } else if (indicator == 1) {
//            GridBagConstraints gbc_inner = new GridBagConstraints();
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 10;
//            gbc_inner.weighty = 0.3;
//            gbc_inner.fill = GridBagConstraints.BOTH;
//            title = new JLabel("<html><div style='color:#383546;'>"
//                    + "<b>Sign Up</b></div></html>");
//            grid.add(title, gbc_inner);
//
//            gbc_inner.gridy = 1;
//            gbc_inner.weighty = 0.125;
//            txt_grid = new CustomComponents.RoundedPanel(40, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            txt_icon1 = new CustomComponents.ImageCell(user_icon, 0.5, 5);
//            txt_grid.add(txt_icon1, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            txt1 = new CustomComponents.EmptyTextField(10, "Username \r\r", new Color(178, 181, 180));
//            txt_grid.add(txt1, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 2;
//            gbc_inner.weighty = 0.125;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            txt_grid = new CustomComponents.RoundedPanel(40, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2.5;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            txt_icon2 = new CustomComponents.ImageCell(lock_icon, 0.5, 5);
//            txt_grid.add(txt_icon2, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.weightx = 7;
//            txt2 = new CustomComponents.EmptyPasswordField(7, "Password \r\r", new Color(178, 181, 180));
//            txt2.setEchoChar((char) 0);
//            txt_grid.add(txt2, gbc_inner);
//
//            gbc_inner.gridx = 2;
//            gbc_inner.weightx = 1;
//            hidden = new CustomComponents.CustomButton("", font, transparent,
//                    transparent, transparent, transparent, transparent, 30, 0,
//                    transparent, false, 5, true, hidden_icon, 0.5);
//            hidden.addActionListener(_ -> {
//                if (hidden.ReturnImageState()) {
//                    hidden.UpdateCustomButton(30, 0, show_icon, 0.5);
//                } else {
//                    hidden.UpdateCustomButton(30, 0, hidden_icon, 0.5);
//                }
//                txt2.UpdateStatus(hidden.ReturnImageState());
//                txt2.repaint();
//            });
//            txt_grid.add(hidden, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 3;
//            gbc_inner.weighty = 0.125;
//            txt_grid = new CustomComponents.RoundedPanel(40, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            txt_icon3 = new CustomComponents.ImageCell(name_icon, 0.5, 5);
//            txt_grid.add(txt_icon3, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            txt3 = new CustomComponents.EmptyTextField(10, "Full name \r\r", new Color(178, 181, 180));
//            txt_grid.add(txt3, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 4;
//            gbc_inner.weighty = 0.125;
//            txt_grid = new CustomComponents.RoundedPanel(40, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            txt_icon4 = new CustomComponents.ImageCell(email_icon, 0.5, 5);
//            txt_grid.add(txt_icon4, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            txt4 = new CustomComponents.EmptyTextField(10, "Email", new Color(178, 181, 180));
//            txt_grid.add(txt4, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 5;
//            gbc_inner.weighty = 0.125;
//            txt_grid = new CustomComponents.RoundedPanel(40, 0, 3,
//                    Color.WHITE, new Color(179, 181, 180));
//            txt_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            txt_icon5 = new CustomComponents.ImageCell(phone_icon, 0.5, 5);
//            txt_grid.add(txt_icon5, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            txt5 = new CustomComponents.EmptyTextField(10, "Phone", new Color(178, 181, 180));
//            txt_grid.add(txt5, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 6;
//            gbc_inner.weighty = 0.125;
//            pick_grid = new CustomComponents.RoundedPanel(0, 0, 3,
//                    transparent, transparent);
//            pick_grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 5));
//            pick_grid.setLayout(new GridBagLayout());
//            grid.add(pick_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 2;
//            label2 = new JLabel("<html><div style='color: #383546;'>" +
//                    "<b>Job Title:</b></div></html>");
//            pick_grid.add(label2, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 6;
//            String[] options = { "Select one", "Sales Manager", "Purchase Manager",
//                    "Inventory Manager", "Finance Manager" };
//            combo1 = new JComboBox<>(options);
//            combo1.addActionListener(_ -> {
//                if (combo1.getItemCount() > 4) {
//                    combo1.removeItem("Select one");
//                }
//            });
//            pick_grid.add(combo1, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 7;
//            gbc_inner.weightx = 10;
//            gbc_inner.weighty = 0.25;
//            button1 = new CustomComponents.CustomButton("Sign Up", font, Color.BLACK, Color.WHITE,
//                    new Color(244, 156, 187), new Color(56, 53, 70), new Color(161, 111, 136),
//                    30, 14, Color.WHITE, true, 5, false, null,
//                    0);
//            button1.addActionListener(_ -> {
//                if (txt1.getText().isEmpty() || new String(txt2.getPassword()).isEmpty()) {
//
//                }
//            });
//            grid.add(button1, gbc_inner);
//
//            gbc_inner.gridy = 8;
//            gbc_inner.weighty = 0.05;
//            gbc_inner.insets = new Insets(10, 0, 10, 0);
//            txt_grid = new CustomComponents.RoundedPanel(0, 0, 0,
//                    transparent, transparent);
//            txt_grid.setLayout(new GridBagLayout());
//            grid.add(txt_grid, gbc_inner);
//
//            gbc_inner.gridx = 0;
//            gbc_inner.gridy = 0;
//            gbc_inner.weightx = 5;
//            gbc_inner.insets = new Insets(0, 0, 0, 0);
//            label1 = new JLabel("<html><div style='color: #383546;'>" +
//                    "<b>Already have an account?</b></div></html>");
//            label1.setHorizontalAlignment(SwingConstants.RIGHT);
//            txt_grid.add(label1, gbc_inner);
//
//            gbc_inner.gridx = 1;
//            gbc_inner.weightx = 5;
//            button2 = new CustomComponents.CustomButton("  Sign In", font.deriveFont(Font.BOLD),
//                    new Color(212, 87, 132), new Color(244, 156, 187), transparent,
//                    transparent, transparent, 0, 14, Color.WHITE, false, 4,
//                    false, null, 0);
//            button2.setHorizontalAlignment(SwingConstants.LEFT);
//            button2.addActionListener(_ -> {
//                ChangeIndicator(0);
//                ChangeLoginInterface(user_icon, lock_icon, name_icon, email_icon, phone_icon,
//                        show_icon, hidden_icon, font, frame);
//                UpdateInterface(parent_height, font);
//            });
//            txt_grid.add(button2, gbc_inner);
//
//            SwingUtilities.invokeLater(() -> title.requestFocusInWindow());
//        }
//        grid.revalidate();
//        grid.repaint();
//    }
//
//    public void UpdateInterface(int parent_height, Font font) {
//        if (indicator == 0) {
//            this.parent_height = parent_height;
//            title.setFont(font.deriveFont((float) parent_height / 14));
//            txt_grid.ChangeRadius(parent_height / 16);
//            txt1.setFont(font.deriveFont((float) parent_height / 30));
//            txt2.setFont(font.deriveFont((float) parent_height / 30));
//            check1.setFont(font.deriveFont((float) parent_height / 40));
//            check1.setIcon(new CustomComponents.CustomCheckBoxIcon(30, 3,2,false,
//                    new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
//            check1.setSelectedIcon(new CustomComponents.CustomCheckBoxIcon(30, 3,1,true,
//                    new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
//            button1.UpdateCustomButton(parent_height / 12, parent_height / 30, null, 0);
//            label1.setFont(font.deriveFont((float) parent_height / 40));
//            button2.UpdateCustomButton(0, parent_height / 37, null, 0);
//            txt_icon1.repaint();
//            txt_icon2.repaint();
//        } else if (indicator == 1) {
//            this.parent_height = parent_height;
//            title.setFont(font.deriveFont((float) parent_height / 14));
//            txt_grid.ChangeRadius(parent_height / 12);
//            txt1.setFont(font.deriveFont((float) parent_height / 30));
//            txt2.setFont(font.deriveFont((float) parent_height / 30));
//            txt3.setFont(font.deriveFont((float) parent_height / 30));
//            txt4.setFont(font.deriveFont((float) parent_height / 30));
//            txt5.setFont(font.deriveFont((float) parent_height / 30));
//            combo1.setFont(font.deriveFont((float) parent_height / 40));
//            button1.UpdateCustomButton(parent_height / 12, parent_height / 30, null, 0);
//            label1.setFont(font.deriveFont((float) parent_height / 40));
//            label2.setFont(font.deriveFont((float) parent_height / 40));
//            button2.UpdateCustomButton(0, parent_height / 37, null, 0);
//            txt_icon1.repaint();
//            txt_icon2.repaint();
//            txt_icon3.repaint();
//            txt_icon4.repaint();
//            txt_icon5.repaint();
//        }
//    }
//}
