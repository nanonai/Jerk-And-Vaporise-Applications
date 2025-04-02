import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class ImagePanel extends JPanel {
    private final Image bg_img;
    private int bg_width, bg_height;
    private final JPanel grid;

    public ImagePanel(Image bg_img) {
        this.bg_img = bg_img;
        if (bg_img != null) {
            bg_width = bg_img.getWidth(this);
            bg_height = bg_img.getHeight(this);
        }
        setBackground(new Color(56, 53, 70));
        setLayout(null);

        grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        add(grid);
    }

    public JPanel getGridPanel() {
        return grid;
    }

    public void updateBgSize(int frame_width, int frame_height) {
        if (bg_img == null) return;
        double bg_ratio = (double) bg_width / bg_height;
        int new_width = frame_width;
        int new_height = (int) (frame_width / bg_ratio);
        if (new_height > frame_height) {
            new_height = frame_height;
            new_width = (int) (frame_height * bg_ratio);
        }
        int x = (frame_width - new_width) / 2;
        int y = (frame_height - new_height) / 2;
        grid.setBounds(x, y, new_width, new_height);
        grid.revalidate();
        grid.repaint();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg_img != null) {
            int panel_width = getWidth();
            int panel_height = getHeight();

            double bg_ratio = (double) bg_width / bg_height;
            int new_width = panel_width;
            int new_height = (int) (panel_width / bg_ratio);

            if (new_height > panel_height) {
                new_height = panel_height;
                new_width = (int) (panel_height * bg_ratio);
            }

            int x = (panel_width - new_width) / 2;
            int y = (panel_height - new_height) / 2;

            g.drawImage(bg_img, x, y, new_width, new_height, this);
        }
    }
}

class ImageCell extends JPanel {
    private final BufferedImage image;

    public ImageCell(BufferedImage image) {
        this.image = image;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panel_width = getWidth();
            int panel_height = getHeight();

            double img_ratio = (double) image.getWidth() / image.getHeight();
            int new_width = panel_width;
            int new_height = (int) (panel_width / img_ratio);

            if (new_height > panel_height) {
                new_height = panel_height;
                new_width = (int) (panel_height * img_ratio);
            }

            int x = (panel_width - new_width) / 2;
            int y = (panel_height - new_height) / 2;

            g.drawImage(image, x, y, new_width, new_height, this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BufferedImage bg_login, icon, logo_login;
            try {
                bg_login = ImageIO.read(new File("images/login_bg.png"));
                icon = ImageIO.read(new File("images/icon.png"));
                logo_login = ImageIO.read(new File("images/logo_original.png"));
            } catch (IOException e) {
                e.getStackTrace();
                return;
            }

            JFrame frame = new JFrame();
            frame.setSize(1244, 730);
            frame.setMinimumSize(new Dimension(1244, 730));
            frame.setTitle("OWSB Automated Purchase Order Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setIconImage(icon);

            ImagePanel contentPanel = new ImagePanel(bg_login);
            frame.setContentPane(contentPanel);

            JPanel grid = contentPanel.getGridPanel();
            GridBagConstraints grid_constraints = new GridBagConstraints();
            grid.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 20));
            grid_constraints.insets = new Insets(0, 40, 0, 20);

            grid_constraints.gridx = 0;
            grid_constraints.gridy = 0;
            grid_constraints.weighty = 1;
            grid_constraints.weightx = 1;
            grid_constraints.fill = GridBagConstraints.BOTH;
            JLabel place_holder1 = new JLabel("", SwingConstants.CENTER);
            place_holder1.setOpaque(false);
            grid.add(place_holder1, grid_constraints);

            grid_constraints.gridx = 0;
            grid_constraints.gridy = 1;
            grid_constraints.weighty = 1;
            grid_constraints.fill = GridBagConstraints.BOTH;
            grid.add(new ImageCell(logo_login), grid_constraints);

            grid_constraints.gridx = 0;
            grid_constraints.gridy = 2;
            grid_constraints.weighty = 1;
            grid_constraints.fill = GridBagConstraints.BOTH;
            JLabel logo_text = new JLabel("<html><div style='text-align: left; "
                    + "font-size: 12px; font-family: courier; color:#383546;'>"
                    + "Omega Wholesale Sdn Bhd (OWSB)<br>"
                    + "Automated Purchase Order Management System"
                    + "</div></html>");
            logo_text.setHorizontalAlignment(SwingConstants.LEFT);
            logo_text.setVerticalAlignment(SwingConstants.TOP);
            logo_text.setOpaque(false);
            grid.add(logo_text, grid_constraints);

            grid_constraints.gridx = 0;
            grid_constraints.gridy = 3;
            grid_constraints.weighty = 1;
            grid_constraints.weightx = 1;
            grid_constraints.fill = GridBagConstraints.BOTH;
            JLabel place_holder2 = new JLabel("<html><div style='text-align: left; "
                    + "font-size: 12px; font-family: courier; color:#383546;'>"
                    + "Welcome to OWSB's management system!<br>"
                    + "Please login to begin your work."
                    + "</div></html>");
            logo_text.setHorizontalAlignment(SwingConstants.LEFT);
            logo_text.setVerticalAlignment(SwingConstants.BOTTOM);
            place_holder2.setOpaque(false);
            grid.add(place_holder2, grid_constraints);

            grid_constraints.gridx = 1;
            grid_constraints.gridy = 0;
            grid_constraints.gridheight = 4;
            grid_constraints.weightx = 5;
            grid_constraints.fill = GridBagConstraints.BOTH;
            JLabel rightLabel = new JLabel("Right Side", SwingConstants.CENTER);
            rightLabel.setOpaque(true);
            rightLabel.setBackground(Color.GREEN);
            grid.add(rightLabel, grid_constraints);

            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    contentPanel.updateBgSize(frame.getContentPane().getWidth(),
                            frame.getContentPane().getHeight());
                }
            });

            frame.setVisible(true);
        });
    }
}
