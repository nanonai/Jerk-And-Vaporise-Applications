package SalesMgr;

import Admin.CustomComponents;
import Admin.Main;
import Admin.User;
import FinanceMgr.PurchaseRequisition;
import InventoryMgr.Item;
import PurchaseMgr.Item_Supplier;
import PurchaseMgr.Supplier;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditPurchaseRequisition {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static PurchaseRequisition current_pr;
    private static String itemNames;
    private static JComboBox<Object> itemComboBox, supplierComboBox;
    private static CustomComponents.EmptyTextField quantity;
    private static JLabel price, total;
    private static JDialog dialog;
    private static User current_user;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user, PurchaseRequisition current_pr) {
        EditPurchaseRequisition.parent = parent;
        EditPurchaseRequisition.merriweather = merriweather;
        EditPurchaseRequisition.boldonse = boldonse;
        EditPurchaseRequisition.content = content;
        EditPurchaseRequisition.current_user = current_user;
        EditPurchaseRequisition.current_pr = current_pr;
    }

    public static void UpdatePurchaseRequisition(PurchaseRequisition purchaseRequisition) {
        EditPurchaseRequisition.current_pr = purchaseRequisition;
    }

    public static boolean ShowPage(){
        JDialog dialog = new JDialog(parent, "Modify Purchase Requisition", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(650, 350);
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
        JLabel title = new JLabel("Modify Purchase Requisition");
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
        JLabel label4 = new JLabel();
        panel.add(label4, gbc);

        gbc.gridy = 5;
        JLabel label5 = new JLabel();
        panel.add(label5, gbc);

        gbc.gridy = 6;
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

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

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
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel blank2 = new JLabel();
        blank2.setOpaque(false);
        button_panel2.add(blank2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel2.add(confirm, gbc);

        confirm.addActionListener(e -> {
            String itemName = Objects.requireNonNull(itemComboBox.getSelectedItem()).toString();
            Item item = Item.getItemByName(itemName, Main.item_file);
            assert item != null;
            String itemID = item.ItemID;

            String supplierName = Objects.requireNonNull(supplierComboBox.getSelectedItem()).toString();
            Supplier supplier = Supplier.getSupplierByName(supplierName, Main.supplier_file);
            assert supplier != null;
            String supplierID = supplier.SupplierID;

            String qtyText = quantity.getText().trim();

            if (qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                PurchaseRequisition PR = new PurchaseRequisition(
                        current_pr.PurchaseReqID,
                        itemID,
                        supplierID,
                        Integer.parseInt(qtyText),
                        current_pr.ReqDate,
                        current_user.UserID,
                        0
                );

                PurchaseRequisition.ModifyPurchaseRequisition(PR.PurchaseReqID, PR, Main.purchase_req_file);
                dialog.dispose();
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Purchase requisition updated successfully",
                        "Successful!",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for quantity.",
                        "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        List<String> itemComboBoxData = new ArrayList<>();
        List<Item> itemsForPO = Item.listAllItem(Main.item_file);
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
                    String selectedItemName = Objects.requireNonNull(itemComboBox.getSelectedItem()).toString();

                    Item selectedItem = Item.getItemByName(selectedItemName, Main.item_file);
                    if (selectedItem == null) return;

                    String selectedItemID = selectedItem.ItemID;

                    List<Supplier> suppliersForPO = Item_Supplier.getSuppliersByItemID(
                            selectedItemID,
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
        itemComboBox.getActionListeners()[0].actionPerformed(new ActionEvent(itemComboBox, ActionEvent.ACTION_PERFORMED, ""));

        panel.add(itemComboBox, gbc);
        String name = Item.getItemByID(current_pr.ItemID, Main.item_file).ItemName;
        itemComboBox.setSelectedItem(name);

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
        gbc.insets = new Insets(3, 2, 3, 2);
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        quantity = new CustomComponents.EmptyTextField(20, "", new Color(0, 0, 0));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        allowOnlyPositiveNonZeroIntegers(quantity);
        quantity.setText(Integer.toString(current_pr.Quantity));
        inner1.add(quantity, gbc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return false;
    }

    public static void allowOnlyPositiveNonZeroIntegers(JTextField textField) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;

                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.insert(offset, string);

                if (isValidQuantity(newText.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;

                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.replace(offset, offset + length, text);

                if (isValidQuantity(newText.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidQuantity(String text) {
                if (text.isEmpty()) return true;

                if (!text.matches("\\d+")) return false;

                if (text.startsWith("0")) return false;

                return true;
            }
        });
    }
}