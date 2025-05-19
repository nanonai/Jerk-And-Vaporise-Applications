package PurchaseMgr;

import Admin.User;
import Admin.CustomComponents;
import Admin.Main;
import InventoryMgr.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddPO {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static String itemNames;
    private static JComboBox<Object> itemComboBox, supplierComboBox;
    private static CustomComponents.EmptyTextField quantity, price, total;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        AddPO.parent = parent;
        AddPO.merriweather = merriweather;
        AddPO.boldonse = boldonse;
        AddPO.content = content;
        AddPO.current_user = current_user;
    }

    public static void ShowPage(){
        JDialog dialog = new JDialog(parent, "Add Purchase Order", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
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
        gbc.insets = new Insets(10, 10, 10, 0);
        JLabel title = new JLabel("Create Purchase Order");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel label1 = new JLabel("ItemName:");
        label1.setOpaque(false);
        label1.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label1, gbc);

        gbc.gridy = 2;
        JLabel label2 = new JLabel("Supplier:");
        label2.setOpaque(false);
        label2.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label2, gbc);

        gbc.gridy = 3;
        JLabel label3 = new JLabel("Quantity:");
        label3.setOpaque(false);
        label3.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label3, gbc);

        gbc.gridy = 4;
        JLabel label4 = new JLabel("Price:");
        label4.setOpaque(false);
        label4.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label4, gbc);

        gbc.gridy = 5;
        JLabel label5 = new JLabel("Total:");
        label5.setOpaque(false);
        label5.setFont(merriweather.deriveFont(Font.PLAIN, (float)(base_size)));
        panel.add(label5, gbc);

        gbc.gridy = 6;
        //gbc.weighty = 1;
        JLabel type_label6 = new JLabel();
        panel.add(type_label6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        panel.add(button_panel1, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel1.add(cancel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel blank1 = new JLabel();
        blank1.setOpaque(false);
        button_panel1.add(blank1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel2 = new JPanel(new GridBagLayout());
        button_panel2.setOpaque(false);
        panel.add(button_panel2, gbc);

        gbc.gridx = 0;
        // gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel blank2 = new JLabel();
        blank2.setOpaque(false);
        button_panel2.add(blank2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel2.add(confirm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        // ArrayList
        List<String> itemComboBoxData = new ArrayList<>();
        // Save data to an Arraylist
        List<Item> itemsForPO = Item.listAllItem(Main.item_file);
        // loop item into array(itemComboBoxData) so we use itemsForPO other than itemComboBox.
        for (Item item : itemsForPO){
            itemComboBoxData.add(item.ItemName);
        }
        itemComboBox = new JComboBox<>(itemComboBoxData.toArray());
        itemComboBox.setFocusable(false);
        itemComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
        itemComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
                            Objects.requireNonNull(Item.getItemByName(Objects.requireNonNull(
                                    itemComboBox.getSelectedItem()).toString(), Main.item_file)).ItemID,
                            Main.item_supplier_file
                    );
                    supplierComboBox.removeAllItems();
                    for (Supplier supplier : suppliersForPO) {
                        supplierComboBox.addItem(supplier.SupplierName);
                    }
                    supplierComboBox.repaint();
                    supplierComboBox.revalidate();
                });
            }
        });
        panel.add(itemComboBox, gbc);

        gbc.gridy = 2;
        List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
                Objects.requireNonNull(Item.getItemByName(Objects.requireNonNull(
                        itemComboBox.getSelectedItem()).toString(), Main.item_file)).ItemID,
                Main.item_supplier_file
        );
        List<String> supplierComboBoxData = new ArrayList<>();
        for (Supplier supplier : suppliersForPO){
            supplierComboBoxData.add(supplier.SupplierName);
        }
        supplierComboBox = new JComboBox<>(supplierComboBoxData.toArray());
        supplierComboBox.setFocusable(false);
        supplierComboBox.setFont(merriweather.deriveFont(Font.PLAIN));
        panel.add(supplierComboBox, gbc);

        gbc.gridy = 3;
        quantity = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        //quantity.addActionListener(_ -> {SwingUtilities.invokeLater(fullname::requestFocusInWindow);});
        //quantity.addFocusListener(new FocusListener() {
        panel.add(quantity, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
