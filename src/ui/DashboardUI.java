package ui;

import util.Connect;
import ui.BookingsPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardUI extends JFrame {

    private JPanel sidebar;
    private JPanel cards;           
    private CardLayout cardLayout;

    private JLabel flightsValueLabel;
    private JLabel bookingsValueLabel;
    private JLabel usersValueLabel;

    public DashboardUI(String username) {
    	
    	if (ConsoleUI.loggedUserId == 0) {
    	    JOptionPane.showMessageDialog(null, "Please login first!");
    	    new ConsoleUI().setVisible(true);
    	    dispose();
    	    return;
    	}
    	
        setTitle("Dashboard - " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initLayout(username);
    }

    private void initLayout(String username) {
        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(220, 220, 220));
        header.setPreferredSize(new Dimension(0, 80));
        JLabel label = new JLabel("Airline Registeration System");
        label.setFont(new Font("Tahoma", Font.BOLD, 28));
        label.setBorder(new EmptyBorder(10, 10, 10, 20));
        header.add(label, BorderLayout.EAST);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar = new JPanel();
        sidebar.setBackground(new Color(200, 200, 200));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JButton btnDashboard = new JButton("Dashboard");
        styleSidebarButton(btnDashboard);

        JButton btnFlights = new JButton("Flights");
        styleSidebarButton(btnFlights);

        JButton btnBooking = new JButton("Booking");
        styleSidebarButton(btnBooking);

        JButton btnUsers = new JButton("Users");
        styleSidebarButton(btnUsers);

        JButton btnLogout = new JButton("Logout");
        styleSidebarButton(btnLogout);
        btnLogout.setBackground(new Color(220, 20, 60));

        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnFlights);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnBooking);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnUsers);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalGlue());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        JPanel dashboardPage = buildDashboardPage();
        cards.add(dashboardPage, "dashboard");

        FlightsPage flightsPage = new FlightsPage();
        cards.add(flightsPage, "flights");

        BookingsPage bookingPage = new BookingsPage();
        cards.add(bookingPage, "booking");

        UsersPage usersPage = new UsersPage();
        cards.add(usersPage, "users");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(cards, BorderLayout.CENTER);

        btnDashboard.addActionListener(e -> {
            updateDashboardCounts();
            cardLayout.show(cards, "dashboard");
        });
        btnFlights.addActionListener(e -> {
            flightsPage.refreshData(); 
            cardLayout.show(cards, "flights");
        });

        btnBooking.addActionListener(e -> {
            bookingPage.refreshData(); 
            cardLayout.show(cards, "booking");
        });

        btnUsers.addActionListener(e -> {
            usersPage.refreshData(); 
            cardLayout.show(cards, "users");
        });

        btnLogout.addActionListener(e -> dispose());
        
        updateDashboardCounts();
        cardLayout.show(cards, "dashboard");

        setVisible(true);
    }

    private void styleSidebarButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    }

    private JPanel buildDashboardPage() {
        JPanel page = new JPanel(null);
        page.setBackground(new Color(245,245,245));

        JPanel card1 = new JPanel();
        card1.setBackground(new Color(180, 200, 255));
        card1.setBounds(50, 50, 200, 120);
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        JLabel flightsTitle = new JLabel("Total Flights");
        flightsTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        flightsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        flightsValueLabel = new JLabel("0");
        flightsValueLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        flightsValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card1.add(Box.createVerticalStrut(20));
        card1.add(flightsTitle);
        card1.add(Box.createVerticalStrut(10));
        card1.add(flightsValueLabel);
        page.add(card1);

        JPanel card2 = new JPanel();
        card2.setBackground(new Color(200, 255, 180));
        card2.setBounds(270, 50, 200, 120);
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
        JLabel bookingsTitle = new JLabel("Total Bookings");
        bookingsTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        bookingsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookingsValueLabel = new JLabel("0");
        bookingsValueLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        bookingsValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card2.add(Box.createVerticalStrut(20));
        card2.add(bookingsTitle);
        card2.add(Box.createVerticalStrut(10));
        card2.add(bookingsValueLabel);
        page.add(card2);

        JPanel card3 = new JPanel();
        card3.setBackground(new Color(255, 200, 200));
        card3.setBounds(490, 50, 200, 120);
        card3.setLayout(new BoxLayout(card3, BoxLayout.Y_AXIS));
        JLabel usersTitle = new JLabel("Total Users");
        usersTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        usersTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        usersValueLabel = new JLabel("0");
        usersValueLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        usersValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card3.add(Box.createVerticalStrut(20));
        card3.add(usersTitle);
        card3.add(Box.createVerticalStrut(10));
        card3.add(usersValueLabel);
        page.add(card3);

        return page;
    }

    private void updateDashboardCounts() {
        SwingUtilities.invokeLater(() -> {
            Connect con = new Connect();
            con.connect();
            try {
                flightsValueLabel.setText(String.valueOf(con.getFlightCount()));
                bookingsValueLabel.setText(String.valueOf(con.getBookingCount()));
                usersValueLabel.setText(String.valueOf(con.getUsersCount()));
            } catch (Exception ex) {
                System.out.println("Failed to update counts: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardUI("Abdul Rehman"));
    }
}
