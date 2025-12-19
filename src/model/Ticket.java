package model;

public class Ticket {
	
	private int ticketId;
	private final String passengerName;
	private final int passengerAge;
	private final SeatType seatType;
	private double finalPrice;
	private final Flight selectedFlight;
	private static final double baseFare = 15.00;
	
	
	public Ticket(String passengerName, int passengerAge, SeatType seatType, Flight selectedFlight) {
		if (passengerName == null || passengerName.isBlank()) {
			throw new IllegalArgumentException("Passenger name field cannot be empty");
		}
		
		if (passengerAge <= 0) {
			throw new IllegalArgumentException("Age cannot be less than or equal to zero!");
		}
		
		if (seatType == null) {
			throw new IllegalArgumentException("Seat type field cannot be null");
		}
		
		if (selectedFlight == null) {
			throw new IllegalArgumentException("Flight must be selected!");
		}
		
		this.passengerName = passengerName;
		this.passengerAge = passengerAge;
		this.seatType = seatType;
		this.selectedFlight = selectedFlight;
		
		this.finalPrice = calculatePrice(baseFare, seatType);

	}
	
	public int getTicketId() {
		return this.ticketId;
	}
	
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getPassengerName() {
		return this.passengerName;
	}
	
	public int getPassengerAge() {
		return this.passengerAge;
	}
	
	public SeatType getSeatType() {
		return this.seatType;
	}
	
	public double getFinalPrice() {
		return this.finalPrice;
	}
	
	public Flight getSelectedFlight() {
		return this.selectedFlight;
	}
	
	public enum SeatType {
		ECONOMY,
		BUSINESS,
		FIRST_CLASS
	}
	
	@Override
	public String toString() {
		return "[Passenger Name: " + this.passengerName + " Passenger Age: " + this.passengerAge + "\n"
				+ "Seat type: " + this.seatType + " Final price: " + this.finalPrice + "\n" + 
				"Selected flight: " + this.selectedFlight + "]";
	}
	
	public double calculatePrice(double baseFare, SeatType seatType) {
		
		switch (seatType) {
		
		case ECONOMY:
			return baseFare * 1.0;
		
		case FIRST_CLASS:
			return baseFare * 1.5;
		
		case BUSINESS:
			return baseFare * 1.8;	
}
		return baseFare;
	}
	
}
