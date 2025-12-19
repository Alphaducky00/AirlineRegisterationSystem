package ui;

import util.Connect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FlightsPage extends JPanel {

    private JTable flightsTable;
    private Object[][] data;
    private String[] columns = {"Flight ID", "Airline", "Source", "Destination", "Date", "Total Seats", "Available Seats", "Base Fare"};

    private Connect connection;

    public FlightsPage() {
        setLayout(null);
        setBackground(new Color(245, 245, 245));

        connection = new Connect();
        connection.connect();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBounds(20, 0, 900, 40);

        JButton addBtn = new JButton("Add Flight");
        JButton editBtn = new JButton("Edit Flight");
        JButton deleteBtn = new JButton("Delete Flight");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(deleteBtn);

        add(buttonsPanel);

        data = fetchFlightsData();
        flightsTable = new JTable(data, columns);
        flightsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setBounds(20, 50, 900, 400);
        add(scrollPane);

        addBtn.addActionListener(e -> showFlightForm(null)); // null = new flight
        editBtn.addActionListener(e -> {
            int selected = flightsTable.getSelectedRow();
            if (selected >= 0) {
                Object[] flightData = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    flightData[i] = flightsTable.getValueAt(selected, i);
                }
                showFlightForm(flightData);
            } else {
                JOptionPane.showMessageDialog(this, "Select a flight to edit.");
            }
        });
        deleteBtn.addActionListener(e -> {
            int selected = flightsTable.getSelectedRow();
            if (selected >= 0) {
                int flightId = (int) flightsTable.getValueAt(selected, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete flight ID " + flightId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteFlight(flightId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a flight to delete.");
            }
        });
    }

    private void showFlightForm(Object[] flightData) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Flight Form", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(8, 2, 5, 5));

        JTextField airlineField = new JTextField(flightData != null ? flightData[1].toString() : "");
        JTextField sourceField = new JTextField(flightData != null ? flightData[2].toString() : "");
        JTextField destField = new JTextField(flightData != null ? flightData[3].toString() : "");
        JTextField dateField = new JTextField(flightData != null ? flightData[4].toString() : "");
        JTextField totalSeatsField = new JTextField(flightData != null ? flightData[5].toString() : "");
        JTextField availSeatsField = new JTextField(flightData != null ? flightData[6].toString() : "");
        JTextField baseFareField = new JTextField(flightData != null ? flightData[7].toString() : "");

        dialog.add(new JLabel("Airline Name:")); dialog.add(airlineField);
        dialog.add(new JLabel("Source:")); dialog.add(sourceField);
        dialog.add(new JLabel("Destination:")); dialog.add(destField);
        dialog.add(new JLabel("Date (YYYY-MM-DD):")); dialog.add(dateField);
        dialog.add(new JLabel("Total Seats:")); dialog.add(totalSeatsField);
        dialog.add(new JLabel("Available Seats:")); dialog.add(availSeatsField);
        dialog.add(new JLabel("Base Fare:")); dialog.add(baseFareField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        dialog.add(saveBtn);
        dialog.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            // --- INPUT VALIDATION ---
            String airline = airlineField.getText().trim();
            String source = sourceField.getText().trim();
            String dest = destField.getText().trim();
            String date = dateField.getText().trim();
            String totalSeatsStr = totalSeatsField.getText().trim();
            String availSeatsStr = availSeatsField.getText().trim();
            String baseFareStr = baseFareField.getText().trim();

            if (airline.isEmpty() || source.isEmpty() || dest.isEmpty() || date.isEmpty() ||
                totalSeatsStr.isEmpty() || availSeatsStr.isEmpty() || baseFareStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.");
                return; 
            }

            int totalSeats, availSeats;
            double baseFare;
            try {
                totalSeats = Integer.parseInt(totalSeatsStr);
                availSeats = Integer.parseInt(availSeatsStr);
                baseFare = Double.parseDouble(baseFareStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for seats and fare.");
                return; 
            }

            try {
                if (flightData == null) {
                    addFlight(airline, source, dest, date, totalSeats, availSeats, baseFare);
                } else {
                    int flightId = (int) flightData[0];
                    updateFlight(flightId, airline, source, dest, date, totalSeats, availSeats, baseFare);
                }
                dialog.dispose(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Database error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void addFlight(String airline, String source, String dest, String date, int total, int avail, double fare) {
        try {
            String query = "INSERT INTO flights (arline_name, source, destination, date, total_seats, avaliable_seats, base_fare) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ps.setString(1, airline);
            ps.setString(2, source);
            ps.setString(3, dest);
            ps.setString(4, date);
            ps.setInt(5, total);
            ps.setInt(6, avail);
            ps.setDouble(7, fare);
            ps.executeUpdate();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to add flight: " + e.getMessage());
        }
    }

    private void updateFlight(int id, String airline, String source, String dest, String date, int total, int avail, double fare) {
        try {
            String query = "UPDATE flights SET arline_name=?, source=?, destination=?, date=?, total_seats=?, avaliable_seats=?, base_fare=? WHERE flight_id=?";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ps.setString(1, airline);
            ps.setString(2, source);
            ps.setString(3, dest);
            ps.setString(4, date);
            ps.setInt(5, total);
            ps.setInt(6, avail);
            ps.setDouble(7, fare);
            ps.setInt(8, id);
            ps.executeUpdate();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to update flight: " + e.getMessage());
        }
    }

    private void deleteFlight(int id) {
        try {
            String query = "DELETE FROM flights WHERE flight_id=?";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete flight: " + e.getMessage());
        }
    }

    private void refreshTable() {
        data = fetchFlightsData();
        flightsTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private Object[][] fetchFlightsData() {
        try {
            String query = "SELECT * FROM flights";
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            java.util.List<Object[]> flightsList = new java.util.ArrayList<>();

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("flight_id");
                row[1] = rs.getString("arline_name");
                row[2] = rs.getString("source");
                row[3] = rs.getString("destination");
                row[4] = rs.getTimestamp("date"); 
                row[5] = rs.getInt("total_seats");
                row[6] = rs.getInt("avaliable_seats");
                row[7] = rs.getDouble("base_fare");

                flightsList.add(row);
            }

            Object[][] flightsArray = new Object[flightsList.size()][8];
            return flightsList.toArray(flightsArray);

        } catch (Exception e) {
            System.out.println("Failed to fetch flights: " + e.getMessage());
            return new Object[0][8];
        }
    }

    
    public void refreshData() {
        refreshTable(); 
    }

}
