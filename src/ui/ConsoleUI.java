package ui;

import java.awt.Color;
import javax.swing.SwingUtilities;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import util.Connect;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class ConsoleUI extends JFrame {
	
	public static int loggedUserId = 0;
	public static String loggedUsername = "";
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsoleUI frame = new ConsoleUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConsoleUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(245, 245, 245)); 
		setContentPane(contentPane);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(null);
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBounds(-42, -37, 600, 400);  

		
		loginPanel.setLocation(
			    (getWidth() - loginPanel.getWidth()) / 2,
			    (getHeight() - loginPanel.getHeight()) / 2
			);
		
		loginPanel.setBorder(new LineBorder(new Color(180, 180, 180), 2, true));
		contentPane.add(loginPanel);
		
		SwingUtilities.invokeLater(() -> {
		    loginPanel.setLocation(
		        (getWidth() - loginPanel.getWidth()) / 2,
		        (getHeight() - loginPanel.getHeight()) / 2
		    );
		});
		
		JLabel title = new JLabel("Login");
		title.setFont(new Font("Tahoma", Font.BOLD, 28));
		title.setBounds(250, 30, 200, 40);
		loginPanel.add(title);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		userLabel.setBounds(120, 120, 100, 30);
		loginPanel.add(userLabel);

		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(230, 120, 250, 30);
		loginPanel.add(txtUsername);

		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		passLabel.setBounds(120, 180, 100, 30);
		loginPanel.add(passLabel);

		JTextField txtPassword = new JTextField();
		txtPassword.setBounds(230, 180, 250, 30);
		loginPanel.add(txtPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usernameString = txtUsername.getText();
				String passwordString = txtPassword.getText();
				
				Connect connection = new Connect();
				connection.connect();
				
				boolean login = connection.login(usernameString, passwordString);
				if (login) {
					JOptionPane.showMessageDialog(null, "Access granted", "Success", JOptionPane.INFORMATION_MESSAGE);
					
					 ConsoleUI.loggedUsername = usernameString;
					 ConsoleUI.loggedUserId = 1;
					    
					 DashboardUI db = new DashboardUI(usernameString);
					    db.setVisible(true);

			            dispose();
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Wrong credentials!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogin.setBounds(220, 260, 160, 40);
		loginPanel.add(btnLogin);

	}

}
