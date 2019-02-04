package parkinggarage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import parkinggarage.model.Simulator;
import parkinggarage.util.Settings;
import parkinggarage.util.StatCollector;
import parkinggarage.view.View;

public class App {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		System.out.println("Hello world, this is the entry point in the application");
		Settings settings = new Settings();
		Simulator sim = new Simulator(settings);
		StatCollector stats = new StatCollector(100000, sim);
		View view = new View(shell, SWT.NO, settings.getFloors(), settings.getRows(), settings.getPlaces());

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			display.readAndDispatch();
			
			sim.tick();
			if (sim.getTime().isWeekDay()) {
				stats.tick();
			}
			view.updateView(sim.getGarage().getSpots());
			view.updateParkingInfo(sim.getGarage().getNumberOfFreeSpots(), sim.getUnplannedEntrance().size() + sim.getSubscriberEntrance().size());
			display.update();
			try {
	            Thread.sleep(settings.getTickPause());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			System.out.println(stats.toString());
		}
		display.dispose();
	}
	/*
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
	}*/

}
