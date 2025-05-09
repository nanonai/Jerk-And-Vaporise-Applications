package Admin;

import Common.*;
import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;


public class UserMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Buffer current_user;
    private static JButton s_btn, p_left, p_right, p_first, p_last;
    private static CustomComponents.CustomButton all, fin, pur, inv, sls, view, add, modify, delete, data_transfer;
    private static JLabel lbl_show, lbl_entries, lbl_indicate;
    private static JComboBox<String> entries, pages;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomArrowIcon left_icon1, left_icon2, right_icon1, right_icon2;
    private static CustomComponents.CustomTable table_user;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static List<User> user_list;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Buffer current_user) {
        UserMng.parent = parent;
        UserMng.merriweather = merriweather;
        UserMng.boldonse = boldonse;
        UserMng.content = content;
        UserMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0);
        all = new CustomComponents.CustomButton("All user", merriweather,
                Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE,
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        all.addActionListener(_ -> {
            all.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            fin.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            pur.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            inv.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            sls.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            filter = 0;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(all, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        fin = new CustomComponents.CustomButton("Finance", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        fin.addActionListener(_ -> {
            all.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            fin.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            pur.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            inv.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            sls.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            filter = 1;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(fin, gbc);

        gbc.gridx = 2;
        pur = new CustomComponents.CustomButton("Purchase", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        pur.addActionListener(_ -> {
            all.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            fin.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            pur.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            inv.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            sls.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            filter = 2;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(pur, gbc);

        gbc.gridx = 3;
        inv = new CustomComponents.CustomButton("Inventory", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        inv.addActionListener(_ -> {
            all.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            fin.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            pur.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            inv.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            sls.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            filter = 3;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(inv, gbc);

        gbc.gridx = 4;
        sls = new CustomComponents.CustomButton("Sales", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        sls.addActionListener(_ -> {
            all.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            fin.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            pur.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172), Main.transparent);
            inv.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172), Main.transparent);
            sls.UpdateColor(Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Main.transparent);
            filter = 4;
            SearchStuff();
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
        });
        content.add(sls, gbc);

        gbc.gridx = 5;
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

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(6, 0, 10, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...\r\r", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search.addActionListener(_ -> SearchStuff());
        search_panel.add(search, ii_gbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 4;
        igbc.insets = new Insets(0, 3, 0, 3);
        String[] titles = new String[]{"Id", "Role", "Username", "Fullname", "Phone", "Email", "Date Joined"};
        user_list = User.listAllUser(Main.userdata_file);
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
        button_panel1.add(view, ii_gbc);

        ii_gbc.gridx = 1;
        add = new CustomComponents.CustomButton("Add User", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel1.add(add, ii_gbc);

        ii_gbc.gridx = 2;
        modify = new CustomComponents.CustomButton("Modify User", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel1.add(modify, ii_gbc);

        ii_gbc.gridx = 3;
        JLabel placeholder3 = new JLabel("");
        button_panel1.add(placeholder3, ii_gbc);

        ii_gbc.gridx = 4;
        data_transfer = new CustomComponents.CustomButton("Transfer User Data", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel2.add(data_transfer, ii_gbc);

        ii_gbc.gridx = 5;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        delete = new CustomComponents.CustomButton("Delete User", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        button_panel2.add(delete, ii_gbc);
    }

    public static void UpdateTable(int length, int page) {
        String[] titles = new String[]{"Id", "Role", "Username", "Fullname", "Phone", "Email", "Date Joined"};
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
        List<User> temp_user_list = User.listAllUserFromFilter(Main.userdata_file, temp, searcher);
        if (temp_user_list.isEmpty()) {
            CustomComponents.CustomDialog msg = new CustomComponents.CustomDialog(parent,
                    merriweather, 1);
            msg.show_dialog("Notification", "No results found.",
                    "Ok", null, null, null);
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
        fin.UpdateCustomButton(0, (int) (base_size), null, 0);
        pur.UpdateCustomButton(0, (int) (base_size), null, 0);
        inv.UpdateCustomButton(0, (int) (base_size), null, 0);
        sls.UpdateCustomButton(0, (int) (base_size), null, 0);
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
        delete.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
    }
}