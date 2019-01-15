package parkinggarage.model;

import parkinggarage.Global;
import parkinggarage.shared.Ticker;

public class Spot implements Ticker {
	// The car occupying this spot
	private Car car;
	
	// the location of this spot
	private int floor;
	private int row;
	private int place;
	
	// whether this is a subscriber-only spot
	private CarType type;

	
	public Spot(int floor, int row, int place, CarType type) {
		this.floor = floor;
		this.row = row;
		this.place = place;
		this.type = type;
		
		// the car variable is intentionally left null, this indicates an empty spot
	}
	
	public Spot(int floor, int row, int place) {
		this(floor, row, place, CarType.UNPLANNED);
	}

	/**
	 * @return the car occupying this spot, or null
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * @param newCar the car to set
	 */
	public void setCar(Car newCar) throws IllegalStateException, IllegalArgumentException {
		if (newCar == null) {
			throw new IllegalArgumentException("car cannot be null, the spot will free itself");
		}
		/*
		 * note that the control flow here means that even if a car is a non-subscriber and
		 * the spot is subscriber-only, if the spot is full the IllegalStateException will be thrown
		 */
		if (this.car == null) {
			if (this.type != newCar.getType()) {
				throw new IllegalArgumentException(
						"Cannot assign car with type " + car.getType().toString() +
							" to spot with type " + this.type.toString()
						);
			} else {
				this.car = newCar;
			}
		} else {
			throw new IllegalStateException("Cannot assign car to occupied spot");
		}
	}
	
	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the place
	 */
	public int getPlace() {
		return place;
	}

	/**
	 * @return the type
	 */
	public CarType getType() {
		return type;
	}

	/**
	 * @return a boolean indicating whether the spot is free
	 */
	public boolean isEmpty() {
		return car == null;
	}
	
	/**
	 * a spot will tick and check if a car is still occupying this spot
	 * it also adds the car 
	 */
	public void tick() {
		if (!isEmpty() && car.getExitTime().greaterThan(Global.getSimulationTime())) {
			car = null;
		}
	}
}
