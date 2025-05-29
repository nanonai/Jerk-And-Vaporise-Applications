package Admin;

import javax.swing.*;
import java.awt.*;
import SalesMgr.*;
import PurchaseMgr.*;
import InventoryMgr.*;
import FinanceMgr.*;

public class Home {
    public static int indicator = -1;
    public static User current_user;
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static CustomComponents.ColorPanel background;
    private static JPanel outer_grid, side_bar, top_bar, content;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse) {
        Home.merriweather = merriweather;
        Home.boldonse = boldonse;
        Home.parent = parent;
    }

    public static void ShowPage() {
        background = new CustomComponents.ColorPanel(new Color(239, 239, 239), 16.0 / 9.0);
        outer_grid = background.getGridPanel();
        GridBagConstraints gbc_outer = new GridBagConstraints();

        gbc_outer.gridx = 0;
        gbc_outer.gridy = 0;
        gbc_outer.fill = GridBagConstraints.BOTH;
        gbc_outer.gridheight = 2;
        gbc_outer.weightx = 1;
        side_bar = new JPanel(new GridBagLayout());
        side_bar.setOpaque(true);
        side_bar.setBackground(new Color(56, 53, 70));
        outer_grid.add(side_bar, gbc_outer);

        gbc_outer.gridx = 1;
        gbc_outer.gridheight = 1;
        gbc_outer.weighty = 1;
        gbc_outer.weightx = 11;
        top_bar = new JPanel(new GridBagLayout());
        top_bar.setOpaque(true);
        top_bar.setBackground(Color.WHITE);
        outer_grid.add(top_bar, gbc_outer);

        gbc_outer.gridy = 1;
        gbc_outer.weighty = 14;
        content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        outer_grid.add(content, gbc_outer);

        AdmHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, current_user);
        SalesHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, current_user);
        PurchaseHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, current_user);
        InventoryHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, current_user);
        FinanceHome.Loader(parent, merriweather, boldonse, side_bar, top_bar, content, current_user);
        PageChanger();

        background.add(outer_grid, gbc_outer);
        parent.setContentPane(background);
        SwingUtilities.invokeLater(side_bar::requestFocusInWindow);
    }

    public static void PageChanger() {
        side_bar.removeAll();
        top_bar.removeAll();
        content.removeAll();
        side_bar.revalidate();
        side_bar.repaint();
        top_bar.revalidate();
        top_bar.repaint();
        content.revalidate();
        content.repaint();
        switch (indicator) {
            case 1:
                AdmHome.ShowPage();
                break;
            case 2:
                SalesHome.ShowPage();
                break;
            case 3:
                PurchaseHome.ShowPage();
                break;
            case 4:
                InventoryHome.ShowPage();
                break;
            case 5:
                FinanceHome.ShowPage();
                break;
        }
        UpdateComponentSize(parent.getWidth(), parent.getHeight());
    }

    public static void UpdateComponentSize(int parent_width, int parent_height) {
        background.updateSize(parent_width, parent_height);
        outer_grid.revalidate();
        outer_grid.repaint();
        side_bar.setPreferredSize(new Dimension(parent_width, parent_height));
        side_bar.revalidate();
        side_bar.repaint();
        top_bar.setPreferredSize(new Dimension(parent_width, parent_height / 12));
        top_bar.revalidate();
        top_bar.repaint();
        content.revalidate();
        content.repaint();
        SwingUtilities.invokeLater(() -> {
            switch (indicator) {
                case 1:
                    AdmHome.UpdateComponentSize(parent_width, parent_height);
                    break;
                case 2:
                    SalesHome.UpdateComponentSize(parent_width, parent_height);
                    break;
                case 3:
                    PurchaseHome.UpdateComponentSize(parent_width, parent_height);
                    break;
                case 4:
                    InventoryHome.UpdateComponentSize(parent_width, parent_height);
                    break;
                case 5:
                    FinanceHome.UpdateComponentSize(parent_width, parent_height);
                    break;
            }
        });
    }
}
