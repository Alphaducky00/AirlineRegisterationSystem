package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import storage.*;
import model.Flight;

public class FlightSearchService {
	
	Scanner sc = new Scanner(System.in);
	
	private List<Flight> foundFlights = new ArrayList<Flight>();
	
	public List<Flight> getFoundFlights() {
		
		foundFlights.clear();
		System.out.println("Enter the source: ");
		String source = sc.nextLine().trim();
		
		System.out.println("Enter the destination: ");
		String destination = sc.nextLine().trim();
		
		for (Flight flight: FlightDataSource.getFlights()) {
			
			if (flight.getSource().equalsIgnoreCase(source) && flight.getDestination().equalsIgnoreCase(destination)){
				System.out.println("Flight " + flight.getFlightId() + " found!");
				foundFlights.add(flight);
			}
		}
		
		return foundFlights;

		
	}
	
	public void printFoundFlights(List <Flight> foundFlights) {
		
		if (foundFlights.isEmpty()) {
			System.out.println("No flights found!");
		}
		
		else {
			System.out.println("The flights found are: ");
			for (Flight flights: foundFlights) {
				String info = flights.toString();
				System.out.println(info);
			}
		}
		
	}
}
