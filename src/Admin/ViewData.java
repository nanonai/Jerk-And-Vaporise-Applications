package Admin;

import FinanceMgr.Payment;
import FinanceMgr.PurchaseRequisition;
import PurchaseMgr.PurchaseOrder;
import SalesMgr.Sales;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewData {
    private static JFrame parent;
    private static Font merriweather;
    private static User current_user;
    private static JComboBox<Object> type_combo;
    private static CustomComponents.CustomList<String> record_list1;
    private static List<Sales> list1;
    private static List<PurchaseRequisition> list2;
    private static List<PurchaseOrder> list3;
    private static List<Payment> list4;

    public static void Loader(JFrame parent, Font merriweather) {
        ViewData.parent = parent;
        ViewData.merriweather = merriweather;
    }

    public static void UpdateUser(User current_user) {
        ViewData.current_user = current_user;
        list1 = User.checkSalesRecordByID(current_user.UserID, Main.sales_file);
        list2 = User.checkPRRecordByID(current_user.UserID, Main.purchase_req_file);
        list3 = User.checkPORecordByID(current_user.UserID, Main.purchase_order_file);
        list4 = User.checkPYRecordByID(current_user.UserID, Main.payment_file);
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Data Transfer", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor;
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
        JLabel title = new JLabel("Please select the account and data to transfer.");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.1)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel userid_label = new JLabel("User ID:");
        userid_label.setOpaque(false);
        userid_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid_label, gbc);

        gbc.gridy = 2;
        JLabel username_label = new JLabel("Username:");
        username_label.setOpaque(false);
        username_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username_label, gbc);

        gbc.gridy = 3;
        JLabel type_label = new JLabel("Record Type:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 4;
        JLabel rec_label = new JLabel("Records:");
        rec_label.setOpaque(false);
        rec_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(rec_label, gbc);

        gbc.gridy = 5;
        gbc.weighty = 10;
        JLabel placeholder1 = new JLabel("");
        placeholder1.setOpaque(false);
        placeholder1.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(placeholder1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        gbc.insets = new Insets(0, 8, 0, 0);
        JLabel userid = new JLabel(current_user.UserID);
        userid.setOpaque(false);
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.9)));
        inner1.add(userid, gbc);
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(inner1, gbc);

        gbc.gridy = 2;
        JPanel inner2 = new JPanel(new GridBagLayout());
        inner2.setOpaque(true);
        inner2.setBackground(Color.WHITE);
        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        gbc.insets = new Insets(0, 8, 0, 0);
        JLabel username = new JLabel(current_user.Username);
        username.setOpaque(false);
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.9)));
        inner2.add(username, gbc);
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(inner2, gbc);

        gbc.gridy = 3;
        List<String> combo_data1 = new ArrayList<>();
        if (current_user.UserID.startsWith("SM")) {
            if (!User.checkSalesRecordByID(current_user.UserID, Main.sales_file).isEmpty()) {
                combo_data1.add("Sales Records");
            }
            if (!User.checkPRRecordByID(current_user.UserID, Main.purchase_req_file).isEmpty()) {
                combo_data1.add("Purchase Requisition Records");
            }
        } else if (current_user.UserID.startsWith("PM")) {
            combo_data1.add("Purchase Order Records");
        } else {
            combo_data1.add("Payment Records");
        }
        type_combo = new JComboBox<>(combo_data1.toArray());
        type_combo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.9)));
        type_combo.setFocusable(false);
        type_combo.addActionListener(_ -> record_list1.UpdateListContent(UpdateJList()));
        panel.add(type_combo, gbc);

        gbc.gridy = 4;
        gbc.gridheight = 2;
        record_list1 = new CustomComponents.CustomList<>(
                UpdateJList(),
                1,
                (int) (base_size * 0.9),
                merriweather,
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212)
        );
        CustomComponents.CustomScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(
                false, 1, record_list1,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );
        panel.add(scrollPane1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 2;
        JLabel placeholder6 = new JLabel("");
        placeholder6.setOpaque(false);
        button_panel.add(placeholder6, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(cancel, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = e.getComponent();
                SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
            }
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);
    }

    private static List<String> UpdateJList() {
        List<String> temp = new ArrayList<>();
        int counter = 1;
        if (type_combo.getSelectedItem() == "Sales Records") {
            for (Sales sales: list1) {
                temp.add(String.format("<html><b>%s.) Sales Record</b></html>", counter));
                temp.add(String.format("Sales ID:           %s", sales.SalesID));
                temp.add(String.format("Sales Date:       %s", sales.SalesDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Requisition Records") {
            for (PurchaseRequisition pr: list2) {
                temp.add(String.format("<html><b>%s.) Purchase Requisition Records</b></html>", counter));
                temp.add(String.format("Purchase Requisition ID:       %s", pr.PurchaseReqID));
                temp.add(String.format("Purchase Requisition Date:   %s", pr.ReqDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Order Records") {
            for (PurchaseOrder po: list3) {
                temp.add(String.format("<html><b>%s.) Purchase Order Records</b></html>", counter));
                temp.add(String.format("Purchase Order ID:           %s", po.PurchaseOrderID));
                temp.add(String.format("Purchase Order Date:       %s", po.OrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(String.format("Purchase Order Status:    %s", po.Status));
                temp.add(" ");
                counter += 1;
            }
        } else {
            for (Payment py: list4) {
                temp.add(String.format("<html><b>%s.) Payment Record</b></html>", counter));
                temp.add(String.format("Payment ID:                %s", py.PaymentID));
                temp.add(String.format("Payment Amount:     RM %s", py.Amount));
                temp.add(String.format("Payment Date:            %s", py.PaymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        }
        return temp;
    }
}