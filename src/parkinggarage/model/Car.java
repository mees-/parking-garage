package parkinggarage.model;

import java.util.Random;

import parkinggarage.util.Time;

public class Car {
	
	// the times are both nullable, if the car hasn't entered the time will be null. Same for exit.
	private Time entranceTime;
	private Time exitTime;
	
	private CarType type;
	
	private static Time getRandomStayTime(Random random) {
		int stayInMinutes = (int) (15 + random.nextFloat() * 3 * 60);
		return Time.fromMinutes(stayInMinutes);
		
	}
	
	public Car(CarType type, Time entranceTime, Time exitTime) {
		this.type = type;
		this.entranceTime = entranceTime;
		this.exitTime = exitTime;
	}
	
	
	public Car(CarType type, Time entranceTime, Random random) {
		this(type, entranceTime, entranceTime.add(Car.getRandomStayTime(random)));
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
	public void setEntranceTime(Time entranceTime) {
		this.entranceTime = entranceTime;
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
}
