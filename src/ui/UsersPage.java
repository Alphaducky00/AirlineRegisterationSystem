package ui;

import util.Connect;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersPage extends JPanel {

    private JTable usersTable;
    private Object[][] data;
    private String[] columns = {"User ID", "Name", "Email", "Phone"};
    private Connect connection;

    public UsersPage() {
        setLayout(null);
        setBackground(new Color(245, 245, 245));

        connection = new Connect();
        connection.connect();

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBounds(20, 0, 600, 40);

        JButton addBtn = new JButton("Add User");
        JButton editBtn = new JButton("Edit User");
        JButton deleteBtn = new JButton("Delete User");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(deleteBtn);
        add(buttonsPanel);

        data = fetchUsersData();
        usersTable = new JTable(data, columns);
        usersTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(20, 50, 600, 400);
        add(scrollPane);

        addBtn.addActionListener(e -> showUserForm(null));
        editBtn.addActionListener(e -> {
            int selected = usersTable.getSelectedRow();
            if (selected >= 0) {
                Object[] userData = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    userData[i] = usersTable.getValueAt(selected, i);
                }
                showUserForm(userData);
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to edit.");
            }
        });
        deleteBtn.addActionListener(e -> {
            int selected = usersTable.getSelectedRow();
            if (selected >= 0) {
                int userId = (int) usersTable.getValueAt(selected, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete user ID " + userId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) deleteUser(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to delete.");
            }
        });
    }

    private void showUserForm(Object[] userData) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "User Form", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));

        JTextField nameField = new JTextField(userData != null ? userData[1].toString() : "");
        JTextField emailField = new JTextField(userData != null ? userData[2].toString() : "");
        JTextField phoneField = new JTextField(userData != null ? userData[3].toString() : "");

        dialog.add(new JLabel("Name:")); dialog.add(nameField);
        dialog.add(new JLabel("Email:")); dialog.add(emailField);
        dialog.add(new JLabel("Phone:")); dialog.add(phoneField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        dialog.add(saveBtn);
        dialog.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.");
                return;
            }

            try {
                if (userData == null) addUser(name, email, phone);
                else updateUser((int) userData[0], name, email, phone);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addUser(String name, String email, String phone) throws Exception {
        String query = "INSERT INTO users (name, email, phone) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.executeUpdate();
        refreshTable();
    }

    private void updateUser(int id, String name, String email, String phone) throws Exception {
        String query = "UPDATE users SET name=?, email=?, phone=? WHERE user_id=?";
        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.setInt(4, id);
        ps.executeUpdate();
        refreshTable();
    }

    private void deleteUser(int id) {
        try {
            String query = "DELETE FROM users WHERE user_id=?";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete user: " + e.getMessage());
        }
    }

    private Object[][] fetchUsersData() {
        try {
            String query = "SELECT * FROM users";
            PreparedStatement ps = connection.getConnection()
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] users = new Object[rowCount][4];
            int i = 0;
            while (rs.next()) {
                users[i][0] = rs.getInt("user_id");
                users[i][1] = rs.getString("name");
                users[i][2] = rs.getString("email");
                users[i][3] = rs.getString("phone");
                i++;
            }
            return users;
        } catch (Exception e) {
            System.out.println("Failed to fetch users: " + e.getMessage());
            return new Object[0][4];
        }
    }

    private void refreshTable() {
        data = fetchUsersData();
        usersTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    public void refreshData() {
        refreshTable();
    }
}
