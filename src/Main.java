import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BufferedImage bg_login, icon, logo_login, user_icon, lock_icon;
            Font merriweather, boldonse;
            int interface_indicator = 0;
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                bg_login = ImageIO.read(new File("images/login_bg.jpg"));
                icon = ImageIO.read(new File("images/icon.png"));
                logo_login = ImageIO.read(new File("images/logo_original.png"));
                user_icon = ImageIO.read(new File("images/user.png"));
                lock_icon = ImageIO.read(new File("images/lock.png"));
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

            ImagePanel background = new ImagePanel(bg_login);
            main_frame.setContentPane(background);

            JPanel outer_grid = background.getGridPanel();
            GridBagConstraints gbc_outer = new GridBagConstraints();
            outer_grid.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 20));

            gbc_outer.gridx = 0;
            gbc_outer.gridy = 0;
            gbc_outer.weightx = 1;
            gbc_outer.weighty = 1;
            gbc_outer.fill = GridBagConstraints.BOTH;
            JLabel place_holder1 = new JLabel("", SwingConstants.CENTER);
            outer_grid.add(place_holder1, gbc_outer);

            gbc_outer.gridy = 1;
            ImageCell logo = new ImageCell(logo_login, 0.85 , 7);
            outer_grid.add(logo, gbc_outer);

            gbc_outer.gridy = 2;
            gbc_outer.weighty = 0.001;
            JLabel logo_text1 = new JLabel("<html><div style='color:#383546; padding-top: 14px;'>"
                    + "<b>Omega Wholesale Sdn Bhd (OWSB)</b></div></html>");
            logo_text1.setFont(merriweather);
            outer_grid.add(logo_text1, gbc_outer);

            gbc_outer.gridy = 3;
            gbc_outer.weighty = 0.7;
            JLabel logo_text2 = new JLabel("<html><div style='color:#383546;'>"
                    + "Automated Purchase Order<br>Management System</div></html>");
            logo_text2.setFont(boldonse);
            outer_grid.add(logo_text2, gbc_outer);

            gbc_outer.gridy = 4;
            gbc_outer.weighty = 1;
            JLabel place_holder2 = new JLabel("", SwingConstants.CENTER);
            outer_grid.add(place_holder2, gbc_outer);

            gbc_outer.gridx = 1;
            gbc_outer.gridy = 0;
            gbc_outer.gridheight = 5;
            gbc_outer.weightx = 5;
            gbc_outer.fill = GridBagConstraints.BOTH;
            RoundedPanel right_panel = new RoundedPanel(30, 1, 0,
                    Color.WHITE, Color.BLACK);
            right_panel.setBackground(Color.WHITE);

            right_panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc_right = new GridBagConstraints();

            gbc_right.gridx = 0;
            gbc_right.gridy = 0;
            gbc_right.fill = GridBagConstraints.BOTH;
            gbc_right.weightx = 1;
            gbc_right.weighty = 1;
            JPanel right_grid = new JPanel(new GridBagLayout());
            right_grid.setOpaque(false);
            right_grid.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));
            right_panel.add(right_grid, gbc_right);

            LoginInterfaceChanger changer = new LoginInterfaceChanger(interface_indicator, right_grid);
            changer.ChangeLoginInterface(user_icon, lock_icon, merriweather);

            outer_grid.add(right_panel, gbc_outer);
            background.add(outer_grid);

            main_frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    background.updateSize(main_frame.getContentPane().getWidth(),
                            main_frame.getContentPane().getHeight());
                    logo.repaint();
                    logo_text1.setFont(merriweather.deriveFont(
                            (float) main_frame.getContentPane().getHeight() / 30));
                    logo_text2.setFont(boldonse.deriveFont(
                            (float) main_frame.getContentPane().getHeight() / 25));
                    changer.UpdateInterface(main_frame.getContentPane().getWidth(),
                            main_frame.getContentPane().getHeight(), merriweather);
                }
            });

            main_frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Component clickedComponent = e.getComponent();
                    SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
                }
            });
            main_frame.setVisible(true);
        });
    }
}

class ImagePanel extends JPanel {
    private final Image image;
    private double ratio;
    private final JPanel grid;

