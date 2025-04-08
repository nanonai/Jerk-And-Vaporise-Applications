import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Home {
    public static int indicator = 0;
    public static Buffer current_user;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static BufferedImage logo, bg;
    private static CustomComponents.ImageCell logo_cell;
    private static JPanel background;
    private static JPanel outer_grid, side_bar, top_bar, content;

    public static void HomeLoader(JFrame parent, Font merriweather, Font boldonse, Buffer current_user) {
        try {
            logo = ImageIO.read(new File("images/logo_white.png"));
            bg = ImageIO.read(new File("images/home_bg.jpg"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Home.merriweather = merriweather;
        Home.boldonse = boldonse;
        Home.parent = parent;
        Home.current_user = current_user;
    }

    public static void ShowPage() {
        background = new JPanel();
        background.setOpaque(false);
        outer_grid = new JPanel(new GridBagLayout());
        outer_grid.setOpaque(true);
        outer_grid.setBackground(Color.black);
        GridBagConstraints gbc_outer = new GridBagConstraints();
        outer_grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc_outer.gridx = 0;
        gbc_outer.gridy = 0;
        gbc_outer.fill = GridBagConstraints.BOTH;
        gbc_outer.gridheight = 2;
        gbc_outer.weightx = 1;
        side_bar = new JPanel(new GridBagLayout());
        side_bar.setOpaque(true);
        side_bar.setBackground(new Color(56, 53, 70));

        GridBagConstraints gbc_side = new GridBagConstraints();
        gbc_side.gridx = 0;
        gbc_side.gridy = 0;
        gbc_side.fill = GridBagConstraints.BOTH;
        logo_cell = new CustomComponents.ImageCell(logo, 0.5, 5);
        side_bar.add(logo_cell, gbc_side);

        outer_grid.add(side_bar, gbc_outer);

        gbc_outer.gridx = 1;
        gbc_outer.gridheight = 1;
        gbc_outer.weighty = 1;
        gbc_outer.weightx = 5;
        top_bar = new JPanel(new GridBagLayout());
        top_bar.setOpaque(true);
        top_bar.setBackground(Color.WHITE);
        outer_grid.add(top_bar, gbc_outer);

        gbc_outer.gridy = 1;
        gbc_outer.weighty = 5;
        content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        outer_grid.add(content, gbc_outer);
        background.add(outer_grid, gbc_outer);
        parent.setContentPane(background);
    }

    public static void PageChanger() {
        switch (indicator) {
//    Please indicate the relation of the indicator value and specific java class:
//    0 -> Administrator Home Page
//    1 -> Sales Manager Home Page
//    2 -> Purchase Manager Home Page
//    3 -> Inventory Manager Home Page
//    4 -> Finance Manager Home Page
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    public static void UpdateComponentSize(int parent_width, int parent_height) {
        int new_width = parent_width;
        int new_height = (int) (parent_width * 9 / 16);

        if (new_height > parent_height) {
            new_height = parent_height;
            new_width = (int) (parent_height * 16 / 9);
        }
        outer_grid.setBounds((parent_width - new_width) / 2,
                (parent_height - new_height) / 2,
                new_width, new_height);
        background.revalidate();
        background.repaint();
        outer_grid.revalidate();
        outer_grid.repaint();
    }
}
