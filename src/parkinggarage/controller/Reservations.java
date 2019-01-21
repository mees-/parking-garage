package parkinggarage.controller;

import java.util.HashMap;

import parkinggarage.model.Car;
import parkinggarage.model.ReservationCar;
import parkinggarage.util.Settings;
import parkinggarage.util.Time;

public class Reservations {
	
	//Hashmap voor alle reservations reservationNumber | Reservation
	private HashMap<Integer,ReservationCar> reservations = new HashMap<>();
	//Hashmap voor alle reservations Car | reservationNumber
	private HashMap<Car,Integer> carres = new HashMap<>();
	private int reservationNumber = 1;
	
	//Check if a car has a reservation.
	public boolean hasReservation(Car car) {
		
		if(carres.containsKey(car)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	//Create a reservation if a car does not already have one.
	public int makeReservation(Car car, Time time) {
		
		int returnint = 0;
		
		try {
			if(!this.hasReservation(car)) {
				
				//TODO Give settings.
				ReservationCar newres = new ReservationCar(car,time, new Settings());
				returnint = ++this.reservationNumber;
				reservations.put(returnint, newres);
				carres.put(car, returnint);
				
			}else {
				throw new Exception("Car already has a reservation!");
			}
		}catch(Exception e) {
			
			System.out.println("ERROR: " + e);
			
		}
		
		return returnint;
	}
	
	//Get the reservation number of a Car if it has a reservation.
	public int getNumberByCar(Car car) {
		
		int returnint = 0;
		
		try {
			
			if(carres.containsKey(car)) {
				
				returnint = carres.get(car);
				
			}else {
				throw new Exception("That car does not have any reservations!");
			}
			
		}catch(Exception e) {
			System.out.println("ERROR: " + e);
		}
		
		return returnint;
		
	}
	
	//Get the reservation listed for a Car by using the reservationNumber
	public Reservation getReservationByNumber(int resNumber) {
		
		Reservation returnres = null;
		
		try {
			
			if(reservations.containsKey(resNumber)) {
				returnres = reservations.get(resNumber);
			}else {
				throw new IndexOutOfBoundsException("No reservation with this number");
			}
			
		}catch(IndexOutOfBoundsException e){
			System.out.println("ERROR: " + e);
		}
		return returnres;
	}
	
	//Get the reservation listed for a Car by using the Car object
	public Reservation getReservationByCar(Car car) {
		
		Reservation returnres = null;
		
		try {
			
			if(this.hasReservation(car)) {
				
				int resnum = carres.get(car);
				returnres = reservations.get(resnum);
				
			}else {
				throw new Exception("Car does not have reservation!");
			}
			
		}catch(Exception e) {
			System.out.println("ERROR: " + e);
		}
		
		return returnres;
		
	}
	
	//Remove the reservation if there is one by using the Reservation number
	public boolean removeReservationByNumber(int num) {
		
		boolean result = false;
		
		try {
			
			if(reservations.containsKey(num)) {
				
				carres.values().remove(num);
				reservations.remove(num);
				result = true;
				
			}else {
				
				throw new Exception("No reservation with this number!");
				
			}
			
		}catch(Exception e) {
			
			System.out.println("ERROR: " + e);
			result = false;
			
		}
		
		return result;
		
	}
	
	//Remove the reservation if there is one by using the Car object
	public boolean removeReservationByCar(Car car) {
		
		boolean result = false;
		
		try {
			
			if(this.hasReservation(car)) {
				
				int resnum = this.getNumberByCar(car);
				reservations.remove(resnum);
				carres.remove(car);
				result = true;
				
			}else {
				throw new Exception("This car does not have a reservation!");
			}
			
		}catch(Exception e) {
			
			System.out.println("ERROR: " + e);
			result = false;
			
		}
		
		return result;
	}
	
}
