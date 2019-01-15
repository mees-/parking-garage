package parkinggarage.model;

import java.util.Random;

import parkinggarage.shared.Settings;
import parkinggarage.shared.Ticker;
import parkinggarage.shared.Time;

public class Simulator implements Ticker {
	private Garage garage;
	private CarQueue entranceQueue;
	private CarQueue paymentQueue;
	private CarQueue exitQueue;
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
		
		entranceQueue = new CarQueue(settings.getSubscribersFirstInQueue());
		paymentQueue = new CarQueue(settings.getSubscribersFirstInQueue());
		exitQueue = new CarQueue(settings.getSubscribersFirstInQueue());
		
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
				paymentQueue.push(leavingCar);
			}
		}
		
		// remove the appropriate number of cars from payment queue
		for (int i = 0; i < settings.getPaymentSpeed(); i++) {
			
		}
		
	}
}
