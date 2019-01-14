package parkeergarage.model;

public class Spot {
	// The car occupying this spot
	private Car car;
	
	// the location of this spot
	private int floor;
	private int row;
	private int place;

	
	public Spot(int floor, int row, int place) {
		this.floor = floor;
		this.row = row;
		this.place = place;
		
		// the car variable is intentionally left null, this indicates an empty spot
	}

	/**
	 * @return the car occupying this spot, or null
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * @param car the car to set
	 */
	public void setCar(Car car) throws IllegalStateException {
		if (this.car == null) {
			this.car = car;
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
	 * @return a boolean indicating whether the spot is free
	 */
	public boolean isEmpty() {
		return car == null;
	}
}
