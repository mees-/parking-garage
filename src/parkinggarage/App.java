package parkinggarage;

import parkinggarage.model.Simulator;
import parkinggarage.util.Settings;
import parkinggarage.util.StatCollector;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Settings settings = new Settings();
		settings.setUnplannedEnterSpeed(5);
		settings.setSubscriberEnterSpeed(6);
		Simulator sim = new Simulator(settings);
		StatCollector weekDay = new StatCollector(100000, sim);
		StatCollector weekend = new StatCollector(100000, sim);
		
		long ticks = 10000;
		int pctCount = 1;
		
		for (long i = 0; i < ticks; i++) {
			sim.tick();
			if (sim.getTime().isWeekDay()) {
				weekDay.tick();
			} else {
				weekend.tick();
			}
			if (i / ticks >= pctCount * 0.1) {
				System.out.println(pctCount * 10 + "%");
			}
		}
		System.out.print('\n');
		
		System.out.println("Weekdays: " + weekDay.toString());
		System.out.println("Weekends: " + weekend.toString());
	}

}
