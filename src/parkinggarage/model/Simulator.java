package parkinggarage.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import parkinggarage.shared.Settings;
import parkinggarage.shared.Ticker;
import parkinggarage.shared.Time;

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
				settings.getSubscriberPlaces()
				);
		
		if (settings.getRandomSeed() != 0) {
			this.random = new Random(settings.getRandomSeed());
		}
		
	}

	@Override
	public void tick() {
		// increment time
		time = time.addMinutes(settings.getMinutesPerTick());
		// tick all parking spots
		for (Spot spot : garage.getSpots()) {
			if (spot.getCar().getExitTime().greaterThan(this.time)) {
				Car leavingCar = spot.getCar();
				spot.setCar(null);
				if (leavingCar.getType() == CarType.SUBSCRIBER) {
					exit.add(leavingCar);
				} else {
					payment.add(leavingCar);
				}
			}
		}
		
		// remove the appropriate number of cars from payment queue
		exit.addAll(payment.removeAmount(settings.getPaymentSpeed()));
		
		// remove cars from exit queue
		exit.removeAmount(settings.getExitSpeed());
		
		// handle entrances
		// subscribers
	}
}
