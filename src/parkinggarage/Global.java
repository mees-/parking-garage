package parkinggarage;

import java.util.Random;
import parkinggarage.shared.Time;

public final class Global {
	
	// random generator
	private static Random generator;
	public static void createRandom(long seed) {
		generator = new Random(seed);
	}
	public static void createRandom() {
		generator = new Random();
	}
	public static Random getRandom() {
		if (generator == null) {
			createRandom();
		}
		return generator;
	}
	
	// time
	private static Time simulationTime;
	public static Time getSimulationTime() {
		return simulationTime;
	}
	public static void setSimulationTime(Time time) {
		simulationTime = time;
	}
	
	public static void tick() {
		setSimulationTime(Time.fromMinutes(getSimulationTime().getTotalMinutes() + 1));
	}
}
