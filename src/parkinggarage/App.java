package parkinggarage;

import parkinggarage.model.Simulator;
import parkinggarage.util.Settings;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Settings settings = new Settings();
		settings.setUnplannedEnterSpeed(5);
		settings.setSubscriberEnterSpeed(6);
		Simulator sim = new Simulator(settings);
		while (true) {
			sim.tick();
			System.out.println(sim.toString());
		}
	}

}
