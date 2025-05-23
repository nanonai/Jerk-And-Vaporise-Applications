package PurchaseMgr;

import Admin.CustomComponents;
import Admin.Main;
import FinanceMgr.PurchaseRequisition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static PurchaseMgr.ViewPurchaseRequisition.*;

public class PurchaseRequisitionDetails {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    public static PurchaseRequisition current_pr;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, PurchaseRequisition current_pr) {
        PurchaseRequisitionDetails.parent = parent;
        PurchaseRequisitionDetails.merriweather = merriweather;
        PurchaseRequisitionDetails.boldonse = boldonse;
        PurchaseRequisitionDetails.content = content;
        PurchaseRequisitionDetails.current_pr = current_pr;
    }

    public static void UpdatePurchaseRequisition(PurchaseRequisition purchaseRequisition) {
        PurchaseRequisitionDetails.current_pr = purchaseRequisition;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Purchase Requisition", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
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
        JLabel title = new JLabel("Details of Purchase Requisition");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel purchaseOrderID_label = new JLabel("Purchase Requisition ID:");
        purchaseOrderID_label.setOpaque(false);
        purchaseOrderID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID_label, gbc);

        gbc.gridy = 2;
        JLabel itemID_label = new JLabel("Item ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 3;
        JLabel supplierID_label = new JLabel("Supplier ID:");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Purchase Quantity:");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel totalAmt_label = new JLabel("Requisition Date:");
        totalAmt_label.setOpaque(false);
        totalAmt_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("Manager ID:");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

        gbc.gridy = 7;
        JLabel managerID_label = new JLabel("Status:");
        managerID_label.setOpaque(false);
        managerID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(managerID_label, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(close, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        button_panel.add(placeholder, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("Add to Purchase Order",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        view.addActionListener(_ -> {
            int selectedRow = table_pr.getSelectedRow();

            if (selectedRow != -1) {
                String selectedPRID = table_pr.getValueAt(selectedRow, 0).toString();
                String status = getStatusFromFile(selectedPRID); // get PR status

                if ("1".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(parent,
                            "This PR has been generated.",
                            "Cannot Add",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        // Generate new Purchase Order ID
                        String newPurchaseOrderID = PurchaseOrder.idMaker(Main.purchaseOrder_file);

                        // Extract PR data from table row
                        String itemID = table_pr.getValueAt(selectedRow, 1).toString();
                        String supplierID = table_pr.getValueAt(selectedRow, 2).toString();
                        int purchaseQuantity = Integer.parseInt(table_pr.getValueAt(selectedRow, 3).toString());
                        double totalAmt = 0;
                        LocalDate orderDate = LocalDate.parse(table_pr.getValueAt(selectedRow, 4).toString());
                        String purchaseMgrID = table_pr.getValueAt(selectedRow, 5).toString();
                        String poStatus = "Pending";

                        // Create new PurchaseOrder object
                        PurchaseOrder newPO = new PurchaseOrder(
                                newPurchaseOrderID,
                                itemID,
                                supplierID,
                                purchaseQuantity,
                                totalAmt,
                                orderDate,
                                purchaseMgrID,
                                poStatus
                        );
                        // Save PO to file
                        PurchaseOrder.savePurchaseOrder(newPO, Main.purchaseOrder_file, parent);

                        // Update PR status to "1"
                        updatePRStatus(selectedPRID, "1", Main.purchaseReq_file);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(parent,
                                "Invalid number format in the selected row.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent,
                                "An error occurred:\n" + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PR to generate a PO.",
                        "Error!",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            }
        });

        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel userid = new JLabel("    " + current_pr.PurchaseReqID);
        userid.setOpaque(true);
        userid.setBackground(Color.WHITE);
        userid.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        userid.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid, gbc);

        gbc.gridy = 2;
        JLabel type = new JLabel("    " + current_pr.ItemID);
        type.setOpaque(true);
        type.setBackground(Color.WHITE);
        type.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        type.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type, gbc);

        gbc.gridy = 3;
        JLabel username = new JLabel("    " + current_pr.SupplierID);
        username.setOpaque(true);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username, gbc);

        gbc.gridy = 4;
        JLabel fullname = new JLabel("    " + current_pr.Quantity);
        fullname.setOpaque(true);
        fullname.setBackground(Color.WHITE);
        fullname.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        fullname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(fullname, gbc);

        gbc.gridy = 5;
        JLabel email = new JLabel("    " + current_pr.ReqDate);
        email.setOpaque(true);
        email.setBackground(Color.WHITE);
        email.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email, gbc);

        gbc.gridy = 6;
        JLabel email1 = new JLabel("    " + current_pr.SalesMgrID);
        email1.setOpaque(true);
        email1.setBackground(Color.WHITE);
        email1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        email1.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(email1, gbc);

        String statusText;
        if (current_pr.ReqDate.isBefore(LocalDate.now())) {
            statusText = "Overdue";
        } else {
            if (current_pr.Status == 1){
                statusText = "Generated";
            } else {
                statusText = "Pending";
            }
        }

        gbc.gridy = 7;
        JLabel date = new JLabel("    " + statusText);
        date.setOpaque(true);
        date.setBackground(Color.WHITE);
        date.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date, gbc);


        /*gbc.gridy = 9;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel code = new JLabel("    +60");
        code.setOpaque(true);
        code.setBackground(Color.WHITE);
        code.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        code.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        inner.add(code, gbc);

        gbc.gridx = 1;
        gbc.weightx = 8;
        JLabel phone = new JLabel("    " + current_user.Phone.substring(1));
        phone.setOpaque(true);
        phone.setBackground(Color.WHITE);
        phone.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        phone.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        inner.add(phone, gbc);*/

        close.addActionListener(_ -> {
            dialog.dispose();
        });

        view.addActionListener(_ -> {
            view_or_not.set(true);
            dialog.dispose();
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

        return view_or_not.get();
    }
}