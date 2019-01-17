package parkinggarage.util;

import parkinggarage.model.CarType;

public class Settings {
	
	/*
	 * Defaults
	 */
	private static final int defaultWeekDayUnplannedArrivals = 100; // average number of arriving cars per hour
	private static final int defaultWeekendUnplannedArrivals = 200; // average number of arriving cars per hour
	private static final int defaultWeekDaySubscriberArrivals = 50; // average number of arriving cars per hour
	private static final int defaultWeekendSubscriberArrivals = 5; // average number of arriving cars per hour
	
	private static final int defaultSubscriberEnterSpeed = 3; // number of subscriber cars that can enter per minute
	private static final int defaultUnplannedEnterSpeed = 3; // number of unplanned cars that can enter per minute
	private static final int defaultPaymentSpeed = 7; // number of cars that can pay per minute
	private static final int defaultExitSpeed = 5; // number of cars that can leave per minute
	
	private static final int defaultFloors = 3;
	private static final int defaultRows = 4;
	private static final int defaultPlaces = 30;
	private static final int defaultSubscriberSpots = 30;
	
	private static final long defaultRandomSeed = 0;
	
	private static final int defaultMinutesPerTick = 1;
	
	/*
	 * Actual settings
	 */
	private int weekDayUnplannedArrivals = defaultWeekDayUnplannedArrivals; // average number of arriving cars per hour
	private int weekendUnplannedArrivals = defaultWeekendUnplannedArrivals; // average number of arriving cars per hour
	private int weekDaySubscriberArrivals = defaultWeekDaySubscriberArrivals; // average number of arriving cars per hour
	private int weekendSubscriberArrivals = defaultWeekendSubscriberArrivals; // average number of arriving cars per hour

	private int subscriberEnterSpeed = defaultSubscriberEnterSpeed; // number of cars that can enter per minute
	private int unplannedEnterSpeed = defaultUnplannedEnterSpeed;
	private int paymentSpeed = defaultPaymentSpeed; // number of cars that can pay per minute
	private int exitSpeed = defaultExitSpeed; // number of cars that can leave per minute

	private int floors = defaultFloors;
	private int rows = defaultRows;
	private int places = defaultPlaces;
	private int subscriberSpots = defaultSubscriberSpots;
	
	private long randomSeed = defaultRandomSeed;
	
	private int minutesPerTick = defaultMinutesPerTick;

	/*
	 * Methods for modifying the settings
	 */
	/**
	 * @return the weekDayUnplannedArrivals
	 */
	public int getWeekDayUnplannedArrivals() {
		return weekDayUnplannedArrivals;
	}
	/**
	 * @param weekDayUnplannedArrivals the weekDayUnplannedArrivals to set
	 */
	public void setWeekDayUnplannedArrivals(int weekDayUnplannedArrivals) {
		this.weekDayUnplannedArrivals = weekDayUnplannedArrivals;
	}
	/**
	 * @return the weekendUnplannedArrivals
	 */
	public int getWeekendUnplannedArrivals() {
		return weekendUnplannedArrivals;
	}
	/**
	 * @param weekendUnplannedArrivals the weekendUnplannedArrivals to set
	 */
	public void setWeekendUnplannedArrivals(int weekendUnplannedArrivals) {
		this.weekendUnplannedArrivals = weekendUnplannedArrivals;
	}
	/**
	 * @return the weekDaySubscriberArrivals
	 */
	public int getWeekDaySubscriberArrivals() {
		return weekDaySubscriberArrivals;
	}
	/**
	 * @param weekDaySubscriberArrivals the weekDaySubscriberArrivals to set
	 */
	public void setWeekDaySubscriberArrivals(int weekDaySubscriberArrivals) {
		this.weekDaySubscriberArrivals = weekDaySubscriberArrivals;
	}
	/**
	 * @return the weekendSubscriberArrivals
	 */
	public int getWeekendSubscriberArrivals() {
		return weekendSubscriberArrivals;
	}
	/**
	 * @param weekendSubscriberArrivals the weekendSubscriberArrivals to set
	 */
	public void setWeekendSubscriberArrivals(int weekendSubscriberArrivals) {
		this.weekendSubscriberArrivals = weekendSubscriberArrivals;
	}
	/**
	 * @return the subscriberEnterSpeed
	 */
	public int getSubscriberEnterSpeed() {
		return subscriberEnterSpeed;
	}
	/**
	 * @param subscriberEnterSpeed the subscriberEnterSpeed to set
	 */
	public void setSubscriberEnterSpeed(int subscriberEnterSpeed) {
		this.subscriberEnterSpeed = subscriberEnterSpeed;
	}
	/**
	 * @return the unplannedEnterSpeed
	 */
	public int getUnplannedEnterSpeed() {
		return unplannedEnterSpeed;
	}
	/**
	 * @param unplannedEnterSpeed the unplannedEnterSpeed to set
	 */
	public void setUnplannedEnterSpeed(int unplannedEnterSpeed) {
		this.unplannedEnterSpeed = unplannedEnterSpeed;
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
	public int getTotalSpots() {
		return floors * rows * places;
	}
	public int getSpots(CarType type) {
		switch (type) {
			case SUBSCRIBER:
				return getSubscriberSpots();
			case UNPLANNED:
				return getTotalSpots() - getSubscriberSpots();
			default:
				return 0;
		}
	}
	/**
	 * @return the subscriberPlaces
	 */
	public int getSubscriberSpots() {
		return subscriberSpots;
	}
	/**
	 * @param subscriberSpots the subscriberPlaces to set
	 */
	public void setSubscriberSpots(int subscriberSpots) {
		this.subscriberSpots = subscriberSpots;
	}
	/**
	 * @return the randomSeed
	 */
	public long getRandomSeed() {
		return randomSeed;
	}
	/**
	 * @param randomSeed the randomSeed to set
	 */
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	/**
	 * @return the minutesPerTick
	 */
	public int getMinutesPerTick() {
		return minutesPerTick;
	}
	/**
	 * @param minutesPerTick the minutesPerTick to set
	 */
	public void setMinutesPerTick(int minutesPerTick) {
		this.minutesPerTick = minutesPerTick;
	}
	
}
