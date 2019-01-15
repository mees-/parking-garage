package parkinggarage;

import parkinggarage.shared.Time;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world, this is the entry point in the application");
		Global.createRandom();
		Global.setSimulationTime(new Time(0, 0, 0));
	}

}
