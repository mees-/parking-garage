package parkinggarage;

import parkinggarage.model.Spot;
import parkinggarage.shared.Time;
import view.SimulatorView;
import view.View;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Global.createRandom();
		Global.setSimulationTime(new Time(0, 0, 0));
		
		View view = new View(3, 6, 30, new Spot[0]);
		view.repaint();
	}

}
