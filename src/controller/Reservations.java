package controller;

import java.util.HashMap;

import parkinggarage.model.Car;
import parkinggarage.model.Reservation;
import parkinggarage.util.Settings;
import parkinggarage.util.Time;

public class Reservations {
	
	//Hashmap voor alle reservations reservationNumber | Reservation
	private HashMap<Integer,Reservation> reservations = new HashMap<>();
	//Hashmap voor alle reservations Car | reservationNumber
	private HashMap<Car,Integer> carres = new HashMap<>();
	private int reservationNumber = 1;
	
	public boolean hasReservation(Car car) {
		
		if(carres.containsKey(car)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public int makeReservation(Car car, Time time) {
		
		int returnint = 0;
		
		try {
			if(!this.hasReservation(car)) {
				
				Reservation newres = new Reservation(car,time, new Settings());
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
	
	public Reservation getReservationByCar(Car car) {
		
		Reservation returnres = null;
		
		return returnres;
		
	}
	
}
