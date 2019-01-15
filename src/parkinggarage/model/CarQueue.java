package parkinggarage.model;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
	private Queue<Car> unplannedCars = new LinkedList<>();
	private Queue<Car> subscribedCars = new LinkedList<>();
	
	public boolean offer(Car car) {
		switch (car.getType()) {
		case UNPLANNED:
			return unplannedCars.offer(car);	
		case SUBSCRIBER:
			return subscribedCars.offer(car);
		default:
			return false;
		}
	}
	
	public Car poll() {
		if (!subscribedCars.isEmpty()) {
			return subscribedCars.poll();
		} else if (!unplannedCars.isEmpty()) {
			return unplannedCars.poll();
		} else {
			return null;
		}
	}
	
	public Car peek() {
		if (!subscribedCars.isEmpty()) {
			return subscribedCars.peek();
		} else if (!unplannedCars.isEmpty()) {
			return unplannedCars.peek();
		} else {
			return null;
		}
	}
	
	public int size() {
		return unplannedCars.size() + subscribedCars.size();
	}
	
	public boolean isEmpty() {
		return unplannedCars.isEmpty() && subscribedCars.isEmpty();
	}
}