    public ImagePanel(Image img) {
        this.image = img;
        if (image != null) {
            int img_width = image.getWidth(this);
            int img_height = image.getHeight(this);
            ratio = (double) img_width / img_height;
        }
        setLayout(null);
        grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        add(grid);
    }

    public JPanel getGridPanel() {
        return grid;
    }

    public void updateSize(int parent_width, int parent_height) {
        if (image == null) return;
        int new_width = parent_width;
        int new_height = (int) (parent_width / ratio);

        if (new_height > parent_height) {
            new_height = parent_height;
            new_width = (int) (parent_height * ratio);
        }

        int x = (parent_width - new_width) / 2;
        int y = (parent_height - new_height) / 2;
        grid.setBounds(x, y, new_width, new_height);
        grid.revalidate();
        grid.repaint();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panel_width = getWidth();
            int panel_height = getHeight();

            int new_width = panel_width;
            int new_height = (int) (panel_width / ratio);

            if (new_height > panel_height) {
                new_height = panel_height;
                new_width = (int) (panel_height * ratio);
            }

            int x = (panel_width - new_width) / 2;
            int y = (panel_height - new_height) / 2;

            g.drawImage(image, x, y, new_width, new_height, this);
        }
    }
}

class ImageCell extends JPanel {
    private final BufferedImage image;
    private final int alignment_factor;
    private final double size_factor;
    private double ratio;

    public ImageCell(BufferedImage image, double size_factor, int alignment_factor) {
        this.image = image;
        if (image != null) {
            int img_width = image.getWidth(this);
            int img_height = image.getHeight(this);
            ratio = (double) img_width / img_height;
        }
        this.size_factor = size_factor;
        this.alignment_factor =alignment_factor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panel_width = getWidth();
            int panel_height = getHeight();
            int max_width = (int) (panel_width * size_factor);
            int max_height = (int) (panel_height * size_factor);

            int new_width = max_width;
            int new_height = (int) (max_width / ratio);

            if (new_height > max_height) {
                new_height = max_height;
                new_width = (int) (max_height * ratio);
            }

            int x = 0;
            int y = 0;

            switch (alignment_factor) {
                case 1:
                    break;
                case 2:
                    x = (panel_width - new_width) / 2;
                    break;
                case 3:
                    x = panel_width - new_width;
                    break;
                case 4:
                    y = (panel_height - new_height) / 2;
                    break;
                case 5:
                    x = (panel_width - new_width) / 2;
                    y = (panel_height - new_height) / 2;
                    break;
                case 6:
                    x = panel_width - new_width;
                    y = (panel_height - new_height) / 2;
                    break;
                case 7:
                    y = panel_height - new_height;
                    break;
                case 8:
                    x = (panel_width - new_width) / 2;
                    y = panel_height - new_height;
                    break;
                case 9:
                    x = panel_width - new_width;
                    y = panel_height - new_height;
                    break;
            }

            g.drawImage(image, x, y, new_width, new_height, this);
        }
    }
}

class RoundedPanel extends JPanel {
    private final int shadow_factor, border_factor;
    private int corner_radius;
    private final Color fill_color, border_color;

    public RoundedPanel(int radius, int shadow_factor, int border_factor, Color fill_color, Color border_color) {
        this.corner_radius = radius;
        this.shadow_factor = shadow_factor;
        this.border_factor = border_factor;
        this.fill_color = fill_color;
        this.border_color = border_color;
        setOpaque(false);
    }

    public void ChangeRadius(int radius) {
        this.corner_radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (shadow_factor != 0) {
            int shadowSize = getHeight() / 80;
            int shadowOffset = 0;
            int maxAlpha = 233;

            for (int i = shadowSize; i > 0; i--) {
                int alpha = (int) ((double) maxAlpha * (i / (double) shadowSize));
                g2.setColor(new Color(0, 0, 0, alpha));
                g2.fillRoundRect(shadowOffset + i, shadowOffset + i,
                        getWidth() - (shadowOffset + i * 2),
                        getHeight() - (shadowOffset + i * 2),
                        corner_radius, corner_radius);
            }
        }

        g2.setColor(fill_color);
        g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, corner_radius, corner_radius);
        if (border_factor != 0) {
            g2.setColor(border_color);
            g2.setStroke(new BasicStroke(border_factor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawRoundRect(border_factor, border_factor, getWidth() - 5 - 2 * border_factor,
                    getHeight() - 5 - 2 * border_factor,
                    corner_radius - border_factor, corner_radius - border_factor);
        }
        g2.dispose();
    }
}

class EmptyTextField extends JTextField {
    private final String placeholder;
    private final Color placeholderColor;

