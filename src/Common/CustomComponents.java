package Common;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.List;

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
                Graphics2D g2d = (Graphics2D) g.create();
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
                drawOuterGlow(g2d, x, y, new_width, new_height, panel_width, panel_height);
            }
        }
    }

    public static class ColorPanel extends JPanel {
        private final Color color;
        private final double ratio;
        private final JPanel grid;

        public ColorPanel(Color color, double ratio) {
            this.color = color;
            this.ratio = ratio;
            setLayout(null);
            grid = new JPanel(new GridBagLayout());
            grid.setOpaque(false);
            add(grid);
        }

        public JPanel getGridPanel() {
            return grid;
        }

        public void updateSize(int parent_width, int parent_height) {
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
            Graphics2D g2d = (Graphics2D) g.create();
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

            g.setColor(color);
            g.fillRect(x, y, new_width, new_height);
            drawOuterGlow(g2d, x, y, new_width, new_height, panel_width, panel_height);
        }
    }

    private static void drawOuterGlow(Graphics2D g2d, int x, int y, int width, int height, int panelWidth, int panelHeight) {
        int glowSize = 8;
        Color transparent = new Color(0, 0, 0, 0);
        Color glowColor = new Color(0, 0, 0, 60);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (x > 0) {
            LinearGradientPaint leftPaint = new LinearGradientPaint(
                    new Point2D.Double(x - glowSize, 0),
                    new Point2D.Double(x, 0),
                    new float[]{0f, 1f},
                    new Color[]{transparent, glowColor}
            );
            g2d.setPaint(leftPaint);
            g2d.fillRect(Math.max(0, x - glowSize), y, glowSize, height);
        }
        if (x + width < panelWidth) {
            LinearGradientPaint rightPaint = new LinearGradientPaint(
                    new Point2D.Double(x + width, 0),
                    new Point2D.Double(x + width + glowSize, 0),
                    new float[]{0f, 1f},
                    new Color[]{glowColor, transparent}
            );
            g2d.setPaint(rightPaint);
            g2d.fillRect(x + width, y, glowSize, height);
        }
        if (y > 0) {
            int topGlowY = Math.max(0, y - glowSize);
            int topGlowHeight = y - topGlowY;
            if (topGlowHeight > 0) {
                LinearGradientPaint topPaint = new LinearGradientPaint(
                        new Point2D.Double(0, topGlowY),
                        new Point2D.Double(0, y),
                        new float[]{0f, 1f},
                        new Color[]{transparent, glowColor}
                );
                g2d.setPaint(topPaint);
                g2d.fillRect(x, topGlowY, width, topGlowHeight);
            }
        }
        if (y + height < panelHeight) {
            LinearGradientPaint bottomPaint = new LinearGradientPaint(
                    new Point2D.Double(0, y + height),
                    new Point2D.Double(0, y + height + glowSize),
                    new float[]{0f, 1f},
                    new Color[]{glowColor, transparent}
            );
            g2d.setPaint(bottomPaint);
            g2d.fillRect(x, y + height, width, glowSize);
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
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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
            } else {
                g2.setColor(fill_color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), corner_radius, corner_radius);
                if (border_factor != 0) {
                    g2.setColor(border_color);
                    g2.setStroke(new BasicStroke(border_factor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawRoundRect(border_factor, border_factor, getWidth() - 2 * border_factor,
                            getHeight()  - 2 * border_factor,
                            corner_radius - border_factor, corner_radius - border_factor);
                }
                g2.dispose();
            }
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
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

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

        public void UpdateColumns(int columns) {
            setColumns(columns);
        }

        public String GetPlaceHolder() { return placeholder; }

        public void Reset() {
            setText(placeholder);
            setToolTipText("");
            setForeground(placeholderColor);
            repaint();
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
        private Color normal, hover, follow, t_normal, t_hover;
        private final Font font;
        private int radius, t_size;
        private double r;
        private Timer timer;
        private boolean hovering;
        private final boolean animated, image_factor;
        private final int align_factor;
        private int size_if_need1, size_if_need2;
        private double size_factor, ratio;
        private BufferedImage image;
        private boolean istate = true;

        public CustomButton(String text, Font font, Color t_normal, Color t_hover,
                            Color normal, Color hover, Color follow, int radius, int t_size,
                            Color background, boolean animated, int align_factor, boolean image_factor,
                            BufferedImage image, double size_factor, int size_if_need1, int size_if_need2) {
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
            this.animated = animated;
            this.align_factor = align_factor;
            this.image_factor = image_factor;
            this.image = image;
            this.size_factor = size_factor;
            this.size_if_need1 = size_if_need1;
            this.size_if_need2 = size_if_need2;
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
            this.t_size = t_size;
            this.image = image;
            this.size_factor = size_factor;
            repaint();
        }

        public void UpdateColor(Color t_normal, Color t_hover, Color normal, Color hover, Color follow) {
            this.t_normal = t_normal;
            this.t_hover = t_hover;
            this.normal = normal;
            this.hover = hover;
            this.follow = follow;
            repaint();
        }

        public void UpdateSize(int size_if_need1, int size_if_need2) {
            this.size_if_need1 = size_if_need1;
            this.size_if_need2 = size_if_need2;
            repaint();
        }

        public void UpdateText(String string) {
            setText(string);
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
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            if (!image_factor){
                g2.setColor(background);
                g2.fillRect(0, 0, getWidth(), getHeight());

                if (animated) {
                    g2.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
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
                    }
                } else {
                    g2.setColor(hovering ? hover : normal);
                    g2.fillRoundRect(0, 0, width, height, radius, radius);
                }

                g2.setFont(font.deriveFont((float) t_size));
                g2.setColor(hovering ? t_hover : t_normal);
                FontMetrics fm = g2.getFontMetrics();

                String[] lines = getText().split("\\r?\\n");
                int lineHeight = fm.getHeight();
                int totalTextHeight = lineHeight * lines.length;

                int startY = switch (align_factor) {
                    case 1, 2, 3 -> 0;
                    case 7, 8, 9 -> height - totalTextHeight + fm.getAscent();
                    default -> (height - totalTextHeight) / 2 + fm.getAscent();
                };
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    int textWidth = fm.stringWidth(line);
                    int x = switch (align_factor) {
                        case 1, 4, 7 -> 0;
                        case 3, 6, 9 -> width - textWidth;
                        default -> (width - textWidth) / 2;
                    };
                    int y = startY + i * lineHeight;
                    g2.drawString(line, x, y);
                }
            } else {
                setSize(size_if_need1, size_if_need2);
                g2.setColor(background);
                g2.fillRect(0, 0, size_if_need1, size_if_need2);

                int max_width = (int) (size_if_need1 * size_factor);
                int max_height = (int) (size_if_need2 * size_factor);

                int new_width = max_width;
                int new_height = (int) (max_width / ratio);

                if (new_height > max_height) {
                    new_height = max_height;
                    new_width = (int) (max_height * ratio);
                }

                int x = 0, y= 0;

                switch (align_factor) {
                    case 1:
                        break;
                    case 2:
                        x = (size_if_need1 - new_width) / 2;
                        break;
                    case 3:
                        x = size_if_need1 - new_width;
                        break;
                    case 4:
                        y = (size_if_need2 - new_height) / 2;
                        break;
                    case 5:
                        x = (size_if_need1 - new_width) / 2;
                        y = (size_if_need2 - new_height) / 2;
                        break;
                    case 6:
                        x = size_if_need1 - new_width;
                        y = (size_if_need2 - new_height) / 2;
                        break;
                    case 7:
                        y = size_if_need2 - new_height;
                        break;
                    case 8:
                        x = (size_if_need1 - new_width) / 2;
                        y = size_if_need2 - new_height;
                        break;
                    case 9:
                        x = size_if_need1 - new_width;
                        y = size_if_need2 - new_height;
                        break;
                }
                g.drawImage(image, x, y, new_width, new_height, this);
            }
            g2.dispose();
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }
    }

    public static class CustomOptionPane extends JOptionPane {
        public static void showErrorDialog(Component parent, String message, String title,
                                           Color btn_bg, Color btn_fg, Color btn_bgh, Color btn_fgh) {
            int base_size = getBaseFontSize(parent);
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, base_size));

            JOptionPane optionPane = new JOptionPane(
                    messageLabel,
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.DEFAULT_OPTION
            );

            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.pack();
            changeButtonColors(parent, dialog, btn_bg, btn_fg, btn_bgh, btn_fgh,
                    null, null, null, null);

            dialog.setVisible(true);
        }

        public static void showInfoDialog(Component parent, String message, String title,
                                           Color btn_bg, Color btn_fg, Color btn_bgh, Color btn_fgh) {
            int base_size = getBaseFontSize(parent);
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, base_size));

            JOptionPane optionPane = new JOptionPane(
                    messageLabel,
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION
            );

            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.pack();
            changeButtonColors(parent, dialog, btn_bg, btn_fg, btn_bgh, btn_fgh,
                    null, null, null, null);

            dialog.setVisible(true);
        }

        public static boolean showConfirmDialog(Component parent, String message, String title,
                                                Color btn_bg1, Color btn_fg1, Color btn_bgh1, Color btn_fgh1,
                                                Color btn_bg2, Color btn_fg2, Color btn_bgh2, Color btn_fgh2,
                                                boolean default_b) {
            int base_size = getBaseFontSize(parent);
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, base_size));

            Object[] options = {"No", "Yes"};

            JOptionPane optionPane = new JOptionPane(
                    messageLabel,
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    options,
                    options[0]
            );

            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.pack();
            changeButtonColors(parent, dialog, btn_bg1, btn_fg1, btn_bgh1, btn_fgh1,
                    btn_bg2, btn_fg2, btn_bgh2, btn_fgh2);
            dialog.setVisible(true);

            Object selectedValue = optionPane.getValue();
            if (selectedValue == null || selectedValue == UNINITIALIZED_VALUE) {
                return default_b;
            }
            return "Yes".equals(selectedValue);
        }

        private static int getBaseFontSize(Component parent) {
            if (parent.getWidth() >= parent.getHeight()) {
                return parent.getHeight() / 60;
            } else {
                return parent.getWidth() / 45;
            }
        }

        private static void changeButtonColors(Component parent, Container container, Color btn_bg1, Color btn_fg1,
                                               Color btn_bgh1, Color btn_fgh1, Color btn_bg2, Color btn_fg2,
                                               Color btn_bgh2, Color btn_fgh2) {
            int counter = 0;
            for (Component comp : container.getComponents()) {
                if (comp instanceof JButton button) {
                    int base_size = getBaseFontSize(parent);
                    button.setFont(new Font("Segoe UI", Font.BOLD, (int) (base_size * 0.9)));
                    if (counter == 0 && btn_bg2 != null) {
                        counter += 1;
                        button.setBorderPainted(false);
                        button.setFocusable(false);
                        button.setBackground(btn_bg2);
                        button.setForeground(btn_fg2);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                button.setBackground(btn_bgh2);
                                button.setForeground(btn_fgh2);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                button.setBackground(btn_bg2);
                                button.setForeground(btn_fg2);
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                                button.setBackground(btn_bg2);
                                button.setForeground(btn_fg2);
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), button);
                                if (button.contains(point)) {
                                    button.setBackground(btn_bgh2);
                                    button.setForeground(btn_fgh2);
                                } else {
                                    button.setBackground(btn_bg2);
                                    button.setForeground(btn_fg2);
                                }
                            }
                        });
                    } else {
                        button.setBorderPainted(false);
                        button.setFocusable(false);
                        button.setBackground(btn_bg1);
                        button.setForeground(btn_fg1);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                button.setBackground(btn_bgh1);
                                button.setForeground(btn_fgh1);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                button.setBackground(btn_bg1);
                                button.setForeground(btn_fg1);
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                                button.setBackground(btn_bg1);
                                button.setForeground(btn_fg1);
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), button);
                                if (button.contains(point)) {
                                    button.setBackground(btn_bgh1);
                                    button.setForeground(btn_fgh1);
                                } else {
                                    button.setBackground(btn_bg1);
                                    button.setForeground(btn_fg1);
                                }
                            }
                        });
                    }
                } else if (comp instanceof Container nested) {
                    changeButtonColors(parent, nested, btn_bg1, btn_fg1, btn_bgh1, btn_fgh1,
                            btn_bg2, btn_fg2, btn_bgh2, btn_fgh2);
                }
            }
        }
    }

    public static class CustomProfileIcon implements Icon {
        private int size;
        private final boolean isHover;
        private final String AccType;
        private final Font font;

        public CustomProfileIcon(int size, boolean isHover, String AccType, Font font) {
            this.size = size;
            this.isHover = isHover;
            this.AccType = AccType;
            this.font = font;
        }

        public void UpdateSize(int size) {
            this.size = size;
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

            switch (AccType) {
                case "Administrator" -> {
                    if (!isHover) {
                        g2d.setColor(new Color(56, 53, 70));
                    } else {
                        g2d.setColor(new Color(73, 69, 87));
                    }
                }
                case "Sales Manager" -> {
                    if (!isHover) {
                        g2d.setColor(new Color(212, 87, 132));
                    } else {
                        g2d.setColor(new Color(225, 108, 150));
                    }
                }
                case "Purchase Manager" -> {
                    if (!isHover) {
                        g2d.setColor(new Color(244, 156, 187));
                    } else {
                        g2d.setColor(new Color(255, 184, 211));
                    }
                }
                case "Inventory Manager" -> {
                    if (!isHover) {
                        g2d.setColor(new Color(255, 191, 217));
                    } else {
                        g2d.setColor(new Color(255, 222, 237));
                    }
                }
                default -> {
                    if (!isHover) {
                        g2d.setColor(new Color(147, 147, 147));
                    } else {
                        g2d.setColor(new Color(197, 197, 197));
                    }
                }
            }
            g2d.fillOval(x, y, size, size);

            g2d.setColor(new Color(56, 53, 70));
            if (AccType.equals("Administrator") || AccType.equals("Sales Manager")) {
                g2d.setColor(Color.WHITE);
            }
            Font font_resize = font.deriveFont(size * 0.4f);
            g2d.setFont(font_resize);

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(String.valueOf(AccType.charAt(0)));
            int textHeight = fm.getAscent() - fm.getDescent();

            int textX = x + (size - textWidth) / 2;
            int textY = y + (size + textHeight) / 2;

            g2d.drawString(String.valueOf(AccType.charAt(0)), textX, textY);

            g2d.dispose();
        }
    }

    public static class CustomPopupMenu extends JPopupMenu {
        private final CustomButton base;

        public CustomPopupMenu(CustomButton base, List<String> options, List<ActionListener> actions,
                Font font, int alignment_factor) {
            this.base = base;

            for (int i = 0; i < options.size(); i++) {
                String label = options.get(i);
                ActionListener action = actions.get(i);

                JMenuItem item = new JMenuItem(label);
                item.setFont(font);
                item.addActionListener(action);
                add(item);
            }

            base.addActionListener(_ -> {
                    if (base.ReturnImageState()) {
                        showRelativeToBase(alignment_factor);
                    } else {
                        this.setVisible(false);
                    }
            });
        }

        private void showRelativeToBase(int alignment_factor) {
            int x = 0;
            if (alignment_factor == 1) {
                x = base.getWidth() - this.getPreferredSize().width;
            }
            this.show(base, x, base.getHeight());
        }
    }

    public static class CustomList<E> extends JList<E> {
        private int mode;
        private int[] previousSelection = new int[0];
        private boolean suppressListener = false;

        @SuppressWarnings("unchecked")
        public CustomList(Object data, int mode, int text_size, Font text_font,
                          Color t_normal, Color t_select, Color bg_normal, Color bg_select) {
            super(new DefaultListModel<>());
            this.mode = mode;
            setBorder(BorderFactory.createEmptyBorder());
            setFocusable(false);
            setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (isSelected) {
                        label.setBackground(bg_select);
                        label.setForeground(t_select);
                    } else {
                        label.setBackground(bg_normal);
                        label.setForeground(t_normal);
                    }
                    return label;
                }
            });
            setFont(text_font.deriveFont(Font.PLAIN, text_size));
            if (data instanceof List<?> list) {
                setListData((E[]) list.toArray());
            } else if (data instanceof Object[]) {
                setListData((E[]) data);
            } else {
                throw new IllegalArgumentException("Data must be a List or an Array.");
            }
            if (mode == 1) {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            } else {
                setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                if (mode > 1) {
                    addListSelectionListener(new MaxSelectionEnforcer());
                }
            }
        }

        public void SetChanges(Font text_font, int text_size, int mode) {
            if (text_size >= 0) {
                setFont(text_font.deriveFont(Font.PLAIN, text_size));
                repaint();
            }
            if (mode >= 0) {
                this.mode = mode;
                if (mode == 1) {
                    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                } else {
                    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    if (mode > 1) {
                        addListSelectionListener(new MaxSelectionEnforcer());
                    }
                }
            }
        }

        private class MaxSelectionEnforcer implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (suppressListener || e.getValueIsAdjusting()) return;
                int[] selected = getSelectedIndices();
                if (mode > 0 && selected.length > mode) {
                    suppressListener = true;
                    setSelectedIndices(previousSelection);
                    suppressListener = false;
                } else {
                    previousSelection = selected;
                }
            }
        }
    }

    public static class CustomScrollPane extends JScrollPane {
        public CustomScrollPane(boolean shadow_factor, int border_factor, JComponent view, int bar_factor,
                                Color s, Color r_h, Color r_s, Color l_h, Color l_s, Color thumb, Color track,
                                Color tmb_hover, Color tmb_press, Color trk_hover, Color trk_press, int r) {
            super(view);
            getVerticalScrollBar().setPreferredSize(new Dimension(bar_factor, Integer.MAX_VALUE));
            getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, bar_factor));
            if (thumb != null && track != null) {
                getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                    private boolean isThumbHovered = false;
                    private boolean isThumbPressed = false;

                    {
                        JScrollBar vsb = getVerticalScrollBar();
                        vsb.addMouseMotionListener(new MouseMotionAdapter() {
                            @Override
                            public void mouseMoved(MouseEvent e) {
                                if (!isThumbPressed) {
                                    Rectangle thumbBounds = getThumbBounds();
                                    boolean nowHovered = thumbBounds.contains(e.getPoint());
                                    if (nowHovered != isThumbHovered) {
                                        isThumbHovered = nowHovered;
                                        thumbColor = nowHovered ? tmb_hover : thumb;
                                        trackColor = nowHovered ? trk_hover : track;
                                        vsb.repaint();
                                    }
                                }
                            }

                            @Override
                            public void mouseDragged(MouseEvent e) {
                                isThumbPressed = true;
                                thumbColor = tmb_press;
                                trackColor = trk_press;
                                vsb.repaint();
                            }
                        });

                        vsb.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if (getThumbBounds().contains(e.getPoint())) {
                                    isThumbPressed = true;
                                    thumbColor = tmb_press;
                                    trackColor = trk_press;
                                    vsb.repaint();
                                }
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                if (isThumbPressed) {
                                    isThumbPressed = false;
                                    // Check if the mouse is still on the thumb
                                    Rectangle thumbBounds = getThumbBounds();
                                    if (thumbBounds.contains(e.getPoint())) {
                                        thumbColor = tmb_hover;
                                        trackColor = trk_hover;
                                    } else {
                                        thumbColor = thumb;
                                        trackColor = track;
                                    }
                                    vsb.repaint();
                                }
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                if (!isThumbPressed) {
                                    if (isThumbHovered) {
                                        isThumbHovered = false;
                                        thumbColor = thumb;
                                        trackColor = track;
                                        vsb.repaint();
                                    }
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (!isThumbPressed) {
                                    Rectangle thumbBounds = getThumbBounds();
                                    if (thumbBounds.contains(e.getPoint())) {
                                        isThumbHovered = true;
                                        thumbColor = tmb_hover;
                                        trackColor = trk_hover;
                                        vsb.repaint();
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    protected void configureScrollBarColors() {
                        this.thumbColor = thumb;
                        this.trackColor = track;
                    }

                    @Override
                    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                        if (!c.isEnabled()) return;
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setPaint(thumbColor);
                        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, r, r);
                        g2.dispose();
                    }

                    @Override
                    protected JButton createDecreaseButton(int orientation) {
                        return createZeroButton();
                    }

                    @Override
                    protected JButton createIncreaseButton(int orientation) {
                        return createZeroButton();
                    }

                    private JButton createZeroButton() {
                        JButton button = new JButton();
                        button.setPreferredSize(new Dimension(0, 0));
                        button.setMinimumSize(new Dimension(0, 0));
                        button.setMaximumSize(new Dimension(0, 0));
                        return button;
                    }
                });

                getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
                    private boolean isThumbHovered = false;
                    private boolean isThumbPressed = false;

                    {
                        JScrollBar hsb = getHorizontalScrollBar();

                        hsb.addMouseMotionListener(new MouseMotionAdapter() {
                            @Override
                            public void mouseMoved(MouseEvent e) {
                                Rectangle thumbBounds = getThumbBounds();
                                boolean nowHovered = thumbBounds.contains(e.getPoint());
                                if (nowHovered != isThumbHovered) {
                                    isThumbHovered = nowHovered;
                                    thumbColor = nowHovered ? tmb_hover : thumb;
                                    trackColor = nowHovered ? trk_hover : track;
                                    hsb.repaint();
                                }
                            }

                            @Override
                            public void mouseDragged(MouseEvent e) {
                                isThumbPressed = true;
                                thumbColor = tmb_press;
                                trackColor = trk_press;
                                hsb.repaint();
                            }
                        });

                        hsb.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if (getThumbBounds().contains(e.getPoint())) {
                                    isThumbPressed = true;
                                    thumbColor = tmb_press;
                                    trackColor = trk_press;
                                    hsb.repaint();
                                }
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                if (isThumbPressed) {
                                    isThumbPressed = false;
                                    Rectangle thumbBounds = getThumbBounds();
                                    if (thumbBounds.contains(e.getPoint())) {
                                        thumbColor = tmb_hover;
                                        trackColor = trk_hover;
                                    } else {
                                        thumbColor = thumb;
                                        trackColor = track;
                                    }
                                    hsb.repaint();
                                }
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                if (!isThumbPressed) {
                                    if (isThumbHovered) {
                                        isThumbHovered = false;
                                        thumbColor = thumb;
                                        trackColor = track;
                                        hsb.repaint();
                                    }
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (!isThumbPressed) {
                                    Rectangle thumbBounds = getThumbBounds();
                                    if (thumbBounds.contains(e.getPoint())) {
                                        isThumbHovered = true;
                                        thumbColor = tmb_hover;
                                        trackColor = trk_hover;
                                        hsb.repaint();
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    protected void configureScrollBarColors() {
                        this.thumbColor = thumb;
                        this.trackColor = track;
                    }

                    @Override
                    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                        if (!c.isEnabled()) return;
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setPaint(thumbColor);
                        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, r, r);
                        g2.dispose();
                    }

                    @Override
                    protected JButton createDecreaseButton(int orientation) {
                        return createZeroButton();
                    }

                    @Override
                    protected JButton createIncreaseButton(int orientation) {
                        return createZeroButton();
                    }

                    private JButton createZeroButton() {
                        JButton button = new JButton();
                        button.setPreferredSize(new Dimension(0, 0));
                        button.setMinimumSize(new Dimension(0, 0));
                        button.setMaximumSize(new Dimension(0, 0));
                        return button;
                    }
                });
            }

            if (!shadow_factor) {
                setBorder(BorderFactory.createEmptyBorder());
            }
            if (border_factor > 0) {
                Border solid = new MatteBorder(border_factor, border_factor, border_factor, border_factor, s);
                Border raisedBevel = new BevelBorder(BevelBorder.RAISED, r_h, r_s);
                Border loweredBevel = new BevelBorder(BevelBorder.LOWERED, l_h, l_s);
                Border compound = BorderFactory.createCompoundBorder(
                        raisedBevel,
                        BorderFactory.createCompoundBorder(solid, loweredBevel)
                );
                setBorder(compound);
            }
        }

        public void UpdateBorder(int border_factor, Color s, Color r_h, Color r_s, Color l_h, Color l_s) {
            if (border_factor > 0) {
                Border solid = new MatteBorder(border_factor, border_factor, border_factor, border_factor, s);
                Border raisedBevel = new BevelBorder(BevelBorder.RAISED, r_h, r_s);
                Border loweredBevel = new BevelBorder(BevelBorder.LOWERED, l_h, l_s);
                Border compound = BorderFactory.createCompoundBorder(
                        raisedBevel,
                        BorderFactory.createCompoundBorder(solid, loweredBevel)
                );
                setBorder(compound);
                repaint();
            }
        }
    }

    public static class CustomSearchIcon implements Icon {
        private int size, border_width;
        private final Color stroke, fill;

        public CustomSearchIcon(int size, int border_width, Color stroke, Color fill) {
            this.size = size;
            this.border_width = border_width;
            this.stroke = stroke;
            this.fill = fill;
        }

        public void UpdateSize(int size) {
            this.size = size;
        }
        
        public void UpdateBorder(int border_width) { this.border_width = border_width; }
        
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
            g2d.setStroke(new BasicStroke(
                    border_width,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER
            ));
            g2d.setColor(stroke);
            double unit = size / 20.0;

            double y1 = 0;
            double x1 = 20;

            double y2 = 8.0525;
            double x2 = 20 - y2;

            int px1 = (int) (x1 * unit);
            int py1 = (int) ((20 - y1) * unit);

            int px2 = (int) (x2 * unit);
            int py2 = (int) ((20 - y2) * unit);

            g2d.drawLine(x + px1, y + py1, x + px2, y + py2);

            double centerX = 7;
            double centerY = 13;
            double radius = 7;

            int pixelDiameter = (int) (2 * radius * unit);
            int pixelCenterX = (int) (centerX * unit);
            int pixelCenterY = (int) ((20 - centerY) * unit);

            g2d.setColor(fill);
            g2d.fillOval(
                    x+ pixelCenterX - pixelDiameter / 2,
                    y + pixelCenterY - pixelDiameter / 2,
                    pixelDiameter,
                    pixelDiameter
            );

            g2d.setColor(stroke);
            g2d.drawOval(
                    x+ pixelCenterX - pixelDiameter / 2,
                    y + pixelCenterY - pixelDiameter / 2,
                    pixelDiameter,
                    pixelDiameter
            );
            g2d.dispose();
        }
    }

    public static class CustomTable extends JTable {
        private int[] previousSelection = new int[0];
        private boolean suppressListener = false;
        private int mode;
        private Font content;
        private Color cfg, cfg_press, cbg, cbg_press;

        public CustomTable(String[] columns, Object[][] data, Font title, Font content,
                           Color cfg, Color cfg_press, Color cbg, Color cbg_press,
                           int mode, int height) {
            super(new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            this.mode = mode;
            this.content = content;
            this.cfg = cfg;
            this.cfg_press = cfg_press;
            this.cbg = cbg;
            this.cbg_press = cbg_press;
            JTableHeader header = getTableHeader();
            header.setFont(title);
            setDefaultRenderer(Object.class, new CellRenderer(content, cbg, cfg, cbg_press, cfg_press));

            if (mode == 1) {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            } else {
                setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                if (mode > 1) {
                    getSelectionModel().addListSelectionListener((new MaxSelectionEnforcer()));
                }
            }

            setRowHeight(height);
            setShowGrid(false);
            setIntercellSpacing(new Dimension(0, 0));
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(new DefaultTableModel(data, columns));
            setRowSorter(sorter);
            setFillsViewportHeight(true);
        }

        public void SetChanges(Font title, Font content, int mode) {
            if (title != null) {
                JTableHeader header = getTableHeader();
                header.setFont(title);
            }
            if (content != null) {
                setDefaultRenderer(Object.class, new CellRenderer(content, cbg, cfg, cbg_press, cfg_press));
            }
            if (mode >= 0) {
                this.mode = mode;
                if (mode == 1) {
                    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                } else {
                    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    if (mode > 1) {
                        getSelectionModel().addListSelectionListener((new MaxSelectionEnforcer()));
                    }
                }
            }
            repaint();
        }

        public void SetColors(Color cfg, Color cfg_press, Color cbg, Color cbg_press) {
            this.cfg = cfg;
            this.cfg_press = cfg_press;
            this.cbg = cbg;
            this.cbg_press = cbg_press;
            setDefaultRenderer(Object.class, new CellRenderer(content, cbg, cfg, cbg_press, cfg_press));
            repaint();
        }

        private static class CellRenderer extends DefaultTableCellRenderer {
            private final Color bg, fg, bg_press, fg_press;
            private final Font font;

            public CellRenderer(Font font, Color bg, Color fg, Color bg_press, Color fg_press) {
                this.font = font;
                this.bg = bg;
                this.fg = fg;
                this.bg_press = bg_press;
                this.fg_press = fg_press;
                setOpaque(true);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                setText(value != null ? value.toString() : "");
                setFont(font);
                if (isSelected) {
                    setBackground(bg_press);
                    setForeground(fg_press);
                } else {
                    setBackground(bg);
                    setForeground(fg);
                }
                return this;
            }
        }

        private class MaxSelectionEnforcer implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (suppressListener || e.getValueIsAdjusting()) return;
                int[] selected = getSelectedRows();
                if (mode > 0 && selected.length > mode) {
                    suppressListener = true;
                    clearSelection();
                    for (int i : previousSelection) {
                        addRowSelectionInterval(i, i);
                    }
                    suppressListener = false;
                } else {
                    previousSelection = selected;
                }
            }
        }

        public void UpdateTableContent(String[] columns, Object[][] data) {
            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            setModel(model);
            setRowSorter(new TableRowSorter<>(model));
            if (mode == 1) {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            } else {
                setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                if (mode > 1) {
                    getSelectionModel().addListSelectionListener((new MaxSelectionEnforcer()));
                }
            }
            repaint();
        }
    }

    public static class CustomArrowIcon implements Icon {
        private int size, borderWidth;
        private final Color stroke;
        private final boolean direction;

        public CustomArrowIcon(int size, int borderWidth, Color stroke, boolean direction) {
            this.size = size;
            this.borderWidth = borderWidth;
            this.stroke = stroke;
            this.direction = direction;
        }

        public void UpdateSize(int size) {
            this.size = size;
        }

        public void UpdateBorder(int borderWidth) {
            this.borderWidth = borderWidth;
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
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(stroke);

            double unit = size / 20.0;
            int[][] arrowPoints;
            if (direction) {
                arrowPoints = new int[][] {
                        {14, 5},
                        {6, 10},
                        {14, 15}
                };
            } else {
                arrowPoints = new int[][] {
                        {6, 5},
                        {14, 10},
                        {6, 15}
                };
            }
            for (int i = 0; i < arrowPoints.length - 1; i++) {
                int x1 = (int) (arrowPoints[i][0] * unit);
                int y1 = (int) (arrowPoints[i][1] * unit);
                int x2 = (int) (arrowPoints[i + 1][0] * unit);
                int y2 = (int) (arrowPoints[i + 1][1] * unit);
                g2d.drawLine(x + x1, y + y1, x + x2, y + y2);
            }
            g2d.dispose();
        }
    }

    public static class CustomXIcon implements Icon {
        private int size, borderWidth;
        private final Color stroke;
        private final boolean roundedEnds;

        public CustomXIcon(int size, int borderWidth, Color stroke, boolean roundedEnds) {
            this.size = size;
            this.borderWidth = borderWidth;
            this.stroke = stroke;
            this.roundedEnds = roundedEnds;
        }

        public void UpdateSize(int size) {
            this.size = size;
        }

        public void UpdateBorder(int borderWidth) {
            this.borderWidth = borderWidth;
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
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int capStyle = roundedEnds ? BasicStroke.CAP_ROUND : BasicStroke.CAP_BUTT;
            g2d.setStroke(new BasicStroke(borderWidth, capStyle, BasicStroke.JOIN_ROUND));
            g2d.setColor(stroke);

            double unit = size / 20.0;

            int x1 = x + (int) (5 * unit);
            int y1 = y + (int) (5 * unit);
            int x2 = x + (int) (15 * unit);
            int y2 = y + (int) (15 * unit);

            int x3 = x + (int) (15 * unit);
            int y3 = y + (int) (5 * unit);
            int x4 = x + (int) (5 * unit);
            int y4 = y + (int) (15 * unit);

            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x3, y3, x4, y4);

            g2d.dispose();
        }
    }
}
