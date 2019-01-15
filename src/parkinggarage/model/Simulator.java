package parkinggarage.model;

import parkinggarage.shared.Settings;

public class Simulator {
	private Garage garage;
	private CarQueue carQueue = new CarQueue();
	private Settings settings;
	
	public Simulator(Settings settings) {
		this.settings = settings;
		garage = new Garage(
				settings.getFloors(),
				settings.getRows(),
				settings.getPlaces(),
				settings.getSubscriberPlaces()
				);
	}
}