    public EmptyTextField(int columns, String text, Color color) {
        super(columns);
        this.placeholder = text;
        this.placeholderColor = color;
        setBorder(null);
        setOpaque(false);
        setForeground(Color.BLACK);
        setBackground(new Color(255, 255, 255));
        setCaretColor(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 26));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                }
            }
        });

        setText(placeholder);
        setForeground(placeholderColor);
    }
}

class CustomCheckBoxIcon implements Icon {
    private final int size, radius, border_width;
    private final boolean isSelected;
    private final Color stroke_color, fill_color, tick_color;

    public CustomCheckBoxIcon(int size, int radius, int border_width, boolean isSelected,
                              Color stroke_color, Color fill_color, Color tick_color) {
        this.size = size;
        this.radius = radius;
        this.border_width = border_width;
        this.isSelected = isSelected;
        this.stroke_color = stroke_color;
        this.fill_color = fill_color;
        this.tick_color = tick_color;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(stroke_color);
        g2d.fillRoundRect(x, y, size, size, radius, radius);

        g2d.setColor(fill_color);
        g2d.fillRoundRect(x + radius - border_width, y + radius - border_width,
                size - 2 * border_width, size - 2 * border_width,
                radius - border_width, radius - border_width);

        if (isSelected) {
            g2d.setColor(tick_color);

            int[] xPoints = {x + size * 2 / 9, x + size * 2 / 5, x + size * 3 / 4};
            int[] yPoints = {y + size / 2, y + size * 2 / 3, y + size / 4};
            g2d.setStroke(new BasicStroke(border_width * 4));
            g2d.drawPolyline(xPoints, yPoints, 3);
        }
    }
}

class RoundedButton extends JButton {
    private final Color background;
    private final Color normal, hover, t_normal, t_hover;
    private final Font font;
    private final double c_radius;
    private int radius, t_size;
    private double r;
    private Timer timer;
    private boolean hovering;

