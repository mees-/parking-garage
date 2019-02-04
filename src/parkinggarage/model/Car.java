package parkinggarage.model;

import java.util.Random;

import parkinggarage.util.Time;

public class Car {
	
	// the times are both nullable, if the car hasn't entered the time will be null. Same for exit.
	private Time entranceTime;
	private Time exitTime;
	
	private CarType type;

	private Random random;
	
	private static Time getRandomStayTime(Random random) {
		int stayInMinutes = (int) (15 + random.nextFloat() * 3 * 60);
		return Time.fromMinutes(stayInMinutes);
		
	}
	
	public Car(CarType type, Time entranceTime, Time exitTime) {
		this.type = type;
		this.entranceTime = entranceTime;
		this.exitTime = exitTime;
	}

	public Car(CarType type, Random random) {
		this.type = type;
		this.random = random;
	}

	/**
	 * @return the entranceTime
	 */
	public Time getEntranceTime() {
		return entranceTime;
	}

	/**
	 * @param entranceTime the entranceTime to set
	 */
	public void setEntranceTime(Time entranceTime) throws IllegalStateException {
		if (this.entranceTime == null) {
			this.entranceTime = entranceTime;
			this.exitTime = entranceTime.add(getRandomStayTime(this.random));
		} else {
			throw new IllegalStateException("Cannot change entranceTime");
		}
	}

	/**
	 * @return the exitTime
	 */
	public Time getExitTime() {
		return exitTime;
	}

	/**
	 * @param exitTime the exitTime to set
	 */
	public void setExitTime(Time exitTime) {
		this.exitTime = exitTime;
	}

	/**
	 * @return the type
	 */
	public CarType getType() {
		return type;
	}
	
	public String toString() {
		return	super.toString() +
				" entranceTime: " + entranceTime +
				" exitTime: " + exitTime +
				" type: " + type;
	}

}
