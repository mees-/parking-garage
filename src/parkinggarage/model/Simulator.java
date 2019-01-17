package parkinggarage.model;

import java.util.Random;

import parkinggarage.util.Settings;
import parkinggarage.util.Ticker;
import parkinggarage.util.Time;

public class Simulator implements Ticker {
	private Garage garage;
	private CarQueue unplannedEntrance = new CarQueue();
	private CarQueue subscriberEntrance = new CarQueue();
	private CarQueue payment = new CarQueue();
	private CarQueue exit = new CarQueue();
	private Settings settings;
	
	private Time time = new Time(0, 0, 0);
	private Random random = new Random();
	
	public Simulator(Settings settings) {
		this.settings = settings;
		garage = new Garage(
				settings.getFloors(),
				settings.getRows(),
				settings.getPlaces(),
				settings.getSubscriberSpots()
				);
		
		if (settings.getRandomSeed() != 0) {
			this.random = new Random(settings.getRandomSeed());
		}
		
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
		time = time.addMinutes(settings.getMinutesPerTick());

		// tick all parking spots
		for (Spot spot : garage.getFilterdSpots(spot -> spot.getCar() != null)) {
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
		
		// handle entrances
		// subscribers
		int subscribersHandled = 0;
		while (subscribersHandled < settings.getSubscriberEnterSpeed()
				&& !subscriberEntrance.isEmpty()
				&& garage.getFreeSpot(CarType.SUBSCRIBER) != null) {
			Spot newSpot = garage.getFreeSpot(CarType.SUBSCRIBER);
			newSpot.setCar(subscriberEntrance.remove());
			subscribersHandled++;
		}
		// unplanned cars
		int unplannedHandled = 0;
		while (unplannedHandled < settings.getUnplannedEnterSpeed()
				&& !unplannedEntrance.isEmpty()
				&& garage.getFreeSpot(CarType.UNPLANNED) != null) {
			Spot newSpot = garage.getFreeSpot(CarType.UNPLANNED);
			newSpot.setCar(unplannedEntrance.remove());
			unplannedHandled++;
		}
		
		// handle arriving cars
		// subscribers
		for (int i = 0; i < getCarsArriving(CarType.SUBSCRIBER); i++) {
			// TODO: the time starts from when it's added to the queue now,
			// fix it so it starts when the car gets to a spot
			subscriberEntrance.add(new Car(CarType.SUBSCRIBER, time, random));
		}
		// unplanned
		for (int i = 0; i < getCarsArriving(CarType.UNPLANNED); i++) {
			// TODO: the time starts from when it's added to the queue now,
			// fix it so it starts when the car gets to a spot
			unplannedEntrance.add(new Car(CarType.UNPLANNED, time, random));
		}
	}
	
	private int getCarsArriving(CarType type) {
		int averageNumberOfCarsPerHour = 
				time.isWeekDay()
				? type == CarType.SUBSCRIBER
					? settings.getWeekDaySubscriberArrivals()
					: settings.getWeekDayUnplannedArrivals()
				: type == CarType.UNPLANNED
					? settings.getWeekendSubscriberArrivals()
					: settings.getWeekendUnplannedArrivals();
		double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
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

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}
}
