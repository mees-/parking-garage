package parkinggarage.shared;

public class Settings {
	
	/*
	 * Defaults
	 */
	private static final int defaultWeekDayArrivals = 100; // average number of arriving cars per hour
	private static final int defaultWeekendArrivals = 200; // average number of arriving cars per hour
	private static final int defaultWeekDayPassArrivals = 50; // average number of arriving cars per hour
	private static final int defaultWeekendPassArrivals = 5; // average number of arriving cars per hour
	
	private static final int defaultEnterSpeed = 3; // number of cars that can enter per minute
	private static final int defaultPaymentSpeed = 7; // number of cars that can pay per minute
	private static final int defaultExitSpeed = 5; // number of cars that can leave per minute
	
	private static final int defaultFloors = 3;
	private static final int defaultRows = 4;
	private static final int defaultPlaces = 30;
	private static final int defaultSubscriberPlaces = 30;
	
	
	/*
	 * Actual settings
	 */
	private int weekDayArrivals = defaultWeekDayArrivals; // average number of arriving cars per hour
	private int weekendArrivals = defaultWeekendArrivals; // average number of arriving cars per hour
	private int weekDayPassArrivals = defaultWeekDayPassArrivals; // average number of arriving cars per hour
	private int weekendPassArrivals = defaultWeekendPassArrivals; // average number of arriving cars per hour

	private int enterSpeed = defaultEnterSpeed; // number of cars that can enter per minute
	private int paymentSpeed = defaultPaymentSpeed; // number of cars that can pay per minute
	private int exitSpeed = defaultExitSpeed; // number of cars that can leave per minute

	private int floors = defaultFloors;
	private int rows = defaultRows;
	private int places = defaultPlaces;
	private int subscriberPlaces = defaultSubscriberPlaces;

	
	/*
	 * Methods for modifying the settings
	 */
	/**
	 * @return the weekDayArrivals
	 */
	public int getWeekDayArrivals() {
		return weekDayArrivals;
	}
	/**
	 * @param weekDayArrivals the weekDayArrivals to set
	 */
	public void setWeekDayArrivals(int weekDayArrivals) {
		this.weekDayArrivals = weekDayArrivals;
	}
	/**
	 * @return the weekendArrivals
	 */
	public int getWeekendArrivals() {
		return weekendArrivals;
	}
	/**
	 * @param weekendArrivals the weekendArrivals to set
	 */
	public void setWeekendArrivals(int weekendArrivals) {
		this.weekendArrivals = weekendArrivals;
	}
	/**
	 * @return the weekDayPassArrivals
	 */
	public int getWeekDayPassArrivals() {
		return weekDayPassArrivals;
	}
	/**
	 * @param weekDayPassArrivals the weekDayPassArrivals to set
	 */
	public void setWeekDayPassArrivals(int weekDayPassArrivals) {
		this.weekDayPassArrivals = weekDayPassArrivals;
	}
	/**
	 * @return the weekendPassArrivals
	 */
	public int getWeekendPassArrivals() {
		return weekendPassArrivals;
	}
	/**
	 * @param weekendPassArrivals the weekendPassArrivals to set
	 */
	public void setWeekendPassArrivals(int weekendPassArrivals) {
		this.weekendPassArrivals = weekendPassArrivals;
	}
	/**
	 * @return the enterSpeed
	 */
	public int getEnterSpeed() {
		return enterSpeed;
	}
	/**
	 * @param enterSpeed the enterSpeed to set
	 */
	public void setEnterSpeed(int enterSpeed) {
		this.enterSpeed = enterSpeed;
	}
	/**
	 * @return the paymentSpeed
	 */
	public int getPaymentSpeed() {
		return paymentSpeed;
	}
	/**
	 * @param paymentSpeed the paymentSpeed to set
	 */
	public void setPaymentSpeed(int paymentSpeed) {
		this.paymentSpeed = paymentSpeed;
	}
	/**
	 * @return the exitSpeed
	 */
	public int getExitSpeed() {
		return exitSpeed;
	}
	/**
	 * @param exitSpeed the exitSpeed to set
	 */
	public void setExitSpeed(int exitSpeed) {
		this.exitSpeed = exitSpeed;
	}
	/**
	 * @return the floors
	 */
	public int getFloors() {
		return floors;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}
	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * @return the places
	 */
	public int getPlaces() {
		return places;
	}
	/**
	 * @param places the places to set
	 */
	public void setPlaces(int places) {
		this.places = places;
	}
	/**
	 * @return the subscriberPlaces
	 */
	public int getSubscriberPlaces() {
		return subscriberPlaces;
	}
	/**
	 * @param subscriberPlaces the subscriberPlaces to set
	 */
	public void setSubscriberPlaces(int subscriberPlaces) {
		this.subscriberPlaces = subscriberPlaces;
	}
	
}
