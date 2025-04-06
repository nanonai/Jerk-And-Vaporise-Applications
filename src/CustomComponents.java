import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomComponents {
    public static class ImagePanel extends JPanel {
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

        public double getRatio() {
            return ratio;
        }

        public void updateSize(int parent_width, int parent_height) {
            if (image == null) return;
            int new_width = parent_width;
            int new_height = (int) (parent_width / ratio);

            if (new_height > parent_height) {
                new_height = parent_height;
                new_width = (int) (parent_height * ratio);
            }

            int x = (getWidth() - new_width) / 2;
            int y = (getHeight() - new_height) / 2;

            grid.setBounds(x, y, new_width, new_height);
            grid.revalidate();
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

    public static class ImageCell extends JPanel {
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

    public static class RoundedPanel extends JPanel {
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

    public static class EmptyTextField extends JTextField {
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

    public static class EmptyPasswordField extends JPasswordField {
        private final String placeholder;
        private final Color placeholderColor;
        private boolean hidden = true;

        public EmptyPasswordField(int columns, String text, Color color) {
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
                    if (new String(getPassword()).equals(placeholder)) {
                        setText("");
                        if (hidden) {
                            setEchoChar('*');
                        } else {
                            setEchoChar((char) 0);
                        }
                        setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (new String(getPassword()).isEmpty()) {
                        setText(placeholder);
                        setEchoChar((char) 0);
                        setForeground(placeholderColor);
                    }
                }
            });
            setText(placeholder);
            setForeground(placeholderColor);
        }

        public void UpdateStatus(boolean hidden) {
            this.hidden = hidden;
            if (hidden && !new String(getPassword()).equals(placeholder)) {
                setEchoChar('*');
            } else {
                setEchoChar((char) 0);
            }
        }

        @Override
        public void setText(String text) {
            super.setText(text);
        }
    }

    public static class CustomCheckBoxIcon implements Icon {
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

    public static class CustomButton extends JButton {
        private final Color background;
        private final Color normal, hover, follow, t_normal, t_hover;
        private final Font font;
        private double c_radius;
        private int radius, t_size;
        private double r;
        private Timer timer;
        private boolean hovering;
        private final boolean animated, image_factor;
        private final int align_factor;
        private double size_factor, ratio;
        private BufferedImage image;
        private boolean istate = true;

        public CustomButton(String text, Font font, Color t_normal, Color t_hover,
                            Color normal, Color hover, Color follow, int radius, int t_size,
                            Color background, boolean animated, int align_factor, boolean image_factor,
                            BufferedImage image, double size_factor) {
            super(text);
            this.font = font;
            this.normal = normal;
            this.hover = hover;
            this.follow = follow;
            this.t_normal = t_normal;
            this.t_hover = t_hover;
            this.radius = radius;
            this.t_size = t_size;
            this.background = background;
            this.c_radius = (double) radius / 2;
            this.animated = animated;
            this.align_factor = align_factor;
            this.image_factor = image_factor;
            this.image = image;
            this.size_factor = size_factor;
            if (image != null){
                int img_width = image.getWidth(null);
                int img_height = image.getHeight(null);
                ratio = (double) img_width / img_height;
            }
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovering = true;
                    if (animated) {
                        r = 0;
                        animation(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    if (animated) {
                        r = getWidth();
                        animation(false);
                    }
                }
            });
        }

        public boolean ReturnImageState() {
            return istate;
        }

        public void UpdateCustomButton(int radius, int t_size, BufferedImage image, double size_factor) {
            this.istate = !istate;
            this.radius = radius;
            this.c_radius = (double) radius / 2;
            this.t_size = t_size;
            this.image = image;
            this.size_factor = size_factor;
            if (image != null){
                int img_width = image.getWidth(null);
                int img_height = image.getHeight(null);
                ratio = (double) img_width / img_height;
            }
            repaint();
        }

        private void animation(boolean expand) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            timer = new Timer(2, _ -> {
                if (expand) {
                    r += (double) getWidth() / 10;
                    if (r >= getWidth() * 120) {
                        r = getWidth() * 120;
                        timer.stop();
                    }
                } else {
                    r -= (double) getWidth() / 10;
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

            if (!image_factor){
                g2.setColor(background);
                g2.fillRect(0, 0, getWidth(), getHeight());

                if (animated) {
                    g2.setColor(normal);
                    g2.fillRoundRect(0, 0, width, height, radius, radius);
                    if (r > 0) {
                        g2.setColor(follow);
                        int x = (int) ((double) width / 2 - r * 1.2 / 2);
                        int y = (int) (-r * 1.8 / 2);
                        g2.fillOval(x, y, (int) ((int) r * 1.2), (int) ((int) r * 1.2));

                        g2.setColor(hover);
                        x = (int) ((double) width / 2 - r / 2);
                        y = (int) (-r * 1.55 / 2);
                        g2.fillOval(x, y, (int) r, (int) r);

                        GeneralPath path = new GeneralPath();
                        path.moveTo((float) -radius / 3, -3);
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
                        path.moveTo((float) (width + radius / 3), -3);
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
                        path.moveTo((float) -radius / 3, height + 1);
                        path.lineTo(c_radius, height + 1);
                        path.curveTo((float) c_radius * 5 / 6, (float) height - c_radius * 0.0139866666666667,
                                (float) c_radius * 4 / 6, (float) height - c_radius * 0.057191,
                                (float) c_radius * 3 / 6, (float) height - c_radius * 0.1339746666666667);
                        path.curveTo((float) c_radius * 2 / 6, (float) height - c_radius * 0.254644,
                                (float) c_radius * 0.254644, (float) height - c_radius * 2 / 6,
                                (float) c_radius * 0.1339746666666667, (float) height - c_radius * 3 / 6);
                        path.curveTo((float) c_radius * 0.057191, (float) height - c_radius * 4 / 6,
                                (float) c_radius * 0.0139866666666667, (float) height - c_radius * 5 / 6,
                                0, (float) height - c_radius);
                        path.closePath();
                        g2.setColor(background);
                        g2.fill(path);

                        path = new GeneralPath();
                        path.moveTo((float) (width + radius / 3), height + 1);
                        path.lineTo(width - c_radius, height + 1);
                        path.curveTo((float) width - c_radius * 5 / 6, (float) height - c_radius * 0.0139866666666667,
                                (float) width - c_radius * 4 / 6, (float) height - c_radius * 0.057191,
                                (float) width - c_radius * 3 / 6, (float) height - c_radius * 0.1339746666666667);
                        path.curveTo((float) width - c_radius * 2 / 6, (float) height - c_radius * 0.254644,
                                (float) width - c_radius * 0.254644, (float) height - c_radius * 2 / 6,
                                (float) width - c_radius * 0.1339746666666667, (float) height - c_radius * 3 / 6);
                        path.curveTo((float) width - c_radius * 0.057191, (float) height - c_radius * 4 / 6,
                                (float) width - c_radius * 0.0139866666666667, (float) height - c_radius * 5 / 6,
                                width, (float) height - c_radius);
                        path.closePath();
                        g2.setColor(background);
                        g2.fill(path);
                    }
                } else {
                    g2.setColor(hovering ? hover : normal);
                    g2.fillRoundRect(0, 0, width, height, radius, radius);
                }

                g2.setFont(font.deriveFont((float) t_size));
                g2.setColor(hovering ? t_hover : t_normal);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent() - fm.getDescent();
                switch (align_factor) {
                    case 1:
                        g2.drawString(getText(), 0, 0);
                        break;
                    case 2:
                        g2.drawString(getText(), (width - textWidth) / 2, 0);
                        break;
                    case 3:
                        g2.drawString(getText(), width - textWidth, 0);
                        break;
                    case 4:
                        g2.drawString(getText(), 0, (height + textHeight) / 2);
                        break;
                    case 5:
                        g2.drawString(getText(), (width - textWidth) / 2, (height + textHeight) / 2);
                        break;
                    case 6:
                        g2.drawString(getText(), width - textWidth, (height + textHeight) / 2);
                        break;
                    case 7:
                        g2.drawString(getText(), 0, height + textHeight);
                        break;
                    case 8:
                        g2.drawString(getText(), (width - textWidth) / 2, height + textHeight);
                        break;
                    case 9:
                        g2.drawString(getText(), width - textWidth, height + textHeight);
                        break;
                }
            } else {
                g2.setColor(background);
                g2.fillRect(0, 0, getWidth(), getHeight());

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

                int x = (width - new_width) / 2;
                int y = (height - new_height) / 2;
                g.drawImage(image, x, y, new_width, new_height, this);
            }
            g2.dispose();
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }
    }

    public static class CustomDialog {
        private final JFrame frame;
        private final Font font;
        private BufferedImage icon, image;

        public CustomDialog(JFrame frame, Font font, int icon_factor) {
            this.frame = frame;
            this.font = font;
            if (icon_factor == 0) {
                Icon pre_icon = UIManager.getIcon("OptionPane.errorIcon");
                if (pre_icon != null) {
                    icon = new BufferedImage(
                            pre_icon.getIconWidth(),
                            pre_icon.getIconHeight(),
                            BufferedImage.TYPE_INT_ARGB
                    );
                    Graphics2D g2d = icon.createGraphics();
                    pre_icon.paintIcon(null, g2d, 0, 0);
                    g2d.dispose();
                }
                try {
                    image = ImageIO.read(new File("images/error.png"));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else if (icon_factor == 1) {
                Icon pre_icon = UIManager.getIcon("OptionPane.informationIcon");
                if (pre_icon != null) {
                    icon = new BufferedImage(
                            pre_icon.getIconWidth(),
                            pre_icon.getIconHeight(),
                            BufferedImage.TYPE_INT_ARGB
                    );
                    Graphics2D g2d = icon.createGraphics();
                    pre_icon.paintIcon(null, g2d, 0, 0);
                    g2d.dispose();
                }
                try {
                    image = ImageIO.read(new File("images/info.png"));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        
        public void show_dialog(String title, String content, String option1, String option2,
                                ActionEvent event1, ActionEvent event2) {
            JDialog dialog = new JDialog(frame, title, true);
            dialog.setSize((int) (frame.getWidth() / 2.5), (int) (frame.getHeight() / 3.5));
            dialog.setLayout(new GridBagLayout());
            dialog.setResizable(false);
            dialog.setIconImage(icon);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(20, 0, 0, 0);
            JPanel panel1 = new JPanel(new GridBagLayout());
            dialog.add(panel1, gbc);

            gbc.weightx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            ImageCell logo = new ImageCell(image, 0.5, 2);
            panel1.add(logo, gbc);

            gbc.gridx = 1;
            gbc.weightx = 2;
            JLabel message = new JLabel(content);
            message.setFont(font.deriveFont((float) frame.getHeight() / 40));
            message.setVerticalAlignment(SwingConstants.TOP);
            panel1.add(message, gbc);

            gbc.insets = new Insets(0, frame.getHeight() / 7, 20, frame.getHeight() / 7);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weighty = 0.3;
            JPanel panel2 = new JPanel(new GridBagLayout());
            if (option2 != null) {
                gbc.insets = new Insets(0, frame.getHeight() / 30, 20, frame.getHeight() / 30);
            }
            dialog.add(panel2, gbc);

            gbc.gridy = 0;
            JButton button2 = new JButton(option2);
            button2.setFont(font.deriveFont((float) frame.getHeight() / 40));
            if (option2 != null) {
                gbc.insets = new Insets(0, 0, 0, frame.getHeight() / 20);
                panel2.add(button2, gbc);
                gbc.gridx = 1;
            }
            gbc.insets = new Insets(0, 0, 0, 0);
            JButton button1 = new JButton(option1);
            button1.setFont(font.deriveFont((float) frame.getHeight() / 40));
            panel2.add(button1, gbc);

            if (event1 != null) {
                button1.addActionListener((ActionListener) event1);
            } else {
                button1.addActionListener(_ -> dialog.dispose());
            }

            if (event1 != null) {
                button2.addActionListener((ActionListener) event2);
            } else {
                button2.addActionListener(_ -> dialog.dispose());
            }

            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }
    }
}
