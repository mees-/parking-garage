package parkeergarage.model;

import parkeergarage.shared.Time;

public abstract class Car {
	
	// the times are both nullable, if the car hasn't entered the time will be null. Same for exit.
	private Time entranceTime;
	private Time exitTime;
	
	/**
	 * @return the entranceTime
	 */
	public Time getEntranceTime() {
		return entranceTime;
	}
	/**
	 * @return the exitTime
	 */
	public Time getExitTime() {
		return exitTime;
	}
}
