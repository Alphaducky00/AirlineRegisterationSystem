package service;

import java.util.List;

import model.Ticket;
import storage.BookingManager;

public class TicketPrinter {
	
	public void printAvaliableTickets(){
		BookingManager bookingManager = new BookingManager();
		List<Ticket> getAvailableTickets = bookingManager.getBookings();
		
		if(getAvailableTickets.isEmpty()) {
			System.out.println("No tickets found!");
		}
		
		else {
			String information;
			
			for (Ticket ticket: getAvailableTickets) {
				information = ticket.toString();
				System.out.println(information);
			}
		}
		
	}
}
