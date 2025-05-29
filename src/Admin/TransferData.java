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

public class TransferData {
    private static JFrame parent;
    private static Font merriweather;
    private static User current_user;
    private static JComboBox<Object> user_combo, type_combo;
    private static CustomComponents.CustomList<String> record_list1, record_list2;
    private static List<User> filter;
    private static List<Sales> left_list1;
    private static List<PurchaseRequisition> left_list2;
    private static List<PurchaseOrder> left_list3;
    private static List<Payment> left_list4;
    private static List<Object> right_list = new ArrayList<>();
    private static int partition = 0;

    public static void Loader(JFrame parent, Font merriweather) {
        TransferData.parent = parent;
        TransferData.merriweather = merriweather;
    }

    public static void UpdateUser(User current_user) {
        TransferData.current_user = current_user;
        filter = User.ListAllUserFromFilter(Main.userdata_file, current_user.AccType, "", current_user);
        left_list1 = User.checkSalesRecordByID(current_user.UserID, Main.sales_file);
        left_list2 = User.checkPRRecordByID(current_user.UserID, Main.purchase_req_file);
        left_list3 = User.checkPORecordByID(current_user.UserID, Main.purchase_order_file);
        left_list4 = User.checkPYRecordByID(current_user.UserID, Main.payment_file);
        right_list = new ArrayList<>();
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "Data Transfer", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize((int) (parent.getWidth() / 1.2), (int) (parent.getHeight() / 1.2));
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
        gbc.gridwidth = 4;
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

        gbc.gridy = 6;
        gbc.weighty = 1;
        JLabel placeholder2 = new JLabel("");
        placeholder2.setOpaque(false);
        placeholder2.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(placeholder2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        gbc.insets = new Insets(0, 8, 0, 0);
        JLabel userid = new JLabel(current_user.UserID);
        userid.setOpaque(false);
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.9)));
        inner1.add(userid, gbc);
        gbc.insets = new Insets(0, 10, 10, 0);
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
        gbc.insets = new Insets(0, 10, 10, 0);
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
        type_combo.addActionListener(_ -> record_list1.UpdateListContent(UpdateLeftJList()));
        panel.add(type_combo, gbc);

        gbc.gridy = 4;
        gbc.gridheight = 2;
        record_list1 = new CustomComponents.CustomList<>(
                UpdateLeftJList(),
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

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 2;
        JLabel placeholder3 = new JLabel("");
        placeholder3.setOpaque(false);
        panel.add(placeholder3, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1;
        JButton to_right = new JButton(">>>");
        to_right.setFocusable(false);
        to_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        to_right.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size)));
        to_right.addActionListener(_ -> {
            if ((type_combo.getSelectedItem() == "Sales Records" && left_list1.isEmpty()) ||
                    (type_combo.getSelectedItem() == "Purchase Requisition Records" && left_list2.isEmpty()) ||
                    (type_combo.getSelectedItem() == "Purchase Order Records" && left_list3.isEmpty()) ||
                    (type_combo.getSelectedItem() == "Payment Records" && left_list4.isEmpty())) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "No more record to transfer from the list!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else if (record_list1.getSelectedIndices().length == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a record to transfer from the list!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                MoveToRight();
            }
        });
        panel.add(to_right, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.gridheight = 6;
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(new Color(0, 0, 0));
        panel.add(separator, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        JLabel transfer_label = new JLabel("Transfer to this account:");
        transfer_label.setOpaque(false);
        transfer_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(transfer_label, gbc);

        gbc.gridy = 2;
        gbc.gridheight = 2;
        List<String> combo_data2 = new ArrayList<>();
        for (User user: filter) {
            combo_data2.add(String.format("<html>User ID:&emsp;&emsp;&emsp;&emsp;&nbsp;<b>%s</b><br>" +
                    "Username:&emsp;&emsp;&emsp;<b>%s</b></html>", user.UserID, user.Username));
        }
        user_combo = new JComboBox<>(combo_data2.toArray());
        user_combo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.9)));
        user_combo.setFocusable(false);
        panel.add(user_combo, gbc);

        gbc.gridy = 4;
        record_list2 = new CustomComponents.CustomList<>(
                new ArrayList<>(),
                1,
                (int) (base_size * 0.9),
                merriweather,
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212)
        );
        CustomComponents.CustomScrollPane scrollPane2 = new CustomComponents.CustomScrollPane(
                false, 1, record_list2,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );
        panel.add(scrollPane2, gbc);

        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JButton to_left = new JButton("<<<");
        to_left.setFocusable(false);
        to_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        to_left.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size)));
        to_left.addActionListener(_ -> {
            if (right_list.isEmpty()) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "No more record to remove from the list!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else if (record_list2.getSelectedIndices().length == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a record to remove from the list!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                MoveToLeft();
            }
        });
        panel.add(to_left, gbc);

        gbc.gridx = 5;
        gbc.weightx = 5;
        JLabel placeholder5 = new JLabel("");
        placeholder5.setOpaque(false);
        panel.add(placeholder5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 6;
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(cancel, gbc);

        gbc.gridx = 1;
        JLabel placeholder6 = new JLabel("");
        placeholder6.setOpaque(false);
        button_panel.add(placeholder6, gbc);

        gbc.gridx = 2;
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm Transfer",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(confirm, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        confirm.addActionListener(_ -> {
            if (right_list.isEmpty()) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "There is no record in the list to transfer!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                boolean save = SaveModification();
                if (save) {
                    dialog.dispose();
                }
            }
        });

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

    private static List<String> UpdateLeftJList() {
        List<String> temp = new ArrayList<>();
        int counter = 1;
        if (type_combo.getSelectedItem() == "Sales Records") {
            partition = 4;
            for (Sales sales: left_list1) {
                temp.add(String.format("<html><b>%s.) Sales Record</b></html>", counter));
                temp.add(String.format("Sales ID:           %s", sales.SalesID));
                temp.add(String.format("Sales Date:       %s", sales.SalesDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Requisition Records") {
            partition = 4;
            for (PurchaseRequisition pr: left_list2) {
                temp.add(String.format("<html><b>%s.) Purchase Requisition Records</b></html>", counter));
                temp.add(String.format("Purchase Requisition ID:       %s", pr.PurchaseReqID));
                temp.add(String.format("Purchase Requisition Date:   %s", pr.ReqDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Order Records") {
            partition = 5;
            for (PurchaseOrder po: left_list3) {
                temp.add(String.format("<html><b>%s.) Purchase Order Records</b></html>", counter));
                temp.add(String.format("Purchase Order ID:           %s", po.PurchaseOrderID));
                temp.add(String.format("Purchase Order Date:       %s", po.OrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(String.format("Purchase Order Status:    %s", po.Status));
                temp.add(" ");
                counter += 1;
            }
        } else {
            partition = 5;
            for (Payment py: left_list4) {
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

    private static void MoveToRight() {
        int selection = record_list1.getSelectedIndex() / partition;
        if (type_combo.getSelectedItem() == "Sales Records") {
            Sales item = left_list1.get(selection);
            left_list1.remove(selection);
            right_list.add(item);
        } else if (type_combo.getSelectedItem() == "Purchase Requisition Records") {
            PurchaseRequisition item = left_list2.get(selection);
            left_list2.remove(selection);
            right_list.add(item);
        } else if (type_combo.getSelectedItem() == "Purchase Order Records") {
            PurchaseOrder item = left_list3.get(selection);
            left_list3.remove(selection);
            right_list.add(item);
        } else {
            Payment item = left_list4.get(selection);
            left_list4.remove(selection);
            right_list.add(item);
        }
        List<String> temp = new ArrayList<>();
        int counter = 1;
        for (Object object: right_list) {
            if (object instanceof Sales sales) {
                temp.add(String.format("<html><b>%s.) Sales Record</b></html>", counter));
                temp.add(String.format("Sales ID:           %s", sales.SalesID));
                temp.add(String.format("Sales Date:       %s", sales.SalesDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof PurchaseRequisition pr) {
                temp.add(String.format("<html><b>%s.) Purchase Requisition Records</b></html>", counter));
                temp.add(String.format("Purchase Requisition ID:       %s", pr.PurchaseReqID));
                temp.add(String.format("Purchase Requisition Date:   %s", pr.ReqDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof PurchaseOrder po) {
                temp.add(String.format("<html><b>%s.) Purchase Order Records</b></html>", counter));
                temp.add(String.format("Purchase Order ID:           %s", po.PurchaseOrderID));
                temp.add(String.format("Purchase Order Date:       %s", po.OrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(String.format("Purchase Order Status:    %s", po.Status));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof Payment py) {
                temp.add(String.format("<html><b>%s.) Payment Record</b></html>", counter));
                temp.add(String.format("Payment ID:                %s", py.PaymentID));
                temp.add(String.format("Payment Amount:     RM %s", py.Amount));
                temp.add(String.format("Payment Date:            %s", py.PaymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        }
        record_list1.UpdateListContent(UpdateLeftJList());
        record_list2.UpdateListContent(temp);
    }

    private static void MoveToLeft() {
        int selection = record_list2.getSelectedIndex() / partition;
        Object item = right_list.get(selection);
        right_list.remove(selection);
        if (item instanceof Sales i) {
            int index = 0;
            for (Sales sls: left_list1) {
                if (Integer.parseInt(i.SalesID.substring(2)) > Integer.parseInt(sls.SalesID.substring(2))) {
                    index += 1;
                } else {
                    break;
                }
            }
            left_list1.add(index, i);
            type_combo.setSelectedItem("Sales Records");
        } else if (item instanceof PurchaseRequisition i) {
            int index = 0;
            for (PurchaseRequisition pr: left_list2) {
                if (Integer.parseInt(i.PurchaseReqID.substring(2)) > Integer.parseInt(pr.PurchaseReqID.substring(2))) {
                    index += 1;
                } else {
                    break;
                }
            }
            left_list2.add(index, i);
            type_combo.setSelectedItem("Purchase Requisition Records");
        } else if (item instanceof PurchaseOrder i) {
            int index = 0;
            for (PurchaseOrder po: left_list3) {
                if (Integer.parseInt(i.PurchaseOrderID.substring(2)) > Integer.parseInt(po.PurchaseOrderID.substring(2))) {
                    index += 1;
                } else {
                    break;
                }
            }
            left_list3.add(index, i);
            type_combo.setSelectedItem("Purchase Order Records");
        } else if (item instanceof Payment i) {
            int index = 0;
            for (Payment py: left_list4) {
                if (Integer.parseInt(i.PaymentID.substring(2)) > Integer.parseInt(py.PaymentID.substring(2))) {
                    index += 1;
                } else {
                    break;
                }
            }
            left_list4.add(index, i);
            type_combo.setSelectedItem("Payment Records");
        }
        List<String> temp = new ArrayList<>();
        int counter = 1;
        for (Object object: right_list) {
            if (object instanceof Sales sales) {
                temp.add(String.format("<html><b>%s.) Sales Record</b></html>", counter));
                temp.add(String.format("Sales ID:           %s", sales.SalesID));
                temp.add(String.format("Sales Date:       %s", sales.SalesDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof PurchaseRequisition pr) {
                temp.add(String.format("<html><b>%s.) Purchase Requisition Records</b></html>", counter));
                temp.add(String.format("Purchase Requisition ID:       %s", pr.PurchaseReqID));
                temp.add(String.format("Purchase Requisition Date:   %s", pr.ReqDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof PurchaseOrder po) {
                temp.add(String.format("<html><b>%s.) Purchase Order Records</b></html>", counter));
                temp.add(String.format("Purchase Order ID:           %s", po.PurchaseOrderID));
                temp.add(String.format("Purchase Order Date:       %s", po.OrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(String.format("Purchase Order Status:    %s", po.Status));
                temp.add(" ");
                counter += 1;
            } else if (object instanceof Payment py) {
                temp.add(String.format("<html><b>%s.) Payment Record</b></html>", counter));
                temp.add(String.format("Payment ID:                %s", py.PaymentID));
                temp.add(String.format("Payment Amount:     RM %s", py.Amount));
                temp.add(String.format("Payment Date:            %s", py.PaymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                temp.add(" ");
                counter += 1;
            }
        }
        record_list1.UpdateListContent(UpdateLeftJList());
        record_list2.UpdateListContent(temp);
    }

    private static boolean SaveModification() {
        User selected = filter.get(user_combo.getSelectedIndex());
        boolean save = CustomComponents.CustomOptionPane.showConfirmDialog(
                parent,
                String.format("<html>Transfer all data in the list to the account below?<br>" +
                        "User ID:&emsp;&emsp;&emsp;&emsp;&nbsp;<b>%s</b><br>" +
                        "Username:&emsp;&emsp;&emsp;<b>%s</b></html>", selected.UserID, selected.Username),
                "Confirmation",
                new Color(159, 4, 4),
                new Color(255, 255, 255),
                new Color(161, 40, 40),
                new Color(255, 255, 255),
                new Color(56, 53, 70),
                new Color(255, 255, 255),
                new Color(73, 69, 87),
                new Color(255, 255, 255),
                false
        );
        if (save) {
            for (Object item: right_list) {
                if (item instanceof Sales sales) {
                    sales.SalesMgrID = selected.UserID;
                    Sales.ModifySales(sales.SalesID, sales, Main.sales_file);
                } else if (item instanceof PurchaseRequisition pr) {
                    pr.SalesMgrID = selected.UserID;
                    PurchaseRequisition.ModifyPurchaseRequisition(pr.PurchaseReqID, pr, Main.purchase_req_file);
                } else if (item instanceof PurchaseOrder po) {
                    po.PurchaseMgrID = selected.UserID;
                    PurchaseOrder.ModifyPurchaseOrder(po.PurchaseOrderID, po, Main.purchase_order_file);
                } else if (item instanceof Payment py) {
                    py.FinanceMgrID = selected.UserID;
                    Payment.ModifyPayment(py.PaymentID, py, Main.payment_file);
                }
            }
        }
        return save;
    }
}