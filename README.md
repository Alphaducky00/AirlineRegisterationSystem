Airline Reservation System (Java • JDBC • MySQL)

A simple, beginner-friendly Airline Reservation System built using Java Swing, JDBC, and MySQL.
This project allows users to log in, view available flights, book tickets, manage passengers, and perform basic CRUD operations through an easy desktop UI.

Features
User Login

Secure login using MySQL database

Validates username & password

Only authenticated users can access the dashboard

Dashboard

Clean UI made with Java Swing

Navigation buttons for:

Add Passenger

Search Flights

Book Flight

View Tickets

Update/Delete Passenger

Add Flights

Passenger Management

Add new passengers with personal details

Auto-generated Passenger ID

Update or delete existing passengers

Flight Management

Add new flights

Store details like airline name, source, destination, date, and available seats

Search flights based on source & destination

Booking System

Real-time seat availability check

Auto-generated Booking ID

Stores ticket details in SQL database

Successfully reduces available seats after booking

Database Integration

Using MySQL tables:

users

passengers

flights

booking

All JDBC concepts used:

Prepared Statements

ResultSet

Batch Processing

Transaction Handling

Technologies Used

Java (Swing)

JDBC

MySQL

Eclipse IDE

Project Structure (Simple View)
src/
│
FlightBookingController.java
Flight.java
Ticket.java
FlightSearchService.java
InputValidator.java
TicketPrinter.java
BookingManager.java
BookingsPage.java
ConsoleUI.java
DashboardUI.java
FlightsPage.java
UsersPage.java
Connect.java