    public RoundedButton(String text, Font font, Color t_normal, Color t_hover,
                         Color normal, Color hover, int radius, int t_size,
                         Color background) {
        super(text);
        this.font = font;
        this.normal = normal;
        this.hover = hover;
        this.t_normal = t_normal;
        this.t_hover = t_hover;
        this.radius = radius;
        this.t_size = t_size;
        this.background = background;
        this.c_radius = radius / 1.26;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                r = 0;
                animation(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                r = getWidth();
                animation(false);
            }
        });
    }

    public void UpdateRoundedButton(int radius, int t_size) {
        this.radius = radius;
        this.t_size = t_size;
        repaint();
    }

    private void animation(boolean expand) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(2, e -> {
            if (expand) {
                r += (double) getWidth() / 12;
                if (r >= getWidth() * 1.5) {
                    r = getWidth() * 1.5;
                    timer.stop();
                }
            } else {
                r -= (double) getWidth() / 12;
                if (r <= 0) {
                    r = 0;
                    timer.stop();
                }
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(background);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(normal);
        g2.fillRoundRect(0, 0, width, height, radius, radius);
        if (r > 0) {
            g2.setColor(hover);
            int x = (int) ((double) width / 2 - r / 2);
            int y = (int) (- r / 2);
            g2.fillOval(x, y, (int) r, (int) r);

            GeneralPath path = new GeneralPath();
            path.moveTo((float) -radius / 3, -1);
            path.lineTo(c_radius, 0);
            path.curveTo((float) c_radius * 5 / 6, (float) c_radius * 0.0139866666666667,
                    (float) c_radius * 4 / 6, (float) c_radius * 0.057191,
                    (float) c_radius * 3 / 6, (float) c_radius * 0.1339746666666667);
            path.curveTo((float) c_radius * 2 / 6, (float) c_radius * 0.254644,
                    (float) c_radius * 0.254644, (float) c_radius * 2 / 6,
                    (float) c_radius * 0.1339746666666667, (float) c_radius * 3 / 6);
            path.curveTo((float) c_radius * 0.057191, (float) c_radius * 4 / 6,
                    (float) c_radius * 0.0139866666666667, (float) c_radius * 5 / 6,
                    0, (float) c_radius);
            path.closePath();
            g2.setColor(background);
            g2.fill(path);

            path = new GeneralPath();
            path.moveTo((float) (width + radius / 3), -1);
            path.lineTo(width - c_radius, 0);
            path.curveTo((float) width - c_radius * 5 / 6, (float) c_radius * 0.0139866666666667,
                    (float) width - c_radius * 4 / 6, (float) c_radius * 0.057191,
                    (float) width - c_radius * 3 / 6, (float) c_radius * 0.1339746666666667);
            path.curveTo((float) width - c_radius * 2 / 6, (float) c_radius * 0.254644,
                    (float) width - c_radius * 0.254644, (float) c_radius * 2 / 6,
                    (float) width - c_radius * 0.1339746666666667, (float) c_radius * 3 / 6);
            path.curveTo((float) width - c_radius * 0.057191, (float) c_radius * 4 / 6,
                    (float) width - c_radius * 0.0139866666666667, (float) c_radius * 5 / 6,
                    width, (float) c_radius);
            path.closePath();
            g2.setColor(background);
            g2.fill(path);

            path = new GeneralPath();
            path.moveTo((float) -radius / 3, height);
            path.lineTo(c_radius, height);
            path.curveTo((float) c_radius * 5 / 6, (float) height - c_radius * 0.0139866666666667,
                    (float) c_radius * 4 / 6, (float) height -  c_radius * 0.057191,
                    (float) c_radius * 3 / 6, (float) height -  c_radius * 0.1339746666666667);
            path.curveTo((float) c_radius * 2 / 6, (float) height - c_radius * 0.254644,
                    (float) c_radius * 0.254644, (float) height -  c_radius * 2 / 6,
                    (float) c_radius * 0.1339746666666667, (float) height - c_radius * 3 / 6);
            path.curveTo((float)c_radius * 0.057191, (float) height - c_radius * 4 / 6,
                    (float)  c_radius * 0.0139866666666667, (float) height - c_radius * 5 / 6,
                    0, (float) height -  c_radius);
            path.closePath();
            g2.setColor(background);
            g2.fill(path);

            path = new GeneralPath();
            path.moveTo((float) (width + radius / 3), height);
            path.lineTo(width - c_radius, height);
            path.curveTo((float) width - c_radius * 5 / 6, (float) height - c_radius * 0.0139866666666667,
                    (float) width - c_radius * 4 / 6, (float) height -  c_radius * 0.057191,
                    (float) width - c_radius * 3 / 6, (float) height -  c_radius * 0.1339746666666667);
            path.curveTo((float) width - c_radius * 2 / 6, (float) height - c_radius * 0.254644,
                    (float) width - c_radius * 0.254644, (float) height -  c_radius * 2 / 6,
                    (float) width - c_radius * 0.1339746666666667, (float) height - c_radius * 3 / 6);
            path.curveTo((float) width - c_radius * 0.057191, (float) height - c_radius * 4 / 6,
                    (float) width -  c_radius * 0.0139866666666667, (float) height - c_radius * 5 / 6,
                    width, (float) height -  c_radius);
            path.closePath();
            g2.setColor(background);
            g2.fill(path);
        }

        g2.setFont(font.deriveFont((float) t_size));
        g2.setColor(hovering ? t_hover : t_normal);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent() - fm.getDescent();
        g2.drawString(getText(), (width - textWidth) / 2, (height + textHeight) / 2);

        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }
}

class LoginInterfaceChanger {
    int indicator;
    JPanel grid, pick_grid1;
    RoundedPanel txt_grid1, txt_grid2, txt_grid3, txt_grid4, txt_grid5;
    JLabel title;
    RoundedButton button;
    ImageCell txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5;
    EmptyTextField txt1, txt2, txt3, txt4, txt5;
    JCheckBox check1;

    public LoginInterfaceChanger(int indicator, JPanel grid) {
        this.indicator = indicator;
        this.grid = grid;
    }

    public void ChangeLoginInterface(BufferedImage user_icon, BufferedImage lock_icon, Font font) {
        grid.removeAll();
        if (indicator == 0) {
            GridBagConstraints gbc_inner = new GridBagConstraints();
            gbc_inner.gridy = 0;
            gbc_inner.weightx = 1;
            gbc_inner.weighty = 0.3;
            gbc_inner.fill = GridBagConstraints.BOTH;
            title = new JLabel("<html><div style='color:#383546;'>"
                    + "<b>Sign In</b></div></html>");
            grid.add(title, gbc_inner);

            gbc_inner.gridy = 1;
            gbc_inner.weighty = 0.2;
            txt_grid1 = new RoundedPanel(60, 0, 3,
                    Color.WHITE, new Color(179, 181, 180));
            txt_grid1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            txt_grid1.setLayout(new GridBagLayout());
            grid.add(txt_grid1, gbc_inner);

            gbc_inner.gridx = 0;
            gbc_inner.gridy = 0;
            gbc_inner.weightx = 4;
            txt_icon1 = new ImageCell(user_icon, 0.5, 5);
            txt_grid1.add(txt_icon1, gbc_inner);

            gbc_inner.gridx = 1;
            gbc_inner.gridy = 0;
            gbc_inner.weightx = 6;
            txt1 = new EmptyTextField(10, "Username or email", new Color(178, 181, 180));
            txt_grid1.add(txt1, gbc_inner);

            gbc_inner.gridx = 0;
            gbc_inner.gridy = 2;
            gbc_inner.weighty = 0.2;
            txt_grid2 = new RoundedPanel(60, 0, 3,
                    Color.WHITE, new Color(179, 181, 180));
            txt_grid2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            txt_grid2.setLayout(new GridBagLayout());
            grid.add(txt_grid2, gbc_inner);

            gbc_inner.gridx = 0;
            gbc_inner.gridy = 0;
            gbc_inner.weightx = 4;
            txt_icon2 = new ImageCell(lock_icon, 0.5, 5);
            txt_grid2.add(txt_icon2, gbc_inner);

            gbc_inner.gridx = 1;
            gbc_inner.gridy = 0;
            gbc_inner.weightx = 6;
            txt2 = new EmptyTextField(10, "Password", new Color(178, 181, 180));
            txt_grid2.add(txt2, gbc_inner);

            gbc_inner.gridx = 0;
            gbc_inner.gridy = 3;
            gbc_inner.insets = new Insets(0, 12, 0, 0);
            check1 = new JCheckBox("<html><div style='color:#383546;'>"
                    + "<pre><b> Remember me</b></pre></div></html>");
            check1.setBorder(null);
            check1.setFocusPainted(false);
            check1.setOpaque(false);
            check1.setVerticalAlignment(SwingConstants.TOP);
            grid.add(check1,gbc_inner);

            gbc_inner.gridy = 4;
            gbc_inner.insets = new Insets(0, 0, 0, 0);
            button = new RoundedButton("Sign In", font, Color.BLACK, Color.WHITE,
                    new Color(244, 156, 187), new Color(56, 53, 70),
                    30, 14, Color.WHITE);
            grid.add(button, gbc_inner);

            SwingUtilities.invokeLater(() -> title.requestFocusInWindow());
        }
    }

    public void UpdateInterface(int parent_width, int parent_height, Font font) {
        title.setFont(font.deriveFont((float) parent_height / 14));
        txt_grid1.ChangeRadius(parent_height / 15);
        txt_grid2.ChangeRadius(parent_height / 15);
        txt1.setFont(font.deriveFont(
                (float) parent_height / 30));
        txt2.setFont(font.deriveFont(
                (float) parent_height / 30));
        check1.setFont(font.deriveFont(
                (float) parent_height / 40));
        check1.setIcon(new CustomCheckBoxIcon(30, 3,2,false,
                new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
        check1.setSelectedIcon(new CustomCheckBoxIcon(30, 3,1,true,
                new Color(145, 145, 145), Color.WHITE, new Color(97, 97, 97)));
        button.UpdateRoundedButton(parent_height / 15, parent_height / 30);
        txt_icon1.repaint();
        txt_icon2.repaint();
    }
}
