package InventoryMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import InventoryMgr.misc.InvStatic;
import PurchaseMgr.PurchaseOrder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateStock {

    private static JFrame parent = InvStatic.parent;
    private static Font merriweather = InvStatic.merriweather, boldonse = InvStatic.boldonse;
    private static JPanel content = InvStatic.content;
    private static User current_user = InvStatic.current_user;

    private static CustomComponents.CustomTable POTable;
    private static CustomComponents.CustomScrollPane POScrollPane;
    private static JLabel lbl_indicate;
    private static CustomComponents.CustomButton BtnAdd;

    public static void ShowPage(Item itm) {
        JDialog dialog = new JDialog(parent, "Stock Update", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize((int) (parent.getWidth() / 1.5), parent.getHeight() / 2);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = 0;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }

        int base_size = size_factor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        JLabel title = new JLabel("Stock Update: " + itm.ItemName);
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("Current Stock: " + itm.StockCount);
        subtitle.setOpaque(false);
        subtitle.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 1.3)));
        panel.add(subtitle, gbc);

        gbc.gridy++;
        List<PurchaseOrder> ListPo = FindPo(itm);
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "PurchaseQuantity", "SupplierID", "OrderDate", "PurchaseMgrID", "Status"};
        Object[][] data = new Object[ListPo.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : ListPo) {
            data[counter] = new Object[]{purchaseOrder.PurchaseOrderID, purchaseOrder.ItemID, purchaseOrder.PurchaseQuantity,
                    purchaseOrder.SupplierID, purchaseOrder.OrderDate, purchaseOrder.PurchaseMgrID, purchaseOrder.Status};
            counter += 1;
        }

        gbc.gridy++;
        if (counter > 0) {
            gbc.insets = new Insets(0, 0, 0, 0);
            POTable = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18), merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                    Color.WHITE, new Color(212, 212, 212), 1, 30);
            POTable.setShowHorizontalLines(true);
            POTable.setShowVerticalLines(true);
            POTable.setGridColor(new Color(230, 230, 230));

            lbl_indicate = new JLabel("");
            lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
            lbl_indicate.setOpaque(false);
            lbl_indicate.setForeground(new Color(122, 122, 122));

            POTable.setShowHorizontalLines(true);
            POTable.setShowVerticalLines(true);
            POTable.setGridColor(new Color(230, 230, 230));

            POScrollPane = new CustomComponents.CustomScrollPane(false, 1, POTable,
                    6, new Color(202, 202, 202), Main.transparent,
                    Main.transparent, Main.transparent, Main.transparent,
                    new Color(170, 170, 170), Color.WHITE,
                    new Color(140, 140, 140), new Color(110, 110, 110),
                    Color.WHITE, Color.WHITE, 6);
            gbc.weighty = 10;
            panel.add(POScrollPane, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weighty = 0.5;
            gbc.insets = new Insets(0, 2, 2, 0);
            panel.add(lbl_indicate, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = 1;
            gbc.weighty = 2;
            gbc.fill = GridBagConstraints.BOTH;

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.setOpaque(false);
            panel.add(buttonPanel, gbc);

            GridBagConstraints buttonGbc = new GridBagConstraints();
            buttonGbc.gridx = 0;
            buttonGbc.gridy = 0;
            buttonGbc.insets = new Insets(0, 7, 5, 7);

            BtnAdd = new CustomComponents.CustomButton("Mark as Received", merriweather, Color.WHITE, Color.WHITE,
                    new Color(225, 108, 150), new Color(237, 136, 172),
                    Main.transparent, 0, 20, Main.transparent, false,
                    5, false, null, 0, 0,0);
            BtnAdd.setPreferredSize(new Dimension(250, 45));
            BtnAdd.addActionListener(_ -> {
                try {
                    int selectedRowIndex = POTable.getSelectedRow();
                    Object value = POTable.getValueAt(selectedRowIndex, 0);
                    PurchaseOrder selectedPO = PurchaseOrder.getPurchaseOrderID(value.toString(), "datafile/purchaseOrder.txt");
                    boolean mark = MarkPOReceived(selectedPO);
                    if (!mark) {
                        CustomComponents.CustomOptionPane.showErrorDialog(parent, "Something went wrong while processing this Purchase Order", "Error",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255));
                        dialog.dispose();
                    } else {
                        CustomComponents.CustomOptionPane.showInfoDialog(parent, "Successfully updated stock count!", "Completed",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255));
                        dialog.dispose();
                    }
                } catch (Exception e){
                    CustomComponents.CustomOptionPane.showErrorDialog(parent, "You might want to select something before doing this", "Invalid selection",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255));
                }
            });

            buttonPanel.add(BtnAdd, buttonGbc);
        } else {
            gbc.insets = new Insets(0, 10, 10, 0);
            JLabel type_label = new JLabel("No Purchase Order Found for Item.");
            type_label.setOpaque(false);
            type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
            panel.add(type_label, gbc);
        }

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);
    }

    static List<PurchaseOrder> FindPo(Item item) {

        // ReadPO File, find itemID and check if the itemId is approved or whatsoever

        List<PurchaseOrder> AllPO = PurchaseOrder.listAllPurchaseOrders(Main.purchase_order_file);
        List<PurchaseOrder> Filtered = new ArrayList<PurchaseOrder>();

        for (PurchaseOrder po : AllPO) {
            if (Objects.equals(po.ItemID, item.ItemID)) {
                if (Objects.equals(po.Status, "Approved")) {
                    Filtered.add(po);
                }
            }
        }

        return Filtered;
    }

    static boolean MarkPOReceived(PurchaseOrder po) {
        Item item = Item.getItemByID(po.ItemID, Main.item_file);
        Item.addStock(item, po.PurchaseQuantity);
        po.Status = "Received";
        PurchaseOrder.ModifyPurchaseOrder(po.PurchaseOrderID, po, "datafile/purchaseOrder.txt");
        return true;
    }
}
