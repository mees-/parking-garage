package parkinggarage.model;

import java.util.*;

import parkinggarage.util.Settings;
import parkinggarage.util.Ticker;
import parkinggarage.util.Time;

public class Simulator implements Ticker {
	private Garage garage;
	private CarQueue unplannedEntrance = new CarQueue();
	private CarQueue subscriberEntrance = new CarQueue();
	private List<ReservationCar> reservations = new ArrayList<>();
	private CarQueue payment = new CarQueue();
	private CarQueue exit = new CarQueue();
	private Settings settings;
	
	private Time time = new Time(0, 0, 0);
	
	// this is for collecting stats
	private Map<CarType, Integer> carsArrivedLastTick = new HashMap<>();
	private Map<CarType, Integer> carsLeftQueueLastTick = new HashMap<>();
	
	public Simulator(Settings settings) {
		this.settings = settings;
		garage = new Garage(
				settings.getFloors(),
				settings.getRows(),
				settings.getPlaces(),
				settings.getSubscriberSpots()
				);
		
		// set this to 0 once because reservation cars never leave the queue
		carsLeftQueueLastTick.put(CarType.RESERVATION, 0);
	}
	
	public Simulator() {
		// use default settings
		this(new Settings());
	}

	@Override
	public void tick() {
		// remove cars from exit queue
		exit.removeAmount(settings.getExitSpeed());
		
		// remove the appropriate number of cars from payment queue
		exit.addAll(payment.removeAmount(settings.getPaymentSpeed()));
		
		// increment time
		time = time.addMinutes(1);

		tickSpots();
		hanldeReservations();
		handleEntrances();
		handleArriving();
	}

	/*
	 * Methods for tasks for a tick
	 */
	private void tickSpots() {
		// tick all parking spots
		for (Spot spot : garage.getFilteredSpots(spot -> spot.getCar() != null)) {
			if (spot.getCar().getExitTime().smallerThanOrEquals(this.time)) {
				Car leavingCar = spot.getCar();
				spot.clearCar();
				if (leavingCar.getType() == CarType.SUBSCRIBER) {
					exit.add(leavingCar);
				} else {
					payment.add(leavingCar);
				}
			}
		}
	}

	private void handleEntrances() {
		// subscribers
		for (int i = 0; i < settings.getSubscriberEnterSpeed(); i++) {
			if (subscriberEntrance.isEmpty()) {
				break;
			}
			Car arrivingCar = subscriberEntrance.remove();
			Spot newSpot;
			if (arrivingCar instanceof ReservationCar) {
				newSpot = ((ReservationCar) arrivingCar).getSpot();
			} else {
				newSpot = garage.getFreeSpot(CarType.SUBSCRIBER);
			}
			if (newSpot == null) {
				break;
			}
			newSpot.setCar(arrivingCar);
			arrivingCar.setEntranceTime(time);
		}
		
		// unplanned cars
		int unplannedHandled = 0;
		while (unplannedHandled < settings.getUnplannedEnterSpeed()
				&& !unplannedEntrance.isEmpty()
				&& garage.getFreeSpot(CarType.UNPLANNED) != null) {
			Spot newSpot = garage.getFreeSpot(CarType.UNPLANNED);
			newSpot.setCar(unplannedEntrance.remove());
			newSpot.getCar().setEntranceTime(time);
			unplannedHandled++;
		}
	}

	private void handleArriving() {
		// subscribers
		carsLeftQueueLastTick.put(CarType.SUBSCRIBER, 0);
		int subscribersArriving = getCarsArriving(CarType.SUBSCRIBER);
		for (int i = 0; i < subscribersArriving; i++) {
			if (settings.getLeavingChance(subscriberEntrance.size()) < settings.getRandom().nextDouble()) {
				subscriberEntrance.add(new Car(CarType.SUBSCRIBER, settings.getRandom()));
			} else {
				carsLeftQueueLastTick.put(CarType.SUBSCRIBER, carsLeftQueueLastTick.get(CarType.SUBSCRIBER) + 1);
			}
		}
		carsArrivedLastTick.put(CarType.SUBSCRIBER, subscribersArriving);
		
		// unplanned
		carsLeftQueueLastTick.put(CarType.UNPLANNED, 0);
		int unplannedArriving = getCarsArriving(CarType.UNPLANNED);
		for (int i = 0; i < unplannedArriving; i++) {
			if (settings.getLeavingChance(unplannedEntrance.size()) < settings.getRandom().nextDouble()) {
				unplannedEntrance.add(new Car(CarType.UNPLANNED, settings.getRandom()));
			} else {
				carsLeftQueueLastTick.put(CarType.UNPLANNED, carsLeftQueueLastTick.get(CarType.UNPLANNED) + 1);
			}
		}

		carsArrivedLastTick.put(CarType.UNPLANNED, unplannedArriving);
	}
	
