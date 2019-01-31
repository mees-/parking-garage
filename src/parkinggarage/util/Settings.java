package parkinggarage.util;

import java.util.Random;

import parkinggarage.model.CarType;

public class Settings {
	
	/*
	 * Defaults
	 */
	private static final int defaultWeekDayUnplannedArrivals = 100; // average number of arriving cars per hour
	private static final int defaultWeekendUnplannedArrivals = 200; // average number of arriving cars per hour
	private static final int defaultWeekDaySubscriberArrivals = 50; // average number of arriving cars per hour
	private static final int defaultWeekendSubscriberArrivals = 5; // average number of arriving cars per hour
	
	private static final int defaultSubscriberEnterSpeed = 3; // number of subscriber cars that can enter per minute per entrance
	private static final int defaultUnplannedEnterSpeed = 3; // number of unplanned cars that can enter per minute per entrance
	private static final int defaultPaymentSpeed = 7; // number of cars that can pay per minute
	private static final int defaultExitSpeed = 5; // number of cars that can leave per minute
	
	private static final int defaultFloors = 3;
	private static final int defaultRows = 4;
	private static final int defaultPlaces = 30;
	private static final int defaultSubscriberSpots = 80;
	
	private static final Long defaultRandomSeed = null;

	private static final int defaultTickPause = 50;

	private static final int defaultReservationTimeBefore = 15;
	private static final int defaultReservationTimeAfter = 30;
	private static final double defaultReservationShowChance = 0.95;
	
	private static final double defaultQueueLeaveThreshold = 5;
	private static final double defaultQueueLeaveScaling = 8;
	
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
	
	private Long randomSeed = defaultRandomSeed;
	private Random generator;

	private int tickPause = defaultTickPause;

	private int reservationTimeBefore = defaultReservationTimeBefore;
	private int reservationTimeAfter = defaultReservationTimeAfter;
	private double reservationShowChance = defaultReservationShowChance;
	
	/*
	 * the next to are relating to people leaving the queue (not joining)
	 * it uses the function y = a(x-p)
	 * where queueLeaveScaling is a and queueLeaveThreshold is p
	 * this is essentially a straight line shifted to the right by p with gradient a
	 */
	private double queueLeaveThreshold = defaultQueueLeaveThreshold; 	// this is when people will start possibly not joining the queue (in number of cars)
	private double queueLeaveScaling = defaultQueueLeaveScaling;		// this is the gradient of the line which determines the chance of leaving
	
	/*
	 * Methods for modifying the settings
	 */

	public int getCarsArriving(Time time, CarType type) {
		if (time.isWeekDay()) {
			switch (type) {
				case SUBSCRIBER:
					return weekDaySubscriberArrivals;
				case UNPLANNED:
					return weekDayUnplannedArrivals;
				default:
					return 0;
			}
		} else {
			switch (type) {
				case SUBSCRIBER:
					return weekendSubscriberArrivals;
				case UNPLANNED:
					return weekendUnplannedArrivals;
				default:
					return 0;
			}
		}
	}
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
	public Long getRandomSeed() {
		return randomSeed;
	}
	/**
	 * @param randomSeed the randomSeed to set
	 */
	public void setRandomSeed(Long randomSeed) {
		this.randomSeed = randomSeed;
	}

	public Random getRandom() {
		if (generator == null) {
			if (getRandomSeed() == null) {
				generator = new Random();
			} else {
				generator = new Random(getRandomSeed());
			}
		}

		return generator;
	}
	
	public int getTickPause() {
		return tickPause;
	}
	
	public void setTickPause(int tickPause) {
		this.tickPause = tickPause;
	}

	public int getReservationTimeBefore() {
		return this.reservationTimeBefore;
	}

	public void setReservationTimeBefore(int reservationTimeBefore) {
		this.reservationTimeBefore = reservationTimeBefore;
	}

	public int getReservationTimeAfter() {
		return this.reservationTimeAfter;
	}

	public void setReservationTimeAfter(int reservationTimeAfter) {
		this.reservationTimeAfter = reservationTimeAfter;
	}

	/**
	 * @return the reservationShowChance
	 */
	public double getReservationShowChance() {
		return reservationShowChance;
	}

	/**
	 * @param reservationShowChance the reservationShowChance to set
	 */
	public void setReservationShowChance(double reservationShowChance) {
		this.reservationShowChance = reservationShowChance;
	}
	
	/**
	 * @param carsInQueue the amount of cars in the queue
	 * @return the chance a car will not join the queue (leave it) as a double from 0.0 to 1.0
	 */
	public double getLeavingChance(int carsInQueue) {
		double x = carsInQueue;
		double y = queueLeaveScaling * (x - queueLeaveThreshold) / 100; // the division by 100 is to scale it down from 0-100 to 0-1.0
		if (y > 1.0) {
			y = 1.0;
		}
		return y;
	}
	/**
	 * @return the queueLeaveThreshold
	 */
	public double getQueueLeaveThreshold() {
		return queueLeaveThreshold;
	}
	/**
	 * @param queueLeaveThreshold the queueLeaveThreshold to set
	 */
	public void setQueueLeaveThreshold(double queueLeaveThreshold) {
		this.queueLeaveThreshold = queueLeaveThreshold;
	}
	/**
	 * @return the queueLeaveScaling
	 */
	public double getQueueLeaveScaling() {
		return queueLeaveScaling;
	}
	/**
	 * @param queueLeaveScaling the queueLeaveScaling to set
	 */
	public void setQueueLeaveScaling(double queueLeaveScaling) {
		this.queueLeaveScaling = queueLeaveScaling;
	}
}
