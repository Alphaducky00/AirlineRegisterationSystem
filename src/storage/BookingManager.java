package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Ticket;

public class BookingManager {
	
	private static List<Ticket> ticketsList = new ArrayList<>();
	
	public void saveBooking(Ticket ticket) {
		if (ticket == null) {
			System.out.println("Ticket cannot be null!");
		}
		
		else {
			ticketsList.add(ticket);
		}
	}
	
	public List<Ticket> getBookings(){
		return Collections.unmodifiableList(ticketsList);
	}
}
