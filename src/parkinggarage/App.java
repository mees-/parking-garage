package parkinggarage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import parkinggarage.model.Simulator;
import parkinggarage.util.Settings;
import parkinggarage.util.StatCollector;
import view.View;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Settings settings = new Settings();
		Simulator sim = new Simulator(settings);
		StatCollector stats = new StatCollector(100000, sim);
		View view = new View(settings.getFloors(), settings.getRows(), settings.getPlaces());
		
		view.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
		
		while (true) {
			for (int i = 0; i < 60; i++) {
				sim.tick();
				if (sim.getTime().isWeekDay()) {
					stats.tick();
				}
				view.updateView(sim.getGarage().getSpots());
				try {
		            Thread.sleep(settings.getTickPause());
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			}
			System.out.println(stats.toString());
//			System.out.println(StatCollector.getReducedList(stats.arrivingCars.get(CarType.SUBSCRIBER), 60));
		}
	}

}
