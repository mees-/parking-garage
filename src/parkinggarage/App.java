package parkinggarage;

import parkinggarage.model.Simulator;
import parkinggarage.util.Settings;
import parkinggarage.util.StatCollector;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Settings settings = new Settings();
		Simulator sim = new Simulator(settings);
		StatCollector stats = new StatCollector(100000, sim);
		
		while (true) {
			for (int i = 0; i < 60; i++) {
				sim.tick();
				if (sim.getTime().isWeekDay()) {
					stats.tick();
				}
			}	
			System.out.println(stats.toString());
//			System.out.println(StatCollector.getReducedList(stats.arrivingCars.get(CarType.SUBSCRIBER), 60));
		}
	}

}
