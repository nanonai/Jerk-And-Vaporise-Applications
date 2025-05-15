package FinanceMgr;

import Common.CustomComponents;
import Common.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewPayment {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Payment current_payment;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Payment current_payment) {
        ViewPayment.parent = parent;
        ViewPayment.merriweather = merriweather;
        ViewPayment.boldonse = boldonse;
        ViewPayment.content = content;
        ViewPayment.current_payment = current_payment;
    }

    public static void UpdatePayment(Payment payment) {
        ViewPayment.current_payment = payment;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "View Payment Details", true);
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
        JLabel title = new JLabel("View Payment Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel payment_label = new JLabel("Payment ID :");
        payment_label.setOpaque(false);
        payment_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(payment_label, gbc);

        gbc.gridy = 2;
        JLabel purchaseID_label = new JLabel("PurchaseOrder ID :");
        purchaseID_label.setOpaque(false);
        purchaseID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseID_label, gbc);

        gbc.gridy = 3;
        JLabel amount_label = new JLabel("Amount :");
        amount_label.setOpaque(false);
        amount_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(amount_label, gbc);

        gbc.gridy = 4;
        JLabel date_label = new JLabel("Date :");
        date_label.setOpaque(false);
        date_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date_label, gbc);

        gbc.gridy = 5;
        JLabel status_label = new JLabel("Status :");
        status_label.setOpaque(false);
        status_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status_label, gbc);

        gbc.gridy = 7;
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
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("View Related Purchase order",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel paymentID = new JLabel("    " + current_payment.paymentID);
        paymentID.setOpaque(true);
        paymentID.setBackground(Color.WHITE);
        paymentID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        paymentID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(paymentID, gbc);

        gbc.gridy = 2;
        JLabel purchaseOrderID = new JLabel("    " + current_payment.purchaseOrderID);
        purchaseOrderID.setOpaque(true);
        purchaseOrderID.setBackground(Color.WHITE);
        purchaseOrderID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseOrderID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID, gbc);

        gbc.gridy = 3;
        JLabel amount = new JLabel("    " + current_payment.amount);
        amount.setOpaque(true);
        amount.setBackground(Color.WHITE);
        amount.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        amount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(amount, gbc);

        gbc.gridy = 4;
        JLabel date = new JLabel("    " + current_payment.date);
        date.setOpaque(true);
        date.setBackground(Color.WHITE);
        date.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        date.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(date, gbc);

        gbc.gridy = 5;
        JLabel status = new JLabel("    " + current_payment.status);
        status.setOpaque(true);
        status.setBackground(Color.WHITE);
        status.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        status.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status, gbc);

        gbc.gridy = 6;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

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
