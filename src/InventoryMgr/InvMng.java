package InventoryMgr;

import Admin.AddUser;
import Admin.DeleteUser;
import Admin.ViewUser;
import Admin.BufferForUser;
import Admin.CustomComponents;
import Admin.Main;
import Admin.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


public class InvMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static BufferForUser current_user;
    private static JButton s_btn, p_left, p_right, p_first, p_last, x_btn;
    private static CustomComponents.CustomButton all, view, add, modify,
            delete1, delete2, data_transfer;
    private static JLabel lbl_show, lbl_entries, lbl_indicate;
    private static JComboBox<String> entries, pages;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomArrowIcon left_icon1, left_icon2, right_icon1, right_icon2;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static CustomComponents.CustomTable table_user;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static boolean deleting = false;
    private static List<User> user_list;
    private static final Set<String> deleting_id = new LinkedHashSet<>();
    private static final Set<Integer> previousSelection = new HashSet<>();

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, BufferForUser current_user) {
        InvMng.parent = parent;
        InvMng.merriweather = merriweather;
        InvMng.boldonse = boldonse;
        InvMng.content = content;
        InvMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0);
        all = new CustomComponents.CustomButton("All Items", merriweather,
                Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE,
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        all.addActionListener(_ -> {
            all.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            filter = 0;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(all, gbc);

        gbc.gridx = 1;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 10);
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(0, 10, 10, 10);
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(10, 10, 0, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 0, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePages(list_length);
            page_counter = 0;
            UpdateTable(list_length, page_counter);
        });
        inner.add(entries, igbc);

        igbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, igbc);

        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 1;
        CustomComponents.RoundedPanel search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3,
                Color.BLACK, Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        s_btn.addActionListener(_ -> SearchStuff());
        search_panel.add(s_btn, ii_gbc);

        icon_clear1 = new CustomComponents.CustomXIcon(16, 3,
                new Color(209, 209, 209), true);
        icon_clear2 = new CustomComponents.CustomXIcon(16, 3,
                Color.BLACK, true);

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(6, 0, 8, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...\r\r", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!search.getText().isEmpty()) {
                    search.UpdateColumns(15);
                    ii_gbc.gridx = 2;
                    ii_gbc.insets = new Insets(6, 0, 8, 0);
                    x_btn = new JButton(icon_clear1);
                    x_btn.setRolloverIcon(icon_clear2);
                    x_btn.setFocusable(false);
                    x_btn.setBorderPainted(false);
                    x_btn.addActionListener(_ -> {
                        search.Reset();
                        search_panel.remove(x_btn);
                        search_panel.repaint();
                        search_panel.revalidate();
                    });
                    search_panel.add(x_btn, ii_gbc);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (search.getText().isEmpty()) {
                    search.UpdateColumns(20);
                    search_panel.remove(x_btn);
                    search_panel.repaint();
                    search_panel.revalidate();
                }
            }
        });
        search.addActionListener(_ -> SearchStuff());
        search_panel.add(search, ii_gbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 4;
        igbc.insets = new Insets(0, 3, 0, 3);
        String[] titles = new String[]{"Id", "Role", "Username", "Full Name", "Phone", "Email", "Date Joined"};
        user_list = User.listAllUserFromFilter(Main.userdata_file, "", "", current_user);
        Object[][] data = new Object[user_list.size()][titles.length];
        int counter = 0;
        for (User user : user_list) {
            data[counter] = new Object[]{user.UserID, user.AccType, user.Username,
                    user.FullName, user.Phone, user.Email, user.DateOfRegis.toString()};
            counter += 1;
        }

        table_user = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_user.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && deleting) {
                    SwingUtilities.invokeLater(() -> {
                        int[] selectedRows = table_user.getSelectedRows();
                        Set<Integer> currentSelection = new HashSet<>();
                        for (int row : selectedRows) {
                            currentSelection.add(row);
                        }
                        Set<Integer> newlySelected = new HashSet<>(currentSelection);
                        newlySelected.removeAll(previousSelection);
                        Set<Integer> deselected = new HashSet<>(previousSelection);
                        deselected.removeAll(currentSelection);
                        for (int row : newlySelected) {
                            deleting_id.add(table_user.getValueAt(row,
                                    table_user.getColumnModel().getColumnIndex("Id")).toString());
                        }
                        for (int row : deselected) {
                            deleting_id.remove(table_user.getValueAt(row,
                                    table_user.getColumnModel().getColumnIndex("Id")).toString());
                        }
                        delete2.setText(String.format("Delete User (%s)", deleting_id.size()));
                        previousSelection.clear();
                        previousSelection.addAll(currentSelection);
                    });
                }
            }
        });
        lbl_indicate = new JLabel("");
        pages = new JComboBox<>();
        UpdateTable(list_length, page_counter);
        UpdatePages(list_length);
        table_user.setShowHorizontalLines(true);
        table_user.setShowVerticalLines(true);
        table_user.setGridColor(new Color(230, 230, 230));

        CustomComponents.CustomScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_user,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 2, 0);
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));
        inner.add(lbl_indicate, igbc);

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 2, 5);
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        inner.add(page_panel, igbc);

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(0);
            UpdateTable(list_length, page_counter);
            previousSelection.clear();
            SwingUtilities.invokeLater(() -> {
                RememberDeletion(deleting_id, table_user);
            });
        });
        p_first.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_first.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_first);
                if (p_first.contains(point)) {
                    p_first.setForeground(Color.BLACK);
                } else {
                    p_first.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_first, ii_gbc);

        ii_gbc.gridx = 1;
        left_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), true);
        left_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, true);
        p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter -= 1;
                pages.setSelectedIndex(page_counter);
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
                SwingUtilities.invokeLater(() -> {
                    RememberDeletion(deleting_id, table_user);
                });
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.addActionListener(e -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
                SwingUtilities.invokeLater(() -> {
                    RememberDeletion(deleting_id, table_user);
                });
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        right_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), false);
        right_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, false);
        p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter += 1;
                pages.setSelectedIndex(page_counter);
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
                SwingUtilities.invokeLater(() -> {
                    RememberDeletion(deleting_id, table_user);
                });
            }
        });
        page_panel.add(p_right, ii_gbc);

        ii_gbc.gridx = 4;
        p_last = new JButton("Last");
        p_last.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_last.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_last.setForeground(new Color(122, 122, 122));
        p_last.addActionListener(_ -> {
            page_counter = pages.getItemCount() - 1;
            pages.setSelectedIndex(page_counter);
            UpdateTable(list_length, page_counter);
            previousSelection.clear();
            SwingUtilities.invokeLater(() -> {
                RememberDeletion(deleting_id, table_user);
            });
        });
        p_last.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_last.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_last);
                if (p_last.contains(point)) {
                    p_last.setForeground(Color.BLACK);
                } else {
                    p_last.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_last, ii_gbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 5, 10, 0);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 10, 5);
        JPanel button_panel2 = new JPanel(new GridBagLayout());
        button_panel2.setOpaque(false);
        inner.add(button_panel2, igbc);

        ii_gbc.gridx = 0;
        ii_gbc.insets = new Insets(0, 0, 0, 4);
        view = new CustomComponents.CustomButton("View Details", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        view.addActionListener(_ -> {
            if (table_user.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select an account to view!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_user.getValueAt(table_user.getSelectedRow(),
                        table_user.getColumnModel().getColumnIndex("Id")).toString();
                ViewUser.UpdateUser(User.GetUserById(selected_id, Main.userdata_file));
                boolean see = ViewUser.ShowPage();
                if (see) {
                    System.out.println("Skibidi");
                }
            }
        });
        button_panel1.add(view, ii_gbc);

        ii_gbc.gridx = 1;
        add = new CustomComponents.CustomButton("Add Item", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        add.addActionListener(_ -> {
            AddItem.ShowPage();
        });
        button_panel1.add(add, ii_gbc);

        ii_gbc.gridx = 2;
        modify = new CustomComponents.CustomButton("Modify Item", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel1.add(modify, ii_gbc);

        ii_gbc.gridx = 3;
        JLabel placeholder3 = new JLabel("");
        button_panel1.add(placeholder3, ii_gbc);

        ii_gbc.gridx = 4;
        data_transfer = new CustomComponents.CustomButton("Transfer Item Data", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel2.add(data_transfer, ii_gbc);

        ii_gbc.gridx = 5;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        delete1 = new CustomComponents.CustomButton("Delete Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        delete1.addActionListener(_ -> {
            deleting = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter delete selection mode?",
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
            if (deleting) {
                view.setEnabled(false);
                add.setEnabled(false);
                modify.setEnabled(false);
                data_transfer.setEnabled(false);
                view.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(242, 242, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                add.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(241, 241, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                modify.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(241, 241, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                data_transfer.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(241, 241, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                table_user.SetColors(Color.BLACK, Color.BLACK, Color.WHITE, new Color(255, 203, 205));
                mode = 0;
                table_user.SetChanges(merriweather.deriveFont(Font.BOLD, 18),
                        merriweather.deriveFont(Font.PLAIN, 16), mode);
                scrollPane1.UpdateBorder(3, new Color(159, 4, 4), Main.transparent,
                        Main.transparent, Main.transparent, Main.transparent);
                button_panel2.remove(delete1);
                ii_gbc.gridx = 5;
                ii_gbc.insets = new Insets(0, 0, 0, 0);
                button_panel2.add(delete2, ii_gbc);
                button_panel2.revalidate();
                button_panel2.repaint();
                int base_size = 0;
                if (parent.getWidth() >= parent.getHeight()) {
                    base_size = parent.getHeight() / 40;
                } else {
                    base_size = parent.getWidth() / 30;
                }
                delete2.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
            }
        });
        button_panel2.add(delete1, ii_gbc);

        delete2 = new CustomComponents.CustomButton("Delete Item (0)", merriweather, Color.WHITE, Color.WHITE,
                new Color(159, 4, 4), new Color(161, 40, 40), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        delete2.addActionListener(_ -> {
            List<String> ids = new ArrayList<>(deleting_id);
            List<User> d_users = User.GetUsersByIds(ids, Main.userdata_file);
            DeleteUser.UpdateUsers(d_users);
            boolean delete = DeleteUser.ShowPage();
            if (delete) {
                deleting = false;
                for (User user: d_users) {
                    User.removeUser(user.UserID, Main.userdata_file);
                }
                user_list = User.listAllUser(Main.userdata_file);
                deleting_id.clear();
                view.setEnabled(true);
                add.setEnabled(true);
                modify.setEnabled(true);
                data_transfer.setEnabled(true);
                view.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                        new Color(225, 108, 150), new Color(237, 136, 172),
                        Main.transparent);
                add.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                        new Color(209, 88, 128), new Color(237, 136, 172),
                        Main.transparent);
                modify.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                        new Color(225, 108, 150), new Color(237, 136, 172),
                        Main.transparent);
                data_transfer.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                        new Color(209, 88, 128), new Color(237, 136, 172),
                        Main.transparent);
                table_user.SetColors(Color.BLACK, Color.BLACK, Color.WHITE, new Color(212, 212, 212));
                mode = 1;
                table_user.SetChanges(merriweather.deriveFont(Font.BOLD, 18),
                        merriweather.deriveFont(Font.PLAIN, 16), mode);
                scrollPane1.UpdateBorder(1, new Color(202, 202, 202), Main.transparent,
                        Main.transparent, Main.transparent, Main.transparent);
                filter = 0;
                page_counter = 0;
                UpdatePages(list_length);
                pages.setSelectedIndex(0);
                UpdateTable(list_length, 0);
                button_panel2.remove(delete2);
                ii_gbc.gridx = 5;
                ii_gbc.insets = new Insets(0, 0, 0, 0);
                button_panel2.add(delete1, ii_gbc);
                button_panel2.revalidate();
                button_panel2.repaint();
                int base_size = 0;
                if (parent.getWidth() >= parent.getHeight()) {
                    base_size = parent.getHeight() / 40;
                } else {
                    base_size = parent.getWidth() / 30;
                }
                delete1.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
            }
        });

        AddUser.Loader(parent, merriweather, boldonse, content, current_user);
        ViewUser.Loader(parent, merriweather, boldonse, content, null);
        DeleteUser.Loader(parent, merriweather, boldonse, content, current_user);
    }

    public static void UpdateTable(int length, int page) {
        String[] titles = new String[]{"Id", "Name", "Price/Unit", "Quantity", "Min Quantity", "Category", "SupplierID"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= user_list.size() - page * length) {
            data = new Object[user_list.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (User user : user_list) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
                data[counter] = new Object[]{user.UserID, user.AccType, user.Username,
                        user.FullName, user.Phone, user.Email, user.DateOfRegis.toString()};
                counter += 1;
                if (counter == length || counter == user_list.size()) { break; }
            }
        }
        table_user.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= user_list.size()) {
            lbl_indicate.setText(String.format(temp2, (!user_list.isEmpty()) ? 1 : 0, user_list.size(),
                    user_list.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, user_list.size()),
                    user_list.size()));
        }
    }

    public static void RememberDeletion(Set<String> deleting_id, CustomComponents.CustomTable table) {
        if (deleting) {
            int rowCount = table.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                Object value = table.getValueAt(i, table.getColumnModel().getColumnIndex("Id"));
                if (value != null && deleting_id.contains(value.toString())) {
                    table.addRowSelectionInterval(i, i);
                }
            }
            table.revalidate();
            table.repaint();
        }
    }

    public static void UpdatePages(int length) {
        int pageCount = (int) Math.ceil(user_list.size() / (double) length);
        if (user_list.size() <= length) {
            pageCount = 1;
        }
        pages.removeAllItems();
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }

    public static void SearchStuff() {
        String searcher = (!search.getText().isEmpty() && !Objects.equals(search.getText(), "Search...\r\r")) ?
                search.getText() : "";
        String temp = switch (filter) {
            case 1 -> "Finance Manager";
            case 2 -> "Purchase Manager";
            case 3 -> "Inventory Manager";
            case 4 -> "Sales Manager";
            default -> "";
        };
        List<User> temp_user_list = User.listAllUserFromFilter(Main.userdata_file, temp, searcher, current_user);
        if (temp_user_list.isEmpty()) {
            CustomComponents.CustomOptionPane.showInfoDialog(
                    parent,
                    "No results found.",
                    "Notification",
                    new Color(88, 149, 209),
                    new Color(255, 255, 255),
                    new Color(125, 178, 228),
                    new Color(255, 255, 255)
            );
        } else {
            user_list = temp_user_list;
            page_counter = 0;
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
            SwingUtilities.invokeLater(table_user::requestFocusInWindow);
        }
    }

    public static void UpdateComponentSize(int base_size) {
        search_icon1.UpdateSize((int) (base_size * 0.8));
        search_icon2.UpdateSize((int) (base_size * 0.8));
        s_btn.setSize(search_icon1.getIconWidth(), search_icon1.getIconHeight());
        s_btn.repaint();
        left_icon1.UpdateSize(base_size);
        left_icon2.UpdateSize(base_size);
        p_left.setSize(left_icon1.getIconWidth(), left_icon1.getIconHeight());
        p_left.repaint();
        right_icon1.UpdateSize(base_size);
        right_icon2.UpdateSize(base_size);
        p_right.setSize(right_icon1.getIconWidth(), right_icon1.getIconHeight());
        p_right.repaint();
        p_first.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        p_last.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        all.UpdateCustomButton(0, (int) (base_size), null, 0);
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        search.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        table_user.SetChanges(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.95)),
                merriweather.deriveFont(Font.PLAIN, (int) (base_size * 0.9)), mode);
        view.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        add.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        modify.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        data_transfer.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        if (!deleting) {
            delete1.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        } else {
            delete2.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        }
    }
}