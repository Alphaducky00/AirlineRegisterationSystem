-- Create database
CREATE DATABASE IF NOT EXISTS airline;
USE airline;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id)
);

-- Insert some sample users
INSERT INTO users (name, email, phone) VALUES
('Alice Johnson', 'alice@example.com', '1234567890'),
('Bob Smith', 'bob@example.com', '0987654321'),
('Charlie Brown', 'charlie@example.com', '1122334455'),
('David Lee', 'david@example.com', '5566778899');

-- ----------------------------
-- Table structure for flights
-- ----------------------------
DROP TABLE IF EXISTS flights;
CREATE TABLE flights (
    flight_id INT(11) NOT NULL AUTO_INCREMENT,
    arline_name VARCHAR(100),
    source VARCHAR(50),
    destination VARCHAR(50),
    date DATETIME,
    total_seats INT(11),
    avaliable_seats INT(11),
    base_fare DECIMAL(10,2) DEFAULT 15.00,
    PRIMARY KEY (flight_id)
);

-- Insert some sample flights
INSERT INTO flights (arline_name, source, destination, date, total_seats, avaliable_seats, base_fare) VALUES
('Air Alpha', 'Karachi', 'Lahore', '2025-12-15 10:00:00', 100, 100, 5000),
('SkyJet', 'Lahore', 'Islamabad', '2025-12-16 14:30:00', 80, 80, 4500),
('FlyHigh', 'Islamabad', 'Karachi', '2025-12-17 09:00:00', 120, 120, 5500);

-- ----------------------------
-- Table structure for booking
-- ----------------------------
DROP TABLE IF EXISTS booking;
CREATE TABLE booking (
    booking_id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    flight_id INT(11) NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    passenger_age INT(11) NOT NULL,
    seat_number VARCHAR(10),
    booking_date DATE,
    PRIMARY KEY (booking_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insert some sample bookings
INSERT INTO booking (user_id, flight_id, passenger_name, passenger_age, seat_number, booking_date) VALUES
(1, 1, 'Alice Johnson', 28, 'A1', '2025-12-01'),
(2, 2, 'Bob Smith', 34, 'B2', '2025-12-02'),
(3, 3, 'Charlie Brown', 22, 'C3', '2025-12-03');
