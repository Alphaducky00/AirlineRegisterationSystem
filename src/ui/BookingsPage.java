package ui;

import util.Connect;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookingsPage extends JPanel {

    private JTable bookingsTable;
    private Object[][] data;
    private String[] columns = {"Booking ID", "User", "Flight", "Seat Number", "Booking Date", "Passenger Name", "Passenger Age"};

    private Connect connection;

    private ArrayList<Integer> userIds = new ArrayList<>();
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayList<Integer> flightIds = new ArrayList<>();
    private ArrayList<String> flightNames = new ArrayList<>();

    public BookingsPage() {
        setLayout(null);
        setBackground(new Color(245, 245, 245));

        connection = new Connect();
        connection.connect();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBounds(20, 0, 1000, 40);

        JButton addBtn = new JButton("Add Booking");
        JButton editBtn = new JButton("Edit Booking");
        JButton deleteBtn = new JButton("Delete Booking");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(deleteBtn);
        add(buttonsPanel);

        data = fetchBookingsData();
        bookingsTable = new JTable(data, columns);
        bookingsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBounds(20, 50, 950, 400);
        add(scrollPane);

        addBtn.addActionListener(e -> showBookingForm(null));
        editBtn.addActionListener(e -> {
            int selected = bookingsTable.getSelectedRow();
            if (selected >= 0) {
                Object[] bookingData = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    bookingData[i] = bookingsTable.getValueAt(selected, i);
                }
                showBookingForm(bookingData);
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to edit.");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int selected = bookingsTable.getSelectedRow();
            if (selected >= 0) {
                int bookingId = (int) bookingsTable.getValueAt(selected, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete booking ID " + bookingId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) deleteBooking(bookingId);
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to delete.");
            }
        });
    }

    private void showBookingForm(Object[] bookingData) {
        loadUsers();
        loadFlights();

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Booking Form", true);
        dialog.setSize(450, 400);
        dialog.setLayout(new GridLayout(7, 2, 5, 5));

        JComboBox<String> userDropdown = new JComboBox<>(userNames.toArray(new String[0]));
        JComboBox<String> flightDropdown = new JComboBox<>(flightNames.toArray(new String[0]));

        JTextField seatNumberField = new JTextField(bookingData != null ? bookingData[3].toString() : "");
        JTextField bookingDateField = new JTextField(bookingData != null ? bookingData[4].toString() : "");
        JTextField passengerNameField = new JTextField(bookingData != null ? bookingData[5].toString() : "");
        JTextField passengerAgeField = new JTextField(bookingData != null ? bookingData[6].toString() : "");

        dialog.add(new JLabel("User:"));
        dialog.add(userDropdown);
        dialog.add(new JLabel("Flight:"));
        dialog.add(flightDropdown);
        dialog.add(new JLabel("Seat Number:"));
        dialog.add(seatNumberField);
        dialog.add(new JLabel("Booking Date (YYYY-MM-DD):"));
        dialog.add(bookingDateField);
        dialog.add(new JLabel("Passenger Name:"));
        dialog.add(passengerNameField);
        dialog.add(new JLabel("Passenger Age:"));
        dialog.add(passengerAgeField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        dialog.add(saveBtn);
        dialog.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            try {
                int userId = userIds.get(userDropdown.getSelectedIndex());
                int flightId = flightIds.get(flightDropdown.getSelectedIndex());
                String seatNumber = seatNumberField.getText().trim();
                String bookingDate = bookingDateField.getText().trim();
                String passengerName = passengerNameField.getText().trim();
                int passengerAge = Integer.parseInt(passengerAgeField.getText().trim());

                if (bookingData == null) {
                    addBooking(userId, flightId, seatNumber, bookingDate, passengerName, passengerAge);
                } else {
                    int bookingId = (int) bookingData[0];
                    updateBooking(bookingId, userId, flightId, seatNumber, bookingDate, passengerName, passengerAge);
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Enter a valid number for passenger age.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadUsers() {
        userIds.clear();
        userNames.clear();
        try {
            ResultSet rs = connection.getConnection().prepareStatement("SELECT user_id, name FROM users").executeQuery();
            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
                userNames.add(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
    }

    private void loadFlights() {
        flightIds.clear();
        flightNames.clear();
        try {
            ResultSet rs = connection.getConnection().prepareStatement("SELECT flight_id, arline_name FROM flights").executeQuery();
            while (rs.next()) {
                flightIds.add(rs.getInt("flight_id"));
                flightNames.add(rs.getString("arline_name"));
            }
        } catch (Exception e) {
            System.out.println("Failed to load flights: " + e.getMessage());
        }
    }

    private void addBooking(int userId, int flightId, String seatNumber, String bookingDate, String passengerName, int passengerAge) throws Exception {
        String query = "INSERT INTO booking (user_id, flight_id, seat_number, booking_date, passenger_name, passenger_age) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, flightId);
        ps.setString(3, seatNumber);
        ps.setString(4, bookingDate);
        ps.setString(5, passengerName);
        ps.setInt(6, passengerAge);
        ps.executeUpdate();
        refreshTable();
    }

    private void updateBooking(int bookingId, int userId, int flightId, String seatNumber, String bookingDate, String passengerName, int passengerAge) throws Exception {
        String query = "UPDATE booking SET user_id=?, flight_id=?, seat_number=?, booking_date=?, passenger_name=?, passenger_age=? WHERE booking_id=?";
        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, flightId);
        ps.setString(3, seatNumber);
        ps.setString(4, bookingDate);
        ps.setString(5, passengerName);
        ps.setInt(6, passengerAge);
        ps.setInt(7, bookingId);
        ps.executeUpdate();
        refreshTable();
    }

    private void deleteBooking(int id) {
        try {
            String query = "DELETE FROM booking WHERE booking_id=?";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete booking: " + e.getMessage());
        }
    }

    private void refreshTable() {
        data = fetchBookingsData();
        bookingsTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private Object[][] fetchBookingsData() {
        try {
            String query = "SELECT * FROM booking";
            PreparedStatement ps = connection.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] bookings = new Object[rowCount][7];
            int i = 0;
            while (rs.next()) {
                bookings[i][0] = rs.getInt("booking_id");
                bookings[i][1] = rs.getInt("user_id");
                bookings[i][2] = rs.getInt("flight_id");
                bookings[i][3] = rs.getString("seat_number");
                bookings[i][4] = rs.getDate("booking_date");
                bookings[i][5] = rs.getString("passenger_name");
                bookings[i][6] = rs.getInt("passenger_age");
                i++;
            }
            return bookings;
        } catch (Exception e) {
            System.out.println("Failed to fetch bookings: " + e.getMessage());
            return new Object[0][7];
        }
    }

    public void refreshData() {
        refreshTable();
    }
}
