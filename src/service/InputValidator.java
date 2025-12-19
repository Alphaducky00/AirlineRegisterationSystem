package service;

public class InputValidator {
	
	
	public static String validateName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be empty or null!");
		}
		
		return name;
	}
	
	public static int validateAge(int age) {
		if (age <= 0 || age > 120) {
			throw new IllegalArgumentException("Invalid age!");
		}
		
		return age;
	}
	
	public static String validateSource(String source) {
		if (source == null || source.isBlank()) {
			throw new IllegalArgumentException("Source cannot be empty or null!");
		}
		
		return source;
	}
	
	public static String validateDestination(String destination) {
		if (destination == null || destination.isBlank()) {
			throw new IllegalArgumentException("Destination cannot be empty or null!");
		}
		
		return destination;
	}
	
	
}