	private int getCarsArriving(CarType type) {
		int averageNumberOfCarsPerHour = settings.getCarsArriving(time, type);
		if (type == CarType.SUBSCRIBER) {
			double factor = (double) garage.getNumberOfFreeSpots(CarType.SUBSCRIBER) / settings.getSubscriberSpots() * 5;
			averageNumberOfCarsPerHour = (int) Math.round(averageNumberOfCarsPerHour * factor);
		}

		double standardDeviation = averageNumberOfCarsPerHour * 0.3;
		double numberOfCarsPerHour = averageNumberOfCarsPerHour
			+ settings.getRandom().nextGaussian() * standardDeviation;
		return (int)Math.round(numberOfCarsPerHour / 60);
	}

	private void hanldeReservations() {
		
		// generate some reservations
		if (settings.getRandom().nextDouble() > 0.9) {
			int totalMinutesIn5Days = 5 * 24 * 60;
			int reservationTimeInMinutes = (int) settings.getRandom().nextDouble() * totalMinutesIn5Days;
			Time reservationTime = time.add((new Time(1, 0, 0)).addMinutes(reservationTimeInMinutes));
			this.reservations.add(new ReservationCar(reservationTime, settings));
		}

		carsArrivedLastTick.put(CarType.RESERVATION, 0);
		for (ReservationCar reservation : reservations) {
			if (reservation.getStartTime().smallerThanOrEquals(time)
					&& reservation.getSpot() == null) {
						Spot spot = garage.getFreeSpot(CarType.UNPLANNED);
						if (spot != null) {
							spot.reserve();
							reservation.setSpot(spot);
						}
			}
			if (reservation.getEndTime().smallerThanOrEquals(time)) {
				reservation.getSpot().freeReservation();
				reservations.remove(reservation);
			}
			if (reservation.getArrivalTime() != null
						&& reservation.getArrivalTime().smallerThanOrEquals(time)
						&& !subscriberEntrance.contains(reservation)
						&& reservation.getEntranceTime() == null) {
				subscriberEntrance.add(reservation);
				carsArrivedLastTick.put(CarType.RESERVATION, carsArrivedLastTick.get(CarType.RESERVATION) + 1);
			} 
		}
	}
	
	public String toString() {
		return super.toString() + " " +
				"occupied spots: " + garage.getNumberOfOccupiedSpots() + " " +
				"Cars in entrances: " + (subscriberEntrance.size() + unplannedEntrance.size()) + " " +
				"Paying cars: " + payment.size() + " " + 
				"Exiting cars: " + exit.size() + " " + 
				"time: " + time;
	}

	/**
	 * @return the garage
	 */
	public Garage getGarage() {
		return garage;
	}

	/**
	 * @return the unplannedEntrance
	 */
	public CarQueue getUnplannedEntrance() {
		return unplannedEntrance;
	}

	/**
	 * @return the subscriberEntrance
	 */
	public CarQueue getSubscriberEntrance() {
		return subscriberEntrance;
	}

	/**
	 * @return the payment
	 */
	public CarQueue getPayment() {
		return payment;
	}

	/**
	 * @return the exit
	 */
	public CarQueue getExit() {
		return exit;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}
	
	public int getCarsArrivedLastTick(CarType type) {
		return carsArrivedLastTick.get(type);
	}
	
	public int getCarsLeftQueueLastTick(CarType type) {
		return carsLeftQueueLastTick.get(type);
	}
}
