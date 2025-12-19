package model;

public class Flight {
	
	private int flightId;
	private final String airlineName;
	private final String source;
	private final String destination;
	private final double baseFare;
	
	public Flight(String airlineName, String source, String destination, double baseFare){
		if (airlineName == null || airlineName.isBlank()) {
			throw new IllegalArgumentException("Airline field cannot be empty");
		}
		
		if (source == null || source.isBlank()) {
			throw new IllegalArgumentException("Source field cannot be empty!");
		}
		
		if (destination == null || destination.isBlank()) {
			throw new IllegalArgumentException("Destination field cannot be empty!");
		}
		
		if (baseFare < 0) {
			throw new IllegalArgumentException("Base fare cannot be negative!");
		}
		
		this.airlineName = airlineName;
		this.source = source;
		this.destination = destination;
		this.baseFare = baseFare;
		
	}
	
	public int getFlightId() {
		return this.flightId;
	}
	
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}
	
	public String getAirlineName() {
		return this.airlineName;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public String getDestination() {
		return this.destination;
	}

	public double getBaseFare() {
		return this.baseFare;
	}
	
	@Override
	public String toString() {
		return "[Airline Name: " + this.airlineName + "\n" + "Source: " + this.source + "\n" +
				"Destination: " + this.destination + " Base Fare: " + this.baseFare + "]";
	}
	

}
