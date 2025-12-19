package controller;

import java.util.List;
import java.util.Scanner;

import model.Flight;
import model.Ticket;
import model.Ticket.SeatType;
import service.FlightSearchService;
import service.InputValidator;
import service.TicketPrinter;
import storage.BookingManager;

public class FlightBookingController {
	
	BookingManager bookingManager;
	Flight selectedFlight = null;
	boolean flag = true;
	
	
	public void flightBookControl() {
		
		Scanner sc = new Scanner(System.in);
		
		while (flag) {
			System.out.println("Select one of the following options: \n"
					+ "1. Search flights    2. Book Ticket\n"
					+ "3. Print booked tickets    4. Exit ");
			
			String user = sc.nextLine();
			int choice = Integer.parseInt(user);
			
			switch (choice) {
			case 1: {
				
				FlightSearchService flightSearchService = new FlightSearchService();
				List<Flight> availableFlights =   flightSearchService.getFoundFlights();
				
				if (availableFlights.isEmpty()) {
					System.out.println("No flights booked yet!");
				}
				
				else {
					System.out.println("Select a flight by number: ");
					
					for (int i = 0; i < availableFlights.size(); i++) {
						System.out.println(i + 1 + ". " + availableFlights.get(i));
					}
					
					String choiceSelection = sc.nextLine();
					int flightChoice = Integer.parseInt(choiceSelection);
					
					if (flightChoice < 0 || flightChoice > availableFlights.size()) {
						throw new IllegalArgumentException("Select a valid range!");
					}
					
					else {
						selectedFlight = availableFlights.get(flightChoice - 1);
					}
				}
				break;
			}
			
			case 2: {
				
				if (selectedFlight == null) {
					System.out.println("Please select a flight first!");
					break;
				}
				
				else {
					System.out.println("Enter the passenger's name: ");
					String passengerName = InputValidator.validateName(sc.nextLine());
					
					System.out.println("Enter the passenger's age: ");
					int age = InputValidator.validateAge(Integer.parseInt(sc.nextLine()));
					
					System.out.println("Select seat type: \n"
							+ "1. Economy\n"
							+ "2. First Class\n"
							+ "3. Business Class");
					
					String choiceString = sc.nextLine();
					int seatChoice = Integer.parseInt(choiceString);
					
					SeatType selectSeatType;
					
					switch (seatChoice){
					case 1: {
						
						selectSeatType = Ticket.SeatType.ECONOMY;
						break;
					}
					
					case 2: {
						selectSeatType = Ticket.SeatType.FIRST_CLASS;
						break;
					}
					
					case 3: {
						selectSeatType = Ticket.SeatType.BUSINESS;
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + seatChoice);
					}
					
					Ticket ticket = new Ticket(passengerName, age, selectSeatType, selectedFlight);
				    bookingManager = new BookingManager();
					bookingManager.saveBooking(ticket);
					break;
				}
			}
			
			case 3:{
				TicketPrinter ticketPrinter = new TicketPrinter();
				ticketPrinter.printAvaliableTickets();
				break;
			}
			
			case 4:{
				System.out.println("Exiting the program...");
				flag = false;
				break;
			}
			default:
				throw new IllegalArgumentException("Invalid Input!");
			}
		}
		
	}
}
