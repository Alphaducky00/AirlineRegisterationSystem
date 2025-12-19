package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Flight;

public class FlightDataSource {
	
	private static List<Flight> Flights = new ArrayList<>();
	
	static {
		Flight flight1 = new Flight("British Airlines", "Birmingham", "Manchester", 50.00);
		
		Flight flight2 = new Flight("German Airlines", "Munich", "Berlin", 30.00);
		
		Flight flight3 = new Flight("Pakistani Airlines", "Karachi", "Lahore", 10.00);
		
		Flight flight4 = new Flight("Turkish Airlines", "Ankara", "Konya", 20.00);
		
		Flight flight5 = new Flight("Indian Airlines", "Mumbai", "Kolkata", 15.00);
		
		Flights.add(flight1);
		Flights.add(flight2);
		Flights.add(flight3);
		Flights.add(flight4);
		Flights.add(flight5);
	}
	
	public static List<Flight> getFlights(){
		return Collections.unmodifiableList(Flights);
	}
}
